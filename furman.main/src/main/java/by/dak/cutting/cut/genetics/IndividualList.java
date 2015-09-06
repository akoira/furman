/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Peca
 */
public class IndividualList extends ArrayList<Individual>{

    @Override
    public Individual[] toArray(){
        return this.toArray(new Individual[this.size()]);
    }
    
    public void sort(Comparator comparator){
        Collections.sort(this, comparator); 
    }
}
