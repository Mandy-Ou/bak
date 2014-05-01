package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.AddressEntity;
import com.cmw.dao.inter.crm.AddressDaoInter;


/**
 * 客户住宅信息  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="客户住宅信息DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("addressDao")
public class AddressDaoImpl extends GenericDaoAbs<AddressEntity, Long> implements AddressDaoInter {

}
