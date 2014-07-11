<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>资源查看</title>
    
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
		adiv.innerText="资源管理>查看资源明细";
	</script>
	<script>
		function deitable(){
			var ip1=document.getElementById("ip1");
			ip1.disabled=false;
			var ip2=document.getElementById("ip2");
			ip2.disabled=false;
			var ip3=document.getElementById("ip3");
			ip3.disabled=false;
			var ip4=document.getElementById("ip4");
			ip4.disabled=false;
			var resName=document.getElementById("resName");
			resName.disabled=false;
			var username=document.getElementById("username");
			username.disabled=false;
			var password=document.getElementById("password");
			password.disabled=false;
		//	var cc1=document.getElementById("cc1");
		//	cc1.disabled=false;
			$('#cc1').combobox('enable');
		//	var cc2=document.getElementById("cc2");
		//	cc2.disabled=false;
			$('#cc2').combobox('enable');
			var resDesc=document.getElementById("resDesc");
			resDesc.disabled=false;
			var resPaused1=document.getElementById("resPaused1");
			resPaused1.disabled=false;
			var resPaused2=document.getElementById("resPaused2");
			resPaused2.disabled=false;
			//var configOperation=document.getElementById("configOperation");
			//configOperation.disabled=false;
			$('#configOperation').combobox('enable');
			//var statusOperation=document.getElementById("statusOperation");
			//statusOperation.disabled=false;
			$('#statusOperation').combobox('enable');
			var commuinty=document.getElementById("commuinty");
			commuinty.disabled=false;
			var snmpuser=document.getElementById("snmpuser");
			snmpuser.disabled=false;
			var snmpau=document.getElementById("snmpau");
			snmpau.disabled=false;
			var snmpaups=document.getElementById("snmpaups");
			snmpaups.disabled=false;
			var snmppr=document.getElementById("snmppr");
			snmppr.disabled=false;
			var snmpprps=document.getElementById("snmpprps");
			snmpprps.disabled=false;
			$('#sub').linkbutton('enable');
			$('#clr').linkbutton('enable');
		}
	</script>
  </head>
  
  <body>
  <div style="padding:5px;border:1px solid #95B8E7;width:588px;background:#EFF5FF">
  	 <% if(app.get("修改防火墙信息")!=null){%>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="deitable()">编辑内容</a>
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  disabled=true>修改提交</a>
	<%} %>
	</div>
  
  <div class="easyui-panel" title="" style="width:600px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifySysObj.action" method="post">
		    <input type="hidden" name="resObjForm.mcaId" value="${ resObjForm.mcaId}"/>
		    <input type="hidden" name="resObjForm.cityCode" value="${ resObjForm.cityCode}"/>
		    <input type="hidden" name="resObjForm.resId" value="${ resObjForm.resId}" />
		    <input type="hidden" name="resObjForm.authId" value="${ resObjForm.authId}"/>
		     <input type="hidden" name="resObjForm.classId" value="${ resObjForm.classId}"/>
		     <input type="hidden" name="company" value="${ resObjForm.company}"/>
		     <input type="hidden" name="devType" value="${ resObjForm.devType}"/>
		    	<table>
	    		<tr>
	    			<td><label>IP：</label></td>
	    			<td><input id="ip1" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp1" value="${ resObjForm.resIp1}" missingMessage="请输入IP地址"  data-options="required:true" disabled=true></input>.
	    			<input id="ip2" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp2" value="${ resObjForm.resIp2}" missingMessage="请输入IP地址" data-options="required:true" disabled=true></input>.
	    			<input id="ip3" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp3" value="${ resObjForm.resIp3}" missingMessage="请输入IP地址" data-options="required:true" disabled=true></input>.
	    			<input id="ip4" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp4" value="${ resObjForm.resIp4}" missingMessage="请输入IP地址" data-options="required:true" disabled=true></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>资源名称：</label></td>
	    			<td><input id="resName" class="easyui-validatebox" type="text" missingMessage="请输入资源名称" name="resObjForm.resName" value="${ resObjForm.resName}" data-options="required:true" disabled=true></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>登录用户：</label></td>
	    			<td><input id="username" class="easyui-validatebox" type="text" missingMessage="请输入登录用户" name="resObjForm.username" value="${ resObjForm.username}" data-options="required:true" disabled=true></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>登录密码：</label></td>
	    			<td><input id="password" class="easyui-validatebox" type="password" missingMessage="请输入登录密码" name="resObjForm.password" value="${ resObjForm.password}" data-options="required:true" disabled=true></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td><select name="resObjForm.company" id="cc1" class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany', 

							onSelect: function(rec){ 

							var url = 'findDevTypeByCompanyCode?companyCode='+rec.id; 

							$('#cc2').combobox('reload', url); 

						}" disabled=true>
							<option value="${ resObjForm.company}" selected>${resObjForm.companyName}</option>
						</select> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙型号：</label></td>
	    			<td><select id="cc2" class="easyui-combobox" name="resObjForm.devType" editable="false" data-options="required:true,valueField:'id',textField:'text'" disabled=true>
	    					<option value="${ resObjForm.devType}" selected>${resObjForm.typeName}</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>资源描述：</label></td>
	    			<td><textarea id="resDesc" class="datagrid-editable-input" name="resObjForm.resDesc" style="resize:none;" disabled=true>${ resObjForm.resDesc}</textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启动：</label></td>
	    			<td>
	    			<c:if test="${ resObjForm.resPaused eq '0'}">
	    			<input id="resPaused1" type="radio" name="resObjForm.resPaused" value="0" checked="true" disabled=true>启动</input>
	    				<input id="resPaused2" type="radio" name="resObjForm.resPaused" value="1" disabled=true>暂停</input>
	    			</c:if>
	    			<c:if test="${ resObjForm.resPaused eq '1'}">
	    			<input id="resPaused1" type="radio" name="resObjForm.resPaused" value="0" disabled=true>启动</input>
	    				<input id="resPaused2" type="radio" name="resObjForm.resPaused" value="1" checked="true"  disabled=true>暂停</input>
	    			</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>策略采集方式：</label></td>
	    			<td>
	    				<select id="configOperation" class="easyui-combobox" name="resObjForm.configOperation" editable="false" data-options="required:true" disabled=true>
	    					<c:if test="${ resObjForm.configOperation eq 'ssh'}">
		    					<option value="ssh" selected>ssh</option>
		    					<option value="telnet">telnet</option>
	    					</c:if>
	    					<c:if test="${ resObjForm.configOperation eq 'telnet'}">
		    					<option value="ssh">ssh</option>
		    					<option value="telnet" selected>telnet</option>
	    					</c:if>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>SNMP版本：</label></td>
	    			<td>
	    				<select id="statusOperation" class="easyui-combobox" name="resObjForm.statusOperation" editable="false" data-options="required:true,
	    				onSelect:function(rec){
	    					if(rec.value=='1'||rec.value=='2'){
	    						$('#v1').css('display','block');
	    						$('#v3').css('display','none');
	    						$('#commuinty').validatebox({ 
									required: true
								}); 
								$('#snmpuser').validatebox({ 
									required: false
								});
	    						
	    						
	    					}else{
	    						$('#v1').css('display','none');
	    						
	    						$('#v3').css('display','block');
	    						$('#commuinty').validatebox({ 
									required: false
								}); 
								$('#snmpuser').validatebox({ 
									required: true
								});
	    					}

	    				}"
	    				disabled=true >
	    				<c:if test="${ resObjForm.statusOperation eq '1'}">
	    					<option value="1" selected >SNMP V1</option>
	    					<option value="2" >SNMP V2</option>
	    					<option value="3">SNMP V3</option>
	    					<script>
	    						$('#v1').css('display','block');
	    						$('#v3').css('display','none');
	    						$('#commuinty').validatebox({ 
									required: true
								}); 
								$('#snmpuser').validatebox({ 
									required: false
								});
	    					</script>
	    				</c:if>
	    				<c:if test="${ resObjForm.statusOperation eq '2'}">
	    					<option value="1" >SNMP V1</option>
	    					<option value="2" selected >SNMP V2</option>
	    					<option value="3">SNMP V3</option>
	    					<script>
	    						$('#v1').css('display','block');
	    						$('#v3').css('display','none');
	    						$('#commuinty').validatebox({ 
									required: true
								}); 
								$('#snmpuser').validatebox({ 
									required: false
								});
	    					</script>
	    				</c:if>
	    				<c:if test="${ resObjForm.statusOperation eq '3'}">
	    					<option value="1" >SNMP V1</option>
	    					<option value="2" >SNMP V2</option>
	    					<option value="3" selected>SNMP V3</option>
	    					<script>
	    						$('#v1').css('display','none');
	    						
	    						$('#v3').css('display','block');
	    						$('#commuinty').validatebox({ 
									required: false
								}); 
								$('#snmpuser').validatebox({ 
									required: true
								});
	    					</script>
	    				</c:if>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    		<td colspan="2">
	    		<HR style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=1>
	    		</td>
	    		</tr>
	    		<tr>
	    			<td colspan="2">SNMP认证信息录入：</td>
	    		</tr>
	    		<tr>
	    		<table>
	    			<tbody id="v1" style="display:''">
	    			<tr>
	    			<td><label>团体名：</label></td>
	    			<td>
	    				<input id="commuinty" type="text"  class="easyui-validatebox" name="resObjForm.commuinty" value="${ resObjForm.commuinty}" data-options="required:true" disabled=true />
	    			</td>
	    			
	    			</tr>
	    			</tbody>
	    		</table>
	    		</tr>
	    		<tr>
	    		
	    		<table>
	    		<tbody id="v3" style="display:none">
	    		<tr>
	    			<td><label>SNMP用户名：</label></td>
	    			<td>
	    				<input id="snmpuser" type="text"  class="easyui-validatebox" name="resObjForm.snmpuser" value="${ resObjForm.snmpuser}"  disabled=true />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>认证算法：</label></td>
	    			<td>
	    				<input id="snmpau" class="easyui-validatebox" name="resObjForm.snmpau" value="${ resObjForm.snmpau}"  disabled=true />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>认证密钥：</label></td>
	    			<td>
	    				<input id="snmpaups" type="text"  class="easyui-validatebox" name="resObjForm.snmpaups" value="${ resObjForm.snmpaups}" disabled=true />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>加密算法：</label></td>
	    			<td>
	    				<input id="snmppr" type="text"  class="easyui-validatebox" name="resObjForm.snmppr" value="${ resObjForm.snmppr}" disabled=true />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>加密密钥：</label></td>
	    			<td>
	    				<input id="snmpprps" type="text"  class="easyui-validatebox" name="resObjForm.snmpprps" value="${ resObjForm.snmpprps}" disabled=true />
	    			</td>
	    		</tr>
	    		</tbody>
	    		</table>
	    		<tr>
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
	    			
	    			</td>
	    			<td>
	    			
	    			</td>
	    		<tr>
	    	</table>
		    </form>		     
    	</div>
  </div>
   <script>
 
		function submitForm(){
			var resIp1=document.getElementById("ip1");
			var resIp2=document.getElementById("ip2");
			var resIp3=document.getElementById("ip3");
			var resIp4=document.getElementById("ip4");
			if(resIp1.value>255||resIp2.value>255||resIp3.value>255||resIp4.value>255){
				
				 $.messager.alert("错误提示", "ip地址不能超过255！", "info");
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
		function checkform(objfrm)
     	{
     		var ss=0;
	         var inputObj = objfrm.getElementsByTagName("input");
	         for (i = 0; i < inputObj.length; i++) {
	         	if(inputObj[i].name=="resObjForm.cityCode"||inputObj[i].name=="resObjForm.resName"||inputObj[i].name=="resObjForm.username"||inputObj[i].name=="resObjForm.password"){	         		         		
	         		ss=ss+hashCode(inputObj[i].value);
	         	
	         	}
	         }
	        
	       var md5s=hex_md5(""+ss);
	       return md5s;
     	}
	</script>
  </body>
</html>
