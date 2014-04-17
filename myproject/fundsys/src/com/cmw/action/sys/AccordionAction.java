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
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.entity.sys.SystemEntity;
import com.cmw.service.inter.sys.AccordionService;
import com.cmw.service.inter.sys.SystemService;

/**
 * @author 程明卫 E-mail:chengmingwei_1984122@126.com
 * @version 创建时间：2010-6-15 下午12:14:31
 * 类说明 	卡片卡片菜单 ACTION   
 */
@Description(remark="卡片菜单 ACTION",createDate="2010-6-15",defaultVals="sysAccordion_")
@SuppressWarnings("serial")
public class AccordionAction extends BaseAction {
	@Resource(name="accordionService")
	private AccordionService accordionService;
	@Resource(name="systemService")
	private SystemService systemService;
	
	
	private String result = ResultMsg.GRID_NODATA;
	//树工具类
	private TreeUtil treeUtil = new TreeUtil();
	/**
	 * 获取卡片菜单列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = getQParams("name,sysid#L");
			//map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("id", getLVal("id"));
			DataTable dt = accordionService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getSize() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取卡片菜单列表
	 * @return
	 * @throws Exception
	 */
	public String navigate()throws Exception {
		try { 
			
			//获取卡片项的ID，并将其作为 父ID
			SHashMap<String, Object> map = getQParams("name,sysid#L");
			map.put("action", getIVal("action"));
			map.put("code", SysConstant.ACCORDION_CODE_TYPE_FORM_PREFIX+","+SysConstant.ACCORDION_CODE_TYPE_SUBFORM_PREFIX);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = accordionService.getResultList(map);
			
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr(SysConstant.MENU_ROOT_ID+"");
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
	 * 获取卡片列表详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AccordionEntity entity = accordionService.getEntity(Long.parseLong(id));
			String sysname = "";
			Long sysId = entity.getSysid();
			if(null != sysId){
				SystemEntity sysEntity = systemService.getEntity(sysId);
				if(null != sysEntity) sysname = sysEntity.getName();
			}
			
			Map<String,Object> appendMap = new HashMap<String, Object>();
			appendMap.put("sysname", sysname);
			result = FastJsonUtil.convertJsonToStr(entity, appendMap);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			AccordionEntity entity = BeanUtil.copyValue(AccordionEntity.class,getRequest());
			accordionService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = accordionService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("A", num);
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
	 * 删除  起用卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  起用卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  起用卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/禁用/起用卡片菜单
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			accordionService.enabledEntitys(ids, isenabled);
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
}
