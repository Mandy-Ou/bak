package com.cmw.dao.impl.fininter;


import java.util.ArrayList;
import java.util.List;

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
import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.dao.inter.fininter.SubjectDaoInter;


/**
 * 科目  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="科目DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("subjectDao")
public class SubjectDaoImpl extends GenericDaoAbs<SubjectEntity, Long> implements SubjectDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.code,A.name,A.levels,A.detail,")
			.append("A.dc,B.name as currency,A.atype,D.name as itemClass,C.name as parent,A.refId,A.isenabled,A.createTime ")
			.append(" from fs_Subject A left join fs_Currency B on A.currencyId=B.refId ")
			.append(" left join fs_Subject C on A.rootId=C.refId ")
			.append(" left join fs_ItemClass D on A.itemClassId=D.refId ")
			.append(" where A.isenabled != '"+SysConstant.OPTION_DEL+"' ");
			String whereStr = SqlUtil.buildWhereStr("A", params,false);
			
			if(StringHandler.isValidStr(whereStr)){
				sb.append(whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.id desc ");
			String cmns = "id,code,name,levels,detail,dc,currency,atype,itemClass,parent,refId,isenabled,createTime#yyyy-MM-dd HH:mm";
			DataTable dt = findBySqlPage(sb.toString(), cmns, offset, pageSize);
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public List<Long> batchSaveEntitys(List<SubjectEntity> objs)
			throws DaoException {
		List<Long> idList = super.batchSaveEntitys(objs);
		String hql = "UPDATE SubjectEntity SET dc=0 where dc=-1";
		super.executeHql(hql);
		return idList;
	}
	
	
	
}
