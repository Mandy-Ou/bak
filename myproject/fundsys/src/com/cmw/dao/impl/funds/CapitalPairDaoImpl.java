package com.cmw.dao.impl.funds;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.CapitalPairEntity;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.CapitalPairDaoInter;
import com.cmw.dao.inter.funds.ReceiptDaoInter;
@Description(remark="资金配对DAO实现类",createDate="2014-02-08T00:00:00",author="李听")
@Repository("capitalPairDao")
public class CapitalPairDaoImpl extends GenericDaoAbs<CapitalPairEntity, Long> implements CapitalPairDaoInter{
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id,B.id as eid,B.appAmount,A.status,A.code,A.name,A.sex,A.cardNum,A.birthday,A.deadline,A.products,A.phone,")
		.append(" A.contactTel,A.inAddress,A.creator,A.createTime,B.yearLoan,B.monthLoan,B.uamount,B.payDate,B.endDate from fu_EntrustContract B inner join   fu_EntrustCust A on B.entrustCustId=A.id")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			String colNames = "id,eid,appAmount,status,code,name,sex,"+
							"cardNum,birthday#yyyy-MM-dd,"+
							"deadline,products,phone,contactTel,inAddress,"+
							"creator,createTime#yyyy-MM-dd,yearLoan,monthLoan,uamount,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd";
			DataTable dt = findBySqlPage(sb.toString(),colNames, offset, pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
			}
		}
	
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select C.amt,C.entrustCustId from  fu_CapitalPair C inner join   fc_LoanContract B on C.contractId=B.id inner join  fu_EntrustCust A on C.entrustCustId=A.id")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			String colNames = "amt,entrustCustId";
			DataTable dt = findBySqlPage(sb.toString(),colNames, offset, pageSize);
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
