package by.dak.cutting.linear.report.data;

import by.dak.cutting.linear.FurnitureTypeCodePair;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 28.04.11
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class LinearMaterialCuttedData
{
    private FurnitureTypeCodePair pair;
    private List<LinearCuttedSheetData> cuttedSheetData;

    public FurnitureTypeCodePair getPair()
    {
        return pair;
    }

    public void setPair(FurnitureTypeCodePair pair)
    {
        this.pair = pair;
    }

    public List<LinearCuttedSheetData> getCuttedSheetData()
    {
        return cuttedSheetData;
    }

    public void setCuttedSheetData(List<LinearCuttedSheetData> cuttedSheetData)
    {
        this.cuttedSheetData = cuttedSheetData;
    }
}
