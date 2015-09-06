package by.dak.report.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author Denis Koyro
 * @version 0.1 21.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public interface ReportGenerator
{
    String DOT = ".";
    String JASPER_EXT = "jasper";

    /**
     * Generate complete JasperPrint report with data representation, which is ready to print/view.
     *
     * @param jReportData is a Jasper report data value object.
     * @return complete JasperPrint report with data representation, which is ready to print/view.
     * @throws JRException throw if generation is failed.
     */
    JasperPrint generate(JReportData jReportData) throws JRException;

    /**
     * Service method. Need to compile report definition.
     *
     * @param definitionPath is a path to report definition file.
     * @param jasperPath     is a destination jasper report file path of compilation.
     * @throws JRException throw if compilation is failed by some reason.
     */
    public void compile(String definitionPath, String jasperPath) throws JRException;
}
