/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */
public class ReproductionParams {
    public enum SelectionType { Steady }

    private SelectionType selectionType = SelectionType.Steady;  //typ selekce jedincu pro reprodukci
    private float crossingProbability = 0.95f;//0.50f;  //pravdepodobnost zkrizeni
    private float mutationProbability = 0.95f;//0.50f;  //pravdepodobnost mutace

    public ReproductionParams(){
        
    }
    
    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public float getCrossingProbability() {
        return crossingProbability;
    }

    public void setCrossingProbability(float crossingProbability) {
        this.crossingProbability = crossingProbability;
    }

    public float getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(float mutationProbability) {
        this.mutationProbability = mutationProbability;
    }
    
}
