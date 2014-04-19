package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 自定义字段表
 * @author pengdenghao
 * @date 2012-11-26T00:00:00
 */
@Description(remark="自定义字段表实体",createDate="2012-11-26T00:00:00",author="pengdenghao")
@Entity
@Table(name="ts_fieldCustom")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FieldCustomEntity extends IdBaseEntity {
	
	
	 //表单diy Id
	 @Column(name="formdiyId" ,nullable=false )
	 private Long formdiyId;



	 //字段属性名
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;



	 //默认显示名称
	 @Column(name="dispName" ,nullable=false ,length=50 )
	 private String dispName;



	 //控件类型
	 @Column(name="controlType" ,nullable=false )
	 private Integer controlType = 0;

	 //是否必填
	 @Column(name="isRequired" ,nullable=false )
	 private Integer isRequired = 0;

	 //最大长度
	 @Column(name="maxlength" )
	 private Integer maxlength = 0;



	 //所在行
	 @Column(name="row" )
	 private Integer row;



	 //所在列
	 @Column(name="col" )
	 private Integer col;



	 //宽
	 @Column(name="width" )
	 private Integer width;



	 //高
	 @Column(name="height" )
	 private Integer height;



	 //默认值
	 @Column(name="dval" ,length=50 )
	 private String dval;



	 //数据源
	 @Column(name="datasource" ,length=200 )
	 private String datasource;



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




	public FieldCustomEntity() {

	}

	
	/**
	  * 设置表单diy Id的值
	 * @param 	formdiyId	 表单diy Id
	**/
	public void setFormdiyId(Long  formdiyId){
		 this.formdiyId=formdiyId;
 	}

	/**
	  * 获取表单diy Id的值
	 * @return 返回表单diy Id的值
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
	  * 设置控件类型的值
	 * @param 	controlType	 控件类型
	**/
	public void setControlType(Integer  controlType){
		 this.controlType=controlType;
 	}

	/**
	  * 获取控件类型的值
	 * @return 返回控件类型的值
	**/
	public Integer getControlType(){
		 return controlType;
 	}

	/**
	  * 设置最大长度的值
	 * @param 	maxlength	 最大长度
	**/
	public void setMaxlength(Integer  maxlength){
		 this.maxlength=maxlength;
 	}

	/**
	  * 获取最大长度的值
	 * @return 返回最大长度的值
	**/
	public Integer getMaxlength(){
		 return maxlength;
 	}

	/**
	  * 设置所在行的值
	 * @param 	row	 所在行
	**/
	public void setRow(Integer  row){
		 this.row=row;
 	}

	/**
	  * 获取所在行的值
	 * @return 返回所在行的值
	**/
	public Integer getRow(){
		 return row;
 	}

	/**
	  * 设置所在列的值
	 * @param 	col	 所在列
	**/
	public void setCol(Integer  col){
		 this.col=col;
 	}

	/**
	  * 获取所在列的值
	 * @return 返回所在列的值
	**/
	public Integer getCol(){
		 return col;
 	}

	/**
	  * 设置宽的值
	 * @param 	width	 宽
	**/
	public void setWidth(Integer  width){
		 this.width=width;
 	}

	/**
	  * 获取宽的值
	 * @return 返回宽的值
	**/
	public Integer getWidth(){
		 return width;
 	}

	/**
	  * 设置高的值
	 * @param 	height	 高
	**/
	public void setHeight(Integer  height){
		 this.height=height;
 	}

	/**
	  * 获取高的值
	 * @return 返回高的值
	**/
	public Integer getHeight(){
		 return height;
 	}

	/**
	  * 设置默认值的值
	 * @param 	dval	 默认值
	**/
	public void setDval(String  dval){
		 this.dval=dval;
 	}

	/**
	  * 获取默认值的值
	 * @return 返回默认值的值
	**/
	public String getDval(){
		 return dval;
 	}

	/**
	  * 设置数据源的值
	 * @param 	datasource	 数据源
	**/
	public void setDatasource(String  datasource){
		 this.datasource=datasource;
 	}

	/**
	  * 获取数据源的值
	 * @return 返回数据源的值
	**/
	public String getDatasource(){
		 return datasource;
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



	public Integer getIsRequired() {
		return isRequired;
	}


	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,formdiyId,name,dispName,controlType,isRequired,maxlength,row,col,width,height,dval,datasource,ename,jname,twname,fname,koname};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","formdiyId","name","dispName","controlType","isRequired","maxlength","row","col","width","height","dval","datasource","ename","jname","twname","fname","koname"};
	}

}
