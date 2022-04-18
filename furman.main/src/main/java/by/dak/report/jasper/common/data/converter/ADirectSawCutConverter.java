package by.dak.report.jasper.common.data.converter;

import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrderBoardDetail;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.report.jasper.GetSawLength;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.utils.convert.Converter;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static by.dak.report.jasper.ReportUtils.calcLinear;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.09.11
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class ADirectSawCutConverter<D extends AOrderBoardDetail> implements Converter<List<D>, CommonDatas<CommonData>> {

    private final static String CUTTING_DATA = ResourceBundle.getBundle("by/dak/report/jasper/common/commonReport").getString("cutting.data");



    private CommonDatas<CommonData> datas;

    private CuttingModel cuttingModel;

    public final MainFacade mainFacade;
    public final Dailysheet dailysheet;
    protected ADirectSawCutConverter(CuttingModel cuttingModel) {
        this.mainFacade = FacadeContext.getMainFacade();
        this.dailysheet = MainFacade.dailysheet.apply(this.mainFacade).apply(cuttingModel.getOrder());

        this.cuttingModel = cuttingModel;
        datas = new CommonDatas<CommonData>(CommonDataType.cutting, cuttingModel.getOrder());
    }

    public CommonDatas<CommonData> getDatas() {
        return datas;
    }

    public void setDatas(CommonDatas<CommonData> datas) {
        this.datas = datas;
    }

    public CuttingModel getCuttingModel() {
        return cuttingModel;
    }

    protected void updateDirectSawCut(BoardDef boardDef, double length) {
        CommonData data = CommonData.valueOf(ServiceType.cutting, boardDef);
        int i = datas.indexOf(data);
        data = i > -1 ? datas.remove(i) : data;
        data.increase(length);
        if (data.isEmptyPrice()) {
            PriceEntity price = mainFacade.getPriceFacade().getPrice(boardDef, ServiceType.cutting);
            ReportUtils.fillPrice(data, price, this.dailysheet);
        }
        datas.add(data);
    }

    public CommonDatas<CommonData> convert(List<D> furnitures) {
        // Stage 1: count sawcut for simple types
        for (TextureBoardDefPair pair : getCuttingModel().getPairs()) {
            Strips strips = getCuttingModel().getStrips(pair);
            if (strips != null) {
                double length = calcLinear(GetSawLength.valueOf(strips, pair.getBoardDef()).get());
                updateDirectSawCut(pair.getBoardDef(), length);
            }
        }

        for (D furniture : furnitures) {
            // Stage 2: count sawcut for complex types
            if (furniture.isComplex()) {
                if (furniture.isPrimary()) {
                    double length = calcLinear((furniture.getLength() * 2 + furniture.getWidth() * 2) * furniture.getAmount());
                    updateDirectSawCut(furniture.getComlexBoardDef(), length);
                }
            } else {
                updateDirectSawCut(furniture.getBoardDef(), 0);
            }
        }
        Collections.sort(getDatas());
        if (getDatas().size() > 0)
            getDatas().get(getDatas().size() - 1).markAsLast();
        return getDatas();
    }
}
