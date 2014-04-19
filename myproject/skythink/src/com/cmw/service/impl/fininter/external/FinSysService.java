package com.cmw.service.impl.fininter.external;

import java.util.List;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.fininter.AcctGroupEntity;
import com.cmw.entity.fininter.BankAccountEntity;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.entity.fininter.UserMappingEntity;
import com.cmw.entity.fininter.VoucherGroupEntity;
import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.service.impl.fininter.external.generic.VoucherModel;

/**
 * 财务数据访问接口
 * @author chengmingwei
 *
 */
public interface FinSysService {
	/**
	 * 服务器连接测试
	 * @throws ServiceException
	 */
	void testConnection() throws ServiceException;
	/**
	 * 获取用户映射数据
	 * @param params 过滤参数
	 * @return 返回用户映射数据列表
	 */
	List<UserMappingEntity> getUserMappings(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取科目组数据
	 * @param params 过滤参数
	 * @return 返回科目组数据列表
	 */
	List<AcctGroupEntity> getAcctGroups(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取科目数据
	 * @param params 过滤参数
	 * @return 返回科目数据列表
	 */
	List<SubjectEntity> getSubjects(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取币别数据
	 * @param params 过滤参数
	 * @return 返回币别数据列表
	 */
	List<CurrencyEntity> getCurrencys(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取核算项类别数据
	 * @param params 过滤参数
	 * @return 返回核算项类别数据列表
	 */
	List<ItemClassEntity> getItemClasses(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取结算方式数据
	 * @param params 过滤参数
	 * @return 返回结算方式数据列表
	 */
	List<SettleEntity> getSettles(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取银行账号数据
	 * @param params 过滤参数
	 * @return 返回银行账号数据列表
	 */
	List<BankAccountEntity> getBankAccounts(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取凭证字数据
	 * @param params 过滤参数
	 * @return 返回凭证字数据列表
	 */
	List<VoucherGroupEntity> getVoucherGroups(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 保存核算项数据
	 * @param <T> 要保存的实体
	 * @param params 过滤参数
	 * @return 返回数据列表
	 */
	<T> SHashMap<String, Object> saveItem(T entity,SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 批量保存核算项数据
	 * @param <T>
	 * @param params 过滤参数
	 * @return 返回需要传回的 SHashMap 数据
	 */
	<T> SHashMap<String, Object> saveItems(List<T> entitys,SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 批量保存凭证数据
	 * @param <T> entitys 凭证模型对象列表
	 * @param params 过滤参数
	 * @return 返回凭证日志列表 数据
	 */
	List<VoucherOplogEntity> saveVouchers(List<VoucherModel> vouchers,SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 删除财务系统凭证
	 * @param ids	财务系统凭证ID或编号
	 * @param params	附加参数
	 * @throws ServiceException
	 */
	void deleteVouchers(String ids,SHashMap<String, Object> params) throws ServiceException;
	
}
