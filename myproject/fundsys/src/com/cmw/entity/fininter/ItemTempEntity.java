package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 核算项
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_ItemTemp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ItemTempEntity extends IdBaseEntity {
	
	
	 @Description(remark="分录模板ID")
	 @Column(name="entryId" ,nullable=false )
	 private Long entryId;

	 @Description(remark="核算项类别ID")
	 @Column(name="itemClassId" ,nullable=false ,length=50 )
	 private String itemClassId;

	 @Description(remark="核算业务对象ID")
	 @Column(name="bussObjectId" ,nullable=false )
	 private Long bussObjectId;

	 @Description(remark="核算自定义字段")
	 @Column(name="fieldIds" ,nullable=false ,length=60 )
	 private String fieldIds;

	 @Description(remark="核算自定义字段fieldNames")
	 @Column(name="fieldNames" ,length=200 )
	 private String fieldNames;
	 

	public ItemTempEntity() {

	}

	
	/**
	  * 设置分录模板ID的值
	 * @param 	entryId	 分录模板ID
	**/
	public void setEntryId(Long  entryId){
		 this.entryId=entryId;
 	}

	/**
	  * 获取分录模板ID的值
	 * @return 返回分录模板ID的值
	**/
	public Long getEntryId(){
		 return entryId;
 	}

	
	public Long getBussObjectId() {
		return bussObjectId;
	}


	public void setBussObjectId(Long bussObjectId) {
		this.bussObjectId = bussObjectId;
	}


	/**
	  * 设置核算项类别ID的值
	 * @param 	itemClassId	 核算项类别ID
	**/
	public void setItemClassId(String  itemClassId){
		 this.itemClassId=itemClassId;
 	}

	/**
	  * 获取核算项类别ID的值
	 * @return 返回核算项类别ID的值
	**/
	public String getItemClassId(){
		 return itemClassId;
 	}

	/**
	  * 设置核算自定义字段的值
	 * @param 	fieldIds	 核算自定义字段
	**/
	public void setFieldIds(String  fieldIds){
		 this.fieldIds=fieldIds;
 	}

	/**
	  * 获取核算自定义字段的值
	 * @return 返回核算自定义字段的值
	**/
	public String getFieldIds(){
		 return fieldIds;
 	}



	public String getFieldNames() {
		return fieldNames;
	}


	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,entryId,bussObjectId,itemClassId,fieldIds,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entryId","bussObjectId","itemClassId","fieldIds","fieldNames","remark"};
	}

}
