package com.cmw.dao.impl.finance;


import java.util.Date;

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
import com.cmw.dao.inter.finance.MinAmountDaoInter;
import com.cmw.entity.finance.MinAmountEntity;


/**
 * 最低金额配置  DAO实现类
 * @author 程明卫	id,opdate,mpamount,moamount,adman,adate,adresult,status,creatorMan,createTime,remark
 * @date 2013-02-28T00:00:00
 */
@Description(remark="最低金额配置DAO实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Repository("minAmountDao")
public class MinAmountDaoImpl extends GenericDaoAbs<MinAmountEntity, Long> implements MinAmountDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.opdate,A.mpamount,A.moamount,A.adman,A.adate,A.adresult,A.status,B.empName as creatorMan,A.isenabled,A.createTime,A.remark ")
			.append(" from MinAmountEntity A,UserEntity B where A.creator = B.userId and A.isenabled != '"+SysConstant.OPTION_DEL+"' ");
			
			Integer status = params.getvalAsInt("status");
			String startDate = params.getvalAsStr("startDate");
			String endDate = params.getvalAsStr("endDate");
			String creatorMan = params.getvalAsStr("creatorMan");
			
			if(StringHandler.isValidObj(status)){
				sb.append(" and A.status = '"+status+"' ");
			}
			
			if(StringHandler.isValidStr(startDate)){//生效日期  起始日期
				sb.append(" and A.opdate >= '"+startDate+"' ");
			}
			
			if(StringHandler.isValidStr(endDate)){//生效日期  结束日期
				endDate = DateUtil.addDays(endDate, 1);
				sb.append(" and A.opdate <= '"+endDate+"' ");
			}
			
			if(StringHandler.isValidStr(creatorMan)){//设定人
				sb.append(" and B.empName like '"+creatorMan+"%' ");
			}
			long totalCount = getTotalCount(sb.toString());
			sb.append(" order by A.opdate desc,A.status ");
			
			String columnNames = "id,opdate#yyyy-MM-dd,mpamount,moamount,adman,adate#yyyy-MM-dd,adresult,status,creatorMan,isenabled,createTime#yyyy-MM-dd,remark";
			
			DataTable dt = findByPage(sb.toString(),columnNames, offset, pageSize);
			dt.setSize(totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e);
		}
	}
	
	/**
	 * 获取 审核通过的 罚息和滞纳金 数据
	 * opdate,mpamount,moamount
	 * @return
	 * @throws DaoException 
	 */
	public DataTable getEnabledAmounts() throws DaoException{
		try{
			String today = DateUtil.dateFormatToStr(new Date());
			String hql = "select opdate,mpamount,moamount from MinAmountEntity A" +
					" where A.isenabled="+SysConstant.OPTION_ENABLED+"" +
					" and A.status="+BussStateConstant.MINAMOUNT_STATUS_3+"" +
					" and A.opdate<='"+today+"' order by opdate asc ";
			DataTable dtAmount = find(hql,"opdate,mpamount,moamount");
			return dtAmount;
		}catch (DataAccessException e) {
			throw new DaoException(e);
		}
	}
}
