package by.dak.report.jasper.common.data;

import by.dak.persistence.entities.AOrder;

import java.util.List;

/**
 * @author akoyro
 * @version 0.1 28.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public interface CommonReportData
{
    public AOrder getOrder();

    public List<CommonData> getServicesData();

    public List<CommonData> getMaterialsData();

    public List<CommonData> getFurnitureData();

    public List<CommonData> getCommonDatas(CommonDataType commonDataType);

    public Double getCommonCost();
}
