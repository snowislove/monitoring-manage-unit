package com.secpro.platform.monitoring.manage.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.entity.RawConfigPolicy;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.entity.SysResClass;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.entity.TelnetSshDict;
import com.secpro.platform.monitoring.manage.services.CityTreeService;
import com.secpro.platform.monitoring.manage.services.RawConfigPolicyService;
import com.secpro.platform.monitoring.manage.services.SysEventService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysResAuthService;
import com.secpro.platform.monitoring.manage.services.SysResClassService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.services.TelnetSshDictService;
import com.secpro.platform.monitoring.manage.util.FWVersionMatch;
import com.secpro.platform.monitoring.manage.util.LocalEncrypt;
import com.secpro.platform.monitoring.manage.util.MsuMangementAPI;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
/**
 * 
 * @author liyan
 * 处理资源类的请求
 * 
 */

@Controller("ResObjectAction")
public class ResObjectAction extends ActionSupport {
	PlatformLogger logger=PlatformLogger.getLogger(ResObjectAction.class);
	private SysResObjService sysService ;
	private ResObjForm resObjForm;
	private CityTreeService cityService ;
	private SysEventService eventService;
	private SysResClassService classService;
	private TelnetSshDictService telnetSshService;
	private SysResAuthService resAuthService;
	private RawConfigPolicyService configService;
	private String returnMsg;
	private String backUrl;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	
	public RawConfigPolicyService getConfigService() {
		return configService;
	}
	@Resource(name="RawConfigPolicyServiceImpl")
	public void setConfigService(RawConfigPolicyService configService) {
		this.configService = configService;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public SysResAuthService getResAuthService() {
		return resAuthService;
	}
	@Resource(name="SysResAuthServiceImpl")
	public void setResAuthService(SysResAuthService resAuthService) {
		this.resAuthService = resAuthService;
	}
	public TelnetSshDictService getTelnetSshService() {
		return telnetSshService;
	}
	@Resource(name="TelnetSshDictServiceImpl")
	public void setTelnetSshService(TelnetSshDictService telnetSshService) {
		this.telnetSshService = telnetSshService;
	}
	public SysResClassService getClassService() {
		return classService;
	}
	@Resource(name="SysResClassServiceImpl")
	public void setClassService(SysResClassService classService) {
		this.classService = classService;
	}

	public SysEventService getEventService() {
		return eventService;
	}
	
	public ResObjForm getResObjForm() {
		return resObjForm;
	}

	public void setResObjForm(ResObjForm resObjForm) {
		this.resObjForm = resObjForm;
	}

	@Resource(name="SysEventServiceImpl")
	public void setEventService(SysEventService eventService) {
		this.eventService = eventService;
	}
	
	public SysResObjService getSysService() {
		return sysService;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setSysService(SysResObjService sysService) {
		this.sysService = sysService;
	}
	
	public CityTreeService getCityService() {
		return cityService;
	}
	@Resource(name="CityTreeServiceImpl")
	public void setCityService(CityTreeService cityService) {
		this.cityService = cityService;
	}
	//查询资源明细
	public String getResObjInof(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resIdtemp=request.getParameter("resid");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		String resids[]=null;
		String residt="";
		if(resIdtemp.contains("_")){
			resids=resIdtemp.split("_");
			residt=resids[1];
		}else{
			residt=resIdtemp;
		}
		final String resId=residt;
		
		if(resId==null){
			logger.info("resid is null");
			returnMsg="系统错误，未获取到资源id，系统将跳转首页！";
			//backUrl="";
			SysLogUtil.saveLog(logService, "获取防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "filed";
		}
		resObjForm=new ResObjForm();
		resObjForm.setResId(resId);
		sysService.getResObjForm(resObjForm);
		
		if(resObjForm.getIp()!=null){
			String ips[]=resObjForm.getIp().split("\\.");
			
			if(ips.length==4){
				resObjForm.setResIp1(ips[0]);
				resObjForm.setResIp2(ips[1]);
				resObjForm.setResIp3(ips[2]);
				resObjForm.setResIp4(ips[3]);
				
			}
		}
		String operation=request.getParameter("operation");
		request.getSession().setAttribute("operation", operation);
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("resObjForm", resObjForm);
		SysLogUtil.saveLog(logService, "获取防火墙信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	
	//更新资源
	public String modifyResObj(){
	
		HttpServletRequest request=ServletActionContext.getRequest();
		String company =request.getParameter("company");
		String devType=request.getParameter("devType");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		boolean flag=false;
		if(resObjForm.getResId()!=null&&!resObjForm.getResId().equals("")){
			flag=true;
		}
		if(company==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(company.equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(devType==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(devType.equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysResObj res=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		res.setCdate(sdf.format(new Date()));	
		if(resObjForm.getCityCode()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}		
			logger.info("fetch citycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getCityCode().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch citycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setCityCode(resObjForm.getCityCode());
		if(resObjForm.getClassId()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch class_id failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getClassId().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch class_id failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setClassId(Long.parseLong(resObjForm.getClassId()));
		if(resObjForm.getCompany()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getCompany().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch companycode failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setCompanyCode(resObjForm.getCompany());
		if(resObjForm.getConfigOperation()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ConfigOperation failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getConfigOperation().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ConfigOperation failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setConfigOperation(resObjForm.getConfigOperation());
		if(resObjForm.getResId()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ResId failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResId().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ResId failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setId(Long.parseLong(resObjForm.getResId()));
		res.setResDesc(resObjForm.getResDesc());
		if(resObjForm.getResIp1()==null||resObjForm.getResIp2()==null||resObjForm.getResIp3()==null||resObjForm.getResIp4()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ip failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResIp1().equals("")||resObjForm.getResIp2().equals("")||resObjForm.getResIp3().equals("")||resObjForm.getResIp4().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch ip failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		if(resObjForm.getResName()==null){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resid failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResName().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resid failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setResName(resObjForm.getResName());
		if(resObjForm.getResPaused()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resPaused failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResPaused().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch resPaused failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setResPaused(resObjForm.getResPaused());
		if(resObjForm.getStatusOperation()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch statusOperation failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getStatusOperation().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch StatusOperation failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setStatusOperation(resObjForm.getStatusOperation());
		if(resObjForm.getDevType()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getDevType().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch devType failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setTypeCode(resObjForm.getDevType());
		
		if(resObjForm.getAuthId()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch SysResAuth failed from db");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getAuthId().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query SysResAuth failed from db");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List auths=resAuthService.queryAll("from SysResAuth s where s.id="+resObjForm.getAuthId());
		if(auths==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query SysResAuth failed from db");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysResAuth auth=(SysResAuth)auths.get(0);
			List dicts=telnetSshService.queryAll("from TelnetSshDict t where t.companyCode='"+resObjForm.getCompany()+"' and t.typeCode='"+resObjForm.getDevType()+"'");
			if(dicts==null&&resObjForm.getConfigOperation().equals("telnet")){
				returnMsg=("系统错误，防火墙修改失败！");
				if(flag){
					backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
				}else{
					
				}
				logger.info("query dict failed from db");
				SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			
			if(dicts.size()==0&&resObjForm.getConfigOperation().equals("telnet")){
				returnMsg=("系统错误，防火墙修改失败！");
				if(flag){
					backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
				}else{
					
				}
				logger.info("query dict failed from db");
				SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			if(dicts!=null&&dicts.size()>0){
				TelnetSshDict tsd=(TelnetSshDict)dicts.get(0);
				auth.setExecPrompt(tsd.getExecPrompt());
				auth.setNextPrompt(tsd.getNextPrompt());
				auth.setPassPrompt(tsd.getPassPrompt());
				auth.setPrompt(tsd.getPrompt());
				auth.setSepaWord(tsd.getSepaWord());
				auth.setUserPrompt(tsd.getUserPrompt());	
				auth.setTerminalType(tsd.getTerminalType());
				auth.setFilterString(tsd.getFilterString());
			}
		
		auth.setCommunity(resObjForm.getCommuinty());
		if(resObjForm.getPassword()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch password failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getPassword().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query password failed from db");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		auth.setPassword(LocalEncrypt.Encode(resObjForm.getPassword()));
		if(resObjForm.getUsername()==null){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("fetch username failed from web browser");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getUsername().equals("")){
			returnMsg=("修改失败，修改值获取失败！");
			if(flag){
				backUrl="toViewSysObj.action?resid="+resObjForm.getResId();
			}else{
				
			}
			logger.info("query username failed from db");
			SysLogUtil.saveLog(logService, "修改防火墙信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		auth.setUsername(resObjForm.getUsername());
		auth.setSnmpv3Auth(resObjForm.getSnmpau());
		auth.setSnmpv3Authpass(resObjForm.getSnmpaups());
		auth.setSnmpv3Priv(resObjForm.getSnmppr());
		auth.setSnmpv3Privpass(resObjForm.getSnmpprps());
		auth.setSnmpv3User(resObjForm.getSnmpuser());
		sysService.update(res);
		resAuthService.update(auth);
		String operation=request.getParameter("operation");
		request.getSession().setAttribute("operation", operation);
		SysLogUtil.saveLog(logService, "修改防火墙信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	//删除资源
	public String removeResObj(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		String res[]=null;
		if(resId==null){
			returnMsg=("删除失败！！");
			SysLogUtil.saveLog(logService, "删除防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "success";
		}
		if(resId.contains("_")){
			res=resId.split("_");
		}
		
		if(res!=null){
			SysResObj reo=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(res[1]));
			String taskRegion=cityService.getTaskRegionByCityCode(reo.getCityCode());		
			sysService.delete(reo);
			MsuMangementAPI.getInstance().publishMUSTaskToMSU(reo.getResIp()+"#"+taskRegion+"#"+reo.getTypeCode(), MsuMangementAPI.MSU_COMMAND_RESOURCE_REMOVE); 
			final String rid=res[1];
			new Thread(){
				public void run(){
					sysService.deleteRelevance(rid);
				}
			}.start();
		}else{
			SysResObj reo=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(resId));
			String taskRegion=cityService.getTaskRegionByCityCode(reo.getCityCode());	
			sysService.delete(reo);
			MsuMangementAPI.getInstance().publishMUSTaskToMSU(reo.getResIp()+"#"+taskRegion+"#"+reo.getTypeCode(), MsuMangementAPI.MSU_COMMAND_RESOURCE_REMOVE);
			final String rid=resId;
			new Thread(){
				public void run(){
					sysService.deleteRelevance(rid);
				}
			}.start();
		}
		
		SysLogUtil.saveLog(logService, "删除防火墙资源,成功", user.getAccount(), request.getRemoteAddr());
		
		return "success";
	}
	//添加保存资源
	public String saveResObj() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		SysResObj res=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		List fwObjList=sysService.queryAll("from SysResObj s where s.resName='"+resObjForm.getResName()+"'");
		if(fwObjList!=null&&fwObjList.size()>0){
			logger.info("this fw sysObj is already in db");
			backUrl="first.jsp";
			returnMsg=("资源名称已存在，资源保存失败！");	
			SysLogUtil.saveLog(logService, "创建防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setCityCode(resObjForm.getCityCode());
		res.setCompanyCode(resObjForm.getCompany());
		res.setConfigOperation(resObjForm.getConfigOperation());
		
		
		res.setResDesc(resObjForm.getResDesc());
		res.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		res.setResName(resObjForm.getResName());
		res.setStatusOperation(resObjForm.getStatusOperation());
		res.setResPaused(resObjForm.getResPaused());
		res.setTypeCode(resObjForm.getDevType());
		res.setCdate(sdf.format(new Date()));
		List resClasses =classService.queryAll("from SysResClass s where s.className='fw'");
		if(resClasses==null){
			logger.info("fetch classid of fw faild");
			backUrl="first.jsp";
			returnMsg=("系统错误，资源保存失败！");	
			SysLogUtil.saveLog(logService, "创建防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resClasses.size()==0){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			backUrl="first.jsp";
			SysLogUtil.saveLog(logService, "创建防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		long classid=((SysResClass)resClasses.get(0)).getId();
		List dicts=telnetSshService.queryAll("from TelnetSshDict t where t.companyCode='"+res.getCompanyCode()+"' and t.typeCode='"+res.getTypeCode()+"'");
		if(dicts==null&&resObjForm.getConfigOperation().equals("telnet")){
			logger.info("fetch TelnetSshDict failed ,by companycode="+res.getCompanyCode()+" and typeCode="+res.getTypeCode());
			returnMsg=("系统错误，资源保存失败！");
			backUrl="first.jsp";
			SysLogUtil.saveLog(logService, "创建防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(dicts.size()==0&&resObjForm.getConfigOperation().equals("telnet")){
			logger.info("fetch TelnetSshDict failed ,by companycode="+res.getCompanyCode()+" and typeCode="+res.getTypeCode());
			returnMsg=("系统错误，资源保存失败！");	
			backUrl="first.jsp";
			SysLogUtil.saveLog(logService, "创建防火墙资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setClassId(classid);
		
		sysService.save(res);
		SysResAuth resAuth=new SysResAuth();
		if(dicts!=null&&dicts.size()>0){
			TelnetSshDict tsd=(TelnetSshDict)dicts.get(0);
			resAuth.setExecPrompt(tsd.getExecPrompt());
			resAuth.setNextPrompt(tsd.getNextPrompt());
			resAuth.setPassPrompt(tsd.getPassPrompt());
			resAuth.setTerminalType(tsd.getTerminalType());
			resAuth.setFilterString(tsd.getFilterString());
			resAuth.setPrompt(tsd.getPrompt());
			resAuth.setSepaWord(tsd.getSepaWord());
			resAuth.setUserPrompt(tsd.getUserPrompt());
		}	
		resAuth.setCommunity(resObjForm.getCommuinty());
		
		resAuth.setPassword(LocalEncrypt.Encode(resObjForm.getPassword()));
		
		resAuth.setSnmpv3Auth(resObjForm.getSnmpau());
		resAuth.setSnmpv3Authpass(resObjForm.getSnmpaups());
		resAuth.setSnmpv3Priv(resObjForm.getSnmppr());
		resAuth.setSnmpv3Privpass(resObjForm.getSnmpprps());
		resAuth.setSnmpv3User(resObjForm.getSnmpuser());
		resAuth.setUsername(resObjForm.getUsername());
		
		resAuth.setResId(res.getId());
		resAuthService.save(resAuth);
		String taskRegion=cityService.getTaskRegionByCityCode(resObjForm.getCityCode());
		MsuMangementAPI.getInstance().publishMUSTaskToMSU(res.getResIp()+"#"+taskRegion+"#"+resObjForm.getDevType(), MsuMangementAPI.MSU_COMMAND_RESOURCE_ADD);
		returnMsg=("资源保存成功，刷新资源树后展示！");	
		SysLogUtil.saveLog(logService, "创建防火墙资源,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	//跳转到添加资源页面
	public String toAddResObj(){

		HttpServletRequest request=ServletActionContext.getRequest();
		String cityCode=request.getParameter("cityCode");
		//String operation=request.getParameter("operation");
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		
		requestMap.put("cityCode", cityCode);
	
		return "toAddSysObj";
	}
	public String viewAllMca(){
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMddHHmmss"); 
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List mcas=sysService.queryAll("select s.id,s.resName,s.resDesc,s.cdate,s.cityCode,s.resIp,sc.cityName from SysResObj s, SysResClass c ,SysCity sc  where s.classId=c.id and c.className='mca' and s.cityCode = sc.cityCode");
		
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List mcaPage = sysService.queryByPage("select s.id,s.resName,s.resDesc,s.cdate,s.resIp,s.resPaused,sc.cityName from SysResObj s, SysResClass c ,SysCity sc  where s.classId=c.id and c.className='mca' and s.cityCode = sc.cityCode ",pageNum,maxPage);
		
		if(mcaPage==null){
			returnMsg="获取采集端失败！";
			SysLogUtil.saveLog(logService, "查看采集端资源列表,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";

		}
		if(mcaPage.size()==0){
			returnMsg="获取采集端失败！";
			SysLogUtil.saveLog(logService, "查看采集端资源列表,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr =new JSONArray();
		try {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		pw = resp.getWriter();
		
		json.put("total", mcas.size());
		json.put("rows", arr);
		for(int i=0;i<mcaPage.size();i++){
			Object[] objs=(Object[])mcaPage.get(i);
			JSONObject sub=new JSONObject();
			List maxlevel=eventService.queryAll("select max(e.eventLevel) from SysEvent e where e.resId="+objs[0]);
			String date="";
			try {
				date=fmt2.format(fmt1.parse((String)objs[3]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sub.put("mcaid", objs[0]);
			
			sub.put("resName", objs[1]);
			
			sub.put("resDesc", (objs[2]==null?" ":objs[2]));
			
			sub.put("cdate", date);
			
			sub.put("resIp", objs[4]);
			
			sub.put("mcapaused", objs[5]);
			
			sub.put("cityName", objs[6]);
			if(objs[5].equals("1")){
				
				sub.put("maxLevel", "-1");
			}else{			
				if(maxlevel!=null&&maxlevel.size()>0){
					
					
					sub.put("maxLevel", maxlevel.get(0)+"");
				}else{
					
					sub.put("maxLevel", "0");
				}
				
			}
			arr.put(sub);
		}
		
		System.out.println(json.toString());
		SysLogUtil.saveLog(logService, "查看采集端资源列表,成功", user.getAccount(), request.getRemoteAddr());
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
		return "success";
	}
	public String updateMcaPaused(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaid");
		String paused=request.getParameter("paused");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(mcaId==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端状态,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(mcaId.trim().equals("")){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端状态,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(paused==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch paused failed ,mcaId is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端状态,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(paused.trim().equals("")){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch paused failed ,mcaId is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端状态,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(mcaId));
		if(mca==null){
			returnMsg="修改采集端状态失败！";
			logger.info("fetch mca failed by mcaid "+mcaId);
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端状态,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		mca.setResPaused(paused);
		sysService.update(mca);
		SysLogUtil.saveLog(logService, "修改采集端状态,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaid");
		if(mcaId==null){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="resobj/viewMca.jsp";
			return "failed";
		}
		if(mcaId.trim().equals("")){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="resobj/viewMca.jsp";
			return "failed";
		}
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(mcaId));
		
		if(mca==null){
			returnMsg="跳转修改页面失败！";
			logger.info("fetch mca failed by mcaId "+mcaId);
			backUrl="resobj/viewMca.jsp";
			return "failed";
		}
		List cityList=cityService.queryAll("from SysCity s where s.cityCode='"+mca.getCityCode()+"'");
		String ciytName="";
		
		if(cityList!=null&&cityList.size()!=0){
			ciytName=((SysCity)cityList.get(0)).getCityName();
		}
		String ip1="";
		String ip2="";
		String ip3="";
		String ip4="";
		if(mca.getResIp()!=null){
			String ips[]=mca.getResIp().split("\\.");
			
			if(ips.length==4){
				ip1=ips[0];
				ip2=ips[1];
				ip3=ips[2];
				ip4=ips[3];
			}
		}
		
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("mca", mca);
		requestMap.put("cityName", ciytName);
		requestMap.put("ip1", ip1);
		requestMap.put("ip2", ip2);
		requestMap.put("ip3", ip3);
		requestMap.put("ip4", ip4);
		return "success";
	}
	public String modifyMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(resObjForm.getResId()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch mcaId failed ,mcaId is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResId().trim().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch mcaId failed ,mcaId is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		SysResObj mca=(SysResObj)sysService.getObj(SysResObj.class, Long.parseLong(resObjForm.getResId()));
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		mca.setCdate(sdf.format(new Date()));	
		if(resObjForm.getCityCode()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch citycode failed ,citycode is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getCityCode().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch citycode failed ,citycode is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		mca.setCityCode(resObjForm.getCityCode());
		
		
		mca.setResDesc(resObjForm.getResDesc());
		if(resObjForm.getResIp1()==null||resObjForm.getResIp2()==null||resObjForm.getResIp3()==null||resObjForm.getResIp4()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch ip failed ,one of ip is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResIp1().equals("")||resObjForm.getResIp2().equals("")||resObjForm.getResIp3().equals("")||resObjForm.getResIp4().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch ip failed ,one of ip is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		mca.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		if(resObjForm.getResName()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resName failed ,one of ip is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResName().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resName failed ,one of ip is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		mca.setResName(resObjForm.getResName());
		if(resObjForm.getResPaused()==null){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resPaused failed ,resPaused is null");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resObjForm.getResPaused().equals("")){
			returnMsg="修改失败，修改值获取失败！";
			logger.info("fetch resPaused failed ,resPaused is ''");
			backUrl="resobj/viewMca.jsp";
			SysLogUtil.saveLog(logService, "修改采集端信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		mca.setResPaused(resObjForm.getResPaused());
		
		sysService.update(mca);
		SysLogUtil.saveLog(logService, "修改采集端信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String saveMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		SysResObj mca=new SysResObj();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		mca.setCityCode(resObjForm.getCityCode());
		
		mca.setResDesc(resObjForm.getResDesc());
		mca.setResIp(resObjForm.getResIp1()+"."+resObjForm.getResIp2()+"."+resObjForm.getResIp3()+"."+resObjForm.getResIp4());
		mca.setResName(resObjForm.getResName());
		
		mca.setResPaused(resObjForm.getResPaused());
		mca.setCdate(sdf.format(new Date()));
		List resClasses =classService.queryAll("from SysResClass s where s.className='mca'");
		if(resClasses==null){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			SysLogUtil.saveLog(logService, "创建采集端资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resClasses.size()==0){
			logger.info("fetch classid of fw faild");
			returnMsg=("系统错误，资源保存失败！");	
			SysLogUtil.saveLog(logService, "创建采集端资源,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		long classid=((SysResClass)resClasses.get(0)).getId();
		mca.setClassId(classid);		
		sysService.save(mca);
		SysLogUtil.saveLog(logService, "创建采集端资源,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String deleteMca(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String mcaId=request.getParameter("mcaids");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		String[] mcaIds=mcaId.split(",");
		List resList=null;
		for(int i=0;i<mcaIds.length;i++){
			if(!mcaIds[i].trim().equals("")){
				resList=sysService.queryAll("from SysResObj s where s.mcaId="+mcaIds[i]);
				if(resList!=null&&resList.size()>0){
					logger.info("MCA has FW , mcaId is "+mcaIds[i]);
					returnMsg=("请先删除归属采集端的防火墙资源在进行删除，删除失败！");	
					backUrl="resobj/viewMca.jsp";
					SysLogUtil.saveLog(logService, "删除采集端资源,失败", user.getAccount(), request.getRemoteAddr());
					return "failed";
				}
			}
		}
		for(int i=0;i<mcaIds.length;i++){
			if(!mcaIds[i].trim().equals("")){
				SysResObj mca =new SysResObj();
				mca.setId(Long.parseLong(mcaIds[i]));
				sysService.delete(mca);
				final String rid=mcaIds[i];
				new Thread(){
					public void run(){
						sysService.deleteRelevance(rid);
					}
				}.start();
			}
			
		}
		SysLogUtil.saveLog(logService, "删除采集端资源,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void getResObjByCity(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String cityCode=request.getParameter("cityCode");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(cityCode==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(cityCode.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			List resList=sysService.queryAll("from SysResObj s where s.cityCode='"+cityCode+"'");
			if(resList==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < resList.size(); i++) {
				SysResObj res=(SysResObj)resList.get(i);
				
				result.append("{\"id\":"+res.getId()+",\"text\":\""+res.getResName()+"\"}");
				
				if((i+1)!=resList.size()){
					result.append(",");
				}
				
			}
			result.append("]");
			System.out.println(result.toString());
			pw.println(result.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void getMcaByCity(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String cityCode=request.getParameter("cityCode");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(cityCode==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(cityCode.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			List resList=sysService.queryAll("select s.id ,s.resName from SysResObj s,SysResClass c where s.cityCode='"+cityCode+"' and s.classId=c.id and c.className='mca'");
			if(resList==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < resList.size(); i++) {
				Object[] res=(Object[])resList.get(i);
				
				result.append("{\"id\":"+res[0]+",\"text\":\""+res[1]+"\"}");
				
				if((i+1)!=resList.size()){
					result.append(",");
				}
				
			}
			result.append("]");
			System.out.println(result.toString());
			pw.println(result.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void getConfigVersion(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId=request.getParameter("resId");
		
		String page=request.getParameter("page");
		String maxRow=request.getParameter("maxRow");
	
		int pageN=1;
		int MaxP=5;
		if(page!=null&&!resId.trim().equals("")){
			pageN=Integer.parseInt(page);
		}
		if(maxRow!=null&&!maxRow.trim().equals("")){
			MaxP=Integer.parseInt(maxRow);
		}
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			
			pw = resp.getWriter();
			if(resId==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(resId.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			List config = configService.queryByPage("from RawConfigPolicy c where c.resId="+resId+" order by c.cdate desc", pageN, MaxP);
			JSONArray array=new JSONArray();
			for(Object o:config){
				RawConfigPolicy conf=(RawConfigPolicy)o;
				JSONObject json=new JSONObject();
				json.put("id", conf.getId());
				json.put("text", "版本"+sdf1.format(sdf.parse(conf.getCdate())));
				array.put(json);
			}
			pw.println(array.toString());
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
	public void configMatch(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String configId1=request.getParameter("configId1");
		String configId2=request.getParameter("configId2");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			
			pw = resp.getWriter();
			if(configId1==null){
				result.append("{\"add\":\"\",\"del\":\"\",\"conf1\":\"\",\"conf2\":\"\"}");					
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(configId1.trim().equals("")){
				result.append("{\"add\":\"\",\"del\":\"\",\"conf1\":\"\",\"conf2\":\"\"}");					
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(configId2==null){
				result.append("{\"add\":\"\",\"del\":\"\",\"conf1\":\"\",\"conf2\":\"\"}");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(configId2.trim().equals("")){
				result.append("{\"add\":\"\",\"del\":\"\",\"conf1\":\"\",\"conf2\":\"\"}");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			
			RawConfigPolicy config1 = (RawConfigPolicy)configService.getObj(RawConfigPolicy.class, Long.parseLong(configId1));
			RawConfigPolicy config2 = (RawConfigPolicy)configService.getObj(RawConfigPolicy.class, Long.parseLong(configId2));
			
			String containRegex="#configuration#([\\s\\S]*)#configuration#";
			Pattern pattern = Pattern.compile(containRegex);
			String conf1="";
			String conf2="";
			
			Matcher mat1 = pattern.matcher(config1.getConfigPolicyInfo());
			if (mat1.find()) {
				conf1=mat1.group(1);
			}
			Matcher mat2 = pattern.matcher(config2.getConfigPolicyInfo());
			if (mat2.find()) {
				conf2=mat2.group(1);
			}
			if(!conf1.endsWith("^")){
				conf1+="^";
			}
			if(!conf2.endsWith("^")){
				conf2+="^";
			}
			
			String[] res=FWVersionMatch.versionMatch(conf2, conf1, "\\^");
			
			JSONObject json=new JSONObject();
			if(res==null){
				result.append("{\"add\":\"\",\"del\":\"\",\"conf1\":\"\",\"conf2\":\"\"}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			
			
			json.put("add", res[0]);
			json.put("del", res[1]);
			json.put("conf1", conf1.replace("^", "\n\r"));
			json.put("conf2", conf2.replace("^", "\n\r"));
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "比对防火墙配置文件,成功", user.getAccount(), request.getRemoteAddr());
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
}
