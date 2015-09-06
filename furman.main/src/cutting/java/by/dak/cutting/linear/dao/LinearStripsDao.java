package by.dak.cutting.linear.dao;

import by.dak.cutting.linear.entity.LinearStripsEntity;
import by.dak.persistence.cutting.dao.AStripsDao;
import org.springframework.stereotype.Repository;

@Repository
public interface LinearStripsDao extends AStripsDao<LinearStripsEntity>
{
}
