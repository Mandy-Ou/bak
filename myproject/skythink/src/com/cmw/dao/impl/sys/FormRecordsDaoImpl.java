package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.FormRecordsEntity;
import com.cmw.dao.inter.sys.FormRecordsDaoInter;


/**
 * 表单记录  DAO实现类
 * @author 程明卫
 * @date 2013-01-08T00:00:00
 */
@Description(remark="表单记录DAO实现类",createDate="2013-01-08T00:00:00",author="程明卫")
@Repository("formRecordsDao")
public class FormRecordsDaoImpl extends GenericDaoAbs<FormRecordsEntity, Long> implements FormRecordsDaoInter {

}
