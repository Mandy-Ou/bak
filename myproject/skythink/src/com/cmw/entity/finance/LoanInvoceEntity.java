package com.cmw.entity.finance;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 放款单
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款单实体",createDate="2013-01-15T00:00:00",author="程明卫")
@Entity
@Table(name="fc_LoanInvoce")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class LoanInvoceEntity extends IdBaseEntity {
	
	 
	 @Description(remark="放款单编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="收款人名称")
	 @Column(name="payName" ,nullable=false ,length=50 )
	 private String payName;

	 @Description(remark="开户行")
	 @Column(name="regBank" ,length=50 )
	 private String regBank;

	 @Description(remark="收款帐号")
	 @Column(name="account" ,length=30 )
	 private String account;

	 @Description(remark="放款金额")
	 @Column(name="payAmount" ,nullable=false )
	 private BigDecimal payAmount = new BigDecimal(0);

	 @Description(remark="放款手续费率")
	 @Column(name="prate" ,nullable=false ,scale=2)
	 private Double prate = 0d;

	 @Description(remark="出纳人员")
	 @Column(name="cashier" ,nullable=false )
	 private Long cashier;

	 @Description(remark="放款日期")
	 @Column(name="payDate" )
	 private Date payDate;
	 
	 @Description(remark="实际放款日期")
	 @Column(name="realDate" )
	 private Date realDate;

	 @Description(remark="放款帐号ID")
	 @Column(name="accountId" )
	 private Long accountId;

	 @Description(remark="凭证单编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;

	 @Description(remark="审批状态")
	 @Column(name="auditState" ,nullable=false )
	 private Integer auditState=0;

	 @Description(remark="放款状态")
	 @Column(name="state" ,nullable=false )
	 private Integer state=0;
	 
	 @Description(remark="计息起始日方式")
	 @Column(name="startWay")
	 private Integer startWay=BussStateConstant.LOANINVOCE_STARTWAY_1;
	 
	 @Description(remark="未放款金额")
	 @Column(name="unAmount"  )
	 private BigDecimal unAmount = new BigDecimal(0);
	 
	 @Description(remark="预收管理费月数")
	 @Column(name="ysMatMonth"  )
	 private Integer ysMatMonth;
	 
	 @Description(remark="预收管理费金额")
	 @Column(name="ysMat"  )
	 private BigDecimal ysMat = new BigDecimal(0);
	 
	 @Description(remark="预收利息月数")
	 @Column(name="ysRatMonth"  )
	 private Integer ysRatMonth;
	 
	 @Description(remark="预收利息金额")
	 @Column(name="ysRat"  )
	 private BigDecimal ysRat = new BigDecimal(0);
	 
	 @Description(remark="是否预收")
	 @Column(name="isNotys"  )
	 private Integer isNotys = 0;
	 
	public LoanInvoceEntity() {

	}
	
	
	
	
	
	/**返回是否预收的判断
	 * @return the isNotys
	 */
	public Integer getIsNotys() {
		return isNotys;
	}

	/** 设置是否预收
	 * @param isNotys the isNotys to set
	 */
	public void setIsNotys(Integer isNotys) {
		this.isNotys = isNotys;
	}

	/**返回预收管理费月数
	 * @return the ysMatMonth
	 */
	public Integer getYsMatMonth() {
		return ysMatMonth;
	}





	/**设置预收管理费月数
	 * @param ysMatMonth the ysMatMonth to set
	 */
	public void setYsMatMonth(Integer ysMatMonth) {
		this.ysMatMonth = ysMatMonth;
	}





	/**返回预收管理费金额
	 * @return the ysMat
	 */
	public BigDecimal getYsMat() {
		return ysMat;
	}





	/**设置预收管理费金额
	 * @param ysMat the ysMat to set
	 */
	public void setYsMat(BigDecimal ysMat) {
		this.ysMat = ysMat;
	}





	/**返回预收利息月数
	 * @return the ysRatMonth
	 */
	public Integer getYsRatMonth() {
		return ysRatMonth;
	}





	/**设置预收利息月数
	 * @param ysRatMonth the ysRatMonth to set
	 */
	public void setYsRatMonth(Integer ysRatMonth) {
		this.ysRatMonth = ysRatMonth;
	}





	/**返回预收利息金额
	 * @return the ysRat
	 */
	public BigDecimal getYsRat() {
		return ysRat;
	}





	/**设置预收利息金额
	 * @param ysRat the ysRat to set
	 */
	public void setYsRat(BigDecimal ysRat) {
		this.ysRat = ysRat;
	}





	/**获得未放款金额
	 * @return the unAmount
	 */
	public BigDecimal getUnAmount() {
		return unAmount;
	}


	/**设置为放款金额
	 * @param unAmount the unAmount to set
	 */
	public void setUnAmount(BigDecimal unAmount) {
		this.unAmount = unAmount;
	}


	/**
	  * 设置放款单编号的值
	 * @param 	code	 放款单编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取放款单编号的值
	 * @return 返回放款单编号的值
	**/
	public String getCode(){
		 return code;
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
	  * 设置借款合同ID的值
	 * @param 	contractId	 借款合同ID
	**/
	public void setContractId(Long  contractId){
		 this.contractId=contractId;
 	}

	/**
	  * 获取借款合同ID的值
	 * @return 返回借款合同ID的值
	**/
	public Long getContractId(){
		 return contractId;
 	}

	/**
	  * 设置收款人名称的值
	 * @param 	payName	 收款人名称
	**/
	public void setPayName(String  payName){
		 this.payName=payName;
 	}

	/**
	  * 获取收款人名称的值
	 * @return 返回收款人名称的值
	**/
	public String getPayName(){
		 return payName;
 	}

	/**
	  * 设置开户行的值
	 * @param 	regBank	 开户行
	**/
	public void setRegBank(String  regBank){
		 this.regBank=regBank;
 	}

	/**
	  * 获取开户行的值
	 * @return 返回开户行的值
	**/
	public String getRegBank(){
		 return regBank;
 	}

	/**
	  * 设置收款帐号的值
	 * @param 	account	 收款帐号
	**/
	public void setAccount(String  account){
		 this.account=account;
 	}

	/**
	  * 获取收款帐号的值
	 * @return 返回收款帐号的值
	**/
	public String getAccount(){
		 return account;
 	}

	/**
	  * 设置放款金额的值
	 * @param 	payAmount	 放款金额
	**/
	public void setPayAmount(BigDecimal  payAmount){
		 this.payAmount=payAmount;
 	}

	/**
	  * 获取放款金额的值
	 * @return 返回放款金额的值
	**/
	public BigDecimal getPayAmount(){
		 return payAmount;
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
	  * 设置出纳人员的值
	 * @param 	cashier	 出纳人员
	**/
	public void setCashier(Long  cashier){
		 this.cashier=cashier;
 	}

	/**
	  * 获取出纳人员的值
	 * @return 返回出纳人员的值
	**/
	public Long getCashier(){
		 return cashier;
 	}

	
	
	public Date getPayDate() {
		return payDate;
	}


	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}


	/**
	  * 设置实际放款日期的值
	 * @param 	realDate	 实际放款日期
	**/
	public void setRealDate(Date  realDate){
		 this.realDate=realDate;
 	}

	/**
	  * 获取实际放款日期的值
	 * @return 返回实际放款日期的值
	**/
	public Date getRealDate(){
		 return realDate;
 	}

	/**
	  * 设置放款帐号ID的值
	 * @param 	accountId	 放款帐号ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取放款帐号ID的值
	 * @return 返回放款帐号ID的值
	**/
	public Long getAccountId(){
		 return accountId;
 	}

	/**
	  * 设置凭证单编号的值
	 * @param 	fcnumber	 凭证单编号
	**/
	public void setFcnumber(String  fcnumber){
		 this.fcnumber=fcnumber;
 	}

	/**
	  * 获取凭证单编号的值
	 * @return 返回凭证单编号的值
	**/
	public String getFcnumber(){
		 return fcnumber;
 	}

	/**
	  * 设置审批状态的值
	 * @param 	auditState	 审批状态
	**/
	public void setAuditState(Integer  auditState){
		 this.auditState=auditState;
 	}

	/**
	  * 获取审批状态的值
	 * @return 返回审批状态的值
	**/
	public Integer getAuditState(){
		 return auditState;
 	}

	/**
	  * 设置放款状态的值
	 * @param 	state	 放款状态
	**/
	public void setState(Integer  state){
		 this.state=state;
 	}

	/**
	  * 获取放款状态的值
	 * @return 返回放款状态的值
	**/
	public Integer getState(){
		 return state;
 	}

	/**
	  * 获取计息起始日方式的值
	 * @return 返回计息起始日方式的值
	**/
	public Integer getStartWay() {
		return startWay;
	}

	/**
	  * 设置计息起始日方式的值
	 * @param 	startWay	 计息起始日方式
	**/
	public void setStartWay(Integer startWay) {
		this.startWay = startWay;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,formId,contractId,payName,regBank,account,
				payAmount,prate,cashier,payDate,realDate,accountId,
				fcnumber,auditState,state,unAmount,startWay,ysMat,
				ysMatMonth,ysRat,ysRatMonth,isNotys
				};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","formId","contractId","payName","regBank","account",
				"payAmount","prate","cashier","payDate#yyyy-MM-dd","realDate#yyyy-MM-dd","accountId",
				"fcnumber","auditState","state","unAmount","startWay",
				"ysMat","ysMatMonth","ysRat","ysRatMonth","isNotys"
				};
	}

}
