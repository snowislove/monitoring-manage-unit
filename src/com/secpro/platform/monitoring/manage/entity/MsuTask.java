package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MsuTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MSU_TASK")
public class MsuTask {

	// Fields

	private String id = "";
	private String region = "";
	private Long createAt = 0L;
	private String schedule = "";
	private String operation = "";
	private String targetIp = "";
	private Integer targetPort = 0;
	private String metaData = "";
	private String content = "";
	private Long resId = 0L;
	private Boolean isRealtime = false;

	// Constructors

	/** default constructor */
	public MsuTask() {
	}

	/** minimal constructor */
	public MsuTask(String id, String region, Long createAt, String schedule, String operation, String targetIp, Integer targetPort, String metaData, String content, Long resId) {
		this.id = id;
		this.region = region;
		this.createAt = createAt;
		this.schedule = schedule;
		this.operation = operation;
		this.targetIp = targetIp;
		this.targetPort = targetPort;
		this.metaData = metaData;
		this.content = content;
		this.resId = resId;
	}

	/** full constructor */
	public MsuTask(String id, String region, Long createAt, String schedule, String operation, String targetIp, Integer targetPort, String metaData, String content, Long resId,
			Boolean isRealtime) {
		this.id = id;
		this.region = region;
		this.createAt = createAt;
		this.schedule = schedule;
		this.operation = operation;
		this.targetIp = targetIp;
		this.targetPort = targetPort;
		this.metaData = metaData;
		this.content = content;
		this.resId = resId;
		this.isRealtime = isRealtime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "REGION", nullable = false, length = 50)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "CREATE_AT", nullable = false, precision = 20, scale = 0)
	public Long getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}

	@Column(name = "SCHEDULE", nullable = false, length = 50)
	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	@Column(name = "OPERATION", nullable = false, length = 50)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "TARGET_IP", nullable = false, length = 50)
	public String getTargetIp() {
		return this.targetIp;
	}

	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}

	@Column(name = "TARGET_PORT", nullable = false, precision = 5, scale = 0)
	public Integer getTargetPort() {
		return this.targetPort;
	}

	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

	@Column(name = "META_DATA", nullable = false, length = 500)
	public String getMetaData() {
		return this.metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	@Column(name = "CONTENT", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "RES_ID", nullable = false, precision = 20, scale = 0)
	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	@Column(name = "IS_REALTIME", precision = 1, scale = 0)
	public Boolean getIsRealtime() {
		return this.isRealtime;
	}

	public void setIsRealtime(Boolean isRealtime) {
		this.isRealtime = isRealtime;
	}

}