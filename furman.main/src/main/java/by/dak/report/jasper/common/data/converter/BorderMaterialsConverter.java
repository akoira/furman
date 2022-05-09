package by.dak.report.jasper.common.data.converter;

import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.Side;
import by.dak.report.jasper.ReportUtils;
import by.dak.report.jasper.common.data.*;
import by.dak.utils.convert.Converter;
import by.dak.utils.convert.ListConverter;

import java.util.*;

/**
 * @author Denis Koyro
 * @version 0.1 29.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 * todo должен быть перенесен в более общий пакет
 */
public class BorderMaterialsConverter implements Converter<List<OrderFurniture>, CommonDatas<CommonData>> {
    private MainFacade mainFacade;
    private SortedMap<String, CommonDatas<CommonData>> borderMaterials = new TreeMap<String, CommonDatas<CommonData>>(new StringComparator());

    private String MATERIAL_DATA = ResourceBundle.getBundle("by/dak/report/jasper/common/commonReport").getString("material.data");
    private AOrder order;

    public BorderMaterialsConverter(AOrder order, MainFacade mainFacade) {
        this.order = order;
        this.mainFacade = mainFacade;
    }

    @Override
    public CommonDatas<CommonData> convert(List<OrderFurniture> furnitures) {
        for (OrderFurniture furniture : furnitures) {
            calculateBorderMaterials(furniture);
        }
        List<CommonData> borderMaterials = sortBorderMaterials();
        borderMaterials = new ListConverter<CommonData, CommonData>(new IncreaseExtraPercentConverter()).convert(borderMaterials);

        CommonDatas<CommonData> result = new CommonDatas<CommonData>(CommonDataType.border, order);
        result.addAll(borderMaterials);
        return result;
    }

    private void calculateBorderMaterials(OrderFurniture furniture) {
        if (furniture.getMilling() != null && furniture.isPrimary()) {
            Milling milling = (Milling) XstreamHelper.getInstance().fromXML(furniture.getMilling());
            if (milling.getCurveGluingLength() > 0 || milling.getDirectGluingLength() > 0) {

                //curveLength + directLength + 10%*directLength + 150
                updateBorderMaterial(milling.getTexture(), milling.getBorderDef(), ReportUtils.calcLinear(milling.getCurveGluingLength()
                        + milling.getDirectGluingLength()
                        + (milling.getCurveGluingLength() > 0 ? FacadeContext.getBorderFacade().getCurveGluingAdditionalLength() : 0)
                ) * furniture.getAmount());
            }
        }

        if (furniture.getGlueing() != null && furniture.isPrimary()) {
            Side[] sides = Side.values();
            for (Side side : sides) {
                GlueingSideHelper glueingSideHelper = new GlueingSideHelper(furniture, side) {

                    @Override
                    public boolean isSide() {
                        return super.isGlueing();
                    }
                };
                if (glueingSideHelper.isSide()) {
                    updateBorderMaterial(glueingSideHelper.getTexture(), glueingSideHelper.getBorderDef(), glueingSideHelper.getSize());
                }
            }
        }
    }

    private void updateBorderMaterial(TextureEntity texture, BorderDefEntity borderDef,
                                      double size) {
        if (texture != null && borderDef != null) {
            CommonDatas<CommonData> datas = borderMaterials.get(borderDef.getName());
            if (datas == null) {
                datas = new CommonDatas<CommonData>(CommonDataType.border, order);
            }

            CommonData data = new BorderCommonData(borderDef, texture);
            int i = datas.indexOf(data);
            data = i > -1 ? datas.remove(i) : data;
            data.increase(size);

            if (data.isEmptyPrice()) {
                PriceEntity price = mainFacade.getPriceFacade().findUniqueBy(borderDef, texture);
                ReportUtils.fillPrice(data, price, order, mainFacade);
            }
            datas.add(data);
            borderMaterials.put(borderDef.getName(), datas);
        }
    }

    private CommonDatas<CommonData> sortBorderMaterials() {
        CommonDatas<CommonData> sorted = new CommonDatas<CommonData>(CommonDataType.border, order);
        for (String borderDefKey : borderMaterials.keySet()) {
            CommonDatas<CommonData> materials = borderMaterials.get(borderDefKey);
            Collections.sort(materials);
            materials.get(materials.size() - 1).markAsLast();
            sorted.addAll(materials);
        }
        return sorted;
    }
}