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
import com.cmw.entity.finance.ExemptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.finance.ExemptDaoInter;


/**
 * 息费豁免申请  DAO实现类
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免申请DAO实现类",createDate="2013-09-14T00:00:00",author="程明卫")
@Repository("exemptDao")
public class ExemptDaoImpl extends GenericDaoAbs<ExemptEntity, Long> implements ExemptDaoInter {

	private static final String SQLEXEMPT_CMNS = "A.id,A.breed,A.procId,A.contractId,A.code,B.code as loanCode,A.etype," +
			"A.exeItems,A.isBackAmount,A.startDate,A.endDate,A.totalAmount,A.managerId,C.empName as managerName," +
			"A.appDate,A.appDept,D.custType,D.cardType,D.cardNum,D.custName," +
			"B.appAmount,B.rateType,B.rate,B.isadvance,B.mgrtype,B.mrate," +
			"B.prate,B.arate,B.urate,B.payType,D.baseId,B.customerId,B.formId as applyId ";
		
		private static final String DT_CMNS =  "id,breed,procId,contractId,code,loanCode,etype," +
				"exeItems,isBackAmount,startDate#yyyy-MM-dd,endDate#yyyy-MM-dd,totalAmount,manager,managerName," +
				"appDate#yyyy-MM-dd,appDept,custType,cardType,cardNum,custName," +
				"appAmount,rateType,rate,isadvance,mgrtype,mrate," +
				"prate,arate,urate,payType,baseId,customerId,applyId ";
		
		@Override
		public <K,V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			StringBuilder sb = new StringBuilder();
			sb.append("select ").append(SQLEXEMPT_CMNS)
			.append(" from  fc_Exempt A ")
			.append(" left join fc_LoanContract B on A.contractId=B.id ")
			.append(" left join ts_user C on A.managerId=C.userId ")
			.append(" left join (")
			.append(" (select XX.custType,XX.name as custName,ZZ.name as cardType,XX.cardNum,YY.id as customerId,YY.baseId as baseId ")
			.append("	from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId")
			.append("   left join ts_Gvlist ZZ on XX.cardType=ZZ.id  where XX.custType='"+SysConstant.CUSTTYPE_0+"' )")
			.append("    union ")
			.append(" (select XX.custType,XX.name as custName,'工商登记号' as cardType,XX.cardNum,YY.id as customerId ,YY.baseId as baseId ")
			.append("    from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType='"+SysConstant.CUSTTYPE_1+"' )")
			.append(") D on B.custType=D.custType and B.customerId=D.customerId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
			try {
					addWhereByActionType(params, sb, user);
					Integer custType = params.getvalAsInt("custType");
					if(StringHandler.isValidObj(custType)){
						sb.append(" and D.custType = '"+custType+"' ");
					}
					Long id = params.getvalAsLng("id");
					if(StringHandler.isValidObj(id)){
						sb.append(" and A.id = '"+id+"' ");
					}
					
					String custName = params.getvalAsStr("custName");
					if(StringHandler.isValidStr(custName)){
						sb.append(" and D.custName like '%"+custName+"%' ");
					}
					
					String etype = params.getvalAsStr("etype");
					if(StringHandler.isValidObj(etype)){
						sb.append(" and A.etype = '"+etype+"' ");
					}
					
					Integer isBackAmount = params.getvalAsInt("isBackAmount");/*是否返还息费*/
					if(StringHandler.isValidObj(isBackAmount) && isBackAmount.intValue() != -1){
						sb.append(" and A.isBackAmount = '"+isBackAmount+"' ");
					}
					
					
					String totalAmount = params.getvalAsStr("totalAmount");
					if(StringHandler.isValidObj(totalAmount) && Double.parseDouble(totalAmount) > 0){
						String eqAmount = params.getvalAsStr("eqAmount");
						if(!StringHandler.isValidObj(eqAmount)){
							eqAmount = " = ";
						}
						sb.append(" and A.totalAmount "+eqAmount+" '"+totalAmount+"' ");
					}
					
					String startDate1 = params.getvalAsStr("startDate1");
					if(StringHandler.isValidStr(startDate1)){
						sb.append(" and A.appDate >= '"+startDate1+"' ");
					}
					
					String endDate1 = params.getvalAsStr("endDate1");
					if(StringHandler.isValidStr(endDate1)){
						endDate1 = DateUtil.addDays(endDate1, 1);
						sb.append(" and A.appDate < '"+endDate1+"' ");
					}
					
				long totalCount = -1;
				if(offset >= 0) totalCount = getTotalCountBySql(sb.toString());
				sb.append(" order by A.id desc ");
				DataTable dt = findBySqlPage(sb.toString(), DT_CMNS, offset, pageSize);
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
	/*-------------  客户资料选择 查询  START CODE ---------------*/
	private static final String SQL_CMNS = "A.id as contractId,A.code as contractCode,D.code as custCode,D.custType,"
			+"D.name as custName, D.cardType,D.cardNum,"
			+"A.appAmount,A.payDate,isnull(A.exteDate,A.endDate) as endDate,"
			+"A.borBank,A.borAccount,A.accName";
		
		private static final String DT_CONTRACTCMNS = "contractId,contractCode,custCode,custType," +
				"custName,cardType,cardNum," +
				"appAmount,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd," +
				"borBank,borAccount,accName";
		
		@Override
		public DataTable getContractResultList(SHashMap<String, Object> params, int offset,int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			StringBuilder sb = new StringBuilder();
			sb.append("select ").append(SQL_CMNS)
			.append(" from fc_LoanContract A ")
			.append(" inner join fc_Apply C on A.formId=C.id")
			.append(" inner join (")
			.append("  (select XX.custType,XX.code,XX.name,ZZ.name as cardType,XX.cardNum,YY.id as customerId ")
			.append("	from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId")
			.append("   left join ts_Gvlist ZZ on XX.cardType=ZZ.id  where XX.custType='"+SysConstant.CUSTTYPE_0+"' )")
			.append("    union ")
			.append(" (select XX.custType,XX.code,XX.name,'工商登记号' as cardType,XX.cardNum,YY.id as customerId ")
			.append("    from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType='"+SysConstant.CUSTTYPE_1+"' )")
			.append(") D on C.custType=D.custType and C.customerId=D.customerId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ")
			.append(" AND C.totalPhases>0 and C.paydPhases>0 ");
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
					sb.append(" and A.id like '"+contractId+"'");
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

		@Override
		public DataTable getContractInfo(Long contractId) throws DaoException {
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("contractId", contractId);
			return getContractResultList(params,-1,-1);
		}/*-------------  客户资料选择 查询  END CODE ---------------*/
}
