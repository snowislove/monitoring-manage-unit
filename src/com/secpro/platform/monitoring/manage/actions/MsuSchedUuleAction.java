package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.RawFwFile;
import com.secpro.platform.monitoring.manage.services.MsuSchedUuleService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("MsuSchedUuleAction")
public class MsuSchedUuleAction {
	PlatformLogger logger = PlatformLogger.getLogger(OrgAction.class);
	private String returnMsg;
	private String backUrl;
	private MsuSchedUuleService schedUuleService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public PlatformLogger getLogger() {
		return logger;
	}
	public void setLogger(PlatformLogger logger) {
		this.logger = logger;
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
	public MsuSchedUuleService getSchedUuleService() {
		return schedUuleService;
	}
	@Resource(name = "MsuSchedUuleServiceImpl")
	public void setSchedUuleService(MsuSchedUuleService schedUuleService) {
		this.schedUuleService = schedUuleService;
	}
	public void viewTaskExecute(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String from=request.getParameter("ff");
		String to=request.getParameter("tt");
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		Date f=null;
		Date t=null;
		int pageNum=1;
		int maxPage=10;
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		String resId=request.getParameter("resId");
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			
			if(resId==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(resId.trim().equals("")){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			if(from!=null&&!from.trim().equals("")){
				f=sdf.parse(sdf.format(sdf1.parse(from)));
				
			}else{
			     f=sdf1.parse(sdf.format(new Date()));
			}
			
			if(to!=null&&!to.trim().equals("")){
				t=sdf1.parse(to);
			}else{
			
				t=new Date();
			}
			System.out.println(to+"--------------"+from+"==============="+resId+"===================="+schedUuleService);
			List taskList=schedUuleService.queryAll("select m.scheduleId,m.taskId,m.schedulePoint,m.createAt,s.cityName,m.operation,m.fetchBy,m.executeAt,m.executeCost,m.executeStatus,m.executeDescription from MsuSchedUule m,MsuTask t,SysCity s where m.taskId=t.id and t.resId="+resId+" and  m.fetchAt>="+f.getTime()+" and m.fetchAt <="+t.getTime()+" and m.region=s.cityCode");
			List taskPage=schedUuleService.queryByPage("select m.scheduleId,m.taskId,m.schedulePoint,m.fetchAt,s.cityName,m.operation,m.fetchBy,m.executeAt,m.executeCost,m.executeStatus,m.executeDescription from MsuSchedUule m,MsuTask t,SysCity s where m.taskId=t.id and t.resId="+resId+" and  m.fetchAt>="+f.getTime()+" and m.fetchAt <="+t.getTime()+" and m.region=s.cityCode",pageNum,maxPage);
			if (taskList == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				pw.flush();
				return;
			}
			JSONObject json =new JSONObject();
			JSONArray arr=new JSONArray();
			json.put("total", taskList.size());
			json.put("rows", arr);
			for (int i = 0; i < taskPage.size(); i++) {
				JSONObject j=new JSONObject();
				Object[] task=(Object[])taskPage.get(i);
				j.put("scheduleId", task[0]);
				j.put("taskId", task[1]);
				j.put("schedulePoint", task[2]);
				Date d1=new Date((Long)task[3]);
				j.put("fetchAt", sdf1.format(d1));
				j.put("cityName", task[4]);
				j.put("operation", task[5]);
				j.put("fetchBy", task[6]);
				Date d2=new Date((Long)task[7]);
				j.put("executeAt", sdf1.format(d2));
				j.put("executeCost", task[8]);
				j.put("executeStatus", task[9].equals("1")?"成功":"失败");
				j.put("executeDescription", task[10]);
				arr.put(j);
			}
			
			System.out.println(arr.toString());
			pw.println(arr.toString());
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
