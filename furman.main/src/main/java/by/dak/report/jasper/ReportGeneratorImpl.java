package by.dak.report.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Koyro
 * @version 0.1 21.01.2009
 * @since 1.0.0
 */
public class ReportGeneratorImpl implements ReportGenerator
{
    private static ReportGeneratorImpl instance = new ReportGeneratorImpl();

    private ReportGeneratorImpl()
    {
    }

    public synchronized static ReportGeneratorImpl getInstance()
    {
        if (instance == null)
        {
            instance = new ReportGeneratorImpl();
        }
        return instance;
    }

    public void compile(String definitionPath, String jasperPath) throws JRException
    {
        String jasperReportPath = jasperPath;
        assert new File(definitionPath).exists() : "There is no corresponding definition file by path: " + definitionPath;

        if (jasperReportPath == null)
        {
            jasperReportPath = getPathPattern(definitionPath) + JASPER_EXT;
        }
        JasperCompileManager.compileReportToFile(definitionPath, jasperReportPath);
    }

    public JasperPrint generate(JReportData jReportData) throws JRException
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(JRParameter.REPORT_LOCALE, jReportData.getLocale());
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, jReportData.getResourceBundle());
        parameters.put(JRParameter.REPORT_DATA_SOURCE, jReportData.getJRDataSource());
        parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);

        fillAdditinalParams(parameters, jReportData.getAdditionalParams());
        JasperReport report = (JasperReport) JRLoader.loadObject(jReportData.getJasperReportPath());
        return JasperFillManager.fillReport(report, parameters);
    }

    private void fillAdditinalParams(Map<String, Object> parameters, Map<String, Object> additionalParams)
    {
        for (Map.Entry<String, Object> additional : additionalParams.entrySet())
        {
            parameters.put(additional.getKey(), additional.getValue());
        }
    }

    private String getPathPattern(String definitionPath)
    {
        return definitionPath.substring(0, definitionPath.lastIndexOf(DOT) + 1);
    }
}