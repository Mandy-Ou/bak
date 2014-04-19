package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EeqstructEntity;
import com.cmw.dao.inter.crm.EeqstructDaoInter;


/**
 * 银行借款情况  DAO实现类
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="银行借款情况DAO实现类",createDate="2012-12-25T00:00:00",author="pdh")
@Repository("eeqstructDao")
public class EeqstructDaoImpl extends GenericDaoAbs<EeqstructEntity, Long> implements EeqstructDaoInter {

}
