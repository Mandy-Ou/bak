package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EbankBorrEntity;
import com.cmw.dao.inter.crm.EbankBorrDaoInter;


/**
 * 银行借款情况  DAO实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="银行借款情况DAO实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Repository("ebankBorrDao")
public class EbankBorrDaoImpl extends GenericDaoAbs<EbankBorrEntity, Long> implements EbankBorrDaoInter {

}
