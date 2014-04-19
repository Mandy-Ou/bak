package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.MortContractEntity;
import com.cmw.entity.finance.MortgageEntity;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.MortContractService;
import com.cmw.service.inter.finance.MortgageService;


/**
 * 抵押合同  ACTION类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="抵押合同ACTION",createDate="2013-01-11T00:00:00",author="pdh",defaultVals="fcMortContract_")
@SuppressWarnings("serial")
public class MortContractAction extends BaseAction {
	@Resource(name="mortContractService")
	private MortContractService mortContractService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name="mortgageService")
	private MortgageService mortgageService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 抵押合同 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("pleman", getVal("pleman"));
			map.put("conAmount", getVal("conAmount"));
			map.put("formId", getLVal("formId"));
			map.put("code", getVal("code"));
			map.put("borrCode", getVal("borrCode"));
			map.put("assMan", getVal("assMan"));
			map.put("appAmount", getVal("appAmount"));
			map.put("rate", getVal("rate"));
			DataTable dt = mortContractService.getResultList(map,getStart(),getLimit());
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
	 * 获取 抵押合同 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long id = getLVal("id");
			if(id ==-1){
				outJsonString(result);
				return null;
			}
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			MortContractEntity entity = mortContractService.getEntity(id);
			String name = "";
			if(entity != null){
				String pleIds = entity.getPleIds();
				StringBuffer sbName = new StringBuffer();
				
				if(StringHandler.isValidStr(pleIds)){
					SHashMap<String, Object>  params = new SHashMap<String, Object>();
					params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+pleIds);
					params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
					List<MortgageEntity> mortgageList = mortgageService.getEntityList(params);
					if(!mortgageList.isEmpty() && mortgageList.size()>0){
						for(MortgageEntity mortgage: mortgageList){
							sbName.append(mortgage.getName()+",");
						}
					}
					name = StringHandler.RemoveStr(sbName);
				}
			}
			final String mortName = name;
			Date startdate = entity.getStartDate();
			Date sdate = entity.getSdate();
			Date edate = entity.getEndDate();
			final String stdate= DateUtil.dateFormatToStr(startdate);
			final String sd = DateUtil.dateFormatToStr(sdate);
			final String ed = DateUtil.dateFormatToStr(edate);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("startDate", stdate);
					jsonObj.put("endDate", ed);
					jsonObj.put("sdate", sd);
					jsonObj.put("mortName", mortName);
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
	 * 保存 抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			SHashMap<String, Object> args= new SHashMap<String,Object>();
			MortContractEntity entity = BeanUtil.copyValue(MortContractEntity.class,getRequest());
			if(entity != null){
				String codeParams =  entity.getCode();
				if(StringHandler.isValidStr(codeParams)){
					args.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
					args.put("code", codeParams);
					MortContractEntity mortContractEntity = mortContractService.getEntity(args);
					if(mortContractEntity != null){
						Long mortId = mortContractEntity.getId();
						if(entity.getId() != mortId){
							outJsonString("-1");
							return null;
						}
					}
				}else{
					Long num = mortContractService.getMaxID();
					if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
					String code = CodeRule.getCode("M", num);
					entity.setCode(code);
				}
			}
			Long formId=entity.getFormId();
			if(!StringHandler.isValidStr(formId.toString())) throw new ServiceException(ServiceException.ID_IS_NULL);
			ApplyEntity applyentity =applyService.getEntity(formId);//applyEntity
			applyentity.setState(2);
			mortContractService.saveOrUpdateEntity(entity);
			applyService.saveOrUpdateEntity(applyentity);
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
	 * 新增  抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long formId = getLVal("formId");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			LoanContractEntity loanEntity= getloan(formId);//根据formid找到借款申请的实体
			if(loanEntity==null){
				outJsonString("-1");
				return null;
			}
			Long loanConId = loanEntity.getId();
			String borrCode = loanEntity.getCode();
			BigDecimal appAmount = loanEntity.getAppAmount();
			Double rate = loanEntity.getRate();
			Date startDate = loanEntity.getPayDate();
			Date endDate = loanEntity.getEndDate();
			String sdate = DateUtil.dateFormatToStr(startDate);
			String edate = DateUtil.dateFormatToStr(endDate);
			ApplyEntity applyentity =applyService.getEntity(formId);//applyEntity
			Integer custtype = applyentity.getCustType();
			Long customerId = applyentity.getCustomerId();
			String assMan = null;
			if(custtype==0){
				CustomerInfoEntity  custEntity = customerInfoService.getEntity(customerId);
				assMan = custEntity.getName();
			}else{
				EcustomerEntity ecusteEntity = ecustomerService.getEntity(customerId);
				assMan = ecusteEntity.getName();
			}
			HashMap<String, Object> Params = new HashMap<String, Object>();
			Params.put("loanConId", loanConId);
			Params.put("borrCode", borrCode);
			Params.put("appAmount", appAmount);
			Params.put("rate", rate);
			Params.put("startDate", sdate);
			Params.put("endDate", edate);
			Params.put("assMan", assMan);
			Long num = mortContractService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("M", num);
			Params.put("code", code);
			result =ResultMsg.getSuccessMsg(Params);
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
	 * 获取 借款合同 
	 * @return
	 * @throws Exception
	 */
	private LoanContractEntity getloan(Long Pk) throws Exception {
		LoanContractEntity loanEntity = null;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("formId", Pk);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		loanEntity = loanContractService.getEntity(params);
		return loanEntity;
	}
	/**
	 * 删除  抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  抵押合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			mortContractService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 抵押合同 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MortContractEntity entity = mortContractService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				Date startdate = entity.getStartDate();
				Date sdate = entity.getSdate();
				Date edate = entity.getEndDate();
				String stdate= DateUtil.dateFormatToStr(startdate);
				String sd = DateUtil.dateFormatToStr(sdate);
				String ed = DateUtil.dateFormatToStr(edate);
				appendParams.put("startDate", stdate);
				appendParams.put("endDate", sd);
				appendParams.put("sdate", ed);
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
	 * 获取指定的 抵押合同 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			MortContractEntity entity = mortContractService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Date startdate = entity.getStartDate();
				Date sdate = entity.getSdate();
				Date edate = entity.getEndDate();
				String stdate= DateUtil.dateFormatToStr(startdate);
				String sd = DateUtil.dateFormatToStr(sdate);
				String ed = DateUtil.dateFormatToStr(edate);
				appendParams.put("startDate", stdate);
				appendParams.put("endDate", sd);
				appendParams.put("sdate", ed);
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
