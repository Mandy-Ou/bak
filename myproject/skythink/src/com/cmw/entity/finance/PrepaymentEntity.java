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
 * 提前还款申请
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */

@Description(remark="提前还款申请实体",createDate="2013-09-11T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Prepayment")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PrepaymentEntity extends IdBaseEntity {
	
	
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

	 @Description(remark="提前还款类别")
	 @Column(name="ptype" ,nullable=false )
	 private Integer ptype;

	 @Description(remark="预计提前还款日期")
	 @Column(name="adDate" ,nullable=false )
	 private Date adDate;

	 @Description(remark="是否退息费")
	 @Column(name="isretreat" ,nullable=false )
	 private Integer isretreat = 1;

	 @Description(remark="退息费金额")
	 @Column(name="imamount" ,nullable=false )
	 private BigDecimal imamount = new BigDecimal("0");

	 @Description(remark="预计提前还款额")
	 @Column(name="adamount" ,nullable=false )
	 private BigDecimal adamount = new BigDecimal("0");

	 @Description(remark="处理方式")
	 @Column(name="treatment" )
	 private Integer treatment = 1;

	 @Description(remark="申请人")
	 @Column(name="appMan" ,nullable=false ,length=30 )
	 private String appMan;

	 @Description(remark="申请日期")
	 @Column(name="appDate" ,nullable=false )
	 private Date appDate;

	 @Description(remark="联系电话")
	 @Column(name="tel" ,length=30 )
	 private String tel;

	 @Description(remark="提前还款原因")
	 @Column(name="reason" ,nullable=false ,length=1000 )
	 private String reason;

	 @Description(remark="经办人")
	 @Column(name="managerId" ,nullable=false )
	 private Long managerId;

	 @Description(remark="办理日期")
	 @Column(name="mgrDate" ,nullable=false )
	 private Date mgrDate;

	 @Description(remark="实际提前还款日期")
	 @Column(name="predDate" )
	 private Date predDate;

	 @Description(remark="手续费率")
	 @Column(name="frate" ,scale=2)
	 private Double frate = 0d;

	 @Description(remark="提前还款手续费")
	 @Column(name="freeamount" )
	 private BigDecimal freeamount = new BigDecimal("0");
	 
	 @Description(remark="已收提前还款手续费")
	 @Column(name="yfreeamount" )
	 private BigDecimal yfreeamount = new BigDecimal("0");

	 @Description(remark="还款计划ID")
	 @Column(name="payplanId" ,nullable=false )
	 private Long payplanId;

	 @Description(remark="提前还款额合计")
	 @Column(name="totalAmount" ,nullable=false )
	 private BigDecimal totalAmount = new BigDecimal("0");

	 @Description(remark="已收提前还款额合计")
	 @Column(name="ytotalAmount")
	 private BigDecimal ytotalAmount = new BigDecimal("0");
	 
	 @Description(remark="财务复核人")
	 @Column(name="reviewer" )
	 private Long reviewer;

	 @Description(remark="复核日期")
	 @Column(name="reviewDate" )
	 private Date reviewDate;

	 @Description(remark="通知方式")
	 @Column(name="notifyType" ,length=10 )
	 private String notifyType;

	 @Description(remark="通知内容")
	 @Column(name="notifyContent" ,length=255 )
	 private String notifyContent;

	 @Description(remark="通知人")
	 @Column(name="notifyMan" )
	 private Long notifyMan;

	 @Description(remark="通知日期")
	 @Column(name="notifyDate" )
	 private Date notifyDate;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="收款状态")
	 @Column(name="xstatus" )
	 private Integer xstatus = 0;

	 @Description(remark="豁免状态")
	 @Column(name="exempt" )
	 private Integer exempt = 0;
	 
	 @Description(remark="收款帐号ID")
	 @Column(name="accountId" )
	 private Long accountId;
	 
	 @Description(remark="豁免/返还利息")
	 @Column(name="trinterAmount")
	 private BigDecimal trinterAmount= new BigDecimal(0.00);
	 
	 
	 @Description(remark="豁免返还管理费")
	 @Column(name="trmgrAmount")
	 private BigDecimal trmgrAmount= new BigDecimal(0.00);
	 
	 @Description(remark="豁免返还罚息")
	 @Column(name="trpenAmount")
	 private BigDecimal trpenAmount= new BigDecimal(0.00);
	 
	 
	 @Description(remark="豁免返还滞纳金")
	 @Column(name="trdelAmount")
	 private BigDecimal trdelAmount= new BigDecimal(0.00);
	 
	 @Description(remark="豁免返还手续费")
	 @Column(name="trfreeAmount")
	 private BigDecimal trfreeAmount= new BigDecimal(0.00);
	 
	 
	
	
	public BigDecimal getTrinterAmount() {
		return trinterAmount;
	}


	public void setTrinterAmount(BigDecimal trinterAmount) {
		this.trinterAmount = trinterAmount;
	}


	public BigDecimal getTrmgrAmount() {
		return trmgrAmount;
	}


	public void setTrmgrAmount(BigDecimal trmgrAmount) {
		this.trmgrAmount = trmgrAmount;
	}


	public BigDecimal getTrpenAmount() {
		return trpenAmount;
	}


	public void setTrpenAmount(BigDecimal trpenAmount) {
		this.trpenAmount = trpenAmount;
	}


	public BigDecimal getTrdelAmount() {
		return trdelAmount;
	}


	public void setTrdelAmount(BigDecimal trdelAmount) {
		this.trdelAmount = trdelAmount;
	}


	public BigDecimal getTrfreeAmount() {
		return trfreeAmount;
	}


	public void setTrfreeAmount(BigDecimal trfreeAmount) {
		this.trfreeAmount = trfreeAmount;
	}


	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}


	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
	  * 设置提前还款类别的值
	 * @param 	ptype	 提前还款类别
	**/
	public void setPtype(Integer  ptype){
		 this.ptype=ptype;
 	}

	/**
	  * 获取提前还款类别的值
	 * @return 返回提前还款类别的值
	**/
	public Integer getPtype(){
		 return ptype;
 	}

	/**
	  * 设置预计提前还款日期的值
	 * @param 	adDate	 预计提前还款日期
	**/
	public void setAdDate(Date  adDate){
		 this.adDate=adDate;
 	}

	/**
	  * 获取预计提前还款日期的值
	 * @return 返回预计提前还款日期的值
	**/
	public Date getAdDate(){
		 return adDate;
 	}

	/**
	  * 设置是否退息费的值
	 * @param 	isretreat	 是否退息费
	**/
	public void setIsretreat(Integer  isretreat){
		 this.isretreat=isretreat;
 	}

	/**
	  * 获取是否退息费的值
	 * @return 返回是否退息费的值
	**/
	public Integer getIsretreat(){
		 return isretreat;
 	}

	/**
	  * 设置退息费金额的值
	 * @param 	imamount	 退息费金额
	**/
	public void setImamount(BigDecimal  imamount){
		 this.imamount=imamount;
 	}

	/**
	  * 获取退息费金额的值
	 * @return 返回退息费金额的值
	**/
	public BigDecimal getImamount(){
		 return imamount;
 	}

	/**
	  * 设置预计提前还款额的值
	 * @param 	adamount	 预计提前还款额
	**/
	public void setAdamount(BigDecimal  adamount){
		 this.adamount=adamount;
 	}

	/**
	  * 获取预计提前还款额的值
	 * @return 返回预计提前还款额的值
	**/
	public BigDecimal getAdamount(){
		 return adamount;
 	}

	/**
	  * 设置处理方式的值
	 * @param 	treatment	 处理方式
	**/
	public void setTreatment(Integer  treatment){
		 this.treatment=treatment;
 	}

	/**
	  * 获取处理方式的值
	 * @return 返回处理方式的值
	**/
	public Integer getTreatment(){
		 return treatment;
 	}

	/**
	  * 设置申请人的值
	 * @param 	appMan	 申请人
	**/
	public void setAppMan(String  appMan){
		 this.appMan=appMan;
 	}

	/**
	  * 获取申请人的值
	 * @return 返回申请人的值
	**/
	public String getAppMan(){
		 return appMan;
 	}

	/**
	  * 设置申请日期的值
	 * @param 	appDate	 申请日期
	**/
	public void setAppDate(Date  appDate){
		 this.appDate=appDate;
 	}

	/**
	  * 获取申请日期的值
	 * @return 返回申请日期的值
	**/
	public Date getAppDate(){
		 return appDate;
 	}

	/**
	  * 设置联系电话的值
	 * @param 	tel	 联系电话
	**/
	public void setTel(String  tel){
		 this.tel=tel;
 	}

	/**
	  * 获取联系电话的值
	 * @return 返回联系电话的值
	**/
	public String getTel(){
		 return tel;
 	}

	/**
	  * 设置提前还款原因的值
	 * @param 	reason	 提前还款原因
	**/
	public void setReason(String  reason){
		 this.reason=reason;
 	}

	/**
	  * 获取提前还款原因的值
	 * @return 返回提前还款原因的值
	**/
	public String getReason(){
		 return reason;
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
	  * 设置办理日期的值
	 * @param 	mgrDate	 办理日期
	**/
	public void setMgrDate(Date  mgrDate){
		 this.mgrDate=mgrDate;
 	}

	/**
	  * 获取办理日期的值
	 * @return 返回办理日期的值
	**/
	public Date getMgrDate(){
		 return mgrDate;
 	}

	/**
	  * 设置实际提前还款日期的值
	 * @param 	predDate	 实际提前还款日期
	**/
	public void setPredDate(Date  predDate){
		 this.predDate=predDate;
 	}

	/**
	  * 获取实际提前还款日期的值
	 * @return 返回实际提前还款日期的值
	**/
	public Date getPredDate(){
		 return predDate;
 	}

	/**
	  * 设置手续费率的值
	 * @param 	frate	 手续费率
	**/
	public void setFrate(Double  frate){
		 this.frate=frate;
 	}

	/**
	  * 获取手续费率的值
	 * @return 返回手续费率的值
	**/
	public Double getFrate(){
		 return frate;
 	}

	/**
	  * 设置提前还款手续费的值
	 * @param 	freeamount	 提前还款手续费
	**/
	public void setFreeamount(BigDecimal  freeamount){
		 this.freeamount=freeamount;
 	}

	/**
	  * 获取提前还款手续费的值
	 * @return 返回提前还款手续费的值
	**/
	public BigDecimal getFreeamount(){
		 return freeamount;
 	}

	/**
	  * 设置还款计划ID的值
	 * @param 	payplanId	 还款计划ID
	**/
	public void setPayplanId(Long  payplanId){
		 this.payplanId=payplanId;
 	}

	/**
	  * 获取还款计划ID的值
	 * @return 返回还款计划ID的值
	**/
	public Long getPayplanId(){
		 return payplanId;
 	}

	/**
	  * 设置提前还款额合计的值
	 * @param 	totalAmount	 提前还款额合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取提前还款额合计的值
	 * @return 返回提前还款额合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置财务复核人的值
	 * @param 	reviewer	 财务复核人
	**/
	public void setReviewer(Long  reviewer){
		 this.reviewer=reviewer;
 	}

	/**
	  * 获取财务复核人的值
	 * @return 返回财务复核人的值
	**/
	public Long getReviewer(){
		 return reviewer;
 	}

	/**
	  * 设置复核日期的值
	 * @param 	reviewDate	 复核日期
	**/
	public void setReviewDate(Date  reviewDate){
		 this.reviewDate=reviewDate;
 	}

	/**
	  * 获取复核日期的值
	 * @return 返回复核日期的值
	**/
	public Date getReviewDate(){
		 return reviewDate;
 	}

	/**
	  * 设置通知方式的值
	 * @param 	notifyType	 通知方式
	**/
	public void setNotifyType(String  notifyType){
		 this.notifyType=notifyType;
 	}

	/**
	  * 获取通知方式的值
	 * @return 返回通知方式的值
	**/
	public String getNotifyType(){
		 return notifyType;
 	}

	/**
	  * 设置通知内容的值
	 * @param 	notifyContent	 通知内容
	**/
	public void setNotifyContent(String  notifyContent){
		 this.notifyContent=notifyContent;
 	}

	/**
	  * 获取通知内容的值
	 * @return 返回通知内容的值
	**/
	public String getNotifyContent(){
		 return notifyContent;
 	}

	/**
	  * 设置通知人的值
	 * @param 	notifyMan	 通知人
	**/
	public void setNotifyMan(Long  notifyMan){
		 this.notifyMan=notifyMan;
 	}

	/**
	  * 获取通知人的值
	 * @return 返回通知人的值
	**/
	public Long getNotifyMan(){
		 return notifyMan;
 	}

	/**
	  * 设置通知日期的值
	 * @param 	notifyDate	 通知日期
	**/
	public void setNotifyDate(Date  notifyDate){
		 this.notifyDate=notifyDate;
 	}

	/**
	  * 获取通知日期的值
	 * @return 返回通知日期的值
	**/
	public Date getNotifyDate(){
		 return notifyDate;
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

	/**
	  * 设置收款状态的值
	 * @param 	xstatus	 收款状态
	**/
	public void setXstatus(Integer  xstatus){
		 this.xstatus=xstatus;
 	}

	/**
	  * 获取收款状态的值
	 * @return 返回收款状态的值
	**/
	public Integer getXstatus(){
		 return xstatus;
 	}

	
	/**
	  * 获取已收提前还款手续费的值
	 * @return 返回已收提前还款手续费的值
	**/
	public BigDecimal getYfreeamount() {
		return yfreeamount;
	}

	/**
	  * 设置已收提前还款手续费的值
	 * @param 	yfreeamount	 已收提前还款手续费
	**/
	public void setYfreeamount(BigDecimal yfreeamount) {
		this.yfreeamount = yfreeamount;
	}

	/**
	  * 获取已收提前还款额合计的值
	 * @return 返回已收提前还款额合计的值
	**/
	public BigDecimal getYtotalAmount() {
		return ytotalAmount;
	}

	/**
	  * 设置已收提前还款额合计的值
	 * @param ytotalAmount	已收提前还款额合计	 
	**/
	public void setYtotalAmount(BigDecimal ytotalAmount) {
		this.ytotalAmount = ytotalAmount;
	}

	/**
	  * 获取豁免状态的值
	 * @return 返回豁免状态的值
	**/
	public Integer getExempt() {
		return exempt;
	}

	/**
	  * 设置豁免状态的值
	 * @param 	exempt	 豁免状态
	**/
	public void setExempt(Integer exempt) {
		this.exempt = exempt;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{accountId,id,code,breed,procId,contractId,ptype,adDate,isretreat,
					imamount,adamount,treatment,appMan,appDate,tel,reason,managerId,mgrDate,
					predDate,frate,freeamount,payplanId,totalAmount,reviewer,reviewDate,notifyType,
					notifyContent,notifyMan,notifyDate,yfreeamount,ytotalAmount,exempt,status,xstatus,
					isenabled,trinterAmount,trfreeAmount,trmgrAmount,trpenAmount,trdelAmount,trfreeAmount};
	}

	@Override
	public String[] getFields() {
		return new String[]{"accountId","id","code","breed","procId","contractId","ptype","adDate",
					"isretreat","imamount","adamount","treatment","appMan","appDate","tel","reason",
					"managerId","mgrDate","predDate","frate","freeamount","payplanId","totalAmount",
					"reviewer","reviewDate","notifyType","notifyContent","notifyMan","notifyDate",
					"yfreeamount","ytotalAmount","exempt","status","xstatus","isenabled","trinterAmount",
					"trfreeAmount","trmgrAmount","trpenAmount","trdelAmount","trfreeAmount"};
	}

}
