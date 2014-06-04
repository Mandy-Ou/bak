package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;


import com.hygj.bean.EmailBean;
import com.hygj.dao.SendedDAOINF;
import com.hygj.db.DbUtil;

public class SendedDAOImpl implements SendedDAOINF {

	/*****
	 * ·¢ËÍÓÊ¼þµÄDAO
	 * @param send
	 * @return int
	 */
	public int sendEmail(EmailBean send, String sql) {
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, send.getSender());
			pstm.setString(2, send.getRecipients());
			pstm.setString(3, send.getTitle());
			pstm.setString(4, send.getContent());
			pstm.setString(5, send.getThetime());
			pstm.setInt(6, send.getType());
			pstm.setInt(7, send.getStatus());
			
			flag = pstm.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally
		{
			try {
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return flag;
	
	}
	
	public int addGet(EmailBean send, String sql) {

		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, send.getSender());
			pstm.setString(2, send.getRecipients());
			pstm.setString(3, send.getTitle());
			pstm.setString(4, send.getContent());
			pstm.setString(5, send.getThetime());
			pstm.setInt(6, send.getType());
			pstm.setInt(7, send.getStatus());
			
			flag = pstm.executeUpdate();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally
		{
			try {
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return flag;
	
	
	}

}
