package com.secpro.platform.monitoring.manage.dao;

import java.util.List;

import com.secpro.platform.monitoring.manage.common.dao.IBaseDao;

public interface SysBaselineDao extends IBaseDao{
	public boolean deleteBaseLine(String[] baseLineIds);
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule);
	public String getRule(String baselineId,String typeCode);
	public List queryResMatchScorePage(Long resId,String startTime,String endTime,int pageSize,int pageNo);
	public int queryAllScoreCountByRes(Long resId,String startTime,String endTime);
	public List queryAllMatchScorePage(String startTime,String endTime,int pageSize,int pageNo);
	public int queryAllMatchScorePageCount(String startTime,String endTime);
	public List queryMatchDatil(String resId ,String taskCode);
}
