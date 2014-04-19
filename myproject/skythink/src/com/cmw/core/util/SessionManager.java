package com.cmw.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cmw.core.security.certificate.MD5;
import com.cmw.entity.sys.UserEntity;

/**
 *  Session 管理类
 * @author Administrator
 *
 */
public class SessionManager {
	private static Map<String, UserSession> store = new HashMap<String, UserSession>(); 
	/**
	 * 创建用用户会话
	 */
	public static void createUserSession(String uuid,UserEntity curruser){
		
	}
	
	/**
	 * 获取 以 uuid 所指定的用户
	 * @param uuid
	 * @return
	 */
	public static UserEntity getUser(String uuid){
		return null;
	}
	
	/**
	 * 通过 uuid 加用户名组合创建 UUID
	 * @param uuid UUID字符串
	 * @param curruser 当前用户，主要用户取出登录密码
	 * @return
	 */
	public static String createUuid(String uuid,UserEntity curruser){
		//MD5加密用户名，防止出现“_”与现有的“_”发生冲突
		String name = new MD5(curruser.getUserName()).toMD5();
		return uuid+"_"+name;
	}
	
	class UserSession{
		private Date createTime;	//UserSession 创建时间
		private Date modifytime;	//UserSession 修改时间
		//默认过期时间为半个小时
		private Integer maxInactiveInterval=1800;
		private UserEntity currobj;	//当前用户对象
		
		
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getModifytime() {
			return modifytime;
		}
		public void setModifytime(Date modifytime) {
			this.modifytime = modifytime;
		}
		public Integer getMaxInactiveInterval() {
			return maxInactiveInterval;
		}
		public void setMaxInactiveInterval(Integer maxInactiveInterval) {
			this.maxInactiveInterval = maxInactiveInterval;
		}
		public UserEntity getCurrobj() {
			return currobj;
		}
		public void setCurrobj(UserEntity currobj) {
			this.currobj = currobj;
		}
		
	}
}
