package by.dak.cutting.zfacade.report;

import by.dak.cutting.afacade.report.AFacadeReportDataCreator;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.persistence.entities.AOrder;

/**
 * User: akoyro
 * Date: 14.08.2010
 * Time: 16:38:40
 */
public class ZFacadeReportDataCreator extends AFacadeReportDataCreator<ZFacade>
{
    public ZFacadeReportDataCreator(AOrder order)
    {
        super(order);
    }
}
