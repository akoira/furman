/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;

import java.util.HashMap;

/**
 * @author Peca
 */
public class BCutter
{
    public static class EqualsElementConstraint implements ElementConstraint
    {
        private HashMap properties = new HashMap();

        @Override
        public void setConstraintProperty(String key, Object value)
        {
            properties.put(key, value);
        }

        @Override
        public Object getConstraintProperty(String key)
        {
            return properties.get(key);
        }

        @Override
        public boolean suit(ElementDescriptor descriptor)
        {
            int width = (Integer) properties.get("width");
            int height = (Integer) properties.get("height");
            return descriptor.getDimension().getWidth() == width &&
                    descriptor.getDimension().getHeight() == height;
        }
    }

    ;

    private ElementProvider elementProvider;

    private long elementCuttedArea;

    private long actualRedSegmentCuttedArea;

    private long actualGreenSegmentCuttedArea;

    private float ratioConstraints = 0.0f;

    private Strips strips;

    private int nextStripIndex;

    private int sawWidth = 0;

    private boolean allowRotation = false;

    private boolean shouldClearStrips;

    private boolean forceBestFit = false;

    public BCutter()
    {
        elementProvider = new ElementProvider();
    }

    private Segment cutGreenRow(int maxItems, int maxLength, int maxWidth, float ratio)
    {
        Segment greenSegment = new Segment();
        greenSegment.setSegmentType(SegmentType.green);
        greenSegment.setPadding(this.sawWidth);

        int itemsCutted = 0;
        int totalLength = 0;
        //nastavime zakladni omezeni
        elementProvider.addWidthConstraint(maxLength);
        elementProvider.addHeightConstraint(maxWidth);
        long greenSegmentCuttedArea = 0;
        this.actualGreenSegmentCuttedArea = 0;
        while (!elementProvider.isEof())
        {
            //vezmeme aktualni okenko na rezani
            ElementDescriptor desr = elementProvider.getCurrent();
            if (desr == null)
            {
                break;
            }
            //pokud uz neni kam rezat tak koncime - если она нигде не сократить, то я бросил
            if ((totalLength >= maxLength) || (itemsCutted >= maxItems))
            {
                break;
            }

            //narezeme aktualni okenko
            Segment elementContainer = new Segment();
            elementContainer.setSegmentType(SegmentType.element);
            elementContainer.setPadding(this.sawWidth);
            elementContainer.setElement(desr.getElement(), desr.isRotation());


            Segment blueSegment = new Segment();
            blueSegment.setSegmentType(SegmentType.blue);
            blueSegment.setPadding(this.sawWidth);
            blueSegment.addSegment(elementContainer);
            greenSegment.addSegment(blueSegment);

            this.elementCuttedArea += desr.getDimension().getArea();
            this.actualGreenSegmentCuttedArea += desr.getDimension().getArea();
            greenSegmentCuttedArea += desr.getDimension().getArea();
            //totalLength += desr.getDimension().getWidth();
            totalLength = greenSegment.getUsedLength();
            itemsCutted++;

            elementProvider.removeCurrent();

            //pridame nova omezeni
            elementProvider.removeConstraints();
            elementProvider.addWidthConstraint(maxLength - totalLength - this.sawWidth);
            elementProvider.addHeightConstraint(maxWidth);
            //elementProvider.addHeightConstraint(desr.getDimension().getHeight());
            int width = greenSegment.getUsedLength();
            int height = greenSegment.getWidth();
            elementProvider.addRatioConstraint(ratio, height, width, greenSegmentCuttedArea);

            //neni co rezat
            if (!elementProvider.moveNext())
            {
                break;
            }

            //pokud nenajdeme zadny vhodny element, zkusime odstranit omezeni
            if (elementProvider.getCurrent() == null)
            {
                elementProvider.removeConstraints();
                elementProvider.addWidthConstraint(maxLength - totalLength - this.sawWidth);
                if (forceBestFit)
                {
                    elementProvider.addHeightConstraintBestFit(height);
                }
                else
                {
                    elementProvider.addHeightConstraint(maxWidth);
                }

                //pokud i tak nic nenajdeme tak koncime
                if (elementProvider.getCurrent() == null)
                {
                    break;
                }
            }
        }
        return greenSegment;
    }

