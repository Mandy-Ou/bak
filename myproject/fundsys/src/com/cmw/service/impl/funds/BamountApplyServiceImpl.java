package com.cmw.service.impl.funds;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.dao.inter.funds.BamountApplyDaoInter;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;
import com.cmw.entity.funds.BamountApplyEntity;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.service.inter.funds.BamountApplyService;
import com.cmw.service.inter.funds.ShareInfoTranService;


/**
 * 增资申请  Service实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请业务实现类",createDate="2014-01-20T00:00:00",author="李听")
@Service("bamountApplyService")
public class BamountApplyServiceImpl extends AbsService<BamountApplyEntity, Long> implements  BamountApplyService {
	@Autowired
	private BamountApplyDaoInter bamountApplyDao;
	@Override
	public GenericDaoInter<BamountApplyEntity, Long> getDao() {
		return bamountApplyDao;
	}
	/**
	 * 获取展期申请单详情
	 * @param id	申请单ID
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public DataTable detail(Long id) throws ServiceException{
		try {
			return bamountApplyDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
