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
 * 还款计划
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划实体",createDate="2013-01-15T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Plan")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PlanEntity extends IdBaseEntity {
	
	
	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="期数")
	 @Column(name="phases" ,nullable=false )
	 private Integer phases;

	 @Description(remark="应还款日期")
	 @Column(name="xpayDate" ,nullable=false )
	 private Date xpayDate;

	 @Description(remark="本金")
	 @Column(name="principal" ,nullable=false ,scale=2)
	 private BigDecimal principal = new BigDecimal(0);

	 @Description(remark="利息")
	 @Column(name="interest" ,nullable=false ,scale=2)
	 private BigDecimal interest = new BigDecimal(0);

	 @Description(remark="管理费")
	 @Column(name="mgrAmount" ,nullable=false ,scale=2)
	 private BigDecimal mgrAmount = new BigDecimal(0);

	 @Description(remark="罚息金额")
	 @Column(name="penAmount" ,nullable=false ,scale=2)
	 private BigDecimal penAmount = new BigDecimal(0);

	 @Description(remark="滞纳金额")
	 @Column(name="delAmount" ,nullable=false ,scale=2)
	 private BigDecimal delAmount = new BigDecimal(0);

	 @Description(remark="应付合计")
	 @Column(name="totalAmount" ,nullable=false ,scale=2)
	 private BigDecimal totalAmount = new BigDecimal(0);

	 @Description(remark="剩余本金")
	 @Column(name="reprincipal" ,nullable=false ,scale=2)
	 private BigDecimal reprincipal = new BigDecimal(0);

	 @Description(remark="实收本金")
	 @Column(name="yprincipal" ,nullable=false ,scale=2)
	 private BigDecimal yprincipal = new BigDecimal(0);

	 @Description(remark="实收利息")
	 @Column(name="yinterest" ,nullable=false ,scale=2)
	 private BigDecimal yinterest = new BigDecimal(0);

	 @Description(remark="实收管理费")
	 @Column(name="ymgrAmount" ,nullable=false ,scale=2)
	 private BigDecimal ymgrAmount = new BigDecimal(0);

	 @Description(remark="实收罚息")
	 @Column(name="ypenAmount" ,nullable=false ,scale=2)
	 private BigDecimal ypenAmount = new BigDecimal(0);

	 @Description(remark="实收滞纳金")
	 @Column(name="ydelAmount" ,nullable=false ,scale=2)
	 private BigDecimal ydelAmount = new BigDecimal(0);

	 @Description(remark="实收合计")
	 @Column(name="ytotalAmount" ,nullable=false ,scale=2)
	 private BigDecimal ytotalAmount = new BigDecimal(0);

	 @Description(remark="返还利息")
	 @Column(name="trinterAmount" ,nullable=false ,scale=2)
	 private BigDecimal trinterAmount = new BigDecimal(0);

	 @Description(remark="返还管理费")
	 @Column(name="trmgrAmount" ,nullable=false ,scale=2)
	 private BigDecimal trmgrAmount = new BigDecimal(0);

	 @Description(remark="返还罚息")
	 @Column(name="trpenAmount" ,nullable=false ,scale=2)
	 private BigDecimal trpenAmount = new BigDecimal(0);

	 @Description(remark="返还滞纳金")
	 @Column(name="trdelAmount" ,nullable=false ,scale=2)
	 private BigDecimal trdelAmount = new BigDecimal(0);

	 @Description(remark="本金逾期天数")
	 @Column(name="pdays" ,nullable=false ,scale=2)
	 private Integer pdays = 0;

	 @Description(remark="利息逾期天数")
	 @Column(name="ratedays" ,nullable=false ,scale=2)
	 private Integer ratedays = 0;

	 @Description(remark="管理费逾期天数")
	 @Column(name="mgrdays" ,nullable=false ,scale=2)
	 private Integer mgrdays = 0;

	 @Description(remark="最后收款日期")
	 @Column(name="lastDate" )
	 private Date lastDate;

	 @Description(remark="本息状态")
	 @Column(name="pistatus" ,nullable=false )
	 private Integer pistatus = 0;

	 @Description(remark="管理费状态")
	 @Column(name="mgrstatus" ,nullable=false )
	 private Integer mgrstatus = 0;

	 @Description(remark="总状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="豁免状态")
	 @Column(name="exempt" ,nullable=false )
	 private Integer exempt = 0;

	 @Description(remark="计提状态")
	 @Column(name="provision" ,nullable=false )
	 private Integer provision = 0;
	 
	 /*===== V2.5 ADD Fields START CODE =====*/
	 @Description(remark="展期次数")
	 @Column(name="extNo")
	 private Integer extNo = 0;
	 
	 @Description(remark="展期协议书ID")
	 @Column(name="extcontractId")
	 private Long extcontractId;
	 /*===== V2.5 ADD Fields END CODE =====*/
	 
	public PlanEntity() {

	}

	

	public Long getContractId() {
		return contractId;
	}



	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}



	/**
	  * 设置期数的值
	 * @param 	phases	 期数
	**/
	public void setPhases(Integer  phases){
		 this.phases=phases;
 	}

	/**
	  * 获取期数的值
	 * @return 返回期数的值
	**/
	public Integer getPhases(){
		 return phases;
 	}

	/**
	  * 设置应还款日期的值
	 * @param 	xpayDate	 应还款日期
	**/
	public void setXpayDate(Date  xpayDate){
		 this.xpayDate=xpayDate;
 	}

	/**
	  * 获取应还款日期的值
	 * @return 返回应还款日期的值
	**/
	public Date getXpayDate(){
		 return xpayDate;
 	}

	/**
	  * 设置本金的值
	 * @param 	principal	 本金
	**/
	public void setPrincipal(BigDecimal  principal){
		 this.principal=principal;
 	}

	/**
	  * 获取本金的值
	 * @return 返回本金的值
	**/
	public BigDecimal getPrincipal(){
		 return principal;
 	}

	/**
	  * 设置利息的值
	 * @param 	interest	 利息
	**/
	public void setInterest(BigDecimal  interest){
		 this.interest=interest;
 	}

	/**
	  * 获取利息的值
	 * @return 返回利息的值
	**/
	public BigDecimal getInterest(){
		 return interest;
 	}

	/**
	  * 设置管理费的值
	 * @param 	mgrAmount	 管理费
	**/
	public void setMgrAmount(BigDecimal  mgrAmount){
		 this.mgrAmount=mgrAmount;
 	}

	/**
	  * 获取管理费的值
	 * @return 返回管理费的值
	**/
	public BigDecimal getMgrAmount(){
		 return mgrAmount;
 	}

	/**
	  * 设置罚息金额的值
	 * @param 	penAmount	 罚息金额
	**/
	public void setPenAmount(BigDecimal  penAmount){
		 this.penAmount=penAmount;
 	}

	/**
	  * 获取罚息金额的值
	 * @return 返回罚息金额的值
	**/
	public BigDecimal getPenAmount(){
		 return penAmount;
 	}

	/**
	  * 设置滞纳金额的值
	 * @param 	delAmount	 滞纳金额
	**/
	public void setDelAmount(BigDecimal  delAmount){
		 this.delAmount=delAmount;
 	}

	/**
	  * 获取滞纳金额的值
	 * @return 返回滞纳金额的值
	**/
	public BigDecimal getDelAmount(){
		 return delAmount;
 	}

	/**
	  * 设置应付合计的值
	 * @param 	totalAmount	 应付合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取应付合计的值
	 * @return 返回应付合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置剩余本金的值
	 * @param 	reprincipal	 剩余本金
	**/
	public void setReprincipal(BigDecimal  reprincipal){
		 this.reprincipal=reprincipal;
 	}

	/**
	  * 获取剩余本金的值
	 * @return 返回剩余本金的值
	**/
	public BigDecimal getReprincipal(){
		 return reprincipal;
 	}

	/**
	  * 设置实收本金的值
	 * @param 	yprincipal	 实收本金
	**/
	public void setYprincipal(BigDecimal  yprincipal){
		 this.yprincipal=yprincipal;
 	}

	/**
	  * 获取实收本金的值
	 * @return 返回实收本金的值
	**/
	public BigDecimal getYprincipal(){
		 return yprincipal;
 	}

	/**
	  * 设置实收利息的值
	 * @param 	yinterest	 实收利息
	**/
	public void setYinterest(BigDecimal  yinterest){
		 this.yinterest=yinterest;
 	}

	/**
	  * 获取实收利息的值
	 * @return 返回实收利息的值
	**/
	public BigDecimal getYinterest(){
		 return yinterest;
 	}

	/**
	  * 设置实收管理费的值
	 * @param 	ymgrAmount	 实收管理费
	**/
	public void setYmgrAmount(BigDecimal  ymgrAmount){
		 this.ymgrAmount=ymgrAmount;
 	}

	/**
	  * 获取实收管理费的值
	 * @return 返回实收管理费的值
	**/
	public BigDecimal getYmgrAmount(){
		 return ymgrAmount;
 	}

	/**
	  * 设置实收罚息的值
	 * @param 	ypenAmount	 实收罚息
	**/
	public void setYpenAmount(BigDecimal  ypenAmount){
		 this.ypenAmount=ypenAmount;
 	}

	/**
	  * 获取实收罚息的值
	 * @return 返回实收罚息的值
	**/
	public BigDecimal getYpenAmount(){
		 return ypenAmount;
 	}

	/**
	  * 设置实收滞纳金的值
	 * @param 	ydelAmount	 实收滞纳金
	**/
	public void setYdelAmount(BigDecimal  ydelAmount){
		 this.ydelAmount=ydelAmount;
 	}

	/**
	  * 获取实收滞纳金的值
	 * @return 返回实收滞纳金的值
	**/
	public BigDecimal getYdelAmount(){
		 return ydelAmount;
 	}

	/**
	  * 设置实收合计的值
	 * @param 	ytotalAmount	 实收合计
	**/
	public void setYtotalAmount(BigDecimal  ytotalAmount){
		 this.ytotalAmount=ytotalAmount;
 	}

	/**
	  * 获取实收合计的值
	 * @return 返回实收合计的值
	**/
	public BigDecimal getYtotalAmount(){
		 return ytotalAmount;
 	}

	/**
	  * 设置返还利息的值
	 * @param 	trpenAmount	 返还利息
	**/
	public void setTrinterAmount(BigDecimal  trinterAmount){
		 this.trinterAmount=trinterAmount;
 	}

	/**
	  * 获取返还利息的值
	 * @return 返回返还利息的值
	**/
	public BigDecimal getTrinterAmount(){
		 return trinterAmount;
 	}

	/**
	  * 设置返还管理费的值
	 * @param 	trmgrAmount	 返还管理费
	**/
	public void setTrmgrAmount(BigDecimal  trmgrAmount){
		 this.trmgrAmount=trmgrAmount;
 	}

	/**
	  * 获取返还管理费的值
	 * @return 返回返还管理费的值
	**/
	public BigDecimal getTrmgrAmount(){
		 return trmgrAmount;
 	}

	/**
	  * 设置返还罚息的值
	 * @param 	trpenAmount	 返还罚息
	**/
	public void setTrpenAmount(BigDecimal  trpenAmount){
		 this.trpenAmount=trpenAmount;
 	}

	/**
	  * 获取返还罚息的值
	 * @return 返回返还罚息的值
	**/
	public BigDecimal getTrpenAmount(){
		 return trpenAmount;
 	}

	/**
	  * 设置返还滞纳金的值
	 * @param 	trdelAmount	 返还滞纳金
	**/
	public void setTrdelAmount(BigDecimal  trdelAmount){
		 this.trdelAmount=trdelAmount;
 	}

	/**
	  * 获取返还滞纳金的值
	 * @return 返回返还滞纳金的值
	**/
	public BigDecimal getTrdelAmount(){
		 return trdelAmount;
 	}

	/**
	  * 设置本金逾期天数的值
	 * @param 	pdays	 本金逾期天数
	**/
	public void setPdays(Integer  pdays){
		 this.pdays=pdays;
 	}

	/**
	  * 获取本金逾期天数的值
	 * @return 返回本金逾期天数的值
	**/
	public Integer getPdays(){
		 return pdays;
 	}

	/**
	  * 设置利息逾期天数的值
	 * @param 	ratedays	 利息逾期天数
	**/
	public void setRatedays(Integer  ratedays){
		 this.ratedays=ratedays;
 	}

	/**
	  * 获取利息逾期天数的值
	 * @return 返回利息逾期天数的值
	**/
	public Integer getRatedays(){
		 return ratedays;
 	}

	/**
	  * 设置管理费逾期天数的值
	 * @param 	mgrdays	 管理费逾期天数
	**/
	public void setMgrdays(Integer  mgrdays){
		 this.mgrdays=mgrdays;
 	}

	/**
	  * 获取管理费逾期天数的值
	 * @return 返回管理费逾期天数的值
	**/
	public Integer getMgrdays(){
		 return mgrdays;
 	}

	/**
	  * 设置最后收款日期的值
	 * @param 	lastDate	 最后收款日期
	**/
	public void setLastDate(Date  lastDate){
		 this.lastDate=lastDate;
 	}

	/**
	  * 获取最后收款日期的值
	 * @return 返回最后收款日期的值
	**/
	public Date getLastDate(){
		 return lastDate;
 	}

	/**
	  * 设置本息状态的值
	 * @param 	pistatus	 本息状态
	**/
	public void setPistatus(Integer  pistatus){
		 this.pistatus=pistatus;
 	}

	/**
	  * 获取本息状态的值
	 * @return 返回本息状态的值
	**/
	public Integer getPistatus(){
		 return pistatus;
 	}

	/**
	  * 设置管理费状态的值
	 * @param 	mgrstatus	 管理费状态
	**/
	public void setMgrstatus(Integer  mgrstatus){
		 this.mgrstatus=mgrstatus;
 	}

	/**
	  * 获取管理费状态的值
	 * @return 返回管理费状态的值
	**/
	public Integer getMgrstatus(){
		 return mgrstatus;
 	}

	/**
	  * 设置总状态的值
	 * @param 	status	 总状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取总状态的值
	 * @return 返回总状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}

	/**
	  * 设置豁免状态的值
	 * @param 	exempt	 豁免状态
	**/
	public void setExempt(Integer  exempt){
		 this.exempt=exempt;
 	}

	/**
	  * 获取豁免状态的值
	 * @return 返回豁免状态的值
	**/
	public Integer getExempt(){
		 return exempt;
 	}

	/**
	  * 设置计提状态的值
	 * @param 	provision	 计提状态
	**/
	public void setProvision(Integer  provision){
		 this.provision=provision;
 	}

	/**
	  * 获取计提状态的值
	 * @return 返回计提状态的值
	**/
	public Integer getProvision(){
		 return provision;
 	}


	/**
	  * 获取展期次数的值
	 * @return 返回展期次数的值
	**/
	public Integer getExtNo() {
		return extNo;
	}


	/**
	  * 设置展期次数的值
	 * @param 	extNo	 展期次数
	**/
	public void setExtNo(Integer extNo) {
		this.extNo = extNo;
	}


	/**
	  * 获取展期协议书ID的值
	 * @return 返回展期协议书ID的值
	**/
	public Long getExtcontractId() {
		return extcontractId;
	}


	/**
	  * 设置展期协议书ID的值
	 * @param 	extcontractId	 展期协议书ID
	**/
	public void setExtcontractId(Long extcontractId) {
		this.extcontractId = extcontractId;
	}



	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),contractId,phases,xpayDate,principal,interest,mgrAmount,penAmount,delAmount,totalAmount,reprincipal,yprincipal,yinterest,ymgrAmount,ypenAmount,ydelAmount,ytotalAmount,trpenAmount,trmgrAmount,trpenAmount,trdelAmount,pdays,ratedays,mgrdays,lastDate,pistatus,mgrstatus,status,exempt,provision,isenabled,extNo,extcontractId,};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","contractId","phases","xpayDate","principal","interest","mgrAmount","penAmount","delAmount","totalAmount","reprincipal","yprincipal","yinterest","ymgrAmount","ypenAmount","ydelAmount","ytotalAmount","trpenAmount","trmgrAmount","trpenAmount","trdelAmount","pdays","ratedays","mgrdays","lastDate","pistatus","mgrstatus","status","exempt","provision","isenabled","extNo","extcontractId"};
	}

}
