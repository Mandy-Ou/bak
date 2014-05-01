package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.entity.sys.MatSubjecEntity;
import com.cmw.dao.inter.sys.MatSubjecDaoInter;


/**
 * 资料标题  DAO实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料标题DAO实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Repository("matSubjecDao")
public class MatSubjecDaoImpl extends GenericDaoAbs<MatSubjecEntity, Long> implements MatSubjecDaoInter {

	@Override
	public <K, V> List<MatSubjecEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		try {
			String whereStr = SqlUtil.buildWhereStr("A", map);
			whereStr += " order by A.orderNo,A.id ";
			List<MatSubjecEntity> list = getList("from MatSubjecEntity A "+whereStr);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
