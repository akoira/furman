package by.dak.cutting.facade;

import by.dak.persistence.entities.PriceAware;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PriceAwareFacade<T extends PriceAware> extends BaseFacade<T>
{
}
