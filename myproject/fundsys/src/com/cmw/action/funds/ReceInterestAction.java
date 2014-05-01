package com.cmw.action.funds;

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
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.ReceInterestEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.funds.ReceInterestService;

/**
 * 汇票利润提成ACTION
 * 
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */

@Description(remark = "汇票利润提成ACTION", createDate = "2014-01-15T00:00:00", author = "彭登浩", defaultVals = "fuReceInterest_")
@SuppressWarnings("serial")
public class ReceInterestAction extends BaseAction {
	@Resource(name = "receInterestService")
	private ReceInterestService receInterestService;
	
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取 汇票利润提成 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = receInterestService.getResultList(map, getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取 汇票利润提成 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get() throws Exception {
		try {
			String id  = getVal("id");
			if(StringHandler.isValidStr("id")) new ServiceException(ServiceException.ID_IS_NULL);
			ReceInterestEntity ReceInterestEntity = receInterestService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(ReceInterestEntity,new Callback(){
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
	 * 保存 汇票利润提成资料
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String save() throws Exception {
		try {
			String batchDatas = getVal("batchDatas");
			UserEntity user = this.getCurUser();
			if(!StringHandler.isValidStr(batchDatas)){
				 new ServiceException(ServiceException.OBJECT_BATCH_SAVEORUPDATE_FAILURE);
			}else {
				List<ReceInterestEntity> receList = FastJsonUtil.convertJsonToList(batchDatas, ReceInterestEntity.class);
				if(!receList.isEmpty()){
					for(ReceInterestEntity x: receList){
						Long id = x.getId();
						if(id == null){
							BeanUtil.setCreateInfo(user, x);
						}else {
							BeanUtil.setModifyInfo(user, x);
						}
					}
					receInterestService.batchSaveOrUpdateEntitys(receList);
					result = ResultMsg.getSuccessMsg();
				}
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
	 * 新增 汇票利润提成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			Long num = receInterestService.getMaxID();
			if (null == num) throw new ServiceException( ServiceException.OBJECT_MAXID_FAILURE);
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
	 * 删除 汇票利润提成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		String ids = getVal("ids");
		if(StringHandler.isValidStr(ids)) new  ServiceException(ServiceException.IDS_IS_NULL);
		receInterestService.deleteEntitys(ids);
		result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
		outJsonString(result);
		return null;
	}

	/**
	 * 启用 汇票利润提成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}

	/**
	 * 禁用 汇票利润提成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disabled() throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}

	/**
	 * 删除/起用/禁用 汇票利润提成
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg) throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			receInterestService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 汇票利润提成 上一个对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prev() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			ReceInterestEntity entity = receInterestService.navigationPrev(params);
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
	 * 获取指定的 汇票利润提成下一个对象
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public String next() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			ReceInterestEntity entity = receInterestService.navigationNext(params);
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
