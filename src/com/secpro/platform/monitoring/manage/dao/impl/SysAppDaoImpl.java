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
import com.secpro.platform.monitoring.manage.dao.SysAppDao;
import com.secpro.platform.monitoring.manage.entity.SysApp;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysAppDaoImpl")
public class SysAppDaoImpl extends BaseDao implements SysAppDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String queryAppByRoleid(String roleid){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		String appids="";
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select id from sys_app a,sys_role_app rs where a.id=rs.app_id and rs.role_id="+roleid);
			int start=1;//如果start为1时，拼接appids时不加,号
			while(rs.next()){
				if(start==1){
					appids+=rs.getString(1);
					start++;
				}else{
					appids+=",";
					appids+=rs.getString(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return appids;
	}
	public List getAppTree(){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		List appTree=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("SELECT ID,APP_NAME,PARENT_ID,HASLEAF FROM sys_app where PARENT_ID!=41 and  PARENT_ID!=46 and PARENT_ID!=36 and id!=36 and id!=41 and id!=46 and id!=76 and id!=90 START WITH ID = 1 CONNECT BY PARENT_ID = PRIOR ID");
			while(rs.next()){
				SysApp app=new SysApp();
				app.setId(rs.getLong(1));
				app.setAppName(rs.getString(2));
				app.setParentId(rs.getLong(3));
				app.setHasLeaf(rs.getString(4));
				appTree.add(app);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return appTree;
	}
}
