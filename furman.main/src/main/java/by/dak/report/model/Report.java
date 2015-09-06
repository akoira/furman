package by.dak.report.model;

import by.dak.report.ReportType;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * User: akoyro
 * Date: 16.03.2009
 * Time: 18:20:36
 */
public interface Report
{
    public JasperPrint getJasperPrint();

    public ReportType getReportType();
}
