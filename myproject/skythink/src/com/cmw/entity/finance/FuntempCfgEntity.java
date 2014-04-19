package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 模板功能配置表
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="模板功能配置表实体",createDate="2013-11-19T00:00:00",author="赵世龙")
@Entity
@Table(name="fc_FuntempCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FuntempCfgEntity extends IdBaseEntity {

	 @Description(remark="模板ID")
	 @Column(name="tempId" ,nullable=false )
	 private Long tempId;

	 @Description(remark="菜单ID")
	 @Column(name="menuId" )
	 private Long menuId;

	 @Description(remark="功能标识码")
	 @Column(name="funTag" ,length=50 )
	 private String funTag;


	public FuntempCfgEntity() {

	}


	/**
	  * 设置模板ID的值
	 * @param 	tempId	 模板ID
	**/
	public void setTempId(Long  tempId){
		 this.tempId=tempId;
 	}

	/**
	  * 获取模板ID的值
	 * @return 返回模板ID的值
	**/
	public Long getTempId(){
		 return tempId;
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
	  * 设置功能标识码的值
	 * @param 	funTag	 功能标识码
	**/
	public void setFunTag(String  funTag){
		 this.funTag=funTag;
 	}

	/**
	  * 获取功能标识码的值
	 * @return 返回功能标识码的值
	**/
	public String getFunTag(){
		 return funTag;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{tempId,menuId,funTag};
	}

	@Override
	public String[] getFields() {
		return new String[]{"tempId","menuId","funTag"};
	}

}
