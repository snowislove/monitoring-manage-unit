package com.secpro.platform.monitoring.manage.tree;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.vandagroup.common.bean.BaseBean;
import com.vandagroup.common.treedic.TreeDic;
import com.vandagroup.common.treedic.TreeDicManager;
import com.vandagroup.hibernate.databean.PageRoll;

/**
 * @author liyan
 * 2013-10-19
 */
public class TreeView {
	
	public final static String TREENODEPOSITION = "_TREENODEPOSITION_";
	public final static String TREETITLE = "treeTitle";
   
    public TreeView() {
    }
    

    /**
     * 构造函数：生成基本型树状菜单，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为两个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
        String title, 
        String tableName, 
        String colName,
        String textColName, 
        String parentColName, 
        String parentColValue)
    throws Exception {

        setTitle(title);
        setTableName(tableName);
        setColName(colName);
        setTextColName(textColName);
        setParentColName(parentColName);
        setParentColValue(parentColValue);
    }

    /**
     * 构造函数：生成基本型树状菜单，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为三个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * arguments[ 2 ]=paraValue(参数字段的值) <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     * @param paraColName
     *            参数字段名称 <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
        String title, 
        String tableName, 
        String colName,
        String textColName, 
        String parentColName, 
        String parentColValue,
        String paraColName) 
    throws Exception {

        setTitle(title);
        setTableName(tableName);
        setColName(colName);
        setTextColName(textColName);
        setParentColName(parentColName);
        setParentColValue(parentColValue);
        setParaColName(paraColName);
    }

    /**
     * 构造函数：生成基本型树状菜单，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为三个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * arguments[ 2 ]=paraValue(参数字段的值) <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     * @param paraColNames
     *            参数字段名称数组（当有多个参数字段时） <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
            String title, 
            String tableName, 
            String colName,
            String textColName, 
            String parentColName, 
            String parentColValue,
            String[] paraColNames) 
        throws Exception {

            setTitle(title);
            setTableName(tableName);
            setColName(colName);
            setTextColName(textColName);
            setParentColName(parentColName);
            setParentColValue(parentColValue);
            setParaColNames(paraColNames);
        }

    /**
     * 构造函数：生成基本型树状菜单，代码字段为数字型，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为两个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
        String title, 
        String tableName, 
        String colName,
        String textColName, 
        String parentColName, 
        int parentColValue)
    throws Exception {

        setTitle(title);
        setTableName(tableName);
        setColName(colName);
        setTextColName(textColName);
        setParentColName(parentColName);
        setIntValue(true);
        setIntParentColValue(parentColValue);
    }

    /**
     * 构造函数：生成基本型树状菜单，代码字段为数字型，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为三个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * arguments[ 2 ]=paraValue(参数字段的值) <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     * @param paraColName
     *            参数字段名称 <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
        String title, 
        String tableName, 
        String colName,
        String textColName, 
        String parentColName, 
        int parentColValue,
        String paraColName) 
    throws Exception {

        setTitle(title);
        setTableName(tableName);
        setColName(colName);
        setTextColName(textColName);
        setParentColName(parentColName);
        setIntValue(true);
        setIntParentColValue(parentColValue);
        setParaColName(paraColName);
    }

    /**
     * 构造函数：生成基本型树状菜单，代码字段为数字型，强制在页面javascript中实现“clickFolder()、clickLeaf()”方法 <br>
     * 方法接收的参数为三个，即 <br>
     * arguments[ 0 ]=colValue <br>
     * arguments[ 1 ]=colText <br>
     * arguments[ 2 ]=paraValue(参数字段的值) <br>
     * <br>
     * 
     * @param title
     *            树状菜单标题 <br>
     * @param tableName
     *            表名 <br>
     * @param colName
     *            主字段名称 <br>
     * @param textColName
     *            主字段显示文字字段 <br>
     * @param parentColName
     *            父字段名称 <br>
     * @param parentColValue
     *            父字段初始值 <br>
     * @param paraColNames
     *            参数字段名称数组（当有多个参数字段时） <br>
     *            Create Date : 2004-1-17 <br>
     */
    public TreeView(
            String title, 
            String tableName, 
            String colName,
            String textColName, 
            String parentColName, 
            int parentColValue,
            String[] paraColNames) 
        throws Exception {

            setTitle(title);
            setTableName(tableName);
            setColName(colName);
            setTextColName(textColName);
            setParentColName(parentColName);
            setIntValue(true);
            setIntParentColValue(parentColValue);
            setParaColNames(paraColNames);
        }

