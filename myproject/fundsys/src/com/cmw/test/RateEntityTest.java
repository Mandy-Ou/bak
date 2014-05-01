package com.cmw.test;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.entity.fininter.AmountLogEntity;

/**
 *Title: RateEntityTest.java
 *@作者： 彭登浩
 *@ 创建时间：2012-12-6下午9:41:26
 *@ 公司：	同心日信科技有限公司
 */
public class RateEntityTest extends AbstractTestCase {
	@Autowired
	private static Session rateService;
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void test() {
//		System.out.println(rateService.getClass());
//		RateEntity rate = new RateEntity();
//		rate.setCode("11");
//		rate.setBdate(new Date());
//		Transaction tx = rateService.getTransaction();
//		tx.begin();
//		rateService.save(rate);
//		tx.commit();
		AmountLogEntity alog1 = new AmountLogEntity();
		AmountLogEntity alog2 = new AmountLogEntity();
		System.out.println("alog1="+alog1.hashCode()+",alog2="+alog2.hashCode());
		alog1.setId(1L);
		alog2.setId(2L);
		System.out.println("alog1="+alog1.hashCode()+",alog2="+alog2.hashCode());
	}

}
