package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="EventMsg")
@Table(name="event_msg")
public class EventMsg {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="event_msg_seq")
	private Long id;
	@Column(name="msg_format", nullable=true)
	private String msgFormat;
	@Column(name="event_type_id", nullable=true)
	private Long eventTypeId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsgFormat() {
		return msgFormat;
	}
	public void setMsgFormat(String msgFormat) {
		this.msgFormat = msgFormat;
	}
	public Long getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	
}
