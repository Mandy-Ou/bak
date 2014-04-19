package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.OutsideRecordsEntity;
import com.cmw.dao.inter.finance.OutsideRecordsDaoInter;
import com.cmw.service.inter.finance.OutsideRecordsService;


/**
 * 表内外转化记录  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内外转化记录业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("outsideRecordsService")
public class OutsideRecordsServiceImpl extends AbsService<OutsideRecordsEntity, Long> implements  OutsideRecordsService {
	@Autowired
	private OutsideRecordsDaoInter outsideRecordsDao;
	@Override
	public GenericDaoInter<OutsideRecordsEntity, Long> getDao() {
		return outsideRecordsDao;
	}

}
