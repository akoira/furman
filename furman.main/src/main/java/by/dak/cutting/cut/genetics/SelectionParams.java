/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 *
 * @author Peca
 */
public class SelectionParams {
    private float minimumRatioToSurvive = 0.80f;//0.50f;   //vsichni jedinci pod toto hodnoceni budou zabiti-Все лица, находящиеся под этой оценки будут убиты
    private float minimumThatWillSurvive = 0.10f;//0.30f;  //minimalni pocet jedincu [%] kteri preziji nezavisle jake maji hodnoceni - минимальное число лиц, [%], чтобы выжить самостоятельно, как они оценили
    private float elitismRatio = 0.8f;//0.9f;             //vsichni jedinci s timto a vetsim hodnocenim budou elitni - всех людей в этом и большая оценка будет элитный
    private float maximumEliteIndividuals = 0.10f;  //maximalni pocet [%] jedincu co mohou byt elitni - Максимальное количество [%] от физических лиц могут быть как элита
    private int maximumAge = Integer.MAX_VALUE; //maximalni doba [generace] kterou mohou jedinci zit
    private boolean shouldNormalize = true;  //jestli se ma normalizovat ohodnoceni
    private boolean killTwins = true;        //jestli se maji zabijet stejne vypadajici jedinci - Следует ли убивать же вида лиц
    
    public SelectionParams(){
        
    }
    
    public int getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(int maximumAge) {
        this.maximumAge = maximumAge;
    }

    public float getMinimumRatioToSurvive() {
        return minimumRatioToSurvive;
    }

    public void setMinimumRatioToSurvive(float minimumRatioToSurvive) {
        this.minimumRatioToSurvive = minimumRatioToSurvive;
    }

    public boolean isShouldNormalize() {
        return shouldNormalize;
    }

    public void setShouldNormalize(boolean shouldNormalize) {
        this.shouldNormalize = shouldNormalize;
    }

    public float getElitismRatio() {
        return elitismRatio;
    }

    public void setElitismRatio(float elitismRatio) {
        this.elitismRatio = elitismRatio;
    }

    public boolean isKillTwins() {
        return killTwins;
    }

    public void setKillTwins(boolean killTwins) {
        this.killTwins = killTwins;
    }

    public float getMaximumEliteIndividuals() {
        return maximumEliteIndividuals;
    }

    public void setMaximumEliteIndividuals(float maximumEliteIndividuals) {
        this.maximumEliteIndividuals = maximumEliteIndividuals;
    }

    public float getMinimumThatWillSurvive() {
        return minimumThatWillSurvive;
    }

    public void setMinimumThatWillSurvive(float minimumThatWillSurvive) {
        this.minimumThatWillSurvive = minimumThatWillSurvive;
    }
 
    
    
}
