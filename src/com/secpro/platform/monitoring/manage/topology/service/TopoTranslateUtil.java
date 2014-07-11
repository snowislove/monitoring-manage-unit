package com.secpro.platform.monitoring.manage.topology.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import y.base.Edge;
import y.base.Node;
import y.base.YList;
import y.geom.YDimension;
import y.geom.YPoint;

import com.yworks.yfiles.server.graphml.flexio.compat.AdvancedStroke;
import com.yworks.yfiles.server.graphml.flexio.compat.Font;
import com.yworks.yfiles.server.graphml.flexio.compat.Insets;
import com.yworks.yfiles.server.graphml.flexio.compat.SolidColor;
import com.yworks.yfiles.server.graphml.flexio.compat.Stroke;
import com.yworks.yfiles.server.graphml.flexio.data.Arrow;
import com.yworks.yfiles.server.graphml.flexio.data.ExteriorLabelModel;
import com.yworks.yfiles.server.graphml.flexio.data.ImageNodeStyle;
import com.yworks.yfiles.server.graphml.flexio.data.InteriorLabelModel;
import com.yworks.yfiles.server.graphml.flexio.data.Label;
import com.yworks.yfiles.server.graphml.flexio.data.PanelNodeStyle;
import com.yworks.yfiles.server.graphml.flexio.data.PolylineEdgeStyle;
import com.yworks.yfiles.server.graphml.flexio.data.ShapeNodeStyle;
import com.yworks.yfiles.server.graphml.flexio.data.SimpleLabelStyle;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;
import com.yworks.yfiles.server.graphml.flexio.data.flexmarkup.CollapsibleNodeStyleDecorator;

public class TopoTranslateUtil {
    public static String NODE_ROOT = "root";
    public static String NODE_PARENT = "parent";
    public static String NODE_RESNODE = "resNode";
    public static String NODE_EDGE = "edge";
    public static String NODE_IMAGENODE = "imageNode";
    public static String NODE_SWIMLANE = "swimlane";
    public static String NODE_GROUP = "group";
    public static String NODE_TEXT = "text";

    /**
     * 检测节点类型
     * root,parent,resNode,edge,imageNode,group,text
     * @param element
     * @return
     */
    private static String checkType(Element element) {
        String id = element.attributeValue("id");
        if (id == null) {
            return null;
        }
        //root
        if (id.equals("0")) {
            return NODE_ROOT;
        }
        //parent
        else if (id.equals("1")) {
            return NODE_PARENT;
        }

        String vertex = element.attributeValue("vertex");//is vertex
        //node,group,imageNode,text
        if (vertex != null && vertex.equals("1")) {
            String isText = element.attributeValue("isText");//is vertex
            //text
            if (isText != null && isText.equals("1")) {
                return NODE_TEXT;
            }
            String resId = element.attributeValue("resId");
            //node
            if (resId != null) {

                return NODE_RESNODE;
            }
            String style = element.attributeValue("style");
            //group
            if (style != null && style.equals("swimlane")) {
                return "swimlane";
            } else if (style != null && style.equals("group")) {
                return "group";
            }
            //imageNode
            else {
                return NODE_IMAGENODE;
            }
        }

        String edge = element.attributeValue("edge");//is edge
        //edge
        if (edge != null && edge.equals("1")) {
            return NODE_EDGE;
        }
        return null;
    }

    /**
     * 分配cell到各节点类型列表
     * @param element
     * @param map
     */
    private static void assignCell(Element element, HashMap<String, List<Element>> map, HashMap<String, Object> nodeMap) {
        String nodeType = checkType(element);
        if (nodeType == null) {
            return;
        }
        String id = element.attributeValue("id");
        nodeMap.put(id, element);
        List<Element> list = map.get(nodeType);
        if (list != null) {
            list.add(element);
        } else {
            list = new ArrayList<Element>();
            list.add(element);
            map.put(nodeType, list);
        }

    }

    /**
     * 设置组
     */
    private static void setGroup(StyledLayoutGraph graph, Node node, String parent, HashMap<String, Object> nodeMap) {
        if (!parent.equals("0") && !parent.equals("1")) {
            Object parentNode = nodeMap.get(parent);
            if (parentNode != null && parentNode instanceof Node && !node.equals(parentNode)) {
                graph.getNodeHierarchy().setParent(node, (Node) parentNode);
                double newx = graph.getX(node) + graph.getX((Node) parentNode);
                double newy = graph.getY(node) + graph.getY((Node) parentNode);
                graph.setLocation(node, newx, newy);
            }
        }
    }

