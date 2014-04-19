package com.cmw.entity.finance;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.entity.IdBaseEntity;


/**
 * 利率
 * @author 彭登浩
 * @date 2012-12-06T00:00:00
 */
@Description(remark="利率实体",createDate="2012-12-06T00:00:00",author="彭登浩")
@Entity
@Table(name="fc_rate")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class RateEntity extends IdBaseEntity {
	
	
	 @Description(remark="编号")
	 @Column(name="code" ,nullable=false ,length=20 )
	 private String code;


	 @Description(remark="利率类型")
	 @Column(name="types" ,nullable=false )
	 private Integer types=0;



	 @Description(remark="利率期限")
	 @Column(name="limits" ,nullable=false )
	 private Integer limits;

	 @Description(remark="利率值")
	 @Column(name="val" ,nullable=false ,scale=2)
	 private Double val;



	 @Description(remark="利率生效日期")
	 @Column(name="bdate" )
	 private Date bdate;



	 @Description(remark="是否启用公式")
	 @Column(name="isFormula" ,nullable=false )
	 private Integer isFormula = 0;



	 @Description(remark="公式ID")
	 @Column(name="formulaId" )
	 private Long formulaId;




	public RateEntity() {

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
	  * 设置利率类型的值
	 * @param 	type	 利率类型
	**/
	public void setTypes(Integer  types){
		 this.types=types;
 	}

	/**
	  * 获取利率类型的值
	 * @return 返回利率类型的值
	**/
	public Integer getTypes(){
		 return types;
 	}

	/**
	  * 设置利率期限的值
	 * @param 	limit	 利率期限
	**/
	public void setLimits(Integer  limits){
		 this.limits=limits;
 	}

	/**
	  * 获取利率期限的值
	 * @return 返回利率期限的值
	**/
	public Integer getLimits(){
		 return limits;
 	}

	/**
	  * 设置利率值的值
	 * @param 	val	 利率值
	**/
	public void setVal(Double  val){
		 this.val=val;
 	}

	/**
	  * 获取利率值的值
	 * @return 返回利率值的值
	**/
	public Double getVal(){
		 return val;
 	}

	/**
	  * 设置利率生效日期的值
	 * @param 	bdate	 利率生效日期
	**/
	public void setBdate(Date  bdate){
		 this.bdate=bdate;
 	}

	/**
	  * 获取利率生效日期的值
	 * @return 返回利率生效日期的值
	**/
	public Date getBdate(){
		 return bdate;
 	}

	/**
	  * 设置是否启用公式的值
	 * @param 	isFormula	 是否启用公式
	**/
	public void setIsFormula(Integer  isFormula){
		 this.isFormula=isFormula;
 	}

	/**
	  * 获取是否启用公式的值
	 * @return 返回是否启用公式的值
	**/
	public Integer getIsFormula(){
		 return isFormula;
 	}
	/**
	  * 设置公式ID的值
	 * @param 	formulaId	 公式ID
	**/
	public void setFormulaId(Long  formulaId){
		 this.formulaId=formulaId;
 	}

	/**
	  * 获取公式ID的值
	 * @return 返回公式ID的值
	**/
	public Long getFormulaId(){
		 return formulaId;
 	}



	@Override
	public Object[] getDatas() {
		return new Object[]{id,isenabled,code,types,limits,val,bdate,isFormula,formulaId,remark};
	}

	@Override
	public String[] getFields() {
		return new String[]{"id","isenabled","code","types","limits","val","bdate#yyyy-MM-dd","isFormula","formulaId","remark"};
	}

}
