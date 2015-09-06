package by.dak.category.dao;

import by.dak.category.Category;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends GenericDao<Category>
{
}
