package com.cmw.action.sys;


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
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.PostEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.sys.PostService;


/**
 * 职位  ACTION类
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
@Description(remark="职位ACTION",createDate="2012-11-10T00:00:00",author="彭登浩",defaultVals="sysPost_")
@SuppressWarnings("serial")
public class PostAction extends BaseAction {
	@Resource(name="postService")
	private PostService postService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 职位 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("name");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = postService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(new JsonDataCallback(){
				public void makeJson(JSONObject jsonObj) {
					Long creator = jsonObj.getLong("creator");
					try {
						UserEntity creatorUser = UserCache.getUser(creator);
						String empName = creatorUser.getEmpName();
						if(!StringHandler.isValidStr(empName)) empName = creatorUser.getUserName();
						jsonObj.put("creator", empName);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
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
	 * 获取 职位 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PostEntity entity=postService.getEntity(id);
			result = FastJsonUtil.convertJsonToStr(entity);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 职位 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			PostEntity entity = BeanUtil.copyValue(PostEntity.class,getRequest());
			postService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  职位 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = postService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("P", num);
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
	 * 删除  职位 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled();
	}
	
	/**
	 * 启用  职位 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			postService.enabledEntitys(ids, isenabled);
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
	 * 禁用  职位 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled();
	}
	
}
