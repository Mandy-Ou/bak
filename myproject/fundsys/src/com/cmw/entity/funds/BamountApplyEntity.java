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
 * 借款承诺书
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="撤资申请表表实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_BamountApply")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BamountApplyEntity extends IdBaseEntity {
	
	 @Description(remark="委托客户ID")
	 @Column(name="entrustCustId" ,nullable=false )
	 private Long entrustCustId;
	 
	 @Description(remark="委托合同ID")
	 @Column(name="entrustContractId" ,nullable=false )
	 private Long entrustContractId;
	 
	 @Description(remark="撤资金额")
	 @Column(name="bamount" ,nullable=false )
	 private BigDecimal bamount=new BigDecimal(0);
	 
	 @Description(remark="退还预付利息")
	 @Column(name="biamount" ,nullable=false )
	 private BigDecimal biamount=new BigDecimal(0);
	 
	 @Description(remark="实际支付金额")
	 @Column(name="rpamount" ,nullable=false )
	 private BigDecimal rpamount=new BigDecimal(0);
	 
	 @Description(remark="违约金额")
	 @Column(name="wamount" ,nullable=false )
	 private BigDecimal wamount=new BigDecimal(0);
	 
	 @Description(remark="是否满期")
	 @Column(name="isNotExpiration" ,nullable=false )
	 private Integer isNotExpiration;
	 
	 @Description(remark="撤资日期")
	 @Column(name="backDate" ,nullable=false )
	 private Date backDate;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=50 )
	 private String procId;
	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;
	 @Description(remark="撤资状态")
	 @Column(name="xstatus" ,nullable=false )
	 private Integer xstatus = 0;

	 

	@Override
	public Object[] getDatas() {
		return new Object[]{id,entrustCustId,entrustContractId,bamount,wamount,isNotExpiration,backDate,breed,procId,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entrustCustId","entrustContractId","bamount","wamount","isNotExpiration","backDate#yyyy-MM-ddddd","breed","procId","status"};
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public BigDecimal getBamount() {
		return bamount;
	}

	public void setBamount(BigDecimal bamount) {
		this.bamount = bamount;
	}

	public BigDecimal getWamount() {
		return wamount;
	}

	public void setWamount(BigDecimal wamount) {
		this.wamount = wamount;
	}

	public Integer getIsNotExpiration() {
		return isNotExpiration;
	}

	public void setIsNotExpiration(Integer isNotExpiration) {
		this.isNotExpiration = isNotExpiration;
	}

	public Date getBackDate() {
		return backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public BigDecimal getBiamount() {
		return biamount;
	}

	public void setBiamount(BigDecimal biamount) {
		this.biamount = biamount;
	}

	public BigDecimal getRpamount() {
		return rpamount;
	}

	public void setRpamount(BigDecimal rpamount) {
		this.rpamount = rpamount;
	}

	public Integer getXstatus() {
		return xstatus;
	}

	public void setXstatus(Integer xstatus) {
		this.xstatus = xstatus;
	}

}
