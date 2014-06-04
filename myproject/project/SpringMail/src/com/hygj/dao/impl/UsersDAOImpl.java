package com.hygj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hygj.bean.UsersBean;
import com.hygj.dao.UsersDAOINF;
import com.hygj.db.DbUtil;

public class UsersDAOImpl implements UsersDAOINF {

	/*****
	 * 验证用户DAO
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users, String sql) {

		UsersBean sd = new UsersBean();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {		
			 conn = DbUtil.getConn();
			 
			 pstm = conn.prepareStatement(sql);
			 
			 pstm.setString(1, users.getUsername());
			 pstm.setString(2, users.getPassword());
			 
			 rs = pstm.executeQuery();
			 
			 if(rs.next())
			 {
				 sd.setId(rs.getInt("id"));
				 sd.setUsername(rs.getString("username"));
				 sd.setPassword(rs.getString("password"));
				 
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
				e.printStackTrace();
			}
		}
	
		return sd;
	}

}
