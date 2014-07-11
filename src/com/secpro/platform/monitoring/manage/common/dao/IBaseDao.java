package com.secpro.platform.monitoring.manage.common.dao;

import java.util.List;

import org.hibernate.SessionFactory;

public interface IBaseDao {
	public void save(Object object);
	public void delete(Object object);
	public void update(Object object);
	public List<Object> queryAll(String hql);
	public SessionFactory getSessionFactory();
	public List<Object> queryByPage(String hql,int page,int maxPage);
	public Object getObj(Class clazz,Long id) ;
}
