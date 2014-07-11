package com.secpro.platform.monitoring.manage.topology.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import y.io.graphml.KeyScope;
import y.io.graphml.KeyType;

import com.secpro.platform.monitoring.manage.topology.service.TopologyAdapter;
import com.secpro.platform.monitoring.manage.topology.service.TopologyLayoutRender;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;
import com.yworks.yfiles.server.graphml.support.GraphRoundtripSupport;

/**
 * <p>
 * Description: flex拓扑图控制servlet
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
 *          ------------------------------------------- 2012-12-19 下午4:11:57
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyServlet extends HttpServlet {
	private static final PlatformLogger logger = PlatformLogger.getLogger(TopologyAdapter.class);
	/**
     *
     */
	private static final long serialVersionUID = 8831554135365020601L;
	private TopologyAdapter topologyAdapter = null;

	/**
	 * Constructor of the object.
	 */
	public TopologyServlet() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		topologyAdapter = TopologyAdapter.getInstance();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleTopology(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleTopology(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 *             处理FLEX前台的拓扑请求
	 */
	private void handleTopology(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		// 分方法执行
		if (TopologyAdapter.TopologyMethod.BUILD_TOPOLOGY.equalsIgnoreCase(method)) {
			// 构建拓扑参考，存放一个拓扑图与节点的信息
			HashMap<String, String> topologyReferentMap = buildTopologyReferent(request);
			// 构建拓扑图
			GraphRoundtripSupport support = new GraphRoundtripSupport();
			support.setSupportUserTags(true);
			// 加入拓扑图附加信息
			support.addMapper("GraphAdditionalDataMap", "GRAPH-ADD-DATA", KeyType.STRING, KeyScope.GRAPH);
			//
			StyledLayoutGraph graph = (StyledLayoutGraph) support.createRoundtripGraph();
			topologyAdapter.buildTopology(graph, topologyReferentMap);
			support.sendGraph(graph, response);
		} else if (TopologyAdapter.TopologyMethod.FETCH_NODE_TOOLTIP.equalsIgnoreCase(method)) {
			// 请求NODE的TOOLTIP
			topologyAdapter.getNodeTooltip(request, response);
		} else if (TopologyAdapter.TopologyMethod.FETCH_NODE_USABLE_STATUS.equalsIgnoreCase(method)) {
			// 请求拓扑图中的NODE健康度信息
			topologyAdapter.fetchNodeUsableStatus(request, response);
		} else if (TopologyAdapter.TopologyMethod.BUILD_DIALING_TOPOLOGY.equalsIgnoreCase(method)) {
			// 构建拓扑参考，存放一个拓扑图与节点的信息
			HashMap<String, String> topologyReferentMap = buildTopologyReferent(request);
			// 构建拓扑图
			GraphRoundtripSupport support = new GraphRoundtripSupport();
			support.setSupportUserTags(true);
			// 加入拓扑图附加信息
			support.addMapper("GraphAdditionalDataMap", "GRAPH-ADD-DATA", KeyType.STRING, KeyScope.GRAPH);
			//
			StyledLayoutGraph graph = (StyledLayoutGraph) support.createRoundtripGraph();
			topologyAdapter.buildDialingTopology(graph, topologyReferentMap);
			support.sendGraph(graph, response);
		}
	}

	/**
	 * @param request
	 * @return 构建拓扑参考，存放一个拓扑图与节点的信息
	 */
	private HashMap<String, String> buildTopologyReferent(HttpServletRequest request) {
		HashMap<String, String> topologyReferentMap = new HashMap<String, String>();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		// 当前查看的节点
		String nodeID = request.getParameter("nodeID");
		topologyReferentMap.put("basePath", basePath);
		topologyReferentMap.put("nodeID", nodeID);
		String layoutStyle = request.getParameter("layoutStlye");
		if (Assert.isEmptyString(layoutStyle) == true) {
			layoutStyle = TopologyLayoutRender.ORGANIC;
		}
		topologyReferentMap.put("startIp", request.getParameter("startIp"));
		topologyReferentMap.put("endIp", request.getParameter("endIp"));
		topologyReferentMap.put("layoutStlye", layoutStyle);
		logger.debug("buildTopologyReferent:" + topologyReferentMap);
		return topologyReferentMap;
	}
}
