package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.dao.inter.fininter.ItemClassDaoInter;
import com.cmw.service.inter.fininter.ItemClassService;


/**
 * 核算项类别  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项类别业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("itemClassService")
public class ItemClassServiceImpl extends AbsService<ItemClassEntity, Long> implements  ItemClassService {
	@Autowired
	private ItemClassDaoInter itemClassDao;
	@Override
	public GenericDaoInter<ItemClassEntity, Long> getDao() {
		return itemClassDao;
	}
	@Override
	public String getRefIds(String objectName) throws ServiceException {
		try {
			String hql = "select refId from "+objectName+" A ";
			DataTable dt = itemClassDao.getResultList(hql, -1, -1);
			if(null == dt || dt.getRowCount() == 0) return "-1";
			StringBuffer sb = new StringBuffer();
			for(int i=0,count=dt.getRowCount(); i<count; i++){
				Object refId = dt.getDataSource().get(i);
				if(!StringHandler.isValidObj(refId)) continue;
				sb.append(refId).append(",");
			}
			return StringHandler.RemoveStr(sb);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	
	}
}
