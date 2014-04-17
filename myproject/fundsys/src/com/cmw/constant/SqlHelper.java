package com.cmw.constant;

import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;
/**
 * Sql 语句处理类
 * @author b
 *
 */
public class SqlHelper {
	/**
	 * SqlHelper.addWhereByActionType(params,sb,user)
	 * 根据动作类型，添加过滤条件
	 * @param params
	 * @param sb
	 * @param user
	 */
	public final static <K, V> void addWhereByActionType(SHashMap<K, V> params,StringBuilder sb, UserEntity user) {
		Integer actionType = params.getvalAsInt("actionType");
		if(null != actionType){
			switch (actionType) {
			case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0:{/*暂存*/
				sb.append(" and A.status = '"+BussStateConstant.BUSS_PROCC_DJZT_0+"' and A.creator = '"+user.getUserId()+"' ");
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1:{/*待办*/
				String procIds = params.getvalAsStr("procIds");
				if(!StringHandler.isValidStr(procIds)) procIds = "-1";
				sb.append(" and A.status = '"+BussStateConstant.BUSS_PROCC_DJZT_1+"' and A.procId in ("+procIds+") ");
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2 :{/*审批历史*/
				appendAuditHistoryWhereStr(sb,user);
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3 :{
			//	sb.append(" and A.status <> '"+BussStateConstant.BUSS_PROCC_DJZT_3+"' ");
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
	private final static void appendAuditHistoryWhereStr(StringBuilder sqlSb,UserEntity user){
		Long userId = user.getUserId();
		sqlSb.append(" and A.status <> '"+BussStateConstant.BUSS_PROCC_DJZT_0+"' ");
		sqlSb.append(" and A.procId in (SELECT procId FROM ts_auditrecords B where creator='"+userId+"')");
	}
}
