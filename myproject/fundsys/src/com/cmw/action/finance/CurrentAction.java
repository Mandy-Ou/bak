package com.cmw.action.finance;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.CurrentService;
import com.cmw.service.inter.fininter.AmountLogService;

/**
 * 随借随还还款管理action
 *Title: CurrentAction.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-25下午12:42:22
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark=" 随借随还还款管理ACTION",createDate="2013-11-22T00:00:00",author="彭登浩",defaultVals="fcCurrent_")
@SuppressWarnings("serial")
public class CurrentAction extends BaseAction{
	@Resource(name="currentService")
	private CurrentService currentService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取 随借随还还款管理列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("id#L,custType#I,name,code,accName,payBank,payAccount");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = currentService.getCurrentList(map,getStart(),getLimit());
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
	 * 获取 随借随还还款管理详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long contractId = getLVal("id");
			if(contractId == null) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", contractId);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = currentService.getCurrentList(map,getStart(),getLimit());
		
			result = ResultMsg.NODATA;
			if(null != dt && dt.getRowCount() > 0){
				Date payDate = currentService.getPayDate(contractId);
				dt.setCellData(0, "realDate#yyyy-MM-dd", payDate);
				JSONObject jsonObj = dt.getJsonObj();
				Date lastDate = currentService.getLastDate(contractId);
				if(null != lastDate) jsonObj.put("lastDate", DateUtil.dateFormatToStr(lastDate));
				result = jsonObj.toJSONString();
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
	 * 获取 计算后的随借随还利息、管理费、罚息、滞纳金结果
	 * @return
	 * @throws Exception
	 */
	public String calculate()throws Exception {
		try {
			Long contractId = getLVal("contractId");
			String rectDate = getVal("rectDate");
			if(contractId == null) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(rectDate == null) throw new ServiceException(ServiceException.CURRENT_RECDATE_ISNOTNULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("contractId", contractId);
			map.put("rectDate", rectDate);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			JSONObject jsonObj = currentService.calculate(map);
			result = (null == jsonObj || jsonObj.size() == 0) ? ResultMsg.NODATA : jsonObj.toJSONString();
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
	 * 随借随还收款 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String receivables()throws Exception {
		try {
			String parNames = "contractId#L,zprincipal,zinterest,zmgrAmount,zpenAmount,zdelAmount,ztotalAmount,"+
					"principal,interest,mgrAmount,penAmount,delAmount,fat,tat,oddAmount,planId#L,c_iamount,c_mamount,lateDatas,"+
					"key,isVoucher#I,vtempCode,sysId#L,accountId#L,rectDate";
			SHashMap<String,Object> complexData = getQParams(parNames);
				
			Long accountId = complexData.getvalAsLng("accountId");
			String rectDate = complexData.getvalAsStr("rectDate");
			
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("收款银行不能为空!");
			if(!StringHandler.isValidStr(rectDate)) throw new ServiceException("实际收款日期不能为空!");
			UserEntity user = getCurUser();
			complexData.put(SysConstant.USER_KEY, user);
			Map<AmountLogEntity,DataTable> logDataMap = (Map<AmountLogEntity,DataTable>)currentService.doComplexBusss(complexData);
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
}
