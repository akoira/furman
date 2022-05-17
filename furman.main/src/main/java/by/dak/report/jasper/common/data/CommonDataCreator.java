package by.dak.report.jasper.common.data;

import by.dak.additional.Additional;
import by.dak.additional.facade.AdditionalFacadeImpl;
import by.dak.additional.report.AdditionalsConverter;
import by.dak.cutting.agt.AGTFacade;
import by.dak.cutting.agt.report.AGTFurnitureDataConverter;
import by.dak.cutting.agt.report.AGTServiceDataConverter;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPainter;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.AttributeKeys;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.GluiengLength;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.MillingConverter;
import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import by.dak.cutting.swing.order.data.*;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.cutting.zfacade.report.ZFacadeFurnitureDataConverter;
import by.dak.cutting.zfacade.report.ZFacadeServiceDataConverter;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.persistence.entities.predefined.ServiceType;
import by.dak.persistence.entities.predefined.Side;
import by.dak.plastic.DSPPlasticDetail;
import by.dak.plastic.jasper.converter.DSPPlasticDirectSawCutConverter;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.converter.BoardMaterialsConverter;
import by.dak.report.jasper.common.data.converter.BorderMaterialsConverter;
import by.dak.report.jasper.common.data.converter.DirectSawCutConverter;
import by.dak.report.jasper.common.data.converter.FurnitureConverter;
import by.dak.template.TemplateFacade;
import by.dak.template.report.TemplateFacadeServiceDataConverter;
import by.dak.utils.Creator;
import org.apache.commons.lang3.StringUtils;
import org.jhotdraw.draw.Figure;

import java.awt.*;
import java.util.List;
import java.util.*;

import static by.dak.report.jasper.ReportUtils.calcLinear;
import static by.dak.report.jasper.ReportUtils.calcSquare;

/**
 * @author akoyro, vkozlovski
 * @version 0.1 04.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 * todo класс должен быть переделан (оптимизирован)
 */
public class CommonDataCreator implements Creator<CommonReportData> {

    private final MainFacade mainFacade;

    private final AOrder order;
    private final Dailysheet dailysheet;

    private final CuttingModel cuttingModel;
    private final CuttingModel dspPlasticModel;

//    private boolean usePriceDealer = false;

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("by/dak/report/jasper/common/commonReport");


    public CommonDataCreator(CuttingModel cuttingModel, MainFacade mainFacade) {
        this.cuttingModel = cuttingModel;
        this.mainFacade = mainFacade;
        this.dspPlasticModel = this.mainFacade.getDspPlasticStripsFacade().loadCuttingModel(cuttingModel.getOrder()).load();
        this.order = cuttingModel.getOrder();
        Dailysheet dailysheet = this.mainFacade.getDailysheetFacade().loadCurrentDailysheet();
        this.dailysheet = dailysheet != null ? dailysheet : this.order.getCreatedDailySheet();
    }

    public CommonReportData create() {
        CommonReportDataImpl reportData = new CommonReportDataImpl(order);

        fillServicesData(reportData);

        fillMaterialsData(reportData);

        fillFacadeData(reportData);

        reportData.getOrder().setCost(reportData.getCommonCost());
        reportData.getOrder().setDialerCost(reportData.getDialerCommonCost());

        mainFacade.getOrderFacadeBy(reportData.getOrder().getClass()).recalculate(reportData.getOrder());
        mainFacade.getOrderFacadeBy(reportData.getOrder().getClass()).save(reportData.getOrder());
        return reportData;
    }

    private void fillFacadeData(CommonReportDataImpl reportData) {
        List<ZFacade> zFacades = mainFacade.getzFacadeFacade().findAllByField(ZFacade.PROPERTY_order, order);
        mainFacade.getzFacadeFacade().fillTransients(zFacades);

        List<AGTFacade> agtFacades = mainFacade.getAgtFacadeFacade().findAllByField(AGTFacade.PROPERTY_order, order);
        mainFacade.getAgtFacadeFacade().fillTransients(agtFacades);

        List<TemplateFacade> templateFacades =  mainFacade.getTemplateFacadeFacade().findAllByField(TemplateFacade.PROPERTY_order, order);

        fillFacadeServicesData(reportData, zFacades, agtFacades, templateFacades);

        fillFacadeFurnitureData(reportData, zFacades, agtFacades);
        fillFacadeDialerData(reportData, zFacades, agtFacades);
    }

