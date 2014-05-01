package com.cmw.service.impl.funds;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.funds.RqueryApplyEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.RqueryApplyDaoInter;
import com.cmw.service.inter.funds.RqueryApplyService;


/**
 * 汇票查询申请表  Service实现类
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */
@Description(remark="汇票查询申请表业务实现类",createDate="2014-02-24T00:00:00",author="郑符明")
@Service("rqueryApplyService")
public class RqueryApplyServiceImpl extends AbsService<RqueryApplyEntity, Long> implements  RqueryApplyService {
	@Autowired
	private RqueryApplyDaoInter rqueryApplyDao;
	@Override
	public GenericDaoInter<RqueryApplyEntity, Long> getDao() {
		return rqueryApplyDao;
	}
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		@SuppressWarnings("unchecked")
		List<RqueryApplyEntity> rqueryApplyEntities = (List<RqueryApplyEntity>) complexData.get("rqueryApplyEntities");
		UserEntity userEntity = (UserEntity) complexData.get("userEntity");
		if(null != rqueryApplyEntities && rqueryApplyEntities.size() > 0){
			for(RqueryApplyEntity rqueryApplyEntity : rqueryApplyEntities){
					try {
						//创建人
						rqueryApplyEntity.setCreator(userEntity.getCreator());
						//部门
						rqueryApplyEntity.setDeptId(userEntity.getDeptId());
						//组织
						rqueryApplyEntity.setOrgid(userEntity.getOrgid());
						Long id = (Long) complexData.get("id");
						rqueryApplyEntity.setId(id);
						rqueryApplyDao.saveOrUpdateEntity(rqueryApplyEntity);
					} catch (DaoException e) {
						e.printStackTrace();
						throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
					}
				}
		}
	}
}
