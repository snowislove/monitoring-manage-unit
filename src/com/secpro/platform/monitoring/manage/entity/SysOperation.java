package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "SysOperation")
@Table(name = "sys_opertion")
public class SysOperation {
	// ID id number(9) ID
	// 操作名称 operation_name varchar2(20) 操作名称，如ssh、telnet、snmpv1、snmpv2c、snmpv3
	// 操作描述 operation_desc varchar2(255) 操作描述
	// 设备型号编码 type_code varchar2(10) 设备型号编码，若没有型号编码，应为type_id
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "sys_operation_seq")
	private Long id;
	@Column(name = "operation_name", nullable = true)
	private String operationName;
	@Column(name = "operation_desc", nullable = true, unique = true)
	private String operationDesc;
	@Column(name = "type_code", nullable = true, unique = true)
	private String typeCode;

	//
	public SysOperation() {
	}

	//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
