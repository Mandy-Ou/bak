package com.cmw.core.base.exception;

import java.util.HashMap;
import java.util.Map;

import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	private static Map<String,String> i18nMap = new HashMap<String, String>();
	private static Map<String,Object[]> argsMap = new HashMap<String, Object[]>();
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(UserEntity user,String message,Object... args) {
		super(message);
		String i18n = (null == user || !StringHandler.isValidStr(user.getI18n())) ? UserEntity.I18N_ZH_CN : user.getI18n();
		i18nMap.put(message, i18n);
		argsMap.put(message, args);
	}

	
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 指 message_*.properties 中的字符串
	 * 根据资源键获取资源文件中字符串所需的参数数组
	 * @param key	资源键
	 * @param isRemove
	 * @return
	 */
	public static Map<String,Object[]> getArgsByKey(String key,boolean isRemove) {
		if(!StringHandler.isValidStr(key)) return null;
		Object[] arg = argsMap.get(key);
		if(null != arg){
			String i18n = i18nMap.get(key);
			Map<String,Object[]> map = new HashMap<String, Object[]>();
			map.put(i18n, arg);
			if(isRemove){
				argsMap.remove(key);
				i18nMap.remove(key);
			}
			return map;
		}
		return null;
	}
	/**
	 * 没有系统id
	 */
	public static final String NO_SYSID_ERROR = "no.sysid.error";
	/**
	 * 没有系统id
	 */
	public static final String NO_NAME_ERROR = "no.name.error";
	/**
	 * 流程文件格式错误
	 */
	public static final String PROCESS_FILES_ERROR = "process.files.error";
	
	/**
	 * 流程部署失败
	 */
	public static final String PROCESS_DEPLOYMENT_FAILURE = "process.deployment.failure";
	/**
	 * 为空
	 */
	public static final String USER_IS_NULL = "user.is.null";
	/**
	 *没有添加借款合同
	 */
	public static final String LOANCONTRACT_IS_NULL = "LoanContract.failure";
	
	/**
	 * 没有业务品种
	 */
	public static final String BREED_IS_NULL = "no.bree.error";
	/**
	 * 客户类型不能为空的
	 */
	public static final String CUSTTYPE_IS_NULL = "custType.bree.error";
	/**
	 * ID 为空
	 */
	public static final String ID_IS_NULL = "id.is.null";
	/**
	 * code 为空
	 */
	public static final String CODE_IS_NULL = "code.is.null";
	/**
	 * 岗位ID 为空
	 */
	public static final String POSTID_IS_NULL = "postid.is.null";
	/**
	 * IDS 为空
	 */
	public static final String IDS_IS_NULL = "ids.is.null";

	/**
	 *  资源ID为空
	 */
	public static final String RESTYPEID_IS_NULL = "restypeid.is.null";
	/**
	 * 获取数据失败
	 * 
	 */
	public static final String DATA_GET_FAILURE = "data.get.failure";
	
	/**
	 * 分页查询的第一条记录索引 为空或0
	 */
	public static final String OFFSET_INDEX_IS_NULL = "offset.index.is.null";
	/**
	 * 过滤参数为空
	 */
	public static final String FILTER_PARAMS_NULL = "filter.params.null";
	/**
	 * 查询参数为空
	 */
	public static final String QUERY_PARAMS_NULL = "query.params.null";
	
	/**
	 * 查询的数据为空
	 */
	public static final String QUERY_DATA_IS_NULL = "query.data.is.null";
	
	/**
	 * 查询的指定对象为空
	 */
	public static final String QUERY_THE_OBJECT_IS_NULL = "query.the.object.is.null";
	
	
	/**
	 * 持久化对象为空
	 */
	public static final String OBJECT_IS_NULL = "object.is.null";
	/**
	 * 对象保存失败
	 */
	public static final String OBJECT_SAVE_FAILURE = "object.save.failure";
	/**
	 * 对象批量保存失败
	 */
	public static final String OBJECT_BATCH_SAVE_FAILURE = "object.batch.save.failure";
	/**
	 * 对象更新失败
	 */
	public static final String OBJECT_UPDATE_FAILURE = "object.update.failure";
	/**
	 * 对象批量更新失败
	 */
	public static final String OBJECT_BATCH_UPDATE_FAILURE = "object.batch.update.failure";
	/**
	 * 对象保存或更新失败
	 */
	public static final String OBJECT_SAVEORUPDATE_FAILURE = "object.saveorupdate.failure";
	/**
	 * 对象批量保存或更新失败
	 */
	public static final String OBJECT_BATCH_SAVEORUPDATE_FAILURE = "object.batch.saveorupdate.failure";
	/**
	 * 对象删除失败
	 */
	public static final String OBJECT_DELETE_FAILURE = "object.delete.failure";
	
	/**
	 * 对象禁用失败
	 */
	public static final String OBJECT_ENABLED_FAILURE = "object.enabled.failure";
	/**
	 * 获取总记录数失败
	 */
	public static final String OBJECT_TOTAL_FAILURE = "object.total.failure";
	/**
	 * 获取最大的ID失败
	 */
	public static final String OBJECT_MAXID_FAILURE = "object.maxid.failure";
	/**
	 * isenabled 的值为空
	 */
	public static final String ISENABLED_IS_NULL = "isenabled.value.null";
	
	/**
	 * "{0}" 是系统中的黑名单客户,其成为黑名单客户的原因如下 : 
	 */
	public static final String CUSTOMER_BLACK_ERROR = "customer.black.error";
	
	/**
	 * 未来周期未配置错误
	 */
	public static final String FUND_REPORT_CFG_ERROR = "fund.report.cfg.error";
	/**
	 * "{0}" 在系统中已经存在,其编号为:[{1}]
	 */
	public static final String CUSTOMER_EXIST_ERROR = "customer.exist.error";
	/**
	 * 业务财务映射对象为空
	 */
	public static final String BUSSFINCFGENTITY_IS_NULL = "bussFinCfgEntity.is.null";
	/**
	 * 财务系统配置对象为空
	 */
	public static final String FINSYSCFGENTITY_IS_NULL = "finSysCfgEntity.is.null";
	
	/**
	 * 网络连接失败
	 */
	public static final String NETWORK_CONN_ERROR = "network.conn.error";
	/**
	 * 没有配置子业务流程
	 */
	public static final String BUSSPROCC_NOT_CONFIG = "bussprocc.not.config";
	
	/**
	 * 放款金额不能小于0
	 */
	public static final String LOANAMOUNT_ERROR = "loanAmount.less.than.zero";
	/**
	 * 帐号XXX的自有资金未初始化
	 */
	public static final String FUNDS_ERROR = "fundsAccount.nohave";
	/**
	 * 帐号XXX的自有资金余额不足
	 */
	public static final String INSUFFICIENT_BALANCE = "insufficient.balance";
	/**
	 * 当前客户的费用在本次提前收款之前已经被其它用户结清了
	 */
	public static final String PREPAYMENT_BEFORE_FINISH = "prepayment.before.finish";
	/**
	 * 随借随还收款日期不能为空
	 */
	public static final String CURRENT_RECDATE_ISNOTNULL = "current.recDate.isnotnull";
}
