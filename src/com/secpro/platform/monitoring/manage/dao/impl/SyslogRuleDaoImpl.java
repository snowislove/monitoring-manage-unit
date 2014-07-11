package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SyslogRuleDao;
import com.secpro.platform.monitoring.manage.entity.RawSyslogHit;
import com.secpro.platform.monitoring.manage.entity.SyslogBean;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SyslogRuleDaoImpl")
public class SyslogRuleDaoImpl extends BaseDao implements SyslogRuleDao{
	
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public boolean syslogRuleSave(SyslogBean syslogB) {
		if(syslogB==null){
			return false;
		}
		Connection conn=null;
		Statement deleteRule = null;
		PreparedStatement insertRule = null;
		PreparedStatement insertRuleMapping = null;
		try {
			conn= dataSource.getConnection();
			conn.setAutoCommit(false);
			deleteRule=conn.createStatement();
			String typeCode=syslogB.getTypeCode();
			String deleteRuleSql = "delete from syslog_rule where type_code='"+typeCode+"'";
			deleteRule.execute(deleteRuleSql);
			String deleteRuleMappingSql="delete from syslog_rule_mapping where type_code='"+typeCode+"'";
			deleteRule.execute(deleteRuleMappingSql);
			//String sql = "insert into raw_config_policy(cdate,config_policy_info,res_id,task_code) values(?,?,?,?)";
			String insertRuleSql="insert into syslog_rule(id,rule_key,rule_value,check_num,check_action,type_code) values(syslog_rule_seq.nextval,?,?,?,?,?)";
			insertRule=conn.prepareStatement(insertRuleSql);
			Map<String,String> regexs=syslogB.getRegexs();
			int ruleBatch=0;
			for(String name:regexs.keySet()){
				insertRule.setString(1, name);
				insertRule.setString(2, regexs.get(name));
				insertRule.setInt(3, syslogB.getCheckNum());
				insertRule.setString(4, syslogB.getCheckAction());
				insertRule.setString(5, typeCode);
				insertRule.addBatch();
				ruleBatch++;
				if(ruleBatch==50){
					insertRule.executeBatch();
					ruleBatch=0;
				}
			}
			if(ruleBatch>0){
				insertRule.executeBatch();
			}
			String insertRuleMappingSql="insert into syslog_rule_mapping(id,rule_key,rule_value,rule_name,type_code) values(syslog_rule_mapping_seq.nextval,?,?,?,?)";
			insertRuleMapping=conn.prepareStatement(insertRuleMappingSql);
			Map<String,Map<String,String>> ruleMapping=syslogB.getRuleMapping();
			int ruleMappingBatch=0;
			for(String name:ruleMapping.keySet()){
				Map<String,String> valueMapping=ruleMapping.get(name);
				for(String key:valueMapping.keySet()){
					insertRuleMapping.setString(1, key);
					insertRuleMapping.setString(2, valueMapping.get(key));
					
					insertRuleMapping.setString(3, name);
					insertRuleMapping.setString(4, typeCode);
					insertRuleMapping.addBatch();
					ruleMappingBatch++;
					if(ruleMappingBatch==50){
						insertRuleMapping.executeBatch();
						ruleMappingBatch=0;
					}
				}
			}
			if(ruleMappingBatch>0){
				insertRuleMapping.executeBatch();
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} finally {
			JdbcUtil.close(insertRule);
			JdbcUtil.close(insertRuleMapping);
			JdbcUtil.close(deleteRule);
			JdbcUtil.close(conn );
		}
		return false;
	}


	public boolean syslogRuleDelete(String typeCode) {
		if(typeCode==null||"".equals(typeCode)){
			return false;
		}
		Connection conn = null;
		Statement deleteRule = null;
		try {
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			deleteRule=conn.createStatement();
			String deleteRuleSql = "delete from syslog_rule where type_code='"+typeCode+"'";
			deleteRule.execute(deleteRuleSql);
			String deleteRuleMappingSql="delete from syslog_rule_mapping where type_code='"+typeCode+"'";
			deleteRule.execute(deleteRuleMappingSql);
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} finally {
			JdbcUtil.close(deleteRule);
			JdbcUtil.close(conn );
		}
		return false;
	}
	public List getRawSyslogHitPage(Long resId,String startTime,String endTime,int pageSize,int pageNo){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		List hitList=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select t2.POLICY_INFO,t2.HIT_COUNT,t2.START_DATE,t2.END_DATE,t2.RES_ID from (select rownum r,t1.POLICY_INFO,t1.HIT_COUNT,t1.START_DATE,t1.END_DATE,t1.RES_ID from RAW_SYSLOG_HIT t1 where t1.RES_ID=? and t1.START_DATE > ? and t1.END_DATE< ? and  rownum<?) t2 where t2.r>? ");
			sta.setLong(1, resId);
			sta.setString(2, startTime);
			sta.setString(3, endTime);
			sta.setInt(4, pageNo*pageSize+1);
			sta.setInt(5, pageSize*(pageNo-1));
			rs=sta.executeQuery();
			while(rs.next()){
				RawSyslogHit hit=new RawSyslogHit();
				hit.setPolicyInfo(rs.getString(1));
				hit.setHitCount(rs.getLong(2));
				hit.setStartDate(rs.getString(3));
				hit.setEndDate(rs.getString(4));
				hit.setResId(rs.getLong(5));
				hitList.add(hit);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return hitList;	
	}
	public int getRawSyslogHitCount(Long resId,String startTime,String endTime){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select count(t1.POLICY_INFO) from RAW_SYSLOG_HIT t1 where t1.RES_ID=? and t1.START_DATE > ? and t1.END_DATE< ?");
			sta.setLong(1, resId);
			sta.setString(2, startTime);
			sta.setString(3, endTime);
			rs=sta.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return count;
	}
}
