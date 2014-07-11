package com.secpro.platform.monitoring.manage.common.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;
import com.secpro.platform.monitoring.manage.common.services.IBaseService;

@Service("BaseService")
@Transactional
public class BaseService implements IBaseService{
	@Resource(name="BaseDao")
	private IBaseDao baseDao;
	

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void save(Object object) {
		// TODO Auto-generated method stub
		baseDao.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		baseDao.update(object);
	}

	public void delete(Object object) {
		// TODO Auto-generated method stub
		baseDao.delete(object);
	}

	public List<Object> queryAll(String hql) {
		// TODO Auto-generated method stub
		return baseDao.queryAll(hql);
	}

	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return baseDao.getSessionFactory();
	}
	public List<Object> queryByPage(String hql,int page,int maxPage){
		return baseDao.queryByPage(hql, page, maxPage);
	}
	public Object getObj(Class clazz,Long id) {
		return baseDao.getObj(clazz, id);
	}

}
