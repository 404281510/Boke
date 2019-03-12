package edu.ahpu.boke.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.ahpu.boke.domain.User;
import edu.ahpu.boke.util.GenericClass;

//继承Spring的Hibernate支持类，将Hibernate交由Spring管理。
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    // 泛型参数的具体类型（即实体类的类型）
    private Class<?> entityClass = GenericClass.getGenericClass(this.getClass());

    // 注入Spring容器中的SessionFactory实例
    @Resource(name = "sessionFactory")
    public void setSessionFactory4Spring(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    // 直接调用Spring的Hibernate支持类的方法对实体对象做CRUD操作
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    // 使用注解压制类型安全警告
    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        return (T) this.getHibernateTemplate().get(entityClass, id);
    }

    public void deleteByIds(Serializable... ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                Object entity = this.getHibernateTemplate().get(entityClass, id);
                // 指定的主键可能不存在对应的实体对象
                if (entity != null) {
                    this.getHibernateTemplate().delete(entity);
                }
            }
        }
    }

    public void deleteAll(Collection<T> entities) {
        this.getHibernateTemplate().deleteAll(entities);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<T> findByCondition(String whereHql, final Object[] params, Map<String, String> orderBy, final boolean cacheable) {
        // 为方便其他重载的方法调用此方法（提高代码复用性），设置了一个永远为真的条件。
        String hql = "select o from " + entityClass.getSimpleName() + " o where 1=1 ";
        if (StringUtils.isNotBlank(whereHql)) {
            hql = hql + whereHql;
        }
        // 根据参数构造order by字符串
        String orderByStr = this.buildOrderBy(orderBy);
        hql = hql + orderByStr;

        final String _hql = hql;
        // 注意execute方法的实参为匿名内部类的对象
        List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(_hql);// 创建查询对象
                if (cacheable) {// 设置是否缓存查询结果
                    query.setCacheable(true);
                }
                setParams(query, params);// 设置查询参数
                return query.list();
            }
        });
        return list;
    }

    // 重载方法
    public List<T> findByCondition(String whereHql, Object[] params, boolean cacheable) {
        return this.findByCondition(whereHql, params, null, cacheable);
    }

    // 重载方法
    public List<T> findByCondition(Map<String, String> orderBy, boolean cacheable) {
        return this.findByCondition(null, null, orderBy, cacheable);
    }

    public List<T> findAll(boolean cacheable) {
        return this.findByCondition(null, null, null, cacheable);
    }

    public T findFirstByCondition(String whereHql, Object[] params, boolean cacheable) {
        List<T> list = this.findByCondition(whereHql, params, cacheable);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    // 设置查询参数
    private void setParams(Query query, Object[] params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

    // 构造order by字符串
    private String buildOrderBy(Map<String, String> orderBy) {
        StringBuffer buf = new StringBuffer();
        if (orderBy != null && !orderBy.isEmpty()) {
            buf.append(" order by ");
            // 迭代排序条件
            for (Map.Entry<String, String> em : orderBy.entrySet()) {
                buf.append(em.getKey() + " " + em.getValue() + ",");
            }
            // 去掉最后一个逗号
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<T> findByConditionWithPaging(String whereHql, final Object[] params, Map<String, String> orderBy, final int offset, final int length) {
        String hql = "select o from " + entityClass.getSimpleName() + " o where 1=1 ";

        if (StringUtils.isNotBlank(whereHql)) {
            hql = hql + whereHql;
        }
        String orderByStr = buildOrderBy(orderBy);
        hql = hql + orderByStr;

        final String _hql = hql;
        List<T> list = this.getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(_hql);
                query.setFirstResult(offset);// 设置首条返回结果的位置
                query.setMaxResults(length);// 设置返回结果的最大数量
                setParams(query, params);
                return query.list();
            }
        });
        return list;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int getRowCount(String whereHql, final Object[] params) {
        String hql = "select count(*) from " + entityClass.getSimpleName() + " o where 1=1 ";

        if (StringUtils.isNotBlank(whereHql)) {
            hql = hql + whereHql;
        }

        final String _hql = hql;
        long count = this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(_hql);
                setParams(query, params);
                // select count(*)的结果只有一个
                return query.uniqueResult();
            }
        });
        return (int) count;
    }
}
