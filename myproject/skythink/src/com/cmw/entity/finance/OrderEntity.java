package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 扣款优先级
 * @author pdt
 * @date 2012-12-22T00:00:00
 */
@Description(remark="扣款优先级实体",createDate="2012-12-22T00:00:00",author="pdt")
@Entity
@Table(name="fc_Order")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class OrderEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="名称")
	 @Column(name="name" ,nullable=false ,length=30 )
	 private String name;

	 @Description(remark="优先级")
	 @Column(name="level" ,nullable=false )
	 private Integer level;

	 @Description(remark="扣收顺序")
	 @Column(name="orders" ,nullable=false )
	 private Integer orders = 1;


	public OrderEntity() {

	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
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
	  * 设置优先级的值
	 * @param 	level	 优先级
	**/
	public void setLevel(Integer  level){
		 this.level=level;
 	}

	/**
	  * 获取优先级的值
	 * @return 返回优先级的值
	**/
	public Integer getLevel(){
		 return level;
 	}

	/**
	  * 设置扣收顺序的值
	 * @param 	order	 扣收顺序
	**/
	public void setOrders(Integer  orders){
		 this.orders=orders;
 	}

	/**
	  * 获取扣收顺序的值
	 * @return 返回扣收顺序的值
	**/
	public Integer getOrders(){
		 return orders;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,name,level,orders};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","name","level","orders"};
	}

}
