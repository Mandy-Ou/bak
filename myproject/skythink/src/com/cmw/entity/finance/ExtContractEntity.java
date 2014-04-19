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
 * 展期协议书
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期协议书实体",createDate="2013-09-23T00:00:00",author="pdh")
@Entity
@Table(name="fc_ExtContract")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ExtContractEntity extends IdBaseEntity {
	
	
	 @Description(remark="展期申请单ID")
	 @Column(name="extensionId" ,nullable=false )
	 private Long extensionId;

	 @Description(remark="展期协议书编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="借款合同号")
	 @Column(name="loanCode" ,nullable=false ,length=20 )
	 private String loanCode;

	 @Description(remark="担保合同号")
	 @Column(name="guaCode" ,length=20 )
	 private String guaCode;

	 @Description(remark="起始日期")
	 @Column(name="ostartDate" )
	 private Date ostartDate;

	 @Description(remark="截止日期")
	 @Column(name="oendDate" )
	 private Date oendDate;

	 @Description(remark="原贷款金额")
	 @Column(name="endAmount" ,scale=2)
	 private BigDecimal endAmount = new BigDecimal(0.00);

	 @Description(remark="展期金额")
	 @Column(name="extAmount" ,scale=2)
	 private BigDecimal extAmount = new BigDecimal(0.00);

	 @Description(remark="展期期限(年)")
	 @Column(name="yearLoan" ,nullable=false )
	 private Integer yearLoan = 0;

	 @Description(remark="展期期限(月)")
	 @Column(name="monthLoan" ,nullable=false )
	 private Integer monthLoan = 0;

	 @Description(remark="展期期限(日)")
	 @Column(name="dayLoan" ,nullable=false )
	 private Integer dayLoan = 0;

	 @Description(remark="展期起始日期")
	 @Column(name="estartDate" )
	 private Date estartDate;

	 @Description(remark="展期截止日期")
	 @Column(name="eendDate" )
	 private Date eendDate;

	 @Description(remark="还款方式")
	 @Column(name="payType" ,nullable=false ,length=50 )
	 private String payType;

	 @Description(remark="每期固定还本额")
	 @Column(name="phAmount" ,nullable=false ,scale=2)
	 private BigDecimal phAmount = new BigDecimal(0.00);

	 @Description(remark="展期利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="展期贷款利率")
	 @Column(name="rate" ,nullable=false ,scale=2)
	 private Double rate = 0.00;

	 @Description(remark="是否预收息")
	 @Column(name="isadvance" ,nullable=false )
	 private Integer isadvance = 0;

	 @Description(remark="管理费收取方式")
	 @Column(name="mgrtype" ,nullable=false )
	 private Integer mgrtype = 1;

	 @Description(remark="管理费率")
	 @Column(name="mrate" ,nullable=false ,scale=2)
	 private Double mrate = 0.00;

	 @Description(remark="息费计算方式")
	 @Column(name="ctype" ,nullable=false )
	 private Integer ctype = 1;

	 @Description(remark="借款人")
	 @Column(name="applyMan" ,length=50 )
	 private String applyMan;

	 @Description(remark="借款人签字日期")
	 @Column(name="asignDate" )
	 private Date asignDate;

	 @Description(remark="担保人")
	 @Column(name="guarantor" ,length=50 )
	 private String guarantor;

	 @Description(remark="担保人签字日期")
	 @Column(name="gsignDate" )
	 private Date gsignDate;

	 @Description(remark="贷款人")
	 @Column(name="manager" ,length=50 )
	 private String manager;

	 @Description(remark="签约日期")
	 @Column(name="signDate" ,nullable=false )
	 private Date signDate;

	 @Description(remark="其他事项")
	 @Column(name="otherRemark" ,length=500 )
	 private String otherRemark;


	public ExtContractEntity() {

	}

	
	/**
	  * 设置展期申请单ID的值
	 * @param 	extensionId	 展期申请单ID
	**/
	public void setExtensionId(Long  extensionId){
		 this.extensionId=extensionId;
 	}

	/**
	  * 获取展期申请单ID的值
	 * @return 返回展期申请单ID的值
	**/
	public Long getExtensionId(){
		 return extensionId;
 	}

	/**
	  * 设置展期协议书编号的值
	 * @param 	code	 展期协议书编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取展期协议书编号的值
	 * @return 返回展期协议书编号的值
	**/
	public String getCode(){
		 return code;
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
	  * 设置借款合同号的值
	 * @param 	loanCode	 借款合同号
	**/
	public void setLoanCode(String  loanCode){
		 this.loanCode=loanCode;
 	}

	/**
	  * 获取借款合同号的值
	 * @return 返回借款合同号的值
	**/
	public String getLoanCode(){
		 return loanCode;
 	}

	/**
	  * 设置担保合同号的值
	 * @param 	guaCode	 担保合同号
	**/
	public void setGuaCode(String  guaCode){
		 this.guaCode=guaCode;
 	}

	/**
	  * 获取担保合同号的值
	 * @return 返回担保合同号的值
	**/
	public String getGuaCode(){
		 return guaCode;
 	}

	/**
	  * 设置起始日期的值
	 * @param 	ostartDate	 起始日期
	**/
	public void setOstartDate(Date  ostartDate){
		 this.ostartDate=ostartDate;
 	}

	/**
	  * 获取起始日期的值
	 * @return 返回起始日期的值
	**/
	public Date getOstartDate(){
		 return ostartDate;
 	}

	/**
	  * 设置截止日期的值
	 * @param 	oendDate	 截止日期
	**/
	public void setOendDate(Date  oendDate){
		 this.oendDate=oendDate;
 	}

	/**
	  * 获取截止日期的值
	 * @return 返回截止日期的值
	**/
	public Date getOendDate(){
		 return oendDate;
 	}

	/**
	  * 设置原贷款金额的值
	 * @param 	endAmount	 原贷款金额
	**/
	public void setEndAmount(BigDecimal  endAmount){
		 this.endAmount=endAmount;
 	}

	/**
	  * 获取原贷款金额的值
	 * @return 返回原贷款金额的值
	**/
	public BigDecimal getEndAmount(){
		 return endAmount;
 	}

	/**
	  * 设置展期金额的值
	 * @param 	extAmount	 展期金额
	**/
	public void setExtAmount(BigDecimal  extAmount){
		 this.extAmount=extAmount;
 	}

	/**
	  * 获取展期金额的值
	 * @return 返回展期金额的值
	**/
	public BigDecimal getExtAmount(){
		 return extAmount;
 	}

	/**
	  * 设置展期期限(年)的值
	 * @param 	yearLoan	 展期期限(年)
	**/
	public void setYearLoan(Integer  yearLoan){
		 this.yearLoan=yearLoan;
 	}

	/**
	  * 获取展期期限(年)的值
	 * @return 返回展期期限(年)的值
	**/
	public Integer getYearLoan(){
		 return yearLoan;
 	}

	/**
	  * 设置展期期限(月)的值
	 * @param 	monthLoan	 展期期限(月)
	**/
	public void setMonthLoan(Integer  monthLoan){
		 this.monthLoan=monthLoan;
 	}

	/**
	  * 获取展期期限(月)的值
	 * @return 返回展期期限(月)的值
	**/
	public Integer getMonthLoan(){
		 return monthLoan;
 	}

	/**
	  * 设置展期期限(日)的值
	 * @param 	dayLoan	 展期期限(日)
	**/
	public void setDayLoan(Integer  dayLoan){
		 this.dayLoan=dayLoan;
 	}

	/**
	  * 获取展期期限(日)的值
	 * @return 返回展期期限(日)的值
	**/
	public Integer getDayLoan(){
		 return dayLoan;
 	}

	/**
	  * 设置展期起始日期的值
	 * @param 	estartDate	 展期起始日期
	**/
	public void setEstartDate(Date  estartDate){
		 this.estartDate=estartDate;
 	}

	/**
	  * 获取展期起始日期的值
	 * @return 返回展期起始日期的值
	**/
	public Date getEstartDate(){
		 return estartDate;
 	}

	/**
	  * 设置展期截止日期的值
	 * @param 	eendDate	 展期截止日期
	**/
	public void setEendDate(Date  eendDate){
		 this.eendDate=eendDate;
 	}

	/**
	  * 获取展期截止日期的值
	 * @return 返回展期截止日期的值
	**/
	public Date getEendDate(){
		 return eendDate;
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
	  * 设置每期固定还本额的值
	 * @param 	phAmount	 每期固定还本额
	**/
	public void setPhAmount(BigDecimal  phAmount){
		 this.phAmount=phAmount;
 	}

	/**
	  * 获取每期固定还本额的值
	 * @return 返回每期固定还本额的值
	**/
	public BigDecimal getPhAmount(){
		 return phAmount;
 	}

	/**
	  * 设置展期利率类型的值
	 * @param 	rateType	 展期利率类型
	**/
	public void setRateType(Integer  rateType){
		 this.rateType=rateType;
 	}

	/**
	  * 获取展期利率类型的值
	 * @return 返回展期利率类型的值
	**/
	public Integer getRateType(){
		 return rateType;
 	}

	/**
	  * 设置展期贷款利率的值
	 * @param 	rate	 展期贷款利率
	**/
	public void setRate(Double  rate){
		 this.rate=rate;
 	}

	/**
	  * 获取展期贷款利率的值
	 * @return 返回展期贷款利率的值
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
	  * 设置息费计算方式的值
	 * @param 	ctype	 息费计算方式
	**/
	public void setCtype(Integer  ctype){
		 this.ctype=ctype;
 	}

	/**
	  * 获取息费计算方式的值
	 * @return 返回息费计算方式的值
	**/
	public Integer getCtype(){
		 return ctype;
 	}

	/**
	  * 设置借款人的值
	 * @param 	applyMan	 借款人
	**/
	public void setApplyMan(String  applyMan){
		 this.applyMan=applyMan;
 	}

	/**
	  * 获取借款人的值
	 * @return 返回借款人的值
	**/
	public String getApplyMan(){
		 return applyMan;
 	}

	/**
	  * 设置借款人签字日期的值
	 * @param 	asignDate	 借款人签字日期
	**/
	public void setAsignDate(Date  asignDate){
		 this.asignDate=asignDate;
 	}

	/**
	  * 获取借款人签字日期的值
	 * @return 返回借款人签字日期的值
	**/
	public Date getAsignDate(){
		 return asignDate;
 	}

	/**
	  * 设置担保人的值
	 * @param 	guarantor	 担保人
	**/
	public void setGuarantor(String  guarantor){
		 this.guarantor=guarantor;
 	}

	/**
	  * 获取担保人的值
	 * @return 返回担保人的值
	**/
	public String getGuarantor(){
		 return guarantor;
 	}

	/**
	  * 设置担保人签字日期的值
	 * @param 	gsignDate	 担保人签字日期
	**/
	public void setGsignDate(Date  gsignDate){
		 this.gsignDate=gsignDate;
 	}

	/**
	  * 获取担保人签字日期的值
	 * @return 返回担保人签字日期的值
	**/
	public Date getGsignDate(){
		 return gsignDate;
 	}

	/**
	  * 设置贷款人的值
	 * @param 	manager	 贷款人
	**/
	public void setManager(String  manager){
		 this.manager=manager;
 	}

	/**
	  * 获取贷款人的值
	 * @return 返回贷款人的值
	**/
	public String getManager(){
		 return manager;
 	}

	/**
	  * 设置签约日期的值
	 * @param 	signDate	 签约日期
	**/
	public void setSignDate(Date  signDate){
		 this.signDate=signDate;
 	}

	/**
	  * 获取签约日期的值
	 * @return 返回签约日期的值
	**/
	public Date getSignDate(){
		 return signDate;
 	}

	/**
	  * 设置其他事项的值
	 * @param 	otherRemark	 其他事项
	**/
	public void setOtherRemark(String  otherRemark){
		 this.otherRemark=otherRemark;
 	}

	/**
	  * 获取其他事项的值
	 * @return 返回其他事项的值
	**/
	public String getOtherRemark(){
		 return otherRemark;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,extensionId,code,contractId,loanCode,guaCode,ostartDate,oendDate,endAmount,extAmount,yearLoan,monthLoan,dayLoan,estartDate,eendDate,payType,phAmount,rateType,rate,isadvance,mgrtype,mrate,ctype,applyMan,asignDate,guarantor,gsignDate,manager,signDate,otherRemark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","extensionId","code","contractId","loanCode","guaCode","ostartDate#yyyy-MM-dd","oendDate#yyyy-MM-dd","endAmount","extAmount","yearLoan","monthLoan","dayLoan","estartDate#yyyy-MM-dd","eendDate#yyyy-MM-dd","payType","phAmount","rateType","rate","isadvance","mgrtype","mrate","ctype","applyMan","asignDate","guarantor","gsignDate#yyyy-MM-dd","manager","signDate#yyyy-MM-dd","otherRemark"};
	}

}
