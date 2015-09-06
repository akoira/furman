package by.dak.cutting.facade;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.Priced;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PricedFacade<T extends Priced> extends BaseFacade<T>
{
    public List<T> findBy(PriceAware priceAware);
}
