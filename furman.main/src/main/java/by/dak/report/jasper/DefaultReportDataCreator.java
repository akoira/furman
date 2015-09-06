package by.dak.report.jasper;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Denis Koyro
 * @version 0.1 22.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DefaultReportDataCreator implements ReportDataCreator
{
    private Locale locale;
    private ResourceBundle resourceBundle;

    public DefaultReportDataCreator(String reportBundlePath)
    {
        this.locale = new Locale("ru");
        this.resourceBundle = ResourceBundle.getBundle(reportBundlePath, locale);
    }

    public JReportData create()
    {
        return null;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public ResourceBundle getResourceBundle()
    {
        return resourceBundle;
    }
}
