package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.hygj.bean.EmailBean;
import com.hygj.dao.DeleteDAOINF;
import com.hygj.db.DbUtil;

public class DeleteDAOImpl implements DeleteDAOINF {

	public List<EmailBean> selectDeleteEmail(String username, String sql) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs= null;
		List<EmailBean> list = new ArrayList<EmailBean>();
		
		try {
			
			System.out.println(sql);
			conn = DbUtil.getConn();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@163.com");
			pstm.setString(2, "4");
			
			rs = pstm.executeQuery();
			
			while(rs.next())
			{
				EmailBean db = new EmailBean();
				
				db.setId(rs.getShort("id"));
				db.setRecipients(rs.getString("recipients"));
				db.setTitle(rs.getString("title"));
				db.setThetime(rs.getString("thetime"));
				
				list.add(db);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally
		{
			try {
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return list;
	}

	public void moveBox(String id,String sql) {
		

		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			

			conn = DbUtil.getConn();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, "4");
			pstm.setString(2, id);
			
			pstm.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally
		{
			try {
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	
	}
	public void deleteEmail(String id, String sql) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			

			conn = DbUtil.getConn();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, id);
		
			pstm.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally
		{
			try {
				pstm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		
	}
}
