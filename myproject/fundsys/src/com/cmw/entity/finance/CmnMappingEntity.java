package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 列映射关系表
 * @author 赵世龙
 * @date 2013-11-22T00:00:00
 */
@Description(remark="列映射关系表实体",createDate="2013-11-22T00:00:00",author="赵世龙")
@Entity
@Table(name="fc_CmnMapping")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CmnMappingEntity extends IdBaseEntity {
	
	
	 @Description(remark="数据列名")
	 @Column(name="name" ,length=50 )
	 private String name;

	 @Description(remark="HTML列索引")
	 @Column(name="cellIndex" ,nullable=false )
	 private Integer cellIndex;

	 @Description(remark="数据类型")
	 @Column(name="dataType" ,nullable=false )
	 private Integer dataType;

	 @Description(remark="日期格式")
	 @Column(name="fmt" ,length=50 )
	 private String fmt;

	 @Description(remark="渲染函数")
	 @Column(name="fun" ,length=60 )
	 private String fun;

	 @Description(remark="映射列")
	 @Column(name="mapingCmns" ,length=60 )
	 private String mapingCmns;

	 @Description(remark="数据源ID")
	 @Column(name="tdsId" ,length=60 )
	 private Long tdsId;
	public Long getTdsId() {
		return tdsId;
	}


	public void setTdsId(Long tdsId) {
		this.tdsId = tdsId;
	}


	public CmnMappingEntity() {

	}

	
	/**
	  * 设置数据列名的值
	 * @param 	name	 数据列名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取数据列名的值
	 * @return 返回数据列名的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置HTML列索引的值
	 * @param 	cellIndex	 HTML列索引
	**/
	public void setCellIndex(Integer  cellIndex){
		 this.cellIndex=cellIndex;
 	}

	/**
	  * 获取HTML列索引的值
	 * @return 返回HTML列索引的值
	**/
	public Integer getCellIndex(){
		 return cellIndex;
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

	/**
	  * 设置日期格式的值
	 * @param 	fmt	 日期格式
	**/
	public void setFmt(String  fmt){
		 this.fmt=fmt;
 	}

	/**
	  * 获取日期格式的值
	 * @return 返回日期格式的值
	**/
	public String getFmt(){
		 return fmt;
 	}

	/**
	  * 设置渲染函数的值
	 * @param 	fun	 渲染函数
	**/
	public void setFun(String  fun){
		 this.fun=fun;
 	}

	/**
	  * 获取渲染函数的值
	 * @return 返回渲染函数的值
	**/
	public String getFun(){
		 return fun;
 	}

	/**
	  * 设置映射列的值
	 * @param 	mapingCmns	 映射列
	**/
	public void setMapingCmns(String  mapingCmns){
		 this.mapingCmns=mapingCmns;
 	}

	/**
	  * 获取映射列的值
	 * @return 返回映射列的值
	**/
	public String getMapingCmns(){
		 return mapingCmns;
 	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,name,tdsId,cellIndex,dataType,fmt,fun,mapingCmns,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","name","tdsId","cellIndex","dataType","fmt","fun","mapingCmns","remark"};
	}

}
