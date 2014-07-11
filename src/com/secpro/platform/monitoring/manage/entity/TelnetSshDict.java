package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name="TelnetSshDict")
@Table(name="telnet_ssh_dict")
public class TelnetSshDict {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="telnet_ssh_dict_seq") 
	private Long id;
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
	@Column(name="open_command")
	private String openCommand;
	@Column(name="command")
	private String command;
	@Column(name="company_code")
	private String companyCode;
	@Column(name="type_code")
	private String typeCode;
	@Column(name="filter_string")
	private String filterString;
	@Column(name="terminal_type")
	private String terminalType;
	
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
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
