package com.cmw.entity.sys;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 表单记录
 * @author 程明卫
 * @date 2013-01-08T00:00:00
 */
@Description(remark="表单记录实体",createDate="2013-01-08T00:00:00",author="程明卫")
@Entity
@Table(name="ts_FormRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FormRecordsEntity extends IdEntity {
	 @Description(remark="节点配置ID")
	 @Column(name="nodeId" ,nullable=false )
	 private Long nodeId;

	 @Description(remark="表单ID")
	 @Column(name="custFormId" ,nullable=false )
	 private Long custFormId;
	 
	 @Description(remark="用 户ID")
	 @Column(name="userId")
	 private Long userId;
	 
	 @Description(remark="业务编号")
	 @Column(name="bussCode")
	 private String bussCode;
	 
	 @Description(remark="业务单ID")
	 @Column(name="formId")
	 private String formId;
	 
	 @Description(remark="创建日期")
	 @Column(name="createTime" ,nullable=false )
	 private Date createTime = new Date();
	 
	public FormRecordsEntity() {

	}

	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	/**
	  * 设置节点配置ID的值
	 * @param 	nodeId	 节点配置ID
	**/
	public void setNodeId(Long  nodeId){
		 this.nodeId=nodeId;
 	}

	/**
	  * 获取节点配置ID的值
	 * @return 返回节点配置ID的值
	**/
	public Long getNodeId(){
		 return nodeId;
 	}

	/**
	  * 设置表单ID的值
	 * @param 	custFormId	 表单ID
	**/
	public void setCustFormId(Long  custFormId){
		 this.custFormId=custFormId;
 	}

	/**
	  * 获取表单ID的值
	 * @return 返回表单ID的值
	**/
	public Long getCustFormId(){
		 return custFormId;
 	}

	
	/**
	  * 获取业务编号的值
	 * @return 返回业务编号的值
	**/
	public String getBussCode() {
		return bussCode;
	}


	/**
	  * 设置业务编号的值
	 * @param 	bussCode	 业务编号
	**/
	public void setBussCode(String bussCode) {
		this.bussCode = bussCode;
	}


	/**
	  * 获取业务单ID的值
	 * @return 返回业务单ID的值
	**/
	public String getFormId() {
		return formId;
	}


	/**
	  * 设置业务单ID的值
	 * @param 	formId	 业务单ID
	**/
	public void setFormId(String formId) {
		this.formId = formId;
	}



	/**
	  * 获取创建日期的值
	 * @return 返回创建日期的值
	**/
	public Date getCreateTime() {
		return createTime;
	}


	/**
	  * 设置创建日期的值
	 * @param 	createTime	创建日期
	**/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),nodeId,custFormId,userId,bussCode,formId,createTime};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","nodeId","custFormId","userId","bussCode","formId","createTime"};
	}

}
