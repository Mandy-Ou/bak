package com.cmw.entity.crm;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 个人信用资料
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人信用资料实体",createDate="2012-12-15T00:00:00",author="pdh")
@Entity
@Table(name="crm_creditInfo")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CreditInfoEntity extends IdBaseEntity {
	
	
	 @Description(remark="个人客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="信用类型")
	 @Column(name="creitType" ,nullable=false )
	 private Long creitType;

	 @Description(remark="数量")
	 @Column(name="count" )
	 private Integer count;

	 @Description(remark="总贷款额度")
	 @Column(name="totalAmount" )
	 private BigDecimal totalAmount;

	 @Description(remark="每月供款")
	 @Column(name="monthAmount" )
	 private BigDecimal monthAmount;

	 @Description(remark="总贷款余额")
	 @Column(name="balance" )
	 private BigDecimal balance;


	public CreditInfoEntity() {

	}

	
	/**
	  * 设置个人客户ID的值
	 * @param 	customerId	 个人客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取个人客户ID的值
	 * @return 返回个人客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}

	/**
	  * 设置信用类型的值
	 * @param 	creitType	 信用类型
	**/
	public void setCreitType(Long  creitType){
		 this.creitType=creitType;
 	}

	/**
	  * 获取信用类型的值
	 * @return 返回信用类型的值
	**/
	public Long getCreitType(){
		 return creitType;
 	}

	/**
	  * 设置数量的值
	 * @param 	count	 数量
	**/
	public void setCount(Integer  count){
		 this.count=count;
 	}

	/**
	  * 获取数量的值
	 * @return 返回数量的值
	**/
	public Integer getCount(){
		 return count;
 	}

	/**
	  * 设置总贷款额度的值
	 * @param 	totalAmount	 总贷款额度
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取总贷款额度的值
	 * @return 返回总贷款额度的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置每月供款的值
	 * @param 	monthAmount	 每月供款
	**/
	public void setMonthAmount(BigDecimal  monthAmount){
		 this.monthAmount=monthAmount;
 	}

	/**
	  * 获取每月供款的值
	 * @return 返回每月供款的值
	**/
	public BigDecimal getMonthAmount(){
		 return monthAmount;
 	}

	/**
	  * 设置总贷款余额的值
	 * @param 	balance	 总贷款余额
	**/
	public void setBalance(BigDecimal  balance){
		 this.balance=balance;
 	}

	/**
	  * 获取总贷款余额的值
	 * @return 返回总贷款余额的值
	**/
	public BigDecimal getBalance(){
		 return balance;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,customerId,creitType,count,totalAmount,monthAmount,balance,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","customerId","creitType","count","totalAmount","monthAmount","balance","remark"};
	}

}
