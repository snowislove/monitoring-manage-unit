<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUserInfo user=(SysUserInfo)session.getAttribute("user");
Map app=user.getApp();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>
    <title>用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/util/md5.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="用户管理>用户列表";
	</script>
  </head>
    <body>
  	<table id="listDetail"></table>
	<script type="text/javascript">
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
	
	
		$(function(){  
    $("#listDetail").datagrid({  
    	width:1050,
        heigth:700,     
        idField:'userid',  
         pageSize:10,
   	 pageList:[10,20,50,100],
        url:'viewUserInfo.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        pagination:true ,
        rownumbers:true,
        columns:[[  
            {field:'userid',checkbox:true},  
            {field:'account',title:'账号',width:100,editor:'text',sortable:true},    
            {field:'userName',title:'姓名',width:100,editor:'text'},  
            {field:'mobelTel',title:'移动电话',width:150,editor:'text'},   
            {field:'officeTel',title:'座机',width:150,editor:'text'},
            {field:'email',title:'邮箱',width:250,editor:'text'},  
           	{field:'orgName',title:'用户部门',width:150,editor:'text'}, 
           	{field:'userDesc',title:'用户描述',width:200,editor:'text'},  
           	{field:'enableAccount',title:'用户状态',width:100,editor:'text'},  
           	{field:'workIp',title:'绑定IP',width:150,editor:'text'},
           	{field:'startTime',title:'工作开始时间',width:120,editor:'text'},
           	{field:'endTime',title:'工作结束时间',width:120,editor:'text'},          	
        ]],   
       toolbar: [
       <% if(app.get("创建用户")!=null||user.getAccount().equals("admin")){ %>
       {   
            text: '创建用户',   
            iconCls: 'icon-adduser',   
            handler: function () {   
               window.location.href="<%=_contexPath%>/users/addUser.jsp";
            }   
        }
        , '-',
         <% }if(app.get("删除用户")!=null||user.getAccount().equals("admin")){ %>
         {   
            text: '删除用户',   
            iconCls: 'icon-removeuser',   
            handler: function () { 
            	var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       $.messager.alert("错误提示", "未选择条目！", "info");  
				        return;  
				     }  
				$.messager.confirm('操作提示', '你确认要删除吗?', function(r){
					if (r){
					 	for(var j=0;j<rows.length;j++){
				     	
				     	if(rows[j].account=='admin' ||rows[j].account=='shenji1' || rows[j].account=='shenji2'){
				     		$.messager.alert("错误提示", "admin、shenji1、shenji2为系统用户不能删除，请重新选择！", "info");
							return;
				     	}
				     }
				    var urll="deleteUser.action?userid=";  
				    for(var i=0;i<rows.length;i++){ 
				    	if(i==(rows.length-1)){
				    		urll+=rows[i].userid;
				    	}else{
				    		urll=urll+rows[i].userid+",";
				    	}
				    } 
				     $(function() {
					$.messager.prompt("操作提示", "请输入确认密码：", function(str) {
						var md5Pwd = hex_md5(str);
						if (md5Pwd == "${user.password}") {
							var ff = document.getElementById("ff");
							
							window.location.href=urll;
								
						} else {
							$.messager.alert("错误提示", "密码错误,提交失败！", "info");
							return;
						}
					},'password');
					});
					} 
				});     
                

            }   
        }, '-',
        <% }if(app.get("修改用户")!=null||user.getAccount().equals("admin")){ %>
         {   
            text: '修改用户',   
            iconCls: 'icon-edituser',   
            handler: function () {     
               	 var rows = $('#listDetail').datagrid('getSelections');  
				     if (null == rows || rows.length == 0) {  
				       $.messager.alert("错误提示", "未选择条目！", "info"); 
				        return;  
				     }  
				     if (rows.length>1){
				     	 $.messager.alert("错误提示", "修改用户只能选择一个条目！", "info");
				     	
				     	return;
				     }
				    var urll="toModifyUser.action?userid="+rows[0].userid;  
				    
				 	window.location.href=urll;
				}  

               
        },'-',
         <% }if(app.get("用户角色关联")!=null||user.getAccount().equals("admin")){ %>
         {   
            text: '用户角色关联',   
            iconCls: 'icon-usermapping',   
            handler: function () {     
               	 
				   window.location.href="<%=_contexPath%>/users/userRoleMapping.jsp";
				}  

               
        }, '-',
        <%}%>
         {   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }]
    }); 
		$('#listDetail').datagrid("getPager").pagination({
		    		displayMsg:'当前显示从{from}到{to}共{total}记录',
		   			 onBeforeRefresh:function(pageNumber, pageSize){
		     		$(this).pagination('loading');
		    		
		    		 $(this).pagination('loaded');
		   		 }
		  	 });  
		
		    $('#listDetail').datagrid("getPager").pagination({  
		
		        pageSize: 10,//每页显示的记录条数，默认为10  
		
		        pageList: [10,20,50,100],//可以设置每页记录条数的列表  
		
		        beforePageText: '第',//页数文本框前显示的汉字  
		
		        afterPageText: '页    共 {pages} 页',  
		
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
		
		        /*onBeforeRefresh:function(){ 
		
		            $(this).pagination('loading'); 
		
		            alert('before refresh'); 
		
		            $(this).pagination('loaded'); 
		
		        }*/ 
		
		    });
	});  
		
		
	</script>
  </body>
</html>