    /**
     * 添加资源节点
     * @param graph
     * @param element
     */
    private static void addText(StyledLayoutGraph graph, Element element, HashMap nodeMap) {
        String id = element.attributeValue("id");
        String value = element.attributeValue("value");
        String style = element.attributeValue("style");
        String parent = element.attributeValue("parent");

        List<Element> ces = element.elements("mxGeometry");
        if (ces.size() <= 0) {
            return;
        }
        Element ce = ces.get(0);

        String x = ce.attributeValue("x");
        if (x == null || x.trim().equals("")) {
            x = "0";
        }
        String y = ce.attributeValue("y");
        if (y == null || y.trim().equals("")) {
            y = "0";
        }
        String width = ce.attributeValue("width");
        String height = ce.attributeValue("height");

        //user data
        //        String resId = element.attributeValue("resId");

        //        String styleName = element.attributeValue("styleName");

        Node node = graph.createNode();
        Label label1 = new Label();
        value = value.replace("<", "&lt;");
        label1.setText(value);
        label1.setLabelModelParameter(InteriorLabelModel.center);

        ShapeNodeStyle ns = new ShapeNodeStyle();
        ns.setFill(new SolidColor(Color.WHITE));
        HashMap<String, String> styles = parseStyle(style);

        SimpleLabelStyle sls = new SimpleLabelStyle();

        if (styles.get("labelBackgroundColor") != null) {
            sls.setBackgroundFill(new SolidColor(getColor(styles.get("labelBackgroundColor"))));
        } else {
            sls.setBackgroundFill(new SolidColor(new Color(255, 255, 255)));
        }

        if (styles.get("fontColor") != null) {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(getColor(styles.get("fontColor"), alpha)));
        } else {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(new Color(0, 0, 0, alpha)));
        }

        if (styles.get("labelBorderColor") != null) {
            Stroke stroke = new Stroke();
            stroke.setColor(getColor(styles.get("labelBorderColor")));
            sls.setBackgroundStroke(stroke);
        }

        label1.setStyle(sls);
        label1.setPreferredSize(new YDimension(Double.valueOf(width) - 20, Double.valueOf(height) - 2));

        graph.addLabel(node, label1);
        graph.setStyle(node, ns);
        graph.setLocation(node, Double.valueOf(x), Double.valueOf(y));
        graph.setSize(node, Double.valueOf(width), Double.valueOf(height));

        nodeMap.put(id, node);

        //设置组
        setGroup(graph, node, parent, nodeMap);
    }

    /**
     * 添加资源节点
     * @param graph
     * @param element
     */
    private static void addNode(StyledLayoutGraph graph, Element element, HashMap nodeMap) {
        String id = element.attributeValue("id");
        String value = element.attributeValue("value");
        String style = element.attributeValue("style");
        String parent = element.attributeValue("parent");

        List<Element> ces = element.elements("mxGeometry");
        if (ces.size() <= 0) {
            return;
        }
        Element ce = ces.get(0);

        String x = ce.attributeValue("x");
        if (x == null || x.trim().equals("")) {
            x = "0";
        }
        String y = ce.attributeValue("y");
        if (y == null || y.trim().equals("")) {
            y = "0";
        }
        String width = ce.attributeValue("width");
        String height = ce.attributeValue("height");

        //user data
        String resId = element.attributeValue("resId");
        String parentResId = element.attributeValue("resParentId");
        //        String resTypeId = element.attributeValue("resTypeId");
        //        String retType = element.attributeValue("retType");
        //        String propids = element.attributeValue("propids");
        //        String attrids = element.attributeValue("attrids");

        String styleName = element.attributeValue("styleName");

        //        String vertex = element.attributeValue("vertex");//is vertex
        //        String edge = element.attributeValue("edge");//is edge

        //        String status = element.attributeValue("status");

        Node node = graph.createNode();
        Label label1 = new Label();
        value = value.replace("<", "&lt;");
        label1.setText(value);
        label1.setLabelModelParameter(ExteriorLabelModel.south);

        ImageNodeStyle ns = new ImageNodeStyle();
        HashMap<String, String> styles = parseStyle(style);
        try {
            if (styleName != null) {
                ns.setUrl("/unionmon/images/res/" + styleName + "/1.gif");
            } else {
                String url = styles.get("image");
                ns.setUrl(url);
            }
        } catch (Exception e) {
        }

        SimpleLabelStyle sls = new SimpleLabelStyle();
        Font font = new Font();
        font.setFamily("Arial");
        sls.setFont(font);
        if (styles.get("labelBackgroundColor") != null) {
            sls.setBackgroundFill(new SolidColor(getColor(styles.get("labelBackgroundColor"))));
        } else {
            sls.setBackgroundFill(new SolidColor(new Color(255, 255, 255)));
        }

        if (styles.get("fontColor") != null) {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(getColor(styles.get("fontColor"), alpha)));
        } else {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(new Color(119, 68, 0, alpha)));
        }

        if (styles.get("labelBorderColor") != null) {
            Stroke stroke = new Stroke();
            stroke.setColor(getColor(styles.get("labelBorderColor")));
            sls.setBackgroundStroke(stroke);
        }
        sls.setAutoFlip(true);
        label1.setStyle(sls);

        graph.addLabel(node, label1);
        graph.setStyle(node, ns);
        try{
        if (parentResId != null && parentResId.trim().length() > 0) {
            JSONObject userTarget = new JSONObject();

            userTarget.put("t", resId);
            userTarget.put("l", value);
            userTarget.put("id", parentResId + "_" + resId);
            String userData = userTarget.toString();
            graph.setUserTag(node, userData);
        } else {
            try {
//                int iResId = Integer.parseInt(resId);
//                JSONObject jo = ResourceProvider.getInstance().getResourceUserTargetObject(Resource.get(iResId));
//                jo.put("i", ns.getUrl());
//                String userData = jo.toString();
//                graph.setUserTag(node, userData);
            } catch (Exception e) {
            }
        }
        }catch(Exception e){
        	e.printStackTrace();
        }
        graph.setLocation(node, Double.valueOf(x), Double.valueOf(y));
        graph.setSize(node, Double.valueOf(width), Double.valueOf(height));

        nodeMap.put(id, node);
        //设置组
        setGroup(graph, node, parent, nodeMap);
    }

    /**
     * 添加组
     * @param graph
     * @param element
     */
    private static void addGroup(StyledLayoutGraph graph, Element element, HashMap nodeMap) {
        String id = element.attributeValue("id");
        String value = element.attributeValue("value");
        String style = element.attributeValue("style");
        String parent = element.attributeValue("parent");

        List<Element> ces = element.elements("mxGeometry");
        if (ces.size() <= 0) {
            return;
        }
        Element ce = ces.get(0);

        String x = ce.attributeValue("x");
        if (x == null || x.trim().equals("")) {
            x = "0";
        }
        String y = ce.attributeValue("y");
        if (y == null || y.trim().equals("")) {
            y = "0";
        }
        String width = ce.attributeValue("width");
        String height = ce.attributeValue("height");

        Node node = graph.createNode();
        Label label1 = new Label();
        value = value.replace("<", "&lt;");
        label1.setText(value);
        if (style != null && style.equals("group")) {
            label1.setLabelModelParameter(ExteriorLabelModel.south);
        } else {
            label1.setLabelModelParameter(InteriorLabelModel.north);
        }

        HashMap<String, String> styles = parseStyle(style);
        SimpleLabelStyle sls = new SimpleLabelStyle();
        Font font = new Font();
        font.setFamily("Arial");
        sls.setFont(font);
        if (styles.get("labelBackgroundColor") != null) {
            sls.setBackgroundFill(new SolidColor(getColor(styles.get("labelBackgroundColor"))));
        }

        if (styles.get("fontColor") != null) {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(getColor(styles.get("fontColor"), alpha)));
        } else {
            int alpha = 1;
            if (styles.get("textOpacity") != null) {
                try {
                    alpha = Integer.parseInt(styles.get("textOpacity"));
                } catch (Exception e) {
                }
            }
            sls.setFontFill(new SolidColor(new Color(119, 68, 0, alpha)));
        }

        if (styles.get("labelBorderColor") != null) {
            Stroke stroke = new Stroke();
            stroke.setColor(getColor(styles.get("labelBorderColor")));
            sls.setBackgroundStroke(stroke);
        }
        sls.setAutoFlip(true);
        label1.setStyle(sls);

        PanelNodeStyle ns = new PanelNodeStyle();
        ns.setColor(new Color(200, 220, 255));
        ns.setLabelInsetsColor(new Color(200, 220, 255));
        ns.setInsets(new Insets(0, 0, 0, 0));

        CollapsibleNodeStyleDecorator cnsd = new CollapsibleNodeStyleDecorator(ns);
        cnsd.setInsets(new Insets(0, 0, 0, 0));
        graph.addLabel(node, label1);
        graph.setStyle(node, cnsd);
        graph.setLocation(node, Double.valueOf(x), Double.valueOf(y));
        graph.setSize(node, Double.valueOf(width), Double.valueOf(height));

        nodeMap.put(id, node);
        //设置组
        setGroup(graph, node, parent, nodeMap);
    }

    /**
     * 添加连线
     * @param graph
     * @param element
     */
    private static void addEdge(StyledLayoutGraph graph, Element element, HashMap nodeMap) {
        //        String id = element.attributeValue("id");
        String value = element.attributeValue("value");
        String style = element.attributeValue("style");
        //        String parent = element.attributeValue("parent");

        //user data
        //        String resId = element.attributeValue("resId");
        //        String resTypeId = element.attributeValue("resTypeId");
        //        String retType = element.attributeValue("retType");
        //        String propids = element.attributeValue("propids");
        //        String attrids = element.attributeValue("attrids");

        String styleName = element.attributeValue("styleName");

        //        String vertex = element.attributeValue("vertex");//is vertex
        //        String edge = element.attributeValue("edge");//is edge

        String source = element.attributeValue("source");
        String target = element.attributeValue("target");

        Node sourceNode = (Node) nodeMap.get(source);
        Node targetNode = (Node) nodeMap.get(target);
        Edge edgeNode = null;
        if (sourceNode != null && targetNode != null) {
            edgeNode = graph.createEdge(sourceNode, targetNode);
        } else {
            return;
        }

        Label label1 = new Label();
        value = value.replace("<", "&lt;");
        label1.setText(value);
        HashMap<String, String> styles = parseStyle(style);
        if (styles != null) {
            PolylineEdgeStyle pes = new PolylineEdgeStyle();
            AdvancedStroke as = new AdvancedStroke();//LineType.DASHED_1;//
            Color color = new Color(100, 130, 185);
            if (styles.get("strokeColor") != null) {
                color = getColor(styles.get("strokeColor"));
            }
            as.setColor(color);

            if (styles.get("startArrow") != null) {
                pes.setSourceArrow(getArrow(styles.get("startArrow"), color));
            }
            if (styles.get("endArrow") != null) {
                pes.setTargetArrow(getArrow(styles.get("endArrow"), color));
            } else {
                pes.setTargetArrow(getArrow("classic", color));
            }

            if (styles.get("strokeWidth") != null) {
                as.setWeight(Double.parseDouble(styles.get("strokeWidth")));
            }

            if (styles.get("dashed") != null) {
                double[] patterns = new double[2];
                patterns[0] = 2;
                patterns[1] = 3;
                as.setPattern(patterns);
            }
            pes.setStroke(as);
            pes.setSmoothing(10);

            graph.setStyle(edgeNode, pes);
            YList list = new YList();
            YPoint p1 = graph.getLocation(sourceNode);
            YPoint p2 = graph.getLocation(targetNode);
            YDimension d1 = graph.getSize(sourceNode);
            YDimension d2 = graph.getSize(targetNode);

            double mx = 0;
            double my = 0;

            if (styleName == null || styleName.trim().equals("")) {

            } else if (styleName.equals("horizontal")) {
                if (mx == 0) {
                    if (p1.x > p2.x) {
                        mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                        my = p1.y + (p1.y - p2.y - d1.height / 2 + d2.height / 2) / 2;
                    } else {
                        mx = p2.x - (p2.x - p1.x - d1.width) / 2;
                        my = p1.y + (p2.y - p1.y - d1.height / 2 + d2.height / 2) / 2;
                    }
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(middleP.x, p1.y + d1.height / 2));
                list.add(new YPoint(middleP.x, p2.y + d2.height / 2));
            } else if (styleName.equals("vertical")) {
                if (p1.y > p2.y) {
                    mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                    my = p1.y - (p1.y - p2.y - d2.width) / 2;
                } else {
                    mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                    my = p2.y - (p2.y - p1.y - d1.width) / 2;
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(p1.x + d1.width / 2, middleP.y));
                list.add(new YPoint(p2.x + d2.width / 2, middleP.y));
            } else if (styleName.equals("entity")) {
                if (mx == 0) {
                    if (p1.x > p2.x) {
                        mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                        my = p1.y + (p1.y - p2.y - d1.height / 2 + d2.height / 2) / 2;
                    } else {
                        mx = p2.x - (p2.x - p1.x - d1.width) / 2;
                        my = p1.y + (p2.y - p1.y - d1.height / 2 + d2.height / 2) / 2;
                    }
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(middleP.x, p1.y + d1.height / 2));
                list.add(new YPoint(middleP.x, p2.y + d2.height / 2));
            }

            graph.setPoints(edgeNode, list);
        } else {
            PolylineEdgeStyle pes = new PolylineEdgeStyle();
            AdvancedStroke as = new AdvancedStroke();
            Color color = new Color(100, 130, 185);
            as.setColor(color);
            pes.setTargetArrow(getArrow("classic", color));
            pes.setStroke(as);
            graph.setStyle(edgeNode, pes);

            YList list = new YList();
            YPoint p1 = graph.getLocation(sourceNode);
            YPoint p2 = graph.getLocation(targetNode);
            YDimension d1 = graph.getSize(sourceNode);
            YDimension d2 = graph.getSize(targetNode);

            double mx = 0;
            double my = 0;
            styleName = "horizontal";
            if (styleName == null || styleName.trim().equals("")) {

            } else if (styleName.equals("horizontal")) {
                if (mx == 0) {
                    if (p1.x > p2.x) {
                        mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                        my = p1.y + (p1.y - p2.y - d1.height / 2 + d2.height / 2) / 2;
                    } else {
                        mx = p2.x - (p2.x - p1.x - d1.width) / 2;
                        my = p1.y + (p2.y - p1.y - d1.height / 2 + d2.height / 2) / 2;
                    }
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(middleP.x, p1.y + d1.height / 2));
                list.add(new YPoint(middleP.x, p2.y + d2.height / 2));
            } else if (styleName.equals("vertical")) {
                if (p1.y > p2.y) {
                    mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                    my = p1.y - (p1.y - p2.y - d2.width) / 2;
                } else {
                    mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                    my = p2.y - (p2.y - p1.y - d1.width) / 2;
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(p1.x + d1.width / 2, middleP.y));
                list.add(new YPoint(p2.x + d2.width / 2, middleP.y));
            } else if (styleName.equals("entity")) {
                if (mx == 0) {
                    if (p1.x > p2.x) {
                        mx = p1.x - (p1.x - p2.x - d2.width) / 2;
                        my = p1.y + (p1.y - p2.y - d1.height / 2 + d2.height / 2) / 2;
                    } else {
                        mx = p2.x - (p2.x - p1.x - d1.width) / 2;
                        my = p1.y + (p2.y - p1.y - d1.height / 2 + d2.height / 2) / 2;
                    }
                }
                YPoint middleP = new YPoint(mx, my);
                list.add(new YPoint(middleP.x, p1.y + d1.height / 2));
                list.add(new YPoint(middleP.x, p2.y + d2.height / 2));
            }

            graph.setPoints(edgeNode, list);
        }

        if (edgeNode != null) {
            SimpleLabelStyle labelsls = new SimpleLabelStyle();

            if (styles != null && styles.get("labelBackgroundColor") != null) {
                labelsls.setBackgroundFill(new SolidColor(getColor(styles.get("labelBackgroundColor"))));
            }

            if (styles != null && styles.get("fontColor") != null) {
                int alpha = 1;
                if (styles != null && styles.get("textOpacity") != null) {
                    try {
                        alpha = Integer.parseInt(styles.get("textOpacity"));
                    } catch (Exception e) {
                    }
                }
                labelsls.setFontFill(new SolidColor(getColor(styles.get("fontColor"), alpha)));
            } else {
                int alpha = 1;
                if (styles != null && styles.get("textOpacity") != null) {
                    try {
                        alpha = Integer.parseInt(styles.get("textOpacity"));
                    } catch (Exception e) {
                    }
                }
                labelsls.setFontFill(new SolidColor(new Color(68, 98, 153, alpha)));
            }

            if (styles != null && styles.get("labelBorderColor") != null) {
                Stroke stroke = new Stroke();
                stroke.setColor(getColor(styles.get("labelBorderColor")));
                labelsls.setBackgroundStroke(stroke);
            }

            label1.setStyle(labelsls);

            graph.addLabel(edgeNode, label1);
        }
    }

    /**
     * 获取颜色
     * @param color
     * @return
     */
    private static Color getColor(String color) {
        return getColor(color, 1);
    }

    private static Color getColor(String color, int alpha) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            Color c = new Color(r, g, b);
            return c;
        } catch (Exception nfe) {
            return null;
        }
    }

    /**
     * 设置箭头
     * @param strArrow
     * @param color
     * @return
     */
    private static Arrow getArrow(String strArrow, Color color) {
        Arrow arrow = new Arrow();
        if (strArrow.equals("diamond")) {
            arrow.setType(Arrow.TYPE_DIAMOND);
        } else if (strArrow.equals("oval")) {
            arrow.setType(Arrow.TYPE_CIRCLE);
        } else if (strArrow.equals("open")) {
            arrow.setType(Arrow.TYPE_SHORT);
        } else if (strArrow.equals("block")) {
            arrow.setType(Arrow.TYPE_DELTA);
        } else if (strArrow.equals("classic")) {
            arrow.setType(Arrow.TYPE_SPEARHEAD);
        } else if (strArrow.equals("none")) {
            arrow.setType(Arrow.TYPE_NONE);
        }
        arrow.setFill(new SolidColor(color));
        Stroke s = new Stroke();
        s.setUseSolidColorStroke(true);
        s.setColor(color);

        arrow.setStroke(s);
        return arrow;
    }

    private static HashMap<String, String> parseStyle(String content) {
        HashMap<String, String> styles = new HashMap<String, String>();
        try {
            String[] aStyles = content.split(";");
            if (aStyles == null || aStyles.length <= 0) {
                return null;
            }

            for (String style : aStyles) {
                String[] curStyle = style.split("=");
                if (curStyle != null && curStyle.length == 2) {
                    styles.put(curStyle[0], curStyle[1]);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return styles;
    }

    /**
     * 解析JS版本拓扑数据
     * @param xml
     * @return
     */
    public static String parseXml(StyledLayoutGraph graph, String xml) {
        if (xml != null && xml.length() > 20) {
            Document document;
            try {
                //document = new SAXReader().read("D:\\workspace\\ytest1\\WebContent\\resources\\NewFile2.xml");//
                document = DocumentHelper.parseText(xml);

                Element root = document.getRootElement();
                List<Element> es = ((Element) (root.elements().get(0))).elements("mxCell");

                //该列表主要用于在group和edge查找节点使用
                HashMap<String, List<Element>> map = new HashMap<String, List<Element>>();
                //用于查找node和group
                HashMap<String, Object> nodeMap = new HashMap<String, Object>();
                for (Element el : es) {
                    assignCell(el, map, nodeMap);
                }

                List<Element> roots = map.get(NODE_ROOT);
                for (Element el : roots) {
                    if (el != null) {
                        String bg = el.attributeValue("backgroundImage");
                        String width = el.attributeValue("backgroundWidth");
                        String height = el.attributeValue("backgroundHeight");
                        if (bg != null && !bg.equals("")) {
                            if (width == null || width.equals("")) {
                                width = "800";
                            }
                            if (height == null || height.equals("")) {
                                height = "800";
                            }
                            TopologyBuilder.getInstance().setGraphBackGroup(graph, bg, width, height);
                        }
                    }

                }

                //                List<Element> parents = map.get(NODE_PARENT);
                //                if (parents != null) {
                //                    for (Element el : parents) {
                //                    }
                //                }

                List<Element> groups = map.get(NODE_GROUP);
                if (groups != null) {
                    for (Element el : groups) {
                        addGroup(graph, el, nodeMap);
                    }
                }

                List<Element> swims = map.get(NODE_SWIMLANE);
                if (swims != null) {
                    for (Element el : swims) {
                        addGroup(graph, el, nodeMap);
                    }
                }

                List<Element> resNodes = map.get(NODE_RESNODE);
                if (resNodes != null) {
                    for (Element el : resNodes) {
                        addNode(graph, el, nodeMap);
                    }
                }

                List<Element> imageNodes = map.get(NODE_IMAGENODE);
                if (imageNodes != null) {
                    for (Element el : imageNodes) {
                        addNode(graph, el, nodeMap);
                    }
                }

                List<Element> texts = map.get(NODE_TEXT);
                if (texts != null) {
                    for (Element el : texts) {
                        addText(graph, el, nodeMap);
                    }
                }

                List<Element> edges = map.get(NODE_EDGE);
                if (edges != null) {
                    for (Element el : edges) {
                        addEdge(graph, el, nodeMap);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //logger.error("can't parse cloud config xml:" + xml, e);
                return null;
            }
            return null;
        } else {
            return null;
        }
    }
}
