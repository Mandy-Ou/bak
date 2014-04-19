package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 节点表单配置
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="节点表单配置实体",createDate="2012-11-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_FormCfg")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FormCfgEntity extends IdBaseEntity {
	
	
	 @Description(remark="节点配置ID")
	 @Column(name="nodeId" ,nullable=false )
	 private Long nodeId;
	 
	 @Description(remark="事项类型")
	 @Column(name="opType" ,nullable=false )
	 private Integer opType;

	 @Description(remark="表单ID")
	 @Column(name="custFormId" ,nullable=false )
	 private Long custFormId;

	 @Description(remark="功能权限")
	 @Column(name="funRights" ,nullable=false ,length=100 )
	 private String funRights;

	 @Description(remark="业务排序")
	 @Column(name="orderNo" ,nullable=false)
	 private Integer orderNo = 0;

	public FormCfgEntity() {

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
	  * 设置功能权限的值
	 * @param 	funRights	 功能权限
	**/
	public void setFunRights(String  funRights){
		 this.funRights=funRights;
 	}

	/**
	  * 获取功能权限的值
	 * @return 返回功能权限的值
	**/
	public String getFunRights(){
		 return funRights;
 	}


	/**
	  * 获取事项类型的值
	 * @return 返回事项类型的值
	**/
	public Integer getOpType() {
		return opType;
	}

	/**
	  * 设置事项类型的值
	 * @param 	opType	 事项类型
	**/
	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	/**
	  * 获取业务排序的值
	 * @return 返回业务排序的值
	**/
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	  * 设置业务排序的值
	 * @param 	orderNo	 业务排序
	**/
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


	@Override
	public Object[] getDatas() {
		return new Object[]{id,nodeId,opType,custFormId,funRights,orderNo,isenabled};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","nodeId","opType","custFormId","funRights","isenabled"};
	}

	/**
	 * 事项类型 [1:必做业务]
	 */
	public static final int OPTYPE_1 = 1;
	/**
	 * 事项类型 [2:选做业务]
	 */
	public static final int OPTYPE_2 = 2;
}
