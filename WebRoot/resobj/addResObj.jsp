<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>资源添加</title>
    
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
		adiv.innerText="资源管理>创建资源";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:500px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveSysObj.action" method="post">
		   
		    <input type="hidden" name="resObjForm.cityCode" value="${cityCode}"/>
		    	<table>
	    		<tr>
	    			<td><label>资源IP：</label></td>
	    			<td><input id="resIp1" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp1" missingMessage="请输入IP地址"  data-options="required:true"></input>.
	    			<input id="resIp2" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp2" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input id="resIp3" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp3" missingMessage="请输入IP地址" data-options="required:true"></input>.
	    			<input id="resIp4" class="easyui-numberbox" style="width:50px" type="text" name="resObjForm.resIp4" missingMessage="请输入IP地址" data-options="required:true"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>资源名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入资源名称" name="resObjForm.resName" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>登录用户：</label></td>
	    			<td><input class="easyui-validatebox" type="text" missingMessage="请输入登录用户" name="resObjForm.username" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>登录密码：</label></td>
	    			<td><input class="easyui-validatebox" type="password" missingMessage="请输入登录密码" name="resObjForm.password" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td><input name="resObjForm.company" id="cc1" class="easyui-combobox" editable="false" missingMessage="请选择设备厂商" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action', 

							onSelect: function(rec){ 

							var url = 'findDevTypeByCompanyCode.action?companyCode='+rec.id; 

							$('#cc2').combobox('reload', url); 

						}" /> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙型号：</label></td>
	    			<td><input id="cc2" class="easyui-combobox" name="resObjForm.devType" editable="false" missingMessage="请选择设备型号" data-options="required:true,valueField:'id',textField:'text'" /></td>
	    		</tr>
	    		<tr>
	    			<td><label>资源描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="resObjForm.resDesc" style="resize:none;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>是否启动：</label></td>
	    			<td><input type="radio" name="resObjForm.resPaused" value="0" checked="true">启动</input>
	    				<input type="radio" name="resObjForm.resPaused" value="1">暂停</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>策略采集方式：</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="resObjForm.configOperation" editable="false" data-options="required:true">
	    					<option value="ssh" selected>ssh</option>
	    					<option value="telnet">telnet</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>SNMP版本：</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="resObjForm.statusOperation" editable="false" data-options="required:true,
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
	    				>
	    					<option value="1" selected >SNMP V1</option>
	    					<option value="2" >SNMP V2</option>
	    					<option value="3">SNMP V3</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    		<td colspan="2">
	    		<HR style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=0>
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
	    				<input id="commuinty" type="text"  class="easyui-validatebox" name="resObjForm.commuinty" data-options="required:true" />
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
	    				<input id="resObjForm.snmpuser" type="text"  class="easyui-validatebox" name="resObjForm.snmpuser" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>认证算法：</label></td>
	    			<td>
	    				<input  class="easyui-validatebox" name="resObjForm.snmpau"  />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>认证密钥：</label></td>
	    			<td>
	    				<input type="text"  class="easyui-validatebox" name="resObjForm.snmpaups"  />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>加密算法：</label></td>
	    			<td>
	    				<input type="text"  class="easyui-validatebox" name="resObjForm.snmppr"  />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>加密密钥：</label></td>
	    			<td>
	    				<input type="text"  class="easyui-validatebox" name="resObjForm.snmpprps"  />
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
