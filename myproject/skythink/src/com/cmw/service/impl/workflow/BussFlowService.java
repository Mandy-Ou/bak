package com.cmw.service.impl.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.workflow.WorkFlowService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.TokenRecordsEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussNodeCache;
import com.cmw.service.impl.cache.BussTransCache;
import com.cmw.service.impl.cache.NodeCfgCache;
import com.cmw.service.impl.cache.TransCfgCache;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.BussNodeService;
import com.cmw.service.inter.sys.BussTransService;
import com.cmw.service.inter.sys.NodeCfgService;
import com.cmw.service.inter.sys.TokenRecordsService;
import com.cmw.service.inter.sys.TransCfgService;
import com.cmw.service.inter.sys.UroleService;

import edu.emory.mathcs.backport.java.util.Arrays;


@Service(value="bussflowService")
public class BussFlowService {
	
	@Resource(name="workflowService")
	private WorkFlowService workflowService;
	
	@Resource(name="bussNodeService")
	private BussNodeService bussNodeService;
	
	@Resource(name="bussTransService")
	private BussTransService bussTransService;
				
	@Resource(name="nodeCfgService")
	private NodeCfgService nodeCfgService;
	
	@Resource(name="countersignServiceHandler")
	private CountersignServiceHandler countersignServiceHandler;
	
	@Resource(name="transCfgService")
	private TransCfgService transCfgService;
	
	@Resource(name="tokenRecordsService")
	private TokenRecordsService tokenRecordsService;
	
	@Resource(name="uroleService")
	private UroleService uroleService;
	
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	private BussFlowCacheManager cacheMgr  = BussFlowCacheManager.getInstance();
	
	private UserEntity curruser;/*当前用户*/
	/**
	 * 根据业务类型和流程实例ID建立缓存
	 * @param bussType 业务类型
	 * @param dtProcessInstanceIds 流程实例ID的 DataTable 对象
	 * @throws ServiceException 
	 */
	public void buildCache(Integer bussType,List<String> processInstanceIds) throws ServiceException{
		if(null == processInstanceIds || processInstanceIds.size() == 0) return;
		for(int i=0,count=processInstanceIds.size(); i<count; i++){
			String procId = processInstanceIds.get(i);
			List<Task> currTasks = workflowService.getTaskList(procId);
			if(null == currTasks || currTasks.size() == 0) continue;
			BussFlowModel flowModel = createBussFlowModel(currTasks, bussType);
			cacheMgr.put(procId, flowModel);
		}
	}
	
	/**
	 * 根据业务类型和用户ID获取当前用户的待办流程实例
	 * @param bussType	业务类型 参见 SysConstant.java  以 SYSTEM_BUSSTYPE_ 的常量
	 * @param user
	 * @return
	 */
	public String getProcIdsByUser(Integer bussType,String user){
		if(cacheMgr.size() == 0) return null;
		printFlowModel();
		List<BussFlowModel> list = cacheMgr.getModelsByBussType(bussType);
		if(null == list || list.size() == 0) return null;
		StringBuffer sb = new StringBuffer();
		for(BussFlowModel flowModel : list){
			Map<String,JSONObject> currTaskInfos = flowModel.getCurrTaskInfos();
			System.out.println("userId="+Arrays.toString(currTaskInfos.keySet().toArray())+",procId="+flowModel.getProcId());
			if(null == currTaskInfos || !currTaskInfos.containsKey(user)) continue;
			String procId = flowModel.getProcId();
			sb.append(procId+",");
		}
		return StringHandler.RemoveStr(sb);
	}
	
	/**
	 * 启动流程
	 * @param processDefinitionId 流程定义ID
	 * @param variables 流程实例参数
	 * @param actorId 下一步参与者
	 * @param callback 业务回调对象
	 * @return processInstanceId 流程实例ID
	 * @throws BussFlowException 
	 * @throws ServiceException 
	 */
	public String startFlow(SHashMap<String, Object> flowParams,Map<String,Object> variables,BussFlowCallback callback) throws BussFlowException, ServiceException{
		String pdid = flowParams.getvalAsStr("pdid");
		String nodeType = flowParams.getvalAsStr("nodeType");
		JSONArray nextTrans = (JSONArray)flowParams.get("nextTrans");
		Integer bussType = flowParams.getvalAsInt(BussFlowService.BUSSTYPE_KEY);
		UserEntity currUser = (UserEntity)flowParams.get(SysConstant.USER_KEY);
		this.curruser = currUser;
		setNextTransByTagWay(null,nextTrans);
		String procId = null;
		if(nodeType.equals(NODENAME_FORK) && (null != nextTrans && nextTrans.size() > 1)){
			ProcessInstance processInstance = (null != variables && variables.size() > 0) ? 
					workflowService.startProcessInstance(pdid) : workflowService.startProcessInstance(pdid,variables);
			procId = processInstance.getId();
			List<Task> currTasks = makeForkTasks(nextTrans, procId);
			if(existToken(nextTrans)) makeToken(nextTrans, procId, null);
			setFlowModelInfo(bussType, callback, null, procId, pdid,currTasks);
		}else{
			procId = startFlow(pdid, variables, nextTrans, callback);
		}
		return procId;
	}
	
	/**
	 * 根据目标任务处理方式来标识要删除的任务
	 * @param nextTrans
	 */
	private void setNextTransByTagWay(String procId, JSONArray nextTrans){
		if((null == nextTrans || nextTrans.size() == 0) || !StringHandler.isValidStr(procId)) return;
		for(int i=0,count=nextTrans.size(); i<count; i++){
			JSONObject nextTran = nextTrans.getJSONObject(i);
			Integer tagWay = nextTran.getInteger("tagWay");
			if(null == tagWay || tagWay.intValue() == 0) continue;
			if(tagWay.intValue() == BussStateConstant.TRANSCFG_TAGWAY_1){
				String taskDefKey = nextTran.getString("activityId");
				List<Task> tasks = workflowService.getTaskListByTaskDefKey(procId, taskDefKey);
				if(null == tasks || tasks.size() == 0) continue;
				setDelAssignee("actorId",nextTran);
			}
		}
	}
	
	/**
	 * 将指定的流转路径参与者标识成要删除的参与者
	 * @param nextTran
	 */
	private void setDelAssignee(String key,JSONObject nextTran) {
		nextTran.put(key, DEL_ASSIGNEEID);
	}
	
	/**
	 * 删除要取消的任务
	 */
	private void delTaskByDelAssigneeId(){
		List<Task> delTasks = workflowService.getTaskListByAssignee(DEL_ASSIGNEEID);
		workflowService.deleteTasks(delTasks);
	}
	
	/**
	 * 并行FORK业务处理方法
	 * @param nextTrans	下一流转路径列表
	 * @param procId	流程实例ID
	 * @return 返回已分配好审批者的当前任务列表
	 */
	private List<Task> makeForkTasks(JSONArray nextTrans, String procId) {
		List<Task> taskList = new ArrayList<Task>();
		List<Task> currTasks = workflowService.getTaskList(procId);
		
		int count = nextTrans.size();
		for(Task currTask : currTasks){
//			String taskName = currTask.getName();
			String taskDefKey = currTask.getTaskDefinitionKey();
			String actorId = null;
			for(int i=0; i<count; i++){
				JSONObject nextTran = nextTrans.getJSONObject(i);
				String activityId = nextTran.getString("activityId");
				if(taskDefKey.equals(activityId)){
					actorId = nextTran.getString("actorId");
					break;
				}
			}
			if(!StringHandler.isValidStr(actorId)) continue;
			currTask.setAssignee(actorId);
			workflowService.saveTask(currTask);
			if(StringHandler.isValidStr(actorId) && !actorId.equals(DEL_ASSIGNEEID)){
				taskList.add(currTask);
			}
		}
		
		if(null == taskList || taskList.size() == 0){
			String currTaskId = currTasks.get(0).getId();
			for(int i=0; i<count; i++){
				JSONObject nextTran = nextTrans.getJSONObject(i);
				String activityId = nextTran.getString("activityId");
				String actorId = nextTran.getString("actorId");
				if(!StringHandler.isValidStr(actorId) || actorId.equals(DEL_ASSIGNEEID)){
					continue;
				}
				Task newTask = workflowService.createTagTask(currTaskId, actorId, activityId);
				taskList.add(newTask);
			}
		}
		delTaskByDelAssigneeId();
		return taskList;
	}
	
