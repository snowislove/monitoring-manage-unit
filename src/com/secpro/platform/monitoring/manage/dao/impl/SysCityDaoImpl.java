package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysCityDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;
@Repository("SysCityDaoImpl")
public class SysCityDaoImpl extends BaseDao implements SysCityDao {
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getTaskRegionByCityCode(String cityCode){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		String taskRegion="";
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select task_region from sys_city s where s.city_code='"+cityCode+"'");
			if(rs.next()){
				taskRegion=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally{
			JdbcUtil.close( con,sta,rs);
			
		}
		return taskRegion;
	}
}
