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
@Description(remark="资金配对实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_CapitalPair")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CapitalPairEntity extends IdBaseEntity {
	 @Description(remark="委托客户ID")
	 @Column(name="entrustCustId" ,nullable=false )
	 private Long entrustCustId;
	 
	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;
	 
	 @Description(remark="借款金额")
	 @Column(name="amt" ,nullable=false )
	 private BigDecimal amt = new BigDecimal(0.00);
	 
	 
	 @Description(remark="资金到位日期")
	 @Column(name="tagDate" )
	 private Date tagDate;
	 
	 @Description(remark="子业务流程ID")
	 @Column(name="breed")
	 private Long breed;
	 
	 
	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=30)
	 private String procId;
	 
	 
	 
	 
	 


	@Override
	public Object[] getDatas() {
		return new Object[]{id,entrustCustId,contractId,amt,tagDate,breed,procId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entrustCustId","contractId","amt","tagDate#yyyy-MM-dd","breed","procId"};
	}

	public Long getEntrustCustId() {
		return entrustCustId;
	}

	public void setEntrustCustId(Long entrustCustId) {
		this.entrustCustId = entrustCustId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Date getTagDate() {
		return tagDate;
	}

	public void setTagDate(Date tagDate) {
		this.tagDate = tagDate;
	}

	public Long getBreed() {
		return breed;
	}

	public void setBreed(Long breed) {
		this.breed = breed;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

}
