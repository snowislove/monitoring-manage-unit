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
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.RawFwFileService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;

@Controller("RawFwFileAction")
public class RawFwFileAction {
	private RawFwFileService fileService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public RawFwFileService getFileService() {
		return fileService;
	}
	@Resource(name = "RawFwFileServiceImpl")
	public void setFileService(RawFwFileService fileService) {
		this.fileService = fileService;
	}
	public void queryFwFileByRes(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	//	SimpleDateFormat sdf2 =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );
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
			
			
			if(resId==null){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				SysLogUtil.saveLog(logService, "查看防火墙配置文件列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if(resId.trim().equals("")){
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				SysLogUtil.saveLog(logService, "查看防火墙配置文件列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if(from!=null&&!from.trim().equals("")){
				from=sdf.format(sdf1.parse(from));

			}else{
				 String todays=sdf1.format(new Date());
			     from=sdf.format(sdf1.parse(todays));
			}
			
			if(to!=null&&!to.trim().equals("")){
				to=sdf.format(sdf1.parse(to));
			}else{
			
				to=sdf.format(new Date());
			}
			
			List fileList=fileService.queryAll("from RawFwFile r where r.cdate>='"+from+"' and r.cdate <='"+to+"' and r.resId="+resId);
			List filePage=fileService.queryByPage("from RawFwFile r where r.cdate>='"+from+"' and r.cdate <='"+to+"' and r.resId="+resId,pageNum,maxPage);
			if (fileList == null) {
				sb.append("{\"total\":0,\"rows\":[]}");
				pw.println(sb.toString());
				SysLogUtil.saveLog(logService, "查看防火墙配置文件列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			JSONObject json =new JSONObject();
			JSONArray arr=new JSONArray();
			json.put("total", fileList.size());
			json.put("rows", arr);
			for (int i = 0; i < filePage.size(); i++) {
				JSONObject j=new JSONObject();
				RawFwFile file=(RawFwFile)filePage.get(i);
				j.put("fileId", file.getId());
				j.put("fileName", file.getFileName());
				j.put("cdate", sdf1.format(sdf.parse(file.getCdate())));
				j.put("fileSize", file.getFileSize());
				j.put("filePath", file.getFilePath());
				arr.put(j);
			}
			
			System.out.println(arr.toString());
			SysLogUtil.saveLog(logService, "查看防火墙配置文件列表,失败", user.getAccount(), request.getRemoteAddr());
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
