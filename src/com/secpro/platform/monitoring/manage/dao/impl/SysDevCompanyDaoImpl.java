package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysDevCompanyDao;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;
@Repository("SysDevCompanyDaoImpl")
public class SysDevCompanyDaoImpl extends BaseDao implements SysDevCompanyDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String createCompanyCode(){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		long companyCode=1000;
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("SELECT max(to_number(company_code)) from sys_dev_company");
			if(rs.next()){
				if(rs.getString(1)!=null){
					companyCode=Long.parseLong(rs.getString(1))+1000;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return companyCode+"";
	}
}
