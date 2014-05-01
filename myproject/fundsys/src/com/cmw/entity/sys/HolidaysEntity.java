package com.cmw.entity.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 节假日设置
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
@Description(remark="节假日设置实体",createDate="2012-11-20T00:00:00",author="程明卫")
@Entity
@Table(name="ts_Holidays")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class HolidaysEntity extends IdBaseEntity {

	@Description(remark="节假日名称")
	 @Column(name="name" ,nullable=false ,length=100 )
	 private String name;



	 @Description(remark="开始日期")
	 @Column(name="sdate" ,nullable=false )
	 private Date sdate;


	 @Description(remark="结束日期")
	 @Column(name="edate" ,nullable=false )
	 private Date edate;



	 @Description(remark="排列顺序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo = 1;




	public HolidaysEntity() {

	}
	
	
	/**
	  * 设置节假日名称的值
	 * @param 	name	 节假日名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取节假日名称的值
	 * @return 返回节假日名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置开始日期的值
	 * @param 	sdate	 开始日期
	**/
	public void setSdate(Date  sdate){
		 this.sdate=sdate;
 	}

	/**
	  * 获取开始日期的值
	 * @return 返回开始日期的值
	**/
	public Date getSdate(){
		 return sdate;
 	}

	/**
	  * 设置结束日期的值
	 * @param 	edate	 结束日期
	**/
	public void setEdate(Date  edate){
		 this.edate=edate;
 	}

	/**
	  * 获取结束日期的值
	 * @return 返回结束日期的值
	**/
	public Date getEdate(){
		 return edate;
 	}

	/**
	  * 设置排列顺序的值
	 * @param 	orderNo	 排列顺序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取排列顺序的值
	 * @return 返回排列顺序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,name,sdate,edate,orderNo,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","name","sdate#yyyy-MM-dd","edate#yyyy-MM-dd","orderNo","remark"};
	}

}
