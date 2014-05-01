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
 * 贷款申请
 * @author 程明卫
 * @date 2012-12-16T00:00:00
 */
@Description(remark="贷款申请实体",createDate="2012-12-16T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Apply")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ApplyEntity extends IdBaseEntity {
	
	
	 @Description(remark="项目申请编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="借款主体")
	 @Column(name="loanMain" ,nullable=false )
	 private Long loanMain;

	 @Description(remark="行业分类")
	 @Column(name="inType" ,nullable=false )
	 private Long inType;

	 @Description(remark="贷款方式")
	 @Column(name="loanType" ,nullable=false ,length=30 )
	 private String loanType;

	 @Description(remark="业务品种")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=20 )
	 private String procId;

	 @Description(remark="贷款金额")
	 @Column(name="appAmount" ,nullable=false )
	 private BigDecimal appAmount;

	 @Description(remark="期限种类")
	 @Column(name="limitType" ,nullable=false )
	 private Long limitType;

	 @Description(remark="贷款期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan;

	 @Description(remark="贷款期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan;

	 @Description(remark="贷款期限(日)")
	 @Column(name="dayLoan" ,nullable=false ,length=50 )
	 private Integer dayLoan;

	 @Description(remark="还款方式")
	 @Column(name="payType" ,nullable=false,length=20  )
	 private String payType;

	 @Description(remark="利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="贷款利率")
	 @Column(name="rate" ,nullable=false ,scale=2)
	 private Double rate;

	 @Description(remark="是否提前收息")
	 @Column(name="isadvance" )
	 private Integer isadvance;

	 @Description(remark="管理费收取方式")
	 @Column(name="mgrtype" )
	 private Integer mgrtype;

	 @Description(remark="管理费率")
	 @Column(name="mrate" ,scale=2)
	 private Double mrate;

	 @Description(remark="放款手续费率")
	 @Column(name="prate" ,scale=2)
	 private Double prate;

	 @Description(remark="提前还款费率")
	 @Column(name="arate" ,scale=2)
	 private Double arate;

	 @Description(remark="罚息利率")
	 @Column(name="urate" ,scale=2)
	 private Double urate;

	 @Description(remark="滞纳金利率")
	 @Column(name="frate" ,scale=2)
	 private Double frate;

	 @Description(remark="申请日期")
	 @Column(name="appdate" )
	 private Date appdate;

	 @Description(remark="业务经理")
	 @Column(name="manager" )
	 private Long manager;

	 @Description(remark="申请原因")
	 @Column(name="reason" ,length=500 )
	 private String reason;

	 @Description(remark="业务来源方式")
	 @Column(name="sourceType" )
	 private Long sourceType;

	 @Description(remark="业务介绍人")
	 @Column(name="referrals" ,length=50 )
	 private String referrals;

	 @Description(remark="贷款用途")
	 @Column(name="payremark" ,length=500 )
	 private String payremark;

	 @Description(remark="状态")
	 @Column(name="state" ,nullable=false )
	 private Integer state = 0;
	 
	 @Description(remark="每期还款金额")
	 @Column(name="phAmount" ,nullable=false )
	 private Double phAmount = 0.00;
	 
	 @Description(remark="总期数")
	 @Column(name="totalPhases")
	 private Integer totalPhases;
	 
	 @Description(remark="已还期数")
	 @Column(name="paydPhases")
	 private Integer paydPhases;
	 
	 @Description(remark="还款计划及资金来源")
	 @Column(name="sourceDesc" ,length=500)
	 private String sourceDesc;
	 
	 
	 @Description(remark="借款银行")
	 @Column(name="borBank" ,nullable=false ,length=50 )
	 private String borBank;

	 @Description(remark="借款帐号")
	 @Column(name="borAccount" ,nullable=false ,length=50 )
	 private String borAccount;

	 @Description(remark="还款银行")
	 @Column(name="payBank" ,length=50 )
	 private String payBank;

	 @Description(remark="还款帐号")
	 @Column(name="payAccount" ,length=50 )
	 private String payAccount;
	 
	 @Description(remark="担保人id")
	 @Column(name="GuaId" ,length=200 )
	 private String GuaId;

	 
	public ApplyEntity() {

	}
	
	
	/**过的担保人id列表
	 * @return the guaId
	 */
	public String getGuaId() {
		return GuaId;
	}


	/**设置担保人Id
	 * @param guaId the guaId to set
	 */
	public void setGuaId(String guaId) {
		GuaId = guaId;
	}


	/**
	  * 设置借款银行的值
	 * @param 	borBank	 借款银行
	**/
	public void setBorBank(String  borBank){
		 this.borBank=borBank;
	}

	/**
	  * 获取借款银行的值
	 * @return 返回借款银行的值
	**/
	public String getBorBank(){
		 return borBank;
	}

	/**
	  * 设置借款帐号的值
	 * @param 	borAccount	 借款帐号
	**/
	public void setBorAccount(String  borAccount){
		 this.borAccount=borAccount;
	}

	/**
	  * 获取借款帐号的值
	 * @return 返回借款帐号的值
	**/
	public String getBorAccount(){
		 return borAccount;
	}

	/**
	  * 设置还款银行的值
	 * @param 	payBank	 还款银行
	**/
	public void setPayBank(String  payBank){
		 this.payBank=payBank;
	}

	/**
	  * 获取还款银行的值
	 * @return 返回还款银行的值
	**/
	public String getPayBank(){
		 return payBank;
	}

	/**
	  * 设置还款帐号的值
	 * @param 	payAccount	 还款帐号
	**/
	public void setPayAccount(String  payAccount){
		 this.payAccount=payAccount;
	}

	/**
	  * 获取还款帐号的值
	 * @return 返回还款帐号的值
	**/
	public String getPayAccount(){
		 return payAccount;
	}

	
	/**获取还款计划及资金来源
	 * @return the sourceDesc
	 */
	public String getSourceDesc() {
		return sourceDesc;
	}


	/**设置还款计划及资金来源
	 * @param sourceDesc the sourceDesc to set
	 */
	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}


	/**
	 * 获取每期还款金额
	 * @return
	 */
	public Double getPhAmount() {
		return phAmount;
	}
	/**
	 * 设置每期还款金额
	 * @param phAmount
	 */
	public void setPhAmount(Double phAmount) {
		this.phAmount = phAmount;
	}


	/**
	  * 设置项目申请编号的值
	 * @param 	code	 项目申请编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取项目申请编号的值
	 * @return 返回项目申请编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置客户类型的值
	 * @param 	custType	 客户类型
	**/
	public void setCustType(Integer  custType){
		 this.custType=custType;
 	}

	/**
	  * 获取客户类型的值
	 * @return 返回客户类型的值
	**/
	public Integer getCustType(){
		 return custType;
 	}

	/**
	  * 设置客户ID的值
	 * @param 	customerId	 客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取客户ID的值
	 * @return 返回客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}

	/**
	  * 设置借款主体的值
	 * @param 	loanMain	 借款主体
	**/
	public void setLoanMain(Long  loanMain){
		 this.loanMain=loanMain;
 	}

	/**
	  * 获取借款主体的值
	 * @return 返回借款主体的值
	**/
	public Long getLoanMain(){
		 return loanMain;
 	}

	/**
	  * 设置行业分类的值
	 * @param 	inType	 行业分类
	**/
	public void setInType(Long  inType){
		 this.inType=inType;
 	}

	/**
	  * 获取行业分类的值
	 * @return 返回行业分类的值
	**/
	public Long getInType(){
		 return inType;
 	}

	/**
	  * 设置贷款方式的值
	 * @param 	loanType	 贷款方式
	**/
	public void setLoanType(String  loanType){
		 this.loanType=loanType;
 	}

	/**
	  * 获取贷款方式的值
	 * @return 返回贷款方式的值
	**/
	public String getLoanType(){
		 return loanType;
 	}

	/**
	  * 设置业务品种的值
	 * @param 	breed	 业务品种
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取业务品种的值
	 * @return 返回业务品种的值
	**/
	public Long getBreed(){
		 return breed;
 	}

	/**
	  * 设置流程实例ID的值
	 * @param 	procId	 流程实例ID
	**/
	public void setProcId(String  procId){
		 this.procId=procId;
 	}

	/**
	  * 获取流程实例ID的值
	 * @return 返回流程实例ID的值
	**/
	public String getProcId(){
		 return procId;
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
	  * 设置期限种类的值
	 * @param 	limitType	 期限种类
	**/
	public void setLimitType(Long  limitType){
		 this.limitType=limitType;
 	}

	/**
	  * 获取期限种类的值
	 * @return 返回期限种类的值
	**/
	public Long getLimitType(){
		 return limitType;
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
	  * 设置还款方式的值
	 * @param 	payType	 还款方式
	**/
	public void setPayType(String  payType){
		 this.payType=payType;
 	}

	/**
	  * 获取还款方式的值
	 * @return 返回还款方式的值
	**/
	public String getPayType(){
		 return payType;
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

	/**
	  * 设置是否提前收息的值
	 * @param 	isadvance	 是否提前收息
	**/
	public void setIsadvance(Integer  isadvance){
		 this.isadvance=isadvance;
 	}

	/**
	  * 获取是否提前收息的值
	 * @return 返回是否提前收息的值
	**/
	public Integer getIsadvance(){
		 return isadvance;
 	}

	/**
	  * 设置管理费收取方式的值
	 * @param 	mgrtype	 管理费收取方式
	**/
	public void setMgrtype(Integer  mgrtype){
		 this.mgrtype=mgrtype;
 	}

	/**
	  * 获取管理费收取方式的值
	 * @return 返回管理费收取方式的值
	**/
	public Integer getMgrtype(){
		 return mgrtype;
 	}

	/**
	  * 设置管理费率的值
	 * @param 	mrate	 管理费率
	**/
	public void setMrate(Double  mrate){
		 this.mrate=mrate;
 	}

	/**
	  * 获取管理费率的值
	 * @return 返回管理费率的值
	**/
	public Double getMrate(){
		 return mrate;
 	}

	/**
	  * 设置放款手续费率的值
	 * @param 	prate	 放款手续费率
	**/
	public void setPrate(Double  prate){
		 this.prate=prate;
 	}

	/**
	  * 获取放款手续费率的值
	 * @return 返回放款手续费率的值
	**/
	public Double getPrate(){
		 return prate;
 	}

	/**
	  * 设置提前还款费率的值
	 * @param 	arate	 提前还款费率
	**/
	public void setArate(Double  arate){
		 this.arate=arate;
 	}

	/**
	  * 获取提前还款费率的值
	 * @return 返回提前还款费率的值
	**/
	public Double getArate(){
		 return arate;
 	}

	/**
	  * 设置罚息利率的值
	 * @param 	urate	 罚息利率
	**/
	public void setUrate(Double  urate){
		 this.urate=urate;
 	}

	/**
	  * 获取罚息利率的值
	 * @return 返回罚息利率的值
	**/
	public Double getUrate(){
		 return urate;
 	}

	/**
	  * 设置滞纳金利率的值
	 * @param 	frate	 滞纳金利率
	**/
	public void setFrate(Double  frate){
		 this.frate=frate;
 	}

	/**
	  * 获取滞纳金利率的值
	 * @return 返回滞纳金利率的值
	**/
	public Double getFrate(){
		 return frate;
 	}

	/**
	  * 设置申请日期的值
	 * @param 	appdate	 申请日期
	**/
	public void setAppdate(Date  appdate){
		 this.appdate=appdate;
 	}

	/**
	  * 获取申请日期的值
	 * @return 返回申请日期的值
	**/
	public Date getAppdate(){
		 return appdate;
 	}

	/**
	  * 设置业务经理的值
	 * @param 	manager	 业务经理
	**/
	public void setManager(Long  manager){
		 this.manager=manager;
 	}

	/**
	  * 获取业务经理的值
	 * @return 返回业务经理的值
	**/
	public Long getManager(){
		 return manager;
 	}

	/**
	  * 设置申请原因
	 * @param 	reason	申请原因
	**/
	public void setReason(String  reason){
		 this.reason=reason;
 	}

	/**
	  * 获取申请原因
	 * @return 返回申请原因
	**/
	public String getReason(){
		 return reason;
 	}

	/**
	  * 设置业务来源方式的值
	 * @param 	sourceType	 业务来源方式
	**/
	public void setSourceType(Long  sourceType){
		 this.sourceType=sourceType;
 	}

	/**
	  * 获取业务来源方式的值
	 * @return 返回业务来源方式的值
	**/
	public Long getSourceType(){
		 return sourceType;
 	}

	/**
	  * 设置业务介绍人的值
	 * @param 	referrals	 业务介绍人
	**/
	public void setReferrals(String  referrals){
		 this.referrals=referrals;
 	}

	/**
	  * 获取业务介绍人的值
	 * @return 返回业务介绍人的值
	**/
	public String getReferrals(){
		 return referrals;
 	}

	/**
	  * 设置贷款用途
	 * @param 	payremark	 贷款用途
	**/
	public void setPayremark(String  payremark){
		 this.payremark=payremark;
 	}

	/**
	  * 获取贷款用途
	 * @return 返回贷款用途
	**/
	public String getPayremark(){
		 return payremark;
 	}


	public Integer getTotalPhases() {
		return totalPhases;
	}

	public void setTotalPhases(Integer totalPhases) {
		this.totalPhases = totalPhases;
	}

	public Integer getPaydPhases() {
		return paydPhases;
	}

	public void setPaydPhases(Integer paydPhases) {
		this.paydPhases = paydPhases;
	}
	
	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{GuaId,id,isenabled,borBank,borAccount,payBank,payAccount,sourceDesc,code,custType,customerId,loanMain,inType,loanType,breed,procId,appAmount,limitType,yearLoan,monthLoan,dayLoan,payType,rateType,rate,isadvance,mgrtype,mrate,prate,arate,urate,frate,appdate,manager,reason,sourceType,referrals,payremark,state,totalPhases,paydPhases};
	}

	@Override
	public String[] getFields() {
		return new String[]{"GuaId","id","isenabled","borBank","borAccount","payBank","payAccount","sourceDesc","code","custType","customerId","loanMain","inType","loanType","breed","procId","appAmount","limitType","yearLoan","monthLoan","dayLoan","payType","rateType","rate","isadvance","mgrtype","mrate","prate","arate","urate","frate","appdate","manager","reason","sourceType","referrals","payremark","state","totalPhases","paydPhases"};
	}

}
