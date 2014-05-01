package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.ExeItemsEntity;
import com.cmw.dao.inter.finance.ExeItemsDaoInter;


/**
 * 息费豁免列表  DAO实现类
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免列表DAO实现类",createDate="2013-09-14T00:00:00",author="程明卫")
@Repository("exeItemsDao")
public class ExeItemsDaoImpl extends GenericDaoAbs<ExeItemsEntity, Long> implements ExeItemsDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			String[] sqlMetea = getSqlMetea(map);
			String sql = sqlMetea[0];
			String colNames = sqlMetea[1];
			return findBySql(sql, colNames);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	
	}
	
	private static final <K, V> String[] getSqlMetea(SHashMap<K, V> map) throws DaoException{
		StringBuilder sbSql = new StringBuilder();
		String whereStr = "";
		String dt_cmns = null;
		Long contractId = map.getvalAsLng("contractId");
		Integer optionType = map.getvalAsInt("optionType");
		Integer etype = map.getvalAsInt("etype");
		switch (etype.intValue()) {
		case BussStateConstant.EXEMPT_ETYPE_1:{/*放款手续费豁免SQL*/
			if(optionType.intValue() == SysConstant.OPTION_ADD){/*新增*/
				sbSql.append("select A.id,(A.freeamount-A.yamount) as zfreeamount,'0' as cfat,A.status,A.exempt,B.realDate,B.contractId ")
					.append(" from fc_Free A  inner join fc_LoanInvoce B on A.loanInvoceId=B.id ");
			}else if(optionType.intValue() == SysConstant.OPTION_ADD){/*修改*/
				sbSql.append("select A.id,(A.freeamount-A.yamount) as zfreeamount,isnull(X.fat,0) as cfat,A.status,A.exempt,B.realDate,B.contractId ")
					.append(" from fc_Free A inner join fc_LoanInvoce B on A.loanInvoceId=B.id ")
					.append(" left join fc_ExeItems X ON A.id=X.formId left join fc_Exempt Y on (X.exemptId=Y.id and Y.etype='"+BussStateConstant.EXEMPT_ETYPE_1+"') ");
			}else{
				throw new DaoException("[optionType="+optionType+"] is error params!");
			}
			whereStr = " where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and B.contractId='"+contractId+"' ";
			dt_cmns = "id,zfreeamount,cfat,status,exempt,realDate#yyyy-MM-dd";
			break;
		}case BussStateConstant.EXEMPT_ETYPE_2:{
			String overStatus = BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5;
			if(optionType.intValue() == SysConstant.OPTION_ADD){/*新增*/
				sbSql.append("select B.id,((A.interest-A.yinterest-A.trinterAmount)+isnull(C.ziat,0)) as zinterest,")
					.append("((A.mgrAmount-A.ymgrAmount-A.trmgrAmount)+isnull(C.ozmat,0)) as zmgrAmount,")
					.append("((A.penAmount-A.ypenAmount-A.trpenAmount)+isnull(C.ozat,0)) as zpenAmount,")
					.append("((A.delAmount-A.ydelAmount-A.trdelAmount)+isnull(C.zdelAmount,0)) as zdelAmount,")
					.append("(B.freeamount-B.yfreeamount-B.trfreeAmount) as zfreeamount,")
					.append("'0' as zytotalAmount,'0' as rat,'0' as mat, '0' as pat, '0' as dat, '0' as fat, '0' as tat,")
					.append("ISNULL(B.predDate,B.adDate) as predDate,B.status,B.exempt from fc_Prepayment B ")
					.append("inner join  fc_Plan A ON B.payplanId=A.id ")
					.append(" left join ( ")
					.append("select (interest-yinterest-trinterAmount) as ziat,")
					.append("(mgrAmount-ymgrAmount-trinterAmount) as ozmat,")
					.append("(penAmount-ypenAmount-trinterAmount) as ozat,")
					.append("(delAmount-ydelAmount-trinterAmount) as zdelAmount,contractId")
					.append(" from fc_Plan where isenabled=1 and status in ("+overStatus+")")
					.append(") C ON B.contractId = C.contractId ");
			}else if(optionType.intValue() == SysConstant.OPTION_ADD){/*修改*/
				sbSql.append("select B.id,(A.interest-A.yinterest-A.trinterAmount) as zinterest,")
					.append("(A.mgrAmount-A.ymgrAmount-A.trmgrAmount) as zmgrAmount,")
					.append("(A.penAmount-A.ypenAmount-A.trpenAmount) as zpenAmount,")
					.append("(A.delAmount-A.ydelAmount-A.trdelAmount) as zdelAmount,")
					.append("(B.freeamount-B.yfreeamount-B.trfreeAmount) as zfreeamount,")
					.append("'0' as zytotalAmount,")
					.append("ISNULL(X.rat,0) as rat,ISNULL(X.mat,0) as mat,")
					.append("ISNULL(X.pat,0) as pat, ISNULL(X.dat,0) as dat, ISNULL(X.fat,0) as fat, ISNULL(X.totalAmount,0) as tat,")
					.append("ISNULL(B.predDate,B.adDate) as predDate,B.status,B.exempt from fc_Prepayment B ")
					.append(" inner join  fc_Plan A ON B.payplanId=A.id ")
					.append(" left join fc_ExeItems X ON A.id=X.formId left join fc_Exempt Y on (X.exemptId=Y.id and Y.etype='"+BussStateConstant.EXEMPT_ETYPE_2+"') ")
					.append(" left join (")
					.append("select (interest-yinterest) as ziat,")
					.append("(mgrAmount-ymgrAmount) as ozmat,")
					.append("(penAmount-ypenAmount) as ozat,")
					.append("(delAmount-ydelAmount) as zdelAmount,contractId")
					.append(" from fc_Plan where isenabled=1 and status in ("+overStatus+")")
					.append(") C ON B.contractId = C.contractId ");
			}
			whereStr = " where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and B.contractId='"+contractId+"' ";
			dt_cmns = "id,zinterest,zmgrAmount,zpenAmount,zdelAmount,zfreeamount,zytotalAmount,rat,mat,pat,dat,fat,tat,predDate#yyyy-MM-dd,status,exempt";
			break;
		}case BussStateConstant.EXEMPT_ETYPE_3:{
			if(optionType.intValue() == SysConstant.OPTION_ADD){/*新增*/
				sbSql.append("select A.id,A.xpayDate,A.phases,(A.interest-A.yinterest-A.trinterAmount) as zinterest,")
				.append("(A.mgrAmount-A.ymgrAmount-A.trmgrAmount) as zmgrAmount,")
				.append("(A.penAmount-A.ypenAmount-A.trpenAmount) as zpenAmount,")
				.append("(A.delAmount-A.ydelAmount-A.trdelAmount) as zdelAmount,")
				.append("'0' as zytotalAmount,")
				.append("'0' as rat,'0' as mat, '0' as pat, '0' as dat, '0' as tat,")
				.append("A.status,A.exempt from fc_Plan A ");
			}else if(optionType.intValue() == SysConstant.OPTION_EDIT){/*修改*/
				sbSql.append("select A.id,A.xpayDate,A.phases,(A.interest-A.yinterest) as zinterest,")
				.append("(A.mgrAmount-A.ymgrAmount) as zmgrAmount,")
				.append("(A.penAmount-A.ypenAmount) as zpenAmount,")
				.append("(A.delAmount-A.ydelAmount) as zdelAmount,")
				.append("'0' as zytotalAmount,")
				.append("ISNULL(X.rat,0) as rat,ISNULL(X.mat,0) as mat,")
				.append("ISNULL(X.pat,0) as pat, ISNULL(X.dat,0) as dat, ISNULL(X.totalAmount,0) as tat,")
				.append("A.status,A.exempt from fc_Plan A ")
				.append("left join fc_ExeItems X ON A.id=X.formId left join fc_Exempt Y on (X.exemptId=Y.id and Y.etype='"+BussStateConstant.EXEMPT_ETYPE_3+"') ");
			}
			String startDate = map.getvalAsStr("startDate");
			String endDate = map.getvalAsStr("endDate");
			whereStr = " where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and A.contractId='"+contractId+"' ";
			if(StringHandler.isValidStr(startDate)){
				whereStr += " and A.xpayDate>='"+startDate+"' ";
			}
			if(StringHandler.isValidStr(endDate)){
				whereStr += " and A.xpayDate<='"+endDate+"' ";
			}
			dt_cmns = "id,xpayDate#yyyy-MM-dd,phases,zinterest,zmgrAmount,zpenAmount,zdelAmount,zytotalAmount,rat,mat,pat,dat,tat,status,exempt";
			break;
		}default:
			throw new DaoException("[etype="+etype+"] is error params!");
		}
		sbSql.append(whereStr);
		return new String[]{sbSql.toString(),dt_cmns};
	}

}
