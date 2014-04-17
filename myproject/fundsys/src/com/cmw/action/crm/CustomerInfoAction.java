package com.cmw.action.crm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.exception.UtilException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CategoryEntity;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.crm.CategoryService;
import com.cmw.service.inter.crm.CustBaseService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.sys.CityService;
import com.cmw.service.inter.sys.CountryService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.ProvinceService;
import com.cmw.service.inter.sys.RegionService;


/**
 * 个人客户基本信息  ACTION类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="个人客户基本信息ACTION",createDate="2012-12-12T00:00:00",author="程明卫",defaultVals="crmCustomerInfo_")
@SuppressWarnings("serial")
public class CustomerInfoAction extends BaseAction {
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name="custBaseService")
	private CustBaseService custBaseService;
	
	@Resource(name="countryService")
	private CountryService countryService;
	
	@Resource(name="provinceService")
	private ProvinceService provinceService;
	
	@Resource(name="cityService")
	private CityService cityService;
	
	@Resource(name="regionService")
	private RegionService regionService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	
	/**
	 * 获取 个人客户基本信息 列表	
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("name", getVal("name"));
			map.put("isenabled",SysConstant.OPTION_ENABLED);
			DataTable dt=null;
			String ids = getVal("id");
			if(StringHandler.isValidStr(ids)){
				map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			}
			 dt = customerInfoService.getResultList(map,getStart(),getLimit());
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
	 * 获取 个人客户基本信息 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			CustomerInfoEntity entity = customerInfoService.getEntity(Long.parseLong(id));
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			Date birthday = entity.getBirthday();
			String bdform = String.format("%tF", birthday);
			if(!StringHandler.isValidStr(bdform)){
				bdform = "";
			}
			Date cendDate = entity.getCendDate();
			String cdform = String.format("%tF", cendDate);
			if(!StringHandler.isValidStr(cdform)){
				cdform = "";
			}
			String InArea = entity.getInArea();
			String inArea = null;
			if(StringHandler.isValidStr(InArea)){
				String[] dq = InArea.split(",");
				inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
				inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
				inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
				inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
				appendParams.put("inArea", InArea+"##"+inArea);
			}
			appendParams.put("birthday", bdform);
			appendParams.put("cendDate", cdform);
			result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 获取 企业法人信息 详情
	 * @return
	 * @throws Exception
	 */
	public String getlegalMan()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("inCustomerId ", id);
			params.put("category", SysConstant.CATEGORY_0);
			params.put("custType", SysConstant.CUSTTYPE_1);
			CategoryEntity cateentity = categoryService.getEntity(params);
			if(cateentity==null){
				EcustomerEntity entity = ecustomerService.getEntity(id);
				String legalman = entity.getLegalMan();
				String IdCard = entity.getLegalIdCard();
				String legalTel = entity.getLegalTel();
				HashMap<String, Object> append = new HashMap<String, Object>();
				append.put("name", legalman);
				append.put("cardNum", IdCard);
				append.put("contactTel", legalTel);
				append.put("id", id);
				result = JsonUtil.getJsonString(append);
				outJsonString(result);
				return null;
			}
			Long relCustomerId = cateentity.getRelCustomerId();
			CustomerInfoEntity entity=null;
			if(relCustomerId!=null){
				 entity = customerInfoService.getEntity(relCustomerId);
			}
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			String InArea = entity.getInArea();
			String inArea = null;
			if(StringHandler.isValidStr(InArea)){
				String[] dq = InArea.split(",");
				inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
				inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
				inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
				inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
				appendParams.put("inArea", InArea+"##"+inArea);
			}
			
			Date birthday = entity.getBirthday();
			String bdform = String.format("%tF", birthday);
			Date cendDate = entity.getCendDate();
			String cdform = String.format("%tF", cendDate);
			appendParams.put("birthday", bdform);
			appendParams.put("cendDate", cdform);
			result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 保存 个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			CustomerInfoEntity entity = BeanUtil.copyValue(CustomerInfoEntity.class,getRequest());
			String inArea = entity.getInArea();
			
			String  baseId = entity.getBaseId().toString();
			if(!StringHandler.isValidStr(baseId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			CustBaseEntity custbaseentity = custBaseService.getEntity(Long.parseLong(baseId));
			Long id = getLVal("id");
			CustomerInfoEntity entity1 = customerInfoService.getEntity(id);
			if(StringHandler.isValidStr(inArea)){
				String[] inA = inArea.split("##");
				if(!inA[0].equals(inA[1])){
					entity.setInArea(inA[0]);
				}else{
					entity.setInArea(entity1.getAccAddress());
				}
			}
			Integer sex= entity1.getSex();
			entity.setName(custbaseentity.getName());
			entity.setCardNum(custbaseentity.getCardNum());
			entity.setCardType(custbaseentity.getCardType());
			entity.setSex(sex);
			entity.setRegisterTime(entity1.getRegisterTime());
			entity.setRegman(entity1.getRegman());
			entity.setSerialNum(entity1.getSerialNum());
			customerInfoService.saveOrUpdateEntity(entity);
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
	 * 公司法人信息保存 
	 * @return
	 * @throws Exception
	 */
	public String savelman()throws Exception {
		try {
			String cid = getVal("id");/*获取得到id文本框中的值*/
			EcustomerEntity ecustomerEntity = new EcustomerEntity();
			if(!StringHandler.isValidStr(cid)){
				savefr(true,cid);
			}else{
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("custType", SysConstant.CUSTTYPE_1);
				params.put("relCustomerId", cid);
				params.put("category", SysConstant.CATEGORY_0);
				CategoryEntity category = categoryService.getEntity(params);
				if(null==category){
					savefr(false,cid);
				}else{
					CustomerInfoEntity entity = BeanUtil.copyValue(CustomerInfoEntity.class,getRequest());
					CustomerInfoEntity cEntity = customerInfoService.getEntity(category.getRelCustomerId());
					String inarea = entity.getInArea();
					if(StringHandler.isValidStr(inarea)){
						String[] inA = inarea.split("##");
						if(!inA[0].equals(inA[1])){
							cEntity.setInArea(inA[0]);
						}
					}
					cEntity.setName(entity.getName());
					cEntity.setSex(entity.getSex());
					cEntity.setAccNature(entity.getAccNature());
					cEntity.setBirthday(entity.getBirthday());
					cEntity.setCardType(entity.getCardType());
					cEntity.setCardNum(entity.getCardNum());
					cEntity.setCendDate(entity.getCendDate());
					cEntity.setAge(entity.getAge());
					cEntity.setMaristal(entity.getMaristal());
					cEntity.setHometown(entity.getHometown());
					cEntity.setNation(entity.getNation());
					cEntity.setDegree(entity.getDegree());
					cEntity.setPhone(entity.getPhone());
					cEntity.setContactTel(entity.getContactTel());
					cEntity.setEmail(entity.getEmail());
					cEntity.setContactor(entity.getContactor());
					cEntity.setZipcode(entity.getZipcode());
					cEntity.setFax(entity.getFax());
					cEntity.setQqmsnNum(entity.getQqmsnNum());
					cEntity.setWorkOrg(entity.getWorkOrg());
					cEntity.setWorkAddress(entity.getWorkAddress());
					cEntity.setRemark(entity.getRemark());
					cEntity.setInAddress(entity.getInAddress());
					cEntity.setIsenabled(Byte.parseByte("-1"));
					//更新法人资料
					
					customerInfoService.saveOrUpdateEntity(cEntity);
					Long ecutomerId = category.getInCustomerId();
					ecustomerEntity= ecustomerService.getEntity(ecutomerId);
					ecustomerEntity.setLegalMan(cEntity.getName());
					
					if(cEntity.getCardType() == 7){
						ecustomerEntity.setLegalIdCard(cEntity.getCardNum());
					}
					ecustomerEntity.setLegalTel(cEntity.getContactTel());
					ecustomerService.saveOrUpdateEntity(ecustomerEntity);
					
					ecustomerEntity.setLegalTel(cEntity.getContactTel());
					Map<String,Object> appendParams = new HashMap<String, Object>();
					appendParams.put("id", cid);
					result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appendParams);
				}
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

	/**保存法人资料
	 * @throws UtilException
	 * @throws ServiceException
	 */
	private void savefr(boolean flag,String cid) throws UtilException, ServiceException {
		CustBaseEntity centity = BeanUtil.copyValue(CustBaseEntity.class,getRequest()); 
		Long num = custBaseService.getMaxID();
		if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("C", num);
		centity.setCode(code);
		centity.setSysId(3l);
		centity.setCustType(0);
		if(!flag){
			centity.setId(null);
			BeanUtil.setCreateInfo(this.getCurUser(),centity);
		}
		custBaseService.saveOrUpdateEntity(centity);
		Long id  = null;
		if(flag){
			id = getLVal("id");
		}
		Integer custType = centity.getCustType();
		CustomerInfoEntity cEntity =null;
		if(null != custType && custType.intValue() == CustBaseEntity.CUSTTYPE_0){	//个人客户
			cEntity = getCustomerInfoEntityc(id,centity.getId());			
		}
		CustomerInfoEntity entity = BeanUtil.copyValue(CustomerInfoEntity.class,getRequest());
		String inarea = entity.getInArea();
		if(StringHandler.isValidStr(inarea)){
			String[] inA = inarea.split("##");
			if(!inA[0].equals(inA[1])){
				cEntity.setInArea(inA[0]);
			}
		}
		cEntity.setRemark(entity.getRemark());
		cEntity.setAccNature(entity.getAccNature());
		cEntity.setAge(entity.getAge());
		cEntity.setBirthday(entity.getBirthday());
		cEntity.setCardNum(entity.getCardNum());
		cEntity.setCendDate(entity.getCendDate());
		cEntity.setContactTel(entity.getContactTel());
		cEntity.setContactor(entity.getContactor());
		cEntity.setDegree(entity.getDegree());
		cEntity.setEmail(entity.getEmail());
		cEntity.setFax(entity.getFax());
		cEntity.setHometown(entity.getHometown());
		cEntity.setAccAddress(entity.getAccAddress());
		cEntity.setPhone(entity.getPhone());
		cEntity.setMaristal(entity.getMaristal());
		cEntity.setOrgid(entity.getOrgid());
		cEntity.setWorkOrg(entity.getWorkOrg());
		cEntity.setWorkAddress(entity.getWorkAddress());
		cEntity.setZipcode(entity.getZipcode());
		cEntity.setQqmsnNum(entity.getQqmsnNum());
		cEntity.setNation(entity.getNation());
		cEntity.setInAddress(entity.getInAddress());
		cEntity.setIsenabled(Byte.parseByte("-1"));
		BeanUtil.setCreateInfo(this.getCurUser(),cEntity);
		customerInfoService.saveOrUpdateEntity(cEntity);
		//更新法人信息
		if(StringHandler.isValidStr(cid)){
			EcustomerEntity ecustomerEntity= ecustomerService.getEntity(Long.parseLong(cid));
			ecustomerEntity.setLegalMan(cEntity.getName());
			if(cEntity.getCardType() == 7){
				ecustomerEntity.setLegalIdCard(cEntity.getCardNum());
			}
			ecustomerEntity.setContacttel(cEntity.getContactTel());
			ecustomerService.saveOrUpdateEntity(ecustomerEntity);
		}
		Long Cid = cEntity.getId();/*得到个人客户基本信息 id*/
		Long eid = ecustomerService.getMaxID();
		CategoryEntity categoryentity = BeanUtil.copyValue(CategoryEntity.class,getRequest());
		categoryentity.setCategory(SysConstant.CATEGORY_0);
		categoryentity.setRelCustomerId(Cid);
		categoryentity.setInCustomerId(eid);
		categoryentity.setCustType(SysConstant.CUSTTYPE_1);
		if(!flag){
			categoryentity.setId(null);
		}
		categoryService.saveOrUpdateEntity(categoryentity);
		Map<String,Object> appendParams = new HashMap<String, Object>();
		appendParams.put("id", Cid);
		result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appendParams);
	}
	
	private CustomerInfoEntity getCustomerInfoEntityc(Long id, Long baseId) throws ServiceException{
		CustomerInfoEntity entity = null;
		UserEntity user = getCurUser();
		SHashMap<String, Object> dataMap = getQParams("name,serialNum,cardType#L,sex#I");
		String name = dataMap.getvalAsStr("name");
		String serialNum = dataMap.getvalAsStr("serialNum");
		Long cardType = dataMap.getvalAsLng("cardType");
		Integer sex = dataMap.getvalAsInt("sex");
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
		return entity;
	}
	
	/**
	 * 新增  个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = customerInfoService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("C", num);
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
	 * 删除  个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  个人客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			customerInfoService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 个人客户基本信息 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			CustomerInfoEntity entity = customerInfoService.navigationPrev(params);
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
	 * 获取指定的 个人客户基本信息 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			CustomerInfoEntity entity = customerInfoService.navigationNext(params);
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
