/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;


public class GuillotineCutter
{
    //private SegmentList segmentList;
    private Segment[] lastSegments;
    //private i[] lastSegments;
    private int depth;
    private int[] maxElementsInFirstRow;
    private int elementsInLastLevelCutted;
    private int sawWidth;
    private int level1SegmentCount;

    public GuillotineCutter(int depth)
    {
        this.setDepth(depth);
        this.sawWidth = 0;
    }

    public void setDepth(int depth)
    {
        lastSegments = new Segment[depth];
        this.depth = depth;
    }

    private void init()
    {
        for (int i = 0; i < lastSegments.length; i++)
        {
            lastSegments[i] = null;
        }
        elementsInLastLevelCutted = 0;
    }

    private int getMaxElements()
    {
        int max = maxElementsInFirstRow[Math.max(0, Math.min(level1SegmentCount, maxElementsInFirstRow.length) - 1)];
        return max;
    }

    private boolean canDoCut(Segment segment, Segment segmentToCut)
    {
        return segment.canAddSegment(segmentToCut);
    }

    private boolean cutSegment(Segment segmentToCut, int cutIntoLevel)
    {
        Segment lastSegment = lastSegments[cutIntoLevel];
        if ((lastSegment == null) ||
                (!canDoCut(lastSegment, segmentToCut)))
        {
            if (cutIntoLevel == 0) return false;
            Segment seg = new Segment();
            seg.setPadding(sawWidth);
            seg.addSegment(segmentToCut);
            lastSegments[cutIntoLevel] = seg;
            //pokus jsme vytvorily novy segment urovne 1
            if (cutIntoLevel == 1)
            {
                elementsInLastLevelCutted = 0;
                this.level1SegmentCount++;
            }
            return cutSegment(seg, cutIntoLevel - 1);
        }

        lastSegment.addSegment(segmentToCut);
        elementsInLastLevelCutted++;
        if (elementsInLastLevelCutted >= getMaxElements())
            if (lastSegments[1] != null) lastSegments[1].setFixedWidth(true);
        return true;
    }

    private void initSegment(Segment segment)
    {
        init();
        segment.clear();
        segment.setPadding(sawWidth);
        lastSegments[0] = segment;
    }

    public synchronized CutResult cutElements(Strips strips, Element[] elements, int[] indexes, boolean[] rotations, int[] maxItems)
    {
        int segmentIndex = 0;
        level1SegmentCount = 0;
        initSegment(strips.getSegment(segmentIndex));
        maxElementsInFirstRow = maxItems;

        boolean[] cutted = new boolean[elements.length];
        for (int i = 0; i < elements.length; i++) cutted[i] = false;

        long cuttedElementsArea = 0;
        int cuttedCount = 0;
        boolean success = true;
        int elementIndex = 0;
        while (elementIndex < indexes.length)
        {
            //boolean firstRow = (i < maxItems);
            //if(i == maxItems)segment.setFixedWidth(true);


            int index = indexes[elementIndex];
            Segment child = new Segment();
            child.setPadding(sawWidth);

            boolean rotate = rotations[index] && strips.isAllowRotation();
            child.setElement(elements[index], rotate);
            Segment parent = new Segment();
            parent.setPadding(sawWidth);
            parent.addSegment(child);
            if (cutSegment(parent, this.depth - 2))
            {
                cutted[index] = true;
                cuttedElementsArea += elements[index].getArea();
                cuttedCount++;
                elementIndex++;
            }
            else
            {
                //if(firstRow)success = false;
                if (segmentIndex < strips.getSegmentsCount() - 1)
                {
                    segmentIndex++;
                    initSegment(strips.getSegment(segmentIndex));
                }
                else
                {
                    success = false;
                    break;
                }
            }
        }

        int[] cuttedIndexes = new int[cuttedCount];
        int[] uncuttedIndexes = new int[elements.length - cuttedCount];
        int cuttedIndex = 0;
        int uncuttedIndex = 0;
        for (int i = 0; i < cutted.length; i++)
        {
            if (cutted[i]) cuttedIndexes[cuttedIndex++] = i;
            else uncuttedIndexes[uncuttedIndex++] = i;
        }

        CutResult result = new CutResult(success, cuttedIndexes, uncuttedIndexes, cuttedElementsArea, segmentIndex + 1);
        return result;
    }

    public int getSawWidth()
    {
        return sawWidth;
    }

    public void setSawWidth(int sawWidth)
    {
        this.sawWidth = sawWidth;
    }


}

