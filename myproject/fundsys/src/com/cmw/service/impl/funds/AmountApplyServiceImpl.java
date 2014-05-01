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
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.dao.inter.funds.AmountApplyDaoInter;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.service.inter.funds.AmountApplyService;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.sys.BussProccService;


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
		SHashMap<Object, Object> params = new SHashMap<Object, Object>();
		params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		for(int i=0,count = dt.getRowCount();i<count;i++){
			String productsId = dt.getString(i, "productsId");
			if(StringHandler.isValidStr(productsId)){
				if(params.validKey("id")){
					params.remove("id");
				}
				params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+productsId);
				List<BussProccEntity>  bussProccEntityList = bussProccService.getEntityList(params);
				if( bussProccEntityList != null && bussProccEntityList.size()>0){
					StringBuffer sb = new StringBuffer();
					for(BussProccEntity x : bussProccEntityList){
						String name = x.getName();
						sb.append(name+",");
					}
					String dtName = StringHandler.RemoveStr(sb);
					dt.setCellData(i, "productsId", dtName);
				}
				
			}
		}
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
