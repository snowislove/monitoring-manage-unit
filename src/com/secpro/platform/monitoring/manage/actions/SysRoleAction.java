package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.SysApp;
import com.secpro.platform.monitoring.manage.entity.SysRole;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysAppService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysRoleService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
@Controller("SysRoleAction")
public class SysRoleAction {
	PlatformLogger logger = PlatformLogger.getLogger(SysRoleAction.class);
	private String returnMsg;
	private String backUrl;
	private SysRoleService roleService;
	private SysRole role;
	private SysAppService appService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysAppService getAppService() {
		return appService;
	}
	@Resource(name = "SysAppServiceImpl")
	public void setAppService(SysAppService appService) {
		this.appService = appService;
	}
	public SysRole getRole() {
		return role;
	}
	public void setRole(SysRole role) {
		this.role = role;
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
	public SysRoleService getRoleService() {
		return roleService;
	}
	@Resource(name = "SysRoleServiceImpl")
	public void setRoleService(SysRoleService roleService) {
		this.roleService = roleService;
	}
	public void viewRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String rows=request.getParameter("rows");
		String page=request.getParameter("page");
		int pageNum=1;
		int maxPage=10;
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(rows!=null&&!rows.trim().equals("")){
			maxPage=Integer.parseInt(rows); 
		}
		if(page!=null&&!page.trim().equals("")){
			pageNum=Integer.parseInt(page); 
		}
		List allRole=roleService.queryAll("from SysRole s where s.roleName!='系统管理员' and s.roleName!='审计管理员'");
		List rolepage=roleService.queryByPage("from SysRole s where s.roleName!='系统管理员' and s.roleName!='审计管理员'", pageNum,maxPage);
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allRole == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看角色列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			json.put("total", rolepage.size());
			json.put("rows", arr);
			for (int i = 0; i < rolepage.size(); i++) {
				SysRole role1=(SysRole)rolepage.get(i);
				JSONObject sub=new JSONObject();
				sub.put("roleid", role1.getId());
				
				sub.put("rolename", role1.getRoleName());
				
				sub.put("roledesc", (role1.getRoleDesc()==null?"":role1.getRoleDesc()));
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看角色列表,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public String saveRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，角色添加！";
			logger.info("fetch roleName failed , roleName is null!");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "创建新角色,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，角色添加！";
			logger.info("fetch roleName failed , roleName is null!");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "创建新角色,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		roleService.save(role);
		SysLogUtil.saveLog(logService, "创建新角色,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		if(roleid==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "users/viewRole.jsp";
			return "failed";
		}
		if(roleid.trim().equals("")){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch userid failed , userid is ''!");
			backUrl = "users/viewRole.jsp";
			return "failed";
		}
		SysRole r=(SysRole)roleService.getObj(SysRole.class, Long.parseLong(roleid));
		if(r==null){
			returnMsg = "系统错误，跳转页面失败！";
			logger.info("fetch user failed from database !");
			backUrl = "users/viewRole.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		requestMap.put("mrole", r);
		return "success";
	}
	public String modifyRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(role.getId()==null){
			returnMsg = "系统错误，角色修改失败！";
			logger.info("fetch userid failed , userid is null !");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "修改角色信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(role.getRoleName()==null){
			returnMsg = "角色名称不能为空，保存失败！";
			logger.info("fetch roleName failed , roleName is null !");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "修改角色信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(role.getRoleName().trim().equals("")){
			returnMsg = "角色名称不能为空，保存失败！";
			logger.info("fetch roleName failed , roleName is ''!");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "修改角色信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		roleService.update(role);
		SysLogUtil.saveLog(logService, "修改角色信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String deleteRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(roleid==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch roleid failed , roleid is ''!");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "删除角色,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(roleid.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch roleid failed , roleid is ''!");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "删除角色,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] roleids=roleid.split(",");
		boolean flag=roleService.deleteRole(roleids);
		if(!flag){
			returnMsg = "系统错误，删除失败！";
			logger.info("delete role failed !");
			backUrl = "users/viewRole.jsp";
			SysLogUtil.saveLog(logService, "删除角色,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "删除角色,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String roleAppMapping(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String[] roleids=request.getParameterValues("roleid");
		String appid=request.getParameter("appid");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(roleids==null){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch roleid failed , roleid is null!");
			backUrl = "users/roleAppMapping.jsp";
			SysLogUtil.saveLog(logService, "为角色分配权限,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if(appid==null){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch appid failed , appid is null!");
			backUrl = "users/roleAppMapping.jsp";
			SysLogUtil.saveLog(logService, "为角色分配权限,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(appid.trim().equals("")){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("fetch appid failed , appid is null!");
			backUrl = "users/roleAppMapping.jsp";
			SysLogUtil.saveLog(logService, "为角色分配权限,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] appids=appid.split(",");
		boolean flag =roleService.createRoleAppMapping(roleids, appids);
		if(!flag){
			returnMsg = "系统错误，角色应用映射失败！";
			logger.info("insert database error!");
			backUrl = "users/roleAppMapping.jsp";
			SysLogUtil.saveLog(logService, "为角色分配权限,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "为角色分配权限,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void getRoleByUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleId=request.getParameter("roleId");
		if(roleId==null){
			return ;
		}
		if(roleId.trim().equals("")){
			return ;
		}
		List roleList=roleService.getAppByRole(Long.parseLong(roleId));
		//创建JSON
	}
	
	public void getAppByRole(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String roleid=request.getParameter("roleid");
		if(roleid==null){
			return ;
		}
		if(roleid.trim().equals("")){
			return ;
		}
		String appids=appService.queryAppByRoleid(roleid);
		String json="{\"appids\":\""+appids+"\"}";
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(json);
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	public void getAppTree(){
		List treeApp=appService.getAppTree();
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		JSONArray treeArray=new JSONArray();
		try {
			pw = resp.getWriter();
			if(treeApp.size()==0){
				pw.println(treeArray.toString());
				pw.flush();
				return;
			}
			JSONObject first=new JSONObject();
			JSONArray firstChild=new JSONArray();
			treeArray.put(first);
			JSONObject appJson=null;
			JSONArray children=null;
			for(int i=0;i<treeApp.size();i++){	
				SysApp app=(SysApp)treeApp.get(i);
				if(app.getId()==1){
					first.put("id", app.getId());
					first.put("text", app.getAppName());
					if(app.getHasLeaf().equals("1")){
						first.put("children", firstChild);
					}else{
						pw.println(treeArray.toString());
						pw.flush();
						return;
					}
				}else if(app.getHasLeaf().equals("1")&&app.getParentId()==1){
					appJson=new JSONObject();
					appJson.put("id", app.getId());
					appJson.put("text", app.getAppName());
					appJson.put("state", "closed");
					children=new JSONArray();
					appJson.put("children", children);
					firstChild.put(appJson);
				}else if(app.getHasLeaf().equals("0")&&app.getParentId()==1){
					appJson=new JSONObject();
					appJson.put("id", app.getId());
					appJson.put("text", app.getAppName());
					firstChild.put(appJson);
				}else{
					appJson=new JSONObject();
					appJson.put("id", app.getId());
					appJson.put("text", app.getAppName());
					if(children!=null){
						children.put(appJson);
					}
				}
				
			}
			
			System.out.println(treeArray.toString());
			pw.println(treeArray.toString());		
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
