package by.dak.plastic.jasper.converter;

import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.FacadeContext;
import by.dak.plastic.DSPPlasticDetail;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.converter.ADirectSawCutConverter;

import java.util.List;

import static by.dak.report.jasper.ReportUtils.calcLinear;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 28.09.11
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class DSPPlasticDirectSawCutConverter extends ADirectSawCutConverter<DSPPlasticDetail>
{
    public DSPPlasticDirectSawCutConverter(CuttingModel cuttingModel)
    {
        super(cuttingModel);
    }

    @Override
    public CommonDatas<CommonData> convert(List<DSPPlasticDetail> furnitures)
    {
        for (DSPPlasticDetail dspPlasticDetail : furnitures)
        {
            //добавлеяем обрезку
            if (FacadeContext.getDSPPlasticDetailFacade().isPlastic(dspPlasticDetail.getBoardDef()) && dspPlasticDetail.isPrimary())
            {
                updateDirectSawCut(dspPlasticDetail.getBoardDef(), calcLinear(
                        (dspPlasticDetail.getWidth() + dspPlasticDetail.getLength()) * 2));
            }
        }
        return super.convert(furnitures);
    }
}
