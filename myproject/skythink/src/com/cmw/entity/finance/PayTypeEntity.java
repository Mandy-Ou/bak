package com.cmw.entity.finance;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 还款方式
 * @author 程明卫
 * @date 2013-01-23T00:00:00
 */
@Description(remark="还款方式实体",createDate="2013-01-23T00:00:00",author="程明卫")
@Entity
@Table(name="fc_PayType")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class PayTypeEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=50 )
	 private String code;

	 @Description(remark="还款方式名称")
	 @Column(name="name" ,nullable=false ,length=60 )
	 private String name;
	 
	 @Description(remark="算法接口")
	 @Column(name="inter" ,length=50 )
	 private String inter;

	 @Description(remark="当期应还利息")
	 @Column(name="rateDispFormula" ,length=255 )
	 private String rateDispFormula;

	 @Description(remark="利息计算公式")
	 @Column(name="rateFormula" ,length=255 )
	 private String rateFormula;

	 @Description(remark="利息参数")
	 @Column(name="rateParams" ,length=150 )
	 private String rateParams;

	 @Description(remark="当期应还本金")
	 @Column(name="amoutDispFormula" ,length=255 )
	 private String amoutDispFormula;

	 @Description(remark="本金计算公式")
	 @Column(name="amountFormula" ,length=255 )
	 private String amountFormula;

	 @Description(remark="本金参数")
	 @Column(name="amountParams" ,length=150 )
	 private String amountParams;

	 @Description(remark="当期还本付息")
	 @Column(name="raDispFormula" ,length=255 )
	 private String raDispFormula;

	 @Description(remark="还本付息公式")
	 @Column(name="raFormula" ,length=255 )
	 private String raFormula;

	 @Description(remark="还本付息参数")
	 @Column(name="raParams" ,length=150 )
	 private String raParams;


	public PayTypeEntity() {

	}

	
	/**
	  * 设置编号的值
	 * @param 	code	 编号
	**/
	public void setCode(String  code){
		 this.code=code;
 	}

	/**
	  * 获取编号的值
	 * @return 返回编号的值
	**/
	public String getCode(){
		 return code;
 	}

	/**
	  * 设置还款方式名称的值
	 * @param 	name	 还款方式名称
	**/
	public void setName(String  name){
		 this.name=name;
 	}

	/**
	  * 获取还款方式名称的值
	 * @return 返回还款方式名称的值
	**/
	public String getName(){
		 return name;
 	}
	
	
	public String getInter() {
		return inter;
	}


	public void setInter(String inter) {
		this.inter = inter;
	}


	/**
	  * 设置当期应还利息的值
	 * @param 	rateDispFormula	 当期应还利息
	**/
	public void setRateDispFormula(String  rateDispFormula){
		 this.rateDispFormula=rateDispFormula;
 	}

	/**
	  * 获取当期应还利息的值
	 * @return 返回当期应还利息的值
	**/
	public String getRateDispFormula(){
		 return rateDispFormula;
 	}

	/**
	  * 设置利息计算公式的值
	 * @param 	rateFormula	 利息计算公式
	**/
	public void setRateFormula(String  rateFormula){
		 this.rateFormula=rateFormula;
 	}

	/**
	  * 获取利息计算公式的值
	 * @return 返回利息计算公式的值
	**/
	public String getRateFormula(){
		 return rateFormula;
 	}

	/**
	  * 设置利息参数的值
	 * @param 	rateParams	 利息参数
	**/
	public void setRateParams(String  rateParams){
		 this.rateParams=rateParams;
 	}

	/**
	  * 获取利息参数的值
	 * @return 返回利息参数的值
	**/
	public String getRateParams(){
		 return rateParams;
 	}

	/**
	  * 设置当期应还本金的值
	 * @param 	amoutDispFormula	 当期应还本金
	**/
	public void setAmoutDispFormula(String  amoutDispFormula){
		 this.amoutDispFormula=amoutDispFormula;
 	}

	/**
	  * 获取当期应还本金的值
	 * @return 返回当期应还本金的值
	**/
	public String getAmoutDispFormula(){
		 return amoutDispFormula;
 	}

	/**
	  * 设置本金计算公式的值
	 * @param 	amountFormula	 本金计算公式
	**/
	public void setAmountFormula(String  amountFormula){
		 this.amountFormula=amountFormula;
 	}

	/**
	  * 获取本金计算公式的值
	 * @return 返回本金计算公式的值
	**/
	public String getAmountFormula(){
		 return amountFormula;
 	}

	/**
	  * 设置本金参数的值
	 * @param 	amountParams	 本金参数
	**/
	public void setAmountParams(String  amountParams){
		 this.amountParams=amountParams;
 	}

	/**
	  * 获取本金参数的值
	 * @return 返回本金参数的值
	**/
	public String getAmountParams(){
		 return amountParams;
 	}

	/**
	  * 设置当期还本付息的值
	 * @param 	raDispFormula	 当期还本付息
	**/
	public void setRaDispFormula(String  raDispFormula){
		 this.raDispFormula=raDispFormula;
 	}

	/**
	  * 获取当期还本付息的值
	 * @return 返回当期还本付息的值
	**/
	public String getRaDispFormula(){
		 return raDispFormula;
 	}

	/**
	  * 设置还本付息公式的值
	 * @param 	raFormula	 还本付息公式
	**/
	public void setRaFormula(String  raFormula){
		 this.raFormula=raFormula;
 	}

	/**
	  * 获取还本付息公式的值
	 * @return 返回还本付息公式的值
	**/
	public String getRaFormula(){
		 return raFormula;
 	}

	/**
	  * 设置还本付息参数的值
	 * @param 	raParams	 还本付息参数
	**/
	public void setRaParams(String  raParams){
		 this.raParams=raParams;
 	}

	/**
	  * 获取还本付息参数的值
	 * @return 返回还本付息参数的值
	**/
	public String getRaParams(){
		 return raParams;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{getId(),name,code,inter,rateDispFormula,rateFormula,rateParams,amoutDispFormula,amountFormula,amountParams,raDispFormula,raFormula,raParams,isenabled,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","name","code","inter","rateDispFormula","rateFormula","rateParams","amoutDispFormula","amountFormula","amountParams","raDispFormula","raFormula","raParams","isenabled","remark"};
	}

}
