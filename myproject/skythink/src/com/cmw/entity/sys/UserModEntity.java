package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 用户模块配置
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="用户模块配置实体",createDate="2013-03-08T00:00:00",author="程明卫")
@Entity
@Table(name="ts_UserMod")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class UserModEntity extends IdEntity {
	
	
	 @Description(remark="用户ID")
	 @Column(name="userId" ,nullable=false )
	 private Long userId;

	 @Description(remark="桌面模块编号")
	 @Column(name="modCode" ,nullable=false )
	 private String modCode;


	public UserModEntity() {

	}

	
	/**
	  * 设置用户ID的值
	 * @param 	userId	 用户ID
	**/
	public void setUserId(Long  userId){
		 this.userId=userId;
 	}

	/**
	  * 获取用户ID的值
	 * @return 返回用户ID的值
	**/
	public Long getUserId(){
		 return userId;
 	}

	/**
	  * 获取桌面模块编号的值
	 * @return 返回桌面模块编号的值
	**/
	public String getModCode() {
		return modCode;
	}

	/**
	  * 设置桌面模块编号值
	 * @param 	modCode	 桌面模块编号
	**/
	public void setModCode(String modCode) {
		this.modCode = modCode;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{userId,modCode};
	}

	@Override
	public String[] getFields() {
		return new String[]{"userId","modCode"};
	}

}
