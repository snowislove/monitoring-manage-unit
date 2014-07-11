 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
  
<%@ page contentType="text/html;charset=UTF-8" %>

<TITLE>controller.jsp</TITLE>

<SCRIPT LANGUAGE="JavaScript">

function saveNodeInfo( treeTitle, systemID ){
	
    //alert("systemid::"+systemID );
    treeUtil.<%=com.secpro.platform.monitoring.manage.tree.TreeView.TREENODEPOSITION%>.value=systemID;
	treeUtil.<%=com.secpro.platform.monitoring.manage.tree.TreeView.TREETITLE%>.value = treeTitle;
    treeUtil.submit();
}


</SCRIPT>

</HEAD>

<BODY>


<form name="treeUtil" action="<%=request.getContextPath()%>/TreeUtil" method="post">
<input type="text" name="<%=com.secpro.platform.monitoring.manage.tree.TreeView.TREENODEPOSITION%>" value="">
<input type="text" name="<%=com.secpro.platform.monitoring.manage.tree.TreeView.TREETITLE%>" value="">
<INPUT TYPE="submit" value="fdafd">
</form>


</BODY>
</HTML>

