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
 * 质押合同
 * @author pdh
 * @date 2013-02-02T00:00:00
 */
@Description(remark="质押合同实体",createDate="2013-02-02T00:00:00",author="pdh")
@Entity
@Table(name="fc_PleContract")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PleContractEntity extends IdBaseEntity {
	
	@Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Long custType;

	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	 
	 @Description(remark="贷款申请单ID")
	 @Column(name="formId" )
	 private Long formId;

	 @Description(remark="借款合同ID")
	 @Column(name="loanConId" )
	 private Long loanConId;

	 @Description(remark="借款合同号")
	 @Column(name="borrCode" ,length=30 )
	 private String borrCode;

	 @Description(remark="借款人")
	 @Column(name="assMan" ,length=50 )
	 private String assMan;

	 @Description(remark="贷款金额")
	 @Column(name="appAmount" ,scale=2)
	 private BigDecimal appAmount;

	 @Description(remark="贷款利率")
	 @Column(name="rate" ,scale=2)
	 private Double rate;

	 @Description(remark="贷款起始日期")
	 @Column(name="startDate" )
	 private Date startDate;

	 @Description(remark="贷款截止日期")
	 @Column(name="endDate" )
	 private Date endDate;

	 @Description(remark="质押合同编号")
	 @Column(name="code" ,length=20 )
	 private String code;

	 @Description(remark="合同签订日期")
	 @Column(name="sdate" )
	 private Date sdate;

	 @Description(remark="权属编号")
	 @Column(name="ownerNums" ,length=30 )
	 private String ownerNums;

	 @Description(remark="质押物ID列表")
	 @Column(name="pleIds" ,length=150 )
	 private String pleIds;

	 @Description(remark="合同中未涉及条款")
	 @Column(name="clause" ,length=255 )
	 private String clause;

	 
	 @Description(remark="确认价值金额")
	 @Column(name="conAmount" ,nullable=false )
	 private String conAmount;
	 
	public PleContractEntity() {

	}
	

	/**获取确认价值金额
	 * @return the conAmount
	 */
	public String getConAmount() {
		return conAmount;
	}



	/**设置确认价值金额
	 * @param conAmount the conAmount to set
	 */
	public void setConAmount(String conAmount) {
		this.conAmount = conAmount;
	}

	
	/**
	 * 获取客户类型的值
	 * @return the custType
	 */
	public Long getCustType() {
		return custType;
	}


	/**设置客户类型的值
	 * @param custType the custType to set
	 */
	public void setCustType(Long custType) {
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
	 * @param 	loanConId	 借款合同ID
	**/
	public void setLoanConId(Long  loanConId){
		 this.loanConId=loanConId;
 	}

	/**
	  * 获取借款合同ID的值
	 * @return 返回借款合同ID的值
	**/
	public Long getLoanConId(){
		 return loanConId;
 	}

	/**
	  * 设置借款合同号的值
	 * @param 	borrCode	 借款合同号
	**/
	public void setBorrCode(String  borrCode){
		 this.borrCode=borrCode;
 	}

	/**
	  * 获取借款合同号的值
	 * @return 返回借款合同号的值
	**/
	public String getBorrCode(){
		 return borrCode;
 	}

	/**
	  * 设置借款人的值
	 * @param 	assMan	 借款人
	**/
	public void setAssMan(String  assMan){
		 this.assMan=assMan;
 	}

	/**
	  * 获取借款人的值
	 * @return 返回借款人的值
	**/
	public String getAssMan(){
		 return assMan;
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
	  * 设置贷款起始日期的值
	 * @param 	startDate	 贷款起始日期
	**/
	public void setStartDate(Date  startDate){
		 this.startDate=startDate;
 	}

	/**
	  * 获取贷款起始日期的值
	 * @return 返回贷款起始日期的值
	**/
	public Date getStartDate(){
		 return startDate;
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
	  * 设置质押合同编号的值
	 * @param 	code	 质押合同编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取质押合同编号的值
	 * @return 返回质押合同编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置合同签订日期的值
	 * @param 	sdate	 合同签订日期
	**/
	public void setSdate(Date  sdate){
		 this.sdate=sdate;
 	}

	/**
	  * 获取合同签订日期的值
	 * @return 返回合同签订日期的值
	**/
	public Date getSdate(){
		 return sdate;
 	}

	/**
	  * 设置权属编号的值
	 * @param 	ownerNums	 权属编号
	**/
	public void setOwnerNums(String  ownerNums){
		 this.ownerNums=ownerNums;
 	}

	/**
	  * 获取权属编号的值
	 * @return 返回权属编号的值
	**/
	public String getOwnerNums(){
		 return ownerNums;
 	}

	/**
	  * 设置质押物ID列表的值
	 * @param 	pleIds	 质押物ID列表
	**/
	public void setPleIds(String  pleIds){
		 this.pleIds=pleIds;
 	}

	/**
	  * 获取质押物ID列表的值
	 * @return 返回质押物ID列表的值
	**/
	public String getPleIds(){
		 return pleIds;
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
		return new Object[]{custType,customerId,id,isenabled,formId,loanConId,borrCode,assMan,appAmount,rate,startDate,endDate,code,sdate,ownerNums,pleIds,clause,conAmount,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"custType","customerId","id","isenabled","formId","loanConId","borrCode","assMan","appAmount","rate","startDate#yyyy-MM-dd","endDate#yyyy-MM-dd","code","sdate#yyyy-MM-dd","ownerNums","pleIds","clause","conAmount","remark"};
	}

}
