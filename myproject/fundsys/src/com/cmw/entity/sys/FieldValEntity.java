package com.cmw.entity.sys;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.BaseInter;
import com.cmw.core.base.entity.IdEntity;


/**
 * 自定义字段值
 * @author 程明卫
 * @date 2013-03-21T00:00:00
 */
@Description(remark="自定义字段值实体",createDate="2013-03-21T00:00:00",author="程明卫")
@Entity
@Table(name="ts_FieldVal")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FieldValEntity extends IdEntity implements Serializable,BaseInter{
	
	
	 @Description(remark="个性化表单ID")
	 @Column(name="formdiyId" )
	 private Long formdiyId;

	 @Description(remark="业务ID")
	 @Column(name="formId" )
	 private Long formId;

	 @Description(remark="字段属性名")
	 @Column(name="fieldName" ,length=60 )
	 private String fieldName;

	 @Description(remark="字段值")
	 @Column(name="val" ,length=255 )
	 private String val;


	public FieldValEntity() {

	}

	
	/**
	  * 设置个性化表单ID的值
	 * @param 	formdiyId	 个性化表单ID
	**/
	public void setFormdiyId(Long  formdiyId){
		 this.formdiyId=formdiyId;
 	}

	/**
	  * 获取个性化表单ID的值
	 * @return 返回个性化表单ID的值
	**/
	public Long getFormdiyId(){
		 return formdiyId;
 	}

	/**
	  * 设置业务ID的值
	 * @param 	formId	 业务ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取业务ID的值
	 * @return 返回业务ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	/**
	  * 设置字段属性名的值
	 * @param 	fieldName	 字段属性名
	**/
	public void setFieldName(String  fieldName){
		 this.fieldName=fieldName;
 	}

	/**
	  * 获取字段属性名的值
	 * @return 返回字段属性名的值
	**/
	public String getFieldName(){
		 return fieldName;
 	}

	/**
	  * 设置字段值的值
	 * @param 	val	 字段值
	**/
	public void setVal(String  val){
		 this.val=val;
 	}

	/**
	  * 获取字段值的值
	 * @return 返回字段值的值
	**/
	public String getVal(){
		 return val;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{formdiyId,formId,fieldName,val};
	}

	@Override
	public String[] getFields() {
		return new String[]{"formdiyId","formId","fieldName","val"};
	}

}
