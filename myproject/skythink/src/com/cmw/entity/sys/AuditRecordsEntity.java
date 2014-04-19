package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 审批记录表
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批记录表实体",createDate="2012-12-26T00:00:00",author="程明卫")
@Entity
@Table(name="ts_AuditRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class AuditRecordsEntity extends IdBaseEntity {

	 @Description(remark="")
	 @Column(name="procId" ,nullable=false ,length=20)
	 private String procId;

	 @Description(remark="流程节点ID")
	 @Column(name="bussNodeId" ,nullable=false )
	 private Long bussNodeId;

	 @Description(remark="流程节点ID")
	 @Column(name="tiid" ,length=20 )
	 private String tiid;

	 @Description(remark="审批结果")
	 @Column(name="result" ,nullable=false ,length=60 )
	 private String result;

	 @Description(remark="审批意见")
	 @Column(name="approval" ,length=1500 )
	 private String approval;

	 @Description(remark="消息通知类型")
	 @Column(name="notifys" ,length=10 )
	 private String notifys;

	 @Description(remark="是否通知流程发起人")
	 @Column(name="notifyStartor")
	 private Integer notifyStartor=2;
	 
	 @Description(remark="记录类型")
	 @Column(name="recordType")
	 private Integer recordType=0;
	 
	public AuditRecordsEntity() {

	}

	/**
	  * 获取流程实例ID的值
	 * @return 返回流程实例ID的值
	**/
	public String getProcId() {
		return procId;
	}

	/**
	  * 设置流程实例ID的值
	 * @param 	procId	 流程实例ID
	**/
	public void setProcId(String procId) {
		this.procId = procId;
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
	  * 设置流程节点ID的值
	 * @param 	tiid	 流程节点ID
	**/
	public void setTiid(String  tiid){
		 this.tiid=tiid;
 	}

	/**
	  * 获取流程节点ID的值
	 * @return 返回流程节点ID的值
	**/
	public String getTiid(){
		 return tiid;
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
	  * 设置审批意见的值
	 * @param 	approval	 审批意见
	**/
	public void setApproval(String  approval){
		 this.approval=approval;
 	}

	/**
	  * 获取审批意见的值
	 * @return 返回审批意见的值
	**/
	public String getApproval(){
		 return approval;
 	}

	/**
	  * 设置消息通知类型的值
	 * @param 	notifys	 消息通知类型
	**/
	public void setNotifys(String  notifys){
		 this.notifys=notifys;
 	}

	/**
	  * 获取消息通知类型的值
	 * @return 返回消息通知类型的值
	**/
	public String getNotifys(){
		 return notifys;
 	}

	/**
	  * 获取通知流程发起人的值
	 * @return 返回通知流程发起人的值
	**/
	public Integer getNotifyStartor() {
		return notifyStartor;
	}
	
	/**
	  * 设置通知流程发起人的值
	 * @param 	notifyStartor	 通知流程发起人
	**/
	public void setNotifyStartor(Integer notifyStartor) {
		this.notifyStartor = notifyStartor;
	}

	
	/**
	  * 获取记录类型的值
	 * @return 返回记录类型的值
	**/
	public Integer getRecordType() {
		return recordType;
	}

	/**
	  * 设置记录类型的值
	 * @param 	recordType	 记录类型
	**/
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	@Override
	public Object[] getDatas() {
		return new Object[]{ id,procId,bussNodeId,tiid,result,approval,notifys,notifyStartor,recordType};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","procId","sysId","formType","formId","bussNodeId","tiid","result","approval","notifys","notifyStartor","recordType"};
	}

}
