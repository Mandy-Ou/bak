package com.cmw.service.impl.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.CurrentDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.ReturnService;
import com.cmw.service.inter.fininter.AmountLogService;

/**
 *息费返还管理业务接口
 *Title: ReturnServiceImpl.java
 *@作者： 程明卫
 *@ 创建时间：2014-02-19 20:53
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="息费返还管理业务接口",createDate="2014-02-19 20:53",author="程明卫")
@Service("returnService")
public class ReturnServiceImpl  extends AbsService<Object, Long> implements ReturnService {
	static Logger logger = Logger.getLogger(ReturnServiceImpl.class);
	@Autowired
	private CurrentDaoInter currentDao;
	
	@Resource(name="planService")
	private PlanService planService;

	@Resource(name="planHandler")
	private PlanHandler planHandler;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return currentDao;
	}
	
	
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		try {
			String recordIds = returnBatch(complexData);
			return saveAmounts(recordIds, complexData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	private Map<AmountLogEntity,DataTable> saveAmounts(String recordIds,SHashMap<String, Object> complexData) throws ServiceException{
		//---> step 2 : 保存实收金额日志<---//
		if(!StringHandler.isValidStr(recordIds)) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("ids", recordIds);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
		params.put("vtempCode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_13);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		
		//----> ste 3 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataMap,user,true);
		return logDataMap;
	}


	/**
	 * 息费返还
	 * @param params 息费返还参数
	 */
	public String returnBatch(SHashMap<String, Object> params) throws ServiceException {
		try {
			Integer bussType = params.getvalAsInt("bussType");
			if(null == bussType) throw new ServiceException("参数:\"bussType\" 不能为空!");
			Long contractId = params.getvalAsLng("contractId");
			
			if(!StringHandler.isValidObj(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			List<PlanEntity> planList = getXplanList(contractId);
			if(null == planList || planList.size() == 0) return null;
			return returnBatch(planList, params);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 息费返还
	 * @param planList
	 * @param params
	 * @throws ServiceException
	 */
	private String returnBatch(List<PlanEntity> planList, SHashMap<String, Object> params) throws ServiceException {
		UserEntity user = (UserEntity)params.get(SysConstant.USER_KEY);
		String rectDateStr = params.getvalAsStr("rectDate");
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		double rat = params.getvalAsDob("rat");
		double mat = params.getvalAsDob("mat");
		double pat = params.getvalAsDob("pat");
		double dat = params.getvalAsDob("dat");
		Long accountId = params.getvalAsLng("accountId");
		List<AmountRecordsEntity> arList = new ArrayList<AmountRecordsEntity>();
		for(PlanEntity x : planList){
			if(rat <= 0d && mat <= 0d && pat <= 0d && dat <= 0d) break;
			BigDecimal yinterest = x.getYinterest();
			BigDecimal ymgrAmount = x.getYmgrAmount();
			BigDecimal ypenAmount = x.getYpenAmount();
			BigDecimal ydelAmount = x.getYdelAmount();
			
			//---> 返还利息、管理费、罚息、滞纳金
			BigDecimal riamount = x.getRiamount();
			BigDecimal rmamount = x.getRmamount();
			BigDecimal rpamount = x.getRpamount();
			BigDecimal rdamount = x.getRdamount();
			if(null == riamount) riamount = new BigDecimal("0");
			if(null == rmamount) rmamount = new BigDecimal("0");
			if(null == rpamount) rpamount = new BigDecimal("0");
			if(null == rdamount) rdamount = new BigDecimal("0");
			yinterest = BigDecimalHandler.sub2BigDecimal(yinterest, riamount);
			ymgrAmount = BigDecimalHandler.sub2BigDecimal(ymgrAmount, rmamount);
			ypenAmount = BigDecimalHandler.sub2BigDecimal(ypenAmount, rpamount);
			ydelAmount = BigDecimalHandler.sub2BigDecimal(ydelAmount, rdamount);
			
			double[] amoutArr = null;
			double d_tramount = 0;
			SHashMap<String, Object> data = new SHashMap<String, Object>();
			data.put("contractId", x.getContractId());
			data.put("invoceId", x.getId());
			data.put("bussTag", BussStateConstant.AMOUNTRECORDS_BUSSTAG_9);
			data.put("rectDate",rectDate);
			data.put("accountId",accountId);
			//--> 返还利息计算  <--//
			if(rat > 0){
				amoutArr = planHandler.getAmountResult(rat, yinterest.doubleValue());
				rat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("rat", b_tramount);
					riamount = BigDecimalHandler.add2BigDecimal(riamount, b_tramount);
					x.setRiamount(riamount);
				}
			}
			
			//--> 返还管理费计算  <--//
			if(mat > 0){
				amoutArr = planHandler.getAmountResult(mat, ymgrAmount.doubleValue());
				mat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("mat", b_tramount);
					rmamount = BigDecimalHandler.add2BigDecimal(rmamount, b_tramount);
					x.setRmamount(rmamount);
				}
			}
		
			//--> 返还罚息计算  <--//
			if(pat > 0){
				amoutArr = planHandler.getAmountResult(pat, ypenAmount.doubleValue());
				pat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("pat", b_tramount);
					rpamount = BigDecimalHandler.add2BigDecimal(rpamount, b_tramount);
					x.setRpamount(rpamount);
				}
			}
			
			//--> 豁免滞纳金计算  <--//
			if(dat > 0){
				amoutArr = planHandler.getAmountResult(dat, ydelAmount.doubleValue());
				pat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("dat", b_tramount);
					rdamount = BigDecimalHandler.add2BigDecimal(rdamount, b_tramount);
					x.setRdamount(rdamount);
				}
			}
			x.setTrDate(rectDate);
			x.setLastDate(rectDate);
			BeanUtil.setModifyInfo(user, x);
			planHandler.setTotalAmountByPlan(x);
			AmountRecordsEntity obj = planHandler.createAmountRecords(data,user);
			if(null != obj) arList.add(obj);
		}
		String recordIds = planHandler.saveAmountRecords(arList, BussStateConstant.CASUALRECORDS_BUSSTAG_2, user);
		planService.batchSaveOrUpdateEntitys(planList);
		return recordIds;
	}
	
	/**
	 * 获取结清或部分收款的还款计划列表
	 * @param contractId
	 * @return
	 * @throws ServiceException
	 */
	private List<PlanEntity> getXplanList(Long contractId)
			throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId",SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+contractId);
		map.put("isenabled",SysConstant.OPTION_ENABLED);
		String statusStr = BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_2+","+BussStateConstant.PLAN_STATUS_3+","+
		BussStateConstant.PLAN_STATUS_6+","+BussStateConstant.PLAN_STATUS_7+","+BussStateConstant.PLAN_STATUS_8+","+
				BussStateConstant.PLAN_STATUS_9+","+BussStateConstant.PLAN_STATUS_10;
		map.put("status",SqlUtil.LOGIC_IN + SqlUtil.LOGIC + statusStr);
		map.put(SqlUtil.ORDERBY_KEY,"desc:phases,id");
		List<PlanEntity> planList= planService.getEntityList(map);
		return planList;
	}
}
