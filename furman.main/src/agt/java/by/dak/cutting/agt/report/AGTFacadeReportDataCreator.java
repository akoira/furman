package by.dak.cutting.agt.report;

import by.dak.cutting.afacade.report.AFacadeReportDataCreator;
import by.dak.cutting.agt.AGTFacade;
import by.dak.persistence.entities.AOrder;

/**
 * User: akoyro
 * Date: 14.08.2010
 * Time: 16:38:40
 */
public class AGTFacadeReportDataCreator extends AFacadeReportDataCreator<AGTFacade>
{
    public AGTFacadeReportDataCreator(AOrder order)
    {
        super(order);
    }
}
