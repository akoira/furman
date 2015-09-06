/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement;

import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.XmlSerializer;
import by.dak.cutting.cut.gui.cuttingApp.BSawyer;
import by.dak.cutting.cut.gui.cuttingApp.CSawyer;
import by.dak.cutting.cut.gui.cuttingApp.GSawyer;
import by.dak.cutting.cut.gui.cuttingApp.Sawyer;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.measurement.utils.ArgumentParser;
import by.dak.cutting.cut.measurement.utils.CsvWriter;
import by.dak.cutting.cut.measurement.utils.Watch;

import java.io.IOException;

/**
 * @author Peca
 */
public class ComparisonTest
{

    public static void main(String[] args) throws IOException, InterruptedException
    {
        ArgumentParser argumentParser = new ArgumentParser(args);

        String individualType = argumentParser.getStringValue("-type", "g").toLowerCase();
        System.out.println(individualType);

        int measureCount = argumentParser.getIntValue("-measureCount", 2);
        System.out.println("measureCount: " + measureCount);

        Sawyer sawyer;
        if (individualType.equals("g"))
        {
            sawyer = new GSawyer();
        }
        else if (individualType.equals("b"))
        {
            sawyer = new BSawyer();
        }
        else if (individualType.equals("c"))
        {
            sawyer = new CSawyer();
        }
        else
        {
            System.out.println("Invalid individual type");
            return;
        }

        XmlSerializer ser = new XmlSerializer();
        String[] fileNames = (String[]) ser.readFromXmlFile("tocutFiles.xml");

        CsvWriter writer = new CsvWriter("data.csv");
        writer.writeString("type");
        writer.writeString("measure");
        writer.writeString("1s");
        writer.writeString("10s");
        writer.writeString("1m");
        writer.writeString("10m");
        writer.newRow();
        writer.flush();

        final Watch oneMeasureTimeWatch = new Watch();
        final float[] minimalWaste = new float[4];

        sawyer.setNewSolutionListener(new Sawyer.INewSolutionListener()
        {

            public void newSolutionFound(Strips strips)
            {
                // System.out.println(strips.getWasteRatio());
                if (oneMeasureTimeWatch.getDurationInSeconds() <= 1)
                {
                    minimalWaste[0] = Math.min(minimalWaste[0], strips.getWasteRatio());
                }
                else if (oneMeasureTimeWatch.getDurationInSeconds() <= 10)
                {
                    minimalWaste[1] = Math.min(minimalWaste[1], strips.getWasteRatio());
                }
                else if (oneMeasureTimeWatch.getDurationInMinutes() <= 1)
                {
                    minimalWaste[2] = Math.min(minimalWaste[2], strips.getWasteRatio());
                }
                else
                {
                    minimalWaste[3] = Math.min(minimalWaste[3], strips.getWasteRatio());
                }
            }
        });

        CutSettings cutSettings;
        float oneMeasureTimeMinutes = 10.0f;

        for (String fileName : fileNames)
        {
            System.out.println(fileName);
            cutSettings = CutSettings.loadFromFile(fileName);
            for (int measureIndex = 0; measureIndex < measureCount; measureIndex++)
            {
                System.out.println("measure: " + measureIndex);

                minimalWaste[0] = Float.MAX_VALUE;
                minimalWaste[1] = Float.MAX_VALUE;
                minimalWaste[2] = Float.MAX_VALUE;
                minimalWaste[3] = Float.MAX_VALUE;

                oneMeasureTimeWatch.reset();
                oneMeasureTimeWatch.start();

                sawyer.setCutSettings(cutSettings);
                sawyer.start();
                while (sawyer.getState() != Sawyer.States.STOPPED)
                {
                    if (oneMeasureTimeWatch.getDurationInMinutes() > oneMeasureTimeMinutes)
                    {
                        sawyer.stop();
                    }
                    Thread.sleep(10);
                    if (Utils.shouldStop())
                    {
                        break;
                    }
                }
                //zapis do souboru pole bestRatings
                writer.writeString(individualType + " - " + fileName);
                writer.writeInteger(measureIndex);
                writer.writeFloat(minimalWaste[0]);
                writer.writeFloat(minimalWaste[1]);
                writer.writeFloat(minimalWaste[2]);
                writer.writeFloat(minimalWaste[3]);
                writer.newRow();
                writer.flush();

                if (Utils.shouldStop())
                {
                    break;
                }
            }
        }
        writer.flush();
        writer.close();
    }

    //---- best solution found
    //pokud cas < 1 sekunda
    //minimalWaste[0] <-- nejlepsi reseni
    //pokud cas < 10 sekund
    //minimalWaste[1] <-- nejlepsi reseni
    //jinak pokud cas < 1 minata
    //minimalWaste[2] <-- nejlepsi reseni
    //jinak
    //minimalWaste[3] <-- nejlepsi reseni
    //konec pokud

    //opakuj pro vsechny .tocut soubory
    //nacti .tocut soubory
    //opakuj measureCount x
    //resetuj a pust stopky oneMeasureTimeWatch
    //resetuj pole bestRatings
    //resetuj sawyera
    //pust sawyera
    //mer po dobu oneMeasureTime
    //zapis do souboru pole bestRatings
    //konec opakuj
    //konec opakuj

}
