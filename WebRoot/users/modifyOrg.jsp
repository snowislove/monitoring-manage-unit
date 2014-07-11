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
<title>部门信息修改</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/util/md5.js"></script>
	<script type="text/javascript" src="js/hashCode.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="部门管理>部门信息修改";
	</script>
</head>
<body>
	<div style="padding:5px;border:1px solid #95B8E7;width:388px;background:#EFF5FF">
		<a id="sub" href="javascript:submitForm();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">修改保存</a>
	</div>
	<div class="easyui-panel" title="" style="width:400px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" name="ff" action="modifyOrg.action" method="post">
		    <input type="hidden" name="org.id" value="${ morg.id}"/>
		    	<table>
		    	<tr>
	    			<td><label>部门名称：</label></td>
	    			<td><input class="easyui-validatebox" type="text" style="width:150px" missingMessage="请输入部门名称" name="org.orgName" data-options="required:true" value="${ morg.orgName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td><label>部门描述：</label></td>
	    			<td><textarea class="datagrid-editable-input"  name="org.orgDesc" style="resize:none;width:150px;height:50px">${ morg.orgDesc}</textarea> 
	    			</td>
	    		</tr>
	    		
	    		</table>
		    </form>		     
    	</div>
  </div>
  <script>
 		function _237(_238,_239,_23a){
			var win=$("<div class=\"messager-body\"></div>").appendTo("body");
			win.append(_239);
			if(_23a){
			var tb=$("<div class=\"messager-button\"></div>").appendTo(win);
			for(var _23b in _23a){
			$("<a></a>").attr("href","javascript:void(0)").text(_23b).css("margin-left",10).bind("click",eval(_23a[_23b])).appendTo(tb).linkbutton();
			}
			}
			win.window({title:_238,noheader:(_238?false:true),width:300,height:"auto",modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,onClose:function(){
			setTimeout(function(){
			win.window("destroy");
			},100);
			}});
			win.window("window").addClass("messager-window");
			win.children("div.messager-button").children("a:first").focus();
			return win;
		}
 		$.extend($.messager, {    
   			 prompt : function(_1c, msg, fn, type) {
    			var _t = 'text';
    			if(type != 'undefined' && type == 'password'){
    				_t = 'password';
    			}
				var _1d = "<div class=\"messager-icon messager-question\"></div>"
						+ "<div>"
						+ msg
						+ "</div>"
						+ "<br/>"
						+ "<div style=\"clear:both;\"/>"
						+ "<div><input class=\"messager-input\" type=\""+_t+"\"/></div>";
				var _1e = {};
				_1e[$.messager.defaults.ok] = function() {
					win.window("close");
					if (fn) {
						fn($(".messager-input", win).val());
						return false;
					}
				};
				_1e[$.messager.defaults.cancel] = function() {
					win.window("close");
					if (fn) {
						fn();
						return false;
					}
				};
				var win = _237(_1c, _1d, _1e);
				//win.children("input.messager-input").focus();
				return win;
			}
		});  
		function submitForm(){
			var ff= $('#ff').form('validate');
			if(ff==false){
				return ff; 
			}
			
			$(function() {
					$.messager.prompt("操作提示", "请输入确认密码：", function(str) {
						var md5Pwd = hex_md5(str);
						if (md5Pwd == "${user.password}") {
							var ff = document.getElementById("ff");
							var md5s = checkform(ff);
							$.post('md5Receive.action', {
								checkS : md5s
							}, function(data) {
								if (data.res == "ok") {
									document.getElementById("ff").submit();
								} else {
									$.messager.alert("错误提示", "数据提交失败！", "info");
								}
							});
						} else {
							$.messager.alert("错误提示", "密码错误,提交失败！", "info");
							return;
						}
					},'password');
				});

			}
			function checkform(objfrm) {
				var ss = 0;
				var inputObj = objfrm.getElementsByTagName("input");
				for (i = 0; i < inputObj.length; i++) {
					if (inputObj[i].name != "" && inputObj[i].type != "radio") {
						ss = ss + hashCode(inputObj[i].value);

					}
				}

				var md5s = hex_md5("" + ss);
				return md5s;
			}
		</script>
</body>
</html>