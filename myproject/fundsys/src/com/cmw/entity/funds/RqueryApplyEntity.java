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
 * 汇票查询申请表
 * @author 郑符明
 * @date 2014-02-24T00:00:00
 */
@Description(remark="汇票查询申请表实体",createDate="2014-02-24T00:00:00",author="郑符明")
@Entity
@Table(name="fu_RqueryApply")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RqueryApplyEntity extends IdBaseEntity {
	
	
	 @Description(remark="汇票收条ID")
	 @Column(name="receiptId" ,nullable=false )
	 private Long receiptId;

	 @Description(remark="银行名称")
	 @Column(name="qbank" ,nullable=false ,length=80 )
	 private String qbank;

	 @Description(remark="申请单位")
	 @Column(name="aorg" ,nullable=false ,length=80 )
	 private String aorg;

	 @Description(remark="账号")
	 @Column(name="account" ,nullable=false ,length=30 )
	 private String account;

	 @Description(remark="票号")
	 @Column(name="rnum" ,nullable=false ,length=30 )
	 private String rnum;

	 @Description(remark="金额")
	 @Column(name="amount" ,nullable=false )
	 private BigDecimal amount = new BigDecimal(0.00);

	 @Description(remark="承兑付款人")
	 @Column(name="payMan" ,nullable=false ,length=80 )
	 private String payMan;

	 @Description(remark="收款人")
	 @Column(name="rtacname" ,nullable=false ,length=80 )
	 private String rtacname;

	 @Description(remark="付款人行名")
	 @Column(name="pbank" ,nullable=false ,length=80 )
	 private String pbank;

	 @Description(remark="付款行行号")
	 @Column(name="bankNum" ,length=30 )
	 private String bankNum;

	 @Description(remark="签名单位")
	 @Column(name="signOrg" ,length=50 )
	 private String signOrg;

	 @Description(remark="申请日期")
	 @Column(name="appDate" )
	 private Date appDate;


	public RqueryApplyEntity() {

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
	  * 设置银行名称的值
	 * @param 	qbank	 银行名称
	**/
	public void setQbank(String  qbank){
		 this.qbank=qbank;
 	}

	/**
	  * 获取银行名称的值
	 * @return 返回银行名称的值
	**/
	public String getQbank(){
		 return qbank;
 	}

	/**
	  * 设置申请单位的值
	 * @param 	aorg	 申请单位
	**/
	public void setAorg(String  aorg){
		 this.aorg=aorg;
 	}

	/**
	  * 获取申请单位的值
	 * @return 返回申请单位的值
	**/
	public String getAorg(){
		 return aorg;
 	}

	/**
	  * 设置账号的值
	 * @param 	account	 账号
	**/
	public void setAccount(String  account){
		 this.account=account;
 	}

	/**
	  * 获取账号的值
	 * @return 返回账号的值
	**/
	public String getAccount(){
		 return account;
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
	  * 设置承兑付款人的值
	 * @param 	payMan	 承兑付款人
	**/
	public void setPayMan(String  payMan){
		 this.payMan=payMan;
 	}

	/**
	  * 获取承兑付款人的值
	 * @return 返回承兑付款人的值
	**/
	public String getPayMan(){
		 return payMan;
 	}

	/**
	  * 设置收款人的值
	 * @param 	rtacname	 收款人
	**/
	public void setRtacname(String  rtacname){
		 this.rtacname=rtacname;
 	}

	/**
	  * 获取收款人的值
	 * @return 返回收款人的值
	**/
	public String getRtacname(){
		 return rtacname;
 	}

	/**
	  * 设置付款人行名的值
	 * @param 	pbank	 付款人行名
	**/
	public void setPbank(String  pbank){
		 this.pbank=pbank;
 	}

	/**
	  * 获取付款人行名的值
	 * @return 返回付款人行名的值
	**/
	public String getPbank(){
		 return pbank;
 	}

	/**
	  * 设置付款行行号的值
	 * @param 	bankNum	 付款行行号
	**/
	public void setBankNum(String  bankNum){
		 this.bankNum=bankNum;
 	}

	/**
	  * 获取付款行行号的值
	 * @return 返回付款行行号的值
	**/
	public String getBankNum(){
		 return bankNum;
 	}

	/**
	  * 设置签名单位的值
	 * @param 	signOrg	 签名单位
	**/
	public void setSignOrg(String  signOrg){
		 this.signOrg=signOrg;
 	}

	/**
	  * 获取签名单位的值
	 * @return 返回签名单位的值
	**/
	public String getSignOrg(){
		 return signOrg;
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



	@Override
	public Object[] getDatas() {
		return new Object[]{receiptId,qbank,aorg,account,rnum,amount,payMan,rtacname,pbank,bankNum,signOrg,appDate};
	}

	@Override
	public String[] getFields() {
		return new String[]{"receiptId","qbank","aorg","account","rnum","amount","payMan","rtacname","pbank","bankNum","signOrg","appDate"};
	}

}
