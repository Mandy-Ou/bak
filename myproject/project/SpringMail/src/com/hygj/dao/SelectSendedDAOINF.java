package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;


public interface SelectSendedDAOINF {

	/*****
	 * ��ѯ�ѷ����ʼ�
	 * @param send
	 * @param sql
	 * @return List
	 */
	public List<EmailBean> selectSendedEmail(String username,String sql);
	
}
