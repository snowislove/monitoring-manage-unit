package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="SysEvent")
@Table(name="sys_event")
public class SysEvent {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_event_seq")
	private Long id;
	@Column(name="EVENT_LEVEL", nullable=true)
	private int eventLevel;
	@Column(name="MESSAGE", nullable=true)
	private String message;
	@Column(name="CDATE", nullable=true)
	private String cdate;
	@Column(name="CONFIRM_USER")
	private String confirmUser;
	@Column(name="CONFIRM_DATE")
	private String confirmDate;
	@Column(name="CLEAR_USER")
	private String clearUser;
	@Column(name="CLEAR_DATE")
	private String clearDate;
	@Column(name="RES_ID", nullable=true)
	private Long resId;
	@Column(name="EVENT_TYPE_ID", nullable=true)
	private Long eventTypeId;
	@Transient
	private SysEventDealMsg dealMsg;
	
	public SysEventDealMsg getDealMsg() {
		return dealMsg;
	}
	public void setDealMsg(SysEventDealMsg dealMsg) {
		this.dealMsg = dealMsg;
	}
	public Long getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(int eventLevel) {
		this.eventLevel = eventLevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getClearUser() {
		return clearUser;
	}
	public void setClearUser(String clearUser) {
		this.clearUser = clearUser;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
}
