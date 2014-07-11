package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="RawKpi")
@Table(name="raw_kpi")
public class RawKpi {
	@Id
	@Column(name="res_id") 
	private Long resId;
	@Column(name="kpi_id")
	private Long kpiId;
	@Column(name="cdate")
	private String cdate;
	@Column(name="kpi_value")
	private String kpiValue;
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
	public Long getKpiId() {
		return kpiId;
	}
	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	
	public String getKpiValue() {
		return kpiValue;
	}
	public void setKpiValue(String kpiValue) {
		this.kpiValue = kpiValue;
	}
	
	
}
