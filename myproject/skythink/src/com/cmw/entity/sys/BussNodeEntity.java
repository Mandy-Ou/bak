package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 业务流程节点
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务流程节点实体",createDate="2012-11-28T00:00:00",author="程明卫")
@Entity
@Table(name="ts_BussNode")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class BussNodeEntity extends IdBaseEntity {
	
	
	 @Description(remark="所属分类")
	 @Column(name="inType" ,nullable=false )
	 private Integer inType;



	 @Description(remark="业务ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="流程定义ID")
	 @Column(name="pdid" ,nullable=false ,length=50 )
	 private String pdid;

	 @Description(remark="节点ID")
	 @Column(name="nodeId" ,nullable=false ,length=50 )
	 private String nodeId;

	 @Description(remark="节点type")
	 @Column(name="nodeType" ,nullable=false ,length=50 )
	 private String nodeType;

	 @Description(remark="节点名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;



	 @Description(remark="是否可配置")
	 @Column(name="isCfg" ,nullable=false )
	 private Integer isCfg = 1;




	public BussNodeEntity() {

	}

	
	/**
	  * 设置所属分类的值
	 * @param 	inType	 所属分类
	**/
	public void setInType(Integer  inType){
		 this.inType=inType;
 	}

	/**
	  * 获取所属分类的值
	 * @return 返回所属分类的值
	**/
	public Integer getInType(){
		 return inType;
 	}

	/**
	  * 设置业务ID的值
	 * @param 	formId	 业务ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取业务ID的值
	 * @return 返回业务ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	
	public String getPdid() {
		return pdid;
	}


	public void setPdid(String pdid) {
		this.pdid = pdid;
	}


	/**
	  * 设置节点ID的值
	 * @param 	nodeId	 节点ID
	**/
	public void setNodeId(String  nodeId){
		 this.nodeId=nodeId;
 	}

	/**
	  * 获取节点ID的值
	 * @return 返回节点ID的值
	**/
	public String getNodeId(){
		 return nodeId;
 	}
	
	
	/**
	  * 设置节点名称的值
	 * @param 	name	 节点名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}
	
	/**
	  * 获取节点类型的值
	 * @return 返回节点类型的值
	**/
	public String getNodeType() {
		return nodeType;
	}

	/**
	  * 设置节点类型的值
	 * @param 	nodeType	 节点类型
	**/
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}


	/**
	  * 获取节点名称的值
	 * @return 返回节点名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置是否可配置的值
	 * @param 	isCfg	 是否可配置
	**/
	public void setIsCfg(Integer  isCfg){
		 this.isCfg=isCfg;
 	}

	/**
	  * 获取是否可配置的值
	 * @return 返回是否可配置的值
	**/
	public Integer getIsCfg(){
		 return isCfg;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),inType,formId,nodeId,pdid,nodeType,name,isCfg};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","inType","formId","pdid","nodeId","nodeType","name","isCfg"};
	}

}
