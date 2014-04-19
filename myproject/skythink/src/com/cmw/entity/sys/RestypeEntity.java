package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 资源
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="资源实体",createDate="2012-11-19T00:00:00",author="彭登浩")
@Entity
@Table(name="ts_restype")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RestypeEntity extends IdBaseEntity {
	
	
	 //名称
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 //引用值
	 @Column(name="recode" ,length=50 )
	 private String recode;




	public RestypeEntity() {

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



	@Override
	public Object[] getDatas() {
		return new Object[]{id,name,recode};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","name","recode"};
	}

}
