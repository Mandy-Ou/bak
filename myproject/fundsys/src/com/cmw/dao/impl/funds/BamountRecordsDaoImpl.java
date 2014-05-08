package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.BamountRecordsEntity;
import com.cmw.dao.inter.funds.BamountRecordsDaoInter;


/**
 * 撤资付款记录  DAO实现类
 * @author zsl
 * @date 2014-05-06T00:00:00
 */
@Description(remark="撤资付款记录DAO实现类",createDate="2014-05-06T00:00:00",author="zsl")
@Repository("bamountRecordsDao")
public class BamountRecordsDaoImpl extends GenericDaoAbs<BamountRecordsEntity, Long> implements BamountRecordsDaoInter {

}
