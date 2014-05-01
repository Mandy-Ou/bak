package com.cmw.entity.funds;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdEntity;


/**
 * 收条-汇票承诺书关联表
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="收条-汇票承诺书关联表实体",createDate="2014-02-20T00:00:00",author="郑符明")
@Entity
@Table(name="fu_Rbrelation")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RbrelationEntity extends IdEntity {
	
	
	 @Description(remark="汇票承诺书ID")
	 @Column(name="receiptBookId" ,nullable=false )
	 private Long receiptBookId;

	 @Description(remark="汇票收条ID")
	 @Column(name="receiptId" ,nullable=false )
	 private Long receiptId;


	public RbrelationEntity() {

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
	  * 设置汇票收条ID的值
	 * @param 	receiptId	 汇票收条ID
	**/
	public void setReceiptId(Long  receiptId){
		 this.receiptId=receiptId;
 	}

	/**
	  * 获取汇票收条ID的值
	 * @return 返回汇票收条ID的值
	**/
	public Long getReceiptId(){
		 return receiptId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{receiptBookId,receiptId};
	}

	@Override
	public String[] getFields() {
		return new String[]{"receiptBookId","receiptId"};
	}

}
