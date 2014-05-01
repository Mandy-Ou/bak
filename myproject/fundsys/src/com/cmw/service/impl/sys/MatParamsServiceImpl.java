package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.MatParamsEntity;
import com.cmw.dao.inter.sys.MatParamsDaoInter;
import com.cmw.service.inter.sys.MatParamsService;


/**
 * 资料项  Service实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料项业务实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Service("matParamsService")
public class MatParamsServiceImpl extends AbsService<MatParamsEntity, Long> implements  MatParamsService {
	@Autowired
	private MatParamsDaoInter matParamsDao;
	@Override
	public GenericDaoInter<MatParamsEntity, Long> getDao() {
		return matParamsDao;
	}

}
