package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;



public interface SelectGetsDAOINF {

	/*****
	 * ��ѯ�ѽ����ʼ�
	 * @param username
	 * @param sql
	 * @return
	 */
	public List<EmailBean> getsEmail(String sql,String username);
	
	/*****
	 * ��ѯ�ѽ����ʼ�������
	 * @param username
	 * @param sql
	 * @return
	 */
	public int getEmailCount(String username,String sql);
	
	/*****
	 * ��ѯ�ѽ����ʼ�������(δ��)
	 * @param username
	 * @param sql
	 * @return
	 */
	public int getsEmailCount(String username,String sql);
}
