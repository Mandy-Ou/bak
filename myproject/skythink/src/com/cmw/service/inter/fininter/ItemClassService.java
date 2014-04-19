package com.cmw.service.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.entity.fininter.ItemClassEntity;


/**
 * 核算项类别  Service接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项类别业务接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface ItemClassService extends IService<ItemClassEntity, Long> {
	/**
	 * 根据实体对象获取其所有对财务的引用主键ID --> refId
	 * @param objectName 
	 * @return 
	 * @throws ServiceException
	 */
	String getRefIds(String objectName) throws ServiceException;
}
