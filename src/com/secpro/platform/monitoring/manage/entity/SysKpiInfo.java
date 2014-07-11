package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name="SysKpiInfo")
@Table(name="sys_kpi_info")
public class SysKpiInfo {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="event_type_seq")  
	private Long id;
	@Column(name="kpi_name")
	private String kpiName;
	@Column(name="kpi_desc")
	private String kpiDesc;
	@Column(name="class_id")
	private Long classId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getKpiDesc() {
		return kpiDesc;
	}
	public void setKpiDesc(String kpiDesc) {
		this.kpiDesc = kpiDesc;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
}
