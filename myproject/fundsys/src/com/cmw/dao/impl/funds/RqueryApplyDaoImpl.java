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
import com.cmw.dao.inter.funds.RqueryApplyDaoInter;


/**
 * 汇票查询申请表  DAO实现类
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */
@Description(remark="汇票查询申请表DAO实现类",createDate="2014-02-24T00:00:00",author="郑符明")
@Repository("rqueryApplyDao")
public class RqueryApplyDaoImpl extends GenericDaoAbs<RqueryApplyEntity, Long> implements RqueryApplyDaoInter {

	@Transactional
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sqlSb = new StringBuilder();
		
		String colNames = "id,isenabled,receiptId,qbank,aorg," +
				"account,rnum,amount,payMan,rtacname,pbank," +
				"bankNum,signOrg,appDate#yyyy-MM-dd,manager";
		
		sqlSb.append("select A.id,A.isenabled,A.receiptId,A.qbank,A.aorg,")
			.append("A.account,A.rnum,A.amount,A.payMan,A.rtacname,A.pbank,")
			.append("A.bankNum,A.signOrg,A.appDate,A.creator as manager")
			.append(" from fu_rqueryApply A")
			.append(" where A.isenabled="+SysConstant.OPTION_ENABLED);
			//UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		try{
/*			SqlHelper.addWhereByActionType(params, sqlSb, user);
			//审批时根据id查询记录
			Long id = params.getvalAsLng("id");
			if(null != id ){
				sqlSb.append(" and A.id = "+id);
			}*/
			/**
			 * 根据条件进行查询
			 * 涉及的查询字段 ： 票号:rnum,金额:amount,申请时间范围: outDate#D,endaDate#D,收款人:rtacname,承兑付款人:payMan,付款行行号:bankNum
			 */
			String rnum = params.getvalAsStr("rnum");
			if(StringHandler.isValidStr(rnum)){
				sqlSb.append(" and A.rnum like '%"+rnum+"%' ");
			}
			/**
			 * 根据金额进行查询
			 */
			String operational = params.getvalAsStr("operational");
			if(StringHandler.isValidStr(operational)){
				Double amount = params.getvalAsDob("amount");
				if(null != amount && amount.doubleValue() > 0){
					sqlSb.append(" and A.amount "+operational+" '"+amount+"' ");
				}
			}
			String outDate = params.getvalAsStr("outDate");
			if(StringHandler.isValidStr(outDate)){
				sqlSb.append(" and A.appDate >= '"+outDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){
				sqlSb.append(" and A.appDate <= '"+endDate+"' ");
			}
			
			String rtacname = params.getvalAsStr("rtacname");
			if(StringHandler.isValidStr(rtacname)){
				sqlSb.append(" and A.rtacname like '%"+rtacname+"%' ");
			}
			
			String payMan = params.getvalAsStr("payMan");
			if(StringHandler.isValidStr(payMan)){
				sqlSb.append(" and A.payMan like '"+payMan+"%' ");
			}
			
			String bankNum = params.getvalAsStr("bankNum");
			if(StringHandler.isValidStr(bankNum)){
				sqlSb.append(" and A.bankNum like '%"+bankNum+"%' ");
			}
			
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" order by A.id desc");
			
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
