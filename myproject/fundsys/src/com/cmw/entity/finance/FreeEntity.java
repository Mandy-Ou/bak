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
 * 放款手续费
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款手续费实体",createDate="2013-01-15T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Free")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FreeEntity extends IdBaseEntity {
	
	
	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="放款单ID")
	 @Column(name="loanInvoceId" ,nullable=false )
	 private Long loanInvoceId;

	 @Description(remark="应收手续费")
	 @Column(name="freeamount" ,nullable=false ,scale=2)
	 private BigDecimal freeamount = new BigDecimal(0);

	 @Description(remark="实收手续费")
	 @Column(name="yamount" ,nullable=false ,scale=2)
	 private BigDecimal yamount = new BigDecimal(0);

	 @Description(remark="最后收款日期")
	 @Column(name="lastDate" )
	 private Date lastDate;

	 @Description(remark="收款状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="豁免状态")
	 @Column(name="exempt" ,nullable=false )
	 private Integer exempt = 0;

	 @Description(remark="计提状态")
	 @Column(name="provision" ,nullable=false )
	 private Integer provision = 0;


	public FreeEntity() {

	}

	
	/**
	  * 设置贷款申请单ID的值
	 * @param 	formId	 贷款申请单ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取贷款申请单ID的值
	 * @return 返回贷款申请单ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	/**
	  * 设置放款单ID的值
	 * @param 	loanInvoceId	 放款单ID
	**/
	public void setLoanInvoceId(Long  loanInvoceId){
		 this.loanInvoceId=loanInvoceId;
 	}

	/**
	  * 获取放款单ID的值
	 * @return 返回放款单ID的值
	**/
	public Long getLoanInvoceId(){
		 return loanInvoceId;
 	}

	/**
	  * 设置应收手续费的值
	 * @param 	freeamount	 应收手续费
	**/
	public void setFreeamount(BigDecimal  freeamount){
		 this.freeamount=freeamount;
 	}

	/**
	  * 获取应收手续费的值
	 * @return 返回应收手续费的值
	**/
	public BigDecimal getFreeamount(){
		 return freeamount;
 	}

	/**
	  * 设置实收手续费的值
	 * @param 	yamount	 实收手续费
	**/
	public void setYamount(BigDecimal  yamount){
		 this.yamount=yamount;
 	}

	/**
	  * 获取实收手续费的值
	 * @return 返回实收手续费的值
	**/
	public BigDecimal getYamount(){
		 return yamount;
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

	/**
	  * 设置收款状态的值
	 * @param 	status	 收款状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取收款状态的值
	 * @return 返回收款状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}

	/**
	  * 设置豁免状态的值
	 * @param 	exempt	 豁免状态
	**/
	public void setExempt(Integer  exempt){
		 this.exempt=exempt;
 	}

	/**
	  * 获取豁免状态的值
	 * @return 返回豁免状态的值
	**/
	public Integer getExempt(){
		 return exempt;
 	}

	/**
	  * 设置计提状态的值
	 * @param 	provision	 计提状态
	**/
	public void setProvision(Integer  provision){
		 this.provision=provision;
 	}

	/**
	  * 获取计提状态的值
	 * @return 返回计提状态的值
	**/
	public Integer getProvision(){
		 return provision;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,formId,loanInvoceId,freeamount,yamount,lastDate,status,exempt,provision};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","formId","loanInvoceId","freeamount","yamount","lastDate","status","exempt","provision"};
	}

}
