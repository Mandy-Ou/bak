package com.hygj.dao;

import com.hygj.bean.EmailBean;





public interface SendedDAOINF {

	/****
	 * 发送邮件DAO
	 * @param users
	 * @return int
	 */
	public int sendEmail(EmailBean send,String sql);
	
	/****
	 * 添加收件箱
	 */
	public int addGet(EmailBean send,String sql);
}
