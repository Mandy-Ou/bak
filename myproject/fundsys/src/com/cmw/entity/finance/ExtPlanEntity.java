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
 * 展期计划
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期计划实体",createDate="2013-09-23T00:00:00",author="pdh")
@Entity
@Table(name="fc_ExtPlan")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ExtPlanEntity extends IdBaseEntity {
	
	
	 @Description(remark="合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="动作类型")
	 @Column(name="actionType" ,nullable=false )
	 private Integer actionType;

	 @Description(remark="展期申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="期数")
	 @Column(name="phases" ,nullable=false )
	 private Integer phases;

	 @Description(remark="应还款日期")
	 @Column(name="xpayDate" ,nullable=false )
	 private Date xpayDate;

	 @Description(remark="本金")
	 @Column(name="principal" ,nullable=false ,scale=2)
	 private BigDecimal principal = new BigDecimal(0.00);

	 @Description(remark="利息")
	 @Column(name="interest" ,nullable=false ,scale=2)
	 private BigDecimal interest = new BigDecimal(0.00);

	 @Description(remark="管理费")
	 @Column(name="mgrAmount" ,nullable=false ,scale=2)
	 private BigDecimal mgrAmount = new BigDecimal(0.00);

	 @Description(remark="应付合计")
	 @Column(name="totalAmount" ,nullable=false ,scale=2)
	 private BigDecimal totalAmount = new BigDecimal(0.00);

	 @Description(remark="剩余本金")
	 @Column(name="reprincipal" ,nullable=false ,scale=2)
	 private BigDecimal reprincipal = new BigDecimal(0.00);


	public ExtPlanEntity() {

	}

	
	/**
	  * 设置合同ID的值
	 * @param 	contractId	 合同ID
	**/
	public void setContractId(Long  contractId){
		 this.contractId=contractId;
 	}

	/**
	  * 获取合同ID的值
	 * @return 返回合同ID的值
	**/
	public Long getContractId(){
		 return contractId;
 	}

	/**
	  * 设置动作类型的值
	 * @param 	actionType	 动作类型
	**/
	public void setActionType(Integer  actionType){
		 this.actionType=actionType;
 	}

	/**
	  * 获取动作类型的值
	 * @return 返回动作类型的值
	**/
	public Integer getActionType(){
		 return actionType;
 	}

	/**
	  * 设置展期申请单ID的值
	 * @param 	formId	 展期申请单ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取展期申请单ID的值
	 * @return 返回展期申请单ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	/**
	  * 设置期数的值
	 * @param 	phases	 期数
	**/
	public void setPhases(Integer  phases){
		 this.phases=phases;
 	}

	/**
	  * 获取期数的值
	 * @return 返回期数的值
	**/
	public Integer getPhases(){
		 return phases;
 	}

	/**
	  * 设置应还款日期的值
	 * @param 	xpayDate	 应还款日期
	**/
	public void setXpayDate(Date  xpayDate){
		 this.xpayDate=xpayDate;
 	}

	/**
	  * 获取应还款日期的值
	 * @return 返回应还款日期的值
	**/
	public Date getXpayDate(){
		 return xpayDate;
 	}

	/**
	  * 设置本金的值
	 * @param 	principal	 本金
	**/
	public void setPrincipal(BigDecimal  principal){
		 this.principal=principal;
 	}

	/**
	  * 获取本金的值
	 * @return 返回本金的值
	**/
	public BigDecimal getPrincipal(){
		 return principal;
 	}

	/**
	  * 设置利息的值
	 * @param 	interest	 利息
	**/
	public void setInterest(BigDecimal  interest){
		 this.interest=interest;
 	}

	/**
	  * 获取利息的值
	 * @return 返回利息的值
	**/
	public BigDecimal getInterest(){
		 return interest;
 	}

	/**
	  * 设置管理费的值
	 * @param 	mgrAmount	 管理费
	**/
	public void setMgrAmount(BigDecimal  mgrAmount){
		 this.mgrAmount=mgrAmount;
 	}

	/**
	  * 获取管理费的值
	 * @return 返回管理费的值
	**/
	public BigDecimal getMgrAmount(){
		 return mgrAmount;
 	}

	/**
	  * 设置应付合计的值
	 * @param 	totalAmount	 应付合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取应付合计的值
	 * @return 返回应付合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置剩余本金的值
	 * @param 	reprincipal	 剩余本金
	**/
	public void setReprincipal(BigDecimal  reprincipal){
		 this.reprincipal=reprincipal;
 	}

	/**
	  * 获取剩余本金的值
	 * @return 返回剩余本金的值
	**/
	public BigDecimal getReprincipal(){
		 return reprincipal;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,contractId,actionType,formId,phases,xpayDate,principal,interest,mgrAmount,totalAmount,reprincipal};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","contractId","actionType","formId","phases","xpayDate#yyyy-MM-dd","principal","interest","mgrAmount","totalAmount","reprincipal"};
	}

}
