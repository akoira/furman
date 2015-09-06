package by.dak.persistence.dao;

import by.dak.persistence.entities.types.FurnitureType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureTypeDao extends GenericDao<FurnitureType>
{
    List<FurnitureType> findChildTypesBy(String keyword);
}
