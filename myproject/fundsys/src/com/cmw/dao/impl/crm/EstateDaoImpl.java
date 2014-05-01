package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EstateEntity;
import com.cmw.dao.inter.crm.EstateDaoInter;


/**
 * 房产物业信息  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="房产物业信息DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("estateDao")
public class EstateDaoImpl extends GenericDaoAbs<EstateEntity, Long> implements EstateDaoInter {

}
