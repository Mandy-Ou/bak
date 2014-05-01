package com.cmw.action.funds;

import java.math.BigDecimal;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.RbrelationEntity;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.funds.ReceiptMsgEntity;
import com.cmw.entity.funds.SettlementEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.funds.RbrelationService;
import com.cmw.service.inter.funds.ReceiptMsgService;
import com.cmw.service.inter.funds.ReceiptService;
import com.cmw.service.inter.funds.SettlementService;
import com.cmw.service.inter.sys.UserService;

/**
 * 汇票信息登记ACTION
 * 
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */

@Description(remark = "汇票信息登记ACTION", createDate = "2014-01-15T00:00:00", author = "彭登浩", defaultVals = "fuReceiptMsg_")
@SuppressWarnings("serial")
public class ReceiptMsgAction extends BaseAction {
	@Resource(name = "receiptMsgService")
	private ReceiptMsgService receiptMsgService;
	
	@Resource(name = "receiptService")
	private ReceiptService receiptService;
	
	@Resource(name = "rbrelationService")
	private RbrelationService rbrelationService;
	
	@Resource(name = "settlementService")
	private SettlementService settlementService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取 汇票信息登记 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			
			SHashMap<String, Object> map = getQParams("receMsgId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = receiptMsgService.getResultList(map, getStart(),getLimit());
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


	/**
	 * 获取 汇票信息登记 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String get() throws Exception {
		try {
			String receiptId = getVal("receiptId");
			if(StringHandler.isValidStr(receiptId)) new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("receiptId", Long.parseLong(receiptId));
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			ReceiptMsgEntity receiptMsgEntity = receiptMsgService.getEntity(params);
			Long isShow = 1l;
			HashMap<String,Object> appendParams = new HashMap<String, Object>();
			SettlementEntity settlementEntity = null;
			if(receiptMsgEntity == null){
				isShow = -1l;
				ReceiptEntity receiptEntity = receiptService.getEntity(Long.parseLong(receiptId));
				String code = getCode();
				String name  = receiptEntity.getName();
				String rnum = receiptEntity.getRnum();
				
				appendParams.put("code", code);
				appendParams.put("name", name);
				appendParams.put("rnum", rnum);
				appendParams.put("receiptId", receiptId);
				
				params.remove("isenabled");
				RbrelationEntity rbrelationEntity  = rbrelationService.getEntity(params);
				if(rbrelationEntity != null){
					Long receiptBookId = rbrelationEntity.getReceiptBookId();
					params.clear();
					params.put("receiptBookId", receiptBookId);
					params.put("isenabled",SysConstant.OPTION_ENABLED);
					 settlementEntity  = settlementService.getEntity(params);
					if(settlementEntity != null){
						Long settleId = settlementEntity.getId();
						BigDecimal amount = settlementEntity.getAmount();
						Double rate = settlementEntity.getRate();
						BigDecimal tiamount = settlementEntity.getTiamount();
						appendParams.put("settleId", settleId);
						appendParams.put("amount", amount);
						appendParams.put("amount", rate);
						appendParams.put("tiamount", tiamount);
						appendParams.put("rate", rate);
					}else {
						outJsonString("-1");
						return null;
					}
				}else {
					outJsonString("-1");
					return null;
				}
			}else {
				Long id = receiptMsgEntity.getId();
				appendParams.put("id", id);
				if(settlementEntity != null){
					outJsonString("-1");
					return null;
				}
			}
			appendParams.put("isNotShowFrm", isShow);
			result = ResultMsg.getSuccessMsg(appendParams);
			
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
	 * 详情方法
	 * @return
	 * @throws ServiceException
	 */
	public String detail() throws ServiceException{
		try {
			String isEdit = getVal("isEdit");
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) new ServiceException(ServiceException.ID_IS_NULL);
			ReceiptMsgEntity rdeceiptMsgEntity = receiptMsgService.getEntity(Long.parseLong(id));
			Long userId = rdeceiptMsgEntity.getServiceMan();
			UserEntity user = userService.getEntity(userId);
			String empName  = "";
			if(user != null){
				if(StringHandler.isValidStr(isEdit)){
					empName = userId+"##"+user.getEmpName();
				}else{
					empName = user.getEmpName();
				}
				
			}
			final String serviceMan = empName;
			Date ticketDate = rdeceiptMsgEntity.getTicketDate();
			final String tDate =  DateUtil.dateFormatToStr(ticketDate);
			Date fundsDate = rdeceiptMsgEntity.getFundsDate();
			final String fDate =  DateUtil.dateFormatToStr(fundsDate);
			result = FastJsonUtil.convertJsonToStr(rdeceiptMsgEntity, new Callback(){
					public void execute(JSONObject jsonObj) {
						jsonObj.put("serviceMan", serviceMan);
						jsonObj.put("fundsDate", fDate);
						jsonObj.put("ticketDate", tDate);
					}
			});
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
	 * @return 
	 * @throws ServiceException
	 */
	private String getCode() throws ServiceException {
		Long num = receiptMsgService.getMaxID();
		if (null == num) throw new ServiceException( ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("R", num);
		return code;
	}


	/**
	 * 保存 汇票信息登记资料
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String save() throws Exception {
		try {
			ReceiptMsgEntity entity = BeanUtil.copyValue(ReceiptMsgEntity.class,getRequest());
			receiptMsgService.saveOrUpdateEntity(entity);
			Long id = entity.getId();
			HashMap<String, Object> appendMap  =  new HashMap<String, Object>();
			appendMap.put("id", id);
			result = ResultMsg.getSuccessMsg(appendMap);
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
	 * 新增 汇票信息登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			String code = getCode();
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
	 * 删除 汇票信息登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}

	/**
	 * 启用 汇票信息登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}

	/**
	 * 禁用 汇票信息登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disabled() throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}

	/**
	 * 删除/起用/禁用 汇票信息登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg) throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			receiptMsgService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 汇票信息登记 上一个对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prev() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			ReceiptMsgEntity entity = receiptMsgService.navigationPrev(params);
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
	 * 获取指定的 汇票信息登记下一个对象
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public String next() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			ReceiptMsgEntity entity = receiptMsgService.navigationNext(params);
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
