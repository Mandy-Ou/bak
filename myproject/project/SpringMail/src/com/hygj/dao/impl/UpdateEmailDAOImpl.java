package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hygj.dao.UpdateEmailDAOINF;
import com.hygj.db.DbUtil;

public class UpdateEmailDAOImpl implements UpdateEmailDAOINF {

	public int updateDeleteEmail(String id,String sql) {
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, "1");
			pstm.setString(2, id);
			
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
	
	public int updateSendedEmail(String id, String sql) {
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, "1");
			pstm.setString(2, id);
			
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
	
	public int updateGetEmail(String id, String sql) {

		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, "1");
			pstm.setString(2, id);
			
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
