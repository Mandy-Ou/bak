package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.MsgNotifyEntity;
import com.cmw.dao.inter.sys.MsgNotifyDaoInter;


/**
 * 消息通知  DAO实现类
 * @author pdh
 * @date 2013-10-08T00:00:00
 */
@Description(remark="消息通知DAO实现类",createDate="2013-10-08T00:00:00",author="pdh")
@Repository("msgNotifyDao")
public class MsgNotifyDaoImpl extends GenericDaoAbs<MsgNotifyEntity, Long> implements MsgNotifyDaoInter {

}
