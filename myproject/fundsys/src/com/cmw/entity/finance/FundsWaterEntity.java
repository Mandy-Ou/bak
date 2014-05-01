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
 * 资金流水
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="资金流水实体",createDate="2013-08-13T00:00:00",author="pdh")
@Entity
@Table(name="fc_FundsWater")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class FundsWaterEntity extends IdBaseEntity {
	
	
	 @Description(remark="自有资金ID")
	 @Column(name="ownfundsId" ,nullable=false )
	 private Long ownfundsId;

	 @Description(remark="流水类型")
	 @Column(name="waterType" ,nullable=false )
	 private Integer waterType;
	 
	 @Description(remark="业务标识")
	 @Column(name="bussTag" ,nullable=false )
	 private Integer bussTag;

	 @Description(remark="贷出/存入金额")
	 @Column(name="amounts" ,nullable=false )
	 private BigDecimal amounts = new BigDecimal(0.00);

	 @Description(remark="实收金额日志ID")
	 @Column(name="amountlogId" ,nullable=false )
	 private Long amountlogId;
	 
	 
	 @Description(remark="其他费用分类")
	 @Column(name="otherSort" ,nullable=true )
	private Long otherSort;
	 
	 

	public FundsWaterEntity() {

	}
	
	
	/**
	 * @return the bussTag
	 */
	public Integer getBussTag() {
		return bussTag;
	}


	/**
	 * @param bussTag the bussTag to set
	 */
	public void setBussTag(Integer bussTag) {
		this.bussTag = bussTag;
	}


	/**
	  * 设置自有资金ID的值
	 * @param 	ownfundsId	 自有资金ID
	**/
	public void setOwnfundsId(Long  ownfundsId){
		 this.ownfundsId=ownfundsId;
 	}

	/**
	  * 获取自有资金ID的值
	 * @return 返回自有资金ID的值
	**/
	public Long getOwnfundsId(){
		 return ownfundsId;
 	}

	/**
	  * 设置流水类型的值
	 * @param 	waterType	 流水类型
	**/
	public void setWaterType(Integer  waterType){
		 this.waterType=waterType;
 	}

	/**
	  * 获取流水类型的值
	 * @return 返回流水类型的值
	**/
	public Integer getWaterType(){
		 return waterType;
 	}


	/**
	  * 设置贷出/存入金额的值
	 * @param 	amounts	 贷出/存入金额
	**/
	public void setAmounts(BigDecimal  amounts){
		 this.amounts=amounts;
 	}

	/**
	  * 获取贷出/存入金额的值
	 * @return 返回贷出/存入金额的值
	**/
	public BigDecimal getAmounts(){
		 return amounts;
 	}

	/**
	  * 设置实收金额日志ID的值
	 * @param 	amountlogId	 实收金额日志ID
	**/
	public void setAmountlogId(Long  amountlogId){
		 this.amountlogId=amountlogId;
 	}

	/**
	  * 获取实收金额日志ID的值
	 * @return 返回实收金额日志ID的值
	**/
	public Long getAmountlogId(){
		 return amountlogId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{bussTag,ownfundsId,waterType,amounts,amountlogId,otherSort};
	}

	@Override
	public String[] getFields() {
		return new String[]{"bussTag","ownfundsId","waterType","amounts","amountlogId","otherSort"};
	}


	public Long getOtherSort() {
		return otherSort;
	}


	public void setOtherSort(Long otherSort) {
		this.otherSort = otherSort;
	}

}
