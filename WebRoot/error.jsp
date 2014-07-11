<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>系统错误</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="<%=_contexPath%>/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=_contexPath%>/js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">	
			function OK(){
				var url="${backUrl}";
				if(url==null || url=="" || url=="null"){
					var u="<%=_contexPath%>/first.jsp";
				  	self.location=u;
				}else{
				 	if(url.indexOf(".action")>=0){
				 	}else{
				 		url = "<%=_contexPath%>/${backUrl}";
				 	}
					self.location.href = url;
				}
		   }
		   function show(){
		   	<c:if test="${empty returnMsg }">
		   	  	 $.messager.alert('错误提示','系统错误！','error',function () { 
		            OK();
		   	  });
			
			return ;
		   	</c:if> 	
		   	  $.messager.alert('错误提示','${returnMsg}','error',function () { 
		            OK();
		      });
		   }
		</script>
	</head>

	<body>
		<div id="main_container">
			<table border="0" align="center" cellpadding="0" cellspacing="0"
				class="operation_top">
				<tr>
					<td valign="top" height="300">
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script type="text/javascript">show();</script>
</html>
