package com.cmw.dao.impl.finance;


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
import com.cmw.dao.inter.finance.PrintTempDaoInter;
import com.cmw.entity.finance.PrintTempEntity;
import com.cmw.entity.finance.TdsCfgEntity;


/**
 * 打印模板表  DAO实现类
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="打印模板表DAO实现类",createDate="2013-11-19T00:00:00",author="赵世龙")
@Repository("PrintTempDao")
public class PrintTempDaoImpl extends GenericDaoAbs<PrintTempEntity, Long> implements PrintTempDaoInter {
	
	
	/**
	 * 获取打印模板列表
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.id,A.isenabled,A.tempType,A.code,A.name,A.custType,A.tempPath,A.createTime,A.remark,B.empName ");
			sqlSb.append("from fc_PrintTemp A inner join ts_User B on A.creator=B.userid");
			query(params, sqlSb);
			String cmns="id,isenabled,tempType,code,name,custType,tempPath,createTime#yyyy-MM-dd,remark,creator";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append("  order by  A.id DESC ");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 条件查询的附加条件
	 * @param params
	 * @param sqlSb
	 */
	private <K, V> void query(SHashMap<K, V> params, StringBuffer sqlSb) {
		String code = params.getvalAsStr("code");
		if(StringHandler.isValidStr(code)){
			sqlSb.append(" and A.code  like '%"+code+"%' ");
		}
		String name=params.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){
			sqlSb.append(" and A.name  like '%"+name+"%' ");
		}
		
		Integer custType = params.getvalAsInt("custType");
		if(StringHandler.isValidObj(custType)){
			sqlSb.append(" and A.custType  = '"+custType+"' ");
		}
		sqlSb.append(" and A.isenabled <>-1");
}
	
	@Override
	public <K, V> DataTable getTempDetail(TdsCfgEntity entity,String cmns,SHashMap<String, Object> map) throws DaoException {
		try{
			String sql = entity.getDataCode();
			String urlparams=entity.getUrlparams();
			map = SqlUtil.getSafeWhereMap(map);
			String[] ps=urlparams.split(",");
			for (String str : ps) {
				String sid=map.getvalAsStr(str);
				str="{"+str+"}";
				if(StringHandler.isValidStr(sid))
					sql=sql.replace(str,sid);
				else
					sql=sql.replace(str,"0");
					
			}
			DataTable dt = findBySql(sql,cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
	