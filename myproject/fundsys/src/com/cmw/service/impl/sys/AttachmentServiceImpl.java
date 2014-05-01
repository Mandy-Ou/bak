package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.AttachmentEntity;
import com.cmw.dao.inter.sys.AttachmentDaoInter;
import com.cmw.service.inter.sys.AttachmentService;


/**
 * 附件  Service实现类
 * @author 程明卫
 * @date 2012-12-04T00:00:00
 */
@Description(remark="附件业务实现类",createDate="2012-12-04T00:00:00",author="程明卫")
@Service("attachmentService")
public class AttachmentServiceImpl extends AbsService<AttachmentEntity, Long> implements  AttachmentService {
	@Autowired
	private AttachmentDaoInter attachmentDao;
	@Override
	public GenericDaoInter<AttachmentEntity, Long> getDao() {
		return attachmentDao;
	}

}
