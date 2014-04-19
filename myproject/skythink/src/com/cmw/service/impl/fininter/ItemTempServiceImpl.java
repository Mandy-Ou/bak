package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.ItemTempEntity;
import com.cmw.dao.inter.fininter.ItemTempDaoInter;
import com.cmw.service.inter.fininter.ItemTempService;


/**
 * 核算项  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("itemTempService")
public class ItemTempServiceImpl extends AbsService<ItemTempEntity, Long> implements  ItemTempService {
	@Autowired
	private ItemTempDaoInter itemTempDao;
	@Override
	public GenericDaoInter<ItemTempEntity, Long> getDao() {
		return itemTempDao;
	}

}
