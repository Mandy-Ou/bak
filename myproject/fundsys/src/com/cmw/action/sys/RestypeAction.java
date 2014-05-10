package com.cmw.action.sys;


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
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;


/**
 * 资源  ACTION类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="资源ACTION",createDate="2012-11-19T00:00:00",author="彭登浩",defaultVals="sysRestype_")
@SuppressWarnings("serial")
public class RestypeAction extends BaseAction {
	@Resource(name="restypeService")
	private RestypeService restypeService;
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 资源 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = restypeService.getResultList(map,getStart(),getLimit());
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
	 * 获取 资源 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			RestypeEntity entity = restypeService.getEntity(Long.parseLong(id));
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
	 * 保存 资源 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UserEntity user = getCurUser();
			RestypeEntity entity = BeanUtil.copyValue(RestypeEntity.class,getRequest());
			Long id = entity.getId();
			String name  =  entity.getName();
			String recode  =  entity.getRecode();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			String errMsg = null;
			if(null != id){/*修改时，如果将名称或引用键修改为资源表中已存在且不是自身的数据时，给出提示*/
				params.put("id", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC + id);
			}
			
			params.put("name", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + name);
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			List<RestypeEntity> rentity  = restypeService.getEntityList(params);
		
			if(null != rentity && rentity.size()>0){
				errMsg = StringHandler.formatFromResource(user.getI18n(), "restype.have", name);
			}else{
				params.remove("name");
				params.put("recode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + recode);
				RestypeEntity recodeentity  = restypeService.getEntity(params);
				if(null!=recodeentity){
					errMsg = StringHandler.formatFromResource(user.getI18n(), "restype.recode.have", recode);
				}
			}
			
			if(StringHandler.isValidStr(errMsg)){
				Map<String,Object> appendParams = new HashMap<String, Object>();
				appendParams.put("msg", errMsg);
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				restypeService.saveOrUpdateEntity(entity);
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  资源 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = restypeService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
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
	 * 删除  资源 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled();
	}
	
	/**
	 * 启用  资源 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			restypeService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 禁用  资源 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled();
	}
	
	/**
	 * 获取指定的 资源 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RestypeEntity entity = restypeService.navigationPrev(params);
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
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 资源 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RestypeEntity entity = restypeService.navigationNext(params);
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
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 将所有可用资源保存为文件
	 * @return
	 * @throws Exception
	 */
	public String savefile()throws Exception {
		try {
			DataTable restypeDt = restypeService.getResultList();
			DataTable gvlistDt = gvlistService.getResultList();
			makeDatas(restypeDt,gvlistDt);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private void makeDatas(DataTable restypeDt, DataTable gvlistDt){
		//"name","recode"
		//"id","isenabled","code","name","restypeId","ename","jname","twname","fname","koname"
		if(null == restypeDt || restypeDt.getRowCount() == 0) return;
		if(null == gvlistDt || gvlistDt.getRowCount() == 0) return;
		int len = gvlistDt.getRowCount();
		JSONObject dataObj = new JSONObject();
		
		for(int i=0,count=restypeDt.getRowCount(); i<count; i++){
			String id = restypeDt.getString(i, "id");
			String name = restypeDt.getString(i, "name");
			String recode = restypeDt.getString(i, "recode");
			if(!StringHandler.isValidStr(recode)) continue;
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObj = null;
			for(int j=0; j<len; j++){
				String restypeId = gvlistDt.getString(j, "restypeId");
				if(id.equals(restypeId)){
					jsonObj = new JSONObject();
					jsonObj.put("id", gvlistDt.getLong(j, "id"));
					jsonObj.put("name", gvlistDt.getString(j, "name"));
					jsonObj.put("ename", gvlistDt.getString(j, "ename"));
					jsonObj.put("jname", gvlistDt.getString(j, "jname"));
					jsonObj.put("twname", gvlistDt.getString(j, "twname"));
					jsonObj.put("fname", gvlistDt.getString(j, "fname"));
					jsonObj.put("koname", gvlistDt.getString(j, "koname"));
					jsonArray.add(jsonObj);
				}
			}
			if(jsonArray.size() > 0){
				JSONObject tempObj = new JSONObject();
				tempObj.put("restypeName", name);
				tempObj.put("totalSize", jsonArray.size());
				tempObj.put("list", jsonArray);
				dataObj.put(recode, tempObj);
			}
		}
		
		String filePath = FileUtil.getFilePath(getRequest(), StringHandler.GetResValue("gvlist_data_filepath"));
		String data = "var GvlistDatas = "+ dataObj.toString();
		FileUtil.writeStrToFile(filePath, data);
	}
}
