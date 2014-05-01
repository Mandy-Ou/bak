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
 * 汇票信息登记
 * @author 彭登浩
 * @date 2014-01-20T00:00:00
 */
@Description(remark="汇票信息登记实体",createDate="2014-01-20T00:00:00",author="彭登浩")
@Entity
@Table(name="fu_AmountProof")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AmountProofEntity extends IdBaseEntity {
	
	@Description(remark="借款合同ID")
	@Column(name="contractId",nullable = false)
	private Long contractId;
	
	@Description(remark="原借款日期")
	@Column(name="bdate",nullable = false)
	private Date bdate;
	
	@Description(remark="原借款金额")
	@Column(name="amount",nullable = false)
	private BigDecimal amount  = new BigDecimal(0.00);
	
	@Description(remark="追加金额")
	@Column(name="bamount",nullable = false)
	private BigDecimal bamount  = new BigDecimal(0.00);
	
	@Description(remark="合计金额")
	@Column(name="tamount",nullable = false)
	private BigDecimal tamount  = new BigDecimal(0.00);
	
	@Description(remark="申请人")
	@Column(name="appManId",nullable = false)
	private Long appManId ;
	
	@Description(remark="申请日期")
	@Column(name="appDate",nullable = false)
	private Date appDate;
	
	@Description(remark="子业务流程ID")
	@Column(name="breed",nullable = false)
	private Long breed;
	
	@Description(remark="流程实例ID")
	@Column(name="procId",length = 50)
	private String procId;
	
	@Description(remark="状态")
	@Column(name="status",nullable = false)
	private Integer status = 0;
	
	
	
	

	/**
	 * @return the contractId
	 */
	public Long getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the bdate
	 */
	public Date getBdate() {
		return bdate;
	}

	/**
	 * @param bdate the bdate to set
	 */
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the bamount
	 */
	public BigDecimal getBamount() {
		return bamount;
	}

	/**
	 * @param bamount the bamount to set
	 */
	public void setBamount(BigDecimal bamount) {
		this.bamount = bamount;
	}

	/**
	 * @return the tamount
	 */
	public BigDecimal getTamount() {
		return tamount;
	}

	/**
	 * @param tamount the tamount to set
	 */
	public void setTamount(BigDecimal tamount) {
		this.tamount = tamount;
	}

	/**
	 * @return the appManId
	 */
	public Long getAppManId() {
		return appManId;
	}

	/**
	 * @param appManId the appManId to set
	 */
	public void setAppManId(Long appManId) {
		this.appManId = appManId;
	}

	/**
	 * @return the appDate
	 */
	public Date getAppDate() {
		return appDate;
	}

	/**
	 * @param appDate the appDate to set
	 */
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	/**
	 * @return the breed
	 */
	public Long getBreed() {
		return breed;
	}

	/**
	 * @param breed the breed to set
	 */
	public void setBreed(Long breed) {
		this.breed = breed;
	}


	/**
	 * @return the procId
	 */
	public String getProcId() {
		return procId;
	}

	/**
	 * @param procId the procId to set
	 */
	public void setProcId(String procId) {
		this.procId = procId;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,contractId,bdate,amount,bamount,tamount,appManId,appDate,breed,procId,status,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","contractId","bdate","amount","bamount","tamount","appManId","appDate","breed","procId","status","remark"};
	}


}
