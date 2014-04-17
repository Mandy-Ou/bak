	package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.FundsWaterEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.OverdueDeductService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 逾期还款管理  ACTION类  
 * @author 程明卫	
 * @date 2013-01-30T00:00:00
 */
@Description(remark="还款计划ACTION",createDate="2013-01-30T00:00:00",author="程明卫",defaultVals="fcOverdueDeduct_")
@SuppressWarnings("serial")
public class OverdueDeductAction extends BaseAction {
	@Resource(name="overdueDeductService")
	private OverdueDeductService overdueDeductService;
	@Resource(name="planService")
	private PlanService planService;
	
	LockManager lockMgr = LockManager.getInstance();
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取逾期扣收 列表 
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("id#L,custType#I,name,code,payBank,payAccount,inouttype#I,flevel#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = overdueDeductService.getResultList(map, getStart(),getLimit());
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
	 * 
	 * 获取逾期详情的页面
	 * 
	 */
	
	public String getLoanRecord()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("contractId#L");
			DataTable dt = overdueDeductService.getLoanRecordsList(map, getStart(),getLimit());
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
 * 逾期的流水详情	
 * @return
 * @throws Exception
 */
	public String RepDetail()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("id#L,custType#I,name,code,payBank,payAccount,inouttype#I,flevel#L");
			DataTable dt = overdueDeductService.repDetail(map, getStart(),getLimit());
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
	public String listRepDedut()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = overdueDeductService.getLoanRecordsList(map);
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
	 * 获取逾期流水详情
	 * @return
	 * @throws Exception
	 */
	
	public String get()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("contractId#L");
			DataTable dt = overdueDeductService.repDetail(map,-1, -1);
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
	 * 获取逾期还款计划 列表
	 * @return
	 * @throws Exception
	 */
	
	public String planlist()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("contractId#L");
			map.put("status", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + BussStateConstant.PLAN_STATUS_4+"," + BussStateConstant.PLAN_STATUS_5);
			DataTable dt = planService.getResultList(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
					Integer pdays = jsonObj.getInteger("pdays");
					Integer ratedays = jsonObj.getInteger("ratedays");
					if(null == pdays) pdays = 0;
					if(null == ratedays) ratedays = 0;
					int latedays = 0;
					if(pdays > ratedays){
						latedays = pdays;
					}else{
						latedays = ratedays;
					}
					jsonObj.put("latedays", latedays);
					jsonObj.put("cat", jsonObj.get("zprincipal"));
					jsonObj.put("rat", jsonObj.get("zinterest"));
					jsonObj.put("mat", jsonObj.get("zmgrAmount"));
					jsonObj.put("pat", jsonObj.get("zpenAmount"));
					jsonObj.put("dat", jsonObj.get("zdelAmount"));
					jsonObj.put("tat", jsonObj.get("ztotalAmount"));
				}
			});
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
	 * 获取逾期罚息和滞纳金 列表 
	 * @return
	 * @throws Exception
	 */
	public String getPdamounts()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			Long contractId = getLVal("contractId");
			String contractIds = getVal("contractIds");
			String lastDate = getVal("lastDate");
			if(StringHandler.isValidObj(contractId)){
				map.put("contractorIds", contractId);
			}else{
				map.put("contractorIds", contractIds);
			}
			map.put("lastDate", DateUtil.dateFormat(lastDate));
			JSONObject jsonObject = overdueDeductService.calculateLateDatas(map);
			result = ResultMsg.getSuccessMsg(jsonObject);;
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
	 * 正常还款批量转逾期 
	 * @return
	 * @throws Exception
	 */
	public String tolate()throws Exception {
		try {
			String ids = getVal("ids");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("ids", ids);
			map.put("lastDate", new Date());
			map.put(SysConstant.USER_KEY, getCurUser());
			overdueDeductService.calculateBatchLateDatas(map);
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
	 * 获取 (贷款资金收付管理 ---> 客户逾期还款信息)
	 * @return
	 * @throws Exception
	 */
	public String obtain()throws Exception {
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
			DataTable dt = overdueDeductService.getResultList(map,-1,-1);
			lockMgr.applyLock(currUser, dt, "name", key);/*申请锁定指定的客户*/
			if(isBatch){
				result = dt.getJsonArr(new JsonDataCallback(){
					public void makeJson(JSONObject jsonObj) {
						Double totalAmounts = jsonObj.getDouble("totalAmounts");
						jsonObj.put("rtotalAmount", totalAmounts);
					}
				});
			}else{
				result = dt.getJsonObjStr();
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
	 * 从导入的Excel 文件中获取 放款单据数据(贷款资金收付管理 ---> 个人/企业贷款发放)
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
			//totalPharses,riskLevel,totalAmounts,rtotalAmount
			String dtCmnNames = "code,custType,name,totalPharses,riskLevel,totalAmounts";
			String cellIndexes = "0,1,2,12,16,10";
			DataTable dtXls = importer.readDatasToDt(7,breakAction,dtCmnNames,cellIndexes);
			if(null != dtXls && dtXls.getRowCount() > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0,count=dtXls.getRowCount(); i<count; i++){
					String code = dtXls.getString(i, "code");
					sb.append("'"+code+"'").append(",");
				}
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				String codes = StringHandler.RemoveStr(sb);
				map.put("codes", codes);
				final DataTable dt = overdueDeductService.getIds(map);
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
	private JsonDataCallback lockCustomers(String key, UserEntity currUser,
			DataTable dtXls,final DataTable dt)
			throws ServiceException {
		//--> 获取锁住的数据
		final JSONArray lockCustomers = lockMgr.getLockData(key, dtXls,"name");
		final List<String> lockList = new ArrayList<String>();
		JsonDataCallback callback = new JsonDataCallback() {
			public void makeJson(JSONObject jsonObj) {
				/* step 1 : 通过比较相同的 code 获取放款单ID 并存入 jsonObj 中*/
				String sourceCode = jsonObj.getString("code");
				Long id = null;
				for(int i=0,count=dt.getRowCount(); i<count; i++){
					String code = dt.getString(i, "code");
					if(sourceCode.equals(code)){
						id = dt.getLong(i, "id");
						break;
					}
				}
				if(null == id || id <= 0) id = System.currentTimeMillis();
				jsonObj.put("contractId", id);
				
				/* step 2 : 通过比较相同的 客户姓名(name) 获取客户是否锁定 并存入 jsonObj 中*/
				boolean locked = false;
				String customerName = jsonObj.getString("name");
				if(null != lockCustomers && lockCustomers.size() > 0){
					for(int i=0,count=lockCustomers.size(); i<count; i++){
						JSONObject obj = lockCustomers.getJSONObject(i);
						String customers = obj.getString("customers");
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
				
				BigDecimal totalAmounts = jsonObj.getBigDecimal("totalAmounts");
				jsonObj.put("rtotalAmount", totalAmounts);
			}
		};

		lockMgr.applyLock(currUser, lockList, key);/*申请锁定指定的客户*/
		return callback;
	}
	
	/**
	 * 单批/批量逾期收款 
	 * @return
	 * @throws Exception
	 */
	public String receivables()throws Exception {
		try {
			Long contractId = getLVal("contractId");
			String contractIds = getVal("contractIds");
			Long accountId = getLVal("accountId");
			String rectDate = getVal("rectDate");
			String batchDatas = getVal("batchDatas");
			Long sysId = getLVal("sysId");
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("收款银行不能为空!");
			if(!StringHandler.isValidStr(rectDate)) throw new ServiceException("实际收款日期不能为空!");
			UserEntity user = getCurUser();
			SHashMap<String,Object> complexData = new SHashMap<String, Object>();
			complexData.put("contractId", contractId);
			complexData.put(SysConstant.USER_KEY, user);
			complexData.put("contractIds", contractIds);
			complexData.put("accountId", accountId);
			complexData.put("rectDate", DateUtil.dateFormat(rectDate));
			complexData.put("sysId", sysId);
			complexData.put("batchDatas", batchDatas);
			@SuppressWarnings("unchecked")
			Map<String,Map<AmountLogEntity,DataTable>> vtlogDataMap = (Map<String,Map<AmountLogEntity,DataTable>>)overdueDeductService.doComplexBusss(complexData);
			
			Integer isVoucher = getIVal("isVoucher");
			if(null != isVoucher && isVoucher.intValue() == SysConstant.SAVE_VOUCHER_1){
				Set<String> vtempcodeSet = vtlogDataMap.keySet();
				for(String vtempcode : vtempcodeSet){
					Map<AmountLogEntity,DataTable> logDataMap = vtlogDataMap.get(vtempcode);
					saveVouchers(vtempcode,logDataMap);
				}
			}
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
	private void saveVouchers(String vtempCode,Map<AmountLogEntity,DataTable> logDataMap) throws ServiceException{
		SHashMap<String,Object> params = new SHashMap<String, Object>();
		Long sysId = getLVal("sysId");
		params.put("sysId", sysId);
		params.put("vtempCode", vtempCode);
		UserEntity user = getCurUser();
		params.put(SysConstant.USER_KEY, user);
		amountLogService.saveVouchers(logDataMap, params);
	}
}
