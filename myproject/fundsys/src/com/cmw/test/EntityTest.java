package com.cmw.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.cmw.core.util.HibernateUtil;
import com.cmw.entity.sys.UserEntity;

/**
 * ʵ�������
 * @author cmw_1984122
 *
 */
public class EntityTest {
	private Session session;
	@Before
	public void initSession(){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	@Test
	public void testSave(){
		UserEntity user = new UserEntity();
		user.setUserName("TEST1");
		user.setPassWord("111");
		Transaction tx = session.getTransaction();
		tx.begin();
		session.save(user);
		tx.commit();
	}
	
}
