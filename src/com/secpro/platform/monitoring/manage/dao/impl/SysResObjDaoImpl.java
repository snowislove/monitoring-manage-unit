package com.secpro.platform.monitoring.manage.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.secpro.platform.monitoring.manage.actions.forms.ResObjForm;
import com.secpro.platform.monitoring.manage.common.dao.impl.BaseDao;
import com.secpro.platform.monitoring.manage.dao.SysResObjDao;
import com.secpro.platform.monitoring.manage.util.JdbcUtil;
import com.secpro.platform.monitoring.manage.util.LocalEncrypt;
@Repository("SysResObjDaoImpl")
public class SysResObjDaoImpl extends BaseDao implements SysResObjDao{
	private DataSource dataSource;
	public DataSource getDataSource() {
		return dataSource;
	}
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getOuterParentCityCode(final String cityCode){
		final StringBuilder sb=new StringBuilder();
		getSessionFactory().getCurrentSession().doWork(new Work(){
			public void execute(Connection connection) {
				
				Statement s=null;
				ResultSet rs=null;
				try {
					s = connection.createStatement();
					
					rs=s.executeQuery("select city_code from ( select city_code,parent_code from sys_city start with city_code = '"+cityCode+"' connect by prior parent_code = city_code) t where t.parent_code='1'");
					
					if(rs.next()){
						sb.append(rs.getString(1));
					}else{
						sb.append("");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		return sb.toString();
	}
	public void getResObjForm(final ResObjForm resObjForm){
		
		getSessionFactory().getCurrentSession().doWork(new Work(){
			public void execute(Connection connection) {
				
				Statement s = null;
				ResultSet rs = null;		
				try {
					
					s = connection.createStatement();
					
					rs = s.executeQuery("select s1.id,s1.res_name,s1.res_desc,s1.res_ip,s1.status_opertion,s1.config_opertion,s1.res_paused,s1.mca_id," +
							" s1.city_code,s1.company_code,s1.type_code,s2.company_name,s3.type_name,s4.id,s4.username,s4.password," +
							"s4.community,s4.snmpv3_user,s4.snmpv3_auth,s4.snmpv3_authpass," +
							" s4.snmpv3_priv,s4.snmpv3_privpass ,s5.city_name,s1.CLASS_ID" +
							" from sys_res_obj s1,sys_dev_company s2,sys_dev_type s3,sys_res_auth s4 , sys_city s5" +
							" where s1.id="+resObjForm.getResId()+" and s1.company_code=s2.company_code and s1.type_code=s3.type_code and s1.city_code=s5.city_code and s1.id=s4.res_id");

					if (rs.next()) {
						resObjForm.setResId(rs.getString(1));
						resObjForm.setResName(rs.getString(2));
						resObjForm.setResDesc(rs.getString(3));
						resObjForm.setIp(rs.getString(4));
						resObjForm.setStatusOperation(rs.getString(5));
						resObjForm.setConfigOperation(rs.getString(6));
						resObjForm.setResPaused(rs.getString(7));
						resObjForm.setMcaId(rs.getString(8));
						resObjForm.setCityCode(rs.getString(9));
						resObjForm.setCompany(rs.getString(10));
						resObjForm.setDevType(rs.getString(11));
						resObjForm.setCompanyName(rs.getString(12));
						resObjForm.setTypeName(rs.getString(13));
						resObjForm.setAuthId(rs.getString(14));
						resObjForm.setUsername(rs.getString(15));
						resObjForm.setPassword(LocalEncrypt.Decode(rs.getString(16)));
						resObjForm.setCommuinty(rs.getString(17));
						resObjForm.setSnmpuser(rs.getString(18));
						resObjForm.setSnmpau(rs.getString(19));
						resObjForm.setSnmpaups(rs.getString(20));
						resObjForm.setSnmppr(rs.getString(21));
						resObjForm.setSnmpprps(rs.getString(22));
						resObjForm.setCityName(rs.getString(23));
						resObjForm.setClassId(rs.getString(24));
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(rs!=null){
						try {
							rs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(s!=null){
						try {
							s.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}
	public void deleteRelevance(String resId){
		Connection con=null;
		Statement sta=null;
		
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			sta=con.createStatement();
			//删除资源参数表中的相关资源数据
			sta.execute("delete from sys_res_auth a where a.res_id="+resId);
			sta.close();
			sta=null;
			//删除这个资源的告警信息
			sta=con.createStatement();
			sta.execute("delete from sys_event e where e.RES_ID="+resId);
			sta.close();
			sta=null;
			//删除私有告警规则
			sta=con.createStatement();
			sta.execute("delete from sys_event_rule e where e.RES_ID="+resId);
			sta.close();
			sta=null;
			//删除告警接受人
			sta=con.createStatement();
			sta.execute("delete from notify_user_rule n where n.RES_ID="+resId);
			sta.close();
			sta=null;
			//删除任务
			sta=con.createStatement();
			sta.execute("delete from msu_task m where m.RES_ID="+resId);
			sta.close();
			sta=null;
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
	
	}
}
