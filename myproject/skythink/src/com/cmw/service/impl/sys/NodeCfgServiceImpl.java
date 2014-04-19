package com.cmw.service.impl.sys;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.NodeCfgDaoInter;
import com.cmw.entity.sys.CountersignCfgEntity;
import com.cmw.entity.sys.FormCfgEntity;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.NodeCfgCache;
import com.cmw.service.impl.workflow.BussFlowCacheManager;
import com.cmw.service.inter.sys.CountersignCfgService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.NodeCfgService;
import com.cmw.service.inter.sys.TransCfgService;


/**
 * 流程节点配置  Service实现类
 * @author 程明卫
 * @date 2012-12-05T00:00:00
 */
@Description(remark="流程节点配置业务实现类",createDate="2012-12-05T00:00:00",author="程明卫")
@Service("nodeCfgService")
public class NodeCfgServiceImpl extends AbsService<NodeCfgEntity, Long> implements  NodeCfgService {
	@Autowired
	private NodeCfgDaoInter nodeCfgDao;
	
	@Resource(name="transCfgService")
	private TransCfgService transCfgService;
	
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	
	@Resource(name="countersignCfgService")
	private CountersignCfgService countersignCfgService;
	
	
	private BussFlowCacheManager cacheMgr  = BussFlowCacheManager.getInstance();
	@Override
	public GenericDaoInter<NodeCfgEntity, Long> getDao() {
		return nodeCfgDao;
	}
	
	@Override
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
			UserEntity user = (UserEntity)complexData.get("user");
			
			//---> step 1  保存节点配置数据
			NodeCfgEntity nodeCfgEntity = saveNodeCfg(complexData,user);
			
			//---> step 2 保存会签配置数据
			saveCountersignCfg(complexData,user,nodeCfgEntity);
			
			String tranCfgs = (String)complexData.get("tranCfgs");
			String mustFormCfgs = (String)complexData.get("mustFormCfgs");
			String opFormCfgs = (String)complexData.get("opFormCfgs");
			Long nodeId = nodeCfgEntity.getId();
			//---> step 2  保存流转路径配置数据
			List<TransCfgEntity> transCfgList = FastJsonUtil.convertJsonToList(tranCfgs, TransCfgEntity.class);
			if(null != transCfgList && transCfgList.size() > 0){
				String transIds = getTransIds(transCfgList);
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("transId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + transIds);
				transCfgService.deleteEntitys(map);
				List<TransCfgEntity> del_transCfgList = new ArrayList<TransCfgEntity>();
				for(TransCfgEntity entity : transCfgList){
					if(null == entity.getAcType()){
						del_transCfgList.add(entity);
						continue;
					}
					Integer stint = entity.getStint();
					if(null == stint || stint.intValue() == TransCfgEntity.STINT_1){
						entity.setLimitVals(null);
					}else{
						String limitVals = entity.getLimitVals();
						if(StringHandler.isValidStr(limitVals) && limitVals.indexOf("##") != -1){
							String[] limitValsArr = limitVals.split("##");
							if(null != limitValsArr && limitValsArr.length >= 1) limitVals = limitValsArr[0];
							if(StringHandler.isValidStr(limitVals)) entity.setLimitVals(limitVals);
						}
					}
					BeanUtil.setCreateInfo(user,entity);
					entity.setNodeId(nodeId);
				}
				if(null != del_transCfgList && del_transCfgList.size() > 0){
					transCfgList.removeAll(del_transCfgList);
				}
				if(null != transCfgList && transCfgList.size() > 0){
					transCfgService.batchSaveEntitys(transCfgList);
				} 
			}
			
			//---> step 3  保存表单配置数据
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("nodeId", nodeId);
			formCfgService.deleteEntitys(params);
			List<FormCfgEntity> mustFormCfgList = FastJsonUtil.convertJsonToList(mustFormCfgs, FormCfgEntity.class);
			if(null != mustFormCfgList && mustFormCfgList.size() > 0){
				for(FormCfgEntity entity : mustFormCfgList){
					BeanUtil.setCreateInfo(user,entity);
					entity.setNodeId(nodeId);
					entity.setOpType(FormCfgEntity.OPTYPE_1);
				}
			}
			
			List<FormCfgEntity> opFormCfgList = FastJsonUtil.convertJsonToList(opFormCfgs, FormCfgEntity.class);
			if(null != opFormCfgList && opFormCfgList.size() > 0){
				for(FormCfgEntity entity : opFormCfgList){
					BeanUtil.setCreateInfo(user,entity);
					entity.setNodeId(nodeId);
					entity.setOpType(FormCfgEntity.OPTYPE_2);
				}
				if(null == mustFormCfgList || mustFormCfgList.size() == 0){
					mustFormCfgList = new ArrayList<FormCfgEntity>();
				}
				mustFormCfgList.addAll(opFormCfgList);
			}
			
			if(null != mustFormCfgList && mustFormCfgList.size() > 0){
				formCfgService.batchSaveEntitys(mustFormCfgList);
			}
			cacheMgr.updateCurrNodeCfgs(nodeCfgEntity);
	}
	
