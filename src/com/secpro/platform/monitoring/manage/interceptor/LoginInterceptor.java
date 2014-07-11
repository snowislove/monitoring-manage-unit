package com.secpro.platform.monitoring.manage.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;

public class LoginInterceptor extends AbstractInterceptor {
	
	 
	 @Override
	 public String intercept(ActionInvocation invocation) throws Exception {
		 	// BaseAction baseAction = (BaseAction)invocation.getAction();
	       // Map<String, Object> sessionMap = baseAction.getSessionMap();        
		 	ActionContext ctx = ActionContext.getContext();
		 	Map<String,Object> sessionMap = ctx.getSession();
		 	SysUserInfo user = (SysUserInfo)sessionMap.get("user") ;
	        if(user == null){
	        	Map<String,Object> requestMap=(Map)ctx.get("request");
	        	requestMap.put("loginError", "请先登录!");
	            return "login.action";
	        }
	        else{
	            return invocation.invoke();
	        }            
	    }

}
