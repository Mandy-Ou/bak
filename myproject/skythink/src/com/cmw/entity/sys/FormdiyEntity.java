package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 表单DIY表
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="表单DIY表实体",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Entity
@Table(name="ts_formdiy")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FormdiyEntity extends IdBaseEntity {
	
	
	 //系统ID
	 @Column(name="sysid" ,nullable=false )
	 private Long sysid;



	 //业务引用键
	 @Column(name="recode" ,nullable=false ,length=50 )
	 private String recode;



	 //表单名称
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 //关联功能列表
	 @Column(name="funcs" ,length=200 )
	 private String funcs;



	 //关联功能引用键
	 @Column(name="frecode" ,length=200 )
	 private String frecode;



	 //是否来自基础数据
	 @Column(name="isRestype" ,nullable=false )
	 private Integer isRestype = 0;



	 //最大列数
	 @Column(name="maxCmncount" )
	 private Integer maxCmncount = 0;




	public FormdiyEntity() {

	}

	
	/**
	  * 设置系统ID的值
	 * @param 	sysid	 系统ID
	**/
	public void setSysid(Long  sysid){
		 this.sysid=sysid;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysid(){
		 return sysid;
 	}

	/**
	  * 设置业务引用键的值
	 * @param 	recode	 业务引用键
	**/
	public void setRecode(String  recode){
		 this.recode=recode;
 	}

	/**
	  * 获取业务引用键的值
	 * @return 返回业务引用键的值
	**/
	public String getRecode(){
		 return recode;
 	}

	/**
	  * 设置表单名称的值
	 * @param 	name	 表单名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取表单名称的值
	 * @return 返回表单名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置关联功能列表的值
	 * @param 	funcs	 关联功能列表
	**/
	public void setFuncs(String  funcs){
		 this.funcs=funcs;
 	}

	/**
	  * 获取关联功能列表的值
	 * @return 返回关联功能列表的值
	**/
	public String getFuncs(){
		 return funcs;
 	}

	/**
	  * 设置关联功能引用键的值
	 * @param 	frecode	 关联功能引用键
	**/
	public void setFrecode(String  frecode){
		 this.frecode=frecode;
 	}

	/**
	  * 获取关联功能引用键的值
	 * @return 返回关联功能引用键的值
	**/
	public String getFrecode(){
		 return frecode;
 	}

	/**
	  * 设置是否来自基础数据的值
	 * @param 	isRestype	 是否来自基础数据
	**/
	public void setIsRestype(Integer  isRestype){
		 this.isRestype=isRestype;
 	}

	/**
	  * 获取是否来自基础数据的值
	 * @return 返回是否来自基础数据的值
	**/
	public Integer getIsRestype(){
		 return isRestype;
 	}

	/**
	  * 设置最大列数的值
	 * @param 	maxCmncount	 最大列数
	**/
	public void setMaxCmncount(Integer  maxCmncount){
		 this.maxCmncount=maxCmncount;
 	}

	/**
	  * 获取最大列数的值
	 * @return 返回最大列数的值
	**/
	public Integer getMaxCmncount(){
		 return maxCmncount;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,sysid,recode,name,funcs,frecode,isRestype,maxCmncount};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","sysid","recode","name","funcs","frecode","isRestype","maxCmncount"};
	}

}
