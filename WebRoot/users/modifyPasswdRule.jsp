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
<title>密码策略修改</title>
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
		adiv.innerText="用户管理>密码策略修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:488px;background:#EFF5FF">
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="submitForm()">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:500px;height:250px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyPasswdRule.action" method="post">
		    <input type="hidden" name="passwdRule.id" value="${ spasswdRule.id}"/>
		    	<table>
		    	<tr>
		    	<td></td><td></td>
		    	</tr>
		    	<tr>
		    	<td></td><td></td>
		    	</tr>
		    	<tr>
	    			<td><label>密码最小长度：</label></td>
	    			<td><input class="easyui-slider" min="8" max="16" value="${ spasswdRule.passwdLong}" style="width:200px" name="passwdRule.passwdLong" data-options="showTip:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td><label>密码错误锁定次数：</label></td>
	    			<td>
	    				<input class="easyui-slider" min="3" max="10" value="${ spasswdRule.wrongTimes}" style="width:200px" name="passwdRule.wrongTimes" data-options="showTip:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			
	    			<td><label>密码重复次数：</label></td>
	    			<td>
	    				<input class="easyui-slider" min="3" max="6" value="${ spasswdRule.passwdRepeatNum}" style="width:200px" name="passwdRule.passwdRepeatNum" data-options="showTip:true">
	    			</td>
	    		
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    			<td>
	    				
	    				<input id="isPasswdTimeout" type="hidden" name="passwdRule.isPasswdTimeout" value="1"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td></td><td></td>
	    		</tr>
	    		<tr>
	    			<td><label>超期时限(月)：</label></td>
	    			<td>
	    				<input class="easyui-slider" min="1" max="12" value="${ spasswdRule.passwdTimeout}" style="width:200px" name="passwdRule.passwdTimeout" data-options="showTip:true">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>密码必须含有字母：</td>
	    			<td><c:if test="${ spasswdRule.hasChar eq '1'}">
	    				<input id="hasChar" type="radio" name="passwdRule.hasChar" value="1" checked="true">启用</input>
	    				<input id="hasChar" type="radio" name="passwdRule.hasChar" value="0">停止</input>
	    				</c:if>
	    				<c:if test="${ spasswdRule.hasChar eq '0'}">
	    				<input id="hasChar" type="radio" name="passwdRule.hasChar" value="1" >启用</input>
	    				<input id="hasChar" type="radio" name="passwdRule.hasChar" value="0" checked="true">停止</input>
	    				</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>密码必须含有数字：</td>
	    			<td><c:if test="${ spasswdRule.hasNum eq '1'}">
	    				<input id="hasNum" type="radio" name="passwdRule.hasNum" value="1" checked="true">启用</input>
	    				<input id="hasNum" type="radio" name="passwdRule.hasNum" value="0">停止</input>
	    				</c:if>
	    				<c:if test="${ spasswdRule.hasNum eq '0'}">
	    				<input id="hasNum" type="radio" name="passwdRule.hasNum" value="1" >启用</input>
	    				<input id="hasNum" type="radio" name="passwdRule.hasNum" value="0" checked="true">停止</input>
	    				</c:if>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>密码必须含有特殊字符：</td>
	    			<td><c:if test="${ spasswdRule.hasSpecialChar eq '1'}">
	    				<input id="hasNum" type="radio" name="passwdRule.hasSpecialChar" value="1" checked="true">启用</input>
	    				<input id="hasNum" type="radio" name="passwdRule.hasSpecialChar" value="0">停止</input>
	    				</c:if>
	    				<c:if test="${ spasswdRule.hasSpecialChar eq '0'}">
	    				<input id="hasNum" type="radio" name="passwdRule.hasSpecialChar" value="1" >启用</input>
	    				<input id="hasNum" type="radio" name="passwdRule.hasSpecialChar" value="0" checked="true">停止</input>
	    				</c:if>
	    			</td>
	    		</tr>
	    		</table>
	    		
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
		
			var flag= $('#ff').form('validate');
			if(flag){
				var ff=document.getElementById("ff");
				var md5s=checkform(ff);
				$.post('md5Receive.action', {checkS:md5s},function(data){
					if(data.res=="ok"){
						document.getElementById("ff").submit();
					}else{
						$.messager.alert("错误提示", "密码错误,提交失败！", "info");
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