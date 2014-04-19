package com.cmw.action.finance;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.cmw.core.base.annotation.Description;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.finance.FundsWaterEntity;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;


/**
 * 资金流水  ACTION类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="资金流水ACTION",createDate="2013-08-13T00:00:00",author="pdh",defaultVals="fcFundsWater_")
@SuppressWarnings("serial")
public class FundsWaterAction extends BaseAction {
	@Resource(name="fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Autowired
	private RestypeService restypeService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 资金流水 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map = getQParams("ownfundsId#L,waterType#I,amounts#o,startDate,endDate");
			map.put("ownfundsId", getLVal("ownfundsId"));
			map.put("waterType", getLVal("waterType"));
			map.put("amounts", getLVal("amounts"));
			DataTable dt = fundsWaterService.getResultList(map,getStart(),getLimit());
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
 * 日常收支管理查询(收支详情)
 * @author pdt
 */
	
	public String listDailmanage()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map = getQParams("name,waterType#I,bankName,startDate,endDate,bussTag#I");
			DataTable dt = fundsWaterService.getLoanRecordsList(map,getStart(),getLimit());
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
 * 获取支付详情
 * 	
 */
	public String getDatilManage()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FundsWaterEntity entity = fundsWaterService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
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
	 * 获取 资金流水 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FundsWaterEntity entity = fundsWaterService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
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
	 * 保存 资金流水 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			FundsWaterEntity entity = BeanUtil.copyValue(FundsWaterEntity.class,getRequest());
			fundsWaterService.saveOrUpdateEntity(entity);
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
	 *支出
	 * @return
	 * @throws Exception
	 */
	public String chargefree()throws Exception {
		try {
			String bankName = getVal("bankName");
		String remark = getVal("remark");
			String otherSort = getVal("otherSort");
			Long amounts = getLVal("amounts");
			Long id = getLVal("id");
			Long sysId = getLVal("sysId");
			Integer waterType = getIVal("waterType");
			String opdate = getVal("opdate");
			UserEntity user = getCurUser();
			SHashMap<String, Object> complexData = new SHashMap<String, Object>();
			complexData.put("bankName", bankName);
			complexData.put(SysConstant.USER_KEY, user);
			complexData.put("otherSort", otherSort);
			complexData.put("amounts", amounts);
			complexData.put("id", id);
			complexData.put("remark", remark);
			complexData.put("sysId", sysId);
			complexData.put("waterType", waterType);
			complexData.put("opdate", opdate);
			@SuppressWarnings("unchecked")
			FundsWaterEntity fundsWaterEntity= (FundsWaterEntity)fundsWaterService.doComplexBusss(complexData);
			if(fundsWaterEntity!=null){
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
			}else{
				result = ResultMsg.getFailureMsg(this, ResultMsg.SAVE_FAILURE);
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
	 *自有资金表备注更新,自有资金流水
	 * @return
	 * @throws Exception
	 */
	public String update()throws Exception {
		try {
			String id = getVal("selId");
			String remark = getVal("remark");
			UserEntity user = getCurUser();
			SHashMap<String, Object> complexData = new SHashMap<String, Object>();
			complexData.put("id", id);
			complexData.put("remark", remark);
			complexData.put(SysConstant.USER_KEY, user);
			@SuppressWarnings("unchecked")
			FundsWaterEntity fundsWaterEntity= fundsWaterService.doUpdate(complexData);
			if(fundsWaterEntity!=null){
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
			}else{
				result = ResultMsg.getFailureMsg(this, ResultMsg.SAVE_FAILURE);
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
	 * 保存 自有资金 
	 * @return
	 * @throws Exception
	 */
	public String updates()throws Exception {
		try {
			Long sysId = getLVal("sysId");
			FundsWaterEntity entity = BeanUtil.copyValue(FundsWaterEntity.class,getRequest());
			Long amountlogId  = entity.getAmountlogId();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("amountlogId", amountlogId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<FundsWaterEntity> queryEntity = fundsWaterService.getEntityList(params);
			Long OwnId = entity.getId();
			if(OwnId!=sysId){
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SYSTEM_ERROR);
			}else{
				entity.setAmountlogId((long) -1);
				fundsWaterService.saveOrUpdateEntity(entity);
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  资金流水 	
	 * @return
	 * @throws Exception
	 */
		
	public String add()throws Exception {
		try {
			String recode = getVal("recode");
			Long restypeId = null;
			String rname = null; 
			if(StringHandler.isValidStr(recode)){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("recode", recode);
				params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				RestypeEntity restypeEntity = restypeService.getEntity(params);
				if(restypeEntity != null){
					restypeId = restypeEntity.getId();
					rname = restypeEntity.getName();
				}
			}
			Long num = fundsWaterService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("F", num);
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			appParams.put("code", code);
			appParams.put("restypeId", restypeId);
			appParams.put("rname", rname);
			result = JsonUtil.getJsonString(appParams);
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
	 * 删除  资金流水 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 删除支付详情
	 * @return
	 * @throws Exception
	 */
	public String deleteDatile()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	/**
	 * 启用  资金流水 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  资金流水 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  资金流水 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			fundsWaterService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 资金流水 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FundsWaterEntity entity = fundsWaterService.navigationPrev(params);
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
	 * 获取指定的 资金流水 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FundsWaterEntity entity = fundsWaterService.navigationNext(params);
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