    private void fillFacadeFurnitureData(CommonReportDataImpl reportData, List<ZFacade> zFacades, List<AGTFacade> agtFacades) {
        ArrayList<FurnitureLink> furnitureLinks = new ArrayList<FurnitureLink>();
        for (AGTFacade agtFacade : agtFacades) {
            furnitureLinks.addAll(mainFacade.getFurnitureLinkFacade().loadAllBy(agtFacade));
        }

        for (ZFacade facade : zFacades) {
            furnitureLinks.addAll(mainFacade.getFurnitureLinkFacade().loadAllBy(facade));
        }
        CommonDatas<CommonData> furnitureData = new FurnitureConverter(CommonDataType.facadeFurniture, order, mainFacade)
                .convert(furnitureLinks);
        reportData.setCommonDatas(furnitureData);
    }

    private void fillFacadeDialerData(CommonReportDataImpl reportData, List<ZFacade> zFacades, List<AGTFacade> agtFacades) {
        CommonDatas<CommonData> zfacadeDatas = new ZFacadeFurnitureDataConverter(order, mainFacade).convert(zFacades);
        CommonDatas<CommonData> agtDatas = new AGTFurnitureDataConverter(order, mainFacade).convert(agtFacades);

        reportData.setCommonDatas(zfacadeDatas);
        reportData.setCommonDatas(agtDatas);
    }

