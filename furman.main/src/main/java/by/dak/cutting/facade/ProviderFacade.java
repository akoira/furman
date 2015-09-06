package by.dak.cutting.facade;

import by.dak.persistence.entities.Provider;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProviderFacade extends BaseFacade<Provider>
{
}
