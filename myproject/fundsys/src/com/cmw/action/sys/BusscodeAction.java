package com.cmw.action.sys;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BusscodeEntity;
import com.cmw.service.inter.sys.BusscodeService;


/**
 * 业务编号配置  ACTION类
 * @author 彭登浩
 * @date 2012-11-21T00:00:00
 */
@Description(remark="业务编号配置ACTION",createDate="2012-11-21T00:00:00",author="彭登浩",defaultVals="sysBusscode_")
@SuppressWarnings("serial")
public class BusscodeAction extends BaseAction {
	@Resource(name="busscodeService")
	private BusscodeService busscodeService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 业务编号配置 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysid", getLVal("sysid"));
			map.put("name", getVal("name"));
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = busscodeService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
			boolean i=(result==null) ? false :true;
			System.out.println(i);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 业务编号配置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BusscodeEntity entity = busscodeService.getEntity(Long.parseLong(id));
			result = JsonUtil.toJson(entity);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 业务编号配置 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			BusscodeEntity entity = BeanUtil.copyValue(BusscodeEntity.class,getRequest());
			busscodeService.saveOrUpdateEntity(entity);
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
	 * 新增  业务编号配置 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = busscodeService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("B", num);
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
	 * 删除  业务编号配置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = Integer.parseInt(getVal("isenabled"));
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.ID_IS_NULL);
			busscodeService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	

	
	/**
	 * 获取指定的 业务编号配置 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BusscodeEntity entity = busscodeService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
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
	 * 获取指定的 业务编号配置 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BusscodeEntity entity = busscodeService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			result = ResultMsg.getSuccessMsg(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}
