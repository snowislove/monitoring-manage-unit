package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.SysKpiInfo;
import com.secpro.platform.monitoring.manage.entity.SysKpiOid;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysKpiInfoService;
import com.secpro.platform.monitoring.manage.services.SysKpiOidService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("SysKpiOidAction")
public class SysKpiOidAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysKpiOidAction.class);
	private String returnMsg;
	private String backUrl;
	private SysKpiOidService oidService;
	private SysKpiInfoService kpiService;	
	private SysKpiOid oid;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}	
	public SysKpiInfoService getKpiService() {
		return kpiService;
	}
	@Resource(name = "SysKpiInfoServiceImpl")
	public void setKpiService(SysKpiInfoService kpiService) {
		this.kpiService = kpiService;
	}
	public SysKpiOid getOid() {
		return oid;
	}
	public void setOid(SysKpiOid oid) {
		this.oid = oid;
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
	public SysKpiOidService getOidService() {
		return oidService;
	}
	@Resource(name = "SysKpiOidServiceImpl")
	public void setOidService(SysKpiOidService oidService) {
		this.oidService = oidService;
	}
	public String toAddMidOid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String kpiId=request.getParameter("kpiId");
		if(kpiId==null){
			returnMsg="系统错误,页面跳转失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch kpiId failed , kpiId is null");
			return "failed";
		}
		if(kpiId.trim().equals("")){
			returnMsg="系统错误,页面跳转失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch kpiId failed , kpiId is null");
			return "failed";
		}
		SysKpiInfo kpi=(SysKpiInfo)kpiService.getObj(SysKpiInfo.class, Long.parseLong(kpiId));
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("kpiName", kpi.getKpiName());
		requestMap.put("kpiId", kpiId);
		return "success";
	}
	public String addOrUpdateMibOid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(oid.getCompanyCode()==null){
			returnMsg="系统错误,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch companyCode failed , companyCode is null");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getCompanyCode().equals("")){
			returnMsg="系统错误,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch companyCode failed , companyCode is ''");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getKpiId()==null){
			returnMsg="系统错误,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch kpiId failed , kpiId is null");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getTypeCode()==null){
			returnMsg="系统错误,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch typeCode failed , typeCode is null");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getTypeCode().equals("")){
			returnMsg="系统错误,操作失败！！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch typeCode failed , typeCode is ''");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getMiboid()==null){
			returnMsg="MIB不能为空,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch midoid failed , midoid is null");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getMiboid().equals("")){
			returnMsg="MIB不能为空,操作失败！";
			backUrl="resobj/viewKpiInfo.jsp";
			logger.info("fetch midoid failed , midoid is ''");
			SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(oid.getId()==null){
			oid.setMiboid(this.midDeal(oid.getMiboid()));
			oidService.save(oid);
		}else{
			SysKpiOid mib=(SysKpiOid)oidService.getObj(SysKpiOid.class, oid.getId());
			if(mib==null){
				returnMsg="系统错误,操作失败！";
				backUrl="resobj/viewKpiInfo.jsp";
				logger.info("fetch SysKpiOid failed from db , id is " +oid.getKpiId());
				SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
			mib.setCompanyCode(oid.getCompanyCode());
			mib.setKpiId(oid.getKpiId());
			mib.setTypeCode(oid.getTypeCode());
			mib.setMiboid(this.midDeal(oid.getMiboid()));
			mib.setRule(oid.getRule());
			oidService.update(mib);
			
		}
		SysLogUtil.saveLog(logService, "创建或修改指标MIB和规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void queryMIb(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String typeCode=request.getParameter("typeCode");
		String kpiId=request.getParameter("kpiId");
		
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(kpiId==null){
				result.append("{}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			
			if(typeCode==null){
				result.append("{}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(typeCode.trim().equals("")){
				result.append("{}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			
			
			List mibList=oidService.queryAll("from SysKpiOid o where o.typeCode='"+typeCode+"' and o.kpiId="+kpiId);
			if(mibList==null){
				result.append("{}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(mibList.size()==0){
				result.append("{}");
				pw.println(result.toString());
				pw.flush();
				return;
			}
			SysKpiOid mib=(SysKpiOid)mibList.get(0);
			JSONObject json=new JSONObject();
			
			json.put("id", mib.getId());
			json.put("miboid", mib.getMiboid());
			json.put("rule", mib.getRule());
			
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
	private String midDeal(String rule){
		if(rule.startsWith(".")){
			int i=rule.indexOf(".");
			rule=rule.substring(i+1);
		}
		return rule;
	}
	
}
