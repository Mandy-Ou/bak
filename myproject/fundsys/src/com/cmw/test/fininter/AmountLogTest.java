package com.cmw.test.fininter;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.generic.VoucherTransform;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.UserService;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 实收金额日志测试类
 * @author chengmingwei
 *
 */
public class AmountLogTest  extends AbstractTestCase {
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Resource(name="voucherTransform")
	VoucherTransform voucherTransform;
	@Test
	public void testSaveByBussTag(){
		try{
			UserEntity user = userService.getEntity(45L);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put(SysConstant.USER_KEY, user);
			params.put("sysId", 3L);
			params.put("ids", "1");
			params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_0);
			params.put("vtempCode", "V00005");
			Map<AmountLogEntity,DataTable> dataMap = amountLogService.saveByBussTag(params);
			if(null == dataMap || dataMap.size() == 0) return;
			for(int i=0,count=dataMap.size(); i<count; i++){
				Set<AmountLogEntity> keys = dataMap.keySet();
				for(AmountLogEntity key : keys){
					String[] fields = key.getFields();
					Object[] data = key.getDatas();
					System.out.println("---------------- AmountLogEntity Data --------------");
					System.out.println(Arrays.toString(fields));
					System.out.println(Arrays.toString(data));
					System.out.println("*********************************************");
					System.out.println("---------------- Record Data --------------");
					DataTable dt = dataMap.get(key);
					System.out.println(dt.toString());
				}
			}
			voucherTransform.convert(dataMap, params);
//			logger.info(Arrays.toString(datas)+"\n");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
