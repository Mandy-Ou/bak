package com.cmw.service.impl.finance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.kit.nosql.mongodb.MongoDbManager;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.AppraiseEntity;
import com.cmw.dao.inter.finance.AppraiseDaoInter;
import com.cmw.service.inter.finance.AppraiseService;


/**
 * 审贷评审  Service实现类
 * @author 李听
 * @date 2014-01-04T00:00:00
 */
@Description(remark="审贷评审业务实现类",createDate="2014-01-04T00:00:00",author="李听")
@Service("appraiseService")
public class AppraiseServiceImpl extends AbsService<AppraiseEntity, Long> implements  AppraiseService {
	@Autowired
	private AppraiseDaoInter appraiseDao;
	@Override
	public GenericDaoInter<AppraiseEntity, Long> getDao() {
		return appraiseDao;
	}


}
