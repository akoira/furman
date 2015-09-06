package by.dak.report.jasper;

import by.dak.utils.Creator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Denis Koyro
 * @version 0.1 15.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public interface ReportDataCreator extends Creator<JReportData>
{
    ReportDataCreator UNKNOWN = new ReportDataCreator()
    {
        public JReportData create()
        {
            return JReportData.UNKNOWN;
        }

        public Locale getLocale()
        {
            return Locale.getDefault();
        }

        public ResourceBundle getResourceBundle()
        {
            return null;
        }
    };

    Locale getLocale();

    ResourceBundle getResourceBundle();
}
