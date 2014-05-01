package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.MatTempEntity;
import com.cmw.dao.inter.sys.MatTempDaoInter;
import com.cmw.service.inter.sys.MatTempService;


/**
 * 资料模板  Service实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料模板业务实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Service("matTempService")
public class MatTempServiceImpl extends AbsService<MatTempEntity, Long> implements  MatTempService {
	@Autowired
	private MatTempDaoInter matTempDao;
	@Override
	public GenericDaoInter<MatTempEntity, Long> getDao() {
		return matTempDao;
	}

}
