package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.fininter.VoucherGroupEntity;
import com.cmw.dao.inter.fininter.VoucherGroupDaoInter;


/**
 * 凭证字  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证字DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("voucherGroupDao")
public class VoucherGroupDaoImpl extends GenericDaoAbs<VoucherGroupEntity, Long> implements VoucherGroupDaoInter {

}
