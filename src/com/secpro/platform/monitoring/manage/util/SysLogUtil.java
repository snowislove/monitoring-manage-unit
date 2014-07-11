package com.secpro.platform.monitoring.manage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.services.SysLogService;

public class SysLogUtil {
	public static void saveLog(SysLogService logService ,String content,String user,String ip){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		Log log=new Log();
		log.setAccount(user);
		log.setHandleDate(sdf.format(new Date()));
		log.setUserIp(ip);
		log.setHandleContent(content);
		if(logService!=null){
			logService.save(log);
		}
	}
}
