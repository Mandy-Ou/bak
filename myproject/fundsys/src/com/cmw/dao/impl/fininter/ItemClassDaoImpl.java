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
import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.dao.inter.fininter.ItemClassDaoInter;


/**
 * 核算项类别  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项类别DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("itemClassDao")
public class ItemClassDaoImpl extends GenericDaoAbs<ItemClassEntity, Long> implements ItemClassDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.code,A.name,B.name as bussObject,A.refId,A.isenabled,A.createTime ")
			.append(" from fs_ItemClass A left join fs_FinBussObject B")
			.append(" on A.bussObjectId=B.id where A.isenabled != '"+SysConstant.OPTION_DEL+"' ");
			String whereStr = SqlUtil.buildWhereStr("A", params,false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(" "+whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.id desc ");
			String cmns = "id,code,name,bussObject,refId,isenabled,createTime#yyyy-MM-dd HH:mm";
			DataTable dt = findBySqlPage(sb.toString(), cmns, offset, pageSize);
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	}
}
