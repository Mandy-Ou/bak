package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.OtherInfoEntity;
import com.cmw.dao.inter.crm.OtherInfoDaoInter;
import com.cmw.service.inter.crm.OtherInfoService;


/**
 * 其它信息  Service实现类
 * @author pdh
 * @date 2013-03-31T00:00:00
 */
@Description(remark="其它信息业务实现类",createDate="2013-03-31T00:00:00",author="pdh")
@Service("otherInfoService")
public class OtherInfoServiceImpl extends AbsService<OtherInfoEntity, Long> implements  OtherInfoService {
	@Autowired
	private OtherInfoDaoInter otherInfoDao;
	@Override
	public GenericDaoInter<OtherInfoEntity, Long> getDao() {
		return otherInfoDao;
	}

}
