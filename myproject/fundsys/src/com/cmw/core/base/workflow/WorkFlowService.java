package com.cmw.core.base.workflow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CommonDaoInter;

/**
 * 工作流业务类
 * @author chengmingwei
 *
 */
@Service(value="workflowService")
public class WorkFlowService {
	@Resource(name="repositoryService")
	RepositoryService repositoryService;
	
	@Resource(name="runtimeService")
	RuntimeService runtimeService;
	
	@Resource(name="taskService")
	TaskService taskService;
	
	@Resource(name="historyService")
	HistoryService historyService;
	
	@Resource(name="managementService")
	ManagementService managementService;
	
	@Autowired
	CommonDaoInter	commonDao;
	/**
	 * 发布 bpmn 流程文件
	 * 例：F:\\dev\\skythink\\resources\\activitis\\DemoProcess.bpmn
	 * @param xmlfilePath	流程文件路径 	例：F:\\dev\\skythink\\resources\\activitis\\DemoProcess.bpmn
	 * @return
	 */
	public Deployment deployXml(String xmlfilePath){
		InputStream ins = null;
		try {
			ins = new FileInputStream(xmlfilePath);
			Deployment deploy = repositoryService.createDeployment().addInputStream("xxxxx", ins).deploy();
			return deploy;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(null != ins)
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	/**
	 * 发布ZIP流程文件并返回 Deployment 对象
	 * @param zipFilePath	zip 文件路径
	 * @return 返回发布对象
	 */
	public Deployment deployZip(String zipFilePath){
		InputStream ins = null;
		try {
			ins = new FileInputStream(zipFilePath);
			return deployZip(ins);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(null != ins)
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	/**
	 * 发布一个ZIP流程文件包
	 * @param ins	输入流对象。该输入流必须是 zip 格式
	 * @return 返回发布对象
	 */
	public Deployment deployZip(InputStream ins) {
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(ins);
			Deployment deploy = repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
			return deploy;
		}finally{
			try {
				if(null != ins) ins.close();
				if(null != zipInputStream) zipInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据流程部署ID删除已部署的流程
	 * 将会删除  Deployment,ProcessDefintion,ProcessInstance,TaskInstance 等所有
	 * 根此部署相关的实例
	 * @param deploymentId	流程部署ID
	 */
	public void deleteDeployment(String deploymentId){
		repositoryService.deleteDeployment(deploymentId);
	}
	
	/**
	 * 根据流程部署ID获取所有 ProcessDefintion 对象
	 * @param deploymentId	流程部署ID
	 * @return	返回该部署流程下的所有流程定义对象列表
	 */
	public List<ProcessDefinition>  getProcDefByDeploymentId(String deploymentId){
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).list();
		return list;
	}
	
	/**
	 * 根据流程部署ID获取最后部署的那个 ProcessDefintion 对象
	 * @param deploymentId	流程部署ID
	 * @return	返回流程定义对象
	 */
	public ProcessDefinition  getLateProcDefByDeploymentId(String deploymentId){
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).orderByProcessDefinitionVersion().desc().list();
		return (null == list || list.size()==0) ? null : list.get(0);
	}
	
	/**
	 * 根据流程部署ID获取  ProcessDefintion 对象 的ID
	 * @param deploymentId	流程部署ID
	 * @return	返回流程定义对象ID
	 */
	public String  getProcDefIdByDeploymentId(String deploymentId){
		ProcessDefinition processDefinition = getLateProcDefByDeploymentId(deploymentId);
		return (null == processDefinition) ? null : processDefinition.getId();
	}
	
	/**
	 * 根据  processDefinitionId 获取所有 ProcessDefintion 对象
	 * @param processDefinitionId	流程部署ID
	 * @return	返回该部署流程下的所有流程定义对象列表
	 */
	public ProcessDefinition  getProcDef(String processDefinitionId){
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).list();
		return (null == list || list.size()==0) ? null : list.get(0);
	}
	
	/**
	 * 根据  processDefinitionId 获取 流程图片流对象
	 * @param processDefinitionId	流程部署ID
	 * @return	返回流程图片 InputStream 对象
	 */
	public InputStream  getProcDefImg(String processDefinitionId){
		InputStream ins = repositoryService.getProcessDiagram(processDefinitionId);
		return ins;
	}
	
	/**
	 * 进入节点的 Transition KEY 标识
	 */
	public static final String  KEY_COMETRANS = "comeTrans";
	/**
	 * 离开节点的  Transition KEY 标识
	 */
	public static final String  KEY_OUTTRANS = "outTrans";
	
	/**
	 * 根据流程定义ID 获取流程中的所有节点对象
	 * 以 List<Map<String,Object>> 对象返回。
	 * 每一个 Map 对象包含该节点属性和值的描述
	 * @param processDefinitionId
	 * @return  以 List<Map<String,Object>> 对象返回所有流程节点
	 */
	public List<Map<String,Object>> getNodesByProcDefId(String processDefinitionId){
		 List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<ActivityImpl> list = getActivitis(processDefinitionId);
		for(ActivityImpl activiti : list){
			Map<String,Object> map = activiti.getProperties();
			List<PvmTransition> comeTransList = activiti.getIncomingTransitions();
			map.put(KEY_COMETRANS, comeTransList);
			
			List<PvmTransition> outTransList = activiti.getOutgoingTransitions();
			map.put(KEY_OUTTRANS, outTransList);
			nodeList.add(map);
		}
		return nodeList;
	}
	
	/**
	 * 根据流程定义ID获取所有流程节点列表
	 * @param processDefinitionId	流程定义ID
	 * @return
	 */
	public List<ActivityImpl> getActivitis(String processDefinitionId) {
		ProcessDefinitionEntity processDefEntity = getProcDefEntity(processDefinitionId);
		processDefEntity.setKey(processDefinitionId);
		List<ActivityImpl> list = processDefEntity.getActivities();
		return list;
	}
	
	/**
	 * 根据流程定义ID和流程实例ID获取当前正在执行的流程节点信息列表
	 * @param processDefinitionId	流程定义ID
	 * @param procId 流程实例ID
	 * 
	 * @return
	 */
	public List<ActivityImpl> getExeActivitis(String processDefinitionId,String procId) {
		List<ActivityImpl> list = null;
		Map<String,Object> props = new HashMap<String, Object>();
		if(!StringHandler.isValidStr(procId)){/*表明流程实例未开始*/
			props.put("name", "Start");
			props.put("type", "startEvent");
			list = getActivitisByProps(processDefinitionId,props); /*返回开始节点信息*/
		}else{
//			HistoricProcessInstance hisProcessInstance = getHistoryProcessInstance(procId);
			 List<Task> tasks = getTaskList(procId);
			ProcessInstance processInstance = getProcessInstance(procId);
			boolean isEnd = (null == processInstance|| processInstance.isEnded());
			if(!isEnd && (null == tasks || tasks.size() == 0)){
				isEnd = true;
			}
			if(null != processInstance && isEnd){/*流程结束*/
				props.put("name", "End");
				props.put("type", "endEvent");
				list = getActivitisByProps(processDefinitionId,props); /*返回结束节点信息*/
			}else{/*流程正在执行*/
				 List<String> activityIds = new ArrayList<String>();
				 for(Task task : tasks){
					 String defKey = task.getTaskDefinitionKey();
					 if(!StringHandler.isValidStr(defKey)) continue;
					 activityIds.add(defKey);
				 }
				 list = getActivitis(processDefinitionId, activityIds);
			}
		}
		return list;
	}
	
	private List<ActivityImpl> getActivitisByProps(String processDefinitionId,Map<String,Object> props){
		List<ActivityImpl> returnList = new ArrayList<ActivityImpl>();
		ProcessDefinitionEntity processDefEntity = getProcDefEntity(processDefinitionId);
		List<ActivityImpl> list = processDefEntity.getActivities();
		Set<String> keys = props.keySet();
		for(ActivityImpl activity : list){
			boolean flag = false;
			for(String key : keys){
				Object eqval = props.get(key);
				Object val = activity.getProperty(key);
				flag = eqval.equals(val);
			}
			if(flag){
				returnList.add(activity);
			}
		}
		return returnList;
	}
	
	/**
	 * 根据流程定义ID获取流程定义实体对象
	 * @param processDefinitionId	流程定义ID
	 * @return 返回流程定义实体对象
	 */
	public ProcessDefinitionEntity getProcDefEntity(String processDefinitionId){
		RepositoryServiceImpl reImpl = (RepositoryServiceImpl)repositoryService;
		ProcessDefinitionEntity processDefEntity = (ProcessDefinitionEntity)(reImpl.getDeployedProcessDefinition(processDefinitionId));
		return processDefEntity;
	}
	/**
	 * 根据流程定义ID 和 activity ID 列表获取当前活动的节点列表
	 * @param processDefinitionId	流程定义ID
	 * @param activityIds   activity ID 列表
	 * @return
	 */
	public List<ActivityImpl> getActivitis(String processDefinitionId,List<String> activityIds) {
		List<ActivityImpl> list = getActivitis(processDefinitionId);
		List<ActivityImpl> returnList = new ArrayList<ActivityImpl>();
		for(ActivityImpl act : list){
			String id = act.getId();
			for(String activityId : activityIds){
				if(id.equals(activityId)){
					returnList.add(act);
					break;
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 根据流程定义ID 和 activity ID 列表获取当前活动的节点
	 * @param processDefinitionId	流程定义ID
	 * @param activityId   activity ID 
	 * @return 返回当前活动的节点
	 */
	public ActivityImpl getActivitiy(String processDefinitionId, String activityId) {
		List<ActivityImpl> list = getActivitis(processDefinitionId);
		ActivityImpl activity = null;
		for(ActivityImpl act : list){
			String id = act.getId();
			if(id.equals(activityId)){
				activity = act;
				break;
			}
		}
		return activity;
	}
	
	/**
	 * 根据流程实例ID获取已经结束的流程实例对象（历史流程实例）
	 * @param processInstanceIds	流程实例ID
	 * @return	返回历史流程实例对象
	 */
	public HistoricProcessInstance getHistoryProcessInstance(String processInstanceId){
		if(!StringHandler.isValidStr(processInstanceId)) return null;
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		return processInstance;
	}
	
	/**
	 * 根据流程实例ID字符串获取指定流程实例
	 * @param processInstanceIds	流程实例ID
	 * @return	返回流程实例对象
	 */
	public ProcessInstance getProcessInstance(String processInstanceId){
		if(!StringHandler.isValidStr(processInstanceId)) return null;
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		return processInstance;
	}
	
	/**
	 * 根据多个流程实例ID字符串获取指定流程实例ID
	 * @param processInstanceIds	流程实例ID字符串列表，如："1,2"
	 * @return	返回流程实例列表
	 */
	public List<ProcessInstance> getProcessInstances(String processInstanceIds){
		if(!StringHandler.isValidStr(processInstanceIds)) return null;
		Set<String> pIds = new HashSet<String>();
		String[] idsArr = processInstanceIds.split(",");
		List<String> ids = Arrays.asList(idsArr);
		pIds.addAll(ids);
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceIds(pIds).list();
		return list;
	}
	
	/**
	 * 根据流程定义ID启动流程实例对象，并返回该 ProcessInstance 对象
	 * @param processDefinitionId	流程定义ID
	 * @return	返回已启动的流程实例对象
	 */
	public ProcessInstance startProcessInstance(String processDefinitionId){
		return startProcessInstance(processDefinitionId, null);
	}
	
	/**
	 * 根据流程定义ID启动流程实例对象，并返回该 ProcessInstance 对象
	 * @param processDefinitionId	流程定义ID
	 * @param variables	开始流程时，附加的参数
	 * @return	返回已启动的流程实例对象
	 */
	public ProcessInstance startProcessInstance(String processDefinitionId,Map<String,Object> variables){
		return (null == variables || variables.size() == 0) ? runtimeService.startProcessInstanceById(processDefinitionId) : runtimeService.startProcessInstanceById(processDefinitionId, variables);
	}
	
	/**
	 * 根据流程实例ID获取所有待办任务
	 * @param processInstanceId	流程实例ID
	 * @return	返回该流程实例的待办任务列表
	 */
	public List<Task> getTaskList(String processInstanceId){
		List<Task> tasks = null;
		tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		return tasks;
	}
	
	/**
	 * 根据参与者ID获取所有待办任务
	 * @param taskAssignee	参与者ID
	 * @return	返回该用户的待办任务列表
	 */
	public List<Task> getTaskListByAssignee(String taskAssignee){
		List<Task> tasks = null;
		tasks = taskService.createTaskQuery().taskAssignee(taskAssignee).list();
		return tasks;
	}
	
	/**
	 * 根据流程实例ID和执行器ID获取所有待办任务
	 * @param processInstanceId	流程实例ID
	 * @param taskDefKey UserTask 节点ID
	 * @return	返回该流程实例的待办任务列表
	 */
	public List<Task> getTaskListByTaskDefKey(String processInstanceId,String taskDefKey){
		List<Task> tasks = null;
		tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefKey).list();
		return tasks;
	}
	
	/**
	 * 根据流程实例ID和流程节点ID获取没有配置参与者所有待办任务
	 * @param processInstanceId	流程实例ID
	 * @param 流程节点ID
	 * @return	返回该流程实例没有配置参与者的待办任务列表
	 */
	public List<Task> getTaskListByAssigneeIsNull(String processInstanceId,String taskDefKey){
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefKey).list();
		List<Task> noactorTasks = new ArrayList<Task>();
		if(null != tasks && tasks.size() > 0){
			for(Task task : tasks){
				String assignee = task.getAssignee();
				if(!StringHandler.isValidStr(assignee)) noactorTasks.add(task);
			}
		}
		return noactorTasks;
	}
	
	/**
	 * 根据任务ID获取任务对象
	 * @param taskId	任务ID
	 * @return	返回任务对象
	 */
	public Task getTask(String taskId){
		Task tasks = taskService.createTaskQuery().taskId(taskId).singleResult();
		return tasks;
	}
	
	/**
	 * 根据流程实例ID 和 当前任务处理人ID 获取所有待办任务
	 * @param processInstanceId	流程实例ID
	 * @param assignee 当前任务处理人
	 * @return	返回该流程实例的待办任务列表
	 */
	public List<Task> getTaskList(String processInstanceId,String assignee){
		List<Task> tasks = null;
		tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(assignee).list();
		return tasks;
	}
	
	
	/**
	 * 根据 流程实例 ID 获取流程变量
	 * @param processInstanceId 流程实例 ID
	 * @return
	 */
	public Map<String, Object> getProcVariables(String processInstanceId){
		List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processInstanceId).list();
		if(null == list || list.size() == 0) return null;
		Map<String, Object> variables = new HashMap<String, Object>();
		for (HistoricDetail historicDetail : list) {
		    HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
		    String name = variable.getVariableName();
		    Object  value =  variable.getValue();
		    System.out.println("variable: " + name + " = " +value);
		    variables.put(name, value);
		}
		return variables;
	}
	
	/**
	 * 根据 executionId 获取 流程实例对象  流程变量
	 * @param executionId 执行对象 ID
	 * @return
	 */
	public Map<String, Object> getProcVariablesByExecutionId(String executionId){
		if(!StringHandler.isValidStr(executionId)) return null;
		Map<String, Object> variables = runtimeService.getVariables(executionId);
		return variables;
	}
	

	/**
	 * 根据  taskId 获取 流程实例对象  流程变量
	 * @param taskId 任务实例ID
	 * @return 返回指定任务的流程变量
	 */
	public Map<String, Object> getTaskVariables(String taskId){
		if(!StringHandler.isValidStr(taskId)) return null;
		Map<String, Object> variables = taskService.getVariables(taskId);
		return variables;
	}
	
	/**
	 * 转交待办任务
	 * @param taskId 任务ID
	 * @param actor  任务接收者
	 * @return 返回转办的 Task
	 */
	public Task referredTask(String taskId, String actor){
		Task task = getTask(taskId);
		task.setAssignee(actor);
		taskService.saveTask(task);
		return task;
	}
	
	/**
	 * 保存任务
	 * @param task
	 */
	public void saveTask(Task task){
		taskService.saveTask(task);
	}
	
	/**
	 * 结束流程（删除流程实例）
	 * @param processInstanceId
	 * @param endReason
	 */
	public void endProcessInstance(String processInstanceId,String endReason){
		ProcessInstance procInstance = getProcessInstance(processInstanceId);
		if(null == procInstance) return;
		runtimeService.deleteProcessInstance(processInstanceId, endReason);
	}
	
	/**
	 * 挂起流程
	 * @param processInstanceId	流程实例ID
	 */
	public void suspensionProcInstance(String processInstanceId){
		runtimeService.suspendProcessInstanceById(processInstanceId);
	}
	
	/**
	 * 激活或恢复流程
	 * @param processInstanceId	流程实例ID
	 */
	public void activeProcInstance(String processInstanceId){
		runtimeService.activateProcessInstanceById(processInstanceId);
	}
	
	/**
	 * 获取执行器对象
	 * @param processInstanceId	流程实例ID
	 * @param activityId	userTask 节点ID
	 * @return
	 */
	public Execution getExecution(String processInstanceId,String activityId){
		Execution obj = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).activityId(activityId).singleResult();
		return obj;
	}
	
	/**
	 * 删除指定的任务列表
	 * @param delTasks 要删除的任务
	 */
	public void deleteTasks(List<Task> delTasks){
		if(null == delTasks || delTasks.size() == 0) return;
		int size = delTasks.size();
		List<String> taskIds = new ArrayList<String>(size);
		for(Task task : delTasks){
			taskIds.add(task.getId());
		}
		taskService.deleteTasks(taskIds, true);
	}
	
	/**
	 * 删除指定的任务列表
	 * @param taskIds 要删除的任务ID列表
	 */
	public void deleteTaskByIds(List<String> taskIds){
		if(null == taskIds || taskIds.size() == 0) return;
		taskService.deleteTasks(taskIds, true);
	}
	
	/**
	 * 串行会签任务名称前缀
	 */
	public static final String  SERIAL_CSTASK_PREFIX = "SERIAL_CSTASK_";
	/**
	 * 并行会签任务名称前缀
	 */
	public static final String  PARALLEL_CSTASK_PREFIX = "PARALLEL_CSTASK_";
	/**
	 * 补签任务名称前缀
	 */
	public static final String  RETROACTIVE_CSTASK_PREFIX = "RETROACTIVE_CSTASK_";
	/** 
     * 创建会签任务 (串行会签)
     * @param taskId  当前任务ID 
     * @param activityId 下一目标节点ID
     * @param actorList 会签人ID集合 
     * @throws Exception 
     */  
    public List<Task> createCounterTasks(Task currTask,String activityId, Map<String,String> actorIdMap) {
    	String processDefintionId = currTask.getProcessDefinitionId();
    	String processInstanceId = currTask.getProcessInstanceId();
    	delDefaultTasks(activityId, processInstanceId);
    	String parentTaskId = currTask.getId();
    	ActivityImpl tagActivity = getActivitiy(processDefintionId, activityId);
    	String newTaskName = (String)tagActivity.getProperty("name");
    	List<Task> tasks = new ArrayList<Task>();
    	Iterator<Entry<String, String>> it = actorIdMap.entrySet().iterator();
    	int taskCount = actorIdMap.size();
    	while (it.hasNext()) {
    		Entry<String,String> entry = it.next();
    		String actor = entry.getKey();
    		String orderNo = entry.getValue();
    		String taskName = SERIAL_CSTASK_PREFIX+actor+"_"+taskCount+"_"+orderNo;
    		TaskEntity task = (TaskEntity) taskService.newTask(UUID.randomUUID().toString());
            task.setAssignee(actor);  
            task.setName(taskName);
            task.setProcessDefinitionId(processDefintionId);  
            task.setProcessInstanceId(processInstanceId);  
            task.setParentTaskId(parentTaskId);
            task.setTaskDefinitionKey(activityId);
            task.setDescription(newTaskName+",taskName=["+taskName+"]");  
            taskService.saveTask(task);
            tasks.add(task);
    	}
        return tasks;
    }

    /**
     * 创建会签节点时，默认先删除系统自动创建的任务
     * @param activityId
     * @param processInstanceId
     * @param executionId
     * @return
     */
	private String delDefaultTasks(String activityId, String processInstanceId) {
		String executionId = null;
		List<Task> delTasks = getTaskListByAssigneeIsNull(processInstanceId,activityId);
    	if(null != delTasks && delTasks.size() > 0){
    		executionId = delTasks.get(0).getExecutionId();
    	}
    	if(null != delTasks) deleteTasks(delTasks);/*--删除由系统在会签节点上创建的任务实例--*/
		return executionId;
	}  
    
    /**
     * 根据指定的任务，创建同一任务节点上的新任务实例(多用于会签和并行)
     * @param currTask	当前任务
     * @param taskName	新任务的名称
     * @param assignee	参与者
     * @return 返回新创建的任务实例s
     */
    public Task createTask(Task currTask,String taskName,String assignee){
    	String processDefintionId = currTask.getProcessDefinitionId();
    	String processInstanceId = currTask.getProcessInstanceId();
    	String activityId = currTask.getTaskDefinitionKey();
    	String parentTaskId = currTask.getId();
    	String executionId = currTask.getExecutionId();
    	ActivityImpl tagActivity = getActivitiy(processDefintionId, activityId);
    	String newTaskName = (String)tagActivity.getProperty("name");
    	TaskEntity task = (TaskEntity) taskService.newTask();//UUID.randomUUID().toString()
        task.setAssignee(assignee);  
        task.setName(taskName);  
        task.setProcessDefinitionId(processDefintionId);  
        task.setProcessInstanceId(processInstanceId);  
        task.setExecutionId(executionId);
        task.setParentTaskId(parentTaskId); 
        task.setTaskDefinitionKey(activityId);
        task.setDescription(newTaskName+",taskName=["+taskName+"]");  
        taskService.saveTask(task);
        return task;
    }
    
    /**
     * 根据指定的任务，创建同一任务节点上的新任务实例(多用于会签和并行)
     * @param currTaskId	当前任务ID
     * @param assignee	参与者
     * @param taskDefKey 目标节点任务ID
     * @return 返回新创建的任务实例
     */
    public Task createTask(String currTaskId,String assignee,String taskDefKey){
//    public Task createTask(String currTaskId,String assignee,String taskDefKey){
    	Task currTask = getTask(currTaskId);
    	String processDefintionId = currTask.getProcessDefinitionId();
    	String processInstanceId = currTask.getProcessInstanceId();
    	String activityId = currTask.getTaskDefinitionKey();
    	String parentTaskId = currTask.getId();
    	String executionId = currTask.getExecutionId();
    	ActivityImpl tagActivity = getActivitiy(processDefintionId, activityId);
    	String newTaskName = (String)tagActivity.getProperty("name");
    	TaskEntity task = (TaskEntity) taskService.newTask();//UUID.randomUUID().toString()
        task.setAssignee(assignee);  
        task.setName(newTaskName);  
        task.setProcessDefinitionId(processDefintionId);  
        task.setProcessInstanceId(processInstanceId);  
        task.setExecutionId(executionId);
        task.setParentTaskId(parentTaskId); 
        task.setTaskDefinitionKey(taskDefKey);
        task.setDescription("taskName=["+newTaskName+"]");  
        taskService.saveTask(task);
        return task;
    }
    
    /**
     * 根据指定任务ID，创建目标节点上的新任务实例(并行)
     * @param currTaskId	当前任务ID
     * @param assignee	参与者
     * @param activityId 目标节点任务ID
     * @return 返回新创建的任务实例
     */
    public Task createTagTask(String currTaskId,String assignee,String activityId){
    	Task currTask = getTask(currTaskId);
        return createTagTask(currTask,assignee,activityId);
    }
    
    /**
     * 根据指定任务ID，创建目标节点上的新任务实例(并行)
     * @param currTaskId	当前任务ID
     * @param assignee	参与者
     * @param activityId 目标节点任务ID
     * @return 返回新创建的任务实例
     */
    public Task createTagTask(Task currTask,String assignee,String activityId){
    	String processDefintionId = currTask.getProcessDefinitionId();
    	String processInstanceId = currTask.getProcessInstanceId();
    	String parentTaskId = currTask.getId();
    	String executionId = currTask.getExecutionId();
    	Execution execution = getExecution(processInstanceId, activityId);
    	if(null != execution) executionId = execution.getId();
    	ActivityImpl tagActivity = getActivitiy(processDefintionId, activityId);
    	String newTaskName = (String)tagActivity.getProperty("name");
    	TaskEntity task = (TaskEntity) taskService.newTask();//UUID.randomUUID().toString()
        task.setAssignee(assignee);  
        task.setName(newTaskName);  
        task.setProcessDefinitionId(processDefintionId);  
        task.setProcessInstanceId(processInstanceId);  
        task.setExecutionId(executionId);
        task.setParentTaskId(parentTaskId); 
        task.setTaskDefinitionKey(activityId);
        task.setDescription("taskName=["+newTaskName+"]");  
        taskService.saveTask(task);
        return task;
    }
    
	/** 
     * 创建会签任务 (并行会签)
     * @param taskId  当前任务ID 
     * @param activityId 下一目标节点ID
     * @param actorList 会签人ID集合 
     * @throws Exception 
     */  
    public List<Task> createCounterTasks(Task currTask,String activityId, List<String> actorList) {
    	String processDefintionId = currTask.getProcessDefinitionId();
    	String processInstanceId = currTask.getProcessInstanceId();
    	delDefaultTasks(activityId, processInstanceId);
    	String praentTaskId = currTask.getId();
    	ActivityImpl tagActivity = getActivitiy(processDefintionId, activityId);
    	String newTaskName = (String)tagActivity.getProperty("name");
    	int taskCount = actorList.size();
    	 List<Task> tasks = new ArrayList<Task>();
    	 int orderNo = 0;
        for (String actor : actorList) {
        	orderNo++;
        	String taskName = PARALLEL_CSTASK_PREFIX+actor+"_"+taskCount+"_"+orderNo;
            TaskEntity task = (TaskEntity) taskService.newTask();
            task.setAssignee(actor);  
            task.setName(taskName);  
            task.setProcessDefinitionId(processDefintionId);  
            task.setProcessInstanceId(processInstanceId);  
            task.setParentTaskId(praentTaskId);
            task.setTaskDefinitionKey(activityId);
            task.setDescription(newTaskName+",taskName=["+taskName+"]");  
            taskService.saveTask(task);
            tasks.add(task);
        }
        return tasks;
    }
    
	
	/**
	 * 提交任务
	 * @param taskId 任务ID
	 */
	public void complete(String taskId){
		taskService.complete(taskId);
	}
	
	/**
	 * 提交任务
	 * @param taskId 任务ID
	 * @param variables	流转所带参数 Map<String,Object> 对象
	 */
	public void complete(String taskId,Map<String,Object> variables){
		taskService.complete(taskId,variables);
	}
	
	/**
	 * 提交任务
	 * @param taskId 任务ID
	 * @param nextAssignee 下一待办任务参与者
	 * @param variables	流转所带参数 Map<String,Object> 对象
	 */
	public Task complete(String taskId,String nextAssignee,Map<String,Object> variables){
		Task task = getTask(taskId);
		taskService.complete(taskId,variables);
		String processInstanceId = task.getProcessInstanceId();
		List<Task> nextTasks = getTaskList(processInstanceId);
		if(null == nextTasks || nextTasks.size() == 0) return null;
		Task nextTask = nextTasks.get(0);
		nextTask.setAssignee(nextAssignee);
		taskService.saveTask(nextTask);
		return nextTask;
	}
	
	
	/**
	 * 提交任务
	 * @param taskId 任务ID
	 * @param transName 流转路径
	 * @param variables	流转所带参数 Map<String,Object> 对象
	 */
	public void complete(String taskId,String activityId){
		Task task = getTask(taskId);
		commitProcess(task, null, activityId);
	}
	
	/**
	 * 提交任务
	 * @param taskId 任务ID
	 * @param activityId 目标节点ID
	 * @param 下一任务执行者
	 * @param variables	流转所带参数 Map<String,Object> 对象
	 * @param 返回下一任务对象
	 */
	public Task complete(String taskId,String activityId,String nextAssignee,Map<String,Object> variables){
		Task task = getTask(taskId);
		return complete(task,activityId, nextAssignee, variables);
	}

	/**
	 * 提交任务
	 * @param task 当前任务对象
	 * @param activityId 目标节点ID
	 * @param 下一任务执行者
	 * @param variables	流转所带参数 Map<String,Object> 对象
	 * @param 返回下一任务对象
	 */
	public Task complete(Task task,String activityId,String nextAssignee, Map<String, Object> variables) {
		ProcessInstance processInstance = getProcessInstance(task.getProcessInstanceId());
		if(processInstance.isEnded()) return null;
		String taskId = task.getId();
		if(null != variables && variables.size()>0){
			taskService.setVariables(taskId, variables);
		}
		commitProcess(task, variables, activityId);
		setExecutionIdByCountersign(task);
		/*------------ 获取下一步待办任务，并给待办任务设置参与者 -------------*/
		String processInstanceId = processInstance.getId();
		List<Task> list = getTaskList(processInstanceId);
		Task nextTask = null;
		for(Task ntask : list){
//			String taskName = ntask.getName();
			String defKey = ntask.getTaskDefinitionKey();
			String assignee = ntask.getAssignee();
			if(StringHandler.isValidStr(defKey) && defKey.equals(activityId) && !StringHandler.isValidStr(assignee)){
				nextTask = ntask;
				break;
			}
			/*else{
				if(StringHandler.isValidStr(defKey) && defKey.equals(activityId)){
					nextTask = ntask;
					break;
				}
			}*/
		}
		if(null == nextTask) return null;
		nextTask.setAssignee(nextAssignee);
		taskService.saveTask(nextTask);
		return nextTask;
	}
	
	public void setExecutionIdByCountersign(Task task){
		String taskName = task.getName();
		if(taskName.indexOf(PARALLEL_CSTASK_PREFIX) == -1 && taskName.indexOf(SERIAL_CSTASK_PREFIX) == -1) return;
		String processInstanceId = task.getProcessInstanceId();
		String activityId = task.getTaskDefinitionKey();
		String taskDefKey = task.getTaskDefinitionKey();
		//会签节点
		Execution executionObj = this.getExecution(processInstanceId, activityId);
		if(null == executionObj) return;
		String executionId = executionObj.getId();
		List<Task> taskList = this.getTaskListByTaskDefKey(processInstanceId, activityId);
		if(null == taskList || taskList.size() != 1) return;
		Task lastTask = taskList.get(0);
		if(!StringHandler.isValidStr(lastTask.getTaskDefinitionKey()) ||
			!taskDefKey.equals(lastTask.getTaskDefinitionKey())) return;
		String id = lastTask.getId();
		String sql = "UPDATE act_ru_task SET EXECUTION_ID_='"+executionId+"' WHERE ID_='"+id+"' ";
		try {
			commonDao.updateDatasBySql(sql);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	 /**  
     * 驳回流程  
     *   
     * @param taskId  
     *            当前任务ID  
     * @param activityId  
     *            驳回节点ID  
     * @param variables  
     *            流程存储参数  
     * @throws Exception  
     */    
    public void backProcess(Task task, String activityId,    
            Map<String, Object> variables) throws Exception {    
        if (!StringHandler.isValidStr(activityId)) {    
            throw new Exception("驳回目标节点ID为空！");    
        }    
        String processInstanceId = task.getProcessInstanceId();
        String taskDefKey = task.getTaskDefinitionKey();
        // 查找所有并行任务节点，同时驳回    
        List<Task> taskList = findTaskListByKey(processInstanceId, taskDefKey);    
        for (Task currtask : taskList) {    
            commitProcess(currtask, variables, activityId);    
        }    
    }  
    
    /**  
     * 根据流程实例ID和任务key值查询所有同级任务集合  
     *   
     * @param processInstanceId  
     * @param key  
     * @return  
     */    
    private List<Task> findTaskListByKey(String processInstanceId, String key) {    
        return taskService.createTaskQuery().processInstanceId(    
                processInstanceId).taskDefinitionKey(key).list();    
    }
    
    /**  
     * @param currtask  
     *            当前任务
     * @param variables  
     *            流程变量  
     * @param activityId  
     *            流程转向执行任务节点ID<br>  
     *            此参数为空，默认为提交操作  
     * @throws Exception  
     */    
    private void commitProcess(Task currtask, Map<String, Object> variables,String activityId){    
        if (variables == null) {    
            variables = new HashMap<String, Object>();    
        }    
        // 跳转节点为空，默认提交操作    
        if (!StringHandler.isValidStr(activityId)) {  
        	String taskId = currtask.getId();
            taskService.complete(taskId, variables);    
        } else {// 流程转向操作    
            turnTransition(currtask, activityId, variables);    
        }    
    }
    
    /**  
     * 流程转向操作  
     *   
     * @param currtask  
     *            当前任务
     * @param activityId  
     *            目标节点任务ID  
     * @param variables  
     *            流程变量  
     * @throws Exception  
     */    
    private void turnTransition(Task currtask, String activityId,    
            Map<String, Object> variables){
    	String taskId = currtask.getId();
        // 当前节点    
        ActivityImpl currActivity = findActivitiImpl(currtask, null);    
        // 清空当前流向    
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);    
    
        // 创建新流向    
        TransitionImpl newTransition = currActivity.createOutgoingTransition();    
        // 目标节点    
        ActivityImpl pointActivity = findActivitiImpl(currtask, activityId);    
        // 设置新流向的目标节点    
        newTransition.setDestination(pointActivity);    
    
        // 执行转向任务    
        taskService.complete(taskId, variables);    
        // 删除目标节点新流入    
        pointActivity.getIncomingTransitions().remove(newTransition);    
    
        // 还原以前流向    
        restoreTransition(currActivity, oriPvmTransitionList);    
    } 
    
    /**  
     * 清空指定活动节点流向  
     *   
     * @param activityImpl  
     *            活动节点  
     * @return 节点流向集合  
     */    
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {    
        // 存储当前节点所有流向临时变量    
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();    
        // 获取当前节点所有流向，存储到临时变量，然后清空    
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();    
        for (PvmTransition pvmTransition : pvmTransitionList) {    
            oriPvmTransitionList.add(pvmTransition);    
        }    
        pvmTransitionList.clear();    
    
        return oriPvmTransitionList;    
    }  
  
    
    /**  
     * 根据任务实例和节点ID获取活动节点 <br>  
     *   
     * @param currtask  任务实例对象  
     * @param activityId  
     *            活动节点ID <br>  
     *            如果为null或""，则默认查询当前活动节点 <br>  
     *            如果为"end"，则查询结束节点 <br>  
     *   
     * @return  
     * @throws Exception  
     */    
    private  ActivityImpl findActivitiImpl(Task currtask, String activityId){
    	String processDefinitionId = currtask.getProcessDefinitionId();
        // 取得流程定义    
        ProcessDefinitionEntity processDefinition = getProcDefEntity(processDefinitionId);
    
        // 获取当前活动节点ID    
        if (!StringHandler.isValidStr(activityId)) {    
            activityId = currtask.getTaskDefinitionKey();    
        }    

        // 根据流程定义，获取该流程实例的结束节点    
        if (activityId.toUpperCase().equals("END")) {    
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {    
                List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();    
                if (pvmTransitionList.isEmpty()) {    
                    return activityImpl;    
                }    
            }    
        }    
        
        // 根据节点ID，获取对应的活动节点    
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);    
    
        return activityImpl;    
    }
    
    /**  
     * 还原指定活动节点流向  
     *   
     * @param activityImpl  
     *            活动节点  
     * @param oriPvmTransitionList  
     *            原有节点流向集合  
     */    
    private void restoreTransition(ActivityImpl activityImpl,    
            List<PvmTransition> oriPvmTransitionList) {    
        // 清空现有流向    
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();    
        pvmTransitionList.clear();    
        // 还原以前流向    
        for (PvmTransition pvmTransition : oriPvmTransitionList) {    
            pvmTransitionList.add(pvmTransition);    
        }    
    }    
}
