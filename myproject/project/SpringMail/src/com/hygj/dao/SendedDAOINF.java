package com.hygj.dao;

import com.hygj.bean.EmailBean;





public interface SendedDAOINF {

	/****
	 * �����ʼ�DAO
	 * @param users
	 * @return int
	 */
	public int sendEmail(EmailBean send,String sql);
	
	/****
	 * ����ռ���
	 */
	public int addGet(EmailBean send,String sql);
}
