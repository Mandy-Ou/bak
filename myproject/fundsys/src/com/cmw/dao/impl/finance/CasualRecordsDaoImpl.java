package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.dao.inter.finance.CasualRecordsDaoInter;


/**
 * 实收随借随还表  DAO实现类
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="实收随借随还表DAO实现类",createDate="2014-01-07T00:00:00",author="程明卫")
@Repository("casualRecordsDao")
public class CasualRecordsDaoImpl extends GenericDaoAbs<CasualRecordsEntity, Long> implements CasualRecordsDaoInter {

}
