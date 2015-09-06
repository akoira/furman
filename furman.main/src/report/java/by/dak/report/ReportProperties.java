package by.dak.report;

import org.jdesktop.beans.AbstractBean;

/**
 * User: akoyro
 * Date: 20.04.11
 * Time: 14:12
 */
public class ReportProperties extends AbstractBean
{
    public static final String PROPERTY_priceReportType = "priceReportType";

    private PriceReportType priceReportType;

    public PriceReportType getPriceReportType()
    {
        return priceReportType;
    }

    public void setPriceReportType(PriceReportType priceReportType)
    {
        this.priceReportType = priceReportType;
    }
}
