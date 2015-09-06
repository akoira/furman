package by.dak.persistence.cutting.dao;

import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.entities.AOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface ABoardStripsDao<S extends ABoardStripsEntity> extends AStripsDao<S>
{
    void deleteAll(AOrder order);
}
