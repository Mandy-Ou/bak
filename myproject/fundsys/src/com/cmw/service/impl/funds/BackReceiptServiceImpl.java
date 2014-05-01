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

import com.cmw.entity.funds.BackInvoceEntity;
import com.cmw.entity.funds.BackReceiptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.BackInvoceDaoInter;
import com.cmw.dao.inter.funds.BackReceiptDaoInter;
import com.cmw.service.inter.funds.BackReceiptService;


/**
 * 回款收条表  Service实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="回款收条表业务实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Service("backReceiptService")
public class BackReceiptServiceImpl extends AbsService<BackReceiptEntity, Long> implements  BackReceiptService {
	@Autowired
	private BackReceiptDaoInter backReceiptDao;
	@Autowired
	private BackInvoceDaoInter backInvoceDao;
	@Override
	public GenericDaoInter<BackReceiptEntity, Long> getDao() {
		return backReceiptDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		//获取回款收条数据的集合
		List<BackReceiptEntity> backReceiptEntities = (List<BackReceiptEntity>) complexData.get("backReceiptEntities");
		//获取回款收条结算单的集合
		List<BackInvoceEntity> backInvoceEntities = (List<BackInvoceEntity>) complexData.get("backInvoceEntities");
		UserEntity userEntity = (UserEntity) complexData.get("userEntity");
		if( null != backReceiptEntities && backReceiptEntities.size() > 0){
			for(BackReceiptEntity backReceiptEntity : backReceiptEntities){
				//创建人
				backReceiptEntity.setCreator(userEntity.getCreator());
				//部门
				backReceiptEntity.setDeptId(userEntity.getDeptId());
				//组织
				backReceiptEntity.setOrgid(userEntity.getOrgid());
				try {
					backReceiptDao.saveOrUpdateEntity(backReceiptEntity);
				} catch (DaoException e) {
					e.printStackTrace();
					throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
				}
				/**
				 * 这里如果使用一个集合先将回款收条集合中的每一个实体的id进行存放，然后再遍历回款结算单赋值进去也行。
				 */
				if( null != backInvoceEntities && backInvoceEntities.size() > 0){
					for(BackInvoceEntity backInvoceEntity : backInvoceEntities){
						//回款收条的id
						backInvoceEntity.setBackReceiptId(backReceiptEntity.getId());
						//创建人
						backInvoceEntity.setCreator(-1L);
						//部门
						backInvoceEntity.setDeptId(8L);
						//组织
						backInvoceEntity.setOrgid(-1L);
						try {
							backInvoceDao.saveOrUpdateEntity(backInvoceEntity);
						} catch (DaoException e) {
							e.printStackTrace();
							throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
						}
					}
					
				}
			}
		}
	}
}
