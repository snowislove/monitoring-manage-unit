<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>访问策略修改</title>
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
		adiv.innerText="用户管理>访问策略修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  >修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyAccessRule.action" method="post" >
		    <input type="hidden" name="accessRule.id" value="${ laccessRule.id}"/>
		    	<table>
		    	<tr>
		    	<td></td><td></td>
		    	</tr>
		    	<tr>
		    	<td></td><td></td>
		    	</tr>
		    	<tr>
	    			<td><label>用户最大并发数：</label></td>
	    			<td><input id="maxUser" class="easyui-numberbox" type="text" value="${ laccessRule.maxUser}" missingMessage="请输入用户最大并发会话数" name="accessRule.maxUser" data-options="required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>系统超时时限（分钟）：</label></td>
	    			<td><input id="accessTimeOut" class="easyui-numberbox" type="text" value="${ laccessRule.accessTimeOut}" missingMessage="请输入系统超时时限" name="accessRule.accessTimeOut" data-options="required:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否开启IP访问限制：</label></td>
	    			<td>
	    				<c:if test="${ laccessRule.isLimitIp eq '1'}">
	    				<input id="isLimitIp1" type="radio" name="accessRule.isLimitIp" value="1" checked="true">启用</input>
	    				<input id="isLimitIp2" type="radio" name="accessRule.isLimitIp" value="0">停止</input>
	    				</c:if>
	    				<c:if test="${ laccessRule.isLimitIp eq '0'}">
	    				<input id="isLimitIp1" type="radio" name="accessRule.isLimitIp" value="1" >启用</input>
	    				<input id="isLimitIp2" type="radio" name="accessRule.isLimitIp" value="0" checked="true">停止</input>
	    				</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否开启访问时间限制：</label></td>
	    			<td>
	    				
	    				
	    				<c:if test="${ laccessRule.isLimitTime eq '1'}">
	    				<input id="isLimitTime1" type="radio" name="accessRule.isLimitTime" value="1" checked="true">启用</input>
	    				<input id="isLimitTime2" type="radio" name="accessRule.isLimitTime" value="0">停止</input>
	    				</c:if>
	    				<c:if test="${ laccessRule.isLimitTime eq '0'}">
	    				<input id="isLimitTime1" type="radio" name="accessRule.isLimitTime" value="1" >启用</input>
	    				<input id="isLimitTime2" type="radio" name="accessRule.isLimitTime" value="0" checked="true">停止</input>
	    				</c:if>
	    			
	    			</td>
	    		</tr>
	    		
	    		
	    		</table>
	    		
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			
			var timeout=document.getElementById("accessTimeOut");
			if(timeout.value>240){
				$.messager.alert("错误提示", "系统最大超时时限不能大于240分钟！", "info");
				
				return false;
			}
			if(timeout.value<=1){
				
				$.messager.alert("错误提示", "系统最大超时时限不能小于2分钟！", "info");
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