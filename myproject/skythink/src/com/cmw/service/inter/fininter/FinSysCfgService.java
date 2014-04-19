package com.cmw.service.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.entity.fininter.FinSysCfgEntity;


/**
 * 财务系统配置  Service接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务系统配置业务接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface FinSysCfgService extends IService<FinSysCfgEntity, Long> {
	/**
	 * 
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	FinSysCfgEntity getCfgByCode(String code) throws ServiceException;
}
