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
import com.cmw.entity.fininter.VoucherTempEntity;
import com.cmw.dao.inter.fininter.VoucherTempDaoInter;


/**
 * 凭证模板  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证模板DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("voucherTempDao")
public class VoucherTempDaoImpl extends GenericDaoAbs<VoucherTempEntity, Long> implements VoucherTempDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.isenabled,A.code,A.name,B.name as groupName,")
			.append("C.name as currency,A.entry,A.maxcount,A.tactics,A.createTime,")
			.append("D.userName as creator,A.remark from fs_VoucherTemp A ")
			.append(" left join fs_VoucherGroup B on  A.groupId=B.refId ")
			.append(" left join fs_Currency C on A.currencyId = C.refId ")
			.append(" left join ts_user D on A.creator=D.userId ")
			.append(" where A.isenabled != '"+SysConstant.OPTION_DEL+"' ");
			String whereStr = SqlUtil.buildWhereStr("A", params,false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(" "+whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.id desc ");
			String cmns = "id,isenabled,code,name,groupName,currency,entry,maxcount,tactics,createTime#yyyy-MM-dd,creator,remark";
			DataTable dt = findBySqlPage(sb.toString(), cmns, offset, pageSize);
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	}
}
