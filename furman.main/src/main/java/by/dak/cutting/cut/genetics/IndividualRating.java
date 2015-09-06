/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */
public class IndividualRating {
    private float rating;

    public IndividualRating(){
        
    }
    
    public IndividualRating(float rating){
        this.rating = rating;
    }
    
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.getRating());
    }
    
    protected int compare(IndividualRating rating){
        return Float.compare(this.getRating(), rating.getRating());
    }
    
    public static int compare(IndividualRating rating1, IndividualRating rating2){
        //if(rating1 == null)return 0;
        //if(rating2 == null)return 0;
        return rating1.compare(rating2) ;
    }

}
