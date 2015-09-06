package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentType;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 19.12.2010
 * Time: 23:44:17
 */
public class FreeSegmentHandler
{
    private CuttingDrawing cuttingDrawing;
    private ElementDragHandle elementDragHandle;

    private List<Segment> freeSegments = new ArrayList<Segment>();
    private boolean freeCalculated = false;

    public FreeSegmentHandler(CuttingDrawing cuttingDrawing, ElementDragHandle elementDragHandle)
    {
        this.cuttingDrawing = cuttingDrawing;
        this.elementDragHandle = elementDragHandle;
    }


    private Segment addFree2Blue0(Segment blueSegment, Element element, boolean rotated)
    {
        Segment elementSegment = new Segment();
        elementSegment.setSegmentType(SegmentType.free);
        elementSegment.setPadding(blueSegment.getPadding());
        elementSegment.setElement(element, rotated);
        elementSegment.setElement(null);

        blueSegment.addSegment(elementSegment);

        return elementSegment;
    }

    private Segment addFree2Blue(Segment blueSegment, Element element, boolean rotated)
    {

        Segment greenSegment = new Segment();
        greenSegment.setSegmentType(SegmentType.green);
        greenSegment.setPadding(blueSegment.getPadding());

        blueSegment.addSegment(greenSegment);

        Segment blueSegment0 = new Segment();
        blueSegment0.setSegmentType(SegmentType.blue);
        blueSegment0.setPadding(greenSegment.getPadding());

        greenSegment.addSegment(blueSegment0);


        return addFree2Blue0(blueSegment0, element, rotated);
    }

    private Segment addFree2Green(Segment greenSegment, Element element, boolean rotated)
    {
        Segment blueSegment = new Segment();
        blueSegment.setSegmentType(SegmentType.blue);
        blueSegment.setPadding(greenSegment.getPadding());

        Segment elementSegment = addFree2Blue(blueSegment, element, rotated);

        greenSegment.addSegment(blueSegment);

        return elementSegment;
    }

    private Segment addFree2Red(Segment redSegment, Element element, boolean rotated)
    {
        Segment greenSegment = new Segment();
        greenSegment.setSegmentType(SegmentType.green);
        greenSegment.setPadding(redSegment.getPadding());

        Segment elementSegment = addFree2Green(greenSegment, element, rotated);

        redSegment.addSegment(greenSegment);

        return elementSegment;
    }

    private Segment addFree2Gray(Segment graySegment, Element element, boolean rotated)
    {

        Segment redSegment = new Segment();
        redSegment.setSegmentType(SegmentType.red);
        redSegment.setPadding(graySegment.getPadding());

        Segment elementSegment = addFree2Red(redSegment, element, rotated);

        graySegment.addSegment(redSegment);
        return elementSegment;
    }

    public void removeFreeSegment(Segment segment)
    {
        freeSegments.remove(segment);
    }

    public void clearFreeSements()
    {
        for (Segment segment : freeSegments)
        {
            SegmentFigure segmentFigure = getCuttingDrawing().findSegmentFigure(segment);
            elementDragHandle.removeSegment(segmentFigure);
        }
        freeSegments.clear();
        freeCalculated = false;
        //надо обновлять сегмент потому что при удалении из буффера элемента надо перерисовать текстовые фигуры для сегментов
        elementDragHandle.reloadSegment();
    }


    public void prepareFreeSegments()
    {
        if (!freeCalculated)
        {
            //todo нужно еже добавить функциональность перещета свободнах квадратов
            clearFreeSements();

            Element element = elementDragHandle.getElementFigure().getElement();

            addFreeSegments(element, elementDragHandle.getElementFigure().isRotated());

            elementDragHandle.reloadSegment();
        }
    }

    private void addFreeSegments(Element element, boolean rotated)
    {
        freeCalculated = true;
        List<Segment> segmentsToAdd = new ArrayList<Segment>();
        elementDragHandle.fillSegmentsToAdd(getCuttingDrawing().getSegment(), element, rotated, segmentsToAdd);

        Segment elementSegment = null;
        for (Segment segment : segmentsToAdd)
        {
            switch (segment.getSegmentType())
            {

                case red:
                    elementSegment = addFree2Red(segment, element, rotated);
                    break;
                case blue:
                    elementSegment = addFree2Blue(segment, element, rotated);
                    break;
                case green:
                    elementSegment = addFree2Green(segment, element, rotated);
                    break;
                case gray:
                    elementSegment = addFree2Gray(segment, element, rotated);
                    break;
                case element:
                    break;
            }
            if (elementSegment != null)
            {
                freeSegments.add(elementSegment);
            }
        }
    }


    public CuttingDrawing getCuttingDrawing()
    {
        return cuttingDrawing;
    }

    SegmentFigure getFreeSegmentFigure(ElementFigure elementFigure)
    {
        for (Segment segment : freeSegments)
        {
            SegmentFigure segmentFigure = getCuttingDrawing().findSegmentFigure(segment);
            if (segmentFigure.getBounds().intersects(elementFigure.getBounds()))
            {
                return segmentFigure;
            }
        }
        return null;
    }

    public boolean hasFreeSegments()
    {
        return freeSegments != null && freeSegments.size() > 0;
    }
}
