package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysOrgDao;
import com.secpro.platform.monitoring.manage.entity.SysOrg;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysOrgDaoImpl")
public class SysOrgDaoImpl extends BaseDao implements SysOrgDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List getOrgTreeByOrgId(String orgId){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		List org=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			//rs=sta.executeQuery("SELECT s.id,s.org_name,s.parent_org_id  FROM sys_org s START WITH ID = "+orgId+" CONNECT BY PRIOR parent_org_id = ID");
			rs=sta.executeQuery("SELECT s.id,s.org_name,s.parent_org_id FROM sys_org s START WITH ID = "+orgId+" CONNECT BY parent_org_id = PRIOR ID");
			while(rs.next()){
				SysOrg o=new SysOrg();
				o.setId(rs.getLong(1));
				o.setOrgName(rs.getString(2));
				o.setParentOrgId(rs.getLong(3));
				org.add(o);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close(con,sta,rs);
		}
		return org;
	}
	
}
