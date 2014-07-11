package com.secpro.platform.monitoring.manage.tree;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;



import com.secpro.platform.monitoring.manage.util.DBConfig;
import com.vandagroup.common.bean.BaseBean;
import com.vandagroup.common.util.ReadProperties;
import com.vandagroup.common.util.StringUtil;
import com.vandagroup.hibernate.databean.PageRoll;
import com.vandagroup.pool.DataPool;

public class PersistentToolUtil {

	public static String ADDCONTROL = "add";

	public static String DELETECONTROL = "delete";

	public static String UPDATECONTROL = "update";

	public static String EXECUTECONTROL = "execute";

	public BaseBean[] query(PageRoll roll) throws Exception {
		BaseBean[] baseBeans = (BaseBean[]) null;
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet resultset = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		conn = getConnection();
		try {
			int dbType = getDBStyle();
			if ((roll.getTotalCount() == 0)
					&& (roll.getPageSize() != 2147483647)) {
				String tempsql = roll.getSql();

				String countSQL = "SELECT COUNT(*) AS TOTALCOUNT from ("
						+ tempsql + ") conuntsql";

				if (dbType != 9) {
					stm = conn.prepareStatement(countSQL, 1004, 1008);
				} else {
					tempsql = tempsql.replaceAll("with UR", "");
					countSQL = "SELECT COUNT(*) AS TOTALCOUNT from (" + tempsql
							+ ") conuntsql with UR";
					stm = conn.prepareStatement(countSQL);
				}
				System.out.println(countSQL);
				rs = stm.executeQuery();

				boolean b1 = rs.next();
				int total = 0;
				if (dbType != 9) {
					if (!rs.isBeforeFirst()) {
						total = rs.getInt("TOTALCOUNT");
					}
				} else if (b1) {
					total = rs.getInt("TOTALCOUNT");
				}
				roll.setTotalCount(total);
			} else if (roll.getPageSize() == 2147483647) {
				roll.setCurrentPage(1);
			}
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
					throw new Exception("操作数据库错误", ex);
				}
			if (stm != null) {
				stm.close();
			}
			String sql = getSQL(dbType, roll.getSql(), roll.getTopNum());
			if (dbType != 9)
				stm = new LogAblePreparedStatementUtil(conn, sql, 1004, 1008);
			else {
				stm = new LogAblePreparedStatementUtil(conn, sql);
			}

			resultset = stm.executeQuery();
			rsmd = resultset.getMetaData();

			checkColumnName(rsmd);
			int columnCount = rsmd.getColumnCount();
			Vector list = new Vector();
			String columnName = "";

			int startPosition = roll.getStartPosition();

			int curSN = 0;

			String[] colNames = new String[columnCount];
			String[] colTypeNames = new String[columnCount];
			for (int j = 1; j <= columnCount; j++) {
				colNames[(j - 1)] = rsmd.getColumnName(j);
				colTypeNames[(j - 1)] = rsmd.getColumnTypeName(j);
			}
			roll.setColNames(colNames);
			roll.setColTypeNames(colTypeNames);
			while (resultset.next()) {
				curSN++;
				if (curSN < startPosition) {
					continue;
				}
				if (curSN > roll.getPageSize() * roll.getCurrentPage()) {
					break;
				}
				BaseBean baseBean = new BaseBean();
				baseBean.setColNames(colNames);
				baseBean.setColTypeNames(colTypeNames);
				baseBean.setLength(columnCount);
				for (int j = 1; j <= columnCount; j++) {
					columnName = rsmd.getColumnName(j);

					baseBean.put(
							columnName.toLowerCase(),
							getResultValue(columnName, rsmd.getColumnType(j),
									resultset));
				}
				list.add(baseBean);
			}
			baseBeans = new BaseBean[list.size()];
			if (roll.getPageSize() == 2147483647) {
				roll.setTotalCount(list.size());
			}
			list.copyInto(baseBeans);

			if (list.size() > 5000)
				System.out.println("VZF-WARN(" + list.size() + ") sql:" + sql);
		} catch (Exception ex1) {
			ex1.printStackTrace();
			throw ex1;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				System.out.println("数据库连接关闭失败");
				ex.printStackTrace();
			}

