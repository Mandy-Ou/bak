package com.cmw.service.impl.finance;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LoanRateReportDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.service.inter.finance.LoanRateReportService;

import edu.emory.mathcs.backport.java.util.Arrays;



/**
 * 贷款发放利率报表  Service实现类
 * @author 程明卫
 * @date 2013-08-05 20:10:00
 */
@Description(remark="贷款发放利率报表业务实现类",createDate="2013-08-05 20:10:00",author="程明卫")
@Service("loanRateReportService")
public class LoanRateReportServiceImpl extends AbsService<LoanInvoceEntity, Long> implements  LoanRateReportService {
	@Autowired
	LoanRateReportDaoInter	loanRateReportDao;
	@Override
	public GenericDaoInter<LoanInvoceEntity, Long> getDao() {
		return loanRateReportDao;
	}
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws ServiceException {
		//loanYear,monthItems,state,rateType
		try {
			Integer loanYear = map.getvalAsInt("loanYear");
			Integer rateType = map.getvalAsInt("rateType");
			String[] monthItems = getMonthByYear(loanYear);
			if(null == monthItems || monthItems.length == 0) return null;
			map.put("monthItems", StringHandler.join(monthItems));
			String contractIds = loanRateReportDao.getContractIds(map);
			map.put("contractIds", contractIds);
			DataTable dtSource = super.getResultList(map);
			if(null == dtSource || dtSource.getRowCount() == 0) return null;
			DataTable dtMonthAmounts = loanRateReportDao.getMonthAppAmounts(map);
			DataTable dtRate = initRateDt(monthItems);
			analysis(dtRate, dtSource,dtMonthAmounts,rateType,loanYear);
			return dtRate;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	private DataTable initRateDt(String[] monthItems){
		List<Object> dataSource = new ArrayList<Object>(monthItems.length);
		for(int i=0,count=monthItems.length; i<count; i++){
			String monthItem = monthItems[i];
			Object[] row = {monthItem,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d};
			dataSource.add(row);
		}
		String columnNames = "monthItem,mavgRate,mmaxRate,mmaxAmount,mminRate,mminAmount,yavgRate,ymaxRate,ymaxAmount,yminRate,yminAmount";
		DataTable dt = new DataTable(dataSource, columnNames);
		return dt;
	}
	
	private String[] getMonthByYear(Integer loanYear){
		int[] ymd = DateUtil.getYMD(new Date());
		int currYear = ymd[0];
		int lastMonth = 12;
		if(null == loanYear || loanYear.intValue() == currYear){
			loanYear = currYear;
			lastMonth = ymd[1];
		}else if(loanYear > currYear){
			return null;
		}
		
		String[] monthItems = new String[lastMonth];
		for(int i=1; i <= lastMonth; i++){
			monthItems[i-1] = i+"";
		}
		return monthItems;
	}
	
	/**
	 * 报表利率分析
	 * @param dtRate 利率DataTable 对象
	 * @param dtSource	分析的放贷数据源
	  *@param dtMonthAmounts	每月的放贷总金额[算平均利率用]
	  *@param chooseRateType 页面用户需要分析的利率类型
	 */
	private void analysis(DataTable dtRate,DataTable dtSource,DataTable dtMonthAmounts,Integer chooseRateType,Integer loanYear){
		int len = dtSource.getRowCount();
		for(int i=0,count=dtRate.getRowCount(); i<count; i++){
			/*----> step 1 : 分析月份数据 <----*/
			analysisMonthDatas(dtRate, dtSource, dtMonthAmounts,chooseRateType, len, i);
			/*----> step 2 : 分析年度累计数据 <----*/
			if(i>0) analysisYearDatas(dtRate, dtSource, dtMonthAmounts,chooseRateType, len, i);
			
		}
		/*----> step 3 : 将第一期的年度累计数据 == 月份数据 <----*/
		Double mavgRate = dtRate.getDouble("mavgRate");
		Double mmaxRate = dtRate.getDouble("mmaxRate");
		Double mmaxAmount = dtRate.getDouble("mmaxAmount");
		Double mminRate = dtRate.getDouble("mminRate");
		Double mminAmount = dtRate.getDouble("mminAmount");
		dtRate.setCellData(0, "yavgRate", mavgRate);
		dtRate.setCellData(0, "ymaxRate", mmaxRate);
		dtRate.setCellData(0, "ymaxAmount", mmaxAmount);
		dtRate.setCellData(0, "yminRate", mminRate);
		dtRate.setCellData(0, "yminAmount", mminAmount);
		
		/*----> step 4 : 为月份加上 XXXX年XX月 <----*/
		String fmtYear = loanYear + "年";
		for(int i=0,count=dtRate.getRowCount(); i < count; i++){
			Integer monthItem = dtRate.getInteger(i, "monthItem");
			dtRate.setCellData(i, "monthItem", fmtYear+monthItem+"月");
		}
	}
	
	/**
	 * 月份数据分析（平均利率、最高利率、发生额、最低利率、发生额）
	 * @param dtRate
	 * @param dtSource
	 * @param dtMonthAmounts
	 * @param chooseRateType
	 * @param len
	 * @param i
	 */
	private void analysisMonthDatas(DataTable dtRate, DataTable dtSource,
			DataTable dtMonthAmounts, Integer chooseRateType, int len, int i) {
		Integer monthItem = dtRate.getInteger(i,"monthItem");
		Double monthAmount = getMonthAmounts(monthItem,dtMonthAmounts);
		List<Double> minOrMaxRateList = new ArrayList<Double>();/*最低或最高利率*/
		Map<Double,Double> minOrMaxAmountMap = new HashMap<Double,Double>();/*最低或最高发生额*/
		Double totalAvgRate = 0d;
		for(int j=0; j<len; j++){
			Integer _monthItem = dtSource.getInteger(j, "monthItem");
			if(!monthItem.equals(_monthItem)) continue;
			Integer rateType = dtSource.getInteger(j, "rateType");
			Double rate = dtSource.getDouble(j, "rate");
			rate = getRate(chooseRateType, rateType, rate);
			dtSource.setCellData(j, "rate", rate);
			Double appAmount = dtSource.getDouble(j, "appAmount");
			minOrMaxRateList.add(rate);
			if(minOrMaxAmountMap.containsKey(rate)){
				Double _appAmount = minOrMaxAmountMap.get(rate);
				_appAmount += appAmount;
				minOrMaxAmountMap.put(rate, _appAmount);
			}else{
				minOrMaxAmountMap.put(rate, appAmount);
			}
			Double avgRate = (appAmount / monthAmount) * rate;
			totalAvgRate += avgRate;
		}
		totalAvgRate = StringHandler.Round(totalAvgRate, 4);
		dtRate.setCellData(i, "mavgRate", totalAvgRate);//月平均利率
		setMinOrMaxRate(dtRate, i, minOrMaxRateList);
		setMinOrMaxAmount(dtRate, i, minOrMaxAmountMap);
	}
	
	private void analysisYearDatas(DataTable dtRate, DataTable dtSource,
			DataTable dtMonthAmounts, Integer chooseRateType, int len, int nextIndex) {
		Integer monthItem = dtRate.getInteger(nextIndex,"monthItem");
		Double[] minOrMaxRate = getMinOrMaxRate2Year(dtRate, nextIndex);
		Double ymaxRate = minOrMaxRate[0];
		Double yminRate = minOrMaxRate[1];
		Double monthAmount = getMonthAmountsByYear(monthItem,dtMonthAmounts);
		Double totalAvgRate = 0d;
		Double ymaxAmount = 0d;
		Double yminAmount = 0d;
		for(int j=0; j<len; j++){
			Integer _monthItem = dtSource.getInteger(j, "monthItem");
			if(monthItem < _monthItem) continue;
			Double rate = dtSource.getDouble(j, "rate");
			Double appAmount = dtSource.getDouble(j, "appAmount");
			Double avgRate = (appAmount / monthAmount) * rate;
			totalAvgRate += avgRate;
			if(rate.equals(ymaxRate)){
				ymaxAmount += appAmount;
			}
			if(rate.equals(yminRate)){
				yminAmount += appAmount;
			}
		}
		totalAvgRate = StringHandler.Round(totalAvgRate, 4);
		if(ymaxAmount>0) ymaxAmount = StringHandler.Round(ymaxAmount, 4);
		if(yminAmount>0) yminAmount = StringHandler.Round(yminAmount, 4);
		dtRate.setCellData(nextIndex, "yavgRate", totalAvgRate);
		dtRate.setCellData(nextIndex, "ymaxRate", ymaxRate);
		dtRate.setCellData(nextIndex, "ymaxAmount", ymaxAmount);
		dtRate.setCellData(nextIndex, "yminRate", yminRate);
		dtRate.setCellData(nextIndex, "yminAmount", yminAmount);
		
	}
	
	/**
	 * 获取年度累计最高利率，最低利率
	 * @param dtRate
	 * @param nextIndex
	 * @return 返回年义马最高利率，最低利率
	 */
	private Double[] getMinOrMaxRate2Year(DataTable dtRate,int nextIndex){
		double year_minRate = 1000d;//假定给一个无比大的利率值,实际业务情况不可能是 1000% 的贷款利率
		double year_maxRate = 0d;
		for(int i = 0; i <= nextIndex; i++){
			Double mmaxRate = dtRate.getDouble(i, "mmaxRate");
			Double mminRate = dtRate.getDouble(i, "mminRate");
			if(mminRate < year_minRate) year_minRate = mminRate;
			if(mmaxRate > year_maxRate) year_maxRate = mmaxRate;
		}
		return new Double[]{year_maxRate,year_maxRate};
	}
	
	/**
	 * 设置月份数据最低和最高利率
	 * @param dtRate
	 * @param i
	 * @param minOrMaxRateList
	 */
	private void setMinOrMaxRate(DataTable dtRate, int i,List<Double> minOrMaxRateList) {
		if(minOrMaxRateList.size() == 0) return;
		Double[] minOrMaxRates = new Double[1];
		minOrMaxRates = minOrMaxRateList.toArray(minOrMaxRates); 
		Arrays.sort(minOrMaxRates);
		Double mmaxRate = minOrMaxRates[minOrMaxRates.length-1];
		Double mminRate = minOrMaxRates[0];
		if(mmaxRate > 0) mmaxRate = StringHandler.Round(mmaxRate,4);
		if(mminRate > 0) mminRate = StringHandler.Round(mminRate,4);
		dtRate.setCellData(i, "mmaxRate", mmaxRate);//月最高利率
		dtRate.setCellData(i, "mminRate", mminRate);//月最低利率
	}
	
	/**
	 * 设置月份数据最低和最高发生额
	 * @param dtRate
	 * @param i
	 * @param minOrMaxAmountList
	 */
	private void setMinOrMaxAmount(DataTable dtRate, int i,Map<Double,Double> minOrMaxAmountMap) {
		if(minOrMaxAmountMap.size() == 0) return;
		 Double mmaxRate = dtRate.getDouble(i, "mmaxRate");
		 Double mminRate = dtRate.getDouble(i, "mminRate");
		Double mmaxAmount = minOrMaxAmountMap.get(mmaxRate);
		Double mminAmount = minOrMaxAmountMap.get(mminRate);
		if(mmaxAmount > 0){
			mmaxAmount = StringHandler.Round(mmaxAmount,4);
		}
		
		if(mminAmount > 0){
			mminAmount = StringHandler.Round(mminAmount,4);
		}
		dtRate.setCellData(i, "mmaxAmount", mmaxAmount);//月最高发生额
		dtRate.setCellData(i, "mminAmount", mminAmount);//月最低发生额
	}
	
	/**
	 * 获取指定月份放贷总金额(年度累计)
	 * @param monthItem	指定月份
	 * @param dtMonthAmounts	月份放贷总金额 DataTable 对象
	 * @return	返回年度月份累计放贷总金额
	 */
	private Double getMonthAmountsByYear(Integer monthItem,DataTable dtMonthAmounts){
		Double monthAmount = 0d;
		for(int i=0,count=dtMonthAmounts.getRowCount(); i<count; i++){
			Integer _monthItem = dtMonthAmounts.getInteger(i,"monthItem");
			if(_monthItem <= monthItem){
				monthAmount += dtMonthAmounts.getDouble(i, "appAmounts");
				break;
			}
		}
		return monthAmount;
	}
	
	/**
	 * 获取指定月份放贷总金额
	 * @param monthItem	指定月份
	 * @param dtMonthAmounts	月份放贷总金额 DataTable 对象
	 * @return	返回月份放贷总金额
	 */
	private Double getMonthAmounts(Integer monthItem,DataTable dtMonthAmounts){
		Double monthAmount = 0d;
		for(int i=0,count=dtMonthAmounts.getRowCount(); i<count; i++){
			Integer _monthItem = dtMonthAmounts.getInteger(i,"monthItem");
			if(_monthItem.equals(monthItem)){
				monthAmount = dtMonthAmounts.getDouble(i, "appAmounts");
				break;
			}
		}
		return monthAmount;
	}
	
	
	/**
	 * 获取换算后的利率
	 * @param chooseRateType 要转换的利率类型
	 * @param rateType	当前利率类型
	 * @param rate	要换算的利率
	 * @return	换算后的利率
	 */
	private Double getRate(Integer chooseRateType,Integer rateType,Double rate){
		Double newRate = 0d;
		switch (chooseRateType.intValue()) {
		case BussStateConstant.RATETYPE_1:
			newRate = getMothRate(rateType, rate);
			break;
		case BussStateConstant.RATETYPE_2:
			newRate = getDayRate(rateType, rate);
			break;
		case BussStateConstant.RATETYPE_3:
			newRate = getYearRate(rateType, rate);
			break;
		default:
			break;
		}
		return (null != newRate && newRate > 0) ? StringHandler.Round(newRate,4): 0d;
	}
	
	/**
	 * 获取日利率
	 * @param rateType	当前数据利率类型
	 * @param rate	利率值
	 * @return 返回换算后的日利率
	 */
	private Double getDayRate(Integer rateType,Double rate){
		switch (rateType.intValue()) {
		case BussStateConstant.RATETYPE_1:/*月 -> 日利率换算*/
			rate = rate / SysConstant.RATE_MONTH2DAY;
			break;
		case BussStateConstant.RATETYPE_3:/*年 -> 日利率换算*/
			rate = rate / SysConstant.RATE_DAY2YEAR;
			break;
		default:
			break;
		}
		return rate;
	}
	
	/**
	 * 获取月利率
	 * @param rateType	当前数据利率类型
	 * @param rate	利率值
	 * @return 返回换算后的月利率
	 */
	private Double getMothRate(Integer rateType,Double rate){
		switch (rateType.intValue()) {
		case BussStateConstant.RATETYPE_2:/*日 -> 月利率换算*/
			rate = rate * SysConstant.RATE_MONTH2DAY;
			break;
		case BussStateConstant.RATETYPE_3:/*年 -> 月利率换算*/
			rate = rate / SysConstant.RATE_MONTH2YEAR;
			break;
		default:
			break;
		}
		return rate;
	}
	
	/**
	 * 获取年利率
	 * @param rateType	当前数据利率类型
	 * @param rate	利率值
	 * @return 返回换算后的年利率
	 */
	private Double getYearRate(Integer rateType,Double rate){
		switch (rateType.intValue()) {
		case BussStateConstant.RATETYPE_1:/*月 -> 年利率换算*/
			rate = rate * SysConstant.RATE_MONTH2YEAR;
			break;
		case BussStateConstant.RATETYPE_2:/*日 -> 年利率换算*/
			rate = rate * SysConstant.RATE_DAY2YEAR;
			break;
		default:
			break;
		}
		return rate;
	}
	
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		DataTable dt = getResultList(new SHashMap<String, Object>(params));
		return dt;
	}
}
