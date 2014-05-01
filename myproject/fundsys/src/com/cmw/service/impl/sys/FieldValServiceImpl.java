package com.cmw.service.impl.sys;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.FieldValDaoInter;
import com.cmw.entity.sys.FieldValEntity;
import com.cmw.service.inter.sys.FieldValService;
import com.mongodb.util.Hash;


/**
 * 自定义字段值  Service实现类
 * @author 程明卫
 * @date 2013-03-21T00:00:00
 */
@Description(remark="自定义字段值业务实现类",createDate="2013-03-21T00:00:00",author="程明卫")
@Service("fieldValService")
public class FieldValServiceImpl extends AbsService<FieldValEntity, Long> implements  FieldValService {
	@Autowired
	private FieldValDaoInter fieldValDao;
	@Override
	public GenericDaoInter<FieldValEntity, Long> getDao() {
		return fieldValDao;
	}
	
	@Override
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		Long formdiyId = (Long)complexData.get("formdiyId");
		Long formId = (Long)complexData.get("formId");
		
		/*--------- step 1 : 删除数据 --------*/
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("formdiyId", formdiyId);
		params.put("formId", formId);
		this.deleteEntitys(params);
		
		/*--------- step 2 : 插入新数据 --------*/
		String fieldVals = (String)complexData.get("fieldVals");
		JSONObject dataObj = FastJsonUtil.convertStrToJSONObj(fieldVals);
		Set<String> keys = dataObj.keySet();
		List<FieldValEntity> list = new ArrayList<FieldValEntity>();
		for(String key : keys){
			String val = dataObj.getString(key);
			if(!StringHandler.isValidStr(val)) continue;
			FieldValEntity entity = new FieldValEntity();
			entity.setFormdiyId(formdiyId);
			entity.setFormId(formId);
			entity.setFieldName(key);
			entity.setVal(val);
			list.add(entity);
		}
		this.batchSaveEntitys(list);
	}
}
