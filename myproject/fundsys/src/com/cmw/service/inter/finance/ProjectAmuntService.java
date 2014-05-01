package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;


/**
 * 项目费用Service接口
 * @author liting
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收金额业务接口",createDate="2013-01-15T00:00:00",author="liting")
public interface ProjectAmuntService extends IService<ProjectAmuntEntity, Long> {
	 /**
		 * 获取正常扣收数据
		 * @param params	查询参数
		 * @param offset	分页起始位
		 * @param pageSize  每页大小
		 * @return 返回 DataTable 对象
		 * @throws DaoException
		 */
		 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws ServiceException;
}
