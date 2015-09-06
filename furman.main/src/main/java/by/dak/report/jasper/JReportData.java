package by.dak.report.jasper;

import net.sf.jasperreports.engine.JRDataSource;

import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author dkoyro
 * @version 0.1 13.03.2009 13:12:40
 */
public interface JReportData
{
    JReportData UNKNOWN = new JReportData()
    {
        public JRDataSource getJRDataSource()
        {
            return null;
        }

        public Map<String, Object> getAdditionalParams()
        {
            return Collections.emptyMap();
        }

        public URL getJasperReportPath()
        {
            return null;
        }

        public ResourceBundle getResourceBundle()
        {
            return null;
        }

        public Locale getLocale()
        {
            return Locale.getDefault();
        }
    };

    JRDataSource getJRDataSource();

    Map<String, Object> getAdditionalParams();

    URL getJasperReportPath();

    ResourceBundle getResourceBundle();

    Locale getLocale();
}
