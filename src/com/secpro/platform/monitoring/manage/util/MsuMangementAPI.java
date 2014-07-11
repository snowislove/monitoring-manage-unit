package com.secpro.platform.monitoring.manage.util;

import java.net.URI;
import java.util.HashMap;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.secpro.platform.monitoring.manage.util.httpclient.HttpClient;
import com.secpro.platform.monitoring.manage.util.httpclient.IClientResponseListener;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

public class MsuMangementAPI {
	private final static PlatformLogger theLogger = PlatformLogger.getLogger(MsuMangementAPI.class);
	//
	final public static String OPERATION_TYPE = "operationType";
	final public static String MSU_COMMAND_TASK_ADD = "TOPIC-TASK-ADD";
	final public static String MSU_COMMAND_TASK_UPDATE = "TOPIC-TASK-UPDATE";
	final public static String MSU_COMMAND_TASK_REMOVE = "TOPIC-TASK-REMOVE";

	final public static String MSU_COMMAND_RESOURCE_ADD = "TOPIC-RESOURCE-ADD";
	final public static String MSU_COMMAND_RESOURCE_REMOVE = "TOPIC-RESOURCE-REMOVE";

	final public static String MSU_COMMAND_SYSLOG_RULE_ADD = "TOPIC-SYSLOG-STANDARD-RULE-ADD";
	final public static String MSU_COMMAND_SYSLOG_RULE_UPDATE = "TOPIC-SYSLOG-STANDARD-RULE-UPDATE";
	final public static String MSU_COMMAND_SYSLOG_RULE_REMOVE = "TOPIC-SYSLOG-STANDARD-RULE-REMOVE";
	
	
	private static MsuMangementAPI msuMangementAPI = null;

	private URI targetURI = null;

	private MsuMangementAPI() {
	}

	public static MsuMangementAPI getInstance() {
		if (msuMangementAPI == null) {
			msuMangementAPI = new MsuMangementAPI();

		}
		return msuMangementAPI;
	}

	/**
	 * @param publishTask
	 * @param opeationType
	 */
	public void publishMUSTaskToMSU(String publishTask, String opeationType) {
		if (Assert.isEmptyString(publishTask) == true || Assert.isEmptyString(opeationType) == true) {
			return;
		}
		try {
			if (targetURI == null) {
				targetURI = new URI(ApplicationConfiguration.MSU_MANAGE_TASK_BEACON_INTERFACE);
			}
			HashMap<String, String> headerParameterMap = new HashMap<String, String>();
			headerParameterMap.put(OPERATION_TYPE, opeationType);
			IClientResponseListener responseListener = new IClientResponseListener() {

				public void fireSucceed(Object messageObj) throws Exception {
					theLogger.info(messageObj.toString());
				}

				public void fireError(Object messageObj) throws Exception {
					theLogger.error(messageObj.toString());
				}

			};
			// String
			// testContent={"isrt":false,"mda":"{\"username\":\"baiyanwei\"}","con":"ls","cat":1386066691274,"rid":1,"reg":"0311","ope":"ssh","sch":"0 10 * * * ?","tid":"0311-0-SSH-513B4E8CFEB2418895DD972C54580467"};
			HttpClient httpClient = new HttpClient(targetURI, HttpMethod.POST, headerParameterMap, publishTask, responseListener);
			httpClient.start();
		} catch (Exception e) {
			theLogger.exception(e);
		}
	}

	/**
	 * send SYSLOG RULE TO MSU
	 * 
	 * @param syslogRule
	 * @param opeationType
	 */
	public void publishSysLogRuleToMSU(String typeCode, String opeationType) {
		publishMUSTaskToMSU(typeCode, opeationType);
	}

	public static void main(String[] args) {
		try {
			URI targetURI = new URI("http://192.168.18.66:8902/msu/manage");
			// URI targetURI = new URI("http://www.baidu.com");
			HashMap<String, String> headerParameterMap = new HashMap<String, String>();
			// headerParameterMap.put(OPERATION_TYPE, "TOPIC-TASK-ADD");
			// headerParameterMap.put(OPERATION_TYPE,
			// MSU_COMMAND_SYSLOG_RULE_REMOVE);
			headerParameterMap.put(OPERATION_TYPE, MSU_COMMAND_RESOURCE_ADD);
			IClientResponseListener responseListener = new IClientResponseListener() {

				public void fireSucceed(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

				public void fireError(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

			};
			// String publishTask =
			// "{\"tpt\":22,\"isrt\":true,\"mda\":{\"username\":\"baiyanwei\",\"password\":\"SELECTFROM\"},\"con\":\"ls\",\"cat\":1386418517950,\"rid\":62,\"reg\":\"1001\",\"ope\":\"ssh\",\"sch\":\"0 10 * * * ?\",\"tid\":\"1001-0-SSH-3274437D5AB044D9AC6554731BEA32AB\",\"tip\":\"192.168.18.66\"}";
			// String publishTask =
			// "{\"tpt\":22,\"isrt\":false,\"mda\":{\"username\":\"234\",\"password\":\"234\"},\"con\":\"ls\",\"cat\":1386418517950,\"rid\":62,\"reg\":\"1001\",\"ope\":\"ssh\",\"sch\":\"0 10 * * * ?\",\"tid\":\"1001-0-SSH-9374437D5AB044D9AC6554731BEA32AB\",\"tip\":\"192.168.18.66\"}";
			// ip#task_region#type_code
			String publishTask = "10.0.0.2#5000#1001";
			// String publishTask = "10.0.0.1,102.2.2.2";
			// String publishTask ="1001";
			System.out.println(">>" + publishTask);
			HttpClient httpClient = new HttpClient(targetURI, HttpMethod.POST, headerParameterMap, publishTask, responseListener);
			httpClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
