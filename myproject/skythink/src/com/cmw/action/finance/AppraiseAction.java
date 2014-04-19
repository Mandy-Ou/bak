package com.cmw.action.finance;


import javax.annotation.Resource;
import com.cmw.core.base.annotation.Description;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.AppraiseEntity;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.AppraiseService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 审贷评审  ACTION类
 * @author 李听
 * @date 2014-01-04T00:00:00
 */
@Description(remark="审贷评审ACTION",createDate="2014-01-04T00:00:00",author="李听",defaultVals="fcAppraise_")
@SuppressWarnings("serial")
public class AppraiseAction extends BaseAction {
	@Resource(name="appraiseService")
	private AppraiseService appraiseService;
	@Resource(name="applyService")
	private ApplyService applyService;	
	@Resource(name="varietyService")
	private VarietyService varietyService;	
	@Resource(name="userService")
	private UserService userService;	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 审贷评审 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = appraiseService.getResultList(map,getStart(),getLimit());
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
	 * 获取业务品种表的name值
	 * @return
	 * @throws Exception
	 */
	public String listVariety()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = varietyService.getResultList(map,getStart(),getLimit());
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
	 * 获取主持人和记录人
	 * @return
	 * @throws Exception
	 */
	public String listVarUser()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = userService.getResultList(map,getStart(),getLimit());
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
	 * 条件查询获取值
	 * @return
	 * @throws Exception
	 */
	public String search()throws Exception {
		try {
			String serchIds=getVal("serchId");
		String serchId=new String(serchIds.getBytes("ISO-8859-1"),"UTF-8");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("serchId", serchId);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = userService.getSerch(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 获取 审贷评审 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AppraiseEntity entity = appraiseService.getEntity(Long.parseLong(id));
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
	 * 保存 审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ApplyEntity appEntiy=applyService.getEntity(1L);
			Long breed=getLVal("breed");
			Double appAmount=getDVal("appAmount");
			Integer yearLoan=getIVal("yearLoan");
			Integer monthLoan=getIVal("monthLoan");
			Integer dayLoan=getIVal("dayLoan");
			String situation=getVal("situation");
			String auditDate=getVal("auditDate");
			String address=getVal("address");
			Long hostMan=getLVal("hostMan");
			Long recordMan=getLVal("recordMan");
			String opinion=getVal("opinion");
			Integer resultTag=getIVal("resultTag");
			Long applyId=getLVal("applyId");
			Long customerId=getLVal("customerId");
			Integer custType=getIVal("custType");
			AppraiseEntity entity = BeanUtil.copyValue(AppraiseEntity.class,getRequest());
			entity.setApplyId(applyId);
			entity.setRate(appEntiy.getRate());
			entity.setRateType(appEntiy.getRateType());
			entity.setCustType(custType);
			entity.setCustomerId(customerId);
			if(StringHandler.isValidObj(breed)&&StringHandler.isValidObj(appAmount)&&StringHandler.isValidObj(yearLoan)
			&&StringHandler.isValidObj(monthLoan)&&StringHandler.isValidObj(dayLoan)&&StringHandler.isValidObj(recordMan)&&StringHandler.isValidObj(hostMan)	
				){
			entity.setBreed(breed);
			entity.setAppAmount(new BigDecimal(appAmount));
			entity.setYearLoan(yearLoan);
			entity.setMonthLoan(monthLoan);
			entity.setDayLoan(dayLoan);
			entity.setRateType(appEntiy.getRateType());
			entity.setSituation(situation);
			entity.setAuditDate(StringHandler.dateFormat("yyyy-MM-dd", auditDate));
			entity.setAddress(address);
			entity.setHostMan(hostMan);
			entity.setRecordMan(recordMan);
			entity.setOpinion(opinion);
			entity.setResultTag(resultTag);
		}
			appraiseService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this,entity, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = appraiseService.getMaxID();
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
	 * 删除  审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  审贷评审 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			appraiseService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 审贷评审 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AppraiseEntity entity = appraiseService.navigationPrev(params);
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
	 * 获取指定的 审贷评审 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AppraiseEntity entity = appraiseService.navigationNext(params);
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
