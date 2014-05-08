package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.PayIntApplyEntity;
import com.cmw.dao.inter.funds.PayIntApplyDaoInter;


/**
 * 付息申请表  DAO实现类
 * @author 李听
 * @date 2014-05-08T00:00:00
 */
@Description(remark="付息申请表DAO实现类",createDate="2014-05-08T00:00:00",author="李听")
@Repository("payIntApplyDao")
public class PayIntApplyDaoImpl extends GenericDaoAbs<PayIntApplyEntity, Long> implements PayIntApplyDaoInter {

}
