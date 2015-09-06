package by.dak.cutting.facade;

import by.dak.persistence.entities.Manufacturer;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ManufacturerFacade extends BaseFacade<Manufacturer>
{
    Manufacturer getDefault();
}
