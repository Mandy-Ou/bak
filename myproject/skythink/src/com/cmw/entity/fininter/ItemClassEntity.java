package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 核算项类别
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项类别实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_ItemClass")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ItemClassEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="核算项类别代码")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="类别名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="业务对象ID")
	 @Column(name="bussObjectId" )
	 private Long bussObjectId;

	 @Description(remark="核算项类别ID")
	 @Column(name="refId" )
	 private Long refId;


	public ItemClassEntity() {

	}

	
	/**
	  * 设置财务系统ID的值
	 * @param 	finsysId	 财务系统ID
	**/
	public void setFinsysId(Long  finsysId){
		 this.finsysId=finsysId;
 	}

	/**
	  * 获取财务系统ID的值
	 * @return 返回财务系统ID的值
	**/
	public Long getFinsysId(){
		 return finsysId;
 	}

	/**
	  * 设置核算项类别代码的值
	 * @param 	code	 核算项类别代码
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取核算项类别代码的值
	 * @return 返回核算项类别代码的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置类别名称的值
	 * @param 	name	 类别名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取类别名称的值
	 * @return 返回类别名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置业务对象ID的值
	 * @param 	bussObjectId	 业务对象ID
	**/
	public void setBussObjectId(Long  bussObjectId){
		 this.bussObjectId=bussObjectId;
 	}

	/**
	  * 获取业务对象ID的值
	 * @return 返回业务对象ID的值
	**/
	public Long getBussObjectId(){
		 return bussObjectId;
 	}

	/**
	  * 设置核算项类别ID的值
	 * @param 	refId	 核算项类别ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取核算项类别ID的值
	 * @return 返回核算项类别ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,code,name,bussObjectId,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","code","name","bussObjectId","refId"};
	}

}
