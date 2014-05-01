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
 * 项目费用表
 * @author 李听
 * @date 2013-01-15T00:00:00
 */
@Description(remark="项目费用表实体",createDate="2013-01-15T00:00:00",author="李听")
@Entity
@Table(name="fc_PamountRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PamountRecordsEntity extends IdBaseEntity {
	 
	 @Description(remark="项目费用ID")
	 @Column(name="paid" ,nullable=false )
	 private Long paid;
	 
	 @Description(remark="收款帐号ID")
	 @Column(name="accountId")
	 private Long accountId;
	 @Description(remark="实收金额")
	 @Column(name="amount" ,nullable=false ,scale=2)
	 private BigDecimal amount = new BigDecimal(0);
	 
	 @Description(remark="收款日期")
	 @Column(name="rectDate" ,nullable=false)
	 private Date rectDate;

	 @Description(remark="收支方向")
	 @Column(name="payType" ,nullable=false )
	 private Integer payType;
	 
	 public Long getPaid() {
		return paid;
	}

	public void setPaid(Long paid) {
		this.paid = paid;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getRectDate() {
		return rectDate;
	}

	public void setRectDate(Date rectDate) {
		this.rectDate = rectDate;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getFcnumber() {
		return fcnumber;
	}

	public void setFcnumber(String fcnumber) {
		this.fcnumber = fcnumber;
	}

	@Description(remark="财务凭证编号")
	 @Column(name="fcnumber")
	 private String fcnumber;

	@Override
	public Object[] getDatas() {
		return new Object[]{id,paid,accountId,amount,rectDate,amount,fcnumber,creator,createTime,payType};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","paid","accountId","amount","rectDate#yyyy-MM-dd","amount","fcnumber","creator","createTime#yyyy-MM-dd","payType"};
	}

}
