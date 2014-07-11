package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysCommand")
@Table(name="sys_command")
public class SysCommand {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_command_seq")   
	private Long id;
	@Column(name="open_command")
	private String openCommand;
	@Column(name="command")
	private String command;
	@Column(name="cdate")
	private Long cdate;
	@Column(name="type_code")
	private String typeCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpenCommand() {
		return openCommand;
	}
	public void setOpenCommand(String openCommand) {
		this.openCommand = openCommand;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Long getCdate() {
		return cdate;
	}
	public void setCdate(Long cdate) {
		this.cdate = cdate;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
