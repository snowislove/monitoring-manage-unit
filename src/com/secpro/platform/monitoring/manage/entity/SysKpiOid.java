package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysKpiOid")
@Table(name="sys_kpi_oid")
public class SysKpiOid {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_kpi_oid_seq")  
	private Long id;
	@Column(name="miboid")
	private String miboid;
	@Column(name="rule")
	private String rule;
	@Column(name="company_code")
	private String companyCode;
	@Column(name="type_code")
	private String typeCode;
	@Column(name="kpi_id")
	private String kpiId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMiboid() {
		return miboid;
	}
	public void setMiboid(String miboid) {
		this.miboid = miboid;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getKpiId() {
		return kpiId;
	}
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	
}
