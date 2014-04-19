package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.ExtensionDaoInter;
import com.cmw.entity.finance.ExtensionEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 展期申请  DAO实现类
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="展期申请DAO实现类",createDate="2013-09-08T00:00:00",author="程明卫")
@Repository("extensionDao")
public class ExtensionDaoImpl extends GenericDaoAbs<ExtensionEntity, Long> implements ExtensionDaoInter {
	
	/*-------------  展期客户资料选择 查询  START CODE ---------------*/
	private static final String SQL_CMNS = "A.id as contractId,A.code as contractCode,B.code as guacontractCode,D.code as custCode,D.custType,"
			+"D.name as custName, D.cardType,D.cardNum,D.customerId,Z.name as breedName,"
			+"Y.name as payTypeName,A.appAmount,A.payDate,isnull(A.exteDate,A.endDate) as endDate,"
			+"A.rateType,A.rate,A.mrate,(A.appAmount-isnull(E.yprincipals,0)) as zprincipals," +
			"A.isadvance,A.phAmount,A.payType,A.mgrtype,A.arate,C.inRate,C.inRateType";
		
		private static final String DT_CONTRACTCMNS = "contractId,contractCode,guacontractCode,custCode,custType," +
				"custName,cardType,cardNum,customerId,breedName," +
				"payTypeName,appAmount,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd,rateType," +
				"rate,mrate,zprincipals,isadvance,phAmount,payType,mgrtype,arate,inRate,inRateType";
		
