<%@ page language="java" import="java.util.*,com.secpro.platform.monitoring.manage.util.PasswdRuleUtil" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<title>密码重置</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		密码过期，请修改密码
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="resetPasswd.action" method="post" onsubmit="return submitForm();">
		    	 <input id="uaccount" type="hidden" name="uaccount" value="${uaccount}"/>
		    	 <input id="o" type="hidden" name="o" value="1"/>
		    	 <input id="n" type="hidden" name="n" value="1"/>
		    	<table>
		    	<tr>
	    			<td><label>旧密码：</label></td>
	    			<td><input id="oldpasswd" class="easyui-validatebox" type="password" missingMessage="请输入旧密码" name="oldpasswd" data-options="required:true" onblur="checkOldPasswd();"></input><div id="d1" style="color:red"></div></td>
	    		</tr>
	    		<tr>
	    			<td><label>新密码：</label></td>
	    			<td><input id="newpasswd" class="easyui-validatebox" type="password" missingMessage="请输入新密码,长度要大于${minlength}
	    			<%if(PasswdRuleUtil.hasNum.equals("1")||PasswdRuleUtil.hasChar.equals("1")||PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,包括
	    			<%}if(PasswdRuleUtil.hasNum.equals("1")){ %>
	    			1个数字
	    			<%}if(PasswdRuleUtil.hasChar.equals("1")){ %>
	    			,1个字母
	    			<%}if(PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,1个特殊字符
	    			<%} %>
	    			" name="newpasswd" data-options="required:true" onblur="checknewPasswd();"></input><div id="d2" style="color:red"></div></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>重复新密码：</label></td>
	    			<td><input id="rnewpaswd" class="easyui-validatebox" type="password" missingMessage="请重复输入新密码" name="rnewpaswd" data-options="required:true"></input><div id="d3" style="color:red"></div></td>
	    		</tr>
	    		<tr>
	    			<td rowspan=2><label><font color="red">${loginError}</font></label></td>	    			
	    		</tr>
	    		<tr>
	    			<td><input type="submit" value="提交"></input></td>
	    			  			
	    		</tr>
	    		</table>
	    		
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			
			var newpasswd=document.getElementById("newpasswd");
			var rnewpasswd=document.getElementById("rnewpaswd");
			var oldpasswd=document.getElementById("oldpasswd");
			var o=document.getElementById("o");
			var n=document.getElementById("n");
			
		
			if(o.value=="1"){
				
				$.messager.alert("错误提示", "旧密码不正确！", "info");
				return false;
			}
			
			
		
			if(n.value=="1"){
				
				$.messager.alert("错误提示", "新密码不符合规范！", "info");
				return false;
			}
			
			if(newpasswd.value!=rnewpasswd.value){
		
				$.messager.alert("错误提示", "重复密码与新密码不相同，请重新填写！", "info");
				return false;
			}
			if(newpasswd.value==oldpasswd.value){
				
				$.messager.alert("错误提示", "新旧密码不能相同，请重新填写！", "info");
				return false;
			}
			
			return $('#ff').form('validate');
		}
		function checkOldPasswd(){
			var uaccount=document.getElementById("uaccount");
			var oldpasswd=document.getElementById("oldpasswd");
			var o=document.getElementById("o");
			$.post('checkOldPasswd.action', {uaccount:uaccount.value,oldpasswd:oldpasswd.value},function(data){
								 						var d1=document.getElementById("d1");							 						
								 						if(data.checkok==null){		
								 							o.value="1";													
 															d1.innerText="密码错误，请重新填写！";
 														//	oldpasswd.focus();
 														//	oldpasswd.focus();
 														}else{
 															o.value="0";
 															d1.innerText="正确！";
 														}
 														
													});
		}
		function checknewPasswd(){
			var uaccount=document.getElementById("uaccount");
			var newpasswd=document.getElementById("newpasswd");
			
			$.post('checkNewPasswd.action', {uaccount:uaccount.value,newpasswd:newpasswd.value},function(data){
								 						var d2=document.getElementById("d2");	
								 						var n=document.getElementById("n");						 						
								 						if(data.checkok=="1"){
								 							n.value="1";																													
 															d2.innerText="密码错误长度不够，请重新填写！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="2"){
 															n.value="1";																														
 															d2.innerText="密码中最少需要一个数字，请重新填写！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="0"){	
 															n.value="1";		
 															d2.innerText="新密码不能为空！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="3"){
 															n.value="1";
 															d2.innerText="密码中最少需要一个英文字母，请重新填写！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="4"){
 															n.value="1"; 															
 															d2.innerText="密码中最少需要一个特殊字符，请重新填写！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="5"){ 	
 															n.value="1";														
 															d2.innerText="密码不能超过16位，请重新填写！";
 														//	newpasswd.focus();
 														}else if(data.checkok=="6"){ 	
 															n.value="1";														
 															d2.innerText="密码不能和前<%=PasswdRuleUtil.passwdRepeatNum%>次相同！";
 														//	newpasswd.focus();
 														}else{
 															n.value="0";
 															d2.innerText="符合规则！";
 														}
 														
													});
		}
	</script>
</body>
</html>