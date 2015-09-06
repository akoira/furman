package by.dak.report.model;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 11:34:26
 */
public interface ReportGenerator
{
    public JasperPrint getReport();
}
