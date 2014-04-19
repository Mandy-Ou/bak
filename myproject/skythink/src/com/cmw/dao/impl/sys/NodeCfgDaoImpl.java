package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.NodeCfgDaoInter;
import com.cmw.entity.sys.NodeCfgEntity;


/**
 * 流程节点配置  DAO实现类
 * @author 程明卫
 * @date 2012-12-05T00:00:00
 */
@Description(remark="流程节点配置DAO实现类",createDate="2012-12-05T00:00:00",author="程明卫")
@Repository("nodeCfgDao")
public class NodeCfgDaoImpl extends GenericDaoAbs<NodeCfgEntity, Long> implements NodeCfgDaoInter {
	@Override
	public <K, V> List<NodeCfgEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		Integer actionType = map.getvalAsInt("actionType");
		if(null != actionType && actionType.intValue() == SysConstant.ACTION_TYPE_TRANSCFG_1){
			map.removes("actionType");
			return super.getEntityList(map);
		}else{
			Integer inType = map.getvalAsInt("inType");
			Long formId = map.getvalAsLng("formId");
			String hql = " from NodeCfgEntity A where A.nodeId in (" +
					"select id from BussNodeEntity B where" +
					" B.inType='"+inType+"' and B.formId='"+formId+"' and B.isenabled='"+SysConstant.OPTION_ENABLED+"')" +
					" order by id";
			return super.getList(hql);
		}
	}
}
