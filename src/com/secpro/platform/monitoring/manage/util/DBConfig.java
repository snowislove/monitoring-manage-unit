package com.secpro.platform.monitoring.manage.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
	public static String DRIVER ;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	static{
		
		Properties configuration = new Properties();
		InputStream in=null;
		try {
            in=ApplicationConfiguration.class.getResourceAsStream("/config/jdbc.properties");
            configuration.load(in);
            DRIVER = configuration.getProperty("jdbc.driverClassName");
            URL=configuration.getProperty("jdbc.url");
            USERNAME=configuration.getProperty("jdbc.username");
            PASSWORD=configuration.getProperty("jdbc.password");
        } catch (Exception e) {
            e.printStackTrace();
           
        }finally{
        	if(in!=null){
        		try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
		
	}
	public DBConfig(){
		
	}
}
