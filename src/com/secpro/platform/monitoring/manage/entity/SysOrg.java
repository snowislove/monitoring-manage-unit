package com.secpro.platform.monitoring.manage.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="SysOrg")
@Table(name="sys_org")
public class SysOrg {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_org_seq") 
	private Long id;
	@Column(name="org_name")
	private String orgName;
	@Column(name="org_desc")
	private String orgDesc;
	@Column(name="parent_org_id")
	private Long parentOrgId;
	@Column(name="hasLeaf")
	private String hasLeaf;
	@Transient
	private List<SysOrg> children = new ArrayList<SysOrg>() ;
	@Transient
	private String state ; 
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<SysOrg> getChildren() {
		return children;
	}
	public void setChildren(List<SysOrg> children) {
		this.children = children;
	}
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public Long getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	
	

}
