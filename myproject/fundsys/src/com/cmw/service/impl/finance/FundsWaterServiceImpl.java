package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.FundsWaterDaoInter;
import com.cmw.dao.inter.fininter.AmountLogDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.FundsWaterEntity;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.OwnFundsService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 资金流水  Service实现类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="资金流水业务实现类",createDate="2013-08-13T00:00:00",author="pdh")
@Service("fundsWaterService")
public class FundsWaterServiceImpl extends AbsService<FundsWaterEntity, Long> implements  FundsWaterService {
	@Autowired
	private FundsWaterDaoInter fundsWaterDao;
	@Autowired
	private AmountLogDaoInter amountLogDao;
	
	@Resource(name="ownFundsService")
	private OwnFundsService ownFundsService;

	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Override
	public GenericDaoInter<FundsWaterEntity, Long> getDao() {
		return fundsWaterDao;
	}
	/**
	 * 计算银行账户中的金额
	 */
	/* (non-Javadoc)			
	 * @see com.cmw.service.inter.sys.AccountService#calculate(java.util.Map)
	 */
	@Override
	public <K, V> void calculate(Map<AmountLogEntity, DataTable> logDataMap,UserEntity user)
			throws ServiceException {
		if(null == logDataMap || logDataMap.size() == 0) return;
		FundsWaterEntity fundsWater = new FundsWaterEntity();
		BeanUtil.setCreateInfo(user,fundsWater);
		Set<AmountLogEntity> logKeys = logDataMap.keySet();
		ArrayList<FundsWaterEntity> fundsWaterList = new ArrayList<FundsWaterEntity>();
		AmountLogEntity firstAmountLog = logDataMap.keySet().iterator().next();
		OwnFundsEntity ownFundsObj = getOwnFundsEntity(firstAmountLog);
		Long ownfundsId = ownFundsObj.getId();
		BigDecimal totalAmount = new BigDecimal("0");
		for(AmountLogEntity amountLog : logKeys){
			FundsWaterEntity cloneFundsWate= (FundsWaterEntity) fundsWater.clone();
			this.getMoney(amountLog,cloneFundsWate,ownfundsId);
			fundsWaterList.add( cloneFundsWate);
			BigDecimal amounts = cloneFundsWate.getAmounts();
			totalAmount = BigDecimalHandler.add2BigDecimal(totalAmount, amounts);
		}
		if(!fundsWaterList.isEmpty()){
			this.batchSaveOrUpdateEntitys(fundsWaterList);
		}
		calculateOwnFundsTotalAmount(ownFundsObj, firstAmountLog, totalAmount);
		ownFundsService.updateEntity(ownFundsObj);
	}
	private void calculateOwnFundsTotalAmount(OwnFundsEntity ownFundsObj,AmountLogEntity firstAmountLog, BigDecimal ytotalAmount){
		Integer category = firstAmountLog.getCategory();
		BigDecimal totalAmount = ownFundsObj.getTotalAmount();
		BigDecimal bamount = ownFundsObj.getBamount();
		BigDecimal uamount = ownFundsObj.getUamount();
		switch (category) {
			case BussStateConstant.AMOUNTLOG_CATEGORY_0:{//放款
				bamount = BigDecimalHandler.add2BigDecimal(bamount, ytotalAmount);
				bamount = BigDecimalHandler.roundToBigDecimal(bamount, 2);
				uamount = BigDecimalHandler.sub2BigDecimal(totalAmount, bamount);
				break;
			}case BussStateConstant.AMOUNTLOG_CATEGORY_1:
			case BussStateConstant.AMOUNTLOG_CATEGORY_2://收款
			case BussStateConstant.AMOUNTLOG_CATEGORY_3:
			case BussStateConstant.AMOUNTLOG_CATEGORY_4:
			case BussStateConstant.AMOUNTLOG_CATEGORY_6:{
				totalAmount = BigDecimalHandler.add2BigDecimal(totalAmount, ytotalAmount,2);
				uamount = BigDecimalHandler.sub2BigDecimal(totalAmount, bamount);
				break;
			}
		}
		uamount = BigDecimalHandler.roundToBigDecimal(uamount, 2);
		ownFundsObj.setTotalAmount(totalAmount);
		ownFundsObj.setBamount(bamount);
		ownFundsObj.setUamount(uamount);
	}
	
