/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;

import java.util.*;

public class ElementProvider
{

    private ArrayList<ElementDescriptor> items;
    private ArrayList<ElementDescriptor> workingtList;
    private int[] buildSequence;
    private int currentBuildSequenceIndex;
    private long elementsTotalArea;

    private Map<ElementConstraint, List<ElementDescriptor>> elementConstraints = new HashMap<ElementConstraint, List<ElementDescriptor>>();

    public ElementProvider()
    {
        items = new ArrayList<ElementDescriptor>();
        workingtList = new ArrayList<ElementDescriptor>();
    }

    /* private class ElementSizeComparator implements Comparator{
    public int compare(Object o1, Object o2) {
    Element el1 = (Element)o1;
    Element el2 = (Element)o2;
    el
    }
    }*/

    public void init(Element[] elements, int[] buildSequence, boolean allowRotation)
    {
        //if(elements.length < buildSequence)throw new Exception("elements length cant be smaller than buildSequence lenght");
        assert (elements.length >= buildSequence.length) : String.format("els: %1d seq: %2d", elements.length, buildSequence.length);
        items.clear();
        for (Element element : elements)
        {
            ElementDescriptor desr = new ElementDescriptor(element, false);
            items.add(desr);
            if (allowRotation || element.isRotatable())
            {
                ElementDescriptor desrRotated = new ElementDescriptor(element, true);
                desr.setRelatedDescriptor(desrRotated);
                desrRotated.setRelatedDescriptor(desr);
                items.add(desrRotated);
            }
        }
        this.buildSequence = buildSequence;
        this.elementsTotalArea = Utils.getElementsTotalArea(elements);
        currentBuildSequenceIndex = 0;
        workingtList.clear();
        workingtList.addAll(items);
        moveFirst();
    }

    public void addWidthConstraint(int maxWidth)
    {
        int index = 0;
        while (index < workingtList.size())
        {
            ElementDescriptor desr = workingtList.get(index);
            if (desr.getDimension().getWidth() > maxWidth)
            {
                workingtList.remove(index);
            }
            else
            {
                index++;
            }
        }
    }

    public void addHeightConstraint(int maxHeight)
    {
        int index = 0;
        while (index < workingtList.size())
        {
            ElementDescriptor desr = workingtList.get(index);
            if (desr.getDimension().getHeight() > maxHeight)
            {
                workingtList.remove(index);
            }
            else
            {
                index++;
            }
        }
    }

    public void addHeightConstraintBestFit(int maxHeight)
    {
        int index = 0;
        int bestFoundHeight = 0;
        for (ElementDescriptor desr : workingtList)
        {
            if (desr.getDimension().getHeight() > maxHeight)
                continue;
            bestFoundHeight = Math.max(bestFoundHeight, desr.getDimension().getHeight());
        }

        while (index < workingtList.size())
        {
            ElementDescriptor desr = workingtList.get(index);
            if (desr.getDimension().getHeight() != bestFoundHeight)
            {
                workingtList.remove(index);
            }
            else
            {
                index++;
            }
        }
    }

    /**
     * Ограничиваем деталь по размеру листа - нужно для остатков чтобы первыми брались всегда остатки
     *
     * @param grayDim
     */
    public void addGrayDimensionConstraint(Dimension grayDim)
    {
        Collections.sort(workingtList);
        ArrayList<ElementDescriptor> result = new ArrayList<ElementDescriptor>();
        int i = 0;
        while (i < workingtList.size())
        {
            if (workingtList.get(i).getDimension().getWidth() <= grayDim.getWidth() && workingtList.get(i).getDimension().getHeight() <= grayDim.getHeight())
            {
                putElementTo(result, i);
                i++;
            }
            else
            {
                i++;
                continue;
            }
        }
        workingtList.clear();
        workingtList.addAll(result);
    }

    private void putElementTo(ArrayList<ElementDescriptor> result, int i)
    {
        result.add(workingtList.get(i));
        if (workingtList.get(i).getRelatedDescriptor() != null)
        {
            result.add(workingtList.get(i).getRelatedDescriptor());
        }
    }

