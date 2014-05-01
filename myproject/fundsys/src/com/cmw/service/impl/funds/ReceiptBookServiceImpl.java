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
import com.cmw.core.util.SHashMap;

import com.cmw.entity.funds.RbrelationEntity;
import com.cmw.entity.funds.ReceiptBookAttachmentEntity;
import com.cmw.entity.funds.ReceiptBookEntity;
import com.cmw.entity.funds.SettlementEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.ReceiptBookAttachmentDaoInter;
import com.cmw.dao.inter.funds.ReceiptBookDaoInter;
import com.cmw.dao.inter.funds.SettlementDaoInter;
import com.cmw.service.inter.funds.RbrelationService;
import com.cmw.service.inter.funds.ReceiptBookService;


/**
 * 汇票转让承诺书表  Service实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票转让承诺书表业务实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Service("receiptBookService")
public class ReceiptBookServiceImpl extends AbsService<ReceiptBookEntity, Long> implements  ReceiptBookService {
	@Autowired
	private ReceiptBookDaoInter receiptBookDao;
	@Autowired
	private ReceiptBookAttachmentDaoInter receiptBookAttachmentDao;
	@Autowired
	private  SettlementDaoInter settlementDao;
	@Autowired
	private  RbrelationService rbrelationService;
	@Override
	public GenericDaoInter<ReceiptBookEntity, Long> getDao() {
		return receiptBookDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		List<ReceiptBookEntity> receiptBookEntities = (List<ReceiptBookEntity>) complexData.get("receiptBookEntities");
		List<ReceiptBookAttachmentEntity> receiptBookAttachmentEntities = (List<ReceiptBookAttachmentEntity>) complexData.get("receiptBookAttachmentEntities");
		List<SettlementEntity> settlementEntities = (List<SettlementEntity>)complexData.get("settlementEntities");
		Long id = (Long) complexData.get("id");
		Long receiptId = (Long) complexData.get("receiptId");
		UserEntity userEntity = (UserEntity) complexData.get("userEntity");
		if(null != receiptBookEntities && receiptBookEntities.size() >0 ){
			for(ReceiptBookEntity  receiptBookEntity : receiptBookEntities ){
					try {
						//汇票承诺书进行保存
						receiptBookEntity.setBreed(-1L);
						receiptBookEntity.setStatus(0);
						receiptBookEntity.setCode("asdfasfd");
						//创建人
						receiptBookEntity.setCreator(userEntity.getCreator());
						//部门
						receiptBookEntity.setDeptId(userEntity.getDeptId());
						//组织
						receiptBookEntity.setOrgid(userEntity.getOrgid());
						if( null != id){
							receiptBookEntity.setId(id);
						}
						receiptBookDao.saveOrUpdateEntity(receiptBookEntity);
						/**
						 * 当承诺书标的数据保存之后，就要将 收条-汇票承诺书关联表的数据进行保存  关系：目前是一对一
						 */
						RbrelationEntity rbrelationEntity = new RbrelationEntity();
						rbrelationEntity.setReceiptBookId(receiptBookEntity.getId());
						rbrelationEntity.setReceiptId(receiptId);
						rbrelationService.saveOrUpdateEntity(rbrelationEntity);
					} catch (DaoException e) {
						e.printStackTrace();
						throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
					}
					/**
					 * 附件表：添加汇票承诺书的id，跟承诺书关系：多对一  进行批量保存
					 */
					if(null != receiptBookAttachmentEntities && receiptBookAttachmentEntities.size() > 0){
						for(ReceiptBookAttachmentEntity receiptBookAttachmentEntity : receiptBookAttachmentEntities){
							receiptBookAttachmentEntity.setReceiptBookId(receiptBookEntity.getId());
							try {
									receiptBookAttachmentDao.saveOrUpdateEntity(receiptBookAttachmentEntity);
								} catch (DaoException e) {
									e.printStackTrace();
									throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
							}
						}
					}
					/**
					 * 汇票结算单表 ： 汇票承诺书 ---》一对一
					 */
					if(null != settlementEntities && settlementEntities.size() >0){
						for(SettlementEntity settlementEntity : settlementEntities){
							settlementEntity.setReceiptBookId(receiptBookEntity.getId());
							settlementEntity.setCreator(-1L);
							settlementEntity.setDeptId(8L);
							settlementEntity.setOrgid(-1L);
								try {
									settlementDao.saveOrUpdateEntity(settlementEntity);
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
