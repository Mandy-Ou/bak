package com.cmw.service.inter.crm;


import java.util.List;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 客户基础信息  Service接口
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户基础信息业务接口",createDate="2012-12-12T00:00:00",author="程明卫")
public interface CustBaseService extends IService<CustBaseEntity, Long> {
	public <K, V> DataTable getDialogResultList(SHashMap<K, V> map,final int offset, final int pageSize)
			throws ServiceException;
	
	/**
	 * 获取未同步的客户基础数据
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public <K, V> List<CustBaseEntity> getSynsCustBaseList(SHashMap<K, V> map) throws ServiceException;
	/**
	 * 更新客户基础信息中的财务系统 refId 值
	 * @param refMap 
	 * @param currUser
	 * @throws DaoException
	 */
	void updateCustBaseRefIds(SHashMap<String, Object> refMap, UserEntity currUser) throws ServiceException;
}
