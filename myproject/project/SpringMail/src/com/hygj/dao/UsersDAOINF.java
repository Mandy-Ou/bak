package com.hygj.dao;

import com.hygj.bean.UsersBean;

public interface UsersDAOINF {

	/****
	 * ��֤�û�DAO
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users,String sql);
}
