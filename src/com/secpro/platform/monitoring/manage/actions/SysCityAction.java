package com.secpro.platform.monitoring.manage.actions;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.entity.SysCity;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.CityTreeService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("CityTreeAction")
public class SysCityAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private PlatformLogger log=PlatformLogger.getLogger(SysCityAction.class);
	private CityTreeService _cityService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public CityTreeService get_userService() {
		return _cityService;
	}
	public void set_userService(CityTreeService _userService) {
		this._cityService = _userService;
	}
	public CityTreeService getUserService() {
		return _cityService;
	}
	@Resource(name="CityTreeServiceImpl")
	public void setUserService(CityTreeService userService) {
		this._cityService = userService;
	}
	public void getCityTree(){
		log.debug("fetch city restree");
		List sysCitys=null;
		String citycode =ServletActionContext.getRequest().getParameter("citycode");
		if(citycode!=null&&!citycode.equals("")){
			sysCitys=_cityService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'"+citycode+"\'");
		}else{
			sysCitys=_cityService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'0\'");
		}	
		PrintWriter pw=null;
		try {
			HttpServletResponse resp=ServletActionContext.getResponse();
		//	String path=ServletActionContext.getRequest().getRequestURL().toString();
			resp.setContentType("text/xml"); 
			pw=resp.getWriter();
			StringBuffer sb=new StringBuffer();
			sb.append("<tree>");
			if(pw!=null){
				for(int i=0;i<sysCitys.size();i++){
					SysCity sc=(SysCity)sysCitys.get(i);
					List ll=_cityService.queryAll("from com.secpro.platform.monitoring.manage.entity.SysCity s where s.parentCode=\'"+sc.getCityCode()+"\'");
					if(ll.size()>0){
						sb.append("<tree text=\""+sc.getCityName()+"\" src=\"CityTreeAction?citycode="+sc.getCityCode()+"\""+"/>");
					}else{
						sb.append("<tree text=\""+sc.getCityName()+"\"/>");
					}
				}
			}
			sb.append("</tree>");
			log.debug(sb.toString());
			pw.println(sb.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	public void getAllCity(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List citys = _cityService.queryAll("from SysCity s where s.parentCode='1' order by s.citySort");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (citys == null) {
				result.append("[]");
				
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "获取全部城市信息,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < citys.size(); i++) {
				SysCity city=(SysCity)citys.get(i);
				
				result.append("{\"id\":"+city.getCityCode()+",\"text\":\""+city.getCityName()+"\"}");
				
				if((i+1)!=citys.size()){
					result.append(",");
				}
				
			}
			result.append("]");
			System.out.println(result.toString());
			SysLogUtil.saveLog(logService, "获取全部城市信息,成功", user.getAccount(), request.getRemoteAddr());
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
	public void getCityByParent(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String parentCode=request.getParameter("cityCode");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if(parentCode==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			if(parentCode.trim().equals("")){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			List cityList=_cityService.queryAll("from SysCity s where s.parentCode='"+parentCode+"' order by s.citySort");
			if(cityList==null){
				result.append("[]");				
				pw.println(result.toString());
				pw.flush();
				return;
			}
			result.append("[");
			for (int i = 0; i < cityList.size(); i++) {
				SysCity city=(SysCity)cityList.get(i);
				
				result.append("{\"id\":"+city.getCityCode()+",\"text\":\""+city.getCityName()+"\"}");
				
				if((i+1)!=cityList.size()){
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
}
