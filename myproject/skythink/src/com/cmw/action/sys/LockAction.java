package com.cmw.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cmw.constant.ResultMsg;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author 程明卫 E-mail:chengmingwei_1984122@126.com
 * @version 创建时间：2010-6-15 下午12:14:31
 * 类说明 	卡片卡片菜单 ACTION   
 */
@Description(remark="卡片菜单 ACTION",createDate="2010-6-15",defaultVals="sysLock_")
@SuppressWarnings("serial")
public class LockAction extends BaseAction {
	private LockManager lockManager = LockManager.getInstance();
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 验证指定业务的客户是否有锁住
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String isLock()throws Exception {
		try {
			String key = getVal("key");
			String customersStr = getVal("customers");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("钥匙 key 不能为空!");
			if(!StringHandler.isValidStr(customersStr)) throw new ServiceException("customers 不能为空!");
			String[] customersArr = customersStr.split(",");
			List<String> customers = (List<String>)Arrays.asList(customersArr);
			JSONArray lockCustomers = lockManager.isLock(key, customers);
			if(null != lockCustomers && lockCustomers.size() > 0){
				Map<String,Object> appendParams = new HashMap<String, Object>();
				appendParams.put("lockCustomers", lockCustomers);
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg();
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 解锁
	 * @return
	 * @throws Exception
	 */
	public String releaseLock()throws Exception {
		try {
			UserEntity currUser = this.getCurUser();
			String key = getVal("key");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("钥匙 key 不能为空!");
			lockManager.releaseLock(currUser, key);
			result = ResultMsg.getSuccessMsg();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
}
