package com.hygj.dao;

public interface UpdateEmailDAOINF {

	/****
	 * ������ɾ���ʼ�״̬
	 * @param users
	 * @return int
	 */
	public int updateDeleteEmail(String id,String sql);
	
	/****
	 * �����ѷ����ʼ�״̬
	 * @param users
	 * @return int
	 */
	public int updateSendedEmail(String id,String sql);
	
	/****
	 * �����ռ���״̬
	 * @param users
	 * @return int
	 */
	public int updateGetEmail(String id,String sql);

	
}
