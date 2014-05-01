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
 * 汇票信息登记
 * @author 彭登浩
 * @date 2014-01-20T00:00:00
 */
@Description(remark="汇票信息登记实体",createDate="2014-01-20T00:00:00",author="彭登浩")
@Entity
@Table(name="fu_ReceiptMsg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ReceiptMsgEntity extends IdBaseEntity {
	
	@Description(remark="编号")
	@Column(name="code",nullable = false,length = 30)
	private String code;
	
	@Description(remark="汇票收条ID")
	@Column(name="receiptId",nullable = false)
	private Long receiptId;
	
	@Description(remark="汇票结算单ID")
	@Column(name="settleId",nullable = false)
	private Long settleId;
	
	@Description(remark="票号")
	@Column(name="rnum",nullable = false,length = 30)
	private String rnum;
	
	@Description(remark="供票人")
	@Column(name="name",nullable = false,length = 50)
	private String name;
	
	@Description(remark="金额")
	@Column(name="amount",nullable = false)
	private BigDecimal amount  = new BigDecimal(0.00);
	
	@Description(remark="利率")
	@Column(name="rate",nullable = false,scale = 2)
	private Double rate;
	
	@Description(remark="汇票收费")
	@Column(name="tiamount",nullable = false)
	private BigDecimal tiamount  = new BigDecimal(0.00);
	
	@Description(remark="业务人员")
	@Column(name="serviceMan",nullable = false)
	private Long serviceMan ;
	
	@Description(remark="贴现票日期")
	@Column(name="ticketDate",nullable = false)
	private Date ticketDate;
	
	@Description(remark="收票人")
	@Column(name="ticketMan",nullable = false,length = 30)
	private String ticketMan;
	
	@Description(remark="资金到账日期")
	@Column(name="fundsDate",nullable = false)
	private Date fundsDate;
	
	@Description(remark="贴现利率")
	@Column(name="ticketRate",nullable = false,scale = 2)
	private Double ticketRate;	
	
	@Description(remark="贴现收费")
	@Column(name="charge",nullable = false)
	private BigDecimal charge  = new BigDecimal(0);
	
	@Description(remark="到账金额")
	@Column(name="funds",nullable = false)
	private BigDecimal funds  = new BigDecimal(0);
	
	@Description(remark="提成")
	@Column(name="adultMoney",nullable = false)
	private BigDecimal adultMoney  = new BigDecimal(0);
	
	@Description(remark="利润")
	@Column(name="interest",nullable = false)
	private BigDecimal interest  = new BigDecimal(0);
	
	
	
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the receiptId
	 */
	public Long getReceiptId() {
		return receiptId;
	}

	/**
	 * @param receiptId the receiptId to set
	 */
	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	/**
	 * @return the settleId
	 */
	public Long getSettleId() {
		return settleId;
	}

	/**
	 * @param settleId the settleId to set
	 */
	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}

	/**
	 * @return the rnum
	 */
	public String getRnum() {
		return rnum;
	}

	/**
	 * @param rnum the rnum to set
	 */
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the tiamount
	 */
	public BigDecimal getTiamount() {
		return tiamount;
	}

	/**
	 * @param tiamount the tiamount to set
	 */
	public void setTiamount(BigDecimal tiamount) {
		this.tiamount = tiamount;
	}

	/**
	 * @return the serviceMan
	 */
	public Long getServiceMan() {
		return serviceMan;
	}

	/**
	 * @param serviceMan the serviceMan to set
	 */
	public void setServiceMan(Long serviceMan) {
		this.serviceMan = serviceMan;
	}

	/**
	 * @return the ticketDate
	 */
	public Date getTicketDate() {
		return ticketDate;
	}

	/**
	 * @param ticketDate the ticketDate to set
	 */
	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	/**
	 * @return the ticketMan
	 */
	public String getTicketMan() {
		return ticketMan;
	}

	/**
	 * @param ticketMan the ticketMan to set
	 */
	public void setTicketMan(String ticketMan) {
		this.ticketMan = ticketMan;
	}

	/**
	 * @return the fundsDate
	 */
	public Date getFundsDate() {
		return fundsDate;
	}

	/**
	 * @param fundsDate the fundsDate to set
	 */
	public void setFundsDate(Date fundsDate) {
		this.fundsDate = fundsDate;
	}

	/**
	 * @return the ticketRate
	 */
	public Double getTicketRate() {
		return ticketRate;
	}

	/**
	 * @param ticketRate the ticketRate to set
	 */
	public void setTicketRate(Double ticketRate) {
		this.ticketRate = ticketRate;
	}

	/**
	 * @return the charge
	 */
	public BigDecimal getCharge() {
		return charge;
	}

	/**
	 * @param charge the charge to set
	 */
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	/**
	 * @return the funds
	 */
	public BigDecimal getFunds() {
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(BigDecimal funds) {
		this.funds = funds;
	}

	/**
	 * @return the adultMoney
	 */
	public BigDecimal getAdultMoney() {
		return adultMoney;
	}

	/**
	 * @param adultMoney the adultMoney to set
	 */
	public void setAdultMoney(BigDecimal adultMoney) {
		this.adultMoney = adultMoney;
	}

	/**
	 * @return the interest
	 */
	public BigDecimal getInterest() {
		return interest;
	}

	/**
	 * @param interest the interest to set
	 */
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,receiptId,settleId,rnum,name,amount,rate,tiamount,serviceMan,ticketDate,ticketMan,fundsDate,ticketRate,charge,funds,adultMoney,interest};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","receiptId","settleId","rnum","name","amount","rate","tiamount","serviceMan","ticketDate#yyyy-MM-dd","ticketMan","fundsDate#yyyy-MM-dd","ticketRate","charge","funds","adultMoney","interest"};
	}


}
