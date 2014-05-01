package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 角色模块配置
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="角色模块配置实体",createDate="2013-03-08T00:00:00",author="程明卫")
@Entity
@Table(name="ts_RoleMod")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RoleModEntity extends IdEntity {
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;
	 
	 @Description(remark="角色ID")
	 @Column(name="roleId" ,nullable=false )
	 private Long roleId;

	 @Description(remark="桌面模块编号")
	 @Column(name="modCode" ,nullable=false )
	 private String modCode;


	public RoleModEntity() {

	}

	
	
	public Long getSysId() {
		return sysId;
	}



	public void setSysId(Long sysId) {
		this.sysId = sysId;
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
		return new Object[]{sysId,roleId,modCode};
	}

	@Override
	public String[] getFields() {
		return new String[]{"sysId","roleId","modCode"};
	}

}
