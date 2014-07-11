package com.secpro.platform.monitoring.manage.entity;

public class RawSyslogHit {
	private String startDate;
	private String endDate;
	private Long resId;
	private String policyInfo;
	private long hitCount;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public String getPolicyInfo() {
		return policyInfo;
	}
	public void setPolicyInfo(String policyInfo) {
		this.policyInfo = policyInfo;
	}
	public long getHitCount() {
		return hitCount;
	}
	public void setHitCount(long hitCount) {
		this.hitCount = hitCount;
	}
	
}
