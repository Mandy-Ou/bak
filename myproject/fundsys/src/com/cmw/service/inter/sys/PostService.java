package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.PostEntity;


/**
 * 职位  Service接口
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
@Description(remark="职位业务接口",createDate="2012-11-10T00:00:00",author="彭登浩")
public interface PostService extends IService<PostEntity, Long> {

	/**获取节点下面的所有职位
	 * @param map
	 * @return
	 */
	<K, V> DataTable getPostList(SHashMap<K, V> map) throws ServiceException;
}
