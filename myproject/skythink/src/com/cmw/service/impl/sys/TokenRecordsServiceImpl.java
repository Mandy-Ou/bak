package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.TokenRecordsEntity;
import com.cmw.dao.inter.sys.TokenRecordsDaoInter;
import com.cmw.service.inter.sys.TokenRecordsService;


/**
 * 并行令牌记录  Service实现类
 * @author 程明卫
 * @date 2013-12-07T00:00:00
 */
@Description(remark="并行令牌记录业务实现类",createDate="2013-12-07T00:00:00",author="程明卫")
@Service("tokenRecordsService")
public class TokenRecordsServiceImpl extends AbsService<TokenRecordsEntity, Long> implements  TokenRecordsService {
	@Autowired
	private TokenRecordsDaoInter tokenRecordsDao;
	@Override
	public GenericDaoInter<TokenRecordsEntity, Long> getDao() {
		return tokenRecordsDao;
	}

}
