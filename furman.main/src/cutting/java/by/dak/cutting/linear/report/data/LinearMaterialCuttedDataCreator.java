package by.dak.cutting.linear.report.data;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.FurnitureTypeCodePair;
import by.dak.cutting.linear.LinearCuttingModel;
import by.dak.cutting.linear.LinearSheetDimensionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 28.04.11
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class LinearMaterialCuttedDataCreator
{
    private LinearCuttingModel cuttingModel;

    public LinearMaterialCuttedDataCreator(LinearCuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    public List<LinearMaterialCuttedData> create()
    {
        List<LinearMaterialCuttedData> linearMaterialCuttedDatas = new ArrayList<LinearMaterialCuttedData>();

        List<FurnitureTypeCodePair> pairs = cuttingModel.getPairs();

        for (FurnitureTypeCodePair pair : pairs)
        {
            LinearMaterialCuttedData materialCuttedData = new LinearMaterialCuttedData();
            materialCuttedData.setPair(pair);

            List<LinearCuttedSheetData> cuttedSheetDataList = new ArrayList<LinearCuttedSheetData>();

            Strips strips = cuttingModel.getStrips(pair);
            List<Segment> segments = strips.getSegments();

            while (segments.size() > 0)
            {
                List<Segment> pack = packSegments(segments);
                int indexFrom = pack.size();
                int indexTo = segments.size();
                segments = segments.subList(indexFrom, indexTo);

                LinearBufferedImageCreator creator = new LinearBufferedImageCreator();
                LinearCuttedSheetData cuttedSheetData = new LinearCuttedSheetData();
                cuttedSheetData.setCuttingMap(creator.createBufferedImage(pack));
                cuttedSheetData.setElements(putElements(pack));
                cuttedSheetDataList.add(cuttedSheetData);
            }

            materialCuttedData.setCuttedSheetData(cuttedSheetDataList);
            linearMaterialCuttedDatas.add(materialCuttedData);
        }
        return linearMaterialCuttedDatas;
    }


    /**
     * элементы для деталей карты
     *
     * @param pack
     * @return
     */
    private List<Element> putElements(List<Segment> pack)
    {
        List<Element> elements = new ArrayList<Element>();
        for (Segment segment : pack)
        {
            elements.addAll(Arrays.asList(segment.getElements()));
        }
        return elements;
    }

    /**
     * упаковывает сегменты до нужного уровня, чтобы в карте раскроя поместилось достаточное кол-во сегментов,
     * а остальные перейдут в дргую карту
     *
     * @param segments
     * @return
     */
    private List<Segment> packSegments(List<Segment> segments)
    {
        List<Segment> enoughtSegments = new ArrayList<Segment>();
        for (int i = 1; i <= segments.size(); i++)
        {
            if (LinearBufferedImageCreator.scaleFactorY * i *
                    (LinearSheetDimensionItem.SHEET_HEIGHT + LinearBufferedImageCreator.OFFSET_Y) < LinearBufferedImageCreator.IMAGE_HEIGHT)
            {
                enoughtSegments.add(segments.get(i - 1));
            }
            else
            {
                break;
            }
        }
        return enoughtSegments;
    }
}
