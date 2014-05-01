package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.FormCfgDaoInter;
import com.cmw.entity.sys.FormCfgEntity;


/**
 * 节点表单配置  DAO实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="节点表单配置DAO实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Repository("formCfgDao")
public class FormCfgDaoImpl extends GenericDaoAbs<FormCfgEntity, Long> implements FormCfgDaoInter {
	@Override
	public <K, V> List<FormCfgEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String nodeIds = map.getvalAsStr("nodeIds");
		String hql = " from FormCfgEntity A where A.nodeId in ("+nodeIds+") and A.isenabled='"+SysConstant.OPTION_ENABLED+"' " +
				" order by A.orderNo, A.id ";
		return super.getList(hql);
	}
}
