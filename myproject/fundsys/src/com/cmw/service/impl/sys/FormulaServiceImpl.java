package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.FormulaEntity;
import com.cmw.dao.inter.sys.FormulaDaoInter;
import com.cmw.service.inter.sys.FormulaService;


/**
 * 公式  Service实现类
 * @author pdh
 * @date 2012-12-06T00:00:00
 */
@Description(remark="公式业务实现类",createDate="2012-12-06T00:00:00",author="pdh")
@Service("formulaService")
public class FormulaServiceImpl extends AbsService<FormulaEntity, Long> implements  FormulaService {
	@Autowired
	private FormulaDaoInter formulaDao;
	@Override
	public GenericDaoInter<FormulaEntity, Long> getDao() {
		return formulaDao;
	}
	
}
