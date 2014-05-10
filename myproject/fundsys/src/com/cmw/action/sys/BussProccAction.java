package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.MenuService;


/**
 * 子业务流程  ACTION类
 * @author 程明卫
 * @date 2013-08-26T00:00:00
 */
@Description(remark="子业务流程ACTION",createDate="2013-08-26T00:00:00",author="程明卫",defaultVals="sysBussProcc_")
@SuppressWarnings("serial")
public class BussProccAction extends BaseAction {
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	@Autowired
	private MenuService menuService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 子业务流程 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("sysId#L,name,bussType#I,menuName");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = bussProccService.getResultList(map,getStart(),getLimit());
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
	 * 获取 子业务流程 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			final BussProccEntity entity = bussProccService.getEntity(Long.parseLong(id));
			final String funName = getMenuFun(entity);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					if(StringHandler.isValidStr(funName)) jsonObj.put("menuId", funName);
					String companyIds = entity.getCompanyIds();
					if(StringHandler.isValidStr(companyIds)) jsonObj.put("companyName", companyIds+"##"+entity.getCompanyNames());
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
	 * 获取 子业务流程条款列表
	 * @return
	 * @throws Exception
	 */
	public String getClauseList()throws Exception {
		try {
			final Long menuId = getLVal("menuId");
			if(!StringHandler.isValidObj(menuId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			List<BussProccEntity> entitys = BussProccCache.getList(new ElementCompare() {
				@Override
				public boolean equals(Object obj) {
					BussProccEntity eqEntity = (BussProccEntity)obj;
					return ((null != eqEntity.getMenuId() && eqEntity.getMenuId().equals(menuId)) && (null != eqEntity.getPdid()));
				}
			});
			if(null == entitys || entitys.size() == 0){
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("menuId", menuId);
				map.put("isenabled", SysConstant.OPTION_ENABLED);
				entitys = bussProccService.getEntityList(map);
			}
			if(null == entitys || entitys.size() == 0) throw new ServiceException(ServiceException.BUSSPROCC_NOT_CONFIG);
			UserEntity currUser = this.getCurUser();
			JSONArray jsonArr = new JSONArray();
			for(BussProccEntity entity : entitys){
				if(!canAdd(currUser,entity)) continue;
				String name = entity.getName();
				String pdid = entity.getPdid();
				String icon = entity.getIcon();
				String formUrl = entity.getFormUrl();
				String txtPath = entity.getTxtPath();
				String content = "";
				if(StringHandler.isValidStr(txtPath)){
					String absFilePath = FileUtil.getFilePath(getRequest(), txtPath);
					content = FileUtil.ReadFileToStr(absFilePath);
				}
				JSONObject obj = new JSONObject();
				obj.put("breed", entity.getId());
				obj.put("name", name);
				obj.put("pdid", pdid);
				obj.put("formUrl", formUrl);
				obj.put("icon", icon);
				obj.put("content", content);
				jsonArr.add(obj);
			}
			
			JSONObject dataList = new JSONObject();
			dataList.put("totalSize", jsonArr.size());
			dataList.put("list", jsonArr);
			result = ResultMsg.getSuccessMsg(dataList);
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
	
	private boolean canAdd(UserEntity currUser, BussProccEntity entity){
		Integer userorg = entity.getUseorg();
		Long incompanyId = currUser.getIncompId();
		if(null == userorg || userorg.intValue() == BussStateConstant.VARIETY_USEORG_0) return true;
		if(null == incompanyId) return true;
		String companyIds = entity.getCompanyIds();
		if(!StringHandler.isValidStr(companyIds)) return false;
		String[] companyIdArr = companyIds.split(",");
		boolean canAdd = false;
		for(String companyId : companyIdArr){
			if(incompanyId.longValue() == Long.parseLong(companyId)){
				canAdd = true;
				break;
			}
		}
		return canAdd;
	}
	
	/**
	 * 获取 子业务流程条款 详情
	 * @return
	 * @throws Exception
	 */
	public String getClause()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BussProccEntity entity = bussProccService.getEntity(Long.parseLong(id));
			String txtPath = entity.getTxtPath();
			String content = "";
			if(StringHandler.isValidStr(txtPath)){
				String absFilePath = FileUtil.getFilePath(getRequest(), txtPath);
				content = FileUtil.ReadFileToStr(absFilePath);
			}
			
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("id", entity.getId());
			appendParams.put("txtPath", entity.getTxtPath());
			appendParams.put("content", content);
			appendParams.put("content", content);
			result = ResultMsg.getSuccessMsg(appendParams);
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
	 * 保存 子业务流程 条款说明
	 * @return
	 * @throws Exception
	 */
	public String saveClause()throws Exception {
		try {
			Long id = getLVal("id");
			String content = getVal("content");
			String txtPath = getVal("txtPath");
			BussProccEntity entity = bussProccService.getEntity(id);
			if(!StringHandler.isValidStr(content)){
				txtPath = null;
				if(StringHandler.isValidStr(txtPath)){
					String fileName = FileUtil.getFilePath(getRequest(), txtPath);
					FileUtil.delFile(fileName);
				}
			}else{
				if(!StringHandler.isValidStr(txtPath)){
					String subprocess_clause_path = StringHandler.GetResValue("subprocess_clause_path");
					String absPath = FileUtil.getFilePath(getRequest(), subprocess_clause_path);
					if(!FileUtil.exist(absPath)) FileUtil.creatDictory(absPath);
					String fileName = System.currentTimeMillis()+".txt";
					txtPath = subprocess_clause_path+fileName;
					FileUtil.writeStrToFile(absPath, fileName, content);
				}else{
					String filePath = FileUtil.getFilePath(getRequest(), txtPath);
					FileUtil.writeStrToFile(filePath,content);
				}
			}
			entity.setTxtPath(txtPath);
			bussProccService.updateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 获取菜单关联功能
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	private String getMenuFun(BussProccEntity entity)
			throws ServiceException {
		String funName = null;
		Long menuId = entity.getMenuId();
		if(null == menuId) return null;
		MenuEntity menuEntity = menuService.getEntity(menuId);
		if(null != menuEntity){
			String name = menuEntity.getName();
			funName = menuId +"##"+ name;
		}
		return funName;
	}
	
	/**
	 * 保存 子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			BussProccEntity entity = BeanUtil.copyValue(BussProccEntity.class,getRequest(),"menuId");
			String menuIdStr = getVal("menuId");
			if(StringHandler.isValidStr(menuIdStr)){
				String[] idNames = menuIdStr.split("##");
				if(null != idNames && idNames.length > 0){
					Long menuId = Long.parseLong(idNames[0]);
					entity.setMenuId(menuId);
				}
			}
			String companyName = getVal("companyName");
			if(StringHandler.isValidStr(companyName)){
				String[] strArr = companyName.split("##");
				if(null != strArr && strArr.length > 0){
					String companyIds = strArr[0];
					String companyNames = strArr[1];
					entity.setCompanyIds(companyIds);
					entity.setCompanyNames(companyNames);
				}
			}
			bussProccService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = bussProccService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("B", num);
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
	 * 删除  子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 删除  
	 * @return
	 * @throws Exception
	 */
	public String delClause()throws Exception {
		try {
			String sucessMsg = ResultMsg.DELETE_SUCCESS;
			String ids = getVal("ids");
			BussProccEntity entity = bussProccService.getEntity(Long.parseLong(ids));
			if(null != entity && StringHandler.isValidStr(entity.getTxtPath())){
				String txtPath = entity.getTxtPath();
				entity.setTxtPath(null);
				bussProccService.updateEntity(entity);
				String absFilePath = FileUtil.getFilePath(getRequest(), txtPath);
				FileUtil.delFile(absFilePath);
			}
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
	 * 启用  子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  子业务流程 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			bussProccService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 子业务流程 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BussProccEntity entity = bussProccService.navigationPrev(params);
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
	 * 获取指定的 子业务流程 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BussProccEntity entity = bussProccService.navigationNext(params);
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
