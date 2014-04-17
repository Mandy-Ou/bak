package com.cmw.action.crm;


import java.rmi.ServerException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CategoryEntity;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.sys.GvlistEntity;
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
 * 企业客户基本信息  ACTION类
 * @author pdh
 * @date 2012-12-21T00:00:00
 */
@Description(remark="企业客户基本信息ACTION",createDate="2012-12-21T00:00:00",author="pdh",defaultVals="crmEcustomer_")
@SuppressWarnings("serial")
public class EcustomerAction extends BaseAction {
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
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
	
	@Resource(name="categoryService")
	private CategoryService categoryService;
	
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 企业客户基本信息 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = ecustomerService.getResultList(map,getStart(),getLimit());
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
	 * 获取 企业客户基本信息 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			EcustomerEntity entity = ecustomerService.getEntity(id);
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			String InArea = entity.getAddress();
			Date  comeTime = entity.getComeTime();
			appendParams.put("comeTime", DateUtil.dateFormatToStr(comeTime));
			Date licencedate = entity.getLicencedate();
			String  licen = DateUtil.dateFormatToStr(licencedate);
			Date RegisterTime  = entity.getRegisterTime();
			String re = DateUtil.dateFormatToStr(false, RegisterTime);
			String inArea = null;
			if(StringHandler.isValidStr(InArea)){
				String[] dq = InArea.split(",");
				inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
				inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
				inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
				inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
				appendParams.put("address", InArea+"##"+inArea);
			}
			appendParams.put("registerTime", re);
			appendParams.put("licencedate", licen);
			result =  ResultMsg.getSuccessMsg(entity,appendParams);
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
	 * 保存 企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			EcustomerEntity entity = BeanUtil.copyValue(EcustomerEntity.class,getRequest());
			String address = entity.getAddress();
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			Long baseId = entity.getBaseId();
			if(!StringHandler.isValidObj(baseId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			CustBaseEntity custbaseentity = custBaseService.getEntity(baseId);
			Long id = getLVal("id");
			EcustomerEntity entity1=  ecustomerService.getEntity(id);
			entity.setOrgcode(custbaseentity.getCardNum());
			entity.setName(custbaseentity.getName());
			entity.setTradNumber(entity1.getTradNumber());
			entity.setRegisterTime(entity1.getRegisterTime());
			entity.setRegman(entity1.getRegman());
			String[] inA = null;
			if(StringHandler.isValidStr(address)){
				inA = address.split("##");
				if(!inA[0].equals(inA[1])){
					entity.setAddress(inA[0]);
				}else{
					entity.setAddress(entity1.getAddress());
				}
			}
			ecustomerService.saveOrUpdateEntity(entity);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("custType", SysConstant.CUSTTYPE_1);
			params.put("inCustomerId", entity.getId());
			params.put("category", SysConstant.CATEGORY_0);
			CategoryEntity category = categoryService.getEntity(params);
			if(category != null){
				Long RelCustomerId = category.getRelCustomerId();//这是企业客户的Id
				CustomerInfoEntity customerInfoEntity =  customerInfoService.getEntity(RelCustomerId);
				String legalMan = entity.getLegalMan();
				if(StringHandler.isValidStr(legalMan)){
					customerInfoEntity.setName(entity.getLegalMan());
				}else{
					String name = customerInfoEntity.getName();
					if(StringHandler.isValidStr(name)){
						entity.setLegalMan(name);
					}
				}
				String contacttel = entity.getLegalTel();
				if(StringHandler.isValidStr(contacttel)){
					customerInfoEntity.setContactTel(contacttel);
				}else{
					String tel = customerInfoEntity.getContactTel();
					if(StringHandler.isValidStr(tel)){
						entity.setLegalTel(tel);
					}
				}
				String idCard = entity.getLegalIdCard();
				if(StringHandler.isValidStr(idCard)){
					customerInfoEntity.setCardNum(idCard);
				}else{
					Long cardType = customerInfoEntity.getCardType();
					if(cardType == 7){
						String cardNum = customerInfoEntity.getCardNum();
						if(StringHandler.isValidStr(cardNum)){
							entity.setLegalIdCard(cardNum);
						}
					}
				}
				ecustomerService.saveOrUpdateEntity(entity);
				customerInfoService.saveOrUpdateEntity(customerInfoEntity);
			}
			appParams.put("id", entity.getId());
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appParams);
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
	 * 新增  企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = ecustomerService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
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
	 * 删除  企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  企业客户基本信息 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			ecustomerService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 企业客户基本信息 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EcustomerEntity entity = ecustomerService.navigationPrev(params);
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
	 * 获取指定的 企业客户基本信息 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EcustomerEntity entity = ecustomerService.navigationNext(params);
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
