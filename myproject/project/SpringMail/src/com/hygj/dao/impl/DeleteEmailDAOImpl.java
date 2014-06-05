package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hygj.bean.EmailBean;
import com.hygj.dao.DeleteEmail;
import com.hygj.db.DbUtil;

public class DeleteEmailDAOImpl implements DeleteEmail {

	public void deleteEmail(String sql,String username,String fg) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			
			conn = DbUtil.getConn();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username+"@163.com");
			pstm.setString(2, fg);

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
