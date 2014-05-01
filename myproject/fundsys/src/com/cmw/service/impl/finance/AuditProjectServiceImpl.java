package com.cmw.service.impl.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.kit.nosql.mongodb.MongoDbManager;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CommonDaoInter;

/**
 * 贷前调查报告 Service
 * @author cmw 
 * @date 2013-11-13
 * 
 */
@Service(value="auditProjectService")
@Transactional
public class AuditProjectServiceImpl  {
	@Autowired
	private CommonDaoInter commonDao;
	static Logger logger = Logger.getLogger(AuditProjectServiceImpl.class);
	/**
	 * 根据项目落实费用ID获取其已收取金额记录明细
	 * @param params	chargeId
	 * @return	返回 DataTable 对象	
	 */
	public DataTable getDataTable(HashMap<String, Object> params){
		checkParams(params);
		String queryStr = (String)params.get("queryStr"); 
		Boolean isHql = (Boolean)params.get("isHql");
		String cmns = (String)params.get("cmns"); 
		DataTable dt = getQuery(queryStr,cmns,isHql);
		return dt;
	}
	
	/**
	 * 根据项目落实费用ID获取其已收取金额记录明细
	 * @param params	chargeId
	 * @return	返回 DataTable 对象	
	 */
	public DataTable getCustDt(HashMap<String, Object> params){
		checkParams(params);
		String queryStr = (String)params.get("queryStr"); 
		Boolean isHql = (Boolean)params.get("isHql");
		String cmns = (String)params.get("cmns"); 
		DataTable dt = getQuery(queryStr,cmns,isHql);
		DataTable hostDt = null;
		if(null == dt || dt.getRowCount() == 0){
			 hostDt = new DataTable(new ArrayList<Object>(),cmns);
		}else{
			 dt.setColumnNames("fieldname,fieldvalue");
			 int len = dt.getRowCount();
			 String[] cmnsArr = cmns.split(",");
			 int count = cmnsArr.length;
			 Object[] row = new Object[count];
			 for(int i=0; i<count; i++){
				 String cmn = cmnsArr[i];
				 for(int j=0; j<len; j++){
					 String fieldname = dt.getString(j,"fieldname");
					 String fieldvalue = dt.getString(j, "fieldvalue");
					 if(!StringHandler.isValidStr(fieldname)) continue;
					 if(cmn.equals(fieldname)){
						 row[i] = fieldvalue;
						 break;
					 }
				 }
			 }
			 hostDt = new DataTable();
			 hostDt.setColumnNames(cmns);
			 List<Object> dataSource = new ArrayList<Object>();
			 dataSource.add(row);
			 hostDt.setDataSource(dataSource);
		}
		return hostDt;
	}
	
	
	private void checkParams(HashMap<String, Object> params){
		String subject = (String)params.get("subject"); 
		String queryStr = (String)params.get("queryStr"); 
		Boolean isHql = (Boolean)params.get("isHql");
		String cmns = (String)params.get("cmns");
		String str = "检查到没有为参数【";
		String endStr = "】提供任何值！";
		boolean flag = true;
		logger.info("========== 查询["+subject+"]检查到的错误信息如下 =============/n");
		if(!StringHandler.isValidStr(queryStr)){
			logger.info(str+"queryStr"+endStr);
			flag = false;
		}
		if(null == isHql){
			logger.info(str+"isHql"+endStr+"系统采用SQL语句进行查询！");
			flag = false;
		}
		
		if(!StringHandler.isValidStr(cmns)){
			logger.info(str+"cmns"+endStr+"无法实例化 DataTable 的列！");
			flag = false;
		}
		if(flag){
			logger.info("========== (*)没有任何错误(*) =============/n");
		}
		logger.info("========== ["+subject+"]的错误信息检查完毕 =============/n");
	}
	
	private DataTable getQuery(String queryString,String columnNames,Boolean isHql){
		DataTable dt = null;
		try {
			if(null == isHql || !isHql){
				dt = commonDao.getDatasBySql(queryString, columnNames);
			}else{
				dt = commonDao.getDatasByHql(queryString, columnNames);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	private MongoDbManager dbMgr = MongoDbManager.getInstance();
	/**
	 * 查询所有表格数据
	 * @param params
	 * @return
	 */
	public List<String> findAll(Map<String, Object> params){
		//customerId custType projectId
		List<String> list = new ArrayList<String>();
		String tabCfgsStr = (String)params.get("tabCfgs");
		//System.out.println("tabCfgsStr="+tabCfgsStr);
		JSONArray jsonArr = FastJsonUtil.convertStrToJSONArr(tabCfgsStr);
		Object[] tabCfgArr = jsonArr.toArray();
		params.remove("tabCfgs");
		for(int i=0,count=tabCfgArr.length; i<count; i++){
			JSONObject tabObject = (JSONObject)tabCfgArr[i];
			String tab = tabObject.getString("tabId");
			//System.out.println("tab="+tab);
			params.put(MongoDbManager.TAB_KEY, tab);
			String dataObject = dbMgr.find(params);
			list.add(dataObject);
			
		}
		return list;
	}

}
