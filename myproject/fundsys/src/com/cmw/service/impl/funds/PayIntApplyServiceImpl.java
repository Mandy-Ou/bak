package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.entity.funds.PayIntApplyEntity;
import com.cmw.dao.inter.funds.PayIntApplyDaoInter;
import com.cmw.service.inter.funds.PayIntApplyService;


/**
 * 付息申请表  Service实现类
 * @author 李听
 * @date 2014-05-08T00:00:00
 */
@Description(remark="付息申请表业务实现类",createDate="2014-05-08T00:00:00",author="李听")
@Service("payIntApplyService")
public class PayIntApplyServiceImpl extends AbsService<PayIntApplyEntity, Long> implements  PayIntApplyService {
	@Autowired
	private PayIntApplyDaoInter payIntApplyDao;
	@Override
	public GenericDaoInter<PayIntApplyEntity, Long> getDao() {
		return payIntApplyDao;
	}

}
