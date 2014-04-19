package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.OrderEntity;
import com.cmw.dao.inter.finance.OrderDaoInter;
import com.cmw.service.inter.finance.OrderService;


/**
 * 扣款优先级  Service实现类
 * @author pdt
 * @date 2012-12-22T00:00:00
 */
@Description(remark="扣款优先级业务实现类",createDate="2012-12-22T00:00:00",author="pdt")
@Service("orderService")
public class OrderServiceImpl extends AbsService<OrderEntity, Long> implements  OrderService {
	@Autowired
	private OrderDaoInter orderDao;
	@Override
	public GenericDaoInter<OrderEntity, Long> getDao() {
		return orderDao;
	}

}
