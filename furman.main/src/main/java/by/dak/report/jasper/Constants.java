package by.dak.report.jasper;

import by.dak.persistence.MainFacade;
import net.sf.jasperreports.engine.ReportContext;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Mar 18, 2009
 * Time: 7:01:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Constants
{
    String MAIN_FACADE = "MAIN_FACADE";
    public static final String EXT_JRXML = ".jrxml";
    public static final String EXT_JASPER = ".jasper";

    ReportContext DUMMY_REPORT_CONTEXT = new ReportContext()
    {
        @Override
        public Map<String, Object> getParameterValues() {
            return null;
        }

        @Override
        public Object removeParameterValue(String parameterName) {
            return null;
        }

        @Override
        public void clearParameterValues() {

        }

        @Override
        public String getId()
        {

            return null;
        }

        @Override
        public Object getParameterValue(String parameterName)
        {
            return null;
        }

        @Override
        public void setParameterValue(String parameterName, Object value)
        {
        }

        @Override
        public boolean containsParameter(String parameterName)
        {
            return false;
        }
    };
}
