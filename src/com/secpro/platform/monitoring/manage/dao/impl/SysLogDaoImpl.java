package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysLogDao;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysLogDaoImpl")
public class SysLogDaoImpl extends BaseDao implements SysLogDao{
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public Map getLogApp(){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		Map logApp=new HashMap();
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select app_name,app_desc from log_app");
			while(rs.next()){
				logApp.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return logApp;
	}
	public long  getLogCount(String from ,String to){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		Map logApp=new HashMap();
		long logcount=0l;
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select count(id) from sys_log r where r.HANDLE_DATE>='"+from+"' and r.HANDLE_DATE <='"+to+"'");
			if(rs.next()){
				logcount=rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return logcount;
	}
}
