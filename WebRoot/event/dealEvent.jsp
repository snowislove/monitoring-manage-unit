<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
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
    <title>告警处理</title>
    
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
	<script>
		if('${close}'=='1'){
			alert("${msg}");
			
			window.close();
		}
	</script>
	
  </head>
  <body>
  	<div class="easyui-panel" title="" style="width:600px,height:900px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="dealEvent.action" method="post" onsubmit="return submitForm();">
		    	<input type="hidden"  name="se.id" value="${eventId}"/>
		    	<table>
		    	<tr>
	    			<td><label>事件级别：</label></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" id="level" name="level" value="" disabled=true/>
					</td>
				</tr>
				<tr>
	    			<td><label>事件类型：</label></td>
	    			<td>
	    				 <input class="easyui-validatebox" type="text" id="eventType" value="" disabled=true/>
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>事件发生时间：</label></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" id="cdate" value="" disabled=true/>
	    			</td>
	    			
				</tr>
				
	    		<tr>
	    			<td><label>事件确认人：</label></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" id="cuser" value="" disabled=true/>
	    			</td>
	    		</tr>
	    		<tr>
	    			
	    			<td><label>事件确认时间：</label></td>
	    			<td>
	    				<input class="easyui-validatebox" type="text" id="confirmDate" value="" disabled=true/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td ><label>事件内容：</label></td>
	    			<td>
	    				 <textarea class="datagrid-editable-input"  id="message" name="message" style="resize:none;width:500px;height:200px" disabled=true></textarea>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>
	    				 <lable>处理信息:</lable></td>
	    			<td>
	    				 <textarea class="datagrid-editable-input" id="dealMsg" name="dealMsg" style="resize:none;width:500px;height:200px"></textarea>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>事件确认/清除：</label></td>
	    			<td>
	    				<input type="radio" name="type" value="0" checked="true">确认</input>
	    				<input type="radio" name="type" value="1">清除</input>
	    			</td>
	    			
	    		</tr>
	    		<tr>
	    		
	    			<td rowspan="2">
	    			    <input type="submit" value="提交"/>
	    			</td>
	    			<td>
	    				<input type="button" value="关闭" onclick="closeWin();"/>
	    			</td>
	    		<tr>
	    	</table>
	    	
		    </form>		     
    	</div>
  </div>
  <script>
  		$.post('getEventByEventId.action', {eventId:'${eventId}'} ,function(data){
  									var eventType=document.getElementById("eventType");
			 						eventType.value=data.eventType;			 						
			 						var level=document.getElementById("level");
			 						level.value=data.eventlevel;
			 						
			 						var message1=document.getElementById("message");
			 						
			 						message1.innerText=data.message;
			 						
			 						var cdate1=document.getElementById("cdate");
			 						
			 						cdate1.value=data.cdate;
			 						var cuser1=document.getElementById("cuser");
			 						cuser1.value=data.cuser;
			 						var confirmDate1=document.getElementById("confirmDate");
			 						confirmDate1.value=data.confirmDate;
			 						var dealMsg1=document.getElementById("dealMsg");
			 						dealMsg1.innerText=data.dealMsg;
								});
		function submitForm(){
			return $('#ff').form('validate');
		}
		function closeWin(){
			window.close();
		}
  </script>
  </body>
  </html>