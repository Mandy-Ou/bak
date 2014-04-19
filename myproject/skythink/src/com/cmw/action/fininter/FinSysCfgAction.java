package com.cmw.action.fininter;


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
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.service.impl.fininter.external.DbPool;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;


/**
 * 财务系统配置  ACTION类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务系统配置ACTION",createDate="2013-03-28T00:00:00",author="程明卫",defaultVals="fsFinSysCfg_")
@SuppressWarnings("serial")
public class FinSysCfgAction extends BaseAction {
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	//树工具类
	private TreeUtil treeUtil = new TreeUtil();
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取菜单列表
	 * @return
	 * @throws Exception
	 */
	public String tree()throws Exception {
		if(!isLoad()) return null;
		try {
			DataTable dt = finSysCfgService.getResultList();
			treeUtil.setIconPath("./");
			treeUtil.setDt(dt);
			result = treeUtil.getJsonArr("0");
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
	 * 获取 财务系统配置 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = finSysCfgService.getResultList(map,getStart(),getLimit());
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
	 * 获取 财务系统配置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id) && id.equals("-1")){
				return null;
			}
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FinSysCfgEntity entity = finSysCfgService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
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
	
	/**
	 * 保存 财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			FinSysCfgEntity entity = BeanUtil.copyValue(FinSysCfgEntity.class,getRequest());
			Long id = entity.getId();
			if(!StringHandler.isValidObj(id)){	
				String code  = entity.getCode();
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("code", code);
				FinSysCfgEntity FsEntity = finSysCfgService.getEntity(params);
				if(FsEntity!=null){
					HashMap<String,Object> append = new HashMap<String, Object>();
					append.put("code", code);
					result = ResultMsg.getFailureMsg(append);
					return null;
				}
			}
				finSysCfgService.saveOrUpdateEntity(entity);
				String finsysCode = entity.getCode();
				DbPool.markChanged(finsysCode);
				result = ResultMsg.getSuccessMsg(this, entity, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result =ResultMsg.getFailureMsg(ex.getMessage());
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result =ResultMsg.getFailureMsg(ex.getMessage());
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	
	/**
	 * 新增  财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = finSysCfgService.getMaxID();
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
	 * 财务系统连接测试
	 * @return
	 * @throws Exception
	 */
	public String test()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			
			FinSysCfgEntity entity = finSysCfgService.getEntity(Long.parseLong(id));
			String finsysCode = entity.getCode();
			FinSysService fservice = FinSysFactory.getInstance(finsysCode);
			fservice.testConnection();
			 entity = finSysCfgService.getEntity(Long.parseLong(id));
			JSONObject appendObj = new JSONObject();
			
			appendObj.put("server", entity.getServer());
			appendObj.put("spAccgroup", entity.getSpAccgroup());
			appendObj.put("spUserName", entity.getSpUserName());
			appendObj.put("spCurrency", entity.getSpCurrency());
			appendObj.put("spSettle", entity.getSpSettle());
			appendObj.put("spVhgroup", entity.getSpVhgroup());
			appendObj.put("spImclass", entity.getSpImclass());
			appendObj.put("spCustomer", entity.getSpCustomer());
			result = ResultMsg.getSuccessMsg(appendObj);
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
	 * 删除前检查是否有关联项
	 */
	public String  isNotLink() throws Exception{
		try {
			Long id= getLVal("id");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("finsysId", id);
			List<BussFinCfgEntity> BussFinCfgList  = bussFinCfgService.getEntityList(params);
			if(!BussFinCfgList.isEmpty() && BussFinCfgList.size()>0){
				result = ResultMsg.getFailureMsg(ResultMsg.DELETE_Link_Not);
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
	 * 删除  财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		
			return enabled(ResultMsg.DELETE_SUCCESS);
		
	}
	
	/**
	 * 启用  财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
			return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  财务系统配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			finSysCfgService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 财务系统配置 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FinSysCfgEntity entity = finSysCfgService.navigationPrev(params);
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
	 * 获取指定的 财务系统配置 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FinSysCfgEntity entity = finSysCfgService.navigationNext(params);
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
