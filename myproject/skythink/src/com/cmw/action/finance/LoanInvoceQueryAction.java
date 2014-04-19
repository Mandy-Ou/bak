package com.cmw.action.finance;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.finance.LoanInvoceService;

/**
 * 放款单信息查询(用于记录放款的流水)
 * 
 * @author pdt
 * 
 */

@Description(remark = "放款单流水ACTION", createDate = "2013-01-15T00:00:00", author = "李听", defaultVals = "fcLoanInvoce_")
@SuppressWarnings("serial")
public class LoanInvoceQueryAction extends BaseAction {
	@Resource(name = "loanInvoceService")
	private LoanInvoceService loanInvoceService;
	LockManager lockMgr = LockManager.getInstance();
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取 放款单 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			Integer custType = getIVal("custType");
			Integer auditState = getIVal("auditState");
			Long formId = getLVal("formId");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			if (null != custType
					&& custType.intValue() == SysConstant.CUSTTYPE_1) {
				/*-->企业贷款发放<--*/
				// name,kind,ccode,payName,regBank,account,eqopAmount,payAmount,startDate,endDate
				map = getQParams("id#L,auditState#I,state#I,custType#I,name,kind,ccode,payName,regBank,account,eqopAmount,payAmount#O,startDate,endDate");
			} else {
				/* 个人贷款发放 */
				// name,cardType,cardNum,payName,regBank,account,eqopAmount,payAmount,startDate,endDate
				map = getQParams("id#L,auditState#I,state#I,custType#I,name,cardType#I,cardNum,payName,regBank,account,eqopAmount,payAmount#O,startDate,endDate");
			}
			map.put("custType", custType);
			map.put("auditState", auditState);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("formId", formId);
			DataTable dt = loanInvoceService.getLoanInvoceQueryDetail(map,
					getStart(), getLimit());
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

	
	public String get()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("id#L");
			Integer custType = getIVal("custType");
			map.put("custType", custType);
			DataTable dt = loanInvoceService.getLoanInvoceQueryDetail(map,-1, -1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	public String rever() throws Exception {
		try {
			Long id = getLVal("id");
			if (!StringHandler.isValidObj(id))
				throw new ServiceException(ServiceException.ID_IS_NULL);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", id);
			loanInvoceService.rever(dataMap);
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
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
}