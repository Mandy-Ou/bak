package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.TreeUtil;
import com.cmw.dao.inter.sys.CompanyDaoInter;
import com.cmw.entity.sys.CompanyEntity;


/**	
 * 公司  DAO实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="公司DAO实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Repository("companyDao")
public class CompanyDaoImpl extends GenericDaoAbs<CompanyEntity, Long> implements CompanyDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String wherStr = "";
		String cmn = "checked,";
		Integer action = map.getvalAsInt("action");
		if(null != action && action.intValue() == SysConstant.ORG_ACTION_COMPANY_3){
			wherStr = "'false' as "+cmn;
		}else{
			wherStr = "'"+TreeUtil.BLANK_NULL+"' as "+cmn;
		}
		
		String hql = "select concat('C',cast(C.id as string)) as id, C.name as text," +
				"(case when C.potype = "+CompanyEntity.COMPANY_POTYPE_0+" then '0' else concat('C',cast(C.poid as string)) end) as pid,"+
				"(case when C.affiliation="+CompanyEntity.COMPANY_AFFILIATION_0+" then '"+SysConstant.COMPANY_ICONPATH_0+"' else '"+SysConstant.COMPANY_ICONPATH_1+"' end) as icon," +
				" 'false' as leaf,"+wherStr+" 2 as type,C.potype " +
		" from CompanyEntity C where 1=1 ";
		if(null == action){
			hql += " and  C.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			hql += " and  C.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		}
		
		return find(hql, "id,text,pid,icon,leaf,"+cmn+"type,potype");
	}
}