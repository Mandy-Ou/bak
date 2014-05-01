package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.MortgageEntity;
import com.cmw.dao.inter.finance.MortgageDaoInter;


/**
 * 抵押物  DAO实现类
 * @author pdh
 * @date 2013-01-06T00:00:00
 */
@Description(remark="抵押物DAO实现类",createDate="2013-01-06T00:00:00",author="pdh")
@Repository("mortgageDao")
public class MortgageDaoImpl extends GenericDaoAbs<MortgageEntity, Long> implements MortgageDaoInter {

	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		StringBuffer sqlSb=new StringBuffer();
		sqlSb.append("select id,code,formId,gtype,name,quantity ,unint,")
			.append("oldVal,mpVal,mman,conTel,morTime,address,carrTime,")
			.append("carrMan,charTime,charMan ,state,remark")
			.append(" from fc_Mortgage where Isenabled<>"+SysConstant.OPTION_DEL);
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
		String cmns="id,code,formId,gtype,name,quantity ,unint,oldVal,mpVal,mman,conTel,morTime#yyyy-MM-dd,address,carrTime#yyyy-MM-dd,carrMan,charTime#yyyy-MM-dd,charMan ,state,remark";
		DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
		return dt;
	}
}