	/**	得到实际收款金额日志表中每次放款金额
	 * @param ownFundsList 
	 * @param logDt 
	 * @throws ServiceException 
	 */
	private void getMoney(AmountLogEntity amountLog,FundsWaterEntity cloneFundsWate, Long ownfundsId) throws ServiceException {
		Integer category = amountLog.getCategory();
		Integer amount_BussTag = amountLog.getBussTag();
		BigDecimal sumamount = amountLog.getSumamount();
		Long amountlogId = amountLog.getId();
		Integer waterType = null;
	
		switch (category) {
			case BussStateConstant.AMOUNTLOG_CATEGORY_0:{//放款
				waterType = BussStateConstant.FUNDSWATER_WATERTYPE_1;
				break;
			}case BussStateConstant.AMOUNTLOG_CATEGORY_1:
			case BussStateConstant.AMOUNTLOG_CATEGORY_2://收款
			case BussStateConstant.AMOUNTLOG_CATEGORY_3:
			case BussStateConstant.AMOUNTLOG_CATEGORY_4:
			case BussStateConstant.AMOUNTLOG_CATEGORY_6:{
				waterType = BussStateConstant.FUNDSWATER_WATERTYPE_2;
				break;
			}
		}
		cloneFundsWate.setAmounts(sumamount);
		cloneFundsWate.setOwnfundsId(ownfundsId);
		cloneFundsWate.setWaterType(waterType);
		cloneFundsWate.setAmountlogId(amountlogId);
		Integer water_bussTag = getWaterBussTag(amount_BussTag);
		cloneFundsWate.setBussTag(water_bussTag);
	}
	
