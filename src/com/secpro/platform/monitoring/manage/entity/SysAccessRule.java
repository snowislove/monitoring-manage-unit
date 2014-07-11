package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysAccessRule")
@Table(name="SYS_ACCESS_RULE")
public class SysAccessRule {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="SYS_ACCESS_RULE_seq")  
	private Long id;
	@Column(name="MAXUSER")
	private Long maxUser;
	@Column(name="isLimitIp")
	private String isLimitIp;
	@Column(name="isLimitTime")
	private String isLimitTime;
	@Column(name="access_timeout")
	private Long accessTimeOut;
	
	public Long getAccessTimeOut() {
		return accessTimeOut;
	}
	public void setAccessTimeOut(Long accessTimeOut) {
		this.accessTimeOut = accessTimeOut;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(Long maxUser) {
		this.maxUser = maxUser;
	}
	public String getIsLimitIp() {
		return isLimitIp;
	}
	public void setIsLimitIp(String isLimitIp) {
		this.isLimitIp = isLimitIp;
	}
	public String getIsLimitTime() {
		return isLimitTime;
	}
	public void setIsLimitTime(String isLimitTime) {
		this.isLimitTime = isLimitTime;
	}
	
}
