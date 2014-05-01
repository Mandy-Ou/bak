package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 其它信息
 * @author pdh
 * @date 2013-03-31T00:00:00
 */
@Description(remark="其它信息实体",createDate="2013-03-31T00:00:00",author="pdh")
@Entity
@Table(name="crm_OtherInfo")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class OtherInfoEntity extends IdBaseEntity {
	
	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;
	
	 @Description(remark="业务类型")
	 @Column(name="formType" ,nullable=false )
	 private Integer formType;

	 @Description(remark="材料名称")
	 @Column(name="otherName" ,nullable=false ,length=50 )
	 private String otherName;
	 
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;
	public OtherInfoEntity() {

	}

	
	/**
	  * 设置客户类型的值
	 * @param 	custType	 客户类型
	**/
	public void setCustType(Integer  custType){
		 this.custType=custType;
	}

	/**
	  * 获取客户类型的值
	 * @return 返回客户类型的值
	**/
	public Integer getCustType(){
		 return custType;
	}
	
	/**
	  * 设置个人客户ID的值
	 * @param 	customerId	 个人客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取个人客户ID的值
	 * @return 返回个人客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}
	/**
	  * 设置业务类型的值
	 * @param 	formType	 业务类型
	**/
	public void setFormType(Integer  formType){
		 this.formType=formType;
 	}

	/**
	  * 获取业务类型的值
	 * @return 返回业务类型的值
	**/
	public Integer getFormType(){
		 return formType;
 	}

	/**
	  * 设置材料名称的值
	 * @param 	dataName	 材料名称
	**/
	public void setOtherName(String  otherName){
		 this.otherName=otherName;
 	}

	/**
	  * 获取材料名称的值
	 * @return 返回材料名称的值
	**/
	public String getOtherName(){
		 return otherName;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,custType,customerId,formType,otherName,creator,createTime,modifytime,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","custType","customerId","formType","otherName","creator","createTime#yyyy-MM-dd","modifytime#yyyy-MM-dd","remark"};
	}

}
