package com.cmw.service.impl.funds;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.funds.BackInvoceEntity;
import com.cmw.entity.funds.BackReceiptEntity;
import com.cmw.dao.inter.funds.BackInvoceDaoInter;
import com.cmw.service.inter.funds.BackInvoceService;


/**
 * 汇票回款单表  Service实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票回款单表业务实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Service("backInvoceService")
public class BackInvoceServiceImpl extends AbsService<BackInvoceEntity, Long> implements  BackInvoceService {
	@Autowired
	private BackInvoceDaoInter backInvoceDao;
	@Override
	public GenericDaoInter<BackInvoceEntity, Long> getDao() {
		return backInvoceDao;
	}

}
