package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.ReportTempEntity;
import com.cmw.dao.inter.sys.ReportTempDaoInter;


/**
 * 报表模板表  DAO实现类
 * @author 程明卫
 * @date 2013-01-19T00:00:00
 */
@Description(remark="报表模板表DAO实现类",createDate="2013-01-19T00:00:00",author="程明卫")
@Repository("reportTempDao")
public class ReportTempDaoImpl extends GenericDaoAbs<ReportTempEntity, Long> implements ReportTempDaoInter {

}
