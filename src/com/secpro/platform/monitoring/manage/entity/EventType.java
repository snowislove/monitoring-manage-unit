package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="EventType")
@Table(name="event_type")
public class EventType {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="event_type_seq")  
	private Long id;
	@Column(name="EVENT_TYPE_NAME", nullable=true)
	private String eventTypeName;
	@Column(name="EVENT_TYPE_DESC")
	private String eventTypeDesc;
	@Column(name="EVENT_RECOVER")
	private String eventRecover;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	public String getEventTypeDesc() {
		return eventTypeDesc;
	}
	public void setEventTypeDesc(String eventTypeDesc) {
		this.eventTypeDesc = eventTypeDesc;
	}
	public String getEventRecover() {
		return eventRecover;
	}
	public void setEventRecover(String eventRecover) {
		this.eventRecover = eventRecover;
	}
	
}
