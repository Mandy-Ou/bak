package com.cmw.action.sys;


import javax.annotation.Resource;
import com.cmw.core.base.annotation.Description;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.sys.CompanyEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.service.inter.sys.CompanyService;
import com.cmw.service.inter.sys.DepartmentService;


/**
 * 部门  ACTION类
 * @author 彭登浩
 * @date 2012-11-09T00:00:00
 */
@Description(remark="部门ACTION",createDate="2012-11-09T00:00:00",author="彭登浩",defaultVals="sysDepartment_")
@SuppressWarnings("serial")
public class DepartmentAction extends BaseAction {
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 部门 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = departmentService.getResultList(map,getStart(),getLimit());
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
	 * 获取 部门 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			Long subid=Long.parseLong(id.substring(1, id.length()));
			DepartmentEntity entity=departmentService.getEntity(subid);
			Long poid = entity.getPoid();
			Map<String,Object> appendParams = new HashMap<String, Object>();
			String parentName = null;
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(entity.getPotype().intValue()==DepartmentEntity.DEPARTMENT_POTYPE_2 ){
				DepartmentEntity parentEntity=departmentService.getEntity(poid);
				parentName =  parentEntity.getName();
			}else{
				DepartmentEntity parentEntity=departmentService.getEntity(poid);
				if(parentEntity!=null)
				parentName = parentEntity.getName();
				}
			Date builddate = entity.getBuilddate();
			String bDate = DateUtil.dateFormatToStr(builddate);
			appendParams.put("parentName", parentName);
			appendParams.put("builddate", bDate);
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
	 * 保存 部门 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			DepartmentEntity entity = BeanUtil.copyValue(DepartmentEntity.class,getRequest());
			departmentService.saveOrUpdateEntity(entity);
			Long id = entity.getId();
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			appendParams.put("name", entity.getName());
			appendParams.put("id", "D"+id.toString());
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
	 * 新增  部门 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = departmentService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String poid = getVal("poid");
			String code = CodeRule.getCode("D", num);
			Date builddate = new Date();
			String bDate = DateUtil.dateFormatToStr(builddate);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", code);
			map.put("builddate", bDate);
			if(StringHandler.isValidStr(poid)){
				map.put("poid", poid);
			}
			result = JsonUtil.getJsonString(map);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 删除  部门 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled();
	}
	
	/**
	 * 启用  部门 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String ids = getVal("ids");
			String idss= ids.substring(1, ids.length());
			Integer isenabled = getIVal("isenabled");
			departmentService.enabledEntitys(idss, isenabled);
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
	 * 禁用  部门 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			departmentService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_DISABLED);
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
	 * 获取指定的 部门 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			DepartmentEntity entity = departmentService.navigationPrev(params);
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
	 * 获取指定的 部门 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			params.put("id", getVal("id"));
			DepartmentEntity entity = departmentService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Long poid= entity.getPoid();
				String parentName = null;
				DepartmentEntity parentEntity = departmentService.getEntity(poid);
				if(null != parentEntity) parentName = parentEntity.getName();
				appendParams.put("parentName", parentName);
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
