package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.GuaContractEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.GuaContractService;
import com.cmw.service.inter.finance.LoanContractService;


/**
 * 保证合同  ACTION类
 * @author pdt
 * @date 2013-01-13T00:00:00
 */
@Description(remark="保证合同ACTION",createDate="2013-01-13T00:00:00",author="pdt",defaultVals="fcGuaContract_")
@SuppressWarnings("serial")
public class GuaContractAction extends BaseAction {
	@Resource(name="guaContractService")
	private GuaContractService guaContractService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取 保证合同 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("code", getVal("code"));
			map.put("assMan", getVal("assMan"));
			map.put("appAmount", getVal("appAmount"));
			DataTable dt = guaContractService.getResultList(map,getStart(),getLimit());
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
	 * 获取 保证合同 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long formId = getLVal("formId");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("formId", formId);
			params.put("isenabled", getVal("isenabled"));
			List<GuaContractEntity> entitylist =  guaContractService.getEntityList(params);
			GuaContractEntity entity=null;
			Long max = 0l;//初始值：0
			for(GuaContractEntity p : entitylist){
				Long id = p.getId();
				if(id>max){
					max = id;	
					entity = p;
				}
			}
			Date startDate= entity.getStartDate();
			Date edate = entity.getEndDate();
			Date sdate = entity.getSdate();
			final String s = DateUtil.dateFormatToStr(sdate);
			final String sd = DateUtil.dateFormatToStr(startDate);
			final String ed = DateUtil.dateFormatToStr(edate);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("startDate", sd);
					jsonObj.put("endDate", ed);
					jsonObj.put("sdate", s);
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
	 * 保存 保证合同 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			SHashMap<String, Object> args= new SHashMap<String,Object>();
			GuaContractEntity entity = BeanUtil.copyValue(GuaContractEntity.class,getRequest());
			if(entity != null){
				String codeParams =  entity.getCode();
				if(StringHandler.isValidStr(codeParams)){
					args.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
					args.put("code", codeParams);
					GuaContractEntity guaContractEntity = guaContractService.getEntity(args);
					if(guaContractEntity != null){
						Long guaId = guaContractEntity.getId();
						if(entity.getId() != guaId){
							outJsonString("-1");
							return null;
						}
					}
				}else{
					Long num = guaContractService.getMaxID();
					if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
					String code = CodeRule.getCode("G", num);
					entity.setCode(code);
				}
			}
			
			guaContractService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, entity,ResultMsg.SAVE_SUCCESS);
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
	 * 获取担保详情
	 */
	public String detail() throws Exception{
		try{
			Long formId = getLVal("formId");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("formId", formId);
			params.put("isenabled", 1);
			List<GuaContractEntity> entitylist =  guaContractService.getEntityList(params);
			GuaContractEntity entity=null;
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			if(!entitylist.isEmpty()){
				Long max = 0l;//初始值：0
				for(GuaContractEntity p : entitylist){
					Long id = p.getId();
					if(id>max){
						max = id;	
						entity = p;
					}
				}
				String guarantorIds  = entity.getGuarantorIds();
				String names=null;
				if(guarantorIds!=null){
					String [] guanames = guarantorIds.split(",");
					LinkedList<String> gualist = new LinkedList<String>();
					for(String x : guanames){
						CustomerInfoEntity custmerentity = customerInfoService.getEntity(Long.parseLong(x));
						gualist.addLast(custmerentity.getName());
					}
					if(!gualist.isEmpty()){
						names = gualist.toString();
						names = names.substring(1, names.length()-1);
					}
				}
				Date startDate= entity.getStartDate();
				Date edate = entity.getEndDate();
				Date sdate = entity.getSdate();
				final String s = DateUtil.dateFormatToStr(sdate);
				final String sd = DateUtil.dateFormatToStr(startDate);
				final String ed = DateUtil.dateFormatToStr(edate);
				appendParams.put("startDate", sd);
				appendParams.put("endDate", ed);
				appendParams.put("sdate", s);
				appendParams.put("guarantorIds",names);
				result = ResultMsg.getSuccessMsg(entity,appendParams);
			}else{
				outJsonString("-1");
				return null;
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
	 * 新增  保证合同 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long formId = getLVal("formId");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			LoanContractEntity loanContractEntity = loanContractService.getEntity(params);
			if(loanContractEntity==null){
				outJsonString("-1");
				return null;
			}
			Long loanContractId = loanContractEntity.getId();
			String lcode =  loanContractEntity.getCode();
			Date startDate = loanContractEntity.getPayDate();
			Date endDate = loanContractEntity.getEndDate();
			String sdate = DateUtil.dateFormatToStr(startDate);
			String edate = DateUtil.dateFormatToStr(endDate);
			ApplyEntity applyEntity = applyService.getEntity(formId);
			Integer custType =applyEntity.getCustType();
			Long customerId = applyEntity.getCustomerId();
			BigDecimal appAmount = applyEntity.getAppAmount();
			Double rate =  applyEntity.getRate();
			String assMan = null;//担保合同人
			if(custType==1){
				EcustomerEntity ecustomerEntity =  ecustomerService.getEntity(customerId);
				assMan = ecustomerEntity.getName();
			}else{
				CustomerInfoEntity customerInfoEntity  = customerInfoService.getEntity(customerId);
				assMan = customerInfoEntity.getName();
			}
			
			Long num = guaContractService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("G", num);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("loanConId", loanContractId);
			map.put("borrCode", lcode);
			map.put("startDate", sdate);
			map.put("endDate", edate);
			map.put("appAmount", appAmount);
			map.put("rate", rate);
			map.put("assMan", assMan);
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
	 * 删除  保证合同 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  保证合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  保证合同 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  保证合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("id");
			Integer isenabled = SysConstant.OPTION_DEL;
			guaContractService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 保证合同 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			GuaContractEntity entity = guaContractService.navigationPrev(params);
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
	 * 获取指定的 保证合同 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			GuaContractEntity entity = guaContractService.navigationNext(params);
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
