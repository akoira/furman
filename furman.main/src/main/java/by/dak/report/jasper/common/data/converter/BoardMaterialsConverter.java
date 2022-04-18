package by.dak.report.jasper.common.data.converter;

import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.StringValueAnnotationProcessor;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Denis Koyro
 * @version 0.1 29.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class BoardMaterialsConverter implements Converter<List<TextureBoardDefPair>, CommonDatas<CommonData>>
{

    private final static String TEXTURE_PATTERN = "{0} {1}";

    private CuttingModel cuttingModel;

    private SortedMap<String, CommonDatas<CommonData>> boardMaterials = new TreeMap<String, CommonDatas<CommonData>>(new StringComparator());

    public final MainFacade mainFacade;
    public final Dailysheet dailysheet;

    public BoardMaterialsConverter(CuttingModel cuttingModel, MainFacade facade) {
        this.cuttingModel = cuttingModel;
        this.mainFacade =facade;
        this.dailysheet = MainFacade.dailysheet.apply(this.mainFacade).apply(null);
    }

    public CommonDatas<CommonData> convert(List<TextureBoardDefPair> textureBoardDefPairs)
    {
        for (TextureBoardDefPair pair : textureBoardDefPairs)
        {
            calculateBoardMaterials(pair);
        }
        return sortBoardMaterials();
    }

    private void calculateBoardMaterials(TextureBoardDefPair pair)
    {
        TextureEntity texture = pair.getTexture();

        Strips strips = cuttingModel.getStrips(pair);
        double value;
        if (strips.getStripsEntity() != null &&
                strips.getStripsEntity().getPaidValue() != null &&
                strips.getStripsEntity().getPaidValue() > 0)
        {
            value = strips.getStripsEntity().getPaidValue();
        }
        else
        {
            value = ReportUtils.getPaidValueBy(pair, strips);
            strips.getStripsEntity().setPaidValue(value);
            mainFacade.getFacadeBy(strips.getStripsEntity().getClass()).save(strips.getStripsEntity());
        }


        updateBoardMaterial(texture, pair.getBoardDef(), value);
    }


    public void resetCommonDataBy(TextureBoardDefPair pair)
    {
		String service = StringValueAnnotationProcessor.getProcessor().convert(pair.getBoardDef());
		String name = StringValueAnnotationProcessor.getProcessor().convert(pair.getTexture());

		CommonDatas<CommonData> datas = boardMaterials.get(service);

		if (datas != null)
        {
            for (CommonData commonData : datas)
            {
				if (commonData.getService().equals(service) && commonData.getName().equals(name)) {
                    commonData.setCount(0d);
                    break;
                }
            }
        }
    }

    private void updateBoardMaterial(TextureEntity texture, BoardDef boardDef, double square)
    {
        String key = StringValueAnnotationProcessor.getProcessor().convert(boardDef);
        CommonDatas<CommonData> datas = boardMaterials.get(key);
        if (datas == null)
        {
            datas = new CommonDatas<CommonData>(CommonDataType.board, cuttingModel.getOrder());
        }

        CommonData data = CommonData.valueOf(boardDef, texture);
        int i = datas.indexOf(data);
        data = i > -1 ? datas.remove(i) : data;
        data.increase(square);
        if (data.isEmptyPrice())
        {
            PriceEntity price = mainFacade.getPriceFacade().findUniqueBy(boardDef, texture);
            ReportUtils.fillPrice(data, price, this.dailysheet);
        }
        datas.add(data);
        boardMaterials.put(key, datas);
    }

    private CommonDatas<CommonData> sortBoardMaterials()
    {
        CommonDatas<CommonData> sorted = new CommonDatas<CommonData>(CommonDataType.board, cuttingModel.getOrder());
        for (String key : boardMaterials.keySet())
        {
            List<CommonData> boards = boardMaterials.get(key);
            Collections.sort(boards);
            boards.get(boards.size() - 1).markAsLast();
            sorted.addAll(boards);
        }
        return sorted;
    }

    public void setCuttingModel(CuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }
}
