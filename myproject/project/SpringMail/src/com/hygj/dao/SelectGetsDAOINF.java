package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;



public interface SelectGetsDAOINF {

	/*****
	 * 查询已接受邮件
	 * @param username
	 * @param sql
	 * @return
	 */
	public List<EmailBean> getsEmail(String sql,String username);
	
	/*****
	 * 查询已接受邮件的数量
	 * @param username
	 * @param sql
	 * @return
	 */
	public int getEmailCount(String username,String sql);
	
	/*****
	 * 查询已接受邮件的数量(未读)
	 * @param username
	 * @param sql
	 * @return
	 */
	public int getsEmailCount(String username,String sql);
}
