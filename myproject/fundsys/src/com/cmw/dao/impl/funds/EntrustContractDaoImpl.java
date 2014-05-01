package com.cmw.dao.impl.funds;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.entity.funds.EntrustContractEntity;


/**
 * 委托合同  DAO实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="委托合同DAO实现类",createDate="2014-01-20T00:00:00",author="李听")
@Repository("entrustContractDao")
public class EntrustContractDaoImpl extends GenericDaoAbs<EntrustContractEntity, Long> implements EntrustContractDaoInter {
@Override
public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
		int pageSize) throws DaoException {		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id,A.isenabled,A.breed ,A.creator as manager,A.procId,A.code,A.status,A.appAmount,A.payBank,A.payAccount,A.accName,A.payDate,A.endDate,B.prange,A.productsId,A.doDate")
		.append(" from fu_EntrustContract A")
		.append(" inner join fu_EntrustCust B on A.entrustCustId=B.id ")
		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
				Long status = params.getvalAsLng("status");/*编号*/
				if(StringHandler.isValidObj(status)){
					sb.append(" and A.status = '"+status+"' ");
				}
				String payAccount = params.getvalAsStr("payAccount");//银行帐号
				if(StringHandler.isValidStr(payAccount)){
					sb.append(" and A.payAccount like '%"+payAccount+"%' ");
				}
				String accName = params.getvalAsStr("accName");//银行
				if(StringHandler.isValidStr(accName)){
					sb.append(" and A.accName like '%"+accName+"%' ");
				}
				String productsId = params.getvalAsStr("productsId");//委托产品
				if(StringHandler.isValidStr(productsId)){
					sb.append(" and A.productsId like '%"+productsId+"%' ");
				}
				String code = params.getvalAsStr("code");//手机
				if(StringHandler.isValidStr(code)){
					sb.append(" and A.code like '%"+code+"%' ");
				}
				String payDate = params.getvalAsStr("payDate");
				if(StringHandler.isValidStr(payDate)){
					sb.append(" and A.payDate >= '"+payDate+"' ");
				}
				String endDate = params.getvalAsStr("endDate");
				if(StringHandler.isValidStr(endDate)){
					endDate = DateUtil.addDays(endDate, 1);
					sb.append(" and A.endDate < '"+endDate+"' ");
				}
			sb.append(" order by A.id desc ");
			String colNames = "id,isenabled,breed,manager,procId,code,status,appAmount,payBank,payAccount,"+
							"accName,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd,prange,productsId,"+
							"doDate#yyyy-MM-dd";
			DataTable dt = findBySqlPage(sb.toString(),colNames, offset, pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}

}

@Override
public DataTable detail(Long id) throws DaoException {
	try{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("id", id);
		DataTable dtResult = getResultList(params, -1, -1);
		return dtResult;
	} catch (DataAccessException e) {
		e.printStackTrace();
		throw new DaoException(e);
	}catch (Exception e) {
		e.printStackTrace();
		throw new DaoException(e);
	}
}
}
