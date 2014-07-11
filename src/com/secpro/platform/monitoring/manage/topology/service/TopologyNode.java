package com.secpro.platform.monitoring.manage.topology.service;

import org.json.JSONObject;

public class TopologyNode {
	final public static String NODE_TYPE_NAME = "nodeType";
	final public static String NODE_ID_NAME = "nodeID";
	final public static String NODE_LABEL_NAME = "nodeLabel";

	public static JSONObject getJSONObjectByRowData(String[] keyNames, String[] nodeDefines, Object[] values) throws Exception {
		if (keyNames == null || values == null) {
			throw new Exception("Can't create the JSONObject by row data and keys");
		}
		JSONObject rowObj = new JSONObject();
		for (int i = 0; i < keyNames.length; i++) {
			if (i < values.length) {
				rowObj.put(keyNames[i], String.valueOf(values[i]));
			} else {
				rowObj.put(keyNames[i], "");
			}

		}
		if (nodeDefines != null) {
			rowObj.put(NODE_ID_NAME, rowObj.getString(nodeDefines[0]));
			rowObj.put(NODE_LABEL_NAME, rowObj.getString(nodeDefines[1]));
			rowObj.put(NODE_TYPE_NAME, nodeDefines[2]);
		}
		return rowObj;
	}
}
