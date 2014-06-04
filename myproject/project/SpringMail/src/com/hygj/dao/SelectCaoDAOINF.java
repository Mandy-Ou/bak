package com.hygj.dao;

import java.util.List;

import com.hygj.bean.EmailBean;

public interface SelectCaoDAOINF {

	public List<EmailBean> selectCaoEmail(String sql,String username);
	
	public int selectCaoCount(String sql,String username);
}
