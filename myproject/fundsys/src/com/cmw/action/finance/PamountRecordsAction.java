package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PamountRecordsEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.PamountRecordsService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.PrepaymentService;
import com.cmw.service.inter.finance.ProjectAmuntService;


/**
 * 实收项目费用表ACTION类
 * @author liting
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收金额ACTION",createDate="2013-01-15T00:00:00",author="liitng",defaultVals="fcPamountRecords_")
@SuppressWarnings("serial")
public class PamountRecordsAction extends BaseAction {
	@Resource(name="pamountRecordsService")
	private PamountRecordsService pamountRecordsService;
	
	@Resource(name="projectAmuntService")
	private ProjectAmuntService projectAmuntService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 实收金额 列表
	 * @return 
	 * @throws Exception
	 */
	
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("contractId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = pamountRecordsService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
					Long creator = jsonObj.getLong("creator");
					try {
						UserEntity creatorObj = UserCache.getUser(creator);
						if(null != creatorObj) jsonObj.put("creator", creatorObj.getEmpName());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
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
	 * 获取 实收金额 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PamountRecordsEntity entity = pamountRecordsService.getEntity(Long.parseLong(id));
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
	 * 保存 实收金额 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String paid=getVal("paid");
			String amount=getVal("amount");
			String rectDate=getVal("rectDate");
			if(!StringHandler.isValidStr(paid)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ProjectAmuntEntity proEntity=projectAmuntService.getEntity(Long.parseLong(paid));
			BigDecimal amountNow=new BigDecimal(amount);//本次收的
			if(StringHandler.isValidObj(proEntity)){
					Double yam=BigDecimalHandler.add(proEntity.getYamount(),amountNow);
					BigDecimal yeam=new BigDecimal(yam);//本次收的
					proEntity.setYamount(yeam);
					Double sal=BigDecimalHandler.sub(proEntity.getAmount(),yeam);
						if(sal==0.00){
							proEntity.setStatus(2);
						}else{
							proEntity.setStatus(1);
						}
			
			}
			StringHandler.dateFormat("yyyy-MM-dd", rectDate);
			proEntity.setLastDate(StringHandler.dateFormat("yyyy-MM-dd", rectDate));
			projectAmuntService.saveOrUpdateEntity(proEntity);
			PamountRecordsEntity entity = BeanUtil.copyValue(PamountRecordsEntity.class,getRequest());
			pamountRecordsService.saveOrUpdateEntity(entity);
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
	 * 新增  实收金额 
	 * @return
	 * @throws Exception
	 */
	
	public String add()throws Exception {
		try {
			Long num = pamountRecordsService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("A", num);
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
	 * 删除  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			pamountRecordsService.enabledEntitys(ids, isenabled);
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
	 * 正常收取的流水情况
	 * 重写了getResult方法,
	 * @return
	 * @throws Exception
	 */
	
	public String listRepDedut()throws Exception {
		try {
			String contractId = getVal("contractId");
			if(!StringHandler.isValidStr(contractId)) new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("contractId", contractId);
			DataTable dt = pamountRecordsService.getResultList(map,getStart(),getLimit());
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
	 * 逾期还款流水,展期
	 */
	public String listPay()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = pamountRecordsService.getLoanRecordsList(map,getStart(),getLimit());
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
	 * 获取指定的 实收金额 上一个对象
	 * @return
	 * @throws Exception
	 */
	
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PamountRecordsEntity entity = pamountRecordsService.navigationPrev(params);
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
	 * 获取指定的 实收金额 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PamountRecordsEntity entity = pamountRecordsService.navigationNext(params);
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
