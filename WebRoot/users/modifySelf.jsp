<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.secpro.platform.monitoring.manage.util.PasswdRuleUtil" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>用户修改</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/util/md5.js"></script>
	<script type="text/javascript" src="js/hashCode.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>个人信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifySelf.action" method="post">
		    <input type="hidden"  name="user.id" value="${ user.id}"/>
		    <input type="hidden" name="passwd" value="${ user.password}"/>
		    <input type="hidden" name="orgid" value="${user.orgId }"/>
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input id="uaccount" class="easyui-validatebox" type="text" missingMessage="请输入账号" name="user.account" data-options="required:true" value="${ user.account}" disabled=true></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码：</label></td>
	    			<td><input id="newpasswd" class="easyui-validatebox" type="password" missingMessage="请输入新密码,长度要大于<%=PasswdRuleUtil.passwdLong%>
	    			<%if(PasswdRuleUtil.hasNum.equals("1")||PasswdRuleUtil.hasChar.equals("1")||PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,包括
	    			<%}if(PasswdRuleUtil.hasNum.equals("1")){ %>
	    			1个数字
	    			<%}if(PasswdRuleUtil.hasChar.equals("1")){ %>
	    			,1个字母
	    			<%}if(PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,1个特殊字符
	    			<%} %>
					" name="user.password" data-options="required:true" value="123!a45678901234" onblur="checkPasswd();"></input>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入姓名" name="user.userName" data-options="required:true" value="${ user.userName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input class="easyui-numberbox" type="text" id="mobile" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true" value="${ user.mobelTel}"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input class="easyui-validatebox" type="text" id="officeTel" name="user.officeTel" value="${ user.officeTel}"> </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'" value="${ user.email}"> </input> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="user.orgId" missingMessage="请选择部门" value="${user.orgId}" data-options="url:'getOrgTreeByid.action?id=${user.orgId}'" style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>用户描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="user.userDesc" style="resize:none;">${ user.userDesc}</textarea> 
	    			</td>
	    		</tr>
	    		</table>
	    		
		    </form>		     
    	</div>
  </div>
  <script>
  var fl=0;
		function submitForm(){
			if(fl==1){
				$.messager.alert("错误提示", "密码不符合规则,请重新填写！", "info");
				return false;
			}
			var mobile=document.getElementById("mobile");
			var officeTel=document.getElementById("officeTel");
			if(mobile.value.length>11){
				$.messager.alert("错误提示", "移动电话格式错误，位数过多！", "info");
				return false;
			}
			if(officeTel.value!=""){
			 var partten = /^(\d{3,4}\-)?\d{7,8}$/i;
			 var zuoji=partten.test(officeTel.value);
			 if(!zuoji){
				$.messager.alert("错误提示", "座机格式错误,格式为010-88888888或88888888！", "info");
				return false;
			}
			}
			var flag= $('#ff').form('validate');
			if(flag){
				var ff=document.getElementById("ff");
				var md5s=checkform(ff);
				$.post('md5Receive.action', {checkS:md5s},function(data){
					if(data.res=="ok"){
						document.getElementById("ff").submit();
					}else{
						$.messager.alert("错误提示", "数据提交失败！", "info");
					}
				});
			}else{
				return false;
			}
		}
		function checkPasswd(){
			var newpasswd=document.getElementById("newpasswd");
			var uaccount=document.getElementById("uaccount");
			$.post('checkPasswd.action', {uaccount:uaccount.value,newpasswd:newpasswd.value},function(data){
								 													 						
								 						if(data.checkok=="1"){
 															
 															$.messager.alert("错误提示", "密码错误长度不够，请重新填写！", "info");
 															fl=1;
 															//newpasswd.focus();
 														}else if(data.checkok=="2"){
 															
 															$.messager.alert("错误提示", "密码中最少需要一个数字，请重新填写！", "info");
 															fl=1;
 															//newpasswd.focus();
 														}else if(data.checkok=="0"){
 															
 															$.messager.alert("错误提示", "新密码不能为空！", "info");
 															fl=1;
 														//	newpasswd.focus();
 														}else if(data.checkok=="3"){
 															
 															$.messager.alert("错误提示", "密码中最少需要一个英文字母，请重新填写！", "info");
 															fl=1;
 														//	newpasswd.focus();
 														}else if(data.checkok=="4"){
 															
 															$.messager.alert("错误提示", "密码中最少需要一个特殊字符，请重新填写！", "info");
 															fl=1;
 														//	newpasswd.focus();
 														}else if(data.checkok=="5"){
 															
 															$.messager.alert("错误提示", "密码不能超过16位，请重新填写！", "info");
 															fl=1;
 														//	newpasswd.focus();
 														}else if(data.checkok=="6"){ 	
 															
 															$.messager.alert("错误提示", "密码不能和前<%=PasswdRuleUtil.passwdRepeatNum%>次相同,请重新填写！", "info");
 															fl=1;
 														//	newpasswd.focus();
 														}else{
 															fl=0;
 														}
 														
													});
		}
		function checkform(objfrm)
     	{
     		var ss=0;
	         var inputObj = objfrm.getElementsByTagName("input");
	         for (i = 0; i < inputObj.length; i++) {
	         	if(inputObj[i].name!=""&&inputObj[i].name!="user.account"&&inputObj[i].type!="radio"){
	         		
	         		ss=ss+hashCode(inputObj[i].value);
	         	
	         	}
	         }
	       
	       var md5s=hex_md5(""+ss);
	       return md5s;
     	}
	</script>
</body>
</html>