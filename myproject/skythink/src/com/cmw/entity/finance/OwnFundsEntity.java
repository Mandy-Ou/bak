package com.cmw.entity.finance;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 自有资金
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="自有资金实体",createDate="2013-08-13T00:00:00",author="pdh")
@Entity
@Table(name="fc_OwnFunds")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class OwnFundsEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="账户ID")
	 @Column(name="accountId" ,nullable=false )
	 private Long accountId;

	 @Description(remark="总金额")
	 @Column(name="totalAmount" ,nullable=false )
	 private BigDecimal totalAmount = new BigDecimal(0.00);

	 @Description(remark="累计贷出金额")
	 @Column(name="bamount" ,nullable=false )
	 private BigDecimal bamount = new BigDecimal(0.00);

	 @Description(remark="剩余可用金额")
	 @Column(name="uamount" ,nullable=false )
	 private BigDecimal uamount = new BigDecimal(0.00);

	 @Description(remark="最后存入时间")
	 @Column(name="lastDate" ,nullable=false )
	 private Date lastDate;


	public OwnFundsEntity() {

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
	  * 设置账户ID的值
	 * @param 	accountId	 账户ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取账户ID的值
	 * @return 返回账户ID的值
	**/
	public Long getAccountId(){
		 return accountId;
 	}

	/**
	  * 设置总金额的值
	 * @param 	totalAmount	 总金额
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取总金额的值
	 * @return 返回总金额的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置累计贷出金额的值
	 * @param 	bamount	 累计贷出金额
	**/
	public void setBamount(BigDecimal  bamount){
		 this.bamount=bamount;
 	}

	/**
	  * 获取累计贷出金额的值
	 * @return 返回累计贷出金额的值
	**/
	public BigDecimal getBamount(){
		 return bamount;
 	}

	/**
	  * 设置剩余可用金额的值
	 * @param 	uamount	 剩余可用金额
	**/
	public void setUamount(BigDecimal  uamount){
		 this.uamount=uamount;
 	}

	/**
	  * 获取剩余可用金额的值
	 * @return 返回剩余可用金额的值
	**/
	public BigDecimal getUamount(){
		 return uamount;
 	}

	/**
	  * 设置最后存入时间的值
	 * @param 	lastDate	 最后存入时间
	**/
	public void setLastDate(Date  lastDate){
		 this.lastDate=lastDate;
 	}

	/**
	  * 获取最后存入时间的值
	 * @return 返回最后存入时间的值
	**/
	public Date getLastDate(){
		 return lastDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,accountId,totalAmount,bamount,uamount,lastDate,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","accountId","totalAmount","bamount","uamount","lastDate","remark"};
	}

}
