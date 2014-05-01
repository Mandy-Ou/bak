package com.cmw.dao.impl.funds;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SqlHelper;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.ReceiptDaoInter;


/**
 * 汇票收条表  DAO实现类
 * @author 郑符明
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票收条表DAO实现类",createDate="2014-02-08T00:00:00",author="郑符明")
@Repository("receiptDao")
public class ReceiptDaoImpl extends GenericDaoAbs<ReceiptEntity, Long> implements ReceiptDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sqlSb = new StringBuilder();
		
		String colNames = "id,isenabled,status,rnum,outMan," +
				"pbank,endDate#yyyy-MM-dd,outDate#yyyy-MM-dd,amount,rtacname,"+
				"rtaccount,rtbank,isbreceipt,isappInvoce,isabook," +
				"recetDate#yyyy-mm-dd,omaccount,rcount,reman,name,breed,procId,manager";
		
		sqlSb.append("select A.id,A.isenabled,A.status,A.rnum,A.outMan,")
			.append("A.pbank,A.endDate,A.outDate,A.amount,A.rtacname,")
			.append("A.rtaccount,A.rtbank,A.isbreceipt,A.isappInvoce,A.isabook,")
			.append("A.recetDate,A.omaccount,A.rcount,A.reman,A.name,A.breed,A.procId,A.creator as manager")
			.append(" from fu_receipt A")
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
			 * 涉及的查询字段 ： 票号:rnum,出票人:outMan,时间: outDate#D,endaDate#D,金额:amount#O,收款人账户名:rtacname,收款人账号:rtaccount
			 */
			String rnum = params.getvalAsStr("rnum");
			if(StringHandler.isValidStr(rnum)){
				sqlSb.append(" and A.rnum like '%"+rnum+"%' ");
			}
			
			String outMan = params.getvalAsStr("outMan");
			if(StringHandler.isValidStr(outMan)){
				sqlSb.append(" and A.outMan like '%"+outMan+"%' ");
			}
			
			String outDate = params.getvalAsStr("outDate");
			if(StringHandler.isValidStr(outDate)){
				//字符串转换成Date
	//			StringHandler.dateFormat("yyyy-mm-dd",outDate);
				sqlSb.append(" and A.outDate >= '"+outDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){
				sqlSb.append(" and A.endDate <= '"+endDate+"' ");
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
			
			String rtacname = params.getvalAsStr("rtacname");
			if(StringHandler.isValidStr(rtacname)){
				sqlSb.append(" and A.rtacname like '"+rtacname+"%' ");
			}
			
			String rtaccount = params.getvalAsStr("rtaccount");
			if(StringHandler.isValidStr(rtaccount)){
				sqlSb.append(" and A.rtaccount like '"+rtaccount+"%' ");
			}
			
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" order by A.id desc");
			
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	@Override
	public DataTable detail(Long id) throws DaoException {
		try{
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", id);
			DataTable dtResult = getResultList(params, -1, -1);
			return dtResult;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
