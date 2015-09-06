package by.dak.cutting.zfacade.report;

import by.dak.cutting.afacade.report.AFacadeServiceDataConverter;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.predefined.ServiceType;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public class ZFacadeServiceDataConverter extends AFacadeServiceDataConverter<ZFacade>
{
    public ZFacadeServiceDataConverter(AOrder order)
    {
        super(order);
    }

    @Override
    protected ServiceType getServiceType()
    {
        return ServiceType.zfacade;
    }
}
