package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EbankEntity;
import com.cmw.dao.inter.crm.EbankDaoInter;


/**
 * 企业开户  DAO实现类
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="企业开户DAO实现类",createDate="2012-12-25T00:00:00",author="pdh")
@Repository("ebankDao")
public class EbankDaoImpl extends GenericDaoAbs<EbankEntity, Long> implements EbankDaoInter {

}
