package com.cmw.action.finance;


import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.MortgageEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.MortgageService;
import com.cmw.service.inter.sys.DepartmentService;


/**
 * 抵押物  ACTION类
 * @author pdh
 * @date 2013-01-06T00:00:00
 */
@Description(remark="抵押物ACTION",createDate="2013-01-06T00:00:00",author="pdh",defaultVals="fcMortgage_")
@SuppressWarnings("serial")
public class MortgageAction extends BaseAction {
	@Resource(name="mortgageService")
	private MortgageService mortgageService;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 抵押物 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put(SysConstant.USER_KEY, this.getCurUser());
			String ids = getVal("id");
			map.put("state", getVal("state"));
			DataTable dt  = new DataTable();
			if(StringHandler.isValidStr(ids)){
				map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
				 dt = mortgageService.getResultList(map,getStart(),getLimit());
			}else{
				map.put("formId", getLVal("formId"));
				map.put("gtype", getLVal("gtype"));
				map.put("id", getLVal("id"));
				map.put("quantity", getLVal("quantity"));
				map.put("remark", getLVal("remark"));
				dt = mortgageService.getResultList(map,getStart(),getLimit());
			}
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
	 * 根据formId 去表格中查找数据
	 */
	public String afterDelDt() throws Exception{
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("formId", getLVal("formId"));
			map.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			DataTable dt = mortgageService.getResultList(map,getStart(),getLimit());
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
	 * 获取 抵押物 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long id = getLVal("id");
			if(id ==-1){
				outJsonString(result);
				return null;
			}else{
				if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
				MortgageEntity entity = mortgageService.getEntity(id);
				Date mt = entity.getMorTime();
				String morTime = DateUtil.dateFormatToStr(null, mt);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("morTime", morTime);
				result = ResultMsg.getSuccessMsg(entity, map);
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
	 * 保存 抵押物 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			MortgageEntity entity = BeanUtil.copyValue(MortgageEntity.class,getRequest());
			if(StringHandler.isValidObj(entity.getId()) && entity.getState()!=0){
				MortgageEntity mort=mortgageService.getEntity(entity.getId());
//				String carrMan=getVal("carrMan");
//				String charMan=getVal("charMan");
//				if(StringHandler.isValidObj(carrMan)){
//					mort.setCarrMan(carrMan.split("##")[0]);
//				}
//				if(StringHandler.isValidObj(charMan)){
//					mort.setCharMan(charMan.split("##")[0]);
//				}
				if(entity.getState()==1){
					mort.setCode(entity.getCode());
					mort.setCarrTime(entity.getCarrTime());
					mort.setState(entity.getState());
					mort.setCarrDept(entity.getCarrDept());
					mort.setCarrMan(entity.getCarrMan());
				}else{
					mort.setCharCode(entity.getCharCode());
					mort.setState(entity.getState());
					mort.setCharMan(entity.getCharMan());
					mort.setCharDept(entity.getCharDept());
					mort.setCharTime(entity.getCharTime());
				}
				mortgageService.saveOrUpdateEntity(mort);
			}else{
				mortgageService.saveOrUpdateEntity(entity);
			}
			Map<String,Object> attachParams = new HashMap<String, Object>();
			attachParams.put("id", entity.getId());
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,attachParams);
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
	 * 新增  抵押物 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = mortgageService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("M", num);
			Map<String, Object> map=new HashMap<String, Object>();
			UserEntity user=this.getCurUser();
			String state=getVal("state");
			if("1".equals(state)){
				map.put("carrMan",user.getUserId()+"##"+user.getUserName());
				map.put("carrTime",StringHandler.dateFormatToStr("yyyy-MM-dd", new Date()));
			}else if("2".equals(state)){
				map.put("charCode", code);
				map.put("charMan",user.getUserId()+"##"+user.getUserName());
				map.put("charTime",StringHandler.dateFormatToStr("yyyy-MM-dd", new Date()));
			}else{
				map.put("code", code);
				map.put("morTime", StringHandler.dateFormatToStr("yyyy-MM-dd", new Date()));
			}
			map.put("indeptId",user.getIndeptId());
			DepartmentEntity dept=departmentService.getEntity(user.getIndeptId());
			if(dept!=null)
				map.put("indeptName", dept.getName());
			result = JsonUtil.getJsonString(map);
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
	 * 删除  抵押物 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  抵押物 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  抵押物 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  抵押物 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			mortgageService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 抵押物 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MortgageEntity entity = mortgageService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				Date  morTime = entity.getMorTime();
				String mt ="";
				if(StringHandler.isValidObj(morTime)){
					mt = DateUtil.dateFormatToStr(morTime);
				}
				
				appendParams.put("morTime", mt);
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
	 * 获取指定的 抵押物 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MortgageEntity entity = mortgageService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Date  morTime = entity.getMorTime();
				String mt ="";
				if(StringHandler.isValidObj(morTime)){
					mt = DateUtil.dateFormatToStr(morTime);
				}
				
				appendParams.put("morTime", mt);
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
