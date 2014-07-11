package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysLog")
@Table(name="sys_log")
public class Log {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_log_seq")  
	private Long id;
	@Column(name="account")
	private String account;
	@Column(name="user_ip")
	private String userIp;
	@Column(name="handle_date")
	private String handleDate;
	@Column(name="handle_content")
	private String handleContent;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	public String getHandleContent() {
		return handleContent;
	}
	public void setHandleContent(String handleContent) {
		this.handleContent = handleContent;
	}
	
	
}
