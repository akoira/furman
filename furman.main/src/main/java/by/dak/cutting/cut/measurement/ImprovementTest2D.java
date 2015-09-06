/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement;

import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.IReproductionListener;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.cuttingApp.*;
import by.dak.cutting.cut.guillotine.BRatingFunction;
import by.dak.cutting.cut.guillotine.CRatingFunction;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.measurement.utils.ArgumentParser;
import by.dak.cutting.cut.measurement.utils.GoldenSectionProvider;
import by.dak.cutting.cut.measurement.utils.TextWriter;
import by.dak.cutting.cut.measurement.utils.Watch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Peca
 */
public class ImprovementTest2D
{
    private static void writeData(String fileName, ImprovementRecord[][] data) throws FileNotFoundException, IOException
    {

        TextWriter writer = new TextWriter(fileName);

        for (int rowIndex = 0; rowIndex < data.length; rowIndex++)
        {
            for (int columnIndex = 0; columnIndex < data[rowIndex].length; columnIndex++)
            {
                ImprovementRecord record = data[rowIndex][columnIndex];
                float betterRatio;
                if (record != null)
                {
                    betterRatio = record.betterCount / (float) record.getTotalCount();
                }
                else
                {
                    betterRatio = 0.0f;
                }
                writer.writeFloat(betterRatio);
            }
            writer.newRow();
        }
        writer.close();
    }

    private static class ImprovementRecord
    {
        float rating;
        int worseCount = 0;
        int sameCount = 0;
        int betterCount = 0;

        public ImprovementRecord(float rating)
        {
            this.rating = rating;
        }

        public void addRecord(float oldRating, float newRating)
        {
            if (Math.abs(oldRating - newRating) < 1e-6)
            {
                sameCount++;
            }
            else if (newRating > oldRating)
            {
                betterCount++;
            }
            else
            {
                worseCount++;
            }
        }

        public int getTotalCount()
        {
            return worseCount + sameCount + betterCount;
        }

    }

    static RatingFunction ratingFunction = null;

    private static void addRecord(ImprovementRecord[][] data, float yValue, float oldRating, float newRating)
    {
        int rowIndex = (int) ((data.length - 1) * yValue);
        int columnIndex = (int) ((data[rowIndex].length - 1) * oldRating);

        if (data[rowIndex][columnIndex] == null)
        {
            data[rowIndex][columnIndex] = new ImprovementRecord(oldRating);
        }

        ImprovementRecord ir = data[rowIndex][columnIndex];
        ir.addRecord(oldRating, newRating);
    }

    private static void addMutationRecord(ImprovementRecord[][] data, float yValue, float oldRating, float newRating)
    {
        addRecord(data, yValue, oldRating, newRating);
    }

    private static void addCrossingRecord(ImprovementRecord[][] data, float yValue, float oldRating1, float oldRating2, float newRating1, float newRating2)
    {
        addRecord(data, yValue, Math.max(oldRating1, oldRating2), Math.max(newRating1, newRating2));
    }

    private static float normalizeRating(float rating, float bestRating, float worstRating)
    {
        float k = 1 / (bestRating - worstRating);
        float q = -k * worstRating;
        return k * rating + q;
    }

    private static ImprovementRecord[][] measuredData = new ImprovementRecord[100][100];
    private static float currentMutationProp;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ArgumentParser argumentParser = new ArgumentParser(args);
        String individualType = argumentParser.getStringValue("-type", "g").toLowerCase();
        String fileName = argumentParser.getStringValue("-file", "RandomBoard100.tocut");
        int oneMeasureTime = argumentParser.getIntValue("-time", 10);
        System.out.println(individualType);
        System.out.println(fileName);
        Sawyer sawyer;

        Type ratingFunctionType = null;
        if (individualType.equals("g"))
        {
            sawyer = new GSawyer();
            ((GSawyer) sawyer).getPopulationParams().setMaxIndividualsCount(100);
            ratingFunctionType = GRatingFunction.class;
        }
        else if (individualType.equals("b"))
        {
            sawyer = new BSawyer();
            ((BSawyer) sawyer).getPopulationParams().setMaxIndividualsCount(100);
            ratingFunctionType = BRatingFunction.class;
        }
        else if (individualType.equals("c"))
        {
            sawyer = new CSawyer();
            ((CSawyer) sawyer).getWorldParams().getPopulationParams().setMaxIndividualsCount(100);
            ratingFunctionType = CRatingFunction.class;
        }
        else
        {
            System.out.println("Invalid individual type");
            return;
        }


        CutSettings cutSettings = CutSettings.loadFromFile(fileName);
        cutSettings.setAllowRotation(true);
        cutSettings.setSawWidth(0);
        sawyer.setCutSettings(cutSettings);

        final HashMap<Integer, ImprovementRecord> mutationMap = new HashMap<Integer, ImprovementTest2D.ImprovementRecord>();
        final HashMap<Integer, ImprovementRecord> crossingMap = new HashMap<Integer, ImprovementTest2D.ImprovementRecord>();


        Statistics.setReproductionListener(new IReproductionListener()
        {

            public void crossing(Individual parent1, Individual parent2, Individual child1, Individual child2)
            {
                addCrossingRecord(measuredData,
                        currentMutationProp,
                        parent1.getRating(),
                        parent2.getRating(),
                        ratingFunction.rate(child1).getRating(),
                        ratingFunction.rate(child2).getRating());
            }

            public void mutation(Individual parent, Individual child)
            {
                /*addMutationRecord(mutationMap,
                        parent.getRating(), 
                        ratingFunction.rate(child).getRating());*/
            }
        });


        TextWriter mutationVector = new TextWriter("mutationVector.txt", true);

        ratingFunction = RatingFunctionFactory.createInstance(cutSettings.getStrips(), cutSettings.getElements(), ratingFunctionType);
        GoldenSectionProvider gsp = new GoldenSectionProvider();
        Watch watch = new Watch();
        while (!Utils.shouldStop())
        {
            currentMutationProp = (float) gsp.nextValue();
            mutationVector.writeFloat(currentMutationProp);
            ((IPopulationSawyer) sawyer).getPopulationParams().getReproductionParams().setMutationProbability(currentMutationProp);
            ((IPopulationSawyer) sawyer).getPopulationParams().getReproductionParams().setCrossingProbability(1.0f - currentMutationProp);

            watch.reset();
            watch.start();
            sawyer.start();
            while (sawyer.getState() != Sawyer.States.STOPPED)
            {
                if (watch.getDurationInSeconds() > oneMeasureTime || Utils.shouldStop())
                {
                    sawyer.stop();
                }
                Thread.sleep(10);
            }
            writeData("dataCrossing.txt", measuredData);
        }
        writeData("dataCrossing.txt", measuredData);
    }

}
