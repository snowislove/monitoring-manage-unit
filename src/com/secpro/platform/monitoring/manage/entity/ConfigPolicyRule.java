package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="ConfigPolicyRule")
@Table(name="config_policy_rule")
public class ConfigPolicyRule {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="config_policy_rule_seq") 
	private Long id;
	@Column(name="STANDARD_RULE")
	private String standardRule;
	@Column(name="TYPE_CODE")
	private String typeCode;
	@Column(name="CONTAIN_CONFLICT_RULE")
	private String containConflictRule;
	
	public String getContainConflictRule() {
		return containConflictRule;
	}
	public void setContainConflictRule(String containConflictRule) {
		this.containConflictRule = containConflictRule;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStandardRule() {
		return standardRule;
	}
	public void setStandardRule(String standardRule) {
		this.standardRule = standardRule;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
