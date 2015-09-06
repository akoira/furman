package by.dak.report.jasper.cutting.data;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;

import java.util.List;

/**
 * Материал:  TextureFurniturePair.texture  TextureFurniturePair.furnitureDef
 * Номер карты: index  getCuttedSheets()
 * Количество листов: Сколько листов занимает текущий      CuttedSheetData
 *
 * @author akoyro
 * @version 0.1 02.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public interface MaterialCuttedData
{
    TextureBoardDefPair getTextureFurniturePair();

    List<CuttedSheetData> getCuttedSheets();
}
