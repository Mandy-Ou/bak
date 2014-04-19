package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.PostEntity;
import com.cmw.dao.inter.sys.PostDaoInter;
import com.cmw.service.inter.sys.PostService;


/**
 * 职位  Service实现类
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
@Description(remark="职位业务实现类",createDate="2012-11-10T00:00:00",author="彭登浩")
@Service("postService")
public class PostServiceImpl extends AbsService<PostEntity, Long> implements  PostService {
	@Autowired
	private PostDaoInter postDao;
	@Override
	public GenericDaoInter<PostEntity, Long> getDao() {
		return postDao;
	}
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.sys.PostService#getPostList(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> DataTable getPostList(SHashMap<K, V> map) throws ServiceException{
		DataTable postDt = null;
		try {
			 postDt =  postDao.getPostList(map);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return postDt;
	}
	
}
