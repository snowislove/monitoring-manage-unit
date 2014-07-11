package com.secpro.platform.monitoring.manage.topology.service;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import y.base.Edge;
import y.base.Node;

import com.yworks.yfiles.server.graphml.flexio.data.PanelNodeStyle;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;

/**
 * <p>
 * Description: 拓扑图绘制
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
 *          ------------------------------------------- 2012-12-28 下午3:44:34
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyPainter {
	private static TopologyPainter topologyPainter = null;

	public static TopologyPainter getInstance() {
		if (topologyPainter == null) {
			topologyPainter = new TopologyPainter();
		}
		return topologyPainter;
	}

	private TopologyPainter() {

	}

	/**
	 * 在拓扑图上绘NODE ,如果图中已经存在此NODE则使用已有NODE.
	 * 
	 * @param graph
	 * @param currentNode
	 * @param direction
	 * @param resourceList
	 */
	public void paintNodeOnGraph(StyledLayoutGraph graph, Node currentNode, String direction, JSONObject currentResource, List<JSONObject> resourceList, int relationType,
			HashMap<JSONObject, Node> graphNodeMap) {
		if (graph == null || currentNode == null || resourceList == null || direction == null || currentResource == null || graphNodeMap == null) {
			return;
		}
		for (int i = 0; i < resourceList.size(); i++) {
			if (resourceList.get(i) == null) {
				continue;
			}
			// 创建NODE
			Node targetNode = createNodeByResourceWithMap(graph, resourceList.get(i), graphNodeMap);
			if (targetNode == null) {
				continue;
			}
			// 绘边
			if ("to".equals(direction)) {
				createEdgeWithRelation(graph, currentNode, targetNode, currentResource, resourceList.get(i), relationType);
			} else {
				createEdgeWithRelation(graph, targetNode, currentNode, resourceList.get(i), currentResource, relationType);
			}
		}
	}

	/**
	 * 创建一个GROUP NODE
	 * 
	 * @param graph
	 * @param labelText
	 * @return
	 */
	public Node createGroupNode(StyledLayoutGraph graph, String groupName, List<JSONObject> resourcesList, int resourceType) {
		Node groupNode = graph.createNode();
		PanelNodeStyle style = new PanelNodeStyle();
		style.setColor(Color.gray);
		graph.setStyle(groupNode, style);
		graph.setUserTag(groupNode, ResourceProvider.getInstance().getGourpUserTargetObject(groupName, resourceType, resourcesList).toString());
		return groupNode;

	}

	/**
	 * 创建一个NODE并写入USERTARGET
	 * 
	 * @param graph
	 * @param resource
	 * @return
	 */
	public Node createNodeByResource(StyledLayoutGraph graph, JSONObject resource) {
		if (graph == null || resource == null) {
			return null;
		}
		Node currentNode = graph.createNode();
		graph.setUserTag(currentNode, ResourceProvider.getInstance().getResourceUserTargetObject(resource).toString());
		return currentNode;
	}

	/**
	 * 参考现有NODE，创建基于资源的NODE，有NODE已经被创建过则使用存在的NODE.
	 * 
	 * @param graph
	 * @param resource
	 * @param graphNodeMap
	 * @return
	 */
	public Node createNodeByResourceWithMap(StyledLayoutGraph graph, JSONObject resource, HashMap<JSONObject, Node> graphNodeMap) {
		if (graph == null || resource == null || graphNodeMap == null) {
			return null;
		}
		// 创建NODE
		Node targetNode = null;
		if (graphNodeMap.containsKey(resource)) {
			targetNode = graphNodeMap.get(resource);
		} else {
			targetNode = createNodeByResource(graph, resource);
			if (targetNode == null) {
				return null;
			}
			graphNodeMap.put(resource, targetNode);
		}
		return targetNode;
	}

	/**
	 * 根据关系创建连接
	 * 
	 * @param graph
	 * @param fromNode
	 * @param toNode
	 * @param relationType
	 * @return
	 */
	public Edge createEdgeWithRelation(StyledLayoutGraph graph, Node sNode, Node eNode, JSONObject sObj, JSONObject eObj, int relationType) {
		if (graph == null || sNode == null || eNode == null) {
			return null;
		}
		// 检查一下是不是有重复的关系
		Edge existEdge = sNode.getEdge(eNode);
		if (existEdge != null) {
			String userTarget = (String) graph.getUserTag(existEdge);
			if (userTarget != null && userTarget.length() > 0) {
				try {
					JSONObject tarObj = new JSONObject(userTarget);
					if (tarObj.has("t") == true && String.valueOf(relationType).equals(tarObj.getString("t"))) {
						return existEdge;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		Edge edge = graph.createEdge(sNode, eNode);
		graph.setUserTag(edge, ResourceProvider.getInstance().getRelationUserTargetObject(sObj, eObj, relationType).toString());
		return edge;
	}
}
