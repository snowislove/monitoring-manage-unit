package com.secpro.platform.monitoring.manage.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.util.service.IService;
import com.secpro.platform.monitoring.manage.util.service.ServiceInfo;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author baiyanwei
 * @date 2013-4-1 负责支持名种数据查询
 */
@Entity
@ServiceInfo(description = "lookup resource from cmdb")
public class DataBaseOperationService implements IService {
	@ManyToOne
	private static PlatformLogger _logger = PlatformLogger.getLogger(DataBaseOperationService.class);
	// 数据库连接池
	private ComboPooledDataSource cpds = null;

	public void start() throws Exception {
		cpds = new ComboPooledDataSource();
	}

	public void stop() throws Exception {
		cpds.close();
		cpds = null;
	}

	public Connection getConnection() throws SQLException {
		if (cpds == null) {
			return null;
		}
		return cpds.getConnection();
	}

	/**
	 * 取得需要同步数据的对象内容
	 * 
	 * @param appName
	 * @param taskName
	 * @return
	 */
	public List<String> getSynchronizedTaskRecord(String appName, String taskName) {
		if (appName == null || appName.length() == 0 || taskName == null || taskName.length() == 0) {
			return null;
		}
		Connection con = null;
		Statement select = null;
		ResultSet result = null;
		List<String> dataList = new ArrayList<String>();
		try {
			con = cpds.getConnection();
			String sql = "SELECT scode FROM HB_SYIF_TASK_DEAL WHERE is_synchronized='n' and target_app='" + appName + "' AND TASK_NAME='" + taskName + "'";
			_logger.debug(">>>>SQL:"+sql);
			select = con.createStatement();
			result = select.executeQuery(sql);
			while (result.next()) {
				dataList.add(result.getString(1));
			}
		} catch (Exception e) {
			_logger.exception("getSynchronizedTaskObjectSCode:", e);
		} finally {
			if (result != null) {
				try {
					result.close();
					result = null;
				} catch (Exception e) {
				}
			}
			if (select != null) {
				try {
					select.close();
					select = null;
				} catch (Exception e) {
				}
			}
			if (con != null) {
				try {
					con.close();
					con = null;
				} catch (Exception e) {
				}
			}
		}
		return dataList;
	}

	/**
	 * @param taskList
	 * @return
	 */
	public boolean updateTaskRecord(List<String> taskList, String appName, String taskName) {
		if (taskList == null || taskList.isEmpty() || appName == null || appName.length() == 0 || taskName == null || taskName.length() == 0) {
			return false;
		}
		Connection con = null;
		Statement update = null;
		List<String> dataList = new ArrayList<String>();
		try {
			con = cpds.getConnection();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("UPDATE HB_SYIF_TASK_DEAL SET is_synchronized='Y',synchronized_time=").append(System.currentTimeMillis())
					.append(" WHERE is_synchronized='n' and target_app='").append(appName).append("' AND task_name='").append(taskName).append("' AND ")
					.append("scode IN (");
			for(int i=0;i<taskList.size();i++){
				sqlBuffer.append("'").append(taskList.get(i)).append("'");
				if((i+1)<taskList.size()){
					sqlBuffer.append(",");
				}
			}
			sqlBuffer.append(")");
			_logger.debug("SQL:"+sqlBuffer);
			update = con.createStatement();
			update.execute(sqlBuffer.toString());
			update.getUpdateCount();
		} catch (Exception e) {
			_logger.exception("getSynchronizedTaskObjectSCode:", e);
		} finally {
			if (update != null) {
				try {
					update.close();
					update = null;
				} catch (Exception e) {
				}
			}
			if (con != null) {
				try {
					con.close();
					con = null;
				} catch (Exception e) {
				}
			}
		}
		return true;
	}
}
