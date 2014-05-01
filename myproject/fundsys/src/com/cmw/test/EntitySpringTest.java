package com.cmw.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.service.inter.sys.UserService;
/**
 * 测试示例 继承 自  AbstractTestCase 类
 * @author cmw_1984122
 *
 */
public class EntitySpringTest extends AbstractTestCase {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testSave(){
//		System.out.println("dataSource="+sessionFactory.getClass().toString());
		System.out.println(userService.getClass());
//		UserEntity user = new UserEntity();
//		user.setUserName("chengmingwei");
//		user.setPassWord("222");
//		user.setCreateTime(new Date());
//		System.out.println(user==null);
//		try {
//			userService.saveEntity(user);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		logger.debug("YES");
	}
	
}
