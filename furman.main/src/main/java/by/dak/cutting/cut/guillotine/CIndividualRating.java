/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

/**
 *
 * @author Peca
 */
public class CIndividualRating extends GIndividualRating{

    private int segmentsUsed = 0;
    public CIndividualRating(long cuttedArea, long usedArea, float progress, int segmentsUsed){
        super(cuttedArea, usedArea, progress);
        this.segmentsUsed = segmentsUsed;
    }

    public int getSegmentsUsed()
    {
        return segmentsUsed;
    }
}
