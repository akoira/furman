/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.Migrator;

/**
 *
 * @author Peca
 */
public class CMigrator extends Migrator {
  
   
    public CMigrator(){
    }
    
    public Individual Migrate(Individual individual){
        CIndividual ind = (CIndividual)individual;

        CIndividual ind2 = new CIndividual(ind.getUncuttedElements().length);
        ind2.initRandom();
        ind2.setId(ind.getId());
        ind2.setRatioConstraint(ind.getRatioConstraint());
        ind2.setSister(ind);
        assert(ind2.getBuildSequenceChromosome().getElements().length == ind.getUncuttedElements().length);
        return ind2;
    }
}

