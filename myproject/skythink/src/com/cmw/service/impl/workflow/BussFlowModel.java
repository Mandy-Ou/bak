package com.cmw.service.impl.workflow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 业务流程数据模型层
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class BussFlowModel implements Serializable{
	//流程定义ID
	private String pdid;
	//流程实例ID
	private String procId;
	//流程是否结束的标识
	private boolean procEnd = false;
	//业务类型 /* 1 : 小贷主业务流程, 2 : 提前还款*/
	private Integer bussType;
	// 存放当前任务信息  格式: key = 用户ID, value = taskId,nodeId,nodeName,taskNodeType[0:普通任务,1:会签任务,2:]
	private Map<String,JSONObject> currTaskInfos = new HashMap<String, JSONObject>();
	//当前节点配置  key = nodeId , value = NodeCfgEntity
	private Map<String,JSONObject> currNodeCfgs = new HashMap<String, JSONObject>();
	//下一步流转路径信息 key = nodeId , value = JSONArrary
	private Map<String,JSONArray> nextTransInfos = new  HashMap<String,JSONArray>();
	//存放与流程相关的所有流程参数
	private Map<String, Object> flowVariables = new HashMap<String, Object>();
	
	public String getPdid() {
		return pdid;
	}
	public void setPdid(String pdid) {
		this.pdid = pdid;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public Integer getBussType() {
		return bussType;
	}
	public void setBussType(Integer bussType) {
		this.bussType = bussType;
	}
	public Map<String, JSONObject> getCurrTaskInfos() {
		return currTaskInfos;
	}
	public void setCurrTaskInfos(Map<String, JSONObject> currTaskInfos) {
		this.currTaskInfos = currTaskInfos;
	}
	public Map<String, JSONObject> getCurrNodeCfgs() {
		return currNodeCfgs;
	}
	public void setCurrNodeCfgs(Map<String, JSONObject> currNodeCfgs) {
		this.currNodeCfgs = currNodeCfgs;
	}
	public Map<String, JSONArray> getNextTransInfos() {
		return nextTransInfos;
	}
	public void setNextTransInfos(Map<String, JSONArray> nextTransInfos) {
		this.nextTransInfos = nextTransInfos;
	}
	public boolean isProcEnd() {
		return procEnd;
	}
	public void setProcEnd(boolean procEnd) {
		this.procEnd = procEnd;
	}
	public Map<String, Object> getFlowVariables() {
		return flowVariables;
	}
	public void setFlowVariables(Map<String, Object> flowVariables) {
		this.flowVariables = flowVariables;
	}
	
	
}
