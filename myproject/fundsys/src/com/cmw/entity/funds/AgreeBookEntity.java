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
@Description(remark="借款承诺书实体",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_AgreeBook")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AgreeBookEntity extends IdBaseEntity {
	
//	
//	 @Description(remark="客户类型")
//	 @Column(name="custType" ,nullable=false )
//	 private Integer custType;
	
	
	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	 
	 @Description(remark="出借人")
	 @Column(name="bman" ,length=100 )
	 private String bman;
	 
	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;
	 
	 @Description(remark="借款期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan;
	 
	 @Description(remark="借款期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan;
	 
	 @Description(remark="超过期限(年)")
	 @Column(name="overyLoan" ,nullable=false )
	 private Integer overyLoan;
	 
	 @Description(remark="借款期限(日)")
	 @Column(name="dayLoan" ,nullable=false )
	 private Integer dayLoan;
	 
	 @Description(remark="超过期限(月)")
	 @Column(name="overmLoan" ,nullable=false )
	 private Integer overmLoan;
	 
	 @Description(remark="超过期限(日)")
	 @Column(name="overdLoan" ,nullable=false )
	 private Integer overdLoan;
	 
	 @Description(remark="上浮利率1")
	 @Column(name="uprate" ,nullable=false )
	 private Float uprate = new Float(0.0000);
	 
	 @Description(remark="上浮利率单位1")
	 @Column(name="unint" ,nullable=false )
	 private Integer unint;
	 
	 @Description(remark="超过期限2(年)")
	 @Column(name="overyLoan2" ,nullable=false )
	 private Integer overyLoan2;
	 
	 @Description(remark="超过期限2(月)")
	 @Column(name="overmLoan2" ,nullable=false )
	 private Integer overmLoan2;
	 
	 @Description(remark="超过期限2(日)")
	 @Column(name="overdLoan2" ,nullable=false )
	 private Integer overdLoan2;
	 
	 @Description(remark="上浮利率2")
	 @Column(name="uprate2" ,nullable=false )
	 private Float uprate2 = new Float(0.0000);
	 
	 @Description(remark="上浮利率单位2")
	 @Column(name="unint2" ,nullable=false )
	 private Integer unint2;
	 
	 @Description(remark="逾期天数")
	 @Column(name="lateDays" ,nullable=false )
	 private Integer lateDays;
	 @Description(remark="罚息利率")
	 @Column(name="pprate" ,nullable=false )
	 private Float pprate = new Float(0.0000);
	 
	 @Description(remark="罚息利率单位1")
	 @Column(name="punint" ,nullable=false )
	 private Integer punint;
	 @Description(remark="处置期限(月)")
	 @Column(name="domonthes" ,nullable=false )
	 private Integer domonthes;
	 @Description(remark="承诺人")
	 @Column(name="comitMan" ,length=60 )
	 private String comitMan;
	 
	 @Description(remark="承诺日期")
	 @Column(name="comitDate" )
	 private Date comitDate;

//	public Integer getCustType() {
//		return custType;
//	}
//
//	public void setCustType(Integer custType) {
//		this.custType = custType;
//	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getBman() {
		return bman;
	}

	public void setBman(String bman) {
		this.bman = bman;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Integer getYearLoan() {
		return yearLoan;
	}

	public void setYearLoan(Integer yearLoan) {
		this.yearLoan = yearLoan;
	}

	public Integer getMonthLoan() {
		return monthLoan;
	}

	public void setMonthLoan(Integer monthLoan) {
		this.monthLoan = monthLoan;
	}

	public Integer getOveryLoan() {
		return overyLoan;
	}

	public void setOveryLoan(Integer overyLoan) {
		this.overyLoan = overyLoan;
	}

	public Integer getDayLoan() {
		return dayLoan;
	}

	public void setDayLoan(Integer dayLoan) {
		this.dayLoan = dayLoan;
	}

	public Integer getOvermLoan() {
		return overmLoan;
	}

	public void setOvermLoan(Integer overmLoan) {
		this.overmLoan = overmLoan;
	}

	public Integer getOverdLoan() {
		return overdLoan;
	}

	public void setOverdLoan(Integer overdLoan) {
		this.overdLoan = overdLoan;
	}

	public Float getUprate() {
		return uprate;
	}

	public void setUprate(Float uprate) {
		this.uprate = uprate;
	}

	public Integer getUnint() {
		return unint;
	}

	public void setUnint(Integer unint) {
		this.unint = unint;
	}

	public Integer getOveryLoan2() {
		return overyLoan2;
	}

	public void setOveryLoan2(Integer overyLoan2) {
		this.overyLoan2 = overyLoan2;
	}

	public Integer getOvermLoan2() {
		return overmLoan2;
	}

	public void setOvermLoan2(Integer overmLoan2) {
		this.overmLoan2 = overmLoan2;
	}

	public Integer getOverdLoan2() {
		return overdLoan2;
	}

	public void setOverdLoan2(Integer overdLoan2) {
		this.overdLoan2 = overdLoan2;
	}

	public Float getUprate2() {
		return uprate2;
	}

	public void setUprate2(Float uprate2) {
		this.uprate2 = uprate2;
	}

	public Integer getUnint2() {
		return unint2;
	}

	public void setUnint2(Integer unint2) {
		this.unint2 = unint2;
	}

	public Integer getLateDays() {
		return lateDays;
	}

	public void setLateDays(Integer lateDays) {
		this.lateDays = lateDays;
	}

	public Float getPprate() {
		return pprate;
	}

	public void setPprate(Float pprate) {
		this.pprate = pprate;
	}

	public Integer getPunint() {
		return punint;
	}

	public void setPunint(Integer punint) {
		this.punint = punint;
	}

	public Integer getDomonthes() {
		return domonthes;
	}

	public void setDomonthes(Integer domonthes) {
		this.domonthes = domonthes;
	}

	public String getComitMan() {
		return comitMan;
	}

	public void setComitMan(String comitMan) {
		this.comitMan = comitMan;
	}

	public Date getComitDate() {
		return comitDate;
	}

	public void setComitDate(Date comitDate) {
		this.comitDate = comitDate;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,customerId,bman,formId,yearLoan,monthLoan,overyLoan,dayLoan,overmLoan,overdLoan,uprate,unint,overyLoan2,overmLoan2,overdLoan2,uprate2,unint2,lateDays,pprate,punint,domonthes,comitMan,comitDate};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","customerId","bman","formId","yearLoan","monthLoan","overyLoan","dayLoan","overmLoan","overdLoan","uprate","unint","overyLoan2","overmLoan2","overdLoan2","uprate2","unint2","lateDays","pprate","punint","domonthes","comitMan","comitDate#yyyy-MM-dd"};
	}


}
