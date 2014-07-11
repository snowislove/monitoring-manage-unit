package com.secpro.platform.monitoring.manage.common.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;


@Repository("BaseDao")
public class BaseDao implements IBaseDao {
		
		@Autowired
		private SessionFactory sessionFactory;
		
	
		public void save(Object object) {
			try {
				sessionFactory.getCurrentSession().save(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public void delete(Object object) {
			try {
				sessionFactory.getCurrentSession().delete(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public Object getObj(Class clazz,Long id) {
			Object obj=null;
			try {
				obj= sessionFactory.getCurrentSession().get(clazz, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
		}


		
		public void update(Object object) {
			try {
				sessionFactory.getCurrentSession().saveOrUpdate(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public List<Object> queryAll(String hql) {

			List<Object> l=null;
			try {
				try {
					System.out.println(hql);
					l = sessionFactory.getCurrentSession().createQuery(hql).list();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return l;
		}
		public List<Object> queryByPage(String hql,int page,int maxPage){
			List<Object> l=null;
			try {
				l = sessionFactory.getCurrentSession().createQuery(hql).setFirstResult((page-1)*maxPage).setMaxResults(maxPage).list();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return l;
		}
		public SessionFactory getSessionFactory(){
			
			return sessionFactory;
		}
		
}
