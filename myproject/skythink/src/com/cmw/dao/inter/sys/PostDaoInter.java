package com.cmw.dao.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.PostEntity;


/**
 * 职位  DAO接口
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
 @Description(remark="职位Dao接口",createDate="2012-11-10T00:00:00",author="彭登浩")
public interface PostDaoInter  extends GenericDaoInter<PostEntity, Long>{

	/**获取部门下面的所有职位
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	 <K, V> DataTable getPostList(SHashMap<K, V> map) throws DaoException;
	 
}
