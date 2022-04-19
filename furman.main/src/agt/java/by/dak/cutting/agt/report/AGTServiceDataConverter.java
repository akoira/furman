package by.dak.cutting.agt.report;

import by.dak.cutting.afacade.report.AFacadeServiceDataConverter;
import by.dak.cutting.agt.AGTFacade;
import by.dak.persistence.MainFacade;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.predefined.ServiceType;

/**
 * User: akoyro
 * Date: 07.09.2010
 * Time: 9:26:34
 */
public final class AGTServiceDataConverter extends AFacadeServiceDataConverter<AGTFacade> {

    public AGTServiceDataConverter(AOrder order, MainFacade mainFacade) {
        super(order, mainFacade);
    }

    @Override
    protected ServiceType getServiceType() {
        return ServiceType.agtfacade;
    }
}
