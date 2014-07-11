package com.secpro.platform.monitoring.manage.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.ConfigPolicyRule;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.ConfigPolicyRuleService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("ConfigPolicyRuleAction")
public class ConfigPolicyRuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(ConfigPolicyRuleAction.class);
	private String returnMsg;
	private String backUrl;
	private String typeCode;
	private String oper;
	private File file;//获取上传文件
    private String fileFileName;//获取上传文件名称
    private String fileContentType;//获取上传文件类型
    private ConfigPolicyRuleService ruleService;
    private String containConflictRule;
    private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public String getContainConflictRule() {
		return containConflictRule;
	}
	public void setContainConflictRule(String containConflictRule) {
		this.containConflictRule = containConflictRule;
	}
	public ConfigPolicyRuleService getRuleService() {
		return ruleService;
	}
	@Resource(name = "ConfigPolicyRuleServiceImpl")
	public void setRuleService(ConfigPolicyRuleService ruleService) {
		this.ruleService = ruleService;
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
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String toChangeConfigRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode = request.getParameter("typeCode");
		String operation= request.getParameter("oper");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(typeCode==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is null ");
			SysLogUtil.saveLog(logService, "跳转修改配置规则页面,失败", user.getAccount(), request.getRemoteAddr());
			backUrl = "rule/viewAllDevType.jsp";
			return "failed";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "跳转修改配置规则页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(operation==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is null ");
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "跳转修改配置规则页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(operation.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch operation failed ,operation is '' ");
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "跳转修改配置规则页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
		
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		if(ruleList!=null&&ruleList.size()>0){
			requestMap.put("configRule", ruleList.get(0));
		}
		requestMap.put("typeCode",typeCode );
		requestMap.put("oper",operation );
		SysLogUtil.saveLog(logService, "跳转修改配置规则页面,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String configRule(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(file != null){
			System.out.println("---------------------------1-----------");
	        String fullFileName=  ApplicationConfiguration.CONFIGRULEPATH+File.separator+typeCode+"_"+sdf.format(new Date());
			File savefile = new File(fullFileName);
			if (!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			try {
				FileUtils.copyFile(file, savefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnMsg="规则上传失败，请重新上传！";
				backUrl = "rule/viewAllDevType.jsp";
				SysLogUtil.saveLog(logService, "创建防火墙配置文件解析脚本,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
			if(ruleList!=null&&ruleList.size()>0){
				for(Object o:ruleList){
					ConfigPolicyRule rule=(ConfigPolicyRule)o;				
					ruleService.delete(rule);
				}
			}
			System.out.println("---------------------------2-----------");
			String rule=readRule(fullFileName);
			System.out.println(rule+"--------------------------------------");
			ConfigPolicyRule config=new ConfigPolicyRule();
			config.setStandardRule(rule);
			config.setTypeCode(typeCode);
			
			config.setContainConflictRule(containConflictRule);
			ruleService.save(config);
			savefile.delete();
			
		}else{
			returnMsg="规则不能为空，请重新上传！";
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "创建防火墙配置文件解析脚本,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "创建防火墙配置文件解析脚本,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String deleteConfigRule(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode1 = request.getParameter("typeCode");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(typeCode==null){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch typeCode failed ,typeCode is null ");
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "删除防火墙配置文件解析脚本,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(typeCode.trim().equals("")){
			returnMsg = "系统错误，删除规则失败！";
			logger.info("fetch typeCode failed ,typeCode is '' ");
			backUrl = "rule/viewAllDevType.jsp";
			SysLogUtil.saveLog(logService, "删除防火墙配置文件解析脚本,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List ruleList=ruleService.queryAll("from ConfigPolicyRule c where c.typeCode='"+typeCode+"'");
		if(ruleList!=null&&ruleList.size()>0){
			for(Object o:ruleList){
				ConfigPolicyRule rule=(ConfigPolicyRule)o;
				ruleService.delete(rule);
			}
		}
		SysLogUtil.saveLog(logService, "删除防火墙配置文件解析脚本,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String readRule(String rulePath) {
		if (Assert.isEmptyString(rulePath)) {
			return null;
		}
		FileReader fileRead = null;
		BufferedReader buffRead = null;
		try {
			fileRead = new FileReader(new File(rulePath));
			buffRead = new BufferedReader(fileRead);
			StringBuilder result = new StringBuilder();
			String ss;
			while ((ss = buffRead.readLine()) != null) {
				result.append(ss + "%%");
			}
			return result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.exception(e);
		} finally {
			if (fileRead != null) {
				try {
					fileRead.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (buffRead != null) {
				try {
					buffRead.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;

	}
}
