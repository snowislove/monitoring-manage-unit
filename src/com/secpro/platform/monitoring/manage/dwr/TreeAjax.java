package com.secpro.platform.monitoring.manage.dwr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.secpro.platform.monitoring.manage.util.DBConfig;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Service("TreeAjax")
public class TreeAjax {
	PlatformLogger logger=PlatformLogger.getLogger(TreeAjax.class);
	
	private ComboPooledDataSource dateSource;
	
	public ComboPooledDataSource getDateSource() {
		return dateSource;
	}
	@Resource(name="dataSource")
	public void setDateSource(ComboPooledDataSource dateSource) {
		this.dateSource = dateSource;
	}

	public List<String> getAllTreeNodeStatus(){
		Connection conn=null;
		Statement stm = null;
	    ResultSet rs = null;
	    List<String> nodeStatus=new ArrayList<String>();
	    try {
	    	logger.debug("getAllTreeNodeStatus");
			conn=this.getConnection();
			stm=conn.createStatement();
			rs=stm.executeQuery("select max(s1.city_code),s2.res_id,max(s2.event_level) from sys_res_obj s1,sys_event s2 where s1.res_paused='0' and s1.id=s2.res_id group by s2.res_id");
			while(rs.next()){
				nodeStatus.add(rs.getString(1)+"_"+rs.getString(2)+"&"+rs.getString(3));
			}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(stm!=null){
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    
		return nodeStatus;
	}
	
	public Connection getConnection() {
		/*ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
		ComboPooledDataSource dateSource = (ComboPooledDataSource)ctx.getBean("dataSource");
		Connection conn=null;
		try {
			if(dateSource!=null){
				conn= dateSource.getConnection();
			}else{
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.138.128:1521:unionmon","fcvst","fcvst");
				return conn;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.138.128:1521:unionmon","fcvst","fcvst");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} */
		Connection conn=null;
		try {
			conn=dateSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("fetch datesource error", e);
			try {
				Class.forName(DBConfig.DRIVER);
				conn=DriverManager.getConnection(DBConfig.URL,DBConfig.USERNAME,DBConfig.PASSWORD);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		return conn;
	}	
	public List<String> getpausedTreeNode(){
		Connection conn=null;
		Statement stm = null;
	    ResultSet rs = null;
	    List<String> pausedNodes=new ArrayList<String>();
	    try {
	    	logger.debug("getpausedTreeNode");
			conn=this.getConnection();
			stm=conn.createStatement();
			rs=stm.executeQuery("select city_code,id from sys_res_obj where res_paused='1' ");
			while(rs.next()){
				pausedNodes.add(rs.getString(1)+"_"+rs.getString(2));
			}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(stm!=null){
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    
		return pausedNodes;
	}
}
