package com.cmw.dao.impl.sys;


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
import com.cmw.entity.sys.AccountEntity;
import com.cmw.dao.inter.sys.AccountDaoInter;


/**
 * 公司账户  DAO实现类
 * @author 彭登浩
 * @date 2012-12-08T00:00:00
 */
@Description(remark="公司账户DAO实现类",createDate="2012-12-08T00:00:00",author="彭登浩")
@Repository("accountDao")
public class AccountDaoImpl extends GenericDaoAbs<AccountEntity, Long> implements AccountDaoInter {
	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getEntityList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
			Integer isenabled = params.getvalAsInt("isenabled");
			
				StringBuffer sqlSb = new StringBuffer();
				sqlSb.append(" select A.id,A.isenabled,A.code,A.sysId,A.account,A.bankName, ")
				.append(" A.atype,A.isPay,A.isIncome,A.fsbankAccountId,A.refId,A.remark,B.uamount ")
				.append(" from ts_Account A left join fc_OwnFunds B on A.id = B.accountId ");
				if(StringHandler.isValidObj(isenabled)){
					sqlSb.append(" where A.isenabled ='"+isenabled+"' ");
				}else{
					sqlSb.append(" where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
				}
				
				Integer sysId = params.getvalAsInt("sysId");
				if(StringHandler.isValidObj(sysId)){
					sqlSb.append(" and A.sysId in ("+sysId+") ");
				}
				
				String bankName =  params.getvalAsStr("bankName");
				if(StringHandler.isValidStr(bankName)){
					sqlSb.append(" and A.bankName  like '"+bankName+"%' ");
				}
				Integer atype =  params.getvalAsInt("atype");
				if(StringHandler.isValidObj(atype)){
					sqlSb.append(" and A.atype  = '"+atype+"' ");	
				}
				Integer isPay =  params.getvalAsInt("isPay");
				if(StringHandler.isValidObj(isPay)){
					sqlSb.append(" and A.isPay  = '"+isPay+"' ");
				}
				Integer isIncome =  params.getvalAsInt("isIncome");
				if(StringHandler.isValidObj(isIncome)){
					sqlSb.append(" and A.isIncome  = '"+isIncome+"' ");
				}
				
				
				long totalCount = getTotalCountBySql(sqlSb.toString());
				sqlSb.append(" ORDER BY A.id  ASC ");
				String cmns = "id,isenabled,code,sysId,account,bankName,atype,isPay,isIncome,fsbankAccountId,refId,remark,uamount";
				DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}
}
