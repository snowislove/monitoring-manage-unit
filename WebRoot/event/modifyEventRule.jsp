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
<title>告警规则修改</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="告警配置管理>告警规则修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:788px;background:#EFF5FF">
		<a id="sub" href="javascript:if(document.ff.onsubmit()!=false)document.ff.submit();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"  onclick="return submitForm();">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:800px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyEventRule.action" method="post" onsubmit="return submitForm();">
		     <input type="hidden" name="eventRule.id" value="${eRule.id }"/>
		   <input type="hidden" name="eventRule.resId" value="${eRule.resId }"/>
		    	<input type="hidden" name="eventRule.eventTypeId" value="${eRule.eventTypeId }"/>
		    	<table>
		    	<tr>
	    			<td><label>告警级别：</label></td>
	    			<td>
	    				<select class="easyui-combobox" name="eventRule.eventLevel" missingMessage="请选择" editable="false" data-options="required:true" disabled=true>
	    					<c:if test="${eRule.eventLevel eq '1'}">
	    						<option value="1">通知</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '2'}">
	    						<option value="2">轻微</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '3'}">
	    						<option value="3">重要</option>
	    					</c:if>
	    					<c:if test="${eRule.eventLevel eq '4'}">
	    						<option value="4">紧急</option>
	    					</c:if>
	    				</select>
					</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否生成告警短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.setMsg" missingMessage="请选择" editable="false" data-options="required:true">
	    				 	<c:if test="${eRule.setMsg eq '0'}">
		    				 	<option value="0" selected>否</option>
		    				 	<option value="1" >是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.setMsg eq '1'}">
		    				 	<option value="0" >否</option>
		    				 	<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>是否重复告警：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.repeat" missingMessage="请选择" editable="false" data-options="required:true">
	    				 	<c:if test="${eRule.repeat eq '0'}">
		    				 	<option value="0" selected>否</option>
		    				 	<option value="1" >是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.repeat eq '1'}">
		    				 	<option value="0" >否</option>
		    				 	<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select><font color="red">如选择不生成告警短信，此选项选择重复告警无效！</font>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>是否产生恢复短信：</label></td>
	    			<td>
	    				 <select class="easyui-combobox" name="eventRule.recoverSetMsg" missingMessage="请选择" editable="false" data-options="required:true">
	    				 	<c:if test="${eRule.recoverSetMsg eq '0'}">
	    				 		<option value="0" selected>否</option>
	    				 		<option value="1">是</option>
	    				 	</c:if>
	    				 	<c:if test="${eRule.recoverSetMsg eq '1'}">
	    				 		<option value="0" >否</option>
	    				 		<option value="1" selected>是</option>
	    				 	</c:if>
	    				 </select><font color="red">如选择不生成告警短信，此选项选择生成恢复告警无效！</font>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>告警判断条件：</label><select id="thresholdOpr"  name="eventRule.thresholdOpr" >
	    				 	
		    				 	<c:if test="${eRule.thresholdOpr eq '==' }">
			    				 	<option value="==" selected>字符等</option>
			    				 	<option value="!=">字符不等</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=">大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
		    				 	</c:if>
		    				 	<c:if test="${eRule.thresholdOpr eq '!=' }">
			    				 	<option value="==" >字符等</option>
			    				 	<option value="!=" selected>字符不等</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=">大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
		    				 	</c:if>
	    				 	
	    				 	
	    				 	
	    				 		<c:if test="${eRule.thresholdOpr eq '>' }">
	    				 			<option value="==">字符等</option>
			    				 	<option value="!=">字符不等</option>
			    				 	<option value=">" selected>大于</option>
			    				 	<option value=">=">大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '>=' }">
			    					<option value="==">字符等</option>
			    				 	<option value="!=">字符不等</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" selected>大于等于</option>
			    				 	<option value="=">等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '=' }">
			    					<option value="==">字符等</option>
			    				 	<option value="!=">字符不等</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" selected>等于</option>
			    				 	<option value="<=">小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '<=' }">
			    					<option value="==">字符等</option>
			    				 	<option value="!=">字符不等</option>
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" >等于</option>
			    				 	<option value="<=" selected >小于等于</option>
			    				 	<option value="<">小于</option>
			    				</c:if>
			    				<c:if test="${eRule.thresholdOpr eq '<' }">
			    					<option value="<">小于</option>
			    					<option value="==">字符等</option>
			    				 	<option value="!=">字符不等</option>
			    					
			    				 	<option value=">" >大于</option>
			    				 	<option value=">=" >大于等于</option>
			    				 	<option value="=" >等于</option>
			    				 	<option value="<=" >小于等于</option>
			    				 	
			    				</c:if>
	    				 	
	    				 </select></td>
	    			<td>
	    				 <lable>阀值:</lable>
	    				 
	    				 	<input id="thresholdValue" class="easyui-validatebox" type="text" missingMessage="请输入阀值" name="eventRule.thresholdValue" data-options="required:true" value="${ eRule.thresholdValue}"></input>
	    				
	    				
	    			</td>
	    		</tr>
	    		
	    		</table>
		    </form>		     
    	</div>
  </div>
  <script>
		function submitForm(){
			var flag=$('#ff').form('validate');
			if(!flag){
				return flag;
			}
			var thresholdValue=document.getElementById("thresholdValue");
			var thresholdOpr=document.getElementById("thresholdOpr");
			if(thresholdValue.value=='null'){
				if(thresholdOpr.value!='!='){
					
					$.messager.alert("错误提示", "只有选择不等于时，阀值才能未字符串null！", "info");
					return false;
				}
			}
			
			if(thresholdOpr.value==">"||thresholdOpr.value=="<"||thresholdOpr.value=="<="||thresholdOpr.value==">="||thresholdOpr.value=="="){
				
				if(isNaN(thresholdValue.value)){
					
					$.messager.alert("错误提示", "阀值请输入数字！", "info");
					return false;
				}
				return true;
			}
		}	
		
	</script>
</body>
</html>