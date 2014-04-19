package com.cmw.service.impl.finance;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.AuditAmountEntity;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.workflow.BussFlowCallback;
import com.cmw.service.impl.workflow.BussFlowException;
import com.cmw.service.impl.workflow.BussFlowModel;
import com.cmw.service.impl.workflow.BussFlowService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.AuditAmountService;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.MsgNotifyService;


/**
 * 小贷业务流程  Service实现类	
 * @author 程明卫
 * @date 2013-01-03T00:00:00
 */
@SuppressWarnings("serial")
@Description(remark="小贷业务流程 业务实现类",createDate="2013-01-03T00:00:00",author="程明卫")
@Service("fcFlowService")
public class FcFlowService implements  Serializable {
	@Resource(name="bussflowService")
	private BussFlowService bussflowService;
	@Resource(name="applyService")
	private ApplyService applyService;
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	@Resource(name="auditAmountService")
	private AuditAmountService auditAmountService;
	@Resource(name="msgNotifyService")
	private MsgNotifyService msgNotifyService;
	
	public void buildCache() throws ServiceException{
		List<String> processInstanceIds = applyService.getProcessInstanceIds();
		bussflowService.buildCache(SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY, processInstanceIds);
	}
	
	/**
	 * 获取指定用户的待审批的流程实例ID(贷款申请审批功能)
	 * @param user	用户
	 * @return 返回待办流程实例ID列表
	 */
	public String getProcIdsByUser(UserEntity user){
		return bussflowService.getProcIdsByUser(SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY, user.getUserId().toString());
	}
	
	public BussFlowModel getTaskByPdid(UserEntity user,String pdid) throws ServiceException{
		if(null == pdid) throw new ServiceException("在获取用户["+user.getUserName()+"]的当前待办任务时，pdid 参数不能为空!");
		BussFlowModel model = new BussFlowModel();
		model.setPdid(pdid);
		String actorId = user.getUserId().toString();
		bussflowService.updateFlowModel(actorId, model);
		return model;
	}
	
	/**
	 * 获取指定流程实例的待办
	 * @param procId 流程实例ID
	 * @return 返回  BussFlowModel 对象
	 * @throws ServiceException
	 */
	public BussFlowModel getTask(String procId) throws ServiceException{
		return getTask(null, procId);
	}
	
	public BussFlowModel getTask(UserEntity user,String procId) throws ServiceException{
		BussFlowModel model = bussflowService.getFlowModel(user,procId,SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY); 
		return model;
	}
	
