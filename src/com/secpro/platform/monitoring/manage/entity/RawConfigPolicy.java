package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="RawConfigPolicy")
@Table(name="raw_config_policy")
public class RawConfigPolicy {
	@Id
	@Column 
	@GeneratedValue(generator ="_assigned")   
	@GenericGenerator( name ="_assigned",strategy="assigned")
	private Long id;
	@Column(name="cdate")
	private String cdate;
	@Column(name="CONFIG_POLICY_INFO")
	private String configPolicyInfo;
	@Column(name="RES_ID")
	private Long resId;
	@Column(name="TASK_CODE")
	private String taskCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getConfigPolicyInfo() {
		return configPolicyInfo;
	}
	public void setConfigPolicyInfo(String configPolicyInfo) {
		this.configPolicyInfo = configPolicyInfo;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	
	
}
