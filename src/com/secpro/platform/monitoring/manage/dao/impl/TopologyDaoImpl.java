package com.secpro.platform.monitoring.manage.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.TopologyDao;
import com.secpro.platform.monitoring.manage.util.Assert;

@Repository("TopologyDaoImpl")
public class TopologyDaoImpl extends BaseDao implements TopologyDao {
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Object> queryBySql(String sql, int pageSize, int pageNo) {
		Session session = null;
		int firstRow = pageSize * (pageNo - 1);
		try {
			session = this.getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.setFirstResult(firstRow);
			query.setMaxResults(pageSize);
			return query.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	/**
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object findById(Class<?> clazz, String id) {
		if (Assert.isEmptyString(id) == true) {
			return null;
		}
		Session session = null;
		try {
			session = this.getSessionFactory().openSession();
			return session.get(clazz, id);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public <T extends Object> List<T> findAll(Class<?> clazz, String whereSql, int pageNo, int pageSize, String orderKey) {
		Session session = null;
		try {
			int firstRow = pageSize * (pageNo - 1);
			session = this.getSessionFactory().openSession();
			Criteria crit = session.createCriteria(clazz);
			if (Assert.isEmptyString(orderKey) == false) {
				crit.addOrder(Order.desc(orderKey));
			}
			if (Assert.isEmptyString(whereSql) == false) {
				crit.add(Restrictions.sqlRestriction(whereSql));
			}
			crit.add(Restrictions.sqlRestriction(whereSql));
			crit.setFirstResult(firstRow);
			crit.setMaxResults(pageSize);
			List<Object> reList = crit.list();
			return (List<T>) reList;
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}
}
