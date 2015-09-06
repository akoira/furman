package by.dak.report.jasper;

import by.dak.report.ReportType;

/**
 * @author Denis Koyro
 * @version 0.1 15.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public interface ReportCreatorFactory<O>
{
    ReportDataCreator createReportDataCreator(O reportObject, ReportType reportType);
}
