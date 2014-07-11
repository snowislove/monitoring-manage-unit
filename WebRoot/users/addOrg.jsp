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
    <title>创建部门</title>
    
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
		adiv.innerText="部门管理>创建部门";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="saveOrg.action" method="post">
		    	<table>
		    	<tr>
	    			<td><label>部门名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入部门名称" name="org.orgName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>部门描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"   name="org.orgDesc" style="resize:none;width:150px;height:50px"></textarea> 
	    			</td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>上级部门：</label></td>
	    			<td> 
	    			<input class="easyui-combotree" name="org.parentOrgId" style="width:150px" missingMessage="请选择上级部门" data-options="url:'allOrgRree.action',required:true" style="width:200px;">
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
