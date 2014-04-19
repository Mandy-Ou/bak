package com.cmw.service.impl.sys;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.dao.inter.sys.DeskModDaoInter;
import com.cmw.entity.sys.DeskModEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.FcFlowService;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.sys.DeskModService;


/**
 * 桌面模块配置  Service实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="桌面模块配置业务实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Service("deskModService")
public class DeskModServiceImpl extends AbsService<DeskModEntity, Long> implements  DeskModService {
	@Autowired
	private DeskModDaoInter deskModDao;
	@Resource(name="fcFlowService")
	FcFlowService fcFlowService;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	@Autowired
	private CommonDaoInter commonDao;
	
	// 默认消息显示条数
	public static final int DEFAULT_MSG_COUNT = 5;
	
	@Override
	public GenericDaoInter<DeskModEntity, Long> getDao() {
		return deskModDao;
	}
	
	@Override
	public DataTable getContents(SHashMap<String, Object> map) throws ServiceException {
		//loadType#I,dataCode,dispcmns
		Integer loadType = map.getvalAsInt("loadType");
		String dataCode = map.getvalAsStr("dataCode");
		//String dispcmns = map.getvalAsStr("dispcmns");
		DataTable dtContent = null;
		try{
			if(loadType.intValue() == SysConstant.LOADTYPE_1){
				dtContent = deskModDao.getDataTableBySql(dataCode, map);
			}else if(loadType.intValue() == SysConstant.LOADTYPE_2){
				dtContent = deskModDao.getDataTableByHql(dataCode, map);
			}else if(loadType.intValue() == SysConstant.LOADTYPE_3){
				dtContent = (DataTable)BeanUtil.invokeMethod(this, dataCode, new Object[]{map});
			}
			return dtContent;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/**
	 * 待审的个人客户项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getCustApplyDatas(SHashMap<String, Object> params) throws ServiceException, DaoException{
		SHashMap<String, Object> map = getParams(params,false);
		String states = BussStateConstant.FIN_APPLY_STATE_0 +","+ BussStateConstant.FIN_APPLY_STATE_14 + "," + BussStateConstant.FIN_APPLY_STATE_15;
		String sql = "select A.id,A.code,B.name,A.appAmount,A.appdate " +
				" from fc_apply A inner join crm_customerInfo B on A.customerId=B.id" +
				" where  A.state not in ("+states+") and A.custType ='"+SysConstant.CUSTTYPE_0+"' "+getAuditWhere(map);
		Integer msgCount = params.getvalAsInt("msgCount");
		if(null == msgCount) msgCount = DEFAULT_MSG_COUNT;
		DataTable dt = commonDao.getDatasBySql(sql, "applyId,code,name,appAmount,appdate#yyyy-MM-dd", 0, msgCount);
		return  dt;
	}
	
	/**
	 * 待审的企业客户项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getEntCustApplyDatas(SHashMap<String, Object> params) throws ServiceException, DaoException{
		SHashMap<String, Object> map = getParams(params,false);
		String states = BussStateConstant.FIN_APPLY_STATE_0 +","+ BussStateConstant.FIN_APPLY_STATE_14 + "," + BussStateConstant.FIN_APPLY_STATE_15;
		String sql = "select A.id,A.code,B.name,A.appAmount,A.appdate " +
				" from fc_apply A inner join crm_Ecustomer B on A.customerId=B.id" +
				" where  A.state not in ("+states+") and A.custType ='"+SysConstant.CUSTTYPE_1+"' "+getAuditWhere(map);
		Integer msgCount = params.getvalAsInt("msgCount");
		if(null == msgCount) msgCount = DEFAULT_MSG_COUNT;
		DataTable dt = commonDao.getDatasBySql(sql, "applyId,code,name,appAmount,appdate#yyyy-MM-dd", 0, msgCount);
		return  dt;
	}
	/**
	 * 待提交的展期申请 项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getExtensionTemporary(SHashMap<String, Object> params) throws ServiceException, DaoException{
		Integer msgCount = params.getvalAsInt("msgCount");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.getMap().putAll(params.getMap());
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_0);
		DataTable dt = getExtensionDts(map,msgCount,user);
		return  dt;
	}
	/**
	 * 待审批的展期申请 项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getExtensionApplyDatas(SHashMap<String, Object> params) throws ServiceException, DaoException{
		Integer msgCount = params.getvalAsInt("msgCount");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = getParams(params,true);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_1);
		DataTable dt = getExtensionDts(map,msgCount,user);
		return  dt;
	}
	private DataTable getExtensionDts(SHashMap<String, Object> params,Integer msgCount,UserEntity user) throws ServiceException , DaoException{
		StringBuilder sqlSb = new StringBuilder();
		Integer status = params.getvalAsInt("status");
		sqlSb.append(" select ")
		 .append(" A.id ,A.code,D.custName as name, ")
		 .append("A.extAmount,A.createTime   ")
		 .append("from   ")
		 .append("dbo.fc_Extension A   inner join fc_LoanContract B on A.contractId = B.id   ")
		 .append("inner join  ")
		 .append("( ")
		 .append("(select XX.custType,XX.name as custName,YY.id as customerId  ") 
		 .append("from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId  where XX.custType = 0  ")
		 .append(") ")
		 .append("union  ")
		 .append("(select XX.custType,XX.name as custName ,YY.id as customerId   ")
		 .append("from   crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType = 1  ")
		 .append(") ")
		 .append(") D on B.custType = D.custType and B.customerId = D.customerId  where A.isenabled= 1 "); 
		addWhereByActionType(params, sqlSb, user);
		 sqlSb.append(" and  A.status in  ("+status+") ");
		 sqlSb.append(" order by A.id desc ");
		if(null == msgCount) msgCount = DEFAULT_MSG_COUNT;
		DataTable dt = commonDao.getDatasBySql(sqlSb.toString(), "id,code,name,extAmount,createTime#yyyy-MM-dd", 0, msgCount); 
		return dt;
	}
	/**
	 * 待提交的提前还款 项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getPrepaymentTemporary(SHashMap<String, Object> params) throws ServiceException, DaoException{
		Integer msgCount = params.getvalAsInt("msgCount");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.getMap().putAll(params.getMap());
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_0);
		DataTable dt = getPrepaymentDataTable(map,msgCount,user);
		return  dt;
	}
	/**
	 * 待审批的提前还款 项目
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getPrepaymentApplyDatas(SHashMap<String, Object> params) throws ServiceException, DaoException{
		Integer msgCount = params.getvalAsInt("msgCount");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = getParams(params,true);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_1);
		DataTable dt = getPrepaymentDataTable(map,msgCount,user);
		return  dt;
	}
	/**
	 * 提前还款DataTable
	 */
	private DataTable getPrepaymentDataTable(SHashMap<String, Object> params,Integer msgCount,UserEntity user) throws ServiceException , DaoException{
		StringBuilder sqlSb = new StringBuilder();
		Integer status = params.getvalAsInt("status");
		sqlSb.append(" select A.id,A.code,D.custName, " );
		sqlSb.append(" (case A.ptype when 1 then '提前结清' when 2 then '借新还旧' when 3 then '部分提前还款' else '' end) ptype , " );
		sqlSb.append(" A.totalAmount  from  fc_Prepayment A " );
		sqlSb.append(" left join fc_LoanContract B on A.contractId=B.id " );
		sqlSb.append(" left join ( " );
		sqlSb.append(" (select XX.custType,XX.name as custName,YY.id as customerId  " );
		sqlSb.append(" from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId " );
		sqlSb.append(" where XX.custType = 0 " );
		sqlSb.append(" ) " );
		sqlSb.append(" union " );
		sqlSb.append(" (select XX.custType,XX.name as custName,YY.id as customerId  " );
		sqlSb.append(" from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType= 1) " );
		sqlSb.append(" )  D on B.custType=D.custType and B.customerId=D.customerId  where A.isenabled = 1  " );
		addWhereByActionType(params, sqlSb, user);
		sqlSb.append(" and  A.status  in ("+status+") ");
		 sqlSb.append(" order by A.id desc ");
		if(null == msgCount) msgCount = DEFAULT_MSG_COUNT;
		DataTable dt = commonDao.getDatasBySql(sqlSb.toString(), "id,code,custName,etype,totalAmount", 0, msgCount); 
		return dt;
	}
	
	/**
	 *暂存息费豁免申请单
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getExemptTemporary(SHashMap<String, Object> params) throws ServiceException, DaoException{
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		Integer msgCount = params.getvalAsInt("msgCount");
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.getMap().putAll(params.getMap());
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_0);
		DataTable dt = getExemptDatas(map,msgCount,user);
		return  dt;
	}
	
	/**
	 *待审批息费豁免申请单
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	public DataTable getExemptApplyDatas(SHashMap<String, Object> params) throws ServiceException, DaoException{
		Integer msgCount = params.getvalAsInt("msgCount");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = getParams(params,true);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
		map.put("status", BussStateConstant.FIN_APPLY_STATE_1);
		DataTable dt = getExemptDatas(map,msgCount,user);
		return  dt;
	}
	/**
	 * 息费豁免DataTable 
	 * @param params
	 * @param msgCount
	 * @param user
	 * @return
	 * @throws ServiceException
	 * @throws DaoException
	 */
	private DataTable getExemptDatas(SHashMap<String, Object> params,Integer msgCount,UserEntity user) throws ServiceException, DaoException{
		StringBuilder sqlSb = new StringBuilder();
		Integer status = params.getvalAsInt("status");
		sqlSb.append("select A.id,A.code,D.custName, ")
		 .append(" A.totalAmount,A.createTime  from  fc_Exempt A ")
		 .append("left join fc_LoanContract B on A.contractId=B.id ")
		 .append("left join ( ")
		 .append("(select XX.custType,XX.name as custName,YY.id as customerId  ")
		 .append("from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId ")
		 .append(" where XX.custType = 0 ")
		 .append(") ")
		 .append("union ")
		 .append("(select XX.custType,XX.name as custName,YY.id as customerId  ")
		 .append("from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType= 1) ")
		 .append(")  D on B.custType=D.custType and B.customerId=D.customerId  where A.isenabled = 1 ");
		 addWhereByActionType(params, sqlSb, user);
		 sqlSb.append(" and  A.status in  ("+status+") ");
		 sqlSb.append(" order by A.id desc ");
		if(null == msgCount) msgCount = DEFAULT_MSG_COUNT;
		DataTable dt = commonDao.getDatasBySql(sqlSb.toString(), "id,code,custName,totalAmount,createTime#yyyy-MM-dd", 0, msgCount);
		return  dt;
	}
	
	/**
	 * 获取参数
	 * @param params 原有参数 SHashMap 对象
	 * @param isBussProcc 是否子流程   true : 取子流程实例ID, false : 取业务品种流程实例ID
	 * @return 返回包含流程实例ID的参数对象
	 * @throws ServiceException
	 */
	private SHashMap<String, Object> getParams(SHashMap<String, Object> params,boolean isBussProcc)
			throws ServiceException {
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		String procIds = isBussProcc  ? bussProccFlowService.getProcIdsByUser(user) : fcFlowService.getProcIdsByUser(user);
		map.put("procId", procIds);
		return map;
	}
	
	
	private String getAuditWhere(SHashMap<String, Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append(" and A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
		String procId = map.getvalAsStr("procId");
		if(!StringHandler.isValidStr(procId)) procId = "-1"; 
		sb.append(" and A.procId in("+procId+") ");
		return sb.toString();
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
				String procIds = params.getvalAsStr("procId");
				if(!StringHandler.isValidStr(procIds)) procIds = "-1";
				sb.append(" and A.procId in ("+procIds+") ");
				break;
			}default:
				break;
			}
		}
	}
	

	 /**
	  * 根据  模块编号获取 DataTable 数据
	  * @param map 条件参数
	  * @return
	  * @throws DaoException
	  */
	@Override
	public <K, V> DataTable getDeskModByCodes(SHashMap<K, V> map) throws ServiceException {
		try{
			return deskModDao.getDeskModByCodes(map);
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
