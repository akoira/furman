package by.dak.persistence.dao;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.Service;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceDao extends GenericDao<PriceEntity>
{

    PriceEntity findUniqueBy(PriceAware priceAware, Priced priced);

    List<PriceEntity> findAllBy(Service service);

}
