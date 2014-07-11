package com.secpro.platform.monitoring.manage.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author liyan 用以保存完整性校验码
 * 
 */

@Controller("Md5RecAction")
public class Md5RecAction {
	public void md5Receive(){
		HttpServletRequest request=ServletActionContext.getRequest();
		
		JSONObject json=new JSONObject();
		PrintWriter pw = null;
		try {
			String md5=request.getParameter("checkS");
			if(md5!=null&&!md5.trim().equals("")){
				json.put("res", "ok");
			}else{
				json.put("res", "no");
			}
			request.getSession().setAttribute("md5check", md5);
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			
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
}
