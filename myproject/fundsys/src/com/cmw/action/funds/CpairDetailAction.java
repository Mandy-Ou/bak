package com.cmw.action.funds;

import java.math.BigDecimal;
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
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.funds.CapitalPairEntity;
import com.cmw.entity.funds.CpairDetailEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.funds.CapitalPairService;
import com.cmw.service.inter.funds.CpairDetailService;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.sys.RestypeService;

/**
 * 资金配对ACTION
 * 
 * @author 李听
 * @date 2014-01-15T00:00:00
 */

@Description(remark = "资金配对ACTION", createDate = "2014-01-15T00:00:00", author = "李听", defaultVals = "fuCpairDetail_")
@SuppressWarnings("serial")
public class CpairDetailAction extends BaseAction {
	@Resource(name = "cpairDetailService")
	private CpairDetailService cpairDetailService;
	@Resource(name = "restypeService")
	private RestypeService restypeService;
	@Resource(name = "capitalPairService")
	private CapitalPairService capitalPairService;
	@Resource(name = "loanContractService")
	private LoanContractService loanContractService;
	@Resource(name = "entrustContractService")
	private EntrustContractService entrustContractService;
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
			DataTable dt = cpairDetailService.getResultList(map, getStart(),
					getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA
					: dt.getJsonArr(new JsonDataCallback() {
						public void makeJson(JSONObject jsonObj) {
							Long creator = jsonObj.getLong("creator");
							try {
								UserEntity creatorObj = UserCache
										.getUser(creator);
								if (null != creatorObj)
									jsonObj.put("creator",creatorObj.getEmpName());
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
	 * 获取 委托客户资料 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getName() throws Exception {
		try {
			Long sysId = getLVal("sysId");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysId", sysId);
			DataTable dt = restypeService.getLoanRecordsList(map, -1, -1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA
					: dt.getJsonArr(new JsonDataCallback() {
						public void makeJson(JSONObject jsonObj) {
							// Long creator = jsonObj.getLong("creator");
							// try {
							// UserEntity creatorObj =
							// UserCache.getUser(creator);
							// if(null != creatorObj) jsonObj.put("creator",
							// creatorObj.getEmpName());
							// } catch (ServiceException e) {
							// e.printStackTrace();
							// }
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
			String id = getVal("id");
			if (!StringHandler.isValidStr(id))
				throw new ServiceException(ServiceException.ID_IS_NULL);
			CpairDetailEntity entity = cpairDetailService.getEntity(Long
					.parseLong(id));
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

	public String getCusName() throws Exception {
		try {
			String applyId = getVal("id");
			Long id = Long.parseLong(applyId);
			if (!StringHandler.isValidStr(applyId))
				throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("formId", id);
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			LoanContractEntity entity = loanContractService.getEntity(map);
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
			String Edit = getVal("Edit");
			String content = getVal("content");
			UserEntity userEntity = this.getCurUser();
			JSONArray jsonArray = FastJsonUtil.convertStrToJSONArr(content);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				BigDecimal amt = jsonObject.getBigDecimal("amt");
				Long id = jsonObject.getLong("id");
				EntrustContractEntity entrustContractEntity = entrustContractService.getEntity(id);
				BigDecimal appamount = entrustContractEntity.getAppAmount();
				entrustContractEntity.setUamount(BigDecimalHandler.sub2BigDecimal(appamount, amt));
			}
			List<CapitalPairEntity> list = FastJsonUtil.convertJsonToList(Edit,CapitalPairEntity.class);
			for (CapitalPairEntity capitalPairEntity : list) {
				BeanUtil.setCreateInfo(userEntity, capitalPairEntity);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			capitalPairService.doComplexBusss(map);
			result = ResultMsg.getSuccessMsg(ResultMsg.SAVE_SUCCESS);
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
	 * 新增 委托客户资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			Long num = cpairDetailService.getMaxID();
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
			cpairDetailService.enabledEntitys(ids, isenabled);
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
			CpairDetailEntity entity = cpairDetailService
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
			CpairDetailEntity entity = cpairDetailService
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
