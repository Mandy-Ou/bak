package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.VarietyDaoInter;
import com.cmw.entity.sys.VarietyEntity;


/**
 * 业务品种  DAO实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务品种DAO实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Repository("varietyDao")
public class VarietyDaoImpl extends GenericDaoAbs<VarietyEntity, Long> implements VarietyDaoInter {

//	@Override
//	public <K, V> List<VarietyEntity> getEntityList(SHashMap<K, V> map)
//			throws DaoException {
//		try{
//			Long sysId = map.getvalAsLng("sysId");
//			String bussType = map.getvalAsStr("bussType");
//			UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
//			StringBuffer hqlSb = new StringBuffer();
//			hqlSb.append(" FROM VarietyEntity A where  A.isenabled="+SysConstant.OPTION_ENABLED+" ");
//			hqlSb.append(" and A.sysId='"+sysId+"' ");
//			if(StringHandler.isValidObj(bussType)){
//				hqlSb.append(" and charindex(rtrim(A.bussType),'"+bussType+"')>=0 ");
//			}
//			if(null != user && null != user.getIncompId()){
//				hqlSb.append(" and (A.useorg='"+BussStateConstant.VARIETY_USEORG_0+"' ")
//				.append(" or (A.useorg='"+BussStateConstant.VARIETY_USEORG_1+"' )) ");
//			}
//			hqlSb.append(" order by A.id desc ");
//			return getList(hqlSb.toString());
//		}catch (DataAccessException e) {
//			e.printStackTrace();
//			throw new DaoException(e);
//		}
//	
//	}
	
}
