package com.cmw.dao.impl.funds;


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
import com.cmw.entity.funds.BackReceiptEntity;
import com.cmw.dao.inter.funds.BackReceiptDaoInter;


/**             
 * 回款收条表  DAO实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="回款收条表DAO实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Repository("backReceiptDao")
public class BackReceiptDaoImpl extends GenericDaoAbs<BackReceiptEntity, Long> implements BackReceiptDaoInter {

	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sqlSb = new StringBuilder();
		
		String colNames = "id,isenabled,receiptId,name,reman," +
				"rcount,rnum,outMan,omaccount,pbank,outDate#yyyy-MM-dd," +
				"endDate#yyyy-MM-dd,amount,rtacname,rtaccount,rtbank,recetDate#yyyy-MM-dd,manager";
		
		sqlSb.append("select A.id,A.isenabled,A.receiptId,A.name,A.reman,")
			.append("A.rcount,A.rnum,A.outMan,A.omaccount,A.pbank,A.outDate,")
			.append("A.endDate,A.amount,A.rtacname,A.rtaccount,A.rtbank,A.recetDate,A.creator as manager")
			.append(" from fu_backReceipt A")
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
			 * 涉及的查询字段 ： 客户姓名:name,金额:amount,时间范围: outDate,endaDate,票号:rnum,收款人账户名:rtacname,收款人账号:rtaccount
			 */
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and A.name like '"+name+"%' ");
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
			System.out.println(outDate);
			if(StringHandler.isValidStr(outDate)){
				sqlSb.append(" and A.outDate >= '"+outDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){
				sqlSb.append(" and A.endDate <= '"+endDate+"' ");
			}
			
			
			String rnum = params.getvalAsStr("rnum");
			if(StringHandler.isValidStr(rnum)){
				sqlSb.append(" and A.rnum like '%"+rnum+"%' ");
			}
			String rtacname = params.getvalAsStr("rtacname");
			if(StringHandler.isValidStr(rtacname)){
				sqlSb.append(" and A.rtacname like '%"+rtacname+"%' ");
			}
			
			String rtaccount = params.getvalAsStr("rtaccount");
			if(StringHandler.isValidStr(rtaccount)){
				sqlSb.append(" and A.rtaccount like '%"+rtaccount+"%' ");
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
