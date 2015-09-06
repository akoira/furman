package by.dak.cutting.afacade.report;

import by.dak.cutting.afacade.AFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.Constants;
import by.dak.report.jasper.JReportData;
import by.dak.report.jasper.JReportDataImpl;
import by.dak.report.jasper.ReportDataCreator;
import by.dak.utils.GenericUtils;
import net.sf.jasperreports.engine.JRParameter;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: akoyro
 * Date: 14.08.2010
 * Time: 16:38:40
 */
public abstract class AFacadeReportDataCreator<F extends AFacade> implements ReportDataCreator
{
    private Class<F> elementClass = GenericUtils.getParameterClass(getClass(), 0);

    private static final String JASPER_REPORT_PATH_SUFFIX = "Report.jasper";
    private AOrder order;

    public AFacadeReportDataCreator(AOrder order)
    {
        this.order = order;
    }

    @Override
    public Locale getLocale()
    {
        return null;
    }

    @Override
    public ResourceBundle getResourceBundle()
    {
        return null;
    }

    @Override
    public JReportData create()
    {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ORDER_ID", order.getId());
        parameters.put(JRParameter.REPORT_CONTEXT, Constants.DUMMY_REPORT_CONTEXT);
        JReportDataImpl reportData = new JReportDataImpl(null, parameters, this.getClass().getResource(getElementClass().getSimpleName() + JASPER_REPORT_PATH_SUFFIX), null, null);
        return reportData;
    }

    public Class<F> getElementClass()
    {
        return elementClass;
    }

    public void setElementClass(Class<F> elementClass)
    {
        this.elementClass = elementClass;
    }
}
