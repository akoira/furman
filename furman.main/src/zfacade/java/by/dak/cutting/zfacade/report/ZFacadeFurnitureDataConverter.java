package by.dak.cutting.zfacade.report;

import by.dak.cutting.afacade.report.AFacadeFurnitureDataConverter;
import by.dak.cutting.zfacade.ZFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonDataType;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public class ZFacadeFurnitureDataConverter extends AFacadeFurnitureDataConverter<ZFacade>
{
    public ZFacadeFurnitureDataConverter(AOrder order)
    {
        super(CommonDataType.zfacade, order);
    }
}
