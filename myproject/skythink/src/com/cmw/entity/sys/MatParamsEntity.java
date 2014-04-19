package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 资料项
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料项实体",createDate="2012-12-26T00:00:00",author="pdt")
@Entity
@Table(name="ts_MatParams ")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MatParamsEntity extends IdBaseEntity {
	
	
	 @Description(remark="资料标题ID")
	 @Column(name="subjectId" ,nullable=false )
	 private Long subjectId;

	 @Description(remark="资料项名称")
	 @Column(name="name" ,nullable=false ,length=200 )
	 private String name;

	 @Description(remark="是否必填项")
	 @Column(name="allowBlank" ,nullable=false )
	 private Integer allowBlank;

	 @Description(remark="是否支持附件")
	 @Column(name="isAttach" ,nullable=false )
	 private Integer isAttach = 1;

	 @Description(remark="是否需要备注")
	 @Column(name="isRemark" ,nullable=false )
	 private Integer isRemark = 1;

	 @Description(remark="排序")
	 @Column(name="orderNo" ,nullable=false )
	 private Integer orderNo = 0;


	public MatParamsEntity() {

	}

	
	/**
	  * 设置资料标题ID的值
	 * @param 	subjectId	 资料标题ID
	**/
	public void setSubjectId(Long  subjectId){
		 this.subjectId=subjectId;
 	}

	/**
	  * 获取资料标题ID的值
	 * @return 返回资料标题ID的值
	**/
	public Long getSubjectId(){
		 return subjectId;
 	}

	/**
	  * 设置资料项名称的值
	 * @param 	name	 资料项名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取资料项名称的值
	 * @return 返回资料项名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置是否必填项的值
	 * @param 	allowBlank	 是否必填项
	**/
	public void setAllowBlank(Integer  allowBlank){
		 this.allowBlank=allowBlank;
 	}

	/**
	  * 获取是否必填项的值
	 * @return 返回是否必填项的值
	**/
	public Integer getAllowBlank(){
		 return allowBlank;
 	}

	/**
	  * 设置是否支持附件的值
	 * @param 	isAttach	 是否支持附件
	**/
	public void setIsAttach(Integer  isAttach){
		 this.isAttach=isAttach;
 	}

	/**
	  * 获取是否支持附件的值
	 * @return 返回是否支持附件的值
	**/
	public Integer getIsAttach(){
		 return isAttach;
 	}

	/**
	  * 设置是否需要备注的值
	 * @param 	isRemark	 是否需要备注
	**/
	public void setIsRemark(Integer  isRemark){
		 this.isRemark=isRemark;
 	}

	/**
	  * 获取是否需要备注的值
	 * @return 返回是否需要备注的值
	**/
	public Integer getIsRemark(){
		 return isRemark;
 	}

	/**
	  * 设置排序的值
	 * @param 	orderNo	 排序
	**/
	public void setOrderNo(Integer  orderNo){
		 this.orderNo=orderNo;
 	}

	/**
	  * 获取排序的值
	 * @return 返回排序的值
	**/
	public Integer getOrderNo(){
		 return orderNo;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,subjectId,name,allowBlank,isAttach,isRemark,orderNo,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","subjectId","name","allowBlank","isAttach","isRemark","orderNo","remark"};
	}

}
