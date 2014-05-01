package com.cmw.test.finance;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.SHashMap;
import com.cmw.service.inter.finance.OverdueDeductService;
/**
 * 逾期收款测试
 * @author chengmingwei
 *
 */
public class OverdueDeductTest extends AbstractTestCase {
	@Resource(name="overdueDeductService")
	private OverdueDeductService overdueDeductService;
	/**
	 * 批量逾期罚息、滞纳金计算
	 * @throws ServiceException
	 */
	@Test
	public void testMinterestEamount() throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		overdueDeductService.calculateBatchLateDatas(map);
	}
	
	
}
