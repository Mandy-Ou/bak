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
 * 汇票利润提成
 * @author 彭登浩
 * @date 2014-01-20T00:00:00
 */
@Description(remark="汇票利润提成实体",createDate="2014-01-20T00:00:00",author="彭登浩")
@Entity
@Table(name="fu_ReceInterest")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ReceInterestEntity extends IdBaseEntity {
	
	@Description(remark="汇票信息登记表Id")
	@Column(name="receMsgId",nullable = false)
	private Long receMsgId;
	
	@Description(remark="提成人")
	@Column(name="adultMan",nullable = false,length = 30)
	private String adultMan;
	
	@Description(remark="提成金额")
	@Column(name="adultMoney",nullable = false)
	private BigDecimal adultMoney  = new BigDecimal(0.00);
	
	@Description(remark="Double")
	@Column(name="adultProp",nullable = false,scale = 2)
	private Double adultProp;
	
	
	
	
	
	
	
	/**
	 * @return the receMsgId
	 */
	public Long getReceMsgId() {
		return receMsgId;
	}

	/**
	 * @param receMsgId the receMsgId to set
	 */
	public void setReceMsgId(Long receMsgId) {
		this.receMsgId = receMsgId;
	}

	/**
	 * @return the adultMan
	 */
	public String getAdultMan() {
		return adultMan;
	}

	/**
	 * @param adultMan the adultMan to set
	 */
	public void setAdultMan(String adultMan) {
		this.adultMan = adultMan;
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
	 * @return the adultProp
	 */
	public Double getAdultProp() {
		return adultProp;
	}

	/**
	 * @param adultProp the adultProp to set
	 */
	public void setAdultProp(Double adultProp) {
		this.adultProp = adultProp;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,receMsgId,adultMan,adultProp,adultMoney,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","receMsgId","adultMan","adultProp","adultMoney","remark"};
	}


}
