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
import com.cmw.dao.inter.finance.LoanBusinessDaoInter;


/**
 * Report  DAO实现类
 * @author 彭登浩
 * @date 2013-08-05T00:00:00
 */
@Description(remark="ReportDAO实现类",createDate="2013-08-05T00:00:00",author="彭登浩")
@Repository("loanBusinessDao")
public class LoanBusinessDaoImpl extends GenericDaoAbs<Object, Long> implements LoanBusinessDaoInter {

	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.LoanBusinessDaoInter#getDeskModByCodes(com.cmw.core.util.SHashMap)
	 */
	@Override
	public DataTable getLoanBusinessList(SHashMap<String, Object> params)
			throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sqlSb = new StringBuffer();
		Long sysId = params.getvalAsLng("sysId");
		String opdate = params.getvalAsStr("opdate");
		Integer between = params.getvalAsInt("between");
		try{
			sqlSb.append("select category ,invoceIds,bussTag,amount,ramount,mamount,pamount,oamount,famount,opdate")
			.append(" from fs_AmountLog A where A.isenabled !='"+SysConstant.OPTION_DEL+"' " );
			if(StringHandler.isValidObj(sysId)){
				sqlSb.append(" and A.sysId = '"+sysId+"' ");
			}
			if(StringHandler.isValidObj(between)){
				switch (between) {
				case 1:
					sqlSb.append(" and datediff(year,A.opdate,'"+opdate+"') =0 ");
					break;
				case 2:
					sqlSb.append(" and datediff(month,A.opdate,'"+opdate+"') =0 ");
					break;
				case 3:
					sqlSb.append(" and datediff(week,A.opdate,'"+opdate+"') =0 ");
					break;
				case 4:
					sqlSb.append(" and datediff(day,A.opdate,'"+opdate+"') =0 ");
					break;
				}
			}
			sqlSb.append(" and A.category !='"+BussStateConstant.AMOUNTLOG_CATEGORY_5+"'  ");
			sqlSb.append(" and A.bussTag !='"+BussStateConstant.AMOUNTLOG_BUSSTAG_11+"'  ");
			DataTable dt = findBySql(sqlSb.toString());
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
}
