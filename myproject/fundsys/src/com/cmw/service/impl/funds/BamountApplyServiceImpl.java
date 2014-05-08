package com.cmw.service.impl.funds;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.omg.CORBA.DoubleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.BamountApplyDaoInter;
import com.cmw.dao.inter.funds.BamountRecordsDaoInter;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.dao.inter.funds.ShareInfoTranDaoInter;
import com.cmw.entity.funds.BamountApplyEntity;
import com.cmw.entity.funds.BamountRecordsEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.ShareInfoTranEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.funds.BamountApplyService;
import com.cmw.service.inter.funds.InterestService;
import com.cmw.service.inter.funds.ShareInfoTranService;
import com.cmw.service.inter.sys.VarietyService;


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
	
	@Autowired
	private EntrustContractDaoInter entrustContractDao;
	
	@Autowired
	private BamountRecordsDaoInter bamountrecordsDao;
	
	@Resource(name="interestService")
	private InterestService interestService;
	
	@Resource(name="varietyService")
	private VarietyService varietyService;
	
	@Override
	public GenericDaoInter<BamountApplyEntity, Long> getDao() {
		return bamountApplyDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = bamountApplyDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			if(null != dt || dt.getRowCount() > 0){
				setNameProces(dt);
			}
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	private void setNameProces(DataTable dt) throws ServiceException {        
		for(int i=0,count = dt.getRowCount();i<count;i++){
			String rate = dt.getString(i, "rate");//C.rate,C.unint StringHandler.isValidStr(rate)
			String unint=dt.getString(i,"unint");			
			if(StringHandler.isValidStr(unint)){
				Integer unints=Integer.parseInt(unint);
				if(unints==1){
					unint="%";
				}else if(unints==2){
					unint="‰";
				}
			}
			if(StringHandler.isValidStr(rate)){
				dt.setCellData(i, "rate", rate+unint);
			}
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
			return bamountApplyDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 撤资财务付款
	 */
	@Override
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		BamountApplyEntity entity=(BamountApplyEntity) complexData.get("entity");
		UserEntity user=(UserEntity) complexData.get("user");
		String accountId=(String) complexData.get("accountId");
		String rectDate=(String) complexData.get("rectDate");
		String settleType=(String) complexData.get("settleType");
		try {
			entity.setXstatus(2);
			bamountApplyDao.saveOrUpdateEntity(entity);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		//添加一条撤资付款记录
		BamountRecordsEntity bamountrecords=new BamountRecordsEntity();
		bamountrecords.setApplyId(entity.getId());
		if(StringHandler.isValidObj(settleType)){
			bamountrecords.setSettleType(Integer.parseInt(settleType));
		}
		bamountrecords.setWamount(entity.getWamount());
		bamountrecords.setBiamount(entity.getBiamount());
		bamountrecords.setRpamount(entity.getRpamount());
		BeanUtil.setCreateInfo(user, bamountrecords);
		if(StringHandler.isValidObj(rectDate)){
			bamountrecords.setRectDate(StringHandler.dateFormat("yyyy-MM-dd", rectDate));
		}
		if(StringHandler.isValidObj(accountId)){
			bamountrecords.setAccountId(Long.parseLong(accountId.split("##")[0]));
		}
		try {
			bamountrecordsDao.saveOrUpdateEntity(bamountrecords);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		//1.修改委托合同的撤资信息;2.重新生成还款计划
		try {
			EntrustContractEntity enContract=entrustContractDao.getEntity(entity.getEntrustContractId());
			enContract.setBackDate(entity.getBackDate());
			enContract.setBstatus(2);
			enContract.setBamount(enContract.getBamount().add(entity.getBamount()));
			enContract.setUamount(enContract.getAppAmount().subtract(enContract.getBamount()));
			entrustContractDao.saveOrUpdateEntity(enContract);
			
			//重新生成还款计划
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("entrustContract", enContract);
			map.put("bamountApply", entity);
			interestService.doComplexBusss(map);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}
