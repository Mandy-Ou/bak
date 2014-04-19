package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 业务编号配置
 * @author 彭登浩
 * @date 2012-11-21T00:00:00
 */
@Description(remark="业务编号配置实体",createDate="2012-11-21T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_busscode")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BusscodeEntity extends IdBaseEntity {
	
	
	 //系统ID
	 @Column(name="sysid" ,nullable=false )
	 private Long sysid;



	 //引用值
	 @Column(name="recode" ,nullable=false ,length=50 )
	 private String recode;



	 //名称
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 //编号规则表达式
	 @Column(name="express" ,nullable=false ,length=255 )
	 private String express;



	 //函数名列表
	 @Column(name="funnames" ,length=200 )
	 private String funnames;




	public BusscodeEntity() {

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
	  * 设置引用值的值
	 * @param 	recode	 引用值
	**/
	public void setRecode(String  recode){
		 this.recode=recode;
 	}

	/**
	  * 获取引用值的值
	 * @return 返回引用值的值
	**/
	public String getRecode(){
		 return recode;
 	}

	/**
	  * 设置名称的值
	 * @param 	name	 名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取名称的值
	 * @return 返回名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置编号规则表达式的值
	 * @param 	express	 编号规则表达式
	**/
	public void setExpress(String  express){
		 this.express=express;
 	}

	/**
	  * 获取编号规则表达式的值
	 * @return 返回编号规则表达式的值
	**/
	public String getExpress(){
		 return express;
 	}

	/**
	  * 设置函数名列表的值
	 * @param 	funnames	 函数名列表
	**/
	public void setFunnames(String  funnames){
		 this.funnames=funnames;
 	}

	/**
	  * 获取函数名列表的值
	 * @return 返回函数名列表的值
	**/
	public String getFunnames(){
		 return funnames;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,sysid,recode,name,express,funnames,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","sysid","recode","name","express","funnames","remark"};
	}

}
