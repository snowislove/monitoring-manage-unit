package com.secpro.platform.monitoring.manage.entity;

public class BaselineMatchScore {
	private Integer socre;
	private String cdate;
	private Long ResId;
	private String taskCode;

	public Integer getSocre() {
		return socre;
	}
	public void setSocre(Integer socre) {
		this.socre = socre;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public Long getResId() {
		return ResId;
	}
	public void setResId(Long resId) {
		ResId = resId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	
}
