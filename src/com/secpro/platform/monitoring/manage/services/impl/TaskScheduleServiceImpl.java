package com.secpro.platform.monitoring.manage.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleDao;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysCommand;
import com.secpro.platform.monitoring.manage.entity.SysOperation;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.services.TaskScheduleService;
import com.secpro.platform.monitoring.manage.util.Assert;

@Service("TaskScheduleServiceImpl")
public class TaskScheduleServiceImpl extends BaseService implements TaskScheduleService {
	private TaskScheduleDao dao;

	public TaskScheduleDao getDao() {
		return dao;
	}

	@Resource(name = "TaskScheduleDaoImpl")
	public void setDao(TaskScheduleDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> List<T> findAll(Class<?> clazz) {
		Session session = null;
		try {
			session = this.dao.getSessionFactory().openSession();
			String queryString = "from " + clazz.getSimpleName();
			Query queryObject = session.createQuery(queryString);
			return (List<T>) queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<SysOperation> getSysOperationByTypeCode(String typeCode) {
		String whereSql = null;
		if (Assert.isEmptyString(typeCode) == false) {
			whereSql = "TYPE_CODE='" + typeCode + "'";
		}
		return this.dao.findAll(SysOperation.class, whereSql, 1, 1000, null);
	}

	public List<SysCommand> getSystCommandByTypeCode(String typeCode) {
		String whereSql = null;
		if (Assert.isEmptyString(typeCode) == false) {
			whereSql = "TYPE_CODE='" + typeCode + "'";
		}
		return this.dao.findAll(SysCommand.class, whereSql, 1, 1000, null);
	}

	public List<SysResAuth> getSysResAuthByResId(long resId) {
		return this.dao.findAll(SysResAuth.class, "RES_ID=" + resId, 1, 1000, null);
	}

	public SysCity getSysCityBycityCode(String cityCode) {
		if (Assert.isEmptyString(cityCode) == true) {
			return null;
		}
		List<SysCity> cityList = this.dao.findAll(SysCity.class, "CITY_CODE='" + cityCode + "'", 1, 1000, null);
		if (cityList == null || cityList.isEmpty()) {
			return null;
		}
		return cityList.get(0);
	}

	public List<String[]> getSnmpOIDbyTypeCode(String typeCode) {
		if (Assert.isEmptyString(typeCode) == true) {
			return new ArrayList<String[]>();
		}
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT O.MIBOID, K.KPI_NAME");
		querySQL.append("  FROM (SELECT O.MIBOID, O.TYPE_CODE, O.KPI_ID");
		querySQL.append("          FROM SYS_KPI_OID O");
		querySQL.append("         WHERE O.TYPE_CODE = '").append(typeCode).append("') O");
		querySQL.append("  LEFT JOIN (SELECT K.KPI_NAME, K.ID FROM SYS_KPI_INFO K) K");
		querySQL.append("    ON K.ID = O.KPI_ID");
		List<Object> rowList = this.dao.queryBySql(querySQL.toString(), 1000, 1);
		if (rowList == null) {
			return new ArrayList<String[]>();
		}
		List<String[]> dataList = new ArrayList<String[]>();
		for (int i = 0; i < rowList.size(); i++) {
			dataList.add(new String[] { String.valueOf(((Object[]) (rowList.get(i)))[0]), String.valueOf(((Object[]) (rowList.get(i)))[1]) });
		}
		return dataList;
	}

	public TaskScheduleDao getTaskScheduleDao() {
		return this.dao;
	}

}
