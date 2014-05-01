package com.cmw.service.impl.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.workflow.WorkFlowService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.entity.sys.CountersignCfgEntity;
import com.cmw.entity.sys.CountersignEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussNodeCache;
import com.cmw.service.impl.cache.BussTransCache;
import com.cmw.service.impl.cache.CountersignCfgCache;
import com.cmw.service.impl.cache.NodeCfgCache;
import com.cmw.service.impl.cache.TransCfgCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.sys.CountersignCfgService;
import com.cmw.service.inter.sys.CountersignService;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 会签业务处理类
 * @author b
 *
 */
@SuppressWarnings("serial")
@Service("countersignServiceHandler")
public class CountersignServiceHandler implements Serializable {
	@Resource(name="countersignCfgService")
	private CountersignCfgService countersignCfgService;
	@Resource(name="countersignService")
	private CountersignService	countersignService;
	@Resource(name="workflowService")
	private WorkFlowService workflowService;
	
	/**
	 * 获取会签配置，优先从缓存中取
	 * @param nodeId 节点配置ID
	 * @return
	 * @throws ServiceException
	 */
	public CountersignCfgEntity getCountersignCfgEntity(final Long nodeId)throws ServiceException {
		List<CountersignCfgEntity> countersignCfgs = null;
			countersignCfgs = CountersignCfgCache.getList(new ElementCompare() {
				@Override
				public boolean equals(Object obj) {
					CountersignCfgEntity entity = (CountersignCfgEntity)obj;
					boolean flag = false;
					if(nodeId.equals(entity.getNodeId())){
						flag = true;
					}
					return flag;
				}
		  });
		
		if(null == countersignCfgs || countersignCfgs.size() == 0){
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("nodeId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + nodeId);
			countersignCfgs = countersignCfgService.getEntityList(map);
			if(null != countersignCfgs && countersignCfgs.size() > 0) CountersignCfgCache.add(countersignCfgs);
		}
		return (null != countersignCfgs && countersignCfgs.size() > 0) ? countersignCfgs.get(0) : null;
	}
	
	/**
	 * 获取会签配置
	 * @param nodeCfgId
	 * @return
	 * @throws ServiceException
	 */
	public JSONObject getCountersignCfg(Long nodeCfgId) throws ServiceException{
		CountersignCfgEntity entity = getCountersignCfgEntity(nodeCfgId);
		if(null == entity) return null;
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("ctype", entity.getCtype());
		jsonObj.put("pdtype", entity.getPdtype());
		jsonObj.put("voteType", entity.getVoteType());
		jsonObj.put("eqlogic", entity.getEqlogic());
		jsonObj.put("eqval", entity.getEqval());
		jsonObj.put("transId", entity.getTransId());
		jsonObj.put("transType", entity.getTransType());
		jsonObj.put("acway", entity.getAcway());
		return jsonObj;
	}
	
	public static final String COUNTERSIGN_SIGN = "##";
	/**
	 * 创建会签任务
	 * @param task
	 * @param activityId
	 * @param actorIds
	 * @return
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	public List<Task> createTasks(Task task,final String activityId,String actorIds) throws ServiceException{
		if(!StringHandler.isValidStr(actorIds)) throw new ServiceException("创建会签任务时，发现 参数\"actorIds\"(会签参与者列表)不存在!");
		List<Task> currTasks = null;
		String[] actorIdArr = actorIds.split(",");
		boolean isOrderNo = false;
		Map<String,String> actorIdMap = new LinkedHashMap<String, String>();
		if(actorIds.indexOf(COUNTERSIGN_SIGN) != -1){//串行会签
			isOrderNo = true;
			Map<String,String> firstTaskMap = new HashMap<String, String>();
			int i=0;
			for(String actorIdCfg : actorIdArr){
				String[] cfgArr = actorIdCfg.split(COUNTERSIGN_SIGN);
				if(null != cfgArr && cfgArr.length < 2) throw new ServiceException("会签参与者["+cfgArr[0]+"没有设置审批顺序]");
				String actorId = cfgArr[0];
				String orderNo = cfgArr[1];
				actorIdMap.put(actorId, orderNo);
				if(i == 0) firstTaskMap.put(actorId, orderNo);
				i++;
			}
			currTasks = workflowService.createCounterTasks(task,activityId, firstTaskMap);/*串行时，刚开始只创建一个待办任务*/
		}else{
			currTasks = workflowService.createCounterTasks(task,activityId, Arrays.asList(actorIdArr));
		}
		final String pdid = task.getProcessDefinitionId();
		BussNodeEntity bussNodeEntity = BussNodeCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				BussNodeEntity bussNodeEntity = (BussNodeEntity)obj;
				return (bussNodeEntity.getNodeId().equals(activityId) && bussNodeEntity.getPdid().equals(pdid));
			}
		});
		if(null == bussNodeEntity) throw new ServiceException("在保存会签记录时，发现参数\" bussNodeId\"不存在!");
		Long bussNodeId = bussNodeEntity.getId();
		String assignee = task.getAssignee();
		UserEntity currUser = UserCache.getUser(Long.parseLong(assignee));
		List<CountersignEntity> list = new ArrayList<CountersignEntity>();
		if(isOrderNo){//串行会签
			Task firstTask = currTasks.get(0);
			String procId = firstTask.getProcessInstanceId();
			
			Set<String> actorList = actorIdMap.keySet();
			for(String actorId : actorList){
				String taskId = null;
				if(actorId.equals(firstTask.getAssignee())){
					taskId = firstTask.getId();
				}
				Integer orderNo = Integer.parseInt(actorIdMap.get(actorId));
				CountersignEntity entity = new CountersignEntity();
				entity.setProcId(procId);
				entity.setBussNodeId(bussNodeId);
				entity.setTiid(taskId);
				entity.setAuditUser(Long.parseLong(actorId));
				entity.setOrderNo(orderNo);
				BeanUtil.setCreateInfo(currUser, entity);
				list.add(entity);
			}
		}else{//并行会签
			for(Task ctask : currTasks){
				String cassignee = ctask.getAssignee();
				String procId = ctask.getProcessInstanceId();
				String ctaskId = ctask.getId();
				CountersignEntity entity = new CountersignEntity();
				entity.setProcId(procId);
				entity.setBussNodeId(bussNodeId);
				entity.setTiid(ctaskId);
				entity.setAuditUser(Long.parseLong(cassignee));
				BeanUtil.setCreateInfo(currUser, entity);
				list.add(entity);
			}
		}
		if(null != list && list.size() > 0) countersignService.batchSaveEntitys(list);
		return currTasks;
	}
	
	/**
	 * 提交任务(会签业务)
	 * @param currTask 当前任务
	 * @param activityId 下一目标节点
	 * @param actor 下一任务审批人
	 * @param resultTag	审批结果
	 * @param currUser	当前用户
	 * @return 返回下一任务实例
	 * @throws ServiceException
	 */
	/**
	 * @param currTask
	 * @param activityId
	 * @param actor
	 * @param resultTag
	 * @param currUser
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Task complete(Task currTask, String activityId, String actor,String resultTag,UserEntity currUser) throws ServiceException{
		if(!StringHandler.isValidStr(resultTag)) throw new ServiceException("参数：\"resultTag\" 不能为空!");
		Task nextTask = null;
		Object[] cfgArr = getCfg(currTask);
		Long bussNodeId = (Long)cfgArr[0];
		CountersignCfgEntity cfgEntity = (CountersignCfgEntity)cfgArr[1];
		String procId = currTask.getProcessInstanceId();
		String taskName = currTask.getName();
		String taskId = currTask.getId();
		updateCountersign(bussNodeId, currTask, currUser, resultTag);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("procId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC +procId);
		map.put("bussNodeId", bussNodeId);
		List<CountersignEntity> clist = countersignService.getEntityList(map);
		Object[] unfinishArr = getUnFinishTaskIds(clist,currUser);
		List<String> deltaskIdList = (List<String>)unfinishArr[0];
		List<CountersignEntity> cancelList = (List<CountersignEntity>)unfinishArr[1];
		if(taskName.indexOf(WorkFlowService.SERIAL_CSTASK_PREFIX) != -1){/*串行会签*/
			if(isDoNext(cfgEntity, clist)){/*如果满足指定票数向下流转的条件*/
				if(!isAllFinish(clist)){/*如果是部分做完，且满足条件的情况下。要把剩余的会签记录标识为“作废”*/
					if(null != cancelList && cancelList.size() > 0){
						countersignService.batchUpdateEntitys(cancelList);
					}
				}
				workflowService.complete(taskId, activityId, actor, null);
			}else{
				if(!isAllFinish(clist)){/*会签未做完，没有达到指定票数条件*/
					workflowService.complete(taskId);
					nextTask = createSerialNewTask(currTask, bussNodeId, currUser);
				}else{/*所有会签全部做完后，仍不满足条件*/
					workflowService.complete(taskId, activityId, actor, null);
				}
			}
		}else{/*并行会签*/
			if(isDoNext(cfgEntity, clist)){/*如果满足向下流转的条件*/
				if(!isAllFinish(clist)){/*如果是部分做完，且满足条件的情况下。要把剩余的会签记录标识为“作废”并且把未做的待办任务移除*/
					if(null != cancelList && cancelList.size() > 0){
						countersignService.batchUpdateEntitys(cancelList);
					}
					if(null != deltaskIdList && deltaskIdList.size() >0) workflowService.deleteTaskByIds(deltaskIdList);
				}
				workflowService.complete(taskId, activityId, actor, null);
			}else{
				if(!isAllFinish(clist)){/*会签未做完，不满足条件*/
					workflowService.complete(taskId);
				}else{/*所有会签全部做完后，仍不满足条件*/
					workflowService.complete(taskId, activityId, actor, null);
				}
			}
		}
		return nextTask;
	}
	
	/**
	 * 获取下一步的流转目标节点和参与者
	 * @param cfgEntity	会签配置信息
	 * @param yes	是否满足条件正常流转 
	 * @return	返回下一步目标流转方向的节点ID和审批人
	 * @throws ServiceException 
	 */
	private String[] getNextTransInfos(CountersignCfgEntity cfgEntity, boolean yes) throws ServiceException{
		String[] nextTrans = null;
		Long nextTransId = null;
		String errMsg = null;
		if(yes){
			nextTransId = cfgEntity.getTransId();
			errMsg = "会签条件满足时的流转方向未配置!";
		}else{
			nextTransId = cfgEntity.getUntransId();
			errMsg = "会签条件不满足时的流转方向未配置!";
		}
		if(null == nextTransId) throw new ServiceException(errMsg);
		BussTransEntity bussTransObj = BussTransCache.get(nextTransId);
		if(null == bussTransObj) throw new ServiceException("业务流转方向为空!");
		
		Long bussNodeId = bussTransObj.getEnodeId();	
		BussNodeEntity bussNodeObj = BussNodeCache.getBussNode(bussNodeId);
		if(null == bussNodeObj) throw new ServiceException("目标业务节点为空!");
		String nodeId = bussNodeObj.getNodeId();
		final Long f_nextTransId = nextTransId;
		TransCfgEntity transCfgObj = TransCfgCache.get(new ElementCompare(){
			public boolean equals(Object obj) {
				TransCfgEntity entity = (TransCfgEntity)obj;
				return (f_nextTransId.equals(entity.getTransId()));
			}
		});
		if(null == transCfgObj) throw new ServiceException("流转方向["+bussTransObj.getName()+"]没有配置参与者!");
		return nextTrans;
	}
	
	/**
	 * 创建串行新会签任务
	 * @param currTask
	 * @param bussNodeId
	 * @param currUser
	 * @return
	 * @throws ServiceException 
	 */
	private Task createSerialNewTask(Task currTask, Long bussNodeId, UserEntity currUser) throws ServiceException{
		String procId = currTask.getProcessInstanceId();
		String taskId = currTask.getId();
		String taskName = currTask.getName();
		String[] arr = taskName.split("_");
		int taskCount = Integer.parseInt(arr[2]);
		int orderNo = Integer.parseInt(arr[3]);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("procId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + procId);
		map.put("bussNodeId", bussNodeId);
		map.put("tiid", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC + taskId);
		map.put("orderNo", SqlUtil.LOGIC_GTEQ + SqlUtil.LOGIC + orderNo);
		map.put(SqlUtil.ORDERBY_KEY,"asc:orderNo,id");
		List<CountersignEntity> list = countersignService.getEntityList(map);
		if(null == list || list.size() == 0) throw new ServiceException("找不到下一个会签串行新任务的配置!");
		CountersignEntity countersignObj = list.get(0);
		Long auditUser = countersignObj.getAuditUser();
		String actor = auditUser.toString();
		String newtaskName = WorkFlowService.SERIAL_CSTASK_PREFIX+actor+"_"+taskCount+"_"+orderNo;
		Task newtask = workflowService.createTask(currTask, newtaskName, actor);
		return newtask;
	}

	/**
	 * 更新会签记录为已做
	 * @param bussNodeId	业务节点ID
	 * @param currTask	当前任务
	 * @param currUser  当前用户
	 * @param resultTag 审批意见结果
	 * @return
	 * @throws ServiceException
	 */
	private void updateCountersign(Long bussNodeId,Task currTask,UserEntity currUser,String resultTag) throws ServiceException {
		String procId = currTask.getProcessInstanceId();
		String taskId = currTask.getId();
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("procId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC +procId);
		map.put("bussNodeId", bussNodeId);
		map.put("tiid", taskId);
		CountersignEntity entity = countersignService.getEntity(map);
		if(null == entity) throw new ServiceException("找不到已做的会签记录对象!");
		entity.setResult(resultTag);
		BeanUtil.setModifyInfo(currUser, entity);
		countersignService.updateEntity(entity);
	}
	
	/**
	 * 判断是否能向下流转
	 * @param cfgEntity
	 * @param clist
	 * @return
	 */
	private boolean isDoNext(CountersignCfgEntity cfgEntity,List<CountersignEntity> clist){
		boolean flag = false;
		Integer transType = cfgEntity.getTransType();
		if(null == transType) transType = BussStateConstant.COUNTERSIGNCFG_TRANSTYPE_1;//默认全部审完后向下流转
		switch (transType.intValue()) {
		case BussStateConstant.COUNTERSIGNCFG_TRANSTYPE_1:{/*所有参会人员给出意见后，才往下流转*/
			if(!isAllFinish(clist)) return false;
			flag = isThroghCondition(cfgEntity, clist);
			break;
		}case BussStateConstant.COUNTERSIGNCFG_TRANSTYPE_2:{/*只要符合会签配置条件，即可向下流转*/
			flag = isThroghCondition(cfgEntity, clist);
			break;
		}default:
			break;
		}
		return flag;
	}
	
	/**
	 * 根据会签判断是否满足向下流转的条件
	 * @param cfgEntity
	 * @param clist
	 * @return
	 */
	private boolean isThroghCondition(CountersignCfgEntity cfgEntity,List<CountersignEntity> clist){
		boolean flag = false;
		Integer pdType = cfgEntity.getPdtype();
		Integer voteType = cfgEntity.getVoteType();
		int voteCount = getAuditResult(clist, pdType);
		if(null == voteType) voteType = BussStateConstant.COUNTERSIGNCFG_VOTETYPE_1;
		switch (voteType.intValue()) {
		case BussStateConstant.COUNTERSIGNCFG_VOTETYPE_1:{/*绝对票数*/
			flag = canVoteThrogh(cfgEntity, voteCount);
			break;
		}case BussStateConstant.COUNTERSIGNCFG_VOTETYPE_2:{/*百分比*/
			int totalCount = clist.size();
			float realVoteCount = (voteCount/totalCount) * 100;
			flag = canVoteThrogh(cfgEntity, realVoteCount);
			break;
		}default:
			break;
		}
		return flag;
	}
	
	/**
	 * 判断票数是否能通过
	 * @param cfgEntity
	 * @param voteCount
	 * @return
	 */
	private boolean canVoteThrogh(CountersignCfgEntity cfgEntity,float voteCount){
		Integer eqlogic = cfgEntity.getEqlogic();
		Float eqval = cfgEntity.getEqval();
		boolean flag = false;
		switch (eqlogic.intValue()) {
		case BussStateConstant.COUNTERSIGNCFG_EQLOGIC_1:
			flag = (voteCount > eqval);
			break;
		case BussStateConstant.COUNTERSIGNCFG_EQLOGIC_2:
			flag = (voteCount >= eqval);		
			break;
		case BussStateConstant.COUNTERSIGNCFG_EQLOGIC_3:
			flag = (voteCount == eqval);		
			break;
		case BussStateConstant.COUNTERSIGNCFG_EQLOGIC_4:
			flag = (voteCount < eqval);
			break;
		case BussStateConstant.COUNTERSIGNCFG_EQLOGIC_5:
			flag = (voteCount <= eqval);
			break;
		default:
			break;
		}
		return flag;
	}
	
	/**
	 * 获取符合指定决策方式的票数
	 * @param clist
	 * @param pdType
	 * @return
	 */
	private int getAuditResult(List<CountersignEntity> clist,Integer pdType){
		int count = 0;
		for(CountersignEntity entity : clist){
			String result = entity.getResult();
			if(!StringHandler.isValidStr(result)) continue;
			if(pdType.intValue() == Integer.parseInt(result)) count++;
		}
		return count;
	}
	
	/**
	 * 获取未完成的会签任务ID 和 未完成的会签记录列表
	 * @param clist
	 * @return
	 */
	private Object[] getUnFinishTaskIds(List<CountersignEntity> clist,UserEntity currUser){
		List<String> taskIdList = new ArrayList<String>();
		List<CountersignEntity> cancelList = new ArrayList<CountersignEntity>();
		for(CountersignEntity entity : clist){
			String result = entity.getResult();
			if(StringHandler.isValidStr(result)) continue;
			String taskId = entity.getTiid();
			taskIdList.add(taskId);
			entity.setResult(CountersignEntity.RESULT_BEAR1);
			BeanUtil.setModifyInfo(currUser, entity);
			cancelList.add(entity);
		}
		return new Object[]{taskIdList, cancelList};
	}
	
	/**
	 * 判断所有会签任务是否全部完成
	 * @param clist
	 * @return
	 */
	private boolean isAllFinish(List<CountersignEntity> clist){
		boolean flag = true;
		for(CountersignEntity entity : clist){
			String result = entity.getResult();
			if(!StringHandler.isValidStr(result)){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	private Object[] getCfg(Task currTask) throws ServiceException{
		final String pdid = currTask.getProcessDefinitionId();
		final String nodeId = currTask.getTaskDefinitionKey();
		String desc = currTask.getDescription();
		BussNodeEntity bussNodeEntity = BussNodeCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				BussNodeEntity entity = (BussNodeEntity)obj;
				return (pdid.equals(entity.getPdid()) && nodeId.equals(entity.getNodeId()));
			}
		});
		if(null == bussNodeEntity) throw new ServiceException("找不到会签节点["+desc+"]!");
		final Long bussNodeId = bussNodeEntity.getId();
		NodeCfgEntity nodeCfgEntity = NodeCfgCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				NodeCfgEntity entity = (NodeCfgEntity)obj;
				return (entity.getNodeId().equals(bussNodeId));
			}
		});
		String bussNodeName = bussNodeEntity.getName();
		if(null == nodeCfgEntity) throw new ServiceException("系统检查到节点["+bussNodeName+"]在流程配置没有进行配置!");
		final Long nodeCfgId = nodeCfgEntity.getId();
		CountersignCfgEntity cfgEntity = CountersignCfgCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				CountersignCfgEntity entity = (CountersignCfgEntity)obj;
				return (nodeCfgId.equals(entity.getNodeId()));
			}
		});
		if(null == cfgEntity) throw new ServiceException("系统检查到节点["+bussNodeName+"]在流程配置没有进行会签配置!");
		return new Object[]{bussNodeId,cfgEntity};
	}
}
