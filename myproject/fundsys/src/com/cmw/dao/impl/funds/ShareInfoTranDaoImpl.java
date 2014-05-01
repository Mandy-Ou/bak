package com.cmw.dao.impl.funds;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.RqueryApplyEntity;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.dao.inter.funds.RqueryApplyDaoInter;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;


/**
 * 汇票查询申请表  DAO实现类
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */
@Description(remark="汇票查询申请表DAO实现类",createDate="2014-02-24T00:00:00",author="郑符明")
@Repository("shareInfoTranDao")
public class ShareInfoTranDaoImpl extends GenericDaoAbs<ShareInfoTranEntity, Long> implements ShareInfoTranDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select C.id as cid,B.id as contractId,B.customerId,B.custType,B.appAmount as loanMoney,A.id as entrustCustId")
		.append("  from fu_CapitalPair C inner join  fc_LoanContract B on C.contractId=B.id  inner join   fu_EntrustCust A on C.entrustCustId=A.id")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			Long formid =params.getvalAsLng("formid");
			if(StringHandler.isValidObj(formid)){
				sb.append(" and B.formId = ("+formid+") ");
			}
			String colNames = "cid,contractId,customerId,custType,loanMoney,entrustCustId";
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
