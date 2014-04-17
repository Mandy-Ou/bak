package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 正常还款管理  ACTION类  
 * @author 程明卫
 * @date 2013-01-30T00:00:00
 */
@Description(remark="还款计划ACTION",createDate="2013-01-30T00:00:00",author="程明卫",defaultVals="fcNomalDeduct_")
@SuppressWarnings("serial")
public class NomalDeductAction extends BaseAction {
	@Resource(name="planService")
	private PlanService planService;
	
	LockManager lockMgr = LockManager.getInstance();
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取正常扣收 还款计划 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,name,code,payBank,payAccount,startDate,accName,endDate,id");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			Long id = map.getvalAsLng("id");
			if(null != id){
				map.put("ids", id);
				map.remove("id");
			}
			DataTable dt = planService.getNomalPlans(map, getStart(),getLimit());
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
	
	public String lists()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = planService.getResultList(map,getStart(),getLimit());
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
	 * 获取正常还款计划流水记录
	 */

	public String RepDetail()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,name,code,payBank,accName,payAccount,id#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			Long id = map.getvalAsLng("id");
			if(null != id){
				map.put("id", id);
				map.remove("id");
			}
			DataTable dt = planService.RepDetail(map, getStart(),getLimit());
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
	 * 获取正常放款流水详情
	 * @return
	 * @throws Exception
	 */
	public String overDetail() throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) new  ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("ids", id);
			DataTable dt = planService.RepDetail(map, getStart(),getLimit());
			if(dt != null && dt.getRowCount()>0){
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
	 * 获取正常扣收 还款计划 列表
	 * @return
	 * @throws Exception
	 */
	public String listRepDetail()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,name,code,payBank,payAccount,startDate,endDate,id");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			Long id = map.getvalAsLng("id");
			if(null != id){
				map.put("ids", id);
				map.remove("id");
			}
			DataTable dt = planService.getNomalPlans(map, getStart(),getLimit());
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
	 * 获取正常流水
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("ids#L");
			DataTable dt = planService.RepDetail(map,-1, -1);
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
	 * 获取 (贷款资金收付管理 ---> 客户正常还款信息)
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
			DataTable dt = planService.getNomalPlans(map,-1,-1);
			lockMgr.applyLock(currUser, dt, "name", key);/*申请锁定指定的客户*/
			if(isBatch){
				result = dt.getJsonArr(new JsonDataCallback(){
					public void makeJson(JSONObject jsonObj) {
						Double ztotalAmount = jsonObj.getDouble("ztotalAmount");
						jsonObj.put("rtotalAmount", ztotalAmount);
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
			String dtCmnNames = "code,custType,name,xpayDate#yyyy-MM-dd,phases,totalAmount,ytotalAmount,ztotalAmount";
			String cellIndexes = "0,1,2,6,7,16,17,18";
			DataTable dtXls = importer.readDatasToDt(7,breakAction,dtCmnNames,cellIndexes);
			if(null != dtXls && dtXls.getRowCount() > 0){
				StringBuffer sb = new StringBuffer();
				StringBuffer sbPhases = new StringBuffer();
				for(int i=0,count=dtXls.getRowCount(); i<count; i++){
					String code = dtXls.getString(i, "code");
					Integer phases = dtXls.getInteger(i, "phases");
					sb.append("'"+code+"'").append(",");
					sbPhases.append(phases).append(",");
				}
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				String codes = StringHandler.RemoveStr(sb);
				map.put("codes", codes);
				map.put("phases", StringHandler.RemoveStr(sbPhases));
				final DataTable dt = planService.getIds(map);
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
				Integer sourcePhases =  jsonObj.getInteger("phases");
				Long id = null;
				Long contractId = null;
				Integer status = null;
				Date lastDate = null;
				BigDecimal sysztotalAmount = null;
				for(int i=0,count=dt.getRowCount(); i<count; i++){
					String code = dt.getString(i, "code");
					Integer phases = dt.getInteger(i, "phases");
					if(sourceCode.equals(code) && sourcePhases.equals(phases)){
						id = dt.getLong(i, "id");
						contractId = dt.getLong(i, "contractId");
						status = dt.getInteger(i,"status");
						lastDate = dt.getDate(i, "lastDate");
						sysztotalAmount = dt.getBigDecimal(i, "sysztotalAmount");
						break;
					}
				}
				if(null == id || id <= 0) id = System.currentTimeMillis();
				jsonObj.put("id", id);
				jsonObj.put("contractId", contractId);
				jsonObj.put("status", status);
				jsonObj.put("lastDate", DateUtil.dateFormatToStr(lastDate));
				jsonObj.put("sysztotalAmount", sysztotalAmount);
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
				
				/* step 4 : 计算应收合计和实收合计 */
				//totalAmount,ytotalAmount,ztotalAmount
				BigDecimal totalAmount = jsonObj.getBigDecimal("totalAmount");
				BigDecimal ytotalAmount = jsonObj.getBigDecimal("ytotalAmount");
				BigDecimal ztotalAmount = jsonObj.getBigDecimal("ztotalAmount");
				double _amount = BigDecimalHandler.sub(totalAmount, ytotalAmount);
				if(_amount<0) _amount = 0;
				jsonObj.put("ztotalAmount", _amount);
				jsonObj.put("rtotalAmount", ztotalAmount);
			}
		};

		lockMgr.applyLock(currUser, lockList, key);/*申请锁定指定的客户*/
		return callback;
	}
	
	/**
	 * 单批/批量正常收款 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String receivables()throws Exception {
		try {
			Long id = getLVal("id");
			String ids = getVal("ids");
			Long accountId = getLVal("accountId");
			String rectDate = getVal("rectDate");
		
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("收款银行不能为空!");
			if(!StringHandler.isValidStr(rectDate)) throw new ServiceException("实际收款日期不能为空!");
			UserEntity user = getCurUser();
			SHashMap<String,Object> complexData = new SHashMap<String, Object>();
			complexData.put("id", id);
			complexData.put(SysConstant.USER_KEY, user);
			if(StringHandler.isValidStr(ids))ids = getUnDoIdDatas(ids);
			complexData.put("ids", ids);
			complexData.put("sysId", getLVal("sysId"));
			complexData.put("accountId", accountId);
			complexData.put("rectDate", rectDate);
			
			if(null != id){/*单批正常扣收*/
				Long contractId = getLVal("contractIds");
				complexData.put("contractId", contractId);
				Double cat = getDVal("cat");
				complexData.put("cat", cat);
				Double rat = getDVal("rat");
				complexData.put("rat", rat);
				Double mat = getDVal("mat");
				complexData.put("mat", mat);
				Double tat = getDVal("tat");
				complexData.put("tat", tat);
				Double oddAmount = getDVal("oddAmount");
				complexData.put("oddAmount", oddAmount);
			}else{/*批量正常扣收*/
				String batchDatas = getVal("batchDatas");
				complexData.put("batchDatas", batchDatas);
			}
			Map<AmountLogEntity,DataTable> logDataMap = (Map<AmountLogEntity,DataTable>)planService.doComplexBusss(complexData);
		
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
		String status = BussStateConstant.PLAN_STATUS_0+","+BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_8;
		map.put("status",  SqlUtil.LOGIC_IN + SqlUtil.LOGIC +status);
		DataTable undoDt = planService.getIds(map);
		if(null == undoDt || undoDt.getRowCount() == 0) throw new ServiceException("导入的数据之前已经扣款，请不要重复扣款!");
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
	}
