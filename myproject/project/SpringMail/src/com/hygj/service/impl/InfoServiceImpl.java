package com.hygj.service.impl;

import java.util.List;


import com.hygj.bean.EmailBean;
import com.hygj.bean.UsersBean;
import com.hygj.dao.DeleteDAOINF;
import com.hygj.dao.DeleteEmail;
import com.hygj.dao.SelectCaoDAOINF;
import com.hygj.dao.SelectGetsDAOINF;
import com.hygj.dao.SelectMoreDAOINF;
import com.hygj.dao.SelectSendedDAOINF;
import com.hygj.dao.SendedDAOINF;
import com.hygj.dao.UpdateEmailDAOINF;
import com.hygj.dao.UsersDAOINF;
import com.hygj.dao.impl.DeleteDAOImpl;
import com.hygj.dao.impl.DeleteEmailDAOImpl;
import com.hygj.dao.impl.SelectCaoDAOImpl;
import com.hygj.dao.impl.SelectGetsDAOImpl;
import com.hygj.dao.impl.SelectMoreDAOImpl;
import com.hygj.dao.impl.SelectSendedDAOImpl;
import com.hygj.dao.impl.SendedDAOImpl;
import com.hygj.dao.impl.UpdateEmailDAOImpl;
import com.hygj.dao.impl.UsersDAOImpl;
import com.hygj.service.InfoServiceINF;

public class InfoServiceImpl implements InfoServiceINF {
	
	/*****
	 * 验证用户的实现方法
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users) {
		// TODO Auto-generated method stub
		String sql = "select * from users where username = ? and password = ? ";
		
		UsersDAOINF usersdao = new UsersDAOImpl();
		
		UsersBean user = usersdao.checkLogin(users, sql);
		
		
		return user;
	}
	
	/*****
	 * 发送邮件的实现方法
	 * @param send
	 * @return int
	 */
	public int sendEmail(EmailBean send) {
		
		String sql = "insert into usersemail(sender,recipients,title,content,thetime,type,status) values(?,?,?,?,?,?,?)";
		
		SendedDAOINF senddao= new SendedDAOImpl();
		
		int flag = senddao.sendEmail(send, sql);
		
		return flag;
	}
	
	/*****
	 * 查询已发送发送邮件的实现方法
	 * @param username 用户帐户
	 * @return List
	 */
	public List<EmailBean> selectsendedEmail(String username) {
		
		String sql = "select * from usersemail where sender = ? and type = ?";
		
		SelectSendedDAOINF selectsended = new SelectSendedDAOImpl();
		
		List<EmailBean> list = selectsended.selectSendedEmail(username, sql);
		
		return list;
	}
	
	/*****
	 * 查询已删除邮件的实现方法
	 * @param username 用户帐户
	 * @return List
	 */
	
	public List<EmailBean> selectdeleteEmail(String username) {
		
		String sql = "select * from usersemail where sender = ? and type = ?";
		
		DeleteDAOINF df = new DeleteDAOImpl();
		
		List<EmailBean> list = df.selectDeleteEmail(username, sql);
		
		return list;
	}
	
	/*****
	 * 查询已发送邮件的详细
	 * @param id 邮件ID
	 * @return List
	 */
	public List<EmailBean> selectMoreEmail(String id) {
		
		String sql = "select * from usersemail where id = ?";
		
		SelectMoreDAOINF sm = new SelectMoreDAOImpl();
		
		List<EmailBean> list = sm.selectMoreEmail(id, sql);
		
		return list;
	}
	
	/*****
	 * 更新已删除邮件状态
	 * @param id 邮件ID
	 * @return int
	 */
	public int updateDeleteEmail(String id) {
		
		String sql = "update usersemail set status =  ? where id = ? ";
		
		UpdateEmailDAOINF ue = new UpdateEmailDAOImpl();
		
		int flag = ue.updateDeleteEmail(id, sql);
		
		return flag;
	}
	
