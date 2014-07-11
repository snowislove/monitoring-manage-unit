<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>拓扑图</title>
<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="首页>拓扑图";
</script>
</head>
<body>
	 <% if(app.get("拓扑图")!=null){ %>
		<c:redirect url="topology/TopologyApplication.html" />
	<%}else{ %>
		<h4>无权限查看拓扑图</h4>
	<%} %>
</body>
</html>