	/**
	 * 处理令牌业务
	 * @param nextTrans
	 * @param procId
	 * @param currTaskId
	 * @throws ServiceException
	 * @return 返回 boolean 值 [true:不需要调用 complete() 方法 ,false:要调用 complete()方法]
	 */
	private boolean makeToken(JSONArray nextTrans, String procId, String currTaskId) throws ServiceException{
		boolean isComplete = false;
		JSONObject nextTran = nextTrans.getJSONObject(0);
		Integer sign = nextTran.getInteger("sign");
		Integer tokenType = nextTran.getInteger("tokenType");
		String token = nextTran.getString("token");
		String activityId = nextTran.getString("activityId");
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("procId",  SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+procId);
		params.put("token", SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+token);
		TokenRecordsEntity tokenRecordsObj = tokenRecordsService.getEntity(params);
		String remark = null;
		Integer tokenStatus = null;
		switch (tokenType.intValue()) {
		case BussStateConstant.TRANSCFG_TOKENTYPE_2:{
			tokenStatus = BussStateConstant.TOKENRECORDS_STATUS_1;
			remark = "[并行令牌开始]";
			break;
		}case BussStateConstant.TRANSCFG_TOKENTYPE_3:{
			if(null != tokenRecordsObj && tokenRecordsObj.getStatus().intValue() == BussStateConstant.TOKENRECORDS_STATUS_3){
				tokenStatus = BussStateConstant.TOKENRECORDS_STATUS_3;
			}else{
				tokenStatus = BussStateConstant.TOKENRECORDS_STATUS_2;
			}
			remark = "[并行令牌汇总]";
			if(null == sign || sign.intValue() == BussStateConstant.NODECFG_SIGN_0){
				isComplete = false;
			}else{
				if(StringHandler.isValidStr(currTaskId)){
					createTaskBySign(procId, currTaskId, nextTran, sign, activityId,tokenRecordsObj);
					isComplete = true;
				}
			}
			break;
		}case BussStateConstant.TRANSCFG_TOKENTYPE_4:{
			tokenStatus = BussStateConstant.TOKENRECORDS_STATUS_3;
			remark = "[并行令牌完成]";
			break;
		}default:
			break;
		}
		if(null == tokenStatus) return isComplete;
		saveTokenRecords(procId, nextTran, tokenRecordsObj, tokenStatus,remark);
		return isComplete;
	}

