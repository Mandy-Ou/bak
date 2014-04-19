package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 并行令牌记录
 * @author 程明卫
 * @date 2013-12-07T00:00:00
 */
@Description(remark="并行令牌记录实体",createDate="2013-12-07T00:00:00",author="程明卫")
@Entity
@Table(name="ts_TokenRecords")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class TokenRecordsEntity extends IdBaseEntity {
	
	 @Description(remark="流程实例ID")
	 @Column(name="procId" ,nullable=false ,length=50 )
	 private String procId;

	 @Description(remark="流程节点ID")
	 @Column(name="bussNodeId" ,nullable=false )
	 private Long bussNodeId;

	 @Description(remark="令牌标识")
	 @Column(name="token" ,nullable=false ,length=50 )
	 private String token;

	 @Description(remark="状态")
	 @Column(name="status" ,nullable=false )
	 private Integer status;


	public TokenRecordsEntity() {

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
	  * 设置令牌标识的值
	 * @param 	token	 令牌标识
	**/
	public void setToken(String  token){
		 this.token=token;
 	}

	/**
	  * 获取令牌标识的值
	 * @return 返回令牌标识的值
	**/
	public String getToken(){
		 return token;
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
		return new Object[]{procId,bussNodeId,token,status};
	}

	@Override
	public String[] getFields() {
		return new String[]{"procId","bussNodeId","token","status"};
	}

}
