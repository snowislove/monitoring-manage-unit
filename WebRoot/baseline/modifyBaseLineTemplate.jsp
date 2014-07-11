<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/autoMergeCells.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="模板管理>模板修改";
	</script>
  </head>
  
  <body>
  <div class="easyui-panel" title="" style="width:900px">
  
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="modifyBaseLineTemplate.action" onsubmit="return submitForm();" method="post">
		    	<input type="hidden"  name="id" value="${baselineTemplate.id }"/>
		    	<table>
	    		<tr>
	    			<td><label>模板名称：</label></td>
	    			<td><input id="templateName" class="easyui-validatebox" type="text" missingMessage="请输入模板名称" name="templateName" value="${baselineTemplate.templateName }" data-options="required:true"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td><label>模板描述：</label></td>
	    			<td><textarea class="datagrid-editable-input" name="templateDesc" style="resize:none;">${baselineTemplate.templateDesc }</textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>防火墙厂商：</label></td>
	    			<td>
	    			<select name="companyCode" id="cc1" class="easyui-combobox" editable="false" missingMessage="请选择厂商" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action'" >
							<option value="${ baselineTemplate.companyCode}" selected>${baselineTemplate.companyName }</option>
						</select>  
	    			</td>
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
	    	<table id="listDetail">
	    	</table>
		     </form>	   
    	</div>
  </div>
  <script>
  $(function(){  
    $("#listDetail").datagrid({  
    	width:700,
        heigth:700,     
        idField:'baselineId',  
        url:'getAllBaseline.action',  
       // queryParams:{'viewType':'RK','RKD_ID':_rkdId},  
        singleSelect:false,  
        fitColumns:true,  
        nowrap:true,  
        loadMsg:'数据加载中,请稍后……',  
        
        rownumbers:true,
        columns:[[  
            {field:'baselineId',checkbox:true},  
            {field:'baselineType',title:'基线类型',width:100,editor:'text',sortable:true},     
            {field:'blackWhite',title:'黑白名单',width:100,editor:'text'} ,
            {field:'baselineDesc',title:'模板描述',width:300,editor:'textarea'}, 
            {field:'score',title:'分数(默认10分，点击修改)',width:150,editor:'text'}
        ]],   
       toolbar: [ {   
            text:'请选择基线'
			
        },'-',{   
            text:'刷新',
			iconCls:'icon-reload',
			handler:function(){$('#listDetail').datagrid('reload'); }
        }],     
        
       
		onClickCell:function(rowIndex, field, value){	
			 if(field == "score"){
   				$.messager.prompt('分值输入', '请输入分值，不需要添加单位！', function(r){
				if (r&&!isNaN(r)){
						$('#listDetail').datagrid('beginEdit', rowIndex);
						var ed=$('#listDetail').datagrid('getEditor', {index:rowIndex,field:field})
						$(ed.target).val(r);
						$('#listDetail').datagrid('endEdit', rowIndex);
					}
				});
 			 }
		},
		onLoadSuccess:function(data){  
			 
			 var list1 =new Array();
			 var j=0;
			 <c:forEach var="id" items="${selectBl}">
			 	list1[j]=${id};
			 	j++;
			 </c:forEach>
			
			for(var i=0;i<data.rows.length;i++){
				 for(var y=0;y<list1.length;y++){
					if(data.rows[i].baselineId==list1[y]){
						$('#listDetail').datagrid('selectRow', i);
					}
				}
				
			}
			
		} 
		
		
    }); 
		
	}); 
	
	 
	
		function submitForm(){
			var flag=$('#ff').form('validate');
			if(!flag){
				return flag;
			}
			var templateName=document.getElementById("templateName");
			var companyCode=document.getElementById("companyCode");
			if(templateName.value!=''&&companyCode!=''){
				var ids = [];
				var rows = $('#listDetail').datagrid('getSelections');
				for(var i=0; i<rows.length; i++){
	    			var input1 = document.createElement("input"); 
	
					input1.setAttribute("type","hidden"); 
					input1.setAttribute("value",rows[i].score); 
					input1.setAttribute("id","score"); 
					input1.setAttribute("name","score"+rows[i].baselineId);
					document.getElementById("ff").appendChild(input1);
				}
			}
			
			return true;
		}
		
		
		
	</script>
	
  </body>
</html>
