package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.PledgeDaoInter;
import com.cmw.entity.finance.PledgeEntity;


/**
 * 质押物  DAO实现类
 * @author pdh
 * @date 2013-01-08T00:00:00
 */
@Description(remark="质押物DAO实现类",createDate="2013-01-08T00:00:00",author="pdh")
@Repository("pledge Dao")
public class PledgeDaoImpl extends GenericDaoAbs<PledgeEntity, Long> implements PledgeDaoInter {

	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		StringBuffer sqlSb=new StringBuffer();
		sqlSb.append("select id,isenabled,formId,gtype,name,quantity,")
			.append("unint,oldVal,mpVal,mman,conTel,morTime,address,")
			.append("carrTime,carrMan,charTime,charMan,")
			.append("state,code,charCode,carrDept,charDept")
			.append(" from fc_Pledge where Isenabled<>"+SysConstant.OPTION_DEL);
		long totalCount = getTotalCountBySql(sqlSb.toString());
		//获取查询条件
		Long gtype=params.getvalAsLng("gtype");
		if(StringHandler.isValidObj(gtype)){
			sqlSb.append(" and gtype="+gtype);
		}
		Long formId=params.getvalAsLng("formId");
		if(StringHandler.isValidObj(formId)){
			sqlSb.append(" and formId="+formId);
		}
		Long id=params.getvalAsLng("id");
		if(StringHandler.isValidObj(id)){
			sqlSb.append(" and id="+id);
		}
		Long quantity=params.getvalAsLng("quantity");
		if(StringHandler.isValidObj(quantity)){
			sqlSb.append(" and quantity="+quantity);
		}
		String state=params.getvalAsStr("state");
		if(StringHandler.isValidObj(state)){
			sqlSb.append(" and state in("+state+")");
		}
		String remark=params.getvalAsStr("remark");
		if(StringHandler.isValidObj(remark)){
			sqlSb.append(" and remark like '%"+remark+"%'");
		}
		sqlSb.append("  order by  id DESC ");
		String cmns="id,isenabled,formId,gtype,name,quantity,unint,oldVal,mpVal,mman,conTel,morTime#yyyy-MM-dd,address,carrTime#yyyy-MM-dd,carrMan,charTime#yyyy-MM-dd,charMan,state,code,charCode,carrDept,charDept";
		DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
		return dt;
	}
}
