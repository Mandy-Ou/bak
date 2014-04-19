package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.CreditInfoEntity;
import com.cmw.dao.inter.crm.CreditInfoDaoInter;


/**
 * 个人信用资料  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人信用资料DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("creditInfoDao")
public class CreditInfoDaoImpl extends GenericDaoAbs<CreditInfoEntity, Long> implements CreditInfoDaoInter {

}
