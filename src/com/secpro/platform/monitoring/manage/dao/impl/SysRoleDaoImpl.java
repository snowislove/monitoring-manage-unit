package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysRoleDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysRoleDaoImpl")
public class SysRoleDaoImpl extends BaseDao implements SysRoleDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean deleteRole(String[] roleid){
		Connection con=null;
		PreparedStatement sta=null;
		PreparedStatement sta1=null;
		PreparedStatement sta2=null;
		ResultSet rs=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("delete from sys_user_role where role_id=?");
			sta1=con.prepareStatement("delete from sys_role_app where role_id=?");
			sta2=con.prepareStatement("delete from sys_role where id=?");
			for(int i=0;i<roleid.length;i++){
				Long id = Long.parseLong(roleid[i]);
				sta.setLong(1, id);
				sta1.setLong(1, id);
				sta2.setLong(1, id);
				sta.execute();
				sta1.execute();
				sta2.execute();
			}
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
			System.out.println("---------------------"+e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close(sta);
			JdbcUtil.close(sta1);
			JdbcUtil.close(sta2);
			JdbcUtil.close(con);
		}
		return flag;
	}
	public boolean createRoleAppMapping(String[] roleids,String[] appids){
		Connection con=null;
		PreparedStatement sta=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("delete from sys_role_app where role_id=?");
			for(int i=0;i<roleids.length;i++){
				sta.setLong(1, Long.parseLong(roleids[i]));
				sta.execute();
			}
			sta.close();
			int count=0;
			sta=con.prepareStatement("insert into sys_role_app(role_id,app_id) values(?,? )");
			for(int i=0;i<roleids.length;i++){
				for(int j=0;j<appids.length;j++){
					sta.setLong(1, Long.parseLong(roleids[i]));
					sta.setLong(2, Long.parseLong(appids[j]));
					sta.addBatch();
					count++;
					if(count==50){
						sta.executeBatch();
						count=0;
					}
				}
			}
			sta.executeBatch();
			con.commit();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			flag=false;
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close(sta);
			JdbcUtil.close(con);
		}
		return flag;
	}
	public List getAppByRole(Long roleId){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		List l=new ArrayList();
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("select app_id from sys_role_app where role_id=?");
			sta.setLong(1, roleId);
			rs=sta.executeQuery();
			while(rs.next()){
				l.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return l;
	}
}
