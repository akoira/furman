package by.dak.report.model.impl;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.entities.AOrder;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import by.dak.report.model.Report;
import by.dak.report.model.ReportsModel;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValue;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.application.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author akoyro
 * @version 0.1 08.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class ReportsModelImpl implements ReportsModel
{

    private AOrder order;

    private HashMap<ReportType, JReportData> reportDatas = new HashMap<ReportType, JReportData>();
    private HashMap<ReportType, JasperPrint> jasperPrints = new HashMap<ReportType, JasperPrint>();

    private CuttingModel cuttingModel;

    public CuttingModel getCuttingModel()
    {
        return cuttingModel;
    }

    public void setCuttingModel(CuttingModel cuttingModel)
    {
        this.cuttingModel = cuttingModel;
    }

    @Override
    public AOrder getOrder()
    {
        return order;
    }


    public void setOrder(AOrder order)
    {
        this.order = order;
    }

    public List<ReportType> getReportTypes()
    {
        ArrayList<ReportType> reportTypes = new ArrayList<ReportType>();
        reportTypes.add(ReportType.production_common);
        reportTypes.add(ReportType.common);
        reportTypes.add(ReportType.cutting);
        reportTypes.add(ReportType.cutoff);
        reportTypes.add(ReportType.milling);
        reportTypes.add(ReportType.glueing);
        reportTypes.add(ReportType.store);
        reportTypes.add(ReportType.zfacade);
        reportTypes.add(ReportType.agtfacade);
        reportTypes.add(ReportType.cutting_dsp_plastic);
        return reportTypes;
    }

    @Override
    public List<Report> getReports()
    {
        ArrayList<Report> reports = new ArrayList<Report>();
        List<ReportType> reportTypes = getReportTypes();
        for (ReportType reportType : reportTypes)
        {
            if (reportType != ReportType.production_common ||
                    CuttingApp.getApplication().getPermissionManager().checkPermission("ProductionPrice", false))
            {
                JasperPrint jasperPrint = getJasperPrint(reportType);
                if (jasperPrint != null && jasperPrint.getPages() != null && jasperPrint.getPages().size() > 0)
                {
                    reports.add(new ReportImpl(jasperPrint, reportType));
                }
            }
        }
        return reports;
    }

    public synchronized JasperPrint getJasperPrint(ReportType reportType)
    {
        return jasperPrints.get(reportType);
    }


    public synchronized void setJasperPrint(ReportType reportType, JasperPrint jasperPrint)
    {
        jasperPrints.put(reportType, jasperPrint);
    }

    public synchronized JReportData getReportData(ReportType reportType)
    {
        return reportDatas.get(reportType);
    }

    public synchronized void setReportData(ReportType reportType, JReportData jReportData)
    {
        reportDatas.put(reportType, jReportData);
    }

    @StringValue(converterClass = Report2StringConverter.class)
    public static class ReportImpl implements Report
    {

        private JasperPrint print;

        private ReportType type;

        public ReportImpl(JasperPrint print, ReportType type)
        {
            this.print = print;
            this.type = type;
        }

        @Override
        public JasperPrint getJasperPrint()
        {
            return print;
        }

        @Override
        public ReportType getReportType()
        {
            return type;
        }
    }

    public static class Report2StringConverter implements EntityToStringConverter<Report>
    {

        @Override
        public String convert(Report report)
        {
            return Application.getInstance().getContext().getResourceMap().getString("Application.report.name." + report.getReportType().name());
        }
    }

}
