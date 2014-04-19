package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 角色菜单权限
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色菜单权限实体",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_Right")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RightEntity extends IdEntity {
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;
	
	 @Description(remark="对象类型")
	 @Column(name="objtype" ,nullable=false )
	 private Integer objtype;


	 @Description(remark="角色ID")
	 @Column(name="roleId" ,nullable=false )
	 private Long roleId;



	 @Description(remark="权限类型")
	 @Column(name="type" ,nullable=false )
	 private Integer type;



	 @Description(remark="卡片/菜单/模块ID")
	 @Column(name="mmId" ,nullable=false )
	 private Long mmId;




	public RightEntity() {

	}

	
	/**
	  * 设置对象类型的值
	 * @param 	objtype	 对象类型
	**/
	public void setObjtype(Integer  objtype){
		 this.objtype=objtype;
 	}

	/**
	  * 获取对象类型的值
	 * @return 返回对象类型的值
	**/
	public Integer getObjtype(){
		 return objtype;
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
	  * 设置权限类型的值
	 * @param 	type	 权限类型
	**/
	public void setType(Integer  type){
		 this.type=type;
 	}

	/**
	  * 获取权限类型的值
	 * @return 返回权限类型的值
	**/
	public Integer getType(){
		 return type;
 	}

	/**
	  * 设置卡片/菜单/模块ID的值
	 * @param 	mmId	 卡片/菜单/模块ID
	**/
	public void setMmId(Long  mmId){
		 this.mmId=mmId;
 	}

	/**
	  * 获取卡片/菜单/模块ID的值
	 * @return 返回卡片/菜单/模块ID的值
	**/
	public Long getMmId(){
		 return mmId;
 	}

	

	public Long getSysId() {
		return sysId;
	}


	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{sysId,objtype,roleId,type,mmId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"sysId","objtype","roleId","type","mmId"};
	}
	
	/**
	 * 对象类型	【角色:0】
	 */
	public static final int OBJTYPE_0 = 0;
	/**
	 *  对象类型	【用户:1】
	 */
	public static final int OBJTYPE_1 = 1;
	/**
	 *  权限类型	【卡片菜单权限:0】
	 */
	public static final int TYPE_0 = 0;
	/**
	 * 权限类型	【菜单权限:1】
	 */
	public static final int TYPE_1 = 1;
	/**
	 * 权限类型	【模块权限:2】
	 */
	public static final int TYPE_2 = 2;
	
}