    private Segment getNextStrip()
    {
        if (nextStripIndex >= strips.getSegmentsCount())
        {
            return null;
        }
        Segment graySegment = strips.getSegment(nextStripIndex);
        if (graySegment == null)
        {
            return graySegment;
        }
        if (this.shouldClearStrips)
        {
            graySegment.clear();
        }
        graySegment.setPadding(this.sawWidth);
        nextStripIndex++;
        return graySegment;
    }

    public CutResult cutElements(Strips strips, Element[] elements, int[] buildSequence, int[] maxItems)
    {
        this.shouldClearStrips = true;
        this.elementCuttedArea = 0;
        this.elementProvider.init(elements, buildSequence, this.allowRotation);
        this.elementProvider.moveFirst();
        this.strips = strips;
        this.nextStripIndex = 0;

        Segment graySegment = this.getNextStrip();
        if (graySegment == null)
        {
            return new CutResult(false, null, null, 0, 0);
        }

        int redSegmentCount = 0;
        long redSegmentsTotalArea = 0;
        //dokud je co rezat DELEJ:
        while (!elementProvider.isEof())
        {
            //prepocteme ratio pro cerveny segmenty
            float ratio = this.ratioConstraints;
            ratio = ratio * (elementProvider.getElementsTotalArea() - elementCuttedArea) / (float) (elementProvider.getElementsTotalArea() - ratio * redSegmentsTotalArea);

            //narezat do jedn� rady maximalni pocet elementu podle maxItems
            elementProvider.removeConstraints();
            Segment greenSegment = this.cutGreenRow(maxItems[redSegmentCount], Integer.MAX_VALUE, Integer.MAX_VALUE, ratio);
            //vytvor novy cerveny segment a vloz do neho predchozi segment
            Segment redSegment = new Segment();
            redSegment.setSegmentType(SegmentType.red);
            redSegment.setPadding(this.sawWidth);
            redSegment.addSegment(greenSegment);
            redSegmentCount++;
            this.actualRedSegmentCuttedArea = this.actualGreenSegmentCuttedArea;

            //urcit podle delky a s�rky zeleneho segmentu na jaky strip se cerveny segment umisti
            if (graySegment.canAddSegment(redSegment))
            {
                graySegment.addSegment(redSegment);
            }
            else
            {
                Segment previousGraySegment = graySegment;
                graySegment = getNextStrip();
                while (graySegment != null)
                {
                    if (graySegment.canAddSegment(redSegment))
                    {
                        graySegment.addSegment(redSegment);
                        redSegmentsTotalArea += previousGraySegment.getFreeArea();
                        break;
                    }
                    //uz neexistuje zadny strip na ktery bychom to mohli umistit
                    graySegment = getNextStrip();
                }
                //сздесь похоже можно добовлять новые boards
                if (graySegment == null)
                {
                    return new CutResult(false, null, null, elementCuttedArea, this.nextStripIndex);
                }
            }

            redSegmentsTotalArea += redSegment.getTotalArea();

            //rez dalsi rady, ktere se vejdou do cerveneho segmentu,
            while (!elementProvider.isEof())
            {
                elementProvider.removeConstraints();

                //prepocti ratio
                float greenRatio = (ratio * redSegment.getTotalArea() - this.actualRedSegmentCuttedArea) / (float) redSegment.getFreeArea();
                greenRatio = Math.max(greenRatio, 0);

                Segment otherGreenSegment = this.cutGreenRow(Integer.MAX_VALUE, greenSegment.getLength(), redSegment.getFreeLength() - this.sawWidth, greenRatio);
                this.actualRedSegmentCuttedArea += this.actualGreenSegmentCuttedArea;

                //dokud se nareze aspon neco
                if (otherGreenSegment.getItemsCount() > 0)
                {
                    redSegment.addSegment(otherGreenSegment);
                }
                else
                {
                    break;
                }
            }
            //konec DELEJ
        }
        return new CutResult(true, null, null, elementCuttedArea, this.nextStripIndex);
    }

