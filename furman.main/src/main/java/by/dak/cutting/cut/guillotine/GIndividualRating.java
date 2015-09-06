/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;
import by.dak.cutting.cut.genetics.IndividualRating;

/**
 *
 * @author Peca
 */
public class GIndividualRating extends IndividualRating{
    private long cuttedArea;
    private long usedArea;
    private float progress;

    public long getTotalArea() {
        return usedArea;
    }

    public void setTotalArea(long totalArea) {
        this.usedArea = totalArea;
    }

    public long getUsedArea() {
        return cuttedArea;
    }

    public void setUsedArea(long usedArea) {
        this.cuttedArea = usedArea;
    }

    public GIndividualRating(long cuttedArea, long usedArea, float progress){
        this.cuttedArea = cuttedArea;
        this.usedArea = usedArea;
        this.progress = progress;
    }
    
    @Override
    public float getRating() {
        return this.cuttedArea / (float)usedArea;
    }

    @Override
    public void setRating(float rating) {
        super.setRating(rating);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
    
    @Override
    public String toString(){
        return String.format("%1.4f (%d)%%", this.getRating(), (int)(progress*100));
    }

    @Override
    protected int compare(IndividualRating rating){
        if(!(rating instanceof GIndividualRating))return super.compare(rating);

        GIndividualRating ar = (GIndividualRating)rating;
        //Сначало проверяем как много остатков а потом райтинг
        int result = Float.compare(this.progress, ar.progress);
        if (result == 0)
        {
            result = super.compare(rating);
        }
        return result;
    }
}
