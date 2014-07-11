package com.secpro.platform.monitoring.manage.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.secpro.platform.monitoring.manage.common.services.impl.BaseService;
import com.secpro.platform.monitoring.manage.dao.SyslogRuleDao;
import com.secpro.platform.monitoring.manage.entity.SyslogBean;
import com.secpro.platform.monitoring.manage.services.SyslogRuleService;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * syslog标准化规则存储
 * 从规则XML文件中，加载对应的syslog规则，并存入数据库
 * 存入数据库后，将此规则文件删除
 * @author sxf
 *
 */
@Service("SyslogRuleStorage")
public class SyslogRuleStorage extends BaseService implements SyslogRuleService{
	PlatformLogger theLogger = PlatformLogger.getLogger(SyslogRuleStorage.class);

	private SyslogRuleDao dao;
	
	private String rulePath;
	private String typeCode;
	//增加：0,修改：1,删除：2
	private String crudOper;
	private SyslogBean syslogB;
	private static final String DROP_NAME="drop";
	private static final String UPLOAD_NAME="upload";
	private static final String DROP_VALUE="0";
	private static final String UPLOAD_VALUE="1";
	private static final String LABEL_CHECK="check";
	private static final String PROPERTIES_NAME="name";
	private static final String VALUE_NUM="num";
	private static final String PROPERTIES_CONDITION="condition";
	private static final String LABEL_ACTION="action";
	private static final String LABEL_REGEXS="regexs";
	private static final String LABEL_REGEX="regex";
	private static final String PROPERTIES_REGEX_NAME="name";
	private static final String LABEL_RULEMAPPING="ruleMapping";
	private static final String PROPERTIES_RULEMAPPING_NAME="name";
	private static final String LABEL_MAPPING="mapping";
	private static final String PROPERTIES_KEY="key";
	private static final String DATE_NAME="date";
	
	public SyslogRuleDao getDao() {
		return dao;
	}
	@Resource(name="SyslogRuleDaoImpl")
	public void setDao(SyslogRuleDao dao) {
		this.dao = dao;
	}
	public  SyslogRuleStorage(){
		
	}
	
