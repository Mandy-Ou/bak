package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.SettlementEntity;
import com.cmw.dao.inter.funds.SettlementDaoInter;


/**
 * 汇票结算单表  DAO实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票结算单表DAO实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Repository("settlementDao")
public class SettlementDaoImpl extends GenericDaoAbs<SettlementEntity, Long> implements SettlementDaoInter {

}
