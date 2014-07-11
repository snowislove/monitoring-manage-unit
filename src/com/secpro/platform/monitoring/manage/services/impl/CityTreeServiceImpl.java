package com.secpro.platform.monitoring.manage.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SysCityDao;
import com.secpro.platform.monitoring.manage.services.CityTreeService;
@Service("CityTreeServiceImpl")
public class CityTreeServiceImpl extends BaseService implements CityTreeService{
	private SysCityDao scdao;

	public SysCityDao getScdao() {
		return scdao;
	}
	@Resource(name="SysCityDaoImpl")
	public void setScdao(SysCityDao scdao) {
		this.scdao = scdao;
	}
	public String getTaskRegionByCityCode(String cityCode){
		return scdao.getTaskRegionByCityCode(cityCode);
	}
	
}