	private OwnFundsEntity getOwnFundsEntity(AmountLogEntity amountLog) throws ServiceException{
		Long accountId = amountLog.getAccountId();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("accountId", accountId);
		params.put("isenabled", SqlUtil.LOGIC_NOT_IN+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		OwnFundsEntity ownFundsEntity = ownFundsService.getEntity(params);
		if(null != ownFundsEntity) return ownFundsEntity;
				
		String code = "O" + CodeRule.getDateString();
		Date opdate = amountLog.getOpdate();
		Long creator = amountLog.getCreator();
		Long deptId = amountLog.getDeptId();
		Long orgId = amountLog.getOrgid();
		ownFundsEntity = new OwnFundsEntity();
		ownFundsEntity.setCode(code);
		ownFundsEntity.setAccountId(accountId);
		ownFundsEntity.setLastDate(opdate);
		ownFundsEntity.setCreator(creator);
		ownFundsEntity.setCreateTime(new Date());
		ownFundsEntity.setDeptId(deptId);
		ownFundsEntity.setOrgid(orgId);
		ownFundsService.saveOrUpdateEntity(ownFundsEntity);
		return ownFundsEntity;
	}
	/**
	 * 获取银行流水的业务类型
	 * @param amount_bussTag
	 * @return
	 */
	
	private Integer getWaterBussTag(Integer amount_bussTag){
		Integer bussTag = null;
		switch (amount_bussTag) {
		case BussStateConstant.AMOUNTLOG_BUSSTAG_0:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_0;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_1:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_1;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_2:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_2;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_4:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_3;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_6:
		case BussStateConstant.AMOUNTLOG_BUSSTAG_7:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_4;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_9:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_5;
			break;
		case BussStateConstant.AMOUNTLOG_BUSSTAG_12:
			bussTag = BussStateConstant.FUNDSWATER_BUSSTAG_7;
			break;
		default:
			break;
		}
		return bussTag;
	}
	/**
	 * 处理日常收支
	 * @author pdt
	 */
		@Override
		@Transactional
		public FundsWaterEntity doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
			Long id = complexData.getvalAsLng("id");
			Long sysId = complexData.getvalAsLng("sysId");
			Integer waterType = complexData.getvalAsInt("waterType");
			Double amounts = complexData.getvalAsDob("amounts");
			String remark = complexData.getvalAsStr("remark");
			String bankName = complexData.getvalAsStr("bankName");
			String opdate = complexData.getvalAsStr("opdate");
			Long otherSort = complexData.getvalAsLng("otherSort");
			UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
			FundsWaterEntity loanEntity = null;
			OwnFundsEntity ownFundsEntity =null;
			String accountId = null;
			Long ownId = null;
			SHashMap<String, Object> ownParams = new SHashMap<String, Object>();
			if(StringHandler.isValidStr(bankName)){
				if(bankName.indexOf("##")!=-1){
					String[] bank = bankName.split("##");
					accountId = bank[0];
					ownParams.put("accountId", Long.parseLong(accountId));
					ownParams.put("isenabled", SysConstant.OPTION_ENABLED);
					ownFundsEntity = ownFundsService.getEntity(ownParams);
					if(ownFundsEntity != null){
				 ownId = ownFundsEntity.getId();
			if(waterType==2){//收
				BigDecimal totalAmount = ownFundsEntity.getTotalAmount();//累计存入金额
				BigDecimal bamount = ownFundsEntity.getBamount();//累计贷出金额
				BigDecimal uamount = ownFundsEntity.getUamount();//剩余可用金额
				totalAmount=BigDecimalHandler.get(BigDecimalHandler.add(totalAmount, BigDecimal.valueOf(amounts)));
				uamount = BigDecimalHandler.get(BigDecimalHandler.sub( totalAmount,bamount));
				ownFundsEntity.setTotalAmount(totalAmount);
				ownFundsEntity.setUamount(uamount);
				
				 }else if(waterType==1){//贷
				 	BigDecimal totalAmount = ownFundsEntity.getTotalAmount();//累计存入金额
					BigDecimal bamount = ownFundsEntity.getBamount();//累计贷出金额
					BigDecimal uamount = ownFundsEntity.getUamount();//剩余可用金额
					bamount = BigDecimalHandler.get(BigDecimalHandler.add(BigDecimal.valueOf(amounts),bamount));
					uamount = BigDecimalHandler.get(BigDecimalHandler.sub(totalAmount,bamount));
					ownFundsEntity.setBamount(bamount);
					ownFundsEntity.setUamount(uamount);
					 }
			BeanUtil.setModifyInfo(user, ownFundsEntity);
			ownFundsService.saveOrUpdateEntity(ownFundsEntity);
					}
				}
			}
				if(StringHandler.isValidObj(id)){
				//--->id存在就修改<-----------//
				loanEntity = getEntity(id);
			 	BeanUtil.setModifyInfo(user, loanEntity);
				}else{
				loanEntity=new FundsWaterEntity();
				loanEntity.setAmountlogId(-1l);
				loanEntity.setBussTag(BussStateConstant.FUNDSWATER_BUSSTAG_6);
				loanEntity.setAmounts(BigDecimal.valueOf(amounts));
				loanEntity.setOtherSort(otherSort);
				loanEntity.setOwnfundsId(ownId);
				loanEntity.setWaterType(waterType);
				loanEntity.setRemark(remark);
				BeanUtil.setCreateInfo(user, loanEntity);
			}
			saveOrUpdateEntity(loanEntity);
			//---> step 2 : 保存实收金额日志<---//
			Long ids =  loanEntity.getId();
			AmountLogEntity amountLog = this.createAmountLogsByDailyRecord(ids,user,accountId,sysId,opdate,waterType);
			if(amountLog!=null){
				Long logId = amountLog.getId();
				loanEntity.setAmountlogId(logId);
				saveOrUpdateEntity(loanEntity);
			}
			return loanEntity;
	}
			private AmountLogEntity createAmountLogsByDailyRecord(Long id, UserEntity user,String accountId,Long sysId,String opdate,Integer waterType) throws ServiceException{
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id",id);
			map.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_ENABLED);
			AmountLogEntity amountLog = null;
			try {
			amountLog = amountLogDao.getEntity(map);
			if(amountLog != null){
				/*------------step 1 更新---------*/
				amountLog = new AmountLogEntity();
				amountLog.setSysId(sysId);
				amountLog.setCategory(-1);//业务分类
				amountLog.setAccountId(Long.parseLong(accountId));
				amountLog.setInvoceIds(String.valueOf(id));//业务单据id
				amountLog.setBussTag(BussStateConstant.AMOUNTLOG_BUSSTAG_11);//业务类型
				amountLog.setOpdate(DateUtil.dateFormat(opdate));
				BeanUtil.setModifyInfo(user, amountLog);
			}else{
				/*------------step 2 创建---------*/
				amountLog = new AmountLogEntity();
				amountLog.setAccountId(Long.parseLong(accountId));
				amountLog.setOpdate(DateUtil.dateFormat(opdate));
				amountLog.setSysId(sysId);
				amountLog.setCategory(-1);//业务分类
				amountLog.setInvoceIds(String.valueOf(id));//业务单据id
				amountLog.setBussTag(BussStateConstant.AMOUNTLOG_BUSSTAG_11);//业务类型
				BeanUtil.setCreateInfo(user, amountLog);
			}
			amountLogService.saveOrUpdateEntity(amountLog);
			} catch (DaoException e) {
			e.printStackTrace();
			}
			return amountLog;
		}
			/**
			 * 更新备注
			 * @author pdt
			 */
			@Override
			public FundsWaterEntity doUpdate(
					SHashMap<String, Object> complexData)
					throws ServiceException {
				Long id = complexData.getvalAsLng("id");
				String remark = complexData.getvalAsStr("remark");
				UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
				FundsWaterEntity loanEntity = new FundsWaterEntity();
				if(StringHandler.isValidObj(id)){
					//id就是fundswater表的id
					loanEntity=getEntity(id);
					loanEntity.setRemark(remark);
					BeanUtil.setModifyInfo(user, loanEntity);
					saveOrUpdateEntity(loanEntity);
				}
				return loanEntity;
			}
}
