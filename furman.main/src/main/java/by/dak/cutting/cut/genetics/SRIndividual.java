/*
 * Individual.java
 *
 * Created on 27. ��jen 2006, 21:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */
public class SRIndividual extends Individual{
    private int elementsCount;
    private int depth;
    
   
    /** Creates a new instance of Individual */
    public SRIndividual(int elementsCount){
        super();
        this.depth = 4;        
        this.elementsCount = elementsCount;
        //initChromosomes() se vola automaticky, ale to jsme potlacili, tak to ted zavolame rucne
        //je to kvuli nastaveni elementsCount
        super.initChromosomes();
    }

   @Override
    protected Chromosome initChromosome(int index) {
        switch(index){
            case 0: return new SChromosome(elementsCount);
            case 1: return new RChromosome(elementsCount);
        }
        assert(false); //k tomu by nemelo nikdy dojit
        return null;
    }

    @Override
    protected int getChromosomesCount() {
        return 2;
    }

    @Override
    protected void initChromosomes() {
        //toto potrebujeme potlacit, volame to rucne na konci konstruktoru
        //super.initChromosomes();
    }

    
    
    @Override
    public Individual clone() {
         SRIndividual ind = (SRIndividual)super.clone();
         ind.setDepth(this.depth);
         return ind;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public SChromosome getSChromosome(){
        return (SChromosome)this.getChromosome(0);
    }
   
    public RChromosome getRChromosome(){
        return (RChromosome)this.getChromosome(1);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    protected Individual createNewInstance() {
        return new SRIndividual(this.elementsCount);
    }

 
    
    
}
