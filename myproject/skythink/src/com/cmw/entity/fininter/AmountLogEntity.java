package com.cmw.entity.fininter;


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
 * 实收金额日志
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="实收金额日志实体",createDate="2013-03-28T00:00:00",author="程明卫")
@Entity
@Table(name="fs_AmountLog")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AmountLogEntity extends IdBaseEntity {
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="业务分类")
	 @Column(name="category" ,nullable=false )
	 private Integer category;

	 @Description(remark="业务标识")
	 @Column(name="bussTag" ,nullable=false )
	 private Integer bussTag;

	 @Description(remark="业务单据ID")
	 @Column(name="invoceIds" )
	 private String invoceIds;
	 
	 @Description(remark="本金")
	 @Column(name="amount" ,nullable=false )
	 private BigDecimal amount = new BigDecimal("0");

	 @Description(remark="利息")
	 @Column(name="ramount" ,nullable=false )
	 private BigDecimal ramount = new BigDecimal("0");

	 @Description(remark="管理费")
	 @Column(name="mamount" ,nullable=false )
	 private BigDecimal mamount = new BigDecimal("0");

	 @Description(remark="罚息金额")
	 @Column(name="pamount" ,nullable=false )
	 private BigDecimal pamount = new BigDecimal("0");

	 @Description(remark="滞纳金")
	 @Column(name="oamount" ,nullable=false )
	 private BigDecimal oamount = new BigDecimal("0");

	 @Description(remark="手续费")
	 @Column(name="famount" ,nullable=false )
	 private BigDecimal famount = new BigDecimal("0");

	 @Description(remark="提前还款手续费")
	 @Column(name="bamount" ,nullable=false )
	 private BigDecimal bamount = new BigDecimal("0");

	 @Description(remark="合计")
	 @Column(name="sumamount" ,nullable=false )
	 private BigDecimal sumamount = new BigDecimal("0");

	 @Description(remark="客户数量")
	 @Column(name="custCount" ,nullable=false )
	 private Integer custCount = 0;

	 @Description(remark="放/收日期")
	 @Column(name="opdate" ,nullable=false )
	 private Date opdate;

	 @Description(remark="放/收款帐号ID")
	 @Column(name="accountId" ,nullable=false )
	 private Long accountId;
	 
	 @Description(remark="财务银行帐号refId")
	 @Column(name="reffinAccountId")
	 private String reffinAccountId;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=50 )
	 private String fcnumber;

	 @Description(remark="财务凭证ID")
	 @Column(name="refId" )
	 private String refId;


	public AmountLogEntity() {

	}

	
	/**
	  * 设置系统ID的值
	 * @param 	sysId	 系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置业务分类的值
	 * @param 	category	 业务分类
	**/
	public void setCategory(Integer  category){
		 this.category=category;
 	}

	/**
	  * 获取业务分类的值
	 * @return 返回业务分类的值
	**/
	public Integer getCategory(){
		 return category;
 	}

	/**
	  * 设置业务标识的值
	 * @param 	bussTag	 业务标识
	**/
	public void setBussTag(Integer  bussTag){
		 this.bussTag=bussTag;
 	}

	/**
	  * 获取业务标识的值
	 * @return 返回业务标识的值
	**/
	public Integer getBussTag(){
		 return bussTag;
 	}
	
	
	/**
	  * 获取业务单据ID的值
	 * @return 返回业务单据ID的值
	**/
	public String getInvoceIds() {
		return invoceIds;
	}

	/**
	  * 设置业务单据ID的值
	 * @param 	invoceIds	 业务单据ID
	**/
	public void setInvoceIds(String invoceIds) {
		this.invoceIds = invoceIds;
	}


	/**
	  * 设置本金的值
	 * @param 	amount	 本金
	**/
	public void setAmount(BigDecimal  amount){
		 this.amount=amount;
 	}

	/**
	  * 获取本金的值
	 * @return 返回本金的值
	**/
	public BigDecimal getAmount(){
		 return amount;
 	}

	/**
	  * 设置利息的值
	 * @param 	ramount	 利息
	**/
	public void setRamount(BigDecimal  ramount){
		 this.ramount=ramount;
 	}

	/**
	  * 获取利息的值
	 * @return 返回利息的值
	**/
	public BigDecimal getRamount(){
		 return ramount;
 	}

	/**
	  * 设置管理费的值
	 * @param 	mamount	 管理费
	**/
	public void setMamount(BigDecimal  mamount){
		 this.mamount=mamount;
 	}

	/**
	  * 获取管理费的值
	 * @return 返回管理费的值
	**/
	public BigDecimal getMamount(){
		 return mamount;
 	}

	/**
	  * 设置罚息金额的值
	 * @param 	pamount	 罚息金额
	**/
	public void setPamount(BigDecimal  pamount){
		 this.pamount=pamount;
 	}

	/**
	  * 获取罚息金额的值
	 * @return 返回罚息金额的值
	**/
	public BigDecimal getPamount(){
		 return pamount;
 	}

	/**
	  * 设置滞纳金的值
	 * @param 	oamount	 滞纳金
	**/
	public void setOamount(BigDecimal  oamount){
		 this.oamount=oamount;
 	}

	/**
	  * 获取滞纳金的值
	 * @return 返回滞纳金的值
	**/
	public BigDecimal getOamount(){
		 return oamount;
 	}

	/**
	  * 设置手续费的值
	 * @param 	famount	 手续费
	**/
	public void setFamount(BigDecimal  famount){
		 this.famount=famount;
 	}

	/**
	  * 获取手续费的值
	 * @return 返回手续费的值
	**/
	public BigDecimal getFamount(){
		 return famount;
 	}

	/**
	  * 设置提前还款手续费的值
	 * @param 	bamount	 提前还款手续费
	**/
	public void setBamount(BigDecimal  bamount){
		 this.bamount=bamount;
 	}

	/**
	  * 获取提前还款手续费的值
	 * @return 返回提前还款手续费的值
	**/
	public BigDecimal getBamount(){
		 return bamount;
 	}

	/**
	  * 设置合计的值
	 * @param 	sumamount	 合计
	**/
	public void setSumamount(BigDecimal  sumamount){
		 this.sumamount=sumamount;
 	}

	/**
	  * 获取合计的值
	 * @return 返回合计的值
	**/
	public BigDecimal getSumamount(){
		 return sumamount;
 	}

	/**
	  * 设置客户数量的值
	 * @param 	custCount	 客户数量
	**/
	public void setCustCount(Integer  custCount){
		 this.custCount=custCount;
 	}

	/**
	  * 获取客户数量的值
	 * @return 返回客户数量的值
	**/
	public Integer getCustCount(){
		 return custCount;
 	}

	/**
	  * 设置放/收日期的值
	 * @param 	opdate	 放/收日期
	**/
	public void setOpdate(Date  opdate){
		 this.opdate=opdate;
 	}

	/**
	  * 获取放/收日期的值
	 * @return 返回放/收日期的值
	**/
	public Date getOpdate(){
		 return opdate;
 	}

	/**
	  * 设置放/收款帐号ID的值
	 * @param 	accountId	 放/收款帐号ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取放/收款帐号ID的值
	 * @return 返回放/收款帐号ID的值
	**/
	public Long getAccountId(){
		 return accountId;
 	}

	
	/**
	  * 获取财务银行帐号refId的值
	 * @return 返回财务银行帐号refId的值
	**/
	public String getReffinAccountId() {
		return reffinAccountId;
	}

	/**
	  * 设置财务银行帐号refId的值
	 * @param 	reffinAccountId	 财务银行帐号refId
	**/
	public void setReffinAccountId(String reffinAccountId) {
		this.reffinAccountId = reffinAccountId;
	}


	/**
	  * 设置财务凭证编号的值
	 * @param 	fcnumber	 财务凭证编号
	**/
	public void setFcnumber(String  fcnumber){
		 this.fcnumber=fcnumber;
 	}

	/**
	  * 获取财务凭证编号的值
	 * @return 返回财务凭证编号的值
	**/
	public String getFcnumber(){
		 return fcnumber;
 	}

	/**
	  * 设置财务凭证ID的值
	 * @param 	refId	 财务凭证ID
	**/
	public void setRefId(String  refId){
		 this.refId=refId;
 	}

	/**
	  * 获取财务凭证ID的值
	 * @return 返回财务凭证ID的值
	**/
	public String getRefId(){
		 return refId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,sysId,category,invoceIds,bussTag,amount,ramount,mamount,pamount,oamount,famount,bamount,sumamount,custCount,opdate,accountId,reffinAccountId,fcnumber,refId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","sysId","category","invoceIds","bussTag","amount","ramount","mamount","pamount","oamount","famount","bamount","sumamount","custCount","opdate#yyyy-MM-dd","accountId","reffinAccountId","fcnumber","refId"};
	}

}
