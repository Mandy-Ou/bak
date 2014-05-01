package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.dao.inter.finance.ProjectAmuntDaoInter;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ProjectAmuntService;


/**
 * 项目费用表)Service实现类
 * @author liting
 * @date 2013-01-15T00:00:00
 */
@Description(remark="项目费用表业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("projectAmuntService")
public class ProjectAmuntServiceImpl extends AbsService<ProjectAmuntEntity, Long> implements  ProjectAmuntService {
	@Autowired
	private ProjectAmuntDaoInter projectAmuntDao;
	@Override
	public GenericDaoInter<ProjectAmuntEntity, Long> getDao() {
		return projectAmuntDao;
	}
	/**
	 * 获取正常扣收数据
	 * @param params	查询参数
	 * @param offset	分页起始位
	 * @param pageSize  每页大小
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws ServiceException{
		 try {
			return projectAmuntDao.getNomalPlans(params, offset, pageSize);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	 }

}
