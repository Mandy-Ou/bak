package com.cmw.action.finance;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.excel.importer.POIXlsImporter;
import com.cmw.core.kit.excel.importer.POIXlsImporter.BreakAction;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.impl.fininter.external.generic.VoucherModel;
import com.cmw.service.impl.fininter.external.generic.VoucherTransform;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.test.pdh.ShashMapTest;

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
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA
					: dt.getJsonArr();
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