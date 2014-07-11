package com.secpro.platform.monitoring.manage.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="SysCity")
@Table(name="sys_city")
public class SysCity {
	@Id
	@Column  
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="generator")   
	@SequenceGenerator( name ="generator",allocationSize = 1,sequenceName="sys_city_seq")   
	private Long id;
	@Column(name="city_name", nullable=true)
	private String cityName;
	@Column(name="city_code", nullable=true,unique = true)
	private String cityCode;
	@Column(name="city_level", nullable=true,unique = true)
	private String cityLevel;
	@Column(name="city_sort")
	private String citySort;
	@Column(name="city_desc")
	private String cityDesc;
	@Column(name="parent_code")
	private String parentCode;
	
	/*@OneToMany  
	@JoinColumn(name="parent_code",referencedColumnName="city_code")   
	private Set<SysCity> sysCitys=new HashSet<SysCity>();*/
	/*@ManyToOne  
	@JoinColumn(name="parent_code",referencedColumnName="city_code")  
	private SysCity sc;*/
	
	/*public SysCity getSc() {
		return sc;
	}
	public void setSc(SysCity sc) {
		this.sc = sc;
	}*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityLevel() {
		return cityLevel;
	}
	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}
	public String getCityDesc() {
		return cityDesc;
	}
	public void setCityDesc(String cityDesc) {
		this.cityDesc = cityDesc;
	}
	
	/*public Set<SysCity> getSysCitys() {
		return sysCitys;
	}
	public void setSysCitys(Set<SysCity> sysCitys) {
		this.sysCitys = sysCitys;
	}*/
	public String getCitySort() {
		return citySort;
	}
	public void setCitySort(String citySort) {
		this.citySort = citySort;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
}
