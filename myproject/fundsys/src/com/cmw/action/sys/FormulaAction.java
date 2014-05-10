package com.cmw.action.sys;


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
import com.cmw.core.kit.ikexpression.FormulaUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.RateEntity;
import com.cmw.entity.fininter.FinCustFieldEntity;
import com.cmw.entity.sys.FormulaEntity;
import com.cmw.service.impl.fininter.external.generic.FinSysBaseDataCache;
import com.cmw.service.inter.finance.RateService;
import com.cmw.service.inter.fininter.FinCustFieldService;
import com.cmw.service.inter.sys.FormulaService;


/**
 * 公式  ACTION类
 * @author pdh
 * @date 2012-12-06T00:00:00
 */
@Description(remark="公式ACTION",createDate="2012-12-06T00:00:00",author="pdh",defaultVals="sysFormula_")
@SuppressWarnings("serial")
public class FormulaAction extends BaseAction {
	@Resource(name="formulaService")
	private FormulaService formulaService;
	@Resource(name="finCustFieldService")
	private FinCustFieldService finCustFieldService;
	@Resource(name="rateService")
	private RateService rateService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 公式 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = formulaService.getResultList(map,getStart(),getLimit());
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
	 * 获取 公式 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			String fieldSource = getVal("fieldSource");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FormulaEntity entity = formulaService.getEntity(Long.parseLong(id));
			String fieldIds = entity.getFieldIds();
			Map<String,Object> dataMap = null;
			if(StringHandler.isValidStr(fieldIds)){
				if(StringHandler.isValidStr(fieldSource) && fieldSource.equals("FinCustField")){/*财务系统自定义字段表*/
					dataMap = getFinCustFields(fieldIds);
				}else{
					throw new ServiceException("该逻辑未实现!");
				}
			}

			final Map<String,Object> fieldDatas = dataMap;
			result = FastJsonUtil.convertJsonToStr(entity, new Callback(){
				public void execute(JSONObject jsonObj) {
					if(null != fieldDatas && fieldDatas.size() > 0){
						jsonObj.put("fieldDatas", fieldDatas);
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

	private Map<String,Object> getFinCustFields(String fieldIds) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + fieldIds);
		List<FinCustFieldEntity> fields = finCustFieldService.getEntityList(params);
		if(null == fields || fields.size() == 0) return null;
		Map<String,Object> dataMap = new HashMap<String, Object>();
		for(FinCustFieldEntity field : fields){
			Long id = field.getId();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("name", field.getName());
			data.put("field", field.getField());
			data.put("dataType", field.getDataType());
			dataMap.put(id.toString(), data);
		}
		return dataMap;
	}
	
	/**
	 * 保存 公式 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			FormulaEntity entity = BeanUtil.copyValue(FormulaEntity.class,getRequest());
			if(null == entity.getId()){
				Long num = formulaService.getMaxID();
				String code = CodeRule.getCode("F", num);
				entity.setCode(code);
			}
			formulaService.saveOrUpdateEntity(entity);
			FinSysBaseDataCache.updateFormulaCache(entity, FinSysBaseDataCache.OPTYPE_UPDATE);
			result = ResultMsg.getSuccessMsg(this,entity, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 公式 详情
	 * @return
	 * @throws Exception
	 */
	public String check()throws Exception {
		try {
			String expression = getVal("express");
			String fmparams = getVal("fmparams");
			String fmresult = null;
			if(StringHandler.isValidStr(fmparams)){
				Map<String,Object> params = FastJsonUtil.convertStrToMap(fmparams);
				if(null == params || params.size() == 0){
					fmresult = FormulaUtil.check(expression);
				}else{
					fmresult = FormulaUtil.check(expression,params);
				}
			}else{
				fmresult = FormulaUtil.check(expression);
			}
			result = ResultMsg.getSuccessMsg(fmresult);
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(ex.getMessage());
			if(null == result) result = ResultMsg.getFailureMsg(ResultMsg.SYSTEM_ERROR);
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 新增  公式 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = formulaService.getMaxID();
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
	 * 删除  公式 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			String ids = getVal("ids");
			RateEntity entity =  rateService.getEntity(Long.parseLong(ids));
			entity.setFormulaId(null);
			rateService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
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
	 * 启用  公式 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  公式 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String ids = getVal("ids");
			RateEntity entity =  rateService.getEntity(Long.parseLong(ids));
			entity.setIsFormula(0);
			rateService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
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
	 * 删除/起用/禁用  公式 
	 * @param <rateService>
	 * @param <entity>
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			RateEntity entity =  rateService.getEntity(Long.parseLong(ids));
			entity.setIsFormula(1);
			rateService.saveOrUpdateEntity(entity);
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
	 * 获取指定的 公式 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FormulaEntity entity = formulaService.navigationPrev(params);
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
	 * 获取指定的 公式 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FormulaEntity entity = formulaService.navigationNext(params);
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
