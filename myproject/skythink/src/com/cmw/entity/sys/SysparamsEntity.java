package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 系统参数
 * @author pengdenghao
 * @date 2012-11-28T00:00:00
 */
@Description(remark="系统参数实体",createDate="2012-11-28T00:00:00",author="pengdenghao")
@Entity
@Table(name="ts_sysparams")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class SysparamsEntity extends IdBaseEntity {
	
	
	 //系统ID
	 @Column(name="sysid" ,nullable=false )
	 private Long sysid;



	 //业务引用键
	 @Column(name="recode" ,nullable=false ,length=50 )
	 private String recode;



	 //参数名称	
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 //参数值
	 @Column(name="val" ,nullable=false ,length=50 )
	 private String val;




	public SysparamsEntity() {

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
	  * 设置参数名称的值
	 * @param 	name	 参数名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取参数名称的值
	 * @return 返回参数名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置参数值的值
	 * @param 	val	 参数值
	**/
	public void setVal(String  val){
		 this.val=val;
 	}

	/**
	  * 获取参数值的值
	 * @return 返回参数值的值
	**/
	public String getVal(){
		 return val;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysid,isenabled,recode,name,val,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysid","isenabled","recode","name","val","remark"};
	}

}
