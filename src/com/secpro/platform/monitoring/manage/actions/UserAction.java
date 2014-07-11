package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.entity.UserOldPasswd;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysOrgService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.MD5Builder;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("UserAction")
public class UserAction {
	PlatformLogger logger = PlatformLogger.getLogger(UserAction.class);
	private String returnMsg;
	private String backUrl;
	private SysUserInfoService suiService;
	private SysUserInfo user;
	private SysOrgService orgService;
	private SysLogService logService;
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysOrgService getOrgService() {
		return orgService;
	}
	@Resource(name = "SysOrgServiceImpl")
	public void setOrgService(SysOrgService orgService) {
		this.orgService = orgService;
	}
	public SysUserInfoService getSuiService() {
		return suiService;
	}
	@Resource(name = "SysUserInfoServiceImpl")
	public void setSuiService(SysUserInfoService suiService) {
		this.suiService = suiService;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	public SysUserInfo getUser() {
		return user;
	}
	public void setUser(SysUserInfo user) {
		this.user = user;
	}
	public void viewUserInfo(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List allUsers=suiService.queryAll("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email,s.workIp,s.workStartTime,s.workEndTime from SysUserInfo s ,SysOrg o where s.orgId=o.id and s.deleted is null");
		List users=suiService.queryByPage("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email,s.workIp,s.workStartTime,s.workEndTime from SysUserInfo s ,SysOrg o where s.orgId=o.id and s.deleted is null",pageNum,maxPage);
		
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allUsers == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看用户列表,失败", loguser.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			
			json.put("total", allUsers.size());
			json.put("rows", arr);
			for (int i = 0; i < users.size(); i++) {
				Object obj[]=(Object[])users.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("userid", obj[0]);
				
				sub.put("account", obj[1]);
				
				sub.put("cdate", sdf1.format(sdf.parse((String)obj[2])));
				
				sub.put("mobelTel", (obj[3]==null?"":obj[3]));
				
				sub.put("officeTel", (obj[4]==null?"":obj[4]));
				
				sub.put("enableAccount", (obj[5].equals("0")?"启用":"暂停"));
				
				sub.put("userDesc", (obj[6]==null?" ":obj[6]));
				
				sub.put("userName", (obj[9]==null?" ":obj[9]));
				
				sub.put("email", (obj[10]==null?" ":obj[10]));
				
				sub.put("workIp", (obj[11]==null?" ":obj[11]));
				
				sub.put("startTime", (obj[12]==null?" ":obj[12]));
				
				sub.put("endTime", (obj[13]==null?" ":obj[13]));
				
				sub.put("orgid",obj[7]);
				sub.put("orgName",obj[8]);
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看用户列表,成功", loguser.getAccount(), request.getRemoteAddr());
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
	public void viewUserInRoleMapping(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List allUsers=suiService.queryAll("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email from SysUserInfo s ,SysOrg o where s.account!='admin' and s.account!='shenji1' and s.account!='shenji2' and s.orgId=o.id and s.deleted is null");
		List users=suiService.queryByPage("select s.id,s.account,s.cdate,s.mobelTel,s.officeTel,s.enableAccount,s.userDesc,o.id,o.orgName,s.userName,s.email from SysUserInfo s ,SysOrg o where s.account!='admin' and s.account!='shenji1' and s.account!='shenji2' and s.orgId=o.id and s.deleted is null",pageNum,maxPage);
		
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allUsers == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				pw.flush();
				return;
			}
			json.put("total", allUsers.size());
			json.put("rows", arr);
			for (int i = 0; i < users.size(); i++) {
				Object obj[]=(Object[])users.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("userid", obj[0]);
				
				sub.put("account", obj[1]);
				
				sub.put("cdate", sdf1.format(sdf.parse((String)obj[2])));
				
				sub.put("mobelTel", (obj[3]==null?"":obj[3]));
				
				sub.put("officeTel", (obj[4]==null?"":obj[4]));
				
				sub.put("enableAccount", (obj[5].equals("0")?"启用":"暂停"));
				
				sub.put("userDesc",  (obj[6]==null?" ":obj[6]));
				
				sub.put("userName", (obj[9]==null?" ":obj[9]));
				
				sub.put("email",(obj[10]==null?" ":obj[10]));
				
				sub.put("orgid", obj[7]);
				
				sub.put("orgName", obj[8]);
				arr.put(sub);
			}
			
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
	public String saveUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		if(user.getAccount()==null){
			returnMsg = "用户账号不能为空，保存失败！";
			logger.info("fetch account failed , account is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getAccount().trim().equals("")){
			returnMsg = "用户账号不能为空，保存失败！";
			logger.info("fetch account failed , account is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		List ulist=suiService.queryAll("from SysUserInfo u where u.account='"+user.getAccount()+"'");
		if(ulist!=null&&ulist.size()>0){
			returnMsg = "账号已存在，保存失败！";
			logger.info("账号已经存在，保存失败 !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(user.getPassword()==null){
			returnMsg = "密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getEnableAccount()==null){
			returnMsg = "用户启停状态不能为空，保存失败！";
			logger.info("fetch EnableAccount failed , EnableAccount is null!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		
		
		
		
			if(user.getIp1()!=null&&!user.getIp1().trim().equals("")&&user.getIp2()!=null&&!user.getIp2().trim().equals("")
					&&user.getIp3()!=null&&!user.getIp3().trim().equals("")&&user.getIp4()!=null&&!user.getIp4().trim().equals("")){
				user.setWorkIp(user.getIp1().trim()+"."+user.getIp2().trim()+"."+user.getIp3().trim()+"."+user.getIp4());
			}else{
				returnMsg = "请填写访问IP地址！";
				logger.info("fetch ip failed , ip is null!");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
				
		
		
			if(user.getHourStart()!=null&&!user.getHourStart().trim().equals("")&&user.getMinuteStart()!=null&&!user.getMinuteStart().trim().equals("")
					&&user.getHourEnd()!=null&&!user.getHourEnd().trim().equals("")&&user.getMinuteEnd()!=null&&!user.getMinuteEnd().trim().equals("")){
				user.setWorkStartTime(user.getHourStart()+":"+user.getMinuteStart()+":00");
				user.setWorkEndTime(user.getHourEnd()+":"+user.getMinuteEnd()+":00");
			}else{
				returnMsg = "请填写访问时间！";
				logger.info("fetch worktime failed , worktime is null!");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "创建新用户,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
		
		user.setCdate(sdf.format(new Date()));
		user.setPassword(MD5Builder.getMD5(user.getPassword()));
		suiService.save(user);
		UserOldPasswd uop=new UserOldPasswd();
		uop.setPassword(user.getPassword());
		uop.setCreateDate(sdf.format(new Date()));
		uop.setUserId(user.getId());
		suiService.save(uop);
		SysLogUtil.saveLog(logService, "创建新用户,成功", loguser.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		if(userid==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "跳转修改用户页面,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(userid.trim().equals("")){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "跳转修改用户页面,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysUserInfo muser=(SysUserInfo)suiService.getObj(SysUserInfo.class, Long.parseLong(userid));
		
		if(muser==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch user failed from database !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "跳转修改用户页面,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(muser.getDeleted()!=null&&muser.getDeleted().equals("1")){
			returnMsg = "用户不存在，跳转页面失败！";
			logger.info("fetch user failed from database !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "跳转修改用户页面,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		SysOrg org=(SysOrg)orgService.getObj(SysOrg.class, muser.getOrgId());
		String ip=muser.getWorkIp();
		if(ip!=null&&!ip.trim().equals("")){
			String[] ips=ip.split("\\.");
			muser.setIp1(ips[0]);
			muser.setIp2(ips[1]);
			muser.setIp3(ips[2]);
			muser.setIp4(ips[3]);
		}else{
			muser.setIp1("");
			muser.setIp2("");
			muser.setIp3("");
			muser.setIp4("");
		}
		String workStartTime=muser.getWorkStartTime();
		String workEndTime=muser.getWorkEndTime();
		if(workStartTime!=null&&!workStartTime.trim().equals("")){
			String startTime[]=workStartTime.split(":");
			muser.setHourStart(startTime[0]);
			muser.setMinuteStart(startTime[1]);
		}else{
			muser.setHourStart("00");
			muser.setMinuteStart("00");
		}
		if(workEndTime!=null&&!workEndTime.trim().equals("")){
			String endTime[]=workEndTime.split(":");
			muser.setHourEnd(endTime[0]);
			muser.setMinuteEnd(endTime[1]);
		}else{
			muser.setHourEnd("00");
			muser.setHourEnd("00");
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("muser", muser);
		requestMap.put("org", org);
		SysLogUtil.saveLog(logService, "跳转修改用户页面,成功", loguser.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String modifyUser(){
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String passwd=request.getParameter("passwd");
		String orgid=request.getParameter("orgid");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		if(passwd==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwd.trim().equals("")){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is '' !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getId()==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		Long orgId=0L;
		
		if(user.getOrgId()!=null){
			orgId=user.getOrgId();
		}else{
			if(orgid==null){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			if(orgid.equals("")){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			orgId=Long.parseLong(orgid);
		}
		SysUserInfo u=(SysUserInfo)suiService.getObj(SysUserInfo.class, user.getId());
		if(user.getPassword()==null){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		boolean flag=false;
		if(!user.getPassword().equals("123!a45678901234")){
			String npaswd=MD5Builder.getMD5(user.getPassword());
			u.setPassword(npaswd);
			Date d=new Date();
			List oldPasswdList=suiService.queryAll("from UserOldPasswd u where u.userId="+user.getId()+" order by u.createDate");
			if(oldPasswdList!=null){
				if(oldPasswdList.size()>(PasswdRuleUtil.passwdRepeatNum-1)){
					suiService.delete(oldPasswdList.get(0));
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}else{
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}
			}else{
				UserOldPasswd uop=new UserOldPasswd();
				uop.setPassword(npaswd);
				uop.setCreateDate(sdf.format(d));
				uop.setUserId(u.getId());
				suiService.save(uop);
			}
			flag=true;
			suiService.updateModifyPasswdDate("", u.getAccount());
		}
		
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setUserName(user.getUserName());
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setMobelTel(user.getMobelTel());
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setEmail(user.getEmail());
		/*if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "viewUserInfo.jsp";
			return "failed";
		}*/
		u.setOrgId(orgId);
		if(user.getEnableAccount()==null){
			returnMsg = "用户启停状态不能为空，保存失败！";
			logger.info("fetch EnableAccount failed , EnableAccount is null!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
			if(user.getIp1()!=null&&!user.getIp1().trim().equals("")&&user.getIp2()!=null&&!user.getIp2().trim().equals("")
					&&user.getIp3()!=null&&!user.getIp3().trim().equals("")&&user.getIp4()!=null&&!user.getIp4().trim().equals("")){
				u.setWorkIp(user.getIp1().trim()+"."+user.getIp2().trim()+"."+user.getIp3().trim()+"."+user.getIp4());
			}else{
				returnMsg = "请填写访问IP地址！";
				logger.info("fetch ip failed , ip is null!");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
				
		
		
			if(user.getHourStart()!=null&&!user.getHourStart().trim().equals("")&&user.getMinuteStart()!=null&&!user.getMinuteStart().trim().equals("")
					&&user.getHourEnd()!=null&&!user.getHourEnd().trim().equals("")&&user.getMinuteEnd()!=null&&!user.getMinuteEnd().trim().equals("")){
				u.setWorkStartTime(user.getHourStart()+":"+user.getMinuteStart()+":00");
				u.setWorkEndTime(user.getHourEnd()+":"+user.getMinuteEnd()+":00");
			}else{
				returnMsg = "请填写访问时间！";
				logger.info("fetch worktime failed , worktime is null!");
				backUrl = "users/viewUser.jsp";
				SysLogUtil.saveLog(logService, "修改用户信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
		
		u.setUserDesc(user.getUserDesc());
		u.setEnableAccount(user.getEnableAccount());
		u.setOfficeTel(user.getOfficeTel());
		u.setMdate(sdf.format(new Date()));
		suiService.update(u);
		
		SysLogUtil.saveLog(logService, "修改用户信息,成功", loguser.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String deleteUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		if(userid==null){
			returnMsg = "系统错误，用户删除失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "删除用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(userid.trim().equals("")){
			returnMsg = "系统错误，用户删除失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "删除用户,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] userids=userid.split(",");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		for(int i=0;i<userids.length;i++){
			SysUserInfo usi=(SysUserInfo)suiService.getObj(SysUserInfo.class, Long.parseLong(userids[i]));
			if(usi!=null){
				usi.setDeleted("1");
				usi.setDeleteDate(sdf.format(new Date()));
				usi.setOrgId(null);
				suiService.update(usi);
				
			}
		}
		SysLogUtil.saveLog(logService, "删除用户,成功", loguser.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String userRoleMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String[] userids=request.getParameterValues("userid");
		String[] roleids=request.getParameterValues("roleid");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		if(userids==null){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("fetch userid failed , userid is null!");
			backUrl = "users/userRoleMapping.jsp";
			SysLogUtil.saveLog(logService, "用户和角色做关联,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(roleids==null){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "users/userRoleMapping.jsp";
			SysLogUtil.saveLog(logService, "用户和角色做关联,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		boolean flag =suiService.createUserRoleMapping(userids, roleids);
		if(!flag){
			returnMsg = "系统错误，用户角色映射失败！";
			logger.info("insert database error!");
			backUrl = "users/userRoleMapping.jsp";
			SysLogUtil.saveLog(logService, "用户和角色做关联,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "用户和角色做关联,成功", loguser.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void getRoleByUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String userid=request.getParameter("userid");
		System.out.println(userid+"----------------------");
		if(userid==null){
			return ;
		}
		if(userid.trim().equals("")){
			return ;
		}
		orgService.queryAll("from SysOrg o where o.parent_id=1");
		List roleList=suiService.getRoleByUser(Long.parseLong(userid));
		String roleids="";
		for(int i=0;i<roleList.size();i++){
			if(i!=roleList.size()-1){
				roleids=roleids+roleList.get(i)+",";
			}else{
				roleids=roleids+roleList.get(i);
			}
		}
		String json="{\"roleid\":\""+roleids+"\"}";
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(json);
			pw.flush();
			System.out.println(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public String modifySelf(){
		
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String passwd=request.getParameter("passwd");
		String orgid=request.getParameter("orgid");
		SysUserInfo loguser=(SysUserInfo)request.getSession().getAttribute("user");
		if(passwd==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is null !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwd.trim().equals("")){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch passwd failed , passwd is '' !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getId()==null){
			returnMsg = "系统错误，用户修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		Long orgId=0L;
		
		if(user.getOrgId()!=null){
			orgId=user.getOrgId();
		}else{
			if(orgid==null){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "first.jsp";
				SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			if(orgid.equals("")){
				returnMsg = "用户部门不能为空，保存失败！";
				logger.info("fetch orgId failed , orgId is null !");
				backUrl = "first.jsp";
				SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			orgId=Long.parseLong(orgid);
		}
		SysUserInfo u=(SysUserInfo)suiService.getObj(SysUserInfo.class, user.getId());
		
		if(user.getPassword()==null){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is null !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getPassword().trim().equals("")){
			returnMsg = "用户密码不能为空，保存失败！";
			logger.info("fetch password failed , password is ''!");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		boolean flag=false;
		if(!user.getPassword().equals("123!a45678901234")){
			String npaswd=MD5Builder.getMD5(user.getPassword());
			u.setPassword(npaswd);
			List oldPasswdList=suiService.queryAll("from UserOldPasswd u where u.userId="+u.getId()+" order by u.createDate");
			u.setPassword(npaswd);
			Date d=new Date();
			u.setMdate(sdf.format(d));
			if(oldPasswdList!=null){
				if(oldPasswdList.size()>(PasswdRuleUtil.passwdRepeatNum-1)){
					suiService.delete(oldPasswdList.get(0));
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}else{
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}
			}else{
				UserOldPasswd uop=new UserOldPasswd();
				uop.setPassword(npaswd);
				uop.setCreateDate(sdf.format(d));
				uop.setUserId(u.getId());
				suiService.save(uop);
			}
			flag=true;
		}
		if(user.getUserName()==null){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is null !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getUserName().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch username failed , username is ''!");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setUserName(user.getUserName());
		if(user.getMobelTel()==null){
			returnMsg = "手机号不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is null !");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getMobelTel().trim().equals("")){
			returnMsg = "用户名不能为空，保存失败！";
			logger.info("fetch mobelTel failed , mobelTel is ''!");
			backUrl = "first.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setMobelTel(user.getMobelTel());
		if(user.getEmail()==null){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is null !");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(user.getEmail().trim().equals("")){
			returnMsg = "邮箱不能为空，保存失败！";
			logger.info("fetch email failed , email is ''!");
			backUrl = "users/viewUser.jsp";
			SysLogUtil.saveLog(logService, "用户修改个人信息,失败", loguser.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		u.setEmail(user.getEmail());
		/*if(user.getOrgId()==null){
			returnMsg = "用户组织ID不能为空，保存失败！";
			logger.info("fetch orgid failed , orgid is null!");
			backUrl = "viewUserInfo.jsp";
			return "failed";
		}*/
		u.setOrgId(orgId);
		
		u.setUserDesc(user.getUserDesc());
		u.setOfficeTel(user.getOfficeTel());
		u.setMdate(sdf.format(new Date()));
		
		suiService.update(u); 
		//如果修改密码，则更新最后更新时间。
		if(flag){
			suiService.updateModifyPasswdDate(sdf.format(new Date()), u.getAccount());
		}
		HttpSession session=request.getSession();
		SysUserInfo olduser=(SysUserInfo)session.getAttribute("user");
		Map app=olduser.getApp();
		u.setApp(app);
		session.setAttribute("user", u);
		SysLogUtil.saveLog(logService, "用户修改个人信息,成功 ", loguser.getAccount(), request.getRemoteAddr());
		return "success";
		
	}
	public String resetPasswd(){
		
		HttpServletRequest request=ServletActionContext.getRequest();
		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String uaccount=request.getParameter("uaccount");
		String oldpasswd=request.getParameter("oldpasswd");
		String newpaswd=request.getParameter("newpasswd");
		ActionContext ctx = ActionContext.getContext();
		Map<String,Object> requestMap=(Map)ctx.get("request");
		requestMap.put("uaccount", uaccount);
		if (uaccount == null||oldpasswd==null||newpaswd==null) {
			logger.info("account or oldpassword or rnewpassword is null!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("用户名或密码为空,修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户名或密码不能为空，修改密码失败!");
			return "resetError";
		}
		
		if (uaccount.trim().equals("")||oldpasswd.trim().equals("")||newpaswd.trim().equals("")) {
			logger.info("account or oldpassword or rnewpassword is ''!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("用户名或密码为空,修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户名或密码不能为空，修改密码失败!");
			return "resetError";
		}
		if(oldpasswd.trim().equals(newpaswd.toString())){
			logger.info("oldpassword the same as newpassword !");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("新旧密码相同,修改密码失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "新旧密码不能相同，修改密码失败！");
			return "resetError";
		}
		if(PasswdRuleUtil.hasNum.equals("1")){
			if(!isDigit(newpaswd)){
				logger.info("new passwd must contain num !");
				Log log=new Log();
			 	log.setAccount(uaccount);
			 	log.setHandleDate(sdf.format(new Date()));
			 	log.setUserIp(request.getRemoteAddr());
			 	log.setHandleContent("新密码必须有数字，修改密码失败");
			 	logService.save(log);
			 	requestMap.put("loginError", "新密码必须有一个数字，修改密码失败！");
				return "resetError";
			}
		}
		if(PasswdRuleUtil.hasChar.equals("1")){
			if(!isYing(newpaswd)){
				logger.info("new passwd must contain zimu !");
				Log log=new Log();
			 	log.setAccount(uaccount);
			 	log.setHandleDate(sdf.format(new Date()));
			 	log.setUserIp(request.getRemoteAddr());
			 	log.setHandleContent("新密码必须有字母，修改密码失败");
			 	logService.save(log);
			 	requestMap.put("loginError", "新密码必须有一个字母，修改密码失败！");
				return "resetError";
			}
		}
		if(PasswdRuleUtil.hasSpecialChar.equals("1")){
			if(!isConSpeCharacters(newpaswd)){
				logger.info("new passwd must contain zimu !");
				Log log=new Log();
			 	log.setAccount(uaccount);
			 	log.setHandleDate(sdf.format(new Date()));
			 	log.setUserIp(request.getRemoteAddr());
			 	log.setHandleContent("新密码必须有特殊字符,修改密码失败");
			 	logService.save(log);
			 	requestMap.put("loginError", "新密码必须有一个特殊字符，修改密码失败！");
				return "resetError";
			}
		}
		String passwd=MD5Builder.getMD5(oldpasswd);	
		List userList=suiService.queryAll("from SysUserInfo u where u.account='"+uaccount+"' and u.password='"+passwd+"' and u.deleted is null ");
		
		if(userList!=null&&userList.size()>0){
			SysUserInfo u=(SysUserInfo)userList.get(0);
			List oldPasswdList=suiService.queryAll("from UserOldPasswd u where u.userId="+u.getId()+" order by u.createDate");
			String npaswd=MD5Builder.getMD5(newpaswd);
			u.setPassword(npaswd);
			Date d=new Date();
			u.setMdate(sdf.format(d));
			if(oldPasswdList!=null){
				if(oldPasswdList.size()>(PasswdRuleUtil.passwdRepeatNum-1)){
					suiService.delete(oldPasswdList.get(0));
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}else{
					UserOldPasswd uop=new UserOldPasswd();
					uop.setPassword(npaswd);
					uop.setCreateDate(sdf.format(d));
					uop.setUserId(u.getId());
					suiService.save(uop);
				}
			}else{
				UserOldPasswd uop=new UserOldPasswd();
				uop.setPassword(npaswd);
				uop.setCreateDate(sdf.format(d));
				uop.setUserId(u.getId());
				suiService.save(uop);
			}
			suiService.update(u);
			suiService.updateLastLoginDate(sdf.format(new Date()), uaccount);
			//第一次登录修改密码
			suiService.updateModifyPasswdDate(sdf.format(new Date()), uaccount);
			
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码,成功");
		 	logService.save(log);
		 	requestMap.put("loginError", "密码修改成功！");
			return "success";
		}else{
			logger.info("account is not exist!");
			Log log=new Log();
		 	log.setAccount(uaccount);
		 	log.setHandleDate(sdf.format(new Date()));
		 	log.setUserIp(request.getRemoteAddr());
		 	log.setHandleContent("修改密码,失败");
		 	logService.save(log);
		 	requestMap.put("loginError", "用户不存在，修改失败！");
			return "resetError";
		}
	}
	public void checkPasswd(){
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
			if (account != null && !account.trim().equals("")) {
				List userList = suiService
						.queryAll("from SysUserInfo u where u.account='"
								+ account + "' and u.deleted is null ");
				SysUserInfo userInfo = (SysUserInfo) userList.get(0);

				List oldPasswdList = suiService
						.queryAll("from UserOldPasswd u where u.userId="
								+ userInfo.getId()
								+ " order by u.createDate desc");
				if (oldPasswdList != null) {
					String md5Passwd = MD5Builder.getMD5(newpasswd);
					long size = 0l;
					if (oldPasswdList.size() > PasswdRuleUtil.passwdRepeatNum) {
						size = PasswdRuleUtil.passwdRepeatNum;
					} else {
						size = oldPasswdList.size();
					}
					for (int i = 0; i < size; i++) {
						UserOldPasswd oldPasswd = (UserOldPasswd) oldPasswdList
								.get(i);

						if (md5Passwd.trim().equals(oldPasswd.getPassword())) {
							json.put("checkok", "6");
							System.out.println(json.toString());
							pw.println(json.toString());
							pw.flush();
							return;
						}
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
	public static void main(String[] args){
		UserAction i=new UserAction();
		System.out.println(i.isDigit("SjzFw@TES1T"));
		String passwd=MD5Builder.getMD5("SjzFw@TEST");
		System.out.println(passwd);
	}
}
