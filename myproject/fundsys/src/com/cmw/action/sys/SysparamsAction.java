package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
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
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.service.impl.job.BussJobManager;
import com.cmw.service.inter.sys.SysparamsService;


/**
 * 系统参数  ACTION类
 * @author pengdenghao
 * @date 2012-11-28T00:00:00
 */
@Description(remark="系统参数ACTION",createDate="2012-11-28T00:00:00",author="pengdenghao",defaultVals="sysSysparams_")
@SuppressWarnings("serial")
public class SysparamsAction extends BaseAction {
	@Resource(name="sysparamsService")
	private SysparamsService sysparamsService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 系统参数 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysid", getLVal("sysid"));
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = sysparamsService.getResultList(map,getStart(),getLimit());
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
	 * 获取 系统参数 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SysparamsEntity entity = sysparamsService.getEntity(Long.parseLong(id));
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
	 * 
	 */
	public String getParamsName() throws Exception{
		try {
			SHashMap<String, Object> params = getQParams("name,recode,sysid#L");
			String name = getVal("name");
			String recode = getVal("recode");
			String sysid = getVal("sysid");
			if(!StringHandler.isValidStr(sysid)) throw new ServiceException(ServiceException.NO_SYSID_ERROR);
			if(StringHandler.isValidStr(name)) params.put("name", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + name);
			if(StringHandler.isValidStr(recode)) params.put("recode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + recode);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			params.put("sysid", Long.parseLong(sysid));
			SysparamsEntity entity = sysparamsService.getEntity(params);
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
	 * 保存 系统参数 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			SysparamsEntity entity = BeanUtil.copyValue(SysparamsEntity.class,getRequest());
			sysparamsService.saveOrUpdateEntity(entity);
			 String recode = entity.getRecode();
			 String time = entity.getVal();
			 BussJobManager.changeJob(recode, time);
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
	 * 新增  系统参数 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = sysparamsService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("S", num);
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
			sysparamsService.enabledEntitys(ids, isenabled);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			List<SysparamsEntity> list = sysparamsService.getEntityList(map);
			String time = null;
			if(isenabled.intValue() == SysConstant.OPTION_DEL ||
			  isenabled.intValue() == SysConstant.OPTION_DISABLED){
				time = "-1";
				for(SysparamsEntity entity : list){
					String recode = entity.getRecode();
					BussJobManager.changeJob(recode, time);/*删除定时器*/
				}
			}else{/*起用状态*/
				for(SysparamsEntity entity : list){
					String recode = entity.getRecode();
					String val = entity.getVal();
					BussJobManager.changeJob(recode, val);/*起用定时器*/
				}
			}
		
		
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
	 * 获取指定的 系统参数 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SysparamsEntity entity = sysparamsService.navigationPrev(params);
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
	 * 获取指定的 系统参数 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SysparamsEntity entity = sysparamsService.navigationNext(params);
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
