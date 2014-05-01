package com.cmw.dao.impl.crm;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
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
import com.cmw.dao.inter.crm.CustBaseDaoInter;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 客户基础信息  DAO实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户基础信息DAO实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Repository("custBaseDao")
public class CustBaseDaoImpl extends GenericDaoAbs<CustBaseEntity, Long> implements CustBaseDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		return getResultList(map, -1, -1);
	}
	@Override
	public <K, V> DataTable getDialogResultList(SHashMap<K, V> map,final int offset, final int pageSize)
			throws DaoException {
		String colNames = null;
		map = SqlUtil.getSafeWhereMap(map);
		Integer custType = map.getvalAsInt("custType");
		Integer category = map.getvalAsInt("category");
		StringBuffer sqlSb = new StringBuffer();
		try{
			if(custType.intValue() == SysConstant.CUSTTYPE_0){	//个人客户
				colNames = getOneCustomerBySql(map, sqlSb);	
			}else{	//企业客户 
				colNames = getEntCustomerBySql(map, sqlSb);	
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			UserEntity user = (UserEntity) map.getvalAsObj(SysConstant.USER_KEY);
			String rightFilter = SqlUtil.getRightFilter(user, "A");
			if(StringHandler.isValidStr(rightFilter)){
				sqlSb.append(" and "+rightFilter);
			}
			sqlSb.append(" order by A.id desc");
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		String colNames = null;
		Integer custType = params.getvalAsInt("custType");
		UserEntity user = (UserEntity) params.getvalAsObj(SysConstant.USER_KEY);
		StringBuffer sqlSb = new StringBuffer();
		try{
			if(custType.intValue() == SysConstant.CUSTTYPE_0){	//个人客户
				//客户编号,客户ID,客户流水号,姓名,性别,出生日期,证件类型,证件号码,婚姻状况,手机,电话,登记时间,登记人
				colNames = "id,isenabled,sysId,customerId,custLevel,code,name,sex,cardType,cardNum,custType,serialNum,birthday#yyyy-MM-dd,age,contactTel,phone,regman,deptid,registerTime#yyyy-MM-dd,refId";
				
				sqlSb.append("select A.id,A.isenabled,A.sysId,B.id as customerId,")
				.append("A.custLevel,A.code,B.name,B.sex,A.cardType,")
				.append("B.cardNum,A.custType,B.serialNum,B.birthday,B.age,")
				.append("B.contactTel,B.phone,C.empName as regman,B.deptid,B.registerTime,A.refId ")
				.append(" from crm_CustBase A,crm_CustomerInfo B left join ts_user C")
				.append(" on B.regman = C.userId ")
				.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' and A.id=B.baseId and B.serialNum IS NOT NULL");
				String sysId = params.getvalAsStr("sysId");
				if(StringHandler.isValidStr(sysId)){
					sqlSb.append(" and A.sysId='"+sysId+"' ");
				}
				String name = params.getvalAsStr("name");
				if(StringHandler.isValidStr(name)){
					sqlSb.append(" and A.name like '"+name+"%' ");
				}
				String code = params.getvalAsStr("code");
				if(StringHandler.isValidStr(code)){
					sqlSb.append(" and A.code like '"+code+"%' ");
				}
				Integer custLevel = params.getvalAsInt("custLevel");
				if(StringHandler.isValidObj(custLevel)){
					sqlSb.append(" and A.custLevel ='"+custLevel+"' ");
				}
				Long cardType = params.getvalAsLng("cardType");
				if(StringHandler.isValidObj(cardType)){
					sqlSb.append(" and A.cardType = '"+cardType+"' ");
				}
				String cardNum = params.getvalAsStr("cardNum");
				if(StringHandler.isValidStr(cardNum)){
					sqlSb.append(" and A.cardNum like '"+cardNum+"%' ");
				}
				Integer sex = params.getvalAsInt("sex");
				if(StringHandler.isValidObj(sex)){
					sqlSb.append(" and B.sex = '"+sex+"' ");
				}
				String birthday = params.getvalAsStr("birthday");
				if(StringHandler.isValidStr(birthday)){
					sqlSb.append(" and  CONVERT(varchar(10) , B.birthday, 120 )  like '"+birthday+"%' ");
				}
				Integer age = params.getvalAsInt("age");
				if(StringHandler.isValidObj(age)){
					sqlSb.append(" and B.age = '"+age+"' ");
				}
				String registerTime = params.getvalAsStr("registerTime");
				if(StringHandler.isValidStr(registerTime)){
					sqlSb.append(" and  CONVERT(varchar(10) , B.registerTime, 120 )  like '"+registerTime+"%' ");
				}
				String phone = params.getvalAsStr("phone");
				if(StringHandler.isValidStr(phone)){
					sqlSb.append(" and B.phone like '"+phone+"%' ");
				}
			}else{	//企业客户
				colNames = "id,isenabled,sysId,serialNum,ecustomerId,custLevel," +
						"code,name,tradNumber,orgcode,trade," +
						"kind,contactor,phone,contactTel,address," +
						"regman,registerTime#yyyy-MM-dd,refId";
				sqlSb.append("select A.id,A.isenabled,A.sysId,B.serialNum,B.id as ecustomerId,")
				.append(" A.custLevel,A.code,B.name,B.tradNumber,B.orgcode,E.name as trade,")
				.append(" D.name as kind,B.contactor,B.phone,B.contactTel,(B.address+'##'+B.inAddress) as address,C.empName as regman,B.registerTime,A.refId ")
				.append(" from crm_CustBase A, crm_Ecustomer B left join ts_user C on B.regman = C.userId ")
				.append(" left join ts_Gvlist D on B.kind = D.id ")
				.append(" left join ts_Gvlist E on B.trade = E.id ")
				.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' and A.id=B.baseId ");
				String sysId = params.getvalAsStr("sysId");
				if(StringHandler.isValidStr(sysId)){
					sqlSb.append(" and A.sysId='"+sysId+"' ");
				}
				String name = params.getvalAsStr("name");
				if(StringHandler.isValidStr(name)){
					sqlSb.append(" and A.name like '"+name+"%' ");
				}
				String code = params.getvalAsStr("code");
				if(StringHandler.isValidStr(code)){
					sqlSb.append(" and A.code like '"+code+"%' ");
				}
				Integer custLevel = params.getvalAsInt("custLevel");
				if(StringHandler.isValidObj(custLevel)){
					sqlSb.append(" and A.custLevel ='"+custLevel+"' ");
				}
				String tradNumber = params.getvalAsStr("tradNumber");
				if(StringHandler.isValidStr(tradNumber)){
					sqlSb.append(" and B.tradNumber like '"+tradNumber+"%' ");
				}
				String phone = params.getvalAsStr("phone");
				if(StringHandler.isValidStr(phone)){
					sqlSb.append(" and B.phone like '"+phone+"%' ");
				}
				String contactor = params.getvalAsStr("contactor");
				if(StringHandler.isValidStr(phone)){
					sqlSb.append(" and B.contactor like '"+contactor+"%' ");
				}
				String contactTel = params.getvalAsStr("contactTel");
				if(StringHandler.isValidStr(contactTel)){
					sqlSb.append(" and B.contactTel like '"+contactTel+"%' ");
				}
				String serialNum = params.getvalAsStr("serialNum");
				if(StringHandler.isValidStr(serialNum)){
					sqlSb.append(" and B.serialNum like '"+serialNum+"%' ");
				}
				String orgcode = params.getvalAsStr("orgcode");
				if(StringHandler.isValidStr(orgcode)){
					sqlSb.append(" and B.orgcode like '"+orgcode+"%' ");
				}
				Long kind = params.getvalAsLng("kind");
				if(StringHandler.isValidObj(kind)){
					sqlSb.append(" and B.kind = '"+kind+"' ");
				}
				Long trade = params.getvalAsLng("trade");
				if(StringHandler.isValidObj(trade)){
					sqlSb.append(" and B.trade = '"+trade+"' ");
				}
				
			}
			String registerTime = params.getvalAsStr("registerTime");
			if(StringHandler.isValidStr(registerTime)){
				sqlSb.append(" and  CONVERT(varchar(10) , B.registerTime, 120 )  like '"+registerTime+"%' ");
			}
			String rightFilter = SqlUtil.getRightFilter(user, "A");
			if(StringHandler.isValidStr(rightFilter)){
				sqlSb.append(" and "+rightFilter);
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			sqlSb.append(" order by A.id desc ,CONVERT(nvarchar, B.registerTime, 120 ) desc");
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	

	/**
	 * 获取个人客户列表数据
	 * @param map
	 * @param sqlSb
	 * @return
	 */
	private <K, V> String getOneCustomerBySql(SHashMap<K, V> map,
			StringBuffer sqlSb) {
		//客户编号,客户ID,客户流水号,姓名,性别,出生日期,证件类型,证件号码,婚姻状况,手机,电话,登记时间,登记人
		String colNames = "baseId,code,customerId,serialNum,name,sex,birthday#yyyy-MM-dd,cardType,cardNum,maristal,phone,contactTel,registerTime#yyyy-MM-dd,regman";
		
		sqlSb.append("select A.id as baseId,A.code,B.id as customerId,B.serialNum,A.name,B.sex,")
		.append("B.birthday,B.cardType,B.cardNum,B.maristal,B.phone,")
		.append("B.contactTel,B.registerTime,C.empName as regman")
		.append(" from crm_CustBase A,crm_CustomerInfo B left join ts_user C")
		.append(" on B.regman = C.userId ")
		.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and A.id=B.baseId and B.serialNum IS NOT NULL");
		String sysId = map.getvalAsStr("sysId");
		if(StringHandler.isValidStr(sysId)){
			sqlSb.append(" and A.sysId='"+sysId+"' ");
		}
		String name = map.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){
			sqlSb.append(" and B.name like '"+name+"%' ");
		}
		return colNames;
	}


	/**
	 * 获取企业客户列表数据
	 * @param map
	 * @param sqlSb
	 * @return
	 */
	private <K, V> String getEntCustomerBySql(SHashMap<K, V> map,
			StringBuffer sqlSb) {
		//客户编号,客户ID,客户流水号,企业名称,工商登记号,组织机构代码,所属行业,企业性质,联系人,手机,电话,法人,成立时间,登记时间,登记人
		String colNames = "baseId,code,customerId,serialNum,name,tradNumber,orgcode,trade,kind,contactor,phone,contactTel,legalMan,comeTime#yyyy-MM-dd,registerTime#yyyy-MM-dd,regman";
		
		sqlSb.append("select A.id as baseId,A.code,B.id as customerId,B.serialNum,A.name,B.tradNumber,")
		.append("B.orgcode,B.trade,B.kind,B.contactor,B.phone,")
		.append("B.contactTel,B.legalMan,B.comeTime,B.registerTime,C.empName as regman")
		.append(" from crm_CustBase A,crm_Ecustomer B left join ts_user C")
		.append(" on B.regman = C.userId ")
		.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and A.id=B.baseId ");
		String sysId = map.getvalAsStr("sysId");
		if(StringHandler.isValidStr(sysId)){
			sqlSb.append(" and A.sysId='"+sysId+"' ");
		}
		String name = map.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){
			sqlSb.append(" and B.name like '"+name+"%' ");
		}
		return colNames;
	}

	@Override
	public <K, V> List<CustBaseEntity> getSynsCustBaseList(SHashMap<K, V> map)
			throws DaoException {
		try{
			Integer custType = map.getvalAsInt("custType");
			String hql = "from CustBaseEntity A where  A.isenabled='"+SysConstant.OPTION_ENABLED+"'" +
					" and A.custType='"+custType+"' and A.refId is null ";
			return getList(hql);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public void updateCustBaseRefIds(SHashMap<String, Object> refMap,UserEntity currUser) throws DaoException {
		try{
			Query query = null;
			Set<String> custbaseIds = refMap.keySet();
			for(String custbaseId : custbaseIds){
				Long refId = refMap.getvalAsLng(custbaseId);
				String hql = "update CustBaseEntity set refId = ?, modifier = ?, modifytime = ? where id = ? ";
				query = getSession().createQuery(hql);
				query.setParameter(0, refId);
				query.setParameter(1, currUser.getUserId());
				query.setParameter(2, new Date(System.currentTimeMillis()));
				query.setParameter(3, Long.parseLong(custbaseId));
				query.executeUpdate();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
