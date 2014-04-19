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
 * 预收帐款实收记录
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款实收记录实体",createDate="2013-02-28T00:00:00",author="程明卫")
@Entity
@Table(name="fc_BfRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BfRecordsEntity extends IdBaseEntity {
	
	
	 @Description(remark="预收帐款ID")
	 @Column(name="bfamountId" ,nullable=false )
	 private Long bfamountId;

	 @Description(remark="本次收款金额")
	 @Column(name="cat" ,nullable=false )
	 private BigDecimal cat = new BigDecimal(0);

	 @Description(remark="已扣取金额")
	 @Column(name="netamount" ,nullable=false )
	 private BigDecimal netamount = new BigDecimal(0);

	 @Description(remark="收款日期")
	 @Column(name="rectDate" ,nullable=false )
	 private Date rectDate;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;


	public BfRecordsEntity() {

	}

	
	/**
	  * 设置预收帐款ID的值
	 * @param 	bfamountId	 预收帐款ID
	**/
	public void setBfamountId(Long  bfamountId){
		 this.bfamountId=bfamountId;
 	}

	/**
	  * 获取预收帐款ID的值
	 * @return 返回预收帐款ID的值
	**/
	public Long getBfamountId(){
		 return bfamountId;
 	}

	/**
	  * 设置本次收款金额的值
	 * @param 	cat	 本次收款金额
	**/
	public void setCat(BigDecimal  cat){
		 this.cat=cat;
 	}

	/**
	  * 获取本次收款金额的值
	 * @return 返回本次收款金额的值
	**/
	public BigDecimal getCat(){
		 return cat;
 	}

	/**
	  * 设置已扣取金额的值
	 * @param 	netamount	 已扣取金额
	**/
	public void setNetamount(BigDecimal  netamount){
		 this.netamount=netamount;
 	}

	/**
	  * 获取已扣取金额的值
	 * @return 返回已扣取金额的值
	**/
	public BigDecimal getNetamount(){
		 return netamount;
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
		return new Object[]{getId(),bfamountId,cat,netamount,rectDate,fcnumber,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","bfamountId","cat","netamount","rectDate","fcnumber","isenabled"};
	}

}
