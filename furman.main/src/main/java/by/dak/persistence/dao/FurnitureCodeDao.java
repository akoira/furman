package by.dak.persistence.dao;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.types.FurnitureCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureCodeDao extends GenericDao<FurnitureCode>
{
    List<FurnitureCode> findBy(PriceAware priceAware);
}
