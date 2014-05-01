package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.FormulaDaoInter;
import com.cmw.entity.sys.FormulaEntity;


/**
 * 公式  DAO实现类
 * @author pdh
 * @date 2012-12-06T00:00:00
 */
@Description(remark="公式DAO实现类",createDate="2012-12-06T00:00:00",author="pdh")
@Repository("formulaDao")
public class FormulaDaoImpl extends GenericDaoAbs<FormulaEntity, Long> implements FormulaDaoInter {
	
}
