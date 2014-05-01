package com.cmw.action.crm;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.crm.CustBlackEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.inter.crm.CustBaseService;
import com.cmw.service.inter.crm.CustBlackService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.sys.CityService;
import com.cmw.service.inter.sys.CountryService;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.ProvinceService;
import com.cmw.service.inter.sys.RegionService;
import com.cmw.service.inter.sys.UserService;


/**
 * 客户基础信息  ACTION类	
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户基础信息ACTION",createDate="2012-12-12T00:00:00",author="程明卫",defaultVals="crmCustBase_")
@SuppressWarnings("serial")
public class CustBaseAction extends BaseAction {
	@Autowired
	private CustBaseService custBaseService;
	
	@Resource(name="custBlackService")
	private CustBlackService custBlackService;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private GvlistService gvlistService;
	
	@Autowired
	private EcustomerService ecustomerService;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="countryService")
	private CountryService countryService;
	
	@Resource(name="provinceService")
	private ProvinceService provinceService;
	
	@Resource(name="cityService")
	private CityService cityService;
	
	@Resource(name="regionService")
	private RegionService regionService;
	
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 客户基础信息 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			Integer custType  = getIVal("custType");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("id", getLVal("id"));
			map.put("tradNumber", getVal("tradNumber"));
			map.put("custType",custType);
			map.put("name", getVal("name"));
			map.put("code", getVal("code"));
			map.put("cardType", getLVal("cardType"));
			map.put("cardNum", getVal("cardNum"));
			map.put("phone", getVal("phone"));
			map.put("custLevel", getIVal("custLevel"));
			map.put("serialNum", getVal("serialNum"));
			map.put("registerTime", getVal("registerTime"));
			if(custType==SysConstant.CUSTTYPE_1){
				map.put("orgcode", getVal("orgcode"));
				map.put("kind", getLVal("kind"));
				map.put("trade", getLVal("trade"));
				map.put("contactor", getVal("contactor"));
			}else{
				map.put("sex", getIVal("sex"));
				map.put("birthday", getVal("birthday"));
				map.put("age", getIVal("age"));
			}
			DataTable dt = custBaseService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();;
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
	 * 获取 客户基础信息 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long customerId = getLVal("customerId");
			Integer custType = getIVal("custType");
			if(!StringHandler.isValidObj(customerId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(customerId.longValue() == -1) return null;
			Map<String,Object> appendParams = new HashMap<String, Object>();
			Object customerEntityObj = null;
			Long baseId = null;
			if(null != custType && custType.intValue() == SysConstant.CUSTTYPE_0){	//个人客户
				log.info("customerInfoService="+customerInfoService);
				 CustomerInfoEntity customerEntity = customerInfoService.getEntity(customerId);
				 String InArea = customerEntity.getInArea();
				 getAddress(appendParams, InArea);
				 baseId = customerEntity.getBaseId();
				 customerEntityObj = customerEntity;
				 Date birthday = customerEntity.getBirthday();
				 if(null != birthday){
					 appendParams.put("birthday", DateUtil.dateFormatToStr(birthday));
				 }
			}else{	//企业
				 EcustomerEntity customerEntity = ecustomerService.getEntity(customerId);
				 SHashMap<String, Object> params = new SHashMap<String, Object>();
				 params.put("baseId", customerEntity.getBaseId());
				 String InArea = customerEntity.getAddress();
				 getAddress(appendParams, InArea);
				 baseId = customerEntity.getBaseId();
				 customerEntityObj = customerEntity;
			}
			String code = null;
			Integer custLevel = null;
			String cardNum = null;
			if(null != baseId){
				CustBaseEntity custBaseEntity = custBaseService.getEntity(baseId);
				code = custBaseEntity.getCode();
				custLevel = custBaseEntity.getCustLevel();
				cardNum = custBaseEntity.getCardNum();
			}
			
			appendParams.put("code", code);
			appendParams.put("custLevel", custLevel);
			appendParams.put("cardNum", cardNum);
			appendParams.put("custType", custType);
			result = ResultMsg.getSuccessMsg(customerEntityObj, appendParams);
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
	 * @param appendParams
	 * @param InArea 地址Id
	 * @throws ServiceException 
	 */
	public void getAddress(Map<String, Object> appendParams, String InArea)
			throws ServiceException {
		try {
			String inArea;
			if(StringHandler.isValidStr(InArea)){
				String[] dq = InArea.split(",");
				inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
				inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
				inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
				inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
				appendParams.put("inArea", InArea+"##"+inArea);
			}
		}catch (Exception ex){
			ex.getMessage();
		}
	}
	/**
	 * 获取客户详情
	 */
	public String detailget() throws Exception{
		try {
			Long customerId = getLVal("customerId");
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(customerId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			CustBaseEntity customerEntity = custBaseService.getEntity(customerId);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("baseId", customerEntity.getId());
			params.put("id", id);
			CustomerInfoEntity costomerinfoEntity = customerInfoService.getEntity(params);
			String serialNum = costomerinfoEntity.getSerialNum();
			Integer sex = costomerinfoEntity.getSex();
			Date registerTime = costomerinfoEntity.getRegisterTime();
			Long regmanid = costomerinfoEntity.getRegman();
			String regman=null;
			if(regmanid!=null){
				UserEntity userEntity = userService.getEntity(regmanid);
				regman  = userEntity.getEmpName();
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("serialNum", serialNum);
			appendParams.put("sex", sex);
			appendParams.put("registerTime", DateUtil.dateFormatToStr(registerTime));
			appendParams.put("regman", regman);
			result = ResultMsg.getSuccessMsg(customerEntity, appendParams);
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
	 * 获取企业客户详情
	 */
	public String detailecustget() throws Exception{
		try {
			Long ecustomerId = getLVal("ecustomerId");
			if(!StringHandler.isValidObj(ecustomerId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			CustBaseEntity ecustomerEntity = custBaseService.getEntity(ecustomerId);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(ecustomerEntity!=null){
				params.put("baseId", ecustomerEntity.getId());
				EcustomerEntity ecustomerinfoEntity = ecustomerService.getEntity(params);
				String serialNum = ecustomerinfoEntity.getSerialNum();
				Date registerTime = ecustomerinfoEntity.getRegisterTime();
				Long regmanid = ecustomerinfoEntity.getRegman();
				String regman=null;
				if(regmanid!=null){
					UserEntity userEntity = userService.getEntity(regmanid);
					regman  = userEntity.getEmpName();
				}
				String tradNumber = ecustomerinfoEntity.getTradNumber();
				String orgcode = ecustomerinfoEntity.getOrgcode(); 
				appendParams.put("serialNum", serialNum);
				appendParams.put("registerTime", DateUtil.dateFormatToStr(registerTime));
				appendParams.put("regman", regman);
				appendParams.put("tradNumber", tradNumber);
				appendParams.put("orgcode", orgcode);
			}
			result = ResultMsg.getSuccessMsg(ecustomerEntity, appendParams);
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
	 * 保存 客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String orgcode = getVal("orgcode");
			CustBaseEntity entity = BeanUtil.copyValue(CustBaseEntity.class,getRequest());
			Long baseId = getLVal("baseId");
			Long cardType = getLVal("cardType");
			Long refId = null;
			if(null != baseId){
				entity.setId(baseId);
				CustBaseEntity oldEntity = custBaseService.getEntity(baseId);
				if(null != oldEntity && oldEntity.getRefId() != null) refId = oldEntity.getRefId();
			}
			if(cardType==0){
				String cardNum = getVal("tradNumber");
				entity.setCardNum(cardNum);
			}
			if(refId != null){
				entity.setRefId(refId);
			}
			custBaseService.saveOrUpdateEntity(entity);
			if(null == entity.getRefId()){/*同步客户数据到财务系统中*/
				Long sysId = getLVal("sysId");
				try{
					synchronous(entity,sysId);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			baseId = entity.getId();
			Long id = getLVal("id");
			String tradNumber = getVal("tradNumber");
			Integer custType = entity.getCustType();
			if(null != custType && custType.intValue() == CustBaseEntity.CUSTTYPE_0){	//个人客户
				CustomerInfoEntity cEntity = getCustomerInfoEntity(id,entity.getId());
				customerInfoService.saveOrUpdateEntity(cEntity);
				id = cEntity.getId();
			}else{//企业客户
				//企业参考  getCustomerInfoEntity 方法
				if(null != custType && custType.intValue() == CustBaseEntity.CUSTTYPE_1){
					EcustomerEntity eEntity = getEcustomerInfoEntity(id,entity.getId());
					eEntity.setTradNumber(tradNumber.toString());
					eEntity.setOrgcode(orgcode);
					ecustomerService.saveOrUpdateEntity(eEntity);
					id = eEntity.getId();
				}
			}
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("baseId", baseId);
			appendParams.put("id", id);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appendParams);
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
	 * 财务系统数据同步
	 * @return
	 * @throws ServiceException 
	 * @throws Exception
	 */
	private void synchronous(CustBaseEntity custbaseEntity,Long sysId) throws ServiceException{
		FinSysService fservice = getFinSysService(sysId);
		try {
			fservice.testConnection();
		} catch (Exception e) {/*网络连接失败，就返回*/
			e.printStackTrace();
			return;
		}
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SysConstant.USER_KEY, getCurUser());
		SHashMap<String, Object> refMap = fservice.saveItem(custbaseEntity, params);
		if(null == refMap || refMap.size() == 0) return;
		custBaseService.updateCustBaseRefIds(refMap, getCurUser());
	}
	
	private CustomerInfoEntity getCustomerInfoEntity(Long id, Long baseId) throws ServiceException{
		CustomerInfoEntity entity = null;
		UserEntity user = getCurUser();
		SHashMap<String, Object> dataMap = getQParams("name,serialNum,cardType#L,sex#I,cardNum");
		String name = dataMap.getvalAsStr("name");
		String serialNum = dataMap.getvalAsStr("serialNum");
		Long cardType = dataMap.getvalAsLng("cardType");
		Integer sex = dataMap.getvalAsInt("sex");
		String cardNum = dataMap.getvalAsStr("cardNum");
		if(null == id){	//个人客户新增
			entity = new CustomerInfoEntity();
			entity.setBaseId(baseId);
			entity.setRegman(user.getUserId());
			entity.setRegisterTime(new Date());
			BeanUtil.setCreateInfo(user, entity);
		}else{	//个人客户修改
			entity = customerInfoService.getEntity(id);
			BeanUtil.setModifyInfo(user, entity);
		}
		entity.setName(name);
		entity.setSerialNum(serialNum);
		entity.setCardType(cardType);
		entity.setSex(sex);
		entity.setCardNum(cardNum);
		return entity;
	}
	
	private  EcustomerEntity getEcustomerInfoEntity(Long id, Long baseId) throws ServiceException{
		EcustomerEntity entity = null;
		UserEntity user = getCurUser();
		SHashMap<String, Object> dataMap = getQParams("name,serialNum,cardNum");
		String name = dataMap.getvalAsStr("name");
		String serialNum = dataMap.getvalAsStr("serialNum");
		String tradNumber = dataMap.getvalAsStr("cardNum");
		if(null == id){	//企业客户新增
			entity = new EcustomerEntity();
			entity.setBaseId(baseId);
			entity.setRegman(user.getUserId());
			entity.setRegisterTime(new Date());
			BeanUtil.setCreateInfo(user, entity);
		}else{	//企业客户修改
			entity = ecustomerService.getEntity(id);
			BeanUtil.setModifyInfo(user, entity);
		}
		entity.setName(name);
		entity.setSerialNum(serialNum);
		entity.setTradNumber(tradNumber);
		return entity;
	}
	/**
	 * 新增  客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			UserEntity user = getCurUser();
			String code = null;
			String serialNum = null;
			SHashMap<String, Object> map = getQParams("name,cardType#L,cardNum");
			map.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			String custName = map.getvalAsStr("name");
			Integer isNon = getIVal("isNon");
			/*--> step 1 验证是否黑名单客户  */
			CustBaseEntity custBaseEntity = custBaseService.getEntity(map);
			if(null != custBaseEntity){
				if(null != custBaseEntity.getCustType() && custBaseEntity.getCustType().intValue() == 0){
					map.clear();
					map.put("baseId", custBaseEntity.getId());
					CustBlackEntity blackEntity = custBlackService.getEntity(map);
					if(null != blackEntity){
						String reason = blackEntity.getReason();
						throw new ServiceException(user,ServiceException.CUSTOMER_BLACK_ERROR,custName,reason);
					}
				}
				/*--> step 2  验证是否已存在的客户  */
				code = custBaseEntity.getCode();
				if(null != custBaseEntity) throw new ServiceException(user,ServiceException.CUSTOMER_EXIST_ERROR,custName,code);
			}else if(!StringHandler.isValidObj(isNon)){
				map.remove("cardType");
				map.remove("cardNum");
				CustBaseEntity custBase = custBaseService.getEntity(map);
				if(custBase != null){
					HashMap<String, Object> params = new HashMap<String, Object>();
					String cardNum = custBase.getCardNum();
					Long  cardType = custBase.getCardType();
					GvlistEntity gvlistEntity = gvlistService.getEntity(cardType);
					String name ="";
					if(gvlistEntity != null){
						name = gvlistEntity.getName();
					}
					params.put("custName", custName);
					params.put("cardNum", cardNum);
					params.put("cardType", name);
					result = ResultMsg.getFailureMsg(params);
					outJsonString(result);
					return null;
				}
			}
			
			Integer IsSystem = user.getIsSystem();
			String indeptname = null;
			if(IsSystem==0){
				Long indeptid = user.getIndeptId();
				if(!StringHandler.isValidStr(indeptid.toString())) throw new ServiceException(ServiceException.ID_IS_NULL);
				DepartmentEntity dentity=departmentService.getEntity(indeptid);
				indeptname = dentity.getName();
			}
			
			Long num = custBaseService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			code = CodeRule.getCode("C", num);
			serialNum = CodeRule.getCode("S", num);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", code);
			jsonObj.put("serialNum", serialNum);
			jsonObj.put("department", indeptname);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("data", jsonObj);
			appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "newcustomer.tip", custName,code,serialNum,indeptname));
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
	 * 财务系统数据同步
	 * @return
	 * @throws Exception
	 */
	public String synchronous()throws Exception {
		try {
			String sysId = getVal("sysId");
			Integer custType = getIVal("custType");
			if(!StringHandler.isValidStr(sysId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FinSysService fservice = getFinSysService(Long.parseLong(sysId));
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("custType", custType);
			List<CustBaseEntity> entitys = custBaseService.getSynsCustBaseList(map);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put(SysConstant.USER_KEY, getCurUser());
			SHashMap<String, Object> refMap = fservice.saveItems(entitys, params);
			if(null != refMap && refMap.size() > 0){
				custBaseService.updateCustBaseRefIds(refMap, getCurUser());
			}
			result = ResultMsg.getSuccessMsg();
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

	private FinSysService getFinSysService(Long sysId)
			throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysId", sysId);
		BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(params);
		if(null == bussFinCfgEntity) throw new ServiceException(ServiceException.BUSSFINCFGENTITY_IS_NULL);
		FinSysCfgEntity entity = finSysCfgService.getEntity(bussFinCfgEntity.getFinsysId());
		if(null == entity || (null != entity.getIsenabled() &&
			entity.getIsenabled().intValue() != SysConstant.OPTION_ENABLED)) throw new ServiceException(ServiceException.FINSYSCFGENTITY_IS_NULL);
		String finsysCode = entity.getCode();
		FinSysService fservice = FinSysFactory.getInstance(finsysCode);
		return fservice;
	}
	
	/**
	 * 删除  客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  客户基础信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			custBaseService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 客户基础信息 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			CustBaseEntity entity = custBaseService.navigationPrev(params);
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
	 * 获取指定的 客户基础信息 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			CustBaseEntity entity = custBaseService.navigationNext(params);
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
