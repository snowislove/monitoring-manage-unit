package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysBaselineDao;
import com.secpro.platform.monitoring.manage.entity.BaselineMatchScore;
import com.secpro.platform.monitoring.manage.entity.RawBaselineMatch;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;

@Repository("SysBaselineDaoImpl")
public class SysBaselineDaoImpl extends BaseDao implements SysBaselineDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public boolean deleteBaseLine(String[] baseLineIds){
		Connection con=null;
		PreparedStatement sta=null;
		PreparedStatement sta1=null;
		PreparedStatement sta2=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("delete baseline_template_mapping  where baseline_id=? ");
			sta1=con.prepareStatement("delete baseline_rule where baseline_id=?");
			sta2=con.prepareStatement("delete sys_baseline where id =?");
			for(int i=0;i<baseLineIds.length;i++){
				sta.setLong(1, Long.parseLong(baseLineIds[i]));
				sta.execute();
				sta1.setLong(1, Long.parseLong(baseLineIds[i]));
				sta1.execute();
				sta2.setLong(1, Long.parseLong(baseLineIds[i]));
				sta2.execute();
			}
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close( sta);
			JdbcUtil.close( sta1);
			JdbcUtil.close( sta2);
			JdbcUtil.close( con);
		}
		return flag;
	}
	public boolean createBaseLineRule(String typeCode ,String baseLineId,String rule){
		Connection con=null;
		Statement sta=null;
		boolean flag=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			sta.execute("delete from baseline_rule where baseline_id="+baseLineId+" and type_code='"+typeCode+"'");
			sta.close();
			sta=con.createStatement();
			sta.execute("insert into baseline_rule(baseline_id,type_code,rule) values("+baseLineId+",'"+typeCode+"','"+rule+"')");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close( sta);
			JdbcUtil.close( con);
		}
		return flag;
	}
	public String getRule(String baselineId,String typeCode){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		String rule="";
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select rule from baseline_rule where baseline_id="+baselineId+" and type_code='"+typeCode+"'");
			if(rs.next()){
				rule=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return rule;
	}
	public List queryResMatchScorePage(Long resId,String startTime,String endTime,int pageSize,int pageNo){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		List matchScore=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select t2.total_SCORE,t2.CDATE,t2.RES_ID,t2.TASK_CODE from (select rownum r,t1.total_SCORE,t1.CDATE,t1.RES_ID,t1.TASK_CODE from RAW_BASELINE_MATCH_SCORE t1 where t1.RES_ID=? and t1.cdate > ? and t1.cdate< ? and  rownum<?) t2 where t2.r>? ");
			sta.setLong(1, resId);
			sta.setString(2, startTime);
			sta.setString(3, endTime);
			sta.setInt(4, pageNo*pageSize+1);
			sta.setInt(5, pageSize*(pageNo-1));
			rs=sta.executeQuery();
			while(rs.next()){
				BaselineMatchScore score=new BaselineMatchScore();
				score.setSocre(rs.getInt(1));
				score.setCdate(rs.getString(2));
				score.setResId(rs.getLong(3));
				score.setTaskCode(rs.getString(4));
				matchScore.add(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return matchScore;	
	}
	public int queryAllScoreCountByRes(Long resId,String startTime,String endTime){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		int count=0;
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select count(t1.res_id) from RAW_BASELINE_MATCH_SCORE t1 where t1.RES_ID=? and t1.cdate > ? and t1.cdate< ? ");
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
	public List queryAllMatchScorePage(String startTime,String endTime,int pageSize,int pageNo){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		List matchScore=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select t2.TOTAL_SCORE,t2.CDATE,t2.RES_ID,t2.TASK_CODE from (select rownum r,t1.TOTAL_SCORE,t1.CDATE,t1.RES_ID,t1.TASK_CODE from RAW_BASELINE_MATCH_SCORE t1 where t1.cdate > ? and t1.cdate< ? and  rownum<? order by t1.total_SCORE) t2 where t2.r>? order by t2.TOTAL_SCORE");
			sta.setString(1, startTime);
			sta.setString(2, endTime);
			sta.setInt(3, pageNo*pageSize+1);
			sta.setInt(4, pageSize*(pageNo-1));
			rs=sta.executeQuery();
			while(rs.next()){
				BaselineMatchScore score=new BaselineMatchScore();
				score.setSocre(rs.getInt(1));
				score.setCdate(rs.getString(2));
				score.setResId(rs.getLong(3));
				score.setTaskCode(rs.getString(4));
				matchScore.add(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return matchScore;	
	}
	public int queryAllMatchScorePageCount(String startTime,String endTime){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		int count=0;
		
		try {
			con=dataSource.getConnection();
			sta=con.prepareStatement("select count(t1.total_SCORE) from RAW_BASELINE_MATCH_SCORE t1 where t1.cdate > ? and t1.cdate< ? ");
			sta.setString(1, startTime);
			sta.setString(2, endTime);
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
	public List queryMatchDatil(String resId ,String taskCode){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		List matchDatil=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select r.match_result,r.result,r.cdate,b.baseline_desc from raw_baseline_match r , sys_baseline b where r.baseline_id=b.id and r.res_Id="+resId+" and task_code='"+taskCode+"'");
			while(rs.next()){
				RawBaselineMatch raw=new RawBaselineMatch();
				raw.setMatchResult(rs.getString(1));
				raw.setResult(rs.getString(2));
				raw.setCdate(rs.getString(3));
				raw.setBaseLineDesc(rs.getString(4));
				matchDatil.add(raw);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JdbcUtil.close( con,sta,rs);
		}
		return matchDatil;
	}
	
}
