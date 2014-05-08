package com.cmw.dao.impl.funds;


import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.EntrustCustDaoInter;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;
/**
 * 利息支付DAO实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息支付DAO实现类",createDate="2014-01-15T00:00:00",author="李听")
@Repository("interestDao")
public class InterestDaoImpl extends GenericDaoAbs<InterestEntity, Long> implements InterestDaoInter {
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id,C.code,B.name,B.cardNum,B.phone,B.inAddress,B.homeNo,C.appAmount,C.payDate,A.xpayDate,A.nextDate,A.iamount,")
		.append(" C.endDate,C.payDay,C.rate,A.yamount,A.status,A.lastDate from fu_Interest A  inner join fu_EntrustCust B on A.entrustCustId=B.id  ")
		.append(" inner join  fu_EntrustContract C  on A.entrustContractId=C.id ")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
				String entrustCustId = params.getvalAsStr("entrustCustId");//id
				if(StringHandler.isValidStr(entrustCustId)){
					sb.append(" and A.entrustCustId ='"+entrustCustId+"' ");
				}
				String entrustContractId = params.getvalAsStr("entrustContractId");//id
				if(StringHandler.isValidStr(entrustContractId)){
					sb.append(" and A.entrustContractId ='"+entrustContractId+"' ");
				}
			String colNames = "id,code,name,cardNum,phone,inAddress,homeNo,"+
							"appAmount,payDate#yyyy-MM-dd,xpayDate#yyyy-MM-dd,nextDate#yyyy-MM-dd,iamount,endDate#yyyy-MM-dd,"+
							"payDay,rate,yamount,status,lastDate#yyyy-MM-dd";
			DataTable dt = findBySqlPage(sb.toString(),colNames,offset,pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/*处理利息支付申请*/
	
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params, int offset,
			int pageSize)
			throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id,A.xpayDate,A.entrustCustId,A.entrustContractId,A.nextDate,A.iamount,A.yamount,A.riamount,A.lastDate,A.status "
				+ ",B.code,B.appAmount,B.yearLoan,B.monthLoan,B.rate,B.unint,C.name from fu_Interest A inner join fu_EntrustContract B on A.entrustContractId=B.id "
				+ " inner join fu_EntrustCust C on A.entrustCustId=C.id")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
//			String entrustCustId = params.getvalAsStr("entrustCustId");//id
//				if(StringHandler.isValidStr(entrustCustId)){
//					sb.append(" and A.entrustCustId ='"+entrustCustId+"' ");
//				}
//				String entrustContractId = params.getvalAsStr("entrustContractId");//id
//				if(StringHandler.isValidStr(entrustContractId)){
//					sb.append(" and A.entrustContractId ='"+entrustContractId+"' ");
//				}
			String colNames = "id,xpayDate#yyyy-MM-dd,entrustCustId,entrustContractId,nextDate#yyyy-MM-dd,iamount,"+
							"yamount,riamount,lastDate#yyyy-MM-dd,status,code,appAmount,yearLoan,monthLoan,rate,unint,name";
			DataTable dt = findBySqlPage(sb.toString(),colNames,offset,pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
