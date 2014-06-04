package com.hygj.service;

import java.util.List;


import com.hygj.bean.EmailBean;
import com.hygj.bean.UsersBean;

public interface InfoServiceINF {

	/*****
	 * ��֤�û�
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users);
	
	/******
	 * �����ʼ�
	 * @param users
	 * @return
	 */
	public int sendEmail(EmailBean send);
	
	/******
	 * ��ѯ�ѽ����ʼ�
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectGetsEmail(String username);
	
	/******
	 * ��ѯ�ѷ����ʼ�
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectsendedEmail(String username);
	
	/******
	 * ��ѯ��ɾ���ʼ�
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectdeleteEmail(String username);
	
	/******
	 * ��ѯ�Է����ʼ���ϸ
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectMoreEmail(String id);
	
	/******
	 * ������ɾ���ʼ�״̬
	 * @param users
	 * @return
	 */
	public int updateDeleteEmail(String id);
	
	/******
	 * ���������ʼ�״̬
	 * @param users
	 * @return
	 */
	public int updateGetEmail(String id);
	
	/******
	 * ��ѯ�ѷ����ʼ�����
	 * @param users
	 * @return
	 */
	public int selectSendedCount(String username);
	
	/******
	 * ��ѯ��ɾ���ʼ�����
	 * @param users
	 * @return
	 */
	public int selectDeleteCount(String username);
	
	/******
	 * ��ѯ��ɾ���ʼ�δ����
	 * @param users
	 * @return
	 */
	public int selectDeleteUCount(String username);
	
	/******
	 * ��ѯ�ѽ����ʼ�����
	 * @param users
	 * @return
	 */
	public int selectGetCount(String username);
	
	/******
	 * ��ѯ�ѽ����ʼ���δ����
	 * @param users
	 * @return
	 */
	public int selectGetSCount(String username);
	
	/******
	 * ��ѯ�ݸ��ʼ�
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectCaoEmail(String username);
	
	/******
	 * ��ѯ�ݸ��ʼ�����
	 * @param users
	 * @return
	 */
	public int selectCaoCount(String username);
	
	/****
	 * ����ռ���
	 */
	public int addGet(EmailBean email);
	
	/****
	 * ɾ���ռ����ʼ�
	 */
	public void deleteShou(String username,String fg);
	
	/***
	 * �ƶ���������
	 */
	public void moveBox(String id);
	
	/***
	 * ɾ���ʼ�
	 */
	public void deleteEmail(String id);
}
