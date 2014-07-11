package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="NotifyUserRule")
@Table(name="notify_user_rule")
public class NotifyUserRule {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="notify_user_rule_seq")
	private Long id;
	@Column(name="res_id", nullable=true)
	private Long resId;
	@Column(name="event_rule_id", nullable=true)
	private Long eventRuleId;
	@Column(name="user_id", nullable=true)
	private long userId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	public Long getEventRuleId() {
		return eventRuleId;
	}
	public void setEventRuleId(Long eventRuleId) {
		this.eventRuleId = eventRuleId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
