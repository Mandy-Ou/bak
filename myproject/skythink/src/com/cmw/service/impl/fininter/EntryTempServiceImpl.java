package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.EntryTempEntity;
import com.cmw.dao.inter.fininter.EntryTempDaoInter;
import com.cmw.service.inter.fininter.EntryTempService;


/**
 * 分录模板  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="分录模板业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("entryTempService")
public class EntryTempServiceImpl extends AbsService<EntryTempEntity, Long> implements  EntryTempService {
	@Autowired
	private EntryTempDaoInter entryTempDao;
	@Override
	public GenericDaoInter<EntryTempEntity, Long> getDao() {
		return entryTempDao;
	}

}
