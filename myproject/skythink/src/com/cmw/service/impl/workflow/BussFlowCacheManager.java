package com.cmw.service.impl.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.entity.sys.NodeCfgEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.service.impl.cache.NodeCfgCache;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 业务流程缓存管理类
 * @author chengmingwei
 *
 */
public class BussFlowCacheManager {
	private Map<String,BussFlowModel> bussFlowMap = new HashMap<String,BussFlowModel>();
	
	private BussFlowCacheManager(){
	}
	
	public void put(String procId, BussFlowModel flowModel){
		bussFlowMap.put(procId, flowModel);
	}
	
	public BussFlowModel get(String procId){
		return bussFlowMap.get(procId);
	}
	
	public void remove(String procId){
		bussFlowMap.remove(procId);
	}
	
	/**
	 * 获取所有业务流程数据模型
	 * @return 返回 list 
	 */
	public List<BussFlowModel> getAllModels(){
		Collection<BussFlowModel> collections = bussFlowMap.values();
		if(null == collections || collections.size() == 0) return null;
		BussFlowModel[] arr = new BussFlowModel[0];
		arr = collections.toArray(arr);
		@SuppressWarnings("unchecked")
		List<BussFlowModel>  list = Arrays.asList(arr);
		return list;
	}
	
	/**
	 * 根据业务类型获取指定业务的业务流程数据模型
	 * @param bussType 业务类型
	 * @return 返回 list 
	 */
	public List<BussFlowModel> getModelsByBussType(Integer bussType){
		List<BussFlowModel>  list = getAllModels();
		
		if(null == list || list.size() == 0) return null;
		List<BussFlowModel>  returnList = new ArrayList<BussFlowModel>();
		for(BussFlowModel flowModel : list){
			Integer currBussType = flowModel.getBussType();
			if(null == currBussType || !currBussType.equals(bussType)) continue;
			returnList.add(flowModel);
		}
		return returnList;
	}
	
	/**
	 * 更新当前审批节点配置
	 * @param nodeCfgObj	节点配置对象参数
	 * @throws ServiceException 
	 */
	public void updateCurrNodeCfgs(NodeCfgEntity nodeCfgObj) throws ServiceException{
		String nodeId = nodeCfgObj.getNodeId().toString();
		List<BussFlowModel>  list = getAllModels();
		if(null == list || list.size() == 0) return;
		BussFlowService service = (BussFlowService)SpringContextUtil.getBean("bussflowService");
		for(int i=0,count=list.size(); i<count; i++){
			BussFlowModel model = list.get(i);
			Map<String, JSONObject> currNodeCfgs = model.getCurrNodeCfgs();
			Map<String, JSONObject> currTaskInfos = model.getCurrTaskInfos();
			if(null == currNodeCfgs || currNodeCfgs.size() == 0){
				if(!isAddCfg(currTaskInfos, nodeId)) continue;
				currNodeCfgs = new HashMap<String, JSONObject>();
				model.setCurrNodeCfgs(currNodeCfgs);
				setCurrNodeCfgs(model,nodeId, service, currNodeCfgs);
			}else{
				if(!currNodeCfgs.containsKey(nodeId)) continue;
				setCurrNodeCfgs(model,nodeId, service, currNodeCfgs); 
			}
		}
	}

	/**
	 * @param nodeId
	 * @param service
	 * @param currNodeCfgs
	 * @return
	 * @throws ServiceException
	 */
	private void setCurrNodeCfgs(BussFlowModel model,String nodeId,BussFlowService service, Map<String, JSONObject> currNodeCfgs)
			throws ServiceException {
		Map<String,JSONObject> newCurrNodeCfgs = service.getCurrNodeCfgs(nodeId);
		if(null != newCurrNodeCfgs){
			JSONObject currNodeCfgDatas = newCurrNodeCfgs.get(nodeId);
			if(null != currNodeCfgDatas){
				currNodeCfgs.put(nodeId, currNodeCfgDatas);	
			}
		}
		Map<String,JSONArray> newNextTransInfos = service.getNextTransInfos(nodeId);
		Map<String,JSONArray> nextTransInfos = model.getNextTransInfos();
		if(null != nextTransInfos && nextTransInfos.size() > 0){
			nextTransInfos.putAll(newNextTransInfos);
		}else{
			model.setNextTransInfos(newNextTransInfos);
		}
	}
	
	private boolean isAddCfg(Map<String, JSONObject> currTaskInfos,String nodeId){
		if(null == currTaskInfos || currTaskInfos.size() == 0) return false;
		Collection<JSONObject> taskInfos = currTaskInfos.values();
		if(null == taskInfos || taskInfos.size() == 0) return false;
		boolean flag = false;
		for(JSONObject taskInfo : taskInfos){
			String _nodeId = taskInfo.getString("nodeId");
			if(nodeId.equals(_nodeId)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 更新下一步流转参数配置
	 * @param transCfgs	新流转参数配置
	 * @throws ServiceException 
	 */
	public void updateNextTransInfos(List<TransCfgEntity> transCfgs) throws ServiceException{
		List<BussFlowModel>  list = getAllModels();
		if(null == list || list.size() == 0) return;
		BussFlowService service = (BussFlowService)SpringContextUtil.getBean("bussflowService");
//		Map<Long, Map<String, String>> transCfgMap = service.getTransCfgMap(transCfgs);
	
		Long nodeCfgId = transCfgs.get(0).getNodeId();
		NodeCfgEntity nodeCfgObj = NodeCfgCache.get(nodeCfgId);
		String nodeId = nodeCfgObj.getNodeId().toString();
		for(int i=0,count=list.size(); i<count; i++){
			BussFlowModel model = list.get(i);
			Map<String, JSONArray> nextTransInfos = model.getNextTransInfos();
			if(nextTransInfos.containsKey(nodeId)){
				nextTransInfos = service.getNextTransInfos(nodeId);
				model.setNextTransInfos(nextTransInfos);
				break;
			}
//			if(!nextTransInfos.containsKey(nodeId)) continue;
//			JSONArray nextTrans = nextTransInfos.get(nodeId);
//			if(null == nextTrans || nextTrans.size() == 0) continue;
//			for(int j=0,len=nextTrans.size(); j<len; j++){
//				JSONObject nextTranObj = nextTrans.getJSONObject(j);
//				Long _transId = nextTranObj.getLong("transId");
//				if(!transCfgMap.containsKey(_transId)) continue;
//				Map<String,String> transCfgData = transCfgMap.get(_transId);
//				nextTranObj.putAll(transCfgData);
//			}
		}
	}
	
	
	
	public int size(){
		return bussFlowMap.size();
	}
	
	public static BussFlowCacheManager getInstance(){
		return LazyHodler.INSTANCE;
	}
	
	private static final class LazyHodler{
		private static final BussFlowCacheManager INSTANCE = new BussFlowCacheManager();
	}
}
