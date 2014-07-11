package com.secpro.platform.monitoring.manage.entity;

public class RawBaselineMatch {
	
	private String matchResult;
	private String result;
	private String cdate;
	private Long baselineId;
	private String taskCode;
	private String baseLineDesc;
	public String getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public Long getBaselineId() {
		return baselineId;
	}
	public void setBaselineId(Long baselineId) {
		this.baselineId = baselineId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getBaseLineDesc() {
		return baseLineDesc;
	}
	public void setBaseLineDesc(String baseLineDesc) {
		this.baseLineDesc = baseLineDesc;
	}
	
	
}
