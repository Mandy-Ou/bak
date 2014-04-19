package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.DeskModEntity;


/**
 * 桌面模块配置  Service接口
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="桌面模块配置业务接口",createDate="2013-03-08T00:00:00",author="程明卫")
public interface DeskModService extends IService<DeskModEntity, Long> {
	/**
	 * 获取桌面版块数据
	 * @param map 参数
	 * @return 返回 DataTable 对象
	 */
	DataTable getContents(SHashMap<String, Object> map) throws ServiceException;

	<K,V> DataTable getDeskModByCodes(SHashMap<K, V> map) throws ServiceException;
}
