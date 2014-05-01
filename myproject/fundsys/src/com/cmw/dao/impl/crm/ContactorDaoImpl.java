package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.ContactorEntity;
import com.cmw.dao.inter.crm.ContactorDaoInter;


/**
 * 联系人资料  DAO实现类
 * @author pdh
 * @date 2012-12-18T00:00:00
 */
@Description(remark="联系人资料DAO实现类",createDate="2012-12-18T00:00:00",author="pdh")
@Repository("contactorDao")
public class ContactorDaoImpl extends GenericDaoAbs<ContactorEntity, Long> implements ContactorDaoInter {

}
