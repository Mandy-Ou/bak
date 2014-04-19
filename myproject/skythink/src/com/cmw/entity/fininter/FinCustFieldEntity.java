package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 财务自定义字段
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务自定义字段实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_FinCustField ")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FinCustFieldEntity extends IdBaseEntity {
	
	@Description(remark="业务对象ID")
	 @Column(name="bussObjectId" ,nullable=false)
	 private String bussObjectId;
	
	 @Description(remark="字段说明")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="字段名称")
	 @Column(name="field" ,nullable=false ,length=30 )
	 private String field;

	 @Description(remark="表字段名称")
	 @Column(name="cmn" ,nullable=false ,length=30 )
	 private String cmn;

	 @Description(remark="数据类型")
	 @Column(name="dataType" ,nullable=false )
	 private Integer dataType = 1;


	public FinCustFieldEntity() {

	}

	
	
	public String getBussObjectId() {
		return bussObjectId;
	}



	public void setBussObjectId(String bussObjectId) {
		this.bussObjectId = bussObjectId;
	}



	/**
	  * 设置字段说明的值
	 * @param 	name	 字段说明
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取字段说明的值
	 * @return 返回字段说明的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置字段名称的值
	 * @param 	field	 字段名称
	**/
	public void setField(String  field){
		 this.field=field;
 	}

	/**
	  * 获取字段名称的值
	 * @return 返回字段名称的值
	**/
	public String getField(){
		 return field;
 	}

	/**
	  * 设置表字段名称的值
	 * @param 	column	 表字段名称
	**/
	public void setCmn(String  cmn){
		 this.cmn=cmn;
 	}

	/**
	  * 获取表字段名称的值
	 * @return 返回表字段名称的值
	**/
	public String getCmn(){
		 return cmn;
 	}

	/**
	  * 设置数据类型的值
	 * @param 	dataType	 数据类型
	**/
	public void setDataType(Integer  dataType){
		 this.dataType=dataType;
 	}

	/**
	  * 获取数据类型的值
	 * @return 返回数据类型的值
	**/
	public Integer getDataType(){
		 return dataType;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,bussObjectId,name,field,cmn,dataType,remark,isenabled,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","bussObjectId","name","field","cmn","dataType","remark","isenabled","createTime#yyyy-MM-dd HH:mm"};
	}

}
