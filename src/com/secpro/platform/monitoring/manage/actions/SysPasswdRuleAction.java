package com.secpro.platform.monitoring.manage.actions;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.SysPasswdRule;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysPasswdRuleService;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("SysPasswdRuleAction")
public class SysPasswdRuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysPasswdRuleAction.class);
	private String returnMsg;
	private String backUrl;
	private SysPasswdRuleService passwdService;
	private SysPasswdRule passwdRule;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysPasswdRule getPasswdRule() {
		return passwdRule;
	}
	public void setPasswdRule(SysPasswdRule passwdRule) {
		this.passwdRule = passwdRule;
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
	public SysPasswdRuleService getPasswdService() {
		return passwdService;
	}
	@Resource(name="SysPasswdRuleServiceImpl")
	public void setPasswdService(SysPasswdRuleService passwdService) {
		this.passwdService = passwdService;
	}
	public String modifyPasswdRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(passwdRule.getId()==null){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch passwdRuleId failed ,passwdRuleId is null");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwdRule.getPasswdLong()==null){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch PasswdLong failed ,PasswdLong is null");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwdRule.getIsPasswdTimeout()==null){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch IsPasswdTimeout failed ,IsPasswdTimeout is null");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwdRule.getIsPasswdTimeout().trim().equals("")){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch IsPasswdTimeout failed ,IsPasswdTimeout is ''");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwdRule.getPasswdTimeout()==null){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch PasswdTimeout failed ,PasswdTimeout is ''");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(passwdRule.getWrongTimes()==null){
			returnMsg = "系统错误，密码策略保存失败！";
			logger.info("fetch WrongTimes failed ,WrongTimes is ''");
			backUrl = "toModifyPasswdRule.action";
			SysLogUtil.saveLog(logService, "修改用户口令规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		passwdService.update(passwdRule);
		PasswdRuleUtil.isPasswdTimeout=passwdRule.getIsPasswdTimeout();
		PasswdRuleUtil.passwdLong=passwdRule.getPasswdLong();
		PasswdRuleUtil.passwdTimeout=passwdRule.getPasswdTimeout();
		PasswdRuleUtil.WrongTimes=passwdRule.getWrongTimes();
		PasswdRuleUtil.hasChar=passwdRule.getHasChar();
		PasswdRuleUtil.hasNum=passwdRule.getHasNum();
		PasswdRuleUtil.hasSpecialChar=passwdRule.getHasSpecialChar();
		PasswdRuleUtil.passwdRepeatNum=passwdRule.getPasswdRepeatNum();
		SysLogUtil.saveLog(logService, "修改用户口令规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyPasswdRule(){
		List pwdRuleList =passwdService.queryAll("from SysPasswdRule");
		if(pwdRuleList!=null&&pwdRuleList.size()>0){
			SysPasswdRule rule=(SysPasswdRule)pwdRuleList.get(0);
			ActionContext actionContext = ActionContext.getContext(); 
			Map<String,Object> requestMap=(Map)actionContext.get("request");
			requestMap.put("spasswdRule", rule);
			return "success";
		}else{
			returnMsg = "获取数据失败，跳转页面失败！";
			logger.info("query SysPasswdRule failed from database");
			backUrl = "first.jsp";
			return "failed";
		}
	}
	
}
