package com.cmw.service.impl.funds;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.sys.UserEntity;
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
