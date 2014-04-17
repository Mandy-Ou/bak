package com.cmw.action.finance;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.kit.nosql.mongodb.MongoDbManager;
import com.cmw.core.util.FastJsonUtil;
import com.mongodb.MongoException;
/**
 * 审批金额建议  ACTION类
 * @author 程明卫
 *  ./fcAuditproject_query.action
 *  ./fcAuditproject_insert.action
 *  ./fcAuditproject_update.action
 *  ./fcAuditproject_delete.action
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批金额建议ACTION",createDate="2013-11-13T00:00:00",author="程明卫",defaultVals="fcAuditproject_")
@SuppressWarnings("serial")
public class AuditprojectAction extends BaseAction{
	
	static Logger log = LoggerFactory.getLogger(AuditprojectAction.class);
	private MongoDbManager dbMgr = new MongoDbManager();
	private String result = null;
	
	public AuditprojectAction() {
	}



	/**
	 * 查询数据
	 * @return
	 */
	public String query(){
		try{
			Map<String, Object> params = getNosqlParams();
			result = dbMgr.find(params);
		}catch (MongoException e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		outJsonString(result);
		return null;
	}



	private Map<String, Object> getNosqlParams() {
		String tab = getVal(MongoDbManager.TAB_KEY);
		String datas = getVal(MongoDbManager.DATAS_KEY);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MongoDbManager.TAB_KEY, tab);
		params.put(MongoDbManager.DATAS_KEY, datas);
		return params;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String save(){
		try{
			Map<String, Object> params = getNosqlParams();
			result = dbMgr.insert(params).toJSONString();
		}catch (MongoException e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		outJsonString(result);
		return null;
	}
	
	public String insert(){
		try{
			System.out.println("Insert data Start ...");
			Map<String, Object> params = getNosqlParams();
			JSONObject retunJson = dbMgr.insert(params);
			Map<String, Object> qparamsMap = new HashMap<String, Object>();
			qparamsMap.put(MongoDbManager.TAB_KEY, retunJson.get(MongoDbManager.TAB_KEY));
			qparamsMap.put(MongoDbManager.PRIMARY_ID_KEY,  retunJson.get(MongoDbManager.PRIMARY_ID_KEY));
			boolean isExist = dbMgr.existRecord(qparamsMap);
			System.out.println(" data isExist = "+isExist);
			if(!isExist){
				int i = 0;
				while(i<3){
					Thread.sleep(100);
					retunJson = dbMgr.insert(params);
					qparamsMap.put(MongoDbManager.PRIMARY_ID_KEY,  retunJson.get(MongoDbManager.PRIMARY_ID_KEY));
					isExist = dbMgr.existRecord(qparamsMap);
					if(isExist) break;
					i++;
				}
			}
			result = retunJson.toJSONString();
			System.out.println(" data result = "+result);
		}catch (MongoException e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		outJsonString(result);
		return null;
	}
	
	public String update(){
		try{
			Map<String, Object> params = getNosqlParams();
			System.out.println("update datas Start ... ");
			result = dbMgr.update(params);
			Map<String, Object> returnJSON = FastJsonUtil.convertMap4Json(result);
			Map<String, Object> qparams = new HashMap<String,Object>();
			qparams.put(MongoDbManager.TAB_KEY, returnJSON.get(MongoDbManager.TAB_KEY));
			qparams.put(MongoDbManager.PRIMARY_ID_KEY, returnJSON.get(MongoDbManager.PRIMARY_ID_KEY));
			String jsonData = dbMgr.find(qparams);
			System.out.println("update datas : \n"+jsonData);
		}catch (MongoException e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		outJsonString(result);
		return null;
	}
	
	public String delete(){
		try{
			Map<String, Object> params = getNosqlParams();
			result = dbMgr.delete(params);
		}catch (MongoException e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result = errMsg(e.getMessage());
		}
		outJsonString(result);
		return null;
	}
	
	private String errMsg(String msg){
		JSONObject object = new JSONObject();
		object.put(MongoDbManager.SUCCESS_KEY, false);
		object.put(MongoDbManager.MSG_KEY, "no");
		return object.toJSONString();
	}
}
