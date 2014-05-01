package com.cmw.service.impl.funds;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.ShareDataEntity;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.dao.inter.funds.AmountApplyDaoInter;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.dao.inter.funds.ShareDataDaoInter;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;
import com.cmw.service.inter.funds.AmountApplyService;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.funds.ShareDataService;
import com.cmw.service.inter.funds.ShareInfoTranService;


/**
 * 增资申请  Service实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请业务实现类",createDate="2014-01-20T00:00:00",author="李听")
@Service("shareDataService")
public class ShareDataServiceImpl extends AbsService<ShareDataEntity, Long> implements  ShareDataService {
	@Autowired
	private ShareDataDaoInter shareDataDao;
	@Override
	public GenericDaoInter<ShareDataEntity, Long> getDao() {
		return shareDataDao;
	}

}
