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

//�̳�Spring��Hibernate֧���࣬��Hibernate����Spring����
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    // ���Ͳ����ľ������ͣ���ʵ��������ͣ�
    private Class<?> entityClass = GenericClass.getGenericClass(this.getClass());

    // ע��Spring�����е�SessionFactoryʵ��
    @Resource(name = "sessionFactory")
    public void setSessionFactory4Spring(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    // ֱ�ӵ���Spring��Hibernate֧����ķ�����ʵ�������CRUD����
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    // ʹ��ע��ѹ�����Ͱ�ȫ����
    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        return (T) this.getHibernateTemplate().get(entityClass, id);
    }

    public void deleteByIds(Serializable... ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                Object entity = this.getHibernateTemplate().get(entityClass, id);
                // ָ�����������ܲ����ڶ�Ӧ��ʵ�����
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
        // Ϊ�����������صķ������ô˷�������ߴ��븴���ԣ���������һ����ԶΪ���������
        String hql = "select o from " + entityClass.getSimpleName() + " o where 1=1 ";
        if (StringUtils.isNotBlank(whereHql)) {
            hql = hql + whereHql;
        }
        // ���ݲ�������order by�ַ���
        String orderByStr = this.buildOrderBy(orderBy);
        hql = hql + orderByStr;

        final String _hql = hql;
        // ע��execute������ʵ��Ϊ�����ڲ���Ķ���
        List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(_hql);// ������ѯ����
                if (cacheable) {// �����Ƿ񻺴��ѯ���
                    query.setCacheable(true);
                }
                setParams(query, params);// ���ò�ѯ����
                return query.list();
            }
        });
        return list;
    }

    // ���ط���
    public List<T> findByCondition(String whereHql, Object[] params, boolean cacheable) {
        return this.findByCondition(whereHql, params, null, cacheable);
    }

    // ���ط���
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

    // ���ò�ѯ����
    private void setParams(Query query, Object[] params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

    // ����order by�ַ���
    private String buildOrderBy(Map<String, String> orderBy) {
        StringBuffer buf = new StringBuffer();
        if (orderBy != null && !orderBy.isEmpty()) {
            buf.append(" order by ");
            // ������������
            for (Map.Entry<String, String> em : orderBy.entrySet()) {
                buf.append(em.getKey() + " " + em.getValue() + ",");
            }
            // ȥ�����һ������
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
                query.setFirstResult(offset);// �����������ؽ����λ��
                query.setMaxResults(length);// ���÷��ؽ�����������
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
                // select count(*)�Ľ��ֻ��һ��
                return query.uniqueResult();
            }
        });
        return (int) count;
    }
}
