package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 会签记录
 * @author 程明卫
 * @version V2.7
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签记录实体",createDate="2013-11-22T00:00:00",author="程明卫")
@Entity
@Table(name="ts_Countersign")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CountersignEntity extends IdBaseEntity {
	
	
	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,nullable=false ,length=50 )
	 private String procId;

	 @Description(remark="流程节点ID")
	 @Column(name="bussNodeId" ,nullable=false )
	 private Long bussNodeId;

	 @Description(remark="任务实例ID")
	 @Column(name="tiid" ,length=50 )
	 private String tiid;
	 
	 @Description(remark="审批人")
	 @Column(name="auditUser" )
	 private Long auditUser;

	 @Description(remark="审批顺序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo = 0;

	 @Description(remark="审批结果")
	 @Column(name="result" ,length=60 )
	 private String result;

	 @Description(remark="审批记录ID")
	 @Column(name="recordId" )
	 private Long recordId;


	public CountersignEntity() {

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
	  * 设置流程节点ID的值
	 * @param 	bussNodeId	 流程节点ID
	**/
	public void setBussNodeId(Long  bussNodeId){
		 this.bussNodeId=bussNodeId;
 	}

	/**
	  * 获取流程节点ID的值
	 * @return 返回流程节点ID的值
	**/
	public Long getBussNodeId(){
		 return bussNodeId;
 	}

	/**
	  * 设置任务实例ID的值
	 * @param 	tiid	 任务实例ID
	**/
	public void setTiid(String  tiid){
		 this.tiid=tiid;
 	}

	/**
	  * 获取任务实例ID的值
	 * @return 返回任务实例ID的值
	**/
	public String getTiid(){
		 return tiid;
 	}



	/**
	  * 设置审批人的值
	 * @param 	auditUser	 审批人
	**/
	public void setAuditUser(Long  auditUser){
		 this.auditUser=auditUser;
 	}

	/**
	  * 获取审批人的值
	 * @return 返回审批人的值
	**/
	public Long getAuditUser(){
		 return auditUser;
 	}

	/**
	  * 设置审批顺序的值
	 * @param 	orderNo	 审批顺序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取审批顺序的值
	 * @return 返回审批顺序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}

	/**
	  * 设置审批结果的值
	 * @param 	result	 审批结果
	**/
	public void setResult(String  result){
		 this.result=result;
 	}

	/**
	  * 获取审批结果的值
	 * @return 返回审批结果的值
	**/
	public String getResult(){
		 return result;
 	}

	/**
	  * 设置审批记录ID的值
	 * @param 	recordId	 审批记录ID
	**/
	public void setRecordId(Long  recordId){
		 this.recordId=recordId;
 	}

	/**
	  * 获取审批记录ID的值
	 * @return 返回审批记录ID的值
	**/
	public Long getRecordId(){
		 return recordId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,procId,bussNodeId,tiid,auditUser,orderNo,result,recordId,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","procId","bussNodeId","tiid","auditUser","orderNo","result","recordId","isenabled"};
	}

	/**
	 * result [-1:系统作废任务]
	 */
	public static final String RESULT_BEAR1 = "-1";
	/**
	 * result [1:通过]
	 */
	public static final String RESULT_1 = "1";
	/**
	 * result [2:拒绝]
	 */
	public static final String RESULT_2 = "2";
	/**
	 * result [3:拒绝]
	 */
	public static final String RESULT_3 = "3";
}
