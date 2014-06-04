package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;



public interface SelectMoreDAOINF {

	/*****
	 * 查询已发邮件详细
	 * @param send
	 * @param sql
	 * @return List
	 */
	public List<EmailBean> selectMoreEmail(String id,String sql);
	
	/*****
	 * 查询已发邮件(数量)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectSendedCount(String username,String sql);
	
	/*****
	 * 查询已删除邮件(数量)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectDeleteCount(String sql ,String username);
	
	/*****
	 * 查询已删除邮件(未读)
	 * @param send
	 * @param sql
	 * @return List
	 */
	public int selectDeleteUCount(String sql,String username);
}
