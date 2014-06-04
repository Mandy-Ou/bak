package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;



public interface DeleteDAOINF {


	/*****
	 * ��ѯ��ɾ���ʼ�
	 * @param send
	 * @param sql
	 * @return List
	 */
	public List<EmailBean> selectDeleteEmail(String username,String sql);
	
	/****
	 * �ʼ��ƶ���������
	 */
	public void moveBox(String id,String sql);
	
	/***
	 * ɾ���ʼ�
	 */
	public void deleteEmail(String id,String s2ql);
}
