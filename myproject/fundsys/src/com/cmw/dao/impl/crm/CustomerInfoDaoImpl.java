package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.dao.inter.crm.CustomerInfoDaoInter;


/**
 * 个人客户基本信息  DAO实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="个人客户基本信息DAO实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Repository("customerInfoDao")
public class CustomerInfoDaoImpl extends GenericDaoAbs<CustomerInfoEntity, Long> implements CustomerInfoDaoInter {

}
