package com.cmw.entity.crm;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 客户黑名单
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户黑名单实体",createDate="2012-12-12T00:00:00",author="程明卫")
@Entity
@Table(name="crm_CustBlack")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class CustBlackEntity extends IdBaseEntity {
	
	 @Description(remark="客户类型")
	 @Column(name="custType" ,nullable=false )
	 private Integer custType;

	 @Description(remark="客户基础信息ID")
	 @Column(name="baseId" ,nullable=false )
	 private Long baseId;
	 
	 @Description(remark="客户ID")
	 @Column(name="customerId" ,nullable=false )
	 private Long customerId;

	 @Description(remark="纳入黑名单原因")
	 @Column(name="reason" ,nullable=false ,length=255 )
	 private String reason;




	public CustBlackEntity() {

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
	  * 获取客户基础信息ID的值
	 * @return 返回客户基础信息ID的值
	**/
	public Long getBaseId() {
		return baseId;
	}

	/**
	  * 设置客户基础信息ID的值
	 * @param 	baseId	 客户基础信息ID的值
	**/
	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}


	/**
	  * 设置客户ID的值
	 * @param 	customerId	 客户ID
	**/
	public void setCustomerId(Long  customerId){
		 this.customerId=customerId;
 	}

	/**
	  * 获取客户ID的值
	 * @return 返回客户ID的值
	**/
	public Long getCustomerId(){
		 return customerId;
 	}

	/**
	  * 设置纳入黑名单原因的值
	 * @param 	reason	 纳入黑名单原因
	**/
	public void setReason(String  reason){
		 this.reason=reason;
 	}

	/**
	  * 获取纳入黑名单原因的值
	 * @return 返回纳入黑名单原因的值
	**/
	public String getReason(){
		 return reason;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{custType,baseId,customerId,reason};
	}

	@Override
	public String[] getFields() {
		return new String[]{"custType","baseId","customerId","reason"};
	}

}
