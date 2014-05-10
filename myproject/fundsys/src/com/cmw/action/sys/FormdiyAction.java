package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cmw.entity.sys.FormdiyEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.FieldCustomService;
import com.cmw.service.inter.sys.FieldPropService;
import com.cmw.service.inter.sys.FormdiyService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;


/**
 * 表单DIY表  ACTION类
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="表单DIY表ACTION",createDate="2012-11-23T00:00:00",author="pengdenghao",defaultVals="sysFormdiy_")
@SuppressWarnings("serial")
public class FormdiyAction extends BaseAction {
	@Resource(name="formdiyService")
	private FormdiyService formdiyService;
	@Resource(name="fieldCustomService")
	private FieldCustomService fieldCustomService;
	@Resource(name="fieldPropService")
	private FieldPropService fieldPropService;
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	@Resource(name="restypeService")
	private RestypeService restypeService;
	
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 表单DIY表 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysid", getLVal("sysid"));
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = formdiyService.getResultList(map,getStart(),getLimit());
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
	 * 获取 表单DIY表 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FormdiyEntity entity=formdiyService.getEntity(Long.parseLong(id));
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
	 * 保存 表单DIY表 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UserEntity user = getCurUser();
			FormdiyEntity entity = BeanUtil.copyValue(FormdiyEntity.class,getRequest());
			Long id = entity.getId();
			String recode  =  entity.getRecode();
			String name = entity.getName();
			Integer isRestype = entity.getIsRestype();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("recode", recode);
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			boolean flag = false;
			boolean rflag = true;
			List<FormdiyEntity> FormdiyEntity = formdiyService.getEntityList(params);
			if(!FormdiyEntity.isEmpty() && FormdiyEntity.size()>0 && isRestype.intValue()==0){
				for(FormdiyEntity fEntity : FormdiyEntity){
					String existRecode = fEntity.getRecode();
					if(existRecode.equals(recode)){
						Long fid = fEntity.getId();
						if(fid != id){
							flag = true;
						}
						break;
					}
				}
			}else{
				FormdiyEntity formdiyentity = formdiyService.getEntity(params);
				Integer itype = null;
				if(formdiyentity != null){
					 itype = formdiyentity.getIsRestype();
						if(itype!=isRestype){
							GvlistEntity gvlistEntity = gvlistService.getEntity(Long.parseLong(recode));
							if(null==gvlistEntity){
								rflag = true;
							}else{
									Long fid = formdiyentity.getId();
									Long restypeId = gvlistEntity.getRestypeId();
									RestypeEntity RestypeEntity = restypeService.getEntity(restypeId);
									String Recode= RestypeEntity.getRecode();
									if("100016".equals(Recode) || Recode.equals("100017")){
										if(fid!=null && fid!=id){
											flag = true;
										}else{
											rflag = false;
										}
									}
							}
						}
				}
			}
			if(flag){
				HashMap<String, Object>appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "formdiy.recode.have",recode));
				result = ResultMsg.getFailureMsg(appendParams);
			}else if(!rflag){
				HashMap<String, Object>appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "Change.recode.have",name));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				formdiyService.saveOrUpdateEntity(entity);
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
	 * 新增  表单DIY表 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = formdiyService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("F", num);
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
	 * 将所有可用资源保存为文件
	 * @return		
	 * @throws Exception
	 */
	public String savefile()throws Exception {
		try {
			DataTable dtList = formdiyService.getResultList();
			makeDatas(dtList);
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
	
	private void makeDatas(DataTable dtList) throws ServiceException{
		String filePath = FileUtil.getFilePath(getRequest(), StringHandler.GetResValue("formdiy_data_filepath"));
		String data = "var FormdiyDatas = {};";
		JSONObject dataObj = null;
		if(null != dtList && dtList.getRowCount() > 0){
			dataObj = getJsonDataObj(dtList);
			String formdiyIds = dataObj.getString("formdiyIds");
			dataObj.remove("formdiyIds");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("formdiyId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + formdiyIds);
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			map.put(SqlUtil.ORDERBY_KEY, "asc:row,col");
		 	DataTable dtFieldCustom = fieldCustomService.getResultList(map);
		 	map.put(SqlUtil.ORDERBY_KEY, "asc:id");
		 	DataTable dtFieldProp = fieldPropService.getResultList(map);
		 	putFieldDatas(dataObj, dtFieldCustom, dtFieldProp);
			data = "var FormdiyDatas = "+ dataObj.toString()+";";
		}
		FileUtil.writeStrToFile(filePath, data);
	}

	private void putFieldDatas(JSONObject dataObj, DataTable dtFieldCustom, DataTable dtFieldProp){
		if(null != dtFieldCustom && dtFieldCustom.getRowCount() > 0){
			for(int i=0,count=dtFieldCustom.getRowCount(); i<count; i++){
				Long formdiyId = dtFieldCustom.getLong(i, "formdiyId");
				String key = eqKey(dataObj,"id",formdiyId);
				if(null == key) continue;
				JSONArray arr = dataObj.getJSONObject(key).getJSONArray("customFields");
				JSONObject jsonObj = convertDtToJSON(dtFieldCustom, i, new String[]{"id","isenabled","formdiyId","name","dispName","controlType","isRequired","maxlength","row","col","width","height","dval","datasource","ename","jname","twname","fname","koname"});
				arr.add(jsonObj);
			}
		}
		if(null != dtFieldProp && dtFieldProp.getRowCount() > 0){
			for(int i=0,count=dtFieldProp.getRowCount(); i<count; i++){
				Long formdiyId = dtFieldProp.getLong(i, "formdiyId");
				String key = eqKey(dataObj,"id",formdiyId);
				if(null == key) continue;
				JSONArray arr = dataObj.getJSONObject(key).getJSONArray("propsFields");
				JSONObject jsonObj = convertDtToJSON(dtFieldProp, i, new String[]{"id","isenabled","formdiyId","name","standName","dispName","isRequired","ename","jname","twname","fname","koname"});
				arr.add(jsonObj);
			}
		}
	}
	
	private JSONObject convertDtToJSON(DataTable dt, int index, String[] cmns){
		JSONObject obj = new JSONObject();
		for(String cmn : cmns){
			String value = dt.getString(index, cmn);
			obj.put(cmn, value);
		}
		return obj;
	}
	
	private String eqKey(JSONObject dataObj,String eqKey, Long eqVal){
		Set<String> keys = dataObj.keySet();
		for(String key : keys){
			JSONObject data = dataObj.getJSONObject(key);
			if(data.getLong(eqKey).equals(eqVal)) return key;
		}
		return null;
	} 
	
	private JSONObject getJsonDataObj(DataTable dtList) {
		JSONObject dataObj = new JSONObject();
		StringBuffer sbFormdiyIds = new StringBuffer();
		for(int i=0,count=dtList.getRowCount(); i<count; i++){
			Long id = dtList.getLong(i, "id");
			Long sysid = dtList.getLong(i, "sysid");
			String recode = dtList.getString(i, "recode");
			String frecode = dtList.getString(i, "frecode");
			Integer isRestype = dtList.getInteger(i, "isRestype");
			Integer maxCmncount = dtList.getInteger(i, "maxCmncount");
			sbFormdiyIds.append(id+",");
			String key = sysid+"_"+recode;
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("frecode", frecode);
			obj.put("isRestype", isRestype);
			obj.put("maxCmncount", maxCmncount);
			obj.put("customFields", new JSONArray());
			obj.put("propsFields", new JSONArray());
			dataObj.put(key, obj);
		}
		dataObj.put("formdiyIds", StringHandler.RemoveStr(sbFormdiyIds));
		return dataObj;
	}
	
	
	/**
	 * 删除 表单
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  表单
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  表单 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  表单 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			String subid = ids.substring(1, ids.length());
			Integer isenabled = getIVal("isenabled");
			formdiyService.enabledEntitys(subid, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 表单DIY表 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FormdiyEntity entity = formdiyService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(entity==null){
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
	 * 获取指定的 表单DIY表 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FormdiyEntity entity = formdiyService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(entity==null){
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
	
	
}
