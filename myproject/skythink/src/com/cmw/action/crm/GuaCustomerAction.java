package com.cmw.action.crm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.crm.CustomerInfoDaoInter;
import com.cmw.entity.crm.CategoryEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.crm.GuaCustomerEntity;
import com.cmw.service.inter.crm.CategoryService;
import com.cmw.service.inter.crm.CustBaseService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.crm.GuaCustomerService;
import com.cmw.service.inter.sys.CityService;
import com.cmw.service.inter.sys.CountryService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.ProvinceService;
import com.cmw.service.inter.sys.RegionService;

/**
 * 第三方担保人 ACTION类
 *
 * @author 李听
 * @date 2013-12-31T00:00:00
 */
@Description(remark = "第三方担保人ACTION", createDate = "2013-12-31T00:00:00", author = "李听", defaultVals = "crmGuaCustomer_")
@SuppressWarnings("serial")
public class GuaCustomerAction extends BaseAction {
	@Resource(name = "guaCustomerService")
	private GuaCustomerService guaCustomerService;

	@Resource(name = "customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "categoryService")
	private CategoryService categoryService;

	@Resource(name = "gvlistService")
	private GvlistService gvlistService;

	@Resource(name = "ecustomerService")
	private EcustomerService ecustomerService;

	@Resource(name = "custBaseService")
	private CustBaseService custBaseService;

	@Resource(name = "countryService")
	private CountryService countryService;

	@Resource(name = "provinceService")
	private ProvinceService provinceService;

	@Resource(name = "cityService")
	private CityService cityService;

	@Resource(name = "regionService")
	private RegionService regionService;
	
	
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取 第三方担保人 列表
	 *
	 * @return
	 * @throws Exception
	 */

	public String list() throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put(SysConstant.USER_KEY, this.getCurUser());
//			map.put("custType", getIVal("custType"));
			map.put("baseId", getLVal("baseId"));
			DataTable dt = guaCustomerService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	public String gualist() throws Exception{
		try{
			SHashMap<String, Object> map = getQParams("custType#I,name,baseId#L");
			DataTable dt = guaCustomerService.getResultList(map,getStart(),getLimit());
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
	
	public String guaContlist() throws Exception{
		try{
			String customerId = getVal("customerId");
			if(!StringHandler.isValidStr(customerId)) new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = getQParams("custType#I,name");
			Integer custType = map.getvalAsInt("custType");
			Long baseId = null;
			if(custType==SysConstant.CUSTTYPE_0){
				CustomerInfoEntity customerInfoEntity  =  customerInfoService.getEntity(Long.parseLong(customerId));
				if(customerInfoEntity != null){
					baseId = customerInfoEntity.getBaseId();
				}
			}else {
				EcustomerEntity ecustomerEntity  = ecustomerService.getEntity(Long.parseLong(customerId));
				if(ecustomerEntity != null){
					baseId = ecustomerEntity.getBaseId();
				}
			}
			map.put("baseId", baseId);
			DataTable dt = guaCustomerService.getResultList(map,getStart(),getLimit());
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
	 * 获取 第三方担保人 详情
	 *
	 * @return
	 * @throws Exception
	 */
//	public String get() throws Exception {
//		try {
//			String id = getVal("id");
//			if (!StringHandler.isValidStr(id))
//				throw new ServiceException(ServiceException.ID_IS_NULL);
//			if(id.equals("-1")){
//				outJsonString(result);
//				return null;
//			}
//			CustomerInfoEntity customerInfoEntity = customerInfoService.getEntity(Long.parseLong(id));
//			SHashMap<String, Object> params = new SHashMap<String, Object>();
//			HashMap<String, Object> appParams = new HashMap<String, Object>();
//			if(customerInfoEntity != null){
//				Long baseId = customerInfoEntity.getBaseId();
//				params.put("category", SysConstant.CATEGORY_2);
//				params.put("relCustomerId", baseId);
//				params.put("inCustomerId", id);
//				CategoryEntity categoryEntity = categoryService.getEntity(params);
//				if(categoryEntity != null){
//					Long categoryId = categoryEntity.getId();
//					if(StringHandler.isValidObj(categoryId)){
//						params.clear();
//						params.put("categoryId", categoryId);	
//						GuaCustomerEntity guaCustomerEntity = guaCustomerService.getEntity(params);
//						if(guaCustomerEntity != null ){
//							Integer isgua = guaCustomerEntity.getIsgua(); 
//							String relation = guaCustomerEntity.getRelation();
//							appParams.put("isgua", isgua);
//							appParams.put("relation", relation);
//							String InArea = customerInfoEntity.getInArea();
//							String inArea = null;
//							if(StringHandler.isValidStr(InArea)){
//								String[] dq = InArea.split(",");
//								inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
//								inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
//								inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
//								inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
//								appParams.put("inArea", InArea+"##"+inArea);
//							}
//							
//							Date birthday = customerInfoEntity.getBirthday();
//							String bdform = String.format("%tF", birthday);
//							Date cendDate = customerInfoEntity.getCendDate();
//							String cdform = String.format("%tF", cendDate);
//							appParams.put("birthday", bdform);
//							appParams.put("cendDate", cdform);
//						}
//					}
//				}
//			}
//			
//			result = FastJsonUtil.convertJsonToStr(customerInfoEntity,appParams);
//		} catch (ServiceException ex) {
//			result = ResultMsg.getFailureMsg(this, ex.getMessage());
//			if (null == result)
//				result = ex.getMessage();
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
//			if (null == result)
//				result = ex.getMessage();
//			ex.printStackTrace();
//		}
//		outJsonString(result);
//		return null;
//	}
	
	/**
	 * 民汇小额贷款获取第三方担保人资料信息
	 * @return
	 * @throws Exception
	 */
	public String get() throws  Exception{
		try {
			String id = getVal("id");
			if (!StringHandler.isValidStr(id))
				throw new ServiceException(ServiceException.ID_IS_NULL);
			if(id.equals("-1")){
				result = "-1";
				outJsonString(result);
				return null;
			}
			GuaCustomerEntity entity = guaCustomerService.getEntity(Long.parseLong(id));
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			if(entity != null){
				String InArea = entity.getInArea();
				String inArea = null;
				if(StringHandler.isValidStr(InArea)){
					String[] dq = InArea.split(",");
					inArea  = countryService.getEntity(Long.parseLong(dq[0])).getName();
					inArea  += provinceService.getEntity(Long.parseLong(dq[1])).getName();
					inArea  += cityService.getEntity(Long.parseLong(dq[2])).getName();
					inArea  += regionService.getEntity(Long.parseLong(dq[3])).getName();
					appParams.put("inArea", InArea+"##"+inArea);
				}
			}
			result = FastJsonUtil.convertJsonToStr(entity,appParams);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
//	public String save() throws Exception {
//		try {
////			Long id = getLVal("id");
//			CustomerInfoEntity entity = BeanUtil.copyValue(CustomerInfoEntity.class,getRequest());
//			String inArea = entity.getInArea();
//			
//			if(StringHandler.isValidStr(inArea)){
//				String[] inA = inArea.split("##");
//				if(!inA[0].equals(inA[1])){
//					entity.setInArea(inA[0]);
//				}else{
//					entity.setInArea(entity.getAccAddress());
//				}
//			}
//			customerInfoService.saveOrUpdateEntity(entity);
//			GuaCustomerEntity guaCustomerEntity = new GuaCustomerEntity();
//			Integer custType = getIVal("custType");
//			String relation = getVal("relation");
//			Integer isgua   = getIVal("isgua");
//			Long id = null;
//			Long baseId = null;
//			if(entity != null){
//				id = entity.getId();
//				baseId = entity.getBaseId();
//				CategoryEntity categoryEntity = new CategoryEntity();
//				categoryEntity.setCategory(SysConstant.CATEGORY_2);
//				categoryEntity.setCustType(custType);
//				categoryEntity.setInCustomerId(id);
//				categoryEntity.setRelCustomerId(baseId);
//				categoryService.saveOrUpdateEntity(categoryEntity);
//				Long categoryId =  categoryEntity.getId();
//				guaCustomerEntity.setIsgua(isgua);
//				guaCustomerEntity.setRelation(relation);
//				guaCustomerEntity.setCategoryId(categoryId);
//				guaCustomerService.saveOrUpdateEntity(guaCustomerEntity);
//				
//			}
//			Map<String, Object> appParams = new HashMap<String, Object>();
//			appParams.put("id", id);
//			result = ResultMsg.getSuccessMsg(guaCustomerEntity,appParams);
//		} catch (ServiceException ex) {
//			result = ResultMsg.getFailureMsg(this, ex.getMessage());
//			if (null == result)
//				result = ex.getMessage();
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
//			if (null == result)
//				result = ex.getMessage();
//			ex.printStackTrace();
//		}
//		outJsonString(result);
//		return null;
//	}
	
	public String save() throws  Exception {
		try {
			GuaCustomerEntity entity = BeanUtil.copyValue(GuaCustomerEntity.class,getRequest());
			String inArea = entity.getInArea();
			if(StringHandler.isValidStr(inArea) && inArea.indexOf("##") !=-1){
				String[] inA = inArea.split("##");
				if(!inA[0].equals(inA[1])){
					entity.setInArea(inA[0]);
				}
			}
			guaCustomerService.saveOrUpdateEntity(entity);
			Map<String, Object> appParams = new HashMap<String, Object>();
			Long  id = entity.getId();
			appParams.put("id", id);
			result = ResultMsg.getSuccessMsg(entity,appParams);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 新增 第三方担保人
	 *
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			Long num = guaCustomerService.getMaxID();
			if (null == num)
				throw new ServiceException(
						ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("G", num);
			result = JsonUtil.getJsonString("code", code);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 删除 第三方担保人
	 *
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}

	/**
	 * 启用 第三方担保人
	 *
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}

	/**
	 * 禁用 第三方担保人
	 *
	 * @return
	 * @throws Exception
	 */
	public String disabled() throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}

	/**
	 * 删除/起用/禁用 第三方担保人
	 *
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg) throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			guaCustomerService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this, sucessMsg);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 第三方担保人 上一个对象
	 *
	 * @return
	 * @throws Exception
	 */
	public String prev() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			GuaCustomerEntity entity = guaCustomerService
					.navigationPrev(params);
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getFirstMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 第三方担保人 下一个对象
	 *
	 * @return
	 * @throws Exception
	 */
	public String next() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			GuaCustomerEntity entity = guaCustomerService
					.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getLastMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

}
