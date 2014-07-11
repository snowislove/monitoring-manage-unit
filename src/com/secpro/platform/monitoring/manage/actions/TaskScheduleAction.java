package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.MsuTask;
import com.secpro.platform.monitoring.manage.entity.SysCommand;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.TaskScheduleService;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.LocalEncrypt;
import com.secpro.platform.monitoring.manage.util.MsuMangementAPI;
import com.secpro.platform.monitoring.manage.util.StringFormat;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("TaskScheduleAction")
public class TaskScheduleAction {
	/**
	 * 
	 */
	final public static String ID_TITLE = "tid";
	final public static String REGION_TITLE = "reg";
	final public static String OPERATION_TITLE = "ope";
	final public static String CREATE_AT_TITLE = "cat";
	final public static String SCHEDULE_TITLE = "sch";
	final public static String CONTENT_TITLE = "con";
	final public static String TARGET_IP_TITLE = "tip";
	final public static String TARGET_PORT_TITLE = "tpt";
	final public static String META_DATA_TITLE = "mda";
	final public static String RES_ID_TITLE = "rid";
	final public static String IS_REALTIME_TITLE = "isrt";
	//
	private final static PlatformLogger theLogger = PlatformLogger.getLogger(TaskScheduleAction.class);

	PlatformLogger logger = PlatformLogger.getLogger(TaskScheduleAction.class);
	private String returnMsg;
	private String backUrl;
	private MsuTask msuTask;
	private TaskScheduleService taskScheduleService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public MsuTask getMsuTask() {
		return msuTask;
	}

	public void setMsuTask(MsuTask msuTask) {
		this.msuTask = msuTask;
	}

