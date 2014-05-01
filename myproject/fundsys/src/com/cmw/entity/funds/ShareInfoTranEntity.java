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
@Description(remark="股东资料交接表实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_ShareInfoTran")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ShareInfoTranEntity extends IdBaseEntity {
	
	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;
	
	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	 
	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;
	 
	 @Description(remark="资金配对表ID ")
	 @Column(name="cid" ,nullable=false )
	 private Long cid;
	 
	 @Description(remark="借款金额")
	 @Column(name="loanMoney" ,nullable=false )
	 private BigDecimal loanMoney=new BigDecimal(0);
	 
	

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,custType,customerId,cid,loanMoney};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","custType","customerId","cid","loanMoney"};
	}


}
