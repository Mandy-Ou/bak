package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.BussTransDaoInter;
import com.cmw.entity.sys.BussTransEntity;


/**
 * 流转路径  DAO实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径DAO实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Repository("bussTransDao")
public class BussTransDaoImpl extends GenericDaoAbs<BussTransEntity, Long> implements BussTransDaoInter {
	@Override
	public <K, V> List<BussTransEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		Integer actionType = map.getvalAsInt("actionType");
		if(null != actionType && actionType.intValue() == SysConstant.ACTION_TYPE_TRANSCFG_1){
			map.removes("actionType");
			return super.getEntityList(map);
		}else{
			Integer inType = map.getvalAsInt("inType");
			Long formId = map.getvalAsLng("formId");
			String hql = " from BussTransEntity A where A.bnodeId in (" +
					"select id from BussNodeEntity B where" +
					" B.inType='"+inType+"' and B.formId='"+formId+"' and B.isenabled='"+SysConstant.OPTION_ENABLED+"')" +
					" order by id";
			return super.getList(hql);
		}
	}
}
