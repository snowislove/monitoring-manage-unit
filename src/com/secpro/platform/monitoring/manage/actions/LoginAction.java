package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.entity.UserOldPasswd;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.AccessRuleUtil;
import com.secpro.platform.monitoring.manage.util.MD5Builder;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;

@Controller("LoginAction")
public class LoginAction {
	private SysUserInfoService suiService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysUserInfoService getSuiService() {
		return suiService;
	}
	@Resource(name = "SysUserInfoServiceImpl")
	public void setSuiService(SysUserInfoService suiService) {
		this.suiService = suiService;
	}
	public String login(){
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "HHmmss" );
		SimpleDateFormat sdf2 =   new SimpleDateFormat( "HH:mm:ss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		
		System.out.println(account+"---------------------"+password+"==========="+request.getRemoteAddr()+"======="+request.getRemoteHost()+"=========="+request.getRemoteUser());
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> requestMap=(Map)ctx.get("request");
		
		if(account==null){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			Log log=new Log();
		 	log.setAccount("");
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名为空");
		 	logService.save(log);
			return "loginError";
		}
		if(account.trim().equals("")){
			requestMap.put("loginError", "登录失败，用户名不能为空！");
			Log log=new Log();
		 	log.setAccount("");
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名为空");
		 	logService.save(log);
			return "loginError";
		}
		if(password==null){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，密码为空");
		 	logService.save(log);
			return "loginError";
		}
		if(password.trim().equals("")){
			requestMap.put("loginError", "登录失败，密码不能为空！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，密码为空");
		 	logService.save(log);
			return "loginError";
		}
		
		HashSet sessions=(HashSet)ServletActionContext.getServletContext().getAttribute("sessions");
		
		if(sessions!=null&&sessions.size()>=AccessRuleUtil.maxUser){
			requestMap.put("loginError", "登录失败，系统超过最大用户数！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，系统超过最大用户数");
		 	logService.save(log);
			return "loginError";
		}
		//判断是否已经登录系统
		
		if(sessions!=null){
			for(Object o:sessions){
				HttpSession s=(HttpSession)o;
				SysUserInfo u=(SysUserInfo)s.getAttribute("user");
				
				if(u!=null){
					if(account.equals(u.getAccount())){
						requestMap.put("loginError", "登录失败，用户已经登录！");
						Log log=new Log();
					 	log.setAccount(account);
					 	log.setHandleDate(sdf.format(new Date()));
					 	log.setUserIp(request.getRemoteAddr());
					 	log.setHandleContent("登录失败，用户已经登录！");
					 	logService.save(log);
						return "loginError";
					}
				}
			}
		}
		
		//判断这台电脑是否已经登录了用户
		/*if(sessions!=null){
			for(Object o:sessions){
				HttpSession s=(HttpSession)o;
				String userip=(String)s.getAttribute("userip");
				if(userip!=null){
					if(userip.equals(request.getRemoteAddr())){
						requestMap.put("loginError", "一台客户端只能登录一个用户，登录失败！");
						Log log=new Log();
					 	log.setAccount(account);
					 	log.setHandleDate(sdf.format(new Date()));
					 	log.setUserIp(request.getRemoteAddr());
					 	log.setHandleContent("登录失败，一台客户端只能登录一个用户");
					 	logService.save(log);
						return "loginError";
					}
				}
			}
		}*/
		
		
		
		String passwd=MD5Builder.getMD5(password);
		
		List userList=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.password='"+passwd+"' and u.deleted is null ");
		
		if(userList==null){	
			requestMap.put("loginError", "用户名不存在或密码错误，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			if(oneuser!=null&&oneuser.size()>0){
				String times=(String)request.getSession().getAttribute(account);
				if(times!=null){
					int count=Integer.parseInt(times);
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
						request.getSession().removeAttribute(account);
						requestMap.put("loginError", "密码错误次数太多，用户被锁定！");
						Log log=new Log();
					 	log.setAccount(account);
					 	log.setHandleDate(sdf.format(new Date()));
					 	log.setUserIp(request.getRemoteAddr());
					 	log.setHandleContent("密码错误次数太多，用户被锁定");
					 	logService.save(log);
					 	return "loginError";
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		if(userList.size()==0){			
			requestMap.put("loginError", "用户名不存在或密码错误，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			
			if(oneuser!=null&&oneuser.size()>0){
				
				Object times=request.getSession().getAttribute(account);
				
				if(times!=null){
					Integer count=(Integer)times;
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
						request.getSession().removeAttribute(account);
						requestMap.put("loginError", "密码错误次数太多，用户被锁定！");
						Log log=new Log();
					 	log.setAccount(account);
					 	log.setHandleDate(sdf.format(new Date()));
					 	log.setUserIp(request.getRemoteAddr());
					 	log.setHandleContent("密码错误次数太多，用户被锁定");
					 	logService.save(log);
					 	return "loginError";
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		
		SysUserInfo user=(SysUserInfo)userList.get(0);
		
		
		if(user==null){			
			requestMap.put("loginError", "登录失败用户名或密码不存在，请重新登录！");
			List oneuser=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null and u.enableAccount !='1'");
			if(oneuser!=null&&oneuser.size()>0){
				String times=(String)request.getSession().getAttribute(account);
				if(times!=null){
					int count=Integer.parseInt(times);
					if(count>=PasswdRuleUtil.WrongTimes){
						SysUserInfo u=(SysUserInfo)oneuser.get(0);
						u.setEnableAccount("1");
						suiService.update(u);
					}else{
						request.getSession().setAttribute(account, count+1);
					}
				}else{
					request.getSession().setAttribute(account, 1);
				}
			}
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，用户名不存在或密码错误");
		 	logService.save(log);
			return "loginError";
		}
		if(user.getEnableAccount().equals("1")){
			requestMap.put("loginError", "账号已锁定，请解锁后登录！");
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("登录失败，账号已锁定");
		 	logService.save(log);
			return "loginError";
		}
		//如果开启访问IP和时间控制，则进行判断
				if(AccessRuleUtil.isLimitIp.equals("1")){
					if(!account.equals("admin")){
						String accectIp=request.getRemoteAddr();
						if(user.getWorkIp()!=null&&!user.getWorkIp().equals("")){
							if(!user.getWorkIp().equals(accectIp)){
								requestMap.put("loginError", "账号登录IP与绑定IP不一致，登录失败！");
								Log log=new Log();
							 	log.setAccount(account);
							 	log.setHandleDate(sdf.format(new Date()));
							 	log.setUserIp(request.getRemoteAddr());
							 	log.setHandleContent("账号登录IP与绑定IP不一致，登录失败！");
							 	logService.save(log);
								return "loginError";
							}
						}else{
							requestMap.put("loginError", "未绑定登录IP地址，登录失败！");
							Log log=new Log();
						 	log.setAccount(account);
						 	log.setHandleDate(sdf.format(new Date()));
						 	log.setUserIp(request.getRemoteAddr());
						 	log.setHandleContent("未绑定登录IP地址，登录失败！");
						 	logService.save(log);
							return "loginError";
						}
					}
				}
				try {
					if (AccessRuleUtil.isLimitTime.equals("1")) {
						if (!account.equals("admin")) {
							if (user.getWorkStartTime() != null
									&& !user.getWorkStartTime().equals("")
									&& user.getWorkEndTime() != null
									&& !user.getWorkEndTime().equals("")) {

								String loginTime = sdf1.format(new Date());
								long nowTime = sdf1.parse(loginTime).getTime();
								long startTime = sdf2.parse(user.getWorkStartTime())
										.getTime();
								long endTime = sdf2.parse(user.getWorkEndTime())
										.getTime();
								if (nowTime < startTime || nowTime > endTime) {
									requestMap.put("loginError", "目前不在工作时间内，登录失败！");
									Log log = new Log();
									log.setAccount(account);
									log.setHandleDate(sdf.format(new Date()));
									log.setUserIp(request.getRemoteAddr());
									log.setHandleContent("目前不在工作时间内，登录失败！");
									logService.save(log);
									return "loginError";

								}
							} else {
								requestMap.put("loginError", "未设置工作时间范围，登录失败！");
								Log log = new Log();
								log.setAccount(account);
								log.setHandleDate(sdf.format(new Date()));
								log.setUserIp(request.getRemoteAddr());
								log.setHandleContent("未设置工作时间范围，登录失败！");
								logService.save(log);
								return "loginError";
							}
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//第一次登录要修改密码
		String modifypasswd=suiService.getModifyPasswdDate(account);
		if(modifypasswd==null){
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("第一次登录系统强制修改密码，跳转修改密码页面!");
		 	logService.save(log);
		 	requestMap.put("uaccount", account);
		 	requestMap.put("minlength", PasswdRuleUtil.passwdLong);
		 	return "toResetPasswd";
		}
		if(modifypasswd.trim().equals("")){
			Log log=new Log();
		 	log.setAccount(account);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("第一次登录系统强制修改密码，跳转修改密码页面!");
		 	logService.save(log);
		 	requestMap.put("uaccount", account);
		 	requestMap.put("minlength", PasswdRuleUtil.passwdLong);
		 	return "toResetPasswd";
		}
		//一个月重置一次密码
		try {
			
			int d=diffDate(new Date(),sdf.parse(modifypasswd));
			System.out.println(d+"==============================="+(PasswdRuleUtil.passwdTimeout*30));
			if(PasswdRuleUtil.isPasswdTimeout.equals("1")&&d>(PasswdRuleUtil.passwdTimeout*30)){				
				Log log=new Log();
			 	log.setAccount(account);
			 	log.setHandleDate(sdf.format(new Date()));
			 	log.setUserIp(request.getRemoteAddr());
			 	log.setHandleContent("超过"+(PasswdRuleUtil.passwdTimeout*30)+"天未修改密码，跳转修改密码页面!");
			 	logService.save(log);
			 	requestMap.put("uaccount", account);
			 	requestMap.put("minlength", PasswdRuleUtil.passwdLong);
			 	return "toResetPasswd";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-----------end----------------
		/*String lastLoginDate=suiService.getLastLoginDate(account);
		
		if(lastLoginDate==null){
			suiService.updateLastLoginDate(sdf.format(new Date()), account);
		}else{
			try {
				
				int d=diffDate(new Date(),sdf.parse(lastLoginDate));
				
				if(PasswdRuleUtil.isPasswdTimeout.equals("1")&&d>(PasswdRuleUtil.passwdTimeout*30)){				
					Log log=new Log();
				 	log.setAccount(account);
				 	log.setHandleDate(sdf.format(new Date()));
				 	log.setUserIp(request.getRemoteAddr());
				 	log.setHandleContent("超过"+(PasswdRuleUtil.passwdTimeout*30)+"天未登录，跳转修改密码页面!");
				 	logService.save(log);
				 	requestMap.put("uaccount", account);
				 	requestMap.put("minlength", PasswdRuleUtil.passwdLong);
				 	return "toResetPasswd";
				}else{
					suiService.updateLastLoginDate(sdf.format(new Date()), account);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				suiService.updateLastLoginDate(sdf.format(new Date()), account);
			}
		}*/
		
		Map logApp=logService.getLogApp();
		user.setApp(suiService.getAllApp(user));
		request.getSession().setAttribute("user", user);
	 	request.getSession().setAttribute("appLog", logApp);
	 	request.getSession().setAttribute("userip", request.getRemoteAddr());
	 	Log log=new Log(); 
	 	log.setAccount(user.getAccount());
	 	log.setHandleDate(sdf.format(new Date()));
	 	log.setUserIp(request.getRemoteAddr());
	 	log.setHandleContent("登录系统,成功");
	 	logService.save(log);
	 	//增加用户登录的当前时间
	 	request.getSession().setAttribute("lastLoginTime", System.currentTimeMillis());
	 	if(request.getSession().getAttribute(account)!=null){
	 		request.getSession().removeAttribute(account);
	 	}
		return "success";
	}
	public String logout(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("lastLoginTime");
		request.getSession().invalidate();
		SysLogUtil.saveLog(logService, "系统注销成功！", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	private int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
	private long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	public void checkOldPasswd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String uaccount = request.getParameter("uaccount");
		String oldpasswd = request.getParameter("oldpasswd");
		
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			if (uaccount == null||oldpasswd==null) {
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			if (uaccount.trim().equals("")||oldpasswd.trim().equals("")) {
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			String passwd=MD5Builder.getMD5(oldpasswd);			
			List userList=suiService.queryAll("from SysUserInfo u where u.account='"+uaccount+"' and u.password='"+passwd+"' and u.deleted is null ");
			if(userList!=null&&userList.size()>0){
				JSONObject json=new JSONObject();
				json.put("checkok", "ok");
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}else{	
				pw.println(new JSONObject().toString());
				pw.flush();
				return;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void checkNewPasswd(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String newpasswd = request.getParameter("newpasswd");
		String account=request.getParameter("uaccount");
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			JSONObject json=new JSONObject();
			if (newpasswd == null) {
				json.put("checkok", "0");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(newpasswd.trim().length()<PasswdRuleUtil.passwdLong){
				json.put("checkok", "1");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			if(PasswdRuleUtil.hasNum.equals("1")){
				if(!isDigit(newpasswd)){
					json.put("checkok", "2");	
					System.out.println(json.toString());
					pw.println(json.toString());
					pw.flush();
					return;
				}
			}
			if(PasswdRuleUtil.hasChar.equals("1")){
				if(!isYing(newpasswd)){
					json.put("checkok", "3");	
					System.out.println(json.toString());
					pw.println(json.toString());
					pw.flush();
					return;
				}
			}
			if(PasswdRuleUtil.hasSpecialChar.equals("1")){
				if(!isConSpeCharacters(newpasswd)){
					json.put("checkok", "4");
					System.out.println(json.toString());
					pw.println(json.toString());
					pw.flush();
					return;
				}
			}
			if(newpasswd.trim().length()>PasswdRuleUtil.maxPwdLong){
				json.put("checkok", "5");	
				System.out.println(json.toString());
				pw.println(json.toString());
				pw.flush();
				return;
			}
			List userList=suiService.queryAll("from SysUserInfo u where u.account='"+account+"' and u.deleted is null ");
			SysUserInfo userInfo=(SysUserInfo)userList.get(0);
			
			List oldPasswdList=suiService.queryAll("from UserOldPasswd u where u.userId="+userInfo.getId()+" order by u.createDate desc");
			if(oldPasswdList!=null){
				String md5Passwd=MD5Builder.getMD5(newpasswd);
				long size=0l;
				if(oldPasswdList.size()>PasswdRuleUtil.passwdRepeatNum){
					size=PasswdRuleUtil.passwdRepeatNum;
				}else{
					size=oldPasswdList.size();
				}
				for(int i=0;i<size;i++){
					UserOldPasswd oldPasswd=(UserOldPasswd)oldPasswdList.get(i);
					
					if(md5Passwd.trim().equals(oldPasswd.getPassword())){
						json.put("checkok", "6");
						System.out.println(json.toString());
						pw.println(json.toString());
						pw.flush();
						return;
					}
				}
			}
			json.put("checkok", "ok");
			System.out.println(json.toString());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
			
	}
	public boolean isDigit(String strNum) {  
		 return strNum.matches(".*?\\d.*?"); 
	}
	public boolean isYing(String str) {  
		 return str.matches(".*\\p{Alpha}.*"); 
	}
	private boolean isConSpeCharacters(String string) {
		  // TODO Auto-generated method stub
		  if(string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*\\s*", "").length()==0){
		   //如果不包含特殊字符
		   return  false;
		  }
		  return true;
	 }
	
}
