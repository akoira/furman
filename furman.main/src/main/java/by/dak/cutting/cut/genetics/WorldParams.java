/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.genetics;

import by.dak.cutting.cut.gui.XmlSerializer;

import java.io.IOException;

/**
 * @author Peca
 */
public class WorldParams
{
    private PopulationParams populationParams = new PopulationParams();
    private float elitismRatio = 0.5f;     //procent jedincu co se zachovaji pred migraci
    private int maximumGenerationsBeforeMigration = 50;
    private int minimumGenerationsBeforeMigration = 1;

    public float getElitismRatio()
    {
        return elitismRatio;
    }

    public void setElitismRatio(float elitismRatio)
    {
        this.elitismRatio = elitismRatio;
    }

    public int getMaximumGenerationsBeforeMigration()
    {
        return maximumGenerationsBeforeMigration;
    }

    public void setMaximumGenerationsBeforeMigration(int maximumGenerationsBeforeMigration)
    {
        this.maximumGenerationsBeforeMigration = maximumGenerationsBeforeMigration;
    }

    public PopulationParams getPopulationParams()
    {
        return populationParams;
    }

    public void setPopulationParams(PopulationParams populationParams)
    {
        this.populationParams = populationParams;
    }

    public int getMinimumGenerationsBeforeMigration()
    {
        return minimumGenerationsBeforeMigration;
    }

    public void setMinimumGenerationsBeforeMigration(int minimumGenerationsBeforeMigration)
    {
        this.minimumGenerationsBeforeMigration = minimumGenerationsBeforeMigration;
    }

    public static void saveToFile(WorldParams worldParams, String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        xmlser.writeToXmlFile(worldParams, fileName);
    }

    public static WorldParams loadFromFile(String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        return (WorldParams) xmlser.readFromXmlFile(fileName);
    }

}
