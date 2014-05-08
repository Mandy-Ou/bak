package com.cmw.dao.impl.funds;


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
import com.cmw.dao.inter.funds.ReceInterestDaoInter;
import com.cmw.entity.funds.ReceInterestEntity;


/**
 * 汇票利润提成DAO实现类
 * @author 彭登浩
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票利润提成DAO实现类",createDate="2014-02-08T00:00:00",author="彭登浩")
@Repository("receInterestDao")
public class ReceInterestDaoImpl extends GenericDaoAbs<ReceInterestEntity, Long> implements ReceInterestDaoInter{
	
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select A.id,A.receMsgId,A.adultMan,A.adultProp,A.adultMoney,A.remark")
				.append(" from fu_ReceInterest A ");
			Long receMsgId=params.getvalAsLng("receMsgId");
			if(StringHandler.isValidObj(receMsgId)){
				sb.append(" where A.receMsgId="+receMsgId);
			}
			sb.append(" order by A.id desc ");
			String colNames = "id,receMsgId,adultMan,adultProp,adultMoney,remark";
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
	
}
