package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.dao.GenericDao;
import by.dak.persistence.entities.PersistenceEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public abstract class BaseFacadeImpl<T extends PersistenceEntity> implements BaseFacade<T>
{
    protected GenericDao<T> dao;

    public void setDao(GenericDao<T> dao)
    {
        this.dao = dao;
    }

    public GenericDao<T> getDao()
    {
        return dao;
    }

    @Override
    public T findBy(Long identity)
    {
        return dao.findById(identity, false);
    }

    @Override
    public T findById(Serializable id, boolean lock)
    {
        return dao.findById(id, lock);
    }

    @Override
    public T findUniqueByField(String fieldName, Object fieldValue)
    {
        return dao.findUniqueByField(fieldName, fieldValue);
    }

    @Override
    public List<T> findAllByFieldIsNotNull(String... fields)
    {
        return dao.findAllByFieldIsNotNull(fields);
    }


    @Override
    public List<T> findAllByField(String fieldName, Object fieldValue)
    {
        return dao.findAllByField(fieldName, fieldValue);
    }

    @Override
    public List<T> loadAll()
    {
        return dao.findAll();
    }

    @Override
    public List<T> loadAll(SearchFilter filter)
    {
        return dao.findAll(filter);
    }

    @Override
    public List<T> loadAll(SearchFilter filter, Class clazz)
    {
        return dao.findAll(filter, clazz);
    }

    @Override
    public List<T> loadAllSortedBy(String field)
    {
        return loadAllSortedBy(field, true);
    }

    @Override
    public List<T> loadAllSortedBy(String field, boolean asc)
    {
        return dao.findAllSortedBy(field, asc);
    }

    @Override
    public void merge(T entity)
    {
        dao.merge(entity);
    }

    @Override
    public void delete(T entity)
    {
        dao.delete(entity, false);
    }

    @Override
    public void delete(Serializable id)
    {
        dao.delete(id, false);
    }

    @Override
    public Integer getCount()
    {
        return dao.getCount();
    }

    @Override
    public Integer getCount(SearchFilter searchFilter)
    {
        return dao.getCount(searchFilter);
    }


    @Override
    public void save(T entity)
    {
        dao.save(entity);
    }

    @Override
    public void saveOrUpdate(T entity)
    {
        dao.saveOrUpdate(entity);
    }

    @Override
    public void update(T entity)
    {
        dao.update(entity);
    }

    @Override
    public void refresh(T entity)
    {
        if (entity.hasId())
            dao.refresh(entity);
    }


    @Override
    public boolean isUnique(T entity, String... property)
    {
        return dao.isUnique(entity, property);
    }

    @Override
    public List<T> findAllBy(T template)
    {
        return getDao().findAllBy(template);
    }

    public Integer getSum(SearchFilter searchFilter, String property)
    {
        return getDao().getSum(searchFilter, property);
    }

    @Override
    public T getFirstBy(SearchFilter searchFilter)
    {
        List<T> list = loadAll(searchFilter);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void loadAll(SearchFilter filter, Callback<List<T>> callback)
    {
        int count = getCount(filter);
        int index = 0;

        while (count > index)
        {
            SearchFilter searchFilter = filter.clone();
            searchFilter.setStartIndex(index);
            searchFilter.setResultSize(searchFilter.getPageSize());
            List<T> list = loadAll(searchFilter);

            callback.callback(list);

            index += searchFilter.getResultSize();
        }
    }
}
