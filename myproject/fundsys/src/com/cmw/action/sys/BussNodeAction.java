package com.cmw.action.sys;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.entity.IdBaseEntity;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.workflow.WorkFlowService;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.entity.sys.CountersignCfgEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.FormCfgEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.cache.CountersignCfgCache;
import com.cmw.service.impl.cache.RoleCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.sys.AttachmentService;
import com.cmw.service.inter.sys.BussNodeService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.BussTransService;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.MenuService;
import com.cmw.service.inter.sys.NodeCfgService;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.TransCfgService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 业务流程节点  ACTION类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务流程节点ACTION",createDate="2012-11-28T00:00:00",author="程明卫",defaultVals="sysBussNode_")
@SuppressWarnings("serial")
public class BussNodeAction extends BaseAction {
	@Resource(name="bussNodeService")
	private BussNodeService bussNodeService;
	
	@Resource(name="bussTransService")
	private BussTransService bussTransService;
	
	@Resource(name="varietyService")
	private VarietyService varietyService;
	
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	
	@Resource(name="attachmentService")
	private AttachmentService attachmentService;
	
	@Resource(name="workflowService")
	private WorkFlowService workflowService;
	
	@Resource(name="nodeCfgService")
	private NodeCfgService nodeCfgService;
	
	@Resource(name="transCfgService")
	private TransCfgService transCfgService;
	
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	private String pdid = null;

	
	private List<UserEntity> userList = null;
	
	private List<RoleEntity> roleList = null;
	
	private List<MenuEntity> menuList = null;
	
