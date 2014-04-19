package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.crm.CategoryDaoInter;
import com.cmw.entity.crm.CategoryEntity;
import com.cmw.service.inter.crm.CategoryService;


/**
 * 客户分类  Service实现类
 * @author 程明卫
 * @date 2012-12-17T00:00:00
 */
@Description(remark="客户分类业务实现类",createDate="2012-12-17T00:00:00",author="程明卫")
@Service("categoryService")
public class CategoryServiceImpl extends AbsService<CategoryEntity, Long> implements  CategoryService {
	@Autowired
	private CategoryDaoInter categoryDao;
	@Override
	public GenericDaoInter<CategoryEntity, Long> getDao() {
		return categoryDao;
	}

}
