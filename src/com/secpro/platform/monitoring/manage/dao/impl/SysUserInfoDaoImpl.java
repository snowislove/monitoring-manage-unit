package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysUserInfoDao;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysUserInfoDaoImpl")
public class SysUserInfoDaoImpl extends BaseDao implements SysUserInfoDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean createUserRoleMapping(String[] userIds,String[] roleIds){
		Connection con=null;
		PreparedStatement sta=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("delete from sys_user_role where user_id=?");
			for(int i=0;i<userIds.length;i++){
				sta.setLong(1, Long.parseLong(userIds[i]));
				sta.execute();
			}
			sta.close();
			int count=0;
			sta=con.prepareStatement("insert into sys_user_role(user_id,role_id) values(?,? )");
			for(int i=0;i<userIds.length;i++){
				for(int j=0;j<roleIds.length;j++){
					sta.setLong(1, Long.parseLong(userIds[i]));
					sta.setLong(2, Long.parseLong(roleIds[j]));
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			flag=false;
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
	public List getRoleByUser(Long userId){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		List l=new ArrayList();
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("select role_id from sys_user_role where user_id=?");
			sta.setLong(1, userId);
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
	public Map getAllApp(SysUserInfo user){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		Map map=new HashMap();
		try {
			
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select a.id,a.app_name from sys_user_role ur,sys_role_app ra , sys_app a where ur.role_id=ra.role_id and ra.app_id=a.id and ur.user_id="+user.getId());
			while(rs.next()){
				map.put(rs.getString(2), rs.getString(1));
			}	
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return map;
	}
	public String getLastLoginDate(String account){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		String lastLoginDate=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("select lastlogin from sys_user_info where account=? and DELETED is null");
			sta.setString(1, account);
			rs=sta.executeQuery();
			while(rs.next()){
				lastLoginDate=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return lastLoginDate;
	}
	public void updateLastLoginDate(String lastLoginDate,String account){
		Connection con=null;
		PreparedStatement sta=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("update sys_user_info set lastlogin = ? where account=? and DELETED is null");
			sta.setString(1, lastLoginDate);
			sta.setString(2, account);
			sta.execute();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	}
	public String getModifyPasswdDate(String account){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		String modifypasswd=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("select modifypasswd from sys_user_info where account=? and DELETED is null");
			sta.setString(1, account);
			rs=sta.executeQuery();
			while(rs.next()){
				modifypasswd=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return modifypasswd;
	}
	public void updateModifyPasswdDate(String modifypasswd,String account){
		Connection con=null;
		PreparedStatement sta=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("update sys_user_info set modifypasswd = ? where account=? and DELETED is null");
			sta.setString(1, modifypasswd);
			sta.setString(2, account);
			sta.execute();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	}
}
