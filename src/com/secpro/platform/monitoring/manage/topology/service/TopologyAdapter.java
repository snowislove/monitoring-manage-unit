package com.secpro.platform.monitoring.manage.topology.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;

/**
 * <p>
 * Description: 拓扑图动作适配器
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
 *          ------------------------------------------- 2012-12-19 下午4:13:47
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyAdapter {
	PlatformLogger logger = PlatformLogger.getLogger(TopologyAdapter.class);
	private static TopologyAdapter topologyAdapter = null;
	private TopologyBuilder topologyBuilder = null;
	private TopologyLayoutRender topologyLayoutRender = null;

	public static TopologyAdapter getInstance() {
		if (topologyAdapter == null) {
			topologyAdapter = new TopologyAdapter();
			topologyAdapter.topologyBuilder = TopologyBuilder.getInstance();
			topologyAdapter.topologyLayoutRender = TopologyLayoutRender.getInstance();
		}
		return topologyAdapter;
	}

	private TopologyAdapter() {

	}

	/**
	 * @param graph
	 * @param referentMap
	 *            构建拓扑图
	 */
	public void buildTopology(StyledLayoutGraph graph, HashMap<String, String> referentMap) {
		if (graph == null || referentMap == null) {
			return;
		}
		// 设置一个默认的背景图
		topologyBuilder.setGraphBackGroup(graph, "/mmu/topology/images/background/bg_default.jpg");
		// 构建关系拓扑图，按NODE类别
		//
		topologyBuilder.buildNodeTopology(graph, referentMap);
		//
		// 生成布局
		topologyLayoutRender.renderLayout(graph, referentMap.get("layoutStlye"));
	}

	/**
	 * @param graph
	 * @param referentMap
	 *            构建拓扑图
	 */
	public void buildDialingTopology(StyledLayoutGraph graph, HashMap<String, String> referentMap) {
		if (graph == null || referentMap == null) {
			return;
		}
		// 设置一个默认的背景图
		topologyBuilder.setGraphBackGroup(graph, "/mmu/topology/images/background/bg_default.jpg");
		// 构建关系拓扑图，按NODE类别
		//
		topologyBuilder.buildDialingTopology(graph, referentMap);
		//
		// 生成布局 orthogonal
		topologyLayoutRender.renderLayout(graph, "orthogonal");
	}

	/**
	 * 取得拓扑图中NODE的TIP信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getNodeTooltip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request == null || response == null) {
			return;
		}
		String nodeID = request.getParameter("nodeID");
		if (nodeID == null || nodeID.length() == 0) {
			writeResponseMessage(response, "没有必要的参数nodeID", 500);
			return;
		}
		String nodeType = request.getParameter("nodeType");
		if (nodeType == null || nodeType.length() == 0) {
			writeResponseMessage(response, "没有必要的参数nodeType", 500);
			return;
		}
		JSONObject nodeResource = null;
		// 当前查看的资源节点
		if (NodeType.CITY_NODE.equalsIgnoreCase(nodeType) == true) {
			nodeResource = ResourceProvider.getInstance().getCityObjByCityCode(nodeID);
		} else if (NodeType.FIREWALL_NODE.equalsIgnoreCase(nodeType) == true) {
			nodeResource = ResourceProvider.getInstance().getFirewallResourceById(nodeID);
		} else {
			writeResponseMessage(response, "没有必要的参数nodeType", 500);
			return;
		}
		if (nodeResource == null) {
			writeResponseMessage(response, "ID[" + nodeID + "]对应的资源不存在", 500);
			return;
		}
		// String nodeTypeID =
		// String.valueOf(nodeResource.getResValue().getResTypeId());
		ArrayList<ArrayList<Object>> nodeTipDataList = new ArrayList<ArrayList<Object>>();
		// 处理默认属性
		topologyBuilder.buildDefaultNodeTip(nodeTipDataList, nodeResource, nodeType);
		// 处理定制属性，指标，动作
		/*
		 * ArrayList<String[]> cfgTipList =
		 * TopologyConfiguration.getInstance().getNodeTooltipCfg(nodeTypeID); //
		 * System.out.println("request for node tooltip >> typeID:" + //
		 * nodeTypeID);
		 * 
		 * if (cfgTipList != null && cfgTipList.isEmpty() == false) { for (int i
		 * = 0; i < cfgTipList.size(); i++) { String[] cfgRaw =
		 * cfgTipList.get(i); if (cfgRaw == null) { continue; } // 0 type 1 name
		 * 2 values ArrayList<String> groupTipList =
		 * topologyBuilder.buildNodeTipByGroup(cfgRaw, nodeResource, true); if
		 * (groupTipList == null || groupTipList.isEmpty()) { continue; }
		 * nodeTipDataList.add(groupTipList); } }
		 */
		// 处理TIP中的事件
		topologyBuilder.buildNodeTipEvent(nodeTipDataList, nodeID, nodeType);
		// 找到最长的列
		int maxLen = 0;
		for (int i = 0; i < nodeTipDataList.size(); i++) {
			if (nodeTipDataList.get(i).size() > maxLen) {
				maxLen = nodeTipDataList.get(i).size();
			}
		}
		// 将列数据组成一个表格
		JSONArray nodeTipArray = new JSONArray();
		for (int i = 0; i < maxLen; i++) {
			JSONArray rawArray = new JSONArray();
			for (int n = 0; n < nodeTipDataList.size(); n++) {
				if (i < nodeTipDataList.get(n).size()) {
					rawArray.put(nodeTipDataList.get(n).get(i));
				} else {
					rawArray.put("");
				}
			}
			nodeTipArray.put(rawArray);
		}
		// 写响应
		//System.out.println("TIP:" + nodeTipArray.toString());
		writeResponseMessage(response, nodeTipArray.toString(), 200);
	}

	/**
	 * 提供拓扑图中的NODE的健康度
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void fetchNodeUsableStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request == null || response == null) {
			return;
		}
		//
		String nodeIDs = request.getParameter("nodeIDs");
		if (nodeIDs == null || nodeIDs.length() == 0) {
			writeResponseMessage(response, "无效的nodeIDs", 500);
			return;
		}
		//
		JSONObject nodesStatus = ResourceProvider.getInstance().getResourceEventStatusByNodeIDs(nodeIDs);
		if (nodesStatus == null) {
			nodesStatus = new JSONObject();
		}
		//
		writeResponseMessage(response, nodesStatus.toString(), 200);
	}

	/**
	 * 写一个HTTP response 消息到客户端
	 * 
	 * @param response
	 * @param body
	 * @param httpCode
	 */
	private void writeResponseMessage(HttpServletResponse response, String body, int httpCode) {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			response.setStatus(httpCode);
			out.println(body);
			out.flush();
		} catch (IOException e) {
			// 忽略HTTP RESPONSE影响，BROWSER可以关闭
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
	}

	/**
	 * <p>
	 * Description: 定义所有中拓扑功能中的前后台交互方式
	 * </p>
	 * 
	 * @author baiyanwei
	 * @version
	 * 
	 *          <p>
	 *          History:
	 * 
	 *          Date Author Version Description
	 *          ----------------------------------
	 *          ----------------------------------------------- 2012-12-28
	 *          下午3:32:45 baiyanwei 3.1 To create
	 *          </p>
	 * 
	 * @since
	 * @see
	 */
	public static class TopologyMethod {
		final public static String BUILD_TOPOLOGY = "buildTopology";
		final public static String FETCH_NODE_TOOLTIP = "fetchNodeTooltip";
		final public static String FETCH_NODE_USABLE_STATUS = "fetchNodeUsableStatus";
		final public static String BUILD_DIALING_TOPOLOGY = "buildDialingTopology";
	}

	/**
	 * <p>
	 * Description: 定义拓扑中NODE的类别
	 * </p>
	 * 
	 * @author baiyanwei
	 * @version
	 * 
	 *          <p>
	 *          History:
	 * 
	 *          Date Author Version Description
	 *          ----------------------------------
	 *          ----------------------------------------------- 2012-12-28
	 *          下午3:32:56 baiyanwei 3.1 To create
	 *          </p>
	 * 
	 * @since
	 * @see
	 */
	public static class NodeType {
		// 实体节点
		final public static String CITY_NODE = "city";
		// 模块节点
		final public static String FIREWALL_NODE = "firewall";
		// 类别节点
		final public static String MCA_NODE = "mca";
	}
}