	/**
	 * 根据流转路径判断是否有并行令牌需要处理
	 * @param nextTrans	目标流转路径列表
	 * @return	返回 boolean  [true:有并行令牌要处理,false : 无]
	 */
	private boolean existToken(JSONArray nextTrans) {
		JSONObject nextTran = nextTrans.getJSONObject(0);
		Integer tokenType = nextTran.getInteger("tokenType");
		String token = nextTran.getString("token");
		boolean flag = true;
		if(!StringHandler.isValidStr(token) || (null == tokenType || tokenType.intValue() == BussStateConstant.TRANSCFG_TOKENTYPE_1)){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 根据是否补签来创建任务实例
	 * @param procId	流程实例ID
	 * @param currTaskId	
	 * @param nextTran
	 * @param sign
	 * @param activityId
	 * @param tokenRecordsObj
	 * @return 返回目标节点的任务列表
	 */
	private List<Task> createTaskBySign(String procId, String currTaskId,
			JSONObject nextTran, Integer sign, String activityId,
			TokenRecordsEntity tokenRecordsObj) {
		List<Task> nextTaskList = null;
		if(null != sign && sign.intValue() == BussStateConstant.NODECFG_SIGN_1){/*有设置创建补签任务*/
			List<Task> taskList = workflowService.getTaskListByTaskDefKey(procId,activityId);
			if(null != taskList && taskList.size() > 0){
				workflowService.complete(currTaskId, activityId);
				List<Task> delTasks = workflowService.getTaskListByAssigneeIsNull(procId, activityId);
				workflowService.deleteTasks(delTasks);
			}else{
				String actorId = nextTran.getString("actorId");
				if(null != tokenRecordsObj && tokenRecordsObj.getStatus().intValue() == BussStateConstant.TOKENRECORDS_STATUS_3){
					workflowService.complete(currTaskId,activityId, actorId, null);
					List<Task> nextTasks =workflowService.getTaskList(procId, actorId);
					if(null == nextTasks || nextTasks.size() == 0) return null;
					for(Task nextTask : nextTasks){
						String taskName = nextTask.getName();
						String siginTaskName = WorkFlowService.RETROACTIVE_CSTASK_PREFIX+taskName;
						nextTask.setName(siginTaskName);
						workflowService.saveTask(nextTask);
					}
					nextTaskList = nextTasks;
				}else{
					Task currTask = workflowService.getTask(currTaskId);
					Task nextTask = workflowService.complete(currTask, activityId, actorId, null);
					if(null == nextTask){
						nextTask = workflowService.createTagTask(currTask, actorId,activityId);
					}
					if(null != nextTask){
						nextTaskList = new ArrayList<Task>();
						nextTaskList.add(nextTask);
					}
				}
			}
		}
		return nextTaskList;
	}
	
	/**
	 * 保存令牌记录
	 * @param procId
	 * @param nextTran
	 * @param tokenRecordsObj
	 * @param tokenStatus
	 * @throws ServiceException
	 */
	private void saveTokenRecords(String procId, JSONObject nextTran,TokenRecordsEntity tokenRecordsObj,
			Integer tokenStatus,String remark) throws ServiceException {
		Long transId = nextTran.getLong("transId");
		BussTransEntity bussTransObj = BussTransCache.get(transId);
		Long bnodeId = bussTransObj.getBnodeId();
		Long enodeId = bussTransObj.getEnodeId();
		BussNodeEntity bussNodeObj = BussNodeCache.getBussNode(enodeId);
		String token = nextTran.getString("token");
		if(null == tokenRecordsObj){
			tokenRecordsObj = new TokenRecordsEntity();
			tokenRecordsObj.setProcId(procId);
			tokenRecordsObj.setBussNodeId(bnodeId);
			tokenRecordsObj.setToken(token);
			BeanUtil.setCreateInfo(curruser, tokenRecordsObj);
		}else{
			BeanUtil.setModifyInfo(curruser, tokenRecordsObj);
		}
		remark += ": {从路径["+bussTransObj.getName()+"] -->至["+bussNodeObj.getName()+"]目标节点} ";
		tokenRecordsObj.setStatus(tokenStatus);
		tokenRecordsObj.setRemark(remark);
		tokenRecordsService.saveOrUpdateEntity(tokenRecordsObj);
	}
	
	/**
	 * 启动流程
	 * @param processDefinitionId 流程定义ID
	 * @param variables 流程实例参数
	 * @param actorId 下一步参与者
	 * @param callback 业务回调对象
	 * @return processInstanceId 流程实例ID
	 * @throws BussFlowException 
	 * @throws ServiceException 
	 */
	public String startFlow(String processDefinitionId,Map<String,Object> variables,String actorId,BussFlowCallback callback) throws BussFlowException, ServiceException{
		ProcessInstance processInstance = (null != variables && variables.size() > 0) ? 
				workflowService.startProcessInstance(processDefinitionId) : workflowService.startProcessInstance(processDefinitionId,variables);
		Integer bussType = (Integer)variables.get(BUSSTYPE_KEY);
		createModelByProcInstance(actorId, processInstance,bussType);
		if(null == callback) return processInstance.getId();
		callback.execute(cacheMgr.get(processInstance.getProcessInstanceId()));
		System.out.println("startFlow.print.bussModel...");
		printFlowModel();
		return processInstance.getId();
	}
	
	/**
	 * 启动流程
	 * @param processDefinitionId 流程定义ID
	 * @param variables 流程实例参数
	 * @param nextTrans 下一步流转路径列表
	 * @param callback 业务回调对象
	 * @return processInstanceId 流程实例ID
	 * @throws BussFlowException 
	 * @throws ServiceException 
	 */
	public String startFlow(String processDefinitionId,Map<String,Object> variables,JSONArray nextTrans,BussFlowCallback callback) throws BussFlowException, ServiceException{
		setNextTransByTagWay(null, nextTrans);
		ProcessInstance processInstance = (null != variables && variables.size() > 0) ? 
				workflowService.startProcessInstance(processDefinitionId) : workflowService.startProcessInstance(processDefinitionId,variables);
		delTaskByDelAssigneeId();
		Integer bussType = (Integer)variables.get(BUSSTYPE_KEY);
		String actorId = getActors(nextTrans);
		createModelByProcInstance(actorId, processInstance,bussType);
		if(null == callback) return processInstance.getId();
		callback.execute(cacheMgr.get(processInstance.getProcessInstanceId()));
		System.out.println("startFlow.print.bussModel...");
		printFlowModel();
		return processInstance.getId();
	}
	
	private String getActors(JSONObject nextTran,String nodeType) throws ServiceException{
		String actorId = nextTran.getString("actorId");
		if(null != nodeType && (nodeType.equals(BussFlowService.NODENAME_END) || 
				nodeType.equals(BussFlowService.NODENAME_SERVICE_END))){/*如果目标节点是结束节点 或异常结束节点，则清空相应参数*/
			actorId = this.curruser.getUserId().toString();
		}
		return actorId;
	}
	
	
	private String getActors(JSONArray nextTrans) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		for(int i=0,count=nextTrans.size(); i<count; i++){
			JSONObject nextTran = nextTrans.getJSONObject(0);
			String actorId = nextTran.getString("actorId");
			if(!StringHandler.isValidStr(actorId)) throw new ServiceException(" actorId 不能为空!");
			sb.append(actorId).append(",");
		}
		return StringHandler.RemoveStr(sb);
	}
	
	private void printFlowModel(){
		List<BussFlowModel> models = cacheMgr.getAllModels();
		Map<Integer,List<BussFlowModel>> map = new HashMap<Integer, List<BussFlowModel>>();
		for(BussFlowModel model : models){
			Integer bussType = model.getBussType();
			if(map.containsKey(bussType)){
				map.get(bussType).add(model);
			}else{
				List<BussFlowModel> tempList = new ArrayList<BussFlowModel>();
				tempList.add(model);
				map.put(bussType, tempList);
			}
		}
		Map<Integer,String> inTypeMap = new HashMap<Integer, String>();
		inTypeMap.put(new Integer(1), "业务品种");
		inTypeMap.put(new Integer(2), "子业务流程");
		Set<Integer> keys = map.keySet();
		for(Integer key : keys){
			String inTypeName = inTypeMap.get(key);
			List<BussFlowModel> tempList = map.get(key);
			System.out.println(inTypeName+"流程实列共有:"+tempList.size()+"个");
			System.out.println("------------------------------------");
			if(null == tempList || tempList.size() == 0) continue;
			for(BussFlowModel model : tempList){
				System.out.println(Arrays.toString(model.getCurrTaskInfos().keySet().toArray())+",procId="+model.getProcId());
			}
			System.out.println("==============================");
		}
	}
	
	/**
	 * 从缓存中获取
	 * @param procId
	 * @return
	 */
	public BussFlowModel getFlowModel(String procId){
		return cacheMgr.get(procId);
	}
	/**
	 * 从缓存中获取指定用户的流程数据
	 * @param user	用户
	 * @param procId	流程实例ID
	 * @return	返回指定用户的流程数据
	 * @throws ServiceException 
	 */
	public BussFlowModel getFlowModel(UserEntity user,String procId,Integer bussType) throws ServiceException{
		BussFlowModel model = getFlowModel(procId); 
		if(null == model){
			List<Task> tasks = null;
			if(null == user){
				tasks = getTasks(procId);
			}else{
				String assignee = user.getUserId().toString();
				tasks = getTasks(procId, assignee);
			}
			if(null == tasks || tasks.size() == 0) return null;
			model = createBussFlowModel(tasks,bussType);
			putFlowModel(model);
		}
		if(null == user) return model;
		Map<String, JSONObject> currTaskInfoMap = model.getCurrTaskInfos();
		filterTrans(user,model);
		String actorId = user.getUserId().toString();
		return currTaskInfoMap.containsKey(actorId) ? model : null;
	}
	
	/**
	 * 排除不满足条件的流转路径
	 * @param user 当前用户
	 * @param model 流程模型对象
	 * @throws ServiceException 
	 */
	private void filterTrans(UserEntity user,BussFlowModel model) throws ServiceException{
		Map<String,JSONArray> nextTransInfos = model.getNextTransInfos();
		if(null == nextTransInfos || nextTransInfos.size() == 0) return;
		String procId = model.getProcId();
		Long userId = user.getUserId();
		Collection<JSONArray> nextTransMap = nextTransInfos.values();
		for(JSONArray jsonArr : nextTransMap){
			if(null == jsonArr || jsonArr.size() == 0) continue;
			List<JSONObject> removeList = new ArrayList<JSONObject>();
			filterTrans(user, procId, userId, jsonArr, removeList);
			jsonArr.removeAll(removeList);
		}
	}

	private void filterTrans(UserEntity user, String procId, Long userId,
			JSONArray jsonArr, List<JSONObject> removeList)
			throws ServiceException {
		for(int i=0,count=jsonArr.size(); i<count; i++){
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			Integer stint = jsonObj.getInteger("stint");
			String limitVals = jsonObj.getString("limitVals");
			String nodeId = jsonObj.getString("nodeId");
			JSONArray forkNextArr = jsonObj.getJSONArray(nodeId);
			if(null != forkNextArr && forkNextArr.size() > 0){
				filterTrans(user,procId,userId,forkNextArr,removeList);
			}
			if(null == stint || stint.intValue() == TransCfgEntity.STINT_1) continue;
			switch (stint) {
			case TransCfgEntity.STINT_2:{/*角色*/
				if(!StringHandler.isValidStr(limitVals)) continue;
				if(!hasCurrRole(limitVals, userId)) removeList.add(jsonObj);/*没有权限，则移除此流转路径*/
				break;
			}case TransCfgEntity.STINT_3:{/*用户*/
				if(!StringHandler.isValidStr(limitVals)) continue;
				if(!hasCurrUser(limitVals, userId)) removeList.add(jsonObj);/*没有权限，则移除此流转路径*/
				break;
			}case TransCfgEntity.STINT_4:{/*目标节点无审批人取消任务*/
				if(!hasActor(user,procId,jsonObj)) setDelAssignee("actorIds",jsonObj);/*将审批参与者标识为删除*/
				break;
			}
			default:
				break;
			}
		}
	}
	
	/**
	 * 判断当前用户是否有当前流转方向的角色条件的权限
	 * @param limitVals
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	private boolean hasCurrRole(String limitVals,Long userId) throws ServiceException{
		boolean flag = false;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", userId);
		List<UroleEntity> uroles = uroleService.getEntityList(map);
		if(null == uroles || uroles.size() == 0) return true;
		
		String[] roleArr = limitVals.split(",");
		for(String id : roleArr){
			Long _roleId = Long.parseLong(id);
			for(UroleEntity urole : uroles){
				if(_roleId.equals(urole.getRoleId())) return true;
			}
		}
		return flag;
	}
	
	/**
	 * 判断目标节点是否有审批人，如果没有审批人则取消任务
	 * @param jsonObj
	 * @return 返回  boolean 值 [true:有审批人,false : 无审批人]
	 * @throws ServiceException 
	 */
	private boolean hasActor(UserEntity user,String procId,JSONObject jsonObj) throws ServiceException{
		boolean flag = false;
		Integer acType = jsonObj.getInteger("acType");
		String actorIds = jsonObj.getString("actorIds");
		Long transId = jsonObj.getLong("transId");
		if(null == acType || acType.intValue() == TransCfgEntity.ACTYPE_0) return true;
		
		switch (acType.intValue()) {
		case TransCfgEntity.ACTYPE_1:{
			if(StringHandler.isValidStr(actorIds)){
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("roleId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + actorIds);
				List<UroleEntity> uroles = uroleService.getEntityList(map);
				if(null != uroles && uroles.size() > 0) flag = true;
			}
			break;
		}case TransCfgEntity.ACTYPE_2:{
			flag = (StringHandler.isValidStr(actorIds));
			break;
		}case TransCfgEntity.ACTYPE_3:{
			BussTransEntity bussTransObj = BussTransCache.get(transId);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
				Long nodeId = (null == bussTransObj) ? -1L : bussTransObj.getEnodeId();
				params.put("procId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + procId);
				params.put("bussNodeId", nodeId);
				List<AuditRecordsEntity> recordEntitys = auditRecordsService.getEntityList(params);
				flag = (null != recordEntitys && recordEntitys.size() > 0);
			break;
		}case TransCfgEntity.ACTYPE_4:{
			flag = (null != user.getLeader());
			break;
		}case TransCfgEntity.ACTYPE_5:{
			Long procStartor = auditRecordsService.getStartor(procId);
			flag = (null != procStartor);
			break;
		}default:
			break;
		}
		return flag;
	}
	
	/**
	 * 判断当前用户是否有当前流转方向的用户条件的权限
	 * @param limitVals
	 * @param userId
	 * @return
	 */
	private boolean hasCurrUser(String limitVals,Long userId){
		boolean flag = false;
		String[] userArr = limitVals.split(",");
		for(String id : userArr){
			Long _userId = Long.parseLong(id);
			if(_userId.equals(userId)) return true;
		}
		return flag;
	}
	
	/**
	 * 从流程数据模型放入缓存中
	 * @param flowModel 流程数据模型
	 */
	public void putFlowModel(BussFlowModel flowModel){
		String procId = flowModel.getProcId();
		cacheMgr.put(procId, flowModel);
	}
	
	/**
	 * 根据流程实例ID 和  候选参与者获取该参与者的待办任务
	 * @param processInstanceId 流程实例ID
	 * @param assignee 参与者
	 * @return 返回参与者的任务列表
	 */
	public List<Task> getTasks(String processInstanceId,String assignee){
		return workflowService.getTaskList(processInstanceId, assignee);
	}
	
	/**
	 * 根据流程实例ID 获取待办任务
	 * @param processInstanceId 流程实例ID
	 * @return 返回参与者的任务列表
	 */
	public List<Task> getTasks(String processInstanceId){
		return workflowService.getTaskList(processInstanceId);
	}
	/**
	 * 获取流程实例
	 * @param processInstanceId 流程实例ID 
	 * @return 返回流程实例
	 */
	public ProcessInstance getProcessInstance(String processInstanceId){
		return workflowService.getProcessInstance(processInstanceId);
	}
	
	/**
	 * 根据流程实例ID和参与者创建业务流程模型数据并加入缓存中
	 * @param actorId
	 * @param processInstance
	 * @param bussType
	 * @return
	 * @throws BussFlowException
	 * @throws ServiceException
	 */
	private String createModelByProcInstance(String actorId,ProcessInstance processInstance, Integer bussType)
			throws BussFlowException, ServiceException {
		String procId = processInstance.getId();
		List<Task> taskList = workflowService.getTaskList(procId);
		if(null == taskList || taskList.size() == 0) throw new BussFlowException("开始流程时，无法根据流程实例ID:["+procId+"]获取下一步行待办任务实例!");
		Task currTask = taskList.get(0);
		currTask.setAssignee(actorId);
		workflowService.saveTask(currTask);
		BussFlowModel  flowModel = createBussFlowModel(taskList,bussType);
		cacheMgr.put(procId, flowModel);
		return procId;
	}
	
	public BussFlowModel createBussFlowModel(List<Task> currTasks, Integer bussType) throws ServiceException{
		BussFlowModel model = new BussFlowModel();
		Task nextTask = currTasks.get(0);
		String pdid = nextTask.getProcessDefinitionId();
		String procId = nextTask.getProcessInstanceId();
		model.setPdid(pdid);
		model.setProcId(procId);
		model.setBussType(bussType);
		updateFlowModel(currTasks, model);
		return model;
	}

	public void updateFlowModel(List<Task> currTasks, BussFlowModel model)
			throws ServiceException {
		removeCurrUserTaskInfos(model);
		Map<String,JSONObject> currTaskInfos = model.getCurrTaskInfos();
		Map<String,JSONObject> _currTaskInfos = getCurrTaskInfos(currTasks);
		if(null == currTaskInfos || currTaskInfos.size() == 0){
			model.setCurrTaskInfos(_currTaskInfos);
		}else{
			currTaskInfos.putAll(_currTaskInfos);
		}
		String nodeIds = getNodeIds(model.getCurrTaskInfos());
		if(!StringHandler.isValidStr(nodeIds)) return;
		Map<String,JSONObject> currNodeCfgs = model.getCurrNodeCfgs();
		Map<String,JSONObject> _currNodeCfgs = getCurrNodeCfgs(nodeIds);
		if(null == currNodeCfgs || currNodeCfgs.size() == 0){
			model.setCurrNodeCfgs(_currNodeCfgs);
		}else{
			currNodeCfgs.putAll(_currNodeCfgs);
		}
		
		Map<String,JSONArray> nextTransInfos = model.getNextTransInfos();
		Map<String,JSONArray> _nextTransInfos = getNextTransInfos(nodeIds);
		if(null == nextTransInfos || nextTransInfos.size() == 0){
			model.setNextTransInfos(_nextTransInfos);
		}else{
			nextTransInfos.putAll(_nextTransInfos);
		}
	}
	
	/**
	 * 从流程模块中移除当前用户的任务实例信息
	 * @param model
	 */
	private void removeCurrUserTaskInfos(BussFlowModel model){
		if(null == curruser) return;
		String userId = curruser.getUserId().toString();
		Map<String,JSONObject> currTaskInfos = model.getCurrTaskInfos();
		if(null == currTaskInfos || currTaskInfos.size() == 0) return;
		JSONObject currTask = currTaskInfos.get(userId);
		if(null == currTask) return;
		if(null != currTask) currTaskInfos.remove(userId);
		String nodeId = currTask.getString("nodeId");
		Map<String,JSONObject> currNodeCfgs = model.getCurrNodeCfgs();
		if(null != currNodeCfgs && currNodeCfgs.size() > 0){
			currNodeCfgs.remove(nodeId);
		}
		Map<String, JSONArray> nextTransInfos = model.getNextTransInfos();
		if(null != nextTransInfos && nextTransInfos.size() > 0){
			nextTransInfos.remove(nodeId);
		}
	}
	
	public void updateFlowModel(String actorId, BussFlowModel model)
			throws ServiceException {
		String pdid = model.getPdid();
		if(!StringHandler.isValidStr(pdid)) throw new ServiceException("在调用 updateFlowModel 方法时，参数 model 对象必须设置 pdid 值!");
		Map<String,JSONObject> currTaskInfos = getCurrTaskInfos(actorId,model.getPdid());
		model.setCurrTaskInfos(currTaskInfos);
		String nodeIds = getNodeIds(currTaskInfos);
		if(StringHandler.isValidStr(nodeIds)){
			Map<String,JSONObject> currNodeCfgs = getCurrNodeCfgs(nodeIds);
			model.setCurrNodeCfgs(currNodeCfgs);
			Map<String,JSONArray> nextTransInfos = getNextTransInfos(nodeIds);
			model.setNextTransInfos(nextTransInfos);
		}
	}
	
	/**
	 * 获取流程定义ID 开始节点待办任务信息
	 * @param assignee 参与者
	 * @param pdid 流程定义ID 
	 * @return
	 * @throws ServiceException
	 */
	private Map<String,JSONObject> getCurrTaskInfos(String assignee,String pdid) throws ServiceException{
		BussNodeEntity startNode = getBussStartNode(pdid);
		if(null == startNode) throw new ServiceException("无法获取流程 "+pdid+" 的开始节点信息!");
		Map<String,JSONObject> currTaskInfos = new HashMap<String, JSONObject>();
		String nodeName = startNode.getName();
		Long nodeId = startNode.getId();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("taskId", null);
		jsonObj.put("nodeId", nodeId);
		jsonObj.put("nodeName", nodeName);
		currTaskInfos.put(assignee, jsonObj);
		return currTaskInfos;
	}
	
	/**
	 * 获取当前待办任务信息
	 * @param currTasks
	 * @return
	 * @throws ServiceException
	 */
	private Map<String,JSONObject> getCurrTaskInfos(List<Task> currTasks) throws ServiceException{
		Map<String,JSONObject> currTaskInfos = new HashMap<String, JSONObject>();
		for(Task task : currTasks){
			String pdid = task.getProcessDefinitionId();
			String taskId = task.getId();
			String taskName = task.getName();
			String taskNodeId = task.getTaskDefinitionKey();
			String assignee = task.getAssignee();
			BussNodeEntity bussNode = getBussNode(pdid, taskNodeId);
			Long nodeId = bussNode.getId();
			String nodeName = bussNode.getName();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("taskId", taskId);
			jsonObj.put("taskName", taskName);
			jsonObj.put("nodeId", nodeId);
			jsonObj.put("nodeName", nodeName);
			currTaskInfos.put(assignee, jsonObj);
		}
		return currTaskInfos;
	}

	/**
	 * 获取当前待办任务的配置
	 * @param nodeIds 待办任务的节点ID列表
	 * @return 返回当前待办任务的配置
	 * @throws ServiceException 
	 */
	public Map<String,JSONObject> getCurrNodeCfgs(String nodeIds) throws ServiceException{
		List<NodeCfgEntity> nodeCfgs = getNodeCfgList(nodeIds);
		if(null == nodeCfgs || nodeCfgs.size() == 0) return null;
		Map<String,JSONObject> currNodeCfgs = new HashMap<String, JSONObject>();
		for(NodeCfgEntity nodeCfg : nodeCfgs){
			Long nodeId = nodeCfg.getNodeId();
			JSONObject obj = new JSONObject();
			obj.put("transWay", nodeCfg.getTransWay());
			obj.put("eventType", nodeCfg.getEventType());
			obj.put("sign", nodeCfg.getSign());
			Integer counterrsign = nodeCfg.getCounterrsign();
			obj.put("counterrsign", counterrsign);
			if(null != counterrsign && counterrsign.intValue() == NodeCfgEntity.COUNTERRSIGN_1){/*加入会签配置*/
				Long nodeCfgId = nodeCfg.getId();
				JSONObject countersignCfg = countersignServiceHandler.getCountersignCfg(nodeCfgId);
				if(null != countersignCfg) obj.put("countersignCfg", countersignCfg);
			}
			obj.put("reminds", nodeCfg.getReminds());
			Integer isTimeOut = nodeCfg.getIsTimeOut();
			boolean flag = false;
			if(null != isTimeOut && isTimeOut.intValue() == NodeCfgEntity.TIMEOUT_1){
				flag = true;
				obj.put("timeOut", nodeCfg.getTimeOut());
				obj.put("btime", nodeCfg.getBtime());
			}
			obj.put("isTimeOut", flag);
			currNodeCfgs.put(nodeId.toString(), obj);
		}
		return currNodeCfgs;
	}
	

	/**
	 * 获取节点配置，优先从缓存中取
	 * @param nodeIds
	 * @return
	 * @throws ServiceException
	 */
	private List<NodeCfgEntity> getNodeCfgList(String nodeIds)
			throws ServiceException {
		final String[] nodeIdArr = nodeIds.split(",");
		List<NodeCfgEntity> nodeCfgs = null;
		if(null != nodeIdArr && nodeIdArr.length > 0){
		  nodeCfgs = NodeCfgCache.getList(new ElementCompare() {
				@Override
				public boolean equals(Object obj) {
					NodeCfgEntity entity = (NodeCfgEntity)obj;
					boolean flag = false;
					for(String nodeId : nodeIdArr){
						if(nodeId.equals(entity.getNodeId().toString())){
							flag = true;
							break;
						}
					}
					return flag;
				}
		  });
		}
		
		if(null == nodeCfgs || nodeCfgs.size() == 0){
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("actionType", SysConstant.ACTION_TYPE_NODECFG_1);
			map.put("nodeId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + nodeIds);
			 nodeCfgs = nodeCfgService.getEntityList(map);
			if(null != nodeCfgs && nodeCfgs.size() > 0) NodeCfgCache.add(nodeCfgs);
		}
		return nodeCfgs;
	}
	
	private String getNodeIds(Map<String, JSONObject> currTaskInfos) {
		if(null == currTaskInfos || currTaskInfos.size() == 0) return null;
		Collection<JSONObject> currTaskList = currTaskInfos.values();
		StringBuffer sb = new StringBuffer();
		for(JSONObject currTask : currTaskList){
			Long nodeId = (Long)currTask.get("nodeId");
			sb.append(nodeId+",");
		}
		if(null == sb || sb.length() == 0) return null;
		String nodeIds = StringHandler.RemoveStr(sb);
		return nodeIds;
	}

	/**
	 * 根据流程定义ID 获取开始节点实体信息
	 * @param currTasks
	 * @param pdid
	 * @return
	 * @throws ServiceException
	 */
	private BussNodeEntity getBussStartNode(final String pdid) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		final String nodeType = "startEvent";
		map.put("pdid", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + pdid);
		map.put("nodeType", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + nodeType);
		BussNodeEntity bussStartNode = BussNodeCache.get(new ElementCompare() {
			@Override
			public boolean equals(Object obj) {
				BussNodeEntity entity = (BussNodeEntity)obj;
				return ((null != pdid && entity.getPdid().equals(pdid)) && entity.getNodeType().equals(nodeType));
			}
		});
		
		if(null == bussStartNode){
			bussStartNode = bussNodeService.getEntity(map);
			if(null != bussStartNode) BussNodeCache.addBussNode(bussStartNode);
		}
		return bussStartNode;
	}
	
	/**
	 * 根据流程定义ID 和 节点名称 获取节点实体信息
	 * @param currTasks
	 * @param pdid
	 * @param nodeName 节点名称
	 * @return 返回 BussNodeEntity 对象
	 * @throws ServiceException
	 */
	private BussNodeEntity getBussNode(final String pdid,final String taskNodeId) throws ServiceException {
		BussNodeEntity bussNode = BussNodeCache.get(new ElementCompare() {
			@Override
			public boolean equals(Object obj) {
				BussNodeEntity entity = (BussNodeEntity)obj;
				return ((null != pdid && entity.getPdid().equals(pdid)) && (null != taskNodeId && entity.getNodeId().equals(taskNodeId)));
			}
		});
		
		if(null == bussNode){
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("pdid", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + pdid);
			map.put("nodeId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + taskNodeId);
			bussNode = bussNodeService.getEntity(map);
			if(null != bussNode) BussNodeCache.addBussNode(bussNode);
		}
		return bussNode;
	}
	
	
	/**
	 * 获取下一步流转路径列表
	 * @param currTaskInfos	当前任务信息
	 * @return 
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,JSONArray> getNextTransInfos(String nodeIds) throws ServiceException{
		List<BussTransEntity> bussTrans = getBussTrans(nodeIds);
		Object[] transCfgDatas = getTransCfgDatas(bussTrans);
		Map<Long,Map<String,String>> tranCfgMap = (Map<Long,Map<String,String>>)transCfgDatas[0];
		Map<Long,Map<String,Object>> nextNodeCfgMap = (Map<Long,Map<String,Object>>)transCfgDatas[1];
		Map<String,JSONArray> nextTransInfos = new HashMap<String, JSONArray>();
		StringBuffer sb = new StringBuffer();
		for(BussTransEntity bussTran : bussTrans){
			Long id = bussTran.getId();
			Long bnodeId = bussTran.getBnodeId();
			
			sb.append(id+",");
			String bnodeIdStr = bnodeId.toString();
			
			JSONObject obj = new JSONObject();
			Long transId = bussTran.getId();
			String transName = bussTran.getName();
			String express = bussTran.getExpress();
			String exparams = bussTran.getExparams();
			obj.put("transId", transId.toString());
			obj.put("name", transName);
			obj.put("express", express);
			obj.put("exparams", exparams);
			if(null != tranCfgMap && tranCfgMap.containsKey(transId)){
				obj.putAll(tranCfgMap.get(transId));
			}
			Long enodeId = bussTran.getEnodeId();
			if(null != nextNodeCfgMap && nextNodeCfgMap.containsKey(enodeId)){
				obj.putAll(nextNodeCfgMap.get(enodeId));
			}
			
			if(nextTransInfos.containsKey(bnodeIdStr)){
				JSONArray jsonArr = nextTransInfos.get(bnodeIdStr);
				jsonArr.add(obj);
			}else{
				JSONArray arr = new JSONArray();
				arr.add(obj);
				nextTransInfos.put(bnodeIdStr, arr);
			}
		}
		return nextTransInfos;
	}

	private List<BussTransEntity> getBussTrans(String nodeIds)throws ServiceException {
		final String[] nodeIdArr = nodeIds.split(",");
		List<BussTransEntity> bussTrans = null;
		if(null != nodeIdArr && nodeIdArr.length > 0){
			bussTrans = BussTransCache.getList(new ElementCompare() {
				@Override
				public boolean equals(Object obj) {
					BussTransEntity entity = (BussTransEntity)obj;
					final String bnodeId = entity.getBnodeId().toString();
					boolean flag = false;
					for(String nodeId : nodeIdArr){
						if(nodeId.equals(bnodeId)){
							flag = true;
							break;
						}
					}
					return flag;
				}
			});
		}
		
		if(null == bussTrans || bussTrans.size() == 0){
			bussTrans = getBussTransBydb(nodeIds);
		}
		return bussTrans;
	}

	/**
	 * 从数据库中加载路径列表数据
	 * @param nodeIds	业务节点ID
	 * @return
	 * @throws ServiceException
	 */
	private List<BussTransEntity> getBussTransBydb(String nodeIds)
			throws ServiceException {
		List<BussTransEntity> bussTrans;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		params.put("bnodeId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + nodeIds);
		bussTrans = bussTransService.getEntityList(params);
		BussTransCache.add(bussTrans);
		return bussTrans;
	}
	
	
	private Object[] getTransCfgDatas(List<BussTransEntity> bussTrans) throws ServiceException{
		Object[] cfgArr = new Object[2];
		StringBuffer sbBussTranIds = new StringBuffer(); 
		StringBuffer sbEnodeIds = new StringBuffer(); 
		for(BussTransEntity bussTran : bussTrans){
			Long bussTranId = bussTran.getId();
			Long enodeId = bussTran.getEnodeId();
			sbBussTranIds.append(bussTranId+",");
			sbEnodeIds.append(enodeId+",");
		}
		Map<Long,Map<String,String>> tranCfgMap = getTransCfgData(sbBussTranIds);
		cfgArr[0] = tranCfgMap;
		Map<Long,Map<String,Object>> nextNodeMap = getNextNodeData(sbEnodeIds);
		cfgArr[1] = nextNodeMap;
		return cfgArr;
	}

	private Map<Long,Map<String,Object>> getNextNodeData(StringBuffer sbEnodeIds) throws ServiceException {
		Map<Long,Map<String,Object>> tagNodeMap = null;
		if(null != sbEnodeIds && sbEnodeIds.length() > 0){
			String enodeIds = StringHandler.RemoveStr(sbEnodeIds);
			List<BussNodeEntity> bussNodeList = getBussNodeList(enodeIds);
			if(null != bussNodeList && bussNodeList.size() > 0){
				tagNodeMap = new HashMap<Long, Map<String,Object>>();
				for(BussNodeEntity bussNode : bussNodeList){
					Long nodeId = bussNode.getId();
					String nodeType = bussNode.getNodeType();
					Map<String,Object> valueMap = new HashMap<String, Object>();
					valueMap.put("nodeId", nodeId.toString());
					valueMap.put("activityId", bussNode.getNodeId());
					valueMap.put("nodeName", bussNode.getName());
					nodeType = getNodeType(bussNode);
					valueMap.put("nodeType", nodeType);
					Integer sign = getTagNodeSign(nodeId);
					valueMap.put("sign", sign);
					Map<String,JSONArray> forkJoinData = getNextForkTagNodes(nodeType, nodeId);
					if(null != forkJoinData && forkJoinData.size() > 0){
						valueMap.putAll(forkJoinData);
					}
					tagNodeMap.put(nodeId, valueMap);
				}
			}
		}
		return tagNodeMap;
	}

	/**
	 * 获取目标节点是否开启了补签
	 * @param nodeId	目标节点ID
	 * @return 返回是否补签
	 */
	private Integer getTagNodeSign(final Long nodeId){
		Integer sign = null;
		NodeCfgEntity nodeCfgObj = NodeCfgCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				NodeCfgEntity entity = (NodeCfgEntity)obj;
				return (entity.getNodeId().equals(nodeId));
		}});
		if(null != nodeCfgObj) sign = nodeCfgObj.getSign();
		return sign;
	}
	
	/**
	 * 获取并行节点类型
	 * @param nodeId
	 * @return
	 */
	private String getNodeTypeByCfg(final Long nodeId){
		NodeCfgEntity nodeCfgObj = NodeCfgCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				NodeCfgEntity entity = (NodeCfgEntity)obj;
				return entity.getNodeId().equals(nodeId);
			}
		});
		if(null == nodeCfgObj) return null;
		Integer eventType = nodeCfgObj.getEventType();
		if(null == eventType || eventType.intValue() == BussStateConstant.NODECFG_EVENTTYPE_1) return null;
		String nodeType = null;
		switch (eventType.intValue()) {
		case BussStateConstant.NODECFG_EVENTTYPE_2:
			nodeType = NODENAME_FORK;
			break;
		case BussStateConstant.NODECFG_EVENTTYPE_3:
			nodeType = NODENAME_JOIN;
			break;
		default:
			break;
		}
		return nodeType;
	}
	
	private List<BussNodeEntity> getBussNodeList(String enodeIds) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + enodeIds);
		List<BussNodeEntity> bussNodeList = BussNodeCache.getBussNodes(enodeIds);
		if(null == bussNodeList || bussNodeList.size() == 0){
			bussNodeList = bussNodeService.getEntityList(params);
			if(null != bussNodeList && bussNodeList.size() > 0) BussNodeCache.add(bussNodeList);
		}
		return bussNodeList;
	}

	private String getNodeType(BussNodeEntity bNode) throws ServiceException {
		Long nodeId = bNode.getId();
		String nodeType = bNode.getNodeType();
		String nodeName = bNode.getName();
		if(nodeType.equals("parallelGateway") && nodeName.equals(NODENAME_FORK)){//并行 Fork 节点类型
			nodeType = NODENAME_FORK;
		}else if(nodeType.equals("parallelGateway") && nodeName.equals(NODENAME_JOIN)){//并行 JOIN 节点类型
			nodeType = NODENAME_JOIN;
		}else if(nodeType.equals("endEvent") && isEndNode(nodeName,"endNode_names")){//结束 节点
			nodeType = NODENAME_END;
		}else if(nodeType.equals("serviceTask") && isEndNode(nodeName,"serviceTask_names")){//异常结束节点类型
			nodeType = NODENAME_SERVICE_END;
		}else{
			List<NodeCfgEntity> nodeCfgList = getNodeCfgList(nodeId.toString());
			NodeCfgEntity nodeCfg = (null == nodeCfgList || nodeCfgList.size() == 0) ? null : nodeCfgList.get(0);
			if(null == nodeCfg) return nodeType;
			Integer counterrsign = nodeCfg.getCounterrsign();
			if((null != counterrsign) && counterrsign.intValue() == NodeCfgEntity.COUNTERRSIGN_1){
				nodeType = NODENAME_COUNTERSIGN;
			}
		}
		String _nodeType = getNodeTypeByCfg(nodeId);
		if(StringHandler.isValidStr(_nodeType)) nodeType = _nodeType;
		return nodeType;
	}
	
	private boolean isEndNode(String nodeName,String resourceKey){
		boolean flag = false;
		String resVals = StringHandler.GetResValue(resourceKey);
		if(!StringHandler.isValidStr(resVals)) return false;
		resVals = resVals.toUpperCase();
		String[] ress = resVals.split(",");
		nodeName = nodeName.toUpperCase();
		for(String res : ress){
			if(nodeName.equals(res)) return true;
		}
		return flag;
	}
	
	private Map<String,JSONArray> getNextForkTagNodes(String nodeType,Long nodeId) throws ServiceException{
		if(!nodeType.equals(NODENAME_FORK) && !nodeType.equals(NODENAME_JOIN)) return null;
		 Map<String,JSONArray> tagNodesMap =  getNextTransInfos(nodeId.toString());
		return tagNodesMap;
	}
	
	private Map<Long,Map<String,String>> getTransCfgData(StringBuffer sbBussTranIds) throws ServiceException {
		Map<Long,Map<String,String>> transCfgMap = null;
		if(null != sbBussTranIds && sbBussTranIds.length() > 0){
			String bussTranIds = StringHandler.RemoveStr(sbBussTranIds);
			final String[] bussTranIdArr = bussTranIds.split(",");
			List<TransCfgEntity> transCfgList = null;
			if(null != bussTranIdArr && bussTranIdArr.length >0){
				transCfgList = TransCfgCache.getList(new ElementCompare() {
					@Override
					public boolean equals(Object obj) {
						TransCfgEntity entity = (TransCfgEntity)obj;
						boolean flag = false;
						for(String bussTranId : bussTranIdArr){
							if(entity.getTransId().toString().equals(bussTranId)){
								flag = true;
								break;
							}
						}
						return flag;
					}
				});
			}
		
			if(null == transCfgList || transCfgList.size() == 0){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
				params.put("transId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + bussTranIds);
				transCfgList = transCfgService.getEntityList(params);
				if(null != transCfgList && transCfgList.size() > 0) TransCfgCache.add(transCfgList);
			}
			if(null != transCfgList && transCfgList.size() > 0){
				 transCfgMap = getTransCfgMap(transCfgList);
			}
		}
		return transCfgMap;
	}

	/**
	 * 将 流转路径配置列表转换成 Map 对象并返回
	 * @param transCfgList	流转路径配置列表
	 * @return	返回  流转路径配置 Map 对象
	 */
	public Map<Long, Map<String, String>> getTransCfgMap(List<TransCfgEntity> transCfgList) {
		Map<Long, Map<String, String>> transCfgMap = new HashMap<Long, Map<String,String>>();
		for(TransCfgEntity transCfg : transCfgList){
			Long transId = transCfg.getTransId();
			Map<String,String> valueMap = new HashMap<String, String>();
			valueMap.put("acType", transCfg.getAcType().toString());
			valueMap.put("actorIds", transCfg.getActorIds());
			Integer stint = transCfg.getStint();
			valueMap.put("stint", (null == stint) ? null : stint.toString());
			valueMap.put("limitVals", transCfg.getLimitVals());
			Integer tpathType = transCfg.getTpathType();
			valueMap.put("tpathType", (null == tpathType) ? null : tpathType.toString());
			Integer tagWay = transCfg.getTagWay();
			valueMap.put("tagWay", (null == tagWay) ? null : tagWay.toString());
			transCfgMap.put(transId, valueMap);
			Integer tokenType = transCfg.getTokenType();
			valueMap.put("tokenType", (null == tokenType) ? null : tokenType.toString());
			valueMap.put("token", transCfg.getToken());
		}
		return transCfgMap;
	}
	
	/**
	 * 提交流程(创建会签任务)
	 * @param taskId 任务ID
	 * @param activityId 目标节点ID
	 * @param actorIds 下一步任务参与者列表 
	 * @param callback 业务回调对象
	 * @throws ServiceException 
	 * @throws BussFlowException 
	 */
	public String submitFlowByCountersign(SHashMap<String, Object> flowParams,BussFlowCallback callback) throws ServiceException, BussFlowException{
		String procId = flowParams.getvalAsStr("procId");
		String taskId = flowParams.getvalAsStr("taskId");
		String activityId  = flowParams.getvalAsStr("activityId");
		String actorIds = flowParams.getvalAsStr("actorIds");
		Integer bussType = flowParams.getvalAsInt("bussType");
		JSONArray nextTrans = (JSONArray)flowParams.get("nextTrans");
		
		Task task = workflowService.getTask(taskId);
		String executionId = task.getExecutionId();
		procId = task.getProcessInstanceId();
		String pdid = task.getProcessDefinitionId();
		setNextTransByTagWay(procId, nextTrans);
		
		//--> setp 1 : 提交任务<--//
		List<Task> currTasks = null;
		workflowService.complete(taskId,activityId);
		delTaskByDelAssigneeId();
		currTasks = countersignServiceHandler.createTasks(task,activityId, actorIds);
		setFlowModelInfo(bussType, callback, executionId, procId, pdid,currTasks);
		return procId;
	}
	
	/**
	 * 提交流程(创建会签任务)
	 * @param taskId 任务ID
	 * @param activityId 目标节点ID
	 * @param actorIds 下一步任务参与者列表 
	 * @param callback 业务回调对象
	 * @throws ServiceException 
	 * @throws BussFlowException 
	 */
	public String submitFlow(SHashMap<String, Object> flowParams,BussFlowCallback callback) throws ServiceException, BussFlowException{
		//--> setp 1 : 提交任务<--//
		String nodeType = flowParams.getvalAsStr("nodeType");
		JSONArray nextTrans = (JSONArray)flowParams.get("nextTrans");
		Integer bussType = flowParams.getvalAsInt(BussFlowService.BUSSTYPE_KEY);
		UserEntity currUser = (UserEntity)flowParams.get(SysConstant.USER_KEY);
		this.curruser = currUser;
		String taskId = flowParams.getvalAsStr("taskId");
		Task task = workflowService.getTask(taskId);
		String executionId = task.getExecutionId();
		String taskName = task.getName();
		String procId = task.getProcessInstanceId();
		String pdid = task.getProcessDefinitionId();
		
		setNextTransByTagWay(procId,nextTrans);
		
		List<Task> currTasks = null;
		if(nodeType.equals(NODENAME_FORK) && (null != nextTrans && nextTrans.size() > 1)){
			String forkJoinActivityId = flowParams.getvalAsStr("forkJoinActivityId");
			workflowService.complete(taskId, forkJoinActivityId);
			currTasks = makeForkTasks(nextTrans, procId);
			if(existToken(nextTrans)) makeToken(nextTrans, procId, null);
		}else if(nodeType.equals(NODENAME_SIGIN)){/*补签任务提交*/
			List<Task> delTasks = new ArrayList<Task>(1);
			delTasks.add(task);
			workflowService.deleteTasks(delTasks);
		}else{
			JSONObject nextTran = nextTrans.getJSONObject(0);
			String activityId = nextTran.getString("activityId");
			String actorId = getActors(nextTran, nodeType);
			
			boolean flag = false;/*目前复杂会签未实现，先走普通会签*/
			if(flag && (taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1 || taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1)){/*会签任务提交*/
				String resultTag = flowParams.getvalAsStr("resultTag");
				countersignServiceHandler.complete(task, activityId, actorId,resultTag, this.curruser);
			}else{
				if(!StringHandler.isValidStr(activityId) && (!StringHandler.isValidStr(actorId))){
					workflowService.complete(taskId);
				}else{
					boolean isCompleted = false;
					if(existToken(nextTrans)){
						isCompleted = makeToken(nextTrans, procId, taskId);
					}
					if(!isCompleted){
						if(!StringHandler.isValidStr(activityId)){
							workflowService.complete(taskId,actorId,null);
						}else{
							workflowService.complete(taskId, activityId,actorId,null);
						}
					}
				}
			}
			delTaskByDelAssigneeId();
			if(StringHandler.isValidStr(activityId)) currTasks = workflowService.getTaskListByTaskDefKey(procId, activityId);
			if((null == currTasks || currTasks.size() == 0) && StringHandler.isValidStr(actorId)) currTasks = workflowService.getTaskList(procId, actorId);
		}
		setFlowModelInfo(bussType, callback, executionId, procId, pdid,currTasks);
		return procId;
	}
	
	
	/**
	 * 提交流程
	 * @param taskId 任务ID
	 * @param activityId 目标节点ID
	 * @param actorIds 下一步任务参与者列表 
	 * @param callback 业务回调对象
	 * @throws ServiceException 
	 * @throws BussFlowException 
	 */
	public String submitFlow(String taskId,String activityId,String[] actorIds,Integer bussType,BussFlowCallback callback,SHashMap<String, Object> appendParams) throws ServiceException, BussFlowException{
		//--> setp 1 : 提交任务<--//
		UserEntity currUser = (UserEntity)appendParams.get(SysConstant.USER_KEY);
		this.curruser = currUser;
		Task task = workflowService.getTask(taskId);
		String executionId = task.getExecutionId();
		String taskName = task.getName();
		String procId = task.getProcessInstanceId();
		String pdid = task.getProcessDefinitionId();
		List<Task> currTasks = null;
		Task nextTask = null;
		boolean flag = false;/*目前复杂会签未实现，先走普通会签*/
		if(flag && (taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1 || taskName.indexOf(WorkFlowService.PARALLEL_CSTASK_PREFIX) != -1)){/*会签任务提交*/
			String actor = (null == actorIds || actorIds.length == 0) ? null : actorIds[0];
			String resultTag = appendParams.getvalAsStr("resultTag");
			nextTask = countersignServiceHandler.complete(task, activityId, actor,resultTag, this.curruser);
		}else{
			if(taskName.indexOf(WorkFlowService.RETROACTIVE_CSTASK_PREFIX) != -1){/*如果是补签任务，只需把当前任务删除即可*/
				List<Task> delTasks = new ArrayList<Task>(1);
				delTasks.add(task);
				workflowService.deleteTasks(delTasks);
			}else{
				if(!StringHandler.isValidStr(activityId) && (null == actorIds || actorIds.length == 0)){
					workflowService.complete(taskId);
				}else{
					String actor = actorIds[0];
					if(!StringHandler.isValidStr(activityId)){
						workflowService.complete(taskId,actor,null);
					}else{
						nextTask = workflowService.complete(taskId, activityId,actor,null);
					}
					
//						Map<String,Object> flowVariables = workflowService.getProcVariables(procId);
//						if(null == flowVariables || flowVariables.size() == 0) flowVariables = workflowService.getProcVariablesByExecutionId(executionId);
				}
				if(null != nextTask){
					currTasks = new ArrayList<Task>(1);
					currTasks.add(nextTask);
				}
			}
		}
		setFlowModelInfo(bussType, callback, executionId, procId, pdid,currTasks);
		return procId;
	}

	
	private void setFlowModelInfo(Integer bussType, BussFlowCallback callback,
			String executionId, String procId, String pdid, List<Task> currTasks)
			throws ServiceException, BussFlowException {
		boolean isEnd = false;
		List<Task> taskList = workflowService.getTaskList(procId);
		if(null == taskList || taskList.size() == 0) isEnd = true;
		//--> setp 2 : 获取缓存流程数据《创建或更新》 <--//
		BussFlowModel flowModel = cacheMgr.get(procId);
		
		if(!isEnd && null == flowModel){
			flowModel = createBussFlowModel(currTasks, bussType);
			cacheMgr.put(procId, flowModel);
		}else{
			if(!isEnd){
				if(null != currTasks && currTasks.size() > 0){
					this.updateFlowModel(currTasks, flowModel);
				}else{/*会签可能有的情况：[会签审批人提交意见时，如果所有参与人员没有全部批完的情况下,不会产生新的任务实例]*/
					Map<String,JSONObject> currTaskInfos =  flowModel.getCurrTaskInfos();
					String userId = curruser.getUserId().toString();
					if(null != currTaskInfos && currTaskInfos.containsKey(userId)) currTaskInfos.remove(userId);
				}
			}else{
				if(null == flowModel){//-->项目结束时，流程数据模型为空的处理
					flowModel = new BussFlowModel();
					flowModel.setPdid(pdid);
					flowModel.setProcId(procId);
					flowModel.setBussType(bussType);
				}
				flowModel.setProcEnd(isEnd);
			}
		}
		//--> setp 3 : 获取流程参数 <--//
		if(!isEnd){
			Map<String,Object> flowVariables = workflowService.getProcVariables(procId);
			if(null == flowVariables || flowVariables.size() == 0) flowVariables = workflowService.getProcVariablesByExecutionId(executionId);
			if(null != flowVariables && flowVariables.size() > 0){
				flowModel.setFlowVariables(flowVariables);
			}
		}
		
		//--> setp 4 : 执行业务回调 <--//
		if(null != callback){
			callback.execute(flowModel);
		}
		//--> setp 5 : 流程如果结束，或者当前任务为空，移除流程缓存数据<--//
		if(isEnd) cacheMgr.remove(procId);
	}
	
	/**
	 * 提交并行分支流程
	 * @param taskId 任务ID
	 * @param nodeType 目标节点类型
	 * @param nextMap 下一步的目标节点和参与者
	 * @param callback 业务回调对象
	 * @throws ServiceException 
	 * @throws BussFlowException 
	 */
	public String submitFlowByParallel(String taskId,String nodeType,Map<String,String> nextMap,Integer bussType,BussFlowCallback callback) throws ServiceException, BussFlowException{
		//--> setp 1 : 提交任务<--//
		Task task = workflowService.getTask(taskId);
		String executionId = task.getExecutionId();
		String procId = task.getProcessInstanceId();
		String pdid = task.getProcessDefinitionId();
		workflowService.complete(taskId);
		List<Task> currTasks = workflowService.getTaskList(procId);
		
		//--> setp 2 : 给新创建的并行任务节点设置参与者 <--//
		List<Task> delTasks = new ArrayList<Task>();
		for(Task currTask : currTasks){
			String taskName = currTask.getName();
			if(!nextMap.containsKey(taskName)){
				delTasks.add(currTask);
				 continue;
			}
			String actorId = nextMap.get(taskName);
			currTask.setAssignee(actorId);
			workflowService.saveTask(currTask);
		}
		/*如果是 JOIN 时，不能删除任务 ,如果一个起始节点有多个指向，例如：打回 和 有Fork 情况下,要删除自动为打回所创建的任务 */
		if(nodeType.equals(NODENAME_FORK) && (null != delTasks && delTasks.size() > 0)){
			workflowService.deleteTasks(delTasks);
		}
		setFlowModelInfo(bussType, callback, executionId, procId, pdid,currTasks);
		return procId;
	}
	
	/**
	 * 设置当前用户
	 * @param curruser
	 */
	public void setCurruser(UserEntity curruser) {
		this.curruser = curruser;
	}
	
	/**
	 * 业务类型 KEY 值
	 */
	public static final String BUSSTYPE_KEY = "BUSS_TYPE_KEY";
	/**
	 * 小额贷款业务主流程
	 */
	public static final int BUSS_TYPE = 1;
	/**
	 * 待删除的参与者ID标识
	 */
	public static final String DEL_ASSIGNEEID = "DEL_999999";
	/**
	 * 并行分支节点标识
	 */
	public static final String NODENAME_FORK = "FORK";
	/**
	 * 并行聚合节点标识
	 */
	public static final String NODENAME_JOIN = "JOIN";
	/**
	 * 补签节点标识
	 */
	public static final String NODENAME_SIGIN = "SIGIN";
	/**
	 * 结束节点标识
	 */
	public static final String NODENAME_END = "END";
	/**
	 * 异常结束节点标识
	 */
	public static final String NODENAME_SERVICE_END = "SERVICE_END";
	/**
	 * 会签节点标识
	 */
	public static final String NODENAME_COUNTERSIGN = "COUNTERSIGN";
	
}
