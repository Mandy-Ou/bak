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
 * 借款合同
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="借款合同实体",createDate="2013-01-11T00:00:00",author="pdh")
@Entity
@Table(name="fc_LoanContract")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class LoanContractEntity extends IdBaseEntity {
	

	@Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	 
	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="合同编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

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
	 @Column(name="payAccount"  ,length=50 )
	 private String payAccount;

	 @Description(remark="帐户户名")
	 @Column(name="accName" ,nullable=false ,length=50 )
	 private String accName;

	 @Description(remark="贷款金额")
	 @Column(name="appAmount" ,nullable=false ,scale=2)
	 private BigDecimal appAmount;

	 @Description(remark="贷款期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan = 0;

	 @Description(remark="贷款期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan = 0;

	 @Description(remark="贷款期限(日)")
	 @Column(name="dayLoan" ,nullable=false )
	 private Integer dayLoan = 0;

	 @Description(remark="放款日期")
	 @Column(name="payDate" ,nullable=false )
	 private Date payDate;

	 @Description(remark="结算日类型")
	 @Column(name="setdayType" ,nullable=false )
	 private Integer setdayType;
	 
	 @Description(remark="每期结算日")
	 @Column(name="payDay" )
	 private Integer payDay;

	 @Description(remark="贷款截止日期")
	 @Column(name="endDate" ,nullable=false )
	 private Date endDate;

	 @Description(remark="展期截止日期")
	 @Column(name="exteDate" ,insertable=false )
	 private Date exteDate;
	 
	 @Description(remark="还款方式")
	 @Column(name="payType" ,nullable=false )
	 private String payType;

	 @Description(remark="利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="贷款利率")
	 @Column(name="rate" ,nullable=false ,scale=2)
	 private Double rate = 0d;

	 @Description(remark="是否预收息")
	 @Column(name="isadvance" ,nullable=false )
	 private Integer isadvance = 0;

	 @Description(remark="管理费收取方式")
	 @Column(name="mgrtype" ,nullable=false )
	 private Integer mgrtype = 1;

	 @Description(remark="管理费率")
	 @Column(name="mrate" ,nullable=false ,scale=2)
	 private Double mrate = 0d;

	 @Description(remark="放款手续费率")
	 @Column(name="prate" ,nullable=false ,scale=2)
	 private Double prate = 0d;

	 @Description(remark="提前还款费率")
	 @Column(name="arate" ,nullable=false ,scale=2)
	 private Double arate = 0d;

	 @Description(remark="罚息利率")
	 @Column(name="urate" ,nullable=false ,scale=2)
	 private Double urate = 0d;

	 @Description(remark="滞纳金利率")
	 @Column(name="frate" ,nullable=false ,scale=2)
	 private Double frate = 0d;

	 @Description(remark="合同签订日期")
	 @Column(name="doDate" )
	 private Date doDate;

	 @Description(remark="合同中未涉及条款")
	 @Column(name="clause" ,length=255 )
	 private String clause;

	 @Description(remark="每期还款金额")
	 @Column(name="phAmount" ,nullable=false )
	 private Double phAmount = 0.00;
	 
	 @Description(remark="原合约放款日期")
	 @Column(name="oldpayDate" )
	 private Date oldpayDate;
	 
	 @Description(remark="原贷款截止日期")
	 @Column(name="oldendDate" )
	 private Date oldendDate;
	 
	public LoanContractEntity() {

	}
	
	
	
	/**得到原合约放款日期
	 * @return the oldpayDate
	 */
	public Date getOldpayDate() {
		return oldpayDate;
	}



	/**设置原合约放款日期
	 * @param oldpayDate the oldpayDate to set
	 */
	public void setOldpayDate(Date oldpayDate) {
		this.oldpayDate = oldpayDate;
	}



	/**得到原贷款截止日期
	 * @return the oldendDate
	 */
	public Date getOldendDate() {
		return oldendDate;
	}



	/**设置原贷款截止日期
	 * @param oldendDate the oldendDate to set
	 */
	public void setOldendDate(Date oldendDate) {
		this.oldendDate = oldendDate;
	}



	/**
	 * 获取客户类型的值
	 * @return the custType
	 */
	public Integer getCustType() {
		return custType;
	}


	/**设置客户类型的值
	 * @param custType the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}


	/**获取客户Id
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}


	/**设置客户Id的值
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	  * 设置合同编号的值
	 * @param 	code	 合同编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取合同编号的值
	 * @return 返回合同编号的值
	**/
	public String getCode(){
		 return code;
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

	/**
	  * 设置帐户户名的值
	 * @param 	accName	 帐户户名
	**/
	public void setAccName(String  accName){
		 this.accName=accName;
 	}

	/**
	  * 获取帐户户名的值
	 * @return 返回帐户户名的值
	**/
	public String getAccName(){
		 return accName;
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
	  * 设置放款日期的值
	 * @param 	payDate	 放款日期
	**/
	public void setPayDate(Date  payDate){
		 this.payDate=payDate;
 	}

	/**
	  * 获取放款日期的值
	 * @return 返回放款日期的值
	**/
	public Date getPayDate(){
		 return payDate;
 	}

	/**
	  * 获取结算日类型的值
	 * @return 返回结算日类型的值
	**/
	public Integer getSetdayType() {
		return setdayType;
	}
	
	/**
	  * 设置结算日类型的值
	 * @param 	setdayType	 结算日类型
	**/
	public void setSetdayType(Integer setdayType) {
		this.setdayType = setdayType;
	}

	/**
	  * 设置每期还款日的值
	 * @param 	payDay	 每期还款日
	**/
	public void setPayDay(Integer  payDay){
		 this.payDay=payDay;
 	}

	/**
	  * 获取每期还款日的值
	 * @return 返回每期还款日的值
	**/
	public Integer getPayDay(){
		 return payDay;
 	}

	/**
	  * 设置贷款截止日期的值
	 * @param 	endDate	 贷款截止日期
	**/
	public void setEndDate(Date  endDate){
		 this.endDate=endDate;
 	}

	/**
	  * 获取贷款截止日期的值
	 * @return 返回贷款截止日期的值
	**/
	public Date getEndDate(){
		 return endDate;
 	}
	
	
	/**
	  * 获取展期截止日期的值
	 * @return 返回展期截止日期的值
	**/
	public Date getExteDate() {
		return exteDate;
	}
	
	/**
	  * 设置展期截止日期的值
	 * @param 	exteDate	 展期截止日期
	**/
	public void setExteDate(Date exteDate) {
		this.exteDate = exteDate;
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
	  * 设置是否预收息的值
	 * @param 	isadvance	 是否预收息
	**/
	public void setIsadvance(Integer  isadvance){
		 this.isadvance=isadvance;
 	}

	/**
	  * 获取是否预收息的值
	 * @return 返回是否预收息的值
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
	  * 设置合同签订日期的值
	 * @param 	doDate	 合同签订日期
	**/
	public void setDoDate(Date  doDate){
		 this.doDate=doDate;
 	}

	/**
	  * 获取合同签订日期的值
	 * @return 返回合同签订日期的值
	**/
	public Date getDoDate(){
		 return doDate;
 	}

	/**
	  * 设置合同中未涉及条款的值
	 * @param 	clause	 合同中未涉及条款
	**/
	public void setClause(String  clause){
		 this.clause=clause;
 	}

	/**
	  * 获取合同中未涉及条款的值
	 * @return 返回合同中未涉及条款的值
	**/
	public String getClause(){
		 return clause;
 	}

	


	@Override 
	public Object[] getDatas() {
		return new Object[]{id,oldpayDate,oldendDate,custType,customerId,isenabled,formId,code,borBank,borAccount,payBank,payAccount,accName,appAmount,yearLoan,monthLoan,dayLoan,payDate,setdayType,payDay,endDate,exteDate,payType,rateType,rate,isadvance,mgrtype,mrate,prate,arate,urate,frate,doDate,clause};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","oldpayDate#yyyy-MM-dd","oldendDate#yyyy-MM-dd","custType","customerId","isenabled","formId","code","borBank","borAccount","payBank","payAccount","accName","appAmount","yearLoan","monthLoan","dayLoan","payDate#yyyy-MM-dd","setdayType","payDay","endDate#yyyy-MM-dd","exteDate#yyyy-MM-dd","payType","rateType","rate","isadvance","mgrtype","mrate","prate","arate","urate","frate","doDate#yyyy-MM-dd","clause"};
	}

}
