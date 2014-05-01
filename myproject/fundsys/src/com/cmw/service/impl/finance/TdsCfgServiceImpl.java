package com.cmw.service.impl.finance;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.CmnMappingEntity;
import com.cmw.entity.finance.TdsCfgEntity;
import com.cmw.dao.inter.finance.CmnMappingDaoInter;
import com.cmw.dao.inter.finance.TdsCfgDaoInter;
import com.cmw.service.inter.finance.CmnMappingService;
import com.cmw.service.inter.finance.TdsCfgService;


/**
 * 模板数据源配置表  Service实现类
 * @author 赵世龙
 * @date 2013-11-21T00:00:00
 */
@Description(remark="模板数据源配置表业务实现类",createDate="2013-11-21T00:00:00",author="赵世龙")
@Service("tdsCfgService")
public class TdsCfgServiceImpl extends AbsService<TdsCfgEntity, Long> implements  TdsCfgService {
	@Autowired
	private TdsCfgDaoInter tdsCfgDao;
	
	@Resource(name="cmnMappingDao")
	private CmnMappingDaoInter cmnMappingDao;
	@Override
	public GenericDaoInter<TdsCfgEntity, Long> getDao() {
		return tdsCfgDao;
	}
	@Override
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		try {
			TdsCfgEntity entity=(TdsCfgEntity) complexData.get("entity");
			tdsCfgDao.saveOrUpdateEntity(entity);
			String batchDatas = (String)complexData.get("batchDatas");
			List<CmnMappingEntity> dataList = FastJsonUtil.convertJsonToList(batchDatas,CmnMappingEntity.class);
//			complexData.remove("batchDatas");
			for (CmnMappingEntity cmnMappingEntity : dataList) {
				cmnMappingDao.saveOrUpdateEntity(cmnMappingEntity);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

}
