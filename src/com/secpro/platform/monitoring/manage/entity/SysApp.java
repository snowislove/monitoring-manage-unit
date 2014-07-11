package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysApp")
@Table(name="sys_app")
public class SysApp {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_app_seq")  
	private Long id;
	@Column(name="app_name")
	private String appName;
	@Column(name="app_url")
	private String appUrl;
	@Column(name="app_desc")
	private String appDesc;
	@Column(name="parent_id")
	private Long parentId;
	@Column(name="hasLeaf")
	private String hasLeaf;
	
	public String getHasLeaf() {
		return hasLeaf;
	}
	public void setHasLeaf(String hasLeaf) {
		this.hasLeaf = hasLeaf;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
