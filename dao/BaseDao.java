package edu.ahpu.boke.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    void setEntityClass(Class<?> entityClass);

    void save(T entity);

    void update(T entity);

    T findById(Serializable id);

    void deleteByIds(Serializable... ids);

    void deleteAll(Collection<T> entities);

    List<T> findByCondition(String whereHql, Object[] params, Map<String, String> orderBy, boolean cacheable);

    List<T> findByCondition(String whereHql, Object[] params, boolean cacheable);

    List<T> findByCondition(Map<String, String> orderby, boolean cacheable);

    List<T> findAll(boolean cacheable);

    T findFirstByCondition(String whereHql, Object[] params, boolean cacheable);

    List<T> findByConditionWithPaging(String whereHql, Object[] params, Map<String, String> orderBy, int offset, int length);

    int getRowCount(String whereHql, Object[] params);
}
