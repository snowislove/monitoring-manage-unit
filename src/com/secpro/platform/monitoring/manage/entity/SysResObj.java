package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="SysResObj")
@Table(name="sys_res_obj")
public class SysResObj {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_res_obj_seq")   
	private Long id;
	@Column(name="RES_NAME", nullable=true)
	private String resName;
	@Column(name="RES_DESC")
	private String resDesc;
	@Column(name="RES_IP", nullable=true)
	private String resIp;
	@Column(name="CDATE")
	private String cdate;
	@Column(name="STATUS_OPERTION")
	private String statusOperation;
	@Column(name="CONFIG_OPERTION")
	private String configOperation;
	@Column(name="RES_PAUSED", nullable=true)
	private String resPaused;
	@Column(name="MCA_ID", nullable=true)
	private Long mcaId;
	@Column(name="CLASS_ID", nullable=true)
	private Long classId;
	@Column(name="CITY_CODE", nullable=true)
	private String cityCode;
	@Column(name="COMPANY_CODE", nullable=true)
	private String companyCode;
	@Column(name="TYPE_CODE", nullable=true)
	private String typeCode;
	@Column(name="TEMPLATE_ID", nullable=true)
	private Long templateId;
	@Transient
	private String cityName;
	
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResDesc() {
		return resDesc;
	}
	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	public String getResIp() {
		return resIp;
	}
	public void setResIp(String resIp) {
		this.resIp = resIp;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getStatusOperation() {
		return statusOperation;
	}
	public void setStatusOperation(String statusOperation) {
		this.statusOperation = statusOperation;
	}
	public String getConfigOperation() {
		return configOperation;
	}
	public void setConfigOperation(String configOperation) {
		this.configOperation = configOperation;
	}
	public String getResPaused() {
		return resPaused;
	}
	public void setResPaused(String resPaused) {
		this.resPaused = resPaused;
	}
	public Long getMcaId() {
		return mcaId;
	}
	public void setMcaId(Long mcaId) {
		this.mcaId = mcaId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
}
