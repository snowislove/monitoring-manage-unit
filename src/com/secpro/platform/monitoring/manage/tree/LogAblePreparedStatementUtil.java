package com.secpro.platform.monitoring.manage.tree;
import com.vandagroup.common.util.StringUtil;
import com.vandagroup.engine.util.WorkflowLog;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class LogAblePreparedStatementUtil
  implements PreparedStatement
{
  private ArrayList<Object> parameterValues;
  private String sqlTemplate;
  private PreparedStatement wrappedStatement;

  public LogAblePreparedStatementUtil(Connection connection, String sql)
    throws SQLException
  {
    this.wrappedStatement = connection.prepareStatement(sql);
    this.sqlTemplate = sql;
    this.parameterValues = new ArrayList();
  }

  public LogAblePreparedStatementUtil(Connection connection, String sql, int ResultSetType, int resultSetConcurrency) throws SQLException
  {
    this.wrappedStatement = connection.prepareStatement(sql, ResultSetType, resultSetConcurrency);
    this.sqlTemplate = sql;
    this.parameterValues = new ArrayList();
  }

  public void addBatch()
    throws SQLException
  {
    this.wrappedStatement.addBatch();
  }

  public void addBatch(String sql)
    throws SQLException
  {
    this.wrappedStatement.addBatch(sql);
  }

  public void cancel()
    throws SQLException
  {
    this.wrappedStatement.cancel();
  }

  public void clearBatch()
    throws SQLException
  {
    this.wrappedStatement.clearBatch();
  }

  public void clearParameters()
    throws SQLException
  {
    this.wrappedStatement.clearParameters();
  }

  public void clearWarnings()
    throws SQLException
  {
    this.wrappedStatement.clearWarnings();
  }

  public void close()
    throws SQLException
  {
    this.wrappedStatement.close();
  }

  private void log(String s)
  {
    WorkflowLog.userWrite(s);
  }

  public boolean execute() throws SQLException
  {
    long curTime = System.currentTimeMillis();
    

    boolean result = this.wrappedStatement.execute();
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public boolean execute(String sql)
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
    log("SQL4=================> 将要执行的sql={  " + StringUtil.convertCodeSet(sql) + "  }");
    boolean result = this.wrappedStatement.execute(sql);
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public int[] executeBatch()
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
    
    int[] result = this.wrappedStatement.executeBatch();
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public ResultSet executeQuery()
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
    ResultSet result = this.wrappedStatement.executeQuery();
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public ResultSet executeQuery(String sql)
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
    
    ResultSet result = this.wrappedStatement.executeQuery(sql);
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public int executeUpdate()
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
   
    int result = this.wrappedStatement.executeUpdate();
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public int executeUpdate(String sql)
    throws SQLException
  {
    long curTime = System.currentTimeMillis();
    
    int result = this.wrappedStatement.executeUpdate(sql);
    curTime = System.currentTimeMillis() - curTime;

    return result;
  }

  public Connection getConnection()
    throws SQLException
  {
    return this.wrappedStatement.getConnection();
  }

  public int getFetchDirection()
    throws SQLException
  {
    return this.wrappedStatement.getFetchDirection();
  }

  public int getFetchSize()
    throws SQLException
  {
    return this.wrappedStatement.getFetchSize();
  }

  public int getMaxFieldSize()
    throws SQLException
  {
    return this.wrappedStatement.getMaxFieldSize();
  }

  public int getMaxRows()
    throws SQLException
  {
    return this.wrappedStatement.getMaxRows();
  }

  public ResultSetMetaData getMetaData()
    throws SQLException
  {
    return this.wrappedStatement.getMetaData();
  }

  public boolean getMoreResults()
    throws SQLException
  {
    return this.wrappedStatement.getMoreResults();
  }

  public int getQueryTimeout()
    throws SQLException
  {
    return this.wrappedStatement.getQueryTimeout();
  }

  public ResultSet getResultSet()
    throws SQLException
  {
    return this.wrappedStatement.getResultSet();
  }

  public int getResultSetConcurrency()
    throws SQLException
  {
    return this.wrappedStatement.getResultSetConcurrency();
  }

  public int getResultSetType()
    throws SQLException
  {
    return this.wrappedStatement.getResultSetType();
  }

  public int getUpdateCount()
    throws SQLException
  {
    return this.wrappedStatement.getUpdateCount();
  }

  public SQLWarning getWarnings()
    throws SQLException
  {
    return this.wrappedStatement.getWarnings();
  }

  public void setArray(int i, Array x)
    throws SQLException
  {
    this.wrappedStatement.setArray(i, x);
    saveQueryParamValue(i, x);
  }

  public void setAsciiStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    this.wrappedStatement.setAsciiStream(parameterIndex, x, length);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setBigDecimal(int parameterIndex, BigDecimal x)
    throws SQLException
  {
    this.wrappedStatement.setBigDecimal(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setBinaryStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    this.wrappedStatement.setBinaryStream(parameterIndex, x, length);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setBlob(int i, Blob x)
    throws SQLException
  {
    this.wrappedStatement.setBlob(i, x);
    saveQueryParamValue(i, x);
  }

  public void setBoolean(int parameterIndex, boolean x)
    throws SQLException
  {
    this.wrappedStatement.setBoolean(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Boolean(x));
  }

  public void setByte(int parameterIndex, byte x)
    throws SQLException
  {
    this.wrappedStatement.setByte(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Integer(x));
  }

  public void setBytes(int parameterIndex, byte[] x)
    throws SQLException
  {
    this.wrappedStatement.setBytes(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setCharacterStream(int parameterIndex, Reader reader, int length)
    throws SQLException
  {
    this.wrappedStatement.setCharacterStream(parameterIndex, reader, length);
    saveQueryParamValue(parameterIndex, reader);
  }

  public void setClob(int i, Clob x)
    throws SQLException
  {
    this.wrappedStatement.setClob(i, x);
    saveQueryParamValue(i, x);
  }

  public void setCursorName(String name)
    throws SQLException
  {
    this.wrappedStatement.setCursorName(name);
  }

  public void setDate(int parameterIndex, java.sql.Date x)
    throws SQLException
  {
    this.wrappedStatement.setDate(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
    throws SQLException
  {
    this.wrappedStatement.setDate(parameterIndex, x, cal);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setDouble(int parameterIndex, double x)
    throws SQLException
  {
    this.wrappedStatement.setDouble(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Double(x));
  }

  public void setEscapeProcessing(boolean enable)
    throws SQLException
  {
    this.wrappedStatement.setEscapeProcessing(enable);
  }

  public void setFetchDirection(int direction)
    throws SQLException
  {
    this.wrappedStatement.setFetchDirection(direction);
  }

  public void setFetchSize(int rows)
    throws SQLException
  {
    this.wrappedStatement.setFetchSize(rows);
  }

  public void setFloat(int parameterIndex, float x)
    throws SQLException
  {
    this.wrappedStatement.setFloat(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Float(x));
  }

  public void setInt(int parameterIndex, int x)
    throws SQLException
  {
    this.wrappedStatement.setInt(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Integer(x));
  }

  public void setLong(int parameterIndex, long x)
    throws SQLException
  {
    this.wrappedStatement.setLong(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Long(x));
  }

  public void setMaxFieldSize(int max)
    throws SQLException
  {
    this.wrappedStatement.setMaxFieldSize(max);
  }

  public void setMaxRows(int max)
    throws SQLException
  {
    this.wrappedStatement.setMaxRows(max);
  }

  public void setNull(int parameterIndex, int sqlType)
    throws SQLException
  {
    this.wrappedStatement.setNull(parameterIndex, sqlType);
    saveQueryParamValue(parameterIndex, null);
  }

  public void setNull(int paramIndex, int sqlType, String typeName)
    throws SQLException
  {
    this.wrappedStatement.setNull(paramIndex, sqlType, typeName);
    saveQueryParamValue(paramIndex, null);
  }

  public void setObject(int parameterIndex, Object x)
    throws SQLException
  {
    this.wrappedStatement.setObject(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setObject(int parameterIndex, Object x, int targetSqlType)
    throws SQLException
  {
    this.wrappedStatement.setObject(parameterIndex, x, targetSqlType);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
    throws SQLException
  {
    this.wrappedStatement.setObject(parameterIndex, x, targetSqlType, scale);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setQueryTimeout(int seconds)
    throws SQLException
  {
    this.wrappedStatement.setQueryTimeout(seconds);
  }

  public void setRef(int i, Ref x)
    throws SQLException
  {
    this.wrappedStatement.setRef(i, x);
    saveQueryParamValue(i, x);
  }

  public void setShort(int parameterIndex, short x)
    throws SQLException
  {
    this.wrappedStatement.setShort(parameterIndex, x);
    saveQueryParamValue(parameterIndex, new Integer(x));
  }

  public void setString(int parameterIndex, String x)
    throws SQLException
  {
    this.wrappedStatement.setString(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setTime(int parameterIndex, Time x)
    throws SQLException
  {
    this.wrappedStatement.setTime(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setTime(int parameterIndex, Time x, Calendar cal)
    throws SQLException
  {
    this.wrappedStatement.setTime(parameterIndex, x, cal);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setTimestamp(int parameterIndex, Timestamp x)
    throws SQLException
  {
    this.wrappedStatement.setTimestamp(parameterIndex, x);
    saveQueryParamValue(parameterIndex, x);
  }

  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
    throws SQLException
  {
    this.wrappedStatement.setTimestamp(parameterIndex, x, cal);
    saveQueryParamValue(parameterIndex, x);
  }

  /** @deprecated */
  public void setUnicodeStream(int parameterIndex, InputStream x, int length)
    throws SQLException
  {
    this.wrappedStatement.setUnicodeStream(parameterIndex, x, length);
    saveQueryParamValue(parameterIndex, x);
  }



  private void saveQueryParamValue(int position, Object obj)
  {
    String strValue;
    if (((obj instanceof String)) || ((obj instanceof java.util.Date)))
    {
      strValue = "'" + obj + "'";
    }
    else
    {
      if (obj == null)
      {
        strValue = "null";
      }
      else {
        strValue = obj.toString();
      }

    }

    while (position >= this.parameterValues.size()) {
      this.parameterValues.add(null);
    }

    this.parameterValues.set(position, strValue);
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    return null;
  }

  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    return false;
  }

  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    return false;
  }

  public boolean getMoreResults(int current) throws SQLException {
    return false;
  }

  public boolean execute(String sql, String[] columnNames) throws SQLException {
    return false;
  }

  public int getResultSetHoldability() throws SQLException {
    return -1;
  }

  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    return -1;
  }

  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    return -1;
  }

  public void setURL(int parameterIndex, URL x) throws SQLException
  {
  }

  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    return -1;
  }

  public ParameterMetaData getParameterMetaData() throws SQLException {
    return null;
  }

public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public void setPoolable(boolean poolable) throws SQLException {
	// TODO Auto-generated method stub
	
}

public boolean isPoolable() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public void setRowId(int parameterIndex, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNString(int parameterIndex, String value) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNCharacterStream(int parameterIndex, Reader value, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNClob(int parameterIndex, NClob value) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setBlob(int parameterIndex, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setAsciiStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setBinaryStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setCharacterStream(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setAsciiStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setBinaryStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setCharacterStream(int parameterIndex, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNCharacterStream(int parameterIndex, Reader value)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setBlob(int parameterIndex, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

public void setNClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}
}