/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.report.jasper;

import by.dak.persistence.entities.AOrder;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author admin
 */
public abstract class OrderHeaderReportDataCreator extends ReportDataCreatorDecorator
{
    private AOrder order;

    public AOrder getOrder()
    {
        return order;
    }

    public OrderHeaderReportDataCreator(AOrder order)
    {
        super();
        setUnderlying(new DefaultReportDataCreator(getReportBundlesPath()));
        this.order = order;
    }

    public abstract URL getJasperReportPath();

    public abstract String getReportBundlesPath();

    public abstract Collection createCollection();

    /**
     * Create data for glueing process report
     */
    @Override
    public JReportData create()
    {
        ReportTitle reportTitle = new ReportTitle(order);
        HashMap<String, Object> parameters = reportTitle.getParameters();
        return new JReportDataImpl(new JRBeanCollectionDataSource(createCollection()), parameters, getJasperReportPath(), getResourceBundle(), getLocale());
    }
}
