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
 * 表内外转化记录
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内外转化记录实体",createDate="2013-02-28T00:00:00",author="程明卫")
@Entity
@Table(name="fc_OutsideRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class OutsideRecordsEntity extends IdBaseEntity {
	
	
	 @Description(remark="表内表外ID")
	 @Column(name="tabId" ,nullable=false )
	 private Long tabId;

	 @Description(remark="对内计息表ID")
	 @Column(name="planId" ,nullable=false )
	 private Long planId;

	 @Description(remark="本息逾期天数")
	 @Column(name="latedays" ,nullable=false )
	 private Integer latedays = 0;

	 @Description(remark="逾期本金")
	 @Column(name="amounts" ,nullable=false ,scale=2)
	 private BigDecimal amounts = new BigDecimal(0);

	 @Description(remark="逾期利息")
	 @Column(name="iamounts" ,nullable=false ,scale=2)
	 private BigDecimal iamounts = new BigDecimal(0);

	 @Description(remark="逾期管理费")
	 @Column(name="mamounts" ,nullable=false ,scale=2)
	 private BigDecimal mamounts = new BigDecimal(0);

	 @Description(remark="转化日期")
	 @Column(name="changedate" ,nullable=false )
	 private Date changedate;

	 @Description(remark="上次转化方向")
	 @Column(name="prevDirection" )
	 private Integer prevDirection;

	 @Description(remark="转化方向")
	 @Column(name="direction" ,nullable=false )
	 private Integer direction;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;


	public OutsideRecordsEntity() {

	}

	
	/**
	  * 设置表内表外ID的值
	 * @param 	tabId	 表内表外ID
	**/
	public void setTabId(Long  tabId){
		 this.tabId=tabId;
 	}

	/**
	  * 获取表内表外ID的值
	 * @return 返回表内表外ID的值
	**/
	public Long getTabId(){
		 return tabId;
 	}

	/**
	  * 设置对内计息表ID的值
	 * @param 	planId	 对内计息表ID
	**/
	public void setPlanId(Long  planId){
		 this.planId=planId;
 	}

	/**
	  * 获取对内计息表ID的值
	 * @return 返回对内计息表ID的值
	**/
	public Long getPlanId(){
		 return planId;
 	}

	/**
	  * 设置本息逾期天数的值
	 * @param 	latedays	 本息逾期天数
	**/
	public void setLatedays(Integer  latedays){
		 this.latedays=latedays;
 	}

	/**
	  * 获取本息逾期天数的值
	 * @return 返回本息逾期天数的值
	**/
	public Integer getLatedays(){
		 return latedays;
 	}

	/**
	  * 设置逾期本金的值
	 * @param 	amounts	 逾期本金
	**/
	public void setAmounts(BigDecimal  amounts){
		 this.amounts=amounts;
 	}

	/**
	  * 获取逾期本金的值
	 * @return 返回逾期本金的值
	**/
	public BigDecimal getAmounts(){
		 return amounts;
 	}

	/**
	  * 设置逾期利息的值
	 * @param 	iamounts	 逾期利息
	**/
	public void setIamounts(BigDecimal  iamounts){
		 this.iamounts=iamounts;
 	}

	/**
	  * 获取逾期利息的值
	 * @return 返回逾期利息的值
	**/
	public BigDecimal getIamounts(){
		 return iamounts;
 	}

	/**
	  * 设置逾期管理费的值
	 * @param 	mamounts	 逾期管理费
	**/
	public void setMamounts(BigDecimal  mamounts){
		 this.mamounts=mamounts;
 	}

	/**
	  * 获取逾期管理费的值
	 * @return 返回逾期管理费的值
	**/
	public BigDecimal getMamounts(){
		 return mamounts;
 	}

	/**
	  * 设置转化日期的值
	 * @param 	changedate	 转化日期
	**/
	public void setChangedate(Date  changedate){
		 this.changedate=changedate;
 	}

	/**
	  * 获取转化日期的值
	 * @return 返回转化日期的值
	**/
	public Date getChangedate(){
		 return changedate;
 	}

	/**
	  * 设置上次转化方向的值
	 * @param 	prevDirection	 上次转化方向
	**/
	public void setPrevDirection(Integer  prevDirection){
		 this.prevDirection=prevDirection;
 	}

	/**
	  * 获取上次转化方向的值
	 * @return 返回上次转化方向的值
	**/
	public Integer getPrevDirection(){
		 return prevDirection;
 	}

	/**
	  * 设置转化方向的值
	 * @param 	direction	 转化方向
	**/
	public void setDirection(Integer  direction){
		 this.direction=direction;
 	}

	/**
	  * 获取转化方向的值
	 * @return 返回转化方向的值
	**/
	public Integer getDirection(){
		 return direction;
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
		return new Object[]{id,tabId,planId,latedays,amounts,iamounts,mamounts,changedate,prevDirection,direction,fcnumber,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","tabId","planId","latedays","amounts","iamounts","mamounts","changedate","prevDirection","direction","fcnumber","isenabled"};
	}

}
