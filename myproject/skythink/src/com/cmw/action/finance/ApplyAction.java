package com.cmw.action.finance;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CategoryEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.crm.GuaCustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.PayTypeEntity;
import com.cmw.entity.sys.FieldPropEntity;
import com.cmw.entity.sys.FieldValEntity;
import com.cmw.entity.sys.FormdiyEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.finance.FcFlowService;
import com.cmw.service.inter.crm.CategoryService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.crm.GuaCustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.PayTypeService;
import com.cmw.service.inter.sys.FieldPropService;
import com.cmw.service.inter.sys.FieldValService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.FormdiyService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 贷款申请  ACTION类 
 * @author 程明卫
 * @date 2012-12-16T00:00:00
 * 	
 */
@Description(remark="贷款申请ACTION",createDate="2012-12-16T00:00:00",author="程明卫",defaultVals="fcApply_")
@SuppressWarnings("serial")
public class ApplyAction extends BaseAction {
	@Resource(name="applyService")
	private ApplyService applyService;
	@Resource(name="categoryService")
	private CategoryService categoryService;
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	@Resource(name="fcFlowService")
	private FcFlowService fcFlowService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	@Resource(name="guaCustomerService")
	private GuaCustomerService guaCustomerService;
	
	@Resource(name="formdiyService")
	private FormdiyService formdiyService;
	
	@Resource(name="fieldPropService")
	private FieldPropService fieldPropService;
	
	@Resource(name="fieldValService")
	private FieldValService fieldValService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 贷款申请 列表
	 * @return		
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			String  eqopLoanLimit = getVal("eqopLoanLimit");
			String  yearLoan = getVal("yearLoan");
			String qCmns = "name,cardType#I,cardNum,";//个人客户
			Integer custType = getIVal("custType");
			if(null == custType) throw new ServiceException("参数 custType[客户类型] 不能为空！");
			if(custType.intValue() == SysConstant.CUSTTYPE_1){//企业客户
				qCmns = "name,tradNumber,contactor,";
			}
			qCmns += "actionType#I,custType#I,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
					"eqopAmount,appAmount#O,payType,breed#L";
			SHashMap<String, Object> map = getQParams(qCmns);
			
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = applyService.getResultList(map,getStart(),getLimit());
			setloanType(dt);
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

	/**设置贷款方式
	 * @param dt
	 * @throws ServiceException
	 */
	private void setloanType(DataTable dt) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		params.put("id", null);
		
