package com.cmw.service.impl.finance;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysCodeConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.FundReportDaoInter;
import com.cmw.service.inter.finance.FundReportService;
import com.cmw.service.inter.sys.GvlistService;

import edu.emory.mathcs.backport.java.util.Arrays;



/**
 * 资金头寸分析表  Service实现类
 * @author 程明卫
 * @date 2013-08-05 20:10:00
 */
@Description(remark="资金头寸分析表业务实现类",createDate="2013-08-05 20:10:00",author="程明卫")
@Service("fundReportService")
public class FundReportServiceImpl extends AbsService<Object, Long> implements  FundReportService {
	@Autowired
	FundReportDaoInter	fundReportDao;
	
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return fundReportDao;
	}
	
	
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws ServiceException {
		Integer[] cycleDays = getDays();
		setParams(map, cycleDays);
		return getDt(map,cycleDays);
	}
	
	int startRowIndex = 0;
	
	private  <K, V> DataTable getDt(SHashMap<K, V> map,Integer[] cycleDays) throws ServiceException{
		try {
			DataTable dtOwnFunds = fundReportDao.getResultList(null);
			Double uamount = dtOwnFunds.getDouble("uamount");
			if(null == uamount) uamount = 0d;
			DataTable dtLoanAmounts = fundReportDao.getResultLoan(map);
			DataTable dtPlans = fundReportDao.getResultPlan(map);
			String startDateStr = map.getvalAsStr("startDate");
			Date startDate = DateUtil.dateFormat(startDateStr);
			Double startAmounts = getCurr2StartDateAmounts(dtPlans,startDate);
			uamount += startAmounts;
		
			List<Object> dataSource = new ArrayList<Object>(cycleDays.length);
			for(Integer cycleDay : cycleDays){
				Object[] dataRow = new Object[7];
				Date limitDate = DateUtil.addDaysToDate(startDate, cycleDay);
			
				Double totalAmount = uamount;
				Double unpayAmount = getUnPayAmount(dtLoanAmounts, limitDate);
				totalAmount -= unpayAmount;
				totalAmount = StringHandler.Round(totalAmount,4);
				Double[] planAmounts = getStart2limitDateAmounts(dtPlans,limitDate);
				Double zprincipal = StringHandler.Round(planAmounts[0],4);
				Double zinterest = StringHandler.Round(planAmounts[1],4);
				Double zmgrAmount = StringHandler.Round(planAmounts[2],4);
				Double sumTotalAmount = totalAmount +  zprincipal + zinterest + zmgrAmount;
				dataRow[0] = cycleDay;
				dataRow[1] = DateUtil.dateFormatToStr(limitDate);
				dataRow[2] = StringHandler.Round(sumTotalAmount,4);
				dataRow[3] = totalAmount;
				dataRow[4] = zprincipal;
				dataRow[5] = zinterest;
				dataRow[6] = zmgrAmount;
				dataSource.add(dataRow);
			}
			String columnNames = "cycleDate,endDate,sumTotalAmount,uamount,principal,interest,mgrAmount";
			DataTable dt = new DataTable(dataSource, columnNames);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 获取当前日期到要查询的起始日期之间的 [本金、利息、管理费]
	 * @param dtPlans
	 * @param startDate
	 * @return
	 */
	private Double getCurr2StartDateAmounts(DataTable dtPlans,Date startDate){
		if(null == dtPlans || dtPlans.getRowCount() == 0) return 0d;
		Double startAmounts = 0d;
		for(int i=0,count=dtPlans.getRowCount(); i<count; i++){
			Date xpayDate = dtPlans.getDate(i, "xpayDate");
			if(null == xpayDate) continue;
			xpayDate = DateUtil.dateFormat(xpayDate);
			int result = xpayDate.compareTo(startDate);
			if(result > 0){
				startRowIndex = i;
				break;
			}
			Double zprincipal = StringHandler.getDoubleVal(dtPlans.getString(i, "zprincipal"));
			Double zinterest = StringHandler.getDoubleVal(dtPlans.getString(i, "zinterest"));
			Double zmgrAmount = StringHandler.getDoubleVal(dtPlans.getString(i, "zmgrAmount"));
			startAmounts += (zprincipal + zinterest + zmgrAmount);
		}
		return startAmounts;
	}
	
	/**
	 * 获取大于查询的起始日期到要指定最大天数（含最大天数所对应的日期）之间的 [本金、利息、管理费]之和
	 * @param dtPlans
	 * @param startDate
	 * @return
	 */
	private Double[] getStart2limitDateAmounts(DataTable dtPlans,Date limitDate){
		if(null == dtPlans || dtPlans.getRowCount() == 0) return new Double[]{0d,0d,0d};
		Double[] planAmounts = new Double[]{0d,0d,0d};
		int count=dtPlans.getRowCount();
		for(int i=startRowIndex; i<count; i++){
			Date xpayDate = dtPlans.getDate(i, "xpayDate");
			if(null == xpayDate) continue;
			xpayDate = DateUtil.dateFormat(xpayDate);
			int result = xpayDate.compareTo(limitDate);
			if(result > 0) break;
			Double zprincipal = StringHandler.getDoubleVal(dtPlans.getString(i, "zprincipal"));
			Double zinterest = StringHandler.getDoubleVal(dtPlans.getString(i, "zinterest"));
			Double zmgrAmount = StringHandler.getDoubleVal(dtPlans.getString(i, "zmgrAmount"));
			planAmounts[0] += zprincipal;
			planAmounts[1] += zinterest;
			planAmounts[2] += zmgrAmount;
		}
		return planAmounts;
	}
	

	private Double getUnPayAmount(DataTable dtLoanAmounts,Date limitDate){
		if(null == dtLoanAmounts || dtLoanAmounts.getRowCount() == 0) return 0d;
		double unpayAmounts = 0d;
		for(int i=0,count=dtLoanAmounts.getRowCount(); i<count; i++){
			Date payDate = dtLoanAmounts.getDate(i, "payDate");
			int result = payDate.compareTo(limitDate);
			if(result>0) break;
			Double uappAmount = dtLoanAmounts.getDouble(i, "uappAmount");
			unpayAmounts += uappAmount;
		}
		return unpayAmounts;
	}

	private <K, V> void setParams(SHashMap<K, V> map, Integer[] cycleDays) {
		String startDateStr = map.getvalAsStr("startDate");
		int maxDay = cycleDays[cycleDays.length-1];
		Date today = new Date();
		String currDate = DateUtil.dateFormatToStr(today);
		Date endDate = DateUtil.addDaysToDate(startDateStr, maxDay);
		map.put("currDate", currDate);
		map.put("endDate", DateUtil.dateFormatToStr(endDate));
	}
	

	private Integer[] getDays() throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("restypeIds", SysCodeConstant.RESTYPE_RECODE_200005);
		DataTable dtGvlist = gvlistService.getDataSource(map);
		if(null == dtGvlist || dtGvlist.getRowCount() == 0) throw new ServiceException(ServiceException.FUND_REPORT_CFG_ERROR);
		int count = dtGvlist.getRowCount();
		Integer[] dayArr = new Integer[count]; 
		for(int i=0; i<count; i++){
			Integer name = dtGvlist.getInteger(i,"name");
			dayArr[i] = name;
		}
		Arrays.sort(dayArr);
		return dayArr;
	}
	
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		DataTable dt = getResultList(new SHashMap<String, Object>(params));
		return dt;
	}
}
