package com.secpro.platform.monitoring.manage.dwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.entity.SysEvent;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.services.SysEventService;
import com.secpro.platform.monitoring.manage.services.SysResObjService;

@Service("EventAjax")
public class EventAjax {
	private SysEventService service;
	private SysResObjService ses;
	
	
	
	public SysResObjService getSes() {
		return ses;
	}
	@Resource(name="SysResObjServiceImpl")
	public void setSes(SysResObjService ses) {
		this.ses = ses;
	}
	public SysEventService getService() {
		return service;
	}
	@Resource(name="SysEventServiceImpl")
	public void setService(SysEventService service) {
		this.service = service;
	}
	//获取全部告警
	public String getNewEventList() {
		List newEventList=service.queryAll("from SysEvent s order by s.cdate desc");
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		int counter = 0;
		if(newEventList==null){
			return "";
		}
		for(int i=0;i<newEventList.size();i++){
			SysEvent se=(SysEvent)newEventList.get(i);
			if (counter <=100) {
				
				String time="";
				try {
					time = formatter.format(formatter1.parse(se.getCdate()));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = "<div id=a" + se.getId()
						+ " ><a href='#' onClick='confirmEvent("
						+ se.getId() + ")'>";
				
				str += "&nbsp;&nbsp;";
				if(se.getConfirmUser()!=null){
					str+="未清除";
				}else{
					str+="未确认";
				}
				str += "&nbsp;&nbsp;";
				// 级别 normal:#39FB31 minor:#C8FF00 warning:#FFD200 major:#FF9200
				// critical:#FF1042
				switch (se.getEventLevel()) {
				case 1:
					str += "<font color='#C8FF00'>";
					break;
				case 2:
					str += "<font color='#FFD200'>";
					break;
				case 3:
					str += "<font color='#FF9200'>";
					break;
				case 4:
					str += "<font color='#FF1042'>";
					break;
				}
				str += this.getLevelName(se.getEventLevel())
						+ "</font>";
				str += "&nbsp;&nbsp;";
				str += time;
				str += "&nbsp;&nbsp;";
				
				String resName =this.getResName(se.getResId());
				
				str+=resName+"&nbsp;&nbsp;";
				str += se.getMessage();
				str += "</a><br></div>";
				buffer.append(str);
			}
			counter++;
			
		}
		System.out.println(counter+"|"+buffer.toString());
		return counter+"|"+buffer.toString();
	}
	private String getLevelName(int level){
		String levelName="";
		switch (level){
		case 1:
			levelName="<font color='#C8FF00'>通知</font>";
			break;
		case 2:
			levelName="<font color='#FFD200'>轻微</font>";
			break;
		case 3:
			levelName="<font color='#FF9200'>重要</font>";
			break;
		case 4:
			levelName="<font color='#FF1042'>紧急</font>";
			break;
		}
		return levelName;
	}
	//按级别获取全部告警
	public String getNewEventListByLevel(String level){
		String[] levels=level.split(",");
		String hql="from SysEvent s where ";
		for(int i=0;i<levels.length;i++){
			if(levels[i]!=null&&!levels[i].equals("")){
				if(i==1){
					hql+=" s.eventLevel="+levels[i];
				}else{
					hql+=" or s.eventLevel="+levels[i];
				}
			}
		}
		hql+=" order by s.cdate desc";
		List newEventList=service.queryAll(hql);
			StringBuffer buffer = new StringBuffer();
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		int counter = 0;
		if(newEventList==null){
			return "";
		}
		for(int i=0;i<newEventList.size();i++){
			SysEvent se=(SysEvent)newEventList.get(i);
			if (counter <=100) {
				
				String time="";
				try {
					time = formatter.format(formatter1.parse(se.getCdate()));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = "<div id=a" + se.getId()
						+ " ><a href='#' onClick='confirmEvent("
						+ se.getId() + ")'>";
				
				str += "&nbsp;&nbsp;";
				if(se.getConfirmUser()!=null){
					str+="未清除";
				}else{
					str+="未确认";
				}
				str += "&nbsp;&nbsp;";
				// 级别 normal:#39FB31 minor:#C8FF00 warning:#FFD200 major:#FF9200
				// critical:#FF1042
				switch (se.getEventLevel()) {
				case 1:
					str += "<font color='#C8FF00'>";
					break;
				case 2:
					str += "<font color='#FFD200'>";
					break;
				case 3:
					str += "<font color='#FF9200'>";
					break;
				case 4:
					str += "<font color='#FF1042'>";
					break;
				}
				str += this.getLevelName(se.getEventLevel())
						+ "</font>";
				str += "&nbsp;&nbsp;";
				str += time;
				str += "&nbsp;&nbsp;";
				
				String resName =this.getResName(se.getResId());
				
				str+=resName+"&nbsp;&nbsp;";
				str += se.getMessage();
				str += "</a><br></div>";
				buffer.append(str);
			}
			counter++;
			
		}
		System.out.println(counter+"|"+buffer.toString());
		return counter+"|"+buffer.toString();
	}
	private String getResName(Long resId){
		List res= ses.queryAll("from SysResObj s where s.id="+resId);
		SysResObj reo= (SysResObj)res.get(0);
		if(reo!=null){
			return reo.getResName();
		}
		return "";
	}
	public String getNewEventListByType(String typeName){
		List newEventList=service.queryAll("select s from SysEvent as s, EventType as t where s.eventTypeId=t.id and t.eventTypeName like '"+typeName+"%' order by s.cdate desc");
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		int counter = 0;
		if(newEventList==null){
			return "";
		}
		for(int i=0;i<newEventList.size();i++){
			SysEvent se=(SysEvent)newEventList.get(i);
			if (counter <=100) {
				
				String time="";
				try {
					time = formatter.format(formatter1.parse(se.getCdate()));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = "<div id=a" + se.getId()
						+ " ><a href='#' onClick='confirmEvent("
						+ se.getId() + ")'>";
				
				str += "&nbsp;&nbsp;";
				if(se.getConfirmUser()!=null){
					str+="未清除";
				}else{
					str+="未确认";
				}
				str += "&nbsp;&nbsp;";
				// 级别 normal:#39FB31 minor:#C8FF00 warning:#FFD200 major:#FF9200
				// critical:#FF1042
				switch (se.getEventLevel()) {
				case 1:
					str += "<font color='#C8FF00'>";
					break;
				case 2:
					str += "<font color='#FFD200'>";
					break;
				case 3:
					str += "<font color='#FF9200'>";
					break;
				case 4:
					str += "<font color='#FF1042'>";
					break;
				}
				str += this.getLevelName(se.getEventLevel())
						+ "</font>";
				str += "&nbsp;&nbsp;";
				str += time;
				str += "&nbsp;&nbsp;";
				
				String resName =this.getResName(se.getResId());
				
				str+=resName+"&nbsp;&nbsp;";
				str += se.getMessage();
				str += "</a><br></div>";
				buffer.append(str);
			}
			counter++;
			
		}
		System.out.println(counter+"|"+buffer.toString());
		return counter+"|"+buffer.toString();
	}
	public String getOneTypeEventListByLevel(String level,String typeName){
		String[] levels=level.split(",");
		String hql="select s from SysEvent as s, EventType as t where (";
		for(int i=0;i<levels.length;i++){
			if(levels[i]!=null&&!levels[i].equals("")){
				if(i==1){
					hql+=" s.eventLevel="+levels[i];
				}else{
					hql+=" or s.eventLevel="+levels[i];
				}
			}
		}
		hql+=") and s.eventTypeId=t.id and t.eventTypeName like '"+typeName+"%' order by s.cdate desc";
		List newEventList=service.queryAll(hql);
			StringBuffer buffer = new StringBuffer();
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
		int counter = 0;
		if(newEventList==null){
			return "";
		}
		for(int i=0;i<newEventList.size();i++){
			SysEvent se=(SysEvent)newEventList.get(i);
			if (counter <=100) {
				
				String time="";
				try {
					time = formatter.format(formatter1.parse(se.getCdate()));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String str = "<div id=a" + se.getId()
						+ " ><a href='#' onClick='confirmEvent("
						+ se.getId() + ")'>";
				
				str += "&nbsp;&nbsp;";
				if(se.getConfirmUser()!=null){
					str+="未清除";
				}else{
					str+="未确认";
				}
				str += "&nbsp;&nbsp;";
				// 级别 normal:#39FB31 minor:#C8FF00 warning:#FFD200 major:#FF9200
				// critical:#FF1042
				switch (se.getEventLevel()) {
				case 1:
					str += "<font color='#C8FF00'>";
					break;
				case 2:
					str += "<font color='#FFD200'>";
					break;
				case 3:
					str += "<font color='#FF9200'>";
					break;
				case 4:
					str += "<font color='#FF1042'>";
					break;
				}
				str += this.getLevelName(se.getEventLevel())
						+ "</font>";
				str += "&nbsp;&nbsp;";
				str += time;
				str += "&nbsp;&nbsp;";
				
				String resName =this.getResName(se.getResId());
				
				str+=resName+"&nbsp;&nbsp;";
				str += se.getMessage();
				str += "</a><br></div>";
				buffer.append(str);
			}
			counter++;
			
		}
		System.out.println(counter+"|"+buffer.toString());
		return counter+"|"+buffer.toString();
	}
}