package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.ServicePlugTypeFacade;
import by.dak.persistence.entities.ServicePlugType;
import by.dak.persistence.entities.predefined.Unit;

public class ServicePlugTypeFacadeImpl extends BaseFacadeImpl<ServicePlugType> implements ServicePlugTypeFacade {

    @Override
    public ServicePlugType findByUnit(Unit unit) {
        return loadAll().stream().filter(x -> x.getUnit().equals(unit)).findFirst().orElse(null);
    }
}
