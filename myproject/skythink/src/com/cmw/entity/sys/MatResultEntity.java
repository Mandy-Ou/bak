package com.cmw.entity.sys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 资料确认结果
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料确认结果实体",createDate="2012-12-26T00:00:00",author="pdt")
@Entity
@Table(name="ts_MatResult")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class MatResultEntity extends IdBaseEntity {
	
	
	 @Description(remark="系统ID")
	 @Column(name="sysId" ,nullable=false )
	 private Long sysId;

	 @Description(remark="业务单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="确认结果")
	 @Column(name="result" ,nullable=false ,length=50 )
	 private String result;

	 @Description(remark="备用formId2")
	 @Column(name="formId2" ,length=50 )
	 private String formId2;

	 @Description(remark="备注值")
	 @Column(name="isRemark" ,nullable=false )
	 private Integer isRemark = 1;


	public MatResultEntity() {

	}

	
	/**
	  * 设置系统ID的值
	 * @param 	sysId	 系统ID
	**/
	public void setSysId(Long  sysId){
		 this.sysId=sysId;
 	}

	/**
	  * 获取系统ID的值
	 * @return 返回系统ID的值
	**/
	public Long getSysId(){
		 return sysId;
 	}

	/**
	  * 设置业务单ID的值
	 * @param 	formId	 业务单ID
	**/
	public void setFormId(Long  formId){
		 this.formId=formId;
 	}

	/**
	  * 获取业务单ID的值
	 * @return 返回业务单ID的值
	**/
	public Long getFormId(){
		 return formId;
 	}

	/**
	  * 设置确认结果的值
	 * @param 	result	 确认结果
	**/
	public void setResult(String  result){
		 this.result=result;
 	}

	/**
	  * 获取确认结果的值
	 * @return 返回确认结果的值
	**/
	public String getResult(){
		 return result;
 	}

	/**
	  * 设置附件ID列表的值
	 * @param 	attachIds	 附件ID列表
	**/
	public void setFormId2(String  formId2){
		 this.formId2=formId2;
 	}

	/**
	  * 获取附件ID列表的值
	 * @return 返回附件ID列表的值
	**/
	public String getFormId2(){
		 return formId2;
 	}

	/**
	  * 设置备注值的值
	 * @param 	isRemark	 备注值
	**/
	public void setIsRemark(Integer  isRemark){
		 this.isRemark=isRemark;
 	}

	/**
	  * 获取备注值的值
	 * @return 返回备注值的值
	**/
	public Integer getIsRemark(){
		 return isRemark;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,sysId,formId,result,formId2,isRemark,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","sysId","formId","result","formId2","isRemark","remark"};
	}

}
