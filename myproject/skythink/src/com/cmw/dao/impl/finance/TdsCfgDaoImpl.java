package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.finance.TdsCfgEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.dao.inter.finance.TdsCfgDaoInter;


/**
 * 模板数据源配置表  DAO实现类
 * @author 赵世龙
 * @date 2013-11-21T00:00:00
 */
@Description(remark="模板数据源配置表DAO实现类",createDate="2013-11-21T00:00:00",author="赵世龙")
@Repository("tdsCfgDao")
public class TdsCfgDaoImpl extends GenericDaoAbs<TdsCfgEntity, Long> implements TdsCfgDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		Long tempId = map.getvalAsLng("tempId");
		String hql = "select concat('D',cast(D.id as string)) as id, D.dstag as text," +
				"'0' as pid,'false' as leaf"+
		" from TdsCfgEntity as D where D.isenabled<>-1";
		if(tempId!=null){
			hql+=" and D.tempId="+tempId;
		}
		return find(hql, "id,text,pid,leaf");
	}
	
}
