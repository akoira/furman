package by.dak.cutting.agt.report;

import by.dak.cutting.afacade.report.AFacadeFurnitureDataConverter;
import by.dak.cutting.agt.AGTFacade;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonDataType;

/**
 * User: akoyro
 * Date: 10.08.2010
 * Time: 23:02:07
 */
public class AGTFurnitureDataConverter extends AFacadeFurnitureDataConverter<AGTFacade>
{
    public AGTFurnitureDataConverter(AOrder order, MainFacade mainFacade)
    {
        super(CommonDataType.agtfacade, order, mainFacade);
    }
}
