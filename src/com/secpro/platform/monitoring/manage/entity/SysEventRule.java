package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysEventRule")
@Table(name="sys_event_rule")
public class SysEventRule {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_event_rule_seq")
	private Long id;
	@Column(name="EVENT_LEVEL", nullable=true)
	private Integer eventLevel;
	@Column(name="THRESHOLD_VALUE", nullable=true)
	private String thresholdValue;
	@Column(name="SET_MSG", nullable=true)
	private String setMsg;
	@Column(name="RECOVER_SET_MSG", nullable=true)
	private String recoverSetMsg;
	@Column(name="REPEAT", nullable=true)
	private String repeat;
	@Column(name="THRESHOLD_OPR", nullable=true)
	private String thresholdOpr;
	@Column(name="RES_ID")
	private Long resId;
	@Column(name="EVENT_TYPE_ID", nullable=true)
	private Long eventTypeId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(Integer eventLevel) {
		this.eventLevel = eventLevel;
	}
	public String getThresholdValue() {
		return thresholdValue;
	}
	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	public String getSetMsg() {
		return setMsg;
	}
	public void setSetMsg(String setMsg) {
		this.setMsg = setMsg;
	}
	public String getRecoverSetMsg() {
		return recoverSetMsg;
	}
	public void setRecoverSetMsg(String recoverSetMsg) {
		this.recoverSetMsg = recoverSetMsg;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public String getThresholdOpr() {
		return thresholdOpr;
	}
	public void setThresholdOpr(String thresholdOpr) {
		this.thresholdOpr = thresholdOpr;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public Long getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	
	
}
