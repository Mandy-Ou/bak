package com.hygj.dao;

public interface UpdateEmailDAOINF {

	/****
	 * 更新已删除邮件状态
	 * @param users
	 * @return int
	 */
	public int updateDeleteEmail(String id,String sql);
	
	/****
	 * 更新已发送邮件状态
	 * @param users
	 * @return int
	 */
	public int updateSendedEmail(String id,String sql);
	
	/****
	 * 更新收件箱状态
	 * @param users
	 * @return int
	 */
	public int updateGetEmail(String id,String sql);

	
}
