package com.cmw.action.finance;


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
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.RateEntity;
import com.cmw.service.inter.finance.RateService;


/**
 * 利率  ACTION类
 * @author 彭登浩
 * @date 2012-12-06T00:00:00
 */ 
@Description(remark="利率ACTION",createDate="2012-12-06T00:00:00",author="彭登浩",defaultVals="sysRate_")
@SuppressWarnings("serial")
public class RateAction extends BaseAction {

	@Resource(name="rateService")
	private RateService rateService;
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取 利率 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("types", getIVal("types"));
			map.put("isenabled", getIVal("isenabled"));
			DataTable dt = rateService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 利率 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			RateEntity entity = rateService.getEntity(Long.parseLong(id));
			Date bdate = entity.getBdate();
			String form = String.format("%tF", bdate);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("bdate", form);
			result = ResultMsg.getSuccessMsg(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 读取当前日期下的利率信息
	 * @return
	 * @throws Exception
	 */
	public String read()throws Exception {
		try {
			DataTable dt = rateService.getResultList();
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 利率 
	 * @return
	 * @throws Exception
	 */
	
	public String save()throws Exception {
		try {
			RateEntity entity = BeanUtil.copyValue(RateEntity.class,getRequest());
			rateService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 验证 利率  
	 * @return
	 * @throws Exception
	 */
	
	public String valid()throws Exception {
		try {
			Long id = getLVal("id");
			Integer types = getIVal("types");
			Integer limits = getIVal("limits");
			String bdate = getVal("bdate");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			if(StringHandler.isValidObj(id)){
				params.put("id", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC +id);
			}
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC +SysConstant.OPTION_DEL);
			params.put("types", types);
			params.put("limits", limits);
			List<RateEntity> rates = rateService.getEntityList(params);
			boolean success = true;
			String msg = "ok";
			if(null != rates && rates.size() > 0){
				Date s_bdate = DateUtil.dateFormat(bdate);
				bdate = DateUtil.dateFormatToStr(s_bdate);
				for(RateEntity entity : rates){
					Date _bdate = entity.getBdate();
					String _bdateStr = DateUtil.dateFormatToStr(_bdate);
					if(bdate.equals(_bdateStr)){
						success = false;
						msg = "no";
						break;
					}
				}
			}
			
			JSONObject appendParams = new JSONObject();
			appendParams.put("success", success);
			appendParams.put("msg", msg);
			result = appendParams.toJSONString();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  利率 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = rateService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  业务利率 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  业务利率 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  业务利率  
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  利率 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			rateService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 利率 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RateEntity entity = rateService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				Date bdate = entity.getBdate();
				String form = String.format("%tF", bdate);
				appendParams.put("bdate", form);
				result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 获取指定的 利率 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RateEntity entity = rateService.navigationNext(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Date bdate = entity.getBdate();
				String form = String.format("%tF", bdate);
				appendParams.put("bdate", form);
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
}
