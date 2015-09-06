/*
 * Pokus.java
 *
 * Created on 23. listopad 2006, 18:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.GIndividual;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.measurement.utils.Watch;

/**
 * @author Peca
 */
public class Pokus
{


    public static void main(String args[])
    {
        Element[] elements = Common.createRandomeElements(1, 10, 10, 150, 1);
        Strips strips = new Strips(new DimensionItem[]{new DimensionItem(10000000, 200, "test")});
        Individual individual = new GIndividual(elements.length);
        RatingFunction rFx = new GRatingFunction(strips, elements);
        Watch watch = new Watch();
        Statistics.clear();
        watch.start();
        for (int i = 0; i < 1000; i++)
        {
            individual.initRandom();
            rFx.rate(individual);
        }
        watch.stop();
        float res1 = watch.getDurationInSeconds() / Statistics.getRatingFxInvocations();
        System.out.println(watch.getDurationInSeconds() + " s");
        System.out.println(res1);

        elements = Common.createRandomeElements(1000, 10, 10, 150, 1);
        strips = new Strips(new DimensionItem[]{new DimensionItem(10000000, 200, "test")});
        individual = new GIndividual(elements.length);
        rFx = new GRatingFunction(strips, elements);

        Statistics.clear();
        watch.start();
        for (int i = 0; i < 1000; i++)
        {
            individual.initRandom();
            rFx.rate(individual);
        }
        watch.stop();
        float res2 = watch.getDurationInSeconds() / Statistics.getRatingFxInvocations();
        System.out.println(res2);
        System.out.println("------");
        System.out.println(res2 / res1);
    }


}