	/**
	 * 获取 业务流程节点 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = bussNodeService.getResultList(map,getStart(),getLimit());
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
	 * 获取 业务流程节点 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			//formId bussType
			Long formId = getLVal("formId");
			Integer inType = getIVal("inType");
			if(!StringHandler.isValidObj(formId)) throw new ServiceException(ServiceException.ID_IS_NULL);
		
			String pdid = "",name="",filePath="";
			if(null != inType && inType.intValue() == 1){	//业务品种
				VarietyEntity vEntity = varietyService.getEntity(formId);
				if(null != vEntity){
					pdid = vEntity.getPdid();
					name = vEntity.getName();
				}
			}else{
				BussProccEntity bEntity = bussProccService.getEntity(formId);
				if(null != bEntity){
					pdid = bEntity.getPdid();
					name = bEntity.getName();
				}
			}
			SHashMap<String, Object> _params = new SHashMap<String, Object>();
			_params.put("formId", formId);
			_params.put("formType", inType);
			AttachmentEntity attachment = attachmentService.getEntity(_params);
			if(null != attachment) filePath = attachment.getFilePath();
			
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("formId", formId);
			appendParams.put("pdid", pdid);
			appendParams.put("name", name);
			appendParams.put("filePath", filePath);
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
	 * 加载流程图
	 * @param pdid
	 */
	public String processImage(){
		BufferedOutputStream bios = null;
		try {
			String pdid = getVal("pdid");
			if(!StringHandler.isValidStr(pdid)) return null;
			InputStream ins = workflowService.getProcDefImg(pdid);
			BufferedInputStream bins = new BufferedInputStream(ins);
			bios = new BufferedOutputStream(getResponse().getOutputStream());
			byte[] data = new byte[1];
			while(-1 != bins.read(data)){	
				bios.write(data);
			}
			bios.flush();
			bins.close();
			bios.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前正在执行的流程节点位置信息[高亮显示正在执行的节点]
	 * @param 
	 */
	public String getExePostion(){
		try {
			String processDefinitionId = getVal("pdid");
			String procId = getVal("procId");
			if(!StringHandler.isValidStr(processDefinitionId)) throw new ServiceException("pdid 为空，无法获取节点定位信息！");
		
			Map<String,JSONArray> taskInfos = getTaskUserInfos(procId);
			List<ActivityImpl> nodeList = workflowService.getExeActivitis(processDefinitionId, procId);
			JSONArray arr = new JSONArray(nodeList.size());
			for(ActivityImpl ac : nodeList){
				JSONObject obj = new JSONObject();
				int x = ac.getX();
				int y = ac.getY();
				int width = ac.getWidth();
				int height = ac.getHeight();
				obj.put("x", x);
				obj.put("y", y);
				obj.put("width", width);
				obj.put("height", height);
				
				String nodeId = ac.getId();
				if(null != taskInfos && taskInfos.containsKey(nodeId)){
					obj.put("taskInfos", taskInfos.get(nodeId));
				}
//				String name = (String)ac.getProperty("name");
//				if(null != taskInfos && taskInfos.containsKey(name)){
//					obj.put("taskInfos", taskInfos.get(name));
//				}
				arr.add(obj);
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("datas", arr);
			result = ResultMsg.getSuccessMsg(appendParams);
		}catch (ServiceException ex){
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
	 * 获取当前执行节点的待办用户信息
	 * @param procId
	 * @return
	 * @throws ServiceException 
	 */
	private Map<String,JSONArray> getTaskUserInfos(String procId) throws ServiceException{
		Map<String,JSONArray> taskMap = new HashMap<String, JSONArray>();
		List<Task> list = workflowService.getTaskList(procId);
		if(null == list || list.size() == 0) return null;
		StringBuffer sb = new StringBuffer();
		for(Task task : list){
			String assignee = task.getAssignee();
			if(!StringHandler.isValidStr(assignee)) continue;
			sb.append(assignee).append(",");
		}
		String userIds = StringHandler.RemoveStr(sb);
		if(!StringHandler.isValidStr(userIds)) return null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		List<UserEntity> users = UserCache.getUsers(userIds);
		if(null == users || users.size() == 0){
			map.put("userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + userIds);
			users = userService.getEntityList(map);
		}
	
		if(null == users || users.size() == 0) return null;
		sb.delete(0, sb.length());
		for(UserEntity user : users){
			Long indeptId = user.getIndeptId();
			if(!StringHandler.isValidObj(indeptId)) continue;
			sb.append(indeptId).append(",");
		}
		List<DepartmentEntity> departments = null;
		String indeptIds = StringHandler.RemoveStr(sb);
		if(StringHandler.isValidStr(indeptIds)){
			map.clear();
			map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + indeptIds);
			departments = departmentService.getEntityList(map);
		}
		
		for(Task task : list){
			String userTaskNodeId = task.getTaskDefinitionKey();
//			String name = task.getName();
			String assignee = task.getAssignee();
			if(!StringHandler.isValidStr(assignee)) continue;
			String[] userAndDept = getUserAndDept(Long.parseLong(assignee), users, departments);
			JSONObject userInfo = new JSONObject();
			userInfo.put("userName", userAndDept[0]);
			userInfo.put("deptName", userAndDept[1]);
			JSONArray arr = null;
			if(taskMap.containsKey(userTaskNodeId)){
				arr = taskMap.get(userTaskNodeId);
				arr.add(userInfo);
			}else{
				arr = new JSONArray();
				arr.add(userInfo);
				taskMap.put(userTaskNodeId, arr);
			}
		}
		return taskMap;
	}
	
	private String[] getUserAndDept(Long assignee,List<UserEntity> users , List<DepartmentEntity> departments){
		for(UserEntity user : users){
			Long userId = user.getUserId();
			if(assignee.equals(userId)){
				String userName = user.getEmpName();
				if(!StringHandler.isValidStr(userName)) userName = user.getUserName();
				String deptName = "";
				Long indeptId = user.getIndeptId();
				if(null != indeptId){
					for(DepartmentEntity dept : departments){
						if(indeptId.equals(dept.getId())){
							deptName = dept.getName();
							break;
						}
					}
				}
				return new String[]{userName,deptName};
			}
		}
		return null;
	}
	
	/**
	 * 获取流程节点位置信息
	 * @param 
	 */
	public String getPostion(){
		try {
			Integer inType = getIVal("inType"); 
			Long formId = getLVal("formId");
			String pdid = getVal("pdid");
			if(!StringHandler.isValidStr(pdid)) throw new ServiceException("pdid 为空，无法获取节点定位信息！");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("inType", inType);
			map.put("formId", formId);
			
			List<ActivityImpl> nodeList = workflowService.getActivitis(pdid);
			List<BussNodeEntity> bussNodes = bussNodeService.getEntityList(map);
			List<BussTransEntity> bussTrans = bussTransService.getEntityList(map);
			
			List<NodeCfgEntity> nodeCfgs = nodeCfgService.getEntityList(map);
			String nodeIds = getNodeCfgIds(nodeCfgs);
			List<TransCfgEntity> transCfgs = null;
			List<FormCfgEntity> formCfgs = null;
			if(StringHandler.isValidStr(nodeIds)){
				map.clear();
				map.put("nodeIds", nodeIds);
				transCfgs = transCfgService.getEntityList(map);
				if(null != transCfgs && transCfgs.size() > 0) loadUserOrRoleList(transCfgs);
				formCfgs = formCfgService.getEntityList(map);
				if(null != formCfgs && formCfgs.size() > 0) loadMenuList(formCfgs);
			}
			
			int bussNodeSize = bussNodes.size();
			int nodeCfgSize = (null == nodeCfgs || nodeCfgs.size() == 0) ? 0 :  nodeCfgs.size();
			JSONArray arr = new JSONArray(nodeList.size());
			for(ActivityImpl ac : nodeList){
				String id = ac.getId();
				JSONObject obj = new JSONObject();
				int x = ac.getX();
				int y = ac.getY();
				int width = ac.getWidth();
				int height = ac.getHeight();
				obj.put("x", x);
				obj.put("y", y);
				obj.put("width", width);
				obj.put("height", height);
				for(int i=0; i<bussNodeSize; i++){
					BussNodeEntity bussNode = bussNodes.get(i);
					if(bussNode.getNodeId().equals(id)){
						Long nodeId = bussNode.getId();
						obj.put("type", bussNode.getNodeType());
						obj.put("nodeId", nodeId);
						obj.put("name", bussNode.getName());
						boolean isCfg = appendCfgDatas(nodeCfgs, formCfgs, nodeCfgSize, obj, nodeId);
						obj.put("isCfg", isCfg);
						appendTransCfgDatas(bussTrans, transCfgs, obj, nodeId);
						break;
					}
				}
				arr.add(obj);
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("datas", arr);
			result = ResultMsg.getSuccessMsg(appendParams);
		}catch (ServiceException ex){
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
	
	private void loadMenuList(List<FormCfgEntity> formCfgs) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		for(FormCfgEntity formCfg : formCfgs){
			Long custFormId = formCfg.getCustFormId();
			if(null == custFormId) continue;
			sb.append(custFormId+",");
		}
		if(null != sb && sb.length() > 0){
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			String menuIds = StringHandler.RemoveStr(sb);
			map.put("menuId", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+menuIds);
			menuList = menuService.getEntityList(map);
		}
	}
	
	private String getNodeCfgIds(List<NodeCfgEntity> nodeCfgs){
		StringBuffer sb = new StringBuffer();
		if(null == nodeCfgs || nodeCfgs.size() == 0) return null;
		for(NodeCfgEntity entity : nodeCfgs){
			sb.append(entity.getId()+",");
		}
		String nodeIds = (sb.length() == 0) ? null : StringHandler.RemoveStr(sb, ",");
		return nodeIds;
	}
	
	/**
	 * 加载用户和角色数据
	 * @param transCfgs
	 * @throws ServiceException 
	 */
	private void loadUserOrRoleList(List<TransCfgEntity> transCfgs) throws ServiceException{
		StringBuffer sbRoles = new StringBuffer();
		StringBuffer sbUsers = new StringBuffer();
		for(TransCfgEntity tcfg : transCfgs){
			Integer acType = tcfg.getAcType();
			String actorId = tcfg.getActorIds();
			if(!StringHandler.isValidStr(actorId)) continue;
			if(null != acType && acType.intValue() == TransCfgEntity.ACTYPE_1){
				sbRoles.append(actorId+",");
			}else if(null != acType && acType.intValue() == TransCfgEntity.ACTYPE_2){
				sbUsers.append(actorId+",");
			}
		}
		if(sbRoles.length()>0){
			String roleIds = StringHandler.RemoveStr(sbRoles);
			roleList = RoleCache.getRoles(roleIds);
			if(null == roleList || roleList.size() == 0){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+roleIds);
				roleList = roleService.getEntityList(params);
			}
		}
		
		if(sbUsers.length()>0){
			String userIds = StringHandler.RemoveStr(sbUsers);
			userList = UserCache.getUsers(userIds);
			if(null == userList || userList.size() == 0){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("userId", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+userIds);
				userList = userService.getEntityList(params);
			}
		}
	}
	
	/**
	 * 添加节点配置信息数据
	 * @param nodeCfgs
	 * @param nodeCfgSize
	 * @param obj
	 * @param nodeId
	 */
	private boolean appendCfgDatas(List<NodeCfgEntity> nodeCfgs, List<FormCfgEntity> formCfgs, int nodeCfgSize,JSONObject obj, Long nodeId) {
		boolean isCfg = false;
		if(nodeCfgSize > 0){
			for(int j=0; j<nodeCfgSize; j++){
				NodeCfgEntity cfgEntity = nodeCfgs.get(j);
				if(cfgEntity.getNodeId().equals(nodeId)){
					JSONObject nodecfgJson = this.getNodeCfg(cfgEntity);
					Integer counterrsign = cfgEntity.getCounterrsign();
					Long nodeCfgId = cfgEntity.getId();
					if(null != counterrsign && counterrsign.intValue() == NodeCfgEntity.COUNTERRSIGN_1){
						JSONObject countersignCfg = getCountersignCfg(nodeCfgId);
						if(null != countersignCfg) nodecfgJson.putAll(countersignCfg);
					}
					obj.put("nodecfg", nodecfgJson);
					appendFormCfgDatas(formCfgs, obj, nodeCfgId);
					isCfg = true;
					break;
				}
			}
		}
		return isCfg;
	}
	
	private JSONObject getNodeCfg(NodeCfgEntity cfgEntity){
		JSONObject cfg = FastJsonUtil.convertObjToJsonObj(cfgEntity);
		cfg.remove("creator");
		cfg.remove("createTime");
		cfg.remove("deptId");
		cfg.remove("empId");
		cfg.remove("datas");
		cfg.remove("fields");
		return cfg;
	}
	
	private JSONObject getCountersignCfg(final Long nodeCfgId){
		CountersignCfgEntity cfgEntity = CountersignCfgCache.get(new ElementCompare() {
			public boolean equals(Object obj) {
				CountersignCfgEntity entity = (CountersignCfgEntity)obj;
				return (nodeCfgId.equals(entity.getNodeId()));
			}
		});
		if(null == cfgEntity) return null;
		JSONObject cfg = FastJsonUtil.convertObjToJsonObj(cfgEntity);
		cfg.remove("id");
		cfg.remove("creator");
		cfg.remove("createTime");
		cfg.remove("deptId");
		cfg.remove("empId");
		cfg.remove("datas");
		cfg.remove("fields");
		return cfg;
	}
	
	
	private void appendFormCfgDatas(List<FormCfgEntity> formCfgs, JSONObject obj, Long nodeCfgId){
		if(null == formCfgs || formCfgs.size() == 0) return;
		JSONArray arrMust = new JSONArray();
		JSONArray arrOp = new JSONArray();
		for(FormCfgEntity formCfg : formCfgs){
			Long _nodeCfgId = formCfg.getNodeId();
			if(_nodeCfgId.equals(nodeCfgId)){
				Integer opType = formCfg.getOpType();
				JSONObject fobj = new JSONObject();
				////id,nodeId,custFormId,formName,funRights,orderNo
				Long custFormId = formCfg.getCustFormId();
				fobj.put("id", formCfg.getId());
				fobj.put("nodeId", _nodeCfgId);
				fobj.put("custFormId", custFormId);
				fobj.put("formName", getFormName(custFormId));
				fobj.put("funRights", formCfg.getFunRights());
				fobj.put("orderNo", formCfg.getOrderNo());
				if(null != opType && opType.equals(FormCfgEntity.OPTYPE_1)){
					arrMust.add(fobj);
				}else if(null != opType && opType.equals(FormCfgEntity.OPTYPE_2)){
					arrOp.add(fobj);
				}
			}
		}
		obj.put("mustFormCfgs", arrMust);	//必选业务表单
		obj.put("arrOp", arrOp);	//可选业务表单
	}
	
	/**
	 * 根据表单ID获取表单名称
	 * @param custFormId
	 * @return
	 */
	private String getFormName(Long custFormId){
		if(null == menuList || menuList.size() == 0) return null;
		String formName = null;
		for(MenuEntity menu : menuList){
			Long menuId = menu.getMenuId();
			if(custFormId.equals(menuId)){
				formName = menu.getName();
				break;
			}
		}
		return formName;
	}
	
	/**
	 * 添加流转配置信息数据
	 * @param nodeCfgs
	 * @param nodeCfgSize
	 * @param obj
	 * @param nodeId
	 * @throws ServiceException 
	 */
	private void appendTransCfgDatas(List<BussTransEntity> bussTrans,List<TransCfgEntity> transCfgs,JSONObject obj, Long nodeId) throws ServiceException {
		if(null == bussTrans || bussTrans.size() == 0) return;
		int count=bussTrans.size();
		int cfgCount = (null == transCfgs || transCfgs.size() == 0) ? 0 : transCfgs.size();
		JSONArray arr = new JSONArray(count);
		for(int i=0; i<count; i++){
			BussTransEntity bussTran = bussTrans.get(i);
			if(!bussTran.getBnodeId().equals(nodeId)) continue;
			JSONObject tranObj = new JSONObject();
			tranObj.put("transId", bussTran.getId());
			tranObj.put("tranName", bussTran.getName());
			if(cfgCount>0){
				Long transId = bussTran.getId();
				for(TransCfgEntity transCfg : transCfgs){
					if(transId.equals(transCfg.getTransId())){
						Integer acType = transCfg.getAcType();
						String actorIds = transCfg.getActorIds();
						tranObj.put("acType", acType);
						tranObj.put("acTypeName", getAcTypeName(acType));
						tranObj.put("actorIds", actorIds);
						String actorNames = getActorNamesByAcType(acType, actorIds);
						tranObj.put("actorNames", actorNames);
						tranObj.put("stint", transCfg.getStint());
						String limitVals = getLimitVals(transCfg);
						tranObj.put("limitVals", limitVals);
						tranObj.put("tpathType", transCfg.getTpathType());
						tranObj.put("tagWay", transCfg.getTagWay());
						tranObj.put("tokenType", transCfg.getTokenType());
						tranObj.put("token", transCfg.getToken());
					}
				}
			}
			arr.add(tranObj);
		}
		obj.put("trans", arr);
	}
	
	private String getLimitVals(TransCfgEntity transCfg) throws ServiceException{
		Integer stint = transCfg.getStint();
		String limitVals = transCfg.getLimitVals();
		if(!StringHandler.isValidStr(limitVals)) return "";
		if(null == stint || stint.intValue() == BussStateConstant.TRANSCFG_STINT_1) return limitVals;
		StringBuilder sb = new StringBuilder();
		switch (stint.intValue()) {
		case BussStateConstant.TRANSCFG_STINT_2:{
			List<RoleEntity> roleList = RoleCache.getRoles(limitVals);
			for(RoleEntity roleObj : roleList){
				String roleName = roleObj.getName();
				sb.append(roleName).append(",");
			}
			break;
		}case BussStateConstant.TRANSCFG_STINT_3:{
			List<UserEntity> userList = UserCache.getUsers(limitVals);
			for(UserEntity userObj : userList){
				String empName = userObj.getEmpName();
				sb.append(empName).append(",");
			}
			break;
		}default:
			break;
		}
		if(null != sb && sb.length() > 0) limitVals += "##"+StringHandler.RemoveStr(sb);
		return limitVals;
	}
	
	private String getAcTypeName(Integer acType){
		String acTypeName = null;
		if(null == acType){
			return acTypeName;
		}
		switch (acType) {
			case 0:
				acTypeName = TransCfgEntity.ACTYPE_0_NAME;
				break;
			case 1:
				acTypeName = TransCfgEntity.ACTYPE_1_NAME;
				break;
			case 2:
				acTypeName = TransCfgEntity.ACTYPE_2_NAME;
				break;
			case 3:
				acTypeName = TransCfgEntity.ACTYPE_3_NAME;
				break;
			case 4:
				acTypeName = TransCfgEntity.ACTYPE_4_NAME;
				break;
			case 5:
				acTypeName = TransCfgEntity.ACTYPE_5_NAME;
				break;
			default:
				break;
		}
		return acTypeName;
	}
	
	/**
	 * 根据参与者类型和参与者ID获取参与者名称列表
	 * @param acType	参与者类型
	 * @param actorIds 参与者ID
	 * @return 返回参与者名称列表
	 */
	private String getActorNamesByAcType(Integer acType, String actorIds){
		if(!StringHandler.isValidStr(actorIds)) return null;
		String[] actorIdArr = actorIds.split(",");
		StringBuffer sb = new StringBuffer();
		if(null != acType && acType.intValue() == TransCfgEntity.ACTYPE_1){//角色
			if(null == roleList || roleList.size() == 0) return null;
			for(String actorId : actorIdArr){
				for(RoleEntity role : roleList){
					if(actorId.equals(role.getId().toString())){
						sb.append(role.getName()+",");
						break;
					}
				}
			}
		}else if(null != acType && acType.intValue() == TransCfgEntity.ACTYPE_2){//用户
			if(null == userList || userList.size() == 0) return null;
			for(String actorId : actorIdArr){
				for(UserEntity user : userList){
					if(actorId.equals(user.getUserId().toString())){
						sb.append(user.getEmpName()+",");
						break;
					}
				}
			}
		}
		String actorNames = (null != sb && sb.length()>0) ? StringHandler.RemoveStr(sb) : null;
		return actorNames;
	}

	/**
	 * 保存 业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String filePath = getVal("filePath");
			List<ActivityImpl> list = deployProcess(filePath);
			saveDatas(list);
			Map<String,Object> attachParams = new HashMap<String, Object>();
			attachParams.put("pdid", pdid);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,attachParams);
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
	 * 保存节点信息数据
	 * @param list
	 * @throws ServiceException 
	 */
	private void saveDatas(List<ActivityImpl> list ) throws ServiceException{
		Integer inType = getIVal("inType");
		Long formId = getLVal("formId");
		UserEntity user = this.getCurUser();
		List<BussNodeEntity> bussNodes = new ArrayList<BussNodeEntity>();
		List<BussTransEntity> bussTrans = new ArrayList<BussTransEntity>();
		BussNodeEntity bussNode = null;
		BussTransEntity bussTran = null;
		for(ActivityImpl activity : list){
			String id = activity.getId();
			String name = (String)activity.getProperty("name");
			String type = (String)activity.getProperty("type");
			bussNode = new BussNodeEntity();
			bussNode.setInType(inType);
			bussNode.setFormId(formId);
			bussNode.setName(name);
			bussNode.setNodeId(id);
			bussNode.setPdid(this.pdid);
			bussNode.setNodeType(type);
			setCreateInfo(user,bussNode);
			bussNodes.add(bussNode);
		
			List<PvmTransition> transList = activity.getOutgoingTransitions();
			if(null == transList || transList.size() == 0) continue;
			for(PvmTransition tran : transList){
				String transName = (String)tran.getProperty("name");
				ActivityImpl sourceActivity = (ActivityImpl)tran.getSource();
				String bnodeId = sourceActivity.getId();
				ActivityImpl desActivity = (ActivityImpl)tran.getDestination();
				String enodeId = desActivity.getId();
				bussTran = new BussTransEntity();
				bussTran.setName(transName);
				bussTran.setExpress(bnodeId);
				bussTran.setExparams(enodeId);
				setCreateInfo(user,bussTran);
				bussTrans.add(bussTran);
			}
		}
		
		if(null == bussNodes || bussNodes.size() == 0) return;
		bussNodeService.batchSaveEntitys(bussNodes);	//批量保存业务节点数据
		
		if(null == bussTrans || bussTrans.size() == 0) return;
		for(BussTransEntity currBussTran : bussTrans){
			String _bnodeId = currBussTran.getExpress();
			String _enodeId = currBussTran.getExparams();
			for(BussNodeEntity currBussNode : bussNodes){
				Long bussNodeId = currBussNode.getId();
				String nodeId = currBussNode.getNodeId();
				if(!nodeId.equals(_bnodeId) && !nodeId.equals(_enodeId)) continue;
				if(nodeId.equals(_bnodeId)){
					currBussTran.setBnodeId(bussNodeId);
				}else if(nodeId.equals(_enodeId)){
					currBussTran.setEnodeId(bussNodeId);
				}
			}
			currBussTran.setExpress(null);
			currBussTran.setExparams(null);
		}
		bussTransService.batchSaveEntitys(bussTrans);//批量保存业务流程路径数据
		Long sysId = null;
		if(inType.intValue() == 1){//业务品种	//-->更新业务品种或子流程实体的 pdid
			VarietyEntity ventity = varietyService.getEntity(formId);
			ventity.setPdid(pdid);
			ventity.setModifier(user.getUserId());
			ventity.setModifytime(new Date());
			varietyService.saveOrUpdateEntity(ventity);
			sysId = ventity.getSysId();
		}else{//子业务流程
			BussProccEntity bentity = bussProccService.getEntity(formId);
			bentity.setPdid(pdid);
			bentity.setModifier(user.getUserId());
			bentity.setModifytime(new Date());
			bussProccService.saveOrUpdateEntity(bentity);
			sysId = bentity.getSysId();
		}
		
		saveAttachment(sysId);
	}
	
	/**
	 * 保存附件
	 * @param sysId
	 * @throws ServiceException
	 */
	private void saveAttachment(Long sysId) throws ServiceException{
		Integer formType = getIVal("inType");
		Long formId = getLVal("formId");
		String filePath = getVal("filePath");
		String custName = getVal("custName");
		Long fileSize = getLVal("size");
		Integer atype = 3;
		if(filePath.endsWith(".xml") || filePath.endsWith(".bpmn")){
			atype = 4;
		}
		
		AttachmentEntity entity = new AttachmentEntity();
		entity.setSysId(sysId);
		entity.setFormType(formType);
		entity.setFormId(formId.toString());
		entity.setAtype(atype);
		entity.setFileName(custName);
		entity.setFilePath(filePath);
		entity.setFileSize(fileSize);
		setCreateInfo(getCurUser(), entity);
		attachmentService.saveEntity(entity);
	}
	
	/**
	 * 设置创建用户信息
	 * @param user
	 * @param entity
	 */
	private void setCreateInfo(UserEntity user, IdBaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreator(user.getUserId());
		entity.setEmpId(user.getInempId());
		entity.setDeptId(user.getIndeptId());
		entity.setOrgid(user.getIncompId());
	}
	
	/**
	 * 发布流程并返回发布后的流程节点
	 * @param filePath
	 * @return
	 * @throws ServiceException
	 */
	private List<ActivityImpl> deployProcess(String filePath) throws ServiceException {
		filePath = FileUtil.getFilePath(getRequest(), filePath);
		boolean flag = filePath.endsWith(".zip") || filePath.endsWith(".xml") || filePath.endsWith(".bpmn") || filePath.endsWith(".bar");
		if(!flag) throw new ServiceException(ServiceException.PROCESS_FILES_ERROR);
		Deployment deployment = null;
		if(filePath.endsWith(".zip") || filePath.endsWith(".bar")){
			deployment = workflowService.deployZip(filePath);
		}else{
			deployment = workflowService.deployXml(filePath);
		}
		if(null == deployment) throw new ServiceException(ServiceException.PROCESS_DEPLOYMENT_FAILURE);
		String processDefinitionId = workflowService.getProcDefIdByDeploymentId(deployment.getId());
		this.pdid = processDefinitionId;
		List<ActivityImpl> list = workflowService.getActivitis(processDefinitionId);
		return list;
	}
	
	/**
	 * 保存 业务流程节点配置数据 
	 * @return
	 * @throws Exception
	 */
	public String saveCfgs()throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("nodeId#L,transType#I,counterrsign#I,isTimeOut#I,timeOut,btime,reminds#I,tranCfgs,mustFormCfgs,opFormCfgs");
			Map<String,Object> complexData = params.getMap();
			complexData.put("user", getCurUser());
			bussNodeService.doComplexBusss(complexData);
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
	 * 新增  业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = bussNodeService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("B", num);
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
	 * 删除  业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  业务流程节点 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			bussNodeService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 业务流程节点 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BussNodeEntity entity = bussNodeService.navigationPrev(params);
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
	 * 获取指定的 业务流程节点 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BussNodeEntity entity = bussNodeService.navigationNext(params);
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
