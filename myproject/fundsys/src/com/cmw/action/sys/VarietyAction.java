package com.cmw.action.sys;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 业务品种  ACTION类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务品种ACTION",createDate="2012-11-28T00:00:00",author="程明卫",defaultVals="sysVariety_")
@SuppressWarnings("serial")
public class VarietyAction extends BaseAction {
	@Resource(name="varietyService")
	private VarietyService varietyService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 业务品种 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("sysId", getLVal("sysId"));
			map.put("name", getVal("name"));
			map.put("bussType", getIVal("bussType"));
			map.put("isCredit", getIVal("isCredit"));
			DataTable dt = varietyService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 业务品种 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			VarietyEntity entity = varietyService.getEntity(Long.parseLong(id));
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			String companyIds = entity.getCompanyIds();
			String companyName = "";
			if(StringHandler.isValidStr(companyIds)){
				companyName = entity.getCompanyIds() +"##"+entity.getCompanyName();
			}
			entity.setCompanyName(companyName);
			result = ResultMsg.getSuccessMsg(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 业务品种 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			VarietyEntity entity = BeanUtil.copyValue(VarietyEntity.class,getRequest());
			String companyName = entity.getCompanyName();
			if(StringHandler.isValidStr(companyName)){
				String[] strArr = companyName.split("##");
				entity.setCompanyIds(strArr[0]);
				entity.setCompanyName(strArr[1]);
			}
			varietyService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  业务品种 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = varietyService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("V", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  业务品种 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  业务品种 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  业务品种 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	
	/**
	 * 删除/起用/禁用  业务品种 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			varietyService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 业务品种 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			VarietyEntity entity = varietyService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				appendParams.put("amountRange", entity.getAmountRange());
				appendParams.put("limitRange", entity.getLimitRange());
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 业务品种 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			VarietyEntity entity = varietyService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				appendParams.put("amountRange", entity.getAmountRange());
				appendParams.put("limitRange", entity.getLimitRange());
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 业务品种 下拉框列表
	 * @return
	 * @throws Exception
	 */
	public String cbolist()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("sysId", getLVal("sysId"));
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			DataTable dt = varietyService.getResultList(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取适用于当前用户条件的业务品种 下拉框列表
	 * @return
	 * @throws Exception
	 */
	public String enablelist()throws Exception {
		try {
			//sysId,custType,yearLoan,monthLoan,dayLoan,appAmount
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put(SysConstant.USER_KEY, getCurUser());
			Integer custType = getIVal("custType");
			map.put("sysId", getLVal("sysId"));
			if(!StringHandler.isValidObj(custType)){/*当修改申请单走 cbolist 方法加载数据*/
				return cbolist();
			}
			StringBuffer bussType = new StringBuffer();
			bussType.append(0+",");
			if(null != custType){
				if(custType.intValue() == SysConstant.CUSTTYPE_0){
					bussType.append(1+",");
				}else{
					bussType.append(2+",");
				}
			}
			map.put("bussType", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+StringHandler.RemoveStr(bussType));
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			List<VarietyEntity> list = varietyService.getEntityList(map);
			Integer yearLoan = getIVal("yearLoan");
			Integer monthLoan = getIVal("monthLoan");
			Integer dayLoan = getIVal("dayLoan");
			Integer loanTotalDay = getTotalDays(0, yearLoan) + getTotalDays(1, monthLoan) + dayLoan;
			Double appAmount = getDVal("appAmount");
			DataTable dt = null;
			List<VarietyEntity> newList = null;
			if(null != list && list.size() > 0){
				newList = new ArrayList<VarietyEntity>();
				boolean isAdd = false;
				
				for(VarietyEntity entity : list){
					isAdd = false;
					boolean isUse = false;
					Integer isLoan = entity.getIsLoan();
					String amountRange = entity.getAmountRange();
					Integer useorg  = entity.getUseorg();
					/*-----------------如果是企业客户的话，查看该公司名称是被限制了--------------------------*/
					if(useorg.intValue()==1){
						String companyIds = entity.getCompanyIds();
						if(StringHandler.isValidStr(companyIds) && (Long.parseLong(companyIds) == getCurUser().getIncompId()
								||getCurUser().getIncompId()==-1)){
							isUse = true;
						}else{
							isUse = false;
							continue;
						}
					}
					/*------ step 1 : 验证贷款金额是否符合条件 -----*/
					if(null != isLoan && isLoan.intValue() == BussStateConstant.VARIETY_ISLOAN_0){
						isAdd = true;
					}else{
						if(null != isLoan && isLoan.intValue() == BussStateConstant.VARIETY_ISLOAN_1 &&
							StringHandler.isValidStr(amountRange)){
							String[] arangeArr = amountRange.split(",");
							Double minAmount = Double.parseDouble(arangeArr[0]) * 10000;
							Double maxAmount = Double.parseDouble(arangeArr[1]) * 10000;
							isAdd = (appAmount >= minAmount && appAmount <= maxAmount);
							if(!isAdd) continue;
						}
					}
					
					/*------ step 2 : 验证贷款期限是否符合条件 -----*/
					Integer isLimit = entity.getIsLimit();
					String limitRange = entity.getLimitRange();
					if(null != isLimit && isLimit.intValue() == BussStateConstant.VARIETY_ISLIMIT_0){
						isAdd = true;
					}else{
						if(null != isLimit && isLimit.intValue() == BussStateConstant.VARIETY_ISLIMIT_1 &&
							StringHandler.isValidStr(limitRange)){
							String[] lrangeArr = limitRange.split(",");
							int minLimit = Integer.parseInt(lrangeArr[0]);
							int maxLimit = Integer.parseInt(lrangeArr[1]);
							String unint = lrangeArr[2];
							if(!StringHandler.isValidStr(unint)){
								isAdd = false;
								continue;
							}
							if(unint.equals("Y")){
								minLimit = getTotalDays(0, minLimit);
								maxLimit = getTotalDays(0, maxLimit);
								isAdd = (yearLoan>=minLimit && yearLoan<=maxLimit);
							}else if(unint.equals("M")){
								minLimit = getTotalDays(1, minLimit);
								maxLimit = getTotalDays(1, maxLimit);
								isAdd = (yearLoan>0 || (monthLoan>=minLimit && monthLoan<=maxLimit));
							}/*else if(unint.equals("D")){
								isAdd = (yearLoan>0 || monthLoan>0 || (dayLoan>=minLimit && dayLoan<=maxLimit));
							}*/
							
							isAdd = (loanTotalDay>=minLimit && loanTotalDay<=maxLimit);
						}
					}
					if(isAdd) newList.add(entity);
					if(isUse){
						continue;
					}
				}
			}
			if(null != newList && newList.size() > 0){
				String[] cmnsArr = newList.get(0).getFields();
				String cmns = StringHandler.join(cmnsArr);
				List<Object> dataList = new ArrayList<Object>();
				for(VarietyEntity entity : newList){
					dataList.add(entity.getDatas());
				}
				dt = new DataTable(dataList, cmns);
			}
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private int getTotalDays(int unint,int ym){
		int totalDays = 0;
		if(ym==0) return totalDays;
		switch (unint) {
			case 0:/*年*/
				totalDays = ym * 360;
				break;
			case 1:/*月*/
				totalDays = ym * 30;
				break;
			default:
				break;
		}
		return totalDays;
	}
}