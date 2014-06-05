package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hygj.bean.EmailBean;
import com.hygj.dao.SelectCaoDAOINF;
import com.hygj.db.DbUtil;

public class SelectCaoDAOImpl implements SelectCaoDAOINF {

	public List<EmailBean> selectCaoEmail(String sql,String username) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<EmailBean> list = new ArrayList<EmailBean>();
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@163.com");
			pstm.setString(2, "2");
			
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
	
	public int selectCaoCount(String sql, String username) {


		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int it = 0;
		
		try {
			conn = DbUtil.getConn();

			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@163.com");
			pstm.setString(2, "2");
			
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
