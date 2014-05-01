package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.funds.ReceInterestDaoInter;
import com.cmw.entity.funds.ReceInterestEntity;


/**
 * 汇票利润提成DAO实现类
 * @author 彭登浩
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票利润提成DAO实现类",createDate="2014-02-08T00:00:00",author="彭登浩")
@Repository("receInterestDao")
public class ReceInterestDaoImpl extends GenericDaoAbs<ReceInterestEntity, Long> implements ReceInterestDaoInter{
	
}
