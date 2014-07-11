package com.secpro.platform.monitoring.manage.actions;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.SysAccessRule;
import com.secpro.platform.monitoring.manage.entity.SysPasswdRule;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysAccessRuleService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.AccessRuleUtil;
import com.secpro.platform.monitoring.manage.util.PasswdRuleUtil;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("SysAccessRuleAction")
public class SysAccessRuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysPasswdRuleAction.class);
	private String returnMsg;
	private String backUrl;
	private SysAccessRuleService accessRuleService;
	private  SysAccessRule accessRule;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
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
	public SysAccessRuleService getAccessRuleService() {
		return accessRuleService;
	}
	@Resource(name="SysAccessRuleServiceImpl")
	public void setAccessRuleService(SysAccessRuleService accessRuleService) {
		this.accessRuleService = accessRuleService;
	}
	public SysAccessRule getAccessRule() {
		return accessRule;
	}
	public void setAccessRule(SysAccessRule accessRule) {
		this.accessRule = accessRule;
	}
	public String toModifyAccessRule(){
		List AccessRuleList =accessRuleService.queryAll("from SysAccessRule");
		if(AccessRuleList!=null&&AccessRuleList.size()>0){
			SysAccessRule rule=(SysAccessRule)AccessRuleList.get(0);
			ActionContext actionContext = ActionContext.getContext(); 
			Map<String,Object> requestMap=(Map)actionContext.get("request");
			requestMap.put("laccessRule", rule);
			return "success";
		}else{
			returnMsg = "获取数据失败，跳转页面失败！";
			logger.info("query SysAccessRule failed from database");
			backUrl = "first.jsp";
			return "failed";
		}
	}
	public String modifyAccessRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(accessRule.getId()==null){
			returnMsg = "系统错误,访问策略保存失败！";
			logger.info("fetch accessRuleId failed ,accessRuleId is null");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(accessRule.getMaxUser()==null){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch MaxUser failed ,MaxUser is null");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(accessRule.getIsLimitIp()==null){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch IsLimitIp failed ,IsLimitIp is null");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(accessRule.getIsLimitIp().trim().equals("")){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch IsLimitIp failed ,IsPasswdTimeout is ''");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(accessRule.getIsLimitTime()==null){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch IsLimitTime failed ,IsLimitTime is null");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(accessRule.getIsLimitTime().equals("")){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch IsLimitTime failed ,IsLimitTime is ''");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(accessRule.getAccessTimeOut()==null){
			returnMsg = "系统错误，访问策略保存失败！";
			logger.info("fetch AccessTimeOut failed ,AccessTimeOut is null");
			backUrl = "toModifyAccessRule.action";
			SysLogUtil.saveLog(logService, "修改用户访问规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		accessRuleService.update(accessRule);
		AccessRuleUtil.maxUser=accessRule.getMaxUser();
		AccessRuleUtil.isLimitIp=accessRule.getIsLimitIp();
		AccessRuleUtil.isLimitTime=accessRule.getIsLimitTime();
		AccessRuleUtil.accessTimeOut=accessRule.getAccessTimeOut();
		SysLogUtil.saveLog(logService, "修改用户访问规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
}
