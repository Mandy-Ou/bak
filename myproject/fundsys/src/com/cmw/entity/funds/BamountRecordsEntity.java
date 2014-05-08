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
 * 撤资付款记录
 * @author zsl
 * @date 2014-05-06T00:00:00
 */
@Description(remark="撤资付款记录实体",createDate="2014-05-06T00:00:00",author="zsl")
@Entity
@Table(name="fu_BamountRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BamountRecordsEntity extends IdBaseEntity {
	
	
	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;

	 @Description(remark="付款帐号ID")
	 @Column(name="accountId" )
	 private Long accountId;

	 @Description(remark="付款日期")
	 @Column(name="rectDate" ,nullable=false )
	 private Date rectDate;

	 @Description(remark="实际支付金额")
	 @Column(name="rpamount" ,nullable=false )
	 private BigDecimal rpamount;

	 @Description(remark="收回预付利息")
	 @Column(name="biamount" ,nullable=false )
	 private BigDecimal biamount;

	 @Description(remark="违约金额")
	 @Column(name="wamount" ,nullable=false )
	 private BigDecimal wamount;

	 @Description(remark="付款方式")
	 @Column(name="settleType" ,nullable=false )
	 private Integer settleType;

	 @Description(remark="撤资申请单ID")
	 @Column(name="applyId" ,nullable=false )
	 private Long applyId;


	public BamountRecordsEntity() {

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
	  * 设置付款帐号ID的值
	 * @param 	accountId	 付款帐号ID
	**/
	public void setAccountId(Long  accountId){
		 this.accountId=accountId;
 	}

	/**
	  * 获取付款帐号ID的值
	 * @return 返回付款帐号ID的值
	**/
	public Long getAccountId(){
		 return accountId;
 	}

	/**
	  * 设置付款日期的值
	 * @param 	rectDate	 付款日期
	**/
	public void setRectDate(Date  rectDate){
		 this.rectDate=rectDate;
 	}

	/**
	  * 获取付款日期的值
	 * @return 返回付款日期的值
	**/
	public Date getRectDate(){
		 return rectDate;
 	}

	/**
	  * 设置实际支付金额的值
	 * @param 	rpamount	 实际支付金额
	**/
	public void setRpamount(BigDecimal  rpamount){
		 this.rpamount=rpamount;
 	}

	/**
	  * 获取实际支付金额的值
	 * @return 返回实际支付金额的值
	**/
	public BigDecimal getRpamount(){
		 return rpamount;
 	}

	/**
	  * 设置收回预付利息的值
	 * @param 	biamount	 收回预付利息
	**/
	public void setBiamount(BigDecimal  biamount){
		 this.biamount=biamount;
 	}

	/**
	  * 获取收回预付利息的值
	 * @return 返回收回预付利息的值
	**/
	public BigDecimal getBiamount(){
		 return biamount;
 	}

	/**
	  * 设置违约金额的值
	 * @param 	wamount	 违约金额
	**/
	public void setWamount(BigDecimal  wamount){
		 this.wamount=wamount;
 	}

	/**
	  * 获取违约金额的值
	 * @return 返回违约金额的值
	**/
	public BigDecimal getWamount(){
		 return wamount;
 	}

	/**
	  * 设置付款方式的值
	 * @param 	settleType	 付款方式
	**/
	public void setSettleType(Integer  settleType){
		 this.settleType=settleType;
 	}

	/**
	  * 获取付款方式的值
	 * @return 返回付款方式的值
	**/
	public Integer getSettleType(){
		 return settleType;
 	}

	/**
	  * 设置撤资申请单ID的值
	 * @param 	applyId	 撤资申请单ID
	**/
	public void setApplyId(Long  applyId){
		 this.applyId=applyId;
 	}

	/**
	  * 获取撤资申请单ID的值
	 * @return 返回撤资申请单ID的值
	**/
	public Long getApplyId(){
		 return applyId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{fcnumber,accountId,rectDate,rpamount,biamount,wamount,settleType,applyId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"fcnumber","accountId","rectDate","rpamount","biamount","wamount","settleType","applyId"};
	}

}
