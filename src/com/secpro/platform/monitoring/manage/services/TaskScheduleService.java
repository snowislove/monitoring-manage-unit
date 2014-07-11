package com.secpro.platform.monitoring.manage.services;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.services.IBaseService;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleDao;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysCommand;
import com.secpro.platform.monitoring.manage.entity.SysOperation;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;

public interface TaskScheduleService extends IBaseService {
	public List<SysOperation> getSysOperationByTypeCode(String typeCode);

	public List<SysCommand> getSystCommandByTypeCode(String typeCode);

	public List<SysResAuth> getSysResAuthByResId(long resId);
	
	public SysCity getSysCityBycityCode(String cityCode);
	
	public List<String[]> getSnmpOIDbyTypeCode(String typeCode);

	public TaskScheduleDao getTaskScheduleDao();
}
