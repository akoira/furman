package by.dak.report.jasper;

import net.sf.jasperreports.engine.ReportContext;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: Mar 18, 2009
 * Time: 7:01:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Constants
{
    public static final String EXT_JRXML = ".jrxml";
    public static final String EXT_JASPER = ".jasper";

    public static final ReportContext DUMMY_REPORT_CONTEXT = new ReportContext()
    {
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
