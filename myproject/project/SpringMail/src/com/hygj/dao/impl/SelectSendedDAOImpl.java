package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.hygj.bean.EmailBean;
import com.hygj.dao.SelectSendedDAOINF;
import com.hygj.db.DbUtil;

public class SelectSendedDAOImpl implements SelectSendedDAOINF {

	/*****
	 * 查询已发送发送邮件的DAO
	 * @param send sql
	 * @return List
	 */
	public List<EmailBean> selectSendedEmail(String username, String sql) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<EmailBean> list = new ArrayList<EmailBean>();
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@sohu.com");
			pstm.setString(2, "3");
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				EmailBean sd = new EmailBean();
				
				sd.setId(rs.getInt("id"));
				sd.setRecipients(rs.getString("recipients"));
				sd.setTitle(rs.getString("title"));
				sd.setThetime(rs.getString("thetime"));
				
				list.add(sd);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally
		{
			try {
				rs.close();
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return list;
	}

	
}
