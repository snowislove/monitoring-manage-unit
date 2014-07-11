package com.secpro.platform.monitoring.manage.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.secpro.platform.monitoring.manage.entity.EventMsg;
import com.secpro.platform.monitoring.manage.entity.EventType;
import com.secpro.platform.monitoring.manage.entity.NotifyUserRule;
import com.secpro.platform.monitoring.manage.entity.SendMsg;
import com.secpro.platform.monitoring.manage.entity.SysEvent;
import com.secpro.platform.monitoring.manage.entity.SysEventDealMsg;
import com.secpro.platform.monitoring.manage.entity.SysEventHis;
import com.secpro.platform.monitoring.manage.entity.SysEventRule;
import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.services.EventMsgService;
import com.secpro.platform.monitoring.manage.services.EventTypeService;
import com.secpro.platform.monitoring.manage.services.NotifyUserRuleService;
import com.secpro.platform.monitoring.manage.services.SendMsgService;
import com.secpro.platform.monitoring.manage.services.SysEventDealMsgService;
import com.secpro.platform.monitoring.manage.services.SysEventHisService;
import com.secpro.platform.monitoring.manage.services.SysEventRuleService;
import com.secpro.platform.monitoring.manage.services.SysEventService;
import com.secpro.platform.monitoring.manage.services.SysKpiInfoService;
import com.secpro.platform.monitoring.manage.services.SysLogService;
import com.secpro.platform.monitoring.manage.services.SysUserInfoService;
import com.secpro.platform.monitoring.manage.util.SysLogUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * 
 * @author liyan 处理告警事件类的请求
 * 
 */

@Controller("EventAction")
public class EventAction {
	PlatformLogger logger = PlatformLogger.getLogger(EventAction.class);
	private SysEventService sysEventService;
	private SysEventHisService sysEventHisService;
	private SysEventHis syh;
	private SysEvent se;
	private EventType eventType;
	private String returnMsg;
	private String backUrl;
	private EventTypeService eventTypeService;
	private EventMsg msg;
	private EventMsgService msgService;
	private SysEventRuleService eventRuleService;
	private SysEventRule eventRule;
	private SysKpiInfoService kpiService;
	private String errorMsg;
	private SysEventDealMsgService dealMsgService;
	private SysUserInfoService userService;
	private NotifyUserRuleService notifyService;
	private SendMsgService sendMsgService;
	private SysLogService logService;
	
	public SysLogService getLogService() {
		return logService;
	}
	@Resource(name = "SysLogServiceImpl")
	public void setLogService(SysLogService logService) {
		this.logService = logService;
	}
	public SendMsgService getSendMsgService() {
		return sendMsgService;
	}

	@Resource(name = "SendMsgServiceImpl")
	public void setSendMsgService(SendMsgService sendMsgService) {
		this.sendMsgService = sendMsgService;
	}

	public NotifyUserRuleService getNotifyService() {
		return notifyService;
	}

	@Resource(name = "NotifyUserRuleServiceImpl")
	public void setNotifyService(NotifyUserRuleService notifyService) {
		this.notifyService = notifyService;
	}

	public SysUserInfoService getUserService() {
		return userService;
	}

	@Resource(name = "SysUserInfoServiceImpl")
	public void setUserService(SysUserInfoService userService) {
		this.userService = userService;
	}

	public SysEventDealMsgService getDealMsgService() {
		return dealMsgService;
	}

