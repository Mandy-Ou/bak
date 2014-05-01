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
 * 汇票结算单表
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票结算单表实体",createDate="2014-02-20T00:00:00",author="郑符明")
@Entity
@Table(name="fu_Settlement")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class SettlementEntity extends IdBaseEntity {
	
	
	 @Description(remark="汇票承诺书ID")
	 @Column(name="receiptBookId" ,nullable=false )
	 private Long receiptBookId;

	 @Description(remark="结算日期")
	 @Column(name="sdate" ,nullable=false )
	 private Date sdate;

	 @Description(remark="客户姓名")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="金额")
	 @Column(name="amount" ,nullable=false )
	 private BigDecimal amount = new BigDecimal(0);

	 @Description(remark="查询费")
	 @Column(name="qamonut" ,nullable=false )
	 private BigDecimal qamonut = new BigDecimal(0);

	 @Description(remark="贴息")
	 @Column(name="tiamount" ,nullable=false )
	 private BigDecimal tiamount = new BigDecimal(0);
	 
	 @Description(remark="贴息利率")
	 @Column(name="rate" ,nullable=false )
	 private Double rate = new Double(0);

	 @Description(remark="实际付款金额")
	 @Column(name="ramount" ,nullable=false )
	 private BigDecimal ramount = new BigDecimal(0);

	 @Description(remark="审核签字")
	 @Column(name="auditMan" ,length=30 )
	 private String auditMan;

	 @Description(remark="领导签字")
	 @Column(name="leaderMan" ,length=30 )
	 private String leaderMan;

	 @Description(remark="财务签字")
	 @Column(name="finMan" ,length=30 )
	 private String finMan;


	public SettlementEntity() {

	}

	
	/**
	  * 设置汇票承诺书ID的值
	 * @param 	receiptBookId	 汇票承诺书ID
	**/
	public void setReceiptBookId(Long  receiptBookId){
		 this.receiptBookId=receiptBookId;
 	}

	/**
	  * 获取汇票承诺书ID的值
	 * @return 返回汇票承诺书ID的值
	**/
	public Long getReceiptBookId(){
		 return receiptBookId;
 	}

	/**
	  * 设置结算日期的值
	 * @param 	sdate	 结算日期
	**/
	public void setSdate(Date  sdate){
		 this.sdate=sdate;
 	}

	/**
	  * 获取结算日期的值
	 * @return 返回结算日期的值
	**/
	public Date getSdate(){
		 return sdate;
 	}

	/**
	  * 设置客户姓名的值
	 * @param 	name	 客户姓名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取客户姓名的值
	 * @return 返回客户姓名的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置金额的值
	 * @param 	amount	 金额
	**/
	public void setAmount(BigDecimal  amount){
		 this.amount=amount;
 	}

	/**
	  * 获取金额的值
	 * @return 返回金额的值
	**/
	public BigDecimal getAmount(){
		 return amount;
 	}

	/**
	  * 设置查询费的值
	 * @param 	qamonut	 查询费
	**/
	public void setQamonut(BigDecimal  qamonut){
		 this.qamonut=qamonut;
 	}

	/**
	  * 获取查询费的值
	 * @return 返回查询费的值
	**/
	public BigDecimal getQamonut(){
		 return qamonut;
 	}

	/**
	  * 设置贴息的值
	 * @param 	tiamount	 贴息
	**/
	public void setTiamount(BigDecimal  tiamount){
		 this.tiamount=tiamount;
 	}

	/**
	  * 获取贴息的值
	 * @return 返回贴息的值
	**/
	public BigDecimal getTiamount(){
		 return tiamount;
 	}
	
	/**
	 * 设置贴息的值
	 * @param 	rate	 贴息
	 **/
	public void setRate(Double  rate){
		this.rate=rate;
	}
	
	/**
	 * 获取贴息的值
	 * @return 返回贴息的值
	 **/
	public Double getRate(){
		return rate;
	}

	/**
	  * 设置实际付款金额的值
	 * @param 	ramount	 实际付款金额
	**/
	public void setRamount(BigDecimal  ramount){
		 this.ramount=ramount;
 	}

	/**
	  * 获取实际付款金额的值
	 * @return 返回实际付款金额的值
	**/
	public BigDecimal getRamount(){
		 return ramount;
 	}

	/**
	  * 设置审核签字的值
	 * @param 	auditMan	 审核签字
	**/
	public void setAuditMan(String  auditMan){
		 this.auditMan=auditMan;
 	}

	/**
	  * 获取审核签字的值
	 * @return 返回审核签字的值
	**/
	public String getAuditMan(){
		 return auditMan;
 	}

	/**
	  * 设置领导签字的值
	 * @param 	leaderMan	 领导签字
	**/
	public void setLeaderMan(String  leaderMan){
		 this.leaderMan=leaderMan;
 	}

	/**
	  * 获取领导签字的值
	 * @return 返回领导签字的值
	**/
	public String getLeaderMan(){
		 return leaderMan;
 	}

	/**
	  * 设置财务签字的值
	 * @param 	finMan	 财务签字
	**/
	public void setFinMan(String  finMan){
		 this.finMan=finMan;
 	}

	/**
	  * 获取财务签字的值
	 * @return 返回财务签字的值
	**/
	public String getFinMan(){
		 return finMan;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{receiptBookId,sdate,name,amount,qamonut,tiamount,rate,ramount,auditMan,leaderMan,finMan};
	}

	@Override
	public String[] getFields() {
		return new String[]{"receiptBookId","sdate","name","amount","qamonut","tiamount","rate","ramount","auditMan","leaderMan","finMan"};
	}

}
