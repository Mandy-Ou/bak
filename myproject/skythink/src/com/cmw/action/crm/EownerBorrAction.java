package com.cmw.action.crm;


import javax.annotation.Resource;
import com.cmw.core.base.annotation.Description;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import java.util.HashMap;
import java.util.Map;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.crm.EownerBorrEntity;
import com.cmw.service.inter.crm.EownerBorrService;


/**
 * 所有者借款情况  ACTION类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="所有者借款情况ACTION",createDate="2012-12-26T00:00:00",author="pdh",defaultVals="crmEownerBorr_")
@SuppressWarnings("serial")
public class EownerBorrAction extends BaseAction {
	@Resource(name="eownerBorrService")
	private EownerBorrService eownerBorrService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 所有者借款情况 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("ecustomerId", getLVal("ecustomerId"));
			map.put("remark", getVal("remark"));
			DataTable dt = eownerBorrService.getResultList(map,getStart(),getLimit());
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
	 * 获取 所有者借款情况 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id) && id.equals("-1")){
				outJsonString(result);
				return null;
			}
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			EownerBorrEntity entity = eownerBorrService.getEntity(Long.parseLong(id));
			HashMap<String, Object> appendParams =new  HashMap<String, Object>();
			appendParams.put("id", entity.getId());
			result = ResultMsg.getSuccessMsg(entity,appendParams);
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
	 * 保存 所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			EownerBorrEntity entity = BeanUtil.copyValue(EownerBorrEntity.class,getRequest());
			eownerBorrService.saveOrUpdateEntity(entity);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("id", entity.getId());
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,params);
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
	 * 新增  所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = eownerBorrService.getMaxID();
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
	 * 删除  所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  所有者借款情况 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			eownerBorrService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 所有者借款情况 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EownerBorrEntity entity = eownerBorrService.navigationPrev(params);
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
	 * 获取指定的 所有者借款情况 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EownerBorrEntity entity = eownerBorrService.navigationNext(params);
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
