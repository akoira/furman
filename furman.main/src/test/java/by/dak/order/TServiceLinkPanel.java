package by.dak.order;


import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.ServiceType;

import java.util.List;

import static by.dak.persistence.FacadeContext.getFacadeBy;

public class TServiceLinkPanel {
    public static void main(String[] args) {
        new SpringConfiguration();

        List<Service> services = FacadeContext.getServiceFacade().loadAll();

        ServiceType serviceType = services.get(0).getServiceType();

        MaterialType materialType = serviceType.getMaterialType();

        Class<? extends PriceAware> priceAwareClass = materialType.priceAwareClass();

        List furnitureTypes = getFacadeBy(priceAwareClass).loadAll();

    }
}
