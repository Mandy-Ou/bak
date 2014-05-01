package com.cmw.service.impl.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.cache.ElementCompare;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.FormCfgDaoInter;
import com.cmw.entity.sys.FormCfgEntity;
import com.cmw.entity.sys.FormRecordsEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.FormCfgCache;
import com.cmw.service.impl.cache.NodeCfgCache;
import com.cmw.service.impl.finance.FcFlowService;
import com.cmw.service.impl.workflow.BussFlowModel;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.FormRecordsService;
import com.cmw.service.inter.sys.MenuService;
import com.cmw.service.inter.sys.NodeCfgService;


/**
 * 节点表单配置  Service实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="节点表单配置业务实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Service("formCfgService")
public class FormCfgServiceImpl extends AbsService<FormCfgEntity, Long> implements  FormCfgService {
	@Autowired
	private FormCfgDaoInter formCfgDao;
	@Resource(name="fcFlowService")
	private FcFlowService fcFlowService;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="formRecordsService")
	private FormRecordsService formRecordsService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	@Resource(name="nodeCfgService")
	private NodeCfgService nodeCfgService;
	@Override
	public GenericDaoInter<FormCfgEntity, Long> getDao() {
		return formCfgDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		FormCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		FormCfgCache.puts(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		FormCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		FormCfgCache.removes(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		FormCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		FormCfgCache.puts(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		FormCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		FormCfgCache.removes(ids);
	}
	@Override
	public Long saveEntity(FormCfgEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		FormCfgCache.add(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(FormCfgEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			FormCfgCache.update(obj);
		}else{
			FormCfgCache.add(obj);
		}
	}
	@Override
	public void updateEntity(FormCfgEntity obj) throws ServiceException {
		super.updateEntity(obj);
		FormCfgCache.update(obj);
	}
	
	@Override
	public List<Long> batchSaveEntitys(List<FormCfgEntity> objs)
			throws ServiceException {
		List<Long> idList = super.batchSaveEntitys(objs);
		FormCfgCache.removes(objs);
		FormCfgCache.add(objs);
		return idList;
	}
	@Override
	public JSONObject getBussFormDatas(SHashMap<String, Object> params)
			throws ServiceException {
		String pdid = params.getvalAsStr("pdid");
		String procId = params.getvalAsStr("procId");
		UserEntity currUser = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		Integer bussType = params.getvalAsInt("bussType");
		String userId = currUser.getUserId().toString();
		String bussCode = params.getvalAsStr("bussCode");
		String formId = params.getvalAsStr("formId");
		BussFlowModel model = getFlowModelByBussType(pdid, procId, currUser,bussType);
		if(null == model) return null;
		Map<String,JSONObject> currTaskInfos = model.getCurrTaskInfos();
		if(null == currTaskInfos || currTaskInfos.size() == 0) return null;
		
		SHashMap<String, Object> map = new SHashMap<String,Object>();
		String nodeId = getCurrNodeId(currTaskInfos, userId);
		if(null == nodeId) return null;
		NodeCfgEntity nodeCfgEntity = getNodeCfgEntity(map, nodeId);
		if(null == nodeCfgEntity) return null;
		Long nodeCfgId = nodeCfgEntity.getId();
		if(!StringHandler.isValidObj(nodeCfgId)) throw new ServiceException(" nodeCfgId 为空!");
		boolean isCurrUserMustForm = currTaskInfos.containsKey(userId);
		map.clear();
		List<FormCfgEntity> allForms = getFormCfgList(map, nodeCfgId);
		if(null == allForms || allForms.size() == 0) return null;
		//---> 找已经做过的业务表单记录
		List<FormRecordsEntity> formRecords = getFormRecords(nodeCfgId.toString(),userId,bussCode,formId);
		if(!isCurrUserMustForm) return null;
		JSONArray mustArr = new JSONArray();
		JSONArray mabeArr = new JSONArray();
		Map<Long,Map<String,String>> menusMap = getMenus(allForms);
		for(FormCfgEntity formCfgEntity : allForms){
			Integer opType = formCfgEntity.getOpType();
			Long custFormId = formCfgEntity.getCustFormId();
			String funRights = formCfgEntity.getFunRights();
			Long _nodeId = formCfgEntity.getNodeId();
			boolean finish = isFinish(userId, formRecords, formCfgEntity);
			JSONObject obj = new JSONObject();
			obj.put("id", custFormId);
			obj.put("nodeId", _nodeId);
			obj.put("finish", finish);/*是否已做完,从 ts_FormRecords 表单事项记录表中来判断*/
			if(null != menusMap && menusMap.containsKey(custFormId)){
				Map<String,String> menuDatas = menusMap.get(custFormId);
				obj.putAll(menuDatas);
			}
			obj.put("modDatas", funRights);/*按钮权限*/
			if(null != opType && opType.intValue() == FormCfgEntity.OPTYPE_1){
				mustArr.add(obj);
			}else{
				mabeArr.add(obj);
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mustForms", mustArr);
		jsonObject.put("mabeForms", mabeArr);
		return jsonObject;
	}
	
	private BussFlowModel getFlowModelByBussType(String pdid, String procId,
			UserEntity currUser, int bussType) throws ServiceException {
		BussFlowModel model = null;
		switch (bussType) {
		case SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY:
			model = (!StringHandler.isValidStr(procId)) ? fcFlowService.getTaskByPdid(currUser, pdid) : fcFlowService.getTask(procId);
			break;
		case SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY:
			model = (!StringHandler.isValidStr(procId)) ? bussProccFlowService.getTaskByPdid(currUser, pdid) : bussProccFlowService.getTask(procId);
			break;
		default:
			break;
		}
		return model;
	}
	
	/**
	 * 判断业务事项是否已被用户完成
	 * @param userId	
	 * @param formRecords	
	 * @param formCfgEntity	
	 * @return
	 */
	private boolean isFinish(String userId, List<FormRecordsEntity> formRecords,
			FormCfgEntity formCfgEntity) {
		boolean finish = false;
		if(null == formRecords || formRecords.size() == 0) return false;
//		Long cfgnodeId = formCfgEntity.getNodeId();
		Long custFormId = formCfgEntity.getCustFormId();
		for(FormRecordsEntity formRecord : formRecords){
//			Long _cfgnodeId = formRecord.getNodeId();
			Long _custFormId = formRecord.getCustFormId();
//			Long formUserId = formRecord.getUserId();
			if(custFormId.equals(_custFormId)){
				finish = true;
				break;
			}
		}
		return finish;
	}
	
	private String getCurrNodeId(Map<String, JSONObject> currTaskInfos,String userId) {
		JSONObject currTaskInfo =  currTaskInfos.get(userId);
		if(null == currTaskInfo || currTaskInfo.size() == 0){
			Set<String> keys = currTaskInfos.keySet();
			for(String key : keys){
				currTaskInfo = currTaskInfos.get(key);
				break;
			}
		}
		String nodeId = currTaskInfo.getString("nodeId");
		return nodeId;
	}
	
	private Map<Long,Map<String,String>> getMenus(List<FormCfgEntity> allForms) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		for(FormCfgEntity formCfgEntity : allForms){
			sb.append(formCfgEntity.getCustFormId()+",");
		}
		String menuIds = StringHandler.RemoveStr(sb);
		if(!StringHandler.isValidStr(menuIds)) return null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("menuId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + menuIds);
		List<MenuEntity> menus = menuService.getEntityList(map);
		 Map<Long,Map<String,String>> menMap = new HashMap<Long, Map<String,String>>();
		Map<String,String> menuItem = null;
		for(MenuEntity menu : menus){
			menuItem = new HashMap<String, String>();
			Long menuId = menu.getMenuId();
			String name = menu.getName();
			menuItem.put("text", name);
			String icon = menu.getIconCls();
			if(StringHandler.isValidStr(icon)) menuItem.put("icon", icon);
			String bicon = menu.getBiconCls();
			if(StringHandler.isValidStr(bicon)) menuItem.put("bicon", bicon);
			String jsArray = menu.getJsArray();
			menuItem.put("jsArray", jsArray);
			String params = menu.getParams();
			menuItem.put("params", params);
			Integer loadType = menu.getLoadType();
			if(null != loadType) menuItem.put("loadType", loadType.toString());
			menMap.put(menuId, menuItem);
		}
		return menMap;
	}
	
	private List<FormRecordsEntity> getFormRecords(String nodeId,String userId,String bussCode,String formId) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("nodeId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + nodeId);
		map.put("userId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + userId);
		map.put("bussCode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + bussCode);
		map.put("formId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + formId);
		List<FormRecordsEntity> formRecords = formRecordsService.getEntityList(map);
		return formRecords;
	}
	
	private List<FormCfgEntity> getFormCfgList(SHashMap<String, Object> params,final Long nodeCfgId) throws ServiceException {
		params.put("nodeIds", nodeCfgId);
		List<FormCfgEntity> allForms = FormCfgCache.getList(new ElementCompare() {
			@Override
			public boolean equals(Object obj) {
				FormCfgEntity entity = (FormCfgEntity)obj;
				return (entity.getNodeId().equals(nodeCfgId));
			}
		});
		if(null == allForms || allForms.size() == 0){
			allForms = formCfgService.getEntityList(params);
			if(null != allForms && allForms.size() > 0) FormCfgCache.add(allForms);
		}
		return allForms;
	}
	
	private NodeCfgEntity getNodeCfgEntity(SHashMap<String, Object> params,
			final String nodeId) throws ServiceException {
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		params.put("nodeId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + nodeId);
		NodeCfgEntity nodeCfgEntity = NodeCfgCache.get(new ElementCompare() {
			@Override
			public boolean equals(Object obj) {
				NodeCfgEntity entity = (NodeCfgEntity)obj;
				return (entity.getNodeId().toString().equals(nodeId));
			}
		});
		if(null == nodeCfgEntity){
			nodeCfgEntity = nodeCfgService.getEntity(params);
			if(null != nodeCfgEntity) NodeCfgCache.add(nodeCfgEntity);
		}
		return nodeCfgEntity;
	}
}
