package com.cmw.core.base.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试基类，继承此类可不用再引入测试注解
 * @author cmw_1984122
 *
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)  
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
public class AbstractTestCase {
	  protected static final Log logger = LogFactory.getLog(SpringJUnit4ClassRunner.class);  
}
