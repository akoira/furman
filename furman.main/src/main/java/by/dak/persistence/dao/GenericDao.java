package by.dak.persistence.dao;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.NamedQueryDefinition;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author dkoyro
 * @version 0.1 26.09.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 2.0.0
 */
@Repository
public interface GenericDao<T> {
	public static final String PROP_ORDER_ID = "id";

	Integer getCount(SearchFilter searchFilter);

	T findById(Serializable id, boolean lock);

	List<T> findAll();

	List<T> findAll(SearchFilter filter);

	List<T> findAll(final SearchFilter filter, final Class clazz);

	List<T> findAllSortedBy(String fieldName, boolean asc);

	T save(T entity);

	void saveOrUpdate(T entity);

	Integer getCount();

	void delete(T entity, boolean markOnly);

	void delete(Serializable id, boolean markOnly);

	void merge(T entity);

	void update(T entity);

	void refresh(T entity);

	List findByClass(Class clazz);

	T findUniqueByField(String fieldName, Object fieldValue);

	List<T> findAllByField(String fieldName, Object fieldValue);

	List<T> findAllByFieldIsNotNull(String... fields);

	boolean isUnique(T entity, String... property);

	List<T> findAllBy(T template);

	Integer getSum(SearchFilter searchFilter, String property);

	List findAllBy(NamedQueryDefinition namedQueryDefinition);

}
