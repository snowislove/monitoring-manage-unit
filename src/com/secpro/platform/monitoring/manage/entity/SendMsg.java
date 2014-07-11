package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SendMsg")
@Table(name="send_msg")
public class SendMsg {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="send_msg_seq")  
	private Long id;
	@Column(name="cdate")
	private String cdate;
	@Column(name="user_name")
	private String userName;
	@Column(name="mobel_tel")
	private String mobelTel;
	@Column(name="message")
	private String message;
	@Column(name="send_msg_status")
	private String sendMesStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobelTel() {
		return mobelTel;
	}
	public void setMobelTel(String mobelTel) {
		this.mobelTel = mobelTel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendMesStatus() {
		return sendMesStatus;
	}
	public void setSendMesStatus(String sendMesStatus) {
		this.sendMesStatus = sendMesStatus;
	}
	
}