    private void fillMaterialsData(CommonReportDataImpl reportData) {
        BoardMaterialsConverter boardMaterialsConverter = new BoardMaterialsConverter(cuttingModel, mainFacade);
        CommonDatas<CommonData> boardMaterialsData = boardMaterialsConverter.convert(cuttingModel.getPairs());

        if (mainFacade.getDspPlasticDetailFacade().hasPlasticDetails(order)) {
            for (TextureBoardDefPair pair : dspPlasticModel.getPairs()) {
                if (mainFacade.getDspPlasticDetailFacade().isPlastic(pair.getBoardDef())) {
                    //reset data calculated in the common cutting
                    boardMaterialsConverter.resetCommonDataBy(pair);
                }
            }
            boardMaterialsConverter.setCuttingModel(dspPlasticModel);
            boardMaterialsData = boardMaterialsConverter.convert(dspPlasticModel.getPairs());
        }
        reportData.setCommonDatas(boardMaterialsData);

        CommonDatas<CommonData> borderMaterialsData = new BorderMaterialsConverter(order, mainFacade)
                .convert(mainFacade.getOrderFurnitureFacade().findOrderedWithGlueing(order, Boolean.TRUE));
        reportData.setCommonDatas(borderMaterialsData);


        List<OrderItem> orderItems = mainFacade.getOrderItemFacade().loadBy(order);
        List<Additional> additionals = new ArrayList<Additional>();
        List<FurnitureLink> furnitureLinks = new ArrayList<FurnitureLink>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getType() == OrderItemType.first || orderItem.getType() == OrderItemType.common) {
                additionals.addAll(mainFacade.getAdditionalFacade().loadAll(AdditionalFacadeImpl.getSearchFilterBy(orderItem)));
                furnitureLinks.addAll(mainFacade.getFurnitureLinkFacade().loadAllBy(orderItem));
            }
        }

        CommonDatas<CommonData> furnitureData = new FurnitureConverter(CommonDataType.furniture, order, mainFacade)
                .convert(furnitureLinks);
        CommonDatas<CommonData> additionalsData = new AdditionalsConverter(order).convert(additionals);

        reportData.setCommonDatas(furnitureData);
        reportData.setCommonDatas(additionalsData);
    }

    private void fillFacadeServicesData(CommonReportDataImpl reportData, List<ZFacade> zFacades,
                                        List<AGTFacade> agtFacades,
                                        List<TemplateFacade> templateFacades) {
        CommonDatas<CommonData> zServiceDatas = new ZFacadeServiceDataConverter(order, mainFacade).convert(zFacades);
        CommonDatas<CommonData> agtServiceDatas = new AGTServiceDataConverter(order, mainFacade).convert(agtFacades);
        CommonDatas<CommonData> templateServiceDatas = new TemplateFacadeServiceDataConverter(order, mainFacade).convert(templateFacades);

        setAndSave(reportData, zServiceDatas);
        setAndSave(reportData, agtServiceDatas);

        //the code needs to add drilling for facade correctly.
        CommonDatas datas = reportData.getCommonDatas(CommonDataType.drilling);
        for (CommonData commonData : templateServiceDatas) {
            int i = datas.indexOf(commonData);
            if (i > -1) {
                ((CommonData) datas.get(i)).increase(commonData.getCount());
            } else {
                datas.add(commonData);
            }
        }
        sort(datas);
        setAndSave(reportData, datas);
    }

    private void fillServicesData(CommonReportDataImpl reportData) {
        fillOrderFurnitureServicesData(reportData);
        fillDSPPlasticServicesData(reportData);
    }

    private void fillDSPPlasticServicesData(CommonReportDataImpl reportData) {
        CommonDatas<CommonData> plasticPatch = new CommonDatas<CommonData>(CommonDataType.plasticPatch, order);

        List<DSPPlasticDetail> dspPlasticDetails = mainFacade.getDspPlasticDetailFacade().loadAllBy(order);
        for (DSPPlasticDetail detail : dspPlasticDetails) {
            if (mainFacade.getDspPlasticDetailFacade().isPlastic(detail.getBoardDef())) {
                countPlasticPatch(plasticPatch, detail);
            }
        }
        sort(plasticPatch);

        setAndSave(reportData, plasticPatch);

        //получаем sawCut для дсп с пластиком и добавяем к общему (orderFurniture)
        DSPPlasticDirectSawCutConverter converter = new DSPPlasticDirectSawCutConverter(dspPlasticModel);
        converter.setDatas(reportData.getCommonDatas(CommonDataType.cutting));
        CommonDatas<CommonData> directSawCutData = converter.convert(dspPlasticDetails);
        reportData.setCommonDatas(directSawCutData.getCommonDataType(), directSawCutData);
        setAndSave(reportData, directSawCutData);
    }

    private void countPlasticPatch(List<CommonData> plasticPatch, DSPPlasticDetail detail) {
        double square = calcSquare(detail.getLength() * detail.getWidth() * detail.getAmount());
        updateCommonDatas(plasticPatch, detail.getBoardDef(), ServiceType.plasticPatch, square);
    }

    private void fillOrderFurnitureServicesData(CommonReportDataImpl reportData) {
        CommonDatas<CommonData> patch = new CommonDatas<>(CommonDataType.patch, order);
        CommonDatas<CommonData> groove = new CommonDatas<>(CommonDataType.groove, order);
        CommonDatas<CommonData> angle = new CommonDatas<>(CommonDataType.angle, order);
        CommonDatas<CommonData> directGlueing = new CommonDatas<>(CommonDataType.directGlueing, order);
        CommonDatas<CommonData> curveGlueing = new CommonDatas<>(CommonDataType.curveGlueing, order);
        CommonDatas<CommonData> milling = new CommonDatas<>(CommonDataType.milling, order);
        CommonDatas<CommonData> cutoff = new CommonDatas<>(CommonDataType.cutoff, order);
        CommonDatas<CommonData> drilling = new CommonDatas<>(CommonDataType.drilling, order);

        List<OrderFurniture> orderFurnitures = mainFacade.getOrderFurnitureFacade().loadAllBy(order);
        for (OrderFurniture furniture : orderFurnitures) {
            countPatch(patch, furniture);
            countGrooveSection(groove, furniture);
            countAnlgeSection(angle, furniture);
            countDirectGlueingSection(directGlueing, furniture);
            countCurveGlueingSection(curveGlueing, furniture);
            countMillingSection(milling, furniture);
            countCutoffSection(cutoff, furniture);
            countDriliingSection(drilling, furniture);
        }
        sort(patch);
        sort(groove);
        sort(angle);
        sort(directGlueing);
        sort(curveGlueing);
        sort(milling);
        sort(cutoff);
        sort(drilling);

        increaseEachEntryByExtraPercent(directGlueing);
        increaseEachEntryByExtraPercent(curveGlueing);

        CommonDatas<CommonData> directSawCutData = new DirectSawCutConverter(cuttingModel).convert(orderFurnitures);


        setAndSave(reportData, cutoff);
        setAndSave(reportData, angle);
        setAndSave(reportData, curveGlueing);
        setAndSave(reportData, directGlueing);
        setAndSave(reportData, patch);
        //todo will be saved when plastic cutData wil be saved
        //setAndSave(reportData, directSawCutData);
        reportData.setCommonDatas(directSawCutData);

        setAndSave(reportData, groove);
        setAndSave(reportData, milling);
        setAndSave(reportData, drilling);
    }

    private void setAndSave(CommonReportDataImpl reportData, CommonDatas<CommonData> commonDatas) {
        reportData.setCommonDatas(commonDatas);
        mainFacade.getCommonDataFacade().saveAll(commonDatas);
    }


    private void sort(List<CommonData> datas) {
        Collections.sort(datas);
        if (datas.size() > 0) {
            datas.get(datas.size() - 1).markAsLast();
        }
    }

    private void countPatch(List<CommonData> complexGlueing, OrderFurniture furniture) {
        if (furniture.isComplex() && furniture.isPrimary()) {
            double square = calcSquare(furniture.getLength() * furniture.getWidth() * furniture.getAmount());
            updateCommonDatas(complexGlueing, furniture.getComlexBoardDef(), ServiceType.patch, square);
        }
    }

    private void countGrooveSection(List<CommonData> grooveDatas, OrderFurniture furniture) {
        if (furniture.getGroove() != null && (!furniture.isComplex() || furniture.isPrimary())) {
            double size = recountFurnitureAmount(furniture, furniture.getGroove());
            updateCommonDatas(grooveDatas, furniture.isComplex() ? furniture.getComlexBoardDef() : furniture.getBoardDef(), ServiceType.groove, size);
        }
    }

    private void countAnlgeSection(List<CommonData> angle45Datas, OrderFurniture furniture) {
        if (furniture.getAngle45() != null && (!furniture.isComplex() || furniture.isPrimary())) {
            double size = recountFurnitureAmount(furniture, furniture.getAngle45());
            updateCommonDatas(angle45Datas, furniture.isComplex() ? furniture.getComlexBoardDef() : furniture.getBoardDef(), ServiceType.angle, size);
        }
    }

    private void countCutoffSection(List<CommonData> cutoffDatas, OrderFurniture furniture) {
        if (furniture.getCutoff() != null && (!furniture.isComplex() || furniture.isPrimary())) {
            Cutoff cutoff = (Cutoff) XstreamHelper.getInstance().fromXML(furniture.getCutoff());
            double length =
                    calcLinear(CutoffPainter.lengthCutoff(cutoff, new Dimension(furniture.getLength().intValue(), furniture.getWidth().intValue())) * furniture.getAmount());
            updateCommonDatas(cutoffDatas, furniture.isComplex() ? furniture.getComlexBoardDef() : furniture.getBoardDef(), ServiceType.cutoff, length);
        }
    }

    private void countDriliingSection(CommonDatas<CommonData> datas, OrderFurniture furniture) {
        if (furniture.getDrilling() != null && (!furniture.isComplex() || furniture.isPrimary())) {
            Drilling drilling = (Drilling) XstreamHelper.getInstance().fromXML(furniture.getDrilling());
            String value = drilling.getNote();
            if (StringUtils.isNumeric(value)) {
                updateCommonDatas(datas, furniture.isComplex() ? furniture.getComlexBoardDef() : furniture.getBoardDef(), ServiceType.drilling, Integer.valueOf(value));
            }
        }
    }

    private void countDirectGlueingSection(List<CommonData> glueingDatas, OrderFurniture furniture) {
        if (furniture.getGlueing() != null && furniture.isPrimary()) {

            Side[] sides = Side.values();
            GlueingSideHelper glueingSideHelper = null;
            for (Side side : sides) {
                glueingSideHelper = new GlueingSideHelper(furniture, side) {

                    @Override
                    public boolean isSide() {
                        return isGlueing();
                    }
                };
                if (glueingSideHelper.isSide()) {
                    updateCommonDatas(glueingDatas, glueingSideHelper.getBorderDef(), ServiceType.directGluing, glueingSideHelper.getSize());
                }
            }
        }
    }

    /**
     * Оклейка для деталей с фрезеровкой
     *
     * @param glueingDatas
     * @param furniture
     */
    private void countCurveGlueingSection(List<CommonData> glueingDatas, OrderFurniture furniture) {
        if (furniture.isPrimary() && furniture.getMilling() != null) {
            Milling milling = (Milling) XstreamHelper.getInstance().fromXML(furniture.getMilling());
            if (milling.getCurveGluingLength() > 0) {
                updateCommonDatas(glueingDatas, milling.getBorderDef(), ServiceType.curveGlueing,
                        ReportUtils.calcLinear((milling.getCurveGluingLength() + milling.getDirectGluingLength()) * furniture.getAmount()));
            }
        }
    }

    private ElementDrawing getElementDrawing(OrderFurniture furniture) {
        MillingConverter converter = new MillingConverter();
        return converter.getElementDrawing(furniture);
    }

    private void countMillingSection(List<CommonData> millingDatas, OrderFurniture furniture) {
        if (furniture.getMilling() != null && (!furniture.isComplex() || furniture.isPrimary())) {
            Milling milling = (Milling) XstreamHelper.getInstance().fromXML(furniture.getMilling());
            double size = ReportUtils.calcLinear(milling.getCurveLength()) * furniture.getAmount();
            BoardDef boardDef = furniture.isComplex() ? furniture.getComlexBoardDef() : furniture.getBoardDef();
            updateCommonDatas(millingDatas, boardDef, ServiceType.milling, size);
        }
    }


    private void increaseEachEntryByExtraPercent(Collection<CommonData> datas) {
        for (CommonData data : datas) {
            double count = data.getRoundCount();
            data.increase(count / 10d);
        }
    }

    private void updateCommonDatas(List<CommonData> datas, PriceAware priceAware, ServiceType serviceType, double size) {
        CommonData data = CommonData.valueOf(serviceType, priceAware);
        int i = datas.indexOf(data);
        data = i > -1 ? datas.remove(i) : data;

        if (data.getPrice() == null || data.getPrice() <= 0d) {
            PriceEntity price = mainFacade.getPriceFacade().getPrice(priceAware, serviceType);
            ReportUtils.fillPrice(data, price, order, mainFacade);
        }

        data.increase(size);
        datas.add(data);
    }

    private double recountFurnitureAmount(OrderFurniture furniture, String stringData) {
        DTO dto = (DTO) XstreamHelper.getInstance().fromXML(stringData);
        return recountFurnitureAmount(furniture, dto);
    }

    private double recountFurnitureAmount(OrderFurniture furniture, DTO dto) {
        long size = 0;
        size += dto.isDown() ? furniture.getLength() : 0;
        size += dto.isLeft() ? furniture.getWidth() : 0;
        size += dto.isRight() ? furniture.getWidth() : 0;
        size += dto.isUp() ? furniture.getLength() : 0;
        assert furniture.getAmount() != 0l;
        size *= furniture.getAmount();
        return calcLinear(size);
    }


    public static class GlueingCalc extends ElementDrawing.Calc {
        private HashMap<Gluieng, Double> map = new HashMap<Gluieng, Double>();

        @Override
        public double getValue(Figure figure) {
            if (figure instanceof GluiengLength) {
                Gluieng gluieng = figure.get(AttributeKeys.GLUEING);
                if (gluieng != null) {
                    Double value = map.get(gluieng.getBorderDef());
                    if (value == null) {
                        value = 0D;
                    }
                    value += ((GluiengLength) figure).getGluiengLength();
                    map.put(gluieng, value);
                }
            }
            return 0;
        }

        public Map<Gluieng, Double> getMap() {
            return map;
        }
    }
}
