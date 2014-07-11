package com.secpro.platform.monitoring.manage.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysBaselineDao;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;

@Service("SysBaselineServiceImpl")
public class SysBaselineServiceImpl extends BaseService implements SysBaselineService{
	private SysBaselineDao dao;

	public SysBaselineDao getDao() {
		return dao;
	}
	@Resource(name="SysBaselineDaoImpl")
	public void setDao(SysBaselineDao dao) {
		this.dao = dao;
	}
	public boolean deleteBaseLine(String[] baseLineIds){
		return dao.deleteBaseLine(baseLineIds);
	}
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule){
		return dao.createBaseLineRule(typeCode, baseLineId, rule);
	}
	public String getRule(String baselineId,String typeCode){
		return dao.getRule(baselineId, typeCode);
	}
	public List queryResMatchScorePage(Long resId,String startTime,String endTime,int pageSize,int pageNo){
		return dao.queryResMatchScorePage(resId, startTime, endTime, pageSize, pageNo);
	}
	public int queryAllScoreCountByRes(Long resId,String startTime,String endTime){
		return dao.queryAllScoreCountByRes(resId, startTime, endTime);
	}
	public List queryAllMatchScorePage(String startTime,String endTime,int pageSize,int pageNo){
		return dao.queryAllMatchScorePage(startTime, endTime, pageSize, pageNo);
	}
	public int queryAllMatchScorePageCount(String startTime,String endTime){
		return dao.queryAllMatchScorePageCount(startTime, endTime);
	}
	public List queryMatchDatil(String resId ,String taskCode){
		return dao.queryMatchDatil(resId, taskCode);
	}
}
