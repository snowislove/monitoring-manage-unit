package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysBaseline")
@Table(name="sys_baseline")
public class SysBaseline {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_baseline_seq")   
	private Long id;
	@Column(name="baseline_desc")
	private String baselineDesc;
	@Column(name="baseline_type")
	private String baselineType;
	@Column(name="baseline_black_white")
	private String baselineBlackWhite;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBaselineDesc() {
		return baselineDesc;
	}
	public void setBaselineDesc(String baselineDesc) {
		this.baselineDesc = baselineDesc;
	}
	public String getBaselineType() {
		return baselineType;
	}
	public void setBaselineType(String baselineType) {
		this.baselineType = baselineType;
	}
	public String getBaselineBlackWhite() {
		return baselineBlackWhite;
	}
	public void setBaselineBlackWhite(String baselineBlackWhite) {
		this.baselineBlackWhite = baselineBlackWhite;
	}
	
	
}
