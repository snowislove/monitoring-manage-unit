package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.EventTypeDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;
@Repository("EventTypeDaoImpl")
public class EventTypeDaoImpl extends BaseDao implements EventTypeDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public void deleteRelevance(String eventId){
		Connection con=null;
		Statement sta=null;
		PreparedStatement sta1=null;
		ResultSet rs=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			rs=sta.executeQuery("select id from  sys_event_rule r where r.EVENT_TYPE_ID="+eventId);
			while(rs.next()){
				sta1=con.prepareStatement("delete from notify_user_rule where event_rule_id=?");
				sta1.setLong(1, rs.getLong(1));
				sta1.execute();
			}
			sta.close();
			sta=null;
			rs.close();
			rs=null;
			sta1.close();
			sta1=null;
			sta=con.createStatement();
			
			sta.execute("delete from sys_event_rule e where e.event_type_id="+eventId);
			sta.close();
			sta=null;
			
			sta=con.createStatement();
			sta.execute("delete from event_msg e where e.event_type_id="+eventId);
			sta.close();
			sta=null;
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
			
			JdbcUtil.close( con,sta,rs);
		}
	}
}
