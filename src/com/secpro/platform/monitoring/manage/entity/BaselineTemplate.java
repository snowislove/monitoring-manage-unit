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

@Entity(name="BaselineTemplate")
@Table(name="baseline_template")
public class BaselineTemplate {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="baseline_template_seq") 
	private Long id;
	@Column(name="template_name")
	private String templateName;
	@Column(name="template_desc")
	private String templateDesc;
	@Column(name="cdate")
	private String cdate;
	@Column(name="company_code")
	private String companyCode;
	@Transient
	private List baseLineList=new ArrayList();
	@Transient
	private String resip;
	@Transient
	private String resname;
	@Transient
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getResip() {
		return resip;
	}
	public void setResip(String resip) {
		this.resip = resip;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public List getBaseLineList() {
		return baseLineList;
	}
	public void setBaseLineList(List baseLineList) {
		this.baseLineList = baseLineList;
	}
	
}
