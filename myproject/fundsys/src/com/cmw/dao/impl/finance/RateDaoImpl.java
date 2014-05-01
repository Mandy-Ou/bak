package com.cmw.dao.impl.finance;


import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.dao.inter.finance.RateDaoInter;
import com.cmw.entity.finance.RateEntity;


/**
 * 利率  DAO实现类
 * @author 彭登浩
 * @date 2012-12-06T00:00:00
 */
@Description(remark="利率DAO实现类",createDate="2012-12-06T00:00:00",author="彭登浩")
@Repository("rateDao")
public class RateDaoImpl extends GenericDaoAbs<RateEntity, Long> implements RateDaoInter {

	@Override
	public <K, V> DataTable getResultList()throws DaoException {
		try{
			StringBuffer hqlSb = new StringBuffer();
			Date nextDate = DateUtil.addDaysToDate(new Date(), 1);
			String nextDateStr = DateUtil.dateFormatToStr(nextDate);
			hqlSb.append("select A.types,A.limits,A.val from RateEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			hqlSb.append(" and A.bdate < '"+nextDateStr+"' ");
			hqlSb.append(" order by A.bdate desc ");
			return find(hqlSb.toString(),"types,limits,val");
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
}
