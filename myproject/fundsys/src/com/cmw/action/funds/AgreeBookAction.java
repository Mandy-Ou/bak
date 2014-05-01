package com.cmw.action.funds;

import java.math.BigDecimal;
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
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.funds.AgreeBookEntity;
import com.cmw.entity.funds.CapitalPairEntity;
import com.cmw.entity.funds.CpairDetailEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.funds.AgreeBookService;
import com.cmw.service.inter.funds.CapitalPairService;
import com.cmw.service.inter.funds.CpairDetailService;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.sys.RestypeService;
import com.cmw.service.inter.sys.VarietyService;

/**
 * 资金配对ACTION
 * 
 * @author 李听
 * @date 2014-01-15T00:00:00
 */

@Description(remark = "资金配对ACTION", createDate = "2014-01-15T00:00:00", author = "李听", defaultVals = "fuAgreeBook_")
@SuppressWarnings("serial")
public class AgreeBookAction extends BaseAction {
	@Resource(name = "agreeBookService")
	private AgreeBookService agreeBookService;
	@Resource(name = "restypeService")
	private RestypeService restypeService;
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取 委托客户资料 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			Integer status = getIVal("status");
			String name = getVal("name");
			String startDate1 = getVal("startDate1");
			String endDate1 = getVal("endDate1");
			String phone = getVal("phone");
			String contactTel = getVal("contactTel");
			String inAddress = getVal("inAddress");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("status", status);
			map.put("name", name);
			map.put("startDate1", startDate1);
			map.put("endDate1", endDate1);
			map.put("phone", phone);
			map.put("contactTel", contactTel);
			map.put("inAddress", inAddress);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = agreeBookService.getResultList(map, getStart(),
					getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA
					: dt.getJsonArr(new JsonDataCallback() {
						public void makeJson(JSONObject jsonObj) {
							Long creator = jsonObj.getLong("creator");
							try {
								UserEntity creatorObj = UserCache
										.getUser(creator);
								if (null != creatorObj)
									jsonObj.put("creator",
											creatorObj.getEmpName());
							} catch (ServiceException e) {
								e.printStackTrace();
							}
						}
					});
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}


	/**
	 * 获取 委托客户资料 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get() throws Exception {
		try {
			Long formId = getLVal("formId");
			Long customerId = getLVal("customerId");
			if(!StringHandler.isValidObj(formId))
				throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params=new SHashMap<String, Object>();
			params.put("formId", formId);
			params.put("customerId", customerId);
			AgreeBookEntity entity = agreeBookService.getEntity(params);
			result = FastJsonUtil.convertJsonToStr(entity, new Callback() {
				public void execute(JSONObject jsonObj) {

				}
			});
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}


	/**
	 * 保存 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String save() throws Exception {
		try {
			AgreeBookEntity entity = BeanUtil.copyValue(AgreeBookEntity.class,getRequest());
			agreeBookService.saveOrUpdateEntity(entity);
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
	 * 新增 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			Long num = agreeBookService.getMaxID();
			if (null == num)
				throw new ServiceException(
						ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
			result = JsonUtil.getJsonString("code", code);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 删除 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}

	/**
	 * 启用 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}

	/**
	 * 禁用 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disabled() throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}

	/**
	 * 删除/起用/禁用 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg) throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			agreeBookService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this, sucessMsg);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 委托客户资料 上一个对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prev() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			AgreeBookEntity entity = agreeBookService
					.navigationPrev(params);
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getFirstMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 委托客户资料 下一个对象
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public String next() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			AgreeBookEntity entity = agreeBookService
					.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getLastMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

}
