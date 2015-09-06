package by.dak.cutting.linear.report.data;

import by.dak.cutting.cut.base.Element;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 28.04.11
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttedSheetData
{
    private BufferedImage cuttingMap;
    private List<Element> elements;

    public List<Element> getElements()
    {
        return elements;
    }

    public void setElements(List<Element> elements)
    {
        this.elements = elements;
    }

    public BufferedImage getCuttingMap()
    {
        return cuttingMap;
    }

    public void setCuttingMap(BufferedImage cuttingMap)
    {
        this.cuttingMap = cuttingMap;
    }

}
