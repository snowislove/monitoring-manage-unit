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
    <title>指标规则添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery/easyui-lang-zh_CN.js"></script>
	<script>
		var adiv= window.parent.document.getElementById("operation");
		adiv.innerText="指标管理>创建指标解析规则";
	</script>
  </head>

  <body>
  <div class="easyui-panel" title="指标：${kpiName}" style="width:800px">
		<div style="padding:10px 0 10px 60px">
		    <form id="ff" action="addOrUpdateMibOid.action" method="post" onsubmit="return submitForm();">
		    	<input type="hidden" name="oid.kpiId" value="${kpiId }"/>
		    	<input type="hidden" id="id1" name="oid.id" value=""/>
		    	<table>
		    	<tbody>
	    		<tr>
	    			<td><label>设备厂商：</label></td>
	    			<td><input name="oid.companyCode" id="cc1" class="easyui-combobox" editable="false" data-options=" required:true, valueField: 'id', textField: 'text', url: 'findAllCompany.action', 

							onSelect: function(rec){ 

							var url = 'findDevTypeByCompanyCode.action?companyCode='+rec.id; 
							$('#cc2').combobox('clear');
							$('#cc2').combobox('reload', url); 

						}" /> 
	    			</td>
	    		</tr>
	    		<tr>
	    			<td><label>设备型号：</label></td>
	    			<td><input id="cc2" class="easyui-combobox" name="oid.typeCode" editable="false" data-options="required:true,valueField:'id',textField:'text',
	    				onSelect: function(rec){
	    					$.post('queryMIb.action', {kpiId:'${kpiId }',typeCode:rec.id},function(data){
				 						if(data.id!=undefined){
				 							var id1=document.getElementById('id1');
				 							id1.value=data.id;
				 							if(data.miboid!=undefined){
					 							var mib=document.getElementById('mib');
					 							mib.innerText=data.miboid;
					 						}else{
					 							var mib=document.getElementById('mib');
					 							mib.innerText='';
					 						}
					 						if(data.rule!=undefined){
					 							var rule=document.getElementById('rule');
					 							rule.innerText=data.rule;
					 						}else{
					 							var rule=document.getElementById('rule');
					 							rule.innerText='';
					 						}
				 						}else{
				 							var id1=document.getElementById('id1');
				 							id1.value='';
				 						}
				 						
									});    					
	    			}" /></td>
	    		</tr>
	    		<tr>
	    			<td><label>MIB值：</label></td>
	    			<td><textarea id="mib" class="datagrid-editable-input"   name="oid.miboid" style="resize:none;width:300px;height:100px"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td><label>解析规则：</label></td>
	    			<td><textarea id="rule" class="datagrid-editable-input"   name="oid.rule" style="resize:none;width:300px;height:100px"></textarea>
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
	    			<input type="submit" value="提交"/>
	    			</td>
	    			<td>
	    			<input type="reset" value="重置"/>
	    			</td>
	    		<tr>
	    	</tbody>
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
