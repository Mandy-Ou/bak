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
import com.cmw.dao.inter.finance.PrepaymentRecordsDaoInter;
import com.cmw.entity.finance.PrepaymentRecordsEntity;


/**
 * 实收提前还款金额  DAO实现类
 * @author 程明卫
 * @date 2013-11-03T00:00:00
 */
@Description(remark="实收提前还款金DAO实现类",createDate="2013-11-03T00:00:00",author="程明卫")
@Repository("prepaymentRecordsDao")
public class PrepaymentRecordsDaoImpl extends GenericDaoAbs<PrepaymentRecordsEntity, Long> implements PrepaymentRecordsDaoInter {
	
	/**
	 * 获取逾期实收金额记录信息
	 * @param params
	 * @return 
	 * @throws DaoException 
	 */
	public DataTable getLateRecords(SHashMap<String, Object> params) throws DaoException{
		try{
			StringBuffer sbhql = new StringBuffer();
			params = SqlUtil.getSafeWhereMap(params);
			sbhql.append("select A.invoceId,cat,rat,mat,pat,dat,rectDate,bussTag from PrepaymentRecordsEntity A ")
				.append(" where isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			Object bussTag = params.get("bussTag");
			if(StringHandler.isValidObj(bussTag)){
				sbhql.append(" and A.bussTag in ("+bussTag+") ");
			}
			
			Object invoceIds = params.get("invoceIds");
			if(StringHandler.isValidObj(invoceIds)){
				sbhql.append(" and A.invoceId in ("+invoceIds+") ");
			}
			sbhql.append(" order by A.invoceId,A.rectDate ");
			return find(sbhql.toString(), "invoceId,cat,rat,mat,pat,dat,rectDate,bussTag");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		try{
			return super.getResultList(params, offset, pageSize);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
