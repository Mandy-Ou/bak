package com.cmw.dao.impl.funds;


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
import com.cmw.entity.funds.ShareDataEntity;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.dao.inter.funds.RqueryApplyDaoInter;
import com.cmw.dao.inter.funds.ShareDataDaoInter;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;


/**
 * 汇票查询申请表  DAO实现类
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */
@Description(remark="汇票查询申请表DAO实现类",createDate="2014-02-24T00:00:00",author="郑符明")
@Repository("shareDataDao")
public class ShareDataDaoImpl extends GenericDaoAbs<ShareDataEntity, Long> implements ShareDataDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id,B.id as eid,B.appAmount,A.status,A.code,A.name,A.sex,A.cardNum,A.birthday,A.deadline,A.products,A.phone,")
		.append(" A.contactTel,A.inAddress,A.creator,A.createTime from   fu_EntrustContract B inner join   fu_EntrustCust A on B.entrustCustId=A.id")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			String colNames = "id,eid,appAmount,status,code,name,sex,"+
							"cardNum,birthday#yyyy-MM-dd,"+
							"deadline,products,phone,contactTel,inAddress,"+
							"creator,createTime#yyyy-MM-dd";
			DataTable dt = findBySqlPage(sb.toString(),colNames, offset, pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}}
