package com.cmw.entity.finance;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 审批金额建议
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批金额建议实体",createDate="2012-12-26T00:00:00",author="程明卫")
@Entity
@Table(name="fc_AuditAmount")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AuditAmountEntity extends IdBaseEntity {
	
	
	 @Description(remark="审批记录ID")
	 @Column(name="arecordId" ,nullable=false )
	 private Long arecordId;

	 @Description(remark="贷款金额")
	 @Column(name="appAmount" )
	 private BigDecimal appAmount;

	 @Description(remark="贷款期限(年)")
	 @Column(name="yearLoan" )
	 private Integer yearLoan;

	 @Description(remark="贷款期限(月)")
	 @Column(name="monthLoan" )
	 private Integer monthLoan;

	 @Description(remark="贷款期限(日)")
	 @Column(name="dayLoan" )
	 private Integer dayLoan;

	 @Description(remark="利率类型")
	 @Column(name="rateType" )
	 private Integer rateType;

	 @Description(remark="贷款利率")
	 @Column(name="rate" ,scale=2)
	 private Double rate;


	public AuditAmountEntity() {

	}

	
	/**
	  * 设置审批记录ID的值
	 * @param 	arecordId	 审批记录ID
	**/
	public void setArecordId(Long  arecordId){
		 this.arecordId=arecordId;
 	}

	/**
	  * 获取审批记录ID的值
	 * @return 返回审批记录ID的值
	**/
	public Long getArecordId(){
		 return arecordId;
 	}

	/**
	  * 设置贷款金额的值
	 * @param 	appAmount	 贷款金额
	**/
	public void setAppAmount(BigDecimal  appAmount){
		 this.appAmount=appAmount;
 	}

	/**
	  * 获取贷款金额的值
	 * @return 返回贷款金额的值
	**/
	public BigDecimal getAppAmount(){
		 return appAmount;
 	}

	/**
	  * 设置贷款期限(年)的值
	 * @param 	yearLoan	 贷款期限(年)
	**/
	public void setYearLoan(Integer  yearLoan){
		 this.yearLoan=yearLoan;
 	}

	/**
	  * 获取贷款期限(年)的值
	 * @return 返回贷款期限(年)的值
	**/
	public Integer getYearLoan(){
		 return yearLoan;
 	}

	/**
	  * 设置贷款期限(月)的值
	 * @param 	monthLoan	 贷款期限(月)
	**/
	public void setMonthLoan(Integer  monthLoan){
		 this.monthLoan=monthLoan;
 	}

	/**
	  * 获取贷款期限(月)的值
	 * @return 返回贷款期限(月)的值
	**/
	public Integer getMonthLoan(){
		 return monthLoan;
 	}

	/**
	  * 设置贷款期限(日)的值
	 * @param 	dayLoan	 贷款期限(日)
	**/
	public void setDayLoan(Integer  dayLoan){
		 this.dayLoan=dayLoan;
 	}

	/**
	  * 获取贷款期限(日)的值
	 * @return 返回贷款期限(日)的值
	**/
	public Integer getDayLoan(){
		 return dayLoan;
 	}

	/**
	  * 设置利率类型的值
	 * @param 	rateType	 利率类型
	**/
	public void setRateType(Integer  rateType){
		 this.rateType=rateType;
 	}

	/**
	  * 获取利率类型的值
	 * @return 返回利率类型的值
	**/
	public Integer getRateType(){
		 return rateType;
 	}

	/**
	  * 设置贷款利率的值
	 * @param 	rate	 贷款利率
	**/
	public void setRate(Double  rate){
		 this.rate=rate;
 	}

	/**
	  * 获取贷款利率的值
	 * @return 返回贷款利率的值
	**/
	public Double getRate(){
		 return rate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{arecordId,appAmount,yearLoan,monthLoan,dayLoan,rateType,rate};
	}

	@Override
	public String[] getFields() {
		return new String[]{"arecordId","appAmount","yearLoan","monthLoan","dayLoan","rateType","rate"};
	}

}
