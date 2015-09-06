package by.dak.buffer.entity.migrator.detail.furniture.code;

import by.dak.buffer.entity.migrator.detail.furniture.AFurnitureCodeMigrator;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.Service;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:41:12
 * To change this template use File | Settings | File Templates.
 */
public class ServiceMigrator extends AFurnitureCodeMigrator<Service>
{
    @Override
    public Service migrate(Priced priced)
    {
        /*Service service = new Service();
        service.setCode(priced.getCode());
        service.setGroupIdentifier(priced.getGroupIdentifier());
        service.setManufacturer(priced.getManufacturer());
        service.setName(priced.getName());
        service.setPricedType(priced.getPricedType());
        service.setPrices(priced.getPrices());
        service.setId(priced.getId());*/
        Service service = FacadeContext.getServiceFacade().findById(priced.getId(), true);

        return service;
    }
}
