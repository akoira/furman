package by.dak.cutting.doors.jasper.common;

import by.dak.cutting.doors.jasper.common.reportData.DoorsDataReport;
import by.dak.persistence.entities.Order;
import by.dak.report.jasper.*;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 28.09.2009
 * Time: 16:19:50
 * To change this template use File | Settings | File Templates.
 */
public class CommonReportDoorsDataCreator extends ReportDataCreatorDecorator
{
    private static final String JASPER_REPORT_PATH = "CommonReportDoors.jasper";
    private static final String REPORT_BUNDLES_PATH = "by/dak/cutting/doors/jasper/common/commonReport";
    private DoorsDataReport doorsDataReport;

    public CommonReportDoorsDataCreator(DoorsDataReport doorsDataReport)
    {
        super(new DefaultReportDataCreator(REPORT_BUNDLES_PATH));
        this.doorsDataReport = doorsDataReport;
    }

    @Override
    public JReportData create()
    {
        Order order = doorsDataReport.getOrder();

        ReportTitle reportTitle = new ReportTitle(order);
        HashMap<String, Object> parameters = reportTitle.getParameters();
        parameters.put(JRParameter.REPORT_LOCALE, getLocale());
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, getResourceBundle());
        parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

        URL definitionPath = CommonReportDoorsDataCreator.class.getResource(JASPER_REPORT_PATH);
        return new JReportDataImpl(new JRBeanCollectionDataSource(doorsDataReport.getMainAggregatedData()), parameters, definitionPath, getResourceBundle(), getLocale());
    }
}
