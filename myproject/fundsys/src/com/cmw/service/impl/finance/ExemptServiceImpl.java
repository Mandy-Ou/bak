package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.finance.ExemptDaoInter;
import com.cmw.entity.finance.ExeItemsEntity;
import com.cmw.entity.finance.ExemptEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.ExeItemsService;
import com.cmw.service.inter.finance.ExemptService;


/**
 * 息费豁免申请  Service实现类
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免申请业务实现类",createDate="2013-09-14T00:00:00",author="程明卫")
@Service("exemptService")
public class ExemptServiceImpl extends AbsService<ExemptEntity, Long> implements  ExemptService {
	@Autowired
	private ExemptDaoInter exemptDao;
	@Resource(name="exeItemsService")
	private ExeItemsService exeItemsService;
	
	@Override
	public GenericDaoInter<ExemptEntity, Long> getDao() {
		return exemptDao;
	}
	
	@Override
	public DataTable getContractResultList(SHashMap<String, Object> params, int offset,int pageSize) throws ServiceException {
		try {
			return exemptDao.getContractResultList(params, offset, pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public DataTable getContractInfo(Long contractId)throws ServiceException{
		try {
			return exemptDao.getContractInfo(contractId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		try {
			UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
			ExemptEntity exemptEntity = (ExemptEntity)complexData.get("exemptEntity");
			String batchDatas = (String)complexData.get("batchDatas");
			Integer etype = exemptEntity.getEtype();
			if(null != etype && etype.intValue() == BussStateConstant.EXEMPT_ETYPE_1){
				exemptEntity.setExeItems(BussStateConstant.EXEMPT_EXEITEMS_1);
			}
			/*------> step 1 : 保存或更新豁免申请单 <------*/
			exemptDao.saveOrUpdateEntity(exemptEntity);
			
			/*------> step 2 : 删除历史豁免项列表 <------*/
			Long exemptId = exemptEntity.getId();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("exemptId", exemptId);
			exeItemsService.deleteEntitys(params);
			
			/*------> step 3 : 保存历史豁免项列表 <------*/
			JSONArray jsonArr = FastJsonUtil.convertStrToJSONArr(batchDatas);
			if(null == jsonArr || jsonArr.size() == 0) return;
			int count = jsonArr.size();
			List<ExeItemsEntity> extItemsList = new ArrayList<ExeItemsEntity>();
			for(int i=0; i<count; i++){
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				Long formId = jsonObj.getLong("formId");
				BigDecimal rat = jsonObj.getBigDecimal("rat");
				if(null == rat) rat = new BigDecimal("0");
				
				BigDecimal mat = jsonObj.getBigDecimal("mat");
				if(null == mat) mat = new BigDecimal("0");
				
				BigDecimal fat = jsonObj.getBigDecimal("fat");
				if(null == fat) fat = new BigDecimal("0");
				
				BigDecimal pat = jsonObj.getBigDecimal("pat");
				if(null == pat) pat = new BigDecimal("0");
				
				BigDecimal dat = jsonObj.getBigDecimal("dat");
				if(null == dat) dat = new BigDecimal("0");
				
				BigDecimal totalAmount = jsonObj.getBigDecimal("totalAmount");
				if(null == totalAmount) totalAmount = new BigDecimal("0");
				ExeItemsEntity itemsEntity = new ExeItemsEntity();
				itemsEntity.setExemptId(exemptId);
				itemsEntity.setFormId(formId);
				itemsEntity.setRat(rat);
				itemsEntity.setMat(mat);
				itemsEntity.setFat(fat);
				itemsEntity.setPat(pat);
				itemsEntity.setDat(dat);
				itemsEntity.setTotalAmount(totalAmount);
				BeanUtil.setCreateInfo(user, itemsEntity);
				extItemsList.add(itemsEntity);
			}
			exeItemsService.batchSaveEntitys(extItemsList);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.ExemptService#detail(java.lang.Long)
	 */
	@Override
	public DataTable detail(Long id) throws ServiceException {
		try {
			return exemptDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
}
