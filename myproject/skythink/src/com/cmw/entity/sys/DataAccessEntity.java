package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 数据访问权限
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="数据访问权限实体",createDate="2012-12-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_DataAccess")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class DataAccessEntity extends IdEntity {
	
	
	 @Description(remark="用户ID")
	 @Column(name="userId" ,nullable=false )
	 private Long userId;

	 @Description(remark="菜单ID")
	 @Column(name="menuId" ,nullable=false )
	 private Long menuId;

	 @Description(remark="数据访问级别")
	 @Column(name="level" ,nullable=false )
	 private Integer level;

	 @Description(remark="数据过滤ID列表")
	 @Column(name="accessIds" ,length=255 )
	 private String accessIds;

	 @Description(remark="数据过滤名称")
	 @Column(name="accessNames" ,length=255 )
	 private String accessNames;


	public DataAccessEntity() {

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

	/**
	  * 设置数据访问级别的值
	 * @param 	level	 数据访问级别
	**/
	public void setLevel(Integer  level){
		 this.level=level;
 	}

	/**
	  * 获取数据访问级别的值
	 * @return 返回数据访问级别的值
	**/
	public Integer getLevel(){
		 return level;
 	}

	/**
	  * 设置数据过滤ID列表的值
	 * @param 	accessIds	 数据过滤ID列表
	**/
	public void setAccessIds(String  accessIds){
		 this.accessIds=accessIds;
 	}

	/**
	  * 获取数据过滤ID列表的值
	 * @return 返回数据过滤ID列表的值
	**/
	public String getAccessIds(){
		 return accessIds;
 	}

	/**
	  * 设置数据过滤名称的值
	 * @param 	accessNames	 数据过滤名称
	**/
	public void setAccessNames(String  accessNames){
		 this.accessNames=accessNames;
 	}

	/**
	  * 获取数据过滤名称的值
	 * @return 返回数据过滤名称的值
	**/
	public String getAccessNames(){
		 return accessNames;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{userId,menuId,level,accessIds,accessNames};
	}

	@Override
	public String[] getFields() {
		return new String[]{"userId","menuId","level","accessIds","accessNames"};
	}

}
