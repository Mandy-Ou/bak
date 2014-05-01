package com.cmw.dao.impl.finance;


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
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.dao.inter.finance.PamountRecordsDaoInter;
import com.cmw.dao.inter.finance.ProjectAmuntDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.PamountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;


/**
 * 项目费用表 DAO实现类
 * @author liitng
 * @date 2013-01-15T00:00:00
 */
@Description(remark="项目费用表DAO实现类",createDate="2013-01-15T00:00:00",author="liting")
@Repository("pamountRecordsDao")
public class PamountRecordsDaoImpl extends GenericDaoAbs<PamountRecordsEntity, Long> implements PamountRecordsDaoInter{
	
	
//	@Override
//	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
//			int pageSize) throws DaoException {try{
//			params = SqlUtil.getSafeWhereMap(params);
//			StringBuffer sqlSb = new StringBuffer();
//			sqlSb.append("select A.id,A.amount,A.rat,A.mat,A.tat,A.rectDate,B.account as account ,B.bankName as bankName ")
//				.append(" from fc_PamountRecords A inner join ts_Account B on A.accountId = B.id ")
//				.append(" where  A.bussTag in ('"+BussStateConstant.AMOUNTRECORDS_BUSSTAG_0+"') ")
//				.append(" and A.isenabled = '"+SysConstant.OPTION_ENABLED+"' ");
//			
//			Long contractId = params.getvalAsLng("contractId");
//			if(StringHandler.isValidObj(contractId)){
//				sqlSb.append(" and A.contractId = '"+contractId+"' ");
//			}
//			String sqlStr = sqlSb.toString();
//			String colNames = "bussTag,cat,rat,mat,"
//					+ "tat,rectDate#yyyy-MM-dd,account,bankName";
//			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize);
//			return dt;
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			throw new DaoException(e);
//		}
//}
	}
