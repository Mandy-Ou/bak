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
 * 付息申请表
 * @author 李听
 * @date 2014-05-08T00:00:00
 */
@Description(remark="付息申请表实体",createDate="2014-05-08T00:00:00",author="李听")
@Entity
@Table(name="fu_PayIntApply")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PayIntApplyEntity extends IdBaseEntity {
	
	
	 @Description(remark="申请人")
	 @Column(name="appMan" ,nullable=false )
	 private Long appMan;

	 @Description(remark="申请日期")
	 @Column(name="appDate" ,nullable=false )
	 private Date appDate;

	 @Description(remark="委托客户笔数")
	 @Column(name="icount" ,nullable=false )
	 private Integer icount = 0;

	 @Description(remark="付息金额合计")
	 @Column(name="totalAmount" ,nullable=false )
	 private BigDecimal totalAmount = new BigDecimal(0);

	 @Description(remark="子业务流程ID")
	 @Column(name="breed" ,nullable=false )
	 private Long breed;

	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,length=50 )
	 private String procId;

	 @Description(remark="撤资状态")
	 @Column(name="xstatus" )
	 private Integer xstatus = 1;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 0;


	public PayIntApplyEntity() {

	}

	
	/**
	  * 设置申请人的值
	 * @param 	appMan	 申请人
	**/
	public void setAppMan(Long  appMan){
		 this.appMan=appMan;
 	}

	/**
	  * 获取申请人的值
	 * @return 返回申请人的值
	**/
	public Long getAppMan(){
		 return appMan;
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
	  * 设置委托客户笔数的值
	 * @param 	icount	 委托客户笔数
	**/
	public void setIcount(Integer  icount){
		 this.icount=icount;
 	}

	/**
	  * 获取委托客户笔数的值
	 * @return 返回委托客户笔数的值
	**/
	public Integer getIcount(){
		 return icount;
 	}

	/**
	  * 设置付息金额合计的值
	 * @param 	totalAmount	 付息金额合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取付息金额合计的值
	 * @return 返回付息金额合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
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
	  * 设置撤资状态的值
	 * @param 	xstatus	 撤资状态
	**/
	public void setXstatus(Integer  xstatus){
		 this.xstatus=xstatus;
 	}

	/**
	  * 获取撤资状态的值
	 * @return 返回撤资状态的值
	**/
	public Integer getXstatus(){
		 return xstatus;
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



	@Override
	public Object[] getDatas() {
		return new Object[]{appMan,appDate,icount,totalAmount,breed,procId,xstatus,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"appMan","appDate","icount","totalAmount","breed","procId","xstatus","status"};
	}

}
