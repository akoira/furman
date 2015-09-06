/*
 * AbstractIndividual.java
 *
 * Created on 5. kv�ten 2007, 0:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */
public abstract class Individual {
    private Chromosome[] chromosomes;
    //protected float rating;
    private IndividualRating rating;
    private Individual parent1;
    private Individual parent2;
    private int bornTime;
    private Population population;
    /**
     * Doba nesmrtelnosti jedince, resp. absolutni cas do ktereho je jedinec nesmrtelny
     */
    private int immortalTime;
    private Object id;
    private IndividualSign sign;
    
    /** Creates a new instance of AbstractIndividual */
    public Individual() {
        this.parent1 = null;
        this.parent2 = null;
        this.bornTime = 0;
        this.immortalTime = 0;
        this.id = "0";
        this.initChromosomes();
    }
    
    protected void initChromosomes(){
        this.chromosomes = new Chromosome[this.getChromosomesCount()];
        for(int i=0; i<this.chromosomes.length; i++){
            this.chromosomes[i] = initChromosome(i);
        }
    };
        
    protected abstract Chromosome initChromosome(int index);
    
    protected abstract int getChromosomesCount();        
    
    private void setChromosomes(Chromosome[] chromosomes){
        this.chromosomes = chromosomes;
    }        
    
    private Chromosome[] cloneChromosomes(){
        Chromosome[] chromClone = new Chromosome[this.chromosomes.length];
        for(int i=0; i<this.chromosomes.length; i++){
            chromClone[i] = this.chromosomes[i].clone();
        }
        return chromClone;
    }
    
    @Override
    public Individual clone(){
        Individual newInd = this.createNewInstance();
        newInd.setBornTime(this.bornTime);
        newInd.setId(this.id);
        newInd.setRating(this.rating);
        newInd.setParent1(this.parent1);
       // newInd.setParent2(this.parent2);
        newInd.setChromosomes(this.cloneChromosomes());
        newInd.setPopulation(this.population);
        newInd.setSign(this.sign);
        return newInd;
    }
    
    protected abstract Individual createNewInstance();
    
    @Override
    public String toString(){
        return id.toString()+ " - "+String.valueOf(rating) ;
    }
    
    public void mutate(){
        for(int i=0; i<chromosomes.length; i++){
            chromosomes[i].mutate();
        }
    }
    
    public Individual crossWith(Individual ind){
        Individual ind2 = this.clone();
        for(int i=0; i<chromosomes.length; i++){
            ind2.chromosomes[i] = this.chromosomes[i].crossWith(ind.chromosomes[i]);
        }
        return ind2;
    }
    
    public void initRandom(){
        for(int i=0; i<chromosomes.length; i++){
            this.chromosomes[i].initRandom();
        }
    }
    
   

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }
    
    public Chromosome getChromosome(int index) {
        return chromosomes[index];
    }

    public float getRating() {
        if(this.rating != null) return this.rating.getRating();
        else return 0;
    }
    
    public IndividualRating getIndividualRating(){
        return this.rating;
    }

   /* public void setRating(float rating) {
       this.rating.setRating(rating);
    }*/
    
    public void setRating(IndividualRating rating) {
       this.rating = rating; 
    }

    public Individual getParent1() {
        return parent1;
    }

    public void setParent1(Individual parent1) {
        this.parent1 = parent1;
    }

    public Individual getParent2() {
        return parent2;
    }

    public void setParent2(Individual parent2) {
        this.parent2 = parent2;
    }

    public int getBornTime() {
        return bornTime;
    }

    public void setBornTime(int bornTime) {
        this.bornTime = bornTime;
    }

    public int getImmortalTime() {
        return immortalTime;
    }

    public void setImmortalTime(int immortalTime) {
        this.immortalTime = immortalTime;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
    
    public IndividualSign getSign(){
        return this.sign;
    }

    public void setSign(IndividualSign sign) {
        this.sign = sign;
    }

    
    
    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
    
    
}
