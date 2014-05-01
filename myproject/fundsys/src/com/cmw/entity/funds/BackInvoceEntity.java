package com.cmw.entity.funds;


import java.math.BigDecimal;
import java.util.Date;

import javassist.expr.NewArray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 汇票回款单表
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票回款单表实体",createDate="2014-02-20T00:00:00",author="郑符明")
@Entity
@Table(name="fu_BackInvoce")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BackInvoceEntity extends IdBaseEntity {
	
	
	 @Description(remark="回款收条ID")
	 @Column(name="backReceiptId" ,nullable=false )
	 private Long backReceiptId;

	 @Description(remark="回款日期")
	 @Column(name="sdate" ,nullable=false )
	 private Date sdate;

	 @Description(remark="姓名")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;

	 @Description(remark="金额")
	 @Column(name="amount" ,nullable=false )
	 private BigDecimal amount = new BigDecimal(0.00);
	 
	 @Description(remark="贴息利率")
	 @Column(name="rate" ,nullable=false )
	 private Double rate = 0.00;

	 @Description(remark="贴息")
	 @Column(name="tiamount" ,nullable=false )
	 private BigDecimal tiamount = new BigDecimal(0.00);

	 @Description(remark="实际回款金额")
	 @Column(name="bamount" ,nullable=false )
	 private BigDecimal bamount = new BigDecimal(0.00);

	 @Description(remark="净利润")
	 @Column(name="pamount" ,nullable=false )
	 private BigDecimal pamount = new BigDecimal(0.00);


	public BackInvoceEntity() {

	}

	
	/**
	  * 设置回款收条ID的值
	 * @param 	backReceiptId	 回款收条ID
	**/
	public void setBackReceiptId(Long  backReceiptId){
		 this.backReceiptId=backReceiptId;
 	}

	/**
	  * 获取回款收条ID的值
	 * @return 返回回款收条ID的值
	**/
	public Long getBackReceiptId(){
		 return backReceiptId;
 	}

	/**
	  * 设置回款日期的值
	 * @param 	sdate	 回款日期
	**/
	public void setSdate(Date  sdate){
		 this.sdate=sdate;
 	}

	/**
	  * 获取回款日期的值
	 * @return 返回回款日期的值
	**/
	public Date getSdate(){
		 return sdate;
 	}

	/**
	  * 设置姓名的值
	 * @param 	name	 姓名
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取姓名的值
	 * @return 返回姓名的值
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
	  * 设置贴息利率的值
	 * @param 	rate	 金额
	**/
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	  * 获取贴息利率的值
	 * @return 返回贴息利率的值
	**/
	public Double getRate() {
		return rate;
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
	  * 设置实际回款金额的值
	 * @param 	bamount	 实际回款金额
	**/
	public void setBamount(BigDecimal  bamount){
		 this.bamount=bamount;
 	}

	/**
	  * 获取实际回款金额的值
	 * @return 返回实际回款金额的值
	**/
	public BigDecimal getBamount(){
		 return bamount;
 	}

	/**
	  * 设置净利润的值
	 * @param 	pamount	 净利润
	**/
	public void setPamount(BigDecimal  pamount){
		 this.pamount=pamount;
 	}

	/**
	  * 获取净利润的值
	 * @return 返回净利润的值
	**/
	public BigDecimal getPamount(){
		 return pamount;
 	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,backReceiptId,sdate,name,rate,amount,tiamount,bamount,pamount};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","backReceiptId","sdate","name","rate","amount","tiamount","bamount","pamount"};
	}

}
