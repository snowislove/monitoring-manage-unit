<%@page import="com.secpro.platform.monitoring.manage.entity.SysResObj"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.SysOperation"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.SysCommand"%>
<%@page import="com.secpro.platform.monitoring.manage.util.SpringBeanUtile"%>
<%@page import="com.secpro.platform.monitoring.manage.util.Assert"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.SysResAuth"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.SysCity"%>
<%@page import="com.secpro.platform.monitoring.manage.entity.MsuTask"%>
<%@page import="com.secpro.platform.monitoring.manage.services.TaskScheduleService"%>
<%
	//localhost:8080/mmu/task/taskSave.jsp?operationType=new&&resId=62
	String tid = request.getParameter("tid");
	String resId = request.getParameter("resId");
	String operationType = request.getParameter("operationType");
	//
	if (Assert.isEmptyString(operationType) == true) {
		out.println("未发现操作类别,请设置operationType参数");
		return;
	}
	//
	String mcaOperation = null;
	String operationTitle = null;
	MsuTask msuTask = null;
	TaskScheduleService taskScheduleService = SpringBeanUtile.getSpringBean(request, TaskScheduleService.class, "TaskScheduleServiceImpl");
	//
	if ("new".equalsIgnoreCase(operationType) == true) {
		if (Assert.isEmptyString(resId) == true) {
			out.println("未发现创建任务需要的资源,请设置resId参数");
			return;
		}
		msuTask = new MsuTask();
		msuTask.setTargetPort(0);
		msuTask.setSchedule("0 10 * * * ?");
		operationTitle = "新建任务";
	} else {
		if (Assert.isEmptyString(tid) == true) {
			out.println("未发现要操作的任务标识,请设置tid参数");
			return;
		}
		msuTask = (MsuTask) (taskScheduleService.getTaskScheduleDao().findById(MsuTask.class, tid));
		if (msuTask == null) {
			out.println("未取得" + tid + "对应的任务");
			return;
		}
		resId = String.valueOf(msuTask.getResId());
		if ("update".equalsIgnoreCase(operationType) == true) {
			operationTitle = "更新任务 " + tid;
		} else {
			operationTitle = "查看详细 " + tid;
		}
	}
	SysResObj sysResObj = (SysResObj) (taskScheduleService.getObj(SysResObj.class, Long.parseLong(resId)));
	if (sysResObj == null) {
		out.println("未取得" + resId + "对应的资源,无法进行创建任务操作");
		return;
	}
	SysCity SysCity = taskScheduleService.getSysCityBycityCode(sysResObj.getCityCode());
	if (SysCity == null) {
		out.println(sysResObj.getResName() + "未指定所属城市,无法进行创建任务操作");
		return;
	}
	List<String> sysOperationList = new ArrayList<String>();
	String snmpVersion = "";
	if (Assert.isEmptyString(sysResObj.getStatusOperation()) == false) {
		sysOperationList.add("snmp");
		snmpVersion = sysResObj.getStatusOperation();
		mcaOperation = "snmp";
	}
	if (Assert.isEmptyString(sysResObj.getConfigOperation()) == false) {
		if (sysResObj.getConfigOperation().toLowerCase().indexOf("ssh") != -1) {
			sysOperationList.add("ssh");
			mcaOperation = "ssh";
		}
		if (sysResObj.getConfigOperation().toLowerCase().indexOf("telnet") != -1) {
			sysOperationList.add("telnet");
			mcaOperation = "telnet";
		}
	}
	if (sysOperationList.isEmpty()) {
		out.println(sysResObj.getResName() + "未指定采集状态信息的操作或采集配置信息的操作");
		return;
	}
	//根据设备型号查询此设备支持的operation
	//List<SysOperation> sysOperationList = taskScheduleService.getSysOperationByTypeCode(sysResObj.getTypeCode());
	List<SysCommand> sysCommandList = taskScheduleService.getSystCommandByTypeCode(sysResObj.getTypeCode());
	if (sysCommandList == null || sysCommandList.isEmpty()) {
		out.println(sysResObj.getResName() + ",未指定设备型号,无法进行创建任务操作");
		return;
	}
	List<SysResAuth> SysResAuthList = taskScheduleService.getSysResAuthByResId(sysResObj.getId());
	if (SysResAuthList == null || SysResAuthList.isEmpty()) {
		out.println(sysResObj.getResName() + ",未指定防火墙认证信息,法进行创建任务操作");
		return;
	}
	SysResAuth currentSysResAuth = SysResAuthList.get(0);
	if (Assert.isEmptyString(msuTask.getOperation()) == false) {
		mcaOperation = msuTask.getOperation();
	}
	List<String[]> snmpOIDList=taskScheduleService.getSnmpOIDbyTypeCode(sysResObj.getTypeCode());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/main.css" />