	private NodeCfgEntity saveNodeCfg(Map<String, Object> complexData,UserEntity user)throws ServiceException{
		NodeCfgEntity nodeCfgEntity = BeanUtil.copyValToEntity(complexData,user, NodeCfgEntity.class);
		Long nodeId = nodeCfgEntity.getNodeId();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("actionType", SysConstant.ACTION_TYPE_TRANSCFG_1);
		params.put("nodeId", nodeId);
		NodeCfgEntity oldNodeCfgEntity = getEntity(params);
		if(null != oldNodeCfgEntity){/*如果存在，就更新*/
			oldNodeCfgEntity.setNodeId(nodeCfgEntity.getNodeId());
			oldNodeCfgEntity.setTransWay(nodeCfgEntity.getTransWay());
			oldNodeCfgEntity.setEventType(nodeCfgEntity.getEventType());
			oldNodeCfgEntity.setSign(nodeCfgEntity.getSign());
			oldNodeCfgEntity.setCounterrsign(nodeCfgEntity.getCounterrsign());
			oldNodeCfgEntity.setIsTimeOut(nodeCfgEntity.getIsTimeOut());
			oldNodeCfgEntity.setTimeOut(nodeCfgEntity.getTimeOut());
			oldNodeCfgEntity.setBtime(nodeCfgEntity.getBtime());
			oldNodeCfgEntity.setReminds(nodeCfgEntity.getReminds());
			BeanUtil.setModifyInfo(user, oldNodeCfgEntity);
			nodeCfgEntity = oldNodeCfgEntity;
		}
		this.saveOrUpdateEntity(nodeCfgEntity);
		return nodeCfgEntity;
	}
	
	/**
	 * 保存会签配置
	 * @param complexData
	 * @param use
	 * @param nodeCfgId
	 * @throws ServiceException
	 */
	private void saveCountersignCfg(Map<String, Object> complexData,UserEntity user,NodeCfgEntity nodeCfgEntity)throws ServiceException{
		Integer counterrsign = nodeCfgEntity.getCounterrsign();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		Long nodeCfgId = nodeCfgEntity.getId();
		params.put("nodeId", nodeCfgId);
		CountersignCfgEntity oldCfgEntity = countersignCfgService.getEntity(params);
		if(null == counterrsign || counterrsign.intValue() == NodeCfgEntity.COUNTERRSIGN_0){
			if(null != oldCfgEntity) countersignCfgService.deleteEntity(oldCfgEntity.getId());
			return;
		}
		
		CountersignCfgEntity countersignCfgEntity = BeanUtil.copyValToEntity(complexData,user, CountersignCfgEntity.class);
		if(null != oldCfgEntity){/*如果存在，就更新*/
			oldCfgEntity.setCtype(countersignCfgEntity.getCtype());
			oldCfgEntity.setPdtype(countersignCfgEntity.getPdtype());
			oldCfgEntity.setVoteType(countersignCfgEntity.getVoteType());
			oldCfgEntity.setEqlogic(countersignCfgEntity.getEqlogic());
			oldCfgEntity.setEqval(countersignCfgEntity.getEqval());
			oldCfgEntity.setTransId(countersignCfgEntity.getTransId());
			oldCfgEntity.setUntransId(countersignCfgEntity.getUntransId());
			oldCfgEntity.setTransType(countersignCfgEntity.getTransType());
			oldCfgEntity.setAcway(countersignCfgEntity.getAcway());
			BeanUtil.setModifyInfo(user, oldCfgEntity);
			countersignCfgEntity = oldCfgEntity;
		}else{
			countersignCfgEntity.setNodeId(nodeCfgId);
		}
		countersignCfgService.saveOrUpdateEntity(countersignCfgEntity);
	}
	
	private String getTransIds(List<TransCfgEntity> list){
		StringBuffer sb = new StringBuffer();
		for(TransCfgEntity entity : list){
			sb.append(entity.getTransId()).append(",");
		}
		return StringHandler.RemoveStr(sb);
	}

	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		NodeCfgCache.removes(id.toString());
	}

	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		NodeCfgCache.puts(null);
	}

	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		NodeCfgCache.removes(StringHandler.join(ids));
	}

	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		NodeCfgCache.removes(ids);
	}

	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		NodeCfgCache.removes(id.toString());
	}

	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		NodeCfgCache.puts(null);
	}

	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		NodeCfgCache.removes(StringHandler.join(ids));
	}

	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		NodeCfgCache.removes(ids);	
	}

	@Override
	public Long saveEntity(NodeCfgEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		NodeCfgCache.add(obj);
		return id;
	}

	@Override
	public void saveOrUpdateEntity(NodeCfgEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			NodeCfgCache.update(obj);
		}else{
			NodeCfgCache.add(obj);
		}
	}

	@Override
	public void updateEntity(NodeCfgEntity obj) throws ServiceException {
		super.updateEntity(obj);
		NodeCfgCache.update(obj);
	}
	
	
}
