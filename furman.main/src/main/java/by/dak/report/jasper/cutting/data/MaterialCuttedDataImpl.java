package by.dak.report.jasper.cutting.data;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class MaterialCuttedDataImpl implements MaterialCuttedData
{
    private TextureBoardDefPair textureBoardDefPair;
    private Segment segment;
    private List<CuttedSheetData> cuttedSheets = new ArrayList<CuttedSheetData>();


    public void addCuttedSheetData(CuttedSheetData sheetData)
    {
        cuttedSheets.add(sheetData);
    }

    @Override
    public List<CuttedSheetData> getCuttedSheets()
    {
        return cuttedSheets;
    }


    @Override
    public TextureBoardDefPair getTextureFurniturePair()
    {
        return textureBoardDefPair;
    }

    public void setTextureFurniturePair(TextureBoardDefPair textureBoardDefPair)
    {
        this.textureBoardDefPair = textureBoardDefPair;
    }
}
