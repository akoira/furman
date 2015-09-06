/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.RatingFunction;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.*;
import by.dak.cutting.cut.measurement.utils.ArgumentParser;
import by.dak.cutting.cut.measurement.utils.CsvWriter;
import by.dak.cutting.cut.measurement.utils.Watch;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peca
 */
public class RandomSearch
{

    static long startTime = 0;
    static int measurement = 0;
    static long appStartTime;


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

        ArgumentParser ap = new ArgumentParser(args);

        String individualType = ap.getStringValue("-type", "G").toUpperCase();
        String fileName = ap.getStringValue("-file", "");
        float maxOneMeasureTime = ap.getTimeValue("-time", 60);

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

        final CsvWriter writer = new CsvWriter("data.csv");
        writer.writeString("rating_function_invocations");
        writer.writeString("best_rating");
        writer.writeString("time");
        writer.writeString("measurement");
        writer.newRow();


        while (System.in.available() > 0) System.in.read();
        Watch watch = new Watch();
        while (System.in.available() == 0)
        {
            Statistics.clear();
            measurement++;
            float bestRating = 0.0f;
            watch.reset();
            watch.start();
            while (true)
            {
                ind.initRandom();
                float rating = rFX.rate(ind).getRating();
                if (rating > bestRating)
                {
                    bestRating = rating;
                    writer.writeInteger(Statistics.getRatingFxInvocations());
                    writer.writeFloat(bestRating);
                    writer.writeFloat(watch.getDurationInSeconds());
                    writer.writeInteger(measurement);
                    writer.newRow();
                    writer.flush();
                    System.out.println(String.format("%d - %1.3f", measurement, bestRating));
                }

                if (Utils.shouldStop())
                {
                    break;
                }
                if (watch.getDurationInSeconds() > maxOneMeasureTime)
                {
                    break;
                }
            }
            if (Utils.shouldStop())
            {
                break;
            }
        }
        writer.close();
    }

}