	public  SyslogRuleStorage(String rulePath,String typeCode,String crudOper){
		this.rulePath=rulePath;
		this.typeCode=typeCode;
		this.crudOper=crudOper;
	}
	public String getRulePath() {
		return rulePath;
	}
	public void setRulePath(String rulePath) {
		this.rulePath = rulePath;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getCrudOper() {
		return crudOper;
	}
	public void setCrudOper(String crudOper) {
		this.crudOper = crudOper;
	}
	/**
	 * syslog标准化规则存储或移除
	 * @return
	 */
	public boolean ruleStorage(){
		System.out.println(typeCode+"-------------"+crudOper+"-----------------"+rulePath);
		if(Assert.isEmptyString(typeCode)||Assert.isEmptyString(crudOper)){
			return false;
		}
		if("0".equals(crudOper)||"1".equals(crudOper)){
			if(Assert.isEmptyString(rulePath)){
				return false;
			}
			if(loadSyslogRuleFromXML()){
				removeFile(rulePath);
				if(syslogB==null){
					return false;
				}
				theLogger.debug("Storing syslog standardization rules");
				return syslogRuleDBStorage();

			}
			removeFile(rulePath);
		}else if("2".equals(crudOper)){
			theLogger.debug("remove syslog standardization rules");
			return syslogRuleDBRemove(typeCode);
		}else{
			theLogger.error("Does not support this type of operation");
		}
		theLogger.debug("storage or remove the syslog standardization rules failed");
		return false;
	}
	/**
	 * 移除数据库中typeCode对应的syslog标准化规则
	 * @param typeCode
	 * @return
	 */
	private boolean syslogRuleDBRemove(String typeCode) {
		
		return dao.syslogRuleDelete(typeCode);
	}
	/**
	 * 新增或修改数据库中typeCode对应的syslog标准化规则
	 * @return
	 */
	private boolean syslogRuleDBStorage() {
	//	SyslogDao syslogDao=new SyslogDao();
		return dao.syslogRuleSave(syslogB);
		
	}
	/**
	 * 移除文件
	 */
	private void removeFile(String rulePath){
		if(Assert.isEmptyString(rulePath)){
			return;
		}
		File ruleFile=new File(rulePath);
		if(ruleFile.exists()&&ruleFile.isFile()){
			ruleFile.delete();
			theLogger.debug("rule file have been deleted");
		}
	}
	/**
	 * 根据规则文件，加载相应的syslog标准化规则
	 * @return
	 */
	private boolean loadSyslogRuleFromXML(){
		
		if(!rulePath.endsWith("rule.xml")&&!rulePath.endsWith("RULE.XML")){
			return false;
		}
		FileInputStream xmlFileInput = null;  
		try {
			SyslogBean syslogB=new SyslogBean();
			syslogB.setTypeCode(typeCode);
			SAXReader saxReader=new SAXReader();
			xmlFileInput = new FileInputStream(rulePath);  
			Document doc=saxReader.read(xmlFileInput);
			Element root=doc.getRootElement();
			//			String name=root.attributeValue("name");
			//			String id=root.attributeValue("des");
			
			//得到check标签
			Element checkLabel=root.element(LABEL_CHECK);
			if(checkLabel==null){
				theLogger.error("required the check lable");
				return false;
			}
			//check标签中的name属性值
			String checkName=checkLabel.attributeValue(PROPERTIES_NAME);
			if(checkName==null||"".equals(checkName.trim())||!VALUE_NUM.equals(checkName.trim()))
			{
				theLogger.error("required the name attribute value of num");
				return false;
			}
			String condition=checkLabel.attributeValue(PROPERTIES_CONDITION);
			if(condition==null||"".equals(condition.trim())||!condition.matches("[-]?\\d+"))
			{
				theLogger.error("required a numeric type of condition attribute");
				return false;
			}

			syslogB.setCheckNum(Integer.parseInt(condition));
			//action标签
			Element actionLabel=checkLabel.element(LABEL_ACTION);
			if(actionLabel==null){
				theLogger.error("required action label");
				return false;
			}
			String action=actionLabel.getText();
			if(action==null||"".equals(action.trim())){
				return false;
			}
			//标签值为drop
			if(action.equalsIgnoreCase(DROP_NAME)){
				syslogB.setCheckAction(DROP_VALUE);
			//标签值为upload
			}else if(action.equalsIgnoreCase(UPLOAD_NAME)){
				syslogB.setCheckAction(UPLOAD_VALUE);
			}else{
				theLogger.error("Only support drop and upload value");
				return false;
			}
			//regexs标签
			Element regexs=root.element(LABEL_REGEXS);
			if(regexs==null){
				theLogger.error("required regexs label");
				return false;
			}
			//regex标签
			List regexLables=regexs.elements(LABEL_REGEX);
			if(regexLables==null||regexLables.size()==0){
				theLogger.error("required regex label");
				return false;
			}
			Iterator regexIter=regexLables.iterator();
			Map<String,String> regexMap=new HashMap<String,String>();
			while(regexIter.hasNext())
			{
				Element regexLable=(Element)regexIter.next();
				//regex标签的name属性
				String name=regexLable.attributeValue(PROPERTIES_REGEX_NAME);
				String value=regexLable.getText();
				if(name==null||value==null||"".equals(name.trim())||"".equals(value.trim())){
					continue;
				}
				regexMap.put(name, value);
				
			}
			if(regexMap.size()==0){
				theLogger.error("Need to define standardization rules");
				return false;
			}
			syslogB.setRegexs(regexMap);
	        
	        //ruleMapping标签
	        List ruleMappings=root.elements(LABEL_RULEMAPPING);
	        if(ruleMappings==null||ruleMappings.size()==0){
	        	return true;
	        }
	        Iterator ruleMappingIter=ruleMappings.iterator();
	        Map<String,Map<String,String>> ruleMappingSave=new HashMap<String,Map<String,String>>();
	        while(ruleMappingIter.hasNext())
			{
				Element ruleMapping=(Element)ruleMappingIter.next();
				String name=ruleMapping.attributeValue(PROPERTIES_RULEMAPPING_NAME);
				if(name==null&&"".equals(name.trim()))
				{
					continue;
				}
				Map<String,String> keyValue=new HashMap<String,String>(); 
				List mappingLabels=ruleMapping.elements(LABEL_MAPPING);
				if(mappingLabels==null||mappingLabels.size()==0){
					continue;
				}
				Iterator mappingIter=mappingLabels.iterator();
				while(mappingIter.hasNext()){
					Element mapping=(Element)mappingIter.next();
					String key=mapping.attributeValue(PROPERTIES_KEY);
					String value=mapping.getText();
					if(name==null||value==null||"".equals(name.trim())||"".equals(value.trim())){
						continue;
					}
					if(DATE_NAME.equals(name)){
						if("1".equals(key)){
							keyValue.put(key, value);
						}
					}else{
						keyValue.put(key, value);
					}
					
				}
				if(keyValue.size()>0){
					ruleMappingSave.put(name, keyValue);
				}
			}
	        if(ruleMappingSave.size()>0){
	        	syslogB.setRuleMapping(ruleMappingSave);
	        }
					
			this.syslogB=syslogB;	
			theLogger.debug("Standardized rules loaded successfully");
	        return true;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			theLogger.exception(e);
		}
		finally{
			if(xmlFileInput!=null)
			{
				try {
					xmlFileInput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return false;
	}
	public List getRawSyslogHitPage(Long resId,String startTime,String endTime,int pageSize,int pageNo){
		return dao.getRawSyslogHitPage(resId, startTime, endTime, pageSize, pageNo);
	}
	public int getRawSyslogHitCount(Long resId,String startTime,String endTime){
		return dao.getRawSyslogHitCount(resId, startTime, endTime);
	}
}
