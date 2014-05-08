package com.cmw.dao.impl.funds;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SqlHelper;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.ReceiptMsgDaoInter;
import com.cmw.entity.funds.ReceiptMsgEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 汇票信息登记DAO实现类
 * @author 彭登浩
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票信息登记DAO实现类",createDate="2014-02-08T00:00:00",author="彭登浩")
@Repository("receiptMsgDao")
public class ReceiptMsgDaoImpl extends GenericDaoAbs<ReceiptMsgEntity, Long> implements ReceiptMsgDaoInter{
/*	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		//rnum,name,serviceMan,amount,fundsDate,ticketMan
		return super.getResultList(map);
	}*/
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V>params, int offset,
			int pageSize) throws DaoException{
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sqlSb = new StringBuilder();
		String colNames = "id,isenabled,code,rnum,amount,rate,tiamount,name," +
				"ticketDate#yyyy-MM-dd,ticketMan,fundsDate#yyyy-MM-dd,"+
				"ticketRate,funds,adultMoney,interest,remark,empName";
		
		sqlSb.append("select A.id,A.isenabled,A.code,A.rnum,A.amount,A.rate,")
				.append("A.tiamount,A.name,A.ticketDate,A.ticketMan,A.fundsDate,")
				.append("A.ticketRate,A.funds,A.adultMoney,A.interest,A.remark,U.empName")
				.append(" from fu_ReceiptMsg A left join ts_User U on A.serviceMan=U.userId")
				.append(" where A.isenabled="+SysConstant.OPTION_ENABLED);
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		try{
			SqlHelper.addWhereByActionType(params, sqlSb, user);
			//审批时根据id查询记录
			Long id = params.getvalAsLng("id");
			if(null != id ){
				sqlSb.append(" and A.id = "+id);
			}
			
		/**
		 * 涉及的查询字段 ：票号:rnum,供票人：name,业务员：serviceMan,金额：amount,
		 * 资金到账日期：时间: outDate#D,endaDate#D fundsDate,收票人：ticketMan
		 */
			String rnum = params.getvalAsStr("rnum");
			if(StringHandler.isValidStr(rnum)){
				sqlSb.append(" and A.rnum like '%"+rnum+"%' ");
			}
			
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and A.name like '%"+name+"%' ");
			}
			
			String serviceMan = params.getvalAsStr("serviceMan");
			if(StringHandler.isValidStr(serviceMan)){
				sqlSb.append(" and A.serviceMan like '"+serviceMan+"%' ");
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
				//字符串转换成Date
	//			StringHandler.dateFormat("yyyy-mm-dd",outDate);
				sqlSb.append(" and A.fundsDate >= '"+outDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){
				sqlSb.append(" and A.fundsDate <= '"+endDate+"' ");
			}
								
			String ticketMan = params.getvalAsStr("ticketMan");
			if(StringHandler.isValidStr(ticketMan)){
				sqlSb.append(" and A.ticketMan like '"+ticketMan+"%' ");
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
