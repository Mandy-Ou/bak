package com.cmw.service.impl.finance.paytype;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;

import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.PayTypeEntity;
import com.cmw.service.inter.finance.PayTypeService;

/**
 * 还款计划工厂对象
 * @author chengmingwei
 *
 */
public class PayTypeCalculateFactory {
	public static final String CURRENT_PACKAGE_NAME = "com.cmw.service.impl.finance.paytype";
	//还款方式业务类
	private static PayTypeService payTypeService = null;
	private static Map<String,PayTypeEntity> payTypeMap = new LinkedHashMap<String,PayTypeEntity>();
	private static Map<String,PayTypeCalculateAbs> instanceMap = new LinkedHashMap<String,PayTypeCalculateAbs>();
	private boolean changed = false;
	private PayTypeCalculateFactory() {
		
	}

	/**
	 * 创建或获取还款方式对象
	 * @param type	还款方式编号
	 * @return	返回还款方式计算对象
	 * @throws com.cmw.core.base.exception.ServiceException 
	 */
	public PayTypeCalculateAbs creator(String bussCode) throws com.cmw.core.base.exception.ServiceException{
		if(!StringHandler.isValidStr(bussCode)) throw new ServiceException("调用 creator 方法时，bussCode 参数不能为空!");
		PayTypeEntity payTypeEntity = getPayType(bussCode);
		if(null == payTypeEntity) throw new ServiceException("PayTypeEntity 实体对象不能为空!");
		PayTypeCalculateAbs instance = null;
		if(instanceMap.containsKey(bussCode)){ /*从缓存中取*/
			instance = instanceMap.get(bussCode);
		}else{
			String name = payTypeEntity.getName();
			String className = payTypeEntity.getInter();
			if(!StringHandler.isValidStr(className)) throw new ServiceException("找不到 \""+name+"\" 中的 inter 为空,请检查在 fc_PayType 表中是否有配置 inter(算法接口)值!");
			if(className.indexOf(".") == -1){
				className = CURRENT_PACKAGE_NAME+"."+className;
			}
			Object objService = BeanUtil.getInstance(className);
			if(null == objService) throw new ServiceException("找不到 \""+name+"\" 对应的接口实现,请检查在 fc_PayType 表中是否有配置 inter(算法接口)值!");
			instance = (PayTypeCalculateAbs)objService;
			instanceMap.put(bussCode, instance);
		}
		instance.setPayTypeEntity(payTypeEntity);
		return instance;
	}

	private PayTypeEntity getPayType(String bussCode) throws com.cmw.core.base.exception.ServiceException {
		PayTypeEntity entity = null;
		if(!payTypeMap.containsKey(bussCode)){
			entity = getPayTypeEntity(bussCode);
			payTypeMap.put(bussCode, entity);
		}
		if(changed){
			entity = getPayTypeEntity(bussCode);
			payTypeMap.put(bussCode, entity);
			changed = false;
		}
		entity = payTypeMap.get(bussCode);
		return entity;
	}
	
	private PayTypeEntity getPayTypeEntity(String bussCode) throws com.cmw.core.base.exception.ServiceException{
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("code", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + bussCode);
		if(null == payTypeService) payTypeService = (PayTypeService)SpringContextUtil.getBean("payTypeService");
		PayTypeEntity entity = payTypeService.getEntity(params);
		return entity;
	}
	
	
	public void setPayTypeService(PayTypeService payTypeService) {
		PayTypeCalculateFactory.payTypeService = payTypeService;
	}

	/**
	 * 获取工厂实例
	 * @return PlanFactory 对象
	 */
	public static PayTypeCalculateFactory getInstance(){
		return LazyHolder.INSTANCE;
	}
	
	private static final class LazyHolder{
		private static final PayTypeCalculateFactory INSTANCE = new PayTypeCalculateFactory();
	}
	
}
