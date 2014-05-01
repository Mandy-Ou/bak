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
 * 息费豁免申请
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免申请实体",createDate="2013-09-14T00:00:00",author="程明卫")
@Entity
@Table(name="fc_Exempt")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ExemptEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=20 )
	 private String procId;

	 @Description(remark="借款合同ID")
	 @Column(name="contractId" ,nullable=false )
	 private Long contractId;

	 @Description(remark="豁免类别")
	 @Column(name="etype" ,nullable=false )
	 private Integer etype;

	 @Description(remark="豁免项目")
	 @Column(name="exeItems" ,length=20 )
	 private String exeItems;

	 @Description(remark="是否返还息费")
	 @Column(name="isBackAmount" ,nullable=false )
	 private Integer isBackAmount = 2;

	 @Description(remark="豁免起始日期")
	 @Column(name="startDate")
	 private Date startDate;

	 @Description(remark="豁免截止日期")
	 @Column(name="endDate")
	 private Date endDate;

	 @Description(remark="豁免息费合计")
	 @Column(name="totalAmount" ,nullable=false )
	 private BigDecimal totalAmount = new BigDecimal("0");

	 @Description(remark="豁免申请理由")
	 @Column(name="reason" ,length=1000 )
	 private String reason;

	 @Description(remark="申请日期")
	 @Column(name="appDate" ,nullable=false )
	 private Date appDate;

	 @Description(remark="申请部门")
	 @Column(name="appDept" ,length=50 )
	 private String appDept;

	 @Description(remark="经办人")
	 @Column(name="managerId" ,nullable=false )
	 private Long managerId;

	 @Description(remark="财务豁免人")
	 @Column(name="cashier" )
	 private Long cashier;

	 @Description(remark="财务豁免日期")
	 @Column(name="exeDate" )
	 private Date exeDate;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;

	 @Description(remark="收款状态")
	 @Column(name="xstatus" ,nullable=false )
	 private Integer xstatus = 0;


	public ExemptEntity() {

	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置子业务流程ID的值
	 * @param 	breed	 子业务流程ID
	**/
	public void setBreed(Long  breed){
		 this.breed=breed;
 	}

	/**
	  * 获取子业务流程ID的值
	 * @return 返回子业务流程ID的值
	**/
	public Long getBreed(){
		 return breed;
 	}

	/**
	  * 设置流程实例ID的值
	 * @param 	procId	 流程实例ID
	**/
	public void setProcId(String  procId){
		 this.procId=procId;
 	}

	/**
	  * 获取流程实例ID的值
	 * @return 返回流程实例ID的值
	**/
	public String getProcId(){
		 return procId;
 	}

	/**
	  * 设置借款合同ID的值
	 * @param 	contractId	 借款合同ID
	**/
	public void setContractId(Long  contractId){
		 this.contractId=contractId;
 	}

	/**
	  * 获取借款合同ID的值
	 * @return 返回借款合同ID的值
	**/
	public Long getContractId(){
		 return contractId;
 	}

	/**
	  * 设置豁免类别的值
	 * @param 	etype	 豁免类别
	**/
	public void setEtype(Integer  etype){
		 this.etype=etype;
 	}

	/**
	  * 获取豁免类别的值
	 * @return 返回豁免类别的值
	**/
	public Integer getEtype(){
		 return etype;
 	}

	/**
	  * 设置豁免项目的值
	 * @param 	exeItems	 豁免项目
	**/
	public void setExeItems(String  exeItems){
		 this.exeItems=exeItems;
 	}

	/**
	  * 获取豁免项目的值
	 * @return 返回豁免项目的值
	**/
	public String getExeItems(){
		 return exeItems;
 	}

	/**
	  * 设置是否返还息费的值
	 * @param 	isBackAmount	 是否返还息费
	**/
	public void setIsBackAmount(Integer  isBackAmount){
		 this.isBackAmount=isBackAmount;
 	}

	/**
	  * 获取是否返还息费的值
	 * @return 返回是否返还息费的值
	**/
	public Integer getIsBackAmount(){
		 return isBackAmount;
 	}

	/**
	  * 设置豁免起始日期的值
	 * @param 	startDate	 豁免起始日期
	**/
	public void setStartDate(Date  startDate){
		 this.startDate=startDate;
 	}

	/**
	  * 获取豁免起始日期的值
	 * @return 返回豁免起始日期的值
	**/
	public Date getStartDate(){
		 return startDate;
 	}

	/**
	  * 设置豁免截止日期的值
	 * @param 	endDate	 豁免截止日期
	**/
	public void setEndDate(Date  endDate){
		 this.endDate=endDate;
 	}

	/**
	  * 获取豁免截止日期的值
	 * @return 返回豁免截止日期的值
	**/
	public Date getEndDate(){
		 return endDate;
 	}

	/**
	  * 设置豁免息费合计的值
	 * @param 	totalAmount	 豁免息费合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取豁免息费合计的值
	 * @return 返回豁免息费合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}

	/**
	  * 设置豁免申请理由的值
	 * @param 	reason	 豁免申请理由
	**/
	public void setReason(String  reason){
		 this.reason=reason;
 	}

	/**
	  * 获取豁免申请理由的值
	 * @return 返回豁免申请理由的值
	**/
	public String getReason(){
		 return reason;
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

	/**
	  * 设置申请部门的值
	 * @param 	appDept	 申请部门
	**/
	public void setAppDept(String  appDept){
		 this.appDept=appDept;
 	}

	/**
	  * 获取申请部门的值
	 * @return 返回申请部门的值
	**/
	public String getAppDept(){
		 return appDept;
 	}

	/**
	  * 设置经办人的值
	 * @param 	managerId	 经办人
	**/
	public void setManagerId(Long  managerId){
		 this.managerId=managerId;
 	}

	/**
	  * 获取经办人的值
	 * @return 返回经办人的值
	**/
	public Long getManagerId(){
		 return managerId;
 	}

	/**
	  * 设置财务豁免人的值
	 * @param 	cashier	 财务豁免人
	**/
	public void setCashier(Long  cashier){
		 this.cashier=cashier;
 	}

	/**
	  * 获取财务豁免人的值
	 * @return 返回财务豁免人的值
	**/
	public Long getCashier(){
		 return cashier;
 	}

	/**
	  * 设置财务豁免日期的值
	 * @param 	exeDate	 财务豁免日期
	**/
	public void setExeDate(Date  exeDate){
		 this.exeDate=exeDate;
 	}

	/**
	  * 获取财务豁免日期的值
	 * @return 返回财务豁免日期的值
	**/
	public Date getExeDate(){
		 return exeDate;
 	}

	/**
	  * 设置状态的值
	 * @param 	status	 状态
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态的值
	 * @return 返回状态的值
	**/
	public Integer getStatus(){
		 return status;
 	}

	/**
	  * 设置收款状态的值
	 * @param 	xstatus	 收款状态
	**/
	public void setXstatus(Integer  xstatus){
		 this.xstatus=xstatus;
 	}

	/**
	  * 获取收款状态的值
	 * @return 返回收款状态的值
	**/
	public Integer getXstatus(){
		 return xstatus;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,code,breed,procId,contractId,etype,exeItems,isBackAmount,startDate,endDate,totalAmount,reason,appDate,appDept,managerId,cashier,exeDate,status,xstatus,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","code","breed","procId","contractId","etype","exeItems","isBackAmount","startDate","endDate","totalAmount","reason","appDate","appDept","managerId","cashier","exeDate","status","xstatus","isenabled"};
	}

}
