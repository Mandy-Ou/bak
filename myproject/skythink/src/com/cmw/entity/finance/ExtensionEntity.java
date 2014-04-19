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
 * 展期申请
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="展期申请实体",createDate="2013-09-08T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Extension")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ExtensionEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=20 )
	 private String procId;

	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="借款合同号")
	 @Column(name="loanCode" ,nullable=false ,length=20 )
	 private String loanCode;

	 @Description(remark="担保合同号")
	 @Column(name="guaCode" ,length=20 )
	 private String guaCode;

	 @Description(remark="原借款起始日期")
	 @Column(name="ostartDate" )
	 private Date ostartDate;

	 @Description(remark="原借款截止日期")
	 @Column(name="oendDate" )
	 private Date oendDate;

	 @Description(remark="到期贷款金额")
	 @Column(name="endAmount" ,nullable=false ,scale=2)
	 private BigDecimal endAmount = new BigDecimal("0");

	 @Description(remark="展期金额")
	 @Column(name="extAmount" ,nullable=false ,scale=2)
	 private BigDecimal extAmount = new BigDecimal("0");

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
	 @Column(name="payType" ,nullable=false ,length=20 )
	 private String payType;

	 @Description(remark="每期固定还本额")
	 @Column(name="phAmount" ,nullable=false ,scale=2)
	 private BigDecimal phAmount = new BigDecimal("0");

	 @Description(remark="展期利率类型")
	 @Column(name="rateType" ,nullable=false )
	 private Integer rateType;

	 @Description(remark="展期贷款利率")
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

	 @Description(remark="息费计算方式")
	 @Column(name="ctype" ,nullable=false )
	 private Integer ctype = 1;

	 @Description(remark="展期原因")
	 @Column(name="extReason" ,length=1000 )
	 private String extReason;

	 @Description(remark="还款来源")
	 @Column(name="paySource" ,length=255 )
	 private String paySource;

	 @Description(remark="申请人")
	 @Column(name="applyMan" ,length=100 )
	 private String applyMan;

	 @Description(remark="申请日期")
	 @Column(name="applyDate" )
	 private Date applyDate;

	 @Description(remark="收到申请书日期")
	 @Column(name="comeDate" )
	 private Date comeDate;

	 @Description(remark="经办人")
	 @Column(name="managerId" )
	 private Long managerId;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="展期内部利率类型")
	 @Column(name="extInRateType" )
	 private Integer extInRateType;
	 
	 @Description(remark="展期内部利率")
	 @Column(name="extInRate" ,nullable=false )
	 private Double extInRate = 0d;
	 
	 
	public ExtensionEntity() {

	}
	
	/** 返回 展期内部利率类型
	 * @return the extInRateType
	 */
	public Integer getExtInRateType() {
		return extInRateType;
	}


	/**设置展期内部利率类型
	 * @param extInRateType the extInRateType to set
	 */
	public void setExtInRateType(Integer extInRateType) {
		this.extInRateType = extInRateType;
	}

	/**
	 * 返回展期内部利率
	 * @return the extInRate
	 */
	public Double getExtInRate() {
		return extInRate;
	}


	/**设置展期内部利率
	 * @param extInRate the extInRate to set
	 */
	public void setExtInRate(Double extInRate) {
		this.extInRate = extInRate;
	}


	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
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
	  * 设置原借款起始日期的值
	 * @param 	ostartDate	 原借款起始日期
	**/
	public void setOstartDate(Date  ostartDate){
		 this.ostartDate=ostartDate;
 	}

	/**
	  * 获取原借款起始日期的值
	 * @return 返回原借款起始日期的值
	**/
	public Date getOstartDate(){
		 return ostartDate;
 	}

	/**
	  * 设置原借款截止日期的值
	 * @param 	oendDate	 原借款截止日期
	**/
	public void setOendDate(Date  oendDate){
		 this.oendDate=oendDate;
 	}

	/**
	  * 获取原借款截止日期的值
	 * @return 返回原借款截止日期的值
	**/
	public Date getOendDate(){
		 return oendDate;
 	}

	/**
	  * 设置到期贷款金额的值
	 * @param 	endAmount	 到期贷款金额
	**/
	public void setEndAmount(BigDecimal  endAmount){
		 this.endAmount=endAmount;
 	}

	/**
	  * 获取到期贷款金额的值
	 * @return 返回到期贷款金额的值
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
	  * 设置展期原因的值
	 * @param 	extReason	 展期原因
	**/
	public void setExtReason(String  extReason){
		 this.extReason=extReason;
 	}

	/**
	  * 获取展期原因的值
	 * @return 返回展期原因的值
	**/
	public String getExtReason(){
		 return extReason;
 	}

	/**
	  * 设置还款来源的值
	 * @param 	paySource	 还款来源
	**/
	public void setPaySource(String  paySource){
		 this.paySource=paySource;
 	}

	/**
	  * 获取还款来源的值
	 * @return 返回还款来源的值
	**/
	public String getPaySource(){
		 return paySource;
 	}

	/**
	  * 设置申请人的值
	 * @param 	applyMan	 申请人
	**/
	public void setApplyMan(String  applyMan){
		 this.applyMan=applyMan;
 	}

	/**
	  * 获取申请人的值
	 * @return 返回申请人的值
	**/
	public String getApplyMan(){
		 return applyMan;
 	}

	/**
	  * 设置申请日期的值
	 * @param 	applyDate	 申请日期
	**/
	public void setApplyDate(Date  applyDate){
		 this.applyDate=applyDate;
 	}

	/**
	  * 获取申请日期的值
	 * @return 返回申请日期的值
	**/
	public Date getApplyDate(){
		 return applyDate;
 	}

	/**
	  * 设置收到申请书日期的值
	 * @param 	comeDate	 收到申请书日期
	**/
	public void setComeDate(Date  comeDate){
		 this.comeDate=comeDate;
 	}

	/**
	  * 获取收到申请书日期的值
	 * @return 返回收到申请书日期的值
	**/
	public Date getComeDate(){
		 return comeDate;
 	}

	/**
	  * 设置经办人的值
	 * @param 	managerId	 经办人
	**/
	public void setManagerId(Long  managerId){
		 this.managerId=managerId;
 	}

	/**
	  * 获取经办人的值
	 * @return 返回经办人的值
	**/
	public Long getManagerId(){
		 return managerId;
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
		return new Object[]{extInRateType,extInRate,id,code,breed,procId,contractId,loanCode,guaCode,ostartDate,oendDate,endAmount,extAmount,yearLoan,monthLoan,dayLoan,estartDate,eendDate,payType,phAmount,rateType,rate,isadvance,mgrtype,mrate,ctype,extReason,paySource,applyMan,applyDate,comeDate,managerId,status,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"extInRateType","extInRate","id","code","breed","procId","contractId","loanCode","guaCode","ostartDate","oendDate","endAmount","extAmount","yearLoan","monthLoan","dayLoan","estartDate","eendDate","payType","phAmount","rateType","rate","isadvance","mgrtype","mrate","ctype","extReason","paySource","applyMan","applyDate","comeDate","managerId","status","isenabled"};
	}

}
