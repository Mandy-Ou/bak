package com.cmw.service.impl.fininter.external;

import org.apache.log4j.Logger;

import com.cmw.core.util.SpringContextUtil;
/**
 * 财务接口实例化工厂类
 * @author chengmingwei
 *
 */
public class FinSysFactory {
	protected static Logger log = Logger.getLogger("com.cmw.service.impl.fininter.external.FinSysFactory");
	
	/**
	 * 根据财务系统编号获取对应的财务接口实例
	 * @param finsysCode 财务系统编号
	 * @return 返回 FinSysService 对象
	 */
	public static FinSysService getInstance(String finsysCode){
		FinSysService service = null;
		if(FINSYS_K3_CODE.equals(finsysCode)){
			service = (FinSysService)SpringContextUtil.getBean("k3FinSysService");
		}else{
			log.error("编号为："+finsysCode+"的财务系统接口未实现，无法获取对应的 FinSysService 实例对象 !");
		}
		return service;
	}
	/**
	 * K3 财务系统编号
	 */
	public static final String FINSYS_K3_CODE = "FS0001";
}
