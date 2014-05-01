package com.cmw.entity.funds;


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
 * 利息支付
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="利息支付实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_Interest")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class InterestEntity extends IdBaseEntity {
	 @Description(remark="委托客户ID")
	 @Column(name="entrustCustId" ,nullable=false )
	 private Long entrustCustId;
	 
	 @Description(remark="委托合同ID")
	 @Column(name="entrustContractId" ,nullable=false )
	 private Long entrustContractId;

	 @Description(remark="应付息日期")
	 @Column(name="xpayDate" )
	 private Date xpayDate;
	 
	 @Description(remark="下一付息日期")
	 @Column(name="nextDate" )
	 private Date nextDate;
	 
	 @Description(remark="利息金额")
	 @Column(name="iamount" ,nullable=false )
	 private BigDecimal iamount = new BigDecimal(0.00);
	 
	 @Description(remark="已付息金额")
	 @Column(name="yamount" ,nullable=false )
	 private BigDecimal yamount = new BigDecimal(0.00);
	
	 @Description(remark="退回利息")
	 @Column(name="riamount" ,nullable=false )
	 private BigDecimal riamount = new BigDecimal(0.00);
	 
	 
	 @Description(remark="最后付息日期")
	 @Column(name="lastDate" )
	 private Date lastDate;
	
	 @Description(remark="总状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status;
	 
	 
	 

	public Long getEntrustCustId() {
		return entrustCustId;
	}

	public void setEntrustCustId(Long entrustCustId) {
		this.entrustCustId = entrustCustId;
	}

	public Long getEntrustContractId() {
		return entrustContractId;
	}

	public void setEntrustContractId(Long entrustContractId) {
		this.entrustContractId = entrustContractId;
	}

	public Date getXpayDate() {
		return xpayDate;
	}

	public void setXpayDate(Date xpayDate) {
		this.xpayDate = xpayDate;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public BigDecimal getIamount() {
		return iamount;
	}

	public void setIamount(BigDecimal iamount) {
		this.iamount = iamount;
	}

	public BigDecimal getYamount() {
		return yamount;
	}

	public void setYamount(BigDecimal yamount) {
		this.yamount = yamount;
	}

	public BigDecimal getRiamount() {
		return riamount;
	}

	public void setRiamount(BigDecimal riamount) {
		this.riamount = riamount;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,entrustCustId,entrustContractId,xpayDate,nextDate,iamount,yamount,riamount,lastDate,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entrustCustId","entrustContractId","xpayDate#yyyy-MM-dd","nextDate#yyyy-MM-dd","iamount","yamount","riamount","lastDate#yyyy-MM-dd","status"};
	}

}
