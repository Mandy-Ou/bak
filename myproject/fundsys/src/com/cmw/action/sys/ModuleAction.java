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
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.ModuleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.MenuService;
import com.cmw.service.inter.sys.ModuleService;


/**
 * 模块  ACTION类	
 * @author chengmingwei
 * @date 2012-10-31T00:00:00
 */
@Description(remark="模块ACTION",createDate="2012-10-31T00:00:00",author="chengmingwei",defaultVals="sysModule_")
@SuppressWarnings("serial")
public class ModuleAction extends BaseAction {
	@Resource(name="moduleService")
	private ModuleService moduleService;
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 模块 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("menuId", getLVal("menuId"));
			DataTable dt = moduleService.getResultList(map,getStart(),getLimit());
			if(dt != null){
				Integer rowSize = dt.getRowCount();
					for(int i=0;i<rowSize;i++){
						Long menuId =  dt.getLong(i, "menuId");
						MenuEntity menu = menuService.getEntity(menuId);
						String menuName = menu.getName();
						dt.setCellData(i, "menuId", menuName);
					}
				}
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
	 * 获取 模块 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ModuleEntity entity = moduleService.getEntity(Long.parseLong(id));
			Long menuId = entity.getMenuId();
			MenuEntity menu = menuService.getEntity(menuId);
			final String menuName = menu.getName();
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
					public void execute(JSONObject jsonObj) {
						jsonObj.put("menuName", menuName);
					}
				});
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 保存 模块 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ModuleEntity entity = BeanUtil.copyValue(ModuleEntity.class,getRequest());
			moduleService.saveOrUpdateEntity(entity);
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
	 * 批量保存 模块 	（工具栏自动插入）
	 * @return
	 * @throws Exception
	 */
	public String batchsave()throws Exception {
		try {
			Long menuId = getLVal("menuId");
			String modulesData = getVal("modules");
			List<ModuleEntity> moduleList = FastJsonUtil.convertJsonToList(modulesData, ModuleEntity.class);
			if(null != moduleList && moduleList.size() > 0){
				UserEntity user = this.getCurUser();
				SHashMap<String, Object> qparams = new SHashMap<String, Object>();
				qparams.put("menuId", menuId);
				for(ModuleEntity modEntity : moduleList){
					qparams.put("name", modEntity.getName());
					ModuleEntity existEntity = moduleService.getEntity(qparams);
					if(null != existEntity) continue;
					modEntity.setMenuId(menuId);
					String code = CodeRule.getCode("M", moduleService.getMaxID());
					modEntity.setCode(code);
					BeanUtil.setCreateInfo(user, modEntity);
					moduleService.saveOrUpdateEntity(modEntity);
				}
			}
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		}catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  模块 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long menuId = getLVal("menuId");
			Long num = moduleService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("M", num);
			MenuEntity menu = menuService.getEntity(menuId);
			String menuName = menu.getName();
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			appendParams.put("code", code);
			appendParams.put("menuName", menuName);
			result = ResultMsg.getSuccessMsg(appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 删除  模块 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.ID_IS_NULL);
			moduleService.enabledEntitys(ids, SysConstant.OPTION_DEL);
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
	 * 启用  模块 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			moduleService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_ENABLED);
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
	 * 禁用  模块 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			moduleService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_DISABLED);
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
	 * 获取指定的 模块 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ModuleEntity entity = moduleService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(entity==null){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				Long menuId = entity.getMenuId();
				MenuEntity mEntity = menuService.getEntity(menuId);
				String menuName = mEntity.getName();
				appendParams.put("menuName", menuName);
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
	 * 获取指定的 模块 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ModuleEntity entity = moduleService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(entity==null){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Long menuId = entity.getMenuId();
				MenuEntity mEntity = menuService.getEntity(menuId);
				String menuName = mEntity.getName();
				appendParams.put("menuName", menuName);
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
