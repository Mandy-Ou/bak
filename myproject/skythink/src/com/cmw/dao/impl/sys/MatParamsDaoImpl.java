package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.MatParamsEntity;
import com.cmw.dao.inter.sys.MatParamsDaoInter;


/**
 * 资料项  DAO实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料项DAO实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Repository("matParamsDao")
public class MatParamsDaoImpl extends GenericDaoAbs<MatParamsEntity, Long> implements MatParamsDaoInter {

}
