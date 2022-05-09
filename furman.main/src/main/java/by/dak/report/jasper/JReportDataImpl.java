package by.dak.report.jasper;

import net.sf.jasperreports.engine.JRDataSource;

import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author dkoyro
 * @version 0.1 13.03.2009 13:25:46
 */
public final class JReportDataImpl implements JReportData
{
    private final JRDataSource dataSource;
    private final Map<String, Object> additionalParams;
    private final ResourceBundle resourceBundle;
    private final Locale locale;
    private final URL jasperReportPath;

    public JReportDataImpl(JRDataSource dataSource, Map<String, Object> additionalParams, URL jasperReportPath,
                           ResourceBundle resourceBundle, Locale locale)
    {
        this.dataSource = dataSource;
        this.additionalParams = additionalParams;
        this.jasperReportPath = jasperReportPath;
        this.resourceBundle = resourceBundle;
        this.locale = locale;
    }

    public JRDataSource getJRDataSource()
    {
        return dataSource;
    }

    public Map<String, Object> getAdditionalParams()
    {
        return additionalParams;
    }

    public URL getJasperReportPath()
    {
        return jasperReportPath;
    }

    public ResourceBundle getResourceBundle()
    {
        return resourceBundle;
    }

    public Locale getLocale()
    {
        return locale;
    }
}
