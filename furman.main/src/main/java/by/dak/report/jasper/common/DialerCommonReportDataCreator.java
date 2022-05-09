package by.dak.report.jasper.common;

import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDataType;
import by.dak.report.jasper.common.data.CommonReportData;
import by.dak.report.jasper.common.data.CommonReportDataImpl;

import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 22.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public final class DialerCommonReportDataCreator extends CommonReportDataCreator
{
    public DialerCommonReportDataCreator(final CommonReportData commonReportData, MainFacade mainFacade)
    {
        super(new CommonReportData()
        {
            @Override
            public AOrder getOrder()
            {
                return commonReportData.getOrder();
            }

            @Override
            public List<CommonData> getServicesData()
            {
                return CommonReportDataImpl.convertForDialer(commonReportData.getServicesData());
            }

            @Override
            public List<CommonData> getMaterialsData()
            {
                return CommonReportDataImpl.convertForDialer(commonReportData.getMaterialsData());
            }

            @Override
            public List<CommonData> getFurnitureData()
            {
                return CommonReportDataImpl.convertForDialer(((CommonReportDataImpl) commonReportData).getDialerFurnitureData());
            }

            @Override
            public List<CommonData> getCommonDatas(CommonDataType commonDataType)
            {
                return CommonReportDataImpl.convertForDialer(commonReportData.getCommonDatas(commonDataType));
            }

            @Override
            public Double getCommonCost()
            {
                return ((CommonReportDataImpl) commonReportData).getDialerCommonCost();
            }
        }, mainFacade);
    }
}
