package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.VoucherGroupEntity;
import com.cmw.dao.inter.fininter.VoucherGroupDaoInter;
import com.cmw.service.inter.fininter.VoucherGroupService;


/**
 * 凭证字  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证字业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("voucherGroupService")
public class VoucherGroupServiceImpl extends AbsService<VoucherGroupEntity, Long> implements  VoucherGroupService {
	@Autowired
	private VoucherGroupDaoInter voucherGroupDao;
	@Override
	public GenericDaoInter<VoucherGroupEntity, Long> getDao() {
		return voucherGroupDao;
	}

}