<link rel="stylesheet" media="all" type="text/css" href="../style/blue/css/basic.css" />
<title><%=operationTitle%></title>
<link rel="stylesheet" type="text/css" href="../style/app/css/app_main.css" />
<link rel="stylesheet" type="text/css" href="../style/blue/css/main.css" />
<script src="../js/ueframe/main.js"></script>
<script src="../js/form_check.js"></script>
<script src="../js/form_util.js"></script>
<script language="javascript">
	window.onresize = function() {
		setCenter(4, 80);
	}
	window.onload = function() {
		setCenter(4, 80);
	}
</script>
<script language="javascript">
	function myFormSubmit(form) {
		if (checkOwnRule(form)) {
			var inputObj = null;
			var operationValue = document.getElementById("operation").value;
			if ("snmp" == operationValue) {
				inputObj = document.getElementsByName("snmp_oids");
				var isChceckOids = false;
				for (i = 0; i < inputObj.length; i++) {
					if (inputObj[i].checked) {
						isChceckOids = true;
						break;
					}
				}
				if (isChceckOids == false) {
					alert("采集命令 不能为空。");
					return;
				}
			} else {
				inputObj = document.getElementById("content");
				if (check_allownull(inputObj.value)) {
					alert(inputObj.getAttribute("hint") + " 不能为空。");
					return;
				}
			}
			form.submit();
		}
	}
	function operationChange() {
		var operationValue = document.getElementById("operation").value;
		//set default port 
		setDefaultProt(operationValue);
		//ssh
		if ("ssh" == operationValue) {
			document.getElementById("operationSSH/TELNETAuthRow").style.display = "block";
			//
			//document.getElementById("operationSSH/TELNETAuthRow").style.display = "none";
			document.getElementById("operationTELNETAuthRow").style.display = "none";
			document.getElementById("operationSNMPV1/2AuthRow").style.display = "none";
			document.getElementById("operationSNMPV3AuthRow").style.display = "none";
			document.getElementById("operationSNMPVersionSelector").style.display = "none";
		}
		//telnet
		if ("telnet" == operationValue) {
			document.getElementById("operationSSH/TELNETAuthRow").style.display = "block";
			document.getElementById("operationTELNETAuthRow").style.display = "block";
			//
			//document.getElementById("operationSSH/TELNETAuthRow").style.display = "none";
			//document.getElementById("operationTELNETAuthRow").style.display = "none";
			document.getElementById("operationSNMPV1/2AuthRow").style.display = "none";
			document.getElementById("operationSNMPV3AuthRow").style.display = "none";
			document.getElementById("operationSNMPVersionSelector").style.display = "none";
		}

		//snmp
		if ("snmp" == operationValue) {
			document.getElementById("operationSNMPVersionSelector").style.display = "block";
			var snmpVersion = document.getElementById("snmp_version").value;
			if ("1" == snmpVersion || "2" == snmpVersion) {
				document.getElementById("operationSNMPV1/2AuthRow").style.display = "block";
				//
				document.getElementById("operationSSH/TELNETAuthRow").style.display = "none";
				document.getElementById("operationTELNETAuthRow").style.display = "none";
				//document.getElementById("operationSNMPV1/2AuthRow").style.display = "none";
				document.getElementById("operationSNMPV3AuthRow").style.display = "none";
			} else if ("3" == snmpVersion) {
				document.getElementById("operationSNMPV3AuthRow").style.display = "block";
				//
				document.getElementById("operationSSH/TELNETAuthRow").style.display = "none";
				document.getElementById("operationTELNETAuthRow").style.display = "none";
				document.getElementById("operationSNMPV1/2AuthRow").style.display = "none";
				//document.getElementById("operationSNMPV3AuthRow").style.display = "none";
			}
			document.getElementById("snmpCommandDiv").style.display = "block";
			document.getElementById("noSnmpCommandDiv").style.display = "none";
		} else {
			document.getElementById("snmpCommandDiv").style.display = "none";
			document.getElementById("noSnmpCommandDiv").style.display = "block";
		}
	}
	function setTaskType() {
		if (document.getElementById("isRealtime").value == "false") {
			document.getElementById("isRealtime").value = "true";
			document.getElementById("schedule").value = "resltime";
			document.getElementById("schedule").disabled = true;
		} else {
			document.getElementById("isRealtime").value = "false";
			document.getElementById("schedule").value = "";
			document.getElementById("schedule").disabled = false;

		}
	}
	function setDefaultProt(operation) {
		//ssh
		if ("ssh" == operation) {
			document.getElementById("targetPort").value = "22";
			return;
		}
		//telnet
		if ("telnet" == operation) {
			document.getElementById("targetPort").value = "23";
			return;
		}
		//snmp
		if ("snmp" == operation) {
			document.getElementById("targetPort").value = "161";
			return;
		}
	}
