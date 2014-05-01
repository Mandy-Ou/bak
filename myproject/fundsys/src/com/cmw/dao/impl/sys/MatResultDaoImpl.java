package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.MatResultEntity;
import com.cmw.dao.inter.sys.MatResultDaoInter;


/**
 * 资料确认结果  DAO实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料确认结果DAO实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Repository("matResultDao")
public class MatResultDaoImpl extends GenericDaoAbs<MatResultEntity, Long> implements MatResultDaoInter {

}
