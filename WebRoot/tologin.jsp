<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
 <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
<script language="JavaScript">
//判断当前窗口是否有顶级窗口，如果有就让当前的窗口的地址栏发生变化，
//这样就可以让登陆窗口显示在整个窗口了
function loadTopWindow(){
	if (window.top!=null ){
		window.top.location= "<%=_contexPath%>/login.jsp"; 
	}
}
</script>
</head>
<body onload="loadTopWindow()">

</body>

</html>