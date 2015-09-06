/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.common;

import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.measurement.utils.Watch;

/**
 * @author Peca
 */
public class Statistics
{
    public class ImprovementRecord
    {
        private float oldRating;
        private float newRating;
        private boolean mutation;

        public ImprovementRecord(float oldRating, float newRating, boolean mutation)
        {
            this.oldRating = oldRating;
            this.newRating = newRating;
            this.mutation = mutation;
        }


    }


    private static int ratingFxInvocations = 0;
    private static Watch ratingFxWatch = new Watch();
    private static IReproductionListener reproductionListener;

    public static int getRatingFxInvocations()
    {
        return ratingFxInvocations;
    }

    public static void clear()
    {
        ratingFxInvocations = 0;
        ratingFxWatch.reset();
    }

    public static void enterRatingFx()
    {
        ratingFxInvocations++;
        ratingFxWatch.start();
    }

    public static void exitRatingFx()
    {
        ratingFxWatch.stop();
    }

    public static float getRatingFxTime()
    {
        return ratingFxWatch.getTotalTimeInSeconds();
    }

    public static void crossing(Individual parent1, Individual parent2, Individual child1, Individual child2)
    {
        if (reproductionListener != null)
        {
            reproductionListener.crossing(parent1, parent2, child1, child2);
        }
    }

    public static void mutation(Individual parent, Individual child)
    {
        if (reproductionListener != null)
        {
            reproductionListener.mutation(parent, child);
        }
    }

    public static void setReproductionListener(IReproductionListener reproductionListener)
    {
        Statistics.reproductionListener = reproductionListener;
    }


}