		if(dt != null && dt.getRowCount()>0){
			for(int i=0, count = dt.getRowCount();i<count;i++){
				String loanType = dt.getString(i, "loanType");
				params.remove("id");
				params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+loanType);
				List<GvlistEntity> gvlist = gvlistService.getEntityList(params);
				StringBuffer sbName = new StringBuffer();
				if(!gvlist.isEmpty() && gvlist.size()>0){
					for(GvlistEntity x : gvlist){
						String name = x.getName();
						sbName.append(name+",");
					}
					String loanTypeName  = StringHandler.RemoveStr(sbName);
					dt.setCellData(i, "loanType", loanTypeName);
				}
			}
		}
	}
	
	/**
	 * 获取 个人/企业贷款审批待办 列表
	 * @return		
	 * @throws Exception
	 */
	public String auditlist()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			String qCmns = "name,cardType#I,cardNum,";//个人客户
			Integer custType = getIVal("custType");
			if(null == custType) throw new ServiceException("参数 custType[客户类型] 不能为空！");
			if(custType.intValue() == SysConstant.CUSTTYPE_1){//企业客户
				qCmns = "name,tradNumber,contactor,";
			}
			qCmns += "custType#I,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
					"eqopAmount,appAmount#O,payType,breed#L";
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
			String procIds = fcFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = applyService.getResultList(map,getStart(),getLimit());
			setloanType(dt);
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
	 * 获取 个人/企业贷款审批历史 列表
	 * @return		
	 * @throws Exception
	 */
	public String audithostorys()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			
			String qCmns = "name,cardType#I,cardNum,";//个人客户
			Integer custType = getIVal("custType");
			if(null == custType) throw new ServiceException("参数 custType[客户类型] 不能为空！");
			if(custType.intValue() == SysConstant.CUSTTYPE_1){//企业客户
				qCmns = "name,tradNumber,contactor,";
			}
			qCmns += "custType#I,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
					"eqopAmount,appAmount#O,payType#I,breed#L";
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
			DataTable dt = applyService.getResultList(map,getStart(),getLimit());
			setloanType(dt);
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
	 * 获取 个人/企业客户贷款一览 列表
	 * @return		
	 * @throws Exception
	 */
	public String alllist()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			
			String qCmns = "name,cardType#I,cardNum,";//个人客户
			Integer custType = getIVal("custType");
			if(null == custType) throw new ServiceException("参数 custType[客户类型] 不能为空！");
			if(custType.intValue() == SysConstant.CUSTTYPE_1){//企业客户
				qCmns = "name,tradNumber,contactor,";
			}
			qCmns += "custType#I,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
					"eqopAmount,appAmount#O,payType#I,breed#L,actionType#I";
			SHashMap<String, Object> map = getQParams(qCmns);
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
			DataTable dt = applyService.getResultList(map,getStart(),getLimit());
			setloanType(dt);
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
	 * 获取 贷款申请修改数据
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			final String applyId = id;
			ApplyEntity entity = applyService.getEntity(Long.parseLong(id));
			Long manager = entity.getManager();
			final String gName = getGuaCustomet(entity);
			final UserEntity managerObj = userService.getEntity(manager);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("applyId", applyId);
					Date appdate = (Date)jsonObj.get("appdate");
					jsonObj.put("appdate", DateUtil.dateFormatToStr(appdate));
					String managerName = null;
					if(null != managerObj){
						managerName = managerObj.getEmpName();
						if(!StringHandler.isValidStr(managerName)) managerName = managerObj.getUserName();
					}
					jsonObj.put("managerName", managerName);
					jsonObj.put("GuaId", gName);
				}
			});
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
	 * 获取 贷款申请 详情 （业务审批页面）
	 * @return	
	 * @throws Exception
	 */
	public String detail()throws Exception {
		try {
			final String formdiy_id = getVal("formdiy_id");
			Long applyId = getLVal("applyId");
			if(!StringHandler.isValidObj(applyId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(applyId == -1) {
				return null;
			}
			final ApplyEntity entity = applyService.getEntity(applyId);
			Long breed = entity.getBreed();
			final VarietyEntity varietyEntity = varietyService.getEntity(breed);
			Long manager = entity.getManager();
			final UserEntity managerEntity = userService.getEntity(manager);
			final Map<String, Object> custMap = getCustomerByCustType(entity);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("code", entity.getPayType());
			final PayTypeEntity payTypeEntity =  payTypeService.getEntity(map);
			String pdid = varietyEntity.getPdid();
			String procId = entity.getProcId();
			final String gName = getGuaCustomet(entity);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			String fielVal = "";
			if(StringHandler.isValidStr(formdiy_id)){
				params.put("recode", formdiy_id);
				params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				FormdiyEntity formdiyEntity = formdiyService.getEntity(params);
				if(formdiyEntity != null){
					params.clear();
					Long formdiyId = formdiyEntity.getId();
					params.put("formdiyId", formdiyId);
					params.put("fieldName", "comanager");
					params.put("formId", applyId);
					FieldValEntity fieldValEntity = fieldValService.getEntity(params);
					if(fieldValEntity!=null){
						fielVal = fieldValEntity.getVal();
					}
				}
			}
			final String val = fielVal;
			
			final JSONObject bussFormDatas = getBussFormDatas(applyId, pdid,procId);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.remove("datas");
					jsonObj.remove("fields");
					Date appdate = (Date)jsonObj.get("appdate");
					jsonObj.put("appdate", DateUtil.dateFormatToStr(appdate));
					if(null != varietyEntity){
						jsonObj.put("breedName", varietyEntity.getName());
						jsonObj.put("pdid", varietyEntity.getPdid());
					}
					if(null != managerEntity){
						String managerName = managerEntity.getEmpName();
						if(!StringHandler.isValidStr(managerName)) managerName = managerEntity.getUserName();
						jsonObj.put("managerName", managerName);
					}
					if(null != payTypeEntity){
						String payType = payTypeEntity.getName();
						jsonObj.put("payType", payType);
					}
					if(null != custMap && custMap.size() > 0){
						jsonObj.putAll(custMap);
					}
					if(null != bussFormDatas && bussFormDatas.size() > 0){/*流程业务表单*/
						jsonObj.put("bussFormDatas", bussFormDatas);
					}
						jsonObj.put("GuaId", gName);
						if(StringHandler.isValidStr(formdiy_id)){
							jsonObj.put("comanager", val);
						}
						
				}
			});
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
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	private String getGuaCustomet(final ApplyEntity entity)
			throws ServiceException {
		String guaId = entity.getGuaId();
		Integer custType = entity.getCustType();
		String guaName = "";
		StringBuffer sbName = new StringBuffer();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		if(StringHandler.isValidStr(guaId)){
			params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+guaId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
//			
//			switch (custType) {
//			case SysConstant.CUSTTYPE_0:
//			case SysConstant.CUSTTYPE_1:
				List<GuaCustomerEntity> guaCustomerEntityList = guaCustomerService.getEntityList(params);
				if(!guaCustomerEntityList.isEmpty() && guaCustomerEntityList.size()>0){
					for(GuaCustomerEntity guaCustomerEntit: guaCustomerEntityList){
						String name = guaCustomerEntit.getName();
						sbName.append(name+",");
					}
					
				}
//				break;
//			}
		}
		guaName = guaId+"##"+StringHandler.RemoveStr(sbName);
		final String gName  = guaName ;
		return gName;
	}
	
	/**
	 * 获取业务表单菜单数据
	 * @param id
	 * @param pdid
	 * @param procId
	 * @return
	 * @throws ServiceException
	 */
	private JSONObject getBussFormDatas(Long id, String pdid, String procId)
			throws ServiceException {
		SHashMap<String, Object> formParams = new SHashMap<String, Object>();
		formParams.put("pdid", pdid);
		formParams.put("procId", procId);
		formParams.put(SysConstant.USER_KEY, this.getCurUser());
		formParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_FINANCE_APPLY);
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_FINAPPLY);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	
	/**
	 * 获取 业务表单
	 * @return	
	 * @throws Exception
	 */
	public String getBussForms()throws Exception {
		try {
			String pdid =  getVal("pdid");
			String procId = getVal("procId");
			final JSONObject bussFormDatas = getBussFormDatas(null, pdid,procId);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("bussFormDatas", bussFormDatas);
			result = jsonObj.toJSONString();
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
	 * 根据申请单中的客户类型返回不同的客户
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	private Map<String, Object> getCustomerByCustType(ApplyEntity entity)
			throws ServiceException {
		Map<String,Object> custMap = new HashMap<String, Object>();
		Long customerId = entity.getCustomerId();
		if(entity.getCustType().intValue() == SysConstant.CUSTTYPE_0){
			 CustomerInfoEntity oneCustomer = customerInfoService.getEntity(customerId);
			 if(null == oneCustomer) return null;
			 custMap = FastJsonUtil.getMapByObject(oneCustomer, "baseId,name,sex,cardType,cardNum,maristal");
		}else{//企业客户
			 EcustomerEntity entCustomer = ecustomerService.getEntity(customerId);
			 if(null == entCustomer) return null;
			 custMap = FastJsonUtil.getMapByObject(entCustomer, "baseId,name,regcapital,currency,regaddress,kind");
		}
		return custMap;
	}
	
	/**
	 * 保存 贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ApplyEntity entity = BeanUtil.copyValue(ApplyEntity.class,getRequest());
			String code = entity.getCode();
			if(!StringHandler.isValidStr(code)){
				code = getCode();
			}else {
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("code", code);
				map.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				List<ApplyEntity>  applyEntityList = applyService.getEntityList(map);
				boolean flag = false;
				Long applyId = entity.getId();
				if(!applyEntityList.isEmpty() && applyEntityList.size()>0){
					for(ApplyEntity aEntity : applyEntityList){
						String existCode = aEntity.getCode();
						if(existCode.equals(code)){
							Long gid = aEntity.getId();
							if(gid != applyId){
								flag = true;
							}
							break;
						}
					}
				}
				if(flag){
					HashMap<String, Object> returnParams = new HashMap<String, Object>();
					returnParams.put("id", -1);
					result = ResultMsg.getFailureMsg(returnParams);
					outJsonString(result);
					return null;
				}
			}
			entity.setCode(code);
			String guaId = entity.getGuaId();
			if(StringHandler.isValidStr(guaId)){
				if(guaId.indexOf("##")!=-1){
					guaId = guaId.split("##")[0];
				}
			}
			entity.setGuaId(guaId);
			applyService.saveOrUpdateEntity(entity);
			updateCoMan(entity);
			Integer submitType = getIVal("submitType");
			Map<String,Object> appnendMap = new HashMap<String, Object>();
			if(null != submitType && submitType.intValue() == 1){
				appnendMap.put("applyId", entity.getId());
			}
			Long id = entity.getId();
			appnendMap.put("id", id);
			result = (null == appnendMap || appnendMap.size() == 0) ?
					ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS) :
					ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appnendMap);
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
	 * 更新共同借款人使其与该申请单关联
	 * @param applyEntity
	 * @throws ServiceException
	 */
	private void updateCoMan(ApplyEntity applyEntity) throws ServiceException{
		Long projectId = applyEntity.getId();
		Integer custType = applyEntity.getCustType();
		Integer category = 1;
		String uuid = getVal("uuid");
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("uuid", uuid);
		params.put("custType", custType);
		params.put("category", category);
		CategoryEntity coManEntity = categoryService.getEntity(params);
		if(null == coManEntity) return;
		coManEntity.setUuid(null);
		coManEntity.setProjectId(projectId);
		categoryService.updateEntity(coManEntity);
	}
	
	
	
	/**
	 * 新增  贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			String code = getCode();
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

	private String getCode() throws ServiceException {
		Long num = applyService.getMaxID();
		if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("A", num);
		return code;
	}
	
	
	/**
	 * 删除  贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  贷款申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			applyService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 贷款申请 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ApplyEntity entity = applyService.navigationPrev(params);
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
	 * 获取指定的 贷款申请 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ApplyEntity entity = applyService.navigationNext(params);
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
