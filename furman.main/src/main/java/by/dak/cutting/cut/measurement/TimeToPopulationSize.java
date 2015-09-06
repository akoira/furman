/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.common.Statistics;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.cuttingApp.*;
import by.dak.cutting.cut.measurement.utils.ArgumentParser;
import by.dak.cutting.cut.measurement.utils.CsvWriter;
import by.dak.cutting.cut.measurement.utils.GoldenSectionProvider;
import by.dak.cutting.cut.measurement.utils.Watch;

import java.io.IOException;

/**
 * @author Peca
 */
public class TimeToPopulationSize
{

    private static DimensionItem[] createRandomElements(int count)
    {
        Element[] els = Common.createRandomeElements(count, 10, 10, 150, 1);
        DimensionItem[] dims = new DimensionItem[els.length];
        for (int i = 0; i < els.length; i++)
        {
            DimensionItem di = new DimensionItem(els[i], "0", 1);
            dims[i] = di;
        }
        return dims;
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ArgumentParser argumentParser = new ArgumentParser(args);
        String individualType = argumentParser.getStringValue("-type", "g").toLowerCase();
        System.out.println(individualType);
        Sawyer sawyer;
        if (individualType.equals("g"))
        {
            sawyer = new GSawyer();
            ((GSawyer) sawyer).getPopulationParams().setMaxIndividualsCount(1);
        }
        else if (individualType.equals("b"))
        {
            sawyer = new BSawyer();
            ((BSawyer) sawyer).getPopulationParams().setMaxIndividualsCount(1);
        }
        else if (individualType.equals("c"))
        {
            sawyer = new CSawyer();
            ((CSawyer) sawyer).getWorldParams().getPopulationParams().setMaxIndividualsCount(1);
        }
        else
        {
            System.out.println("Invalid individual type");
            return;
        }

        int oneMeasureTime = argumentParser.getIntValue("-time", 1);

        CutSettings cutSettings = new CutSettings();
        cutSettings.setAllowRotation(true);
        cutSettings.setSawWidth(0);
        cutSettings.setSheets(new DimensionItem[]{new DimensionItem(100000, 200, "1", 10)});

        final CsvWriter writer = new CsvWriter("data.csv");
        writer.writeString("elements_count");
        writer.writeString("time_per_rating");
        writer.writeString("invocations");
        writer.writeString("time");
        writer.writeString("total_time");
        writer.newRow();

        Watch watch = new Watch();
        GoldenSectionProvider gsp = new GoldenSectionProvider(1, 1000);

        while (true)
        {
            int elementsCount = gsp.nextIntValue();
            DimensionItem[] elements = createRandomElements(elementsCount);
            cutSettings.setElementItems(elements);
            sawyer.setCutSettings(cutSettings);
            /*Element[] elements = Common.createRandomeElements(elementsCount, 10, 10, 150, 1);
            Strips strips = new Strips(new Dimension[]{ new Dimension(10000000, 200)});
            Individual individual;
            RatingFunction rFx;
            if(individualType.equals("b")){
                individual = new BIndividual(elements.length);
                rFx = new BRatingFunction(strips, elements);
             }else{
                individual = new GIndividual(elements.length);
                rFx = new GRatingFunction(strips, elements);
            }
            */

            System.out.println(elementsCount);
            Statistics.clear();
            watch.reset();
            watch.start();
            sawyer.start();
            while (sawyer.getState() != Sawyer.States.STOPPED)
            {
                if (watch.getDurationInSeconds() > oneMeasureTime)
                {
                    sawyer.stop();
                }
                Thread.sleep(10);
            }
            /*while(watch.getDurationInSeconds() < 2){
                individual.initRandom();
                rFx.rate(individual);
            }*/
            watch.stop();
            writer.writeInteger(elementsCount);
            writer.writeFloat(Statistics.getRatingFxTime() / Statistics.getRatingFxInvocations());
            writer.writeInteger(Statistics.getRatingFxInvocations());
            writer.writeFloat(Statistics.getRatingFxTime());
            writer.writeFloat(watch.getDurationInSeconds());
            writer.newRow();
            writer.flush();
            if (Utils.shouldStop())
            {
                break;
            }
        }

        writer.close();
    }
}
