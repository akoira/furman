package by.dak.report.statistics.data;

import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.DefaultReportDataCreator;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.JReportDataImpl;
import by.dak.report.jasper.ReportDataCreatorDecorator;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 20.10.11
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class StatisticOrdersReportDataCreator extends ReportDataCreatorDecorator
{
    private static final String JASPER_REPORT_PATH = "statistics.orders.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/report/statistics/data/statisticsOrdersReport";
    private List<Order> orders;

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public StatisticOrdersReportDataCreator()
    {
        setUnderlying(new DefaultReportDataCreator(REPORT_BUNDLES_PATH));
    }

    @Override
    public JReportData create()
    {
        URL definitionPath = StatisticOrdersReportDataCreator.class.getResource(JASPER_REPORT_PATH);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        return new JReportDataImpl(new JRBeanCollectionDataSource(createStatisticOrdersReportData()), parameters, definitionPath, getResourceBundle(), getLocale());
    }

    private List<StatisticOrdersReportData> createStatisticOrdersReportData()
    {
        List<StatisticOrdersReportData> reportDataList = new ArrayList<StatisticOrdersReportData>();

        for (Order order : getOrders())
        {
            StatisticOrdersReportData ordersReportData = new StatisticOrdersReportData();
            ordersReportData.setName(order.getName());
            AOrder.OrderNumber orderNumber = new AOrder.OrderNumber(order);
            ordersReportData.setNumber(orderNumber.convert(order.getNumber()));
            reportDataList.add(ordersReportData);
        }

        return reportDataList;
    }
}
