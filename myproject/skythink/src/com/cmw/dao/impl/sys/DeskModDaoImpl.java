package com.cmw.dao.impl.sys;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.DeskModDaoInter;
import com.cmw.entity.sys.DeskModEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 桌面模块配置  DAO实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="桌面模块配置DAO实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Repository("deskModDao")
public class DeskModDaoImpl extends GenericDaoAbs<DeskModEntity, Long> implements DeskModDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		StringBuffer hqlSb = new StringBuffer();
		hqlSb.append("select code,title,isdefault from DeskModEntity A" )
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
		Long sysId = map.getvalAsLng("sysId");
		hqlSb.append(" and A.sysId='"+sysId+"' ");
		hqlSb.append(" order by A.isdefault,A.orderNo ");
		try{
			return find(hqlSb.toString(), "code,title,isdefault");
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getDeskModByCodes(SHashMap<K, V> map) throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		StringBuffer hqlSb = new StringBuffer();
		String cmns = "id,sysId,code,title,cls,orderNo,msgCount,dispType,url,urlparams,isdefault,ismore,moreUrl,loadType,dataCode,dispcmns,dataTemp,isenabled";
		hqlSb.append("select "+cmns+" from DeskModEntity A" )
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
		try{
			String codes = map.getvalAsStr("code");
			String[] code = null;
			StringBuffer sb = new StringBuffer();
			
			if(StringHandler.isValidStr(codes) && codes.indexOf(",")!=-1){
				 code =  codes.split(",");
			}
			if(StringHandler.isValidStr(codes)){
				if( code.length>0){
					for(String x : code){
						sb.append("'"+x+"',");
					}
				}
			}
			codes = StringHandler.RemoveStr(sb);
			
			Integer isdefault = map.getvalAsInt("isdefault");
			String notCodes = map.getvalAsStr("notCodes");
			if(StringHandler.isValidStr(codes)){
				hqlSb.append(" and A.code in ("+codes+") ");
			}else if(!StringHandler.isValidObj(isdefault)){
				if(user != null){
					Integer isSystem = user.getIsSystem();
					if(isSystem != SysConstant.USER_ISSYSTEM){
						hqlSb.append(" and A.isdefault in ('"+BussStateConstant.DESKMOD_ISDEFAULT_1+"') ");
					}
				}
			}
//			if(StringHandler.isValidStr(notCodes)){
//				hqlSb.append(" and A.code not in ("+notCodes+") ");
//			}
			
			
			hqlSb.append(" order by A.isdefault,A.orderNo ");
			return find(hqlSb.toString(),cmns);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
	@Override
	public <K, V> DataTable getDataTableByHql(String hql,SHashMap<K, V> map)throws DaoException {
		try{
			String dispcmns = map.getvalAsStr("dispcmns");
			return find(hql, dispcmns);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getDataTableBySql(String sql,SHashMap<K, V> map)throws DaoException {
		try{
			String dispcmns = map.getvalAsStr("dispcmns");
			return findBySql(sql, dispcmns);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
}
