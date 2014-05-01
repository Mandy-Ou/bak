package com.cmw.dao.impl.funds;



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
import com.cmw.dao.inter.funds.InterestRecordsDaoInter;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
/**
 * 利息支付DAO实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息记录DAO实现类",createDate="2014-01-15T00:00:00",author="李听")
@Repository("interestRecordsDao")
public class InterestRecordsDaoImpl extends GenericDaoAbs<InterestRecordsEntity, Long> implements InterestRecordsDaoInter {
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.isenabled,A.id,")
		.append(" A.amt,A.rectDate,settleType from fu_InterestRecords A ")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			sb.append(" order by A.id desc ");
			String colNames = "isenabled,id,amt,rectDate#yyyy-MM-dd,settleType";
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
