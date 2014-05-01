package com.cmw.dao.impl.funds;


import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SqlHelper;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.ReceiptBookEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.ReceiptBookDaoInter;


/**
 * 汇票转让承诺书表  DAO实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票转让承诺书表DAO实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Repository("receiptBookDao")
public class ReceiptBookDaoImpl extends GenericDaoAbs<ReceiptBookEntity, Long> implements ReceiptBookDaoInter {

	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sqlSb = new StringBuilder();
		
		String colNames = "id,isenabled,status,code,tcount," +
				"tamount,name,cardNum,tel,rtacname,rtaccount," +
				"rtbank,breed,procId,tman,mman,tdate,manager";
		
		sqlSb.append("select A.id,A.isenabled,A.status,A.code,A.tcount,")
			.append("A.tamount,A.name,A.cardNum,A.tel,A.rtacname,A.rtaccount,")
			.append("A.rtbank,A.breed,A.procId,A.tman,A.mman,A.tdate,A.creator as manager")
			.append(" from fu_receiptBook A")
			.append(" where A.isenabled="+SysConstant.OPTION_ENABLED);
			//UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		try{
/*			SqlHelper.addWhereByActionType(params, sqlSb, user);
			//审批时根据id查询记录
			Long id = params.getvalAsLng("id");
			if(null != id ){
				sqlSb.append(" and A.id = "+id);
			}*/
			
			/**
			 * 根据条件进行查询
			 * 涉及的查询字段 ： 编号:code,总金额:tamount,转让时间范围: outDate#D,endaDate#D,收款人姓名:name,收款人账户名:rtacname,收款人账号:rtaccount
			 */
			String code = params.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){
				sqlSb.append(" and A.code like '%"+code+"%' ");
			}
			/**
			 * 根据金额进行查询
			 */
			String operational = params.getvalAsStr("operational");
			if(StringHandler.isValidStr(operational)){
				Double tamount = params.getvalAsDob("tamount");
				if(null != tamount && tamount.doubleValue() > 0){
					sqlSb.append(" and A.tamount "+operational+" '"+tamount+"' ");
				}
			}
			String outDate = params.getvalAsStr("outDate");
			if(StringHandler.isValidStr(outDate)){
				sqlSb.append(" and A.tdate >= '"+outDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){
				sqlSb.append(" and A.tdate <= '"+endDate+"' ");
			}
			
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and A.name like '%"+name+"%' ");
			}
			String rtacname = params.getvalAsStr("rtacname");
			if(StringHandler.isValidStr(rtacname)){
				sqlSb.append(" and A.rtacname like '%"+rtacname+"%' ");
			}
			
			String rtaccount = params.getvalAsStr("rtaccount");
			if(StringHandler.isValidStr(rtaccount)){
				sqlSb.append(" and A.rtaccount like '%"+rtaccount+"%' ");
			}
			
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" order by A.id desc");
			
			
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
