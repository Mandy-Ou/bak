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
 * 实收手续费
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收手续费实体",createDate="2013-01-15T00:00:00",author="程明卫")
@Entity
@Table(name="fc_FreeRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FreeRecordsEntity extends IdBaseEntity {
	
	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;
	
	 @Description(remark="业务标识")
	 @Column(name="bussTag" ,nullable=false )
	 private Integer bussTag=0;
	 
	 @Description(remark="手续费ID")
	 @Column(name="invoceId" ,nullable=false )
	 private Long invoceId;

	 @Description(remark="实收金额")
	 @Column(name="amount" ,nullable=false ,scale=2)
	 private BigDecimal amount = new BigDecimal(0);

	 @Description(remark="收款日期")
	 @Column(name="rectDate" ,nullable=false )
	 private Date rectDate;

	 @Description(remark="收款帐号ID")
	 @Column(name="accountId" ,nullable=false )
	 private Long accountId;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;


	public FreeRecordsEntity() {

	}

	
	/**获取借款合同iD
	 * @return the contractId
	 */
	public Long getContractId() {
		return contractId;
	}


	/**设置借款合同Id
	 * @param contractId the contractId to set
	 */
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	
	/**
	 * 获取业务标识
	 * @return bussTag 业务标识
	 */
	public Integer getBussTag() {
		return bussTag;
	}

	/**
	 * 设置业务标识
	 * @param bussTag 业务标识
	 */
	public void setBussTag(Integer bussTag) {
		this.bussTag = bussTag;
	}


	/**
	  * 设置手续费ID的值
	 * @param 	invoceId	 手续费ID
	**/
	public void setInvoceId(Long  invoceId){
		 this.invoceId=invoceId;
 	}

	/**
	  * 获取手续费ID的值
	 * @return 返回手续费ID的值
	**/
	public Long getInvoceId(){
		 return invoceId;
 	}

	/**
	  * 设置实收金额的值
	 * @param 	amount	 实收金额
	**/
	public void setAmount(BigDecimal  amount){
		 this.amount=amount;
 	}

	/**
	  * 获取实收金额的值
	 * @return 返回实收金额的值
	**/
	public BigDecimal getAmount(){
		 return amount;
 	}

	/**
	  * 设置收款日期的值
	 * @param 	rectDate	 收款日期
	**/
	public void setRectDate(Date  rectDate){
		 this.rectDate=rectDate;
 	}

	/**
	  * 获取收款日期的值
	 * @return 返回收款日期的值
	**/
	public Date getRectDate(){
		 return rectDate;
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
	  * 设置财务凭证编号的值
	 * @param 	fcnumber	 财务凭证编号
	**/
	public void setFcnumber(String  fcnumber){
		 this.fcnumber=fcnumber;
 	}

	/**
	  * 获取财务凭证编号的值
	 * @return 返回财务凭证编号的值
	**/
	public String getFcnumber(){
		 return fcnumber;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,contractId,bussTag,invoceId,amount,rectDate,accountId,fcnumber,creator};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","contractId","bussTag","invoceId","amount","rectDate#yyy-MM-dd","accountId","fcnumber","creator"};
	}

}
