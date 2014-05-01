package com.cmw.dao.impl.crm;


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
import com.cmw.dao.inter.crm.CategoryDaoInter;
import com.cmw.entity.crm.CategoryEntity;


/**
 * 客户分类  DAO实现类
 * @author 程明卫
 * @date 2012-12-17T00:00:00
 */
@Description(remark="客户分类DAO实现类",createDate="2012-12-17T00:00:00",author="程明卫")
@Repository("categoryDao")
public class CategoryDaoImpl extends GenericDaoAbs<CategoryEntity, Long> implements CategoryDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			Integer custType = params.getvalAsInt("custType");
			Long inCustomerId = params.getvalAsLng("inCustomerId");
			Integer category = params.getvalAsInt("category");
			String uuid = params.getvalAsStr("uuid");
			Long projectId = params.getvalAsLng("projectId");
			params.removes("custType,inCustomerId,category,uuid,projectId");
			String cmns = "categoryId,customerId,name,sex,birthday#yyyy-MM-dd,accNature," +
					"cardType,cardNum,maristal,degree,job," +
					"workOrg,workAddress,phone,contactTel,inArea,inAddress";
			StringBuffer hqlSb = new StringBuffer();
			hqlSb.append("select B.id as categoryId,A.id as customerId,A.name,A.sex,A.birthday,A.accNature," )
				.append("A.cardType,A.cardNum,A.maristal,A.degree,A.job," )
				.append("A.workOrg,A.workAddress,A.phone,A.contactTel,A.inArea," )
				.append("A.inAddress from  CustomerInfoEntity A,CategoryEntity B " )
				.append(" where A.isenabled = "+SysConstant.OPTION_ENABLED+" and A.id=B.relCustomerId " )
				.append(" and B.category='"+category+"' and B.inCustomerId = '"+inCustomerId+"' and B.custType = '"+custType+"' ");
		
			if(StringHandler.isValidStr(uuid)){
				hqlSb.append(" and B.uuid = '"+uuid+"' ");
			}
			
			if(StringHandler.isValidObj(projectId)){
				hqlSb.append(" and B.projectId = '"+projectId+"' ");
			}
			hqlSb.append(SqlUtil.buildWhereStr("A",params,false));
			long totalCount = getTotalCount(hqlSb.toString());
			hqlSb.append(" order by A.id desc ");
			DataTable dt = findByPage(hqlSb.toString(),cmns, offset, pageSize);
			dt.setSize(totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e);
		}
	}

}
