package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hygj.bean.EmailBean;
import com.hygj.dao.SelectMoreDAOINF;
import com.hygj.db.DbUtil;

public class SelectMoreDAOImpl implements SelectMoreDAOINF {

	public List<EmailBean> selectMoreEmail(String id, String sql) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<EmailBean> list = new ArrayList<EmailBean>();
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, id);
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				EmailBean sd = new EmailBean();
				
				sd.setSender(rs.getString("sender"));
				sd.setRecipients(rs.getString("recipients"));
				sd.setTitle(rs.getString("title"));
				sd.setContent(rs.getString("content"));
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
	
	public int selectSendedCount(String sql,String username) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int it = 0;

		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@sohu.com");
			pstm.setString(2, "3");
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				
				it = rs.getInt("num");
				
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
	
		return it;
	}
	
	public int selectDeleteCount(String sql,String username) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int it = 0;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@sohu.com");
			pstm.setString(2, "4");
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				
				it = rs.getInt("num");
				
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
	
		return it;
	
	}

	public int selectDeleteUCount(String sql,String username) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int it = 0;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@sohu.com");
			pstm.setString(2, "0");
			pstm.setString(3, "4");
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				
				it = rs.getInt("num");
				
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
	
		return it;
	
	
	}



}
