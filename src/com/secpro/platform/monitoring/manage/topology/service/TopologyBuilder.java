package com.secpro.platform.monitoring.manage.topology.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import y.base.DataMap;
import y.base.Node;

import com.secpro.platform.monitoring.manage.topology.service.TopologyAdapter.NodeType;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;

/**
 * <p>
 * Description: 拓扑构建器
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
 *          ------------------------------------------- 2012-12-28 下午3:46:48
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyBuilder {
	final static private PlatformLogger logger = PlatformLogger.getLogger(TopologyAdapter.class);
	private static TopologyBuilder topologyBuilder = null;
	private ResourceProvider resourceProvider = null;
	private TopologyPainter topologyPainter = null;

	public static TopologyBuilder getInstance() {
		if (topologyBuilder == null) {
			topologyBuilder = new TopologyBuilder();
			topologyBuilder.resourceProvider = ResourceProvider.getInstance();
			topologyBuilder.topologyPainter = TopologyPainter.getInstance();
		}
		return topologyBuilder;
	}

	private TopologyBuilder() {

	}

	/**
	 * 创建一个基于实体节点的拓扑图
	 * 
	 * @param graph
	 * @param referentMap
	 */
	public void buildNodeTopology(StyledLayoutGraph graph, HashMap<String, String> referentMap) {
		logger.info("start build the hole topology graph ");
		// 实体节点出拓扑图时，按实现节点的所有关系都出来
		if (graph == null || referentMap == null) {
			return;
		}
		// 找到基于当前资源的所有关系
		HashMap<String, JSONObject> cityDataMap = resourceProvider.getALLCityResourceMap();
		if (cityDataMap == null) {
			return;
		}
		// 找到FW资源
		HashMap<String, List<JSONObject>> fwDataMap = resourceProvider.getFirewallAllResourceMap();
		// 全国CITY_CODE从1开始
		JSONObject currentResource = resourceProvider.getCityObjByCityCode("1");
		if (currentResource == null) {
			return;
		}
		// 构建当前全国节点到图上
		Node currentNode = topologyPainter.createNodeByResource(graph, currentResource);
		if (currentNode == null) {
			return;
		}
		// NODE与资源的对照
		HashMap<JSONObject, Node> graphNodeMap = new HashMap<JSONObject, Node>();
		// 当前资源
		graphNodeMap.put(currentResource, currentNode);
		// 构建全国节点的下层节点.
		buildSonNodeOnTopology(10000, graph, currentResource, currentNode, graphNodeMap, cityDataMap, fwDataMap);
		//
	}

	/**
	 * 构建连通性测试
	 * 
	 * @param graph
	 * @param referentMap
	 */
	public void buildDialingTopology(StyledLayoutGraph graph, HashMap<String, String> referentMap) {
		logger.info("start build the hole topology graph ");
		// 实体节点出拓扑图时，按实现节点的所有关系都出来
		if (graph == null || referentMap == null) {
			return;
		}
		String startIp = referentMap.get("startIp");
		if (Assert.isEmptyString(startIp) == true) {
			return;
		}
		String endIp = referentMap.get("endIp");
		if (Assert.isEmptyString(endIp) == true) {
			return;
		}
		// 找到基于当前资源的所有关系
		List<JSONObject> startIpFWList = resourceProvider.getFWResourceListByDialing(startIp);
		if (Assert.isEmptyCollection(startIpFWList) == true) {
			return;
		}

		List<JSONObject> endIpFWList = resourceProvider.getFWResourceListByDialing(endIp);
		if (Assert.isEmptyCollection(endIpFWList) == true) {
			return;
		}
		// NODE与资源的对照
		HashMap<JSONObject, Node> graphNodeMap = new HashMap<JSONObject, Node>();
		//
		// 构建当前全国节点到图上
		Node startIpNode = this.buildLineNodeOnTopology(graph, startIpFWList, graphNodeMap);
		if (startIpNode == null) {
			return;
		}
		Node endIpNode = this.buildLineNodeOnTopology(graph, endIpFWList, graphNodeMap);
		if (endIpNode == null) {
			return;
		}
		//
		topologyPainter.createEdgeWithRelation(graph, startIpNode, endIpNode, startIpFWList.get(startIpFWList.size() - 1), endIpFWList.get(endIpFWList.size() - 1),
				RelationConstants.FILIATION);
	}

	/**
	 * 构建一个基于类别节点的拓扑图
	 * 
	 * @param graph
	 * @param referentMap
	 */
	private void buildSonNodeOnTopology(int maxLoop, StyledLayoutGraph graph, JSONObject currentResource, Node currentNode, HashMap<JSONObject, Node> graphNodeMap,
			HashMap<String, JSONObject> cityDataMap, HashMap<String, List<JSONObject>> fwDataMap) {
		// #1取得当前资源的一层子城市资源,
		// safe call.
		if (maxLoop <= 0) {
			return;
		}
		List<JSONObject> sonCityList = getSonCityList(currentResource, cityDataMap);

		try {
			// #2构建当前资源下的FW资源.
			List<JSONObject> fwList = fwDataMap.get(currentResource.getString("CITY_CODE"));
			topologyPainter.paintNodeOnGraph(graph, currentNode, "to", currentResource, fwList, RelationConstants.FILIATION, graphNodeMap);
		} catch (JSONException e) {
			logger.exception(e);
		}
		//
		if (Assert.isEmptyCollection(sonCityList) == true) {
			return;
		}
		// 3构建当前资源的子城市资源.
		topologyPainter.paintNodeOnGraph(graph, currentNode, "to", currentResource, sonCityList, RelationConstants.FILIATION, graphNodeMap);
		//
		for (int n = 0; n < sonCityList.size(); n++) {
			Node sonCityNode = graphNodeMap.get(sonCityList.get(n));
			// #4 构建子资源下面的城市资源
			buildSonNodeOnTopology(maxLoop--, graph, sonCityList.get(n), sonCityNode, graphNodeMap, cityDataMap, fwDataMap);
		}

	}

	/**
	 * 根据集合中的资源,将资源一个连一个的画出来.并返回最后一个节点
	 * 
	 * @param graph
	 * @param resourceList
	 * @param graphNodeMap
	 * @return
	 */
	private Node buildLineNodeOnTopology(StyledLayoutGraph graph, List<JSONObject> resourceList, HashMap<JSONObject, Node> graphNodeMap) {
		//

		JSONObject currentResource = null;
		Node currentNode = null;

		JSONObject theLastResource = resourceList.get(0);
		Node theLastNode = topologyPainter.createNodeByResource(graph, theLastResource);
		graphNodeMap.put(theLastResource, theLastNode);
		for (int n = 1; n < resourceList.size(); n++) {
			currentResource = resourceList.get(n);
			if (graphNodeMap.containsKey(currentNode) == true) {
				currentNode = graphNodeMap.get(currentResource);
			} else {
				currentNode = topologyPainter.createNodeByResource(graph, currentResource);
				graphNodeMap.put(currentResource, currentNode);
			}
			//
			topologyPainter.createEdgeWithRelation(graph, theLastNode, currentNode, theLastResource, currentResource, RelationConstants.FILIATION);
			//
			theLastResource = currentResource;
			theLastNode = currentNode;
		}
		return currentNode;
	}

	/**
	 * 取得指定资源的子城市
	 * 
	 * @param currentResource
	 * @param cityDataMap
	 * @return
	 */
	private List<JSONObject> getSonCityList(JSONObject currentResource, HashMap<String, JSONObject> cityDataMap) {
		ArrayList<JSONObject> sonCityList = new ArrayList<JSONObject>();
		try {
			String currentID = currentResource.getString("CITY_CODE");
			JSONObject sonResouce = null;
			for (Iterator<String> keyIter = cityDataMap.keySet().iterator(); keyIter.hasNext();) {
				sonResouce = cityDataMap.get(keyIter.next());
				if (sonResouce.getString("PARENT_CODE").equalsIgnoreCase(currentID) == true) {
					sonCityList.add(sonResouce);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sonCityList;
	}

	/**
	 * 为NODE TOOLTIP加入默认信息
	 * 
	 * @param nodeTipArray
	 * @param currentResource
	 */
	public void buildDefaultNodeTip(ArrayList<ArrayList<Object>> nodeTipDataList, JSONObject currentResource, String nodeType) {
		if (nodeTipDataList == null || currentResource == null) {
			return;
		}
		ArrayList<Object> defaultTipData = new ArrayList<Object>();
		nodeTipDataList.add(defaultTipData);
		// 资源名称，资源IP，采集器，健康度，监控状态
		// 标题
		JSONArray titleObject = new JSONArray();
		titleObject.put("default");
		titleObject.put("基本信息");
		defaultTipData.add(titleObject);

		if (NodeType.CITY_NODE.equalsIgnoreCase(nodeType) == true) {
			try {
				// "CITY_NAME", "CITY_CODE", "CITY_LEVEL", "PARENT_CODE"
				defaultTipData.add("城市名称:" + currentResource.getString("CITY_NAME"));
				defaultTipData.add("城市代码:" + currentResource.getString("CITY_CODE"));
				defaultTipData.add("城市级别:" + currentResource.getString("CITY_LEVEL"));
				defaultTipData.add("上级城市:" + currentResource.getString("PARENT_NAME"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (NodeType.FIREWALL_NODE.equalsIgnoreCase(nodeType) == true) {
			try {
				// "RES_NAME", "RES_DESC", "RES_IP", "RES_PAUSED", "CITY_CODE",
				// "COMPANY_CODE", "TYPE_CODE", "COMPANY_NAME", "TYPE_NAME",
				// "CITY_NAME", "ID"
				defaultTipData.add("防火墙名称:" + currentResource.getString("RES_NAME"));
				defaultTipData.add("描述:" + currentResource.getString("RES_DESC"));
				defaultTipData.add("IP:" + currentResource.getString("RES_IP"));
				// 资源启用状态0：表示启用1：表示停用，默认资源状态为启用
				defaultTipData.add("状态:" + (currentResource.getString("RES_PAUSED").equals("0") ? "启用" : "停用"));
				defaultTipData.add("设备厂商:" + currentResource.getString("COMPANY_NAME"));
				defaultTipData.add("设备类别:" + currentResource.getString("TYPE_NAME"));
				defaultTipData.add("所属城市:" + currentResource.getString("CITY_NAME"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
	}

	/**
	 * 为NODE TOOLTIP加入事件信息
	 * 
	 * @param nodeTipDataList
	 * @param currentResource
	 */
	public void buildNodeTipEvent(ArrayList<ArrayList<Object>> nodeTipDataList, String nodeId, String nodeType) {
		if (Assert.isEmptyCollection(nodeTipDataList) == true) {
			return;
		}
		if (Assert.isEmptyString(nodeId) == true) {
			return;
		}
		if (Assert.isEmptyString(nodeType) == true) {
			return;
		}
		ArrayList<Object> eventTipData = new ArrayList<Object>();
		//
		int lastActionIndex = -1;
		for (int i = 0; i < nodeTipDataList.size(); i++) {
			ArrayList<Object> colList = nodeTipDataList.get(i);
			if (colList == null || colList.isEmpty()) {
				continue;
			}
			if (colList.get(0).toString().startsWith("[\"action\",")) {
				lastActionIndex = i;
			}
		}
		if (lastActionIndex == -1) {
			nodeTipDataList.add(eventTipData);
		} else {
			nodeTipDataList.add(lastActionIndex, eventTipData);
		}
		// 查询最新的事件
		//
		List<JSONObject> eventList = resourceProvider.getEventList(new String[] { nodeId });
		// 标题
		JSONArray titleObject = new JSONArray();
		titleObject.put("event");
		if (eventList == null || eventList.isEmpty() == true) {
			titleObject.put("事件(0)");
			eventTipData.add(titleObject);
			return;
		}
		titleObject.put("事件(" + eventList.size() + ")");
		eventTipData.add(titleObject);
		
		for (int i = 0; i < eventList.size(); i++) {
			try {
				JSONObject eventObj = eventList.get(i);
				// "ID", "EVENT_LEVEL", "MESSAGE", "CDATE
				JSONArray eventLevelArray = new JSONArray();
				eventLevelArray.put("(" + resourceProvider.getUsableStatusDescr(eventObj.getString("EVENT_LEVEL")) + "," + eventObj.getString("CDATE") + ")");
				eventLevelArray.put(eventObj.getString("EVENT_LEVEL"));
				eventTipData.add(eventLevelArray);
				JSONArray eventDetailArray = new JSONArray();
				eventDetailArray.put("\t" + eventObj.getString("MESSAGE"));
				eventDetailArray.put(eventObj.getString("EVENT_LEVEL"));
				eventTipData.add(eventDetailArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 设置拓扑图的附加数据
	 * 
	 * @param graph
	 * @param dataObj
	 */
	public void setGraphAdditionalData(StyledLayoutGraph graph, JSONObject dataObj) {
		if (graph == null || dataObj == null) {
			return;
		}
		DataMap graphDp = (DataMap) graph.getDataProvider("GraphAdditionalDataMap");
		if (graphDp == null) {
			return;
		}
		graphDp.set(graph, dataObj.toString());
	}

	/**
	 * 设置拓扑图的附加数据
	 * 
	 * @param graph
	 * @param dataObj
	 */
	public String getGraphAdditionalData(StyledLayoutGraph graph) {
		if (graph == null) {
			return null;
		}
		DataMap graphDp = (DataMap) graph.getDataProvider("GraphAdditionalDataMap");
		if (graphDp == null) {
			return null;
		}
		Object dataObj = graphDp.get(graph);
		if (dataObj == null) {
			return null;
		}
		return String.valueOf(dataObj);
	}

	/**
	 * 设置拓扑图的背景 图片URL可以有三种 #1 xxx 取默认配置的背景目录下的同名图片 #2 /unionmon/xx/xx/xxx
	 * 应该下的相对路径同名图片 #3 http://x.x.x.x/xx/xxx 全URL路径
	 * 
	 * @param graph
	 * @param imageURL
	 */
	public void setGraphBackGroup(StyledLayoutGraph graph, String imageURL, String width, String height) {
		JSONObject dataObj = new JSONObject();
		// JSONObject backGroundObj = new JSONObject();
		// backGroundObj.put("imageURL", imageURL);
		// backGroundObj.put("width", width);
		// backGroundObj.put("height", height);
		// dataObj.put("graphBackGround", backGroundObj);
		setGraphAdditionalData(graph, dataObj);

	}

	/**
	 * 只设置背景图
	 * 
	 * @param graph
	 * @param imageURL
	 */
	public void setGraphBackGroup(StyledLayoutGraph graph, String imageURL) {
		// JSONObject dataObj = new JSONObject();
		// JSONObject backGroundObj = new JSONObject();
		// backGroundObj.put("imageURL", imageURL);
		// dataObj.put("graphBackGround", backGroundObj);
		// setGraphAdditionalData(graph, dataObj);

	}
}
