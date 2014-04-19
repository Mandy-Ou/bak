package com.cmw.service.inter.finance;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

/**
 * 随借随还管理业务接口
 *Title: CurrentService.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-25下午12:46:37
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="随借随还管理业务接口",createDate="2013-11-22T00:00:00",author="彭登浩")
public interface CurrentService extends IService<Object, Long> {

	/** 获取随借随还 表格中的数据 的业务处理类
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	DataTable getCurrentList(SHashMap<String, Object> map, int start, int limit) throws ServiceException;
	/**
	 * 随借随还计算利息、管理费、罚息、滞纳金方法
	 * @param map 参数值
	 */
	public JSONObject calculate(SHashMap<String, Object> map) throws ServiceException;
	/**
	 * 获取用来计算还款计划的放款日期
	 * @param contractId	合同ID
	 * @return	返回合同放款日期
	 * @throws ServiceException 
	 */
	public Date getPayDate(Long contractId) throws ServiceException;
	/**
	 * 获取最后一次收款日期
	 * @param contractId	合同ID
	 * @return	返回最后一次收款日期
	 * @throws ServiceException 
	 */
	public Date getLastDate(Long contractId) throws ServiceException;
	
	/**
	 * 随借随还数据状态转换，把数据转换为结清状态 
	 * 借款合同id 
	 * @param params
	 * @throws Exception 
	 */
	public void settle(SHashMap<String, Object> params) throws ServiceException, Exception ;
}
