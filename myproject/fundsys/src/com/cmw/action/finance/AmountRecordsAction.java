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
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.PrepaymentService;


/**
 * 实收金额  ACTION类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收金额ACTION",createDate="2013-01-15T00:00:00",author="程明卫",defaultVals="fcAmountRecords_")
@SuppressWarnings("serial")
public class AmountRecordsAction extends BaseAction {
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="planService")
	private PlanService planService;
	
	@Resource(name="prepaymentService")
	private PrepaymentService prepaymentService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 实收金额 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("contractId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = amountRecordsService.getResultList(map,getStart(),getLimit());
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
	 * 客户贷款台账明细资料
	 * @return
	 * @throws Exception
	 */
	public String getLedger()throws Exception {
		try {
			String contractId = getVal("contractId");
			if(!StringHandler.isValidStr(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("contractId", Long.parseLong(contractId));
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<AmountRecordsEntity> amountRecordsList = amountRecordsService.getEntityList(params);
			LoanContractEntity loanContractEntity = loanContractService.getEntity(Long.parseLong(contractId));
			List<PlanEntity> planList = planService.getEntityList(params);
			List<PrepaymentEntity>  prepaymentList = prepaymentService.getEntityList(params);
			BigDecimal rat = new BigDecimal(0.00);
			if(!amountRecordsList.isEmpty() && amountRecordsList.size()>0){
				for(AmountRecordsEntity amountRecordsEntity :  amountRecordsList){
					Double ratDouble = BigDecimalHandler.add(rat, amountRecordsEntity.getRat());
					rat = BigDecimal.valueOf(ratDouble);
				}
			}
			double pat = 0.00;//罚息收入
			BigDecimal interest = new BigDecimal(0.00);//应收手续费
			if(!planList.isEmpty() && planList.size()>0){
				for(PlanEntity planEntity :  planList){
					Double interestDouble = BigDecimalHandler.add(interest,planEntity.getInterest());
					interest = BigDecimal.valueOf(interestDouble);
					BigDecimal ypenAmount = planEntity.getYpenAmount();//实收罚息
					BigDecimal ydelAmount = planEntity.getYdelAmount();//实收滞纳金
					
					Double patDouble = BigDecimalHandler.add(ypenAmount,ydelAmount);
					pat = BigDecimalHandler.add(pat, patDouble);
				}
			}
			BigDecimal yfreeamount = new BigDecimal(0.00);
			Date predDate = null;
			if(!prepaymentList.isEmpty() && prepaymentList.size()>0){
				for(PrepaymentEntity x : prepaymentList){
					predDate  = x.getPredDate();
					Double doublefree = BigDecimalHandler.add(yfreeamount, x.getYfreeamount()) ;
					yfreeamount = BigDecimal.valueOf(doublefree);
				}
			}
			pat = BigDecimalHandler.round(pat, 2);
			Date payDate = loanContractEntity.getPayDate();
			Date endDate = loanContractEntity.getEndDate();
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			appendParams.put("payDate", payDate);
			appendParams.put("endDate", endDate);
			appendParams.put("interest", interest);
			appendParams.put("rat", rat);
			appendParams.put("pat", pat);
			appendParams.put("yfreeamount", yfreeamount);
			appendParams.put("predDate", predDate);
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
	 * 获取 实收金额 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AmountRecordsEntity entity = amountRecordsService.getEntity(Long.parseLong(id));
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
	 * 保存 实收金额 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			AmountRecordsEntity entity = BeanUtil.copyValue(AmountRecordsEntity.class,getRequest());
			amountRecordsService.saveOrUpdateEntity(entity);
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
	 * 新增  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = amountRecordsService.getMaxID();
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
	 * 删除  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  实收金额 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			amountRecordsService.enabledEntitys(ids, isenabled);
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
	 * 正常收取的流水情况
	 * 重写了getResult方法,
	 * @return
	 * @throws Exception
	 */
	
	public String listRepDedut()throws Exception {
		try {
			String contractId = getVal("contractId");
			if(!StringHandler.isValidStr(contractId)) new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("contractId", contractId);
			DataTable dt = amountRecordsService.getResultList(map,getStart(),getLimit());
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
	 * 逾期还款流水,展期
	 */
	public String listPay()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = amountRecordsService.getLoanRecordsList(map,getStart(),getLimit());
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
	 * 获取指定的 实收金额 上一个对象
	 * @return
	 * @throws Exception
	 */
	
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AmountRecordsEntity entity = amountRecordsService.navigationPrev(params);
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
	 * 获取指定的 实收金额 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AmountRecordsEntity entity = amountRecordsService.navigationNext(params);
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
