package com.cmw.dao.impl.fininter;


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
import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.fininter.VoucherOplogDaoInter;


/**
 * 凭证日志  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证日志DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("voucherOplogDao")
public class VoucherOplogDaoImpl extends GenericDaoAbs<VoucherOplogEntity, Long> implements VoucherOplogDaoInter {

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getEntityList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
				UserEntity currUser = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
				StringBuffer sqlSb = new StringBuffer();
				sqlSb.append(" select A.id, A.vtempId,A.amountLogId,A.status, A.errCode,")
				.append("A.reason,C.empName as creatorName,A.createTime,D.code,D.name as tempName,B.bussTag ")
				.append(" from fs_VoucherOplog A inner join fs_AmountLog B on A.amountLogId = B.id ")
				.append(" inner join  ts_user C on B.creator = C.userId ")
				.append(" left join  fs_VoucherTemp D on D.id = A.vtempId ")
				.append(" where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
				
				Long sysId = params.getvalAsLng("sysId");
				if(StringHandler.isValidObj(sysId)){
					sqlSb.append(" and A.sysId in ("+sysId+") ");
				}
				Integer status = params.getvalAsInt("status");
				if(StringHandler.isValidObj(status)){
					sqlSb.append(" and A.status in ("+status+") ");
				}
				
				Integer errCode = params.getvalAsInt("errCode");
				if(StringHandler.isValidObj(errCode)){
					sqlSb.append(" and A.errCode in ("+errCode+") ");
				}
				Integer bussTag = params.getvalAsInt("bussTag");
				if(StringHandler.isValidObj(bussTag)){
					sqlSb.append(" and B.bussTag in ("+bussTag+") ");
				}
				Integer actionType = params.getvalAsInt("actionType");
				if(null != actionType && actionType.intValue() == 1){
					String whereStr = SqlUtil.appendUserFilter("A", currUser);
					if(StringHandler.isValidStr(whereStr)) sqlSb.append(whereStr);
				}
				long totalCount = getTotalCountBySql(sqlSb.toString());
				sqlSb.append(" ORDER BY A.id  DESC  ");
				String cmns = "id,vtempId,amountLogId,status,errCode,reason,empName,opdate#yyyy-MM-dd HH:mm:ss,code,tempName,bussTag";
				DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}
}
