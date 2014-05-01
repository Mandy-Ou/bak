package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 用户角色关联
 * @author chengmingwei
 * @date 2012-12-08T00:00:00
 */
@Description(remark="用户角色关联实体",createDate="2012-12-08T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_Urole")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class UroleEntity extends IdEntity {
	
	
	 //用户ID
	 @Column(name="userId" ,nullable=false )
	 private Long userId;



	 //角色ID
	 @Column(name="roleId" ,nullable=false )
	 private Long roleId;




	public UroleEntity() {

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
	  * 设置角色ID的值
	 * @param 	roleId	 角色ID
	**/
	public void setRoleId(Long  roleId){
		 this.roleId=roleId;
 	}

	/**
	  * 获取角色ID的值
	 * @return 返回角色ID的值
	**/
	public Long getRoleId(){
		 return roleId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{userId,roleId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"userId","roleId"};
	}

}
