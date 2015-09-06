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
public class PopulationParams
{
    private int maxIndividualsCount = 1000;  //maximalni pocet jedincu v populaci-Максимальное количество лиц в области народонаселения
    private SelectionParams selectionParams = new SelectionParams(); //parametry pro selekci
    private ReproductionParams reproductionParams = new ReproductionParams(); //parametry pro reproduckci

    public int getMaxIndividualsCount()
    {
        return maxIndividualsCount;
    }

    public void setMaxIndividualsCount(int maxIndividualsCount)
    {
        this.maxIndividualsCount = maxIndividualsCount;
    }

    public ReproductionParams getReproductionParams()
    {
        return reproductionParams;
    }

    public void setReproductionParams(ReproductionParams reproductionParams)
    {
        this.reproductionParams = reproductionParams;
    }

    public SelectionParams getSelectionParams()
    {
        return selectionParams;
    }

    public void setSelectionParams(SelectionParams selectionParams)
    {
        this.selectionParams = selectionParams;
    }

    public static void saveToFile(PopulationParams populationParams, String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        xmlser.writeToXmlFile(populationParams, fileName);
    }

    public static PopulationParams loadFromFile(String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        return (PopulationParams) xmlser.readFromXmlFile(fileName);
    }
}
