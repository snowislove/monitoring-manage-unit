<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>系统登录</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
		<link type="text/css" rel="stylesheet" href="style/login/css/login.css">
	</head>

	<body>
		<div id="center_box">
			<div id="form_box">
				<form id="loginForm" action="login.action" method="post">
					<div id="mess">
					
					<font color="red">${loginError}</font>
					
					</div>
					<div id="user">
						<label>
							账号：
						</label>
						<input class="input_text" type="text" name="account" />
						<input class="butt" name="" type="submit" value="登录" />
					</div>
					<div id="password">
						<label>
							密码：
						</label>
						<input class="input_text" name="password" type="password"/>
						<input class="butt" name="" type="reset" value="重置" />
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
