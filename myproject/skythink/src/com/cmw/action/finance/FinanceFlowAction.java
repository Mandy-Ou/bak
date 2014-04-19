package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.workflow.WorkFlowService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.AuditAmountEntity;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.FcFlowService;
import com.cmw.service.impl.workflow.BussFlowModel;


/**
 * 小贷业务流程  ACTION类
 * @author 程明卫
 * @date 2012-12-08T00:00:00
 */
@Description(remark="小贷业务流程 ACTION",createDate="2012-12-08T00:00:00",author="程明卫",defaultVals="fcAccount_")
@SuppressWarnings("serial")
public class FinanceFlowAction extends BaseAction {
	@Resource(name="fcFlowService")
	private FcFlowService fcFlowService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 公司账户 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		return null;
	}
	
	/**
	 * 获取当前用户待办任务
	 * @return
	 * @throws Exception
	 */
	public String getTask()throws Exception {
		try {
//			Long applyId = getLVal("applyId");//申请单ID
			String procId = getVal("procId");//流程实例ID
			String pdid = getVal("pdid");//流程实例ID
			UserEntity user = this.getCurUser();
			BussFlowModel model =  (!StringHandler.isValidStr(procId)) ? fcFlowService.getTaskByPdid(user, pdid) : fcFlowService.getTask(user, procId);
			if(null == model){
				result = "{}";
			}else{
				result = FastJsonUtil.convertJsonToStr(model, new Callback(){
					public void execute(JSONObject jsonObj) {
						
					}
				});
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
	 * 提交流程
	 * @return
	 * @throws Exception
	 */
	public String submit()throws Exception {
		try {
			Map<String,Object> submitData = new HashMap<String, Object>();
			SHashMap<String, Object> map = getQParams("sysId,pdid,applyId,nodeId,taskId,taskName,result,nodeType,forkJoinActivityId,nextTrans,approval,appAmount,yearLoan,monthLoan,dayLoan,rateType,rate,reminds");
			String taskName = map.getvalAsStr("taskName");
			if(StringHandler.isValidStr(taskName) && 
				(taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1 || taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1)){/*会签任务提交*/
				String result = map.getvalAsStr("result");
				submitData.put("resultTag", result);
				result = getResult(Integer.parseInt(result));
				map.put("result", result);
			}
			
			AuditRecordsEntity recordEntity = getAuditRecordsEntity(map);
			AuditAmountEntity auditAmountEntity = getAuditAmountEntity(map);
			submitData.put(SysConstant.USER_KEY, getCurUser());
			submitData.put("pdid", map.get("pdid"));
			submitData.put("applyId", map.get("applyId"));
			submitData.put("taskId", map.get("taskId"));
			submitData.put("nodeType", map.get("nodeType"));
			submitData.put("forkJoinActivityId", map.get("forkJoinActivityId"));
			String nextTransStr = map.getvalAsStr("nextTrans");
			JSONArray nextTrans = FastJsonUtil.convertStrToJSONArr(nextTransStr);
			submitData.put("nextTrans", nextTrans);
			if(null != recordEntity){
				submitData.put("recordEntity", recordEntity);
			}
			
			if(null != auditAmountEntity){
				submitData.put("auditAmountEntity", auditAmountEntity);
			}
			String procId = fcFlowService.submitFlow(submitData);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("procId", procId);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS, appendParams);
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
	 * 当会签时，获取会签结果
	 * @param resultTag
	 * @return
	 */
	private String getResult(Integer resultTag){
		String result = "";
		switch (resultTag.intValue()) {
		case BussStateConstant.COUNTERSIGNCFG_PDTYPE_1:
			result = "通过";
			break;
		case BussStateConstant.COUNTERSIGNCFG_PDTYPE_2:
			result = "拒绝";
			break;
		case BussStateConstant.COUNTERSIGNCFG_PDTYPE_3:
			result = "弃权";
			break;
		default:
			break;
		}
		return result;
	}
	
	private AuditRecordsEntity getAuditRecordsEntity(SHashMap<String, Object> map){
		AuditRecordsEntity auditRecordsEntity = new AuditRecordsEntity();
		Long bussNodeId = map.getvalAsLng("nodeId");
		String taskId = map.getvalAsStr("taskId"); 
		String result = map.getvalAsStr("result");
		String approval = map.getvalAsStr("approval");
		String reminds = map.getvalAsStr("reminds");
		auditRecordsEntity.setBussNodeId(bussNodeId);
		auditRecordsEntity.setTiid(taskId);
		auditRecordsEntity.setResult(result);
		auditRecordsEntity.setApproval(approval);
		auditRecordsEntity.setNotifys(reminds);
		BeanUtil.setCreateInfo(getCurUser(), auditRecordsEntity);
		return auditRecordsEntity;
	}

	private AuditAmountEntity getAuditAmountEntity(SHashMap<String, Object> map){
		AuditAmountEntity auditAmountEntity = new AuditAmountEntity();
		Double appAmount = map.getvalAsDob("appAmount");
		Double rate = map.getvalAsDob("rate");
		Integer yearLoan = map.getvalAsInt("yearLoan");
		Integer monthLoan = map.getvalAsInt("monthLoan");
		Integer dayLoan = map.getvalAsInt("dayLoan");
		Integer rateType = map.getvalAsInt("rateType");
		if((null == appAmount || appAmount.doubleValue() <=0) &&
			(null == rate || rate.doubleValue() <= 0) && 
			((null == yearLoan || yearLoan.intValue() <= 0) && 
			(null == monthLoan || monthLoan.intValue() <= 0) && 
			(null == dayLoan || dayLoan.intValue() <= 0))) return null;
		auditAmountEntity.setAppAmount(new BigDecimal(appAmount));
		auditAmountEntity.setRate(rate);
		auditAmountEntity.setYearLoan(yearLoan);
		auditAmountEntity.setMonthLoan(monthLoan);
		auditAmountEntity.setDayLoan(dayLoan);
		auditAmountEntity.setRateType(rateType);
		auditAmountEntity.setRate(rate);
		BeanUtil.setCreateInfo(getCurUser(), auditAmountEntity);
		return auditAmountEntity;
	}
}