		@Override
		public DataTable getContractResultList(SHashMap<String, Object> params, int offset,int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			String filterSates = BussStateConstant.FIN_APPLY_STATE_6+","+BussStateConstant.FIN_APPLY_STATE_14
					+","+BussStateConstant.FIN_APPLY_STATE_15;
			StringBuilder sb = new StringBuilder();
			sb.append("select ").append(SQL_CMNS)
			.append(" from fc_LoanContract A left join fc_GuaContract B on A.id =B.loanConId")
			.append(" inner join fc_Apply C on A.formId=C.id left join ts_Variety Z on C.breed=Z.id ")
			.append(" left join fc_PayType Y on A.payType=Y.code ")
			.append(" inner join (")
			.append("  (select XX.custType,XX.code,XX.name,ZZ.name as cardType,XX.cardNum,YY.id as customerId ")
			.append("	from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId")
			.append("   left join ts_Gvlist ZZ on XX.cardType=ZZ.id  where XX.custType='"+SysConstant.CUSTTYPE_0+"' )")
			.append("    union ")
			.append(" (select XX.custType,XX.code,XX.name,'工商登记号' as cardType,XX.cardNum,YY.id as customerId ")
			.append("    from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType='"+SysConstant.CUSTTYPE_1+"' )")
			.append(") D on C.custType=D.custType and C.customerId=D.customerId left join (")
			.append("	select contractId,sum(yprincipal) as yprincipals from fc_Plan where yprincipal>0   group by contractId")
			.append(") E on A.id = E.contractId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ")
			.append(" AND C.state NOT IN ("+filterSates+")")
			.append(" AND C.totalPhases>0 ");
			//.append(" AND C.totalPhases>0 and C.paydPhases>0 ");
			//.append(" AND (C.totalPhases-C.paydPhases)=1 ");//-->只显示只剩一期未还的记录
			try {
				
				Integer custType = params.getvalAsInt("custType");
				if(StringHandler.isValidObj(custType)){
					sb.append(" and D.custType = '"+custType+"'");
				}
				
				String custName = params.getvalAsStr("custName");
				if(StringHandler.isValidStr(custName)){
					sb.append(" and D.name like '%"+custName+"%'");
				}
				
				Long contractId = params.getvalAsLng("contractId");
				if(StringHandler.isValidObj(contractId)){
					sb.append(" and A.id = '"+contractId+"'");
				}
				
				long totalCount = -1;
				if(offset >= 0) totalCount = getTotalCountBySql(sb.toString());
				sb.append(" order by A.id ");
				DataTable dt = findBySqlPage(sb.toString(), DT_CONTRACTCMNS, offset, pageSize);
				if(totalCount>-1) dt.setSize(totalCount);
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
		public DataTable getContractInfo(Long contractId) throws DaoException {
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("contractId", contractId);
			return getContractResultList(params,-1,-1);
		}/*-------------  展期客户资料选择 查询  END CODE ---------------*/
		
		
		private static final String EXTENSION_SQL_CMNS = "A.id,A.contractId,A.code,A.loanCode,A.guaCode,A.ostartDate,"
				+"A.oendDate,A.endAmount,A.extAmount,A.estartDate,A.eendDate,"
				+"Y.name as payTypeName,A.rateType,A.rate,A.isadvance,D.baseId,"
				+"A.mgrtype,A.mrate,D.custType,D.cardType,D.cardNum,D.custName," +
				"B.rateType as orateType,B.rate as orate,A.breed,A.procId,A.managerId as manager," +
				"D.customerId,E.empName as managerName,A.applyMan,A.applyDate,A.createTime,B.formId as applyId,C.inRateType,C.inRate,A.extInRate ";
		
		private static final String DT_EXTENSIONCMNS = "id,contractId,code,loanCode,guaCode,ostartDate#yyyy-MM-dd," +
				"oendDate#yyyy-MM-dd,endAmount,extAmount,estartDate#yyyy-MM-dd,eendDate#yyyy-MM-dd," +
				"payTypeName,rateType,rate,isadvance,baseId," +
				"mgrtype,mrate,custType,cardType,cardNum," +
				"custName,orateType,orate,breed,procId,manager," +
				"customerId,managerName,applyMan,applyDate#yyyy-MM-dd,creatTime#yyyy-MM-dd,applyId,inRateType,inRate,extInRate ";
		@Override
		public <K, V> DataTable getResultList(SHashMap<K, V> params,
				int offset, int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			StringBuilder sb = new StringBuilder();
			sb.append("select ").append(EXTENSION_SQL_CMNS)
			.append("  from fc_Extension A inner join fc_LoanContract B ON A.contractId = B.id  ")
			.append(" inner join fc_apply C on B.formId = C.id ")
			.append(" left join fc_PayType Y on A.payType=Y.code  ")
			.append(" inner join (")
			.append("  (select XX.id as baseId,XX.custType,XX.code,XX.name as custName,ZZ.name as cardType,XX.cardNum,YY.id as customerId ")
			.append("	from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId")
			.append("   left join ts_Gvlist ZZ on XX.cardType=ZZ.id  where XX.custType='"+SysConstant.CUSTTYPE_0+"' )")
			.append("    union ")
			.append(" (select XX.id as baseId,XX.custType,XX.code,XX.name as custName,'工商登记号' as cardType,XX.cardNum,YY.id as customerId ")
			.append("    from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType='"+SysConstant.CUSTTYPE_1+"' )")
			.append(") D on B.custType=D.custType and B.customerId=D.customerId ")
			.append(" left join ts_User E on A.managerId=E.userId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
			try {
				addWhereByActionType(params, sb, user);
				Long id = params.getvalAsLng("id");
				if(StringHandler.isValidObj(id)){
					sb.append(" and A.id = '"+id+"' ");
				}
				
				String status = params.getvalAsStr("status");
				if(StringHandler.isValidStr(status)){
					sb.append(" and  A.status in ("+status+") ");
				}
				
				Integer custType = params.getvalAsInt("custType");
				if(StringHandler.isValidObj(custType)){
					sb.append(" and D.custType = '"+custType+"' ");
				}
				
				String custName = params.getvalAsStr("custName");
				if(StringHandler.isValidStr(custName)){
					sb.append(" and D.custName like '%"+custName+"%' ");
				}
				
				String endAmount = params.getvalAsStr("endAmount");
				if(StringHandler.isValidObj(endAmount) && Double.parseDouble(endAmount) > 0){
					String eqopAmount = params.getvalAsStr("eqopAmount");
					if(!StringHandler.isValidObj(eqopAmount)){
						eqopAmount = " = ";
					}
					sb.append(" and A.endAmount "+eqopAmount+" '"+endAmount+"' ");
				}
				
				String startDate1 = params.getvalAsStr("startDate1");
				if(StringHandler.isValidStr(startDate1)){
					sb.append(" and A.estartDate >= '"+startDate1+"' ");
				}
				
				String endDate1 = params.getvalAsStr("endDate1");
				if(StringHandler.isValidStr(endDate1)){
					endDate1 = DateUtil.addDays(endDate1, 1);
					sb.append(" and A.estartDate < '"+endDate1+"' ");
				}
				
				String startDate2 = params.getvalAsStr("startDate2");
				if(StringHandler.isValidStr(startDate2)){
					sb.append(" and A.eendDate >= '"+startDate2+"' ");
				}
				
				String endDate2 = params.getvalAsStr("endDate2");
				if(StringHandler.isValidStr(endDate2)){
					endDate2 = DateUtil.addDays(endDate2, 1);
					sb.append(" and A.eendDate < '"+endDate2+"' ");
				}
				
				String extAmount = params.getvalAsStr("extAmount");
				if(StringHandler.isValidObj(extAmount) && Double.parseDouble(extAmount) > 0){
					String eqextAmount = params.getvalAsStr("eqextAmount");
					if(!StringHandler.isValidObj(eqextAmount)){
						eqextAmount = " = ";
					}
					sb.append(" and A.extAmount "+eqextAmount+" '"+extAmount+"' ");
				}
				
				long totalCount = -1;
				if(offset >= 0) totalCount = getTotalCountBySql(sb.toString());
				sb.append(" order by A.id ");
				DataTable dt = findBySqlPage(sb.toString(), DT_EXTENSIONCMNS, offset, pageSize);
				if(totalCount>-1) dt.setSize(totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}

		/**
		 * 根据动作类型，添加过滤条件
		 * @param params
		 * @param sb
		 * @param user
		 */
		private <K, V> void addWhereByActionType(SHashMap<K, V> params,
				StringBuilder sb, UserEntity user) {
			Integer actionType = params.getvalAsInt("actionType");
			if(null != actionType){
				switch (actionType) {
				case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0:{/*暂存*/
					sb.append(" and A.creator = '"+user.getUserId()+"' ");
					break;
				}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1:{/*待办*/
					String procIds = params.getvalAsStr("procIds");
					if(!StringHandler.isValidStr(procIds)) procIds = "-1";
					sb.append(" and A.procId in ("+procIds+") ");
					break;
				}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2 :{
					appendAuditHistoryWhereStr(sb,user);
					break;
				}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3 :{
					sb.append(" and A.status <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
					break;
				}default:
					break;
				}
			}
		}
		/**
		 * 审批历史需加的过滤条件
		 * @param sqlSb 条件SQL 
		 * @param user	当前用户
		 */
		private void appendAuditHistoryWhereStr(StringBuilder sqlSb,UserEntity user){
			Long userId = user.getUserId();
			sqlSb.append(" and A.status <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
			sqlSb.append(" and A.procId in (SELECT procId FROM ts_auditrecords B where creator='"+userId+"')");
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
