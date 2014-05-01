package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.PamountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;


/**
 * 项目费用Service接口
 * @author liting
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收金额业务接口",createDate="2013-01-15T00:00:00",author="liting")
public interface PamountRecordsService extends IService<PamountRecordsEntity, Long> {
}
