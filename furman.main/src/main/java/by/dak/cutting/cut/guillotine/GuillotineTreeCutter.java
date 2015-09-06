/*
 * GuillotineCutter.java
 *
 * Created on 2. kv�ten 2007, 14:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Peca
 */
public class GuillotineTreeCutter
{


    /**
     * Creates a new instance of GuillotineCutter
     */
    public GuillotineTreeCutter()
    {
    }

    /**
     * public boolean cut(Segment segment, Element element, Integer[] maxSizes){
     * boolean cutted = false;
     * if(segment.getLevel() < 2){
     * for(Segment seg : segment.getItems()){
     * if(cut(seg, element, maxSizes)){
     * cutted = true;
     * break;
     * }
     * }
     * if(!cutted){
     * int lev = segment.getLevel()+1;
     * int s = Integer.MAX_VALUE;
     * if(lev == 1){
     * int i = segment.items.size();
     * if(i < maxSizes.length) s = maxSizes[i];
     * }
     * Segment seg = new Segment(0, 0, s, Integer.MAX_VALUE);
     * if(segment.cut(seg)){
     * if(cut(seg, element, maxSizes)) cutted = true;
     * else segment.removeSegment(seg);
     * }
     * }
     * }
     * else{
     * Segment seg;
     * if(segment.isFixedHeight()) seg = new Segment(element.getHeight(), element.getWidth());
     * else seg = new Segment(element.getWidth(), element.getHeight());
     * if(segment.cut(seg)){
     * seg.setElement(element);
     * cutted = true;
     * }
     * }
     * return cutted;
     * }
     */
    private boolean tryCut(Segment parent, Segment child, int maxItems)
    {

        if (parent.getItemsCount() >= maxItems) return false;
        if (parent.canAddSegment(child))
        {
            parent.addSegment(child);
            return true;
        }
        return false;
    }

    private boolean cut(Segment parent, Segment child, int level, int maxItems)
    {
        if (parent.getLevel() == level)
        {
            return tryCut(parent, child, maxItems);
        }
        for (Segment seg : parent.getItems())
        {
            if (cut(seg, child, level, maxItems)) return true;
        }
        //nenalezeno

        Segment seg = new Segment();
        seg.addSegment(child);
        return cut(parent, seg, --level, maxItems);
    }

    public boolean cut(Segment segment, Element element, int maxLevel, int maxItems, boolean rotate)
    {
        Segment child = new Segment();
        child.setElement(element, rotate);
        return cut(segment, child, maxLevel, maxItems);
    }

    public boolean cut(Segment segment, SegmentNode node, boolean[] rotationIndexes)
    {
        for (SegmentNode child : node.getNodes())
        {
            Segment seg = new Segment();
            if (!cut(seg, child, rotationIndexes))
            {
                return false;
            }
            if (!tryCut(segment, seg, Integer.MAX_VALUE))
            {
                return false;
            }
        }
        if (node.getElement() != null)
        {
            segment.setElement(node.getElement(), false);
        }
        return true;
    }

    private boolean cut2(Segment segment, SegmentNode node, ArrayList<Element> uncuttedElements, boolean[] rotationIndexes)
    {
        for (SegmentNode child : node.getNodes())
        {
            Segment seg = new Segment();
            if (!cut2(seg, child, uncuttedElements, rotationIndexes))
            {
                return false;
            }
            if (!tryCut(segment, seg, Integer.MAX_VALUE))
            {
                uncuttedElements.addAll(Arrays.asList(seg.getElements()));
                //return !false;
            }
        }
        if (node.getElement() != null)
        {
            segment.setElement(node.getElement(), false);
        }
        return true;
    }

    public boolean cut2(Segment segment, SegmentNode node, int maxLevel, boolean[] rotationIndexes)
    {
        ArrayList<Element> uncuttedElements = new ArrayList<Element>();
        boolean success = cut2(segment, node, uncuttedElements, rotationIndexes);
        int cnt = Math.max(uncuttedElements.size() / 4, 1);
        for (Element el : uncuttedElements)
        {
            if (!cut(segment, el, maxLevel, cnt, false))
                return false;
        }
        //aktualizujeme strom
        node.createFromSegment(segment);
        return success;
    }