</script>
</head>
<body onload="operationChange();">
	<div style="overflow-x: auto; overflow-y: auto;height:360px; width:100%; ">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
		<tr>
			<td valign="top">
				<fieldset style="width: 96%; border: 1px #cccccc solid; margin-left: 5px; margin-top: 5px; text-align: center;">
					<legend><%=operationTitle%></legend>
					<form name="taskForm" id="taskForm" action="saveTaskScheduleAction.action" method="post">
						<input type="hidden" name="id" id="id" value="<%=tid%>" /> <input type="hidden" name="resId" id="resId" value="<%=resId%>" /> <input type="hidden"
							name="operationType" id="operationType" value="<%=operationType%>" /> <input type="hidden" name="region" id="region" value="<%=sysResObj.getCityCode()%>" /> <input
							type="hidden" name="targetIp" id="targetIp" value="<%=sysResObj.getResIp()%>" /> <input type="hidden" name="isRealtime" id="isRealtime" value="false" /> <input
							type="hidden" name="snmp_version" id="snmp_version" value="<%=snmpVersion%>" /> <input type="hidden" name="terminalType" id="terminalType"
							value="<%=(currentSysResAuth.getTerminalType() == null ? "" : currentSysResAuth.getTerminalType())%>" /> <input type="hidden" name="filterString" id="filterString"
							value="<%=(currentSysResAuth.getFilterString() == null ? "" : currentSysResAuth.getFilterString())%>" />
						<div id="div1">
							<p></p>
							<table cellpadding="0" cellspacing="0" border="0" width="800px" align="center">
								<tr>
									<td>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
											<tr>
												<td valign="top" width="20%"><div align="right">采集目标：</div>
												</td>
												<td valign="top" width="80%"><div align="left">
														<span><%=sysResObj.getResName()%></span>
													</div>
												</td>
											</tr>
											<tr>
												<td valign="top" width="20%"><div align="right">归属城市：</div>
												</td>
												<td valign="top" width="80%"><div align="left">
														<span><%=SysCity.getCityName()%></span>
													</div>
												</td>
											</tr>
											<tr>
												<td valign="top" width="20%"><div align="right">目标IP：</div>
												</td>
												<td valign="top" width="80%"><div align="left">
														<span><%=sysResObj.getResIp()%></span>
													</div>
												</td>
											</tr>
											<tr>
												<td valign="top" width="20%"><div align="right">
														<span class="input_redstar ">*</span>采集方式：
													</div>
												</td>
												<td valign="top" width="80%"><div align="left" style="float:left">
														<select id="operation" name="operation" hint="采集方式" onchange="operationChange()">
															<%
																if (sysOperationList != null) {
																	for (int i = 0; i < sysOperationList.size(); i++) {
																		if (mcaOperation.equalsIgnoreCase(sysOperationList.get(i)) == true) {
																			out.println("<option value=\"" + sysOperationList.get(i) + "\" selected=\"selected\">" + sysOperationList.get(i) + "</option>");
																		} else {
																			out.println("<option value=\"" + sysOperationList.get(i) + "\">" + sysOperationList.get(i) + "</option>");
																		}
																	}
																}
															%>
														</select>
													</div>
													<div id="operationSNMPVersionSelector" style="float:left;display:none">
														|SNMP版本:v<%=snmpVersion%>
													</div></td>
											</tr>
											<tr>
												<td valign="top" width="20%"><div align="right">采集端口：</div>
												</td>
												<td valign="top" width="80%"><div align="left">
														<input name="targetPort" id="targetPort" type="text" size="10" maxlength="10" hint="采集端口" allownull="false" isPortNo="true"
															value="<%=(msuTask.getTargetPort() == 0 ? "" : msuTask.getTargetPort())%>" />
													</div>
												</td>
											</tr>
										</table></td>
								</tr>

								<tr>
									<td>
										<div id="operationSSH/TELNETAuthRow" style="display:none">
											<%
												//opeation:ssh/telnet
												//用户名	username	varchar2(20)	ssh/telnet用户名
												//密码	password	varchar2(20)	ssh/telnet密码
											%>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>用户名：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="username" id="username" type="text" size="20" maxlength="20" hint="用户名" readonly="readonly" value="<%=currentSysResAuth.getUsername()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>密码：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="password" id="password" type="password" size="20" maxlength="20" hint="密码" readonly="readonly" value="<%=currentSysResAuth.getPassword()%>" />
														</div>
													</td>
												</tr>
											</table>
										</div>
										<div id="operationTELNETAuthRow" style="display:none">
											<%
												//opeation:telent
												//用户名提示字符串	user_prompt	varchar2(20)	telnet连接所需参数
												//密码提示字符串	pass_prompt	varchar2(20)	telnet连接所需参数
												//命令提示符	prompt	varchar2(20)	telnet连接所需参数
												//执行命令提示符	exec_prompt	varchar2(20)	telnet连接所需参数
												//翻页命令提示符	next_prompt	varchar2(20)	telnet连接所需参数
											%>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>用户名提示字符串：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="userPrompt" id="userPrompt" type="text" size="20" maxlength="20" hint="用户名提示字符串" readonly="readonly"
																value="<%=currentSysResAuth.getUserPrompt()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>密码提示字符串：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="passPrompt" id="passPrompt" type="text" size="20" maxlength="20" hint="密码提示字符串" readonly="readonly"
																value="<%=currentSysResAuth.getPassPrompt()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>命令提示符：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="prompt" id="prompt" type="text" size="20" maxlength="20" hint="命令提示符" readonly="readonly" value="<%=currentSysResAuth.getPrompt()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>执行命令提示符：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="execPrompt" id="execPrompt" type="text" size="20" maxlength="20" hint="执行命令提示符" readonly="readonly"
																value="<%=currentSysResAuth.getExecPrompt()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>编码方式：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="nextPrompt" id="nextPrompt" type="text" size="20" maxlength="20" hint="翻页命令提示符" readonly="readonly"
																value="<%=currentSysResAuth.getNextPrompt()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">
															<span class="input_redstar ">*</span>分页提示符：
														</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="sepaWord" id="sepaWord" type="text" size="20" maxlength="20" hint="分页提示符" readonly="readonly" value="<%=currentSysResAuth.getSepaWord()%>" />
														</div>
													</td>
												</tr>
											</table>
										</div>
										<div id="operationSNMPV1/2AuthRow" style="display:none">
											<%
												//operation:snmpv1/snmpv2c
												//团体名	community	varchar2(20)	snmpv1、v2c所需参数
											%>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
												<tr>
													<td valign="top" width="20%"><div align="right">团体名：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="community" id="community" type="text" size="20" maxlength="20" hint="团体名" readonly="readonly" value="<%=currentSysResAuth.getCommunity()%>" />
														</div>
													</td>
												</tr>
											</table>
										</div>
										<div id="operationSNMPV3AuthRow" style="display:none">
											<%
												//operation:snmpv3
												//V3版本用户名	snmpv3_user	varchar2(20)	snmpv3所需参数
												//认证算法	snmpv3_auth	varchar2(20)	snmpv3所需参数
												//认证密钥	snmpv3_authpass	varchar2(20)	snmpv3所需参数
												//加密算法	snmpv3_priv	varchar2(20)	snmpv3所需参数
												//加密密钥	snmpv3_privpass	varchar2(20)	snmpv3所需参数
											%>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
												<tr>
													<td valign="top" width="20%"><div align="right">V3版本用户名：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="snmpv3User" id="snmpv3User" type="text" size="20" maxlength="20" hint="V3版本用户名" readonly="readonly"
																value="<%=currentSysResAuth.getSnmpv3User()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">认证算法：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="snmpv3Auth" id="snmpv3Auth" type="text" size="20" maxlength="20" hint="认证算法" readonly="readonly" value="<%=currentSysResAuth.getSnmpv3Auth()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">认证密钥：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="snmpv3Authpass" id="snmpv3Authpass" type="text" size="20" maxlength="20" hint="认证密钥" readonly="readonly"
																value="<%=currentSysResAuth.getSnmpv3Authpass()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">加密算法：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="snmpv3Priv" id="snmpv3Priv" type="text" size="20" maxlength="20" hint="加密算法" readonly="readonly" value="<%=currentSysResAuth.getSnmpv3Priv()%>" />
														</div>
													</td>
												</tr>
												<tr>
													<td valign="top" width="20%"><div align="right">加密密钥：</div>
													</td>
													<td valign="top" width="80%"><div align="left">
															<input name="snmpv3Privpass" id="snmpv3Privpass" type="text" size="20" maxlength="20" hint="加密密钥" readonly="readonly"
																value="<%=currentSysResAuth.getSnmpv3Privpass()%>" />
														</div>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableborder">
											<tr>
												<td valign="top" width="20%"><div align="right">调度周期：</div>
												</td>
												<td valign="top" width="80%"><div align="left">
														<input name="schedule" id="schedule" type="text" size="30" maxlength="50" hint="调度周期" allownull="false" value="<%=msuTask.getSchedule()%>" /> <input
															type="checkbox" onclick="setTaskType()" />即时任务
													</div>
												</td>
											</tr>
											<tr>
												<td valign="top" width="20%"><div align="right">
														<span class="input_redstar ">*</span>采集命令：
													</div>
												</td>
												<td valign="top" width="80%"><div align="left" id="noSnmpCommandDiv">
														<select id="content" name="content" hint="采集命令">
															<%
																if (sysCommandList != null) {
																	for (int i = 0; i < sysCommandList.size(); i++) {
																		if (Assert.isEmptyString(msuTask.getContent()) == false && msuTask.getContent().equalsIgnoreCase(sysCommandList.get(i).getCommand()) == true) {
																			//BEAN中已经设置了命令
																			out.println("<option value=\"" + sysCommandList.get(i).getId() + "\" selected=\"selected\">" + sysCommandList.get(i).getCommand() + "</option>");
																		} else {
																			out.println("<option value=\"" + sysCommandList.get(i).getId() + "\">" + sysCommandList.get(i).getCommand() + "</option>");
																		}
																	}
																}
															%>
														</select>
													</div>
													<div align="left" id="snmpCommandDiv">
														<%
															if (snmpOIDList != null) {
																for (int i = 0; i < snmpOIDList.size(); i++) {
																	if (Assert.isEmptyString(msuTask.getContent()) == false && msuTask.getContent().indexOf(snmpOIDList.get(i)[0]) != -1) {
																		//BEAN中已经设置了命令
																		out.println("<input type=\"checkbox\" name=\"snmp_oids\" value=\"" + snmpOIDList.get(i)[0] + "\" checked=\"true\"/>" + snmpOIDList.get(i)[1]);
																	} else {
																		out.println("<input type=\"checkbox\" name=\"snmp_oids\" value=\"" + snmpOIDList.get(i)[0] + "\" />" + snmpOIDList.get(i)[1]);
																	}
																}
															}
														%>
													</div>
												</td>
											</tr>
										</table></td>
								</tr>
								<tr>
									<td>
										<div align="center" style="margin-top: 15px; margin-bottom: 15px;">
											<%
												if ("new".equalsIgnoreCase(operationType) == true || "update".equalsIgnoreCase(operationType) == true) {
											%><input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" name="Submit" value="提交"
												onclick="myFormSubmit(taskForm)" />
											<%
												}
											%>
											<input type="button" class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" onclick="window.history.back();" name="back"
												value="返回" />
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</fieldset>
			</td>
		</tr>
	</table>
	</div>
</body>
</html>