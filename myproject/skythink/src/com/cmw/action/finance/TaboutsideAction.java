package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.excel.importer.POIXlsImporter;
import com.cmw.core.kit.excel.importer.POIXlsImporter.BreakAction;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.TaboutsideEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.finance.TaboutsideService;


/**
 * 表内表外  ACTION类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外ACTION",createDate="2013-02-28T00:00:00",author="程明卫",defaultVals="fcTaboutside_")
@SuppressWarnings("serial")
public class TaboutsideAction extends BaseAction {
	@Resource(name="taboutsideService")
	private TaboutsideService taboutsideService;
	
	LockManager lockMgr = LockManager.getInstance();
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 表内表外 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = taboutsideService.getResultList(map,getStart(),getLimit());
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
	 * 获取 表内表外 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			TaboutsideEntity entity = taboutsideService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
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
	 * 保存 表内表外 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			TaboutsideEntity entity = BeanUtil.copyValue(TaboutsideEntity.class,getRequest());
			taboutsideService.saveOrUpdateEntity(entity);
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
			DataTable dt = taboutsideService.getResultList(map, -1, -1);
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
				final DataTable dt = taboutsideService.getIds(map);
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
				for(int i=0,count=dt.getRowCount(); i<count; i++){
					String code = dt.getString(i, "code");
					Integer phases = dt.getInteger(i, "phases");
					if(sourceCode.equals(code) && sourcePhases.equals(phases)){
						id = dt.getLong(i, "id");
						break;
					}
				}
				if(null == id || id <= 0) id = System.currentTimeMillis();
				jsonObj.put("id", id);
				
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
	public String receivables()throws Exception {
		try {
			Long id = getLVal("id");
			String ids = getVal("ids");
			Long accountId = getLVal("accountId");
			String rectDate = getVal("rectDate");
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("收款银行不能为空!");
			if(!StringHandler.isValidStr(rectDate)) throw new ServiceException("实际收款日期不能为空!");
			UserEntity user = getCurUser();
			Map<String,Object> complexData = new HashMap<String, Object>();
			complexData.put("id", id);
			complexData.put(SysConstant.USER_KEY, user);
			complexData.put("ids", ids);
			complexData.put("accountId", accountId);
			complexData.put("rectDate", rectDate);
			
			if(null != id){/*单批正常扣收*/
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
			taboutsideService.doComplexBusss(complexData);
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
	
}
