package com.cmw.service.impl.lock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁数据模型
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class LockModel implements Serializable {
	private String userName;
	private Map<String,List<String>> lockMap = new HashMap<String,List<String>>();
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Map<String, List<String>> getLockMap() {
		return lockMap;
	}
	public void setLockMap(Map<String, List<String>> lockMap) {
		this.lockMap = lockMap;
	}
	
}
