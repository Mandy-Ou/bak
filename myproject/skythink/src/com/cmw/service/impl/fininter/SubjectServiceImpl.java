package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.dao.inter.fininter.SubjectDaoInter;
import com.cmw.service.inter.fininter.SubjectService;


/**
 * 科目  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="科目业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("subjectService")
public class SubjectServiceImpl extends AbsService<SubjectEntity, Long> implements  SubjectService {
	@Autowired
	private SubjectDaoInter subjectDao;
	@Override
	public GenericDaoInter<SubjectEntity, Long> getDao() {
		return subjectDao;
	}

}
