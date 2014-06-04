package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;



public interface SelectMoreDAOINF {

	/*****
	 * ��ѯ�ѷ��ʼ���ϸ
	 * @param send
	 * @param sql
	 * @return List
	 */
	public List<EmailBean> selectMoreEmail(String id,String sql);
	
	/*****
	 * ��ѯ�ѷ��ʼ�(����)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectSendedCount(String username,String sql);
	
	/*****
	 * ��ѯ��ɾ���ʼ�(����)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectDeleteCount(String sql ,String username);
	
	/*****
	 * ��ѯ��ɾ���ʼ�(δ��)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectDeleteUCount(String sql,String username);
}
