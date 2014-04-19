package com.cmw.entity.fininter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 凭证字
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="凭证字实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_VoucherGroup")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class VoucherGroupEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务系统ID")
	 @Column(name="finsysId" ,nullable=false )
	 private Long finsysId;

	 @Description(remark="凭证字名称")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="凭证字ID")
	 @Column(name="refId" )
	 private Long refId;


	public VoucherGroupEntity() {

	}

	
	/**
	  * 设置财务系统ID的值
	 * @param 	finsysId	 财务系统ID
	**/
	public void setFinsysId(Long  finsysId){
		 this.finsysId=finsysId;
 	}

	/**
	  * 获取财务系统ID的值
	 * @return 返回财务系统ID的值
	**/
	public Long getFinsysId(){
		 return finsysId;
 	}

	/**
	  * 设置凭证字名称的值
	 * @param 	name	 凭证字名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取凭证字名称的值
	 * @return 返回凭证字名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置凭证字ID的值
	 * @param 	refId	 凭证字ID
	**/
	public void setRefId(Long  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取凭证字ID的值
	 * @return 返回凭证字ID的值
	**/
	public Long getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{finsysId,name,refId,isenabled,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"finsysId","name","refId","isenabled","createTime#yyyy-MM-dd HH:mm"};
	}

}
