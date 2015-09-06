/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;

/**
 * @author Peca
 */
public class CutResult
{

    private int[] cuttedIndexes;
    private int[] uncuttedIndexes;
    private long cuttedElementsArea;
    private boolean success;
    private int segmentsUsed;
    private Element[] uncuttedElements;
    private long totalUsedArea;

    public CutResult(boolean success, int[] cuttedIndexes, int[] uncuttedIndexes, long cuttedElementsArea, int segmentsUsed)
    {
        this.success = success;
        this.cuttedIndexes = cuttedIndexes;
        this.uncuttedIndexes = uncuttedIndexes;
        this.cuttedElementsArea = cuttedElementsArea;
        this.segmentsUsed = segmentsUsed;
    }

    public CutResult(boolean success, Element[] uncuttedElements, long cuttedElementsArea, long totalUsedArea, int segmentsUsed)
    {
        this.success = success;
        this.uncuttedElements = uncuttedElements;
        this.cuttedElementsArea = cuttedElementsArea;
        this.totalUsedArea = totalUsedArea;
        this.segmentsUsed = segmentsUsed;
    }

    public int[] getCuttedIndexes()
    {
        return cuttedIndexes;
    }

    public int[] getUncuttedIndexes()
    {
        return uncuttedIndexes;
    }

    public long getCuttedElementsArea()
    {
        return cuttedElementsArea;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public int getSegmentsUsed()
    {
        return segmentsUsed;
    }

    public Element[] getUncuttedElements()
    {
        return uncuttedElements;
    }

    public long getTotalUsedArea()
    {
        return totalUsedArea;
    }
} 
    
