package by.dak.cutting.facade;

import by.dak.cutting.SearchFilter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
@Transactional
public interface BaseFacade<T>
{
    List<T> loadAll();

    List<T> loadAll(SearchFilter filter);

    void loadAll(SearchFilter filter, Callback<List<T>> callback);

    List<T> loadAll(SearchFilter filter, Class clazz);

    List<T> loadAllSortedBy(String field);

    List<T> loadAllSortedBy(String field, boolean asc);

    void merge(T entity);

    void save(T entity);

    @Deprecated
    void saveOrUpdate(T entity);

    void delete(T entity);

    void delete(Serializable id);

    Integer getCount();

    Integer getCount(SearchFilter searchFilter);

    void update(T entity);

    void refresh(T entity);

    T findBy(Long identity);

    T findById(Serializable id, boolean lock);

    T findUniqueByField(String fieldName, Object fieldValue);

    List<T> findAllByField(String fieldName, Object fieldValue);

    List<T> findAllByFieldIsNotNull(String... fields);

    boolean isUnique(T entity, String... property);

    List<T> findAllBy(T template);

    Integer getSum(SearchFilter searchFilter, String property);

    T getFirstBy(SearchFilter searchFilter);

}
