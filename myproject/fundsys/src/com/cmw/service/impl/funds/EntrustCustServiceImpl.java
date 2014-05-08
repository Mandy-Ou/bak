package com.cmw.service.impl.funds;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.EntrustCustDaoInter;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.funds.EntrustCustService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 委托客户资料  Service实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="委托客户资料业务实现类",createDate="2014-01-15T00:00:00",author="李听")
@Service("entrustCustService")
public class EntrustCustServiceImpl extends AbsService<EntrustCustEntity, Long> implements  EntrustCustService {
	@Autowired
	private EntrustCustDaoInter entrustCustDao;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	@Resource(name="userService")
	private UserService userService;
	@Override
	public GenericDaoInter<EntrustCustEntity, Long> getDao() {
		return entrustCustDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = entrustCustDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			if(null != dt || dt.getRowCount() > 0){
				setNameProce(dt);
			}
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * 
	 * @param dt
	 * @throws ServiceException
	 */
	
	private void setNameProce(DataTable dt) throws ServiceException {        
		for(int i=0,count = dt.getRowCount();i<count;i++){
			String productsId = dt.getString(i, "products");
			String creator = dt.getString(i, "creator");
			if(StringHandler.isValidStr(productsId)){
				Long breed=Long.parseLong(productsId);
				VarietyEntity creatorObj=varietyService.getEntity(breed);
				if(StringHandler.isValidObj(creatorObj)){
					dt.setCellData(i, "products", creatorObj.getName());
				}
			}
			if(StringHandler.isValidStr(creator)){
				Long creators=Long.parseLong(creator);
				UserEntity creatorObj=userService.getEntity(creators);
				if(StringHandler.isValidObj(creatorObj)){
					dt.setCellData(i, "creator", creatorObj.getEmpName());
				}
			}
	}
}
}