    /**
     * public CutResult cutStrips(Strips strips, Element[] elements, int[] indexes, boolean[] rotations, int maxItems){
     * ArrayList<Element> cutted = new ArrayList<Element>();
     * long cuttedElementsArea = 0;
     * Segment[] segments = strips.getSegments();
     * Segment segment = segments[0];
     * segment.setFixedWidth(false);
     * int[] uncutted = (int[])indexes.clone();
     * int uncuttedCount = uncutted.length;
     * for(int i = 0; i < indexes.length; i++){
     * boolean firstRow = (i < maxItems);
     * if(i == maxItems)segment.setFixedWidth(true);
     * int index = indexes[i];
     * Segment child = new Segment();
     * child.setElement(elements[index], rotations[index]);
     * if(cut(segment, child, 2, Integer.MAX_VALUE)){
     * uncutted[i] = -1;
     * cutted.add(elements[index]);
     * cuttedElementsArea += elements[index].getArea();
     * uncuttedCount--;
     * }else{
     * if(firstRow){
     * break;
     * }
     * else  break;
     * }
     * }
     * <p/>
     * Element[] els = new Element[uncuttedCount];
     * int i2=0;
     * for(int i : uncutted){
     * if(i != -1)els[i2++] = elements[i];
     * }
     * CutResult result = new CutResult(true, cutted.toArray(new Element[cutted.size()]), els, cuttedElementsArea);
     * return result;
     * }
     */
    public CutResult cutSegment(Segment segment, Element[] elements, int[] indexes, boolean[] rotations, int maxItems, int maxLevel)
    {
        HashSet<Integer> cuttedIndexesTable = new HashSet<Integer>(indexes.length);
        //for(int i=0; i < indexes.length; i++) cuttedIndexesTable.put(indexes[i], false);

        long cuttedElementsArea = 0;
        segment.setFixedWidth(false);

        int cuttedCount = 0;
        boolean success = true;
        for (int i = 0; i < indexes.length; i++)
        {
            boolean firstRow = (i < maxItems);
            if (i == maxItems) segment.setFixedWidth(true);
            int index = indexes[i];
            Segment child = new Segment();
            child.setElement(elements[index], rotations[index]);
            if (cut(segment, child, maxLevel, Integer.MAX_VALUE))
            {
                cuttedIndexesTable.add(index);
                cuttedElementsArea += elements[index].getArea();
                cuttedCount++;
            }
            else
            {
                if (firstRow) success = false;
                break;
            }
        }

        assert (cuttedIndexesTable.size() == cuttedCount);
        int[] cuttedIndexes = new int[cuttedCount];
        int[] uncuttedIndexes = new int[indexes.length - cuttedCount];
        int cuttedIndex = 0;
        int uncuttedIndex = 0;
        for (int i = 0; i < indexes.length; i++)
        {
            if (cuttedIndexesTable.contains(indexes[i])) cuttedIndexes[cuttedIndex++] = indexes[i];
            else uncuttedIndexes[uncuttedIndex++] = indexes[i];
        }

        CutResult result = new CutResult(success, cuttedIndexes, uncuttedIndexes, cuttedElementsArea, 1);

        return result;
    }

    
   /* public boolean cutRandom(Segment segment, Element[] elements, int maxLevel){
        by.dak.cutting.cut.genetics.SChromosome sch = new by.dak.cutting.cut.genetics.SChromosome(elements.length);
        sch.initRandom();
        int[] indexes = sch.getElements();
        segment.clear();
        boolean success = true;
        for(int i=0; i<indexes.length; i++){
            if(!by.dak.cutting.cut(segment, elements[indexes[i]], maxLevel, Common.nextInt(4)+1)){
                success = false;
                break;
            }
        }
        return success;
    }*/
}
