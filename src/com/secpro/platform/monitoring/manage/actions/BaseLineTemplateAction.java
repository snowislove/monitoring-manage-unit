package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.BaselineTemplate;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.BaselineTemplateService;
import com.secpro.platform.monitoring.manage.services.SysBaselineService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("BaseLineTemplateAction")
public class BaseLineTemplateAction {
	PlatformLogger logger = PlatformLogger.getLogger(BaseLineTemplateAction.class);
	private BaselineTemplateService btService;
	private SysBaselineService sbService;
	private SysResObjService resService;
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
	public SysResObjService getResService() {
		return resService;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setResService(SysResObjService resService) {
		this.resService = resService;
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
	public BaselineTemplateService getBtService() {
		return btService;
	}
	@Resource(name="BaselineTemplateServiceImpl")
	public void setBtService(BaselineTemplateService btService) {
		this.btService = btService;
	}
	public SysBaselineService getSbService() {
		return sbService;
	}
	@Resource(name="SysBaselineServiceImpl")
	public void setSbService(SysBaselineService sbService) {
		this.sbService = sbService;
	}
	public void getAllBaseLinetemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List allTemplateList = btService.queryAll("from BaselineTemplate");
		List templateList = btService.queryByPage("from BaselineTemplate",pageNum,maxPage);
		templateList = btService.getAllBaseLineTemplate(templateList);
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (templateList == null) {
			
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看基线模板列表,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			
			json.put("total", allTemplateList.size());
			json.put("rows", arr);
			for (int i = 0; i < templateList.size(); i++) {
				BaselineTemplate bt = (BaselineTemplate) templateList.get(i);
				
				
				if(bt.getBaseLineList().size()==0){
					JSONObject sub=new JSONObject();
					sub.put("templateId", bt.getId());
					
					sub.put("templateName", bt.getTemplateName());
					
					sub.put("templateDesc", (bt.getTemplateDesc()==null?" ":bt.getTemplateDesc()));
					
					sub.put("companyName", (bt.getCompanyName()==null?" ":bt.getCompanyName()));
					
					sub.put("baselineDesc", "");
					
					sub.put("baselineType", "");
					
					sub.put("blackWhite", "");
					arr.put(sub);
				}else{
					for (int j = 0; j < bt.getBaseLineList().size(); j++) {
						JSONObject sub=new JSONObject();
						
						sub.put("templateId", bt.getId());
						
						sub.put("templateName",  bt.getTemplateName());
						
						sub.put("templateDesc",  (bt.getTemplateDesc()==null?" ":bt.getTemplateDesc()));
						
						sub.put("companyName", (bt.getCompanyName()==null?"":bt.getCompanyName()));
						SysBaseline sbl = (SysBaseline) bt.getBaseLineList().get(j);
						
						sub.put("baselineDesc", sbl.getBaselineDesc());
						
						sub.put("baselineType", (sbl.getBaselineType().equals("0") ? "配置基线": "策略基线"));
						
						sub.put("blackWhite", (sbl.getBaselineBlackWhite().equals("0") ? "白名单": "黑名单"));
						arr.put(sub);
					}
				}
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看基线模板列表,成功", user.getAccount(), request.getRemoteAddr());
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
	public String saveBaseLineTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String companyCode =request.getParameter("companyCode");
		String templateName = request.getParameter("templateName");
		
		String templateDesc = request.getParameter("templateDesc");
		
		String[] baselineIds= request.getParameterValues("baselineId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List ll=btService.queryAll("from BaselineTemplate b where b.templateName='"+templateName+"'");
		if(ll!=null && ll.size()>0){
			returnMsg = "模板名字不能重名，模板保存失败！";
			logger.info("basetemplate is Exist !");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (companyCode == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (companyCode.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is ''");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (templateName == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is null");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (templateName.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is ''");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (baselineIds == null) {
			returnMsg = "未选择基线，模板保存失败！";
			logger.info("fetch baselineIds failed ,baselineIds is null");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		BaselineTemplate bt=new BaselineTemplate();
		bt.setCompanyCode(companyCode);
		bt.setTemplateName(templateName);
		bt.setTemplateDesc(templateDesc);
		bt.setCdate(sdf.format(new Date()));
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i<baselineIds.length;i++){
			String score=request.getParameter("score"+baselineIds[i]);
			
			map.put(baselineIds[i], score);	
		}
		btService.save(bt);
		boolean flag=btService.saveBaseLineTemplete(bt.getId(), baselineIds, map);
		
		if(!flag){
			btService.delete(bt);
			returnMsg = "模板保存失败！";
			logger.info("save baseline_template_mapping failed");
			backUrl = "baseline/addBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "创建基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "创建基线模板,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void getAllBaseline(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List baselineList=sbService.queryAll("from SysBaseline s order by s.baselineType,s.baselineBlackWhite");
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(baselineList!=null&&!baselineList.isEmpty()){
				
				json.put("total",  baselineList.size() );
				json.put("rows", arr);
				for(int i=0;i<baselineList.size();i++){
					SysBaseline baseline=(SysBaseline)baselineList.get(i);
					JSONObject sub=new JSONObject();
					
					sub.put("baselineId",  baseline.getId());
					
					sub.put("baselineType",  (baseline.getBaselineType().equals("0") ? "配置基线":"策略基线"));
					
					sub.put("blackWhite", (baseline.getBaselineBlackWhite().equals("0") ? "白名单":"黑名单"));
					
					sub.put("score", "10");
					
					sub.put("baselineDesc", baseline.getBaselineDesc());
					arr.put(sub);
				}
			}else{
				
				json.put("total", 0);
				json.put("rows", arr);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查询基线列表,成功", user.getAccount(), request.getRemoteAddr());
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
	public String deleteTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String templateId=request.getParameter("templateIds");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(templateId==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch templateId failed ,templateId is null");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "删除基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch templateId failed ,templateId is ''");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "删除基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String templateIds[]=templateId.split(",");
		boolean flag=btService.deleteTemplate(templateIds);
		if(!flag){
			returnMsg = "系统错误，删除失败！";
			logger.info("delete failed");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "删除基线模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "删除基线模板,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String templateId=request.getParameter("templateId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(templateId==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch templateId failed ,templateId is null");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "跳转修改基线模板页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch templateId failed ,templateId is ''");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "跳转修改基线模板页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List templateList=btService.queryAll("select b.id,b.templateName,b.templateDesc,b.companyCode,s.companyName from BaselineTemplate b,SysDevCompany s where b.companyCode=s.companyCode and b.id="+templateId);
		if(templateList==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("query BaselineTemplate failed from datebase");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "跳转修改基线模板页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(templateList.size()==0){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("query BaselineTemplate failed from datebase");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "跳转修改基线模板页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		BaselineTemplate stt=new BaselineTemplate();
		Object[] obj=(Object[])templateList.get(0);
		stt.setId((Long)obj[0]);
		stt.setTemplateName((String)obj[1]);
		stt.setTemplateDesc((String)obj[2]);
		stt.setCompanyCode((String)obj[3]);
		stt.setCompanyName((String)obj[4]);
		List ll=btService.getSelectBaseLine(stt.getId());
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("baselineTemplate", stt);
		requestMap.put("selectBl", ll);
		SysLogUtil.saveLog(logService, "跳转修改基线模板页面,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String modifyBaseLineTemplate(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		String companyCode =request.getParameter("companyCode");
		String id =request.getParameter("id");
		String templateName = request.getParameter("templateName");
		
		String templateDesc = request.getParameter("templateDesc");
		
		String[] baselineIds= request.getParameterValues("baselineId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List ll=btService.queryAll("from BaselineTemplate b where b.templateName='"+templateName+"'");
		
		if(ll!=null && ll.size()>0){
			if(!(((BaselineTemplate)ll.get(0)).getId().toString()).equals(id)){
				returnMsg = "模板名字不能重名，模板保存失败！";
				logger.info("basetemplate is Exist !");
				backUrl = "toModifyTemplate.action?templateId="+id;
				SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
		}
		if (companyCode == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is null");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (companyCode.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch companyCode failed ,companyCode is ''");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (templateName == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is null");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (templateName.trim().equals("")) {
			returnMsg = "模板保存失败！";
			logger.info("fetch templateName failed ,templateName is ''");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (baselineIds == null) {
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineIds failed ,baselineIds is null");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List bll=btService.queryAll("from BaselineTemplate b where b.id="+id);
		if(bll==null){
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineTemplate failed from database");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(bll.size()==0){
			returnMsg = "模板保存失败！";
			logger.info("fetch baselineTemplate failed from database");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		BaselineTemplate bt=(BaselineTemplate)bll.get(0);
		bt.setCompanyCode(companyCode);
		bt.setTemplateName(templateName);
		bt.setTemplateDesc(templateDesc);
		bt.setCdate(sdf.format(new Date()));
		Map<String,String> map=new HashMap<String,String>();
		for(int i=0;i<baselineIds.length;i++){
			String score=request.getParameter("score"+baselineIds[i]);
			
			map.put(baselineIds[i], score);	
		}
		
		boolean flag=btService.saveBaseLineTemplete(bt.getId(), baselineIds, map);
		
		if(!flag){
			btService.delete(bt);
			returnMsg = "模板保存失败！";
			logger.info("save baseline_template_mapping failed");
			backUrl = "toModifyTemplate.action?templateId="+id;
			SysLogUtil.saveLog(logService, "修改基线模板信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		btService.update(bt);
		SysLogUtil.saveLog(logService, "修改基线模板信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void getBaseLineTemplateByCompany(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		String companyCode=request.getParameter("companyCode");
		String resId=request.getParameter("resId");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(resId!=null&&!resId.trim().equals("")){
				List tbList=btService.queryAll("select b.id,b.templateName from BaselineTemplate b,SysResObj o where b.id=o.templateId and o.id="+resId);
				if(tbList==null){
					result.append("[]");				
					pw.println(result.toString());
					SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,失败", user.getAccount(), request.getRemoteAddr());
					pw.flush();
					return;
				}
				result.append("[");
				for (int i = 0; i < tbList.size(); i++) {
					Object[] obj=(Object[])tbList.get(i);
					
					result.append("{\"id\":"+obj[0]+",\"text\":\""+obj[1]+"\"}");
					
					if((i+1)!=tbList.size()){
						result.append(",");
					}
					
				}
				result.append("]");
				System.out.println(result.toString());
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return ;
			}
			if(companyCode==null){
				result.append("[]");				
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if(companyCode.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			List templateList=btService.queryAll("from BaselineTemplate b where  b.companyCode='"+companyCode+"'");
			if(templateList==null){
				result.append("[]");				
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < templateList.size(); i++) {
				BaselineTemplate template=(BaselineTemplate)templateList.get(i);
				
				result.append("{\"id\":"+template.getId()+",\"text\":\""+template.getTemplateName()+"\"}");
				
				if((i+1)!=templateList.size()){
					result.append(",");
				}
				
			}
			result.append("]");
			System.out.println(result.toString());
			SysLogUtil.saveLog(logService, "根据设备厂商查找基线模板,成功", user.getAccount(), request.getRemoteAddr());
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
	public String templateResMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String resId=request.getParameter("resId");
		String templateId=request.getParameter("templateId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(resId==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resId failed , resId is null!");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "为资源添加模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(resId.trim().equals("")){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resId failed , resId is ''!");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "为资源添加模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(templateId==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch templateId failed , templateId is null!");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "为资源添加模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(templateId.trim().equals("")){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch templateId failed , templateId is ''!");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "为资源添加模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysResObj res=(SysResObj)resService.getObj(SysResObj.class, Long.parseLong(resId));
		if(res==null){
			returnMsg = "系统错误，映射关系保存失败！";
			logger.info("fetch resObj failed from database!");
			backUrl = "baseline/viewBaseLineTemplate.jsp";
			SysLogUtil.saveLog(logService, "为资源添加模板,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		res.setTemplateId(Long.parseLong(templateId));
		resService.update(res);
		SysLogUtil.saveLog(logService, "为资源添加模板,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	
}
