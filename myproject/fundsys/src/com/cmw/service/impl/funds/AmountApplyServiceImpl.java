package com.cmw.service.impl.funds;


import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.AmountApplyDaoInter;
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.inter.funds.AmountApplyService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.UserService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 增资申请  Service实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请业务实现类",createDate="2014-01-20T00:00:00",author="李听")
@Service("amountApplyService")
public class AmountApplyServiceImpl extends AbsService<AmountApplyEntity, Long> implements  AmountApplyService {
	@Autowired
	private AmountApplyDaoInter amountApplyDao;
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	@Resource(name="userService")
	private UserService userService;
	
	@Override
	public GenericDaoInter<AmountApplyEntity, Long> getDao() {
		return amountApplyDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = amountApplyDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
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
			String productsId = dt.getString(i, "productsId");
			if(StringHandler.isValidStr(productsId)){
				Long breed=Long.parseLong(productsId);
				VarietyEntity creatorObj=varietyService.getEntity(breed);
//							StringBuffer sb = new StringBuffer();
//							String dtName = StringHandler.RemoveStr(sb);
				if(StringHandler.isValidObj(creatorObj)){
					dt.setCellData(i, "productsId", creatorObj.getName());
				}
			}}
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
			return amountApplyDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
