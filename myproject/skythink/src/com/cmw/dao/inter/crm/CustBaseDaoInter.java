package com.cmw.dao.inter.crm;


import java.util.List;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 客户基础信息  DAO接口
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
 @Description(remark="客户基础信息Dao接口",createDate="2012-12-12T00:00:00",author="程明卫")
public interface CustBaseDaoInter  extends GenericDaoInter<CustBaseEntity, Long>{
	 /**
		 * 根据指定的参数 获取弹出框结果集列表
		 * 注：【该方法在抽象类中未实现，子类须重写】
		 * @param <K>
		 * @param <V>
		 * @param map 过滤参数
		 * @return 返回符合条件的 DataTable 对象
		 * @throws DaoException  抛出DaoException 
		 */
		<K, V> DataTable getDialogResultList(SHashMap<K, V> map,final int offset, final int pageSize)throws DaoException;
		
		<K, V> List<CustBaseEntity> getSynsCustBaseList(SHashMap<K, V> map)throws DaoException;
		/**
		 * 更新客户基础信息中的财务系统 refId 值
		 * @param refMap 
		 * @param currUser
		 * @throws DaoException
		 */
		void updateCustBaseRefIds(SHashMap<String, Object> refMap, UserEntity currUser) throws DaoException;
}
