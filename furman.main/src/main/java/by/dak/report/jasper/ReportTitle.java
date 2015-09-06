package by.dak.report.jasper;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 28.09.2009
 * Time: 16:08:29
 * To change this template use File | Settings | File Templates.
 */
public class ReportTitle
{
    private static final String ORDER_NUMBER = "orderNumber";
    private static final String CUSTOMER = "customer";
    private static final String DEADLINE = "deadline";
    private HashMap<String, Object> parameters;

    public ReportTitle(AOrder order)
    {
        parameters = new HashMap<String, Object>();

        parameters.put(ORDER_NUMBER, FacadeContext.getOrderFacadeBy(order.getClass()).parseOrderNumber(order));
        parameters.put(CUSTOMER, order.getCustomer().getName());
        parameters.put(DEADLINE, ReportUtils.getReadyDateForReport(order.getReadyDate()));
    }

    public HashMap<String, Object> getParameters()
    {
        return parameters;
    }
}
