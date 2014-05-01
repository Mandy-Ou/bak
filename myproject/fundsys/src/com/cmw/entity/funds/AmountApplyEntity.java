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
 * 委托合同
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请",createDate="2014-01-20T00:00:00",author="李听")
@Entity
@Table(name="fu_AmountApply")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AmountApplyEntity extends IdBaseEntity {
	 @Description(remark="委托客户ID")
	 @Column(name="entrustCustId" ,nullable=false )
	 private Long entrustCustId;
	 @Description(remark="申请单编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;
	 @Description(remark="收款银行")
	 @Column(name="payBank" ,length=60 )
	 private String payBank;

	 @Description(remark="收款账号")
	 @Column(name="payAccount" ,length=30 )
	 private String payAccount;

	 @Description(remark="账户名")
	 @Column(name="accName" ,length=60 )
	 private String accName;

	 @Description(remark="委托金额")
	 @Column(name="appAmount" ,nullable=false )
	 private BigDecimal appAmount = new BigDecimal(0.00);

	 @Description(remark="委托期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan = 0;

	 @Description(remark="委托期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan = 0;

	 @Description(remark="委托生效日期")
	 @Column(name="payDate" ,nullable=false )
	 private Date payDate;

	 @Description(remark="委托失效日期")
	 @Column(name="endDate" ,nullable=false )
	 private Date endDate;

	 @Description(remark="结息日类型")
	 @Column(name="setdayType" ,nullable=false )
	 private Integer setdayType = 1;

	 @Description(remark="每月结息日")
	 @Column(name="payDay" ,nullable=false )
	 private Integer payDay;

	 @Description(remark="每月收益金额")
	 @Column(name="iamount" ,nullable=false )
	 private BigDecimal iamount = new BigDecimal(0.00);

	 @Description(remark="利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="利率")
	 @Column(name="rate" ,nullable=false ,scale=2)
	 private Double rate = 0D;

	 @Description(remark="利率单位")
	 @Column(name="unint" ,nullable=false )
	 private Integer unint;

	 @Description(remark="委托产品范围")
	 @Column(name="prange" ,nullable=false )
	 private Integer prange = 1;

	 @Description(remark="委托产品")
	 @Column(name="productsId" ,length=60 )
	 private String productsId;

	 @Description(remark="合同签订日期")
	 @Column(name="doDate" )
	 private Date doDate;

	 @Description(remark="合同中未涉及条款")
	 @Column(name="clause" ,length=255 )
	 private String clause;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=50 )
	 private String procId;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;


	public AmountApplyEntity() {

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

	 public Long getEntrustCustId() {
		return entrustCustId;
	}

	public void setEntrustCustId(Long entrustCustId) {
		this.entrustCustId = entrustCustId;
	}


	/**
	  * 设置收款银行的值
	 * @param 	payBank	 收款银行
	**/
	public void setPayBank(String  payBank){
		 this.payBank=payBank;
 	}

	/**
	  * 获取收款银行的值
	 * @return 返回收款银行的值
	**/
	public String getPayBank(){
		 return payBank;
 	}

	/**
	  * 设置收款账号的值
	 * @param 	payAccount	 收款账号
	**/
	public void setPayAccount(String  payAccount){
		 this.payAccount=payAccount;
 	}

	/**
	  * 获取收款账号的值
	 * @return 返回收款账号的值
	**/
	public String getPayAccount(){
		 return payAccount;
 	}

	/**
	  * 设置账户名的值
	 * @param 	accName	 账户名
	**/
	public void setAccName(String  accName){
		 this.accName=accName;
 	}

	/**
	  * 获取账户名的值
	 * @return 返回账户名的值
	**/
	public String getAccName(){
		 return accName;
 	}

	/**
	  * 设置委托金额的值
	 * @param 	appAmount	 委托金额
	**/
	public void setAppAmount(BigDecimal  appAmount){
		 this.appAmount=appAmount;
 	}

	/**
	  * 获取委托金额的值
	 * @return 返回委托金额的值
	**/
	public BigDecimal getAppAmount(){
		 return appAmount;
 	}

	/**
	  * 设置委托期限(年)的值
	 * @param 	yearLoan	 委托期限(年)
	**/
	public void setYearLoan(Integer  yearLoan){
		 this.yearLoan=yearLoan;
 	}

	/**
	  * 获取委托期限(年)的值
	 * @return 返回委托期限(年)的值
	**/
	public Integer getYearLoan(){
		 return yearLoan;
 	}

	/**
	  * 设置委托期限(月)的值
	 * @param 	monthLoan	 委托期限(月)
	**/
	public void setMonthLoan(Integer  monthLoan){
		 this.monthLoan=monthLoan;
 	}

	/**
	  * 获取委托期限(月)的值
	 * @return 返回委托期限(月)的值
	**/
	public Integer getMonthLoan(){
		 return monthLoan;
 	}

	/**
	  * 设置委托生效日期的值
	 * @param 	payDate	 委托生效日期
	**/
	public void setPayDate(Date  payDate){
		 this.payDate=payDate;
 	}

	/**
	  * 获取委托生效日期的值
	 * @return 返回委托生效日期的值
	**/
	public Date getPayDate(){
		 return payDate;
 	}

	/**
	  * 设置委托失效日期的值
	 * @param 	endDate	 委托失效日期
	**/
	public void setEndDate(Date  endDate){
		 this.endDate=endDate;
 	}

	/**
	  * 获取委托失效日期的值
	 * @return 返回委托失效日期的值
	**/
	public Date getEndDate(){
		 return endDate;
 	}

	/**
	  * 设置结息日类型的值
	 * @param 	setdayType	 结息日类型
	**/
	public void setSetdayType(Integer  setdayType){
		 this.setdayType=setdayType;
 	}

	/**
	  * 获取结息日类型的值
	 * @return 返回结息日类型的值
	**/
	public Integer getSetdayType(){
		 return setdayType;
 	}

	/**
	  * 设置每月结息日的值
	 * @param 	payDay	 每月结息日
	**/
	public void setPayDay(Integer  payDay){
		 this.payDay=payDay;
 	}

	/**
	  * 获取每月结息日的值
	 * @return 返回每月结息日的值
	**/
	public Integer getPayDay(){
		 return payDay;
 	}

	/**
	  * 设置每月收益金额的值
	 * @param 	iamount	 每月收益金额
	**/
	public void setIamount(BigDecimal  iamount){
		 this.iamount=iamount;
 	}

	/**
	  * 获取每月收益金额的值
	 * @return 返回每月收益金额的值
	**/
	public BigDecimal getIamount(){
		 return iamount;
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
	  * 设置利率的值
	 * @param 	rate	 利率
	**/
	public void setRate(Double  rate){
		 this.rate=rate;
 	}

	/**
	  * 获取利率的值
	 * @return 返回利率的值
	**/
	public Double getRate(){
		 return rate;
 	}

	/**
	  * 设置利率单位的值
	 * @param 	unint	 利率单位
	**/
	public void setUnint(Integer  unint){
		 this.unint=unint;
 	}

	/**
	  * 获取利率单位的值
	 * @return 返回利率单位的值
	**/
	public Integer getUnint(){
		 return unint;
 	}

	/**
	  * 设置委托产品范围的值
	 * @param 	prange	 委托产品范围
	**/
	public void setPrange(Integer  prange){
		 this.prange=prange;
 	}

	/**
	  * 获取委托产品范围的值
	 * @return 返回委托产品范围的值
	**/
	public Integer getPrange(){
		 return prange;
 	}

	/**
	  * 设置委托产品的值
	 * @param 	productsId	 委托产品
	**/
	public void setProductsId(String  productsId){
		 this.productsId=productsId;
 	}

	/**
	  * 获取委托产品的值
	 * @return 返回委托产品的值
	**/
	public String getProductsId(){
		 return productsId;
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

	/**
	  * 设置子业务流程ID的值
	 * @param 	breed	 子业务流程ID
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取子业务流程ID的值
	 * @return 返回子业务流程ID的值
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
	  * 设置状态的值
	 * @param 	status	 状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,entrustCustId,code,payBank,payAccount,accName,appAmount,yearLoan,monthLoan,payDate,endDate,setdayType,payDay,iamount,rateType,rate,unint,prange,productsId,doDate,clause,breed,procId,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","entrustCustId","code","payBank","payAccount","accName","appAmount","yearLoan","monthLoan","payDate","endDate","setdayType","payDay","iamount","rateType","rate","unint","prange","productsId","doDate","clause","breed","procId","status"};
	}

}
