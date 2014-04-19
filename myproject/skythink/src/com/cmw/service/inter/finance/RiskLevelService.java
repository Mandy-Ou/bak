package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.RiskLevelEntity;


/**
 * 风险等级  Service接口
 * @author pdt
 * @date 2012-12-23T00:00:00
 */
@Description(remark="风险等级业务接口",createDate="2012-12-23T00:00:00",author="pdt")
public interface RiskLevelService extends IService<RiskLevelEntity, Long> {
	/**
	 * 根据指定的参数 获取下拉框数据源
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws ServiceException  抛出DaoException 
	 */
	<K, V> DataTable getDataSource(SHashMap<K, V> map) throws ServiceException;
}