	/*****
	 * 更新已收邮件状态
	 * @param id 邮件ID
	 * @return int
	 */
	public int updateGetEmail(String id) {
		
		String sql = "update usersemail set status =  ? where id = ? ";//
		
		UpdateEmailDAOINF ue = new UpdateEmailDAOImpl();
		
		int flag = ue.updateGetEmail(id, sql);
		
		return flag;
	}
	
	/*****
	 * 查询已发送邮件数量
	 * @param id 邮件ID
	 * @return int
	 */
	public int selectSendedCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ? and type = ?";
		
		SelectMoreDAOINF sm = new SelectMoreDAOImpl();
		
		int it = sm.selectSendedCount(sql,username);
		
		return it;
	}
	
	/*****
	 * 查询已删除邮件数量
	 * @param id 邮件ID
	 * @return int
	 */
	public int selectDeleteCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ? and type = ?";
		
		SelectMoreDAOINF sm = new SelectMoreDAOImpl();
	
		int it = sm.selectDeleteCount(sql,username);
		
		return it;
	}
	
	/*****
	 * 查询已删除邮件(未读)
	 * @param id 邮件ID
	 * @return int
	 */
	public int selectDeleteUCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ? and status = ? and type = ?";
		
		SelectMoreDAOINF sm = new SelectMoreDAOImpl();
	
		int is = sm.selectDeleteUCount(sql,username);
		
		return is;
	}
	/*****
	 * 查询已接受邮件
	 * @param id 邮件ID
	 * @return int
	 */
	public List<EmailBean> selectGetsEmail(String username) {
		
		String sql = "select * from usersemail where recipients = ? and type = ?";
		
		SelectGetsDAOINF sg = new SelectGetsDAOImpl();
		
		List<EmailBean> list = sg.getsEmail(sql, username);
		
		return list;
	}
	
	/*****
	 * 查询已接受邮件数量
	 * @param id 邮件ID
	 * @return int
	 */
	public int selectGetCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ?  and type = ?";
		
		SelectGetsDAOINF sm = new SelectGetsDAOImpl();
	
		int it = sm.getEmailCount(username, sql);
		
		return it;
	}

	/*****
	 * 查询已接受邮件(未读)
	 * @param id 邮件ID
	 * @return int
	 */

	public int selectGetSCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ? and status = ? and type = ?";
		
		SelectGetsDAOINF sm = new SelectGetsDAOImpl();
		
		int it = sm.getsEmailCount(username, sql);
		
		return it;
	}
	

	/*****
	 * 查询草稿邮件
	 * @param id 邮件ID
	 * @return int
	 */
	public List<EmailBean> selectCaoEmail(String username) {
		
		String sql = "select * from usersemail where sender = ? and type = ?";
		
		SelectCaoDAOINF sc = new SelectCaoDAOImpl();
		
		List<EmailBean> list = sc.selectCaoEmail(sql, username);
		
		return list;
	}
	
	public int selectCaoCount(String username) {
		
		String sql = "select count(id) as num from usersemail where sender = ?  and type = ?";
		
		SelectCaoDAOINF sc = new SelectCaoDAOImpl();
	
		int it = sc.selectCaoCount(sql, username);
		
		return it;
	}
	public int addGet(EmailBean send) {
		
		String sql = "insert into usersemail(sender,recipients,title,content,thetime,type,status) values(?,?,?,?,?,?,?)";
		
		SendedDAOINF senddao= new SendedDAOImpl();
		
		int flag = senddao.addGet(send, sql);
		
		return flag;
	}
	public void deleteShou(String username,String fg) {
		
		String sql = "delete from usersemail where recipients = ? and type = ?";
		
		DeleteEmail de = new DeleteEmailDAOImpl();
		
		de.deleteEmail(sql, username, fg);
	
	}
	public void moveBox(String id) {
		
		String sql = "update usersemail set type = ? where id = ?";
		
		DeleteDAOINF de = new DeleteDAOImpl();
		
		de.moveBox(id, sql);
		
	}
	public void deleteEmail(String id) {
		
		String sql = "delete from usersemail where id = ?";
		
		DeleteDAOINF de = new DeleteDAOImpl();
		
		de.deleteEmail(id, sql);
		
	}
}
