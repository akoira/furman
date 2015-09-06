package by.dak.report.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.PdfFont;
import net.sf.jasperreports.engine.util.JRProperties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Koyro
 * @version 0.1 21.01.2009
 * @since 1.0.0
 */
@Deprecated
public class PDFReportGenerator
{
    private final static String USER_DIRECTORY = System.getProperty("user.dir");

    //Fonts
    private final static String ARIAL = USER_DIRECTORY + "\\reports\\fonts\\arial.ttf";
    private final static String ARIAL_ITALIC = USER_DIRECTORY + "\\reports\\fonts\\ariali.ttf";
    private final static String ARIAL_BOLD_ITALIC = USER_DIRECTORY + "\\reports\\fonts\\arialbi.ttf";
    private final static String ARIAL_BOLD = USER_DIRECTORY + "\\reports\\fonts\\arialbd.ttf";

    private Map<FontKey, PdfFont> fontMap = new HashMap<FontKey, PdfFont>();
    private static PDFReportGenerator instance = new PDFReportGenerator();

    private PDFReportGenerator()
    {
        JRProperties.setProperty(JRProperties.PDF_FONT_FILES_PREFIX + "Arial", ARIAL);
        JRProperties.setProperty(JRProperties.PDF_FONT_FILES_PREFIX + "ArialItalic", ARIAL_ITALIC);
        JRProperties.setProperty(JRProperties.PDF_FONT_FILES_PREFIX + "ArialBoldItalic", ARIAL_BOLD_ITALIC);
        JRProperties.setProperty(JRProperties.PDF_FONT_FILES_PREFIX + "ArialBold", ARIAL_BOLD);

        fontMap.put(new FontKey("Arial", false, false), new PdfFont("Arial", "Cp1251", false, false, false));
        fontMap.put(new FontKey("ArialBD", true, false), new PdfFont("ArialBold", "Cp1251", false, true, false));
        fontMap.put(new FontKey("ArialBI", true, true), new PdfFont("ArialBoldItalic", "Cp1251", false, true, true));
        fontMap.put(new FontKey("ArialI", false, true), new PdfFont("ArialItalic", "Cp1251", false, false, true));
    }

    public synchronized static PDFReportGenerator getInstance()
    {
        if (instance == null)
        {
            instance = new PDFReportGenerator();
        }
        return instance;
    }

    public JasperPrint compileGenerate(String definitionPath, String reportPath, Map<String, Object> parameters) throws ReportGenerationException
    {
        String pathPattern = getPathPattern(definitionPath);
        String jasperReportPath = pathPattern + "jasper";

        assert new File(definitionPath).exists() : "There is no corresponding jrxml file by path: " + jasperReportPath;
        try
        {
            JasperCompileManager.compileReportToFile(definitionPath, jasperReportPath);
            return generate(jasperReportPath, reportPath, parameters);
        }
        catch (JRException e)
        {
            throw new ReportGenerationException(e.getLocalizedMessage(), e);
        }
    }

    public JasperPrint generate(String jasperPath, String reportPath, Map<String, Object> parameters) throws ReportGenerationException
    {
        try
        {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath, parameters);
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, reportPath);
            exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
            exporter.exportReport();
            return jasperPrint;
        }
        catch (JRException e)
        {
            throw new ReportGenerationException(e.getLocalizedMessage(), e);
        }
    }

    private String getPathPattern(String definitionPath)
    {
        return definitionPath.substring(0, definitionPath.lastIndexOf(".") + 1);
    }
}