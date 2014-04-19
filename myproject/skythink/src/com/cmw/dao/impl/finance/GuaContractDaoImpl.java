package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.GuaContractEntity;
import com.cmw.dao.inter.finance.GuaContractDaoInter;


/**
 * 保证合同  DAO实现类
 * @author pdt
 * @date 2013-01-13T00:00:00
 */
@Description(remark="保证合同DAO实现类",createDate="2013-01-13T00:00:00",author="pdt")
@Repository("guaContractDao")
public class GuaContractDaoImpl extends GenericDaoAbs<GuaContractEntity, Long> implements GuaContractDaoInter {

}
