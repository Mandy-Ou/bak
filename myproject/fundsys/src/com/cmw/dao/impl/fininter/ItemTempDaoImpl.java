package com.cmw.dao.impl.fininter;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.fininter.ItemTempDaoInter;
import com.cmw.entity.fininter.ItemTempEntity;


/**
 * 核算项  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("itemTempDao")
public class ItemTempDaoImpl extends GenericDaoAbs<ItemTempEntity, Long> implements ItemTempDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,B.name as itemClass,C.name as bussObject,A.fieldNames,A.remark ")
			.append(" from fs_ItemTemp A left join fs_ItemClass B on A.itemClassId=B.refId ")
			.append(" left join fs_FinBussObject C on A.bussObjectId=C.id ")
			.append(" where A.isenabled<> '"+SysConstant.OPTION_DEL+"' ");
			String whereStr = SqlUtil.buildWhereStr("A", params, false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(whereStr);
			}
			String colNames = "id,itemClass,bussObject,fieldNames,remark";
			DataTable dt = findBySqlPage(sb.toString(), colNames, offset, pageSize);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