    /**
     * 树状菜单类型：带radio的树状菜单
     */
    public static int RADIO = 1;

    /**
     * 树状菜单类型：带checkbox的树状菜单
     */
    public static int CHECKBOX = 2;

    /**
     * 代码字段是否为int型
     */
    private boolean isIntValue = false;

    /**
     * 带“checkbox”或“radio”的树状菜单被选中的值的集合
     */
    private String[] checkValue = null;

    /**
     * 显示到页面上的html代码
     */
    private StringBuffer html = null;

    /**
     * 表名
     */
    private String tableName = null;

    /**
     * 主字段名称
     */
    private String colName = null;

    /**
     * 主字段显示文字字段
     */
    private String textColName = null;

    /**
     * 父字段名称
     */
    private String parentColName = null;

    /**
     * 父字段初始值
     */
    private String parentColValue = null;

    /**
     * 父字段初始值
     */
    private int intParentColValue = 0;

    /**
     * 菜单项被点击获得的参数字段名称
     */
    private String paraColName = null;

    /**
     * 多个参数字段的数组
     */
    private String[] paraColNames = null;

    /**
     * “是否叶节点”标识字段
     */
    private String isLeafColName = null;

    /**
     * “是叶节点”对应的标识值
     */
    private String isLeaf = null;

    /**
     * “不是叶节点”对应的标识值
     */
    private String isNotLeaf = null;

    /**
     * 显示在菜单最顶层的标题
     */
    private String title = "多级字典";

    private String imgTreeHead = null;

    private String imgPlus = null;

    private String imgMinus = null;

    private String imgFolder = null;

    private String imgNode = null;

    private String imgTrans = null;

    private String whereClause = null;

    private String orderBy = null;
    
    private String itemID = null;
    private String level  = null;
    private String filterWhere = "";

    /**
     * @return String
     */
    public String getColName() {
        return colName;
    }

    /**
     * @return String
     */
    public String getIsLeaf() {
        return isLeaf;
    }

    /**
     * @return String
     */
    public String getIsLeafColName() {
        return isLeafColName;
    }

    /**
     * @return String
     */
    public String getIsNotLeaf() {
        return isNotLeaf;
    }

    /**
     * @return String
     */
    public String getParaColName() {
        return paraColName;
    }

    /**
     * @return String
     */
    public String getParentColName() {
        return parentColName;
    }

    /**
     * @return String
     */
    public String getParentColValue() {
        return parentColValue;
    }

    /**
     * @return String
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the colName.
     * 
     * @param colName
     *            The colName to set
     */
    public void setColName(String colName) {
        this.colName = colName;
    }

    /**
     * Sets the isLeaf.
     * 
     * @param isLeaf
     *            The isLeaf to set
     */
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * Sets the isLeafColName.
     * 
     * @param isLeafColName
     *            The isLeafColName to set
     */
    public void setIsLeafColName(String isLeafColName) {
        this.isLeafColName = isLeafColName;
    }

    /**
     * Sets the isNotLeaf.
     * 
     * @param isNotLeaf
     *            The isNotLeaf to set
     */
    public void setIsNotLeaf(String isNotLeaf) {
        this.isNotLeaf = isNotLeaf;
    }

    /**
     * Sets the paraColName.
     * 
     * @param paraColName
     *            The paraColName to set
     */
    public void setParaColName(String paraColName) {
        this.paraColName = paraColName;
    }

    /**
     * Sets the parentColName.
     * 
     * @param parentColName
     *            The parentColName to set
     */
    public void setParentColName(String parentColName) {
        this.parentColName = parentColName;
    }

    /**
     * Sets the parentColValue.
     * 
     * @param parentColValue
     *            The parentColValue to set
     */
    public void setParentColValue(String parentColValue) {
        this.parentColValue = parentColValue;
    }

