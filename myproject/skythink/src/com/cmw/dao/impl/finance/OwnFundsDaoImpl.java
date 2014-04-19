package com.cmw.dao.impl.finance;



import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.dao.inter.finance.OwnFundsDaoInter;


/**
 * 自有资金  DAO实现类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="自有资金DAO实现类",createDate="2013-08-13T00:00:00",author="pdh")
@Repository("ownFundsDao")
public class OwnFundsDaoImpl extends GenericDaoAbs<OwnFundsEntity, Long> implements OwnFundsDaoInter {

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getEntityList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
				StringBuffer sqlSb = new StringBuffer();
				sqlSb.append(" select A.id,A.isenabled,A.code,B.account,B.bankName, ")
				.append(" A.totalAmount,A.bamount,A.uamount,B.isPay,B.isIncome,B.atype ")
				.append(" from fc_OwnFunds A inner join ts_Account B on A.accountId = B.id ");
				
				Integer isenabled  = params.getvalAsInt("isenabled");
				if(StringHandler.isValidObj(isenabled)){
					sqlSb.append(" where A.isenabled ='"+isenabled+"' ");
				}else{
					sqlSb.append(" where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
				}
				String code = params.getvalAsStr("code");
				String bankName = params.getvalAsStr("bankName");
				if(StringHandler.isValidStr(code)){
					sqlSb.append(" and A.code like '"+code+"%' ");
				}
				if(StringHandler.isValidStr(bankName)){
					sqlSb.append(" and B.bankName like '"+bankName+"%' ");
				}
				long totalCount = getTotalCountBySql(sqlSb.toString());
				sqlSb.append(" ORDER BY A.id  ASC ");
				String cmns = "id,isenabled,code,account,bankName,totalAmount,bamount,uamount,isPay,isIncome,atype";
				DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}
	
	
}
