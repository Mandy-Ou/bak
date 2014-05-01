package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EbankBorrEntity;
import com.cmw.dao.inter.crm.EbankBorrDaoInter;
import com.cmw.service.inter.crm.EbankBorrService;


/**
 * 银行借款情况  Service实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="银行借款情况业务实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Service("ebankBorrService")
public class EbankBorrServiceImpl extends AbsService<EbankBorrEntity, Long> implements  EbankBorrService {
	@Autowired
	private EbankBorrDaoInter ebankBorrDao;
	@Override
	public GenericDaoInter<EbankBorrEntity, Long> getDao() {
		return ebankBorrDao;
	}

}
