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
 * 表内表外
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外实体",createDate="2013-02-28T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Taboutside")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class TaboutsideEntity extends IdBaseEntity {
	
	
	 @Description(remark="合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="首期逾期计划ID")
	 @Column(name="planId")
	 private Long planId;

	 @Description(remark="本月收款期次")
	 @Column(name="monthPharses" )
	 private Integer monthPharses;

	 @Description(remark="累计逾期期次")
	 @Column(name="totalPharses" )
	 private Integer totalPharses;

	 @Description(remark="逾期本金")
	 @Column(name="amounts" ,nullable=false )
	 private BigDecimal amounts;

	 @Description(remark="逾期利息")
	 @Column(name="iamounts" ,nullable=false )
	 private BigDecimal iamounts = new BigDecimal(0);

	 @Description(remark="逾期管理费")
	 @Column(name="mamounts" ,nullable=false )
	 private BigDecimal mamounts = new BigDecimal(0);

	 @Description(remark="累计罚息")
	 @Column(name="pamounts" ,nullable=false )
	 private BigDecimal pamounts = new BigDecimal(0);

	 @Description(remark="累计滞纳金")
	 @Column(name="damounts" ,nullable=false )
	 private BigDecimal damounts = new BigDecimal(0);

	 @Description(remark="五级分类")
	 @Column(name="flevel" ,nullable=false )
	 private Long flevel;

	 @Description(remark="表内表外")
	 @Column(name="inouttype" ,nullable=false )
	 private Integer inouttype = 0;

	 @Description(remark="转化方向")
	 @Column(name="direction" ,nullable=false )
	 private Integer direction = 0;

	 @Description(remark="核销状态")
	 @Column(name="xstatus" ,nullable=false )
	 private Integer xstatus = 2;

	 @Description(remark="核销日期")
	 @Column(name="xdate" )
	 private Date xdate;

	 @Description(remark="核销人")
	 @Column(name="xman" ,length=20 )
	 private String xman;

	 @Description(remark="最后转化时间")
	 @Column(name="changeDate" )
	 private Date changeDate;

	 @Description(remark="财务凭证编号")
	 @Column(name="fcnumber" ,length=30 )
	 private String fcnumber;


	public TaboutsideEntity() {

	}

	
	/**
	  * 设置合同ID的值
	 * @param 	contractId	 合同ID
	**/
	public void setContractId(Long  contractId){
		 this.contractId=contractId;
 	}

	/**
	  * 获取合同ID的值
	 * @return 返回合同ID的值
	**/
	public Long getContractId(){
		 return contractId;
 	}

	/**
	  * 设置首期逾期计划ID的值
	 * @param 	planId	 首期逾期计划ID
	**/
	public void setPlanId(Long  planId){
		 this.planId=planId;
 	}

	/**
	  * 获取首期逾期计划ID的值
	 * @return 返回首期逾期计划ID的值
	**/
	public Long getPlanId(){
		 return planId;
 	}

	/**
	  * 设置本月收款期次的值
	 * @param 	monthPharses	 本月收款期次
	**/
	public void setMonthPharses(Integer  monthPharses){
		 this.monthPharses=monthPharses;
 	}

	/**
	  * 获取本月收款期次的值
	 * @return 返回本月收款期次的值
	**/
	public Integer getMonthPharses(){
		 return monthPharses;
 	}

	/**
	  * 设置累计逾期期次的值
	 * @param 	totalPharses	 累计逾期期次
	**/
	public void setTotalPharses(Integer  totalPharses){
		 this.totalPharses=totalPharses;
 	}

	/**
	  * 获取累计逾期期次的值
	 * @return 返回累计逾期期次的值
	**/
	public Integer getTotalPharses(){
		 return totalPharses;
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
	  * 设置累计罚息的值
	 * @param 	pamounts	 累计罚息
	**/
	public void setPamounts(BigDecimal  pamounts){
		 this.pamounts=pamounts;
 	}

	/**
	  * 获取累计罚息的值
	 * @return 返回累计罚息的值
	**/
	public BigDecimal getPamounts(){
		 return pamounts;
 	}

	/**
	  * 设置累计滞纳金的值
	 * @param 	damounts	 累计滞纳金
	**/
	public void setDamounts(BigDecimal  damounts){
		 this.damounts=damounts;
 	}

	/**
	  * 获取累计滞纳金的值
	 * @return 返回累计滞纳金的值
	**/
	public BigDecimal getDamounts(){
		 return damounts;
 	}

	/**
	  * 设置五级分类的值
	 * @param 	flevel	 五级分类
	**/
	public void setFlevel(Long  flevel){
		 this.flevel=flevel;
 	}

	/**
	  * 获取五级分类的值
	 * @return 返回五级分类的值
	**/
	public Long getFlevel(){
		 return flevel;
 	}

	/**
	  * 设置表内表外的值
	 * @param 	inouttype	 表内表外
	**/
	public void setInouttype(Integer  inouttype){
		 this.inouttype=inouttype;
 	}

	/**
	  * 获取表内表外的值
	 * @return 返回表内表外的值
	**/
	public Integer getInouttype(){
		 return inouttype;
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
	  * 设置核销状态的值
	 * @param 	xstatus	 核销状态
	**/
	public void setXstatus(Integer  xstatus){
		 this.xstatus=xstatus;
 	}

	/**
	  * 获取核销状态的值
	 * @return 返回核销状态的值
	**/
	public Integer getXstatus(){
		 return xstatus;
 	}

	/**
	  * 设置核销日期的值
	 * @param 	xdate	 核销日期
	**/
	public void setXdate(Date  xdate){
		 this.xdate=xdate;
 	}

	/**
	  * 获取核销日期的值
	 * @return 返回核销日期的值
	**/
	public Date getXdate(){
		 return xdate;
 	}

	/**
	  * 设置核销人的值
	 * @param 	xman	 核销人
	**/
	public void setXman(String  xman){
		 this.xman=xman;
 	}

	/**
	  * 获取核销人的值
	 * @return 返回核销人的值
	**/
	public String getXman(){
		 return xman;
 	}

	/**
	  * 设置最后转化时间的值
	 * @param 	changeDate	 最后转化时间
	**/
	public void setChangeDate(Date  changeDate){
		 this.changeDate=changeDate;
 	}

	/**
	  * 获取最后转化时间的值
	 * @return 返回最后转化时间的值
	**/
	public Date getChangeDate(){
		 return changeDate;
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
		return new Object[]{id,contractId,planId,monthPharses,totalPharses,amounts,iamounts,mamounts,pamounts,damounts,flevel,inouttype,direction,xstatus,xdate,xman,changeDate,fcnumber,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","contractId","planId","monthPharses","totalPharses","amounts","iamounts","mamounts","pamounts","damounts","flevel","inouttype","direction","xstatus","xdate","xman","changeDate","fcnumber","isenabled"};
	}

}