    public CutResult cutRedSegment(Strips strips, int cutToStripIndex, Element[] elements, int[] buildSequence, int maxItems, long totalCuttedArea, long totalUsedArea, int stripUsedLength,
                                   boolean simulate)
    {
        this.shouldClearStrips = simulate;
        this.elementCuttedArea = totalCuttedArea;
        this.elementProvider.init(elements, buildSequence, this.allowRotation);
        this.strips = strips;
        this.nextStripIndex = cutToStripIndex;

        Segment graySegment = this.getNextStrip();
        if (graySegment == null)
        {
            return new CutResult(false, null, null, 0, 0);
        }

        //umele pridame do aktualniho stripu cerveny segment, ktery simuluje obsazeny prostor
        if (simulate)
        {
            graySegment.addSegment(new Segment(0, stripUsedLength));
        }

        long redSegmentsTotalArea = totalUsedArea;
        //prepocteme ratio pro cerveny segmenty
        float ratio = this.ratioConstraints;
        ratio = ratio * (elementProvider.getElementsTotalArea() - elementCuttedArea) / (float) (elementProvider.getElementsTotalArea() - ratio * redSegmentsTotalArea);

        //narezat do jedn� rady maximalni pocet elementu podle maxItems
        elementProvider.removeConstraints();
        Segment greenSegment = null;

//        elementProvider.addGrayDimensionConstraint(graySegment.getDimensionItem().getDimension());
        //Ограничение на максимальную ширину для детали - используется когда укладывается первая деталь в RED
        elementProvider.addMaxElementConstraint();
        if (BCutterConfiguration.FIX_SIZE_GREEN_SEGMENT_BY_CURRENT_ELEMENT_SIZE)
        {
            //todo вроде попомгло bug 7214 фиксирует размер сигмента
            greenSegment = this.cutGreenRow(maxItems, elementProvider.getCurrent().getDimension().getWidth(),
                    elementProvider.getCurrent().getDimension().getHeight(),
                    ratio);
        }
        else
        {
            greenSegment = this.cutGreenRow(maxItems, Integer.MAX_VALUE, Integer.MAX_VALUE, ratio);
        }
        //vytvor novy cerveny segment a vloz do neho predchozi segment
        Segment redSegment = new Segment();
        redSegment.setSegmentType(SegmentType.red);
        redSegment.setPadding(this.sawWidth);
        redSegment.addSegment(greenSegment);
        this.actualRedSegmentCuttedArea = this.actualGreenSegmentCuttedArea;

        //urcit podle delky a srky zeleneho segmentu na jaky strip se cerveny segment umisti
        if (graySegment.canAddSegment(redSegment))
        {
            graySegment.addSegment(redSegment);
        }
        else
        {
            Segment previousGraySegment = graySegment;
            graySegment = getNextStrip();
            while (graySegment != null)
            {
                if (graySegment.canAddSegment(redSegment))
                {
                    graySegment.addSegment(redSegment);
                    redSegmentsTotalArea += previousGraySegment.getFreeArea();
                    break;
                }
                graySegment = getNextStrip();
            }
            //uz neexistuje zadny strip na ktery bychom to mohli umistit-нет ничего на полосу которую можно ставить
            if (graySegment == null)
            {
                return new CutResult(false, null, null, elementCuttedArea, this.nextStripIndex);
            }
        }

        redSegmentsTotalArea += redSegment.getTotalArea();

        //rez dalsi rady, ktere se vejdou do cerveneho segmentu,
        while (!elementProvider.isEof())
        {
            elementProvider.removeConstraints();

            //prepocti ratio
            float greenRatio = (ratio * redSegment.getTotalArea() - this.actualRedSegmentCuttedArea) / (float) redSegment.getFreeArea();
            greenRatio = Math.max(greenRatio, 0);

            Segment otherGreenSegment = this.cutGreenRow(Integer.MAX_VALUE, greenSegment.getLength(), redSegment.getFreeLength() - this.sawWidth, greenRatio);
            this.actualRedSegmentCuttedArea += this.actualGreenSegmentCuttedArea;

            //dokud se nareze aspon neco
            if (otherGreenSegment.getItemsCount() > 0)
            {
                redSegment.addSegment(otherGreenSegment);
            }
            else
            {
                break;
            }
        }
        return new CutResult(true, elementProvider.getUncuttedElements(), elementCuttedArea, redSegmentsTotalArea, this.nextStripIndex - 1);
    }


    public float getRatio()
    {
        return ratioConstraints;
    }

    public void setRatio(float ratio)
    {
        this.ratioConstraints = ratio;
    }

    public boolean isAllowRotation()
    {
        return allowRotation;
    }

    public void setAllowRotation(boolean allowRotation)
    {
        this.allowRotation = allowRotation;
    }

    public int getSawWidth()
    {
        return sawWidth;
    }

    public void setSawWidth(int sawWidth)
    {
        this.sawWidth = sawWidth;
    }

    public void setForceBestFit(boolean forceBestFit)
    {
        this.forceBestFit = forceBestFit;
    }
}
