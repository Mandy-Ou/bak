package com.cmw.dao.impl.finance;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.FundsWaterEntity;
import com.cmw.dao.inter.finance.FundsWaterDaoInter;


/**
 * 资金流水  DAO实现类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */

@Description(remark="资金流水DAO实现类",createDate="2013-08-13T00:00:00",author="pdh")
@Repository("fundsWaterDao")
public class FundsWaterDaoImpl extends GenericDaoAbs<FundsWaterEntity, Long> implements FundsWaterDaoInter {

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getEntityList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
				try{
				StringBuffer sqlSb = new StringBuffer();
				sqlSb.append("select A.id,A.remark,C.bankName,C.account,A.waterType,A.ownfundsId," +
						"A.amounts,A.ModifyTime" +
						",B.opdate, B.bussTag ,A.otherSort,D.name")
				.append(" from fc_FundsWater A inner join fs_AmountLog B on A.amountlogId = B.id " +
						"  inner join ts_Account C on B.accountId = C.id left join ts_Gvlist D on A.otherSort=D.id " +
						"where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
				params = SqlUtil.getSafeWhereMap(params);
				
				Long waterType = params.getvalAsLng("waterType");//流水
				if(StringHandler.isValidObj(waterType)){
					sqlSb.append(" and A.waterType in ("+waterType+") ");
				}
				Long amounts = params.getvalAsLng("amounts");
				if(StringHandler.isValidObj(amounts)){//贷出金额
					sqlSb.append(" and A.amounts= '"+amounts+"' ");
				}
				String startDate = params.getvalAsStr("startDate");
				if(StringHandler.isValidStr(startDate)){//应还款款日  起始日期
					sqlSb.append(" and B.opdate >= '"+startDate+"' ");
				}
				String endDate = params.getvalAsStr("endDate");
				if(StringHandler.isValidStr(endDate)){//应还款款日  结束日期
					endDate = DateUtil.addDays(endDate, 1);
					sqlSb.append(" and B.opdate < '"+endDate+"' ");
				}		
//				Long ownfundsId = params.getvalAsLng("ownfundsId");
//				if(StringHandler.isValidObj(ownfundsId)){
//					sqlSb.append(" and A.ownfundsId in ("+ownfundsId+") ");
//				}
				
				long totalCount = getTotalCountBySql(sqlSb.toString());
				String cmns = "id,remark,bankName,account,waterType,ownfundsId,amounts,ModifyTime,opdate#yyyy-MM-dd,bussTag,otherSort,name";
				DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
		/**
		 * 日常收支管理里详情页面(查询)
		 */
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select A.id,A.waterType,A.bussTag,A.amounts,A.creator,A.createTime," +
					"B.opdate,C.bankName,C.account,A.remark,D.name ")
			//------------>fs_AmountLog(实收金额日志表) fc_FundsWater(资金流水表)	fc_OwnFunds(自有资金表)	ts_Gvlist（基础数据表）<--------------------//
			.append(" from fc_FundsWater A inner join fs_AmountLog B " +
					" on A.amountlogId=B.id inner join ts_account C  on B.accountId=C.id " +
					"inner join ts_Gvlist D on A.otherSort=D.id " +
					"where A.bussTag='"+BussStateConstant.FUNDSWATER_BUSSTAG_6+"' ");
			params = SqlUtil.getSafeWhereMap(params);
			Long waterType = params.getvalAsLng("waterType");//支付方式类型
			if(StringHandler.isValidObj(waterType)){
				sqlSb.append(" and A.waterType in ("+waterType+") ");
			}
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){//费用类型
				sqlSb.append(" and D.name= '"+name+"' ");
			}
			String bankName = params.getvalAsStr("bankName");
			if(StringHandler.isValidStr(bankName)){//银行名称
				sqlSb.append(" and C.bankName= '"+bankName+"' ");
			}
			String startDate = params.getvalAsStr("startDate");
			if(StringHandler.isValidStr(startDate)){//应还款款日  起始日期
				sqlSb.append(" and A.createTime >= '"+startDate+"' ");
			}
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){//应还款款日  结束日期
				endDate = DateUtil.addDays(endDate, 1);
				sqlSb.append(" and A.createTime < '"+endDate+"' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());
			String cmns = "id,waterType,bussTag,amounts,creator,createTime#yyyy-MM-dd,opdate#yyyy-MM-dd,bankName,account,remark,name";
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
			} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
