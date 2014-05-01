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
 * 项目费用表
 * @author 李听
 * @date 2013-01-15T00:00:00
 */
@Description(remark="项目费用表实体",createDate="2013-01-15T00:00:00",author="李听")
@Entity
@Table(name="fc_ProjectAmunt")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ProjectAmuntEntity extends IdBaseEntity {

	 @Description(remark="业务类型")
	 @Column(name="bussType" ,nullable=false )
	 private Integer bussType;
	 
	 @Description(remark="业务单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;
	
	 @Description(remark="费用类型")
	 @Column(name="itemType" ,nullable=false )
	 private Long itemType;


	 @Description(remark="收支方向")
	 @Column(name="direction" ,nullable=false )
	 private Integer direction;
	 

	 @Description(remark="金额")
	 @Column(name="amount" ,nullable=false ,scale=2)
	 private BigDecimal amount = new BigDecimal(0);

	 @Description(remark="实收[付]金额")
	 @Column(name="yamount" ,nullable=false ,scale=2)
	 private BigDecimal yamount = new BigDecimal(0);

	 @Description(remark="最后收款日期")
	 @Column(name="lastDate" ,nullable=true )
	 private Date lastDate;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBussType() {
		return bussType;
	}

	public void setBussType(Integer bussType) {
		this.bussType = bussType;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Long getItemType() {
		return itemType;
	}

	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getYamount() {
		return yamount;
	}

	public void setYamount(BigDecimal yamount) {
		this.yamount = yamount;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{id,bussType,formId,itemType,direction,amount,yamount,lastDate,status,remark,creator,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","bussType","formId","itemType","direction","amount","yamount","lastDate#yyyy-MM-dd","status","remark","creator","createTime#yyyy-MM-dd"};
	}

}
