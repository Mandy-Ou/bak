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
import com.cmw.entity.crm.GuaCustomerEntity;
import com.cmw.dao.inter.crm.GuaCustomerDaoInter;


/**
 * 第三方担保人  DAO实现类
 * @author 李听
 * @date 2013-12-31T00:00:00
 */
@Description(remark="第三方担保人DAO实现类",createDate="2013-12-31T00:00:00",author="李听")
@Repository("guaCustomerDao")
public class GuaCustomerDaoImpl extends GenericDaoAbs<GuaCustomerEntity, Long> implements GuaCustomerDaoInter {
//
//	/* (non-Javadoc)
//	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
//	 */
//	@Override
//	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
//			int pageSize) throws DaoException {
//		try{
//			params = SqlUtil.getSafeWhereMap(params);
//			String colNames = "id,baseId,name,sex,birthday#yyyy-mm-dd,contactor,contactTel,phone,qqmsnNum,cardType,cardNum,age,maristal";
//			StringBuffer sqlSb = new StringBuffer();
//			sqlSb.append(" select C.id as id,C.baseId,C.name,C.sex,C.birthday,C.contactor as contactor , ")
//				.append(" C.contactTel as contactTel,C.phone as phone,C.qqmsnNum as qqmsnNum ,  ")
//				.append(" C.cardType as cardType,C.cardNum as cardNum,C.age as age,C.maristal as maristal ")
//				.append(" from crm_GuaCustomer A inner join crm_Category B on B.id = A.categoryId ")
//				.append(" inner join crm_CustomerInfo C on C.id = B.inCustomerId ")
//				.append(" where C.isenabled='"+SysConstant.OPTION_ENABLED+"' and B.relCustomerId = C.baseId  ")
//				.append(" and B.category = '"+SysConstant.CATEGORY_2+"' ");
//			Long baseId = params.getvalAsLng("baseId");
//			Integer custType = params.getvalAsInt("custType");
//			if(StringHandler.isValidObj(baseId)){
//				sqlSb.append(" and C.baseId = '"+baseId+"' ");
//			}
//			
//			if(StringHandler.isValidObj(custType)){
//				sqlSb.append(" and B.custType = '"+custType+"' ");
//			}
//			String name = params.getvalAsStr("name");
//			if(StringHandler.isValidStr(name)){
//				sqlSb.append(" and C.name like '"+name+"%' ");
//			}
//			long totalCount = getTotalCountBySql(sqlSb.toString());	//
//			sqlSb.append("  order by C.id desc ");
//			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
//			return dt;
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			throw new DaoException(e);
//		}
//	}
	
}