	@Resource(name = "SysEventDealMsgServiceImpl")
	public void setDealMsgService(SysEventDealMsgService dealMsgService) {
		this.dealMsgService = dealMsgService;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public SysKpiInfoService getKpiService() {
		return kpiService;
	}

	@Resource(name = "SysKpiInfoServiceImpl")
	public void setKpiService(SysKpiInfoService kpiService) {
		this.kpiService = kpiService;
	}

	public SysEventRule getEventRule() {
		return eventRule;
	}

	public void setEventRule(SysEventRule eventRule) {
		this.eventRule = eventRule;
	}

	public SysEventRuleService getEventRuleService() {
		return eventRuleService;
	}

	@Resource(name = "SysEventRuleServiceImpl")
	public void setEventRuleService(SysEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}

	public EventMsgService getMsgService() {
		return msgService;
	}

	@Resource(name = "EventMsgServiceImpl")
	public void setMsgService(EventMsgService msgService) {
		this.msgService = msgService;
	}

	public EventMsg getMsg() {
		return msg;
	}

	public void setMsg(EventMsg msg) {
		this.msg = msg;
	}

	public EventTypeService getEventTypeService() {
		return eventTypeService;
	}

	@Resource(name = "EventTypeServiceImpl")
	public void setEventTypeService(EventTypeService eventTypeService) {
		this.eventTypeService = eventTypeService;
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

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public SysEvent getSe() {
		return se;
	}

	public void setSe(SysEvent se) {
		this.se = se;
	}

	public SysEventHis getSyh() {
		return syh;
	}

	public void setSyh(SysEventHis syh) {
		this.syh = syh;
	}

	public SysEventService getSysEventService() {
		return sysEventService;
	}

	@Resource(name = "SysEventServiceImpl")
	public void setSysEventService(SysEventService sysEventService) {
		this.sysEventService = sysEventService;
	}

	public SysEventHisService getSysEventHisService() {
		return sysEventHisService;
	}

	@Resource(name = "SysEventHisServiceImpl")
	public void setSysEventHisService(SysEventHisService sysEventHisService) {
		this.sysEventHisService = sysEventHisService;
	}

	// 告警确认
	public String dealEvent() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");// 0代表确认告警，1代表清楚告警
		String dealMsg = request.getParameter("dealMsg");
		HttpSession s = request.getSession();
		SysUserInfo user = (SysUserInfo) s.getAttribute("user");
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		if (type == null) {
			requestMap.put("close", "1");
			requestMap.put("msg", "系统错误，告警处理失败！");
			logger.error("未获取到告警处理类型，处理失败！");
			SysLogUtil.saveLog(logService, "未获取到告警处理类型，处理失败", user.getAccount(), request.getRemoteAddr());
			return "success";
		}
		if (type.trim().equals("")) {
			requestMap.put("close", "1");
			requestMap.put("msg", "系统错误，告警处理失败！");
			logger.error("未获取到告警处理类型，处理失败！");
			SysLogUtil.saveLog(logService, "未获取到告警处理类型，处理失败", user.getAccount(), request.getRemoteAddr());
			return "success";
		}
		
		if (user == null) {
			requestMap.put("close", "1");
			requestMap.put("msg", "告警处理人为空,告警处理失败！");
			logger.error("告警处理人为空,告警处理失败！");
			SysLogUtil.saveLog(logService, "告警处理人为空,告警处理失败", user.getAccount(), request.getRemoteAddr());
			return "success";
		}
		if (se.getId() == null) {
			requestMap.put("close", "1");
			requestMap.put("msg", "系统错误，告警处理失败！");
			logger.error("事件ID为空,告警处理失败！");
			SysLogUtil.saveLog(logService, "事件ID为空,告警处理失败", user.getAccount(), request.getRemoteAddr());
			return "success";
		}

		if (type.equals("0")) {
			SysEvent event = (SysEvent) sysEventService.getObj(SysEvent.class,
					se.getId());
			SysEventDealMsg smsg = (SysEventDealMsg) dealMsgService.getObj(
					SysEventDealMsg.class, se.getId());
			event.setConfirmUser(user.getAccount());
			event.setConfirmDate(sdf.format(new Date()));
			sysEventService.update(event);
			if (smsg == null) {
				smsg = new SysEventDealMsg();
				smsg.setEventId(event.getId());
				smsg.setConfirmDealmsg(dealMsg);
				dealMsgService.save(smsg);
			} else {
				smsg.setConfirmDealmsg(dealMsg);
				dealMsgService.update(smsg);
			}
		} else if (type.equals("1")) {
			
			SysEvent event = (SysEvent) sysEventService.getObj(SysEvent.class,
					se.getId());
			SysEventDealMsg smsg = (SysEventDealMsg) dealMsgService.getObj(
					SysEventDealMsg.class, se.getId());
			final SysEventHis eventHis = new SysEventHis();
			eventHis.setId(event.getId());
			eventHis.setEventLevel(event.getEventLevel());
			eventHis.setEventTypeId(event.getEventTypeId());
			eventHis.setMessage(event.getMessage());
			eventHis.setResId(event.getResId());
			eventHis.setCdate(event.getCdate());
			eventHis.setConfirmUser(event.getConfirmUser() == null ? user
					.getAccount() : event.getConfirmUser());
			eventHis.setConfirmDate(event.getConfirmDate() == null ? sdf
					.format(new Date()) : event.getConfirmDate());
			eventHis.setClearUser(user.getAccount());
			eventHis.setClearDate(sdf.format(new Date()));
			if (smsg == null) {
				smsg = new SysEventDealMsg();
				smsg.setEventId(event.getId());
				smsg.setConfirmDealmsg(dealMsg);
				dealMsgService.save(smsg);
			} else {
				smsg.setConfirmDealmsg(dealMsg);
				dealMsgService.update(smsg);
			}
			sysEventService.delete(event);
			sysEventHisService.save(eventHis);

			new Thread() {
				public void run() {
					
					List notifyUsers = notifyService 
							.queryAll("select u.userName , u.mobelTel from NotifyUserRule n, SysUserInfo u , SysEventRule r  where  u.id=n.userId and n.resId="
									+ eventHis.getResId()
									+ " and n.eventRuleId = r.id and r.eventTypeId="
									+ eventHis.getEventTypeId());
					
					if (notifyUsers != null && notifyUsers.size() > 0) {
						for (int i = 0; i < notifyUsers.size(); i++) {
							Object[] u = (Object[]) notifyUsers.get(i);
							SendMsg sendMsg = new SendMsg();
							sendMsg.setMessage("[恢复]" + eventHis.getMessage());
							
							sendMsg.setMobelTel((String) u[1]);
							sendMsg.setUserName((String) u[0]);
							sendMsg.setCdate(sdf.format(new Date()));
							sendMsgService.save(sendMsg);
						}
					}
				}
			}.start();
		}
		requestMap.put("close", 1);
		requestMap.put("msg", "告警处理成功，点击确认后关闭此页面！");
		SysLogUtil.saveLog(logService, "告警处理,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	// 根据资源ID获取告警列表
	public void getEventbyResId() {

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		int pageNum = 1;
		int maxPage = 10;
		if (rows != null && !rows.trim().equals("")) {
			maxPage = Integer.parseInt(rows);
		}
		if (page != null && !page.trim().equals("")) {
			pageNum = Integer.parseInt(page);
		}
		String resId = request.getParameter("resId");

		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (resId == null) {
				
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看资源告警,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if (resId.trim().equals("")) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看资源告警,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			List eventList = sysEventService
					.queryAll("from SysEvent s where s.resId=" + resId);
			List pageEvent = sysEventService.queryByPage(
					"from SysEvent s where s.resId=" + resId, pageNum, maxPage);
			if (eventList == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				pw.flush();
				return;
			}
			
			json.put("total", eventList.size());
			json.put("rows", arr);
			for (int i = 0; i < pageEvent.size(); i++) {
				SysEvent event = (SysEvent) pageEvent.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("eventid", event.getId());
				int level = event.getEventLevel();
				if (level == 1) {
					
					sub.put("eventlevel", "通知");
				} else if (level == 2) {
					
					sub.put("eventlevel", "轻微");
				} else if (level == 3) {
					
					sub.put("eventlevel", "重要");
				} else if (level == 4) {
					
					sub.put("eventlevel", "紧急");
				}
				
				sub.put("message", event.getMessage());
				
				sub.put("cdate", sdf1.format(sdf.parse(event.getCdate())));
				
				sub.put("status", (event.getConfirmUser() == null ? "未确认" : "未清除"));
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看资源告警,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public String toDealEvent() {
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventId = request.getParameter("eventId");
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("eventId", eventId);
		return "success";
	}

	public String toViewEvent() {
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		String resId = request.getParameter("resId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (resId.contains("_")) {
			requestMap.put("resId", resId.split("_")[1]);
			SysLogUtil.saveLog(logService, "展示告警事件列表,成功", user.getAccount(), request.getRemoteAddr());
			return "success";
		}
		requestMap.put("resId", resId);
		SysLogUtil.saveLog(logService, "展示告警事件列表,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public void getEventByEventId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventId = request.getParameter("eventId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		StringBuilder result = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (eventId == null) {
				result.append("{}");
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "查询告警事件明细,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if (eventId.trim().equals("")) {
				result.append("{}");
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "查询告警事件明细,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}

			SysEvent event = (SysEvent) sysEventService.getObj(SysEvent.class,
					Long.parseLong(eventId));
			if (event == null) {
				result.append("{}");
				pw.println(result.toString());
				SysLogUtil.saveLog(logService, "查询告警事件明细,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			SysEventDealMsg msg = (SysEventDealMsg) dealMsgService.getObj(
					SysEventDealMsg.class, Long.parseLong(eventId));
			EventType et = (EventType) eventTypeService.getObj(EventType.class,
					event.getEventTypeId());
			JSONObject json = new JSONObject();

			json.put("eventId", event.getId());
			json.put("eventType", et.getEventTypeName());

			int level = event.getEventLevel();
			if (level == 1) {
				json.put("eventlevel", "通知");

			} else if (level == 2) {
				json.put("eventlevel", "轻微");

			} else if (level == 3) {
				json.put("eventlevel", "重要");

			} else if (level == 4) {
				json.put("eventlevel", "紧急");

			}
			json.put("message", event.getMessage());

			json.put("cdate", sdf1.format(sdf.parse(event.getCdate())));

			json.put(
					"cuser",
					(event.getConfirmUser() == null ? "" : event
							.getConfirmUser()));

			json.put(
					"confirmDate",
					(event.getConfirmDate() == null ? "" : sdf1.format(sdf
							.parse(event.getConfirmDate()))));

			if (msg == null) {
				json.put("dealMsg", "");

			} else {
				json.put(
						"dealMsg",
						(msg.getConfirmDealmsg() == null ? "" : msg
								.getConfirmDealmsg()));

			}

			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查询告警事件明细,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 根据时间段查找告警事件
	public void getEventbyTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf2 = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss"
		// );
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String from = request.getParameter("ff");
		String to = request.getParameter("tt");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		int pageNum = 1;
		int maxPage = 10;
		if (rows != null && !rows.trim().equals("")) {
			maxPage = Integer.parseInt(rows);
		}
		if (page != null && !page.trim().equals("")) {
			pageNum = Integer.parseInt(page);
		}
		String resId = request.getParameter("resId");
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();

			if (resId == null) {
				
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "按时间查询历史告警,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if (resId.trim().equals("")) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "按时间查询历史告警,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			if (from != null && !from.trim().equals("")) {
				from = sdf.format(sdf1.parse(from));

			} else {
				String todays = sdf1.format(new Date());
				from = sdf.format(sdf1.parse(todays));
			}

			if (to != null && !to.trim().equals("")) {
				to = sdf.format(sdf1.parse(to));
			} else {

				to = sdf.format(new Date());
			}
			List eventList = sysEventHisService
					.queryAll("from SysEventHis s where s.cdate>='" + from
							+ "' and s.cdate <='" + to + "' and s.resId="
							+ resId);
			List pageEvent = sysEventHisService.queryByPage(
					"from SysEventHis s where s.cdate>='" + from
							+ "' and s.cdate <='" + to + "' and s.resId="
							+ resId, pageNum, maxPage);
			if (eventList == null) {
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "按时间查询历史告警,失败", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			
			json.put("total", eventList.size());
			json.put("rows", arr);
			for (int i = 0; i < pageEvent.size(); i++) {
				SysEventHis event = (SysEventHis) pageEvent.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("eventid", event.getId());
				int level = event.getEventLevel();
				if (level == 1) {
					
					sub.put("eventlevel", "通知");
				} else if (level == 2) {
					
					sub.put("eventlevel", "轻微");
				} else if (level == 3) {
					
					sub.put("eventlevel", "重要");
				} else if (level == 4) {
					
					sub.put("eventlevel", "紧急");
				}
				
				sub.put("message", event.getMessage());
				
				sub.put("cdate", sdf1.format(sdf.parse(event.getCdate())));
				
				sub.put("confirmUser", event.getConfirmUser());
			
				sub.put("confirmDate", (event.getConfirmDate() == null ? "" : sdf1.format(sdf.parse(event.getConfirmDate()))));
				
				sub.put("clearUser",  event.getClearUser());
				
				sub.put("clearDate",  (event.getClearDate() == null ? "" : sdf1.format(sdf.parse(event.getClearDate()))));
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "按时间查询历史告警,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public String saveEventType() {
		ActionContext actionContext = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (eventType.getEventTypeName() == null) {
			returnMsg = "类型名称不能为空，事件类型保存失败！";
			logger.info("fetch EventTypeName failed , EventTypeName is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventTypeName().trim().equals("")) {
			returnMsg = "事件类型名称不能为空，事件类型保存失败！";
			logger.info("fetch EventTypeName failed , EventTypeName is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		List typeList=eventTypeService.queryAll("from EventType e where e.eventTypeName='"+eventType.getEventTypeName()+"'");
		if(typeList!=null&&typeList.size()>0){
			returnMsg = "此事件类型已经存在！";
			logger.info("此事件类型已经存在！");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventRecover() == null) {
			returnMsg = "事件类型是否可恢复不能为空，事件类型保存失败！";
			logger.info("fetch EventRecover failed , EventRecover is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventRecover().trim().equals("")) {
			returnMsg = "事件类型是否可恢复不能为空，事件类型保存失败！";
			logger.info("fetch EventRecover failed , EventRecover is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		eventTypeService.save(eventType);
		SysLogUtil.saveLog(logService, "创建事件类型,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String toModifyEventType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventTypeId = request.getParameter("eventTypeId");
		
		if (eventTypeId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			
			return "failed";
		}
		if (eventTypeId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		EventType et = (EventType) eventTypeService.getObj(EventType.class,
				Long.parseLong(eventTypeId));
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("eType", et);
		return "success";
	}

	public String modifyEventType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (eventType.getId() == null) {
			returnMsg = "系统错误，事件类型修改失败！";
			logger.info("fetch EventTypeName failed , EventTypeName is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventTypeName() == null) {
			returnMsg = "类型名称不能为空，事件类型修改失败！";
			logger.info("fetch EventTypeName failed , EventTypeName is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventTypeName().trim().equals("")) {
			returnMsg = "事件类型名称不能为空，事件类型修改失败！";
			logger.info("fetch EventTypeName failed , EventTypeName is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		
		if (eventType.getEventRecover() == null) {
			returnMsg = "事件类型是否可恢复不能为空，事件类型修改失败！";
			logger.info("fetch EventRecover failed , EventRecover is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventType.getEventRecover().trim().equals("")) {
			returnMsg = "事件类型是否可恢复不能为空，事件类型修改失败！";
			logger.info("fetch EventRecover failed , EventRecover is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}

		EventType et = (EventType) eventTypeService.getObj(EventType.class,
				eventType.getId());
		if(!et.getEventTypeName().equals(eventType.getEventTypeName())){
			List typeList=eventTypeService.queryAll("from EventType e where e.eventTypeName='"+eventType.getEventTypeName()+"'");
			if(typeList!=null&&typeList.size()>0){
				returnMsg = "此事件类型已经存在！";
				logger.info("此事件类型已经存在！");
				backUrl = "event/viewEventType.jsp";
				SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
				return "failed";
			}
		}
		if (et == null) {
			returnMsg = "系统错误，事件类型修改失败！";
			logger.info("fetch EventType failed from database!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		et.setEventRecover(eventType.getEventRecover());

		et.setEventTypeDesc(eventType.getEventTypeDesc());
		et.setEventTypeName(eventType.getEventTypeName());
		eventTypeService.update(et);
		SysLogUtil.saveLog(logService, "修改事件类型,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public void ViewEventType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		int pageNum = 1;
		int maxPage = 10;
		if (rows != null && !rows.trim().equals("")) {
			maxPage = Integer.parseInt(rows);
		}
		if (page != null && !page.trim().equals("")) {
			pageNum = Integer.parseInt(page);
		}
		List eventTypeList = eventTypeService.queryAll("from EventType");
		List pageEventType = eventTypeService.queryByPage("from EventType",
				pageNum, maxPage);
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (eventTypeList == null) {
				
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看事件类型列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			
			json.put("total", eventTypeList.size());
			json.put("rows", arr);
			for (int i = 0; i < pageEventType.size(); i++) {
				EventType et = (EventType) pageEventType.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("eventTypeId", et.getId());
				
				sub.put("eventTypeName", et.getEventTypeName());
				
				sub.put("eventTypeDesc", (et.getEventTypeDesc() == null ? " " : et.getEventTypeDesc()));
				List msgList = msgService
						.queryAll("from EventMsg m where m.eventTypeId="
								+ et.getId());
				if (msgList != null && msgList.size() > 0) {
					
					sub.put("isMsg","1");
				} else {
					
					sub.put("isMsg","0");
				}

				
				sub.put("eventRecover",(et.getEventRecover().equals("1") ? "自动恢复": "不自动恢复"));
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看事件类型列表,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public String deleteEventType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventTypeId = request.getParameter("eventTypeId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (eventTypeId == null) {
			returnMsg = "系统错误，事件类型删除失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "删除事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventTypeId.trim().equals("")) {
			returnMsg = "系统错误，事件类型删除失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "删除事件类型,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] eventTypeIds = eventTypeId.split(",");
		for (int i = 0; i < eventTypeIds.length; i++) {
			EventType et = new EventType();
			et.setId(Long.parseLong(eventTypeIds[i]));

			/*
			 * List
			 * msgList=msgService.queryAll("from EventMsg m where m.eventTypeId="
			 * +et.getId()); if(msgList!=null&&msgList.size()>0){
			 * msgService.delete(msgList.get(0)); }
			 */
			final String eid = et.getId() + "";
			eventTypeService.delete(et);
			new Thread() {
				public void run() {
					eventTypeService.deleteRelevance(eid);
				}
			}.start();
		}
		SysLogUtil.saveLog(logService, "删除事件类型,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String saveEventMsg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (msg.getMsgFormat() == null) {
			returnMsg = "消息格式不能为空，事件消息格式保存失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件消息格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (msg.getMsgFormat().trim().equals("")) {
			returnMsg = "消息格式不能为空，事件消息格式保存失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件消息格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (msg.getEventTypeId() == null) {
			returnMsg = "系统错误，事件消息格式保存失败！";
			logger.info("fetch EventTypeId failed , EventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "创建事件消息格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		msgService.save(msg);
		SysLogUtil.saveLog(logService, "创建事件消息格式,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String toSaveEventMsg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventTypeId = request.getParameter("eventTypeId");
		if (eventTypeId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		if (eventTypeId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("eventTypeId", eventTypeId);
		return "success";
	}

	public String toModifyEventMsg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String eventTypeId = request.getParameter("eventTypeId");
		if (eventTypeId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		if (eventTypeId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		List msgList = msgService
				.queryAll("from EventMsg m where m.eventTypeId=" + eventTypeId);
		if (msgList == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventMsg failed from database!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}
		if (msgList.size() == 0) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventMsg failed from database!");
			backUrl = "event/viewEventType.jsp";
			return "failed";
		}

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("emsg", msgList.get(0));
		return "success";
	}

	public String modifyEventMsg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (msg.getId() == null) {
			returnMsg = "系统错误，事件消息格式修改失败！";
			logger.info("fetch id failed , eventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (msg.getMsgFormat() == null) {
			returnMsg = "消息格式不能为空，事件消息格式修改失败！";
			logger.info("fetch msgFormat failed , msgFormat is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (msg.getMsgFormat().trim().equals("")) {
			returnMsg = "消息格式不能为空，事件消息格式修改失败！";
			logger.info("fetch msgFormat failed , msgFormat is ''!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}

		if (msg.getEventTypeId() == null) {
			returnMsg = "系统错误，事件消息格式修改失败！";
			logger.info("fetch EventTypeId failed , EventTypeId is null!");
			backUrl = "event/viewEventType.jsp";
			SysLogUtil.saveLog(logService, "修改事件格式,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		msgService.update(msg);
		SysLogUtil.saveLog(logService, "修改事件格式,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public void getAllEventType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		
		PrintWriter pw = null;
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();

			List eventType = eventTypeService
					.queryAll("select e.id ,e.eventTypeName,e.eventTypeDesc from EventType e");
			if (eventType == null) {
				SysLogUtil.saveLog(logService, "查看事件类型列表,成功", user.getAccount(), request.getRemoteAddr());
				pw.println(arr.toString());
				
				pw.flush();
				return;
			}
			
			for (int i = 0; i < eventType.size(); i++) {
				Object[] etype = (Object[]) eventType.get(i);
				JSONObject sub=new JSONObject();
				
				sub.put("id", etype[0]);
				sub.put("text", etype[1]+ " " + (etype[2] == null ? "" : etype[2]));
				arr.put(sub);

			}
			
			System.out.println(arr.toString());
			SysLogUtil.saveLog(logService, "查看事件类型列表,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(arr.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public void viewEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		String eventTypeId = request.getParameter("eventTypeId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		List allEventRule = null;
		if (resId != null && !resId.equals("")) {

			allEventRule = eventRuleService
					.queryAll("from SysEventRule s where s.resId=" + resId
							+ " and s.eventTypeId=" + eventTypeId);
		} else {
			allEventRule = eventRuleService
					.queryAll("from SysEventRule s where s.resId is null and s.eventTypeId="
							+ eventTypeId);
		}
		// List allEventRule=eventRuleService.queryAll("from SysEventRule");
		
		PrintWriter pw = null;
		JSONObject json=new JSONObject();
		JSONArray arr=new JSONArray();
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (allEventRule == null) {
				
				json.put("total", 0);
				json.put("rows", arr);
				pw.println(json.toString());
				SysLogUtil.saveLog(logService, "查看事件规则,成功", user.getAccount(), request.getRemoteAddr());
				pw.flush();
				return;
			}
			
			json.put("total", allEventRule.size());
			json.put("rows", arr);
			for (int i = 0; i < allEventRule.size(); i++) {
				SysEventRule rule = (SysEventRule) allEventRule.get(i);
				JSONObject sub=new JSONObject();
				List notify = null;
				if (resId != null && !resId.equals("")) {
					notify = notifyService
							.queryAll("from NotifyUserRule n where n.resId="
									+ resId + " and n.eventRuleId="
									+ rule.getId());
				}
				
				sub.put("ruleid", rule.getId());
				int level = rule.getEventLevel();
				if (level == 1) {
					
					sub.put("eventlevel", "通知");
				} else if (level == 2) {
					
					sub.put("eventlevel", "轻微");
				} else if (level == 3) {
					
					sub.put("eventlevel", "重要");
				} else if (level == 4) {
					
					sub.put("eventlevel", "紧急");
				}
				
				sub.put("setMsg", (rule.getSetMsg().equals("0") ? "否" : "是"));
				
				sub.put("tvalue", rule.getThresholdValue());
				String opr = rule.getThresholdOpr();
				if (opr.equals("==") || opr.equals("=")) {
					
					sub.put("topr", "等于");
				} else if (opr.equals("!=")) {
					
					sub.put("topr", "不等于");
				} else if (opr.equals(">")) {
					
					sub.put("topr", "大于");
				} else if (opr.equals(">=")) {
					
					sub.put("topr", "大于等于");
				} else if (opr.equals("<")) {
					
					sub.put("topr", "小于");
				} else if (opr.equals("<=")) {
					
					sub.put("topr", "小于等于");
				}
				
				sub.put("recoverSetMsg", (rule.getRecoverSetMsg().equals("0") ? "否" : "是"));
				
				sub.put("resId", rule.getResId());
				
				sub.put("eventTypeId", rule.getEventTypeId());
				if (notify != null && notify.size() > 0) {
					
					sub.put("isNotyfUser", "1");
				} else {
					
					sub.put("isNotyfUser", "0");
				}
				
				sub.put("repeat", (rule.getRepeat().equals("0") ? "否" : "是"));
				arr.put(sub);
			}
			
			System.out.println(json.toString());
			SysLogUtil.saveLog(logService, "查看事件规则,成功", user.getAccount(), request.getRemoteAddr());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	public String toAddEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		String eventTypeId = request.getParameter("eventTypeId");
		if (eventTypeId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (eventTypeId.equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch eventTypeId failed , eventTypeId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}

		EventType type = (EventType) eventTypeService.getObj(EventType.class,
				Long.parseLong(eventTypeId));

		List ruleList = null;
		if (resId != null && !resId.equals("")) {
			ruleList = eventTypeService
					.queryAll("from SysEventRule s where s.resId=" + resId
							+ " and s.eventTypeId=" + eventTypeId);
		} else {
			ruleList = eventTypeService
					.queryAll("from SysEventRule s where s.resId is null and s.eventTypeId="
							+ eventTypeId);
		}
		String levels = "";
		for (int i = 0; i < ruleList.size(); i++) {
			SysEventRule r = (SysEventRule) ruleList.get(i);
			levels += r.getEventLevel();
			if (i != (ruleList.size() - 1)) {
				levels += ",";
			}
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("resId", resId);
		requestMap.put("type", type);
		requestMap.put("levels", levels);
		requestMap.put("eventName", type.getEventTypeName());
		return "success";
	}

	public String toModifyEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ruleId = request.getParameter("ruleid");
		if (ruleId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId.equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		SysEventRule rule = (SysEventRule) eventRuleService.getObj(
				SysEventRule.class, Long.parseLong(ruleId));
		EventType type = (EventType) eventTypeService.getObj(EventType.class,
				rule.getEventTypeId());
		/*
		 * List
		 * kpiList=kpiService.queryAll("from SysKpiInfo k where k.kpiName='"
		 * +type.getEventTypeName()+"'"); if(kpiList==null){ returnMsg =
		 * "请先创建KPI指标，页面跳转失败！";
		 * logger.info("fetch SysKpiInfo failed from database !"); backUrl =
		 * "event/eventRule.jsp"; return "failed"; } if(kpiList.size()==0){
		 * returnMsg = "请先创建KPI指标，页面跳转失败！";
		 * logger.info("fetch SysKpiInfo failed from database !"); backUrl =
		 * "event/eventRule.jsp"; return "failed"; }
		 */
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("eRule", rule);
		// requestMap.put("kpi", kpiList.get(0));
		return "success";
	}

	public String saveEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (eventRule.getEventLevel() == null) {
			returnMsg = "告警级别不能为空，规则保存失败！";
			logger.info("fetch EventLevel failed , EventLevel is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getEventTypeId() == null) {
			returnMsg = "系统错误，规则保存失败！";
			logger.info("fetch EventTypeId failed , EventTypeId is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRecoverSetMsg() == null) {
			returnMsg = "是否产生恢复告警不能为空，规则保存失败！";
			logger.info("fetch RecoverSetMsg failed , RecoverSetMsg is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRecoverSetMsg().equals("")) {
			returnMsg = "是否产生恢复告警不能为空，规则保存失败！";
			logger.info("fetch RecoverSetMsg failed , RecoverSetMsg is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRepeat() == null) {
			returnMsg = "是否重复告警不能为空，规则保存失败！";
			logger.info("fetch Repeat failed , Repeat is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRepeat().equals("")) {
			returnMsg = "是否重复告警不能为空，规则保存失败！";
			logger.info("fetch Repeat failed , Repeat is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg() == null) {
			returnMsg = "是否产生告警短信不能为空，规则保存失败！";
			logger.info("fetch SetMsg failed , SetMsg is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg().equals("")) {
			returnMsg = "是否产生告警短信不能为空，规则保存失败！";
			logger.info("fetch SetMsg failed , SetMsg is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getThresholdOpr() == null) {
			returnMsg = "操作符不能为空，规则保存失败！";
			logger.info("fetch ThresholdOpr failed , ThresholdOpr is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (eventRule.getThresholdOpr().equals("")) {
			returnMsg = "操作符不能为空，规则保存失败！";
			logger.info("fetch ThresholdOpr failed , ThresholdOpr is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getThresholdValue() == null) {
			returnMsg = "阀值不能为空，规则保存失败！";
			logger.info("fetch ThresholdValue failed , ThresholdValue is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg().equals("0")) {
			eventRule.setRecoverSetMsg("0");
			eventRule.setRepeat("0");
		}
		eventRuleService.save(eventRule);
		SysLogUtil.saveLog(logService, "创建事件规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String modifyEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (eventRule.getId() == null) {
			returnMsg = "系统错误，规则修改失败！";
			logger.info("fetch EventLevel failed , EventLevel is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getEventLevel() == null) {
			returnMsg = "告警级别不能为空，规则修改失败！";
			logger.info("fetch EventLevel failed , EventLevel is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getEventTypeId() == null) {
			returnMsg = "系统错误，规则修改失败！";
			logger.info("fetch EventTypeId failed , EventTypeId is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}

		if (eventRule.getRecoverSetMsg() == null) {
			returnMsg = "是否产生恢复告警不能为空，规则修改失败！";
			logger.info("fetch RecoverSetMsg failed , RecoverSetMsg is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRecoverSetMsg().equals("")) {
			returnMsg = "是否产生恢复告警不能为空，规则修改失败！";
			logger.info("fetch RecoverSetMsg failed , RecoverSetMsg is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRepeat() == null) {
			returnMsg = "是否重复告警不能为空，规则修改失败！";
			logger.info("fetch Repeat failed , Repeat is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getRepeat().equals("")) {
			returnMsg = "是否重复告警不能为空，规则修改失败！";
			logger.info("fetch Repeat failed , Repeat is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg() == null) {
			returnMsg = "是否产生告警短信不能为空，规则修改失败！";
			logger.info("fetch SetMsg failed , SetMsg is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg().equals("")) {
			returnMsg = "是否产生告警短信不能为空，规则修改失败！";
			logger.info("fetch SetMsg failed , SetMsg is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getThresholdOpr() == null) {
			returnMsg = "操作符不能为空，规则修改失败！";
			logger.info("fetch ThresholdOpr failed , ThresholdOpr is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getThresholdOpr().equals("")) {
			returnMsg = "操作符不能为空，规则修改失败！";
			logger.info("fetch ThresholdOpr failed , ThresholdOpr is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getThresholdValue() == null) {
			returnMsg = "阀值不能为空，规则修改失败！";
			logger.info("fetch ThresholdValue failed , ThresholdValue is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "修改事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (eventRule.getSetMsg().equals("0")) {
			eventRule.setRecoverSetMsg("0");
			eventRule.setRepeat("0");
		}
		eventRuleService.update(eventRule);
		SysLogUtil.saveLog(logService, "修改事件规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String deleteEventRule() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ruleId = request.getParameter("ruleId");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (ruleId == null) {
			returnMsg = "系统错误，规则删除失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "删除事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (ruleId.equals("")) {
			returnMsg = "系统错误，规则删除失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "删除事件规则,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] ruleIds = ruleId.split(",");
		for (int i = 0; i < ruleIds.length; i++) {
			SysEventRule r = new SysEventRule();
			r.setId(Long.parseLong(ruleIds[i]));
			eventRuleService.delete(r);
			final String rid = r.getId() + "";
			new Thread() {
				public void run() {
					notifyService.deleteRelevance(rid);
				}
			}.start();
		}
		SysLogUtil.saveLog(logService, "删除事件规则,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String toAddAlarmReceive() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ruleId = request.getParameter("ruleId");
		String resId = request.getParameter("resId");
		if (resId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId failed , resId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (resId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId failed , resId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		List userList = userService
				.queryAll("from SysUserInfo u where u.deleted is null");
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("userList", userList);
		requestMap.put("ruleId", ruleId);
		requestMap.put("resId", resId);
		return "success";
	}

	public String toModifyAlarmReceive() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		String ruleId = request.getParameter("ruleId");
		if (resId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId failed , resId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (resId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch resId failed , resId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}

		List userList = userService
				.queryAll("from SysUserInfo u where u.deleted is null");
		List notifyList = notifyService
				.queryAll("from NotifyUserRule n where n.resId=" + resId
						+ " and eventRuleId=" + ruleId);
		List notifyUser = new ArrayList();

		for (Object o : notifyList) {
			NotifyUserRule notify = (NotifyUserRule) o;
			for (Object u : userList) {
				SysUserInfo user = (SysUserInfo) u;
				if (user.getId() == notify.getUserId()) {
					notifyUser.add(user);
				}
			}
		}
		userList.removeAll(notifyUser);

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("notifyUser", notifyUser);
		requestMap.put("lastUser", userList);
		requestMap.put("resId", resId);
		requestMap.put("ruleId", ruleId);
		return "success";
	}

	public String saveOrUpdateNotifyUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String resId = request.getParameter("resId");
		String ruleId = request.getParameter("ruleId");
		String userIds = request.getParameter("userIds");
		SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
		if (resId == null) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch resId failed , resId is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (resId.trim().equals("")) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch resId failed , resId is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (ruleId == null) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (ruleId.trim().equals("")) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (userIds == null) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch userIds failed , userIds is null!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		if (userIds.trim().equals("")) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("fetch userIds failed , userIds is ''!");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		String[] userid = userIds.split(",");
		boolean flag = notifyService.deleteAllNotifyUser(resId, ruleId);
		if (!flag) {
			returnMsg = "系统错误，告警接收人保存失败！";
			logger.info("delete old notifyUser failed !");
			backUrl = "event/eventRule.jsp";
			SysLogUtil.saveLog(logService, "创建或修改告警接收人,失败", user.getAccount(), request.getRemoteAddr());
			return "failed";
		}
		for (int i = 0; i < userid.length; i++) {
			NotifyUserRule notify = new NotifyUserRule();
			notify.setResId(Long.parseLong(resId));
			notify.setEventRuleId(Long.parseLong(ruleId));
			notify.setUserId(Long.parseLong(userid[i]));
			notifyService.save(notify);
		}
		SysLogUtil.saveLog(logService, "创建或修改告警接收人,成功", user.getAccount(), request.getRemoteAddr());
		return "success";
	}

	public String toAddFWAlarmReceive() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ruleId = request.getParameter("ruleId");

		if (ruleId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("ruleId", ruleId);
		return "success";
	}

	public String toAddMcaAlarmReceive() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ruleId = request.getParameter("ruleId");

		if (ruleId == null) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is null!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		if (ruleId.trim().equals("")) {
			returnMsg = "系统错误，页面跳转失败！";
			logger.info("fetch ruleId failed , ruleId is ''!");
			backUrl = "event/eventRule.jsp";
			return "failed";
		}
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> requestMap = (Map) actionContext.get("request");
		requestMap.put("ruleId", ruleId);
		return "success";
	}

	public void queryAlarmReceive() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ruleId = request.getParameter("ruleId");
		String resId = request.getParameter("resId");

		StringBuilder sb = new StringBuilder();
		PrintWriter pw = null;
		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setContentType("text/json");
			pw = resp.getWriter();
			if (resId == null) {
				pw.println(new JSONObject().toString());
				pw.flush();
			}
			if (resId.trim().equals("")) {
				pw.println(new JSONObject().toString());
				pw.flush();
			}
			if (ruleId == null) {
				pw.println(new JSONObject().toString());
				pw.flush();
			}
			if (ruleId.trim().equals("")) {
				pw.println(new JSONObject().toString());
				pw.flush();
			}

			List userList = userService
					.queryAll("from SysUserInfo u where u.deleted is null");
			List notifyList = notifyService
					.queryAll("from NotifyUserRule n where n.resId=" + resId
							+ " and eventRuleId=" + ruleId);
			List notifyUser = new ArrayList();

			for (Object o : notifyList) {
				NotifyUserRule notify = (NotifyUserRule) o;
				for (Object u : userList) {
					SysUserInfo user = (SysUserInfo) u;
					if (user.getId() == notify.getUserId()) {
						notifyUser.add(user);
					}
				}
			}
			userList.removeAll(notifyUser);
			JSONObject json = new JSONObject();
			String userDisplay = "";
			String receiveDispaly = "";
			for (int i = 0; i < userList.size(); i++) {
				SysUserInfo value = (SysUserInfo) userList.get(i);
				userDisplay += value.getId() + "|" + value.getAccount() + ":"
						+ value.getUserName();
				if (i < userList.size() - 1) {
					userDisplay += ";";
				}
			}
			for (int i = 0; i < notifyUser.size(); i++) {
				SysUserInfo value = (SysUserInfo) notifyUser.get(i);
				receiveDispaly += value.getId() + "|" + value.getAccount()
						+ ":" + value.getUserName();
				if (i < notifyUser.size() - 1) {
					receiveDispaly += ";";
				}
			}
			json.put("userList", userDisplay);
			json.put("receiveList", receiveDispaly);
			System.out.println(json.toString());
			pw.println(json.toString());
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