    /**
     * Sets the tableName.
     * 
     * @param tableName
     *            The tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return String
     */
    public String getTextColName() {
        return textColName;
    }

    /**
     * Sets the textColName.
     * 
     * @param textColName
     *            The textColName to set
     */
    public void setTextColName(String textColName) {
        this.textColName = textColName;
    }

    /**
     * @return Returns the paraColNames.
     */
    public String[] getParaColNames() {
        return paraColNames;
    }

    /**
     * @param paraColNames
     *            The paraColNames to set.
     */
    public void setParaColNames(String[] paraColNames) {
        this.paraColNames = paraColNames;
    }

    /**
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title
     *            The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String
     */
    public String getImgFolder() {
        return imgFolder;
    }

    /**
     * @return String
     */
    public String getImgMinus() {
        return imgMinus;
    }

    /**
     * @return String
     */
    public String getImgNode() {
        return imgNode;
    }

    /**
     * @return String
     */
    public String getImgPlus() {
        return imgPlus;
    }

    /**
     * @return String
     */
    public String getImgTrans() {
        return imgTrans;
    }

    /**
     * @return String
     */
    public String getImgTreeHead() {
        return imgTreeHead;
    }

    /**
     * Sets the imgFolder.
     * 
     * @param imgFolder
     *            The imgFolder to set
     */
    public void setImgFolder(String imgFolder) {
        this.imgFolder = imgFolder;
    }

    /**
     * Sets the imgMinus.
     * 
     * @param imgMinus
     *            The imgMinus to set
     */
    public void setImgMinus(String imgMinus) {
        this.imgMinus = imgMinus;
    }

    /**
     * Sets the imgNode.
     * 
     * @param imgNode
     *            The imgNode to set
     */
    public void setImgNode(String imgNode) {
        this.imgNode = imgNode;
    }

    /**
     * Sets the imgPlus.
     * 
     * @param imgPlus
     *            The imgPlus to set
     */
    public void setImgPlus(String imgPlus) {
        this.imgPlus = imgPlus;
    }

    /**
     * Sets the imgTrans.
     * 
     * @param imgTrans
     *            The imgTrans to set
     */
    public void setImgTrans(String imgTrans) {
        this.imgTrans = imgTrans;
    }

    /**
     * Sets the imgTreeHead.
     * 
     * @param imgTreeHead
     *            The imgTreeHead to set
     */
    public void setImgTreeHead(String imgTreeHead) {
        this.imgTreeHead = imgTreeHead;
    }

    /**
     * @return String
     */
    public String getWhereClause() {
        return whereClause;
    }

    /**
     * Sets the whereClause.
     * 
     * @param whereClause
     *            The whereClause to set
     */
    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    /**
     * @return Returns the orderBy.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            The orderBy to set.
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return Returns the intParentColValue.
     */
    public int getIntParentColValue() {
        return intParentColValue;
    }

    /**
     * @param intParentColValue
     *            The intParentColValue to set.
     */
    public void setIntParentColValue(int intParentColValue) {
        this.intParentColValue = intParentColValue;
    }

    /**
     * @return Returns the isIntValue.
     */
    public boolean isIntValue() {
        return isIntValue;
    }

    /**
     * @param isIntValue
     *            The isIntValue to set.
     */
    public void setIntValue(boolean isIntValue) {
        this.isIntValue = isIntValue;
    }

