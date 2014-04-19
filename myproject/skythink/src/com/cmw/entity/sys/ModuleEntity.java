package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 模块
 * @author chengmingwei
 * @date 2012-10-31T00:00:00
 */
@Description(remark="模块实体",createDate="2012-10-31T00:00:00",author="chengmingwei")
@Entity
@Table(name="ts_module")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ModuleEntity extends IdBaseEntity {
	
	
	 //按钮编码
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;



	 //按钮名称
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;

	 //按钮样式
	 @Column(name="iconCls" ,length=30 )
	 private String iconCls;

	 

	 //菜单ID
	 @Column(name="menuId" ,nullable=false )
	 private Long menuId;


	public ModuleEntity() {

	}

	
	/**
	  * 设置按钮编码的值
	 * @param 	code	 按钮编码
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取按钮编码的值
	 * @return 返回按钮编码的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置按钮名称的值
	 * @param 	name	 按钮名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取按钮名称的值
	 * @return 返回按钮名称的值
	**/
	public String getName(){
		 return name;
 	}
	
	
	/**
	  * 获取按钮样式的值
	 * @return 返回按钮样式的值
	**/
	public String getIconCls() {
		return iconCls;
	}

	/**
	  * 设置按钮样式的值
	 * @param 	name	 按钮样式
	**/
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	/**
	  * 设置菜单ID的值
	 * @param 	menuId	 菜单ID
	**/
	public void setMenuId(Long  menuId){
		 this.menuId=menuId;
 	}

	/**
	  * 获取菜单ID的值
	 * @return 返回菜单ID的值
	**/
	public Long getMenuId(){
		 return menuId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,name,iconCls,menuId,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","name","iconCls","menuId","remark"};
	}

}
