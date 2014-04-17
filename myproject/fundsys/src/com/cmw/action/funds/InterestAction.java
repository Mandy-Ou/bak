package com.cmw.action.funds;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.funds.EntrustCustService;
import com.cmw.service.inter.funds.InterestRecordsService;
import com.cmw.service.inter.funds.InterestService;
import com.cmw.service.inter.sys.RestypeService;

/**
 * 委托客户资料  ACTION类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息支付ACTION",createDate="2014-01-15T00:00:00",author="李听",defaultVals="fuInterest_")
@SuppressWarnings("serial")
public class InterestAction extends BaseAction {
	@Resource(name="interestService")
	private InterestService interestService;
	@Resource(name="interestRecordsService")
	private InterestRecordsService interestRecordsService;
	@Resource(name="entrustCustService")
	private EntrustCustService entrustCustService;
	@Resource(name="entrustContractService")
	private EntrustContractService entrustContractService;
	@Resource(name="restypeService")
	private RestypeService restypeService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 委托客户资料 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = interestRecordsService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {/*
					Long creator = jsonObj.getLong("creator");
					try {
						UserEntity creatorObj = UserCache.getUser(creator);
						if(null != creatorObj) jsonObj.put("creator", creatorObj.getEmpName());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				*/}
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
	 * 获取 委托客户资料 列表
	 * @return
	 * @throws Exception
	 */
	public String getName()throws Exception {
		try {
			Long sysId=getLVal("sysId");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysId", sysId);
			DataTable dt = restypeService.getLoanRecordsList(map, -1, -1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
//					Long creator = jsonObj.getLong("creator");
//					try {
//						UserEntity creatorObj = UserCache.getUser(creator);
//						if(null != creatorObj) jsonObj.put("creator", creatorObj.getEmpName());
//					} catch (ServiceException e) {
//						e.printStackTrace();
//					}
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
	 * 获取 委托客户资料 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			InterestEntity entity = interestService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {}
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
	public String getCusName()throws Exception {
		try {
			String applyId = getVal("applyId");
			String id = getVal("id");
			if (!StringHandler.isValidStr(id))throw new ServiceException(ServiceException.ID_IS_NULL);
			if (!StringHandler.isValidStr(applyId))throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<Object, Object> params=new SHashMap<Object, Object>();
			params.put("applyId", applyId);
			EntrustContractEntity entrustContraEntity = entrustContractService.getEntity(params);
//			EntrustContractEntity entrustContraEntity = entrustContractService.getEntity(Long.parseLong(id));
			UserEntity user= this.getCurUser();
			if(!StringHandler.isValidObj(entrustContraEntity)){
				HashMap<String, Object> map=new HashMap<String, Object>();
				map.put("ids", -1);	
				result = ResultMsg.getSuccessMsg(map);
				outJsonString(result);
				return null;
			}
				Long entrustCustId = entrustContraEntity.getEntrustCustId();
				if (!StringHandler.isValidObj(entrustCustId))throw new ServiceException(ServiceException.ID_IS_NULL);
//				EntrustCustEntity entruEntity=entrustCustService.getEntity(entrustCustId);
				BigDecimal Iamount = entrustContraEntity.getIamount();
				Double rate = entrustContraEntity.getRate();
				//委托时间的计算
				Integer yearLoan=	entrustContraEntity.getYearLoan();
				Integer	monthLoan=entrustContraEntity.getMonthLoan();
				Date doDate=entrustContraEntity.getDoDate();//实际的付款日期：也就是每月的解析日
				BigDecimal rates = new BigDecimal(rate);
				SHashMap<Object, Object> map = new SHashMap<Object, Object>();
				map.put("entrustCustId", entrustCustId);
				map.put("entrustContractId", id);
				InterestEntity entity = interestService.getEntity(map);
				if (!StringHandler.isValidObj(entity)) {
					entity = new InterestEntity();
					entity.setEntrustCustId(entrustCustId);
					entity.setEntrustContractId(Long.parseLong(id));
					entity.setXpayDate(doDate);//实际签订的合同的日期
					entity.setIamount(Iamount.multiply(rates));
					entity.setStatus(0);
				}else{
				if(entrustCustId==entity.getEntrustCustId()){
				entity.setXpayDate(entity.getXpayDate());}}
				Integer mongth=(yearLoan*12)+monthLoan;//共有多少个下个月
				BeanUtil.setCreateInfo(user, entity);
				interestService.saveOrUpdateEntity(entity);
				DataTable dt=interestService.getResultList(map,-1,-1);
				dt.appendData("ids", new String[]{"-3"});
				result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
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
	 * 保存 委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String interestId=getVal("interestId");
			Double  iamount=getDVal("iamount");
			Double  amt=getDVal("amt");
			String nextDate=getVal("nextDate");String rectDate=getVal("rectDate");
//			String rectDateHide=getVal("rectDateHide");
			InterestRecordsEntity entity=BeanUtil.copyValue(InterestRecordsEntity.class,getRequest());
//			Date rectDates=StringHandler.dateFormat("yyyy-MM-dd", rectDateHide);
//			entity.setRectDate(rectDates);
			interestRecordsService.saveOrUpdateEntity(entity);
//			InterestEntity interEntity= interestService.getEntity(Long.parseLong(interestId));
//			if(interEntity.getNextDate()!=null){
//				interEntity.setStatus(1);//部分付息
//			}else{
//				interEntity.setStatus(2);//付息完成了
//			}
//			interestService.saveOrUpdateEntity(interEntity);
			UserEntity user = getCurUser();
			SHashMap<String,Object> complexData = new SHashMap<String, Object>();
			complexData.put("interestId", interestId);
			complexData.put("iamount", iamount);
			complexData.put("amt", amt);
			complexData.put(SysConstant.USER_KEY, user);
			complexData.put("nextDate", nextDate);
			complexData.put("rectDate", rectDate);
//			complexData.put("rectDateHide", rectDateHide);
//			@SuppressWarnings("unchecked")
			interestService.doComplexBusss(complexData);
			/*--->2:生成财务凭证*/
//			Integer isVoucher = getIVal("isVoucher");
//			if(null != isVoucher && isVoucher.intValue() == SysConstant.SAVE_VOUCHER_1) saveVouchers(logDataMap);
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
	 * 新增  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = interestService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
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
	 * 删除  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  委托客户资料 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			interestService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 委托客户资料 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			InterestEntity entity = interestService.navigationPrev(params);
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
	 * 获取指定的 委托客户资料 下一个对象
	 * @return
	 * @throws Exception
	 * 
	 */
	
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			InterestEntity entity = interestService.navigationNext(params);
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
