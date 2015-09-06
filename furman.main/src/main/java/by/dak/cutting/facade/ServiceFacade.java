package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.Service;
import by.dak.persistence.entities.predefined.ServiceType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ServiceFacade extends BaseFacade<Service>
{
    List<Service> findAll(final SearchFilter filter);

    List<ServiceType> getAvailableServiceTypes();

}
