package com.secpro.platform.monitoring.manage.entity;

import java.util.Map;

public class SyslogBean {
	private String typeCode;
	private int checkNum;
	private String checkAction;
	private Map<String,String> regexs;
	private Map<String,Map<String,String>> ruleMapping;
	
	public Map<String, Map<String, String>> getRuleMapping() {
		return ruleMapping;
	}

	public void setRuleMapping(Map<String, Map<String, String>> ruleMapping) {
		this.ruleMapping = ruleMapping;
	}

	public Map<String, String> getRegexs() {
		return regexs;
	}

	public void setRegexs(Map<String, String> regexs) {
		this.regexs = regexs;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public String getCheckAction() {
		return checkAction;
	}

	public void setCheckAction(String checkAction) {
		this.checkAction = checkAction;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
