package com.cmw.action.sys;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.druid.mapping.Entity;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.CompanyEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.PostEntity;
import com.cmw.service.inter.sys.CompanyService;


/**
 * 公司  ACTION类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="公司ACTION",createDate="2011-09-24T00:00:00",author="chengmingwei",defaultVals="sysCompany_")
@SuppressWarnings("serial")
public class CompanyAction extends BaseAction {
	@Resource(name="companyService")
	private CompanyService companyService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 公司 列表
	 * @return	
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("name", getVal("name"));
			DataTable dt = companyService.getResultList(map,getStart(),getLimit());
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
	 * 获取 公司 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			Long subid=Long.parseLong(id.substring(1, id.length()));
			CompanyEntity entity=companyService.getEntity(subid);
			Long poid = entity.getPoid();
			Map<String,Object> appendParams = new HashMap<String, Object>();
			String parentName = null;
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(entity.getPotype().intValue()==CompanyEntity.COMPANY_POTYPE_1 ){
				CompanyEntity parentEntity=companyService.getEntity(poid);
				parentName = parentEntity.getName();
			}else{
				CompanyEntity parentEntity=companyService.getEntity(poid);
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
	 * 保存 公司
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			CompanyEntity entity = BeanUtil.copyValue(CompanyEntity.class,getRequest());
			Integer aff =entity.getAffiliation();
			if(null==aff || aff.intValue()==CompanyEntity.COMPANY_AFFILIATION_0){
				entity.setPotype(CompanyEntity.COMPANY_POTYPE_0);
			}else{
				entity.setPotype(CompanyEntity.COMPANY_POTYPE_1);
			}
			companyService.saveOrUpdateEntity(entity);
			Long id = entity.getId();
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			appendParams.put("id", "C"+id.toString());
			appendParams.put("aff", entity.getAffiliation());
			appendParams.put("name", entity.getName());
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
	 * 新增 公司
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = companyService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String poid = getVal("poid");
			String code = CodeRule.getCode("C", num);
			Date builddate = new Date();
			String bDate = DateUtil.dateFormatToStr(builddate);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", code);
			map.put("builddate", bDate);
			if(StringHandler.isValidStr(poid)&& poid.indexOf("root_")==-1){
				Long subpoid = Long.parseLong((poid.substring(1, poid.length())));
				map.put("poid", subpoid);
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
	 * 删除 公司
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled();
	}
	
	/**
	 * 启用 公司
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String ids = getVal("ids");
			String idss= ids.substring(1, ids.length());
			Integer isenabled = getIVal("isenabled");
			companyService.enabledEntitys(idss, isenabled);
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
	 * 禁用公司
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			companyService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_DISABLED);
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
			CompanyEntity entity = companyService.navigationPrev(params);
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
	 * 获取指定的 公司 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			params.put("id", getVal("id"));
			CompanyEntity entity = companyService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Long poid = entity.getPoid();
				String parentName = null;
				CompanyEntity parentEntity = companyService.getEntity(poid);
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
