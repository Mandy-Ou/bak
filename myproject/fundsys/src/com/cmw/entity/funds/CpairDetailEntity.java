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
 * 资金配对实体
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="配对明细实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_CpairDetail")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CpairDetailEntity extends IdBaseEntity {
	
	 @Description(remark="资金配对ID")
	 @Column(name="cid" ,nullable=false )
	 private Long cid;
	 
	 @Description(remark="委托合同ID")
	 @Column(name="eid" ,nullable=false )
	 private Long eid;
	 
	 @Description(remark="贷出金额")
	 @Column(name="amt" ,nullable=false )
	 private BigDecimal amt = new BigDecimal(0.00);
	 
	 @Description(remark="退回金额")
	 @Column(name="tamt" ,nullable=false )
	 private BigDecimal tamt = new BigDecimal(0.00); 
	 @Description(remark="最后还款日期")
	 @Column(name="doDate" )
	 private Date doDate;


	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getTamt() {
		return tamt;
	}

	public void setTamt(BigDecimal tamt) {
		this.tamt = tamt;
	}

	public Date getDoDate() {
		return doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,cid,eid,amt,tamt,doDate};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","cid","eid","amt","tamt","doDate#yyyy-MM-dd"};
	}

}
