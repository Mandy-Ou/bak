package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.entity.finance.GopinionEntity;
import com.cmw.dao.inter.finance.GopinionDaoInter;


/**
 * 担保人意见  DAO实现类
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="担保人意见DAO实现类",createDate="2013-09-08T00:00:00",author="程明卫")
@Repository("gopinionDao")
public class GopinionDaoImpl extends GenericDaoAbs<GopinionEntity, Long> implements GopinionDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String hql = "select concat('D',cast(D.id as string)) as id, D.guarantor as text," +
				"'0' as pid,'true' as leaf"+
		" from GopinionEntity as D where D.isenabled=1";
		return find(hql, "id,text,pid,leaf");
	}
}
