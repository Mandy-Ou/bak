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
 * 回款收条表
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="回款收条表实体",createDate="2014-02-20T00:00:00",author="郑符明")
@Entity
@Table(name="fu_BackReceipt")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BackReceiptEntity extends IdBaseEntity {
	
	
	 @Description(remark="汇票收条ID")
	 @Column(name="receiptId" ,nullable=false )
	 private Long receiptId;

	 @Description(remark="客户姓名")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="收条发送人")
	 @Column(name="reman" ,nullable=false ,length=50 )
	 private String reman;

	 @Description(remark="汇票数量")
	 @Column(name="rcount" ,nullable=false )
	 private String rcount = "0";

	 @Description(remark="票号")
	 @Column(name="rnum" ,nullable=false ,length=30 )
	 private String rnum;

	 @Description(remark="出票人")
	 @Column(name="outMan" ,nullable=false ,length=80 )
	 private String outMan;

	 @Description(remark="出票人账号")
	 @Column(name="omaccount" ,nullable=false ,length=30 )
	 private String omaccount;

	 @Description(remark="付款行")
	 @Column(name="pbank" ,nullable=false ,length=80 )
	 private String pbank;

	 @Description(remark="出票日期")
	 @Column(name="outDate" ,nullable=false )
	 private Date outDate;

	 @Description(remark="到票日期")
	 @Column(name="endDate" )
	 private Date endDate;

	 @Description(remark="金额")
	 @Column(name="amount" ,nullable=false )
	 private BigDecimal amount = new BigDecimal(0);

	 @Description(remark="收款人账户名")
	 @Column(name="rtacname" ,nullable=false ,length=80 )
	 private String rtacname;

	 @Description(remark="收款人账号")
	 @Column(name="rtaccount" ,nullable=false ,length=30 )
	 private String rtaccount;

	 @Description(remark="收款人开户行")
	 @Column(name="rtbank" ,nullable=false ,length=80 )
	 private String rtbank;

	 @Description(remark="回款收条签收日期")
	 @Column(name="recetDate" )
	 private  Date recetDate;


	public BackReceiptEntity() {

	}

	
	/**
	  * 设置汇票收条ID的值
	 * @param 	receiptId	 汇票收条ID
	**/
	public void setReceiptId(Long  receiptId){
		 this.receiptId=receiptId;
 	}

	/**
	  * 获取汇票收条ID的值
	 * @return 返回汇票收条ID的值
	**/
	public Long getReceiptId(){
		 return receiptId;
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
	  * 设置收条发送人的值
	 * @param 	reman	 收条发送人
	**/
	public void setReman(String  reman){
		 this.reman=reman;
 	}

	/**
	  * 获取收条发送人的值
	 * @return 返回收条发送人的值
	**/
	public String getReman(){
		 return reman;
 	}

	/**
	  * 设置汇票数量的值
	 * @param 	rcount	 汇票数量
	**/
	public void setRcount(String  rcount){
		 this.rcount=rcount;
 	}

	/**
	  * 获取汇票数量的值
	 * @return 返回汇票数量的值
	**/
	public String getRcount(){
		 return rcount;
 	}

	/**
	  * 设置票号的值
	 * @param 	rnum	 票号
	**/
	public void setRnum(String  rnum){
		 this.rnum=rnum;
 	}

	/**
	  * 获取票号的值
	 * @return 返回票号的值
	**/
	public String getRnum(){
		 return rnum;
 	}

	/**
	  * 设置出票人的值
	 * @param 	outMan	 出票人
	**/
	public void setOutMan(String  outMan){
		 this.outMan=outMan;
 	}

	/**
	  * 获取出票人的值
	 * @return 返回出票人的值
	**/
	public String getOutMan(){
		 return outMan;
 	}

	/**
	  * 设置出票人账号的值
	 * @param 	omaccount	 出票人账号
	**/
	public void setOmaccount(String  omaccount){
		 this.omaccount=omaccount;
 	}

	/**
	  * 获取出票人账号的值
	 * @return 返回出票人账号的值
	**/
	public String getOmaccount(){
		 return omaccount;
 	}

	/**
	  * 设置付款行的值
	 * @param 	pbank	 付款行
	**/
	public void setPbank(String  pbank){
		 this.pbank=pbank;
 	}

	/**
	  * 获取付款行的值
	 * @return 返回付款行的值
	**/
	public String getPbank(){
		 return pbank;
 	}

	/**
	  * 设置出票日期的值
	 * @param 	outDate	 出票日期
	**/
	public void setOutDate(Date  outDate){
		 this.outDate=outDate;
 	}

	/**
	  * 获取出票日期的值
	 * @return 返回出票日期的值
	**/
	public Date getOutDate(){
		 return outDate;
 	}

	/**
	  * 设置到票日期的值
	 * @param 	endDate	 到票日期
	**/
	public void setEndDate(Date  endDate){
		 this.endDate=endDate;
 	}

	/**
	  * 获取到票日期的值
	 * @return 返回到票日期的值
	**/
	public Date getEndDate(){
		 return endDate;
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
	  * 设置收款人账户名的值
	 * @param 	rtacname	 收款人账户名
	**/
	public void setRtacname(String  rtacname){
		 this.rtacname=rtacname;
 	}

	/**
	  * 获取收款人账户名的值
	 * @return 返回收款人账户名的值
	**/
	public String getRtacname(){
		 return rtacname;
 	}

	/**
	  * 设置收款人账号的值
	 * @param 	rtaccount	 收款人账号
	**/
	public void setRtaccount(String  rtaccount){
		 this.rtaccount=rtaccount;
 	}

	/**
	  * 获取收款人账号的值
	 * @return 返回收款人账号的值
	**/
	public String getRtaccount(){
		 return rtaccount;
 	}

	/**
	  * 设置收款人开户行的值
	 * @param 	rtbank	 收款人开户行
	**/
	public void setRtbank(String  rtbank){
		 this.rtbank=rtbank;
 	}

	/**
	  * 获取收款人开户行的值
	 * @return 返回收款人开户行的值
	**/
	public String getRtbank(){
		 return rtbank;
 	}

	/**
	  * 设置回款收条签收日期的值
	 * @param 	recetDate	 回款收条签收日期
	**/
	public void setRecetDate(Date  recetDate){
		 this.recetDate=recetDate;
 	}

	/**
	  * 获取回款收条签收日期的值
	 * @return 
	 * @return 返回回款收条签收日期的值
	**/
	public  Date getRecetDate(){
		 return recetDate;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{receiptId,name,reman,rcount,rnum,outMan,omaccount,pbank,outDate,endDate,amount,rtacname,rtaccount,rtbank,recetDate};
	}

	@Override
	public String[] getFields() {
		return new String[]{"receiptId","name","reman","rcount","rnum","outMan","omaccount","pbank","outDate","endDate","amount","rtacname","rtaccount","rtbank","recetDate"};
	}

}