	/**
	 * 提交流程
	 * @param submitDatas
	 * @throws BussFlowException 
	 */
	@Transactional
	public String  submitFlow(Map<String,Object> submitDatas) throws ServiceException, BussFlowException{
		String procId = null;
		//pdid,taskId,nodeType,nextTrans,recordEntity,auditAmountEntity
		String pdid = (String)submitDatas.get("pdid");
		String taskId = (String)submitDatas.get("taskId");
		String nodeType = (String)submitDatas.get("nodeType");
		String forkJoinActivityId = (String)submitDatas.get("forkJoinActivityId");
		JSONArray nextTrans = (JSONArray)submitDatas.get("nextTrans");
		UserEntity currUser = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		if(null == nextTrans || nextTrans.size() == 0) throw new ServiceException(" nextTrans 不能为空!"); 
		SHashMap<String, Object> flowParams = new SHashMap<String, Object>();
		flowParams.put(SysConstant.USER_KEY, currUser);
		flowParams.put("pdid", pdid);
		flowParams.put("taskId", taskId);
		flowParams.put("nodeType", nodeType);
		flowParams.put("forkJoinActivityId", forkJoinActivityId);
		flowParams.put("nextTrans", nextTrans);
		flowParams.put(BussFlowService.BUSSTYPE_KEY, SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY);
		String actorIds = null;
		bussflowService.setCurruser(currUser);
		if(!StringHandler.isValidStr(taskId)){
			Map<String,Object> variables = new HashMap<String, Object>();
			variables.put(BussFlowService.BUSSTYPE_KEY, SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY);
			actorIds = getActors(nextTrans);
			BussFlowCallback callback = getCallback(submitDatas);
			procId = bussflowService.startFlow(flowParams, variables, callback);
		}else{
			if(!StringHandler.isValidStr(nodeType)) throw new ServiceException(" nodeType 不能为空!");
			if(nodeType.equals(BussFlowService.NODENAME_COUNTERSIGN)){/*会签节点*/
				JSONObject nextTran = nextTrans.getJSONObject(0);
				String activityId = nextTran.getString("activityId");
				actorIds = getActors(nextTrans);
				flowParams.put("activityId", activityId);
				flowParams.put("actorIds", actorIds);
				flowParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY);
				BussFlowCallback callback = getCallback(submitDatas);
				procId = bussflowService.submitFlowByCountersign(flowParams,callback);
			}else{
				BussFlowCallback callback = getCallback(submitDatas);
				flowParams.put("resultTag", submitDatas.get("resultTag"));
				procId = bussflowService.submitFlow(flowParams, callback);
			}
		}
		//--> 发送消息通知//
		msgNotifyService.sendNotify(submitDatas,nextTrans);
		return procId;
	}
	
	private String getActors(JSONArray nextTrans) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=nextTrans.size(); i<count; i++){
			JSONObject nextTran = nextTrans.getJSONObject(i);
			String actorId = nextTran.getString("actorId");
			if(!StringHandler.isValidStr(actorId)) throw new ServiceException(" actorId 不能为空!");
			sb.append(actorId).append(",");
		}
		return StringHandler.RemoveStr(sb);
	}
	/**
	 * 
	 * @param submitDatas
	 * @param actorIds 接收消息的下一步参与者ID列表
	 * @return
	 * @throws ServiceException
	 */
	private BussFlowCallback getCallback(Map<String,Object> submitDatas) throws ServiceException{
		String applyIdStr = (String)submitDatas.get("applyId");
		if(!StringHandler.isValidStr(applyIdStr)) throw new ServiceException(" applyId 不能为空!");
		final ApplyEntity applyEntity = applyService.getEntity(Long.parseLong(applyIdStr));
		final AuditRecordsEntity recordEntity = (AuditRecordsEntity)submitDatas.get("recordEntity");
		final AuditAmountEntity auditAmountEntity = (AuditAmountEntity)submitDatas.get("auditAmountEntity");
		BussFlowCallback callback = new BussFlowCallback() {
			public Object execute(BussFlowModel flowModel) throws BussFlowException {
				boolean isEnd = flowModel.isProcEnd();
				int state = !isEnd ? BussStateConstant.FIN_APPLY_STATE_1 : BussStateConstant.FIN_APPLY_STATE_15;
				Map<String,Object> vars = flowModel.getFlowVariables();
				if(null != vars && vars.size() > 0){
					String _djzt = (String)vars.get("djzt");
					if(StringHandler.isValidStr(_djzt)){
						Integer djzt = Integer.parseInt(_djzt);
						if(BussStateConstant.BUSS_PROCC_DJZT_3 == djzt.intValue()) state = BussStateConstant.FIN_APPLY_STATE_14;
					}
				}
				applyEntity.setState(state);
				if(!StringHandler.isValidStr(applyEntity.getProcId())){
					String procId = flowModel.getProcId();
					applyEntity.setProcId(procId);
				}
				try {
					/* step 1 : 更新申请单*/
					applyService.updateEntity(applyEntity);
					
					/* step 2 : 保存审批记录 */
					String procId = flowModel.getProcId();
					recordEntity.setProcId(procId);
					auditRecordsService.saveEntity(recordEntity);
					
					/* step 3 : 保存审批金额建议数据  */
					if(null != auditAmountEntity){
						Long recordId = recordEntity.getId();
						auditAmountEntity.setArecordId(recordId);
						auditAmountService.saveEntity(auditAmountEntity);
					}
				} catch (ServiceException e) {
					e.printStackTrace();
					throw new BussFlowException(e);
				}
				return null;
			}
		};
		return callback;
	}
	
}
