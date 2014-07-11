package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.Log;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;

@Controller("LogAction")
public class LogAction {
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public void viewLog(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd" );
	//	SimpleDateFormat sdf2 =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
		SimpleDateFormat sdf3 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		ActionContext actionContext = ActionContext.getContext(); 
		HttpServletRequest request=ServletActionContext.getRequest();
		String from=request.getParameter("ff");
		String to=request.getParameter("tt");
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
		String resId=request.getParameter("resId");
		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
			if(from!=null&&!from.trim().equals("")){
				from=sdf.format(sdf3.parse(from));

			}else{
				 String todays=sdf1.format(new Date());
			     from=sdf.format(sdf1.parse(todays));
			}
			
			if(to!=null&&!to.trim().equals("")){
				to=sdf.format(sdf3.parse(to));
			}else{
			
				to=sdf.format(new Date());
			}
			long count=logService.getLogCount(from,to);
			List logPage=logService.queryByPage("from com.secpro.platform.monitoring.manage.entity.Log r where r.handleDate>='"+from+"' and r.handleDate <='"+to+"' order by r.handleDate desc",pageNum,maxPage);
			if (count == 0) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				SysLogUtil.saveLog(logService, "查看系统日志,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			sb.append("{\"total\":" + count + ",\"rows\":[");
			for (int i = 0; i < logPage.size(); i++) {
				Log log=(Log)logPage.get(i);
				sb.append("{\"logid\":" + log.getId() + ",");
				sb.append("\"account\":\"" + log.getAccount() + "\",");
				sb.append("\"ip\":\"" + log.getUserIp() + "\",");
				sb.append("\"cdate\":\"" + sdf3.format(sdf.parse(log.getHandleDate())) + "\",");
				
				
				if(i==(logPage.size()-1)){
					sb.append("\"oper\":\"" + log.getHandleContent()+ "\"}");
				}else{
					sb.append("\"oper\":\"" +log.getHandleContent()+ "\"},");
				}
			}
			sb.append("]}");
			System.out.println(sb.toString());
			SysLogUtil.saveLog(logService, "查看系统日志,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(sb.toString());
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
