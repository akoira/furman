/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.persistence.entities.Cutter;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Peca
 */
public class CutSettings
{
    private DimensionItem[] elementItems;
    private DimensionItem[] sheets;
    private boolean allowRotation;
    private int sawWidth;
    private Strips strips;
    private Element[] elements;
    private Cutter cutter;

    @Deprecated
    public boolean getAllowRotation()
    {
        return allowRotation;
    }

    /**
     * allowRotation устанавливается для strips
     *
     * @param allowRotation
     */
    @Deprecated
    public void setAllowRotation(boolean allowRotation)
    {
        this.allowRotation = allowRotation;
    }

    /**
     * в cutSettingsCreator инициализируются strips и устанавливаются сегметы
     *
     * @return
     */
    public Strips getStrips()
    {
        return strips;
    }

    public void setStrips(Strips strips)
    {
        this.strips = strips;
    }

    public Element[] getElements()
    {
        return elements;
    }

    public void setElements(Element[] elements)
    {
        this.elements = elements;
    }

    @Deprecated
    public int getSawWidth()
    {
        return sawWidth;
    }

    /**
     * устанавливается для strips
     *
     * @param sawWidth
     */
    @Deprecated
    public void setSawWidth(int sawWidth)
    {
        this.sawWidth = sawWidth;
    }

    @Deprecated
    public void setElementItems(DimensionItem[] elements)
    {
        this.elementItems = elements;
    }

    @Deprecated
    public DimensionItem[] getElementItems()
    {
        return this.elementItems;
    }

    @Deprecated
    public void setSheets(DimensionItem[] sheets)
    {
        this.sheets = sheets;
    }

    @Deprecated
    public DimensionItem[] getSheets()
    {
        return Arrays.copyOf(this.sheets, this.sheets.length);
    }


    public static void saveToFile(CutSettings cutSettings, String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        xmlser.alias("item", DimensionItem.class);
        xmlser.alias("cutSettings", CutSettings.class);
        xmlser.writeToXmlFile(cutSettings, fileName);
    }

    public static CutSettings loadFromFile(String fileName) throws IOException
    {
        XmlSerializer xmlser = new XmlSerializer();
        xmlser.alias("item", DimensionItem.class);
        xmlser.alias("cutSettings", CutSettings.class);
        return (CutSettings) xmlser.readFromXmlFile(fileName);
    }

    public Cutter getCutter()
    {
        return cutter;
    }

    public void setCutter(Cutter cutter)
    {
        this.cutter = cutter;
    }
}
