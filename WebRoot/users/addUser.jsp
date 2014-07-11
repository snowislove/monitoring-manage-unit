<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.secpro.platform.monitoring.manage.util.PasswdRuleUtil" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>创建用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/md5Builder.js"></script>
	<script type="text/javascript" src="js/hashCode.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>创建用户";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:600px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveUser.action" method="post" >
		    	<table>
		    	<tr>
	    			<td><label>账号：</label></td>
	    			<td><input id="account" class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入账号" name="user.account" data-options="required:true"></input></td>
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
	    			" name="user.password" data-options="required:true" onblur="checkPasswd();"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>姓名：</label></td>
	    			<td><input id="userName" class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入姓名" name="user.userName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>移动电话：</label></td>
	    			<td><input id="mobile" class="easyui-numberbox" type="text" style="width:150px" missingMessage="请输入移动电话" name="user.mobelTel" data-options="required:true"> </input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>座机：</label></td>
	    			<td><input id="officeTel" class="easyui-validatebox" type="text" style="width:150px"  name="user.officeTel" > </input> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>邮箱：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入邮箱" name="user.email" data-options="required:true,validType:'email'"> </input> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="user.orgId" style="width:150px" missingMessage="请选择部门" data-options="url:'allOrgRree.action',required:true" style="width:200px;">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>用户描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" style="width:150px;height:50px" name="user.userDesc" style="resize:none;"></textarea> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启用：</label></td>
	    			<td><input type="radio" name="user.enableAccount" value="0" checked="true">启用</input>
	    				<input type="radio" name="user.enableAccount" value="1">暂停</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>访问IP：</label></td>
	    			<td><input id="resIp1" class="easyui-numberbox" style="width:50px" type="text" name="user.ip1" missingMessage="请输入IP地址"  data-options="required:true"></input>.
	    			<input id="resIp2" class="easyui-numberbox" style="width:50px" type="text" name="user.ip2" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input id="resIp3" class="easyui-numberbox" style="width:50px" type="text" name="user.ip3" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input id="resIp4" class="easyui-numberbox" style="width:50px" type="text" name="user.ip4" missingMessage="请输入IP地址" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>工作开始时间：</label></td>
	    			<td><select id="hourStart" class="easyui-combobox" style="width:50px" name="user.hourStart" editable="false" data-options="required:true">
	    					<option value="00">0</option>
	    					<option value="01">1</option>
	    					<option value="02">2</option>
	    					<option value="03">3</option>
	    					<option value="04">4</option>
	    					<option value="05">5</option>
	    					<option value="06">6</option>
	    					<option value="07">7</option>
	    					<option value="08" selected>8</option>
	    					<option value="09">9</option>
	    					<option value="10">10</option>
	    					<option value="11">11</option>
	    					<option value="12">12</option>
	    					<option value="13">13</option>
	    					<option value="14">14</option>
	    					<option value="15">15</option>
	    					<option value="16">16</option>
	    					<option value="17">17</option>
	    					<option value="18">18</option>
	    					<option value="19">19</option>
	    					<option value="20">20</option>
	    					<option value="21">21</option>
	    					<option value="22">22</option>
	    					<option value="23">23</option>
	    				</select>&nbsp;时
	    				<select id="minuteStart" class="easyui-combobox" style="width:50px" name="user.minuteStart" editable="false" data-options="required:true">
	    					<option value="00">0</option>
	    					<option value="01">1</option>
	    					<option value="02">2</option>
	    					<option value="03">3</option>
	    					<option value="04">4</option>
	    					<option value="05">5</option>
	    					<option value="06">6</option>
	    					<option value="07">7</option>
	    					<option value="08">8</option>
	    					<option value="09">9</option>
	    					<option value="10">10</option>
	    					<option value="11">11</option>
	    					<option value="12">12</option>
	    					<option value="13">13</option>
	    					<option value="14">14</option>
	    					<option value="15">15</option>
	    					<option value="16">16</option>
	    					<option value="17">17</option>
	    					<option value="18">18</option>
	    					<option value="19">19</option>
	    					<option value="20">20</option>
	    					<option value="21">21</option>
	    					<option value="22">22</option>
	    					<option value="23">23</option>
	    					<option value="24">24</option>
	    					<option value="25">25</option>
	    					<option value="26">26</option>
	    					<option value="27">27</option>
	    					<option value="28">28</option>
	    					<option value="29">29</option>
	    					<option value="30" selected>30</option>
	    					<option value="31">31</option>
	    					<option value="32">32</option>
	    					<option value="33">33</option>
	    					<option value="34">34</option>
	    					<option value="35">35</option>
	    					<option value="36">36</option>
	    					<option value="37">37</option>
	    					<option value="38">38</option>
	    					<option value="39">39</option>
	    					<option value="40">40</option>
	    					<option value="41">41</option>
	    					<option value="42">42</option>
	    					<option value="43">43</option>
	    					<option value="44">44</option>
	    					<option value="45">45</option>
	    					<option value="46">46</option>
	    					<option value="47">47</option>
	    					<option value="48">48</option>
	    					<option value="49">49</option>
	    					<option value="50">50</option>
	    					<option value="51">51</option>
	    					<option value="52">52</option>
	    					<option value="53">53</option>
	    					<option value="54">54</option>
	    					<option value="55">55</option>
	    					<option value="56">56</option>
	    					<option value="57">57</option>
	    					<option value="58">58</option>
	    					<option value="59">59</option>
	    					
	    				</select>&nbsp;分
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>工作结束时间：</label></td>
	    			<td><select id="hourEnd" class="easyui-combobox" style="width:50px" name="user.hourEnd" editable="false" data-options="required:true">
	    					<option value="00">0</option>
	    					<option value="01">1</option>
	    					<option value="02">2</option>
	    					<option value="03">3</option>
	    					<option value="04">4</option>
	    					<option value="05">5</option>
	    					<option value="06">6</option>
	    					<option value="07">7</option>
	    					<option value="08">8</option>
	    					<option value="09">9</option>
	    					<option value="10">10</option>
	    					<option value="11">11</option>
	    					<option value="12">12</option>
	    					<option value="13">13</option>
	    					<option value="14">14</option>
	    					<option value="15">15</option>
	    					<option value="16">16</option>
	    					<option value="17">17</option>
	    					<option value="18" selected>18</option>
	    					<option value="19">19</option>
	    					<option value="20">20</option>
	    					<option value="21">21</option>
	    					<option value="22">22</option>
	    					<option value="23">23</option>
	    				</select>&nbsp;时
	    				<select id="minuteEnd" class="easyui-combobox" style="width:50px" name="user.minuteEnd" editable="false" data-options="required:true">
	    					<option value="00">0</option>
	    					<option value="01">1</option>
	    					<option value="02">2</option>
	    					<option value="03">3</option>
	    					<option value="04">4</option>
	    					<option value="05">5</option>
	    					<option value="06">6</option>
	    					<option value="07">7</option>
	    					<option value="08">8</option>
	    					<option value="09">9</option>
	    					<option value="10">10</option>
	    					<option value="11">11</option>
	    					<option value="12">12</option>
	    					<option value="13">13</option>
	    					<option value="14">14</option>
	    					<option value="15">15</option>
	    					<option value="16">16</option>
	    					<option value="17">17</option>
	    					<option value="18">18</option>
	    					<option value="19">19</option>
	    					<option value="20">20</option>
	    					<option value="21">21</option>
	    					<option value="22">22</option>
	    					<option value="23">23</option>
	    					<option value="24">24</option>
	    					<option value="25">25</option>
	    					<option value="26">26</option>
	    					<option value="27">27</option>
	    					<option value="28">28</option>
	    					<option value="29">29</option>
	    					<option value="30">30</option>
	    					<option value="31">31</option>
	    					<option value="32">32</option>
	    					<option value="33">33</option>
	    					<option value="34">34</option>
	    					<option value="35">35</option>
	    					<option value="36">36</option>
	    					<option value="37">37</option>
	    					<option value="38">38</option>
	    					<option value="39">39</option>
	    					<option value="40">40</option>
	    					<option value="41">41</option>
	    					<option value="42">42</option>
	    					<option value="43">43</option>
	    					<option value="44">44</option>
	    					<option value="45">45</option>
	    					<option value="46">46</option>
	    					<option value="47">47</option>
	    					<option value="48">48</option>
	    					<option value="49">49</option>
	    					<option value="50">50</option>
	    					<option value="51">51</option>
	    					<option value="52">52</option>
	    					<option value="53">53</option>
	    					<option value="54">54</option>
	    					<option value="55">55</option>
	    					<option value="56">56</option>
	    					<option value="57">57</option>
	    					<option value="58">58</option>
	    					<option value="59">59</option>
	    					
	    				</select>&nbsp;分
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    			&nbsp;
	    			</td>
	    			<td>
	    			&nbsp;
	    			</td>
	    		<tr>
	    		<tr>
	    			<td>
	    			<input type="button" value="提交" onclick="return submitForm();"/>
	    			</td>
	    			<td>
	    			<input type="reset" value="重置"/>
	    			</td>
	    		<tr>
	    	</table>
	    	
		    </form>		     
    	</div>
  </div>
   <script>
 
		function submitForm(){
			var resIp1=document.getElementById("resIp1");
			var resIp2=document.getElementById("resIp2");
			var resIp3=document.getElementById("resIp3");
			var resIp4=document.getElementById("resIp4");
			var mobile=document.getElementById("mobile");
			var officeTel=document.getElementById("officeTel");
			if(fl==1){
				$.messager.alert("错误提示", "密码不符合规则,请重新填写！", "info");
				
				return false;
			}
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
		var fl=1;
		function checkPasswd(){
			var newpasswd=document.getElementById("newpasswd");
			
			$.post('checkPasswd.action', {newpasswd:newpasswd.value},function(data){
								 													 						
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
	         	if(inputObj[i].name!=""&&inputObj[i].type!="radio"){	         		         		
	         		ss=ss+hashCode(inputObj[i].value);
	         	
	         	}
	         }
	        
	       var md5s=hex_md5(""+ss);
	       return md5s;
     	}
	</script>
  </body>
</html>
