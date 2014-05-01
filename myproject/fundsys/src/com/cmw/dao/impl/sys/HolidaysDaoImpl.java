package com.cmw.dao.impl.sys;


import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.HolidaysDaoInter;
import com.cmw.entity.sys.HolidaysEntity;


/**
 * 节假日设置  DAO实现类
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
@Description(remark="节假日设置DAO实现类",createDate="2012-11-20T00:00:00",author="程明卫")
@Repository("holidaysDao")
public class HolidaysDaoImpl extends GenericDaoAbs<HolidaysEntity, Long> implements HolidaysDaoInter {

	@Override
	public <K, V> List<HolidaysEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String Edate = (String)map.getvalAsObj("Edate");
		String Sdate = (String)map.getvalAsObj("Sdate");
		String queryDate = null;
		if(StringHandler.isValidObj(Sdate)){
			queryDate = "and  CONVERT(varchar(10) , A.sdate, 120 ) ='"+Sdate+"' ";
		}else{
			queryDate = "and  CONVERT(varchar(10) , A.edate, 120 ) ='"+Edate+"' ";
		}
//		int[] ymd = DateUtil.getYMD(eqDate);
		String hql = "from HolidaysEntity A where A.isenabled="+SysConstant.OPTION_ENABLED+"" +
//				" and (year(sdate)='"+ymd[0]+"' or year(edate)='"+ymd[0]+"') and (month(sdate)='"+ymd[1]+"' or month(edate)='"+ymd[1]+"')";
					queryDate;
		List<HolidaysEntity> holidays = super.getList(hql);
		return holidays;
	}

	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.sys.HolidaysDaoInter#getInitEntity()
	 */
	@Override
	public <K, V> List<HolidaysEntity> getInitEntity(SHashMap<K, V> map) throws DaoException {
		// TODO Auto-generated method stub
		map = SqlUtil.getSafeWhereMap(map);
		try{
			String Sdate = (String)map.getvalAsObj("Sdate");
			String Edate = (String)map.getvalAsObj("Edate");
			String queryDate = null;
			if(StringHandler.isValidObj(Sdate)){
				queryDate = "and  CONVERT(varchar(4) , A.sdate, 120 ) ='"+Sdate+"' ";
			}else{
				queryDate = "and  CONVERT(varchar(4) , A.edate, 120 ) ='"+Edate+"' ";
			}
			String hql = "from HolidaysEntity A where A.isenabled="+SysConstant.OPTION_ENABLED+"" +queryDate;
			List<HolidaysEntity> holidays = super.getList(hql);
			return holidays;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#deleteEntitys(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params) throws DaoException {
		// TODO Auto-generated method stub
		params = SqlUtil.getSafeWhereMap(params);
		try{
			String iniyear = (String)params.getvalAsObj("iniyear");
			String querySdate = null;
			if(StringHandler.isValidObj(iniyear)){
				querySdate = "  CONVERT(varchar(4) , A.sdate, 120 ) ='"+iniyear+"' or  CONVERT(varchar(4) , A.edate, 120 ) ='"+iniyear+"' ";
			}
			String hql = "DELETE FROM   HolidaysEntity as A where  "+querySdate;
			find(hql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select A.id,A.isenabled,A.name,A.sdate,A.edate,A.remark ")
				.append(" from ts_holidays A where A.isenabled !='"+SysConstant.OPTION_DEL+"'");
			
			Integer isenabled = params.getvalAsInt("isenabled");
			if(StringHandler.isValidObj(isenabled) ){
				sqlSb.append(" and A.isenabled in ("+isenabled+") ");
			}
			
			String sdate = params.getvalAsStr("sdate");
			if(StringHandler.isValidStr(sdate)){//起始日期
				sqlSb.append(" and A.sdate >= '"+sdate+"' ");
			}
			
			String edate = params.getvalAsStr("edate");
			if(StringHandler.isValidStr(edate)){//  结束日期
				edate = DateUtil.addDays(edate, 1);
				sqlSb.append(" and A.edate <= '"+edate+"' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			sqlSb.append(" ORDER BY A.id ");
			String cmns =null;
			cmns = "id,isenabled,name,sdate#yyyy-MM-dd,edate#yyyy-MM-dd,remark";
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			dt.setColumnNames(cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
