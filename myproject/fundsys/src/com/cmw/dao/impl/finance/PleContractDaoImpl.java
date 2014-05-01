package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.PleContractEntity;
import com.cmw.dao.inter.finance.PleContractDaoInter;


/**
 * 质押合同  DAO实现类
 * @author pdh
 * @date 2013-02-02T00:00:00
 */
@Description(remark="质押合同DAO实现类",createDate="2013-02-02T00:00:00",author="pdh")
@Repository("pleContractDao")
public class PleContractDaoImpl extends GenericDaoAbs<PleContractEntity, Long> implements PleContractDaoInter {

}
