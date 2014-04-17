package com.cmw.action.sys;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.AuditAmountEntity;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.AuditAmountService;
import com.cmw.service.inter.sys.AuditRecordsService;
import com.cmw.service.inter.sys.BussNodeService;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.UserService;


/**
 * 审批记录表  ACTION类
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批记录表ACTION",createDate="2012-12-26T00:00:00",author="程明卫",defaultVals="sysAuditRecords_")
@SuppressWarnings("serial")
public class AuditRecordsAction extends BaseAction {
	@Resource(name="auditRecordsService")
	private AuditRecordsService auditRecordsService;
	@Resource(name="bussNodeService")
	private BussNodeService bussNodeService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	@Resource(name="auditAmountService")
	private AuditAmountService auditAmountService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 审批记录表 列表	
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			//nodeName,result,approval,userName,sex,deptName,createTime,auditAmountData{appAmount,rateType,rate,yearLoan,monthLoan,dayLoan}
			SHashMap<String, Object> map = getQParams("procId");
			String procId = map.getvalAsStr("procId");
			if(!StringHandler.isValidStr(procId)){
				result = ResultMsg.NODATA;
				outJsonString(result);
				return null;
			}else{
				map.put("procId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + procId);
			}
			
			List<AuditRecordsEntity> list = auditRecordsService.getEntityList(map);
			if(null == list || list.size() == 0){
				result = ResultMsg.NODATA;
			}else{
				String[] idsArr = getIdsArr(list);
				List<AuditAmountEntity> amountList = getAuditAmounts(idsArr[0]);
				Map<Long,String> nodesMap = getBussNodeNames(idsArr[1]);
				List<UserEntity> userList = getAuditUsers(idsArr[2]);
				Map<Long,String> deptsMap = getDepartments(userList);
				JSONArray jsonArr = new JSONArray();
				for(AuditRecordsEntity record : list){
					Long recordId = record.getId();
					JSONObject obj = new JSONObject();
					Long nodeId = record.getBussNodeId();
					if(nodesMap.containsKey(nodeId)){
						obj.put("nodeName", nodesMap.get(nodeId));
					}
					obj.put("result", record.getResult());
					obj.put("approval", record.getApproval());
					obj.put("createTime", DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, record.getCreateTime()));
					//--> 添加用户信息
					Long creator = record.getCreator();
					for(UserEntity userEntity : userList){
						Long userId = userEntity.getUserId();
						Long indeptId = userEntity.getIndeptId();
						if(creator.equals(userId)){
							String userName = userEntity.getEmpName();
							if(!StringHandler.isValidStr(userName)) userName = userEntity.getUserName();
							obj.put("userName", userName);
							obj.put("sex", userEntity.getSex());
							if(null != indeptId && null != deptsMap && deptsMap.containsKey(indeptId)){
								obj.put("deptName", deptsMap.get(indeptId));
							}
							break;
						}
					}
					
					//--> 添加审批金额信息
					if(null != amountList && amountList.size() > 0){
						for(AuditAmountEntity amountEntity : amountList){
							Long auditrecordId = amountEntity.getArecordId();
							if(recordId.equals(auditrecordId)){
								JSONObject auditAmountData = new JSONObject();
//								/appAmount,rateType,rate,yearLoan,monthLoan,dayLoan
								BigDecimal appAmount = amountEntity.getAppAmount();
								Integer rateType = amountEntity.getRateType();
								Double rate = amountEntity.getRate();
								Integer yearLoan = amountEntity.getYearLoan();
								Integer monthLoan = amountEntity.getMonthLoan();
								Integer dayLoan = amountEntity.getDayLoan();
								auditAmountData.put("appAmount", appAmount);
								auditAmountData.put("rateType", rateType);
								auditAmountData.put("rate", rate);
								auditAmountData.put("yearLoan", yearLoan);
								auditAmountData.put("monthLoan", monthLoan);
								auditAmountData.put("dayLoan", dayLoan);
								obj.put("auditAmountData", auditAmountData);
								break;
							}
						}
					}
					jsonArr.add(obj);
				}
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("list", jsonArr);
				jsonObj.put("size", jsonArr.size());
				jsonObj.put("success", "true");
				result = jsonObj.toJSONString();
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
	
	private String[] getIdsArr(List<AuditRecordsEntity> list){
		String[] idsArr = new String[3];
		StringBuffer sbRecordIds = new StringBuffer();
		StringBuffer sbNodeIds = new StringBuffer();
		StringBuffer sbUserIds = new StringBuffer();
		
		for(AuditRecordsEntity entity : list){
			sbRecordIds.append(entity.getId() + ",");
			sbNodeIds.append(entity.getBussNodeId() + ",");
			sbUserIds.append(entity.getCreator() + ",");
		}
		idsArr[0] = StringHandler.RemoveStr(sbRecordIds);
		idsArr[1] = StringHandler.RemoveStr(sbNodeIds);
		idsArr[2] = StringHandler.RemoveStr(sbUserIds);
		return idsArr;
	}
	
	private Map<Long,String> getBussNodeNames(String nodeIds) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put((String) "id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + nodeIds);
		List<BussNodeEntity> list = bussNodeService.getEntityList(map);
		Map<Long,String> nodesMap = new HashMap<Long, String>();
		for(BussNodeEntity entity : list){
			nodesMap.put(entity.getId(), entity.getName());
		}
		return nodesMap;
	}
	
	private List<UserEntity> getAuditUsers(String userIds) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put((String) "userId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + userIds);
		List<UserEntity> list = userService.getEntityList(map);
		return list;
	}
	
	private Map<Long,String> getDepartments(List<UserEntity> users) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		for(UserEntity user : users){
			if(null == user.getIndeptId()) continue;
			sb.append(user.getIndeptId()+",");
		}
		String deptIds = StringHandler.RemoveStr(sb);
		if(!StringHandler.isValidStr(deptIds)) return null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put((String) "id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + deptIds);
		List<DepartmentEntity> list = departmentService.getEntityList(map);
		Map<Long,String> deptsMap = new HashMap<Long, String>();
		for(DepartmentEntity entity : list){
			deptsMap.put(entity.getId(), entity.getName());
		}
		return deptsMap;
	}
	
	private List<AuditAmountEntity> getAuditAmounts(String recordIds) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put((String) "arecordId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + recordIds);
		List<AuditAmountEntity> list = auditAmountService.getEntityList(map);
		return list;
	}
	/**
	 * 获取 审批记录表 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AuditRecordsEntity entity = auditRecordsService.getEntity(Long.parseLong(id));
			result = JsonUtil.toJson(entity);
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
	 * 保存 审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			AuditRecordsEntity entity = BeanUtil.copyValue(AuditRecordsEntity.class,getRequest());
			auditRecordsService.saveOrUpdateEntity(entity);
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
	 * 新增  审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = auditRecordsService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("A", num);
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
	 * 删除  审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  审批记录表 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			auditRecordsService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 审批记录表 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AuditRecordsEntity entity = auditRecordsService.navigationPrev(params);
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
	 * 获取指定的 审批记录表 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			AuditRecordsEntity entity = auditRecordsService.navigationNext(params);
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
