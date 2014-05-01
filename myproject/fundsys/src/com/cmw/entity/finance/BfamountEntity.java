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
 * 预收帐款
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款实体",createDate="2013-02-28T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Bfamount")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BfamountEntity extends IdBaseEntity {
	
	
	 @Description(remark="基础客户ID")
	 @Column(name="custBaseId" ,nullable=false )
	 private Long custBaseId;

	 @Description(remark="累计预收金额")
	 @Column(name="bfamount" ,nullable=false)
	 private BigDecimal bfamount = new BigDecimal(0);

	 @Description(remark="已扣金额")
	 @Column(name="netamount" ,nullable=false )
	 private BigDecimal netamount = new BigDecimal(0);

	 @Description(remark="可扣金额")
	 @Column(name="canamount" ,nullable=false )
	 private BigDecimal canamount = new BigDecimal(0);

	 @Description(remark="收款帐号ID")
	 @Column(name="accountId" )
	 private Long accountId;

	 @Description(remark="最后收款日期")
	 @Column(name="lastDate" )
	 private Date lastDate;


	public BfamountEntity() {

	}

	
	/**
	  * 设置基础客户ID的值
	 * @param 	custBaseId	 基础客户ID
	**/
	public void setCustBaseId(Long  custBaseId){
		 this.custBaseId=custBaseId;
 	}

	/**
	  * 获取基础客户ID的值
	 * @return 返回基础客户ID的值
	**/
	public Long getCustBaseId(){
		 return custBaseId;
 	}

	/**
	  * 设置累计预收金额的值
	 * @param 	bfamount	 累计预收金额
	**/
	public void setBfamount(BigDecimal  bfamount){
		 this.bfamount=bfamount;
 	}

	/**
	  * 获取累计预收金额的值
	 * @return 返回累计预收金额的值
	**/
	public BigDecimal getBfamount(){
		 return bfamount;
 	}

	/**
	  * 设置已扣金额的值
	 * @param 	netamount	 已扣金额
	**/
	public void setNetamount(BigDecimal  netamount){
		 this.netamount=netamount;
 	}

	/**
	  * 获取已扣金额的值
	 * @return 返回已扣金额的值
	**/
	public BigDecimal getNetamount(){
		 return netamount;
 	}

	/**
	  * 设置可扣金额的值
	 * @param 	canamount	 可扣金额
	**/
	public void setCanamount(BigDecimal  canamount){
		 this.canamount=canamount;
 	}

	/**
	  * 获取可扣金额的值
	 * @return 返回可扣金额的值
	**/
	public BigDecimal getCanamount(){
		 return canamount;
 	}

	/**
	  * 设置收款帐号ID的值
	 * @param 	accountId	 收款帐号ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取收款帐号ID的值
	 * @return 返回收款帐号ID的值
	**/
	public Long getAccountId(){
		 return accountId;
 	}

	/**
	  * 设置最后收款日期的值
	 * @param 	lastDate	 最后收款日期
	**/
	public void setLastDate(Date  lastDate){
		 this.lastDate=lastDate;
 	}

	/**
	  * 获取最后收款日期的值
	 * @return 返回最后收款日期的值
	**/
	public Date getLastDate(){
		 return lastDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),custBaseId,bfamount,netamount,canamount,accountId,lastDate,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","custBaseId","bfamount","netamount","canamount","accountId","lastDate","isenabled"};
	}

}
