package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 字段属性表
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="字段属性表实体",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Entity
@Table(name="ts_fieldProp")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FieldPropEntity extends IdBaseEntity {
	
	
	 //表单diyId
	 @Column(name="formdiyId" ,nullable=false )
	 private Long formdiyId;



	 //字段属性名
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;



	 //标准名称
	 @Column(name="standName" ,nullable=false ,length=30 )
	 private String standName;



	 //默认显示名称
	 @Column(name="dispName" ,nullable=false ,length=50 )
	 private String dispName;



	 //是否必填
	 @Column(name="isRequired" )
	 private Integer isRequired = 0;



	 //英文名称
	 @Column(name="ename" ,length=50 )
	 private String ename;



	 //日文名称
	 @Column(name="jname" ,length=50 )
	 private String jname;



	 //繁体中文名称
	 @Column(name="twname" ,length=50 )
	 private String twname;



	 //法文名称
	 @Column(name="fname" ,length=50 )
	 private String fname;



	 //韩文名称
	 @Column(name="koname" ,length=50 )
	 private String koname;




	public FieldPropEntity() {

	}

	
	/**
	  * 设置表单diyId的值
	 * @param 	formdiyId	 表单diyId
	**/
	public void setFormdiyId(Long  formdiyId){
		 this.formdiyId=formdiyId;
 	}

	/**
	  * 获取表单diyId的值
	 * @return 返回表单diyId的值
	**/
	public Long getFormdiyId(){
		 return formdiyId;
 	}

	/**
	  * 设置字段属性名的值
	 * @param 	name	 字段属性名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取字段属性名的值
	 * @return 返回字段属性名的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置标准名称的值
	 * @param 	standName	 标准名称
	**/
	public void setStandName(String  standName){
		 this.standName=standName;
 	}

	/**
	  * 获取标准名称的值
	 * @return 返回标准名称的值
	**/
	public String getStandName(){
		 return standName;
 	}

	/**
	  * 设置默认显示名称的值
	 * @param 	dispName	 默认显示名称
	**/
	public void setDispName(String  dispName){
		 this.dispName=dispName;
 	}

	/**
	  * 获取默认显示名称的值
	 * @return 返回默认显示名称的值
	**/
	public String getDispName(){
		 return dispName;
 	}

	/**
	  * 设置是否必填的值
	 * @param 	isRequired	 是否必填
	**/
	public void setIsRequired(Integer  isRequired){
		 this.isRequired=isRequired;
 	}

	/**
	  * 获取是否必填的值
	 * @return 返回是否必填的值
	**/
	public Integer getIsRequired(){
		 return isRequired;
 	}

	/**
	  * 设置英文名称的值
	 * @param 	ename	 英文名称
	**/
	public void setEname(String  ename){
		 this.ename=ename;
 	}

	/**
	  * 获取英文名称的值
	 * @return 返回英文名称的值
	**/
	public String getEname(){
		 return ename;
 	}

	/**
	  * 设置日文名称的值
	 * @param 	jname	 日文名称
	**/
	public void setJname(String  jname){
		 this.jname=jname;
 	}

	/**
	  * 获取日文名称的值
	 * @return 返回日文名称的值
	**/
	public String getJname(){
		 return jname;
 	}

	/**
	  * 设置繁体中文名称的值
	 * @param 	twname	 繁体中文名称
	**/
	public void setTwname(String  twname){
		 this.twname=twname;
 	}

	/**
	  * 获取繁体中文名称的值
	 * @return 返回繁体中文名称的值
	**/
	public String getTwname(){
		 return twname;
 	}

	/**
	  * 设置法文名称的值
	 * @param 	fname	 法文名称
	**/
	public void setFname(String  fname){
		 this.fname=fname;
 	}

	/**
	  * 获取法文名称的值
	 * @return 返回法文名称的值
	**/
	public String getFname(){
		 return fname;
 	}

	/**
	  * 设置韩文名称的值
	 * @param 	koname	 韩文名称
	**/
	public void setKoname(String  koname){
		 this.koname=koname;
 	}

	/**
	  * 获取韩文名称的值
	 * @return 返回韩文名称的值
	**/
	public String getKoname(){
		 return koname;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,formdiyId,name,standName,dispName,isRequired,ename,jname,twname,fname,koname};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","formdiyId","name","standName","dispName","isRequired","ename","jname","twname","fname","koname"};
	}

}
