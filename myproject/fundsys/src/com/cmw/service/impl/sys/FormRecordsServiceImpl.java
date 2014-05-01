package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.FormRecordsEntity;
import com.cmw.dao.inter.sys.FormRecordsDaoInter;
import com.cmw.service.inter.sys.FormRecordsService;


/**
 * 表单记录  Service实现类
 * @author 程明卫
 * @date 2013-01-08T00:00:00
 */
@Description(remark="表单记录业务实现类",createDate="2013-01-08T00:00:00",author="程明卫")
@Service("formRecordsService")
public class FormRecordsServiceImpl extends AbsService<FormRecordsEntity, Long> implements  FormRecordsService {
	@Autowired
	private FormRecordsDaoInter formRecordsDao;
	@Override
	public GenericDaoInter<FormRecordsEntity, Long> getDao() {
		return formRecordsDao;
	}

}
