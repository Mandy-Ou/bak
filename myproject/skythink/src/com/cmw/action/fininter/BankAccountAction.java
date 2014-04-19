package com.cmw.action.fininter;


import java.util.HashMap;
import java.util.List;
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
import com.cmw.entity.fininter.BankAccountEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.inter.fininter.BankAccountService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;


/**
 * 财务系统银行账号  ACTION类
 * @author 程明卫
 * @date 2013-04-20T00:00:00
 */
@Description(remark="财务系统银行账号ACTION",createDate="2013-04-20T00:00:00",author="程明卫",defaultVals="fsBankAccount_")
@SuppressWarnings("serial")
public class BankAccountAction extends BaseAction {
	@Resource(name="bankAccountService")
	private BankAccountService bankAccountService;
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 财务系统银行账号 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map =  getQParams("code,name,bankName");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("sysId", getLVal("sysId"));
			map.put("isPay", getIVal("isPay"));
			DataTable dt = bankAccountService.getResultList(map,getStart(),getLimit());
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
	 * 财务系统数据同步
	 * @return
	 * @throws Exception
	 */
	public String synchronous()throws Exception {
		try {
			String finsysId = getVal("finsysId");
			if(!StringHandler.isValidStr(finsysId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(Long.parseLong(finsysId));
			if(null == bussFinCfgEntity) throw new ServiceException(ServiceException.BUSSFINCFGENTITY_IS_NULL);
			FinSysCfgEntity entity = finSysCfgService.getEntity(bussFinCfgEntity.getFinsysId());
			String finsysCode = entity.getCode();
			FinSysService fservice = FinSysFactory.getInstance(finsysCode);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put(SysConstant.USER_KEY, getCurUser());
			params.put("objectName", "BankAccountEntity");
			List<BankAccountEntity> list = fservice.getBankAccounts(params);
			bankAccountService.batchSaveEntitys(list);
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
	
	/**
	 * 获取 财务系统银行账号 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BankAccountEntity entity = bankAccountService.getEntity(Long.parseLong(id));
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
	 * 保存 财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			BankAccountEntity entity = BeanUtil.copyValue(BankAccountEntity.class,getRequest());
			bankAccountService.saveOrUpdateEntity(entity);
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
	 * 新增  财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = bankAccountService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("B", num);
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
	 * 删除  财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  财务系统银行账号 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			bankAccountService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 财务系统银行账号 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BankAccountEntity entity = bankAccountService.navigationPrev(params);
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
	 * 获取指定的 财务系统银行账号 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			BankAccountEntity entity = bankAccountService.navigationNext(params);
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
