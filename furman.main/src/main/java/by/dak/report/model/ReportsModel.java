package by.dak.report.model;

import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.persistence.entities.AOrder;
import by.dak.report.ReportType;
import by.dak.report.jasper.JReportData;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.List;

/**
 * @author akoyro
 * @version 0.1 04.02.2009
 * @since 2.0.0
 */
public interface ReportsModel
{
    public CuttingModel getCuttingModel();

    AOrder getOrder();

    List<Report> getReports();

    public JasperPrint getJasperPrint(ReportType reportType);

    public void setJasperPrint(ReportType reportType, JasperPrint jasperPrint);

    public JReportData getReportData(ReportType reportType);

    public void setReportData(ReportType reportType, JReportData jReportData);


}