			if (resultset != null)
				try {
					resultset.close();
				} catch (SQLException ex) {
					throw new Exception("操作数据库错误", ex);
				}
			if (stm != null)
				try {
					stm.close();
				} catch (SQLException ex) {
					throw new Exception("操作数据库错误", ex);
				}
		}
		return baseBeans;
	}

	public Connection getConnection() throws Exception {
			Connection conn=null;
			try{	
				Class.forName(DBConfig.DRIVER);
				conn=DriverManager.getConnection(DBConfig.URL,DBConfig.USERNAME,DBConfig.PASSWORD);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} 
		return conn;
	}

	private Vector checkColumnName(ResultSetMetaData rsmd) throws SQLException,
			Exception {
		Vector colNames = new Vector();
		for (int m = 1; m <= rsmd.getColumnCount(); m++) {
			if (colNames.contains(rsmd.getColumnName(m))) {
				throw new Exception("您传入的SQL语句有重名的列，请改写您的SQL语句！");
			}
			colNames.add(rsmd.getColumnName(m).toLowerCase());
		}
		return colNames;
	}

	private String getAddSQL(BaseBean curBean) {
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("INSERT INTO ");
		tempSQL.append(curBean.getTableName());

		HashMap ht = curBean.getHashMap();
		Iterator iterator = ht.keySet().iterator();
		String costStr = "";
		String columnStr = "(";
		while (iterator.hasNext()) {
			String columnName = (String) iterator.next();
			Object value = ht.get(columnName);
			if (value != null) {
				costStr = costStr + " ?,";
				columnStr = columnStr + columnName + ",";
			}
		}

		if (costStr.length() > 0) {
			costStr = costStr.substring(0, costStr.lastIndexOf(","));
		}
		if (columnStr.length() > 0) {
			columnStr = columnStr.substring(0, columnStr.lastIndexOf(","));
		}

		tempSQL.append(columnStr);
		tempSQL.append(")");
		tempSQL.append(" VALUES(");

		tempSQL.append(costStr);
		tempSQL.append(")");
		return tempSQL.toString();
	}

	public int getDBStyle() throws SQLException {
		return 1;
	}

	private Object getResultValue(String colName, int sqlType, ResultSet rs)
			throws SQLException, Exception {
		ObjectInputStream ois = null;
		InputStream istr = null;
		switch (sqlType) {
		case -4:
			istr = rs.getBinaryStream(colName);
			ois = new ObjectInputStream(istr);
			return (DataPool) ois.readObject();
		case 2004:
			if (getDBStyle() == 1) {
				istr = rs.getBinaryStream(colName);
				ois = new ObjectInputStream(istr);
				Object ob = ois.readObject();
				return (DataPool) ob;
			}

			Blob blob = rs.getBlob(colName);

			istr = blob.getBinaryStream();

			ois = new ObjectInputStream(istr);

			return (DataPool) ois.readObject();
		case 2005:
			if (getDBStyle() == 1) {
				Clob clob = rs.getClob(colName);
				if (ReadProperties.getString("is_gbk").equals("n")) {
					InputStream is = clob.getAsciiStream();
					DataInputStream datais = new DataInputStream(is);

					StringBuffer buffer = new StringBuffer();
					String temp = "";
					while ((temp = datais.readLine()) != null) {
						buffer.append(temp);
					}
					return StringUtil.convertCodeSet(buffer.toString());
				}

				Reader clobReader = clob.getCharacterStream();
				BufferedReader bReader = new BufferedReader(clobReader);
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while ((line = bReader.readLine()) != null)
					buffer.append(line).append("\n");
				return buffer.toString();
			}

			if (getDBStyle() == 9) {
				return StringUtil.convertCodeSet(rs.getString(colName));
			}
			Clob clob = rs.getClob(colName);

			if (ReadProperties.getString("is_gbk").equals("n")) {
				InputStream is = clob.getAsciiStream();
				DataInputStream datais = new DataInputStream(is);

				StringBuffer buffer = new StringBuffer();
				String temp = "";
				while ((temp = datais.readLine()) != null) {
					buffer.append(temp);
				}
				return StringUtil.convertCodeSet(buffer.toString());
			}

			Reader clobReader = clob.getCharacterStream();
			BufferedReader bReader = new BufferedReader(clobReader);
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = bReader.readLine()) != null)
				buffer.append(line).append("\n");
			return buffer.toString();
		case -5:
			return Long.valueOf(String.valueOf(rs.getLong(colName)));
		case -7:
			return String.valueOf(rs.getBoolean(colName));
		case 1:
			return rs.getString(colName) == null ? "" : StringUtil
					.convertCodeSet(rs.getString(colName));
		case 91:
			return rs.getDate(colName);
		case 3:
			return rs.getBigDecimal(colName);
		case 8:
			return Double.valueOf(String.valueOf(rs.getDouble(colName)));
		case 6:
			return Float.valueOf(String.valueOf(rs.getFloat(colName)));
		case 4:
			return Integer.valueOf(String.valueOf(rs.getInt(colName)));
		case -1:
			return StringUtil.convertCodeSet(rs.getString(colName));
		case 2:
			if (getDBStyle() == 1) {
				if (rs.getString(colName) == null)
					return new Integer(0);
				try {
					if ((colName.equalsIgnoreCase("taskno"))
							|| (colName.equalsIgnoreCase("INSTANCEID"))) {
						return new Long(rs.getLong(colName));
					}

					if (colName.equalsIgnoreCase("PULLAMT")) {
						return rs.getString(colName);
					}
					if (rs.getString(colName).indexOf(".") == -1) {
						return new Integer(rs.getInt(colName));
					}
					return rs.getBigDecimal(colName);
				} catch (SQLException e) {
					return new Long(rs.getLong(colName));
				}
			}
			return rs.getBigDecimal(colName);
		case 7:
			return rs.getBigDecimal(colName);
		case 5:
			return Integer.valueOf(String.valueOf(rs.getInt(colName)));
		case 92:
			return rs.getTime(colName) != null ? rs.getTime(colName)
					: new Time(0L);
		case 93:
			return rs.getTimestamp(colName) != null ? rs.getTimestamp(colName)
					: new Timestamp(0L);
		case -6:
			return Integer.valueOf(String.valueOf(rs.getShort(colName)));
		case 12:
			return rs.getString(colName) == null ? "" : rs.getString(colName);
		}
		return "";
	}

	private String getSQL(int DATABASETYPE, String sql, int maxrows) {
		String temp = "";
		if (maxrows == 0) {
			return sql;
		}

		switch (DATABASETYPE) {
		case 0:
		case 1:
			if (sql.toLowerCase().indexOf("ROWNUM") > 0) {
				break;
			}
			temp = sql + " ROWNUM =" + maxrows;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 10:
			temp = sql;
			break;
		case 3:
			temp = sql;
			break;
		case 2:
			if (sql.toLowerCase().indexOf("TOP") > 0) {
				break;
			}
			if (sql.toLowerCase().indexOf("DISTINCT") >= 0) {
				temp = "SELECT DISTINCT top "
						+ maxrows
						+ " "
						+ sql.substring(sql.toLowerCase().indexOf("DISTINCT") + 8);
			} else {
				if (sql.toLowerCase().indexOf("SELECT") < 0)
					break;
				temp = sql.substring(0, 6);
				temp = temp + " top " + maxrows + " " + sql.substring(6);
			}
			break;
		case 9:
			if (sql.toLowerCase().indexOf("FETCH FIRST") > 0) {
				break;
			}
			temp = sql + " FETCH FIRST " + maxrows + " ROWS ONLY";
		}

		return temp;
	}

}