package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LoanContractDaoInter;
import com.cmw.dao.inter.finance.PlanDaoInter;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.service.inter.finance.LoanContractService;


/**
 * 借款合同  Service实现类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="借款合同业务实现类",createDate="2013-01-11T00:00:00",author="pdh")
@Service("loanContractService")
public class LoanContractServiceImpl extends AbsService<LoanContractEntity, Long> implements  LoanContractService {
	@Autowired
	private LoanContractDaoInter loanContractDao;
	@Autowired
	private PlanDaoInter planDao;
	@Override
	public GenericDaoInter<LoanContractEntity, Long> getDao() {
		return loanContractDao;
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.LoanContractService#getSuperList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public DataTable getSuperList(SHashMap<String, Object> dtParams,
			int offset, int pageSize) throws ServiceException {
		try {
			return loanContractDao.getSuperList(dtParams,offset,pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.GuaContractService#getDataSource(java.util.HashMap)
	 */
	@Override
	public DataTable getDataSource(HashMap<String, Object> params) throws ServiceException {
		try {
			return loanContractDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.LoanContractService#getContractResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public DataTable getContractResultList(SHashMap<String, Object> map,int start, int limit) throws ServiceException {
		try{
			 return loanContractDao.getContractResultList(map,start,limit);
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.LoanContractService#getReportList(com.cmw.core.util.SHashMap, int, int)
	 */
	public String getReportList(SHashMap<String, Object> map, int start,
			int limit) throws ServiceException {
		try {
			String queryDate = map.getvalAsStr("queryDate");
			Date qDate = DateUtil.dateFormat(queryDate);
			DataTable dt = loanContractDao.getContractResultList(map, start, limit);
			StringBuffer htmlSb = new StringBuffer();
			if(null != dt || dt.getRowCount() > 0){
				BigDecimal sumYprincipal = new BigDecimal(0.00);//实收本金和
				BigDecimal sumReprincipal = new BigDecimal(0.00);//剩余本金和
				BigDecimal sumYinterest = new BigDecimal(0.00);//实收利息和
				BigDecimal sumRate = new BigDecimal(0.00);//财务累计
				BigDecimal sumPrincipal = new BigDecimal(0.00);//本金和
				List<JSONObject> dataList = new ArrayList<JSONObject>();
				for(Integer i =0; i<dt.getRowCount();i++){
					JSONObject data = new JSONObject();
					String contractCode = dt.getString(i, "contractCode");
					String custName = dt.getString(i, "custName");
					String applyCode = dt.getString(i, "applyCode");
					String loanLimit = dt.getString(i, "loanLimit");
					String endDate = dt.getDateString(i, "endDate");
					Integer rateType = dt.getInteger(i, "rateType");
					Double rate = dt.getDouble(i, "rate");
					String type = "";
					Double yrate = 0.00;
					Double mrate = 0.00;
					switch (rateType) {
					case BussStateConstant.RATETYPE_1:
						mrate = rate;
						yrate = yrate*SysConstant.RATE_MONTH2YEAR;
						type = "按月";
						break;
					case BussStateConstant.RATETYPE_2:
						mrate = rate*SysConstant.RATE_MONTH2DAY;
						yrate = rate*SysConstant.RATE_MONTH2YEAR;
						type = "按日";
						break;
					case BussStateConstant.RATETYPE_3:
						mrate = BigDecimalHandler.round(rate/SysConstant.RATE_DAY2YEAR, 2);
						yrate = rate;
						type = "按年";
						break;
					}
					data.put("yrate", yrate);
					data.put("mrate", mrate);
					BigDecimal appAmount = dt.getBigDecimal(i, "appAmount");
					Integer payDay = dt.getInteger(i, "payDay");
					
					data.put("contractCode", contractCode);
					data.put("custName", custName);
					data.put("applyCode", applyCode);
					data.put("loanLimit", loanLimit);
					data.put("endDate", endDate);
					data.put("rateType", rateType);
					data.put("appAmount", appAmount);
					data.put("payDay", payDay);
					data.put("qDate", qDate);
					String contractId = dt.getString(i, "contractId");
					if(StringHandler.isValidStr(contractId)){
						SHashMap<String, Object> params = new SHashMap<String, Object>();
						params.put("contractId", Long.parseLong(contractId));
						List<PlanEntity> planList = planDao.getEntityList(params);
						if(!planList.isEmpty() && planList.size()>0){
							for(PlanEntity x: planList){
								Date xpayDate = x.getXpayDate();
								if(xpayDate.before(qDate) || xpayDate.equals(qDate)){
									BigDecimal principal = x.getPrincipal();//本金、
									sumPrincipal = BigDecimalHandler.get(BigDecimalHandler.add(sumPrincipal, principal));
									BigDecimal yprincipal = x.getYprincipal();//实收本金
									sumYprincipal = BigDecimalHandler.get(BigDecimalHandler.add(sumYprincipal, yprincipal));
									data.put("sumYprincipal", sumYprincipal);
									sumReprincipal = BigDecimalHandler.sub2BigDecimal(sumPrincipal, sumYprincipal);
									data.put("sumReprincipal", sumReprincipal);
									
									BigDecimal yinterest = x.getYinterest();//实收利息
									sumYinterest = BigDecimalHandler.get(BigDecimalHandler.add(sumYinterest, yinterest));
									data.put("sumYinterest", sumYinterest);
								}
								BigDecimal interest = x.getInterest();//每期应收利息
								sumRate = BigDecimalHandler.add2BigDecimal(interest, sumRate);
								data.put("sumRate", sumRate);
							}
						}
					}
					dataList.add(data);
				}
				getHtml(htmlSb,dataList);
			}
			return htmlSb.toString();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		
	}

	/**
	 * @param dataList
	 */
	private void getHtml(StringBuffer htmlSb,List<JSONObject> dataList) {
		
		if(dataList.size()>0 && !dataList.isEmpty()){
			for(JSONObject data : dataList){
				htmlSb.append("<tr>");
				Object contractCode = data.get("contractCode");
				htmlSb.append(getHtmlTd(contractCode));
				
				Object custName = data.get("custName");
				htmlSb.append(getHtmlTd(custName));
				
				Object applyCode = data.get("applyCode");
				htmlSb.append(getHtmlTd(applyCode));
				
				Object appAmount = StringHandler.fmtMicrometer(data.get("appAmount"));
				htmlSb.append(getHtmlTd(appAmount));
				
				Object payDate = data.get("payDate");
				htmlSb.append(getHtmlTd(payDate));
				
				Object loanLimit = data.get("loanLimit");
				htmlSb.append(getHtmlTd(loanLimit));
				
				Object payDay = data.get("payDay");
				htmlSb.append(getHtmlTd(payDay));
				htmlSb.append(getHtmlTd(payDay));
				
				Object sumYprincipal = StringHandler.fmtMicrometer( data.get("sumYprincipal"));
				htmlSb.append(getHtmlTd(sumYprincipal));
				
				Object sumReprincipal = StringHandler.fmtMicrometer( data.get("sumReprincipal"));
				htmlSb.append(getHtmlTd(sumReprincipal));
				
				Object type = data.get("type");
				htmlSb.append(getHtmlTd(type));
				
				Object yrate = data.get("yrate")+"%";
				htmlSb.append(getHtmlTd(yrate));
				
				Object mrate = data.get("mrate")+"%";
				htmlSb.append(getHtmlTd(mrate));
				
				Object sumYinterest = StringHandler.fmtMicrometer(data.get("sumYinterest"));
				htmlSb.append(getHtmlTd(sumYinterest));
				
				Object sumRate = StringHandler.fmtMicrometer(data.get("sumRate"));
				htmlSb.append(getHtmlTd(sumRate));
				
				Object qDate = StringHandler.fmtMicrometer(data.get("qDate"));
				htmlSb.append(getHtmlTd(qDate));
				htmlSb.append("</tr>");
			}
		}
		
	}

	private String getHtmlTd(Object cellData){
		return "<td>"+cellData+"</td>";
	}
}