    /**
     * Ограничение на максимальную ширину для детали - используется когда укладывается первая деталь в RED
     */
    public void addMaxElementConstraint()
    {
        Collections.sort(workingtList);
        int width = workingtList.get(0).getDimension().getWidth();
        ArrayList<ElementDescriptor> result = new ArrayList<ElementDescriptor>();
        int i = 0;
        while (i < workingtList.size())
        {
            if (workingtList.get(i).getDimension().getWidth() >= width)
            {
                putElementTo(result, i);
                i++;
            }
            else
            {
                break;
            }
        }
        workingtList.clear();
        workingtList.addAll(result);
    }


    public void addRatioConstraint(float ratio, int height, int width, long cuttedArea)
    {
        int index = 0;
        while (index < workingtList.size())
        {
            ElementDescriptor desr = workingtList.get(index);
            Dimension dim = desr.getDimension();

            //ratio = 0.99f;
            boolean pass;
            pass = dim.getArea() >= ratio * Math.max(height, dim.getHeight()) * (width + dim.getWidth()) - cuttedArea;

            if (!pass)
            {
                workingtList.remove(index);
            }
            else
            {
                index++;
            }
        }
    }

    public void removeConstraints()
    {
        workingtList.clear();
        elementConstraints.clear();
        for (ElementDescriptor desr : items)
        {
            if (!desr.isCutted())
            {
                workingtList.add(desr);
            }
        }
    }

    public Element[] getUncuttedElements()
    {
        Object tag = new Object();
        ArrayList<Element> list = new ArrayList<Element>();
        for (ElementDescriptor desr : items)
        {
            if (desr.getTag() == tag)
                continue;
            if (desr.isCutted())
                continue;
            list.add(desr.getElement());
            if (desr.getRelatedDescriptor() != null)
            {
                desr.getRelatedDescriptor().setTag(tag);
            }
        }
        return list.toArray(new Element[list.size()]);
    }

    public ElementDescriptor getCurrent()
    {
        Collections.sort(workingtList);
        if (workingtList.size() == 0)
        {
            return null;
        }
        int sequence = buildSequence[currentBuildSequenceIndex];
        ElementDescriptor desr = workingtList.get(sequence % workingtList.size());
        return desr;
    }

    public boolean moveFirst()
    {
        currentBuildSequenceIndex = 0;
        return !this.isEof();
    }

    public boolean moveNext()
    {
        currentBuildSequenceIndex++;
        return !this.isEof();
    }

    public void removeCurrent()
    {
        int sequence = buildSequence[currentBuildSequenceIndex];
        ElementDescriptor desr = workingtList.remove(sequence % workingtList.size());
        desr.setCutted(true);
        ElementDescriptor desrRelated = desr.getRelatedDescriptor();
        if (desrRelated != null)
        {
            desrRelated.setCutted(true);
            workingtList.remove(desrRelated);
        }
    }

    public boolean isEof()
    {
        return currentBuildSequenceIndex >= buildSequence.length;
    }

    public long getElementsTotalArea()
    {
        return elementsTotalArea;
    }


    public void addConstrain(ElementConstraint elementConstraint)
    {
        ArrayList<ElementDescriptor> elementDescriptors = new ArrayList<ElementDescriptor>();
        int index = 0;
        while (index < workingtList.size())
        {
            ElementDescriptor desr = workingtList.get(index);
            if (!elementConstraint.suit(desr))
            {
                workingtList.remove(index);
                elementDescriptors.add(desr);
            }
            else
            {
                index++;
            }
        }
        elementConstraints.put(elementConstraint, elementDescriptors);
    }

    public void removeConstrain(ElementConstraint elementConstraint)
    {
        List<ElementDescriptor> elementDescriptors = elementConstraints.remove(elementConstraint);
        workingtList.addAll(elementDescriptors);
        Collections.sort(workingtList);
    }

}
