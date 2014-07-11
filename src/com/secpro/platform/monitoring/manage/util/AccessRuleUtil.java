package com.secpro.platform.monitoring.manage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccessRuleUtil {
	public static long maxUser=150l;
	public static String isLimitIp="0";
	public static String isLimitTime="0";
	public static long accessTimeOut=15l;
	static {
		Connection conn=null;
		Statement sta=null;
		ResultSet rs=null;
		try{	
			Class.forName(DBConfig.DRIVER);
			conn=DriverManager.getConnection(DBConfig.URL,DBConfig.USERNAME,DBConfig.PASSWORD);
			sta=conn.createStatement();
			rs=sta.executeQuery("select MAXUSER,isLimitIp,isLimitTime,access_timeout from SYS_ACCESS_RULE");
			if(rs.next()){
				maxUser=rs.getLong(1);
				isLimitIp=rs.getString(2);
				isLimitTime=rs.getString(3);		
				accessTimeOut=rs.getLong(4);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sta!=null){
				try {
					sta.close();
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
	}
	public AccessRuleUtil(){
		
	}
}
