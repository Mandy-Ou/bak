package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.MatSubjecEntity;
import com.cmw.dao.inter.sys.MatSubjecDaoInter;
import com.cmw.service.inter.sys.MatSubjecService;


/**
 * 资料标题  Service实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料标题业务实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Service("matSubjecService")
public class MatSubjecServiceImpl extends AbsService<MatSubjecEntity, Long> implements  MatSubjecService {
	@Autowired
	private MatSubjecDaoInter matSubjecDao;
	@Override
	public GenericDaoInter<MatSubjecEntity, Long> getDao() {
		return matSubjecDao;
	}

}
