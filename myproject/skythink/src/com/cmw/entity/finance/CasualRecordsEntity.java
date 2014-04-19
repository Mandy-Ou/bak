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
 * 实收随借随还表
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="实收随借随还表实体",createDate="2014-01-07T00:00:00",author="程明卫")
@Entity
@Table(name="fc_CasualRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CasualRecordsEntity extends IdBaseEntity {
	
	
	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="业务标识")
	 @Column(name="bussTag" ,nullable=false )
	 private Integer bussTag;

	 @Description(remark="实收本金")
	 @Column(name="cat" ,nullable=false ,scale=2)
	 private BigDecimal cat = new BigDecimal("0");

	 @Description(remark="实收利息")
	 @Column(name="rat" ,nullable=false ,scale=2)
	 private BigDecimal rat = new BigDecimal("0");

	 @Description(remark="实收管理费")
	 @Column(name="mat" ,nullable=false ,scale=2)
	 private BigDecimal mat = new BigDecimal("0");

	 @Description(remark="实收罚息")
	 @Column(name="pat" ,nullable=false ,scale=2)
	 private BigDecimal pat = new BigDecimal("0");

	 @Description(remark="实收滞纳金")
	 @Column(name="dat" ,nullable=false ,scale=2)
	 private BigDecimal dat = new BigDecimal("0");

	 @Description(remark="实收随借随还手续费")
	 @Column(name="fat" ,nullable=false ,scale=2)
	 private BigDecimal fat = new BigDecimal("0");

	 @Description(remark="实收合计")
	 @Column(name="tat" ,nullable=false ,scale=2)
	 private BigDecimal tat = new BigDecimal("0");

	 @Description(remark="收款日期")
	 @Column(name="rectDate" ,nullable=false )
	 private Date rectDate;

	 @Description(remark="收款帐号ID")
	 @Column(name="accountId" )
	 private Long accountId;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;


	public CasualRecordsEntity() {

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
	  * 设置实收本金的值
	 * @param 	cat	 实收本金
	**/
	public void setCat(BigDecimal  cat){
		 this.cat=cat;
 	}

	/**
	  * 获取实收本金的值
	 * @return 返回实收本金的值
	**/
	public BigDecimal getCat(){
		 return cat;
 	}

	/**
	  * 设置实收利息的值
	 * @param 	rat	 实收利息
	**/
	public void setRat(BigDecimal  rat){
		 this.rat=rat;
 	}

	/**
	  * 获取实收利息的值
	 * @return 返回实收利息的值
	**/
	public BigDecimal getRat(){
		 return rat;
 	}

	/**
	  * 设置实收管理费的值
	 * @param 	mat	 实收管理费
	**/
	public void setMat(BigDecimal  mat){
		 this.mat=mat;
 	}

	/**
	  * 获取实收管理费的值
	 * @return 返回实收管理费的值
	**/
	public BigDecimal getMat(){
		 return mat;
 	}

	/**
	  * 设置实收罚息的值
	 * @param 	pat	 实收罚息
	**/
	public void setPat(BigDecimal  pat){
		 this.pat=pat;
 	}

	/**
	  * 获取实收罚息的值
	 * @return 返回实收罚息的值
	**/
	public BigDecimal getPat(){
		 return pat;
 	}

	/**
	  * 设置实收滞纳金的值
	 * @param 	dat	 实收滞纳金
	**/
	public void setDat(BigDecimal  dat){
		 this.dat=dat;
 	}

	/**
	  * 获取实收滞纳金的值
	 * @return 返回实收滞纳金的值
	**/
	public BigDecimal getDat(){
		 return dat;
 	}

	/**
	  * 设置实收随借随还手续费的值
	 * @param 	fat	 实收随借随还手续费
	**/
	public void setFat(BigDecimal  fat){
		 this.fat=fat;
 	}

	/**
	  * 获取实收随借随还手续费的值
	 * @return 返回实收随借随还手续费的值
	**/
	public BigDecimal getFat(){
		 return fat;
 	}

	/**
	  * 设置实收合计的值
	 * @param 	tat	 实收合计
	**/
	public void setTat(BigDecimal  tat){
		 this.tat=tat;
 	}

	/**
	  * 获取实收合计的值
	 * @return 返回实收合计的值
	**/
	public BigDecimal getTat(){
		 return tat;
 	}

	/**
	  * 设置收款日期的值
	 * @param 	rectDate	 收款日期
	**/
	public void setRectDate(Date  rectDate){
		 this.rectDate=rectDate;
 	}

	/**
	  * 获取收款日期的值
	 * @return 返回收款日期的值
	**/
	public Date getRectDate(){
		 return rectDate;
 	}

	/**
	  * 设置收款帐号ID的值
	 * @param 	accountId	 收款帐号ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取收款帐号ID的值
	 * @return 返回收款帐号ID的值
	**/
	public Long getAccountId(){
		 return accountId;
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



	@Override
	public Object[] getDatas() {
		return new Object[]{contractId,bussTag,cat,rat,mat,pat,dat,fat,tat,rectDate,accountId,fcnumber,id,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"contractId","bussTag","cat","rat","mat","pat","dat","fat","tat","rectDate","accountId","fcnumber","id","isenabled"};
	}

}