	public TaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	@Resource(name = "TaskScheduleServiceImpl")
	public void setTaskScheduleService(TaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	//
	/**
	 * create or update the task.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		try {
			String operationType = request.getParameter("operationType");
			if (Assert.isEmptyString(operationType) == true) {
				SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
				throw new Exception("未发现操作类别,操作失败");
			}
			boolean isNew = false;
			if ("new".equalsIgnoreCase(operationType) == true) {
				isNew = true;
			}
			String mcaOperation = request.getParameter("operation");
			if (Assert.isEmptyString(mcaOperation) == true) {
				SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
				throw new Exception("未发现MCA采集类型,操作失败");
			}
			if ("ssh,snmp,telnet".indexOf(mcaOperation) == -1) {
				SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
				throw new Exception(mcaOperation + "不是MCA有效的采集类型[ssh/snmp/telnet],操作失败");
			}
			String commandValue = null;
			String openCommandValue = null;
			if ("snmp".equalsIgnoreCase(mcaOperation) == true) {
				String[] snmpOids = request.getParameterValues("snmp_oids");
				commandValue = "";
				if (snmpOids.length == 1) {
					commandValue = snmpOids[0];
				} else {
					for (int i = 0; i < snmpOids.length; i++) {
						if (i == 0) {
							commandValue = commandValue + snmpOids[i];
						} else {
							commandValue = commandValue + "," + snmpOids[i];
						}
					}
				}
			} else {
				// content中存放的是命令的ID
				SysCommand sysCommand = (SysCommand) (taskScheduleService.getObj(SysCommand.class, Long.parseLong(request.getParameter("content").trim())));
				if (sysCommand == null) {
					SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
					throw new Exception("未找到ID" + request.getParameter("content") + "对应的命令资源");
				}
				commandValue = sysCommand.getCommand();
				openCommandValue = sysCommand.getOpenCommand();
			}
			//
			SysResAuth sysResAuth = buildSysResAuthByRequest(request);
			JSONObject metaDataObj = BuildTaskMetaData(sysResAuth, mcaOperation, request.getParameter("snmp_version"), openCommandValue);
			//
			MsuTask task = buildMSUTaskByRequest(request, isNew, metaDataObj, commandValue);
			
			if (task == null) {
				SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
				throw new Exception("无法从请求中分析出任务主体,操作失败.");
			}
			//
			if(isNew){
				taskScheduleService.save(task);
			}else{
				taskScheduleService.update(task);
			}
			
			//
			returnMsg = "操作成功！";
			String msu_command = MsuMangementAPI.MSU_COMMAND_TASK_ADD;
			if (isNew == false) {
				msu_command = MsuMangementAPI.MSU_COMMAND_TASK_UPDATE;
			}
			MsuMangementAPI.getInstance().publishMUSTaskToMSU(formatMSUTask(task).toString(), msu_command);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			returnMsg = "操作失败:" + e.getMessage();
			SysLogUtil.saveLog(logService, "新建采集任务,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		SysLogUtil.saveLog(logService, "新建采集任务,成功", user.getAccount(), request.getRemoteAddr());
		return "toView";
	}

	/**
	 * remove the musTask by ids,
	 * 
	 * @return
	 * @throws Exception
	 */
	public String remove() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		try {
			String tids = request.getParameter("tids");
			String resId= request.getParameter("resId");
			
			if (Assert.isEmptyString(tids) == true) {
				SysLogUtil.saveLog(logService, "删除采集任务,失败", user.getAccount(), request.getRemoteAddr());
				throw new Exception("未找到需要删除的任务ID");
			}
			String[] idArray = tids.split(",");
			StringBuffer querySQL = new StringBuffer();
			querySQL.append("DELETE FROM MSU_TASK WHERE ID IN (");
			if (idArray.length > 1) {
				for (int i = 0; i < idArray.length; i++) {
					if (i == 0) {
						querySQL.append("'").append(idArray[i]).append("'");
					} else {
						querySQL.append(",'").append(idArray[i]).append("'");
					}
				}
				querySQL.append(")");
			} else {
				querySQL.append("'").append(idArray[0]).append("')");
			}

			//
			this.taskScheduleService.getTaskScheduleDao().executeBySql(querySQL.toString());
			returnMsg = "操作成功！";
			MsuMangementAPI.getInstance().publishMUSTaskToMSU(tids, MsuMangementAPI.MSU_COMMAND_TASK_REMOVE);
			ActionContext actionContext = ActionContext.getContext(); 
			Map<String,Object> requestMap=(Map)actionContext.get("request");
			requestMap.put("resId", resId);
			SysLogUtil.saveLog(logService, "删除采集任务,成功", user.getAccount(), request.getRemoteAddr());
			return "toView";
		} catch (Exception e) {
			request.setAttribute("exception", e);
			returnMsg = "操作失败:" + e.getMessage();
			SysLogUtil.saveLog(logService, "删除采集任务,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}

	}

	public String viewTask() {
		//
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		String resId=request.getParameter("resId");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int pageNo = 1;
		int pageSize = 10;
		if (Assert.isEmptyString(rows) == false) {
			pageSize = Integer.parseInt(rows);
		}
		if (Assert.isEmptyString(page) == false) {
			pageNo = Integer.parseInt(page);
		}
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT MT.ID,");
		querySQL.append("	       R.CITY_NAME,");
		querySQL.append("	       MT.CREATE_AT,");
		querySQL.append("	       MT.SCHEDULE,");
		querySQL.append("	       MT.OPERATION,");
		querySQL.append("	       MT.TARGET_IP,");
		querySQL.append("	       MT.TARGET_PORT,");
		querySQL.append("	       MT.CONTENT,");
		querySQL.append("	       MT.META_DATA,");
		querySQL.append("	       MT.RES_ID,");
		querySQL.append("	       MT.IS_REALTIME");
		querySQL.append("	  FROM (SELECT T.ID,");
		querySQL.append("	               T.REGION,");
		querySQL.append("	               T.CREATE_AT,");
		querySQL.append("	               T.SCHEDULE,");
		querySQL.append("	               T.OPERATION,");
		querySQL.append("	               T.TARGET_IP,");
		querySQL.append("	               T.TARGET_PORT,");
		querySQL.append("	               T.CONTENT,");
		querySQL.append("	               T.META_DATA,");
		querySQL.append("	               T.RES_ID,");
		querySQL.append("	               T.IS_REALTIME");
		querySQL.append("	          FROM MSU_TASK T");
		if(resId!=null&&!resId.equals("")){
			querySQL.append("	         WHERE T.RES_ID="+resId+" AND T.IS_REALTIME = 0) MT");
		}else{
			querySQL.append("	         WHERE T.IS_REALTIME = 0) MT");
		}
		querySQL.append("	  LEFT JOIN (SELECT C.CITY_NAME, C.CITY_CODE FROM SYS_CITY C) R");
		querySQL.append("	    ON MT.REGION = R.CITY_CODE");
		System.out.println(querySQL);
		List<Object> viewTableList = this.taskScheduleService.getTaskScheduleDao().queryBySql(querySQL.toString(), pageSize, pageNo);
		if (viewTableList == null) {
			returnMsg = "获取任务失败！";
			SysLogUtil.saveLog(logService, "查看采集任务,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		int totalSize=0;
		if(resId!=null&&!resId.equals("")){
			totalSize=this.taskScheduleService.getTaskScheduleDao().getRowNumber("MSU_TASK", "IS_REALTIME = 0 AND RES_ID="+resId);
		}else{
			totalSize= this.taskScheduleService.getTaskScheduleDao().getRowNumber("MSU_TASK", "IS_REALTIME = 0");
		}
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			JSONObject viewObj = new JSONObject();
			try {
				viewObj.put("total", totalSize);
				JSONArray rowArray = new JSONArray();
				//
				for (Object taskObj : viewTableList) {
					Object[] rowObj = (Object[]) taskObj;
					if (rowObj != null) {
						try {
							JSONObject messageObj = new JSONObject();
							// querySQL.append("SELECT MT.ID,");
							// querySQL.append("	       R.CITY_NAME,");
							// querySQL.append("	       MT.CREATE_AT,");
							// querySQL.append("	       MT.SCHEDULE,");
							// querySQL.append("	       MT.OPERATION,");
							// querySQL.append("	       MT.TARGET_IP,");
							// querySQL.append("	       MT.TARGET_PORT,");
							// querySQL.append("	       MT.CONTENT,");
							// querySQL.append("	       MT.META_DATA,");
							// querySQL.append("	       MT.RES_ID,");
							// querySQL.append("	       MT.IS_REALTIME");
							messageObj.put("id", (String) rowObj[0]);
							messageObj.put(ID_TITLE, (String) rowObj[0]);
							messageObj.put(REGION_TITLE, (String) rowObj[1]);
							messageObj.put(CREATE_AT_TITLE, fmt.format(new Date(((Number) rowObj[2]).longValue())));
							messageObj.put(SCHEDULE_TITLE, (String) rowObj[3]);
							messageObj.put(OPERATION_TITLE, (String) rowObj[4]);
							messageObj.put(TARGET_IP_TITLE, (String) rowObj[5]);
							messageObj.put(TARGET_PORT_TITLE, String.valueOf(rowObj[6]));
							messageObj.put(CONTENT_TITLE, (String) rowObj[7]);
							messageObj.put(META_DATA_TITLE, (String) rowObj[8]);
							messageObj.put(RES_ID_TITLE, String.valueOf(rowObj[9]));
							messageObj.put(IS_REALTIME_TITLE, String.valueOf(rowObj[10]));
							rowArray.put(messageObj);
						} catch (JSONException e) {
							theLogger.exception(e);
						}
					}
				}
				viewObj.put("rows", rowArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(viewObj.toString());
			SysLogUtil.saveLog(logService, "查看采集任务,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(viewObj.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return "success";
	}

	/**
	 * @param request
	 * @param isNew
	 * @param command
	 * @return
	 * @throws Exception
	 * 
	 *             build bean from form.
	 */
	private MsuTask buildMSUTaskByRequest(HttpServletRequest request, boolean isNew, JSONObject metaDataObj, String command) throws Exception {
		if (request == null || metaDataObj == null) {
			return null;
		}

		MsuTask task = new MsuTask();
		//
		task.setRegion(request.getParameter("region"));
		task.setOperation(request.getParameter("operation"));
		task.setCreateAt(System.currentTimeMillis());
		task.setMetaData(metaDataObj.toString());
		task.setContent(command);
		task.setResId(Long.parseLong(request.getParameter("resId")));
		task.setTargetIp(request.getParameter("targetIp").trim());
		task.setTargetPort(Integer.parseInt(request.getParameter("targetPort")));
		task.setIsRealtime(Boolean.parseBoolean(request.getParameter("isRealtime")));
		if (task.getIsRealtime() == true) {
			task.setSchedule("realtime");
		} else {
			task.setSchedule(request.getParameter("schedule"));
		}
		if (isNew == true) {
			// create a UUID.
			task.setId(createMSUTaskID(task.getRegion(), task.getOperation(), task.getIsRealtime()));
		} else {
			task.setId(request.getParameter("id").trim());
		}
		//
		return task;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private SysResAuth buildSysResAuthByRequest(HttpServletRequest request) throws Exception {
		if (request == null) {
			throw new Exception("request为空");
		}
		SysResAuth resAuth = new SysResAuth();
		
		resAuth.setUsername(request.getParameter("username"));
		resAuth.setPassword(LocalEncrypt.Decode(request.getParameter("password")));
		resAuth.setUserPrompt(request.getParameter("userPrompt").equals("null")?"":request.getParameter("userPrompt"));
		resAuth.setPassPrompt(request.getParameter("passPrompt").equals("null")?"":request.getParameter("passPrompt"));
		resAuth.setPrompt(request.getParameter("prompt").equals("null")?"":request.getParameter("prompt"));
		resAuth.setExecPrompt(request.getParameter("execPrompt").equals("null")?"":request.getParameter("execPrompt"));
		resAuth.setNextPrompt(request.getParameter("nextPrompt").equals("null")?"":request.getParameter("nextPrompt"));
		
		resAuth.setSepaWord(request.getParameter("sepaWord").equals("null")?"":request.getParameter("sepaWord"));
		resAuth.setCommunity(request.getParameter("community").equals("null")?"":request.getParameter("community"));
		resAuth.setSnmpv3User(request.getParameter("snmpv3User").equals("null")?"":request.getParameter("snmpv3User"));
		resAuth.setSnmpv3Auth(request.getParameter("snmpv3Auth").equals("null")?"":request.getParameter("snmpv3Auth"));
		resAuth.setSnmpv3Authpass(request.getParameter("snmpv3Authpass").equals("null")?"":request.getParameter("snmpv3Authpass"));
		resAuth.setSnmpv3Priv(request.getParameter("snmpv3Priv").equals("null")?"":request.getParameter("snmpv3Priv"));
		resAuth.setSnmpv3Privpass(request.getParameter("snmpv3Privpass").equals("null")?"":request.getParameter("snmpv3Privpass"));
		resAuth.setResId(Long.valueOf(request.getParameter("resId")));
		//
		resAuth.setFilterString(request.getParameter("filterString").equals("null")?"":request.getParameter("filterString"));
		resAuth.setTerminalType(request.getParameter("terminalType").equals("null")?"":request.getParameter("terminalType"));
		//
		return resAuth;
	}

	/**
	 * create a SN or ID for task , UUID
	 * 
	 * @param region
	 * @param operation
	 * @param isRealTime
	 * @param createAt
	 * @return
	 * @throws Exception
	 */
	public final static String createMSUTaskID(String region, String operation, boolean isRealTime) throws Exception {
		if (region == null || region.length() == 0) {
			throw new Exception("invalid parameter region.");
		}
		if (operation == null || operation.length() == 0) {
			throw new Exception("invalid parameter operation.");
		}
		String regionShort = region;
		if (region.length() > 6) {
			regionShort = region.substring(0, 6);
		}
		String operationShort = operation;
		if (region.length() > 8) {
			operationShort = operation.substring(0, 8);
		}
		String sn = UUID.randomUUID().toString();
		String sortSN = sn.substring(0, 8) + sn.substring(9, 13) + sn.substring(14, 18) + sn.substring(19, 23) + sn.substring(24);
		// region(6)-isRealTime(1)-operation(8)-UUID(32) size=6+1+8+32+3<=50;
		return regionShort.toUpperCase() + "-" + (isRealTime ? 1 : 0) + "-" + operationShort.toUpperCase() + "-" + sortSN.toUpperCase();
	}

	private JSONObject formatMSUTask(MsuTask task) {
		JSONObject messageObj = new JSONObject();
		if (task != null) {
			try {
				messageObj.put(ID_TITLE, task.getId());
				messageObj.put(REGION_TITLE, task.getRegion());
				messageObj.put(OPERATION_TITLE, task.getOperation());
				messageObj.put(CREATE_AT_TITLE, task.getCreateAt());
				messageObj.put(SCHEDULE_TITLE, task.getSchedule());
				messageObj.put(CONTENT_TITLE, task.getContent());
				messageObj.put(META_DATA_TITLE, new JSONObject(task.getMetaData()));
				messageObj.put(RES_ID_TITLE, task.getResId());
				messageObj.put(IS_REALTIME_TITLE, task.getIsRealtime());
				messageObj.put(TARGET_IP_TITLE, task.getTargetIp());
				messageObj.put(TARGET_PORT_TITLE, task.getTargetPort());
			} catch (JSONException e) {
				theLogger.exception(e);
			}

		}
		System.out.println(">>" + messageObj);
		return messageObj;
	}

	private JSONObject BuildTaskMetaData(SysResAuth resAuth, String mcaOperation, String snmpVersion, String openCommand) throws Exception {

		if (resAuth == null) {
			throw new Exception("防火墙认证信息为空");
		}
		if (Assert.isEmptyString(mcaOperation) == true) {
			throw new Exception("MCA采集类型为空");
		}
		if (mcaOperation.equalsIgnoreCase("snmp") == true) {
			if (Assert.isEmptyString(snmpVersion) == true) {
				throw new Exception("MCA采集类型snmp的版本为空");
			}
		}
		if (mcaOperation.equalsIgnoreCase("telnet") == true) {
			if (Assert.isEmptyString(openCommand) == true) {
				throw new Exception("MCA采集类型telnet中openCommand为空");
			}
		}
		JSONObject metaDataObj = new JSONObject();
		try {
			//
			if ("telnet".equalsIgnoreCase(mcaOperation) == true || "ssh".equalsIgnoreCase(mcaOperation) == true) {
				if (resAuth.getUsername() != null && resAuth.getUsername().length() > 0) {
					metaDataObj.put("username", resAuth.getUsername());
				}
				//
				if (resAuth.getPassword() != null && resAuth.getPassword().length() > 0) {
					metaDataObj.put("password", resAuth.getPassword());
				}
			}
			if ("ssh".equalsIgnoreCase(mcaOperation) == true) {
				if (Assert.isEmptyString(resAuth.getFilterString()) == false) {
					metaDataObj.put("filterString", resAuth.getFilterString());
				}
				if (Assert.isEmptyString(resAuth.getTerminalType()) == false) {
					metaDataObj.put("terminalType", resAuth.getTerminalType());
				}
			}
			if ("telnet".equalsIgnoreCase(mcaOperation) == true) {
				if (resAuth.getUserPrompt() != null && resAuth.getUserPrompt().length() > 0) {
					metaDataObj.put("userPrompt", resAuth.getUserPrompt());
				}
				if (resAuth.getPassPrompt() != null && resAuth.getPassPrompt().length() > 0) {
					metaDataObj.put("passwdPrompt", resAuth.getPassPrompt());
				}
				if (resAuth.getPrompt() != null && resAuth.getPrompt().length() > 0) {
					metaDataObj.put("prompt", resAuth.getPrompt());
				}
				if (resAuth.getExecPrompt() != null && resAuth.getExecPrompt().length() > 0) {
					metaDataObj.put("execPrompt", resAuth.getExecPrompt());
				}
				if (resAuth.getNextPrompt() != null && resAuth.getNextPrompt().length() > 0) {
					metaDataObj.put("separPrompt", resAuth.getNextPrompt());
				}
				if (resAuth.getSepaWord() != null && resAuth.getSepaWord().length() > 0) {				
					metaDataObj.put("separWrod", resAuth.getSepaWord());				
				}
				metaDataObj.put("openCommand", openCommand);
			}
			if ("snmp".equalsIgnoreCase(mcaOperation) == true) {
				if ("1".equals(snmpVersion) || "2".equals(snmpVersion)) {
					if (resAuth.getCommunity() != null && resAuth.getCommunity().length() > 0) {
						metaDataObj.put("community", resAuth.getCommunity());
					}
				} else if ("3".equals(snmpVersion)) {
					if (resAuth.getSnmpv3User() != null && resAuth.getSnmpv3User().length() > 0) {
						metaDataObj.put("snmpv3User", resAuth.getSnmpv3User());
					}
					if (resAuth.getSnmpv3Auth() != null && resAuth.getSnmpv3Auth().length() > 0) {
						metaDataObj.put("snmpv3Auth", resAuth.getSnmpv3Auth());
					}
					if (resAuth.getSnmpv3Authpass() != null && resAuth.getSnmpv3Authpass().length() > 0) {
						metaDataObj.put("snmpv3Authpass", resAuth.getSnmpv3Authpass());
					}
					if (resAuth.getSnmpv3Priv() != null && resAuth.getSnmpv3Priv().length() > 0) {
						metaDataObj.put("snmpv3Priv", resAuth.getSnmpv3Priv());
					}
					if (resAuth.getSnmpv3Privpass() != null && resAuth.getSnmpv3Privpass().length() > 0) {
						metaDataObj.put("snmpv3Privpass", resAuth.getSnmpv3Privpass());
					}
				}
				metaDataObj.put("snmp_version", snmpVersion);
			}
		} catch (JSONException e) {
			theLogger.exception(e);
		}

		return metaDataObj;
	}
}
