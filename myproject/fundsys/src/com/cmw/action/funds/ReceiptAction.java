package com.cmw.action.funds;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.store.MemoryStore.BruteForceSearchManager;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.funds.ReceiptService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.FormCfgService;


/**
 * 汇票收条表  ACTION类
 * @author 郑符明
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票收条表ACTION",createDate="2014-02-08T00:00:00",author="郑符明",defaultVals="fuReceipt_")
@SuppressWarnings("serial")
public class ReceiptAction extends BaseAction {
	@Resource(name="receiptService")
	private ReceiptService receiptService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 汇票收条表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
/*			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
			String startDateString = permitInfo.getString("startDate");
			Date startDate = sdf.parse(operational);*/
			String qCmns = "rnum,outMan,outDate,endDate,operational,amount#O,rtacname,rtaccount";//根据条件进行查询
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
			DataTable dt = receiptService.getResultList(map,getStart(),getLimit());
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
	 * 获取 汇票收条审批 列表
	 * @return		
	 * @throws Exception
	 */
	public String auditlist()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			String qCmns = "rnum,outMan,outDate#D,endaDate#D,operational,amount#O,rtacname,rtaccount";//根据条件进行查询
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = receiptService.getResultList(map,getStart(),getLimit());
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
	 * 获取 历史 列表
	 * @return		
	 * @throws Exception
	 */
	public String auditHistory()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			String qCmns = "rnum,outMan,outDate#D,endaDate#D,operational,amount#O,rtacname,rtaccount";//根据条件进行查询
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = receiptService.getResultList(map,getStart(),getLimit());
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
	 * 获取 汇票收条一览表
	 * @return		
	 * @throws Exception
	 */
	public String  auditAll()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			String qCmns = "rnum,outMan,outDate#D,endaDate#D,operational,amount#O,rtacname,rtaccount";//根据条件进行查询
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = receiptService.getResultList(map,getStart(),getLimit());
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
	 * 获取 汇票收条表 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ReceiptEntity entity = receiptService.getEntity(Long.parseLong(id));
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
	 * 保存 汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String id = getVal("id");
			System.out.println(id);
			ReceiptEntity entity = BeanUtil.copyValue(ReceiptEntity.class,getRequest(),"breed","procId","status");
			//子业务流程id TODO  引用 ts_BussProcc(子业务流程) 的ID
			entity.setBreed(-1L);
			receiptService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this,entity, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = receiptService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
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
	 * 删除  汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  汇票收条表 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			receiptService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 汇票收条表 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ReceiptEntity entity = receiptService.navigationPrev(params);
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
	 * 获取指定的 汇票收条表 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ReceiptEntity entity = receiptService.navigationNext(params);
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
	
	/**
	 * 获取的汇票收条 详情（审批管理）
	 * @return	
	 * @throws Exception
	 */
	
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			Long menuId = getLVal("menuId");
			Long sysId = getLVal("sysId");
			System.out.println(menuId + ""+sysId);
			ReceiptEntity receiptEntity = receiptService.getEntity(id);
			if(null == receiptEntity.getBreed() || -1L == receiptEntity.getBreed()){
				SHashMap<Object, Long> sHashMap = new SHashMap<Object, Long>();
				sHashMap.put("menuId", menuId);
				sHashMap.put("sysId", sysId);
				BussProccEntity brBussProccEntity = bussProccService.getEntity(sHashMap);
				receiptEntity.setBreed(brBussProccEntity.getId());
				receiptService.saveOrUpdateEntity(receiptEntity);
			}
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = receiptService.detail(id);
			//breed,procId
			String breed = dtResult.getString("breed");
			String pdid = null;
			if(StringHandler.isValidStr(breed)){
				List<BussProccEntity> bussProccList = BussProccCache.getBussProccs(breed);
				if(null != bussProccList && bussProccList.size() > 0){
					BussProccEntity bussProccEntity = bussProccList.get(0);
					pdid = bussProccEntity.getPdid();
					dtResult.appendData("pdid", new Object[]{pdid});
				}
			}
			String procId = dtResult.getString("procId");
			if(StringHandler.isValidStr(pdid) || StringHandler.isValidStr(procId)){
				final JSONObject bussFormDatas = getBussFormDatas(id, pdid,procId);
				if(null != bussFormDatas && bussFormDatas.size() > 0){/*流程业务表单*/
					dtResult.appendData("bussFormDatas", new Object[]{bussFormDatas});
				}
			}
			result = dtResult.getJsonObjStr();
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
	
	private JSONObject getBussFormDatas(Long id, String pdid, String procId)
			throws ServiceException {
		SHashMap<String, Object> formParams = new SHashMap<String, Object>();
		formParams.put("pdid", pdid);
		formParams.put("procId", procId);
		formParams.put(SysConstant.USER_KEY, this.getCurUser());
		formParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_EXTENSION);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	
	
}
