package com.secpro.platform.monitoring.manage.common.services;

import java.util.List;

import org.hibernate.SessionFactory;

public interface IBaseService {
	void save(Object object);
	void update(Object object);
	void delete(Object object);
	public List<Object> queryAll(String hql);
	public SessionFactory getSessionFactory();
	public List<Object> queryByPage(String hql,int page,int maxPage);
	public Object getObj(Class clazz,Long id) ;
}
