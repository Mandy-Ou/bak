package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.dao.inter.fininter.SettleDaoInter;


/**
 * 结算方式  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="结算方式DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("settleDao")
public class SettleDaoImpl extends GenericDaoAbs<SettleEntity, Long> implements SettleDaoInter {

}
