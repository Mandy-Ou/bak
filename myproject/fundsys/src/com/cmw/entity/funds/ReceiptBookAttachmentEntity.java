package com.cmw.entity.funds;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;



/**
 * 承诺书附件表
 * @author 郑符明
 * @date 2014-02-22T00:00:00
 */
@Description(remark="承诺书附件表实体",createDate="2014-02-22T00:00:00",author="郑符明")
@Entity
@Table(name="fu_ReceiptBookAttachment")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ReceiptBookAttachmentEntity extends IdEntity {
	
	
	 @Description(remark="汇票承诺书ID")
	 @Column(name="receiptBookId" ,nullable=false )
	 private Long receiptBookId;

	 @Description(remark="材料名称")
	 @Column(name="name" ,nullable=false ,length=50 )
	 private String name;

	 @Description(remark="状态(是否提供)")
	 @Column(name="status" ,nullable=false )
	 private Integer status = 1;

	 @Description(remark="备注")
	 @Column(name="remark" ,length=200 )
	 private String remark;


	public ReceiptBookAttachmentEntity() {

	}

	
	/**
	  * 设置汇票承诺书ID的值
	 * @param 	receiptBookId	 汇票承诺书ID
	**/
	public void setReceiptBookId(Long  receiptBookId){
		 this.receiptBookId=receiptBookId;
 	}

	/**
	  * 获取汇票承诺书ID的值
	 * @return 返回汇票承诺书ID的值
	**/
	public Long getReceiptBookId(){
		 return receiptBookId;
 	}

	/**
	  * 设置材料名称的值
	 * @param 	name	 材料名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取材料名称的值
	 * @return 返回材料名称的值
	**/
	public String getName(){
		 return name;
 	}

	/**
	  * 设置状态(是否提供)的值
	 * @param 	status	 状态(是否提供)
	**/
	public void setStatus(Integer  status){
		 this.status=status;
 	}

	/**
	  * 获取状态(是否提供)的值
	 * @return 返回状态(是否提供)的值
	**/
	public Integer getStatus(){
		 return status;
 	}

	/**
	  * 设置备注的值
	 * @param 	remark	 备注
	**/
	public void setRemark(String  remark){
		 this.remark=remark;
 	}

	/**
	  * 获取备注的值
	 * @return 返回备注的值
	**/
	public String getRemark(){
		 return remark;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{receiptBookId,name,status,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"receiptBookId","name","status","remark"};
	}

}
