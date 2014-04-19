package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.VoucherTempEntity;
import com.cmw.dao.inter.fininter.VoucherTempDaoInter;
import com.cmw.service.inter.fininter.VoucherTempService;


/**
 * 凭证模板  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证模板业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("voucherTempService")
public class VoucherTempServiceImpl extends AbsService<VoucherTempEntity, Long> implements  VoucherTempService {
	@Autowired
	private VoucherTempDaoInter voucherTempDao;
	@Override
	public GenericDaoInter<VoucherTempEntity, Long> getDao() {
		return voucherTempDao;
	}

}
