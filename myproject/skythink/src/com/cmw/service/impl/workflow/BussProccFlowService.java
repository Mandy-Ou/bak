package com.cmw.service.impl.workflow;


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
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.BussNodeService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.BussTransService;
import com.cmw.service.inter.sys.MsgNotifyService;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.UroleService;


/**
 * 通用业务流程  Service实现类
 * @author 程明卫
 * @date 2013-05-09 T00:00:00
 */
@SuppressWarnings("serial")
@Description(remark="通用业务流程  Service实现类",createDate="2013-05-09 T00:00:00",author="程明卫")
@Service("bussProccFlowService")
public class BussProccFlowService implements  Serializable {
	@Resource(name="bussflowService")
	private BussFlowService bussflowService;
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	@Resource(name="uroleService")
	private UroleService uroleService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="bussMakeService")
	private BussMakeService	bussMakeService;
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	@Resource(name="bussNodeService")
	private BussNodeService bussNodeService;
	@Resource(name="bussTransService")
	private BussTransService bussTransService;
	@Resource(name="msgNotifyService")
	private MsgNotifyService msgNotifyService;
	private UserEntity curruser;/*当前用户*/

	
	public void buildCache() throws ServiceException{
		List<String> processInstanceIds = bussMakeService.getProcessInstanceIds();
		bussflowService.buildCache(SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY, processInstanceIds);
	}
	
	/**
	 * 获取指定用户的待审批的流程实例ID(贷款申请审批功能)
	 * @param user	用户
	 * @return 返回待办流程实例ID列表
	 */
	public String getProcIdsByUser(UserEntity user){
		return bussflowService.getProcIdsByUser(SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY, user.getUserId().toString());
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
		BussFlowModel model = bussflowService.getFlowModel(user,procId,SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
		return model;
	}
	
	
	
	public Map<String, Object> startFlow(SHashMap<String, Object> map) throws ServiceException, BussFlowException {
		Long sysId = map.getvalAsLng("sysId");
		Long applyId = map.getvalAsLng("applyId");
		String bussProccCode = map.getvalAsStr("bussProccCode");
		UserEntity currUser = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		if(!StringHandler.isValidStr(bussProccCode)) throw new ServiceException("流程编号 \"bussProccCode\" 值不能为空!");
		if(!StringHandler.isValidObj(applyId)) throw new ServiceException("申请单ID参数 \"applyId\" 值不能为空!");
		
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		BussProccEntity bussProccEntity = null;
		Long bussProccId = null;
		params.put("sysId", sysId);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put("code", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + bussProccCode);
		bussProccEntity = bussProccService.getEntity(params);
		if(null != bussProccEntity){
			bussProccId = bussProccEntity.getId();
		}

		if(null == bussProccEntity) throw new ServiceException("没有配置编号为：\""+bussProccCode+"\"的业务流程，无法提交!");
		if(null == bussProccEntity.getPdid()) throw new ServiceException("编号为：\""+bussProccCode+"\"所对应的的业务流程没有配置流程文件，无法提交!");
		params.clear();
		params.put("formId", bussProccId);
		params.put("inType", 2);/* 1: 业务流程*/
		params.put("nodeType", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + "startEvent");
		BussNodeEntity bnodeEntity = bussNodeService.getEntity(params);
		String pdid = bussProccEntity.getPdid();
		Long nodeId = bnodeEntity.getId();
		params.clear();
		params.put("bnodeId", nodeId);
		BussTransEntity entity = bussTransService.getEntity(params);
		String resultOp = (null != entity) ? entity.getName() : "开始流程";
		//map.clear();
		String nextTrans = "[{actorId:\""+currUser+"\"}]";
		map.put("bussProccCode", bussProccCode);
		map.put("applyId", applyId.toString());
		map.put("pdid", pdid);
		map.put("nodeId", nodeId);
		map.put("nextTrans", nextTrans);
		map.put("result", resultOp);
		//"sysId,pdid,applyId,nodeId,taskId,result,nodeType,forkJoinActivityId,nextTrans,"
		Map<String, Object> appendParams = submitFlow(map);
		return appendParams;
	}
	
	private Map<String, Object> submitFlow(SHashMap<String, Object> map)
			throws ServiceException, BussFlowException {
		UserEntity currUser = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		Long applyId = map.getvalAsLng("applyId");
		AuditRecordsEntity recordEntity = getAuditRecordsEntity(map);
		Map<String,Object> submitData = new HashMap<String, Object>();
		submitData.put(SysConstant.USER_KEY, currUser);
		submitData.put("pdid", map.get("pdid"));
		submitData.put("applyId", applyId);
		submitData.put("bussProccCode", map.get("bussProccCode"));
		submitData.put("taskId", map.get("taskId"));
		submitData.put("nodeType", map.get("nodeType"));
		submitData.put("forkJoinActivityId", map.get("forkJoinActivityId"));
		submitData.putAll(map.getMap());
		String nextTransStr = map.getvalAsStr("nextTrans");
		JSONArray nextTrans = FastJsonUtil.convertStrToJSONArr(nextTransStr);
		submitData.put("nextTrans", nextTrans);
		if(null != recordEntity){
			submitData.put("recordEntity", recordEntity);
		}
		
		setCurruser(currUser);
		String procId = submitFlow(submitData);
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
		UserEntity currUser = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		BeanUtil.setCreateInfo(currUser, auditRecordsEntity);
		return auditRecordsEntity;
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
		UserEntity currUser = (UserEntity)submitDatas.get(SysConstant.USER_KEY);
		Long userId = null;
		if(null != currUser){
			userId = currUser.getUserId();
		}
		String pdid = (String)submitDatas.get("pdid");
		String taskId = (String)submitDatas.get("taskId");
		String nodeType = (String)submitDatas.get("nodeType");
		String forkJoinActivityId = (String)submitDatas.get("forkJoinActivityId");
		JSONArray nextTrans = (JSONArray)submitDatas.get("nextTrans");
		if(null == nextTrans || nextTrans.size() == 0) throw new ServiceException(" nextTrans 不能为空!");
		SHashMap<String, Object> flowParams = new SHashMap<String, Object>();
		flowParams.put(SysConstant.USER_KEY, currUser);
		flowParams.put("pdid", pdid);
		flowParams.put("taskId", taskId);
		flowParams.put("nodeType", nodeType);
		flowParams.put("forkJoinActivityId", forkJoinActivityId);
		flowParams.put("nextTrans", nextTrans);
		flowParams.put(BussFlowService.BUSSTYPE_KEY, SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
		String actorIds = null;
		bussflowService.setCurruser(currUser);
		
		if(!StringHandler.isValidStr(taskId)){
			Map<String,Object> variables = new HashMap<String, Object>();
			if(null != userId){
				String roleName = getCurrRoleName(userId);
				variables.put("roleName", roleName);
			}
			variables.put(BussFlowService.BUSSTYPE_KEY, SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
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
				flowParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
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
	
	/**
	 * 判断当前用户的角色名
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	private String getCurrRoleName(Long userId) throws ServiceException{
		String roleName = null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", userId);
		List<UroleEntity> uroles = uroleService.getEntityList(map);
		if(null == uroles || uroles.size() == 0) return null;
		
		UroleEntity urole = uroles.get(0);
		RoleEntity roleEntity = roleService.getEntity(urole.getRoleId());
		if(null != roleEntity) roleName = roleEntity.getName();
		return roleName;
	}
	
	private String getActors(JSONArray nextTrans) throws ServiceException{
		JSONObject nextTran = nextTrans.getJSONObject(0);
		String actorId = nextTran.getString("actorId");
		//if(!StringHandler.isValidStr(actorId)) throw new ServiceException(" actorId 不能为空!");
		return actorId;
	}
	/**
	 * 
	 * @param submitDatas
	 * @param actorIds 接收消息的下一步参与者ID列表
	 * @return
	 * @throws ServiceException
	 */
	private BussFlowCallback getCallback(final Map<String,Object> submitDatas) throws ServiceException{
		Object applyIdObj = submitDatas.get("applyId");
		if(!StringHandler.isValidObj(applyIdObj)) throw new ServiceException(" applyId 不能为空!");
		final AuditRecordsEntity recordEntity = (AuditRecordsEntity)submitDatas.get("recordEntity");
		BussFlowCallback callback = new BussFlowCallback() {
			public Object execute(BussFlowModel flowModel) throws BussFlowException {
				boolean isEnd = flowModel.isProcEnd();
				int djzt = !isEnd ? BussStateConstant.BUSS_PROCC_DJZT_1 : BussStateConstant.BUSS_PROCC_DJZT_2;
				
				try {
					Map<String,Object> vars = flowModel.getFlowVariables();
					if(null != vars && vars.size() > 0){
						String _djzt = (String)vars.get("djzt");
						if(StringHandler.isValidStr(_djzt)) djzt = Integer.parseInt(_djzt);
					}
					
					/* step 1 : 保存审批记录 */
					String procId = flowModel.getProcId();
					recordEntity.setProcId(procId);
					auditRecordsService.saveEntity(recordEntity);
					
					/* step 3 : 更新业务申请单*/
					submitDatas.put("djzt", djzt);
					submitDatas.put("procId", procId);
					bussMakeService.doBuss(submitDatas);
				} catch (ServiceException e) {
					e.printStackTrace();
					throw new BussFlowException(e);
				}
				return null;
			}
		};
		return callback;
	}
	
	/**
	 * 设置当前用户
	 * @param curruser
	 */
	public void setCurruser(UserEntity curruser) {
		this.curruser = curruser;
		bussflowService.setCurruser(curruser);
	}
}
