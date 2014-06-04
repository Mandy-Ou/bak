package com.hygj.dao;

import com.hygj.bean.UsersBean;

public interface UsersDAOINF {

	/****
	 * 验证用户DAO
	 * @param users
	 * @return UsersBean
	 */
	public UsersBean checkLogin(UsersBean users,String sql);
}
