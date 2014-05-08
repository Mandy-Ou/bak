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
import com.cmw.dao.inter.funds.EntrustCustDaoInter;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.sys.UserEntity;
/**
 * 委托客户资料  DAO实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="委托客户资料DAO实现类",createDate="2014-01-15T00:00:00",author="李听")
@Repository("entrustCustDao")
public class EntrustCustDaoImpl extends GenericDaoAbs<EntrustCustEntity, Long> implements EntrustCustDaoInter {
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuilder sb = new StringBuilder();		
		sb.append("select A.isenabled,A.id,")
			.append(" (case A.dlimit when '1' then cast(A.deadline as varchar)+'年' when '2' then cast(A.deadline as varchar)+'个月' when '3' then cast(A.deadline as varchar)+'日' END )as deadline,")
			.append("A.dlimit,A.code,A.name,A.sex,A.cardNum,A.birthday,A.appAmount,A.products,A.phone,")
			.append(" A.contactTel,A.inAddress,A.creator,A.createTime,A.homeNo,A.job,A.maristal,A.nation,A.hometown,A.degree,A.workOrg,A.workAddress,A.contactor,A.email,A.zipcode,A.fax,A.qqmsnNum,A.status,A.ctype,A.prange"
					+ " from fu_EntrustCust A ")
	 		.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");	 
		try {
			UserEntity user = (UserEntity) params.getvalAsObj(SysConstant.USER_KEY);
			String rightFilter = SqlUtil.getRightFilter(user, "A");
			if(StringHandler.isValidStr(rightFilter)){
				sb.append(" and "+rightFilter);
			}
				Long status = params.getvalAsLng("status");/*状态*/
				if(StringHandler.isValidObj(status)){
					sb.append(" and A.status = '"+status+"' ");
				}	
				Long id = params.getvalAsLng("id");/*状态*/
				if(StringHandler.isValidObj(id)){
					sb.append(" and A.id = '"+id+"' ");
				}
				String name = params.getvalAsStr("name");//姓名
				if(StringHandler.isValidStr(name)){
					sb.append(" and A.name like '%"+name+"%' ");
				}
				String phone = params.getvalAsStr("phone");//电话
				if(StringHandler.isValidStr(phone)){
					sb.append(" and A.phone like '%"+phone+"%' ");
				}
				String contactTel = params.getvalAsStr("contactTel");//手机
				if(StringHandler.isValidStr(contactTel)){
					sb.append(" and A.contactTel like '%"+contactTel+"%' ");
				}
				String inAddress = params.getvalAsStr("inAddress");//手机
				if(StringHandler.isValidStr(inAddress)){
					sb.append(" and A.inAddress like '%"+inAddress+"%' ");
				}
				String startDate1 = params.getvalAsStr("startDate1");
				if(StringHandler.isValidStr(startDate1)){
					sb.append(" and A.birthday >= '"+startDate1+"' ");
				}
				String endDate1 = params.getvalAsStr("endDate1");
				if(StringHandler.isValidStr(endDate1)){
					endDate1 = DateUtil.addDays(endDate1, 1);
					sb.append(" and A.birthday < '"+endDate1+"' ");
				}
			sb.append(" order by A.id desc ");
			String colNames = "isenabled,id,deadline,dlimit,code,name,sex,"+
							"cardNum,birthday#yyyy-MM-dd,appAmount"+
							",products,phone,contactTel,inAddress,"+
							"creator,createTime#yyyy-MM-dd,homeNo,job,maristal,nation,hometown,degree,workOrg,"
							+ "workAddress,contactor,email,zipcode,fax,qqmsnNum,status,ctype,prange";
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
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		return getResultList(map, -1, -1);
	}
}
