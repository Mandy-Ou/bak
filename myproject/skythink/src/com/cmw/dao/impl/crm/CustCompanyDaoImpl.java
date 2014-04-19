package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.CustCompanyEntity;
import com.cmw.dao.inter.crm.CustCompanyDaoInter;


/**
 * 个人旗下/企业关联公司  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人旗下/企业关联公司DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("custCompanyDao")
public class CustCompanyDaoImpl extends GenericDaoAbs<CustCompanyEntity, Long> implements CustCompanyDaoInter {

}
