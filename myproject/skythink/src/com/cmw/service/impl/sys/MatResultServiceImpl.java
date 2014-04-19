package com.cmw.service.impl.sys;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.MatResultDaoInter;
import com.cmw.entity.sys.MatResultEntity;
import com.cmw.service.inter.sys.MatResultService;


/**
 * 资料确认结果  Service实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料确认结果业务实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Service("matResultService")
public class MatResultServiceImpl extends AbsService<MatResultEntity, Long> implements  MatResultService {
	@Autowired
	private MatResultDaoInter matResultDao;
	
	@Override
	public GenericDaoInter<MatResultEntity, Long> getDao() {
		return matResultDao;
	}
}
