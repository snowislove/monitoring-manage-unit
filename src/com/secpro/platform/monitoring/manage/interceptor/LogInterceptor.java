package com.secpro.platform.monitoring.manage.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;

public class LogInterceptor extends AbstractInterceptor {
	
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	 
	 @Override
	 public String intercept(ActionInvocation invocation) throws Exception {
		 
		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		 HttpServletRequest request=ServletActionContext.getRequest();
		
		 HttpSession session = request.getSession(false);
		 if(session!=null){
			 Map logApp=(Map)session.getAttribute("appLog");
			 SysUserInfo user=(SysUserInfo)session.getAttribute("user");
			
			 if(logApp!=null&&user!=null){
				
				 String path=this.pathUtil(request.getRequestURI());
				
				 String desc=(String)logApp.get(path);
				
				 if(desc!=null){
					 Log log=new Log();
					 log.setAccount(user.getAccount());
					 log.setUserIp(request.getRemoteAddr());
					 log.setHandleContent(desc);
					 log.setHandleDate(sdf.format(new Date()));
					 logService.save(log);
				 }
			 }
		 }
	     return invocation.invoke();
	                 
	  }
	 public String pathUtil(String path){
		 int i=path.lastIndexOf("/");
		 int j=path.indexOf("?");
		 
		 String s="";
		 if(j<0){
			 s=path.substring(i+1,path.length());
		 }else{
			 s=path.substring(i+1, j);
		 }
		 
		 return s;
	 }
	
}
