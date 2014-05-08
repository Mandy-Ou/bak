package com.cmw.dao.impl.funds;



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
import com.cmw.entity.funds.BamountApplyEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.BamountApplyDaoInter;


/**
 * 汇票查询申请表  DAO实现类
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */

@Description(remark="汇票查询申请表DAO实现类",createDate="2014-02-24T00:00:00",author="郑符明")
@Repository("bamountApplyDao")
public class BamountApplyDaoImpl extends GenericDaoAbs<BamountApplyEntity, Long> implements BamountApplyDaoInter {
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {		params = SqlUtil.getSafeWhereMap(params);
			StringBuilder sb = new StringBuilder();
			sb.append("select C.code,B.name,B.appAmount,A.id,A.isenabled,A.remark,A.breed,A.procId,"
					+ "A.creator as manager,A.status,A.bamount,A.wamount,A.isNotExpiration,A.backDate,"
					+ "B.prange,C.payDate,C.endDate,C.rate,C.unint")
			.append(" from fu_BamountApply A")
			.append(" inner join fu_EntrustCust B on A.entrustCustId=B.id ")
			.append(" inner join fu_EntrustContract C on C.id=A.entrustContractId ")
			.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
			UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
			try {
					addWhereByActionType(params, sb, user);
					
					Long id = params.getvalAsLng("id");
					if(StringHandler.isValidObj(id)){
						sb.append(" and A.id ='"+id+"' ");
					}
					/*委托人姓名:name，委托合同编号:code，委托金额:eqopAmount,appAmount，
					撤资金额:eqopBamount,bamount，撤资类型:isNotExpiration，*/
					String name = params.getvalAsStr("name");//银行帐号
					if(StringHandler.isValidStr(name)){
						sb.append(" and B.name like '%"+name+"%' ");
					}
					String code = params.getvalAsStr("code");//编号
					if(StringHandler.isValidStr(code)){
						sb.append(" and C.code like '%"+code+"%' ");
					}
					String eqopAmount = params.getvalAsStr("eqopAmount");
					if(StringHandler.isValidStr(eqopAmount)){
						Double appAmount = params.getvalAsDob("appAmount");
						if(null != appAmount && appAmount.doubleValue() > 0){
							sb.append(" and B.appAmount "+eqopAmount+" '"+appAmount+"' ");
						}
					}
					String eqopBamount = params.getvalAsStr("eqopBamount");
					if(StringHandler.isValidStr(eqopBamount)){
						Double bamount = params.getvalAsDob("bamount");
						if(null != bamount && bamount.doubleValue() > 0){
							sb.append(" and A.bamount "+eqopBamount+" '"+bamount+"' ");
						}
					}
					String isNotExpiration = params.getvalAsStr("isNotExpiration");
					if(StringHandler.isValidStr(isNotExpiration)){
						sb.append(" and A.isNotExpiration like '%"+isNotExpiration+"%' ");
					}
				long count = getTotalCountBySql(sb.toString());
				sb.append(" order by A.id desc ");
				String colNames = "code,name,appAmount,id,isenabled,remark,breed,procId,manager,status,bamount,wamount,isNotExpiration,"+
								"backDate#yyyy-MM-dd,prange,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd,rate,unint";
				DataTable dt = findBySqlPage(sb.toString(),colNames,offset, pageSize,count);
				//dt.setSize(totalCount);
				
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
				sb.append(" and A.status='"+BussStateConstant.FIN_APPLY_STATE_0+"' and A.creator = '"+user.getUserId()+"' ");
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
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		return getResultList(map, -1, -1);
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
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		String mark=params.getvalAsStr("mark");
		StringBuilder sb = new StringBuilder();		
		sb.append("select A.isenabled,A.id,B.id as entrustContractId,B.appAmount,B.code,A.name,B.yearLoan,")
		.append("B.monthLoan,B.doDate,B.rate,B.payDate,B.endDate,B.iamount,B.unint,B.rateType  ") ;
		if(StringHandler.isValidObj(mark))
			sb.append(",I.nextDate");
		sb.append(" from fu_EntrustCust A right join fu_EntrustContract B on A.id=B.entrustCustId ");
		if(StringHandler.isValidObj(mark))
			sb.append(" left join fu_Interest I on B.id=I.entrustContractId");
		sb.append(" where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
		try {
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sb.append(" and A.name like '%"+name+"%'");
			}
			if(StringHandler.isValidObj(mark)){
				String backDate=params.getvalAsStr("backDate");
				if(StringHandler.isValidStr(backDate)){
					sb.append(" and I.nextDate> '"+backDate+"' ");
				}			
			}
			sb.append(" order by A.id desc ");
			if(StringHandler.isValidObj(mark))
				sb.append(",I.nextDate asc");
				
			String colNames = "isenabled,id,entrustContractId,appAmount,code,name,yearLoan,monthLoan,doDate#yyyy-MM-dd,"+
							"rate,payDate#yyyy-MM-dd,endDate#yyyy-MM-dd,iamount,unint,rateType";
			if(StringHandler.isValidObj(mark))
				colNames+=",nextDate";
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
