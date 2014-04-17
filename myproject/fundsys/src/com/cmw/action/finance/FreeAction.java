package com.cmw.action.finance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.excel.importer.POIXlsImporter;
import com.cmw.core.kit.excel.importer.POIXlsImporter.BreakAction;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.FreeEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.finance.FreeService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 放款手续费  ACTION类
 * @author pdh
 * @date 2013-01-17T00:00:00
 */
@Description(remark="放款手续费ACTION",createDate="2013-01-17T00:00:00",author="pdh",defaultVals="fcFree_")
@SuppressWarnings("serial")
public class FreeAction extends BaseAction {
	@Resource(name="freeService")
	private FreeService freeService;
	
	LockManager lockMgr = LockManager.getInstance();
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 放款手续费 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,name,ccode,accName,eqopAmount,payAmount#O,startDate,endDate");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("formId", getLVal("formId"));
			DataTable dt = freeService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 放款手续费 列表
	 * @return
	 * @throws Exception
	 */
	public String listLoanRecord()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,name,ccode,accName,eqopAmount,payAmount#O,startDate,endDate");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("formId", getLVal("formId"));
			DataTable dt = freeService.getLoanRecordsList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 实收手续费 流水
	 * @return
	 * @throws Exception
	 */
	
	public String getLoanRec()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("id#L");
			DataTable dt = freeService.getLoanRecord(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 放款手续费 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(id) && !StringHandler.isValidStr(ids)) throw new ServiceException("参数 id 和 ids 必须至少有一个不能为空!");
			String key = getVal("key");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("参数 key 不能为空!");
			UserEntity currUser = getCurUser();
			boolean isBatch = false;
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			if(StringHandler.isValidStr(id)){
				ids = id;
			}else{
				isBatch = true;
			}
			map.put("ids", ids);
			DataTable dt = freeService.getResultList(map);
			lockMgr.applyLock(currUser, dt, "name", key);/*申请锁定指定的客户*/
			if(dt != null && dt.getRowCount()>0){
				result = isBatch ? dt.getJsonArr() : dt.getJsonObjStr();
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 放款手续费 详情流水
	 * @return
	 * @throws Exception
	 */
	public String getLoanRecord()throws Exception {
		try {
			String id = getVal("id");
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(id) && !StringHandler.isValidStr(ids)) throw new ServiceException("参数 id 和 ids 必须至少有一个不能为空!");
			UserEntity currUser = getCurUser();
			boolean isBatch = false;
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			if(StringHandler.isValidStr(id)){
				ids = id;
			}else{
				isBatch = true;
			}
			map.put("ids", ids);
			DataTable dt = freeService.getLoanRecordsList(map);
			if(dt != null && dt.getRowCount()>0){
				result = isBatch ? dt.getJsonArr() : dt.getJsonObjStr();
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			FreeEntity entity = BeanUtil.copyValue(FreeEntity.class,getRequest());
			freeService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 从导入的Excel 文件中获取 放款单手续数据(贷款资金收付管理 ---> 个人/企业贷款发放)
	 * @return 
	 * @throws Exception
	 */
	public String readxls()throws Exception {
		try {
			String filePath = getVal("filePath");
			if(!StringHandler.isValidStr(filePath)) throw new ServiceException("参数 filePath 不能为空!");
			String key = getVal("key");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("参数 key 不能为空!");
			UserEntity currUser = getCurUser();
			filePath = FileUtil.getFilePath(getRequest(), filePath);
			POIXlsImporter importer = new POIXlsImporter(filePath);
			BreakAction breakAction = new BreakAction(){
				public boolean exit(int rowNum, Object cellVal) {/*返回 true : 退出读取Excel数据*/
					return (null != cellVal && cellVal.equals("合计")) ? true : false;
				}
			};
			/*==========>已放款金额,借款合同编号，放款单编号，客户姓名，手续费利率，应收手续费，已收手续费，本次时候手续费，实际收款日期<==================*/
			String dtCmnNames = "name,prate,freeamount,yamount,notamount,realDate#yyyy-MM-dd,ccode,payAmount,code";
			String cellIndexes = "3,4,5,6,7,9,10,11,12";
			DataTable dtXls = importer.readDatasToDt(7,breakAction,dtCmnNames,cellIndexes);
			if(null != dtXls && dtXls.getRowCount() > 0){
				StringBuffer sbccode = new StringBuffer();
				for(int i=0,count=dtXls.getRowCount(); i<count; i++){
					String ccode = dtXls.getString(i, "ccode");
					if(!StringHandler.isValidStr(ccode))
						continue;
					sbccode.append("'"+ccode+"'").append(",");
				}
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				String ccodes = StringHandler.RemoveStr(sbccode);
				map.put("ccodes", ccodes);
				final DataTable dt = freeService.getIds(map);
				JsonDataCallback callback = lockCustomers(key, currUser, dtXls,dt);
				result = dtXls.getJsonArr(callback);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 锁住业务客户
	 * @param key 业务钥匙
	 * @param currUser 当前用户
	 * @param dtXls 装有 Excel 数据的 DataTable 对象 
	 * @param dt 还有 id,code 列的 DataTable 对象	
	 * @return	返回 JsonDatCallback 对象
	 * @throws ServiceException
	 */
	public JsonDataCallback lockCustomers(String key, UserEntity currUser,
			DataTable dtXls,final DataTable dt)
			throws ServiceException {
		//--> 获取锁住的数据
		final JSONArray lockCustomers = lockMgr.getLockData(key, dtXls,"name");
		final List<String> lockList = new ArrayList<String>();
		JsonDataCallback callback = new JsonDataCallback() {
			public void makeJson(JSONObject jsonObj) {
				/* step 1 : 通过比较相同的 code 获取放款单ID 并存入 jsonObj 中*/
				String sourceCode = jsonObj.getString("ccode");
				Long id = null;
				Long contractId = null;
				for(int i=0,count=dt.getRowCount(); i<count; i++){
					String ccode = dt.getString(i, "ccode");
					if(sourceCode.equals(ccode)){
						id = dt.getLong(i, "id");
						contractId = dt.getLong(i, "contractId");
						break;
					}
				}
				if(null == id || id <= 0) id = System.currentTimeMillis();
				jsonObj.put("id", id);
				jsonObj.put("contractId", contractId);
				/* step 2 : 通过比较相同的 客户姓名(name) 获取客户是否锁定 并存入 jsonObj 中*/
				boolean locked = false;
				String customerName = jsonObj.getString("name");
				if(null != lockCustomers && lockCustomers.size() > 0){
					for(int i=0,count=lockCustomers.size(); i<count; i++){
						JSONObject obj = lockCustomers.getJSONObject(i);
						String customers = obj.getString("customers");
						if(!StringHandler.isValidStr(customers)) break;
						String[] customerArr = customers.split(",");
						for(String customer : customerArr){
							if(customerName.equals(customer)){
								locked = true;
								break;
							}
						}
						if(locked) break;
					}
				}
				jsonObj.put("locked", locked);
				
				/* step 3 : 获取未被锁定的用户*/
				if(!locked) lockList.add(customerName);
				
				/* step 4 : 计算应收合计和实收合计 */
				//payAmount,freeamount,amount,yamount,notamount
			}
		};

		lockMgr.applyLock(currUser, lockList, key);/*申请锁定指定的客户*/
		return callback;
	}
	
	/**
	 * 收取手续费
	 * @return
	 * @throws Exception
	 */
	public String chargefree()throws Exception {
		try {
			Long  id = getLVal("id");
			String  ids = getVal("ids");
			Long accountId = getLVal("accountId");
			String lastDate = getVal("lastDate");
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("收款银行不能为空!");
			if(!StringHandler.isValidStr(lastDate)) throw new ServiceException("最后收款日期不能为空!");
			UserEntity user = getCurUser();
			SHashMap<String,Object> complexData = new SHashMap<String, Object>();
			Long sysId = getLVal("sysId");
			complexData.put("sysId", sysId);
			complexData.put("id", id);
			complexData.put(SysConstant.USER_KEY, user);
			if(StringHandler.isValidStr(ids))ids = getUnDoIdDatas(ids);
			complexData.put("ids", ids);
			complexData.put("accountId", accountId);
			complexData.put("lastDate", lastDate);
			if(null != id){/*单批正常扣收*/
				Double yamount = getDVal("yamount");
				Double amount = getDVal("amount");
				Long contractId = getLVal("contractId");
				if(null == yamount || yamount.equals(0.0d)){
					yamount = amount;
				}
				complexData.put("contractId", contractId);
				complexData.put("yamount", yamount);
				complexData.put("amount", amount);
			}else{/*批量收取*/
				String batchDatas = getVal("batchDatas");
				complexData.put("batchDatas", batchDatas);
			}
			@SuppressWarnings("unchecked")
			Map<AmountLogEntity,DataTable> logDataMap = (Map<AmountLogEntity,DataTable>)freeService.doComplexBusss(complexData);
			
			/*--->2:生成财务凭证*/
			Integer isVoucher = getIVal("isVoucher");
			if(null != isVoucher && isVoucher.intValue() == SysConstant.SAVE_VOUCHER_1) saveVouchers(logDataMap);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	private void saveVouchers(Map<AmountLogEntity,DataTable> logDataMap) throws ServiceException{
		SHashMap<String,Object> params = new SHashMap<String, Object>();
		Long sysId = getLVal("sysId");
		String vtempCode = getVal("vtempCode");
		params.put("sysId", sysId);
		params.put("vtempCode", vtempCode);
		UserEntity user = getCurUser();
		params.put(SysConstant.USER_KEY, user);
		amountLogService.saveVouchers(logDataMap, params);
	}
	
	/**
	 * 找出未放过款的放款单ID数据
	 * 原因：客户有可能将Excel文件导多次，这样就会存在重复放款的问题
	 *      该方法处理重复放款的问题。
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	
	private String getUnDoIdDatas(String ids) throws ServiceException{
		//String ids = map.getvalAsStr("ids");
		String newIds = null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		map.put("status", BussStateConstant.FREE_STATUS_0);
		DataTable undoDt = freeService.getIds(map);
		if(null == undoDt || undoDt.getRowCount() == 0) throw new ServiceException("导入的数据之前已经收完款，请不要重复收款!");
		StringBuilder sb = new StringBuilder();
		for(int i=0,count=undoDt.getRowCount(); i<count; i++){
			String id = undoDt.getString(i, "id");
			sb.append(id).append(",");
		}
		if(null != sb && sb.length() > 0){
			newIds = StringHandler.RemoveStr(sb);
		}
		return newIds;
	}
	
	
	/**
	 * 新增  放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = freeService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("F", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	/**
	 * 删除/起用/禁用  放款手续费 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			freeService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取指定的 放款手续费 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FreeEntity entity = freeService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 放款手续费 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FreeEntity entity = freeService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
}
