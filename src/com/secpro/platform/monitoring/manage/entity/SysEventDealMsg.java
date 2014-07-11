package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="SysEventDealMsg")
@Table(name="sys_event_dealmsg")
public class SysEventDealMsg {
	@Id
	@Column(name="event_id")  
	@GeneratedValue(generator ="_assigned")   
	@GenericGenerator( name ="_assigned",strategy="assigned")
	private Long eventId;
	@Column(name="confirm_dealmsg")
	private String confirmDealmsg;
	@Column(name="clear_dealmsg")
	private String clearDealmsg;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getConfirmDealmsg() {
		return confirmDealmsg;
	}
	public void setConfirmDealmsg(String confirmDealmsg) {
		this.confirmDealmsg = confirmDealmsg;
	}
	public String getClearDealmsg() {
		return clearDealmsg;
	}
	public void setClearDealmsg(String clearDealmsg) {
		this.clearDealmsg = clearDealmsg;
	}
	
}
