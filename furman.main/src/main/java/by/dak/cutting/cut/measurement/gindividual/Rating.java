/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.gindividual;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.Population;
import by.dak.cutting.cut.genetics.PopulationParams;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.*;
import by.dak.cutting.cut.measurement.utils.CsvWriter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peca
 */
public class Rating
{

    static long startTime = 0;
    static int measurement = 0;
    static long maxRunTime = Integer.MAX_VALUE;
    static long appStartTime;


    private static boolean timeOut()
    {
        return (System.currentTimeMillis() > appStartTime + maxRunTime);
    }

    private static String removePrefix(String str)
    {
        return str.substring(2);
    }

    private static long parseTime(String value)
    {
        Matcher matcher = Pattern.compile("(\\d+)").matcher(value);
        matcher.find();
        long el = Integer.parseInt(matcher.group()) * 1000;
        if (value.matches("\\d*m$"))
        {
            el *= 60;
        }
        else if (value.matches("\\d*h$"))
        {
            el *= 3600;
        }
        return el;
    }

    public static void main(String[] args) throws IOException
    {
        appStartTime = System.currentTimeMillis();

        PopulationParams params = new PopulationParams();
        String individualType = "G";
        String fileName = "";
        int maxGenerations = Integer.MAX_VALUE;
        long maxOneMeasureTime = Integer.MAX_VALUE;

        for (int i = 0; i < args.length / 2; i++)
        {
            String prefix = args[i * 2].toLowerCase();
            String value = args[i * 2 + 1].toLowerCase();
            if (prefix.equals("-runtime"))
            {
                maxRunTime = parseTime(value);
            }
            else if (prefix.equals("-measuretime"))
            {
                maxOneMeasureTime = parseTime(value);
            }
            else if (prefix.equals("-maxindividuals"))
            {
                params.setMaxIndividualsCount(Integer.parseInt(value));
            }
            else if (prefix.equals("-individualtype"))
            {
                individualType = value.toUpperCase();
            }
            else if (prefix.equals("-maxgenerations"))
            {
                maxGenerations = Integer.parseInt(value);
            }
            else if (prefix.equals("-filename"))
            {
                fileName = value;
            }
            else
            {
                System.out.println("Invalid argument " + prefix);
                return;
            }
        }

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        CutSettings cutSettings = CutSettings.loadFromFile(fileName);

        Element[] elements = cutSettings.getElements();
        Strips strips = cutSettings.getStrips();

        RatingFunction rFX;
        Individual ind;

        if (individualType.equals("G"))
        {
            rFX = new GRatingFunction(strips, elements);
            ind = new GIndividual(elements.length);
        }
        else if (individualType.equals("B"))
        {
            rFX = new BRatingFunction(strips, elements);
            ind = new BIndividual(elements.length);
        }
        else
        {
            System.out.println("Invalid individual type");
            return;
        }

        final Population population = new Population(rFX, ind);
        population.setPopulationParams(params);

        CutSettings.saveToFile(cutSettings, "CutSettings.xml");
        PopulationParams.saveToFile(params, "PopulationParams.xml");
        final CsvWriter writer = new CsvWriter("data.csv");
        writer.writeString("generation");
        writer.writeString("rating_function_invocations");
        writer.writeString("best_rating");
        writer.writeString("time");
        writer.writeString("measurement");
        writer.writeString(String.format("inds: %d\nels: %d\ntype: %s\nmaxgen: %d",
                params.getMaxIndividualsCount(),
                elements.length,
                individualType,
                maxGenerations));
        writer.newRow();


        population.setPopulationListener(new Population.IPopulationListener()
        {

            public void bestIndividualFound(Individual bestIndividual)
            {
                try
                {
                    writer.writeInteger(population.getGeneration());
                    writer.writeInteger(Statistics.getRatingFxInvocations());
                    writer.writeFloat(bestIndividual.getRating());
                    writer.writeLong(System.currentTimeMillis() - startTime);
                    writer.writeInteger(measurement);
                    writer.newRow();
                    writer.flush();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        while (System.in.available() > 0) System.in.read();
        while (System.in.available() == 0)
        {
            Statistics.clear();
            writer.writeInteger(0);
            writer.writeInteger(0);
            writer.writeFloat(0.0f);
            writer.writeLong(0);
            writer.writeInteger(0);
            writer.newRow();
            measurement++;
            startTime = System.currentTimeMillis();
            population.createFirstPopulation();
            for (int i = 0; i < maxGenerations; i++)
            {
                population.nextGeneration();

                System.out.println(String.format("%d - %d", measurement, i));
                if (timeOut()) break;
                if (System.in.available() > 0)
                {
                    break;
                }
                if (System.currentTimeMillis() > startTime + maxOneMeasureTime)
                {
                    break;
                }
            }
            if (timeOut()) break;
        }
        writer.close();
    }

}
