package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.ServiceFacade;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.ServiceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: akoyro
 * Date: 17.06.2009
 * Time: 15:46:57
 */
public class ServiceFacadeImpl extends BaseFacadeImpl<Service> implements ServiceFacade
{
    @Override
    public List<Service> findAll(SearchFilter filter)
    {
        return dao.findAll(filter);
    }

    @Override
    public List<ServiceType> getAvailableServiceTypes()
    {
        List<Service> services = loadAll();
        List<ServiceType> allTypes = new ArrayList(Arrays.asList(ServiceType.values()));

        for (Service service : services)
        {
            allTypes.remove(service.getServiceType());
        }

        return allTypes;
    }
}
