package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.TokenRecordsEntity;
import com.cmw.dao.inter.sys.TokenRecordsDaoInter;


/**
 * 并行令牌记录  DAO实现类
 * @author 程明卫
 * @date 2013-12-07T00:00:00
 */
@Description(remark="并行令牌记录DAO实现类",createDate="2013-12-07T00:00:00",author="程明卫")
@Repository("tokenRecordsDao")
public class TokenRecordsDaoImpl extends GenericDaoAbs<TokenRecordsEntity, Long> implements TokenRecordsDaoInter {

}
