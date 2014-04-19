package com.cmw.core.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Hibernate 工具类
 * @author cmw_1984122
 *
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	
	static{
		try{
	        //sessionFactory = new Configuration().configure().buildSessionFactory();
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}catch (Exception ex) {
		     System.err.println("Initial SessionFactory creation failed." + ex);
	          throw new ExceptionInInitializerError(ex);
		}
	}
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static void main(String[] args){
		SessionFactory factory = getSessionFactory();
		Session session = factory.openSession();
		System.out.println(null==factory);
	}
}
