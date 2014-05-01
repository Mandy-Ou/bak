package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 客户分类
 * @author 程明卫
 * @date 2012-12-17T00:00:00
 */
@Description(remark="客户分类实体",createDate="2012-12-17T00:00:00",author="程明卫")
@Entity
@Table(name="crm_Category")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CategoryEntity extends IdEntity {
	
	
	 @Description(remark="客户类别")
	 @Column(name="category" ,nullable=false )
	 private Integer category;

	 @Description(remark="所属客户ID")
	 @Column(name="inCustomerId" ,nullable=false )
	 private Long inCustomerId;

	 @Description(remark="关联客户ID")
	 @Column(name="relCustomerId" ,nullable=false )
	 private Long relCustomerId;
	 
	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="唯一标识符")
	 @Column(name="uuid",length=50)
	 private String uuid;
	 
	 @Description(remark="所属项目ID")
	 @Column(name="projectId")
	 private Long projectId;


	public CategoryEntity() {

	}
	/**
	 * 获取客户类型
	 * @return
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * 设置客户类型
	 * @param custType
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}
	/**
	  * 设置客户类别的值
	 * @param 	category	 客户类别
	**/
	public void setCategory(Integer  category){
		 this.category=category;
 	}

	/**
	  * 获取客户类别的值
	 * @return 返回客户类别的值
	**/
	public Integer getCategory(){
		 return category;
 	}

	/**
	  * 设置所属客户ID的值
	 * @param 	inCustomerId	 所属客户ID
	**/
	public void setInCustomerId(Long  inCustomerId){
		 this.inCustomerId=inCustomerId;
 	}

	/**
	  * 获取所属客户ID的值
	 * @return 返回所属客户ID的值
	**/
	public Long getInCustomerId(){
		 return inCustomerId;
 	}

	/**
	  * 设置关联客户ID的值
	 * @param 	relCustomerId	 关联客户ID
	**/
	public void setRelCustomerId(Long  relCustomerId){
		 this.relCustomerId=relCustomerId;
 	}

	/**
	  * 获取关联客户ID的值
	 * @return 返回关联客户ID的值
	**/
	public Long getRelCustomerId(){
		 return relCustomerId;
 	}

	/**
	  * 获取uuid的值
	 * @return 返回uuid的值
	**/
	public String getUuid() {
		return uuid;
	}
	
	/**
	  * 设置uuid的值
	 * @param 	uuid	 唯一标识符
	**/
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	  * 获取所属项目ID的值
	 * @return 返回所属项目ID的值
	**/
	public Long getProjectId() {
		return projectId;
	}
	
	/**
	  * 获取所属项目ID的值
	 * @param projectId  所属项目ID
	**/
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	@Override
	public Object[] getDatas() {
		return new Object[]{category,inCustomerId,relCustomerId,uuid,projectId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"category","inCustomerId","relCustomerId","uuid","projectId"};
	}

}
