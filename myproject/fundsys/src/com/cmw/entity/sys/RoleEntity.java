package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 角色
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色实体",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_Role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RoleEntity extends IdBaseEntity {
	
	
	 //角色编号
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 //角色名称
	 @Column(name="name" ,length=30 )
	 private String name;




	public RoleEntity() {

	}

	
	/**
	  * 设置角色编号的值
	 * @param 	code	 角色编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取角色编号的值
	 * @return 返回角色编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置角色编号的值
	 * @param 	name	 角色编号
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取角色编号的值
	 * @return 返回角色编号的值
	**/
	public String getName(){
		 return name;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name"};
	}

}
