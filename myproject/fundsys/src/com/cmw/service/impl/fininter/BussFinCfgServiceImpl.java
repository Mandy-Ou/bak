package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.dao.inter.fininter.BussFinCfgDaoInter;
import com.cmw.service.inter.fininter.BussFinCfgService;


/**
 * 业务财务映射  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="业务财务映射业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("bussFinCfgService")
public class BussFinCfgServiceImpl extends AbsService<BussFinCfgEntity, Long> implements  BussFinCfgService {
	@Autowired
	private BussFinCfgDaoInter bussFinCfgDao;
	@Override
	public GenericDaoInter<BussFinCfgEntity, Long> getDao() {
		return bussFinCfgDao;
	}

}
