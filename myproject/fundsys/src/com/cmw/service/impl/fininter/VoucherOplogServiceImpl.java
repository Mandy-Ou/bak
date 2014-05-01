package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.dao.inter.fininter.VoucherOplogDaoInter;
import com.cmw.service.inter.fininter.VoucherOplogService;


/**
 * 凭证日志  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证日志业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("voucherOplogService")
public class VoucherOplogServiceImpl extends AbsService<VoucherOplogEntity, Long> implements  VoucherOplogService {
	@Autowired
	private VoucherOplogDaoInter voucherOplogDao;
	@Override
	public GenericDaoInter<VoucherOplogEntity, Long> getDao() {
		return voucherOplogDao;
	}

}
