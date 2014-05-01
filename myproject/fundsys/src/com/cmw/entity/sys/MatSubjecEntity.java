package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 资料标题
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料标题实体",createDate="2012-12-26T00:00:00",author="pdt")
@Entity
@Table(name="ts_MatSubjec")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MatSubjecEntity extends IdBaseEntity {
	
	
	 @Description(remark="资料模板ID")
	 @Column(name="tempId" ,nullable=false )
	 private Long tempId;

	 @Description(remark="标题名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;

	 @Description(remark="排序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo;


	public MatSubjecEntity() {

	}

	
	/**
	  * 设置资料模板ID的值
	 * @param 	tempId	 资料模板ID
	**/
	public void setTempId(Long  tempId){
		 this.tempId=tempId;
 	}

	/**
	  * 获取资料模板ID的值
	 * @return 返回资料模板ID的值
	**/
	public Long getTempId(){
		 return tempId;
 	}

	/**
	  * 设置标题名称的值
	 * @param 	name	 标题名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取标题名称的值
	 * @return 返回标题名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置排序的值
	 * @param 	orderNo	 排序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取排序的值
	 * @return 返回排序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,tempId,name,orderNo,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","tempId","name","orderNo","remark"};
	}

}
