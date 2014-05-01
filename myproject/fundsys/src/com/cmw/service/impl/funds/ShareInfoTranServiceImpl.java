package com.cmw.service.impl.funds;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.service.inter.funds.ShareInfoTranService;


/**
 * 增资申请  Service实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请业务实现类",createDate="2014-01-20T00:00:00",author="李听")
@Service("shareInfoTranService")
public class ShareInfoTranServiceImpl extends AbsService<ShareInfoTranEntity, Long> implements  ShareInfoTranService {
	@Autowired
	private ShareInfoTranDaoInter shareInfoTranDao;
	@Override
	public GenericDaoInter<ShareInfoTranEntity, Long> getDao() {
		return shareInfoTranDao;
	}
	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		List<ShareInfoTranEntity> list=(List<ShareInfoTranEntity>) complexData.get("list");
		this.batchSaveOrUpdateEntitys(list);
	}
}
