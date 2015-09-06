package by.dak.persistence.dao.impl;

import by.dak.cutting.CriteriaFiller;
import by.dak.cutting.SearchFilter;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.dao.GenericDao;
import by.dak.persistence.entities.PersistenceEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * @author dkoyro
 * @version 0.1 26.09.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 2.0.0
 */
public class GenericDaoImpl<T extends PersistenceEntity> implements GenericDao<T>
{

    private SessionFactory sessionFactory;

    private Class<T> persistentClass;

    public GenericDaoImpl()
    {
    }

    public Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public Class<T> getPersistentClass()
    {
        if (persistentClass == null)
        {
            persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T findById(Serializable id, boolean lock)
    {
        T entity;
        if (lock)
        {
            entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
        }
        else
        {
            entity = (T) getSession().load(getPersistentClass(), id);
        }

        return entity;
    }

    @Override
    public List<T> findAll()
    {
        return findByClass(getPersistentClass());
    }

    @Override
    public List<T> findAll(final SearchFilter filter)
    {
        Criteria execCriteria = createCriteria(getPersistentClass());
        fillFilter(execCriteria, filter);
        return execCriteria.list();
    }

    @Override
    public List<T> findAll(final SearchFilter filter, final Class clazz)
    {
        Criteria execCriteria = createCriteria(clazz);
        fillFilter(execCriteria, filter);
        return execCriteria.list();
    }

    @Override
    public List<T> findAllSortedBy(String fieldName, boolean asc)
    {
        Criteria execCriteria = createCriteria(getPersistentClass());
        return execCriteria.addOrder(asc ? Order.asc(fieldName) : Order.desc(fieldName)).list();
    }

    protected void fillFilter(Criteria execCriteria, SearchFilter filter)
    {
        CriteriaFiller filler = new CriteriaFiller(execCriteria, filter);
        filler.fill();
    }


    @Override
    public Integer getCount()
    {
        Criteria criteria = createCriteria(getPersistentClass());
        criteria.setProjection(Projections.rowCount());
        Long result = (Long) criteria.uniqueResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Integer getCount(SearchFilter searchFilter)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        CriteriaFiller filler = new CriteriaFiller(criteria, searchFilter);
        filler.fillForCount();
        criteria.setProjection(Projections.rowCount());
        Long result = (Long) criteria.uniqueResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public Integer getSum(SearchFilter searchFilter, String property)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        CriteriaFiller filler = new CriteriaFiller(criteria, searchFilter);
        filler.fillForCount();
        criteria.setProjection(Projections.sum(property));
        Long result = (Long) criteria.uniqueResult();
        return result != null ? result.intValue() : 0;
    }

    private void updateDates(T entity)
    {
        entity.setModified(new Date());
        if (entity.getCreated() == null)
            entity.setCreated(entity.getModified());
    }


    @Override
    public T save(T entity)
    {
        updateDates(entity);
        getSession().saveOrUpdate(entity);
        flush();
        return entity;
    }

    @Override
    public void saveOrUpdate(T entity)
    {
        updateDates(entity);
        getSession().saveOrUpdate(entity);
        flush();
    }

    @Override
    public void delete(T entity, boolean markOnly)
    {
        if (markOnly)
        {
            entity.setModified(new Date());
            entity.setDeleted(true);
            saveOrUpdate(entity);
        }
        else
        {
            getSession().delete(entity);
            flush();
        }

    }

    @Override
    public void delete(Serializable id, boolean markOnly)
    {
        T g = findById(id, true);
        if (g != null)
        {
            delete(g, markOnly);
        }
    }

    protected void flush()
    {
        getSession().flush();
    }

    protected void clear()
    {
        getSession().clear();
    }

    @Override
    public List<T> findByClass(Class clazz)
    {
        return createCriteria(clazz).list();
    }

    protected Criteria createCriteria(Class clazz)
    {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(PersistenceEntity.PROPERTY_deleted, false));
        return criteria;

    }

    protected Object findUniqueByCriteria(Criterion... criterion)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        return findUniqueByCriteria(criteria, criterion);
    }

    protected Object findUniqueByCriteria(Criteria criteria, Criterion... criterion)
    {
        for (Criterion c : criterion)
        {
            criteria.add(c);
        }
        return criteria.uniqueResult();
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    protected List<T> findByCriteria(Criterion... criterion)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        for (Criterion c : criterion)
        {
            criteria.add(c);
        }
        return criteria.list();
    }

    @Override
    public void merge(T entity)
    {
        getSession().merge(entity);
    }

    @Override
    public void update(T entity)
    {
        getSession().update(entity);
    }

    @Override
    public T findUniqueByField(String fieldName, Object fieldValue)
    {
        List<T> temp = findByCriteria(eq(fieldName, fieldValue));
        if (temp.size() > 0)
        {
            return temp.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<T> findAllByField(String fieldName, Object fieldValue)
    {
        return findByCriteria(eq(fieldName, fieldValue));
    }

    @Override
    public List<T> findAllByFieldIsNotNull(String... fields)
    {
        Criterion[] criterions = new Criterion[fields.length];
        for (int i = 0; i < fields.length; i++)
        {
            String field = fields[i];
            criterions[i] = isNotNull(field);
        }
        return findByCriteria(criterions);
    }

    @Override
    public void refresh(T entity)
    {
        getSession().refresh(entity);
    }


    @Override
    public boolean isUnique(T entity, String... property)
    {
        try
        {
            Criteria criteria = createCriteria(getPersistentClass());
            for (String s : property)
            {
                Object value = PropertyUtils.getProperty(entity, s);
                criteria.add(eq(s, value));
            }
            if (entity.hasId())
            {
                criteria.add(ne("id", entity.getId()));
            }
            List<T> result = criteria.list();
            return result == null || result.size() == 0;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    protected Criteria createCriteria(T template)
    {
        Criteria criteria = createCriteria(getPersistentClass());
        criteria.add(Example.create(template));
        return criteria;
    }

    @Override
    public List<T> findAllBy(T template)
    {
        return createCriteria(template).list();
    }

    public List findAllBy(NamedQueryDefinition namedQueryDefinition)
    {
        Query q = getSession().getNamedQuery(namedQueryDefinition.getNameQuery());
        namedQueryDefinition.fillQuery(q);
        return q.list();
    }


    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
}
