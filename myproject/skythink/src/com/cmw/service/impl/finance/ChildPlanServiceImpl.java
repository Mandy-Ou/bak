package com.cmw.service.impl.finance;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.ChildPlanDaoInter;
import com.cmw.dao.inter.finance.LoanContractDaoInter;
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.service.inter.finance.ChildPlanService;


/**
 * 还款计划子表  Service实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划子表业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("childPlanService")
public class ChildPlanServiceImpl extends AbsService<ChildPlanEntity, Long> implements  ChildPlanService {
	@Autowired
	private ChildPlanDaoInter childPlanDao;
	@Autowired
	private LoanContractDaoInter loanContractDao;
	@Override
	public GenericDaoInter<ChildPlanEntity, Long> getDao() {
		return childPlanDao;
	}
	
	
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			Object contractId = (Object)params.get("contractId");
			DataTable dtContract = null;
			if(StringHandler.isValidObj(contractId)) {
				SHashMap<String, Object> loancontractParams = new SHashMap<String, Object>();
				loancontractParams.put("contractId", contractId);
				 dtContract = loanContractDao.getResultList(loancontractParams, -1, -1);
			}
			HashMap<String, Object> conMap = getloanContractParams(dtContract);
			params.putAll(conMap);
			DataTable dt = childPlanDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}


	/**
	 * @param dtContract
	 * @return
	 */
	private HashMap<String, Object> getloanContractParams(DataTable dtContract) {
		HashMap<String, Object> vals = new HashMap<String, Object>();
		String code = dtContract.getString("code");
		String custType = dtContract.getString("custType");
		if(StringHandler.isValidStr(custType)){
			if(custType.equals("0")){
				custType = "个人客户";
			}else{
				custType = "企业客户";
			}
		}
		
		String accName = dtContract.getString("accName");
		String doDate = dtContract.getString( "doDate#yyyy-MM-dd");
		String mgrtype = dtContract.getString("mgrtype");
		if(StringHandler.isValidStr(mgrtype)){
			if(mgrtype.equals("0")){
				mgrtype = "不收管理费";
			}else{
				mgrtype = "按还款方式算法收取";
			}
		}
		String isadvance = dtContract.getString("isadvance");
		if(StringHandler.isValidStr(isadvance)){
			if(isadvance.equals("0")){
				isadvance = "否";
			}else{
				isadvance = "是";
			}
		}
		String rateType = dtContract.getString("rateType");
		if(StringHandler.isValidStr(rateType)){
			if(rateType.equals("1")){
				rateType = "月利率";
			}else if(rateType.equals("2")){
				rateType = "日利率";
			}else{
				rateType="年利率";
			}
		}
		String payType = dtContract.getString("payType");
		String endDate = dtContract.getString("endDate#yyyy-MM-dd");
		String payDay = dtContract.getString("payDay");
		String payDate = dtContract.getString("payDate#yyyy-MM-dd");
		String loanLimit = dtContract.getString("loanLimit");
		String payAccount = dtContract.getString("payAccount");
		String payBank = dtContract.getString("payBank");
		String borAccount = dtContract.getString("borAccount");
		String borBank = dtContract.getString("borBank");
		String appAmount = dtContract.getString("appAmount");
		String frate = dtContract.getString("frate");
		String urate = dtContract.getString("urate");
		String arate = dtContract.getString("arate");
		String prate = dtContract.getString("prate");
		String mrate = dtContract.getString("mrate");
		String rate = dtContract.getString("rate");
		
		vals.put("code", code);
		vals.put("doDate", doDate);
		vals.put("isadvance", isadvance);
		vals.put("payType", payType);
		vals.put("borBank", borBank);
		
		vals.put("custType", custType);
		vals.put("accName", accName);
		vals.put("loanLimit", loanLimit);
		vals.put("appAmount", appAmount);
		vals.put("rateType", rateType);
		vals.put("rate", rate);
		vals.put("prate", prate);
		vals.put("mgrtype", mgrtype);
		vals.put("mrate", mrate);
		vals.put("frate", frate);
		vals.put("urate", urate);
		vals.put("endDate", endDate);
		vals.put("payDay", payDay);
		vals.put("arate", arate);
		vals.put("borAccount", borAccount);
		vals.put("payBank", payBank);
		vals.put("payAccount", payAccount);
		vals.put("payDate", payDate);
		return vals;
	}
}
