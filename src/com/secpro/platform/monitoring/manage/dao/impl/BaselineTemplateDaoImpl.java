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
import com.secpro.platform.monitoring.manage.dao.BaselineTemplateDao;
import com.secpro.platform.monitoring.manage.entity.BaselineTemplate;
import com.secpro.platform.monitoring.manage.entity.SysBaseline;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Repository("BaselineTemplateDaoImpl")
public class BaselineTemplateDaoImpl extends BaseDao implements BaselineTemplateDao{
	private DataSource dataSource;
	PlatformLogger logger = PlatformLogger.getLogger(BaselineTemplateDaoImpl.class);
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List getAllBaseLineTemplate(List templateList){
		Connection con=null;
		PreparedStatement sta=null;
		ResultSet rs=null;
		try {
			con=dataSource.getConnection();
			if(templateList==null){
				return null;
			}
			sta=con.prepareStatement("select b.id,b.baseline_desc,b.baseline_type,b.baseline_black_white,d.company_name from  baseline_template t, sys_baseline b ,baseline_template_mapping btm,sys_dev_company d  where btm.template_id=? and t.id=btm.template_id and b.id=btm.baseline_id  and t.company_code=d.company_code");
			for(int i=0;i<templateList.size();i++){
				BaselineTemplate bt=(BaselineTemplate)templateList.get(i);			
				sta.setLong(1, bt.getId());
				rs=sta.executeQuery();
				while(rs.next()){
					SysBaseline sbl=new SysBaseline();
					sbl.setId(rs.getLong(1));
					sbl.setBaselineDesc(rs.getString(2));
					sbl.setBaselineType(rs.getString(3));
					sbl.setBaselineBlackWhite(rs.getString(4));
					bt.setCompanyName(rs.getString(5));
					bt.getBaseLineList().add(sbl);
					
				}
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return templateList;
	}
	public boolean saveBaseLineTemplete(Long BaselineTemplateId,String[] baselineIds,Map<String,String> scores){
		Connection con=null;
		PreparedStatement sta=null;
		PreparedStatement sta1=null;
		ResultSet rs=null;
		boolean flag = true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta1=con.prepareStatement("delete baseline_template_mapping where template_id=?");
			sta1.setLong(1, BaselineTemplateId);
			sta1.execute();
			sta=con.prepareStatement("insert into baseline_template_mapping(baseline_id,template_id,score) values(?,?,?)");
			
			for(int i=0;i<baselineIds.length;i++){
			//	BaselineTemplate bt=(BaselineTemplate)templateList.get(i);
				long blId=Long.parseLong(baselineIds[i]);
				double score=Double.parseDouble(scores.get(baselineIds[i]));
				sta.setLong(1, blId);
				sta.setLong(2, BaselineTemplateId);
				sta.setDouble(3, score);
				sta.addBatch();
			}
			sta.executeBatch();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			flag=false;
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return flag;
	}
	public boolean deleteTemplate(String[] ids){
		boolean flag=true;
		Connection con=null;
		PreparedStatement sta=null;
		PreparedStatement sta1=null;
		PreparedStatement sta2=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.prepareStatement("update sys_res_obj set TEMPLATE_ID = null where TEMPLATE_ID=?");
			sta1=con.prepareStatement("delete from baseline_template_mapping where TEMPLATE_ID=?");
			sta2=con.prepareStatement("delete from baseline_template where id=?");
			for(int i=0;i<ids.length;i++){
				long id=Long.parseLong(ids[i]);
				sta.setLong(1, id);
				sta.execute();
				sta1.setLong(1, id);
				sta1.execute();
				sta2.setLong(1, id);
				sta2.execute();
			}
			con.commit();
		} catch (Exception e) {
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
	public List getSelectBaseLine(Long id){
		Connection con=null;
		Statement sta=null;
		ResultSet rs=null;
		List bl=new ArrayList();
		try {
			con=dataSource.getConnection();
			sta=con.createStatement();
			rs=sta.executeQuery("select s.id  from sys_baseline s , baseline_template_mapping b where s.id=b.BASELINE_ID and b.TEMPLATE_ID="+id);
			while(rs.next()){
				bl.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally{
			JdbcUtil.close(con, sta, rs);
		}
		return bl;
	}
}
