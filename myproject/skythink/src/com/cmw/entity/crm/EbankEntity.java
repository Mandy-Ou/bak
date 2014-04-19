package com.cmw.entity.crm;


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
 * 企业开户
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="企业开户实体",createDate="2012-12-25T00:00:00",author="pdh")
@Entity
@Table(name="crm_Ebank")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class EbankEntity extends IdBaseEntity {
	
	
	 @Description(remark="企业客户ID")
	 @Column(name="ecustomerId" ,nullable=false)
	 private Long ecustomerId;

	 @Description(remark="帐户类型")
	 @Column(name="accountType" ,nullable=false )
	 private Integer accountType = 1;

	 @Description(remark="开户帐号")
	 @Column(name="account" ,nullable=false ,length=25 )
	 private String account;

	 @Description(remark="开户银行")
	 @Column(name="bankOrg" ,length=80 )
	 private String bankOrg;

	 @Description(remark="开户时间")
	 @Column(name="orderDate" )
	 private Date orderDate;

	 @Description(remark="月均结算量")
	 @Column(name="setAmount" ,scale=2)
	 private BigDecimal setAmount;

	 @Description(remark="月均存款余额")
	 @Column(name="balance" ,scale=2)
	 private BigDecimal  balance;


	public EbankEntity() {

	}

	
	/**
	  * 设置企业客户ID的值
	 * @param 	ecustomerId	 企业客户ID
	**/
	public void setEcustomerId(Long  ecustomerId){
		 this.ecustomerId=ecustomerId;
 	}

	/**
	  * 获取企业客户ID的值
	 * @return 返回企业客户ID的值
	**/
	public Long getEcustomerId(){
		 return ecustomerId;
 	}

	/**
	  * 设置帐户类型的值
	 * @param 	accountType	 帐户类型
	**/
	public void setAccountType(Integer  accountType){
		 this.accountType=accountType;
 	}

	/**
	  * 获取帐户类型的值
	 * @return 返回帐户类型的值
	**/
	public Integer getAccountType(){
		 return accountType;
 	}

	/**
	  * 设置开户帐号的值
	 * @param 	account	 开户帐号
	**/
	public void setAccount(String  account){
		 this.account=account;
 	}

	/**
	  * 获取开户帐号的值
	 * @return 返回开户帐号的值
	**/
	public String getAccount(){
		 return account;
 	}

	/**
	  * 设置开户银行的值
	 * @param 	bankOrg	 开户银行
	**/
	public void setBankOrg(String  bankOrg){
		 this.bankOrg=bankOrg;
 	}

	/**
	  * 获取开户银行的值
	 * @return 返回开户银行的值
	**/
	public String getBankOrg(){
		 return bankOrg;
 	}

	/**
	  * 设置开户时间的值
	 * @param 	orderDate	 开户时间
	**/
	public void setOrderDate(Date  orderDate){
		 this.orderDate=orderDate;
 	}

	/**
	  * 获取开户时间的值
	 * @return 返回开户时间的值
	**/
	public Date getOrderDate(){
		 return orderDate;
 	}

	/**
	  * 设置月均结算量的值
	 * @param 	setAmount	 月均结算量
	**/
	public void setSetAmount(BigDecimal  setAmount){
		 this.setAmount=setAmount;
 	}

	/**
	  * 获取月均结算量的值
	 * @return 返回月均结算量的值
	**/
	public BigDecimal getSetAmount(){
		 return setAmount;
 	}

	/**
	  * 设置月均存款余额的值
	 * @param 	balance	 月均存款余额
	**/
	public void setBalance(BigDecimal   balance){
		 this.balance=balance;
 	}

	/**
	  * 获取月均存款余额的值
	 * @return 返回月均存款余额的值
	**/
	public BigDecimal  getBalance(){
		 return balance;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,ecustomerId,accountType,account,bankOrg,orderDate,setAmount,balance,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","ecustomerId","accountType","account","bankOrg","orderDate#yyyy-MM-dd","setAmount","balance","remark"};
	}

}
