package com.secpro.platform.monitoring.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="RawFwFile")
@Table(name="RAW_FW_FILE")
public class RawFwFile {
	@Id
	@Column 
	@GeneratedValue(generator ="_assigned")   
	@GenericGenerator( name ="_assigned",strategy="assigned")
	private Long id;
	@Column(name="cdate")
	private String cdate;
	@Column(name="RES_ID")
	private String resId;
	@Column(name="FILE_PATH")
	private String filePath;
	@Column(name="FILE_SIZE")
	private String fileSize;
	@Column(name="FILE_NAME")
	private String fileName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
