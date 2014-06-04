package com.hygj.service;

import java.util.List;


import com.hygj.bean.EmailBean;
import com.hygj.bean.UsersBean;

public interface InfoServiceINF {

	/*****
	 * 验证用户
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users);
	
	/******
	 * 发送邮件
	 * @param users
	 * @return
	 */
	public int sendEmail(EmailBean send);
	
	/******
	 * 查询已接受邮件
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectGetsEmail(String username);
	
	/******
	 * 查询已发送邮件
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectsendedEmail(String username);
	
	/******
	 * 查询已删除邮件
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectdeleteEmail(String username);
	
	/******
	 * 查询以发送邮件详细
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectMoreEmail(String id);
	
	/******
	 * 更新已删除邮件状态
	 * @param users
	 * @return
	 */
	public int updateDeleteEmail(String id);
	
	/******
	 * 更新已收邮件状态
	 * @param users
	 * @return
	 */
	public int updateGetEmail(String id);
	
	/******
	 * 查询已发送邮件数量
	 * @param users
	 * @return
	 */
	public int selectSendedCount(String username);
	
	/******
	 * 查询已删除邮件数量
	 * @param users
	 * @return
	 */
	public int selectDeleteCount(String username);
	
	/******
	 * 查询已删除邮件未读的
	 * @param users
	 * @return
	 */
	public int selectDeleteUCount(String username);
	
	/******
	 * 查询已接受邮件数量
	 * @param users
	 * @return
	 */
	public int selectGetCount(String username);
	
	/******
	 * 查询已接受邮件（未读）
	 * @param users
	 * @return
	 */
	public int selectGetSCount(String username);
	
	/******
	 * 查询草稿邮件
	 * @param users
	 * @return
	 */
	public List<EmailBean> selectCaoEmail(String username);
	
	/******
	 * 查询草稿邮件数量
	 * @param users
	 * @return
	 */
	public int selectCaoCount(String username);
	
	/****
	 * 添加收件箱
	 */
	public int addGet(EmailBean email);
	
	/****
	 * 删除收件箱邮件
	 */
	public void deleteShou(String username,String fg);
	
	/***
	 * 移动到垃圾箱
	 */
	public void moveBox(String id);
	
	/***
	 * 删除邮件
	 */
	public void deleteEmail(String id);
}
