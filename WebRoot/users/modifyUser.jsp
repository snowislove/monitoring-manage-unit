<%@ page language="java" import="java.util.*,com.opensymphony.xwork2.ActionContext" pageEncoding="UTF-8"%>
<%@ page import="com.secpro.platform.monitoring.manage.util.PasswdRuleUtil,com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
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
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/util/md5.js"></script>
	<script type="text/javascript" src="js/hashCode.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>用户信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:588px;background:#EFF5FF">
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:600px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyUser.action" method="post">
		    <input type="hidden" name="user.id" value="${ muser.id}"/>
		    <input type="hidden" name="passwd" value="${ muser.password}"/>
		    <input type="hidden" name="orgid" value="${muser.orgId }"/>
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input class="easyui-validatebox" id="uaccount" type="text" style="width:150px" missingMessage="请输入账号" name="user.account" data-options="required:true" value="${ muser.account}" disabled=true></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码：</label></td>
	    			<td><input id="newpasswd" class="easyui-validatebox" type="password" style="width:150px" missingMessage="请输入新密码,长度要大于<%=PasswdRuleUtil.passwdLong%>
	    			<%if(PasswdRuleUtil.hasNum.equals("1")||PasswdRuleUtil.hasChar.equals("1")||PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,包括
	    			<%}if(PasswdRuleUtil.hasNum.equals("1")){ %>
	    			1个数字
	    			<%}if(PasswdRuleUtil.hasChar.equals("1")){ %>
	    			,1个字母
	    			<%}if(PasswdRuleUtil.hasSpecialChar.equals("1")){ %>
	    			,1个特殊字符
	    			<%} %>
	    			" name="user.password" data-options="required:true" value="123!a45678901234" onblur="checkPasswd();"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入姓名" name="user.userName" data-options="required:true" value="${ muser.userName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input id="mobile" class="easyui-numberbox" type="text" style="width:150px" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true" value="${ muser.mobelTel}"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input id="officeTel" class="easyui-validatebox" type="text" style="width:150px"  name="user.officeTel" value="${ muser.officeTel}"> </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'" value="${ muser.email}"> </input> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="user.orgId" style="width:150px" missingMessage="请选择部门" value="${muser.orgId}" style="width:200px;" data-options="url:'getOrgTreeByid.action?id=${muser.orgId}'" >
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>用户描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" style="width:150px;height:50px" name="user.userDesc" style="resize:none;">${ muser.userDesc}</textarea> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启用：</label></td>
	    			<td>
	    			<c:if test="${ muser.enableAccount eq '0'}">
	    				<input type="radio" name="user.enableAccount" value="0" checked="true">启用</input>
	    				<input type="radio" name="user.enableAccount" value="1">暂停</input>
	    			</c:if>
	    			<c:if test="${ muser.enableAccount eq '1'}">
	    			<input type="radio" name="user.enableAccount" value="0" >启用</input>
	    				<input type="radio" name="user.enableAccount" value="1" checked="true">暂停</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>访问IP：</label></td>
	    			<td><input id="resIp1" class="easyui-numberbox" style="width:50px" type="text" name="user.ip1" missingMessage="请输入IP地址" value="${muser.ip1 }"  data-options="required:true"></input>.
	    			<input id="resIp2" class="easyui-numberbox" style="width:50px" type="text" name="user.ip2" missingMessage="请输入IP地址" value="${muser.ip2 }" data-options="required:true"></input>.
	    			<input id="resIp3" class="easyui-numberbox" style="width:50px" type="text" name="user.ip3" missingMessage="请输入IP地址" value="${muser.ip3 }" data-options="required:true"></input>.
	    			<input id="resIp4" class="easyui-numberbox" style="width:50px" type="text" name="user.ip4" missingMessage="请输入IP地址" value="${muser.ip4 }" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>工作开始时间：</label></td>
	    			<td><select id="hourStart" class="easyui-combobox" style="width:50px" name="user.hourStart" editable="false" data-options="required:true">
	    			<%
	    			ActionContext actionContext = ActionContext.getContext(); 
							Map<String,Object> requestMap=(Map)actionContext.get("request");
							SysUserInfo uu=(SysUserInfo)requestMap.get("muser");
	    				String myhour="00";
	    				for(int i=0;i<=23;i++){
	    					if(i<10){
	    						myhour="0"+i;
	    					}else{
	    						myhour=""+i;
	    					}
	    					
	    					if(uu.getHourStart()!=null&&uu.getHourStart().equals(myhour)){
	    					 %>
	    						<option value="<%=myhour%>" selected><%=i %></option>
	    					<%}else{ %>	
	    						<option value="<%=myhour%>"><%=i %></option>
	    					<%}} %>
	    					
	    				</select>&nbsp;时
	    				<select id="minuteStart" class="easyui-combobox" style="width:50px" name="user.minuteStart" editable="false" data-options="required:true">
	    					<%
	    				String mymini="00";
	    				for(int i=0;i<=59;i++){
	    					if(i<10){
	    						mymini="0"+i;
	    					}else{
	    						mymini=""+i;
	    					}
	    					if(uu.getMinuteStart()!=null&&uu.getMinuteStart().equals(mymini)){
	    					 %>
	    						<option value="<%=mymini%>" selected><%=i %></option>
	    					<%}else{ %>	
	    						<option value="<%=mymini%>"><%=i %></option>
	    					<%}} %>
	    					
	    				</select>&nbsp;分
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>工作结束时间：</label></td>
	    			<td><select id="hourEnd" class="easyui-combobox" style="width:50px" name="user.hourEnd" editable="false" data-options="required:true">
	    					<%
	    				String myhourEnd="00";
	    				for(int i=0;i<=23;i++){
	    					if(i<10){
	    						myhourEnd="0"+i;
	    					}else{
	    						myhourEnd=""+i;
	    					}
	    					if(uu.getHourEnd()!=null&&uu.getHourEnd().equals(myhourEnd)){
	    					 %>
	    						<option value="<%=myhourEnd%>" selected><%=i %></option>
	    					<%}else{ %>	
	    						<option value="<%=myhourEnd%>"><%=i %></option>
	    					<%}} %>
	    					
	    				</select>&nbsp;时
	    				<select id="minuteEnd" class="easyui-combobox" style="width:50px" name="user.minuteEnd" editable="false" data-options="required:true">
	    					<%
	    				String myminiEnd="00";
	    				for(int i=0;i<=59;i++){
	    					if(i<10){
	    						myminiEnd="0"+i;
	    					}else{
	    						myminiEnd=""+i;
	    					}
	    					if(uu.getMinuteEnd()!=null&&uu.getMinuteEnd().equals(myminiEnd)){
	    						
	    					 %>
	    						<option value="<%=myminiEnd%>" selected><%=i %></option>
	    					<%}else{ %>	
	    						<option value="<%=myminiEnd%>"><%=i %></option>
	    					<%}} %>
	    				</select>&nbsp;分
	    			</td>
	    		</tr>
	    		</table>
		    </form>		     
    	</div>
  </div>
  <script>
  		function _237(_238,_239,_23a){
			var win=$("<div class=\"messager-body\"></div>").appendTo("body");
			win.append(_239);
			if(_23a){
			var tb=$("<div class=\"messager-button\"></div>").appendTo(win);
			for(var _23b in _23a){
			$("<a></a>").attr("href","javascript:void(0)").text(_23b).css("margin-left",10).bind("click",eval(_23a[_23b])).appendTo(tb).linkbutton();
			}
			}
			win.window({title:_238,noheader:(_238?false:true),width:300,height:"auto",modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,onClose:function(){
			setTimeout(function(){
			win.window("destroy");
			},100);
			}});
			win.window("window").addClass("messager-window");
			win.children("div.messager-button").children("a:first").focus();
			return win;
		}
 		$.extend($.messager, {    
   			 prompt : function(_1c, msg, fn, type) {
    			var _t = 'text';
    			if(type != 'undefined' && type == 'password'){
    				_t = 'password';
    			}
				var _1d = "<div class=\"messager-icon messager-question\"></div>"
						+ "<div>"
						+ msg
						+ "</div>"
						+ "<br/>"
						+ "<div style=\"clear:both;\"/>"
						+ "<div><input class=\"messager-input\" type=\""+_t+"\"/></div>";
				var _1e = {};
				_1e[$.messager.defaults.ok] = function() {
					win.window("close");
					if (fn) {
						fn($(".messager-input", win).val());
						return false;
					}
				};
				_1e[$.messager.defaults.cancel] = function() {
					win.window("close");
					if (fn) {
						fn();
						return false;
					}
				};
				var win = _237(_1c, _1d, _1e);
				//win.children("input.messager-input").focus();
				return win;
			}
		}); 
		function submitForm(){
			if(fl==1){
				
				$.messager.alert("错误提示", "密码不符合规则,请重新填写！", "info");
				return false;
			}
			var resIp1=document.getElementById("resIp1");
			var resIp2=document.getElementById("resIp2");
			var resIp3=document.getElementById("resIp3");
			var resIp4=document.getElementById("resIp4");
			var mobile=document.getElementById("mobile");
			var officeTel=document.getElementById("officeTel");
			if(resIp1.value>255||resIp2.value>255||resIp3.value>255||resIp4.value>255){
				$.messager.alert("错误提示", "ip地址不能超过255！", "info");
				return false;
			}
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
			var obj1 = $('#hourStart').combobox('getValue');
			var obj2 = $('#minuteStart').combobox('getValue');
			var obj3 = $('#hourEnd').combobox('getValue');
			var obj4 = $('#minuteEnd').combobox('getValue');
			
			if(Number(obj3)*60+Number(obj4)<Number(obj1)*60+Number(obj2)){
				
				$.messager.alert("错误提示", "工作结束时间不能小于工作开始时间！", "info");
				return false;
			}
			var ff= $('#ff').form('validate');
			if(ff==false){
				return false; 
			}
		$(function() {
					$.messager.prompt("操作提示", "请输入确认密码：", function(str) {
						var md5Pwd = hex_md5(str);
						if (md5Pwd == "${sessionScope.user.password}") {
							var ff = document.getElementById("ff");
							var md5s = checkform(ff);
							$.post('md5Receive.action', {
								checkS : md5s
							}, function(data) {
								if (data.res == "ok") {
									document.getElementById("ff").submit();
								} else {
									$.messager.alert("错误提示", "数据提交失败！", "info");
								}
							});
						} else {
							$.messager.alert("错误提示", "密码错误,提交失败！", "info");
							return;
						}
					},'password');
				});
		}
		var fl=0;
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
 															//newpasswd.focus();
 														}else if(data.checkok=="3"){
 															
 															$.messager.alert("错误提示", "密码中最少需要一个英文字母，请重新填写！", "info");
 															fl=1;
 															//newpasswd.focus();
 														}else if(data.checkok=="4"){
 															
 															$.messager.alert("错误提示", "密码中最少需要一个特殊字符，请重新填写！", "info");
 															fl=1;
 															//newpasswd.focus();
 														}else if(data.checkok=="5"){
 															
 															$.messager.alert("错误提示", "密码不能超过16位，请重新填写！", "info");
 															fl=1;
 															//newpasswd.focus();
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