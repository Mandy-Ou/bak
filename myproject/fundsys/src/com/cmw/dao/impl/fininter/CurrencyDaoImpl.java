package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.dao.inter.fininter.CurrencyDaoInter;


/**
 * 币别  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="币别DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("currencyDao")
public class CurrencyDaoImpl extends GenericDaoAbs<CurrencyEntity, Long> implements CurrencyDaoInter {

}
