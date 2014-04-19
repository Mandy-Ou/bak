package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cmw.dao.inter.finance.LoanBusinessDaoInter;
import com.cmw.dao.inter.fininter.AmountLogDaoInter;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.LoanBusinessService;
import com.cmw.service.inter.fininter.AmountLogService;



/**
 * 业务统计报表  Service实现类
 * @author 彭登浩
 * @date 2013-08-05 20:10:00
 */
@Description(remark="业务统计报表业务实现类",createDate="2013-08-05 20:10:00",author="彭登浩")
@Service("loanBusinessService")
public class LoanBusinessServiceImpl extends AbsService<Object, Long> implements  LoanBusinessService {
	@Autowired
	LoanBusinessDaoInter	loanBusinessDao;
	
	@Autowired
	private AmountLogService amountLogService;
	
	@Autowired
	private AmountLogDaoInter amountLogDao;
	
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return loanBusinessDao;
	}

	/* (non-Javadoc)
	 * @see com.cmw.core.base.service.AbsService#getResultList(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws ServiceException {
		String startDate = map.getvalAsStr("startDate");
		Long sysId = map.getvalAsLng("sysId");
		UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		String nowDate = DateUtil.dateFormatToStr(new Date());
		if(!StringHandler.isValidStr(startDate)){
			startDate = nowDate;
		}else {
			Date start = DateUtil.dateFormat(startDate);
			Date now = DateUtil.dateFormat(nowDate);
			if(DateUtil.compareDate(start, now)==1){
				startDate = nowDate;
			}
		}
		DataTable dt = new DataTable("between,sumAmount,loansHave,sumInterest,loans,sumMat,sumPat,sumDat,sumFree,sum");
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysId", sysId);
		params.put("between",1);
		params.put("opdate", startDate);
		DataTable logYearDt = getLoanBusinessList(params);
		params.put("between",2);
		DataTable logMonthDt = getLoanBusinessList(params);
		params.put("between",3);
		DataTable logWeekDt = getLoanBusinessList(params);
		params.put("between",4);
		DataTable logDayDt = getLoanBusinessList(params);
		DataTable[] dataTableArray = {logYearDt,logMonthDt,logWeekDt,logDayDt};
		int i =0;
		for(DataTable dataTable : dataTableArray){
			String between  = " ";
			switch (i) {
			case 0:
				between = "当年";
				break;
			case 1:
				between = "当月";
				break;
			case 2:
				between = "当周";
				break;
			case 3:
				between = "当日";
				break;
			}
			appendDatas(user, dt, dataTable,between);
			i++;
		}
		
		return dt;
	}

	/**
	 * @param user
	 * @param dt
	 * @param dataTable
	 */
	private void appendDatas(UserEntity user, DataTable dt, DataTable dataTable,String between) {
//		if(user != null){
			BigDecimal sumAmount = new BigDecimal(0.00).setScale(2);//累计发放贷额
			BigDecimal loansHave = new BigDecimal(0.00).setScale(2);//已还贷款
			BigDecimal sumInterest = new BigDecimal(0.00).setScale(2);//累计收利息
			BigDecimal loans = new BigDecimal(0.00).setScale(2);//贷款余额
			BigDecimal sumMat = new BigDecimal(0.00).setScale(2);//累计收管理费
			BigDecimal sumPat = new BigDecimal(0.00).setScale(2);//累计收罚息
			BigDecimal sumDat = new BigDecimal(0.00).setScale(2);//累计收滞纳金
			BigDecimal sumFree = new BigDecimal(0.00).setScale(2);//手续费
			BigDecimal sum = new BigDecimal(0.00).setScale(2);//总收入
			if(null != dataTable || dataTable.getRowCount() > 0){
				for(int i=0,count = dataTable.getRowCount();i<count;i++){
					Integer category = dataTable.getInteger(i, "category");//业务分类
					String invoceIds = dataTable.getString(i, "invoceIds");//业务单据ID
					Integer bussTag = dataTable.getInteger(i, "bussTag");//业务标识
					BigDecimal amount = dataTable.getBigDecimal(i, "amount");//本金
					BigDecimal ramount = dataTable.getBigDecimal(i, "ramount");//利息
					BigDecimal mamount = dataTable.getBigDecimal(i, "mamount");//管理费
					BigDecimal pamount = dataTable.getBigDecimal(i, "pamount");//罚息金额
					BigDecimal oamount = dataTable.getBigDecimal(i, "oamount");//滞纳金
					BigDecimal famount = dataTable.getBigDecimal(i, "famount");//手续费
//						String opdate = logYearDt.getDateString(i, "opdate");//放/收日期
					switch (category) {
					case BussStateConstant.AMOUNTLOG_CATEGORY_0:
						sumAmount = BigDecimalHandler.add2BigDecimal(sumAmount, amount);
						break;
					case BussStateConstant.AMOUNTLOG_CATEGORY_1:
					case BussStateConstant.AMOUNTLOG_CATEGORY_2:
					case BussStateConstant.AMOUNTLOG_CATEGORY_3:
					case BussStateConstant.AMOUNTLOG_CATEGORY_4:
					case BussStateConstant.AMOUNTLOG_BUSSTAG_6:
						loansHave =  BigDecimalHandler.add2BigDecimal(loansHave, amount);
						sumInterest =  BigDecimalHandler.add2BigDecimal(sumInterest, ramount);
						sumMat =  BigDecimalHandler.add2BigDecimal(sumMat, mamount);
						sumMat =  BigDecimalHandler.add2BigDecimal(sumMat, famount);
						sumPat =  BigDecimalHandler.add2BigDecimal(sumPat, pamount);
						sumDat =  BigDecimalHandler.add2BigDecimal(sumDat, oamount);
						sumFree =  BigDecimalHandler.add2BigDecimal(sumFree, famount);
						break;
					default:
						break;
					}
				}
				BigDecimal[] mount =  {loansHave,sumInterest,sumMat,sumPat,sumDat,sumFree};
				for(int j=0;j<mount.length;j++){
					sum = BigDecimalHandler.add2BigDecimal(sum, mount[j]);
				}
				loans = BigDecimalHandler.sub2BigDecimal(sumAmount, loansHave);
				Object[] data  = {between,sumAmount,loansHave,sumInterest,loans,sumMat,sumPat,sumDat,sumFree,sum};
				dt.addRowData(data);
			}
//		}
	}
	
	/**
	 * @param params
	 * @return
	 */
	private DataTable getLoanBusinessList(SHashMap<String, Object> params) throws ServiceException {
		try{
			return loanBusinessDao.getLoanBusinessList(params);
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		DataTable dt = getResultList(new SHashMap<String, Object>(params));
		return dt;
	}
}
