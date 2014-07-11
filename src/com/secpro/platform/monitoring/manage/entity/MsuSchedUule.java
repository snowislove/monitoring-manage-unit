package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="MsuSchedUule")
@Table(name="MSU_SCHEDULE")
public class MsuSchedUule {
	@Id
	@Column(name="SCHEDULE_ID") 
	@GeneratedValue(generator ="_assigned")   
	@GenericGenerator( name ="_assigned",strategy="assigned")
	private String scheduleId;
	@Column(name="task_Id")
	private String taskId;
	@Column(name="SCHEDULE_POINT")
	private Long schedulePoint;
	@Column(name="CREATE_AT")
	private Long createAt;
	@Column(name="REGION")
	private String region;
	@Column(name="OPERATION")
	private String operation;
	@Column(name="FETCH_AT")
	private Long fetchAt;
	@Column(name="FETCH_BY")
	private String fetchBy;
	@Column(name="EXECUTE_AT")
	private Long executeAt;
	@Column(name="EXECUTE_COST")
	private Long executeCost;
	@Column(name="EXECUTE_STATUS")
	private Integer executeStatus;
	@Column(name="EXECUTE_DESCRIPTION")
	private String executeDescription;
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getSchedulePoint() {
		return schedulePoint;
	}
	public void setSchedulePoint(Long schedulePoint) {
		this.schedulePoint = schedulePoint;
	}
	public Long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Long getFetchAt() {
		return fetchAt;
	}
	public void setFetchAt(Long fetchAt) {
		this.fetchAt = fetchAt;
	}
	public String getFetchBy() {
		return fetchBy;
	}
	public void setFetchBy(String fetchBy) {
		this.fetchBy = fetchBy;
	}
	public Long getExecuteAt() {
		return executeAt;
	}
	public void setExecuteAt(Long executeAt) {
		this.executeAt = executeAt;
	}
	public Long getExecuteCost() {
		return executeCost;
	}
	public void setExecuteCost(Long executeCost) {
		this.executeCost = executeCost;
	}
	public Integer getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(Integer executeStatus) {
		this.executeStatus = executeStatus;
	}
	public String getExecuteDescription() {
		return executeDescription;
	}
	public void setExecuteDescription(String executeDescription) {
		this.executeDescription = executeDescription;
	}
	
}
