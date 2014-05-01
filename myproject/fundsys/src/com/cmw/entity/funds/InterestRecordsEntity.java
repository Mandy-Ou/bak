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
@Description(remark="利息记录实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_InterestRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class InterestRecordsEntity extends IdBaseEntity {
	 @Description(remark="利息总表ID")
	 @Column(name="interestId" ,nullable=false )
	 private Long interestId;

	 @Description(remark="付息方式")
	 @Column(name="settleType" ,nullable=false )
	 private Integer settleType;
	 
	 @Description(remark="付息金额")
	 @Column(name="amt" ,nullable=false )
	 private BigDecimal amt = new BigDecimal(0.00);
	 
	 
	 @Description(remark="付息日期")
	 @Column(name="rectDate" )
	 private Date rectDate;
	 
	 @Description(remark="收款帐号ID")
	 @Column(name="accountId")
	 private Long accountId;
	 


	 @Description(remark="	 财务凭证编号")
	 @Column(name="fcnumber" ,length=30)
	 private String fcnumber;

	public Long getInterestId() {
		return interestId;
	}

	public void setInterestId(Long interestId) {
		this.interestId = interestId;
	}

	public Integer getSettleType() {
		return settleType;
	}

	public void setSettleType(Integer settleType) {
		this.settleType = settleType;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Date getRectDate() {
		return rectDate;
	}

	public void setRectDate(Date rectDate) {
		this.rectDate = rectDate;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getFcnumber() {
		return fcnumber;
	}

	public void setFcnumber(String fcnumber) {
		this.fcnumber = fcnumber;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,interestId,settleType,amt,rectDate,accountId,fcnumber};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","interestId","settleType","amt","rectDate#yyyy-MM-dd","accountId","fcnumber"};
	}

}
