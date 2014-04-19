package com.cmw.entity.finance;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 息费豁免列表
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免列表实体",createDate="2013-09-14T00:00:00",author="程明卫")
@Entity
@Table(name="fc_ExeItems")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class ExeItemsEntity extends IdBaseEntity {
	
	
	 @Description(remark="豁免申请单ID")
	 @Column(name="exemptId" ,nullable=false )
	 private Long exemptId;

	 @Description(remark="业务单ID")
	 @Column(name="formId" ,nullable=false )
	 private Long formId;

	 @Description(remark="豁免利息")
	 @Column(name="rat" ,nullable=false ,scale=2)
	 private BigDecimal rat = new BigDecimal("0");

	 @Description(remark="豁免管理费")
	 @Column(name="mat" ,nullable=false ,scale=2)
	 private BigDecimal mat = new BigDecimal("0");

	 @Description(remark="放款/提前还款手续费")
	 @Column(name="fat" ,nullable=false ,scale=2)
	 private BigDecimal fat = new BigDecimal("0");

	 @Description(remark="罚息")
	 @Column(name="pat" ,nullable=false ,scale=2)
	 private BigDecimal pat = new BigDecimal("0");

	 @Description(remark="滞纳金")
	 @Column(name="dat" ,nullable=false ,scale=2)
	 private BigDecimal dat = new BigDecimal("0");

	 @Description(remark="豁免合计")
	 @Column(name="totalAmount" ,nullable=false ,scale=2)
	 private BigDecimal totalAmount = new BigDecimal("0");


	public ExeItemsEntity() {

	}

	
	/**
	  * 设置豁免申请单ID的值
	 * @param 	exemptId	 豁免申请单ID
	**/
	public void setExemptId(Long  exemptId){
		 this.exemptId=exemptId;
 	}

	/**
	  * 获取豁免申请单ID的值
	 * @return 返回豁免申请单ID的值
	**/
	public Long getExemptId(){
		 return exemptId;
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
	  * 设置豁免利息的值
	 * @param 	rat	 豁免利息
	**/
	public void setRat(BigDecimal  rat){
		 this.rat=rat;
 	}

	/**
	  * 获取豁免利息的值
	 * @return 返回豁免利息的值
	**/
	public BigDecimal getRat(){
		 return rat;
 	}

	/**
	  * 设置豁免管理费的值
	 * @param 	mat	 豁免管理费
	**/
	public void setMat(BigDecimal  mat){
		 this.mat=mat;
 	}

	/**
	  * 获取豁免管理费的值
	 * @return 返回豁免管理费的值
	**/
	public BigDecimal getMat(){
		 return mat;
 	}

	/**
	  * 设置放款/提前还款手续费的值
	 * @param 	fat	 放款/提前还款手续费
	**/
	public void setFat(BigDecimal  fat){
		 this.fat=fat;
 	}

	/**
	  * 获取放款/提前还款手续费的值
	 * @return 返回放款/提前还款手续费的值
	**/
	public BigDecimal getFat(){
		 return fat;
 	}

	/**
	  * 设置罚息的值
	 * @param 	pat	 罚息
	**/
	public void setPat(BigDecimal  pat){
		 this.pat=pat;
 	}

	/**
	  * 获取罚息的值
	 * @return 返回罚息的值
	**/
	public BigDecimal getPat(){
		 return pat;
 	}

	/**
	  * 设置滞纳金的值
	 * @param 	dat	 滞纳金
	**/
	public void setDat(BigDecimal  dat){
		 this.dat=dat;
 	}

	/**
	  * 获取滞纳金的值
	 * @return 返回滞纳金的值
	**/
	public BigDecimal getDat(){
		 return dat;
 	}

	/**
	  * 设置豁免合计的值
	 * @param 	totalAmount	 豁免合计
	**/
	public void setTotalAmount(BigDecimal  totalAmount){
		 this.totalAmount=totalAmount;
 	}

	/**
	  * 获取豁免合计的值
	 * @return 返回豁免合计的值
	**/
	public BigDecimal getTotalAmount(){
		 return totalAmount;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{exemptId,formId,rat,mat,fat,pat,dat,totalAmount};
	}

	@Override
	public String[] getFields() {
		return new String[]{"exemptId","formId","rat","mat","fat","pat","dat","totalAmount"};
	}

}
