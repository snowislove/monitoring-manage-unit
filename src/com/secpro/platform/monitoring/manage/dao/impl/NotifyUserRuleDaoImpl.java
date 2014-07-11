package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.NotifyUserRuleDao;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("NotifyUserRuleDaoImpl")
public class NotifyUserRuleDaoImpl extends BaseDao implements NotifyUserRuleDao{
private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean deleteAllNotifyUser(String resId,String ruleId){
		Connection con=null;
		Statement sta=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			sta.execute("delete from notify_user_rule n where n.RES_ID="+resId+" and EVENT_RULE_ID="+ruleId);
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
			flag=false;
		} finally{
			JdbcUtil.close(sta);
			JdbcUtil.close(con);
		}
		return flag;
	}
	public void deleteRelevance(String ruleId){
		Connection con=null;
		Statement sta=null;
		
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			
			sta.execute("delete from notify_user_rule e where e.EVENT_RULE_ID="+ruleId);
			
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
			JdbcUtil.close( sta);
			JdbcUtil.close( con);
		}
	}
}
