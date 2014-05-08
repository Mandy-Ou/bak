package com.cmw.service.impl.funds;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.entity.funds.BamountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.funds.EntrustCustService;
import com.cmw.service.inter.funds.InterestRecordsService;
import com.cmw.service.inter.funds.InterestService;


/**
 * 利息支付资料  Service实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息支付实现类",createDate="2014-01-15T00:00:00",author="李听")
@Service("interestService")
public class InterestServiceImpl extends AbsService<InterestEntity, Long> implements  InterestService {
	@Autowired
	private InterestDaoInter interestDao;
	@Resource(name="interestRecordsService")
	private InterestRecordsService interestRecordsService;
	@Resource(name="entrustCustService")
	private EntrustCustService entrustCustService;
	@Resource(name="entrustContractService")
	private EntrustContractService entrustContractService;
	@Override
	public GenericDaoInter<InterestEntity, Long> getDao() {
		return interestDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = interestDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
	/**
	 * 生成还息计划
	 */
	@Override
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		//委托合同实体
		EntrustContractEntity entrustcon=(EntrustContractEntity) complexData.get("entrustContract");
		//撤资申请实体
		BamountApplyEntity bamountApply= (BamountApplyEntity)complexData.get("bamountApply");
		UserEntity user=(UserEntity)complexData.get("user");
		if(entrustcon!=null){
			SHashMap<String, Object> params=new SHashMap<String, Object>();
			params.put("entrustContractId", entrustcon.getId());
			List<InterestEntity> inteList = null;
			try {
				inteList = interestDao.getEntityList(params);
			} catch (DaoException e) {
				e.printStackTrace();
			}
			if(bamountApply!=null && inteList!=null && inteList.size()>0){
//				BigDecimal uamount=entrustcon.getAppAmount().subtract(bamountApply.getBamount());
				//获得利率
				Double rate=entrustcon.getUnint()==1?entrustcon.getRate()/100:entrustcon.getRate()/1000;
				BigDecimal bRate=new BigDecimal(rate);
				//BigDecimal的精度
//				MathContext mac=new MathContext(2);
				int m=0;
				List<InterestEntity> interests=new ArrayList<InterestEntity>();
				for(int i=inteList.size()-1;i>=0;i--){
					InterestEntity inte=inteList.get(i);
					if(inte.getStatus()==0){
						//获取退息金额
						BigDecimal riamount=null;
						if(m>0){
							riamount=bamountApply.getBamount().multiply(bRate);
						}else{
							int day=DateUtil.minusDateToDays(inte.getNextDate(), bamountApply.getBackDate());
							riamount=bamountApply.getBamount().multiply(bRate);
							double dayRate=30;
							switch (entrustcon.getRateType()) {
							case 2:
								dayRate=1;
								break;
							case 3:
								dayRate=360;
								break;
							}
							double d=riamount.doubleValue()/dayRate*day;
							riamount=new BigDecimal(d);
						}
						riamount.setScale(2, BigDecimal.ROUND_HALF_UP);
						inte.setRiamount(riamount);
						inte.setIamount(inte.getIamount().subtract(riamount));
						interests.add(inte);
						m++;
					}
				}
				try {
					interestDao.batchSaveOrUpdateEntitys(interests);
					for (InterestEntity interestEntity : interests) {
						System.out.println(interestEntity.getId());
						System.out.println(interestEntity.getXpayDate());
						System.out.println(interestEntity.getNextDate());
						System.out.println(interestEntity.getIamount().doubleValue());
						System.out.println(interestEntity.getRiamount().doubleValue());
						System.out.println("------------");
					}
				} catch (DaoException e) {
					e.printStackTrace();
				}
		}else{
			createInterest(entrustcon, user);				
		}
	}
	}
	
	
	/**
	 * 生成还息计划
	 * @param entrustcon
	 * @param user
	 * @throws ServiceException
	 */
	public void createInterest(EntrustContractEntity entrustcon, UserEntity user)
			throws ServiceException {
		Integer yue=entrustcon.getYearLoan()*12+entrustcon.getMonthLoan();
		Long entrustCustId=entrustcon.getEntrustCustId();
		Long entrustContractId=entrustcon.getId();
		Double rate=entrustcon.getUnint()==1?entrustcon.getRate()/100:entrustcon.getRate()/1000;
		BigDecimal bRate=new BigDecimal(rate);
//		MathContext mac=new MathContext(2);
		BigDecimal iamount=entrustcon.getAppAmount().multiply(bRate);
		Date payDate=entrustcon.getPayDate();
		EntrustCustEntity entrustCust=entrustCustService.getEntity(entrustCustId);
		List<InterestEntity> interests=new ArrayList<InterestEntity>();
		for(int i=0;i<yue;i++){
			InterestEntity entity=new InterestEntity();
			BeanUtil.setCreateInfo(user, entity);
			entity.setEntrustCustId(entrustCustId);
			entity.setEntrustContractId(entrustContractId);
			entity.setXpayDate(payDate);
			switch (entrustcon.getRateType()) {
			case 2:
				iamount=iamount.multiply(new BigDecimal(30));
				break;
			case 3:
				iamount=iamount.divide(new BigDecimal(12));
				break;
			}
			iamount.setScale(2, BigDecimal.ROUND_HALF_UP);
			entity.setIamount(iamount);
			Date nextDate= DateUtil.plusMonthToDate(payDate, 1);
			entity.setNextDate(nextDate);
			payDate = nextDate;
			if(entrustCust!=null && entrustCust.getCtype()==1){
				entity.setXpayDate(nextDate);
			}
			interests.add(entity);
		}
		try {
			interestDao.batchSaveEntitys(interests);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
			Long id = complexData.getvalAsLng("interestId");
			Double amt = complexData.getvalAsDob("amt");
			Double iamount = complexData.getvalAsDob("iamount");
//			String  rectDateHide=complexData.getvalAsStr("rectDateHide");
			Date nextDate =StringHandler.dateFormat("yyyy-MM-dd",complexData.getvalAsStr("nextDate"));
			Date rectDate =StringHandler.dateFormat("yyyy-MM-dd",complexData.getvalAsStr("rectDate"));
			UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
			InterestEntity interEntity = getEntity(id);
			Double iamounts=  BigDecimalHandler.sub(BigDecimal.valueOf(iamount),BigDecimal.valueOf(amt));
			BigDecimal yamount=BigDecimalHandler.add2BigDecimal(BigDecimal.valueOf(amt), interEntity.getYamount());//已付息金额
			BigDecimal amounts=new BigDecimal(iamounts);
			BigDecimal iamounte=new BigDecimal(iamount);
			if(!StringHandler.isValidObj(nextDate)){
				interEntity.setXpayDate(nextDate);
			}
			interEntity.setIamount(iamounte);//重新设置金额
			interEntity.setYamount(yamount);//已付息金额
			interEntity.setLastDate(rectDate);//最后付息日期
			Double value= BigDecimalHandler.sub(iamounte,yamount);
			if(value==0.00){
				interEntity.setStatus(2);
				interEntity.setNextDate(null);//当付息完成之后就没有下一个付息日了。
			}else{
				interEntity.setStatus(1);
			}
			saveOrUpdateEntity(interEntity);
//		SHashMap<String, Object> params = new SHashMap<String, Object>();
//		Long sysId = complexData.getvalAsLng("sysId");
//		params.put("sysId", sysId);
//		String vtempCode = complexData.getvalAsStr("vtempCode");
//		params.put("vtempCode", vtempCode);
//		params.put(SysConstant.USER_KEY, user);
//		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_0);
//		Map<InterestRecordsEntity,DataTable> logDataMap = interestRecordsService.saveByBussTag(params);
		return interEntity;
	}
}
