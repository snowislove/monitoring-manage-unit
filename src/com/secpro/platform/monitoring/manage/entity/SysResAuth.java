package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name="SysResAuth")
@Table(name="sys_res_auth")
public class SysResAuth {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_res_auth_seq")  
	private Long id;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="user_prompt")
	private String userPrompt;
	@Column(name="pass_prompt")
	private String passPrompt;
	@Column(name="prompt")
	private String prompt;
	@Column(name="exec_prompt")
	private String execPrompt;
	@Column(name="next_prompt")
	private String nextPrompt;
	@Column(name="sepa_word")
	private String sepaWord;
	@Column(name="community")
	private String community;
	@Column(name="snmpv3_user")
	private String snmpv3User;
	@Column(name="snmpv3_Auth")
	private String snmpv3Auth;
	@Column(name="snmpv3_authpass")
	private String snmpv3Authpass;
	@Column(name="snmpv3_priv")
	private String snmpv3Priv;
	@Column(name="snmpv3_privpass")
	private String snmpv3Privpass;
	@Column(name="filter_string")
	private String filterString;
	@Column(name="terminal_type")
	private String terminalType;
	@Column(name="res_id")
	private Long resId;
	public String getFilterString() {
		return filterString;
	}
	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserPrompt() {
		return userPrompt;
	}
	public void setUserPrompt(String userPrompt) {
		this.userPrompt = userPrompt;
	}
	public String getPassPrompt() {
		return passPrompt;
	}
	public void setPassPrompt(String passPrompt) {
		this.passPrompt = passPrompt;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getExecPrompt() {
		return execPrompt;
	}
	public void setExecPrompt(String execPrompt) {
		this.execPrompt = execPrompt;
	}
	public String getNextPrompt() {
		return nextPrompt;
	}
	public void setNextPrompt(String nextPrompt) {
		this.nextPrompt = nextPrompt;
	}
	public String getSepaWord() {
		return sepaWord;
	}
	public void setSepaWord(String sepaWord) {
		this.sepaWord = sepaWord;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getSnmpv3User() {
		return snmpv3User;
	}
	public void setSnmpv3User(String snmpv3User) {
		this.snmpv3User = snmpv3User;
	}
	public String getSnmpv3Auth() {
		return snmpv3Auth;
	}
	public void setSnmpv3Auth(String snmpv3Auth) {
		this.snmpv3Auth = snmpv3Auth;
	}
	public String getSnmpv3Authpass() {
		return snmpv3Authpass;
	}
	public void setSnmpv3Authpass(String snmpv3Authpass) {
		this.snmpv3Authpass = snmpv3Authpass;
	}
	public String getSnmpv3Priv() {
		return snmpv3Priv;
	}
	public void setSnmpv3Priv(String snmpv3Priv) {
		this.snmpv3Priv = snmpv3Priv;
	}
	public String getSnmpv3Privpass() {
		return snmpv3Privpass;
	}
	public void setSnmpv3Privpass(String snmpv3Privpass) {
		this.snmpv3Privpass = snmpv3Privpass;
	}
	public Long getResId() {
		return resId;
	}
	public void setResId(Long resId) {
		this.resId = resId;
	}
	
	
}
