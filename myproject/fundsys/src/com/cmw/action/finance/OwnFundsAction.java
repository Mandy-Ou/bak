package com.cmw.action.finance;


import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.service.inter.finance.OwnFundsService;
import com.cmw.service.inter.sys.AccountService;


/**
 * 自有资金  ACTION类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="自有资金ACTION",createDate="2013-08-13T00:00:00",author="pdh",defaultVals="fcOwnFunds_")
@SuppressWarnings("serial")
public class OwnFundsAction extends BaseAction {
	@Resource(name="ownFundsService")
	private OwnFundsService ownFundsService;
	@Resource(name= "accountService")
	private AccountService accountService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 自有资金 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("isenabled#I,code,bankName");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = ownFundsService.getResultList(map,getStart(),getLimit());
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
	 * 获取 自有资金 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			OwnFundsEntity entity = ownFundsService.getEntity(Long.parseLong(id));
			 resultAll(entity);
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
	 * @throws ServiceException
	 */
	private void resultAll(OwnFundsEntity entity) throws ServiceException {
		String accountIds = "";
		 String accounts = "";
		 String last = null;
		 Date lastDate = entity.getLastDate();
		 if(StringHandler.isValidObj(lastDate)){
			 last = DateUtil.dateFormatToStr(lastDate);
		 }
		 final String lastdate = last;
		if(entity!=null){
			Long aId = entity.getAccountId();//账号Id
			AccountEntity accountEntity = accountService.getEntity(aId);
			String bankName = accountEntity.getBankName();
			accounts = accountEntity.getAccount();
			accountIds = aId+"##"+bankName;
			
		}
		final String accountId = accountIds;
		final String account = accounts;
		result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
			public void execute(JSONObject jsonObj) {
				jsonObj.put("accountId", accountId);
				jsonObj.put("account", account);
				jsonObj.put("lastDate", lastdate);
			}
		});
	}
	
	/**
	 * 保存 自有资金 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String accountId = getVal("accountId");
			OwnFundsEntity entity = BeanUtil.copyValue(OwnFundsEntity.class,getRequest());
			Long OaccountId  = entity.getAccountId();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("accountId", accountId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			List<OwnFundsEntity> queryEntity = ownFundsService.getEntityList(params);
			boolean flag = false;
			Long OwnId = entity.getId();
			if(!queryEntity.isEmpty() && queryEntity.size()>0){
				for(OwnFundsEntity oEntity : queryEntity){
					Long existaccountId = oEntity.getAccountId();
					if(existaccountId.equals(OaccountId)){
						Long oid = oEntity.getId();
						if(oid != OwnId){
							flag = true;
						}
						break;
					}
				}
			}
			if(flag){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("zh", 1);
				jsonObj.put("success", true);
				result = jsonObj.toJSONString();
			}else{
				entity.setAccountId(Long.parseLong(accountId.split("##")[0]));
				entity.setUamount(entity.getTotalAmount());
				ownFundsService.saveOrUpdateEntity(entity);
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
	 * 新增  自有资金 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = ownFundsService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("O", num);
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
	 * 删除  自有资金 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  自有资金 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  自有资金 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  自有资金 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			ownFundsService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 自有资金 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			OwnFundsEntity entity = ownFundsService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				resultAll(entity);
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
	 * 获取指定的 自有资金 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			OwnFundsEntity entity = ownFundsService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				resultAll(entity);
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
