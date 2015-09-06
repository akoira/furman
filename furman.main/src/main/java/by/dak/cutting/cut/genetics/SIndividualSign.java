/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;

/**
 *
 * @author Peca
 */
public class SIndividualSign extends IndividualSign{
    private int hashCode;
    private float rating;
    
    public SIndividualSign(Strips strips, float rating){
        int hash = 13;
        for(Segment seg : strips.getSegments()){
            hash *= (computeHash(seg)+17);
        }
        this.hashCode = hash;
        this.rating = rating;
    }
    
    private int computeHash(Segment segment){
        int hash = 17;
        for(Segment seg : segment.getItems()){
            hash *= (computeHash(seg)+23);
        }
        hash *= (segment.getLength()+3) * 
                (segment.getWidth()+7) * 
                (segment.getFreeLength()+11) * 
                (segment.getItemsCount()+13) + 27;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof SIndividualSign))return false;
        SIndividualSign sign = (SIndividualSign)obj;
        if(sign.hashCode != this.hashCode)return false;
        if(!sameValue(sign.rating, this.rating))return false;
        return true;
    }

    private boolean sameValue(float value1, float value2){
        return Math.abs(value1 - value2) < 1e-6f;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public int compare(IndividualSign sign) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return String.valueOf(this.hashCode);
    }
    
}
