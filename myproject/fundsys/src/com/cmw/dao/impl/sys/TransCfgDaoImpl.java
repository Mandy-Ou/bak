package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.TransCfgDaoInter;
import com.cmw.entity.sys.TransCfgEntity;


/**
 * 流转路径配置  DAO实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径配置DAO实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Repository("transCfgDao")
public class TransCfgDaoImpl extends GenericDaoAbs<TransCfgEntity, Long> implements TransCfgDaoInter {
	@Override
	public <K, V> List<TransCfgEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		Integer actionType = map.getvalAsInt("actionType");
		if(null != actionType && actionType.intValue() == SysConstant.ACTION_TYPE_TRANSCFG_1){
			map.removes("actionType");
			return super.getEntityList(map);
		}else{
			String nodeIds = map.getvalAsStr("nodeIds");
			String hql = " from TransCfgEntity A where A.nodeId in ("+nodeIds+") and A.isenabled='"+SysConstant.OPTION_ENABLED+"' order by id";
			return super.getList(hql);
		}
	}
}
