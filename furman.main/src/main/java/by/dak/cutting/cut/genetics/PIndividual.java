/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

/**
 * @author Peca
 */
public class PIndividual extends Individual
{

    private static final int MAX_VALUE = 1000;

    @Override
    protected Chromosome initChromosome(int index)
    {
        assert index == 0;
        NChromosome nchrom = new NChromosome(10, 0, MAX_VALUE);
        nchrom.setUseGauss(false);
        return nchrom;
    }

    @Override
    protected int getChromosomesCount()
    {
        return 1;
    }

    @Override
    protected Individual createNewInstance()
    {
        return new PIndividual();
    }

    private float valueToPropability(int value)
    {
        return value / (float) MAX_VALUE;
    }

    private boolean valueToBoolean(int value)
    {
        return (value % 2) == 0;
    }

    @Override
    public void initRandom()
    {
        super.initRandom();
    }


    public PopulationParams getPopulationParams()
    {
        NChromosome nchrom = (NChromosome) this.getChromosome(0);
        int[] els = nchrom.getElements();

        PopulationParams pp = new PopulationParams();
        ReproductionParams rp = new ReproductionParams();
        SelectionParams sp = new SelectionParams();
        pp.setReproductionParams(rp);
        pp.setSelectionParams(sp);

        rp.setCrossingProbability(valueToPropability(els[0]));
        rp.setMutationProbability(valueToPropability(els[1]));

        sp.setElitismRatio(valueToPropability(els[2]));
        sp.setKillTwins(valueToBoolean(els[3]));
        sp.setMaximumAge(els[4] + 1);
        sp.setMaximumEliteIndividuals(valueToPropability(els[5]));
        sp.setMinimumRatioToSurvive(valueToPropability(els[6]));
        sp.setMinimumThatWillSurvive(valueToPropability(els[7]));
        sp.setShouldNormalize(valueToBoolean(els[8]));

        pp.setMaxIndividualsCount(els[9] + 1);

        return pp;
    }
}
