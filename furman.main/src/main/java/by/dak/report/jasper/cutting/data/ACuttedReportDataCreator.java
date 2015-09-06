package by.dak.report.jasper.cutting.data;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.helper.BoardDimensionsHelper;
import by.dak.cutting.cut.guillotine.helper.DimensionsHelper;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.Board;
import by.dak.utils.Creator;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.09.11
 * Time: 8:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class ACuttedReportDataCreator<D extends AOrderBoardDetail> implements Creator<CuttedReportData>
{
    private CuttingModel cuttingModel;

    public ACuttedReportDataCreator(CuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }


    public CuttedReportData create()
    {
        CuttedReportDataImpl cuttedReportData = new CuttedReportDataImpl();
        cuttedReportData.setOrder(getCuttingModel().getOrder());
        for (int i = 0; i < getCuttingModel().getPairs().size(); i++)
        {
            TextureBoardDefPair pair = getCuttingModel().getPairs().get(i);
            MaterialCuttedDataImpl materialCuttedData = new MaterialCuttedDataImpl();
            materialCuttedData.setTextureFurniturePair(pair);
            cuttedReportData.add(materialCuttedData);
            Strips s = getCuttingModel().getStrips(pair);
            if (s == null)
            {
                continue;
            }
            List<Segment> segments = s.getSegments();
            for (Segment segment : segments)
            {
                CuttedSheetDataImpl cuttedSheetData = new CuttedSheetDataImpl();
                materialCuttedData.addCuttedSheetData(cuttedSheetData);
                cuttedSheetData.setSheetSegment(segment);
                Board board = new Board();
                board.setPair(pair);
                board.setWidth(segment.getMaterialWidth());
                board.setLength(segment.getMaterialLength());
                cuttedSheetData.setBoard(board);
                cuttedSheetData.setBufferedImage(new BufferedImageWrapper(segment, pair.getBoardDef().getCutter()));
                Element[] elements = segment.getElements();
                for (Element element : elements)
                {
                    DimensionsHelper dimensionsHelper = new BoardDimensionsHelper();
                    AOrderBoardDetail entity = (AOrderBoardDetail) dimensionsHelper.getElementOrderDetail(element);
//                    OrderFurniture entity = element.getFurniture();
                    if (cuttedSheetData.containsFurniture(entity.getNumber()))
                    {
                        cuttedSheetData.incrementCount(entity.getNumber());
                    }
                    else
                    {
                        AOrderBoardDetail newE = (AOrderBoardDetail) entity.clone();
                        newE.setAmount(1);
                        cuttedSheetData.addFurniture(newE);
                    }
                }
            }
        }
        return cuttedReportData;
    }

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }
}
