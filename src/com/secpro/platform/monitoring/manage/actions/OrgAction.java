package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysOrgService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("OrgAction")
public class OrgAction {
	PlatformLogger logger = PlatformLogger.getLogger(OrgAction.class);
	private String returnMsg;
	private String backUrl;
	private SysOrgService orgService;
	private SysOrg org;
	private SysUserInfoService suiService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SysOrg getOrg() {
		return org;
	}
	public void setOrg(SysOrg org) {
		this.org = org;
	}
	public SysUserInfoService getSuiService() {
		return suiService;
	}
	@Resource(name = "SysUserInfoServiceImpl")
	public void setSuiService(SysUserInfoService suiService) {
		this.suiService = suiService;
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
	public SysOrgService getOrgService() {
		return orgService;
	}
	@Resource(name="SysOrgServiceImpl")
	public void setOrgService(SysOrgService orgService) {
		this.orgService = orgService;
	}
	public void viewAllOrg(){
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
		List allOrgs=orgService.queryAll("from SysOrg o where o.parentOrgId!=0");
		List pageOrgs=orgService.queryByPage("select o1.id,o1.orgName,o1.orgDesc,o1.parentOrgId,o2.orgName from SysOrg o1,SysOrg o2 where o1.parentOrgId=o2.id order by o1.parentOrgId",pageNum,maxPage);
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allOrgs == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看部门列表,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			json.put("total", allOrgs.size());
			json.put("rows", arr);
			for (int i = 0; i < pageOrgs.size(); i++) {
				JSONObject sub=new JSONObject();
				Object obj[]=(Object[])pageOrgs.get(i);
				
				sub.put("orgId", obj[0]);
				
				sub.put("orgName", obj[1]);
				
				sub.put("orgDesc", (obj[2]==null?" ":obj[2]));
				
				sub.put("parentOrgId", obj[3]);
			
				sub.put("parentName", obj[4]);
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看部门列表,成功", user.getAccount(), request.getRemoteAddr());
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
	public String saveOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(org.getOrgName()==null){
			returnMsg = "组织名称不能为空，组织添加失败！";
			logger.info("fetch orgName failed , orgName is null!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "创建部门信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(org.getOrgName().trim().equals("")){
			returnMsg = "组织名称不能为空，组织添加失败！";
			logger.info("fetch orgName failed , orgName is ''!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "创建部门信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		org.setHasLeaf("0");
		orgService.save(org);
		if(org.getParentOrgId()!=null){
			SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, org.getParentOrgId());
			o.setHasLeaf("1");
			orgService.update(o);
		}
		SysLogUtil.saveLog(logService, "创建部门信息,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String toModifyOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgId=request.getParameter("orgId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(orgId==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch orgId failed , orgId is null!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "跳转部门修改页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(orgId.trim().equals("")){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch orgId failed , orgId is ''!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "跳转部门修改页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, Long.parseLong(orgId));
		if(o==null){
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch SysOrg failed from database!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "跳转部门修改页面,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext(); 
		Map<String,Object> requestMap=(Map)actionContext.get("request");
		if(o.getParentOrgId()!=null){
			SysOrg parent=(SysOrg)orgService.getObj(SysOrg.class, o.getParentOrgId());
			if(parent!=null){
				requestMap.put("parent", parent);
			}
		}
		requestMap.put("morg", o);
		SysLogUtil.saveLog(logService, "跳转部门修改页面,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public String modifyOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(org.getId()==null){
			returnMsg = "系统错误，组织修改失败！";
			logger.info("fetch orgId failed , orgId is null!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "修改部门信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(org.getOrgName()==null){
			returnMsg = "组织名称不能为空，组织修改失败！";
			logger.info("fetch orgName failed , orgName is null!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "修改部门信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(org.getOrgName().trim().equals("")){
			
			returnMsg = "组织名称不能为空，组织修改失败！";
			logger.info("fetch orgName failed , orgName is ''!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "修改部门信息,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, org.getId());
		o.setOrgName(org.getOrgName());
		o.setOrgDesc(org.getOrgDesc());
		
		orgService.update(o);
		
		SysLogUtil.saveLog(logService, "修改部门信息,成功", user.getAccount(), request.getRemoteAddr());
		
		return "success";
	}
	public String deleteOrg(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgid=request.getParameter("orgids");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if(orgid==null){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch orgids failed , orgids is null!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "删除部门,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if(orgid.trim().equals("")){
			returnMsg = "系统错误，删除失败！";
			logger.info("fetch orgids failed , orgids is ''!");
			backUrl = "users/viewOrg.jsp";
			SysLogUtil.saveLog(logService, "删除部门,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] orgids=orgid.split(",");
		for(int i=0;i<orgids.length;i++){
			System.out.println(Long.parseLong(orgids[i])+"=============================");
			List users=suiService.queryAll("from SysUserInfo s where s.orgId="+Long.parseLong(orgids[i]));
			if(users!=null&&users.size()>0){
				returnMsg="部分组织下存在用户，请先删除用户！";
				backUrl = "users/viewOrg.jsp";
				SysLogUtil.saveLog(logService, "删除部门,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}else{
				
				SysOrg o=(SysOrg)orgService.getObj(SysOrg.class, Long.parseLong(orgids[i]));
				List childList=orgService.queryAll("from SysOrg s where s.parentOrgId="+o.getId());
				if(childList!=null && childList.size()>0){
					returnMsg="请先删除子部门！";
					backUrl = "users/viewOrg.jsp";
					SysLogUtil.saveLog(logService, "删除部门,失败", user.getAccount(), request.getRemoteAddr());
					return "failed";
				}
				orgService.delete(o);
				List l=orgService.queryAll("from SysOrg s where s.parentOrgId="+o.getParentOrgId());
				if(l!=null&&l.size()>0){
					
				}else{
					SysOrg par=(SysOrg)orgService.getObj(SysOrg.class, o.getParentOrgId());
					par.setHasLeaf("0");
					orgService.update(par);
				}
			}
		}
		SysLogUtil.saveLog(logService, "删除部门,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}
	public void allOrgRree(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("id");
		String hql="";
		if(id==null){
	//	sql="select * from sys.dept start with paredeptid=0 connect by prior deptid=paredeptid";
			hql="from SysOrg o where o.parentOrgId=0";
		}else if(id.trim().equals("")){
			hql="from SysOrg o where o.parentOrgId=0";
		}else{
			hql="from SysOrg o where o.parentOrgId="+id;
		}
		List orgList=orgService.queryAll(hql);
		if(orgList==null){
			return ;
		}
		if(orgList.size()==0){
			return ;
		}
		JSONArray jsonArrayIn = new JSONArray();
		try {
			for(Object o:orgList){
				SysOrg org=(SysOrg)o;
				JSONObject json = new JSONObject();
				json.put("id", org.getId());
				json.put("text", org.getOrgName());
				if(org.getHasLeaf().equals("1")){
					json.put("state", "closed");
				}
				jsonArrayIn.put(json);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			pw.println(jsonArrayIn.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getOrgTreeByid(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String orgid=request.getParameter("id");
		List selectList =orgService.getOrgTreeByOrgId("1");
		SysOrg root=formatTree(selectList);		
		System.out.println("--------------------------"+orgid);
		JSONArray arr=formatJSONArray(root,Long.parseLong(orgid));	
		PrintWriter pw = null;
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/json");
		try {
			pw = resp.getWriter();
			System.out.println(arr.toString());
			pw.println(arr.toString());
			pw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public SysOrg formatTree(List list) {

        SysOrg root = new SysOrg();
        SysOrg node = new SysOrg();
        List treelist = new ArrayList();// 拼凑好的json格式的数据
        List parentnodes = new ArrayList();// parentnodes存放所有的父节点
        
        if (list != null && list.size() > 0) {
            root = (SysOrg)list.get(0) ;
            //循环遍历oracle树查询的所有节点
            for (int i = 1; i < list.size(); i++) {
                node = (SysOrg)list.get(i);
                if(node.getParentOrgId().equals(root.getId())){
                    //为tree root 增加子节点
                    parentnodes.add(node) ;
                    root.getChildren().add(node) ;
                }else{//获取root子节点的孩子节点
                    getChildrenNodes(parentnodes, node);
                    parentnodes.add(node) ;
                }
            }    
        }
        
        return root ;

    }

    private void getChildrenNodes(List<SysOrg> parentnodes, SysOrg node) {
        //循环遍历所有父节点和node进行匹配，确定父子关系
        for (int i = parentnodes.size() - 1; i >= 0; i--) {
            
            SysOrg pnode = parentnodes.get(i);
            //如果是父子关系，为父节点增加子节点，退出for循环
            if (pnode.getId().equals(node.getParentOrgId())) {
                pnode.setState("closed") ;//关闭二级树
                pnode.getChildren().add(node) ;
                return ;
            } else {
                //如果不是父子关系，删除父节点栈里当前的节点，
                //继续此次循环，直到确定父子关系或不存在退出for循环
                parentnodes.remove(i) ;
            }
        }
    }

	private JSONArray formatJSONArray(SysOrg root,long id) {
		JSONArray arr = new JSONArray();	
		try {
			if (root.getChildren().size() > 0) {
				JSONObject r = new JSONObject();
				r.put("id", root.getId());
				r.put("text", root.getOrgName());
				if(root.getId().longValue()==id){
					r.put("checked", true);
				}
				r.put("state", "closed");
				arr.put(r);
				JSONArray ch=new JSONArray();
				r.put("children", ch);
				for (int i = 0; i < root.getChildren().size(); i++) {				
					SysOrg org = (SysOrg) root.getChildren().get(i);
					ch.put( formatJSON(org,id));
				}
			} else {
				JSONObject r = new JSONObject();
				r.put("id", root.getId());
				r.put("text", root.getOrgName());
				if(root.getId().longValue()==id){
					r.put("checked", true);
				}
				arr.put(r);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	private JSONObject formatJSON(SysOrg node,long id){
		JSONObject json=new JSONObject();
		try {
			json.put("id", node.getId());
			json.put("text", node.getOrgName());
			if (node.getChildren().size() > 0) {
				json.put("state", "closed");
				JSONArray subar=new JSONArray();
				if(node.getId().longValue()==id){
					json.put("checked", true);
				}
				json.put("children", subar);
				for(int i=0;i<node.getChildren().size();i++){
					subar.put(formatJSON(node.getChildren().get(i),id));
				}
			}else{
				
				json.put("id", node.getId());
				json.put("text", node.getOrgName());
				if(node.getId().longValue()==id){
					json.put("checked", true);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
}