    /**
     * 生成javascript中生成树状菜单使用的数组
     * 
     * @throws Exception
     *             Create Date : 2004-1-17
     */
    public void create() throws Exception {
//        Statement stmt = null;
//        ResultSet rs = null;
//        TreeConnection treeConn = new TreeConnection();
        PersistentToolUtil commonPersistent = new PersistentToolUtil();
        
        try {

            if (this.tableName == null)
                throw new Exception("表名不能为空！");
            if (this.colName == null)
                throw new Exception("主字段名称不能为空！");
            if (this.textColName == null)
                throw new Exception("主字段显示文字字段不能为空！");
            if (this.parentColName == null)
                throw new Exception("父字段名称不能为空！");
            //if (this.parentColValue == null)
            //	throw new Exception("父字段初始值不能为空！");

//            treeConn.begin();
//            stmt = treeConn.getConn().createStatement();
            	
            String sql;
            if (isIntValue()) {
                sql = "select " + this.parentColName + ", " + this.colName
                        + ", " + this.textColName;
                sql += this.paraColName == null ? "" : ", " + this.paraColName;
                sql += this.paraColNames == null ? "" : getParaCols();
                sql += " from " + this.tableName;
                if (whereClause != null) {                	
                		sql += " where " + this.colName + " in ( "
                            + this.whereClause + " ) "+filterWhere;
                }
                
                sql += " order by "
                        + (this.orderBy == null ? this.colName : this.orderBy);
            } else {
                sql = "select " + this.parentColName + ", " + this.colName
                        + ", " + this.textColName;
                sql += this.paraColName == null ? "" : ", " + this.paraColName;
                sql += this.paraColNames == null ? "" : getParaCols();
                sql += " from " + this.tableName;
                if (whereClause != null) {
                    sql += " where " + this.colName + " in ( "
                            + this.whereClause + " )"+filterWhere;
                } 
                sql += " order by "
                        + (this.orderBy == null ? this.colName : this.orderBy);
            }
            System.out.println("TreeView的sql语句:" + sql);
            PageRoll roll = new PageRoll();
            roll.setSql(sql.toString());
            
            BaseBean[] baseBeans = commonPersistent.query(roll);
            
//            rs = stmt.executeQuery(sql.toString());

            html = new StringBuffer();
            html.append("<script language='javascript'>\n");
            html.append("<!--\n");
            html.append("var _arrTreeView = new Array();\n");
            for(int i = 0; i < baseBeans.length; i++) {
            	BaseBean curBean = baseBeans[i];
            	
                html.append("_arrTreeView[ _arrTreeView.length ] = new Array(");
                html.append("\"" + curBean.get(this.parentColName) + "\",");
                html.append("\"" + curBean.get(this.colName) + "\",");
                html.append("\"" + curBean.get(this.textColName) + "\"");
                html.append(this.paraColName == null ? "" : ",\"" + curBean.get(this.paraColName) + "\"");
                
                for (int j = 0; this.paraColNames != null && j < this.paraColNames.length; j++) {
                	html.append(",\"" + curBean.get(this.paraColNames[j]) + "\"");
                }
                html.append(");\n");
            }

            html.append(this.imgTreeHead == null ? "" : "_imgTreeHead = \""
                    + this.imgTreeHead + "\"\n");
            html.append(this.imgPlus == null ? "" : "_imgPlus = \""
                    + this.imgPlus + "\"\n");
            html.append(this.imgMinus == null ? "" : "_imgMinus = \""
                    + this.imgMinus + "\"\n");
            html.append(this.imgFolder == null ? "" : "_imgFolder = \""
                    + this.imgFolder + "\"\n");
            html.append(this.imgNode == null ? "" : "_imgNode = \""
                    + this.imgNode + "\"\n");
            html.append(this.imgTrans == null ? "" : "_imgTrans = \""
                    + this.imgTrans + "\"\n");
            if (!isIntValue())
                html.append("var treeView = new TreeView(\""
                        + this.parentColValue + "\", \"" + this.title
                        + "\");\n");
            else
                html.append("var treeView = new TreeView(\""
                        + this.intParentColValue + "\", \"" + this.title
                        + "\");\n");

            html.append("-->\n");
            html.append("</script>");
        } catch (SQLException se) {
            se.printStackTrace();
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } 
//        finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (Throwable e) {
//                System.out.println("TreeView rs close error");
//                e.printStackTrace();
//            }
//
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (Throwable e) {
//                System.out.println("TreeView stmt close error");
//                e.printStackTrace();
//            }
//
//            try {
//                treeConn.free();
//            } catch (Throwable e) {
//                System.out.println("TreeView conn close error");
//                e.printStackTrace();
//            }
//        }
    }

    
//    public void createBySQL( String sql ) throws Exception {
////        Statement stmt = null;
////        ResultSet rs = null;
////        TreeConnection treeConn = new TreeConnection();
//        try {
//
//            
//            
////            treeConn.begin();
////            stmt = treeConn.getConn().createStatement();
//
//            PageRoll roll = new PageRoll();
//            roll.setSql(sql.toString());
//            
//            BaseBean[] baseBeans = ESSPO.commonPersistent.query(roll); 
//            	
//           
//            //System.out.println("TreeView:" + sql);
////            rs = stmt.executeQuery(sql.toString());
//
//            html = new StringBuffer();
//            html.append("<script language='javascript'>\n");
//            html.append("<!--\n");
//            html.append("var _arrTreeView = new Array();\n");
//    
////            ResultSetMetaData rsmd=rs.getMetaData();
////            int col=rsmd.getColumnCount();
//            
//            for(int i = 0; i < baseBeans.length; i++) {
//            	BaseBean curBean = baseBeans[i];
//            	
//            	Object[] colNames = curBean.getHashMap().keySet().toArray();
//            	
//            	for(int j = 0; j < colNames.length; j++) {
//            		html.append(",\"" + curBean.get((String) colNames[j]) + "\"");
//            	}
//            }
// 
//            
//            while (rs.next()) {
//                html.append("_arrTreeView[ _arrTreeView.length ] = new Array(");
//                html.append("\"" + rs.getString(1) + "\",");
//                System.out.print("UPID="+rs.getString(1)+",");
//                html.append("\"" + rs.getString(2) + "\",");
//                System.out.print("ID="+rs.getString(2)+",");
//                html.append("\"" + rs.getString(3) + "\"");
//                System.out.println(""+rs.getString(3));
//                
//                for( int i=4; i<=col; i++){
//                	html.append(",\"" + rs.getString(i) + "\"");
//                }
//                html.append(");\n");
//            }
//
//            html.append(this.imgTreeHead == null ? "" : "_imgTreeHead = \""
//                    + this.imgTreeHead + "\"\n");
//            html.append(this.imgPlus == null ? "" : "_imgPlus = \""
//                    + this.imgPlus + "\"\n");
//            html.append(this.imgMinus == null ? "" : "_imgMinus = \""
//                    + this.imgMinus + "\"\n");
//            html.append(this.imgFolder == null ? "" : "_imgFolder = \""
//                    + this.imgFolder + "\"\n");
//            html.append(this.imgNode == null ? "" : "_imgNode = \""
//                    + this.imgNode + "\"\n");
//            html.append(this.imgTrans == null ? "" : "_imgTrans = \""
//                    + this.imgTrans + "\"\n");
//            
//            if (!isIntValue()){
//    
//                html.append("var treeView = new TreeView(\""
//                        + this.parentColValue + "\", \"" + this.title
//                        + "\");\n");
//            }else{
//        
//                html.append("var treeView = new TreeView(\""
//                        + this.intParentColValue + "\", \"" + this.title
//                        + "\");\n");
//             }
//
//            html.append("-->\n");
//            html.append("</script>");
//        } catch (SQLException se) {
//            se.printStackTrace();
//            throw se;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (Throwable e) {
//                System.out.println("TreeView rs close error");
//                e.printStackTrace();
//            }
//
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (Throwable e) {
//                System.out.println("TreeView stmt close error");
//                e.printStackTrace();
//            }
//
//            try {
//                treeConn.free();
//            } catch (Throwable e) {
//                System.out.println("TreeView conn close error");
//                e.printStackTrace();
//            }
//        }
//    }

    
    /**
     * 根据指定itemid获得树结构
     * @param itemid
     * @param level
     * @author hehao 2005-03-01
     */
    public void create(String typeid,String level,String itemid,String itemval) throws Exception{
        //获得目录树的二维数组
        TreeDic treedic = TreeDicManager.getTreeDic(typeid);
        
        String[][] dicvalue = treedic.getArrayTree(itemid,Integer.parseInt(level),false);
        
        System.out.println("itemval:" + itemval);
        html = new StringBuffer();
        html.append("<script language='javascript'>\n");
        html.append("<!--\n");
        html.append("var _arrTreeView = new Array();\n");
        
        for(int i=0;i<dicvalue.length;i++) {
            String[] temp = dicvalue[i];
		    html.append("_arrTreeView[ _arrTreeView.length ] = new Array(");
		    for( int j=0; j<temp.length; j++ ){
		        System.out.println("value:"+j+":=" + temp[j] + "\n");
		        html.append("\"" + temp[j]);
		        if(j == temp.length -1){
		            html.append("\"" );
		        }
		        else{
		            html.append( "\",");
		        }
		        
		    }
            html.append(");\n");
        }

        html.append(this.imgTreeHead == null ? "" : "_imgTreeHead = \""
                + this.imgTreeHead + "\"\n");
        html.append(this.imgPlus == null ? "" : "_imgPlus = \""
                + this.imgPlus + "\"\n");
        html.append(this.imgMinus == null ? "" : "_imgMinus = \""
                + this.imgMinus + "\"\n");
        html.append(this.imgFolder == null ? "" : "_imgFolder = \""
                + this.imgFolder + "\"\n");
        html.append(this.imgNode == null ? "" : "_imgNode = \""
                + this.imgNode + "\"\n");
        html.append(this.imgTrans == null ? "" : "_imgTrans = \""
                + this.imgTrans + "\"\n");
        if (!isIntValue())
            html.append("var treeView = new TreeView(\""
                    + itemid + "\", \"" + itemval
                    + "\");\n");
        else
            html.append("var treeView = new TreeView(\""
                    + itemid + "\", \"" + itemval
                    + "\");\n");

        html.append("-->\n");
        html.append("</script>");
        
        
        System.out.println(html.toString());
    }

    /**
     * 显示菜单
     * 
     * @param out
     *            Create Date : 2004-1-17
     */
    
    
    public void display(JspWriter out) throws Exception {
        System.out.println(this.html.toString());
    	out.println(this.html.toString());
    }
    
    public void readTreeNode( HttpServletRequest request, JspWriter out ) throws Exception{
    	StringBuffer disTreeNode = new StringBuffer();
    	
    	disTreeNode.append("<iframe name=\"controller_tree\" src=\"" + request.getContextPath() + "/includes/jsp/ifmTreeView.jsp\" frameborder=\"0\" width=\"0\" height=\"0\"></iframe>");
    	
    	HttpSession session = request.getSession();
        HashMap treeNode = (HashMap)session.getAttribute( TreeView.TREENODEPOSITION  );
        if( treeNode != null ){
        	
        	String treeNodeID = (String)treeNode.get( title );
        	
        	disTreeNode.append( "<script language=\"JavaScript\">\n");
        	disTreeNode.append( "<!--\n");    	
        	disTreeNode.append( "try{\n");
        	disTreeNode.append( "    _clickEvent('"+treeNodeID+"');\n");
        	disTreeNode.append( "} catch(e){\n");
        	disTreeNode.append( "//alert('fdsfdsfs'+e);\n");
        	disTreeNode.append( "}\n");
        	disTreeNode.append( "//-->\n");
        	disTreeNode.append( "</script>\n\n");
        	
        }
        System.out.println(disTreeNode.toString());
        out.println(disTreeNode.toString());
    }
    
    
    
    /**
     * 如果有多个参数字段时返回多个参数字段拼成的字符串
     * @return String
     * @throws Exception
     */
    public String getParaCols() throws Exception {
        String paraCols = "";
        for (int i = 0; this.paraColNames != null && i < this.paraColNames.length; i++) {
            paraCols += ", " + this.paraColNames[i];
        }
        return paraCols;
    }
    public String getItemID() {
        return itemID;
    }
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    
	/**
	 * 读取数据库中的数据解析出前菜单，生成xml格式
	 * 读取前两层菜单
	 * 返回basebean数组，在页面解析
	 *
	 */
	public BaseBean[] printlnxml_head() throws Exception {

		PersistentToolUtil commonPersistent = new PersistentToolUtil();

		try {

			if (this.tableName == null)
				throw new Exception("表名不能为空！");
			if (this.colName == null)
				throw new Exception("主字段名称不能为空！");
			if (this.textColName == null)
				throw new Exception("主字段显示文字字段不能为空！");
			if (this.parentColName == null)
				throw new Exception("父字段名称不能为空！");

			String sql;
			if (isIntValue()) {
				sql =
					"select "
						+ this.parentColName
						+ ", "
						+ this.colName
						+ ", "
						+ this.textColName;
				sql += this.paraColName == null ? "" : ", " + this.paraColName;
				sql += this.paraColNames == null ? "" : getParaCols();
				//菜单等级ID
				sql += ", LEVELID ";
				sql += " from " + this.tableName;
				if (whereClause != null) {
					sql += " where "
						+ this.colName
						+ " in ( "
						+ this.whereClause
						+ " )";
					sql = sql + " and P_LEVEL <=2";

				} else {
					sql = sql + " where P_LEVEL <=2 ";

				}
				sql += " order by "
					+ (this.orderBy == null ? this.colName : this.orderBy);
			} else {
				sql =
					"select "
						+ this.parentColName
						+ ", "
						+ this.colName
						+ ", "
						+ this.textColName;
				sql += this.paraColName == null ? "" : ", " + this.paraColName;
				sql += this.paraColNames == null ? "" : getParaCols();
				//菜单等级ID
				sql += ", LEVELID ";
				sql += " from " + this.tableName;
				if (whereClause != null) {
					sql += " where "
						+ this.colName
						+ " in ( "
						+ this.whereClause
						+ " )";
					sql = sql + " and P_LEVEL <= 2";

				} else {
					sql = sql + " where P_LEVEL <= 2";

				}
				sql += " order by "
					+ (this.orderBy == null ? this.colName : this.orderBy);
			}
			//System.out.println("TreeView:" + sql);
			PageRoll roll = new PageRoll();
			roll.setSql(sql.toString());

			BaseBean[] baseBeans_head = commonPersistent.query(roll);

			return baseBeans_head;

		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 查询三层一下功能生成xml格式对象，返回StringBuffer并将其打入页面
	 * @param LEVELID 第二层菜单的功能编号
	 * @return         
	 * @throws Exception
	 */
	public StringBuffer printlnxml_left(String LEVELID) throws Exception {

		PersistentToolUtil commonPersistent = new PersistentToolUtil();

		StringBuffer xml_left = new StringBuffer();

		try {

			if (this.tableName == null)
				throw new Exception("表名不能为空！");
			if (this.colName == null)
				throw new Exception("主字段名称不能为空！");
			if (this.textColName == null)
				throw new Exception("主字段显示文字字段不能为空！");
			if (this.parentColName == null)
				throw new Exception("父字段名称不能为空！");

			String sql;
			if (isIntValue()) {
				sql =
					"select "
						+ this.parentColName
						+ ", "
						+ this.colName
						+ ", "
						+ this.textColName;
				sql += this.paraColName == null ? "" : ", " + this.paraColName;
				sql += this.paraColNames == null ? "" : getParaCols();
				// LEVELID 菜单等级ID
				sql += ", LEVELID ";
				sql += " from " + this.tableName;
				if (whereClause != null) {
					sql += " where "
						+ this.colName
						+ " in ( "
						+ this.whereClause
						+ " )";
					sql =
						sql
							+ " and LEVELID like '"
							+ LEVELID
							+ "%'"
							+ " and LEVELID <> '"
							+ LEVELID
							+ "'";
				} else {
					sql =
						sql
							+ " where LEVELID like '"
							+ LEVELID
							+ "%'"
							+ " and LEVELID <> '"
							+ LEVELID
							+ "'";
				}
				sql += " order by "
					+ (this.orderBy == null ? this.colName : this.orderBy);
			} else {
				sql =
					"select "
						+ this.parentColName
						+ ", "
						+ this.colName
						+ ", "
						+ this.textColName;
				sql += this.paraColName == null ? "" : ", " + this.paraColName;
				sql += this.paraColNames == null ? "" : getParaCols();
				//LEVELID 菜单等级ID
				sql += ", LEVELID ";
				sql += " from " + this.tableName;
				if (whereClause != null) {
					sql += " where "
						+ this.colName
						+ " in ( "
						+ this.whereClause
						+ " )";
					sql =
						sql
							+ " and LEVELID like '"
							+ LEVELID
							+ "%'"
							+ " and LEVELID <> '"
							+ LEVELID
							+ "'";
				} else {
					sql =
						sql
							+ " where LEVELID like '"
							+ LEVELID
							+ "%'"
							+ " and LEVELID <> '"
							+ LEVELID
							+ "'";
				}
				sql += " order by "
					+ (this.orderBy == null ? this.colName : this.orderBy);
			}

			PageRoll roll = new PageRoll();
			roll.setSql(sql.toString());

			BaseBean[] baseBeans_left = commonPersistent.query(roll);
			//构造DOM对象
			Element Root = new Element("Root");

			Document doc = new Document(Root);

			//遍历basebean填充DOM对象
			for (int i = 0; i < baseBeans_left.length; i++) {
				BaseBean curBean = baseBeans_left[i];
				//判断当前的basebean是否是三级菜单
				if (curBean.get("LEVELID").toString().trim().length() == 9) {
					//插入心节点
					Element TreeNode = new Element("TreeNode");
					Root.addContent(TreeNode);
					//属性Title
					TreeNode.setAttribute(
						"Title",
						curBean.get("FUNCNAME").toString());
					//属性Href,当URL为空时不添加该属性
					String hrefStr = curBean.get("URL").toString();
					if (hrefStr.trim().length() > 0) {
						TreeNode.setAttribute("Href", hrefStr);
					}
					//属性LEVELID
					String LEVELID_str = curBean.get("LEVELID").toString().trim();
					TreeNode.setAttribute("LEVELID", LEVELID_str);
					TreeNode =
						creatxml(baseBeans_left, TreeNode, i, 4, LEVELID_str);
				}
			}
			//开始将构造好的DOM对象转换成字符串打入HTML页面
			xml_left.append("<xml ID=\"xml_div\" NAME=\"xml_div\">");
			//DOM对象不为空，开始转换字符串
			if (doc != null) {
				String temp_xml = null;
				//取出节点树 list
				List list = doc.getRootElement().getContent();
				//生成xml的输出流
				XMLOutputter xmlOut = new XMLOutputter();
				temp_xml = xmlOut.outputString(doc);
				//删除xml字符串的xml声明行
				int pos = temp_xml.indexOf("\n");
				temp_xml = temp_xml.substring(pos + 1);
				xml_left.append(temp_xml);
			}
			xml_left.append("</xml>");

			return xml_left;
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 *  构造DOM对象（4级以下菜单结构）
	 * @param baseBeans_head 结果BASEBEAN
	 * @param TreeNode 上层节点
	 * @param number BASEBEAN的遍历进度
	 * @param frame  目前层次
	 * @param LEVELID_str  父节点的功能代码
	 * @return
	 */
	public Element creatxml(
		BaseBean[] baseBeans_head,
		Element TreeNode,
		int number,
		int frame,
		String LEVELID_str) {
		//遍历basebean构造子节点
		for (int j = number; j < baseBeans_head.length; j++) {
			BaseBean curBean = baseBeans_head[j];
			int length = 3 * frame;
			String child_LEVELID = curBean.get("LEVELID").toString().trim();
			//判断当前层次的功能代码长度<(curBean.get("LEVELID").toString().length() == length)为true>，及是否时当前节点的子节点<(child_LEVELID.startsWith(LEVELID_str))为true>
			if ((curBean.get("LEVELID").toString().trim().length() == length)
				&& (child_LEVELID.startsWith(LEVELID_str))) {
				//生成子节点
				Element TreeNode_child = new Element("TreeNode");
				TreeNode.addContent(TreeNode_child);
				TreeNode_child.setAttribute(
					"Title",
					curBean.get("FUNCNAME").toString());
				String hrefStr = curBean.get("URL").toString();
				if (hrefStr.trim().length() > 0) {
					TreeNode_child.setAttribute("Href", hrefStr);
				}

				TreeNode_child.setAttribute("LEVELID", child_LEVELID);
				//递归构造下一层孩子节点 
				int new_frame = frame + 1;
				TreeNode_child =
					creatxml(
						baseBeans_head,
						TreeNode_child,
						j,
						new_frame,
						child_LEVELID);
			}
		}

		return TreeNode;

	}


	public String getFilterWhere() {
		return filterWhere;
	}


	public void setFilterWhere(String filterWhere) {
		this.filterWhere = filterWhere;
	}

}