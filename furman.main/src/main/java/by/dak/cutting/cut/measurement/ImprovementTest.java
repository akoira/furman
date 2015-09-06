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
import by.dak.cutting.cut.gui.cuttingApp.BSawyer;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.GSawyer;
import by.dak.cutting.cut.gui.cuttingApp.Sawyer;
import by.dak.cutting.cut.guillotine.BRatingFunction;
import by.dak.cutting.cut.guillotine.CRatingFunction;
import by.dak.cutting.cut.guillotine.GRatingFunction;
import by.dak.cutting.cut.measurement.utils.ArgumentParser;
import by.dak.cutting.cut.measurement.utils.CsvWriter;
import by.dak.cutting.cut.measurement.utils.Watch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Peca
 */
public class ImprovementTest
{
    private static void writeData(String fileName, HashMap<Integer, ImprovementRecord> hashMap) throws FileNotFoundException, IOException
    {

        CsvWriter writer = new CsvWriter(fileName);
        writer.writeString("rating");
        writer.writeString("rating_normalized");
        writer.writeString("worse");
        writer.writeString("same");
        writer.writeString("better");
        writer.newRow();

        float bestRating = Float.MIN_VALUE;
        float worstRating = Float.MAX_VALUE;
        for (ImprovementRecord record : hashMap.values())
        {
            bestRating = Math.max(bestRating, record.rating);
            worstRating = Math.min(worstRating, record.rating);
        }

        for (ImprovementRecord record : hashMap.values())
        {
            writer.writeFloat(record.rating);
            writer.writeFloat(normalizeRating(record.rating, bestRating, worstRating));
            writer.writeInteger(record.worseCount);
            writer.writeInteger(record.sameCount);
            writer.writeInteger(record.betterCount);
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

    }

    static RatingFunction ratingFunction = null;

    private static void addRecord(HashMap<Integer, ImprovementRecord> hashMap, float oldRating, float newRating)
    {
        Integer intRating = (int) (oldRating * 1000);
        if (hashMap.containsKey(intRating))
        {
            hashMap.get(intRating).addRecord(oldRating, newRating);
        }
        else
        {
            ImprovementRecord ir = new ImprovementRecord(oldRating);
            ir.addRecord(oldRating, newRating);
            hashMap.put(intRating, ir);
        }
    }

    private static void addMutationRecord(HashMap<Integer, ImprovementRecord> hashMap, float oldRating, float newRating)
    {
        addRecord(hashMap, oldRating, newRating);
    }

    public static void addCrossingRecord(HashMap<Integer, ImprovementRecord> hashMap, float oldRating1, float oldRating2, float newRating1, float newRating2)
    {
        addRecord(hashMap, Math.max(oldRating1, oldRating2), Math.max(newRating1, newRating2));
    }

    private static float normalizeRating(float rating, float bestRating, float worstRating)
    {
        float k = 1 / (bestRating - worstRating);
        float q = -k * worstRating;
        return k * rating + q;
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ArgumentParser argumentParser = new ArgumentParser(args);
        String individualType = argumentParser.getStringValue("-type", "g").toLowerCase();
        String fileName = argumentParser.getStringValue("-file", "RandomBoard100.tocut");
        int oneMeasureTime = argumentParser.getIntValue("-time", 1);
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

        final HashMap<Integer, ImprovementRecord> mutationMap = new HashMap<Integer, ImprovementTest.ImprovementRecord>();
        final HashMap<Integer, ImprovementRecord> crossingMap = new HashMap<Integer, ImprovementTest.ImprovementRecord>();


        Statistics.setReproductionListener(new IReproductionListener()
        {

            public void crossing(Individual parent1, Individual parent2, Individual child1, Individual child2)
            {
                addCrossingRecord(crossingMap,
                        parent1.getRating(),
                        parent2.getRating(),
                        ratingFunction.rate(child1).getRating(),
                        ratingFunction.rate(child2).getRating());
            }

            public void mutation(Individual parent, Individual child)
            {
                addMutationRecord(mutationMap,
                        parent.getRating(),
                        ratingFunction.rate(child).getRating());
            }
        });

        ratingFunction = RatingFunctionFactory.createInstance(cutSettings.getStrips(), cutSettings.getElements(), ratingFunctionType);
        Watch watch = new Watch();
        while (!Utils.shouldStop())
        {
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
        }
        writeData("dataMutation.csv", mutationMap);
        writeData("dataCrossing.csv", crossingMap);
    }

}
