package com.secpro.platform.monitoring.manage.topology.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secpro.platform.monitoring.manage.dao.TopologyDao;
import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.SpringBeanUtile;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * <p>
 * Description: 拓扑数据提供者
 * </p>
 * 
 * @author baiyanwei
 * @version
 * 
 *          <p>
 *          History:
 * 
 *          Date Author Version Description
 *          --------------------------------------
 *          ------------------------------------------- 2012-12-28 下午2:53:22
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class ResourceProvider {
	final private static PlatformLogger logger = PlatformLogger.getLogger(TopologyAdapter.class);

	public static int MAX_QUERY_PAGE_SIZE = 3000;
	private static ResourceProvider resourceProvider = null;
	private TopologyDao topologyDao = null;

	public static ResourceProvider getInstance() {
		if (resourceProvider == null) {
			resourceProvider = new ResourceProvider();
			resourceProvider.lookupDBOperationDao();
		}
		return resourceProvider;
	}

	private ResourceProvider() {

	}

	private void lookupDBOperationDao() {
		topologyDao = SpringBeanUtile.getSpringBean(TopologyDao.class, "TopologyDaoImpl");
	}

	/**
	 * 根据ID取得资源对象
	 * 
	 * @param nodeID
	 * @return
	 */
	public JSONObject getCityObjByCityCode(String cityCode) {
		if (Assert.isEmptyString(cityCode) == true) {
			return null;
		}
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT C1.ID,");
		querySQL.append("       C1.CITY_NAME,");
		querySQL.append("       C1.CITY_CODE,");
		querySQL.append("       C1.CITY_LEVEL,");
		querySQL.append("       C1.PARENT_CODE,");
		querySQL.append("       C2.CITY_NAME AS PARENT_NAME");
		querySQL.append("  FROM (SELECT ID, CITY_NAME, CITY_CODE, CITY_LEVEL, PARENT_CODE");
		querySQL.append("          FROM SYS_CITY C");
		querySQL.append("         WHERE C.CITY_CODE = '").append(cityCode).append("') C1");
		querySQL.append("  LEFT JOIN (SELECT CITY_NAME, CITY_CODE FROM SYS_CITY C2) C2");
		querySQL.append("    ON C1.PARENT_CODE = C2.CITY_CODE");
		//
		String[] keyNames = new String[] { "ID", "CITY_NAME", "CITY_CODE", "CITY_LEVEL", "PARENT_CODE","PARENT_NAME"};
		// package
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, new String[] { "CITY_CODE", "CITY_NAME", TopologyAdapter.NodeType.CITY_NODE }, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return null;
		}
		return rowList.get(0);
	}

	public HashMap<String, JSONObject> getALLCityResourceMap() {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT T.ID, T.CITY_NAME, T.CITY_CODE, T.CITY_LEVEL, T.PARENT_CODE");
		querySQL.append("  FROM SYS_CITY T");
		querySQL.append(" WHERE T.CITY_CODE IS NOT NULL");
		//
		String[] keyNames = new String[] { "ID", "CITY_NAME", "CITY_CODE", "CITY_LEVEL", "PARENT_CODE" };
		//
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, new String[] { "CITY_CODE", "CITY_NAME", TopologyAdapter.NodeType.CITY_NODE }, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return new HashMap<String, JSONObject>();
		}
		HashMap<String, JSONObject> rowMap = new HashMap<String, JSONObject>();
		// package
		for (int i = 0; i < rowList.size(); i++) {
			try {
				rowMap.put(rowList.get(i).getString("CITY_CODE"), rowList.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return rowMap;
	}

	public List<JSONObject> getFWResourceListByDialing(String ip) {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT T1.RES_NAME,");
		querySQL.append("       T1.RES_DESC,");
		querySQL.append("       T1.RES_IP,");
		querySQL.append("       T1.RES_PAUSED,");
		querySQL.append("       T1.CITY_CODE,");
		querySQL.append("       T1.COMPANY_CODE,");
		querySQL.append("       T1.TYPE_CODE,");
		querySQL.append("       T2.COMPANY_NAME,");
		querySQL.append("       T3.TYPE_NAME,");
		querySQL.append("       T4.CITY_NAME,");
		querySQL.append("       T1.ID");
		querySQL.append("  FROM (SELECT ID,");
		querySQL.append("               R.RES_NAME,");
		querySQL.append("               R.RES_DESC,");
		querySQL.append("               R.RES_IP,");
		querySQL.append("               R.RES_PAUSED,");
		querySQL.append("               R.CITY_CODE,");
		querySQL.append("               R.COMPANY_CODE,");
		querySQL.append("               R.TYPE_CODE");
		querySQL.append("          FROM SYS_RES_OBJ R");
		querySQL.append("         WHERE R.CITY_CODE IS NOT NULL");
		querySQL.append("           AND R.CLASS_ID = 1) T1");
		querySQL.append("  LEFT JOIN (SELECT C.COMPANY_NAME, C.COMPANY_CODE");
		querySQL.append("               FROM SYS_DEV_COMPANY C");
		querySQL.append("              WHERE C.COMPANY_CODE IS NOT NULL) T2");
		querySQL.append("    ON T1.COMPANY_CODE = T2.COMPANY_CODE");
		querySQL.append("  LEFT JOIN (SELECT T.TYPE_NAME, T.TYPE_CODE");
		querySQL.append("               FROM SYS_DEV_TYPE T");
		querySQL.append("              WHERE T.TYPE_CODE IS NOT NULL) T3");
		querySQL.append("    ON T1.TYPE_CODE = T3.TYPE_CODE");
		querySQL.append("  LEFT JOIN (SELECT SC.CITY_CODE, SC.CITY_NAME");
		querySQL.append("               FROM SYS_CITY SC");
		querySQL.append("              WHERE SC.CITY_CODE IS NOT NULL) T4");
		querySQL.append("    ON T4.CITY_CODE = T1.CITY_CODE");
		//
		String[] keyNames = new String[] { "RES_NAME", "RES_DESC", "RES_IP", "RES_PAUSED", "CITY_CODE", "COMPANY_CODE", "TYPE_CODE", "COMPANY_NAME", "TYPE_NAME", "CITY_NAME", "ID" };
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, new String[] { "ID", "RES_NAME", TopologyAdapter.NodeType.FIREWALL_NODE }, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return new ArrayList<JSONObject>();
		}
		// List<JSONObject> rowMap = new ArrayList<JSONObject>();
		// // package
		// for (int i = 0; i < rowList.size(); i++) {
		// try {
		// if (rowMap.containsKey(rowList.get(i).getString("CITY_CODE")) ==
		// true) {
		// List<JSONObject> cityFWList =
		// rowMap.get(rowList.get(i).getString("CITY_CODE"));
		// cityFWList.add(rowList.get(i));
		// } else {
		// ArrayList<JSONObject> cityFWList = new ArrayList<JSONObject>();
		// cityFWList.add(rowList.get(i));
		// rowMap.put(rowList.get(i).getString("CITY_CODE"), cityFWList);
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		return rowList;
	}

	/**
	 * get all FireWall resource.
	 * 
	 * @return
	 */
	public HashMap<String, List<JSONObject>> getFirewallAllResourceMap() {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT T1.RES_NAME,");
		querySQL.append("       T1.RES_DESC,");
		querySQL.append("       T1.RES_IP,");
		querySQL.append("       T1.RES_PAUSED,");
		querySQL.append("       T1.CITY_CODE,");
		querySQL.append("       T1.COMPANY_CODE,");
		querySQL.append("       T1.TYPE_CODE,");
		querySQL.append("       T2.COMPANY_NAME,");
		querySQL.append("       T3.TYPE_NAME,");
		querySQL.append("       T4.CITY_NAME,");
		querySQL.append("       T1.ID");
		querySQL.append("  FROM (SELECT ID,");
		querySQL.append("               R.RES_NAME,");
		querySQL.append("               R.RES_DESC,");
		querySQL.append("               R.RES_IP,");
		querySQL.append("               R.RES_PAUSED,");
		querySQL.append("               R.CITY_CODE,");
		querySQL.append("               R.COMPANY_CODE,");
		querySQL.append("               R.TYPE_CODE");
		querySQL.append("          FROM SYS_RES_OBJ R");
		querySQL.append("         WHERE R.CITY_CODE IS NOT NULL");
		querySQL.append("           AND R.CLASS_ID = 1) T1");
		querySQL.append("  LEFT JOIN (SELECT C.COMPANY_NAME, C.COMPANY_CODE");
		querySQL.append("               FROM SYS_DEV_COMPANY C");
		querySQL.append("              WHERE C.COMPANY_CODE IS NOT NULL) T2");
		querySQL.append("    ON T1.COMPANY_CODE = T2.COMPANY_CODE");
		querySQL.append("  LEFT JOIN (SELECT T.TYPE_NAME, T.TYPE_CODE");
		querySQL.append("               FROM SYS_DEV_TYPE T");
		querySQL.append("              WHERE T.TYPE_CODE IS NOT NULL) T3");
		querySQL.append("    ON T1.TYPE_CODE = T3.TYPE_CODE");
		querySQL.append("  LEFT JOIN (SELECT SC.CITY_CODE, SC.CITY_NAME");
		querySQL.append("               FROM SYS_CITY SC");
		querySQL.append("              WHERE SC.CITY_CODE IS NOT NULL) T4");
		querySQL.append("    ON T4.CITY_CODE = T1.CITY_CODE");
		//
		String[] keyNames = new String[] { "RES_NAME", "RES_DESC", "RES_IP", "RES_PAUSED", "CITY_CODE", "COMPANY_CODE", "TYPE_CODE", "COMPANY_NAME", "TYPE_NAME", "CITY_NAME", "ID" };
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, new String[] { "ID", "RES_NAME", TopologyAdapter.NodeType.FIREWALL_NODE }, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return new HashMap<String, List<JSONObject>>();
		}
		HashMap<String, List<JSONObject>> rowMap = new HashMap<String, List<JSONObject>>();
		// package
		for (int i = 0; i < rowList.size(); i++) {
			try {
				if (rowMap.containsKey(rowList.get(i).getString("CITY_CODE")) == true) {
					List<JSONObject> cityFWList = rowMap.get(rowList.get(i).getString("CITY_CODE"));
					cityFWList.add(rowList.get(i));
				} else {
					ArrayList<JSONObject> cityFWList = new ArrayList<JSONObject>();
					cityFWList.add(rowList.get(i));
					rowMap.put(rowList.get(i).getString("CITY_CODE"), cityFWList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return rowMap;
	}

	public JSONObject getFirewallResourceById(String nodeId) {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT T1.RES_NAME,");
		querySQL.append("       T1.RES_DESC,");
		querySQL.append("       T1.RES_IP,");
		querySQL.append("       T1.RES_PAUSED,");
		querySQL.append("       T1.CITY_CODE,");
		querySQL.append("       T1.COMPANY_CODE,");
		querySQL.append("       T1.TYPE_CODE,");
		querySQL.append("       T2.COMPANY_NAME,");
		querySQL.append("       T3.TYPE_NAME,");
		querySQL.append("       T4.CITY_NAME,");
		querySQL.append("       T1.ID");
		querySQL.append("  FROM (SELECT ID,");
		querySQL.append("               R.RES_NAME,");
		querySQL.append("               R.RES_DESC,");
		querySQL.append("               R.RES_IP,");
		querySQL.append("               R.RES_PAUSED,");
		querySQL.append("               R.CITY_CODE,");
		querySQL.append("               R.COMPANY_CODE,");
		querySQL.append("               R.TYPE_CODE");
		querySQL.append("          FROM SYS_RES_OBJ R");
		querySQL.append("         WHERE R.CITY_CODE IS NOT NULL");
		querySQL.append("           AND R.CLASS_ID = 1");
		querySQL.append(" AND R.ID=").append(nodeId);
		querySQL.append(") T1");
		querySQL.append("  LEFT JOIN (SELECT C.COMPANY_NAME, C.COMPANY_CODE");
		querySQL.append("               FROM SYS_DEV_COMPANY C");
		querySQL.append("              WHERE C.COMPANY_CODE IS NOT NULL) T2");
		querySQL.append("    ON T1.COMPANY_CODE = T2.COMPANY_CODE");
		querySQL.append("  LEFT JOIN (SELECT T.TYPE_NAME, T.TYPE_CODE");
		querySQL.append("               FROM SYS_DEV_TYPE T");
		querySQL.append("              WHERE T.TYPE_CODE IS NOT NULL) T3");
		querySQL.append("    ON T1.TYPE_CODE = T3.TYPE_CODE");
		querySQL.append("  LEFT JOIN (SELECT SC.CITY_CODE, SC.CITY_NAME");
		querySQL.append("               FROM SYS_CITY SC");
		querySQL.append("              WHERE SC.CITY_CODE IS NOT NULL) T4");
		querySQL.append("    ON T4.CITY_CODE = T1.CITY_CODE");
		//
		String[] keyNames = new String[] { "RES_NAME", "RES_DESC", "RES_IP", "RES_PAUSED", "CITY_CODE", "COMPANY_CODE", "TYPE_CODE", "COMPANY_NAME", "TYPE_NAME", "CITY_NAME", "ID" };
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, new String[] { "ID", "RES_NAME", TopologyAdapter.NodeType.FIREWALL_NODE }, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return null;
		} else {
			return rowList.get(0);
		}
	}

	/**
	 * 取得两个资源之间的关系
	 * 
	 * @param resourceA
	 * @param resourceB
	 * @return
	 */
	public List<Integer> getRelation(JSONObject resourceA, JSONObject resourceB) {
		if (resourceA == null || resourceB == null) {
			return null;
		}
		List<Integer> relationList = new ArrayList<Integer>();
		relationList.add(RelationConstants.FILIATION);
		return relationList;
	}

	/**
	 * 取得 userTarget 对象
	 * 
	 * @param resObj
	 * @return
	 */
	public JSONObject getResourceUserTargetObject(JSONObject resObj) {
		/*
		 * { "t":"resource type", "l":"label name",
		 * "id":"node ID","i":"node image" }
		 */
		JSONObject userTarget = new JSONObject();
		if (resObj == null) {
			return userTarget;
		}
		try {
			userTarget.put("t", resObj.getString(TopologyNode.NODE_TYPE_NAME));
			userTarget.put("l", resObj.getString(TopologyNode.NODE_LABEL_NAME));
			userTarget.put("id", resObj.getString(TopologyNode.NODE_ID_NAME));
			if (Assert.isEmptyString(resObj.getString(TopologyNode.NODE_TYPE_NAME)) == false) {
				userTarget.put("i", "/mmu/topology/images/node/" + resObj.getString(TopologyNode.NODE_TYPE_NAME) + ".png");
			} else {
				userTarget.put("i", "/mmu/topology/images/node/node.png");
			}
			if (resObj.getString(TopologyNode.NODE_TYPE_NAME).equals(TopologyAdapter.NodeType.FIREWALL_NODE)) {
				userTarget.put("status", resObj.getString("RES_PAUSED"));
			}
		} catch (JSONException e) {
			logger.exception(e);
		}
		// image 3 type
		// #1http://x.x.x.x/application/image/xx.png
		// #2/unionmon/image/x.png
		// #3node.png
		return userTarget;
	}

	/**
	 * 取得一个资源组的USERTARGET
	 * 
	 * @param groupName
	 * @param resourceType
	 * @param resourcesList
	 * @return
	 */
	public JSONObject getGourpUserTargetObject(String groupName, int resourceType, List<JSONObject> resourcesList) {
		/*
		 * { "t":"resource type", "g":"label name",ids":" all resource id "}
		 */
		JSONObject userTarget = new JSONObject();
		if (resourcesList == null || groupName == null || groupName.length() == 0 || resourcesList.isEmpty()) {
			return userTarget;
		}
		try {
			userTarget.put("g", groupName);
			userTarget.put("t", String.valueOf(resourceType));
			JSONArray idsArray = new JSONArray();
			for (int i = 0; i < resourcesList.size(); i++) {
				if (resourcesList.get(i) == null) {
					continue;
				}
				// idsArray.add(String.valueOf(resourcesList.get(i).getResValue().getId()));
			}
			userTarget.put("ids", idsArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userTarget;
	}

	/**
	 * 取得关系的UserTarget数据
	 * 
	 * @param sObj
	 * @param eObj
	 * @param raltionType
	 * @return
	 */
	public JSONObject getRelationUserTargetObject(JSONObject sObj, JSONObject eObj, int raltionType) {
		/*
		 * { "s":"start resource id",
		 * "t":"relation type id","e":"end resource id"}
		 */
		JSONObject userTarget = new JSONObject();
		if (sObj == null || sObj == null) {
			return userTarget;
		}
		try {
			userTarget.put("s", sObj.getString(TopologyNode.NODE_ID_NAME));
			userTarget.put("t", String.valueOf(raltionType));
			userTarget.put("e", eObj.getString(TopologyNode.NODE_ID_NAME));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return userTarget;

	}

	/**
	 * 创建关系的USERTARGET
	 * 
	 * @param sObj
	 * @param eGroupName
	 * @param raltionType
	 * @return
	 */
	public JSONObject getRelationGroupUserTarget(SysResObj sObj, String eGroupName, int raltionType) {
		/*
		 * { "s":"start resource id", "t":"relation type id","g":"group name"}
		 */
		JSONObject userTarget = new JSONObject();
		if (sObj == null || eGroupName == null || eGroupName.length() == 0) {
			return userTarget;
		}
		// userTarget.put("s",
		// String.valueOf(sObj.getResValue().getResTypeId()));
		// userTarget.put("t", String.valueOf(raltionType));
		// userTarget.put("g", eGroupName);

		return userTarget;

	}

	/**
	 * 取得最新事件
	 * 
	 * @param resourceNode
	 * @param pageNo
	 * @return
	 */
	public List<JSONObject> getEventList(String[] resIds) {
		List<JSONObject> eventList = new ArrayList<JSONObject>();
		if (resIds == null || resIds.length == 0) {
			return eventList;
		}
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT E.ID, E.EVENT_LEVEL, E.MESSAGE, E.CDATE");
		querySQL.append("  FROM SYS_EVENT E");
		querySQL.append(" WHERE E.RES_ID IN (");

		for (int i = 0; i < resIds.length && i < 2000; i++) {
			querySQL.append(resIds[i]);
			if ((i + 1) < resIds.length) {
				querySQL.append(",");
			}
		}
		querySQL.append(") AND (E.CONFIRM_USER IS NULL OR E.CONFIRM_USER = '')");
		querySQL.append(" ORDER BY E.EVENT_LEVEL DESC, E.CDATE DESC");
		String[] keyNames = new String[] { "ID", "EVENT_LEVEL", "MESSAGE", "CDATE" };
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, null, querySQL.toString());
		if (rowList != null) {
			eventList.addAll(rowList);
		}
		return eventList;
	}

	/**
	 * 取得资源的状态信息,以事件级别最高的为准
	 * 
	 * @param resIds
	 * @return
	 */
	public JSONObject getResourceEventStatusByNodeIDs(String resIds) {
		if (Assert.isEmptyString(resIds) == true) {
			return new JSONObject();
		}
		String nodeIds = resIds;
		if (nodeIds.endsWith(",") == true) {
			nodeIds = nodeIds.substring(0, nodeIds.length() - 1);
		}
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT E.RES_ID, MAX(E.EVENT_LEVEL) AS MAX_EVENT_LEVEL");
		querySQL.append("  FROM SYS_EVENT E");
		querySQL.append(" WHERE E.RES_ID IN (");
		querySQL.append(nodeIds);
		querySQL.append(") AND (E.CONFIRM_USER IS NULL OR E.CONFIRM_USER = '')");
		querySQL.append(" GROUP BY E.RES_ID ORDER BY E.RES_ID");
		String[] keyNames = new String[] { "RES_ID", "MAX_EVENT_LEVEL" };
		JSONObject statusObj = new JSONObject();
		List<JSONObject> rowList = queryResourcePackageInJSON(keyNames, null, querySQL.toString());
		if (Assert.isEmptyCollection(rowList) == true) {
			return statusObj;
		}
		for (int i = 0; i < rowList.size(); i++) {
			try {
				statusObj.put(rowList.get(i).getString("RES_ID"), rowList.get(i).getString("MAX_EVENT_LEVEL"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return statusObj;
	}

	/**
	 * 健康度描述
	 * 
	 * @param status
	 * @return
	 */
	public String getUsableStatusDescr(String status) {
		if (status == null || status.length() == 0) {
			return "";
		}
		if (status.equals("1")) {
			return "正常";
		} else if (status.equals("2")) {
			return "轻微";
		} else if (status.equals("3")) {
			return "一般";
		} else if (status.equals("4")) {
			return "重要";
		} else if (status.equals("5")) {
			return "紧急";
		}
		return "未定义";
	}

	/**
	 * 监控状态描述
	 * 
	 * @param status
	 * @return
	 */
	public String getMonitoringStatusDescr(int status) {
		if (status != 1) {
			return "正常";
		} else {
			return "暂停";
		}
	}

	private List<JSONObject> queryResourcePackageInJSON(String[] keyNames, String[] nodeDefines, String querySQL) {
		List<Object> rowList = this.topologyDao.queryBySql(querySQL.toString(), MAX_QUERY_PAGE_SIZE, 1);
		if (Assert.isEmptyCollection(rowList) == true) {
			return null;
		}
		// package
		List<JSONObject> nodeList = new ArrayList<JSONObject>();
		if (Assert.isEmptyCollection(rowList) == true || keyNames == null || keyNames.length == 0) {
			return nodeList;
		}
		for (int i = 0; i < rowList.size(); i++) {
			if (rowList.get(i) == null) {
				continue;
			}
			try {
				nodeList.add(TopologyNode.getJSONObjectByRowData(keyNames, nodeDefines, (Object[]) rowList.get(i)));
			} catch (Exception e) {
				logger.exception(e);
			}

		}
		return nodeList;
	}
}
