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
    <title>创建模板</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" media="all" type="text/css" href="style/blue/css/main.css" />
	
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="基线管理>创建基线";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:700px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveBaseLine.action" method="post" onsubmit="return submitForm();">
		    	<table>
	    		
	    		
	    		<tr>
	    			<td><label>黑/白名单：</label></td>
	    			<td><input type="radio" name="sbl.baselineBlackWhite" value="0" checked="true">白名单</input>
	    				<input type="radio" name="sbl.baselineBlackWhite" value="1">黑名单</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>基线类型：</label></td>
	    			<td><input type="radio" name="sbl.baselineType" value="0" checked="true">配置基线</input>
	    				<input type="radio" name="sbl.baselineType" value="1">策略基线</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>基线描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="sbl.baselineDesc" style="resize:none;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>
	    			<input type="submit" value="提交"/>
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
			return $('#ff').form('validate');
		}
	</script>
  </body>
</html>
