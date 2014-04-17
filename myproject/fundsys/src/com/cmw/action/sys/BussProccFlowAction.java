package com.cmw.action.sys;


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
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.workflow.BussFlowException;
import com.cmw.service.impl.workflow.BussFlowModel;
import com.cmw.service.impl.workflow.BussProccFlowService;


/**
 * 业务流程审批  ACTION类
 * @author 程明卫
 * @date 2013-05-29 20:45
 */
@Description(remark="业务流程审批  ACTION类",createDate="2013-05-29 20:45",author="程明卫",defaultVals="sysBussProccFlow_")
@SuppressWarnings("serial")
public class BussProccFlowAction extends BaseAction {
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	private String result = ResultMsg.GRID_NODATA;
	
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
			BussFlowModel model =  (!StringHandler.isValidStr(procId)) ? bussProccFlowService.getTaskByPdid(user, pdid) : bussProccFlowService.getTask(user, procId);
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
	 * 开始启动流程 
	 * @return
	 * @throws Exception
	 */
	public String start()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("applyId#L,sysId,bussProccCode");
			map.put(SysConstant.USER_KEY, getCurUser());
			Map<String, Object> appendParams = bussProccFlowService.startFlow(map);
			//appendParams.put("applyId", applyId);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ResultMsg.getFailureMsg(ex.getMessage());
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ResultMsg.getFailureMsg(ex.getMessage());
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

//	private Map<String, Object> startFlow(SHashMap<String, Object> map)
//			throws ServiceException, BussFlowException {
//		Long sysId = map.getvalAsLng("sysId");
//		Long applyId = map.getvalAsLng("applyId");
//		String bussProccCode = map.getvalAsStr("bussProccCode");
//		if(!StringHandler.isValidStr(bussProccCode)) throw new ServiceException("流程编号 \"bussProccCode\" 值不能为空!");
//		if(!StringHandler.isValidObj(applyId)) throw new ServiceException("申请单ID参数 \"applyId\" 值不能为空!");
//		
//		SHashMap<String, Object> params = new SHashMap<String, Object>();
//		BussProccEntity bussProccEntity = null;
//		Long bussProccId = null;
//		params.put("sysId", sysId);
//		params.put("isenabled", SysConstant.OPTION_ENABLED);
//		params.put("code", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + bussProccCode);
//		bussProccEntity = bussProccService.getEntity(params);
//		if(null != bussProccEntity){
//			bussProccId = bussProccEntity.getId();
//		}
//
//		if(null == bussProccEntity) throw new ServiceException("没有配置编号为：\""+bussProccCode+"\"的业务流程，无法提交!");
//		if(null == bussProccEntity.getPdid()) throw new ServiceException("编号为：\""+bussProccCode+"\"所对应的的业务流程没有配置流程文件，无法提交!");
//		params.clear();
//		params.put("formId", bussProccId);
//		params.put("inType", 2);/* 1: 业务流程*/
//		params.put("nodeType", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + "startEvent");
//		BussNodeEntity bnodeEntity = bussNodeService.getEntity(params);
//		String pdid = bussProccEntity.getPdid();
//		Long nodeId = bnodeEntity.getId();
//		params.clear();
//		params.put("bnodeId", nodeId);
//		BussTransEntity entity = bussTransService.getEntity(params);
//		String resultOp = (null != entity) ? entity.getName() : "开始流程";
//		map.clear();
//		String nextTrans = "[{actorId:\""+this.getCurUser().getUserId()+"\"}]";
//		map.put("bussProccCode", bussProccCode);
//		map.put("applyId", applyId.toString());
//		map.put("pdid", pdid);
//		map.put("nodeId", nodeId);
//		map.put("nextTrans", nextTrans);
//		map.put("result", resultOp);
//		//"sysId,pdid,applyId,nodeId,taskId,result,nodeType,forkJoinActivityId,nextTrans,"
//		Map<String, Object> appendParams = submitFlow(map);
//		return appendParams;
//	}
	
	
	/**
	 * 提交流程
	 * @return
	 * @throws Exception
	 */
	public String submit()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("sysId,pdid,applyId,nodeId,taskId,taskName,result,nodeType,forkJoinActivityId,nextTrans,approval,bussProccCode,reminds,notifyStartor,customerParams");
			map.put(SysConstant.USER_KEY, getCurUser());
			String taskName = map.getvalAsStr("taskName");
			if(StringHandler.isValidStr(taskName) && 
				(taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1 || taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1)){/*会签任务提交*/
				String result = map.getvalAsStr("result");
				map.put("resultTag", result);
				result = getResult(Integer.parseInt(result));
				map.put("result", result);
			}
			Map<String, Object> appendParams = submitFlow(map);
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
	private Map<String, Object> submitFlow(SHashMap<String, Object> map)
			throws ServiceException, BussFlowException {
		Long applyId = map.getvalAsLng("applyId");
		AuditRecordsEntity recordEntity = getAuditRecordsEntity(map);
		Map<String,Object> submitData = new HashMap<String, Object>();
		submitData.put(SysConstant.USER_KEY, getCurUser());
		submitData.put("pdid", map.get("pdid"));
		submitData.put("applyId", applyId);
		submitData.put("bussProccCode", map.get("bussProccCode"));
		submitData.put("taskId", map.get("taskId"));
		submitData.put("nodeType", map.get("nodeType"));
		submitData.put("forkJoinActivityId", map.get("forkJoinActivityId"));
		
		String customerParams = map.getvalAsStr("customerParams");
		if(StringHandler.isValidStr(customerParams)){
			Map<String,Object> customerParamsMap = FastJsonUtil.convertStrToMap(customerParams);
			if(null != customerParamsMap && customerParamsMap.size() > 0){
				submitData.putAll(customerParamsMap);
			}
		}
		String nextTransStr = map.getvalAsStr("nextTrans");
		JSONArray nextTrans = FastJsonUtil.convertStrToJSONArr(nextTransStr);
		submitData.put("nextTrans", nextTrans);
		if(null != recordEntity){
			submitData.put("recordEntity", recordEntity);
		}
		
		bussProccFlowService.setCurruser(getCurUser());
		String procId = bussProccFlowService.submitFlow(submitData);
		Map<String,Object> appendParams = new HashMap<String, Object>();
		appendParams.put("procId", procId);
		appendParams.put("applyId", applyId);
		return appendParams;
	}
	
	private AuditRecordsEntity getAuditRecordsEntity(SHashMap<String, Object> map){
		AuditRecordsEntity auditRecordsEntity = new AuditRecordsEntity();
		Long bussNodeId = map.getvalAsLng("nodeId");
		String taskId = map.getvalAsStr("taskId"); 
		String result = map.getvalAsStr("result");
		String approval = map.getvalAsStr("approval");
		String reminds = map.getvalAsStr("reminds");
		Integer notifyStartor = map.getvalAsInt("notifyStartor");
		auditRecordsEntity.setBussNodeId(bussNodeId);
		auditRecordsEntity.setTiid(taskId);
		auditRecordsEntity.setResult(result);
		auditRecordsEntity.setApproval(approval);
		auditRecordsEntity.setNotifys(reminds);
		auditRecordsEntity.setNotifyStartor(notifyStartor);
		BeanUtil.setCreateInfo(getCurUser(), auditRecordsEntity);
		return auditRecordsEntity;
	}

}
